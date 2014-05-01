package com.OpenATK.machineryapp.models;

import java.util.Date;

public class Machine {
	
	private Integer id = null;
	private String remote_id = null;

	private String name = "";
	private Date dateNameChanged = null;
	
	private Integer list = null;
	private Date dateListChanged = null;
	
	private String year = "";
	private Date dateYearChanged = null;

	private Integer order = null;
	private Date dateOrderChanged = null;
	
	private String greased = "";
	private Date dateGreasedChanged = null;
	
	private String maintenance = "";
	private Date dateMaintenanceChanged = null;
	private String maintenanceTableName = "";

	private Boolean deleted = false;
	private Date dateDeleted = null;

	public Machine(){

	}


	public Machine(Integer id, String remote_id, String name,
			Date dateNameChanged, Integer list, Date dateListChanged, 
			String year, Date dateYearChanged, Integer order, Date dateOrderChanged,
			String greased, Date dateGreasedChanged, String maintenance, Date dateMaintenanceChanged,
			String maintenanceTableName, Boolean deleted, Date dateDeleted) {
		super();
		this.id = id;
		this.remote_id = remote_id;
		this.name = name;
		this.dateNameChanged = dateNameChanged;
		this.list = list;
		this.dateListChanged = dateListChanged;
		this.year = year;
		this.dateYearChanged = dateYearChanged;
		this.order = order;
		this.dateOrderChanged = dateOrderChanged;
		this.setGreased(greased);
		this.setDateGreasedChanged(dateGreasedChanged);
		this.setMaintenance(maintenance);
		this.setDateMaintenanceChanged(dateMaintenanceChanged);
		this.setMaintenanceTableName(maintenanceTableName);
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

	
	public Integer getList() {
		return list;
	}


	public void setList(Integer list) {
		this.list = list;
	}
	
	
	public Date getDateListChanged() {
		return dateListChanged;
	}


	public void setDateListChanged(Date dateListChanged) {
		this.dateListChanged = dateListChanged;
	}
	
	
	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}
	
	public Date getDateYearChanged() {
		return dateYearChanged;
	}


	public void setDateYearChanged(Date dateYearChanged) {
		this.dateYearChanged = dateYearChanged;
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


	public String getGreased() {
		return greased;
	}


	public void setGreased(String greased) {
		this.greased = greased;
	}


	public Date getDateGreasedChanged() {
		return dateGreasedChanged;
	}


	public void setDateGreasedChanged(Date dateGreasedChanged) {
		this.dateGreasedChanged = dateGreasedChanged;
	}


	public String getMaintenance() {
		return maintenance;
	}


	public void setMaintenance(String maintenance) {
		this.maintenance = maintenance;
	}


	public Date getDateMaintenanceChanged() {
		return dateMaintenanceChanged;
	}


	public void setDateMaintenanceChanged(Date dateMaintenanceChanged) {
		this.dateMaintenanceChanged = dateMaintenanceChanged;
	}


	public String getMaintenanceTableName() {
		return maintenanceTableName;
	}


	public void setMaintenanceTableName(String maintenanceTableName) {
		this.maintenanceTableName = maintenanceTableName;
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
