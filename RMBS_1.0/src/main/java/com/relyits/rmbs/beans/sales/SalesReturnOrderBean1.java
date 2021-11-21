package com.relyits.rmbs.beans.sales;

import java.math.BigDecimal;
import java.sql.Date;

import com.relyits.rmbs.beans.consumer.CustomerBean;
import com.relyits.rmbs.beans.consumer.PatientBean;
import com.relyits.rmbs.beans.registration.BranchBean;
import com.relyits.rmbs.beans.registration.DoctorBean;
import com.relyits.rmbs.beans.registration.OutletBean;
import com.relyits.rmbs.beans.resources.ReasonBean;
import com.relyits.rmbs.beans.resources.StatusBean;


public class SalesReturnOrderBean1 {
	
	private Integer id;
	private BigDecimal amount;
	private CustomerBean customerBean;
	private Date billingDateAndTime;
	private BranchBean branchBean;
	private String orderIdByDate;
	private StatusBean statusBean;
	private BigDecimal discountPrice;
	private OutletBean outletBean;
	private BigDecimal totalVAT;
	private BigDecimal payAmount;
	private DoctorBean doctorBean;
	private PatientBean patientBean;
	private ReasonBean reasonBean;
	private BigDecimal deductionOnMargin;
	private SalesOrderBean1 salesOrderBean1;
	
	
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
	public CustomerBean getCustomerBean() {
		return customerBean;
	}
	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}
	public Date getBillingDateAndTime() {
		return billingDateAndTime;
	}
	public void setBillingDateAndTime(Date billingDateAndTime) {
		this.billingDateAndTime = billingDateAndTime;
	}
	public BranchBean getBranchBean() {
		return branchBean;
	}
	public void setBranchBean(BranchBean branchBean) {
		this.branchBean = branchBean;
	}
	public String getOrderIdByDate() {
		return orderIdByDate;
	}
	public void setOrderIdByDate(String orderIdByDate) {
		this.orderIdByDate = orderIdByDate;
	}
	public StatusBean getStatusBean() {
		return statusBean;
	}
	public void setStatusBean(StatusBean statusBean) {
		this.statusBean = statusBean;
	}
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	public OutletBean getOutletBean() {
		return outletBean;
	}
	public void setOutletBean(OutletBean outletBean) {
		this.outletBean = outletBean;
	}
	public BigDecimal getTotalVAT() {
		return totalVAT;
	}
	public void setTotalVAT(BigDecimal totalVAT) {
		this.totalVAT = totalVAT;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public DoctorBean getDoctorBean() {
		return doctorBean;
	}
	public void setDoctorBean(DoctorBean doctorBean) {
		this.doctorBean = doctorBean;
	}
	public PatientBean getPatientBean() {
		return patientBean;
	}
	public void setPatientBean(PatientBean patientBean) {
		this.patientBean = patientBean;
	}
	public ReasonBean getReasonBean() {
		return reasonBean;
	}
	public void setReasonBean(ReasonBean reasonBean) {
		this.reasonBean = reasonBean;
	}
	public BigDecimal getDeductionOnMargin() {
		return deductionOnMargin;
	}
	public void setDeductionOnMargin(BigDecimal deductionOnMargin) {
		this.deductionOnMargin = deductionOnMargin;
	}
	public SalesOrderBean1 getSalesOrderBean1() {
		return salesOrderBean1;
	}
	public void setSalesOrderBean1(SalesOrderBean1 salesOrderBean1) {
		this.salesOrderBean1 = salesOrderBean1;
	}
	
	
	

}
