package com.mycompany.testapp.model;

import java.util.Objects;

public class Mkb10 {

	private String id;
	private String recCode;
	private String code;
	private String name;
	private String parentId;
	private String actusl;

	public Mkb10() {
	}

	public Mkb10(String id, String recCode, String code, String name, String parentId, String actusl) {
		this.id = id;
		this.recCode = recCode;
		this.code = code;
		this.name = name;
		this.parentId = parentId;
		this.actusl = actusl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecCode() {
		return recCode;
	}

	public void setRecCode(String recCode) {
		this.recCode = recCode;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getActusl() {
		return actusl;
	}

	public void setActusl(String actusl) {
		this.actusl = actusl;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + Objects.hashCode(this.id);
		hash = 29 * hash + Objects.hashCode(this.recCode);
		hash = 29 * hash + Objects.hashCode(this.code);
		hash = 29 * hash + Objects.hashCode(this.name);
		hash = 29 * hash + Objects.hashCode(this.parentId);
		hash = 29 * hash + Objects.hashCode(this.actusl);
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
		final Mkb10 other = (Mkb10) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		if (!Objects.equals(this.recCode, other.recCode)) {
			return false;
		}
		if (!Objects.equals(this.code, other.code)) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.parentId, other.parentId)) {
			return false;
		}
		return Objects.equals(this.actusl, other.actusl);
	}

	@Override
	public String toString() {
		return "Mkb10{" + "id=" + id + ", recCode=" + recCode + ", code=" + code + ", name=" + name + ", parentId=" + parentId + ", actusl=" + actusl + '}';
	}

}
