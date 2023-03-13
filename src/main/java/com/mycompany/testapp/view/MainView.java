package com.mycompany.testapp.view;

import com.mycompany.testapp.model.Mkb10;
import com.mycompany.testapp.services.DataService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.MultiSortPriority;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import java.util.Collection;

@Route
public class MainView extends VerticalLayout {

	public MainView() {
		Grid<Mkb10> grid = new Grid<>(Mkb10.class, false);
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

		VerticalLayout layout = new VerticalLayout(searchField, grid);
		layout.setPadding(false);

		add(layout);
	}

	private boolean matchesTerm(String value, String searchTerm) {
		return value.toLowerCase().contains(searchTerm.toLowerCase());
	}
}
