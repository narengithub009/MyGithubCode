package com.relyits.rmbs.beans_preparation.sales;

import java.util.ArrayList;
import java.util.List;

import com.relyits.rmbs.beans.sales.SalesReturnLineItemsBean1;
import com.relyits.rmbs.beans_preparation.product.ProductInventoryBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.OutletBeanPreparation;
import com.relyits.rmbs.beans_preparation.resources.AddressBeanPreparation;
import com.relyits.rmbs.model.sales.SalesReturnLineItemsModel;
import com.relyits.rmbs.utilities.ConvertDoubleToBigDecimal;



public class SalesReturnLineItemsBeanPreparation1 {
	
	private static SalesReturnLineItemsBean1 salesReturnLineItemsBean1=null;
	private static List<SalesReturnLineItemsBean1> salesReturnLineItemsBeans1 = null;
	
 public static SalesReturnLineItemsBean1 prepareSalesReturnLineItemsBean1(SalesReturnLineItemsModel salesReturnLineItemsModel){
	 
	 salesReturnLineItemsBean1=new SalesReturnLineItemsBean1();
	 
	 salesReturnLineItemsBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnLineItemsModel.getAmount()));
	 salesReturnLineItemsBean1.setDeductionOnMargin(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnLineItemsModel.getDeductionOnMargin()));
	 salesReturnLineItemsBean1.setDelivaeredQuantity(salesReturnLineItemsModel.getDelivaeredQuantity());
	 salesReturnLineItemsBean1.setDeliverableQuantity(salesReturnLineItemsModel.getDeliverableQuantity());
	 salesReturnLineItemsBean1.setDiscount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnLineItemsModel.getDiscount()));
	 salesReturnLineItemsBean1.setExpiryDate(salesReturnLineItemsModel.getExpiryDate());
	 salesReturnLineItemsBean1.setId(salesReturnLineItemsModel.getId());
	 salesReturnLineItemsBean1.setNetPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnLineItemsModel.getNetPrice()));
	 salesReturnLineItemsBean1.setOutletBean(OutletBeanPreparation.prepareOutLetBean(salesReturnLineItemsModel.getOutletModel()));
	 salesReturnLineItemsBean1.setPreparationCharges(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnLineItemsModel.getPreparationCharges()));
	 salesReturnLineItemsBean1.setProductInventoryBean(ProductInventoryBeanPreparation.prepareProductInventoryBean(salesReturnLineItemsModel.getProductInventoryModel()));
	 salesReturnLineItemsBean1.setQuantity(salesReturnLineItemsModel.getQuantity());
	 salesReturnLineItemsBean1.setSalesReturnOrderBean1(SalesReturnOrderBeanPreparation1.prepareSalesReturnOrderBean1(salesReturnLineItemsModel.getSalesReturnOrderModel()));
	 salesReturnLineItemsBean1.setStatusBean(AddressBeanPreparation.prepareStatusBean(salesReturnLineItemsModel.getStatusModel()));
	 salesReturnLineItemsBean1.setUnitPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnLineItemsModel.getUnitPrice()));
	 salesReturnLineItemsBean1.setVat(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesReturnLineItemsModel.getVat()));
	 
	 return salesReturnLineItemsBean1;
 }
	
	public static List<SalesReturnLineItemsBean1> prepareListOfSalesReturnLineItemsBeans1(List<SalesReturnLineItemsModel> salesReturnLineItemsModels){
		
		if(salesReturnLineItemsModels != null && !salesReturnLineItemsModels.isEmpty()){
			salesReturnLineItemsBeans1 = new ArrayList<SalesReturnLineItemsBean1>();				
			for(SalesReturnLineItemsModel salesReturnLineItemsModel : salesReturnLineItemsModels){
				salesReturnLineItemsBeans1.add(prepareSalesReturnLineItemsBean1(salesReturnLineItemsModel));
				}
		}


return salesReturnLineItemsBeans1;


}

}
