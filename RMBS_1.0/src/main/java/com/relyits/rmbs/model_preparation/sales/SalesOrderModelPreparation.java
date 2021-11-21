package com.relyits.rmbs.model_preparation.sales;

import java.text.ParseException;

import com.relyits.rmbs.beans.sales.SalesOrderBean;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model_preparation.consumer.CustomerModelPreparation;
import com.relyits.rmbs.model_preparation.registration.DoctorModelPreparation;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;


public class SalesOrderModelPreparation {
	
	   private static SalesOrderModel salesOrderModel=null;
	    
		public static SalesOrderModel prepareSalesOrderBean(SalesOrderBean salesOrderBean){
			salesOrderModel=new SalesOrderModel();
			salesOrderModel.setAmount(salesOrderBean.getAmount());
			try {
				salesOrderModel.setBillingDateAndTime(DateAndTimeUtilities.getCurrentDateTimeInSqlFormat());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}			
			if(salesOrderBean.getCustomerBean().getName()!=""){
			salesOrderModel.setCustomerModel(CustomerModelPreparation.prepareCustomerModel(salesOrderBean.getCustomerBean()));
			}
			salesOrderModel.setDiscountPrice(salesOrderBean.getDiscountPrice());
			try {
				if(salesOrderBean.getDoctorBean()!=null){
				salesOrderModel.setDoctorModel(DoctorModelPreparation.prepareDoctorModel(salesOrderBean.getDoctorBean()));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			salesOrderModel.setId(salesOrderBean.getId());
			salesOrderModel.setMargin(salesOrderBean.getMargin());
			salesOrderModel.setOrderIdByDate(salesOrderModel.getBillingDateAndTime()+"");
			salesOrderModel.setPayAmount(salesOrderBean.getPayAmount());
			salesOrderModel.setTotalVAT(salesOrderBean.getTotalVAT());
			
			return salesOrderModel;
			
		}

}
