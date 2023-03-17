package com.mycompany.testapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class Mkb10TreeData {

	protected Collection<Mkb10Data> dataList;

	public Mkb10TreeData() {
		this.dataList = null;
	}

	public Collection<Mkb10Data> createTreeData(Collection<Mkb10> mkb10s) {
		this.dataList = new ArrayList<>();
		Collection<Mkb10> roots = mkb10s.stream()
			.filter(elem -> elem.getCodeParent() == null
			|| elem.getCodeParent().isEmpty())
			.collect(Collectors.toList());
		roots.forEach(elem -> this.dataList.add(new Mkb10Data(elem.getCode(), elem.getName(), "", null)));
		Collection<Mkb10> childs = mkb10s.stream()
			.filter(elem -> elem.getCodeParent() != null
			&& !elem.getCodeParent().isEmpty())
			.collect(Collectors.toList());
		for (var elem : childs) {
			Mkb10Data root = this.dataList.stream()
				.filter(el -> el.getCode().equalsIgnoreCase(elem.getCodeParent()))
				.findFirst().orElse(null);
			this.dataList.add(new Mkb10Data(elem.getCode(), elem.getName(), "", root));
		}
		return this.dataList;
	}

	public Collection<Mkb10Data> getRootTreeData() {
		return this.dataList.stream()
			.filter(treeDataList -> treeDataList.getParent() == null)
			.collect(Collectors.toList());
	}

	public Collection<Mkb10Data> getChildTreeData(Mkb10Data parent) {
		return this.dataList.stream().filter(
			treeDataList -> Objects.equals(treeDataList.getParent(), parent))
			.collect(Collectors.toList());
	}
}
