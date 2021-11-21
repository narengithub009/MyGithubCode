package com.relyits.rmbs.model_preparation.sales;

import java.text.ParseException;

import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesReturnLineItemsModel;



public class SalesReturnLineItemsModelPreparation {
	

	private static SalesReturnLineItemsModel salesReturnLineItemsModel=null;
	
	public static SalesReturnLineItemsModel prepareSalesReturnLineItemsModel(SalesLineItemsModel salesLineItemsModel) throws ParseException{
		salesReturnLineItemsModel=new SalesReturnLineItemsModel();
		
		salesReturnLineItemsModel.setAmount(salesLineItemsModel.getAmount());
		salesReturnLineItemsModel.setDeductionOnMargin(salesLineItemsModel.getMargin());
		salesReturnLineItemsModel.setDelivaeredQuantity(salesLineItemsModel.getDelivaeredQuantity());
		salesReturnLineItemsModel.setDeliverableQuantity(salesLineItemsModel.getDeliverableQuantity());
		salesReturnLineItemsModel.setDiscount(salesLineItemsModel.getDiscount());
		salesReturnLineItemsModel.setExpiryDate(salesLineItemsModel.getExpiryDate());
		salesReturnLineItemsModel.setNetPrice(salesLineItemsModel.getNetPrice());
		salesReturnLineItemsModel.setOutletModel(salesLineItemsModel.getOutletModel());
		salesReturnLineItemsModel.setPreparationCharges(salesLineItemsModel.getPreparationCharges());
		salesReturnLineItemsModel.setProductInventoryModel(salesLineItemsModel.getProductInventoryModel());
		salesReturnLineItemsModel.setQuantity(salesLineItemsModel.getQuantity());
		salesReturnLineItemsModel.setSalesReturnOrderModel(SalesReturnOrderModelPreparation.prepareSSalesReturnOrderModel(salesLineItemsModel.getSalesOrderModel()));
		salesReturnLineItemsModel.setStatusModel(salesLineItemsModel.getStatusModel());
		salesReturnLineItemsModel.setUnitPrice(salesLineItemsModel.getUnitPrice());
		salesReturnLineItemsModel.setVat(salesLineItemsModel.getVat());
	
		
		
		
		return salesReturnLineItemsModel;
	   
   }  
	

}
