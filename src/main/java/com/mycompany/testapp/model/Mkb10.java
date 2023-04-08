package com.mycompany.testapp.model;

import java.util.Objects;

public class Mkb10 {

	private Long id;
	private String recCode;
	private String code;
	private String name;
	private Long parentId;
	private Boolean actusl;

	public Mkb10() {
	}

	public Mkb10(Long id, String recCode, String code, String name, Long parentId, Boolean actusl) {
		this.id = id;
		this.recCode = recCode;
		this.code = code;
		this.name = name;
		this.parentId = parentId;
		this.actusl = actusl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean isActusl() {
		return actusl;
	}

	public void setActusl(Boolean actusl) {
		this.actusl = actusl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id,
			this.recCode,
			this.code,
			this.name,
			this.parentId,
			this.actusl);
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
		if (!Objects.equals(this.recCode, other.getRecCode())) {
			return false;
		}
		if (!Objects.equals(this.code, other.getCode())) {
			return false;
		}
		if (!Objects.equals(this.name, other.getName())) {
			return false;
		}
		if (!Objects.equals(this.id, other.getId())) {
			return false;
		}
		if (!Objects.equals(this.parentId, other.getParentId())) {
			return false;
		}
		return Objects.equals(this.actusl, other.isActusl());
	}

	@Override
	public String toString() {
		return "Mkb10{" + "id=" + id + ", recCode=" + recCode + ", code=" + code + ", name=" + name + ", parentId=" + parentId + ", actusl=" + actusl + '}';
	}

}
