package com.relyits.rmbs.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.beans.purchase.PurchaseLineItemsBean;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseLineItemsBeanPreparation;
import com.relyits.rmbs.dao.PurchaseReturnsDAO;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;

@Repository("purchaseReturnDAO")
public class PurchaseReturnsDAOImpl implements PurchaseReturnsDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	private PurchaseReturnOrderModel purchaseReturnOrderModel = null;	
	
	private Object obj = null;

//	**************purchase returns stuff*******************

	private ProductInventoryModel productInventoryModel = null;	

	private PurchaseLineItemsModel purchaseLineItemsModel1 = null;
	
	@SuppressWarnings("unchecked")
	public List<String> getPurchaseInvoiceNo(PurchaseOrderModel purchaseOrderModel){
		
		Session session = sessionFactory.openSession();
		
		Criteria criteria = session.createCriteria(PurchaseOrderModel.class , "purchaseModel")
				.createAlias("purchaseModel.branchModel", "branch")
				.setProjection(Projections.projectionList()
						.add(Projections.property("purchaseModel.orderIdByDate"),"orderIdByDate"))
						.setResultTransformer(Transformers.aliasToBean(PurchaseOrderModel.class))
				.add(Restrictions.eq("branch.id", purchaseOrderModel.getBranchModel().getId()))
				.add(Restrictions.like("purchaseModel.orderIdByDate", purchaseOrderModel.getOrderIdbyDate(),MatchMode.ANYWHERE));
	
		List<String> list = criteria.list();
		return list;
	}

	
	@SuppressWarnings("unused")
	public PurchaseLineItemsModel getPurchaseLineItemsById(
			PurchaseLineItemsModel purchaseLineItemsModel) {
		
		Session session = sessionFactory.openSession();
		purchaseLineItemsModel1  = (PurchaseLineItemsModel) session.get(PurchaseLineItemsModel.class, purchaseLineItemsModel.getId());
		PurchaseLineItemsBean purchaseLineItemsBean = new PurchaseLineItemsBean();
		session.close();
		return purchaseLineItemsModel1;
	}
	
	@SuppressWarnings("unchecked")
	public List<PurchaseLineItemsBean> getPurchaseLineItemsByPOId(PurchaseOrderModel purchaseOrderModel){
		
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(PurchaseLineItemsModel.class,"pLineItems")
				.createAlias("pLineItems.purchaseOrderModel", "pModel")
				.add(Restrictions.eq("pModel.id",purchaseOrderModel.getId()));
		
		List<PurchaseLineItemsModel> purchaseLineItemsModels = criteria.list();
		List<PurchaseLineItemsBean> purchaseLineItemsBeans=PurchaseLineItemsBeanPreparation.prepareListOfPurchaseLineItemsBean(purchaseLineItemsModels);
		session.close();
		return purchaseLineItemsBeans;
	}	


	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public List<PurchaseReturnLineItemsModel> createPurchaseReturnOrder(List<PurchaseReturnLineItemsModel> purchaseReturnLineItemsModels,
			List<PurchaseLineItemsModel> purchaseLineItemsModels) {
		
		Session session = sessionFactory.openSession();
		
int i=0;
	
		
		try {
			session.beginTransaction();
		int count=0;
		int pROId=0;
		String oIdByDt="";
		purchaseReturnOrderModel=new PurchaseReturnOrderModel();
		for(PurchaseReturnLineItemsModel returnLineItemsModel:purchaseReturnLineItemsModels){
			count++;
			if(count==1){
				purchaseReturnOrderModel = returnLineItemsModel.getPurchaseReturnOrderModel();
				
				if(purchaseReturnOrderModel.getId()==null){
					session.saveOrUpdate(purchaseReturnOrderModel);
					pROId = purchaseReturnOrderModel.getId();
					
					oIdByDt = "PRO"+purchaseReturnOrderModel.getOrderIdByDate().replaceAll("-", "")+"/"
							+purchaseReturnOrderModel.getBranchModel().getId()+"/"
							+purchaseReturnOrderModel.getAgencyModel().getId()+"/"
							+pROId;
					
					purchaseReturnOrderModel.setOrderIdByDate(oIdByDt);
					session.saveOrUpdate(purchaseReturnOrderModel);
				}
			}else{
				PurchaseReturnOrderModel pReturnOrderModel1 = returnLineItemsModel.getPurchaseReturnOrderModel();
				pReturnOrderModel1.setId(pROId);
				
				PurchaseOrderModel purchaseOrderModel=purchaseReturnOrderModel.getPurchaseOrderModel();
				purchaseReturnOrderModel=pReturnOrderModel1;
				purchaseReturnOrderModel.setOrderIdByDate(oIdByDt);
				purchaseReturnOrderModel.setPurchaseOrderModel(purchaseOrderModel);
				session.merge(purchaseReturnOrderModel);

			}
			
			session.saveOrUpdate(returnLineItemsModel);
			session.saveOrUpdate(purchaseLineItemsModels.get(count-1));
			int sLIId=returnLineItemsModel.getId();
			productInventoryModel=returnLineItemsModel.getProductInventoryModel();
			session.merge(productInventoryModel);

		}
		session.getTransaction().commit();
		
		Criteria criteria=session.createCriteria(PurchaseReturnLineItemsModel.class,"PRLI")
				.createAlias("PRLI.purchaseReturnOrderModel", "PRO")
				.add(Restrictions.eq("PRO.id", purchaseReturnOrderModel.getId()));
				
		purchaseReturnLineItemsModels=criteria.list();

		} 
		catch(Exception e) {
				session.cancelQuery();
			}finally{
				session.close();			
			}
		return purchaseReturnLineItemsModels;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseReturnOrderModel> getPurchaseReturnOrderList(PurchaseReturnOrderModel purchaseReturnOrderModel) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = null;
		if(purchaseReturnOrderModel.getBranchModel().getId()!=null){
		criteria = session.createCriteria(PurchaseReturnOrderModel.class,"pROrderModel")
							.createAlias("pROrderModel.branchModel", "branch")
							.add(Restrictions.eq("branch.id", purchaseReturnOrderModel.getBranchModel().getId()));
		}else{
		criteria = session.createCriteria(PurchaseReturnOrderModel.class,"pROrderModel")
					.createAlias("pROrderModel.branchModel", "branch")
					.add(Restrictions.eq("branch.organizationModel.id", purchaseReturnOrderModel.getBranchModel().getOrganizationModel().getId()));
		}
						List<PurchaseReturnOrderModel>	returnOrderModels = criteria.list();
						
							
		session.close();
		return returnOrderModels;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseReturnLineItemsModel> getPurchaseReturnLineItems(PurchaseReturnLineItemsModel purchaseReturnLineItemsModel) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(PurchaseReturnLineItemsModel.class,"pRLineItemsModel")
							.createAlias("pRLineItemsModel.purchaseReturnOrderModel", "pROrderModel")
				            .add(Restrictions.eq("pROrderModel.id", purchaseReturnLineItemsModel.getPurchaseReturnOrderModel().getId()));
		
		List<PurchaseReturnLineItemsModel> returnLineItemsModels = criteria.list();
		return returnLineItemsModels;
	}


	
	public Long countAll(PurchaseReturnOrderModel purchaseReturnOrderModel) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		if(purchaseReturnOrderModel.getBranchModel().getId()!=null){
		obj = session.createCriteria(PurchaseReturnOrderModel.class,"pROrderModel")
							.createAlias("pROrderModel.branchModel", "branch")
							.add(Restrictions.eq("branch.id", purchaseReturnOrderModel.getBranchModel().getId()))
							.setProjection(Projections.rowCount()).uniqueResult()
						   ;
		}else{
		obj = session.createCriteria(PurchaseReturnOrderModel.class,"pROrderModel")
					.createAlias("pROrderModel.branchModel", "branch")
					.add(Restrictions.eq("branch.organizationModel.id", purchaseReturnOrderModel.getBranchModel().getOrganizationModel().getId()))
					.setProjection(Projections.rowCount()).uniqueResult();
		}
					
						
							
		session.close();
		return ((Number)obj).longValue();
	}


	
	@SuppressWarnings("unchecked")
	public List<PurchaseReturnOrderModel> listByPage(int firstResult,
			int pageSize, PurchaseReturnOrderModel purchaseReturnOrderModel) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = null;
		if(purchaseReturnOrderModel.getBranchModel().getId()!=null){
		criteria = session.createCriteria(PurchaseReturnOrderModel.class,"pROrderModel")
							.createAlias("pROrderModel.branchModel", "branch")
							.add(Restrictions.eq("branch.id", purchaseReturnOrderModel.getBranchModel().getId()))
							.setFirstResult(firstResult)
							.setMaxResults(pageSize)
							.addOrder(Order.desc("pROrderModel.id"));
		}else{
		criteria = session.createCriteria(PurchaseReturnOrderModel.class,"pROrderModel")
					.createAlias("pROrderModel.branchModel", "branch")
					.add(Restrictions.eq("branch.organizationModel.id", purchaseReturnOrderModel.getBranchModel().getOrganizationModel().getId()))
					.setFirstResult(firstResult)
					.setMaxResults(pageSize)
					.addOrder(Order.desc("pROrderModel.id"));
		}
						List<PurchaseReturnOrderModel>	returnOrderModels = criteria.list();
						
							
		session.close();
		return returnOrderModels;
	}
		
}
