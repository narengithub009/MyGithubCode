package com.relyits.rmbs.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.RevenueDAO;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;

@Repository("revenueDAO")
public class RevenueDAOImpl implements RevenueDAO{

	@Autowired
	private SessionFactory sessionFactory;

	Criteria criteria=null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PurchaseOrderModel> revenuePurchaseOrder(java.util.Date fromDate,java.util.Date toDate,BranchModel branchModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		criteria=session.createCriteria(PurchaseOrderModel.class, "purchaseOrderModel")
				.setProjection(Projections.projectionList()
						.add(Projections.property("id"), "id")
						.add(Projections.property("orderIdByDate"), "orderIdByDate")
						.add(Projections.property("billingDateAndTime"), "billingDateAndTime")
						.add(Projections.property("payAmount"),"payAmount")
						.add(Projections.property("discountPrice"),"discountPrice")
						.add(Projections.property("totalVAT"),"totalVAT")
						.add(Projections.property("amount"),"amount")
						.add(Projections.property("purchaseOrderModel.branchModel"),"branchModel"))

						.setResultTransformer(Transformers.aliasToBean(PurchaseOrderModel.class))
						.add(Restrictions.between("purchaseOrderModel.billingDateAndTime", fromDate, toDate))
						.add(Restrictions.eq("purchaseOrderModel.branchModel.id", branchModel.getId()));

		List purchaseOrderModels= criteria.list();	

		session.close();
		
		return purchaseOrderModels;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SalesOrderModel> revenueSalesOrder(java.util.Date fromDate,java.util.Date toDate,BranchModel branchModel,CategoryModel categoryModel,OutletModel outletModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();		
		if(outletModel.getId()!=null){
			criteria=session.createCriteria(SalesOrderModel.class, "salesOrderModel")
					.setProjection(Projections.projectionList()
							.add(Projections.property("id"), "id")
							.add(Projections.property("orderIdByDate"), "orderIdByDate")
							.add(Projections.property("billingDateAndTime"), "billingDateAndTime")
							.add(Projections.property("payAmount"),"payAmount")
							.add(Projections.property("discountPrice"),"discountPrice")
							.add(Projections.property("totalVAT"),"totalVAT")
							.add(Projections.property("amount"),"amount")
							.add(Projections.property("salesOrderModel.branchModel"),"branchModel")
							.add(Projections.property("salesOrderModel.categoryModel"),"categoryModel"))

							.setResultTransformer(Transformers.aliasToBean(SalesOrderModel.class))
							.add(Restrictions.between("salesOrderModel.billingDateAndTime", fromDate, toDate))
							.add(Restrictions.eq("salesOrderModel.branchModel.id",branchModel.getId()))
							.add(Restrictions.eq("salesOrderModel.categoryModel.id",categoryModel.getId()))
							.add(Restrictions.eq("salesOrderModel.outletModel.id",outletModel.getId()));

		}else{
			criteria=session.createCriteria(SalesOrderModel.class, "salesOrderModel")
					.setProjection(Projections.projectionList()
							.add(Projections.property("id"), "id")
							.add(Projections.property("orderIdByDate"), "orderIdByDate")
							.add(Projections.property("billingDateAndTime"), "billingDateAndTime")
							.add(Projections.property("payAmount"),"payAmount")
							.add(Projections.property("discountPrice"),"discountPrice")
							.add(Projections.property("totalVAT"),"totalVAT")
							.add(Projections.property("amount"),"amount")
							.add(Projections.property("salesOrderModel.branchModel"),"branchModel")
							.add(Projections.property("salesOrderModel.categoryModel"),"categoryModel"))

							.setResultTransformer(Transformers.aliasToBean(SalesOrderModel.class))
							.add(Restrictions.between("salesOrderModel.billingDateAndTime", fromDate, toDate))
							.add(Restrictions.eq("salesOrderModel.branchModel.id",branchModel.getId()))
							.add(Restrictions.eq("salesOrderModel.categoryModel.id",categoryModel.getId()));

		}
		
		List salesOrderModels= criteria.list();	

		session.close();
		
		return salesOrderModels;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SalesReturnOrderModel> revenueSalesReturnOrder(java.util.Date fromDate,java.util.Date toDate,BranchModel branchModel,OutletModel outletModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		if(outletModel.getId()!=null){
		criteria=session.createCriteria(SalesReturnOrderModel.class, "salesReturnOrderModel")
				.setProjection(Projections.projectionList()
						.add(Projections.property("id"), "id")
						.add(Projections.property("orderIdByDate"), "orderIdByDate")
						.add(Projections.property("billingDateAndTime"), "billingDateAndTime")
						.add(Projections.property("payAmount"),"payAmount")
						.add(Projections.property("discountPrice"),"discountPrice")
						.add(Projections.property("totalVAT"),"totalVAT")
						.add(Projections.property("amount"),"amount")
						.add(Projections.property("salesReturnOrderModel.branchModel"),"branchModel"))
						
						.setResultTransformer(Transformers.aliasToBean(SalesReturnOrderModel.class))
						.add(Restrictions.between("salesReturnOrderModel.billingDateAndTime", fromDate, toDate))
						.add(Restrictions.eq("salesReturnOrderModel.branchModel.id", branchModel.getId()))
						.add(Restrictions.eq("salesReturnOrderModel.outletModel.id",outletModel.getId()));

		}else{
			criteria=session.createCriteria(SalesReturnOrderModel.class, "salesReturnOrderModel")
					.setProjection(Projections.projectionList()
							.add(Projections.property("id"), "id")
							.add(Projections.property("orderIdByDate"), "orderIdByDate")
							.add(Projections.property("billingDateAndTime"), "billingDateAndTime")
							.add(Projections.property("payAmount"),"payAmount")
							.add(Projections.property("discountPrice"),"discountPrice")
							.add(Projections.property("totalVAT"),"totalVAT")
							.add(Projections.property("amount"),"amount")
							.add(Projections.property("salesReturnOrderModel.branchModel"),"branchModel"))
							
							.setResultTransformer(Transformers.aliasToBean(SalesReturnOrderModel.class))
							.add(Restrictions.between("salesReturnOrderModel.billingDateAndTime", fromDate, toDate))
							.add(Restrictions.eq("salesReturnOrderModel.branchModel.id", branchModel.getId()));

			
		}

		List salesReturnOrderModels= criteria.list();	

		session.close();
		
		return salesReturnOrderModels;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PurchaseReturnOrderModel> revenuePurchaseReturnOrder(java.util.Date fromDate,java.util.Date toDate,BranchModel branchModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		criteria=session.createCriteria(PurchaseReturnOrderModel.class, "purchaseReturnOrderModel")
				.setProjection(Projections.projectionList()
						.add(Projections.property("id"), "id")
						.add(Projections.property("orderIdByDate"), "orderIdByDate")
						.add(Projections.property("billingDateAndTime"), "billingDateAndTime")
						.add(Projections.property("payAmount"),"payAmount")
						.add(Projections.property("discountPrice"),"discountPrice")
						.add(Projections.property("totalVAT"),"totalVAT")
						.add(Projections.property("amount"),"amount")
						.add(Projections.property("purchaseReturnOrderModel.branchModel"),"branchModel"))

						.setResultTransformer(Transformers.aliasToBean(PurchaseReturnOrderModel.class))
						.add(Restrictions.between("purchaseReturnOrderModel.billingDateAndTime", fromDate, toDate))
						.add(Restrictions.eq("purchaseReturnOrderModel.branchModel.id", branchModel.getId()));

		List purchaseReturnOrderModels= criteria.list();	

		session.close();
		
		return purchaseReturnOrderModels;
	}
	
}
