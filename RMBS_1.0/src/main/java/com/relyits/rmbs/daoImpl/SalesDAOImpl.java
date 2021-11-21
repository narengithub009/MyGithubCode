package com.relyits.rmbs.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.SalesDAO;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;


@Repository("salesDAO")
public class SalesDAOImpl implements SalesDAO{

	@Autowired
	private SessionFactory sessionFactory;
	private SalesLineItemsModel salesLineItemsModel=null;
	private SalesOrderModel salesOrderModel=null;	
	private ProductInventoryModel productInventoryModel=null;
	private List<SalesLineItemsModel> salesLineItemsModels=null;
	Criteria criteria = null;
	Object obj = null;
	
	public SalesLineItemsModel createSalesOrder(SalesLineItemsModel salesLineItemsModel1) {
		Session session=sessionFactory.openSession();
	
		try {
			session.beginTransaction();
			salesOrderModel=new SalesOrderModel();
			salesOrderModel=salesLineItemsModel1.getSalesOrderModel();
			
			if(salesOrderModel.getId()==null){
			session.saveOrUpdate(salesOrderModel.getCustomerModel().getAddressModel());
			session.saveOrUpdate(salesOrderModel.getCustomerModel());
			}
			
			session.saveOrUpdate(salesOrderModel);
			int sOId=salesOrderModel.getId();
			
			String oIdByDt="SO"+salesOrderModel.getOrderIdByDate().replaceAll("-", "")+"/"
					+salesOrderModel.getBranchModel().getId()+"/"
					+salesOrderModel.getOutletModel().getId()+"/"
					+sOId;
			salesOrderModel.setOrderIdByDate(oIdByDt);
			
			session.saveOrUpdate(salesOrderModel);
			
			session.saveOrUpdate(salesLineItemsModel1);
			
			int sLIId=salesLineItemsModel1.getId();
			productInventoryModel=salesLineItemsModel1.getProductInventoryModel();
			productInventoryModel.setQuantity(productInventoryModel.getQuantity()-salesLineItemsModel1.getQuantity());
			session.saveOrUpdate(productInventoryModel);
			session.getTransaction().commit();
			
			salesLineItemsModel=(SalesLineItemsModel) session.get(SalesLineItemsModel.class, sLIId);
		} catch (Exception e) {
			session.cancelQuery();
			session.close();
		}
		
		return salesLineItemsModel;
	}
	public SalesOrderModel getSalesOrder(SalesOrderModel salesOrderModel1){
		Session session=sessionFactory.openSession();
		salesOrderModel=(SalesOrderModel) session.get(SalesOrderModel.class, salesOrderModel1.getId());
		session.close();
		return salesOrderModel;
	}
	
	public SalesLineItemsModel getSalesOrderLineItemById(SalesLineItemsModel salesLineItemsModel1){
		Session session=sessionFactory.openSession();
		salesLineItemsModel=(SalesLineItemsModel) session.get(SalesLineItemsModel.class, salesLineItemsModel1.getId());
		session.close();
		return salesLineItemsModel;
	}
	public SalesLineItemsModel createSalesOrderLineItems(SalesLineItemsModel salesLineItemsModel){
		
		return salesLineItemsModel;
	}
	
	@SuppressWarnings("unchecked")
	public List<SalesLineItemsModel> updateSalesOrder(SalesLineItemsModel salesLineItemsModel1){
		Session session=sessionFactory.openSession();
		try {
			session.beginTransaction();
			salesOrderModel=new SalesOrderModel();
			salesOrderModel=salesLineItemsModel1.getSalesOrderModel();
	
			
			session.update(salesOrderModel);
			productInventoryModel=salesLineItemsModel1.getProductInventoryModel();
			session.update(productInventoryModel);
			session.delete(salesLineItemsModel1);
			session.getTransaction().commit();
			
		    Criteria itemsCriteria=session.createCriteria(SalesLineItemsModel.class,"lineItems")	
		    		.createAlias("lineItems.salesOrderModel", "Order")
		    		.add(Restrictions.eq("Order.id",salesOrderModel.getId()));
		    salesLineItemsModels=itemsCriteria.list();
		    if(salesLineItemsModels.size()==0){
		    	session.beginTransaction();
		    	session.delete(salesOrderModel);
		    	session.getTransaction().commit();
		    }
		} catch (Exception e) {
			session.cancelQuery();
			session.close();
		}
		
		return salesLineItemsModels;
	}
	
