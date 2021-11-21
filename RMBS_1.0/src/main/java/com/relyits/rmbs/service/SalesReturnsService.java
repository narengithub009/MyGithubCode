package com.relyits.rmbs.service;

import java.util.List;

import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnLineItemsModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;


public interface SalesReturnsService {

	public List<SalesReturnLineItemsModel> createSalesReturnOrder(List<SalesReturnLineItemsModel> salesReturnLineItemsModels,List<SalesLineItemsModel> salesLineItemsModels);

	public List<SalesReturnLineItemsModel> getSalesReturnLineItemsModelsBySalesReturnOrderModel(SalesReturnOrderModel salesReturnOrderModel);
	
	public List<SalesReturnOrderModel> getSalesReturnOrderList(SalesReturnOrderModel salesReturnOrderModel);

	public List<SalesLineItemsModel> getSalesLineItemsModelsByOrderIdByDate(SalesOrderModel salesOrderModel);
 
	public Long countAll(SalesReturnOrderModel salesReturnOrderModel);
	 
	public List<SalesReturnOrderModel> getByPage(int page, int pageSize,SalesReturnOrderModel salesReturnOrderModel);
    
	public Long countAll(SalesReturnLineItemsModel salesReturnLineItemsModel);
	 
	public List<SalesReturnLineItemsModel> getByPage(int page, int pageSize,SalesReturnLineItemsModel salesReturnLineItemsModel);
    

}
