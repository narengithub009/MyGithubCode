package com.relyits.rmbs.beans_preparation.sales;

import java.util.ArrayList;
import java.util.List;

import com.relyits.rmbs.beans.sales.SalesOrderBean1;
import com.relyits.rmbs.beans_preparation.consumer.CustomerBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.DoctorBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.OutletBeanPreparation;
import com.relyits.rmbs.beans_preparation.resources.AddressBeanPreparation;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.utilities.ConvertDoubleToBigDecimal;


public class SalesOrderBeanPreparation1 {
	
    private static SalesOrderBean1 salesOrderBean1=null;
    
	public static SalesOrderBean1 prepareSalesOrderBean1(SalesOrderModel salesOrderModel){
		
		salesOrderBean1 = new SalesOrderBean1();
		salesOrderBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesOrderModel.getAmount()));
		salesOrderBean1.setBillingDateAndTime(salesOrderModel.getBillingDateAndTime());
		salesOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(salesOrderModel.getBranchModel()));
		salesOrderBean1.setCategoryBean(AddressBeanPreparation.prepareCategoryBean(salesOrderModel.getCategoryModel()));
		salesOrderBean1.setCustomerBean(CustomerBeanPreparation.prepareCustomerBean(salesOrderModel.getCustomerModel()));
		salesOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesOrderModel.getDiscountPrice()));
		if(salesOrderModel.getDoctorModel()!=null){
			salesOrderBean1.setDoctorBean(DoctorBeanPreparation.prepareDoctorBean(salesOrderModel.getDoctorModel()));
		}		
		salesOrderBean1.setId(salesOrderModel.getId());
		salesOrderBean1.setMargin(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesOrderModel.getMargin()));
		salesOrderBean1.setOrderIdByDate(salesOrderModel.getOrderIdByDate());
		salesOrderBean1.setOutletBean(OutletBeanPreparation.prepareOutLetBean(salesOrderModel.getOutletModel()));
	    salesOrderBean1.setStatusBean(AddressBeanPreparation.prepareStatusBean(salesOrderModel.getStatusModel()));
		salesOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesOrderModel.getTotalVAT()));
		salesOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesOrderModel.getPayAmount()));
		
		return salesOrderBean1;
		
	}
	
	public static List<SalesOrderBean1> prepareListOfSalesOrderBean1(List<SalesOrderModel> sales){
		List<SalesOrderBean1> salesOrderBeans1 = null;
		if(sales != null && !sales.isEmpty()){
			salesOrderBeans1 = new ArrayList<SalesOrderBean1>();				
			for(SalesOrderModel salesOrderModel : sales){
				salesOrderBeans1.add(prepareSalesOrderBean1(salesOrderModel));
				}
		}


return salesOrderBeans1;


}
	
	public static SalesOrderBean1 prepareSalesOrderBeanForRevenueAccounts1(SalesOrderModel salesOrderModel){
		
		salesOrderBean1 = new SalesOrderBean1();			
		
		salesOrderBean1.setId(salesOrderModel.getId());
		salesOrderBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesOrderModel.getAmount()));
		salesOrderBean1.setBillingDateAndTime(salesOrderModel.getBillingDateAndTime());
		salesOrderBean1.setOrderIdByDate(salesOrderModel.getOrderIdByDate());
		salesOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesOrderModel.getDiscountPrice()));
		salesOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesOrderModel.getPayAmount()));
		salesOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesOrderModel.getTotalVAT()));
		salesOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(salesOrderModel.getBranchModel()));
						
	return salesOrderBean1;


	}

	public static List<SalesOrderBean1> prepareListOfSalesOrderBeanForRevenueAccounts1(List<SalesOrderModel> salesOrderModels){
		List<SalesOrderBean1> salesOrderBeans1 = null;
		if(salesOrderModels != null && !salesOrderModels.isEmpty()){
			salesOrderBeans1 = new ArrayList<SalesOrderBean1>();				
			for(SalesOrderModel salesOrderModel : salesOrderModels){
				salesOrderBeans1.add(prepareSalesOrderBeanForRevenueAccounts1(salesOrderModel));
				}
		}


	return salesOrderBeans1;


	}
	
}
