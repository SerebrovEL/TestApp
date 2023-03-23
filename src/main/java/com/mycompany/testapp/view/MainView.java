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

		Grid<Mkb10> grid = new Grid<>(Mkb10.class, false);
		grid.addColumn(Mkb10::getId).setHeader("ID").setSortable(true);
		grid.addColumn(Mkb10::getRecCode).setHeader("Код записи").setSortable(true);
		grid.addColumn(Mkb10::getCode).setHeader("Код").setSortable(true);
		grid.addColumn(Mkb10::getName).setHeader("Наименование").setSortable(true);
		grid.addColumn(Mkb10::getCodeParent).setHeader("ID родителя").setSortable(true);
		grid.addColumn(Mkb10::getActusl).setHeader("Актуальность записи").setSortable(true);
		grid.setMultiSort(true, MultiSortPriority.APPEND);
		grid.setItems(mkb10s);

		TextField searchField = new TextField();
		searchField.setWidth("50%");
		searchField.setPlaceholder("Search");
		searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
		searchField.setValueChangeMode(ValueChangeMode.EAGER);
		searchField.addValueChangeListener(e -> grid.getListDataView().refreshAll());

		ExplorerTreeGrid<Mkb10> treeGrid = new ExplorerTreeGrid<>();
		treeGrid.setAllRowsVisible(false);
		treeGrid.setItems(mkb10TreeService.getRootTreeData(), mkb10TreeService::getChildTreeData);
		treeGrid.addHierarchyColumn(Mkb10::getCode).setHeader("Коды заболеваний");
		treeGrid.setWidthFull();
		treeGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
		treeGrid.setHeight("100%");
		treeGrid.setWidth("20%");
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
		searchField.addKeyPressListener(listener -> {
			grid.getListDataView().addFilter(mkb10 -> {
				String searchTerm = searchField.getValue().trim();
				if (searchTerm.isEmpty()) {
					return true;
				}
				Mkb10 sel = treeGrid.getSelectedItems().stream().findFirst().orElse(null);
				if (sel == null) {
					boolean matchesCode = matchesTermCode(mkb10.getCode(), searchTerm);
					boolean matchesName = matchesTerm(mkb10.getName(), searchTerm);
					boolean matchesCodeParent = matchesTermCode(mkb10.getCodeParent(), searchTerm);
					return matchesCode || matchesName || matchesCodeParent;
				} else {
					boolean b = matchesTermRecCode(mkb10.getRecCode().trim(), searchTerm);
					boolean matchesCode = matchesTermCode(mkb10.getCode(), searchTerm);
					boolean matchesName = matchesTerm(mkb10.getName(), searchTerm);
					boolean matchesCodeParent = matchesTermCode(mkb10.getCodeParent(), searchTerm);
					return b || (matchesCode || matchesName || matchesCodeParent);
				}
			});
			if (searchField.getValue().trim().isEmpty()
				&& treeGrid.getSelectedItems().stream().findFirst().orElse(null) == null) {
				grid.getListDataView().removeFilters();
			}
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

	private boolean matchesTermRecCode(String value, String searchTerm) {
		return value.toLowerCase().indexOf(searchTerm.toLowerCase()) == 0;
	}

	private boolean matchesTerm(String value, String searchTerm) {
		return value.toLowerCase().contains(searchTerm.toLowerCase());
	}

	private boolean matchesTermCode(String value, String searchTerm) {
		return value.toLowerCase().indexOf(searchTerm.toLowerCase()) == 0;
	}
}
