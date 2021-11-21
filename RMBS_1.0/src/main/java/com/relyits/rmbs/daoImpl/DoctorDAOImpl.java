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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.DoctorDAO;
import com.relyits.rmbs.model.refference.AddressModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.DoctorModel;
import com.relyits.rmbs.model.registration.ResourceModel;

@Repository("doctorDAO")
public class DoctorDAOImpl implements DoctorDAO{

	@Autowired
	private SessionFactory sessionFactory;

	 public Long countAll() {
		 Object obj = sessionFactory.getCurrentSession()
                 .createQuery("select count(id) from DoctorModel" ).uniqueResult();

return (Long) obj;
	    }
	 
	 public Long countAll(DoctorModel doctorModel) {
		 
		 if(doctorModel.getResourceModel().getAccountStatusModel().getId()==0){	
			 Object obj=sessionFactory.getCurrentSession().createCriteria(DoctorModel.class)
					 
					 .setProjection(Projections.rowCount()).uniqueResult();
			 
			 return ((Number) obj).longValue();		    
		 
			 
		 }else if(doctorModel.getResourceModel().getAccountStatusModel().getId()==7){
			 Object obj = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor")				 
					 	.createAlias("doctor.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")
						.add(Restrictions.ne("AC_STATUS.id",doctorModel.getResourceModel().getAccountStatusModel().getId()))
						.setProjection(Projections.rowCount()).uniqueResult();
		
			 return ((Number) obj).longValue();
		 }
		 
		 else{	
			 
			 Object obj = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor")				 
					 	.createAlias("doctor.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")
						.add(Restrictions.eq("AC_STATUS.id",doctorModel.getResourceModel().getAccountStatusModel().getId()))
						.setProjection(Projections.rowCount()).uniqueResult();
		
			 return ((Number) obj).longValue();
			 }
	    }

	 public List listByPage(Class clazz, int firstResult, int maxResult) {
	    /*    String strQry = "from " + clazz.getName() + " c order by c.id desc";

	        Query query = this.sessionFactory.getCurrentSession().createQuery(strQry);
	        query.setFirstResult(firstResult);
	        query.setMaxResults(maxResult);

	        return query.list();*/
		 
		 Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor")
					.setFirstResult(firstResult)
					.setMaxResults(maxResult) 
     	        .addOrder(Order.desc("doctor.id")); 
	 	        return criteria.list();	
	    }
	 
	 public List listByPage(Class clazz, int firstResult, int maxResult,DoctorModel doctorModel) {	     

	        if(doctorModel.getResourceModel().getAccountStatusModel().getId()==0){
	        	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor")
						.setFirstResult(firstResult)
						.setMaxResults(maxResult) 
	        	        .addOrder(Order.desc("doctor.id")); 
		 	        return criteria.list();	        
	        
	        }else if(doctorModel.getResourceModel().getAccountStatusModel().getId()==7){
	        	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor")

						.createAlias("doctor.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")
						.add(Restrictions.ne("AC_STATUS.id",doctorModel.getResourceModel().getAccountStatusModel().getId()))
						.setFirstResult(firstResult)
						.setMaxResults(maxResult)
						.addOrder(Order.desc("doctor.id"))
						
						;   	       

		        return criteria.list();
	        }else{
	        	
	        	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor")

						.createAlias("doctor.resourceModel", "Resource")
						.createAlias("Resource.accountStatusModel", "AC_STATUS")
						.add(Restrictions.eq("AC_STATUS.id",doctorModel.getResourceModel().getAccountStatusModel().getId()))
						.setFirstResult(firstResult)
						.setMaxResults(maxResult)
						.addOrder(Order.desc("doctor.id"))
						
						;   	       

		        return criteria.list();
	        	
	        }
	        
	    }

