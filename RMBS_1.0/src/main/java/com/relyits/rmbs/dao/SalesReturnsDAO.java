package com.relyits.rmbs.dao;

import java.util.List;

import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnLineItemsModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;


public interface SalesReturnsDAO {
	
	public List<SalesReturnLineItemsModel> createSalesReturnOrder(List<SalesReturnLineItemsModel> salesReturnLineItemsModels,List<SalesLineItemsModel> salesLineItemsModel);
	
	public List<SalesReturnLineItemsModel> getSalesReturnLineItemsModelsBySalesReturnOrderModel(SalesReturnOrderModel salesReturnOrderModel1);
	
	public List<SalesReturnOrderModel> getSalesReturnOrderList(SalesReturnOrderModel salesReturnOrderModel);
	
	public List<SalesLineItemsModel> getSalesLineItemsModelsByOrderIdByDate(SalesOrderModel salesOrderModel);
	
	public Long countAll(SalesReturnOrderModel salesReturnOrderModel);
	 
	public List<SalesReturnOrderModel> listByPage(int firstResult, int pageSize,SalesReturnOrderModel salesReturnOrderModel);
	
	public Long countAll(SalesReturnLineItemsModel salesReturnLineItemsModel);
	 
	public List<SalesReturnLineItemsModel> listByPage(int firstResult, int pageSize,SalesReturnLineItemsModel salesReturnLineItemsModel);
}
