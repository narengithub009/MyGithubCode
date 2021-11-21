package com.relyits.rmbs.beans.sales;

import java.math.BigDecimal;
import java.sql.Date;


import com.relyits.rmbs.beans.product.ProductInventoryBean;
import com.relyits.rmbs.beans.registration.OutletBean;
import com.relyits.rmbs.beans.resources.StatusBean;


public class SalesLineItemsBean1 {
	
	private Integer id;
	private Integer quantity;
	private BigDecimal amount;
	private BigDecimal unitPrice;
	private BigDecimal discount;
	private BigDecimal netPrice;
	private OutletBean outletBean;
	private BigDecimal vat;
	private Integer delivaeredQuantity;
	private StatusBean statusBean;
	private Integer deliverableQuantity;
	private BigDecimal preparationCharges;
	private Date expiryDate;
	private ProductInventoryBean productInventoryBean;
	private SalesOrderBean1 salesOrderBean1;
	private BigDecimal margin;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public BigDecimal getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(BigDecimal netPrice) {
		this.netPrice = netPrice;
	}
	public OutletBean getOutletBean() {
		return outletBean;
	}
	public void setOutletBean(OutletBean outletBean) {
		this.outletBean = outletBean;
	}
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	public Integer getDelivaeredQuantity() {
		return delivaeredQuantity;
	}
	public void setDelivaeredQuantity(Integer delivaeredQuantity) {
		this.delivaeredQuantity = delivaeredQuantity;
	}
	public StatusBean getStatusBean() {
		return statusBean;
	}
	public void setStatusBean(StatusBean statusBean) {
		this.statusBean = statusBean;
	}
	public Integer getDeliverableQuantity() {
		return deliverableQuantity;
	}
	public void setDeliverableQuantity(Integer deliverableQuantity) {
		this.deliverableQuantity = deliverableQuantity;
	}
	public BigDecimal getPreparationCharges() {
		return preparationCharges;
	}
	public void setPreparationCharges(BigDecimal preparationCharges) {
		this.preparationCharges = preparationCharges;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public ProductInventoryBean getProductInventoryBean() {
		return productInventoryBean;
	}
	public void setProductInventoryBean(ProductInventoryBean productInventoryBean) {
		this.productInventoryBean = productInventoryBean;
	}
	public SalesOrderBean1 getSalesOrderBean1() {
		return salesOrderBean1;
	}
	public void setSalesOrderBean1(SalesOrderBean1 salesOrderBean1) {
		this.salesOrderBean1 = salesOrderBean1;
	}
	public BigDecimal getMargin() {
		return margin;
	}
	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}
	
	

}
