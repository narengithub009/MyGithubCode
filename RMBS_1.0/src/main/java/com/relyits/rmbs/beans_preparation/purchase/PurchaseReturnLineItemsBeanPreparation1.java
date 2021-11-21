package com.relyits.rmbs.beans_preparation.purchase;

import java.util.ArrayList;
import java.util.List;

import com.relyits.rmbs.beans.purchase.PurchaseReturnLineItemsBean1;
import com.relyits.rmbs.beans_preparation.product.ProductInventoryBeanPreparation;
import com.relyits.rmbs.model.purchase.PurchaseReturnLineItemsModel;
import com.relyits.rmbs.utilities.ConvertDoubleToBigDecimal;




public class PurchaseReturnLineItemsBeanPreparation1 {
	
	private static PurchaseReturnLineItemsBean1 returnLineItemsBean1 = null;
	private static List<PurchaseReturnLineItemsBean1> returnLineItemsBeans1 = null;

	
	public static PurchaseReturnLineItemsBean1 preparePurchaseReturnLineitemBean1(PurchaseReturnLineItemsModel purchaseReturnLineItemsModel){
		
		returnLineItemsBean1 =new PurchaseReturnLineItemsBean1();
		
		
		returnLineItemsBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnLineItemsModel.getAmount()));
		returnLineItemsBean1.setDeductionOnPaidAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnLineItemsModel.getDeductionOnPaidAmount()));
		returnLineItemsBean1.setDiscount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnLineItemsModel.getDiscount()));
		returnLineItemsBean1.setExpiryDate(purchaseReturnLineItemsModel.getExpiryDate());
		returnLineItemsBean1.setId(purchaseReturnLineItemsModel.getId());
		returnLineItemsBean1.setNetPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnLineItemsModel.getNetPrice()));
		returnLineItemsBean1.setUnitPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnLineItemsModel.getUnitPrice()));
		returnLineItemsBean1.setPreparationCharges(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnLineItemsModel.getPreparationCharges()));
		returnLineItemsBean1.setProductInventoryBean(ProductInventoryBeanPreparation.prepareProductInventoryBean(purchaseReturnLineItemsModel.getProductInventoryModel()));
		returnLineItemsBean1.setPurchaseReturnOrderBean1(PurchaseReturnOrderBeanPreparation1.preparePurchaseReturnOrderBean1(purchaseReturnLineItemsModel.getPurchaseReturnOrderModel()));
		returnLineItemsBean1.setQuantity(purchaseReturnLineItemsModel.getQuantity());
		returnLineItemsBean1.setVat(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnLineItemsModel.getVat()));
		returnLineItemsBean1.setDelivaeredQuantity(purchaseReturnLineItemsModel.getDelivaeredQuantity());
		returnLineItemsBean1.setDeliverableQuantity(purchaseReturnLineItemsModel.getDeliverableQuantity());
		returnLineItemsBean1.setFreeQantity(purchaseReturnLineItemsModel.getFreeQuantity());
		returnLineItemsBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseReturnLineItemsModel.getPayAmount()));
		return returnLineItemsBean1;
		
	}
	public static List<PurchaseReturnLineItemsBean1> prepareListOfPurchaseReturnLineitemBeans1(List<PurchaseReturnLineItemsModel> purchaseReturnLineItemsModels ){
		if(purchaseReturnLineItemsModels!=null && !purchaseReturnLineItemsModels.isEmpty()){
			returnLineItemsBeans1 = new ArrayList<PurchaseReturnLineItemsBean1>();
			for(PurchaseReturnLineItemsModel returnLineItemsModel:purchaseReturnLineItemsModels){
				returnLineItemsBeans1.add(preparePurchaseReturnLineitemBean1(returnLineItemsModel));
			}
		}
			return returnLineItemsBeans1;
	}
}
