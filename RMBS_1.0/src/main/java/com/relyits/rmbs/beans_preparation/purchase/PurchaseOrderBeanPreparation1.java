package com.relyits.rmbs.beans_preparation.purchase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.relyits.rmbs.beans.purchase.PurchaseOrderBean1;
import com.relyits.rmbs.beans.resources.PurchaseFormBean;
import com.relyits.rmbs.beans_preparation.product.ProductInventoryBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.AgencyBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.utilities.ConvertDoubleToBigDecimal;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;

public class PurchaseOrderBeanPreparation1 {

	private static PurchaseOrderBean1 purchaseOrderBean1=null;

	public static PurchaseOrderBean1 preparePurchaseOrderBeanFromPurchaseFormbean1(PurchaseFormBean purchaseFormBean) throws ParseException{
		purchaseOrderBean1 = new PurchaseOrderBean1();
		purchaseOrderBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getOrdAmt()));
		purchaseOrderBean1.setBillingDateAndTime(DateAndTimeUtilities.parseStringDateToSqlDate(purchaseFormBean.getBlDtAndTm()));
		purchaseOrderBean1.setDeliveryDate(DateAndTimeUtilities.getCurrentDateTimeInSqlFormat());
		purchaseOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getTotDis()));
		purchaseOrderBean1.setId(purchaseFormBean.getpOId());
		purchaseOrderBean1.setInvoiceNo(purchaseFormBean.getInvNo());
		purchaseOrderBean1.setOrderIdByDate(purchaseFormBean.getInvNo());
		purchaseOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getOrdAmt()));
		purchaseOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getTotVat()));
		purchaseOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBeanFromPurchaseFormBean(purchaseFormBean));
		purchaseOrderBean1.setProductInventoryBean(ProductInventoryBeanPreparation.prepareProductInventoryBeanFromPurchaseFormBean(purchaseFormBean));
		return purchaseOrderBean1;

	}
	
	public static PurchaseOrderBean1 preparePurchaseOrderBean1(PurchaseOrderModel purchaseOrderModel){
		
				purchaseOrderBean1 = new PurchaseOrderBean1();			
				
				purchaseOrderBean1.setId(purchaseOrderModel.getId());
				purchaseOrderBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getAmount()));
				purchaseOrderBean1.setOverallDiscount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getOverallDiscount()));
				purchaseOrderBean1.setBillingDateAndTime(purchaseOrderModel.getBillingDateAndTime());
				purchaseOrderBean1.setDeliveryDate(purchaseOrderModel.getDeliveryDate());
				purchaseOrderBean1.setOrderIdByDate(purchaseOrderModel.getOrderIdbyDate());
				purchaseOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getDiscountPrice()));
				purchaseOrderBean1.setInvoiceNo(purchaseOrderModel.getInvoiceNo());
				purchaseOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getPayAmount()));
				purchaseOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getTotalVAT()));
				purchaseOrderBean1.setAgencyBean(AgencyBeanPreparation.prepareAgencyBean(purchaseOrderModel.getAgencyModel()));
				purchaseOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(purchaseOrderModel.getBranchModel()));
								
		return purchaseOrderBean1;


}
	

public static List<PurchaseOrderBean1> prepareListOfPurchaseOrderBean1(List<PurchaseOrderModel> purchases){
			List<PurchaseOrderBean1> purchaseOrderBeans1 = null;
			if(purchases != null && !purchases.isEmpty()){
				purchaseOrderBeans1 = new ArrayList<PurchaseOrderBean1>();				
				for(PurchaseOrderModel purchaseOrderModel : purchases){
					purchaseOrderBeans1.add(preparePurchaseOrderBean1(purchaseOrderModel));
					}
			}
	
	
	return purchaseOrderBeans1;
	
	
}
public static PurchaseOrderBean1 preparePurchaseOrderBeanForRevenueAccounts1(PurchaseOrderModel purchaseOrderModel){
	
	purchaseOrderBean1 = new PurchaseOrderBean1();			
	
	purchaseOrderBean1.setId(purchaseOrderModel.getId());
	purchaseOrderBean1.setAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getAmount()));
	purchaseOrderBean1.setBillingDateAndTime(purchaseOrderModel.getBillingDateAndTime());
	purchaseOrderBean1.setOrderIdByDate(purchaseOrderModel.getOrderIdbyDate());
	purchaseOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getTotalVAT()));
	purchaseOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getDiscountPrice()));
	purchaseOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getPayAmount()));
	purchaseOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(purchaseOrderModel.getBranchModel()));
					
return purchaseOrderBean1;


}

public static List<PurchaseOrderBean1> prepareListOfPurchaseOrderBeanForRevenueAccounts1(List<PurchaseOrderModel> purchases){
	List<PurchaseOrderBean1> purchaseOrderBeans1 = null;
	if(purchases != null && !purchases.isEmpty()){
		purchaseOrderBeans1 = new ArrayList<PurchaseOrderBean1>();				
		for(PurchaseOrderModel purchaseOrderModel : purchases){
			purchaseOrderBeans1.add(preparePurchaseOrderBeanForRevenueAccounts1(purchaseOrderModel));
			}
	}


return purchaseOrderBeans1;


}

public static PurchaseOrderBean1 preparePurchaseOrderBeanForReports1(PurchaseOrderModel purchaseOrderModel){
	
	purchaseOrderBean1 = new PurchaseOrderBean1();			
	
	purchaseOrderBean1.setId(purchaseOrderModel.getId());
	purchaseOrderBean1.setBillingDateAndTime(purchaseOrderModel.getBillingDateAndTime());
	purchaseOrderBean1.setTotalVAT(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getTotalVAT()));
	purchaseOrderBean1.setOrderIdByDate(purchaseOrderModel.getOrderIdbyDate());
	purchaseOrderBean1.setInvoiceNo(purchaseOrderModel.getInvoiceNo());
	purchaseOrderBean1.setDiscountPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getDiscountPrice()));
	purchaseOrderBean1.setPayAmount(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseOrderModel.getPayAmount()));
	purchaseOrderBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(purchaseOrderModel.getBranchModel()));
					
return purchaseOrderBean1;


}

public static List<PurchaseOrderBean1> prepareListOfPurchaseOrderBeanForReports1(List<PurchaseOrderModel> purchases){
	List<PurchaseOrderBean1> purchaseOrderBeans1 = null;
	if(purchases != null && !purchases.isEmpty()){
		purchaseOrderBeans1 = new ArrayList<PurchaseOrderBean1>();				
		for(PurchaseOrderModel purchaseOrderModel : purchases){
			purchaseOrderBeans1.add(preparePurchaseOrderBeanForReports1(purchaseOrderModel));
			}
	}


return purchaseOrderBeans1;


}
}