	public boolean registerDoctor(DoctorModel doctorModel) {
		boolean success = false;  
		DoctorModel doctorModel1 =null;
		Session session= sessionFactory.openSession();
		session.beginTransaction();

		doctorModel1 = new DoctorModel();
		doctorModel1=doctorModel;

		AddressModel addressModel = new AddressModel();

		RoleModel roleModel = (RoleModel)session.get(RoleModel.class, doctorModel.getResourceModel().getRoleModel().getId());

		RoleModel creatorRoleModel =(RoleModel)session.get(RoleModel.class, doctorModel.getResourceModel().getCreatorRoleModel().getId());

		StatusModel accountStatusModel = (StatusModel)session.get(StatusModel.class, doctorModel.getResourceModel().getAccountStatusModel().getId());

		StatusModel loginStatusModel = (StatusModel)session.get(StatusModel.class, doctorModel.getResourceModel().getLoginStatusModel().getId());

		addressModel = doctorModel.getResourceModel().getAddressModel();
		session.saveOrUpdate(addressModel);

		ResourceModel resourceModel = new ResourceModel();
		resourceModel.setId(doctorModel.getResourceModel().getId());
		resourceModel.setAccountStatusModel(accountStatusModel);
		resourceModel.setLoginStatusModel(loginStatusModel);
		resourceModel.setCreatorRoleModel(creatorRoleModel);
		resourceModel.setRoleModel(roleModel);
		resourceModel.setAddressModel(addressModel);	
		resourceModel.setCreatedBy(doctorModel.getResourceModel().getCreatedBy());

		session.saveOrUpdate(resourceModel);			


		doctorModel1.setResourceModel(resourceModel);

		session.saveOrUpdate(doctorModel1);	

		session.getTransaction().commit();
		success = true;

		session.cancelQuery();

		return success;

	}	




	@SuppressWarnings("unchecked")
	public List<DoctorModel> listDoctors() {

		return (List<DoctorModel>) sessionFactory.getCurrentSession().createCriteria(DoctorModel.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<DoctorModel> listDoctorsByCreator(DoctorModel doctorModel) {
		try{
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor");
			return criteria.list();
		}catch(Exception e){
			return null;
		}
	}


	@SuppressWarnings("rawtypes")
	public DoctorModel getDoctor(DoctorModel doctorModel) {
		Session session = sessionFactory.openSession();
		if(doctorModel.getId()!=null){
			return (DoctorModel) sessionFactory.getCurrentSession().get(DoctorModel.class, doctorModel.getId());
		}else{

			Criteria criteria = session.createCriteria(DoctorModel.class,"doctor")
					.createAlias("doctor.resourceModel", "Resource")
					.createAlias("Resource.accountStatusModel", "AC_STATUS")
					.add(Restrictions.eq("Resource.createdBy",doctorModel.getResourceModel().getCreatedBy()))
					.add(Restrictions.eq("doctorName",doctorModel.getDoctorName()))			        
					.add(Restrictions.eq("AC_STATUS.id",doctorModel.getResourceModel().getAccountStatusModel().getId()));
			List doctors = criteria.list();
			DoctorModel doctorModel1 = null;
			for(int i=0;i<doctors.size();i++){
				doctorModel1=(DoctorModel) doctors.get(i);
			}

			return doctorModel1;
		}

	}



	public void disableDoctor(DoctorModel doctorModel) {
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();
		
		Query query = hsession.createSQLQuery(
				"CALL UpdateAccountStatus(:flag,:id)")
				.addEntity(DoctorModel.class)
				.setParameter("flag", doctorModel.getResourceModel().getAccountStatusModel().getId())
				.setParameter("id", doctorModel.getResourceModel().getId());
		query.executeUpdate();
		hsession.getTransaction().commit();

	}

	public void deleteDoctor(DoctorModel doctorModel) {
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();
		Query query = hsession.createSQLQuery(
				"CALL UpdateAccountStatus(:flag,:id)")
				.addEntity(DoctorModel.class)
				.setParameter("flag", doctorModel.getResourceModel().getAccountStatusModel().getId())
				.setParameter("id", doctorModel.getResourceModel().getId());
		query.executeUpdate();
		hsession.getTransaction().commit();

	}

	public void enableDoctor(DoctorModel doctorModel) {
		Session hsession=sessionFactory.getCurrentSession();
		hsession.beginTransaction();
	Query query = hsession.createSQLQuery(
				"CALL UpdateAccountStatus(:flag,:id)")
				.addEntity(DoctorModel.class)
				.setParameter("flag", doctorModel.getResourceModel().getAccountStatusModel().getId())
				.setParameter("id", doctorModel.getResourceModel().getId());
		query.executeUpdate();
		hsession.getTransaction().commit();
	}

	public void enableAllDoctors(DoctorModel doctorModel) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		ScrollableResults doctors = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor")
				.createAlias("doctor.resourceModel", "Resource")
				.createAlias("Resource.accountStatusModel", "AC_STATUS")
				.add(Restrictions.eq("Resource.createdBy",doctorModel.getResourceModel().getCreatedBy()))
				.add(Restrictions.ne("AC_STATUS.id",doctorModel.getResourceModel().getAccountStatusModel().getId()))
				.setCacheMode(CacheMode.IGNORE)
				.scroll(ScrollMode.FORWARD_ONLY);

		while ( doctors.next() ) {
			DoctorModel doctorModel1 = (DoctorModel) doctors.get(0);
			StatusModel accountStatusModel = new StatusModel();
			ResourceModel resourceModel = new ResourceModel();

			resourceModel = doctorModel1.getResourceModel();
			accountStatusModel.setId(doctorModel.getResourceModel().getAccountStatusModel().getId());
			resourceModel.setAccountStatusModel(accountStatusModel);
			doctorModel1.setResourceModel(resourceModel);

			enableDoctor(doctorModel1);
		}

		tx.commit();
		session.close();

	}

	public void disableAllDoctors(DoctorModel doctorModel) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		ScrollableResults doctors = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor")
				.createAlias("doctor.resourceModel", "Resource")
				.createAlias("Resource.accountStatusModel", "AC_STATUS")
				.add(Restrictions.eq("Resource.createdBy",doctorModel.getResourceModel().getCreatedBy()))
				.add(Restrictions.ne("AC_STATUS.id",doctorModel.getResourceModel().getAccountStatusModel().getId()))
				.setCacheMode(CacheMode.IGNORE)
				.scroll(ScrollMode.FORWARD_ONLY);

		while ( doctors.next() ) {
			DoctorModel doctorModel1 = (DoctorModel) doctors.get(0);
			StatusModel accountStatusModel = new StatusModel();
			ResourceModel resourceModel = new ResourceModel();

			resourceModel = doctorModel1.getResourceModel();
			accountStatusModel.setId(doctorModel.getResourceModel().getAccountStatusModel().getId());
			resourceModel.setAccountStatusModel(accountStatusModel);
			doctorModel1.setResourceModel(resourceModel);

			disableDoctor(doctorModel1);

		}

		tx.commit();
		session.close();
	}

