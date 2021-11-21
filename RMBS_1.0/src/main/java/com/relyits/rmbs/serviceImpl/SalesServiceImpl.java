package com.relyits.rmbs.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relyits.rmbs.dao.SalesDAO;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.service.SalesService;

@Service("SalesService")
public class SalesServiceImpl implements SalesService{

	@Autowired
	private SalesDAO salesDAO;
	
	
	public SalesLineItemsModel createSalesOrder(SalesLineItemsModel salesLineItemsModel){
		return salesDAO.createSalesOrder(salesLineItemsModel);
	}
	
	public SalesOrderModel getSalesOrder(SalesOrderModel salesOrderModel){
		return salesDAO.getSalesOrder(salesOrderModel);
	}
	
	public SalesLineItemsModel getSalesOrderLineItemById(SalesLineItemsModel salesLineItemsModel){
		return salesDAO.getSalesOrderLineItemById(salesLineItemsModel);
	}
	public SalesLineItemsModel createSalesOrderLineItems(SalesLineItemsModel salesLineItemsModel){
		 return salesDAO.createSalesOrderLineItems(salesLineItemsModel);	
	}
	
	public List<SalesLineItemsModel> updateSalesOrder(SalesLineItemsModel SalesLineItemsModel){
		return salesDAO.updateSalesOrder(SalesLineItemsModel);
	}
	
	public List<SalesLineItemsModel> updateSalesLineItem(ProductInventoryModel oldProductInventoryModel,SalesLineItemsModel salesLineItemsModel){
		return salesDAO.updateSalesLineItem(oldProductInventoryModel,salesLineItemsModel);
	}
	
	public SalesOrderModel confirmSalesOrder(List<SalesLineItemsModel> salesLineItemsModels){
		return salesDAO.confirmSalesOrder(salesLineItemsModels);
	}
	
	@Override
	public List<SalesLineItemsModel> getSalesLineItemsModelsBySalesOrderModel(SalesOrderModel salesOrderModel) {
		return salesDAO.getSalesLineItemsModelsBySalesOrderModel(salesOrderModel);
	}

	@Override
	public List<SalesOrderModel> getSalesOrderList(SalesOrderModel salesOrderModel) {
		return salesDAO.getSalesOrderList(salesOrderModel);
	}

	@Override
	public Long countAll(SalesOrderModel salesOrderModel) {
		return salesDAO.countAll(salesOrderModel);
	}

	@Override
	public List<SalesOrderModel> getByPage(int page, int pageSize,SalesOrderModel salesOrderModel) {
		int firstResult = page * pageSize - pageSize;
		return salesDAO.listByPage(firstResult, pageSize, salesOrderModel);
	}

	@Override
	public Long countAll(SalesLineItemsModel salesLineItemsModel) {
		return salesDAO.countAll(salesLineItemsModel);
	}

	@Override
	public List<SalesLineItemsModel> getByPage(int page, int pageSize,SalesLineItemsModel salesLineItemsModel) {
		int firstResult = page * pageSize - pageSize;
		return salesDAO.listByPage(firstResult, pageSize, salesLineItemsModel);
	}


}
