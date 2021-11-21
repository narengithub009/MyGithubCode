package com.relyits.rmbs.beans_preparation.sales;

import java.util.ArrayList;
import java.util.List;

import com.ibm.icu.text.DecimalFormat;
import com.relyits.rmbs.beans.sales.SalesReturnOrderBean;
import com.relyits.rmbs.beans_preparation.consumer.CustomerBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.DoctorBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.OutletBeanPreparation;
import com.relyits.rmbs.beans_preparation.resources.AddressBeanPreparation;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;



public class SalesReturnOrderBeanPreparation {
	
	
	private static SalesReturnOrderBean salesReturnOrderBean=null;
	
  public static SalesReturnOrderBean prepareSalesReturnOrderBean(SalesReturnOrderModel salesReturnOrderModel){
	
	  salesReturnOrderBean=new SalesReturnOrderBean();
	  
	  salesReturnOrderBean.setAmount(Double.parseDouble(new DecimalFormat("##.##").format(salesReturnOrderModel.getAmount())));
	  salesReturnOrderBean.setBillingDateAndTime(salesReturnOrderModel.getBillingDateAndTime());
	  salesReturnOrderBean.setBranchBean(BranchBeanPreparation.prepareBranchBean(salesReturnOrderModel.getBranchModel()));
	  salesReturnOrderBean.setCustomerBean(CustomerBeanPreparation.prepareCustomerBean(salesReturnOrderModel.getCustomerModel()));
	  salesReturnOrderBean.setDeductionOnMargin(Double.parseDouble(new DecimalFormat("##.##").format(salesReturnOrderModel.getDeductionOnMargin())));
	  salesReturnOrderBean.setDiscountPrice(Double.parseDouble(new DecimalFormat("##.##").format(salesReturnOrderModel.getDiscountPrice())));
	  salesReturnOrderBean.setDoctorBean(DoctorBeanPreparation.prepareDoctorBean(salesReturnOrderModel.getDoctorModel()));
	  salesReturnOrderBean.setId(salesReturnOrderModel.getId());
	  salesReturnOrderBean.setPayAmount(salesReturnOrderModel.getPayAmount());
	  salesReturnOrderBean.setOrderIdByDate(salesReturnOrderModel.getOrderIdByDate());
	  salesReturnOrderBean.setOutletBean(OutletBeanPreparation.prepareOutLetBean(salesReturnOrderModel.getOutletModel()));
	  salesReturnOrderBean.setStatusBean(AddressBeanPreparation.prepareStatusBean(salesReturnOrderModel.getStatusModel()));
	  salesReturnOrderBean.setTotalVAT(Double.parseDouble(new DecimalFormat("##.##").format(salesReturnOrderModel.getTotalVAT())));
	  salesReturnOrderBean.setSalesOrderBean(SalesOrderBeanPreparation.prepareSalesOrderBean(salesReturnOrderModel.getSalesOrderModel()));
	  
	  return salesReturnOrderBean;
  }
	
  public static List<SalesReturnOrderBean> prepareListOfSalesReturnOrderBean(List<SalesReturnOrderModel> salesReturnOrderModels){
		List<SalesReturnOrderBean> salesReturnOrderBeans = null;
		if(salesReturnOrderModels != null && !salesReturnOrderModels.isEmpty()){
			salesReturnOrderBeans = new ArrayList<SalesReturnOrderBean>();				
			for(SalesReturnOrderModel salesReturnOrderModel : salesReturnOrderModels){
				salesReturnOrderBeans.add(prepareSalesReturnOrderBean(salesReturnOrderModel));
				}
		}


return salesReturnOrderBeans;


}
  
  public static SalesReturnOrderBean prepareSalesReturnOrderBeanForRevenueAccounts(SalesReturnOrderModel salesReturnOrderModel){
		
		salesReturnOrderBean = new SalesReturnOrderBean();			
		
		salesReturnOrderBean.setId(salesReturnOrderModel.getId());
		salesReturnOrderBean.setAmount(Double.parseDouble(new DecimalFormat("##.##").format(salesReturnOrderModel.getAmount())));
		salesReturnOrderBean.setBillingDateAndTime(salesReturnOrderModel.getBillingDateAndTime());
		salesReturnOrderBean.setOrderIdByDate(salesReturnOrderModel.getOrderIdByDate());
		salesReturnOrderBean.setDiscountPrice(Double.parseDouble(new DecimalFormat("##.##").format(salesReturnOrderModel.getDiscountPrice())));
		salesReturnOrderBean.setPayAmount(Double.parseDouble(new DecimalFormat("##.##").format(salesReturnOrderModel.getPayAmount())));
		salesReturnOrderBean.setTotalVAT(Double.parseDouble(new DecimalFormat("##.##").format(salesReturnOrderModel.getTotalVAT())));
		salesReturnOrderBean.setBranchBean(BranchBeanPreparation.prepareBranchBean(salesReturnOrderModel.getBranchModel()));
						
	return salesReturnOrderBean;


	}

  public static List<SalesReturnOrderBean> prepareListOfSalesReturnOrderBeanForRevenueAccounts(List<SalesReturnOrderModel> salesReturnOrderModels){
		List<SalesReturnOrderBean> salesReturnOrderBeans = null;
		if(salesReturnOrderModels != null && !salesReturnOrderModels.isEmpty()){
			salesReturnOrderBeans = new ArrayList<SalesReturnOrderBean>();				
			for(SalesReturnOrderModel salesReturnOrderModel : salesReturnOrderModels){
				salesReturnOrderBeans.add(prepareSalesReturnOrderBeanForRevenueAccounts(salesReturnOrderModel));
				}
		}


return salesReturnOrderBeans;


  }

}
	


