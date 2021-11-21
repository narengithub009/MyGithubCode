package com.relyits.rmbs.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relyits.rmbs.beans.purchase.PurchaseLineItemsBean;
import com.relyits.rmbs.dao.ReportsDAO;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;
import com.relyits.rmbs.service.ReportsService;

@Service("reportsService")
public class ReportsServiceImpl implements ReportsService{


	@Autowired
	private ReportsDAO reportsDAO;

	@Override
	public JRDataSource retrieveAllExistedProucts() {

		return reportsDAO.retrieveAllExistedProucts();
	}

	public JRDataSource productStockListBybranch(ProductInventoryModel productInventoryModel) {

		return reportsDAO.productStockListBybranch(productInventoryModel);
	}


	@Override
	public List<PurchaseOrderModel> purchaseOrderReports(Date fromDate, Date toDate,BranchModel branchModel) {
		return reportsDAO.purchaseOrderReports(fromDate, toDate,branchModel);
	}



	@Override
	public JRDataSource purchaseOrderListReports(
			PurchaseOrderModel purchaseOrderModel) {
		return reportsDAO.purchaseOrderListReports(purchaseOrderModel);
	}

	@Override
	public List<PurchaseLineItemsBean> purchaseOrderLineitemsReports(
			PurchaseOrderModel purchaseOrderModel) {
		return reportsDAO.purchaseOrderLineitemsReports(purchaseOrderModel);
	}

	@Override
	public List<PurchaseReturnOrderModel> purchaseReturnOrderReports(
			Date fromDate, Date toDate, BranchModel branchModel) {
		return reportsDAO.purchaseReturnOrderReports(fromDate, toDate, branchModel);
	}

	@Override
	public List<SalesOrderModel> getSalesOrderReportsList(Date fromDate,
			Date toDate, SalesOrderModel salesOrderModel) {
		return reportsDAO.getSalesOrderReportsList( fromDate,toDate,  salesOrderModel);
	}

	@Override
	public List<SalesReturnOrderModel> getSalesReturnOrderList(Date fromDate,
			Date toDate, SalesReturnOrderModel salesReturnOrderModel) {
		return reportsDAO.getSalesReturnOrderList(fromDate, toDate, salesReturnOrderModel);
	}

	@Override
	public ArrayList<SalesLineItemsModel> getProductSales(Date fromDate,
			Date toDate, SalesLineItemsModel salesLineItemsModel) {
		return reportsDAO.getProductSales( fromDate,toDate,  salesLineItemsModel);
	}
}
