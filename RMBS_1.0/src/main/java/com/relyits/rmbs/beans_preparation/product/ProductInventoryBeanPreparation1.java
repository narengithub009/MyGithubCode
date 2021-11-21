package com.relyits.rmbs.beans_preparation.product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.relyits.rmbs.beans.product.ProductInventoryBean1;
import com.relyits.rmbs.beans.resources.PurchaseFormBean;
import com.relyits.rmbs.beans_preparation.registration.AgencyBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.beans_preparation.resources.AddressBeanPreparation;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.utilities.ConvertDoubleToBigDecimal;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;



public class ProductInventoryBeanPreparation1 {

	private static ProductInventoryBean1 productInventoryBean1=null;



	public static ProductInventoryBean1 prepareProductInventoryBean1(ProductInventoryModel productInventoryModel){
		productInventoryBean1=new ProductInventoryBean1();
		productInventoryBean1.setBatchNo(productInventoryModel.getBatchNo());
		productInventoryBean1.setId(productInventoryModel.getId());
		productInventoryBean1.setPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(productInventoryModel.getPrice()));
		productInventoryBean1.setDlPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(productInventoryModel.getDlPrice()));
		productInventoryBean1.setQuantity(productInventoryModel.getQuantity());
		productInventoryBean1.setVat(ConvertDoubleToBigDecimal.twoDecimalPlaces(productInventoryModel.getVat()));
		productInventoryBean1.setPwVat(ConvertDoubleToBigDecimal.twoDecimalPlaces(productInventoryModel.getPwVat()));
		productInventoryBean1.setVatPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(productInventoryModel.getVatPrice()));
		productInventoryBean1.setExpiryDate(""+productInventoryModel.getExpiryDate()+"");
		productInventoryBean1.setProductBean(ProductBeanPreparation.prepareProductBean(productInventoryModel.getProductModel()));
		productInventoryBean1.setCreatedBy(productInventoryModel.getCreatedBy());
		productInventoryBean1.setBranchBean(BranchBeanPreparation.prepareBranchBean(productInventoryModel.getBranchModel()));
		productInventoryBean1.setAgencyBean(AgencyBeanPreparation.prepareAgencyBean(productInventoryModel.getAgencyModel()));
		productInventoryBean1.setCreatorRoleBean(AddressBeanPreparation.prepareRoleBean(productInventoryModel.getCreatorRoleModel()));
		productInventoryBean1.setCreatedDate(productInventoryModel.getCreatedDate());
		productInventoryBean1.setUpdatedDate(productInventoryModel.getUpdatedDate());




		return productInventoryBean1;
	}



	public static List<ProductInventoryBean1> prepareListofProductInventoryBeanromExpairyProductInventoryModel1(List<ProductInventoryModel> products) throws ParseException{
		List<ProductInventoryBean1> productInventoryBeans1 = null;
		if(products != null && !products.isEmpty()){		
			productInventoryBeans1 = new ArrayList<ProductInventoryBean1>();			
			for(ProductInventoryModel productInventoryModel : products){	
				productInventoryBean1=prepareProductInventoryBean1(productInventoryModel);
				productInventoryBean1.setExpiryDays(DateAndTimeUtilities.getDaysDifference(productInventoryModel.getExpiryDate()));
				productInventoryBeans1.add(productInventoryBean1);	


			}
		}
		return productInventoryBeans1;
	}
	public static List<ProductInventoryBean1> prepareListofProductInventoryBean1(List<ProductInventoryModel> products){
		List<ProductInventoryBean1> productInventoryBeans1 = null;
		if(products != null && !products.isEmpty()){		
			productInventoryBeans1 = new ArrayList<ProductInventoryBean1>();			
			for(ProductInventoryModel productInventoryModel : products){	
				productInventoryBeans1.add(prepareProductInventoryBean1(productInventoryModel));
			}
		}
		return productInventoryBeans1;
	}
	
	
	public static ProductInventoryBean1 prepareProductInventoryBeanFromPurchaseFormBean1(PurchaseFormBean purchaseFormBean) throws ParseException{
		productInventoryBean1=new ProductInventoryBean1();
		productInventoryBean1.setBatchNo(purchaseFormBean.getBatNo());
		productInventoryBean1.setId(purchaseFormBean.getPiId());
		productInventoryBean1.setPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getPr()));
		productInventoryBean1.setDlPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getuPr()));
		productInventoryBean1.setQuantity(purchaseFormBean.getQntty()+purchaseFormBean.getfQntty());
		productInventoryBean1.setVat(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getVat()));
		productInventoryBean1.setPwVat(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getpWVat()));
		productInventoryBean1.setVatPrice(ConvertDoubleToBigDecimal.twoDecimalPlaces(purchaseFormBean.getVatPr()));
		productInventoryBean1.setExpiryDate(purchaseFormBean.getExpDate());
		productInventoryBean1.setCreatedBy(purchaseFormBean.getcById());
		productInventoryBean1.setCreatedDate(DateAndTimeUtilities.getCurrentDateTimeInSqlFormat());
		productInventoryBean1.setUpdatedDate(DateAndTimeUtilities.getCurrentDateTimeInSqlFormat());
		productInventoryBean1.setProductBean(ProductBeanPreparation.prepareProductBeanFormPurchaseFormBean(purchaseFormBean));
		productInventoryBean1.setBranchBean(BranchBeanPreparation.prepareBranchBeanFromPurchaseFormBean(purchaseFormBean));
		productInventoryBean1.setAgencyBean(AgencyBeanPreparation.prepareAgencyBeanFromPurchaseFormBean(purchaseFormBean));
		productInventoryBean1.setCreatorRoleBean(AddressBeanPreparation.prepareCreatorRoleBeanFromPurchaseFormBean(purchaseFormBean));
		return productInventoryBean1;
	}
}
