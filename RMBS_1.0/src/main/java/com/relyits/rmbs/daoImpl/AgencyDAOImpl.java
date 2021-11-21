package com.relyits.rmbs.daoImpl;

import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.AgencyDAO;
import com.relyits.rmbs.model.refference.AddressModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.DoctorModel;
import com.relyits.rmbs.model.registration.ResourceModel;

@Repository("agencyDAO")
public class AgencyDAOImpl implements AgencyDAO {

	@Autowired
	private SessionFactory sessionFactory;
   
	 public Long countAll() {
		 Object obj = sessionFactory.getCurrentSession()
                 .createQuery("select count(id) from AgencyModel" ).uniqueResult();

return (Long) obj;
	    }
	 
	 public Long countAll(AgencyModel agencyModel) {
		 
		 if(agencyModel.getResourceModel().getAccountStatusModel().getId()==0){	
			 Object obj=sessionFactory.getCurrentSession().createCriteria(AgencyModel.class)
					 
					 .setProjection(Projections.rowCount()).uniqueResult();
			 
			 return ((Number) obj).longValue();		    
		 
			 
		 }else if(agencyModel.getResourceModel().getAccountStatusModel().getId()==7){
			 Object obj = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"agency")				 
					 	.createAlias("agency.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")
						.add(Restrictions.ne("AC_STATUS.id",agencyModel.getResourceModel().getAccountStatusModel().getId()))
						.setProjection(Projections.rowCount()).uniqueResult();
		
			 return ((Number) obj).longValue();
			 
		 }else{	
			 
			 Object obj = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"agency")				 
					 	.createAlias("agency.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")
						.add(Restrictions.eq("AC_STATUS.id",agencyModel.getResourceModel().getAccountStatusModel().getId()))
						.setProjection(Projections.rowCount()).uniqueResult();
		
