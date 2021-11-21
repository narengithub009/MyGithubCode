package com.relyits.rmbs.beans_preparation.purchase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.relyits.rmbs.beans.purchase.PurchaseReturnLineItemsBean;
import com.relyits.rmbs.beans_preparation.product.ProductInventoryBeanPreparation;
import com.relyits.rmbs.model.purchase.PurchaseReturnLineItemsModel;




public class PurchaseReturnLineItemsBeanPreparation {
	
	private static PurchaseReturnLineItemsBean returnLineItemsBean = null;	

	
	public static PurchaseReturnLineItemsBean preparePurchaseReturnLineitemBean(PurchaseReturnLineItemsModel purchaseReturnLineItemsModel){
		
		returnLineItemsBean =new PurchaseReturnLineItemsBean();
		
		Double amount = purchaseReturnLineItemsModel.getAmount();
		  Double unitPrice = purchaseReturnLineItemsModel.getUnitPrice();
		  Double discount = purchaseReturnLineItemsModel.getDiscount();
		  Double netPrice = purchaseReturnLineItemsModel.getNetPrice();
		  Double vat = purchaseReturnLineItemsModel.getVat();
		  Double preparationCharges = purchaseReturnLineItemsModel.getPreparationCharges();


		returnLineItemsBean.setAmount(Double.parseDouble(new DecimalFormat("##.##").format(amount)));
		returnLineItemsBean.setDeductionOnPaidAmount(purchaseReturnLineItemsModel.getDeductionOnPaidAmount());
		returnLineItemsBean.setDiscount(Double.parseDouble(new DecimalFormat("##.##").format(discount)));
		returnLineItemsBean.setExpiryDate(purchaseReturnLineItemsModel.getExpiryDate());
		returnLineItemsBean.setId(purchaseReturnLineItemsModel.getId());
		returnLineItemsBean.setNetPrice(Double.parseDouble(new DecimalFormat("##.##").format(netPrice)));
		returnLineItemsBean.setUnitPrice(Double.parseDouble(new DecimalFormat("##.##").format(unitPrice)));
		returnLineItemsBean.setPreparationCharges(Double.parseDouble(new DecimalFormat("##.##").format(preparationCharges)));
		returnLineItemsBean.setProductInventoryBean(ProductInventoryBeanPreparation.prepareProductInventoryBean(purchaseReturnLineItemsModel.getProductInventoryModel()));
		returnLineItemsBean.setPurchaseReturnOrderBean(PurchaseReturnOrderBeanPreparation.preparePurchaseReturnOrderBean(purchaseReturnLineItemsModel.getPurchaseReturnOrderModel()));
		returnLineItemsBean.setQuantity(purchaseReturnLineItemsModel.getQuantity());
		returnLineItemsBean.setVat(Double.parseDouble(new DecimalFormat("##.##").format(vat)));
		returnLineItemsBean.setDelivaeredQuantity(purchaseReturnLineItemsModel.getDelivaeredQuantity());
		returnLineItemsBean.setDeliverableQuantity(purchaseReturnLineItemsModel.getDeliverableQuantity());
		returnLineItemsBean.setFreeQantity(purchaseReturnLineItemsModel.getFreeQuantity());
		returnLineItemsBean.setPayAmount(purchaseReturnLineItemsModel.getPayAmount());
		return returnLineItemsBean;
		
	}
	public static List<PurchaseReturnLineItemsBean> prepareListOfPurchaseReturnLineitemBeans(List<PurchaseReturnLineItemsModel> purchaseReturnLineItemsModels ){
		List<PurchaseReturnLineItemsBean> returnLineItemsBeans = null;
		if(purchaseReturnLineItemsModels!=null && !purchaseReturnLineItemsModels.isEmpty()){
			returnLineItemsBeans = new ArrayList<PurchaseReturnLineItemsBean>();
			for(PurchaseReturnLineItemsModel returnLineItemsModel:purchaseReturnLineItemsModels){
				returnLineItemsBeans.add(preparePurchaseReturnLineitemBean(returnLineItemsModel));
			}
		}
			return returnLineItemsBeans;
	}
}
