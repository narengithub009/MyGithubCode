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

import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.registration.BranchModel;

@SuppressWarnings("serial")
@Entity
@Table(name="RMBS203", schema="RMBS",
            uniqueConstraints = {
		           @UniqueConstraint(columnNames = "RMBS20301")})   //id
		           
public class ProductLogModel implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RMBS20301", unique = true, nullable = false)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "PI_RMBS20201", nullable = false)
	private ProductInventoryModel productInventoryModel;
	
	@Column(name = "RMBS20302", nullable = false, length = 25)
	private Date date;
	
	@OneToOne
	@JoinColumn(name = "BR_RMBS10201", nullable = false)
	private BranchModel branchModel;
		
	@Column(name = "RMBS20303", nullable = false, length = 25)
	private Integer updater;
	
	@OneToOne
	@JoinColumn(name = "CRL_RMBS80201", nullable = false)
	private RoleModel creatorRoleModel;

	@Column(name = "RMBS20304", nullable = false, length = 100)
	private String feildName;
	
	@Column(name = "RMBS20305", nullable = false, length = 50)
	private Integer oldValue;
	
	@Column(name = "RMBS20306", nullable = false, length = 50)
	private Integer newValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public ProductInventoryModel getProductInventoryModel() {
		return productInventoryModel;
	}

	public void setProductInventoryModel(ProductInventoryModel productInventoryModel) {
		this.productInventoryModel = productInventoryModel;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BranchModel getBranchModel() {
		return branchModel;
	}

	public void setBranchModel(BranchModel branchModel) {
		this.branchModel = branchModel;
	}

	public Integer getUpdater() {
		return updater;
	}

	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	

	public RoleModel getCreatorRoleModel() {
		return creatorRoleModel;
	}

	public void setCreatorRoleModel(RoleModel creatorRoleModel) {
		this.creatorRoleModel = creatorRoleModel;
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
	
	
	
	
}
