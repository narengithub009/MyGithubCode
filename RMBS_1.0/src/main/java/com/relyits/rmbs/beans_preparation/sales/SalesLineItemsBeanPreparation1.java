package com.relyits.rmbs.beans_preparation.sales;

import java.util.ArrayList;
import java.util.List;

import com.relyits.rmbs.beans.sales.SalesLineItemsBean1;
import com.relyits.rmbs.beans_preparation.product.ProductInventoryBeanPreparation;
import com.relyits.rmbs.beans_preparation.resources.AddressBeanPreparation;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.utilities.ConvertDoubleToBigDecimal;



public class SalesLineItemsBeanPreparation1 {

	private static SalesLineItemsBean1 salesLineItemsBean1=null;
	
	public static SalesLineItemsBean1 prepareSalesLineItemsBean1(SalesLineItemsModel salesLineItemsModel){
		
		salesLineItemsBean1=new SalesLineItemsBean1();
		salesLineItemsBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesLineItemsModel.getAmount()));
		salesLineItemsBean1.setDelivaeredQuantity(salesLineItemsModel.getDelivaeredQuantity());
		salesLineItemsBean1.setDeliverableQuantity(salesLineItemsModel.getDeliverableQuantity());
		salesLineItemsBean1.setDiscount(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesLineItemsModel.getDiscount()));
		salesLineItemsBean1.setExpiryDate(salesLineItemsModel.getExpiryDate());
		salesLineItemsBean1.setId(salesLineItemsModel.getId());
		salesLineItemsBean1.setMargin(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesLineItemsModel.getMargin()));
		salesLineItemsBean1.setNetPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesLineItemsModel.getNetPrice()));
		salesLineItemsBean1.setPreparationCharges(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesLineItemsModel.getPreparationCharges()));
		salesLineItemsBean1.setUnitPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesLineItemsModel.getUnitPrice()));
		salesLineItemsBean1.setVat(ConvertDoubleToBigDecimal.twoDecimalPlaces(salesLineItemsModel.getVat()));
		salesLineItemsBean1.setProductInventoryBean(ProductInventoryBeanPreparation.prepareProductInventoryBean(salesLineItemsModel.getProductInventoryModel()));
		salesLineItemsBean1.setSalesOrderBean1(SalesOrderBeanPreparation1.prepareSalesOrderBean1(salesLineItemsModel.getSalesOrderModel()));
		salesLineItemsBean1.setStatusBean(AddressBeanPreparation.prepareStatusBean(salesLineItemsModel.getStatusModel()));
		salesLineItemsBean1.setQuantity(salesLineItemsModel.getQuantity());
		
		return salesLineItemsBean1;

	}
	
	public static List<SalesLineItemsBean1> prepareListOfSalesLineItemsBean1(List<SalesLineItemsModel> salesLineItemsModels){
		List<SalesLineItemsBean1> salesLineItemsBeans1 = null;
		if(salesLineItemsModels != null && !salesLineItemsModels.isEmpty()){
			salesLineItemsBeans1 = new ArrayList<SalesLineItemsBean1>();				
			for(SalesLineItemsModel salesLineItemsModel : salesLineItemsModels){
				salesLineItemsBeans1.add(prepareSalesLineItemsBean1(salesLineItemsModel));
				}
		}
		return salesLineItemsBeans1;
	}
	
	
}
