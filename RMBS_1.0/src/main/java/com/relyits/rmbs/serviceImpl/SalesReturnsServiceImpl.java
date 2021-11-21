package com.relyits.rmbs.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relyits.rmbs.dao.SalesReturnsDAO;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnLineItemsModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;
import com.relyits.rmbs.service.SalesReturnsService;

@Service("SalesReturnService")
public class SalesReturnsServiceImpl implements SalesReturnsService{

	@Autowired
	private SalesReturnsDAO salesRetunsDAO;
	
	
	public List<SalesReturnLineItemsModel> createSalesReturnOrder(List<SalesReturnLineItemsModel> salesReturnLineItemsModels,List<SalesLineItemsModel> salesLineItemsModels){
		return salesRetunsDAO.createSalesReturnOrder(salesReturnLineItemsModels,salesLineItemsModels);
	}


	public List<SalesReturnLineItemsModel> getSalesReturnLineItemsModelsBySalesReturnOrderModel(SalesReturnOrderModel salesReturnOrderModel) {
		return salesRetunsDAO.getSalesReturnLineItemsModelsBySalesReturnOrderModel(salesReturnOrderModel);
	}


	@Override
	public List<SalesReturnOrderModel> getSalesReturnOrderList(	SalesReturnOrderModel salesReturnOrderModel) {
		return salesRetunsDAO.getSalesReturnOrderList(salesReturnOrderModel);
	}


	@Override
	public List<SalesLineItemsModel> getSalesLineItemsModelsByOrderIdByDate(SalesOrderModel salesOrderModel) {
		return salesRetunsDAO.getSalesLineItemsModelsByOrderIdByDate(salesOrderModel);
	}


	@Override
	public Long countAll(SalesReturnOrderModel salesReturnOrderModel) {
		return salesRetunsDAO.countAll(salesReturnOrderModel);
	}


	@Override
	public List<SalesReturnOrderModel> getByPage(int page, int pageSize,SalesReturnOrderModel salesReturnOrderModel) {
		int firstResult = page * pageSize - pageSize;
		return salesRetunsDAO.listByPage(firstResult, pageSize, salesReturnOrderModel);
	}


	@Override
	public Long countAll(SalesReturnLineItemsModel salesReturnLineItemsModel) {
		return salesRetunsDAO.countAll(salesReturnLineItemsModel);
	}


	@Override
	public List<SalesReturnLineItemsModel> getByPage(int page, int pageSize,SalesReturnLineItemsModel salesReturnLineItemsModel) {
	    int firstResult = page * pageSize - pageSize;
		return salesRetunsDAO.listByPage(firstResult, pageSize, salesReturnLineItemsModel);
	}
	
}
