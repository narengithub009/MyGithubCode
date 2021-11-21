package com.relyits.rmbs.service;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import com.relyits.rmbs.beans.purchase.PurchaseLineItemsBean;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;


public interface ReportsService {

	// ********product reports service*******
	
	public JRDataSource retrieveAllExistedProucts();	
	
	public JRDataSource productStockListBybranch(ProductInventoryModel productInventoryModel);

	// ********purchase reports service*******

	public List<PurchaseOrderModel> purchaseOrderReports(Date fromDate,Date toDate,BranchModel branchModel);
	
	public List<PurchaseReturnOrderModel> purchaseReturnOrderReports(Date fromDate,Date toDate,BranchModel branchModel);

	
	public JRDataSource purchaseOrderListReports(PurchaseOrderModel purchaseOrderModel);

	public List<PurchaseLineItemsBean> purchaseOrderLineitemsReports(PurchaseOrderModel purchaseOrderModel);

	public List<SalesOrderModel> getSalesOrderReportsList(Date fromDate,Date toDate, SalesOrderModel salesOrderModel);

	public List<SalesReturnOrderModel> getSalesReturnOrderList(Date fromDate,
			Date toDate, SalesReturnOrderModel salesReturnOrderModel);

	public ArrayList<SalesLineItemsModel> getProductSales(Date fromDate,
			Date toDate, SalesLineItemsModel salesLineItemsModel);

}
