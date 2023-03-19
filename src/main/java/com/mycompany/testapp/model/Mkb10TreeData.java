package com.mycompany.testapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class Mkb10TreeData {

	protected Collection<Mkb10Data> dataList;

	public Mkb10TreeData() {
		this.dataList = new ArrayList<>();
	}

	public void createTreeData(Collection<Mkb10> mkb10s) {
		addRoot(mkb10s);
		addChild(mkb10s);
	}

	public Collection<Mkb10Data> getRootTreeData() {
		return this.dataList.stream()
			.filter(treeDataList -> treeDataList.getParent() == null)
			.sorted((o1, o2) -> o1.getCode().trim().compareTo(o2.getCode().trim()))
			.collect(Collectors.toList());
	}

	public Collection<Mkb10Data> getChildTreeData(Mkb10Data parent) {
		return this.dataList.stream()
			.filter(treeDataList -> Objects.equals(treeDataList.getParent(), parent))
			.sorted((o1, o2) -> o1.getCode().trim().compareTo(o2.getCode().trim()))
			.collect(Collectors.toList());
	}

	protected void addRoot(Collection<Mkb10> mkb10s) {
		mkb10s.stream()
			.filter(elem -> elem.getCodeParent() == null
			|| elem.getCodeParent().trim().isEmpty()
			|| elem.getCodeParent().trim().isBlank()
			|| elem.getCodeParent().trim().contains("NULL"))
			.sorted((o1, o2) -> o1.getCode().trim().compareTo(o2.getCode().trim()))
			.distinct()
			.collect(Collectors.toList())
			.forEach(elem -> this.dataList.add(new Mkb10Data(elem.getCode(), "", null)));
	}

	protected void addChild(Collection<Mkb10> mkb10s) {
		int i = 0;
		while (true) {
			final int level = i++;
			Collection<Mkb10Data> par = this.dataList.stream()
				.filter(elem -> elem.getLevel() == level)
				.sorted((o1, o2) -> o1.getCode().trim().compareTo(o2.getCode().trim()))
				.collect(Collectors.toList());
			if (par.isEmpty()) {
				break;
			}
			par.forEach(root -> {
				mkb10s.stream()
					.filter(elem -> elem.getCodeParent() != null
					&& !elem.getCodeParent().trim().isEmpty()
					&& elem.getCodeParent().trim().equalsIgnoreCase(root.getCode().trim())
					)
					.sorted((o1, o2) -> o1.getCode().trim().compareTo(o2.getCode().trim()))
					.collect(Collectors.toList())
					.forEach(elem -> {
						this.dataList
							.add(
								new Mkb10Data(
									elem.getCode().trim(),
									"",
									this.dataList
										.stream()
										.filter(el -> el.getCode()
										.trim()
										.equalsIgnoreCase(root.getCode().trim()))
										.findFirst().orElse(null)
								)
							);
					});
			});
		}
	}

}
