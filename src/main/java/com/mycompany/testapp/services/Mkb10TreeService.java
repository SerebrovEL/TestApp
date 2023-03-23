package com.mycompany.testapp.services;

import com.mycompany.testapp.model.Mkb10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Mkb10TreeService {

	private List<Mkb10> dataRootList = null;
	private Map<String, List<Mkb10>> dataChildList = null;

	public Mkb10TreeService(List<Mkb10> mkb10s) {
		if (mkb10s != null) {
			this.dataRootList = mkb10s.stream()
				.filter(elem -> elem.getParentId()== null
				|| elem.getParentId().trim().isEmpty()
				|| elem.getParentId().trim().isBlank()
				|| elem.getParentId().trim().equalsIgnoreCase("NULL"))
				.collect(Collectors.toList());
			this.dataChildList = mkb10s.stream()
				.filter(elem -> !(elem.getParentId() == null
				|| elem.getParentId().trim().isEmpty()
				|| elem.getParentId().trim().isBlank()
				|| elem.getParentId().trim().equalsIgnoreCase("NULL")))
				.collect(Collectors.groupingBy(Mkb10::getParentId));
		} else {
			this.dataRootList = new ArrayList<>();
			this.dataChildList = new HashMap<>();
		}
	}

	public List<Mkb10> getRootTreeData() {
		return this.dataRootList;
	}

	public List<Mkb10> getChildTreeData(Mkb10 parent) {
		return this.dataChildList
			.getOrDefault(parent.getId(), new ArrayList<>());
	}

}
