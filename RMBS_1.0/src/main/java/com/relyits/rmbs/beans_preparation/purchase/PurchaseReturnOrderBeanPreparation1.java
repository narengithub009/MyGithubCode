package com.relyits.rmbs.beans_preparation.purchase;

import java.util.ArrayList;
import java.util.List;
import com.relyits.rmbs.beans.purchase.PurchaseReturnOrderBean1;
import com.relyits.rmbs.beans_preparation.registration.AgencyBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;
import com.relyits.rmbs.utilities.ConvertDoubleToBigDecimal;


public class PurchaseReturnOrderBeanPreparation1 {
	
private static PurchaseReturnOrderBean1 returnOrderBean1 = null;
	public static PurchaseReturnOrderBean1 preparePurchaseReturnOrderBean1(PurchaseReturnOrderModel purchaseReturnOrderModel){
		returnOrderBean1 = new PurchaseReturnOrderBean1();
		
		returnOrderBean1.setAgencyBean(AgencyBeanPreparation.prepareAgencyBean(purchaseReturnOrderModel.getAgencyModel()));
		returnOrderBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getAmount()));
		returnOrderBean1.setBillingDateAndTime(purchaseReturnOrderModel.getBillingDateAndTime());
		returnOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(purchaseReturnOrderModel.getBranchModel()));
		returnOrderBean1.setDeductionOnPaidAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getDeductionOnPaidAmount()));
		returnOrderBean1.setDeliveryDate(purchaseReturnOrderModel.getDeliveryDate());
		returnOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getDiscountPrice()));
		returnOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getTotalVAT()));
		returnOrderBean1.setOrderIdByDate(purchaseReturnOrderModel.getOrderIdByDate());
		returnOrderBean1.setPurchaseOrderBean1(PurchaseOrderBeanPreparation1.preparePurchaseOrderBean1(purchaseReturnOrderModel.getPurchaseOrderModel()));
		returnOrderBean1.setOverallDiscount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getOverallDiscount()));
		returnOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getPayAmount()));

		
		
		return returnOrderBean1;
		
	}
	public static PurchaseReturnOrderBean1 preparePurchaseReturnOrderBeanwithId1(PurchaseReturnOrderModel purchaseReturnOrderModel){
		returnOrderBean1 = new PurchaseReturnOrderBean1();
		returnOrderBean1.setId(purchaseReturnOrderModel.getId());
		returnOrderBean1.setAgencyBean(AgencyBeanPreparation.prepareAgencyBean(purchaseReturnOrderModel.getAgencyModel()));
		returnOrderBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getAmount()));
		returnOrderBean1.setBillingDateAndTime(purchaseReturnOrderModel.getBillingDateAndTime());
		returnOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(purchaseReturnOrderModel.getBranchModel()));
		returnOrderBean1.setDeductionOnPaidAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getDeductionOnPaidAmount()));
		returnOrderBean1.setDeliveryDate(purchaseReturnOrderModel.getDeliveryDate());
		returnOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getDiscountPrice()));
		returnOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getTotalVAT()));
		returnOrderBean1.setOrderIdByDate(purchaseReturnOrderModel.getOrderIdByDate());
		returnOrderBean1.setPurchaseOrderBean1(PurchaseOrderBeanPreparation1.preparePurchaseOrderBean1(purchaseReturnOrderModel.getPurchaseOrderModel()));
		returnOrderBean1.setOverallDiscount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getOverallDiscount()));
		returnOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getPayAmount()));

		
		
		return returnOrderBean1;
		
	}
	public static List<PurchaseReturnOrderBean1> preparePurchaseReturnOrderBeanFromPurchaseReturnOrderModel1(List<PurchaseReturnOrderModel> purchaseReturnOrderModels) {
		
		List<PurchaseReturnOrderBean1> returnOrderBeans1= null;
		if(purchaseReturnOrderModels!=null && !purchaseReturnOrderModels.isEmpty()){
			
			returnOrderBeans1 = new ArrayList<PurchaseReturnOrderBean1>();
			for(PurchaseReturnOrderModel orderModel:purchaseReturnOrderModels){
				returnOrderBeans1.add(preparePurchaseReturnOrderBeanwithId1(orderModel));
			}
					
		}	
		return returnOrderBeans1;
		
	}
	
	public static PurchaseReturnOrderBean1 preparePurchaseReturnOrderBeanForRevenueAccounts1(PurchaseReturnOrderModel purchaseReturnOrderModel){
		
		returnOrderBean1 = new PurchaseReturnOrderBean1();			
		
		returnOrderBean1.setId(purchaseReturnOrderModel.getId());
		returnOrderBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getAmount()));
		returnOrderBean1.setBillingDateAndTime(purchaseReturnOrderModel.getBillingDateAndTime());
		returnOrderBean1.setOrderIdByDate(purchaseReturnOrderModel.getOrderIdByDate());
		returnOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getDiscountPrice()));
		returnOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getPayAmount()));
		returnOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnOrderModel.getTotalVAT()));
		returnOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(purchaseReturnOrderModel.getBranchModel()));
						
	return returnOrderBean1;


	}

	public static List<PurchaseReturnOrderBean1> prepareListOfPurchaseReturnOrderBeanForRevenueAccounts1(List<PurchaseReturnOrderModel> purchaseReturnOrderModels){
		List<PurchaseReturnOrderBean1> purchaseReturnOrderBeans1 = null;
		if(purchaseReturnOrderModels != null && !purchaseReturnOrderModels.isEmpty()){
			purchaseReturnOrderBeans1 = new ArrayList<PurchaseReturnOrderBean1>();				
			for(PurchaseReturnOrderModel purchaseReturnOrderModel : purchaseReturnOrderModels){
				purchaseReturnOrderBeans1.add(preparePurchaseReturnOrderBeanForRevenueAccounts1(purchaseReturnOrderModel));
				}
		}


	return purchaseReturnOrderBeans1;


	}
}
