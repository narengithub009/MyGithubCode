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

import com.relyits.rmbs.dao.OrganizationDAO;
import com.relyits.rmbs.model.menu.ChildMenuModel;
import com.relyits.rmbs.model.menu.MenuModel;
import com.relyits.rmbs.model.refference.AddressModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.registration.ResourceModel;


/**
 * @author Amar Rao Errabelli
 *
 */
@Repository("organizationDAO")
public class OrganizationDAOImpl implements OrganizationDAO {

	@Autowired
	private SessionFactory sessionFactory;

	
	@SuppressWarnings("rawtypes")
	public OrganizationModel checkAvailabilty(OrganizationModel organizationModel) {
		Session session = sessionFactory.getCurrentSession();
		 session.beginTransaction();
		 OrganizationModel organizationModel1=null;
		try{
				 ArrayList list=(ArrayList)session.createQuery("FROM OrganizationModel WHERE RMBS10102 = '"+organizationModel.getUserName()+"'")
				 .list();
			session.getTransaction().commit();	 
				
		 if(!list.isEmpty()){
			 organizationModel1=(OrganizationModel)list.get(0);
		 }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		 return organizationModel1; 
	 }
	
	
	public boolean  createOraganization(OrganizationModel organizationModel) {
		boolean success = false;   
		 Session session = sessionFactory.openSession();
         
	        session.beginTransaction();  
		try {
				
	 RoleModel roleModel=(RoleModel) session.get(RoleModel.class, organizationModel.getResourceModel().getRoleModel().getId());
			 
    RoleModel creatorRoleModel=(RoleModel) session.get(RoleModel.class, organizationModel.getResourceModel().getCreatorRoleModel().getId());
		
	StatusModel accountStatusModel=(StatusModel) session.get(StatusModel.class, organizationModel.getResourceModel().getAccountStatusModel().getId());	
	
	StatusModel loginStatusModel=(StatusModel) session.get(StatusModel.class, organizationModel.getResourceModel().getLoginStatusModel().getId());
	
	AddressModel addressModel=organizationModel.getResourceModel().getAddressModel();
	 session.save(addressModel);
	
	ResourceModel resourceModel = new ResourceModel();
	resourceModel.setAccountStatusModel(accountStatusModel);
	resourceModel.setLoginStatusModel(loginStatusModel);
	resourceModel.setCreatorRoleModel(creatorRoleModel);
	resourceModel.setRoleModel(roleModel);
	resourceModel.setAddressModel(addressModel);		 
			 
	session.save(resourceModel);
	
	organizationModel.setResourceModel(resourceModel);
	
	session.save(organizationModel);
			 
	session.getTransaction().commit();
			success = true;
		} catch (Exception e) {
			session.cancelQuery();
		} 
		session.close();
		return success ;
	}
	
	@SuppressWarnings("unused")
	public boolean  updateOraganization(OrganizationModel organizationModel) {
			  boolean success = false;  			
			  OrganizationModel organizationModel1 =null;			
			     Session session= sessionFactory.openSession();
				     session.beginTransaction();
					try{				
						organizationModel1 = new OrganizationModel();
						organizationModel1 = organizationModel;
						
						AddressModel addressModel = new AddressModel();
						
						RoleModel roleModel = (RoleModel)session.get(RoleModel.class, organizationModel.getResourceModel().getRoleModel().getId());
						
						RoleModel creatorRoleModel =(RoleModel)session.get(RoleModel.class, organizationModel.getResourceModel().getCreatorRoleModel().getId());
						
						StatusModel accountStatusModel = (StatusModel)session.get(StatusModel.class, organizationModel.getResourceModel().getAccountStatusModel().getId());
						
						StatusModel loginStatusModel = (StatusModel)session.get(StatusModel.class, organizationModel.getResourceModel().getLoginStatusModel().getId());
						
						addressModel = organizationModel.getResourceModel().getAddressModel();
						session.saveOrUpdate(addressModel);
						
						ResourceModel resourceModel = new ResourceModel();
						resourceModel.setId(organizationModel.getResourceModel().getId());
						resourceModel.setAccountStatusModel(accountStatusModel);
						resourceModel.setLoginStatusModel(loginStatusModel);
						resourceModel.setCreatorRoleModel(creatorRoleModel);
						resourceModel.setRoleModel(roleModel);
						resourceModel.setAddressModel(addressModel);	
						resourceModel.setCreatedBy(organizationModel.getResourceModel().getCreatedBy());
						
						session.saveOrUpdate(resourceModel);
						
						organizationModel.setResourceModel(resourceModel);
						
						session.saveOrUpdate(organizationModel);
																
						session.getTransaction().commit();
						    success = true;
					
			}catch(Exception e){
				
			}
				
			return success;
		  }  
	
	
	 @SuppressWarnings("rawtypes")
	public OrganizationModel loginValidate(OrganizationModel organizationModel){
		 Session session = sessionFactory.getCurrentSession();
		 session.beginTransaction();
				 ArrayList list=(ArrayList)session.createQuery("FROM OrganizationModel WHERE RMBS10102 = '"+organizationModel.getUserName()+"' and password = '"+organizationModel.getPassword()+"'")
				 .list();
			session.getTransaction().commit();	 
		 OrganizationModel organizationModel1=null;
		 if(!list.isEmpty()){
			 organizationModel1=(OrganizationModel)list.get(0);
		 }
		 session.close();
		 return organizationModel1; 
	 }
	
	@SuppressWarnings("unchecked")
	public List<OrganizationModel> getlistOfOrganizations() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(OrganizationModel.class);
		
		
		session.close();
		return criteria.list();
	}

