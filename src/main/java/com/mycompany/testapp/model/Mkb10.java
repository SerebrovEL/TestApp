package com.mycompany.testapp.model;

import java.util.Objects;

public class Mkb10 {

	private String id;
	private String recCode;
	private String code;
	private String name;
	private String codeParent;
	private String actusl;

	public Mkb10() {
	}

	public Mkb10(String id, String recCode, String code, String name, String codeParent, String actusl) {
		this.id = id;
		this.recCode = recCode;
		this.code = code;
		this.name = name;
		this.codeParent = codeParent;
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

	public String getCodeParent() {
		return codeParent;
	}

	public void setCodeParent(String codeParent) {
		this.codeParent = codeParent;
	}

	public String getActusl() {
		return actusl.equalsIgnoreCase("1") ? "Актуальна" : "Не актуальна";
	}

	public void setActusl(String actusl) {
		this.actusl = actusl;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 79 * hash + Objects.hashCode(this.id);
		hash = 79 * hash + Objects.hashCode(this.recCode);
		hash = 79 * hash + Objects.hashCode(this.code);
		hash = 79 * hash + Objects.hashCode(this.name);
		hash = 79 * hash + Objects.hashCode(this.codeParent);
		hash = 79 * hash + Objects.hashCode(this.actusl);
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
		if (!Objects.equals(this.codeParent, other.codeParent)) {
			return false;
		}
		return Objects.equals(this.actusl, other.actusl);
	}

	@Override
	public String toString() {
		return "Mkb10{" + "id=" + id + ", recCode=" + recCode + ", code=" + code + ", name=" + name + ", codeParent=" + codeParent + ", actusl=" + actusl + '}';
	}

}
