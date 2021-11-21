package com.relyits.rmbs.beans.purchase;

import java.math.BigDecimal;
import java.sql.Date;

import com.relyits.rmbs.beans.product.ProductInventoryBean;



public class PurchaseLineItemsBean1 {

	private Integer id;
	private Integer quantity;
	private Integer freeQantity;
	private BigDecimal amount;
	private BigDecimal unitPrice;
	private BigDecimal discount;
	private BigDecimal netPrice;
//	private OutletBean outletBean;
	private BigDecimal vat;
//	private Integer delivaeredQuantity;
//	private StatusBean statusBean;
	private Integer deliverableQuantity;
	private BigDecimal preparationCharges;
	private Date expiryDate;
	private ProductInventoryBean productInventoryBean;
	private PurchaseOrderBean1 purchaseOrderBean1;
	private BigDecimal payAmount;
	
	
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
	public Integer getFreeQantity() {
		return freeQantity;
	}
	public void setFreeQantity(Integer freeQantity) {
		this.freeQantity = freeQantity;
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
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
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
	
	public PurchaseOrderBean1 getPurchaseOrderBean1() {
		return purchaseOrderBean1;
	}
	public void setPurchaseOrderBean1(PurchaseOrderBean1 purchaseOrderBean1) {
		this.purchaseOrderBean1 = purchaseOrderBean1;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public Integer getDeliverableQuantity() {
		return deliverableQuantity;
	}
	public void setDeliverableQuantity(Integer deliverableQuantity) {
		this.deliverableQuantity = deliverableQuantity;
	}
	
	
	
	
}
