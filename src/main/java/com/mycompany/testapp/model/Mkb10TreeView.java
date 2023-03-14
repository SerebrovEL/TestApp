package com.mycompany.testapp.model;

import java.util.Objects;

public class Mkb10TreeView {

	private String code;
	private String name;
	private Mkb10TreeView parent;

	public Mkb10TreeView() {
	}

	public Mkb10TreeView(String code, String name, Mkb10TreeView parent) {
		this.code = code;
		this.name = name;
		this.parent = parent;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Mkb10TreeView getParent() {
		return parent;
	}

	public void setParent(Mkb10TreeView parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 83 * hash + Objects.hashCode(this.code);
		hash = 83 * hash + Objects.hashCode(this.name);
		hash = 83 * hash + Objects.hashCode(this.parent);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Mkb10TreeView other = (Mkb10TreeView) obj;
		if (!Objects.equals(this.code, other.code)) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return Objects.equals(this.parent, other.parent);
	}

	@Override
	public String toString() {
		return "Mkb10TreeView{" + "code=" + code + ", name=" + name + ", parent=" + parent + '}';
	}

}
