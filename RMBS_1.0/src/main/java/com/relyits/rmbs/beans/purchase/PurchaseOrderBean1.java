package com.relyits.rmbs.beans.purchase;

import java.math.BigDecimal;
import java.sql.Date;

import com.relyits.rmbs.beans.product.ProductInventoryBean;
import com.relyits.rmbs.beans.registration.AgencyBean;
import com.relyits.rmbs.beans.registration.BranchBean;


public class PurchaseOrderBean1 {
	
	private Integer id;
	private BigDecimal amount;
	private AgencyBean agencyBean;
	private Date billingDateAndTime;
	private Date deliveryDate;
	private BranchBean branchBean;
	private ProductInventoryBean productInventoryBean;
	private String orderIdByDate;
//	private StatusBean statusBean;
	private BigDecimal overallDiscount;
	private BigDecimal discountPrice;
//	private OutletBean outletBean;
	private BigDecimal totalVAT;
//	private DoctorBean doctorBean;
//	private PatientBean patientBean;
	private String invoiceNo;
	private BigDecimal payAmount;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public AgencyBean getAgencyBean() {
		return agencyBean;
	}
	public void setAgencyBean(AgencyBean agencyBean) {
		this.agencyBean = agencyBean;
	}
	public Date getBillingDateAndTime() {
		return billingDateAndTime;
	}
	public void setBillingDateAndTime(Date billingDateAndTime) {
		this.billingDateAndTime = billingDateAndTime;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public BranchBean getBranchBean() {
		return branchBean;
	}
	public void setBranchBean(BranchBean branchBean) {
		this.branchBean = branchBean;
	}
	public ProductInventoryBean getProductInventoryBean() {
		return productInventoryBean;
	}
	public void setProductInventoryBean(ProductInventoryBean productInventoryBean) {
		this.productInventoryBean = productInventoryBean;
	}
	public String getOrderIdByDate() {
		return orderIdByDate;
	}
	public void setOrderIdByDate(String orderIdByDate) {
		this.orderIdByDate = orderIdByDate;
	}
	public BigDecimal getOverallDiscount() {
		return overallDiscount;
	}
	public void setOverallDiscount(BigDecimal overallDiscount) {
		this.overallDiscount = overallDiscount;
	}
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	public BigDecimal getTotalVAT() {
		return totalVAT;
	}
	public void setTotalVAT(BigDecimal totalVAT) {
		this.totalVAT = totalVAT;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	
	
	

}
