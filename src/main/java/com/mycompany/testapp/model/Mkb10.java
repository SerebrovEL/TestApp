package com.mycompany.testapp.model;

import java.util.Objects;

public class Mkb10 {

	private String code;
	private String name;
	private String codeParent;

	public Mkb10() {
	}

	public Mkb10(String code, String name, String codeParent) {
		this.code = code;
		this.name = name;
		this.codeParent = codeParent;
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

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 53 * hash + Objects.hashCode(this.code);
		hash = 53 * hash + Objects.hashCode(this.name);
		hash = 53 * hash + Objects.hashCode(this.codeParent);
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
		if (!Objects.equals(this.code, other.code)) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return Objects.equals(this.codeParent, other.codeParent);
	}

	@Override
	public String toString() {
		return "Mkb10{" + "code=" + code + ", name=" + name + ", codeParent=" + codeParent + '}';
	}

}
