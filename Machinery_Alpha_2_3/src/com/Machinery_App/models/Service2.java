package com.Machinery_App.models;

public class Service2 {
	private long 	id; 			//Required
	private String 	name; 			//Required
	private long	machine;		//Required
	private long 	date; 			//Required
	private String  note;
	
	
	public Service2(long id, String name, long machine, long date, String note) {
		this.id = id;
		this.name = name;
		this.machine = machine;
		this.date = date;
		this.note = note;
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

	public long getMachine() {
		return machine;
	}

	public void setMachine(long machine) {
		this.machine = machine;
	}
	
	public long getDate(){
		return date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public String getNote(){
		return note;
	}
	
	public void setNote(String note){
		this.note = note;
	}
}
