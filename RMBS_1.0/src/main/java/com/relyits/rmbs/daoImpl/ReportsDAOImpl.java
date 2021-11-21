package com.relyits.rmbs.daoImpl;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.beans.purchase.PurchaseLineItemsBean;
import com.relyits.rmbs.beans.purchase.PurchaseOrderBean;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseLineItemsBeanPreparation;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseOrderBeanPreparation;
import com.relyits.rmbs.dao.ReportsDAO;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.product.ProductModel;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;

@Repository("reportsDAO")
public class ReportsDAOImpl implements ReportsDAO{

	@Autowired
	private SessionFactory sessionFactory;

	private List<PurchaseOrderBean> purchaseOrderBeans=null;

	private ArrayList<PurchaseOrderModel> purchaseOrderModels = null;	
	private Criteria criteria=null;

	private List<PurchaseReturnOrderModel>  purchaseReturnOrderModels=null;
	@SuppressWarnings("unchecked")
	@Override
	public JRDataSource retrieveAllExistedProucts() {

		Session session=sessionFactory.openSession();

		Criteria criteria=session.createCriteria(ProductModel.class)
				.setProjection(Projections.projectionList()
						.add(Projections.property("name"), "name")
						.add(Projections.property("code"), "code")
						.add(Projections.property("schDrug"), "schDrug")
						.add(Projections.property("mFCompanay"), "mFCompanay")
						.add(Projections.property("createdDate"), "createdDate"))
						.setResultTransformer(Transformers.aliasToBean(ProductModel.class));
		
		List<ProductModel> list =criteria.list();
		JRDataSource ds = new JRBeanCollectionDataSource(list,false);
		session.close();
		return ds;
	}
	
	@Override
	public JRDataSource productStockListBybranch(ProductInventoryModel productInventoryModel){
		Session session=sessionFactory.openSession();
		if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
		criteria=session.createCriteria(ProductInventoryModel.class, "productInventory")
				.createAlias("productInventory.productModel", "product")
				.createAlias("productInventory.branchModel", "branchModel")
				.createAlias("branchModel.organizationModel", "org")
		.setProjection(Projections.projectionList()
					.add(Projections.property("quantity"), "quantity")
					.add(Projections.property("batchNo"), "batchNo")
					.add(Projections.property("price"),"price")
					.add(Projections.property("expiryDate"),"expiryDate")
					.add(Projections.property("productInventory.productModel"),"productModel")
					.add(Projections.property("productInventory.branchModel"),"branchModel"))

				    .setResultTransformer(Transformers.aliasToBean(ProductInventoryModel.class))
		.add(Restrictions.eq("org.id", productInventoryModel.getBranchModel().getOrganizationModel().getId()));
		}else{
			criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
				.createAlias("productInventory.branchModel", "branchModel")
				.setProjection(Projections.projectionList()
						.add(Projections.property("quantity"), "quantity")
						.add(Projections.property("batchNo"), "batchNo")
						.add(Projections.property("price"),"price")
						.add(Projections.property("expiryDate"),"expiryDate")
						.add(Projections.property("productInventory.productModel"),"productModel")
						.add(Projections.property("productInventory.branchModel"),"branchModel"))

					    .setResultTransformer(Transformers.aliasToBean(ProductInventoryModel.class))
					    .add(Restrictions.eq("branchModel.id", productInventoryModel.getBranchModel().getId()));
		}
		@SuppressWarnings("unchecked")
		List<ProductInventoryModel> list=criteria.list();

