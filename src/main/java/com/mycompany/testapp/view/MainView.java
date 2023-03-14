package com.mycompany.testapp.view;

import com.mycompany.testapp.model.Mkb10;
import com.mycompany.testapp.model.Mkb10TreeView;
import com.mycompany.testapp.services.DataService;
import com.vaadin.componentfactory.explorer.ExplorerTreeGrid;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.MultiSortPriority;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.Collection;

@Route
public class MainView extends VerticalLayout {

	private Mkb10TreeView getTreeViewElem(Mkb10 mkb10, Collection<Mkb10> mkb10s) {
		if (mkb10 == null) {
			return null;
		} else {
			if (mkb10.getCodeParent() == null) {
				return new Mkb10TreeView(mkb10.getCode(), mkb10.getName(), null);
			} else {
				Mkb10 t = mkb10s.stream()
					.filter(el -> el.getCode().equalsIgnoreCase(mkb10.getCodeParent()))
					.findAny().orElse(null);
				return new Mkb10TreeView(mkb10.getCode(), mkb10.getName(), getTreeViewElem(t, mkb10s));
			}
		}
	}

	public MainView() {
		Grid<Mkb10> grid = new Grid<Mkb10>(Mkb10.class, false);
		grid.addColumn(Mkb10::getCode).setHeader("Код").setSortable(true);
		grid.addColumn(Mkb10::getName).setHeader("Наименование").setSortable(true);
		grid.addColumn(Mkb10::getCodeParent).setHeader("Код родителя").setSortable(true);
		grid.setMultiSort(true, MultiSortPriority.APPEND);

		Collection<Mkb10> mkb10s = (new DataService()).getData();
		GridListDataView<Mkb10> dataView = grid.setItems(mkb10s);

		TextField searchField = new TextField();
		searchField.setWidth("50%");
		searchField.setPlaceholder("Search");
		searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
		searchField.setValueChangeMode(ValueChangeMode.EAGER);
		searchField.addValueChangeListener(e -> dataView.refreshAll());

		dataView.addFilter(mkb10 -> {
			String searchTerm = searchField.getValue().trim();
			if (searchTerm.isEmpty()) {
				return true;
			}
			boolean matchesCode = matchesTerm(mkb10.getCode(), searchTerm);
			boolean matchesName = matchesTerm(mkb10.getName(), searchTerm);
			boolean matchesCodeParent = matchesTerm(mkb10.getCodeParent(), searchTerm);
			return matchesCode || matchesName || matchesCodeParent;
		});

		Collection<Mkb10TreeView> treeViews = new ArrayList<>();
		for (var elem : mkb10s) {
			treeViews.add(getTreeViewElem(elem, mkb10s));
		}
		TreeData<Mkb10TreeView> treeData = new TreeData<>();
		treeData.addRootItems(treeViews);
		TreeDataProvider<Mkb10TreeView> inMemoryDataProvider = new TreeDataProvider<>(treeData);
		ExplorerTreeGrid<Mkb10TreeView> grid1 = new ExplorerTreeGrid<>();
		grid1.addHierarchyColumn(Mkb10TreeView::getCode).setHeader("Код");
		grid1.setItems(inMemoryDataProvider);
		
		VerticalLayout layout = new VerticalLayout(searchField, grid1, grid);
		layout.setPadding(false);

		add(layout);
	}

	private boolean matchesTerm(String value, String searchTerm) {
		return value.toLowerCase().contains(searchTerm.toLowerCase());
	}
}
