package com.relyits.rmbs.beans_preparation.sales;

import java.util.ArrayList;
import java.util.List;

import com.relyits.rmbs.beans.sales.SalesReturnOrderBean1;
import com.relyits.rmbs.beans_preparation.consumer.CustomerBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.DoctorBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.OutletBeanPreparation;
import com.relyits.rmbs.beans_preparation.resources.AddressBeanPreparation;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;
import com.relyits.rmbs.utilities.ConvertDoubleToBigDecimal;



public class SalesReturnOrderBeanPreparation1 {
	
	
	private static SalesReturnOrderBean1 salesReturnOrderBean1=null;
	
  public static SalesReturnOrderBean1 prepareSalesReturnOrderBean1(SalesReturnOrderModel salesReturnOrderModel){
	
	  salesReturnOrderBean1=new SalesReturnOrderBean1();
	  
	  salesReturnOrderBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnOrderModel.getAmount()));
	  salesReturnOrderBean1.setBillingDateAndTime(salesReturnOrderModel.getBillingDateAndTime());
	  salesReturnOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(salesReturnOrderModel.getBranchModel()));
	  salesReturnOrderBean1.setCustomerBean(CustomerBeanPreparation.prepareCustomerBean(salesReturnOrderModel.getCustomerModel()));
	  salesReturnOrderBean1.setDeductionOnMargin(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnOrderModel.getDeductionOnMargin()));
	  salesReturnOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnOrderModel.getDiscountPrice()));
	  salesReturnOrderBean1.setDoctorBean(DoctorBeanPreparation.prepareDoctorBean(salesReturnOrderModel.getDoctorModel()));
	  salesReturnOrderBean1.setId(salesReturnOrderModel.getId());
	  salesReturnOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnOrderModel.getPayAmount()));
	  salesReturnOrderBean1.setOrderIdByDate(salesReturnOrderModel.getOrderIdByDate());
	  salesReturnOrderBean1.setOutletBean(OutletBeanPreparation.prepareOutLetBean(salesReturnOrderModel.getOutletModel()));
	  salesReturnOrderBean1.setStatusBean(AddressBeanPreparation.prepareStatusBean(salesReturnOrderModel.getStatusModel()));
	  salesReturnOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnOrderModel.getTotalVAT()));
	  salesReturnOrderBean1.setSalesOrderBean1(SalesOrderBeanPreparation1.prepareSalesOrderBean1(salesReturnOrderModel.getSalesOrderModel()));
	  
	  return salesReturnOrderBean1;
  }
	
  public static List<SalesReturnOrderBean1> prepareListOfSalesReturnOrderBean1(List<SalesReturnOrderModel> salesReturnOrderModels){
		List<SalesReturnOrderBean1> salesReturnOrderBeans1 = null;
		if(salesReturnOrderModels != null && !salesReturnOrderModels.isEmpty()){
			salesReturnOrderBeans1 = new ArrayList<SalesReturnOrderBean1>();				
			for(SalesReturnOrderModel salesReturnOrderModel : salesReturnOrderModels){
				salesReturnOrderBeans1.add(prepareSalesReturnOrderBean1(salesReturnOrderModel));
				}
		}


return salesReturnOrderBeans1;


}
  
  public static SalesReturnOrderBean1 prepareSalesReturnOrderBeanForRevenueAccounts1(SalesReturnOrderModel salesReturnOrderModel){
		
		salesReturnOrderBean1 = new SalesReturnOrderBean1();			
		
		salesReturnOrderBean1.setId(salesReturnOrderModel.getId());
		salesReturnOrderBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnOrderModel.getAmount()));
		salesReturnOrderBean1.setBillingDateAndTime(salesReturnOrderModel.getBillingDateAndTime());
		salesReturnOrderBean1.setOrderIdByDate(salesReturnOrderModel.getOrderIdByDate());
		salesReturnOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnOrderModel.getDiscountPrice()));
		salesReturnOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnOrderModel.getPayAmount()));
		salesReturnOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnOrderModel.getTotalVAT()));
		salesReturnOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(salesReturnOrderModel.getBranchModel()));
						
	return salesReturnOrderBean1;


	}

  public static List<SalesReturnOrderBean1> prepareListOfSalesReturnOrderBeanForRevenueAccounts1(List<SalesReturnOrderModel> salesReturnOrderModels){
		List<SalesReturnOrderBean1> salesReturnOrderBeans1 = null;
		if(salesReturnOrderModels != null && !salesReturnOrderModels.isEmpty()){
			salesReturnOrderBeans1 = new ArrayList<SalesReturnOrderBean1>();				
			for(SalesReturnOrderModel salesReturnOrderModel : salesReturnOrderModels){
				salesReturnOrderBeans1.add(prepareSalesReturnOrderBeanForRevenueAccounts1(salesReturnOrderModel));
				}
		}


return salesReturnOrderBeans1;


  }

}
	