		session.close();
		JRDataSource ds=new JRBeanCollectionDataSource(list,false);
		return ds;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseOrderModel> purchaseOrderReports(java.util.Date fromDate,
			java.util.Date toDate,BranchModel branchModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria=session.createCriteria(PurchaseOrderModel.class, "purchaseOrderModel")
				.setProjection(Projections.projectionList()
						.add(Projections.property("id"), "id")
						.add(Projections.property("orderIdByDate"), "orderIdByDate")
						.add(Projections.property("billingDateAndTime"), "billingDateAndTime")
						.add(Projections.property("payAmount"),"payAmount")
						.add(Projections.property("discountPrice"),"discountPrice")
						.add(Projections.property("totalVAT"),"totalVAT")
						.add(Projections.property("invoiceNo"),"invoiceNo")
						.add(Projections.property("purchaseOrderModel.branchModel"),"branchModel"))

						.setResultTransformer(Transformers.aliasToBean(PurchaseOrderModel.class))
						.add(Restrictions.between("purchaseOrderModel.billingDateAndTime", fromDate, toDate))
						.add(Restrictions.eq("purchaseOrderModel.branchModel.id", branchModel.getId()));

		purchaseOrderModels=(ArrayList<PurchaseOrderModel>) criteria.list();
	
		session.close();
		return purchaseOrderModels;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseReturnOrderModel> purchaseReturnOrderReports(
			java.util.Date fromDate, java.util.Date toDate, BranchModel branchModel) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(PurchaseReturnOrderModel.class,"purchaseReturnOrderModel")
				.add(Restrictions.between("purchaseReturnOrderModel.billingDateAndTime", fromDate, toDate))
				.add(Restrictions.eq("purchaseReturnOrderModel.branchModel.id", branchModel.getId()));
		purchaseReturnOrderModels = criteria.list();
		session.close();
		return purchaseReturnOrderModels;
	}
	@SuppressWarnings("unchecked")
	@Override
	public JRDataSource purchaseOrderListReports(PurchaseOrderModel purchaseOrderModel) {
		
		Session session = sessionFactory.openSession();
		
			if(purchaseOrderModel.getBranchModel().getOrganizationModel()!=null){
				Query query = session.createSQLQuery(
						"CALL purchaseReports(:id,:role)")
						.addEntity(PurchaseOrderModel.class)
						.setParameter("id",purchaseOrderModel.getBranchModel().getOrganizationModel().getId())
						.setParameter("role",purchaseOrderModel.getBranchModel().getOrganizationModel().getResourceModel().getCreatorRoleModel().getId());
				List<PurchaseOrderModel> purchaseOrderModels = query.list();
				purchaseOrderBeans=PurchaseOrderBeanPreparation.prepareListOfPurchaseOrderBean(purchaseOrderModels);

			}
		
			else if(purchaseOrderModel.getBranchModel().getId()!=null){
				Query query = session.createSQLQuery(
						"CALL purchaseReports(:id,:role)")
						.addEntity(PurchaseOrderModel.class)
						.setParameter("id",purchaseOrderModel.getBranchModel().getId())
						.setParameter("role",purchaseOrderModel.getBranchModel().getResourceModel().getCreatorRoleModel().getId());
				
				List<PurchaseOrderModel> purchaseOrderModels = query.list();
				purchaseOrderBeans=PurchaseOrderBeanPreparation.prepareListOfPurchaseOrderBean(purchaseOrderModels);
				}
		session.close();
		JRDataSource jr = new JRBeanCollectionDataSource(purchaseOrderBeans,false);
		return jr;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseLineItemsBean> purchaseOrderLineitemsReports(
			PurchaseOrderModel purchaseOrderModel) {
		Session session = sessionFactory.openSession();
		
			Criteria criteria = session.createCriteria(PurchaseLineItemsModel.class,"purchaseLineItems")
					.createAlias("purchaseLineItems.purchaseOrderModel", "purchaseOrder")
					.add(Restrictions.eq("purchaseOrder.invoiceNo",purchaseOrderModel.getInvoiceNo()));		        

			List<PurchaseLineItemsModel> purchaseLineItemsModels = criteria.list();
			
			List<PurchaseLineItemsBean> purchaseLineItemsBeans=PurchaseLineItemsBeanPreparation.prepareListOfPurchaseLineItemsBean(purchaseLineItemsModels);
			session.close();
		return purchaseLineItemsBeans;
	}
	@Override
	public List<SalesOrderModel> getSalesOrderReportsList(java.util.Date fromDate, java.util.Date toDate,SalesOrderModel salesOrderModel) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		if(salesOrderModel.getBranchModel().getOrganizationModel().getId()!=null){
			 criteria = session.createCriteria(SalesOrderModel.class,"salesOrderModel")
					 .createAlias("salesOrderModel.branchModel", "branch")
					 .createAlias("branch.organizationModel", "org")
					 .createAlias("salesOrderModel.categoryModel", "category")
					 .add(Restrictions.between("billingDateAndTime", fromDate, toDate))
					 .add(Restrictions.eq("org.id", salesOrderModel.getBranchModel().getOrganizationModel().getId()))
					 .add(Restrictions.eq("branch.id", salesOrderModel.getBranchModel().getId()))
					 .add(Restrictions.eq("category.id", salesOrderModel.getCategoryModel().getId()));	
		}else{
			criteria = session.createCriteria(SalesOrderModel.class,"salesOrderModel")
					 .createAlias("salesOrderModel.branchModel", "branch")
					 .createAlias("branch.organizationModel", "org")
					 .createAlias("salesOrderModel.categoryModel", "category")
					 .add(Restrictions.between("billingDateAndTime", fromDate, toDate))
					 .add(Restrictions.eq("branch.id", salesOrderModel.getBranchModel().getId()))
					 .add(Restrictions.eq("category.id", salesOrderModel.getCategoryModel().getId()));	
		
		}
		@SuppressWarnings("unchecked")
		List<SalesOrderModel> salesOrderModels = (List<SalesOrderModel>)criteria.list();
		session.close();
		return salesOrderModels;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesReturnOrderModel> getSalesReturnOrderList(
			java.util.Date fromDate, java.util.Date toDate,
			SalesReturnOrderModel salesReturnOrderModel) {
		
		try{
			
			Session session = sessionFactory.openSession();
			
			if(salesReturnOrderModel.getBranchModel().getOrganizationModel().getId()!=null){
				criteria = session.createCriteria(SalesReturnOrderModel.class,"salesReturnOrder")				
						.createAlias("salesReturnOrder.branchModel", "branch")
						.createAlias("branch.organizationModel", "org")
						.add(Restrictions.between("billingDateAndTime", fromDate, toDate))
						.add(Restrictions.eq("org.id",salesReturnOrderModel.getBranchModel().getOrganizationModel().getId()))
						.add(Restrictions.eq("branch.id", salesReturnOrderModel.getBranchModel().getId()));
				}else{
					criteria = session.createCriteria(SalesReturnOrderModel.class,"salesReturnOrder")	
						.createAlias("salesReturnOrder.branchModel", "branch")
						.add(Restrictions.between("billingDateAndTime", fromDate, toDate))
						.add(Restrictions.eq("branch.id",salesReturnOrderModel.getBranchModel().getId()));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		List<SalesReturnOrderModel> returnOrderModels = criteria.list();
		
		return returnOrderModels;
	}
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<SalesLineItemsModel> getProductSales(java.util.Date fromDate, java.util.Date toDate,
			SalesLineItemsModel salesLineItemsModel) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		if(salesLineItemsModel.getOutletModel().getBranchModel().getOrganizationModel().getId()!=null){
			criteria = session.createCriteria(SalesLineItemsModel.class,"salesLineItemsModel")
					.createAlias("salesLineItemsModel.outletModel", "outlet")
					.createAlias("salesLineItemsModel.productInventoryModel", "inv")
					.createAlias("inv.productModel", "product")
					.createAlias("outletModel.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.createAlias("salesLineItemsModel.salesOrderModel", "sales")
					.add(Restrictions.between("sales.billingDateAndTime", fromDate, toDate))
					.add(Restrictions.eq("org.id", salesLineItemsModel.getOutletModel().getBranchModel().getOrganizationModel().getId()))
					.add(Restrictions.eq("product.name", salesLineItemsModel.getProductInventoryModel().getProductModel().getName()));
		}else{
			criteria = session.createCriteria(SalesLineItemsModel.class,"salesLineItemsModel")
					.createAlias("salesLineItemsModel.outletModel", "outlet")
					.createAlias("salesLineItemsModel.productInventoryModel", "inv")
					.createAlias("inv.productModel", "product")
					.createAlias("outletModel.branchModel", "branch")
					.createAlias("salesLineItemsModel.salesOrderModel", "sales")
					.add(Restrictions.between("sales.billingDateAndTime", fromDate, toDate))
					.add(Restrictions.eq("branch.id", salesLineItemsModel.getOutletModel().getBranchModel().getId()))
					.add(Restrictions.eq("product.name", salesLineItemsModel.getProductInventoryModel().getProductModel().getName()));

		}
		List<SalesLineItemsModel> itemsModels = criteria.list();
		return (ArrayList<SalesLineItemsModel>) itemsModels;
	}
	
}
