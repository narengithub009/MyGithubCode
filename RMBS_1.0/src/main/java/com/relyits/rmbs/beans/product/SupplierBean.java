package com.relyits.rmbs.beans.product;

import java.util.List;

import com.relyits.rmbs.beans.resources.AddressBean;

public class SupplierBean {

	private Integer id;
	private String supplierName;
	private List<ProductInventoryBean> productInventoryBeans;
	private AddressBean addressBean;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public List<ProductInventoryBean> getProductInventoryBeans() {
		return productInventoryBeans;
	}
	public void setProductInventoryBeans(
			List<ProductInventoryBean> productInventoryBeans) {
		this.productInventoryBeans = productInventoryBeans;
	}
	public AddressBean getAddressBean() {
		return addressBean;
	}
	public void setAddressBean(AddressBean addressBean) {
		this.addressBean = addressBean;
	}
	
	
}