	public void deleteAllDoctors(DoctorModel doctorModel) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		ScrollableResults doctors = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor")
				.createAlias("doctor.resourceModel", "Resource")
				.createAlias("Resource.accountStatusModel", "AC_STATUS")
				.add(Restrictions.eq("Resource.createdBy",doctorModel.getResourceModel().getCreatedBy()))
				.add(Restrictions.ne("AC_STATUS.id",doctorModel.getResourceModel().getAccountStatusModel().getId()))
				.setCacheMode(CacheMode.IGNORE)
				.scroll(ScrollMode.FORWARD_ONLY);

		while ( doctors.next() ) {
			DoctorModel doctorModel1 = (DoctorModel) doctors.get(0);
			StatusModel accountStatusModel = new StatusModel();
			ResourceModel resourceModel = new ResourceModel();

			resourceModel = doctorModel1.getResourceModel();
			accountStatusModel.setId(doctorModel.getResourceModel().getAccountStatusModel().getId());
			resourceModel.setAccountStatusModel(accountStatusModel);
			doctorModel1.setResourceModel(resourceModel);

			deleteDoctor(doctorModel1);			

		}

		tx.commit();
		session.close();


	}

	@SuppressWarnings("unchecked")
	public List<DoctorModel> listDoctorsByCategory(DoctorModel doctorModel) {
		try{
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DoctorModel.class,"doctor")
					.createAlias("doctor.resourceModel", "Resource")
					.createAlias("Resource.accountStatusModel", "AC_STATUS")
					.add(Restrictions.eq("AC_STATUS.id",doctorModel.getResourceModel().getAccountStatusModel().getId()));
			return criteria.list();
		}catch(Exception e){
			return null;	
		}
	}

	

}
