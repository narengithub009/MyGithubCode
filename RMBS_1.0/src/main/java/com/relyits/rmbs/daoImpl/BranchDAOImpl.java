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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.BranchDAO;
import com.relyits.rmbs.model.refference.AddressModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.ResourceModel;

@Repository("branchDao")
public class BranchDAOImpl implements BranchDAO{

	@Autowired
	private SessionFactory sessionFactory;

		@SuppressWarnings("rawtypes")
		public BranchModel checkBranchAvailability(BranchModel branchModel) {
			
			
			 Session session = sessionFactory.getCurrentSession();
			 session.beginTransaction();
					 ArrayList list=(ArrayList)session.createQuery("FROM BranchModel WHERE RMBS10202 = '"+branchModel.getUserName()+"'")
					 .list();
				session.getTransaction().commit();	 
				BranchModel branchModel1=null;
			 if(!list.isEmpty()){
				 branchModel1=(BranchModel)list.get(0);
			 }
			 return branchModel1; 
			
		}

		private OrganizationModel organizationModel=null;
		private RoleModel roleModel=null;
		private RoleModel creatorRoleModel =null;
		private StatusModel accountStatusModel =null;
		private StatusModel loginStatusModel =null;
		private ResourceModel resourceModel =null;
		private AddressModel addressModel =null;
		
		public boolean createBranch(BranchModel branchModel) {
			
			boolean success = false;  
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			try{
				organizationModel = (OrganizationModel) session.get(OrganizationModel.class, branchModel.getOrganizationModel().getId());
				branchModel.setOrganizationModel(organizationModel);
				
				
				roleModel = (RoleModel)session.get(RoleModel.class, branchModel.getResourceModel().getRoleModel().getId());
			    creatorRoleModel =(RoleModel)session.get(RoleModel.class, branchModel.getResourceModel().getCreatorRoleModel().getId());
				accountStatusModel = (StatusModel)session.get(StatusModel.class, branchModel.getResourceModel().getAccountStatusModel().getId());
				loginStatusModel = (StatusModel)session.get(StatusModel.class, branchModel.getResourceModel().getLoginStatusModel().getId());
				
				addressModel = new AddressModel();
				addressModel = branchModel.getResourceModel().getAddressModel();
				session.saveOrUpdate(addressModel);
				
				resourceModel = new ResourceModel();
				resourceModel.setId(branchModel.getResourceModel().getId());
				resourceModel.setAccountStatusModel(accountStatusModel);
				resourceModel.setLoginStatusModel(loginStatusModel);
				resourceModel.setCreatorRoleModel(creatorRoleModel);
				resourceModel.setRoleModel(roleModel);
				resourceModel.setAddressModel(addressModel);	
				resourceModel.setCreatedBy(branchModel.getResourceModel().getCreatedBy());
				session.saveOrUpdate(resourceModel);			
				
				branchModel.setResourceModel(resourceModel);
				session.saveOrUpdate(branchModel);	
				
				organizationModel.setNoOfBranches(organizationModel.getNoOfBranches()+1);
				
				session.saveOrUpdate(organizationModel);
				
				session.getTransaction().commit();
				    success = true;

			}catch(Exception e){
					
				session.cancelQuery();
				
			}finally{
				session.close();
			}
			return success;
			
			
			
			
		}
		
		@SuppressWarnings("unused")
		public boolean  updateBranch(BranchModel branchModel) {
			  boolean success = false;  			
			  BranchModel branchModel1 =null;			
			     Session session= sessionFactory.openSession();
				     session.beginTransaction();
					try{				
						branchModel1 = new BranchModel();
						branchModel1 = branchModel;
						
						AddressModel addressModel = new AddressModel();
						
						RoleModel roleModel = (RoleModel)session.get(RoleModel.class, branchModel.getResourceModel().getRoleModel().getId());
						
						RoleModel creatorRoleModel =(RoleModel)session.get(RoleModel.class, branchModel.getResourceModel().getCreatorRoleModel().getId());
						
						StatusModel accountStatusModel = (StatusModel)session.get(StatusModel.class, branchModel.getResourceModel().getAccountStatusModel().getId());
						
						StatusModel loginStatusModel = (StatusModel)session.get(StatusModel.class, branchModel.getResourceModel().getLoginStatusModel().getId());
						
						addressModel = branchModel.getResourceModel().getAddressModel();
						session.update(addressModel);
						
						ResourceModel resourceModel = new ResourceModel();
						resourceModel.setId(branchModel.getResourceModel().getId());
						resourceModel.setAccountStatusModel(accountStatusModel);
						resourceModel.setLoginStatusModel(loginStatusModel);
						resourceModel.setCreatorRoleModel(creatorRoleModel);
						resourceModel.setRoleModel(roleModel);
						resourceModel.setAddressModel(addressModel);	
						resourceModel.setCreatedBy(branchModel.getResourceModel().getCreatedBy());
						
						session.update(resourceModel);
						
						branchModel.setResourceModel(resourceModel);
						
						session.update(branchModel);
																
						session.getTransaction().commit();
						    success = true;
					
			}catch(Exception e){
			}
				
			return success;
		  }    

