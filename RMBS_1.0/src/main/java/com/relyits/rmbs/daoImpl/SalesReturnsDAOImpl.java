package com.relyits.rmbs.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.SalesReturnsDAO;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnLineItemsModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;


@Repository("salesReturnsDAO")
public class SalesReturnsDAOImpl implements SalesReturnsDAO{

	@Autowired
	private SessionFactory sessionFactory;
	private ProductInventoryModel productInventoryModel=null;
	
	Criteria criteria = null;
	Object obj = null;
	
	private SalesReturnOrderModel salesReturnOrderModel=null;
	
	@SuppressWarnings({ "unused", "unchecked" })
	public List<SalesReturnLineItemsModel> createSalesReturnOrder(List<SalesReturnLineItemsModel> salesReturnLineItemsModels,List<SalesLineItemsModel> salesLineItemsModels) {
		Session session=sessionFactory.openSession();
	
		try {
			session.beginTransaction();
		int count=0;
		int sOId=0;
		String oIdByDt="";
		salesReturnOrderModel=new SalesReturnOrderModel();
			for(SalesReturnLineItemsModel salesReturnLineItemsModel1:salesReturnLineItemsModels){
				count++;
				
			if(count==1){	
			salesReturnOrderModel=salesReturnLineItemsModel1.getSalesReturnOrderModel();
			
				if(salesReturnOrderModel.getId()==null){
					session.saveOrUpdate(salesReturnOrderModel);

					 sOId=salesReturnOrderModel.getId();
					oIdByDt="SRO"+salesReturnOrderModel.getOrderIdByDate().replaceAll("-", "")+"/"
							+salesReturnOrderModel.getBranchModel().getId()+"/"
							+salesReturnOrderModel.getOutletModel().getId()+"/"
							+sOId;
					salesReturnOrderModel.setOrderIdByDate(oIdByDt);
					session.saveOrUpdate(salesReturnOrderModel);
				}
				
			}else{
				
				SalesReturnOrderModel orderModel=salesReturnLineItemsModel1.getSalesReturnOrderModel();
				 
				orderModel.setAmount(orderModel.getAmount()/*+salesReturnOrderModel.getAmount()*/);
				orderModel.setDiscountPrice(orderModel.getDiscountPrice()/*+salesReturnOrderModel.getDiscountPrice()*/);
				orderModel.setDeductionOnMargin(orderModel.getDeductionOnMargin()+salesReturnOrderModel.getDeductionOnMargin());
				orderModel.setTotalVAT(orderModel.getTotalVAT()/*+salesReturnOrderModel.getTotalVAT()*/);
				orderModel.setId(sOId);
				
				SalesOrderModel salesOrderModel=salesReturnOrderModel.getSalesOrderModel();
				salesReturnOrderModel=orderModel;
				salesReturnOrderModel.setOrderIdByDate(oIdByDt);
				salesReturnOrderModel.setSalesOrderModel(salesOrderModel);
				session.merge(salesReturnOrderModel);
			}
			
			
			
			
			
			session.saveOrUpdate(salesReturnLineItemsModel1);
			session.saveOrUpdate(salesLineItemsModels.get(count-1));
			int sLIId=salesReturnLineItemsModel1.getId();
			productInventoryModel=salesReturnLineItemsModel1.getProductInventoryModel();
			session.merge(productInventoryModel);
		
			}
		
			session.getTransaction().commit();
		
			Criteria salesReturnLineItemsCriteria=session.createCriteria(SalesReturnLineItemsModel.class,"SRLI")
					.createAlias("SRLI.salesReturnOrderModel", "SRO")
					.add(Restrictions.eq("SRO.id", salesReturnOrderModel.getId()));
					
			salesReturnLineItemsModels=salesReturnLineItemsCriteria.list();
		} catch (Exception e) {
			session.cancelQuery();
		}finally{
			session.close();			
		}
		
		return salesReturnLineItemsModels;
	}
	