	public OrganizationModel getUser(int userid) {
		Session session = sessionFactory.getCurrentSession();
		OrganizationModel organizationModel=(OrganizationModel) session.get(OrganizationModel.class, userid);
		return organizationModel;
	}

	public void deleteUser(OrganizationModel organizationModel) {
		Session session = sessionFactory.getCurrentSession();
		session.createQuery("DELETE FROM Employee WHERE empid = "+organizationModel.getId()).executeUpdate();
	  session.close();
	}

	@SuppressWarnings("unchecked")
	public List<OrganizationModel> roleTwoUsersList(OrganizationModel organizationModel) {
		return (List<OrganizationModel>) sessionFactory.getCurrentSession()
				.createCriteria(OrganizationModel.class)
				.add(Restrictions.ne(""+2+"",""+organizationModel.getResourceModel().getAccountStatusModel().getId()+""))
				.list();
		
	}
	public List<OrganizationModel> roleFourUsersList(OrganizationModel organizationModel) {
		
		return null;
	}


	public OrganizationModel getOrganizationbyId(OrganizationModel organizationModel) {
		return (OrganizationModel) sessionFactory.getCurrentSession().get(OrganizationModel.class, organizationModel.getId());
	}
	
	@SuppressWarnings("unchecked")
	public List<OrganizationModel> getAccountRequestsByOrganizations(OrganizationModel organizationModel) {
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();
		try{
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OrganizationModel.class,"Org")
					.createAlias("Org.resourceModel", "Resource")
					.createAlias("Resource.accountStatusModel", "AC_STATUS")
					.add(Restrictions.eq("AC_STATUS.id",organizationModel.getResourceModel().getAccountStatusModel().getId()));
					
			
			return criteria.list();
		}catch(Exception e){
			
		return null;
		}
	}
    

	public int changeOrganizationActiveStatus(OrganizationModel organizationModel) {
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();
		int returnResult=0;
	try{
		Query query1=hsession.createSQLQuery(" UPDATE RMBS.rmbs106 SET rmbs10601="+organizationModel.getResourceModel().getId()
				 +", AC_ST_RMBS80301="+organizationModel.getResourceModel().getAccountStatusModel().getId()
				 +", rmbs10602="+organizationModel.getResourceModel().getCreatedBy()
			       +" where rmbs10601="+organizationModel.getResourceModel().getId()+";");
		
		returnResult=query1.executeUpdate();
		hsession.getTransaction().commit();
		hsession.close();  
			
	}catch(Exception e){
		hsession.cancelQuery();
		hsession.close();
		            
	}
		return returnResult;
	
	}
	
	public int upadtePasswordById(OrganizationModel organizationModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		int result=0;
		try {
		Query query1=session.createSQLQuery("UPDATE rmbs101 SET rmbs10101 ="+organizationModel.getId()
				+", rmbs10107='"+organizationModel.getPassword()+"' where rmbs10101="+organizationModel.getId()+";");

		result=query1.executeUpdate();
		} catch (Exception e) {
			session.cancelQuery();
		}

		session.getTransaction().commit();
		session.close();
		return result;
	}



	//********************menu**********************
	
	@SuppressWarnings("unchecked")
	public List<ChildMenuModel> getUserchildMenu(int[] childMenuOptions) {
		int i=0;
     		int flag=childMenuOptions[i];
     		String query=null;
     		if(flag==1){
     			query="FROM ChildMenuModel WHERE id in (";
		for(i=1;i<childMenuOptions.length;i++){
			if(childMenuOptions[i]!=0){
			query=query+childMenuOptions[i];
			if(childMenuOptions[i+1]!=0){
				query=query+",";
			}
			}
			
		}
		query=query+")";
     		}else{
     			query="FROM ChildMenu";
     		}
		Session session = sessionFactory.getCurrentSession();
		 session.beginTransaction();
		 return (List<ChildMenuModel>)session.createQuery(query)
				 .list();
			
		
	}

    public int[] createUserMenu(MenuModel menuModel) {
	String options=menuModel.getChildMenuIds();	
	if(options!=null){	
		 String[] child = options.split(",");
		 int[] childId = new int[50];
		 int j=0;
		 for (String i : child){
			 System.out.println("id----"+i);
		
			 childId[j]=Integer.parseInt(i);
			 System.out.println("childId[j]----"+childId);
			 j++;
		 }
	}
	return null;
	}


	@Override
	public Long countAll(OrganizationModel organizationModel) {
		 Object obj = sessionFactory.getCurrentSession().createCriteria(OrganizationModel.class,"org")				 
				 	.createAlias("org.resourceModel", "Resource")
					.createAlias("Resource.accountStatusModel", "AC_STATUS")
					.createAlias("Resource.roleModel", "role")
					.add(Restrictions.eq("AC_STATUS.id",organizationModel.getResourceModel().getAccountStatusModel().getId()))
					.add(Restrictions.eq("role.id",organizationModel.getResourceModel().getRoleModel().getId()))
					.setProjection(Projections.rowCount()).uniqueResult();
	System.out.println("Object********"+((Number) obj).longValue());
		 return ((Number) obj).longValue();
	}


	@Override
	public List<OrganizationModel> listByPage(Class clazz, int firstResult,
			int maxResult, OrganizationModel organizationModel) {
		
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(OrganizationModel.class,"org")

				.createAlias("org.resourceModel", "Resource")
				.createAlias("Resource.accountStatusModel", "AC_STATUS")
			//	.createAlias("Resource.roleModel", "role")
				.add(Restrictions.eq("AC_STATUS.id",organizationModel.getResourceModel().getAccountStatusModel().getId()))
			//	.add(Restrictions.eq("role.id",organizationModel.getResourceModel().getRoleModel().getId()))
				.setFirstResult(firstResult)
				.setMaxResults(maxResult)
				.addOrder(Order.desc("org.id"))
				
				;   	       

        return criteria.list();
	}
    
	@Override
	public String getEmailId(OrganizationModel organizationModel) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(OrganizationModel.class,"orgModel")
				.createAlias("orgModel.resourceModel", "resModel")
				.setFetchMode("resModel.addressModel",org.hibernate.FetchMode.JOIN)
				.createAlias("resModel.addressModel", "addModel")
				.setProjection(Projections.projectionList()
						.add(Projections.property("addModel.email"),"email"))
						.add(Restrictions.eq("orgModel.id", organizationModel.getId()));
		
		String orgEmail1 = null;
		List<String> list = criteria.list();
		for(int i=0;i<list.size();i++){
			orgEmail1 = list.get(i);
		}
		session.close();
		
		return orgEmail1;
	}


	@Override
	public String getAdminEmailId(OrganizationModel organizationModel) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(OrganizationModel.class,"orgModel")
				.createAlias("orgModel.resourceModel", "resModel")
				.createAlias("resModel.creatorRoleModel", "creatorModel")
				.setFetchMode("resModel.addressModel", org.hibernate.FetchMode.JOIN)
				.createAlias("resModel.addressModel", "addModel")
				.setProjection(Projections.projectionList()
						.add(Projections.property("addModel.email"),"email"))
						.add(Restrictions.eq("orgModel.id", organizationModel.getId()))
						.add(Restrictions.eq("creatorModel.id", organizationModel.getResourceModel().getCreatorRoleModel().getId()));
		String adminEmailId = null;
		List<String> list = criteria.list();
		session.close();
		for(int i=0;i<list.size();i++){
			adminEmailId = list.get(i);
		}
		return adminEmailId;
	}


	@Override
	public String getBranchMailId(BranchModel branchModel) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BranchModel.class,"branchModel")
							.createAlias("branchModel.resourceModel", "resModel")
							.setFetchMode("resModel.addressModel", org.hibernate.FetchMode.JOIN)
							.createAlias("resModel.addressModel", "addModel")
							.createAlias("branchModel.organizationModel", "orgModel")
							.setProjection(Projections.projectionList()
									.add(Projections.property("addModel.email"),"email"))
									.add(Restrictions.eq("branchModel.id", branchModel.getId()))
									.add(Restrictions.eq("orgModel.id", branchModel.getOrganizationModel().getId()));
		String mailId = null;
		List<String> list = criteria.list();
		
		for(int i=0;i<list.size();i++){
			mailId = list.get(i);
		}
		return mailId;
	}


	@Override
	public String getOutletEmailId(OutletModel outletModel) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(OutletModel.class,"outModel")
							.createAlias("outModel.resourceModel", "resModel")
							.setFetchMode("resModel.addressModel", org.hibernate.FetchMode.JOIN)
							.createAlias("resModel.addressModel", "addModel")
							.createAlias("outModel.branchModel", "branchModel")
							.setProjection(Projections.projectionList()
									.add(Projections.property("addModel.email"),"email"))
									.add(Restrictions.eq("outModel.id", outletModel.getId()))
									.add(Restrictions.eq("branchModel.id", outletModel.getBranchModel().getId()));
		String mailId = null;
		List<String> list = criteria.list();
		
		for(int i=0;i<list.size();i++){
			mailId = list.get(i);
		}
		return mailId;
	}
 }
