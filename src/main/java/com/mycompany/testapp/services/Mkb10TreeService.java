package com.mycompany.testapp.services;

import com.mycompany.testapp.model.Mkb10;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Mkb10TreeService {

	private List<Mkb10> dataRootList = null;
	private Map<Long, List<Mkb10>> dataChildList = null;

	private final Function<Mkb10, Boolean> rootFilter = elem -> elem.getParentId() == null;
	private final Function<Mkb10, Boolean> childFilter = elem -> !rootFilter.apply(elem);

	public Mkb10TreeService(List<Mkb10> mkb10s) {
		if (mkb10s != null) {
			this.dataRootList = mkb10s.stream()
				.filter(elem -> rootFilter.apply(elem))
				.collect(Collectors.toList());
			this.dataChildList = mkb10s.stream()
				.filter(elem -> childFilter.apply(elem))
				.collect(Collectors.groupingByConcurrent(Mkb10::getParentId));
		} else {
			this.dataRootList = new LinkedList<>();
			this.dataChildList = new HashMap<>();
		}
	}

	public List<Mkb10> getRootTreeData() {
		return this.dataRootList;
	}

	public List<Mkb10> getChildTreeData(Mkb10 parent) {
		return this.dataChildList
			.getOrDefault(parent.getId(), new LinkedList<>());
	}

}