	@SuppressWarnings("unchecked")
	public List<SalesReturnLineItemsModel> getSalesReturnLineItemsModelsBySalesReturnOrderModel(SalesReturnOrderModel salesReturnOrderModel1) {
		
		Session session=sessionFactory.openSession();
		
		Criteria salesReturnLineItemsCriteria=session.createCriteria(SalesReturnLineItemsModel.class,"salesReturnLineItemsModel")
				.createAlias("salesReturnLineItemsModel.salesReturnOrderModel", "saleReturnOrder")
				.add(Restrictions.eq("saleReturnOrder.id",salesReturnOrderModel1.getId()));
		List<SalesReturnLineItemsModel> salesReturnLineItemsModels=salesReturnLineItemsCriteria.list();
		session.close();
		return salesReturnLineItemsModels;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SalesReturnOrderModel> getSalesReturnOrderList(SalesReturnOrderModel salesReturnOrderModel) {
		try{
			Session session = sessionFactory.openSession();
			if(salesReturnOrderModel.getBranchModel().getOrganizationModel().getId()!=null){
			criteria = session.createCriteria(SalesReturnOrderModel.class,"salesReturnOrder")				
					.createAlias("salesReturnOrder.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")					
					.add(Restrictions.eq("org.id",salesReturnOrderModel.getBranchModel().getOrganizationModel().getId()));
			}else{
				criteria = session.createCriteria(SalesReturnOrderModel.class,"salesReturnOrder")	
						.createAlias("salesReturnOrder.branchModel", "branch")
						.add(Restrictions.eq("branch.id",salesReturnOrderModel.getBranchModel().getId()));
			}	

			List list = criteria.list();
			System.out.println("products.size() "+list.size());		

			return list;


		}catch(Exception e){

			return null;
		}
	}

@SuppressWarnings("unchecked")
public List<SalesLineItemsModel> getSalesLineItemsModelsByOrderIdByDate(SalesOrderModel salesOrderModel) {
		
		Session session=sessionFactory.openSession();
		
		Criteria salesLineItemsCriteria=session.createCriteria(SalesLineItemsModel.class,"salesLineItemsModel")
				.createAlias("salesLineItemsModel.salesOrderModel", "saleOrder")
				.add(Restrictions.eq("saleOrder.orderIdByDate",salesOrderModel.getOrderIdByDate()));
		List<SalesLineItemsModel> salesLineItemsModels=salesLineItemsCriteria.list();
		session.close();
		return salesLineItemsModels;
	}

@Override
public Long countAll(SalesReturnOrderModel salesReturnOrderModel) {
	try{
		Session session = sessionFactory.openSession();
		if(salesReturnOrderModel.getBranchModel().getOrganizationModel().getId()!=null){
		obj = session.createCriteria(SalesReturnOrderModel.class,"salesReturnOrder")				
				.createAlias("salesReturnOrder.branchModel", "branch")
				.createAlias("branch.organizationModel", "org")					
				.add(Restrictions.eq("org.id",salesReturnOrderModel.getBranchModel().getOrganizationModel().getId()))
				.setProjection(Projections.rowCount()).uniqueResult()
				;
		}else{
			obj = session.createCriteria(SalesReturnOrderModel.class,"salesReturnOrder")	
					.createAlias("salesReturnOrder.branchModel", "branch")
					.add(Restrictions.eq("branch.id",salesReturnOrderModel.getBranchModel().getId()))
					.setProjection(Projections.rowCount()).uniqueResult()
					;
		}	

		return ((Number)obj).longValue();


	}catch(Exception e){

		return null;
	}
}

@Override
public List<SalesReturnOrderModel> listByPage(int firstResult, int pageSize,SalesReturnOrderModel salesReturnOrderModel) {
	try{
		Session session = sessionFactory.openSession();
		if(salesReturnOrderModel.getBranchModel().getOrganizationModel().getId()!=null){
		criteria = session.createCriteria(SalesReturnOrderModel.class,"salesReturnOrder")				
				.createAlias("salesReturnOrder.branchModel", "branch")
				.createAlias("branch.organizationModel", "org")					
				.add(Restrictions.eq("org.id",salesReturnOrderModel.getBranchModel().getOrganizationModel().getId()))
				.setFirstResult(firstResult)
				.setMaxResults(pageSize)
				.addOrder(Order.desc("salesReturnOrder.id"));
		}else{
			criteria = session.createCriteria(SalesReturnOrderModel.class,"salesReturnOrder")	
					.createAlias("salesReturnOrder.branchModel", "branch")
					.add(Restrictions.eq("branch.id",salesReturnOrderModel.getBranchModel().getId()))
					.setFirstResult(firstResult)
					.setMaxResults(pageSize)
					.addOrder(Order.desc("salesReturnOrder.id"));
		}	

		List list = criteria.list();
		System.out.println("products.size() "+list.size());		

		return list;


	}catch(Exception e){

		return null;
	}
}


public Long countAll(SalesReturnLineItemsModel salesReturnLineItemsModel) {
	Session session=sessionFactory.openSession();
	obj=session.createCriteria(SalesReturnLineItemsModel.class,"salesReturnLineItemsModel")
			.createAlias("salesReturnLineItemsModel.salesReturnOrderModel", "saleReturnOrder")
			.add(Restrictions.eq("saleReturnOrder.id",salesReturnLineItemsModel.getSalesReturnOrderModel().getId()))
			.setProjection(Projections.rowCount()).uniqueResult();
	
	session.close();
	return ((Number)obj).longValue();
}


public List<SalesReturnLineItemsModel> listByPage(int firstResult,int pageSize, SalesReturnLineItemsModel salesReturnLineItemsModel) {
	Session session=sessionFactory.openSession();
	
	Criteria salesReturnLineItemsCriteria=session.createCriteria(SalesReturnLineItemsModel.class,"salesReturnLineItemsModel")
			.createAlias("salesReturnLineItemsModel.salesReturnOrderModel", "saleReturnOrder")
			.add(Restrictions.eq("saleReturnOrder.id",salesReturnLineItemsModel.getSalesReturnOrderModel().getId()))
			.setFirstResult(firstResult)
			.setMaxResults(pageSize)
			.addOrder(Order.desc("salesReturnLineItemsModel.id"));
	List<SalesReturnLineItemsModel> salesReturnLineItemsModels=salesReturnLineItemsCriteria.list();
	session.close();
	return salesReturnLineItemsModels;
}


	

}
