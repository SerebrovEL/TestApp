package com.mycompany.testapp.services;

import com.mycompany.testapp.model.Mkb10;
import java.util.Collection;
import java.util.stream.Collectors;

public class Mkb10TreeService {

	private Collection<Mkb10> dataList = null;

	public Mkb10TreeService(Collection<Mkb10> mkb10s) {
		this.dataList = mkb10s;
	}

	public Collection<Mkb10> getRootTreeData() {
		return this.dataList.stream()
			.filter(elem -> elem.getCodeParent() == null
			|| elem.getCodeParent().trim().isEmpty()
			|| elem.getCodeParent().trim().isBlank()
			|| elem.getCodeParent().trim().equalsIgnoreCase("NULL"))
			.collect(Collectors.toList());
	}

	public Collection<Mkb10> getChildTreeData(Mkb10 parent) {
		return this.dataList.parallelStream().filter(elem
			-> elem.getCodeParent() != null
			&& elem.getCodeParent().trim().equalsIgnoreCase(parent.getId().trim())
		).collect(Collectors.toList());
	}

}
