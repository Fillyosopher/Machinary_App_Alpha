package com.OpenATK.machineryapp.models;

import java.util.Date;

public class Maintenance {
		
	private Integer id = null;
	private String remote_id = null;

	private String name = "";
	private Date dateNameChanged = null;

	private String note = "";
	private Date dateNoteChanged = null;

	private Boolean deleted = false;
	private Date dateDeleted = null;

	public Maintenance(){

	}

	public Maintenance(Integer id, String remote_id, String name,
			Date dateNameChanged, String note, Date dateNoteChanged,
			Boolean deleted, Date dateDeleted) {
		super();
		this.id = id;
		this.remote_id = remote_id;
		this.name = name;
		this.dateNameChanged = dateNameChanged;
		this.setNote(note);
		this.setDateNoteChanged(dateNoteChanged);
		this.deleted = deleted;
		this.dateDeleted = dateDeleted;
	}

	//---------- Auto generated Getters and setters -------------
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getRemote_id() {
		return remote_id;
	}


	public void setRemote_id(String remote_id) {
		this.remote_id = remote_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getDateNameChanged() {
		return dateNameChanged;
	}


	public void setDateNameChanged(Date dateNameChanged) {
		this.dateNameChanged = dateNameChanged;
	}

	
	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public Date getDateNoteChanged() {
		return dateNoteChanged;
	}


	public void setDateNoteChanged(Date dateNoteChanged) {
		this.dateNoteChanged = dateNoteChanged;
	}
	
	
	public Boolean getDeleted() {
		return deleted;
	}


	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}


	public Date getDateDeleted() {
		return dateDeleted;
	}


	public void setDateDeleted(Date dateDeleted) {
		this.dateDeleted = dateDeleted;
	}

}