			 return ((Number) obj).longValue();
			 }
	    }

	 public List listByPage(Class clazz, int firstResult, int maxResult) {
	   /*     String strQry = "from " + clazz.getName() + " c order by c.id desc";

	        Query query = this.sessionFactory.getCurrentSession().createQuery(strQry);
	        query.setFirstResult(firstResult);
	        query.setMaxResults(maxResult);

	        return query.list();*/
		 
		 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"Agency")
					.setFirstResult(firstResult)
					.setMaxResults(maxResult)
					.addOrder(Order.desc("Agency.id"));  

	 	        return criteria.list();	   
	    }
	 
	 public List listByPage(Class clazz, int firstResult, int maxResult,AgencyModel agencyModel) {	     

	        if(agencyModel.getResourceModel().getAccountStatusModel().getId()==0){
	        	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"Agency")
						.setFirstResult(firstResult)
						.setMaxResults(maxResult)
						.addOrder(Order.desc("Agency.id"));  

		 	        return criteria.list();	        
	        
	        }else if(agencyModel.getResourceModel().getAccountStatusModel().getId()==7){
	        	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"Agency")

						.createAlias("Agency.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")
						.add(Restrictions.ne("AC_STATUS.id",agencyModel.getResourceModel().getAccountStatusModel().getId()))
						.setFirstResult(firstResult)
						.setMaxResults(maxResult)
						.addOrder(Order.desc("Agency.id"))
						
						;   	       

		        return criteria.list();
				 
			 }else{
	        	
	        	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"Agency")

						.createAlias("Agency.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")
						.add(Restrictions.eq("AC_STATUS.id",agencyModel.getResourceModel().getAccountStatusModel().getId()))
						.setFirstResult(firstResult)
						.setMaxResults(maxResult)
						.addOrder(Order.desc("Agency.id"))
						
						;   	       

		        return criteria.list();
	        	
	        }
	        
	    }
	 
	@SuppressWarnings("unused")
	public boolean registerAgency(AgencyModel agencyModel){
		boolean success = false;  
		AgencyModel agencyModel1 =null;
		Session session= sessionFactory.openSession();
		session.beginTransaction();
		try{
			agencyModel1 = new AgencyModel();
			agencyModel1 = agencyModel;

			AddressModel addressModel = new AddressModel();

			RoleModel roleModel = (RoleModel)session.get(RoleModel.class, agencyModel.getResourceModel().getRoleModel().getId());

			RoleModel creatorRoleModel =(RoleModel)session.get(RoleModel.class, agencyModel.getResourceModel().getCreatorRoleModel().getId());

			StatusModel accountStatusModel = (StatusModel)session.get(StatusModel.class, agencyModel.getResourceModel().getAccountStatusModel().getId());

			StatusModel loginStatusModel = (StatusModel)session.get(StatusModel.class, agencyModel.getResourceModel().getLoginStatusModel().getId());

			addressModel = agencyModel.getResourceModel().getAddressModel();
			session.saveOrUpdate(addressModel);

			ResourceModel resourceModel = new ResourceModel();
			resourceModel.setId(agencyModel.getResourceModel().getId());
			resourceModel.setAccountStatusModel(accountStatusModel);
			resourceModel.setLoginStatusModel(loginStatusModel);
			resourceModel.setCreatorRoleModel(creatorRoleModel);
			resourceModel.setRoleModel(roleModel);
			resourceModel.setAddressModel(addressModel);	
			resourceModel.setCreatedBy(agencyModel.getResourceModel().getCreatedBy());

			session.saveOrUpdate(resourceModel);

			agencyModel.setResourceModel(resourceModel);

			session.saveOrUpdate(agencyModel);

			session.getTransaction().commit();
			success = true;


		}catch(Exception e){
			e.printStackTrace();
		}

		return success;
	}    
	@SuppressWarnings("unchecked")
	public List<AgencyModel> listAgencies(){
		return (List<AgencyModel>) sessionFactory.getCurrentSession().createCriteria(AgencyModel.class).list();

	}

	@SuppressWarnings("unchecked")
	public List<AgencyModel> listAgenciesByCreator(AgencyModel agencyModel){
		try{
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"agency");
			return criteria.list();
		}catch(Exception e){			

			return null;
		}
	}



	@SuppressWarnings("unchecked")
	public List<AgencyModel> listAgenciesByCategory(AgencyModel agencyModel) {
		try{
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"Agency")

					.createAlias("Agency.resourceModel", "Resource")
					.createAlias("Resource.accountStatusModel", "AC_STATUS")
					.add(Restrictions.eq("AC_STATUS.id",agencyModel.getResourceModel().getAccountStatusModel().getId()));
			;
			System.out.println("**********criteria.list()*********"+criteria.list().size());
			return criteria.list();
		}catch(Exception e){

			return null;	
		}
	}

	@SuppressWarnings("rawtypes")
	public AgencyModel getAgency(AgencyModel agencyModel){
		Session session = sessionFactory.openSession();
		if(agencyModel.getId()!=null){
			return (AgencyModel) sessionFactory.getCurrentSession().get(AgencyModel.class, agencyModel.getId());
		}else{

			Criteria criteria = session.createCriteria(AgencyModel.class,"agency")
					.createAlias("agency.resourceModel", "Resource")
					.createAlias("Resource.accountStatusModel", "AC_STATUS")
					.add(Restrictions.eq("Resource.createdBy",agencyModel.getResourceModel().getCreatedBy()))
					.add(Restrictions.eq("agencyName",agencyModel.getAgencyName()))		        
					.add(Restrictions.eq("AC_STATUS.id",agencyModel.getResourceModel().getAccountStatusModel().getId()));

			List agencies = criteria.list();
			AgencyModel agencyModel1 = null;
			for(int i=0;i<agencies.size();i++){
				agencyModel1=(AgencyModel) agencies.get(i);
			}

			return agencyModel1;
		}
	}	
	@SuppressWarnings("rawtypes")
	public AgencyModel validateAgency(AgencyModel agencyModel){
		Session session = sessionFactory.openSession();
		if(agencyModel.getId()!=null){
			return (AgencyModel) sessionFactory.getCurrentSession().get(AgencyModel.class, agencyModel.getId());
		}else{

			Criteria criteria = session.createCriteria(AgencyModel.class,"agency")
					.createAlias("agency.resourceModel", "Resource")
					.createAlias("Resource.accountStatusModel", "AC_STATUS")
					.add(Restrictions.eq("agencyName",agencyModel.getAgencyName()))		        
					.add(Restrictions.ne("AC_STATUS.id",agencyModel.getResourceModel().getAccountStatusModel().getId()));

			List agencies = criteria.list();
			AgencyModel agencyModel1 = null;
			for(int i=0;i<agencies.size();i++){
				agencyModel1=(AgencyModel) agencies.get(i);
			}

			return agencyModel1;
		}
	}	

	public void deleteAgency(AgencyModel agencyModel){
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();

		Query query = hsession.createSQLQuery(
				"CALL UpdateAccountStatus(:flag,:id)")
				.addEntity(DoctorModel.class)
				.setParameter("flag", agencyModel.getResourceModel().getAccountStatusModel().getId())
				.setParameter("id", agencyModel.getResourceModel().getId());
		query.executeUpdate();
		hsession.getTransaction().commit();
	}

	public void disableAgency(AgencyModel agencyModel) {
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();

		Query query = hsession.createSQLQuery(
				"CALL UpdateAccountStatus(:flag,:id)")
				.addEntity(DoctorModel.class)
				.setParameter("flag", agencyModel.getResourceModel().getAccountStatusModel().getId())
				.setParameter("id", agencyModel.getResourceModel().getId());
		query.executeUpdate();
		hsession.getTransaction().commit();
	}

	public void enableAgency(AgencyModel agencyModel) {
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();

		Query query = hsession.createSQLQuery(
				"CALL UpdateAccountStatus(:flag,:id)")
				.addEntity(DoctorModel.class)
				.setParameter("flag", agencyModel.getResourceModel().getAccountStatusModel().getId())
				.setParameter("id", agencyModel.getResourceModel().getId());
		query.executeUpdate();
		hsession.getTransaction().commit();
	}



	public void disableAllAgencies(AgencyModel agencyModel) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		ScrollableResults agencies = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"agency")
				.createAlias("agency.resourceModel", "Resource")
				.createAlias("Resource.accountStatusModel", "AC_STATUS")
				.add(Restrictions.eq("Resource.createdBy",agencyModel.getResourceModel().getCreatedBy()))					      
				.add(Restrictions.ne("AC_STATUS.id",agencyModel.getResourceModel().getAccountStatusModel().getId()))
				.setCacheMode(CacheMode.IGNORE)
				.scroll(ScrollMode.FORWARD_ONLY);


		while ( agencies.next() ) {
			AgencyModel agency = (AgencyModel) agencies.get(0);
			StatusModel accountStatusModel = new StatusModel();
			ResourceModel resourceModel = new ResourceModel();

			resourceModel = agency.getResourceModel();
			accountStatusModel.setId(agencyModel.getResourceModel().getAccountStatusModel().getId());
			resourceModel.setAccountStatusModel(accountStatusModel);
			agency.setResourceModel(resourceModel);

			disableAgency(agency);

		}

		tx.commit();
		session.close();

	}



	public void enableAllAgencies(AgencyModel agencyModel) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		ScrollableResults agencies = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"agency")
				.createAlias("agency.resourceModel", "Resource")
				.createAlias("Resource.accountStatusModel", "AC_STATUS")
				.add(Restrictions.eq("Resource.createdBy",agencyModel.getResourceModel().getCreatedBy()))					      
				.add(Restrictions.ne("AC_STATUS.id",agencyModel.getResourceModel().getAccountStatusModel().getId()))
				.setCacheMode(CacheMode.IGNORE)
				.scroll(ScrollMode.FORWARD_ONLY);

		while ( agencies.next() ) {
			AgencyModel agency = (AgencyModel) agencies.get(0);
			StatusModel accountStatusModel = new StatusModel();
			ResourceModel resourceModel = new ResourceModel();

			resourceModel = agency.getResourceModel();
			accountStatusModel.setId(agencyModel.getResourceModel().getAccountStatusModel().getId());
			resourceModel.setAccountStatusModel(accountStatusModel);
			agency.setResourceModel(resourceModel);

			enableAgency(agency);

		}
		tx.commit();
		session.close();

	}





	public void deleteAllAgencies(AgencyModel agencyModel) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		ScrollableResults agencies = sessionFactory.getCurrentSession().createCriteria(AgencyModel.class,"agency")
				.createAlias("agency.resourceModel", "Resource")
				.createAlias("Resource.accountStatusModel", "AC_STATUS")
				.add(Restrictions.eq("Resource.createdBy",agencyModel.getResourceModel().getCreatedBy()))					      
				.add(Restrictions.ne("AC_STATUS.id",agencyModel.getResourceModel().getAccountStatusModel().getId()))
				.setCacheMode(CacheMode.IGNORE)
				.scroll(ScrollMode.FORWARD_ONLY);	

		while ( agencies.next() ) {
			AgencyModel agency = (AgencyModel) agencies.get(0);
			StatusModel accountStatusModel = new StatusModel();
			ResourceModel resourceModel = new ResourceModel();

			resourceModel = agency.getResourceModel();
			accountStatusModel.setId(agencyModel.getResourceModel().getAccountStatusModel().getId());
			resourceModel.setAccountStatusModel(accountStatusModel);
			agency.setResourceModel(resourceModel);

			deleteAgency(agency);

		}

		tx.commit();
		session.close();

	}


}
