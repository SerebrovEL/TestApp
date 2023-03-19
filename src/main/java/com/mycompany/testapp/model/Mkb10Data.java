package com.mycompany.testapp.model;

import java.util.Objects;

public class Mkb10Data {

	private String code;
	private String icon;
	private Mkb10Data parent;
	private int level;

	public Mkb10Data() {
	}

	public Mkb10Data(String code, String icon, Mkb10Data parent) {
		this.code = code;
		this.icon = icon;
		this.parent = parent;
		if (parent == null) {
			this.level = 0;
		} else {
			this.level = parent.getLevel() + 1;
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Mkb10Data getParent() {
		return parent;
	}

	public void setParent(Mkb10Data parent) {
		this.parent = parent;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 89 * hash + Objects.hashCode(this.code);
		hash = 89 * hash + Objects.hashCode(this.icon);
		hash = 89 * hash + Objects.hashCode(this.parent);
		hash = 89 * hash + this.level;
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
		final Mkb10Data other = (Mkb10Data) obj;
		if (this.level != other.level) {
			return false;
		}
		if (!Objects.equals(this.code, other.code)) {
			return false;
		}
		if (!Objects.equals(this.icon, other.icon)) {
			return false;
		}
		return Objects.equals(this.parent, other.parent);
	}

	@Override
	public String toString() {
		return "Mkb10Data{" + "code=" + code + ", icon=" + icon + ", parent=" + parent + ", level=" + level + '}';
	}

}
