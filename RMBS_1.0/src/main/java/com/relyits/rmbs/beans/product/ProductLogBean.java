package com.relyits.rmbs.beans.product;

import java.sql.Date;

import com.relyits.rmbs.beans.registration.BranchBean;
import com.relyits.rmbs.beans.resources.RoleBean;


public class ProductLogBean {
	
	private Integer id;
	private ProductInventoryBean productInventoryBean;
	private Date date;
	private BranchBean branchBean;
	private Integer updater;
	private RoleBean creatorRoleBean;
	private String feildName;
	private Integer oldValue;
	private Integer newValue;
	private String updaterName;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getUpdater() {
		return updater;
	}
	public void setUpdater(Integer updater) {
		this.updater = updater;
	}
	public String getFeildName() {
		return feildName;
	}
	public void setFeildName(String feildName) {
		this.feildName = feildName;
	}
	public Integer getOldValue() {
		return oldValue;
	}
	public void setOldValue(Integer oldValue) {
		this.oldValue = oldValue;
	}
	public Integer getNewValue() {
		return newValue;
	}
	public void setNewValue(Integer newValue) {
		this.newValue = newValue;
	}
	
	public ProductInventoryBean getProductInventoryBean() {
		return productInventoryBean;
	}
	public void setProductInventoryBean(ProductInventoryBean productInventoryBean) {
		this.productInventoryBean = productInventoryBean;
	}
	public BranchBean getBranchBean() {
		return branchBean;
	}
	public void setBranchBean(BranchBean branchBean) {
		this.branchBean = branchBean;
	}
	public RoleBean getCreatorRoleBean() {
		return creatorRoleBean;
	}
	public void setCreatorRoleBean(RoleBean creatorRoleBean) {
		this.creatorRoleBean = creatorRoleBean;
	}
	public String getUpdaterName() {
		return updaterName;
	}
	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}
	
	
	

}