	@SuppressWarnings("unchecked")
	public List<SalesLineItemsModel> updateSalesLineItem(ProductInventoryModel oldProductInventoryModel,SalesLineItemsModel salesLineItemsModel1){
		Session session=sessionFactory.openSession();
		try {
			session.beginTransaction();
			salesOrderModel=new SalesOrderModel();
			salesOrderModel=salesLineItemsModel1.getSalesOrderModel();
			productInventoryModel=salesLineItemsModel1.getProductInventoryModel();
	        if(oldProductInventoryModel.getId()!=productInventoryModel.getId()){
	       	session.update(oldProductInventoryModel);
	        }
			session.update(salesOrderModel);
			productInventoryModel.setQuantity(productInventoryModel.getQuantity()-salesLineItemsModel1.getQuantity());
			session.update(productInventoryModel);
			session.update(salesLineItemsModel1);
			session.getTransaction().commit();
			
		    Criteria itemsCriteria=session.createCriteria(SalesLineItemsModel.class,"lineItems")	
		    		.createAlias("lineItems.salesOrderModel", "Order")
		    		.add(Restrictions.eq("Order.id",salesOrderModel.getId()));
		    salesLineItemsModels=itemsCriteria.list();
		   
		} catch (Exception e) {
			session.cancelQuery();
			session.close();
		}
		
		return salesLineItemsModels;
	}
	
	
	public SalesOrderModel confirmSalesOrder(List<SalesLineItemsModel> salesLineItemsModels){
		
		  StatelessSession hibSession = sessionFactory.openStatelessSession();
		  Transaction hibTx = hibSession.beginTransaction();
		  
		  try {		  
			  
			 for(SalesLineItemsModel slim:salesLineItemsModels){ 
		          hibSession.update(slim);
		          salesOrderModel=slim.getSalesOrderModel();
			 	}
			 hibSession.update(salesOrderModel);
		      hibTx.commit();
		      hibSession.close();
		  }
		  catch (HibernateException ex) {
			  
		      if (hibTx != null) {
		    	  salesOrderModel=null;
		          hibTx.rollback();
		      }
		      if (hibSession != null) {
		          hibSession.close();
		      }
		      throw new DataAccessResourceFailureException("Hibernate update failed", ex);
		  }
		return salesOrderModel;
	}
	
		
	@SuppressWarnings("unchecked")
	public List<SalesLineItemsModel> getSalesLineItemsModelsBySalesOrderModel(
			SalesOrderModel salesOrderModel1) {
		
		Session session=sessionFactory.openSession();
		
		Criteria salesLineItemsCriteria=session.createCriteria(SalesLineItemsModel.class,"salesLineItemsModel")
				.createAlias("salesLineItemsModel.salesOrderModel", "saleOrder")
				.add(Restrictions.eq("saleOrder.id",salesOrderModel1.getId()));
		List<SalesLineItemsModel> salesLineItemsModels=salesLineItemsCriteria.list();
		session.close();
		return salesLineItemsModels;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SalesOrderModel> getSalesOrderList(SalesOrderModel salesOrderModel) {
		try{
			Session session = sessionFactory.openSession();
			if(salesOrderModel.getBranchModel().getOrganizationModel().getId()!=null){
			criteria = session.createCriteria(SalesOrderModel.class,"salesOrder")				
					.createAlias("salesOrder.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.createAlias("salesOrder.categoryModel", "category")
					.add(Restrictions.eq("org.id",salesOrderModel.getBranchModel().getOrganizationModel().getId()))
					.add(Restrictions.eq("category.id",salesOrderModel.getCategoryModel().getId()));
			}else{
				criteria = session.createCriteria(SalesOrderModel.class,"salesOrder")	
						.createAlias("salesOrder.categoryModel", "category")
						.createAlias("salesOrder.branchModel", "branch")
						.add(Restrictions.eq("branch.id",salesOrderModel.getBranchModel().getId()))
						.add(Restrictions.eq("category.id",salesOrderModel.getCategoryModel().getId()));
			}

			List list = criteria.list();
			session.close();
			return list;


		}catch(Exception e){

			return null;
		}
	}
	@Override
	public Long countAll(SalesOrderModel salesOrderModel) {
		try{
			Session session = sessionFactory.openSession();
			if(salesOrderModel.getBranchModel().getOrganizationModel().getId()!=null){
			obj = session.createCriteria(SalesOrderModel.class,"salesOrder")				
					.createAlias("salesOrder.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.createAlias("salesOrder.categoryModel", "category")
					.add(Restrictions.eq("org.id",salesOrderModel.getBranchModel().getOrganizationModel().getId()))
					.add(Restrictions.eq("category.id",salesOrderModel.getCategoryModel().getId()))
					.setProjection(Projections.rowCount()).uniqueResult();
			}else{
				obj = session.createCriteria(SalesOrderModel.class,"salesOrder")	
						.createAlias("salesOrder.categoryModel", "category")
						.createAlias("salesOrder.branchModel", "branch")
						.add(Restrictions.eq("branch.id",salesOrderModel.getBranchModel().getId()))
						.add(Restrictions.eq("category.id",salesOrderModel.getCategoryModel().getId()))
						.setProjection(Projections.rowCount()).uniqueResult();
			}

			System.out.println("((Number)obj).longValue()*******"+((Number)obj).longValue() );
			session.close();
			return ((Number)obj).longValue();


		}catch(Exception e){

			return null;
		}
	}
	@Override
	public List<SalesOrderModel> listByPage(int firstResult, int pageSize,SalesOrderModel salesOrderModel) {
		try{
			Session session = sessionFactory.openSession();
			if(salesOrderModel.getBranchModel().getOrganizationModel().getId()!=null){
			criteria = session.createCriteria(SalesOrderModel.class,"salesOrder")				
					.createAlias("salesOrder.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.createAlias("salesOrder.categoryModel", "category")
					.add(Restrictions.eq("org.id",salesOrderModel.getBranchModel().getOrganizationModel().getId()))
					.add(Restrictions.eq("category.id",salesOrderModel.getCategoryModel().getId()))
					.setFirstResult(firstResult)
					.setMaxResults(pageSize)
					.addOrder(Order.desc("salesOrder.id"));
			}else{
				criteria = session.createCriteria(SalesOrderModel.class,"salesOrder")	
						.createAlias("salesOrder.categoryModel", "category")
						.createAlias("salesOrder.branchModel", "branch")
						.add(Restrictions.eq("branch.id",salesOrderModel.getBranchModel().getId()))
						.add(Restrictions.eq("category.id",salesOrderModel.getCategoryModel().getId()))
						.setFirstResult(firstResult)
						.setMaxResults(pageSize)
						.addOrder(Order.desc("salesOrder.id"));
			}

			List list = criteria.list();
			session.close();
			return list;


		}catch(Exception e){

			return null;
		}
	}
	@Override
	public Long countAll(SalesLineItemsModel salesLineItemsModel) {
	Session session=sessionFactory.openSession();
		
		obj=session.createCriteria(SalesLineItemsModel.class,"salesLineItemsModel")
				.createAlias("salesLineItemsModel.salesOrderModel", "saleOrder")
				.add(Restrictions.eq("saleOrder.id",salesLineItemsModel.getSalesOrderModel().getId()))
				.setProjection(Projections.rowCount()).uniqueResult();
	
		session.close();
		return ((Number)obj).longValue();
	}
	@Override
	public List<SalesLineItemsModel> listByPage(int firstResult,int pageSize, SalesLineItemsModel salesLineItemsModel) {
	Session session=sessionFactory.openSession();
		
		Criteria salesLineItemsCriteria=session.createCriteria(SalesLineItemsModel.class,"salesLineItemsModel")
				.createAlias("salesLineItemsModel.salesOrderModel", "saleOrder")
				.add(Restrictions.eq("saleOrder.id",salesLineItemsModel.getSalesOrderModel().getId()))
				.setFirstResult(firstResult)
				.setMaxResults(pageSize)
				.addOrder(Order.desc("salesLineItemsModel.id"));
		List<SalesLineItemsModel> salesLineItemsModels=salesLineItemsCriteria.list();
		session.close();
		return salesLineItemsModels;
	}

	
	

}
