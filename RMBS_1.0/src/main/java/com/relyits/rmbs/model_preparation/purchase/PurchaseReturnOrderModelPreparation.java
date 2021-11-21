package com.relyits.rmbs.model_preparation.purchase;

import java.text.ParseException;

import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;


public class PurchaseReturnOrderModelPreparation {
	

private static PurchaseReturnOrderModel purchaseReturnOrderModel=null;
	
	public static PurchaseReturnOrderModel prepareSpurchaseReturnOrderModel(PurchaseOrderModel purchaseOrderModel) throws ParseException{
	
		purchaseReturnOrderModel=new PurchaseReturnOrderModel();
		purchaseReturnOrderModel.setAmount(purchaseOrderModel.getAmount());
		purchaseReturnOrderModel.setBillingDateAndTime(DateAndTimeUtilities.getCurrentDateTimeInSqlFormat());
		purchaseReturnOrderModel.setBranchModel(purchaseOrderModel.getBranchModel());
		purchaseReturnOrderModel.setDiscountPrice(purchaseOrderModel.getDiscountPrice());
		purchaseReturnOrderModel.setOrderIdByDate(""+purchaseReturnOrderModel.getBillingDateAndTime());
		purchaseReturnOrderModel.setDeliveryDate(DateAndTimeUtilities.getCurrentDateTimeInSqlFormat());
		purchaseReturnOrderModel.setDeductionOnPaidAmount(purchaseOrderModel.getAmount());
		purchaseReturnOrderModel.setTotalVAT(purchaseOrderModel.getTotalVAT());
		purchaseReturnOrderModel.setPayAmount(purchaseOrderModel.getPayAmount());
		purchaseReturnOrderModel.setAgencyModel(purchaseOrderModel.getAgencyModel());
		purchaseReturnOrderModel.setOverallDiscount(purchaseOrderModel.getOverallDiscount());
		return purchaseReturnOrderModel;
	 }
}
