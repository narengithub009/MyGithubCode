package com.relyits.rmbs.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.NotificationDAO;
import com.relyits.rmbs.model.notifications.MessageNotificationsModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;

@Repository("notificationDAO")
public class NotificationDAOImpl implements NotificationDAO{

	@Autowired
	private SessionFactory sessionFactory;
	

	@Override
	public boolean saveMessage(MessageNotificationsModel messageNotificationsModel) {
		  boolean success = false;  
		 
				Session session= sessionFactory.openSession();
					session.beginTransaction();
			MessageNotificationsModel messageNotificationsModel1 = null;
			
			messageNotificationsModel1 = new MessageNotificationsModel();
			messageNotificationsModel1 = messageNotificationsModel;
					
			messageNotificationsModel1.setId(messageNotificationsModel.getId());
			messageNotificationsModel1.setCreatedBy(messageNotificationsModel.getCreatedBy());
			messageNotificationsModel1.setStatusModel(messageNotificationsModel.getStatusModel());
			messageNotificationsModel1.setCategoryModel(messageNotificationsModel.getCategoryModel());
			messageNotificationsModel1.setCreatorRoleModel(messageNotificationsModel.getCreatorRoleModel());
			messageNotificationsModel1.setResponsibleRoleModel(messageNotificationsModel.getResponsibleRoleModel());
			messageNotificationsModel1.setMessage(messageNotificationsModel.getMessage());
			messageNotificationsModel1.setMessage(messageNotificationsModel.getMessage());
			
					
			session.saveOrUpdate(messageNotificationsModel1);	
													
			session.getTransaction().commit();
				    success = true;
			
						
				session.cancelQuery();
	
		return success;
				
	}	
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MessageNotificationsModel> listOfMessageNotificationsToAdmin(MessageNotificationsModel messageNotificationsModel) {
		try{
			Session session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(MessageNotificationsModel.class,"message")				
					.createAlias("message.responsibleRoleModel", "responseOf")
					.add(Restrictions.eq("responseOf.id",messageNotificationsModel.getResponsibleRoleModel().getId()))
					.add(Restrictions.eq("message.responsibility",messageNotificationsModel.getResponsibility()));

			
			List list = criteria.list();
			return list;


		}catch(Exception e){

			return null;
		}
	}		
	 @SuppressWarnings({ "unchecked", "unused" })
		public List<OrganizationModel> getAdminByRole(OrganizationModel organizationModel){
			Session session = sessionFactory.openSession();
			return (List<OrganizationModel>) sessionFactory.getCurrentSession()
					
					.createCriteria(OrganizationModel.class, "org")
					.createAlias("org.resourceModel", "resource")
					.createAlias("resource.roleModel", "role")				
					.add(Restrictions.eq("role.id",organizationModel.getResourceModel().getRoleModel().getId()))
					.list();
		}

		public int updateMessageStatus(List<MessageNotificationsModel> messageNotificationsModels) {
			Session hsession=sessionFactory.getCurrentSession();
			hsession.beginTransaction();			
			int i = 0;
		for(MessageNotificationsModel messageNotificationsModel : messageNotificationsModels){
			Query query = hsession.createSQLQuery(
					"CALL UpdateMessageStatus(:flag,:id)")
					.addEntity(MessageNotificationsModel.class)
					.setParameter("flag", messageNotificationsModel.getStatusModel().getId())
			         .setParameter("id", messageNotificationsModel.getId());
			i=query.executeUpdate();
		}
			hsession.getTransaction().commit();
			return i;
			
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<MessageNotificationsModel> listOfMessages(MessageNotificationsModel messageNotificationsModel) {
			try{
			
					Criteria criteria = sessionFactory.getCurrentSession().createCriteria(MessageNotificationsModel.class);
					return criteria.list();
				}catch(Exception e){
				
				return null;
		}
		
		}
	
		 @SuppressWarnings({ "unchecked", "unused" })
			public List<OrganizationModel> getOrganizationOfselectedBranch(OrganizationModel organizationModel){
				Session session = sessionFactory.openSession();
				return (List<OrganizationModel>) sessionFactory.getCurrentSession()
						
						.createCriteria(OrganizationModel.class, "org")
						.createAlias("org.resourceModel", "resource")
						.createAlias("resource.roleModel", "role")				
						.add(Restrictions.eq("role.id",organizationModel.getResourceModel().getRoleModel().getId()))
						.add(Restrictions.eq("org.id",organizationModel.getId()))
						.list();
			}
	
		 @SuppressWarnings({ "unchecked", "unused" })
			public List<BranchModel> getBranchOfselectedOutlet(BranchModel branchModel){
				Session session = sessionFactory.openSession();
				return (List<BranchModel>) sessionFactory.getCurrentSession()
						
						.createCriteria(BranchModel.class, "branch")
						.createAlias("branch.resourceModel", "resource")
						.createAlias("resource.roleModel", "role")				
						.add(Restrictions.eq("role.id",branchModel.getResourceModel().getRoleModel().getId()))
						.add(Restrictions.eq("branch.id",branchModel.getId()))
						.list();
			}
	

}
