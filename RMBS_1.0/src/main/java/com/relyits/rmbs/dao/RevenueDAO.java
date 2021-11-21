package com.relyits.rmbs.dao;

import java.util.Date;
import java.util.List;

import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;

public interface RevenueDAO {
	
	public List<PurchaseOrderModel> revenuePurchaseOrder(Date fromDate, Date toDate,BranchModel branchModel);

	public List<SalesOrderModel> revenueSalesOrder(java.util.Date fromDate,Date toDate,BranchModel branchModel,CategoryModel categoryModel,OutletModel outletModel);

	public List<SalesReturnOrderModel> revenueSalesReturnOrder(java.util.Date fromDate,java.util.Date toDate,BranchModel branchModel,OutletModel outletModel);

	public List<PurchaseReturnOrderModel> revenuePurchaseReturnOrder(java.util.Date fromDate,java.util.Date toDate,BranchModel branchModel);
}
