package com.relyits.rmbs.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relyits.rmbs.dao.RevenueDAO;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;
import com.relyits.rmbs.service.RevenueService;

@Service("revenueService")
public class RevenueServiceImpl implements RevenueService{


	@Autowired
	private RevenueDAO revenueDAO;

	@Override
	public List<PurchaseOrderModel> revenuePurchaseOrder(Date fromDate, Date toDate,BranchModel branchModel) {
		return revenueDAO.revenuePurchaseOrder(fromDate, toDate, branchModel);
	}

	@Override
	public List<SalesOrderModel> revenueSalesOrder(Date fromDate, Date toDate,BranchModel branchModel,CategoryModel categoryModel,OutletModel outletModel) {
		return revenueDAO.revenueSalesOrder(fromDate, toDate, branchModel,categoryModel,outletModel);
	}

	@Override
	public List<SalesReturnOrderModel> revenueSalesReturnOrder(Date fromDate,	Date toDate,BranchModel branchModel,OutletModel outletModel) {
		return revenueDAO.revenueSalesReturnOrder(fromDate, toDate, branchModel,outletModel);
	}

	@Override
	public List<PurchaseReturnOrderModel> revenuePurchaseReturnOrder(Date fromDate, Date toDate, BranchModel branchModel) {
		return revenueDAO.revenuePurchaseReturnOrder(fromDate, toDate, branchModel);
	}

	
}
