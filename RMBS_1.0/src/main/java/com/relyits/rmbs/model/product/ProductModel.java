package com.relyits.rmbs.model.product;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.SubCategoryModel;
import com.relyits.rmbs.model.registration.AgencyModel;

@SuppressWarnings("serial")
@Entity
@Table(name="RMBS201", schema="RMBS",
            uniqueConstraints = {
		           @UniqueConstraint(columnNames = "RMBS20101"),   //id
		           @UniqueConstraint(columnNames = "RMBS20103")})  // code
public class ProductModel implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RMBS20101", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "RMBS20102", nullable = false, length = 100)
	private String name;
	
	@OneToOne
	@JoinColumn(name = "AG_RMBS10401", nullable = false)
	private AgencyModel agencyModel;
	
	@Column(name = "RMBS20103", unique = true, nullable = false, length = 100)
	private String code;
	
	@Column(name = "RMBS20104", nullable = false, length = 100)
	private String mFCompanay;
	
	@Column(name = "RMBS20105", nullable = false, length = 100)
	private String schDrug;
	
	@OneToOne
	@JoinColumn(name = "CT_RMBS80401", nullable = false)
	private CategoryModel categoryModel;
	
	@OneToOne
	@JoinColumn(name = "SCT_RMBS80501", nullable = false)
	private SubCategoryModel subCategoryModel;
	
	@Column(name = "RMBS20106", nullable = false, length = 25)
	private Integer createdBy;
	
	@OneToOne
	@JoinColumn(name = "CR_RMBS80201", nullable = false)
	private RoleModel creatorRoleModel;

	@Column(name = "RMBS20107", nullable = false, length = 25)
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

	public AgencyModel getAgencyModel() {
		return agencyModel;
	}

	public void setAgencyModel(AgencyModel agencyModel) {
		this.agencyModel = agencyModel;
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

	public CategoryModel getCategoryModel() {
		return categoryModel;
	}

	public void setCategoryModel(CategoryModel categoryModel) {
		this.categoryModel = categoryModel;
	}

	public SubCategoryModel getSubCategoryModel() {
		return subCategoryModel;
	}

	public void setSubCategoryModel(SubCategoryModel subCategoryModel) {
		this.subCategoryModel = subCategoryModel;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public RoleModel getCreatorRoleModel() {
		return creatorRoleModel;
	}

	public void setCreatorRoleModel(RoleModel creatorRoleModel) {
		this.creatorRoleModel = creatorRoleModel;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
}
