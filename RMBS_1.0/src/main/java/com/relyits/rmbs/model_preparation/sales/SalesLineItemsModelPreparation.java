package com.relyits.rmbs.model_preparation.sales;

import java.text.ParseException;

import com.relyits.rmbs.beans.sales.SalesLineItemsBean;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model_preparation.product.ProductInventoryModelPreparation;



public class SalesLineItemsModelPreparation {
	
	private static SalesLineItemsModel salesLineItemsModel=null;
	
	public static SalesLineItemsModel prepareSalesLineItemsModel(SalesLineItemsBean salesLineItemsBean){
	  
		salesLineItemsModel=new SalesLineItemsModel();
	
	   salesLineItemsModel.setAmount(salesLineItemsBean.getAmount());
	   salesLineItemsModel.setDelivaeredQuantity(salesLineItemsBean.getQuantity());
	   salesLineItemsModel.setDeliverableQuantity(salesLineItemsBean.getQuantity());
	   salesLineItemsModel.setDiscount(salesLineItemsBean.getDiscount());
	   salesLineItemsModel.setExpiryDate(salesLineItemsBean.getExpiryDate());
	   salesLineItemsModel.setId(salesLineItemsBean.getId());
	   salesLineItemsModel.setMargin(salesLineItemsBean.getMargin());
	   salesLineItemsModel.setNetPrice(salesLineItemsBean.getNetPrice());
	   salesLineItemsModel.setPreparationCharges(salesLineItemsBean.getPreparationCharges());
	   salesLineItemsModel.setQuantity(salesLineItemsBean.getQuantity());
	   salesLineItemsModel.setUnitPrice(salesLineItemsBean.getUnitPrice());
	   salesLineItemsModel.setVat(salesLineItemsBean.getVat());
	   try {
		salesLineItemsModel.setProductInventoryModel(ProductInventoryModelPreparation.prepareProductInventoryModel(salesLineItemsBean.getProductInventoryBean()));
	} catch (ParseException e) {
		e.printStackTrace();
	}
	   salesLineItemsModel.setSalesOrderModel(SalesOrderModelPreparation.prepareSalesOrderBean(salesLineItemsBean.getSalesOrderBean()));
	   
	   return salesLineItemsModel;
	   
   }  
	
	private static double product_unitPrice=0.00;
	private static double product_netPrice=0.00;
	
	public static double getNetPrice(double price,double vat,double discount){
		product_unitPrice=price;
		product_netPrice=(product_unitPrice-(product_unitPrice*discount/100));
		product_netPrice=product_netPrice+(product_netPrice*vat/100);
		return product_netPrice;
		
	}
	public static double getAmount(double price,int quantity){
		
		return price*quantity;
		
	}
	public static double getPayAmount(double price,int quantity,double vat,double discount){
		product_unitPrice=price;
		product_netPrice=(product_unitPrice-(product_unitPrice*discount/100));
		product_netPrice=product_netPrice+(product_netPrice*vat/100);
		return product_netPrice*quantity;
		
	}
	public static double getMargin(double dlPrice,double unitPrice,int quantity,double vat,double discount){
		double sale_amount=getNetPrice(unitPrice, vat, discount)*quantity;
		
		double purchase_amount=dlPrice*quantity;
		return sale_amount-purchase_amount;
		
	}
	public static double getTotalDiscountPrice(double price,int quantity,double discount){
		product_unitPrice=price;
		double order_discount_amount=(product_unitPrice*(discount/100))*quantity;
		return order_discount_amount;
		
	}
	public static double getTotalVatPrice(double price,int quantity,double vat,double discount){
		product_unitPrice=price;
		product_netPrice=(product_unitPrice-(product_unitPrice*discount/100));
		double product_vat_price=(product_netPrice*vat/100)*quantity;
		return product_vat_price;
		
	}
	public static double getLineItemVatPrice(double price,int quantity,double vat,double discount){
		product_unitPrice=price;
		product_netPrice=(product_unitPrice-(product_unitPrice*discount/100));
		double product_vat_price=(product_netPrice*vat/100)*quantity;
		return product_vat_price;
		
	}
	public static double getLineItemDiscountPrice(double price,int quantity,double discount){
		product_unitPrice=price;
		double order_discount_amount=(product_unitPrice*(discount/100))*quantity;
		return order_discount_amount;
		
	}
}
