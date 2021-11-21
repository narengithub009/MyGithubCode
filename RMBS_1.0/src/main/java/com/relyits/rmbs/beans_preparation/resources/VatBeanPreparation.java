package com.relyits.rmbs.beans_preparation.resources;

import java.util.ArrayList;
import java.util.List;

import com.relyits.rmbs.beans.purchase.PurchaseLineItemsBean1;
import com.relyits.rmbs.beans.purchase.PurchaseReturnLineItemsBean1;
import com.relyits.rmbs.beans.sales.SalesLineItemsBean1;
import com.relyits.rmbs.beans.sales.SalesReturnLineItemsBean1;
import com.relyits.rmbs.beans.sales.VatBean;


public class VatBeanPreparation {
private static VatBean vatBean=null;
	
	public static VatBean prepareVatBeanOfSalesLineItems(SalesLineItemsBean1 salesLineItemsBean1){
		
		vatBean=new VatBean();
		vatBean.setAmount(salesLineItemsBean1.getAmount());
		vatBean.setDiscount(salesLineItemsBean1.getDiscount());
		vatBean.setVat(salesLineItemsBean1.getVat());		
		return vatBean;

	}
	
	public static List<VatBean> prepareListOfVatBeanOfSalesLineItems(List<SalesLineItemsBean1> salesLineItemsBeans1){
		List<VatBean> vatBeans = null;
		if(salesLineItemsBeans1 != null && !salesLineItemsBeans1.isEmpty()){
			vatBeans = new ArrayList<VatBean>();				
			for(SalesLineItemsBean1 salesLineItemsBean1 : salesLineItemsBeans1){
				vatBeans.add(prepareVatBeanOfSalesLineItems(salesLineItemsBean1));
				}
		}
		return vatBeans;
	}
	
	
public static VatBean prepareVatBeanOfSalesReturnLineItems(SalesReturnLineItemsBean1 salesReturnLineItemsBean1){
		
		vatBean=new VatBean();
		vatBean.setAmount(salesReturnLineItemsBean1.getAmount());
		vatBean.setDiscount(salesReturnLineItemsBean1.getDiscount());
		vatBean.setVat(salesReturnLineItemsBean1.getVat());		
		return vatBean;

	}
	
	public static List<VatBean> prepareListOfVatBeanOfSalesReturnLineItems(List<SalesReturnLineItemsBean1> salesReturnLineItemsBeans1){
		List<VatBean> vatBeans = null;
		if(salesReturnLineItemsBeans1 != null && !salesReturnLineItemsBeans1.isEmpty()){
			vatBeans = new ArrayList<VatBean>();				
			for(SalesReturnLineItemsBean1 salesReturnLineItemsBean1 : salesReturnLineItemsBeans1){
				vatBeans.add(prepareVatBeanOfSalesReturnLineItems(salesReturnLineItemsBean1));
				}
		}
		return vatBeans;
	}
	
	
public static VatBean prepareVatBeanOfPurchaseLineItems(PurchaseLineItemsBean1 purchaseLineItemsBean1){
		
		vatBean=new VatBean();
		vatBean.setAmount(purchaseLineItemsBean1.getAmount());
		vatBean.setDiscount(purchaseLineItemsBean1.getDiscount());
		vatBean.setVat(purchaseLineItemsBean1.getVat());		
		return vatBean;

	}
	
	public static List<VatBean> prepareListOfVatBeanOfPurchaseLineItems(List<PurchaseLineItemsBean1> purchaseLineItemsBeans1){
		List<VatBean> vatBeans = null;
		if(purchaseLineItemsBeans1 != null && !purchaseLineItemsBeans1.isEmpty()){
			vatBeans = new ArrayList<VatBean>();				
			for(PurchaseLineItemsBean1 purchaseLineItemsBean1 : purchaseLineItemsBeans1){
				vatBeans.add(prepareVatBeanOfPurchaseLineItems(purchaseLineItemsBean1));
				}
		}
		return vatBeans;
	}
	
	
public static VatBean prepareVatBeanOfPurchaseReturnLineItems(PurchaseReturnLineItemsBean1 purchaseReturnLineItemsBean1){
		
		vatBean=new VatBean();
		vatBean.setAmount(purchaseReturnLineItemsBean1.getAmount());
		vatBean.setDiscount(purchaseReturnLineItemsBean1.getDiscount());
		vatBean.setVat(purchaseReturnLineItemsBean1.getVat());		
		return vatBean;

	}
	
	public static List<VatBean> prepareListOfVatBeanOfPurchaseReturnLineItems(List<PurchaseReturnLineItemsBean1> purchaseReturnLineItemsBeans1){
		List<VatBean> vatBeans = null;
		if(purchaseReturnLineItemsBeans1 != null && !purchaseReturnLineItemsBeans1.isEmpty()){
			vatBeans = new ArrayList<VatBean>();				
			for(PurchaseReturnLineItemsBean1 purchaseReturnLineItemsBean1 : purchaseReturnLineItemsBeans1){
				vatBeans.add(prepareVatBeanOfPurchaseReturnLineItems(purchaseReturnLineItemsBean1));
				}
		}
		return vatBeans;
	}
	
	

}
