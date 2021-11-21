package com.relyits.rmbs.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.OutletDao;
import com.relyits.rmbs.model.refference.AddressModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.registration.ResourceModel;


@Repository("outletDao")
public class OutletDaoImpl implements OutletDao{

	@Autowired
	private SessionFactory sessionFactory;
	private BranchModel branchModel=null;
	private RoleModel roleModel=null;
	private RoleModel creatorRoleModel =null;
	private StatusModel accountStatusModel =null;
	private StatusModel loginStatusModel =null;
	private ResourceModel resourceModel =null;
	private AddressModel addressModel =null;
	
	@SuppressWarnings("rawtypes")
	public OutletModel checkOutletAvailability(OutletModel outletModel) {
		 Session session = sessionFactory.getCurrentSession();
		 session.beginTransaction();
				 ArrayList list=(ArrayList)session.createQuery("FROM OutletModel WHERE RMBS10302 = '"+outletModel.getUserName()+"'")
				 .list();
			session.getTransaction().commit();	 
			OutletModel OutletModel1=null;
		 if(!list.isEmpty()){
			 OutletModel1=(OutletModel)list.get(0);
		 }
		 return OutletModel1; 
	
}
	

	public boolean createOutlet(OutletModel outletModel) {
		
		
		
		boolean success = false;  
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try{
		
		   branchModel = new BranchModel();
			branchModel = (BranchModel) session.get(BranchModel.class, outletModel.getBranchModel().getId());
			outletModel.setBranchModel(branchModel);
			
			
			roleModel = (RoleModel)session.get(RoleModel.class, outletModel.getResourceModel().getRoleModel().getId());
		    creatorRoleModel =(RoleModel)session.get(RoleModel.class, outletModel.getResourceModel().getCreatorRoleModel().getId());
			accountStatusModel = (StatusModel)session.get(StatusModel.class, outletModel.getResourceModel().getAccountStatusModel().getId());
			loginStatusModel = (StatusModel)session.get(StatusModel.class, outletModel.getResourceModel().getLoginStatusModel().getId());
			
			addressModel = new AddressModel();
			addressModel = outletModel.getResourceModel().getAddressModel();
			session.saveOrUpdate(addressModel);
			
			resourceModel = new ResourceModel();
			resourceModel.setId(outletModel.getResourceModel().getId());
			resourceModel.setAccountStatusModel(accountStatusModel);
			resourceModel.setLoginStatusModel(loginStatusModel);
			resourceModel.setCreatorRoleModel(creatorRoleModel);
			resourceModel.setRoleModel(roleModel);
			resourceModel.setAddressModel(addressModel);	
			resourceModel.setCreatedBy(outletModel.getResourceModel().getCreatedBy());
			session.saveOrUpdate(resourceModel);			
			
			outletModel.setResourceModel(resourceModel);
			session.saveOrUpdate(outletModel);	
			branchModel.setNoOfOutlets(branchModel.getNoOfOutlets()+1);
			session.saveOrUpdate(branchModel);
			
			session.getTransaction().commit();
			    success = true;

		}catch(Exception e){
			session.cancelQuery();
		}
		return success;
		
		
		
	}
	
	@SuppressWarnings("unused")
	public boolean  updateOutlet(OutletModel outletModel) {
		  boolean success = false;  			
		  OutletModel outletModel1 =null;			
		     Session session= sessionFactory.openSession();
			     session.beginTransaction();
				try{				
					outletModel1 = new OutletModel();
					outletModel1 = outletModel;
					
					AddressModel addressModel = new AddressModel();
					
					RoleModel roleModel = (RoleModel)session.get(RoleModel.class, outletModel.getResourceModel().getRoleModel().getId());
					
					RoleModel creatorRoleModel =(RoleModel)session.get(RoleModel.class, outletModel.getResourceModel().getCreatorRoleModel().getId());
					
					StatusModel accountStatusModel = (StatusModel)session.get(StatusModel.class, outletModel.getResourceModel().getAccountStatusModel().getId());
					
					StatusModel loginStatusModel = (StatusModel)session.get(StatusModel.class, outletModel.getResourceModel().getLoginStatusModel().getId());
					
					addressModel = outletModel.getResourceModel().getAddressModel();
					session.update(addressModel);
					
					ResourceModel resourceModel = new ResourceModel();
					resourceModel.setId(outletModel.getResourceModel().getId());
					resourceModel.setAccountStatusModel(accountStatusModel);
					resourceModel.setLoginStatusModel(loginStatusModel);
					resourceModel.setCreatorRoleModel(creatorRoleModel);
					resourceModel.setRoleModel(roleModel);
					resourceModel.setAddressModel(addressModel);	
					resourceModel.setCreatedBy(outletModel.getResourceModel().getCreatedBy());
					
					session.update(resourceModel);
					
					outletModel.setResourceModel(resourceModel);
					
					session.update(outletModel);
															
					session.getTransaction().commit();
					    success = true;
				
		}catch(Exception e){
			
		}
			
		return success;
	  }    

	
	public OutletModel getOutlet(OutletModel outletModel) {
		return (OutletModel) sessionFactory.getCurrentSession().get(OutletModel.class, outletModel.getId());
	}

