package com.relyits.rmbs.service;


import java.util.List;

import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;


public interface SalesService {

	public SalesLineItemsModel createSalesOrder(SalesLineItemsModel SalesLineItemsModel);
	
	public SalesOrderModel getSalesOrder(SalesOrderModel salesOrderModel);
	
	public SalesLineItemsModel getSalesOrderLineItemById(SalesLineItemsModel salesLineItemsModel);
	
	public SalesLineItemsModel createSalesOrderLineItems(SalesLineItemsModel SalesLineItemsModel);
	
	public List<SalesLineItemsModel> updateSalesOrder(SalesLineItemsModel SalesLineItemsModel);
	
	public List<SalesLineItemsModel> updateSalesLineItem(ProductInventoryModel oldProductInventoryModel,SalesLineItemsModel SalesLineItemsModel);
	
	public SalesOrderModel confirmSalesOrder(List<SalesLineItemsModel> salesLineItemsModels);
	
    public List<SalesLineItemsModel> getSalesLineItemsModelsBySalesOrderModel(SalesOrderModel SalesOrderModel);

    public List<SalesOrderModel> getSalesOrderList(SalesOrderModel salesOrderModel);
    
    public Long countAll(SalesOrderModel salesOrderModel);
	 
	public List<SalesOrderModel> getByPage(int page, int pageSize,SalesOrderModel salesOrderModel);
    
    public Long countAll(SalesLineItemsModel salesLineItemsModel);
	 
   	public List<SalesLineItemsModel> getByPage(int page, int pageSize,SalesLineItemsModel salesLineItemsModel);

}
