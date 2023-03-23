package com.mycompany.testapp.services;

import com.mycompany.testapp.model.Mkb10;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mkb10TreeService {

	private List<Mkb10> dataList = null;
	private Map<String, List<Mkb10>> dataChild = null;

	public Mkb10TreeService(List<Mkb10> mkb10s) {
		this.dataList = mkb10s;
		this.dataChild = mkb10s.stream()
			.collect(Collectors.groupingBy(Mkb10::getCodeParent));
	}

	public List<Mkb10> getRootTreeData() {
		return this.dataList.stream()
			.filter(elem -> elem.getCodeParent() == null
			|| elem.getCodeParent().trim().isEmpty()
			|| elem.getCodeParent().trim().isBlank()
			|| elem.getCodeParent().trim().equalsIgnoreCase("NULL"))
			.collect(Collectors.toList());
	}

	public List<Mkb10> getChildTreeData(Mkb10 parent) {
		return this.dataChild
			.getOrDefault(parent.getId(), new ArrayList<>());
	}

}
