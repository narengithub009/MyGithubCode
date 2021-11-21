package com.relyits.rmbs.model_preparation.purchase;

import java.text.ParseException;

import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnLineItemsModel;




public class PurchaseReturnLineItemsModelPreparation {
	

	private static PurchaseReturnLineItemsModel purchaseReturnLineItemsModel=null;
	
	public static PurchaseReturnLineItemsModel preparepurchaseReturnLineItemsModel(PurchaseLineItemsModel purchaseLineItemsModel) throws ParseException{
		purchaseReturnLineItemsModel=new PurchaseReturnLineItemsModel();
		
		purchaseReturnLineItemsModel.setAmount(purchaseLineItemsModel.getAmount());
		purchaseReturnLineItemsModel.setDelivaeredQuantity(purchaseLineItemsModel.getQuantity());
		purchaseReturnLineItemsModel.setDeliverableQuantity(purchaseLineItemsModel.getDeliverableQuantity());
		purchaseReturnLineItemsModel.setDiscount(purchaseLineItemsModel.getDiscount());
		purchaseReturnLineItemsModel.setExpiryDate(purchaseLineItemsModel.getExpiryDate());
		purchaseReturnLineItemsModel.setFreeQuantity(purchaseLineItemsModel.getFreeQuantity());
		purchaseReturnLineItemsModel.setNetPrice(purchaseLineItemsModel.getNetPrice());
		purchaseReturnLineItemsModel.setPreparationCharges(purchaseLineItemsModel.getPreparationCharges());
		purchaseReturnLineItemsModel.setProductInventoryModel(purchaseLineItemsModel.getProductInventoryModel());
		purchaseReturnLineItemsModel.setQuantity(purchaseLineItemsModel.getQuantity());
		purchaseReturnLineItemsModel.setPurchaseReturnOrderModel(PurchaseReturnOrderModelPreparation.prepareSpurchaseReturnOrderModel(purchaseLineItemsModel.getPurchaseOrderModel()));
		purchaseReturnLineItemsModel.setDeductionOnPaidAmount(purchaseLineItemsModel.getAmount());
		purchaseReturnLineItemsModel.setUnitPrice(purchaseLineItemsModel.getUnitPrice());
		purchaseReturnLineItemsModel.setVat(purchaseLineItemsModel.getVat());
		purchaseReturnLineItemsModel.setPayAmount(purchaseLineItemsModel.getPayAmount());
		return purchaseReturnLineItemsModel;
	   
   }  
}
