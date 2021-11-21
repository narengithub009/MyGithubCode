package com.relyits.rmbs.beans_preparation.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.relyits.rmbs.beans.product.ProductInventoryBean;
import com.relyits.rmbs.beans.product.SupplierBean;

public class SupplierBeanPreparation {

	private static List<SupplierBean> supplierBeans=null;
	private static SupplierBean supplierBean=null;
	private static List<ProductInventoryBean> inventoryBeans1=null;
	private static Map<Integer,SupplierBean> productMap=null;
	private static Integer products[]=null;
	
	public static List<SupplierBean> prepareListOfSupplierBeansformProductInventoryBeans(List<ProductInventoryBean> productInventoryBeans){
	
		supplierBeans=new ArrayList<>();
		
		if(productInventoryBeans!=null){
			productMap=new HashMap<Integer,SupplierBean>();
			int childCount=0;
			for(ProductInventoryBean inventoryBean1:productInventoryBeans){
				if(productInventoryBeans.size()==childCount){
					break;
				}
				if(productMap.containsKey(inventoryBean1.getAgencyBean().getId())){
					continue;
				}
				supplierBean=new SupplierBean();
				supplierBean.setId(inventoryBean1.getAgencyBean().getId());
				supplierBean.setSupplierName(inventoryBean1.getAgencyBean().getAgencyName());
				supplierBean.setAddressBean(inventoryBean1.getAgencyBean().getResourceBean().getAddressBean());
				inventoryBeans1=new ArrayList<ProductInventoryBean>();
				for(ProductInventoryBean inventoryBean2:productInventoryBeans){
					if(supplierBean.getId().equals(inventoryBean2.getAgencyBean().getId())){
						if(inventoryBeans1.size()>0){
							products=new Integer[productInventoryBeans.size()];
							for(int i=0;i<inventoryBeans1.size();i++){
								products[i]=inventoryBeans1.get(i).getProductBean().getId();
							}
							boolean flag=true;
							for(Integer product:products){
								if(product==null)break;
								if(product==inventoryBean2.getProductBean().getId()){
									flag=false;
									break;
								}
							}
							if(flag)
								inventoryBeans1.add(inventoryBean2);
						}else{
							inventoryBeans1.add(inventoryBean2);
						}
						childCount++;
					}
				}
				supplierBean.setProductInventoryBeans(inventoryBeans1);
				productMap.put(supplierBean.getId(),supplierBean); 
				supplierBeans.add(supplierBean);
			}			
		}
		
		
		
		return supplierBeans;
	}
}
