package com.relyits.rmbs.dao;

import java.util.List;

import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;


public interface SalesDAO {
	
	public SalesLineItemsModel createSalesOrder(SalesLineItemsModel salesLineItemsModel);
	
	public SalesOrderModel getSalesOrder(SalesOrderModel salesOrderModel);
	
	public SalesLineItemsModel getSalesOrderLineItemById(SalesLineItemsModel salesLineItemsModel);
	
	public SalesLineItemsModel createSalesOrderLineItems(SalesLineItemsModel salesLineItemsModel);
	
	public List<SalesLineItemsModel> updateSalesOrder(SalesLineItemsModel salesLineItemsModel);
	
	public List<SalesLineItemsModel> updateSalesLineItem(ProductInventoryModel oldProductInventoryModel,SalesLineItemsModel salesLineItemsModel);
	
	public SalesOrderModel confirmSalesOrder(List<SalesLineItemsModel> salesLineItemsModels);
		
	public List<SalesLineItemsModel> getSalesLineItemsModelsBySalesOrderModel(SalesOrderModel SalesOrderModel) ;

	public List<SalesOrderModel> getSalesOrderList(SalesOrderModel salesOrderModel);
	
	public Long countAll(SalesOrderModel salesOrderModel);
	 
	public List<SalesOrderModel> listByPage(int firstResult, int pageSize,SalesOrderModel salesOrderModel);
	
	public Long countAll(SalesLineItemsModel salesLineItemsModel);
	 
	public List<SalesLineItemsModel> listByPage(int firstResult, int pageSize,SalesLineItemsModel salesLineItemsModel);
}
