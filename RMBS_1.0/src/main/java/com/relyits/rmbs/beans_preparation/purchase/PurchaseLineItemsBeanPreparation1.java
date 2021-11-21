package com.relyits.rmbs.beans_preparation.purchase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.relyits.rmbs.beans.purchase.PurchaseLineItemsBean1;
import com.relyits.rmbs.beans.resources.PurchaseFormBean;
import com.relyits.rmbs.beans_preparation.product.ProductInventoryBeanPreparation;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.utilities.ConvertDoubleToBigDecimal;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;



public class PurchaseLineItemsBeanPreparation1 {

	private static PurchaseLineItemsBean1 purchaseLineItemsBean1=null;
	private static List<PurchaseLineItemsBean1> purchaseLineItemsBeans1=null;
   
	public static PurchaseLineItemsBean1 preparePurchaseLineItemsBeanFromPurchaseFormBean1(PurchaseFormBean purchaseFormBean) throws ParseException{
		purchaseLineItemsBean1= new PurchaseLineItemsBean1();
		purchaseLineItemsBean1.setId(purchaseFormBean.getpOLId());
		purchaseLineItemsBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getlAmt()));
		purchaseLineItemsBean1.setDiscount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getDisPrcnt()));
		purchaseLineItemsBean1.setExpiryDate(DateAndTimeUtilities.parseStringDateToSqlDate(purchaseFormBean.getExpDate()));
		purchaseLineItemsBean1.setNetPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getnPr()));
		purchaseLineItemsBean1.setPayAmount(null);
		purchaseLineItemsBean1.setPreparationCharges(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getPprChrgs()));
		purchaseLineItemsBean1.setQuantity(purchaseFormBean.getQntty());
		purchaseLineItemsBean1.setFreeQantity(purchaseFormBean.getfQntty());		
		purchaseLineItemsBean1.setUnitPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getuPr()));
		purchaseLineItemsBean1.setVat(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getVat()));
		purchaseLineItemsBean1.setProductInventoryBean(ProductInventoryBeanPreparation.prepareProductInventoryBeanFromPurchaseFormBean(purchaseFormBean));
		purchaseLineItemsBean1.setPurchaseOrderBean1(PurchaseOrderBeanPreparation1.preparePurchaseOrderBeanFromPurchaseFormbean1(purchaseFormBean));
		return purchaseLineItemsBean1;
		
	}
	public static List<PurchaseLineItemsBean1> preparePurchaseLineItemsBeansListFromPurchaseFormBeansList1(List<PurchaseFormBean> purchaseFormBeans) throws ParseException{
		purchaseLineItemsBeans1= new ArrayList<PurchaseLineItemsBean1>();
	    for(PurchaseFormBean purchaseFormBean: purchaseFormBeans){
	    	purchaseLineItemsBeans1.add(preparePurchaseLineItemsBeanFromPurchaseFormBean1(purchaseFormBean));
	    }
	    return purchaseLineItemsBeans1;
		
	}
	
	public static PurchaseLineItemsBean1 preparePurchaseLineItemsBean1(PurchaseLineItemsModel purchaseLineItemsModel){
		purchaseLineItemsBean1 = new PurchaseLineItemsBean1();
				  
		  purchaseLineItemsBean1.setId(purchaseLineItemsModel.getId());
		  purchaseLineItemsBean1.setQuantity(purchaseLineItemsModel.getQuantity());
		  purchaseLineItemsBean1.setFreeQantity(purchaseLineItemsModel.getFreeQuantity());
		  purchaseLineItemsBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseLineItemsModel.getAmount()));
		  purchaseLineItemsBean1.setUnitPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseLineItemsModel.getUnitPrice()));
		  purchaseLineItemsBean1.setDiscount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseLineItemsModel.getDiscount()));
		  purchaseLineItemsBean1.setNetPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseLineItemsModel.getNetPrice()));
		  purchaseLineItemsBean1.setVat(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseLineItemsModel.getVat()));
		  purchaseLineItemsBean1.setPreparationCharges(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseLineItemsModel.getPreparationCharges()));
		  purchaseLineItemsBean1.setExpiryDate(purchaseLineItemsModel.getExpiryDate());
		  purchaseLineItemsBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseLineItemsModel.getPayAmount()));
		  purchaseLineItemsBean1.setProductInventoryBean(ProductInventoryBeanPreparation.prepareProductInventoryBean(purchaseLineItemsModel.getProductInventoryModel()));
		  purchaseLineItemsBean1.setPurchaseOrderBean1(PurchaseOrderBeanPreparation1.preparePurchaseOrderBean1(purchaseLineItemsModel.getPurchaseOrderModel()));
		  purchaseLineItemsBean1.setDeliverableQuantity(purchaseLineItemsModel.getDeliverableQuantity()); 
		  	
		
		
		return purchaseLineItemsBean1;
		
		
	}
	
	public static List<PurchaseLineItemsBean1> prepareListOfPurchaseLineItemsBean1(List<PurchaseLineItemsModel> purchaseLineItemsModel){
		List<PurchaseLineItemsBean1> purchaseLineItemBeans1 = null;
		if(purchaseLineItemsModel != null && !purchaseLineItemsModel.isEmpty()){
			purchaseLineItemBeans1 = new ArrayList<PurchaseLineItemsBean1>();				
			for(PurchaseLineItemsModel purchaseLineItemsModel1 : purchaseLineItemsModel){
				purchaseLineItemBeans1.add(preparePurchaseLineItemsBean1(purchaseLineItemsModel1));
				}
		}


return purchaseLineItemBeans1;


}
}
