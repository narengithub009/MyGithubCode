package com.relyits.rmbs.beans_preparation.purchase;

import java.util.ArrayList;
import java.util.List;

import com.ibm.icu.text.DecimalFormat;
import com.relyits.rmbs.beans.purchase.PurchaseReturnOrderBean;
import com.relyits.rmbs.beans_preparation.registration.AgencyBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;


public class PurchaseReturnOrderBeanPreparation {
	
private static PurchaseReturnOrderBean returnOrderBean = null;
	public static PurchaseReturnOrderBean preparePurchaseReturnOrderBean(PurchaseReturnOrderModel purchaseReturnOrderModel){
		returnOrderBean = new PurchaseReturnOrderBean();
		
		returnOrderBean.setAgencyBean(AgencyBeanPreparation.prepareAgencyBean(purchaseReturnOrderModel.getAgencyModel()));
		returnOrderBean.setAmount(purchaseReturnOrderModel.getAmount());
		returnOrderBean.setBillingDateAndTime(purchaseReturnOrderModel.getBillingDateAndTime());
		returnOrderBean.setBranchBean(BranchBeanPreparation.prepareBranchBean(purchaseReturnOrderModel.getBranchModel()));
		returnOrderBean.setDeductionOnPaidAmount(purchaseReturnOrderModel.getDeductionOnPaidAmount());
		returnOrderBean.setDeliveryDate(purchaseReturnOrderModel.getDeliveryDate());
		returnOrderBean.setDiscountPrice(purchaseReturnOrderModel.getDiscountPrice());
		returnOrderBean.setTotalVAT(purchaseReturnOrderModel.getTotalVAT());
		returnOrderBean.setOrderIdByDate(purchaseReturnOrderModel.getOrderIdByDate());
		returnOrderBean.setPurchaseOrderBean(PurchaseOrderBeanPreparation.preparePurchaseOrderBean(purchaseReturnOrderModel.getPurchaseOrderModel()));
		returnOrderBean.setOverallDiscount(purchaseReturnOrderModel.getOverallDiscount());
		returnOrderBean.setPayAmount(purchaseReturnOrderModel.getPayAmount());
			
		
		return returnOrderBean;
		
	}
	public static PurchaseReturnOrderBean preparePurchaseReturnOrderBeanwithId(PurchaseReturnOrderModel purchaseReturnOrderModel){
		returnOrderBean = new PurchaseReturnOrderBean();
		returnOrderBean.setId(purchaseReturnOrderModel.getId());
		returnOrderBean.setAgencyBean(AgencyBeanPreparation.prepareAgencyBean(purchaseReturnOrderModel.getAgencyModel()));
		returnOrderBean.setAmount(purchaseReturnOrderModel.getAmount());
		returnOrderBean.setBillingDateAndTime(purchaseReturnOrderModel.getBillingDateAndTime());
		returnOrderBean.setBranchBean(BranchBeanPreparation.prepareBranchBean(purchaseReturnOrderModel.getBranchModel()));
		returnOrderBean.setDeductionOnPaidAmount(purchaseReturnOrderModel.getDeductionOnPaidAmount());
		returnOrderBean.setDeliveryDate(purchaseReturnOrderModel.getDeliveryDate());
		returnOrderBean.setDiscountPrice(purchaseReturnOrderModel.getDiscountPrice());
		returnOrderBean.setTotalVAT(purchaseReturnOrderModel.getTotalVAT());
		returnOrderBean.setOrderIdByDate(purchaseReturnOrderModel.getOrderIdByDate());
		returnOrderBean.setPurchaseOrderBean(PurchaseOrderBeanPreparation.preparePurchaseOrderBean(purchaseReturnOrderModel.getPurchaseOrderModel()));
		returnOrderBean.setOverallDiscount(purchaseReturnOrderModel.getOverallDiscount());
		returnOrderBean.setPayAmount(purchaseReturnOrderModel.getPayAmount());

		
		
		return returnOrderBean;
		
	}
	public static List<PurchaseReturnOrderBean> preparePurchaseReturnOrderBeanFromPurchaseReturnOrderModel(List<PurchaseReturnOrderModel> purchaseReturnOrderModels) {
		
		List<PurchaseReturnOrderBean> returnOrderBeans= null;
		if(purchaseReturnOrderModels!=null && !purchaseReturnOrderModels.isEmpty()){
			
			returnOrderBeans = new ArrayList<PurchaseReturnOrderBean>();
			for(PurchaseReturnOrderModel orderModel:purchaseReturnOrderModels){
				returnOrderBeans.add(preparePurchaseReturnOrderBeanwithId(orderModel));
			}
					
		}	
		return returnOrderBeans;
		
	}
	
	public static PurchaseReturnOrderBean preparePurchaseReturnOrderBeanForRevenueAccounts(PurchaseReturnOrderModel purchaseReturnOrderModel){
		
		returnOrderBean = new PurchaseReturnOrderBean();			
		
		returnOrderBean.setId(purchaseReturnOrderModel.getId());
		returnOrderBean.setAmount(Double.parseDouble(new DecimalFormat("##.##").format(purchaseReturnOrderModel.getAmount())));
		returnOrderBean.setBillingDateAndTime(purchaseReturnOrderModel.getBillingDateAndTime());
		returnOrderBean.setOrderIdByDate(purchaseReturnOrderModel.getOrderIdByDate());
		returnOrderBean.setDiscountPrice(Double.parseDouble(new DecimalFormat("##.##").format(purchaseReturnOrderModel.getDiscountPrice())));
		returnOrderBean.setPayAmount(Double.parseDouble(new DecimalFormat("##.##").format(purchaseReturnOrderModel.getPayAmount())));
		returnOrderBean.setTotalVAT(Double.parseDouble(new DecimalFormat("##.##").format(purchaseReturnOrderModel.getTotalVAT())));
		returnOrderBean.setBranchBean(BranchBeanPreparation.prepareBranchBean(purchaseReturnOrderModel.getBranchModel()));
						
	return returnOrderBean;


	}

	public static List<PurchaseReturnOrderBean> prepareListOfPurchaseReturnOrderBeanForRevenueAccounts(List<PurchaseReturnOrderModel> purchaseReturnOrderModels){
		List<PurchaseReturnOrderBean> purchaseReturnOrderBeans = null;
		if(purchaseReturnOrderModels != null && !purchaseReturnOrderModels.isEmpty()){
			purchaseReturnOrderBeans = new ArrayList<PurchaseReturnOrderBean>();				
			for(PurchaseReturnOrderModel purchaseReturnOrderModel : purchaseReturnOrderModels){
				purchaseReturnOrderBeans.add(preparePurchaseReturnOrderBeanForRevenueAccounts(purchaseReturnOrderModel));
				}
		}


	return purchaseReturnOrderBeans;


	}
}
