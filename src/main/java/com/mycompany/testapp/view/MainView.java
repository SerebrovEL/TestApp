package com.mycompany.testapp.view;

import com.mycompany.testapp.model.Mkb10;
import com.mycompany.testapp.model.Mkb10Data;
import com.mycompany.testapp.model.Mkb10TreeData;
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
import java.util.Collection;

@Route
public class MainView extends HorizontalLayout {

	public MainView() {
		Collection<Mkb10> mkb10s = (new DataService()).getData();
		Mkb10TreeData mkb10TreeData = new Mkb10TreeData();
		mkb10TreeData.createTreeData(mkb10s);

		Grid<Mkb10> grid = new Grid<Mkb10>(Mkb10.class, false);
		grid.addColumn(Mkb10::getCode).setHeader("Код").setSortable(true);
		grid.addColumn(Mkb10::getName).setHeader("Наименование").setSortable(true);
		grid.addColumn(Mkb10::getCodeParent).setHeader("Код родителя").setSortable(true);
		grid.setMultiSort(true, MultiSortPriority.APPEND);
		grid.setItems(mkb10s);

		TextField searchField = new TextField();
		searchField.setWidth("50%");
		searchField.setPlaceholder("Search");
		searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
		searchField.setValueChangeMode(ValueChangeMode.EAGER);
		searchField.addValueChangeListener(e -> grid.getListDataView().refreshAll());

		ExplorerTreeGrid<Mkb10Data> treeGrid = new ExplorerTreeGrid<>();
		treeGrid.setItems(mkb10TreeData.getRootTreeData(), mkb10TreeData::getChildTreeData);
		treeGrid.addHierarchyColumn(Mkb10Data::getCode).setHeader("Меню (Код заболевания)");
		treeGrid.setWidthFull();
		treeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
		treeGrid.setHeight("100%");
		treeGrid.setWidth("20%");
		treeGrid.addSelectionListener(listener -> {
			grid.getListDataView().removeFilters();
			Mkb10Data sel = listener.getFirstSelectedItem().orElse(null);
			if (sel != null) {
				grid.getListDataView().addFilter(mkb10 -> {
					String searchTerm = sel.getCode().trim();
					if (searchTerm.isEmpty()) {
						return true;
					}
					return matchesTermCode(mkb10.getCode(), searchTerm);
				});
			} else {
				grid.getListDataView().removeFilters();
			}
		});
		searchField.addKeyPressListener(listener -> {
			treeGrid.deselectAll();
			grid.getListDataView().removeFilters();
			grid.getListDataView().addFilter(mkb10 -> {
				String searchTerm = searchField.getValue().trim();
				if (searchTerm.isEmpty()) {
					return true;
				}
				boolean matchesCode = matchesTermCode(mkb10.getCode(), searchTerm);
				boolean matchesName = matchesTerm(mkb10.getName(), searchTerm);
				boolean matchesCodeParent = matchesTermCode(mkb10.getCodeParent(), searchTerm);
				return matchesCode || matchesName || matchesCodeParent;
			});
		});

		VerticalLayout vLayout = new VerticalLayout(searchField, grid);
		vLayout.setPadding(true);
		vLayout.setHeight("100%");
			
		this.add(treeGrid);
		this.add(vLayout);
		this.setVerticalComponentAlignment(Alignment.START, treeGrid);
		this.setVerticalComponentAlignment(Alignment.START, vLayout);
		this.setHeightFull();
		
	}

	private boolean matchesTerm(String value, String searchTerm) {
		return value.toLowerCase().contains(searchTerm.toLowerCase());
	}

	private boolean matchesTermCode(String value, String searchTerm) {
		return value.trim().equalsIgnoreCase(searchTerm.trim());
	}
}