		@SuppressWarnings({ "unchecked", "unused" })
		public List<BranchModel> getBranchesByOrganization(BranchModel branchModel){
			Session session = sessionFactory.openSession();
			return (List<BranchModel>) sessionFactory.getCurrentSession()
					
					.createCriteria(BranchModel.class, "branch")
					.createAlias("branch.organizationModel", "Org")
					.createAlias("branch.resourceModel", "resource")
					.createAlias("resource.accountStatusModel", "status")
					.add(Restrictions.eq("status.id",branchModel.getResourceModel().getAccountStatusModel().getId()))
					.add(Restrictions.eq("Org.id",branchModel.getOrganizationModel().getId()))
					.list();
		}


		@SuppressWarnings("unchecked")
		public List<BranchModel> getAccountRequestsByBranches(BranchModel branchModel) {
			
			Session hsession=sessionFactory.getCurrentSession();
			hsession.beginTransaction();
			 System.out.println("************ListDAO impl getAccountRequestsByAdmin**********");
			try{
				Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BranchModel.class,"Branch")
						.createAlias("Branch.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")
						.createAlias("Branch.organizationModel", "Org")
						.add(Restrictions.eq("Org.id",branchModel.getOrganizationModel().getId()))
						.add(Restrictions.eq("AC_STATUS.id",branchModel.getResourceModel().getAccountStatusModel().getId()));
				
				return criteria.list();
			}catch(Exception e){
				
			return null;
			}
		}
		
		public BranchModel getBranchbyId(BranchModel BranchModel) {
			return (BranchModel) sessionFactory.getCurrentSession().get(BranchModel.class, BranchModel.getId());
		}

		

		public boolean changeBranchStatus(BranchModel branchModel) {
			boolean success = false;
			Session hsession=sessionFactory.getCurrentSession();
			hsession.beginTransaction();
			try{
			Query query1=hsession.createSQLQuery(" UPDATE rmbs.rmbs106 SET rmbs10601="+branchModel.getResourceModel().getId()
					 +", AC_ST_rmbs80301='"+branchModel.getResourceModel().getAccountStatusModel().getId()+"'"
				       +" where rmbs10601="+branchModel.getResourceModel().getId()+";");
			
			query1.executeUpdate();
			hsession.getTransaction().commit();
			success = true;
			}catch(Exception e){
				
				hsession.cancelQuery();
				
			}
			return success;
			
		}

		@SuppressWarnings("rawtypes")
		public BranchModel loginValidate(BranchModel branchModel) {
			 Session session = sessionFactory.getCurrentSession();
			 session.beginTransaction();
					 ArrayList list=(ArrayList)session.createQuery("FROM BranchModel WHERE RMBS10202 = '"+branchModel.getUserName()+"' and password = '"+branchModel.getPassword()+"'")
					 .list();
				session.getTransaction().commit();	 
				BranchModel branchModel1=null;
			 if(!list.isEmpty()){
				 branchModel1=(BranchModel)list.get(0);
			 }
			 session.close();
			return branchModel1;
		}

		public int upadtePasswordByIdOfBranch(BranchModel branchModel) {
			Session session=sessionFactory.openSession();
			session.beginTransaction();
			int result=0;
			try {
			Query query1=session.createSQLQuery("UPDATE rmbs102 SET rmbs10201 ="+branchModel.getId()
					+", rmbs10207='"+branchModel.getPassword()+"' where rmbs10201="+branchModel.getId()+";");

			result=query1.executeUpdate();
			} catch (Exception e) {
				session.cancelQuery();
			}

			session.getTransaction().commit();
			session.close();
			return result;
		}


		
		@Override
		public Long countAll(BranchModel branchModel) {
			 Object obj = sessionFactory.getCurrentSession().createCriteria(BranchModel.class,"branch")				 
					 	.createAlias("branch.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")						
						.add(Restrictions.eq("AC_STATUS.id",branchModel.getResourceModel().getAccountStatusModel().getId()))
						.setProjection(Projections.rowCount()).uniqueResult();
		
			 return ((Number) obj).longValue();
		}


		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List<BranchModel> listByPage(Class clazz, int firstResult,
				int maxResult, BranchModel branchModel) {
			
			Session hsession=sessionFactory.getCurrentSession();
			hsession.beginTransaction();
			 System.out.println("************ListDAO impl getAccountRequestsByAdmin**********");
			try{
				Criteria criteria = sessionFactory.getCurrentSession().createCriteria(BranchModel.class,"Branch")
						.createAlias("Branch.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")
						.createAlias("Branch.organizationModel", "Org")
						.add(Restrictions.eq("Org.id",branchModel.getOrganizationModel().getId()))
						.add(Restrictions.eq("AC_STATUS.id",branchModel.getResourceModel().getAccountStatusModel().getId()))
						.setFirstResult(firstResult)
						.setMaxResults(maxResult)
						.addOrder(Order.desc("Branch.id"));
				
				return criteria.list();
			}catch(Exception e){
				
			return null;
			}
			
		}

		
		

		
		

		
		

		
		

		
		

		

		

	

		

		
}
