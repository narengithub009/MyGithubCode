package com.relyits.rmbs.beans_preparation.resources;


import com.relyits.rmbs.beans.registration.OrganizationBean;
import com.relyits.rmbs.beans.registration.ResourceBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.OrganizationBeanPreparation;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;

public class SessionBeanPreparation {

	
	private static UserSessionBean userSessionBean=null;
	private static ResourceBean resourceBean=null;
	private static OrganizationBean organizationBean=null;

	/*******************************************************************************************/
	/***************************************   User Session  ***********************************/
	/*******************************************************************************************/

	public static UserSessionBean prepareUserSessionBeanFromOrganizationModel(OrganizationModel organizationModel){
		
		userSessionBean = new UserSessionBean();
		resourceBean = new ResourceBean();

		userSessionBean.setId(organizationModel.getId());
		userSessionBean.setUserName(organizationModel.getUserName());
		userSessionBean.setFirstName(organizationModel.getFirstName());
		userSessionBean.setLastName(organizationModel.getLastName());
		userSessionBean.setName(organizationModel.getName());
	
		resourceBean = AddressBeanPreparation.prepareResourceBean(organizationModel.getResourceModel());                                                                     
			
		userSessionBean.setResourceBean(resourceBean);
		
		return userSessionBean;
		
	}
	
	public static UserSessionBean prepareUserSessionBeanFromBranchModel(BranchModel branchModel){
		
		userSessionBean = new UserSessionBean();
		resourceBean = new ResourceBean();

		userSessionBean.setId(branchModel.getId());
		userSessionBean.setUserName(branchModel.getUserName());
		userSessionBean.setFirstName(branchModel.getFirstName());
		userSessionBean.setLastName(branchModel.getLastName());
		userSessionBean.setName(branchModel.getName());
		organizationBean = new OrganizationBean();
	    organizationBean.setId(branchModel.getOrganizationModel().getId());
	    organizationBean.setName(branchModel.getOrganizationModel().getName());
        userSessionBean.setOrganizationBean(organizationBean);
		resourceBean = AddressBeanPreparation.prepareResourceBean(branchModel.getResourceModel()); 
		
				
		userSessionBean.setResourceBean(resourceBean);
		
		return userSessionBean;
		
	}
	
	public static UserSessionBean prepareUserSessionBeanFromOutletModel(OutletModel outletModel){
		
		userSessionBean = new UserSessionBean();
		resourceBean = new ResourceBean();

		userSessionBean.setId(outletModel.getId());
		userSessionBean.setUserName(outletModel.getUserName());
		userSessionBean.setFirstName(outletModel.getFirstName());
		userSessionBean.setLastName(outletModel.getLastName());
		userSessionBean.setBranchBean(BranchBeanPreparation.prepareBranchBean(outletModel.getBranchModel()));
		userSessionBean.setOrganizationBean(OrganizationBeanPreparation.prepareOrganizationBean(outletModel.getBranchModel().getOrganizationModel()));
		resourceBean = AddressBeanPreparation.prepareResourceBean(outletModel.getResourceModel()); 
	
		userSessionBean.setResourceBean(resourceBean);
		
		return userSessionBean;
		
	}
}