	public boolean changeOutletStatus(OutletModel outletModel) {
		boolean success = false;
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();
		try{
		Query query1=hsession.createSQLQuery(" UPDATE rmbs.rmbs106 SET rmbs10601="+outletModel.getResourceModel().getId()
				 +", AC_ST_rmbs80301='"+outletModel.getResourceModel().getAccountStatusModel().getId()+"'"
			       +" where rmbs10601="+outletModel.getResourceModel().getId()+";");
		
		query1.executeUpdate();
		hsession.getTransaction().commit();
		success = true;
		}catch(Exception e){
			
			hsession.cancelQuery();
			
		}
		return success;
		
	}


	@SuppressWarnings("unchecked")
	public List<OutletModel> getOutletListsByBranch(OutletModel outletModel) {
		
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();
		try{
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OutletModel.class,"Outlet")
					.createAlias("Outlet.resourceModel", "Resource")
					.createAlias("Resource.accountStatusModel", "AC_STATUS")
					.createAlias("Outlet.branchModel", "Branch")
					.add(Restrictions.eq("Branch.id",outletModel.getBranchModel().getId()))
			.add(Restrictions.eq("AC_STATUS.id",outletModel.getResourceModel().getAccountStatusModel().getId()));
			
			hsession.getTransaction().commit();
			
			return criteria.list();
		}catch(Exception e){
			return null;
		}
	}

	public List<OutletModel> getOutletListsByOrganization(OutletModel outletModel) {
		return null;
	}


	@SuppressWarnings("rawtypes")
	@Override
	public OutletModel loginValidate(OutletModel outletModel) {
		  Session session = sessionFactory.getCurrentSession();
		 session.beginTransaction();
				 ArrayList list=(ArrayList)session.createQuery("FROM OutletModel WHERE RMBS10302 = '"+outletModel.getUserName()+"' and password = '"+outletModel.getPassword()+"'")
				 .list();
			session.getTransaction().commit();	 
			OutletModel outletModel1=null;
		 if(!list.isEmpty()){
			 outletModel1=(OutletModel)list.get(0);
		 }
		 session.close();
		return outletModel1;
	}

	public int upadtePasswordByIdOfOutlet(OutletModel outletModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		int result=0;
		try {
		Query query1=session.createSQLQuery("UPDATE rmbs103 SET rmbs10301 ="+outletModel.getId()
				+", rmbs10307='"+outletModel.getPassword()+"' where rmbs10301="+outletModel.getId()+";");

		result=query1.executeUpdate();
		} catch (Exception e) {
			session.cancelQuery();
		}

		session.getTransaction().commit();
		session.close();
		return result;
	}

	
	@Override
	public Long countAll(OutletModel outletModel) {
		 Object obj = sessionFactory.getCurrentSession().createCriteria(OutletModel.class,"out")				 
				 	.createAlias("out.resourceModel", "Resource")
					.createAlias("Resource.accountStatusModel", "AC_STATUS")						
					.add(Restrictions.eq("AC_STATUS.id",outletModel.getResourceModel().getAccountStatusModel().getId()))
					.setProjection(Projections.rowCount()).uniqueResult();
	
		 return ((Number) obj).longValue();
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OutletModel> listByPage(Class clazz, int firstResult,
			int maxResult, OutletModel outletModel) {
		
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();
		try{
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OutletModel.class,"Outlet")
					.createAlias("Outlet.resourceModel", "Resource")
					.createAlias("Resource.accountStatusModel", "AC_STATUS")
					.createAlias("Outlet.branchModel", "Branch")
					.add(Restrictions.eq("Branch.id",outletModel.getBranchModel().getId()))
					.add(Restrictions.eq("AC_STATUS.id",outletModel.getResourceModel().getAccountStatusModel().getId()))
					.setFirstResult(firstResult)
					.setMaxResults(maxResult)
					.addOrder(Order.desc("Outlet.id"));
			
			hsession.getTransaction().commit();
			
			return criteria.list();
		}catch(Exception e){
			return null;
		}
	}


	@Override
	public List<OutletModel> getOutlets(OutletModel outletModel) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<OutletModel> lists =null;
		 
//		Query query = session.createSQLQuery("select RMBS10302,RMBS10301 from RMBS103 where BR_RMBS10201 = "+branchModel.getId());
		
		Criteria criteria = session.createCriteria(OutletModel.class,"outletModel")
				.createAlias("outletModel.branchModel", "branchModel")
				.setProjection(Projections.projectionList()
						.add(Projections.property("outletModel.id"),"id")
						.add(Projections.property("outletModel.userName"),"userName"))
						.setResultTransformer(Transformers.aliasToBean(OutletModel.class))
						.add(Restrictions.eq("branchModel.id", outletModel.getBranchModel().getId()));
		
		List<OutletModel> list = criteria.list();
		
		/*for(int i=0;i<list.size();i++){
			outletModel = new OutletModel();
			outletModel.setId(list.get(i).getId());
			outletModel.setUserName(list.get(i).getUserName());
			lists.add(outletModel);
		}*/
		session.close();
		return list;
	}
		

}
