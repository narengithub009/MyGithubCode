package com.relyits.rmbs.beans.product;

import java.sql.Date;

import com.relyits.rmbs.beans.registration.AgencyBean;
import com.relyits.rmbs.beans.resources.CategoryBean;
import com.relyits.rmbs.beans.resources.RoleBean;
import com.relyits.rmbs.beans.resources.SubCategoryBean;



public class ProductBean {
	
	private Integer id;
	private String name;
	private AgencyBean agencyBean;
	private String code;
	private String mFCompanay;
	private String schDrug;
	private CategoryBean categoryBean;
	private SubCategoryBean subCategoryBean;
	private Integer createdBy;
	private RoleBean creatorRoleBean;
	private Date createdDate;
	
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getmFCompanay() {
		return mFCompanay;
	}
	public void setmFCompanay(String mFCompanay) {
		this.mFCompanay = mFCompanay;
	}
	public String getSchDrug() {
		return schDrug;
	}
	public void setSchDrug(String schDrug) {
		this.schDrug = schDrug;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public AgencyBean getAgencyBean() {
		return agencyBean;
	}
	public void setAgencyBean(AgencyBean agencyBean) {
		this.agencyBean = agencyBean;
	}
	public CategoryBean getCategoryBean() {
		return categoryBean;
	}
	public void setCategoryBean(CategoryBean categoryBean) {
		this.categoryBean = categoryBean;
	}
	public SubCategoryBean getSubCategoryBean() {
		return subCategoryBean;
	}
	public void setSubCategoryBean(SubCategoryBean subCategoryBean) {
		this.subCategoryBean = subCategoryBean;
	}
	public RoleBean getCreatorRoleBean() {
		return creatorRoleBean;
	}
	public void setCreatorRoleBean(RoleBean creatorRoleBean) {
		this.creatorRoleBean = creatorRoleBean;
	}

	

}
