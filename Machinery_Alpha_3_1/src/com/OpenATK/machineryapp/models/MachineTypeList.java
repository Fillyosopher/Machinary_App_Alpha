package com.OpenATK.machineryapp.models;

import java.util.Date;

public class MachineTypeList {
		
	private Integer id = null;
	private String remote_id = null;

	private String name = "";
	private Date dateNameChanged = null;

	private Integer order = null;
	private Date dateOrderChanged = null;

	private Boolean deleted = false;
	private Date dateDeleted = null;

	public MachineTypeList(){

	}


	public MachineTypeList(Integer id, String remote_id, String name,
			Date dateNameChanged, Integer order, Date dateOrderChanged,
			Boolean deleted, Date dateDeleted) {
		super();
		this.id = id;
		this.remote_id = remote_id;
		this.name = name;
		this.dateNameChanged = dateNameChanged;
		this.order = order;
		this.dateOrderChanged = dateOrderChanged;
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


	public Integer getOrder() {
		return order;
	}


	public void setOrder(Integer order) {
		this.order = order;
	}


	public Date getDateOrderChanged() {
		return dateOrderChanged;
	}


	public void setDateOrderChanged(Date dateOrderChanged) {
		this.dateOrderChanged = dateOrderChanged;
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
