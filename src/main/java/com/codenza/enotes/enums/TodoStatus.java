package com.codenza.enotes.enums;

public enum TodoStatus {

	NOT_STARTED(1,"Not Started"), IN_PROGRESS(2,"In progress"), COMPLETED(3,"Completed");

	Integer id;
	String name;
	
	TodoStatus(Integer id, String name) {
		this.id=id;
		this.name=name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
