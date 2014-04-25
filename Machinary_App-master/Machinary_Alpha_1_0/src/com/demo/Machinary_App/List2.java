package com.demo.Machinary_App;

public class List2 {
	// DATA STORED IN SQLite
	// if you edit this, you should edit the helper	
	private long id;
	private String name;
	private String sortBy;

	public List2(long id, String name, String sortBy) {
		this.id = id;
		this.name = name;
		this.sortBy = sortBy;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

}