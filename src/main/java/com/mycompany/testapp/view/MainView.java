package com.mycompany.testapp.view;

import com.mycompany.testapp.model.Mkb10;

import com.mycompany.testapp.services.Mkb10TreeService;
import com.mycompany.testapp.services.DataService;

import com.vaadin.componentfactory.explorer.ExplorerTreeGrid;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.MultiSortPriority;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.data.value.ValueChangeMode;

import com.vaadin.flow.router.Route;

import java.util.List;

@Route
public class MainView extends HorizontalLayout {

	public MainView() {
		List<Mkb10> mkb10s = (new DataService()).getData();
		Mkb10TreeService mkb10TreeService = new Mkb10TreeService(mkb10s);

		Grid<Mkb10> grid = createGrid(mkb10s);
		TextField searchField = createTextField(grid);
		ExplorerTreeGrid<Mkb10> treeGrid = createExplorerTreeGrid(mkb10TreeService);
		VerticalLayout vLayout = createVerticalLayout(grid, searchField);

		addSelectionListenerTreeGrid(treeGrid, grid, searchField);
		addKeyPressListenerSearchField(treeGrid, grid, searchField);

		this.add(treeGrid);
		this.add(vLayout);
		this.setVerticalComponentAlignment(Alignment.START, treeGrid);
		this.setVerticalComponentAlignment(Alignment.START, vLayout);
		this.setHeightFull();

	}

	private Grid<Mkb10> createGrid(List<Mkb10> mkb10s) {
		Grid<Mkb10> grid = new Grid<>(Mkb10.class, false);
		grid.addColumn(Mkb10::getId).setHeader("ID").setSortable(true);
		grid.addColumn(Mkb10::getRecCode).setHeader("Код записи").setSortable(true);
		grid.addColumn(Mkb10::getCode).setHeader("Код").setSortable(true);
		grid.addColumn(Mkb10::getName).setHeader("Наименование").setSortable(true);
		grid.addColumn(Mkb10::getParentId).setHeader("ID родителя").setSortable(true);
		grid.setMultiSort(false, MultiSortPriority.APPEND);
		grid.setItems(mkb10s);
		return grid;
	}

	private TextField createTextField(Grid<Mkb10> grid) {
		TextField searchField = new TextField();
		searchField.setWidth("50%");
		searchField.setPlaceholder("Search");
		searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
		searchField.setValueChangeMode(ValueChangeMode.EAGER);
		searchField.addValueChangeListener(e -> grid.getListDataView().refreshAll());
		return searchField;
	}

	private ExplorerTreeGrid<Mkb10> createExplorerTreeGrid(Mkb10TreeService mkb10TreeService) {
		ExplorerTreeGrid<Mkb10> treeGrid = new ExplorerTreeGrid<>();
		treeGrid.setAllRowsVisible(false);
		treeGrid.setItems(mkb10TreeService.getRootTreeData(), mkb10TreeService::getChildTreeData);
		treeGrid.addHierarchyColumn(Mkb10::getCode).setHeader("Коды заболеваний");
		treeGrid.setWidthFull();
		treeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
		treeGrid.setHeight("100%");
		treeGrid.setWidth("20%");
		return treeGrid;
	}

	private void addSelectionListenerTreeGrid(ExplorerTreeGrid<Mkb10> treeGrid, Grid<Mkb10> grid, TextField searchField) {
		treeGrid.addSelectionListener(listener -> {
			grid.getListDataView().removeFilters();
			Mkb10 sel = listener.getFirstSelectedItem().orElse(null);
			if (sel != null) {
				grid.getListDataView().addFilter(mkb10 -> {
					String searchTerm = sel.getRecCode().trim();
					if (searchTerm.isEmpty()) {
						return true;
					}
					return matchesTermRecCode(mkb10.getRecCode().trim(), searchTerm);
				});
			} else {
				grid.getListDataView().removeFilters();
				searchField.setValue("");
			}
		});
	}

	private VerticalLayout createVerticalLayout(Grid<Mkb10> grid, TextField searchField) {
		VerticalLayout vLayout = new VerticalLayout(searchField, grid);
		vLayout.setPadding(true);
		vLayout.setHeight("100%");
		return vLayout;
	}

	private void addKeyPressListenerSearchField(ExplorerTreeGrid<Mkb10> treeGrid, Grid<Mkb10> grid, TextField searchField) {
		searchField.addKeyPressListener(listener -> {
			grid.getListDataView().addFilter(mkb10 -> {
				String searchTerm = searchField.getValue().trim();
				if (searchTerm.isEmpty()) {
					return true;
				}
				Mkb10 sel = treeGrid.getSelectedItems().stream().findFirst().orElse(null);
				if (sel == null) {
					return matchesTerm(mkb10.getId(), searchTerm)
						|| matchesTerm(mkb10.getCode(), searchTerm)
						|| matchesTerm(mkb10.getName(), searchTerm)
						|| matchesTerm(mkb10.getParentId(), searchTerm);
				} else {
					return matchesTermRecCode(mkb10.getRecCode().trim(), sel.getRecCode())
						&& (matchesTerm(mkb10.getId(), searchTerm)
						|| matchesTerm(mkb10.getCode(), searchTerm)
						|| matchesTerm(mkb10.getName(), searchTerm)
						|| matchesTerm(mkb10.getParentId(), searchTerm));
				}
			});
			if (searchField.getValue().trim().isEmpty()
				&& treeGrid.getSelectedItems().stream().findFirst().orElse(null) == null) {
				grid.getListDataView().removeFilters();
			}
		});
	}

	private boolean matchesTermRecCode(String value, String searchTerm) {
		return value.toLowerCase().indexOf(searchTerm.toLowerCase()) == 0;
	}

	private boolean matchesTerm(String value, String searchTerm) {
		return value.toLowerCase().contains(searchTerm.toLowerCase());
	}

}
