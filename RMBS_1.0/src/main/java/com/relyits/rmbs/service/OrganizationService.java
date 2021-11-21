package com.relyits.rmbs.service;

import java.util.List;

import com.relyits.rmbs.model.menu.ChildMenuModel;
import com.relyits.rmbs.model.menu.MenuModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.DoctorModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;

/**
 * @author Amar Rao Errabelli
 *
 */


public interface OrganizationService {

	public OrganizationModel checkAvailabilty(OrganizationModel organizationModel);
	
	public boolean  createOraganization(OrganizationModel organizationModel);
	
	public OrganizationModel loginValidate(OrganizationModel organizationModel);

	public List<OrganizationModel> getlistOfOrganizations();
	
	public OrganizationModel getUser(int userid);
	
	public void deleteUser(OrganizationModel organizationModel);
	
    public List<OrganizationModel> roleTwoUsersList(OrganizationModel organizationModel);
	
	public List<OrganizationModel> roleFourUsersList(OrganizationModel organizationModel);
	
	public OrganizationModel getOrganizationbyId(OrganizationModel organizationModel);
	
	public int changeOrganizationStatus(OrganizationModel organizationModel);
	
	public List<OrganizationModel> getAccountRequestsByOrganizations(OrganizationModel organizationModel);

	public int upadtePasswordById(OrganizationModel organizationModel);
	
	public boolean  updateOraganization(OrganizationModel organizationModel);
	
	public Long countAll(OrganizationModel organizationModel);
	 
	public List<OrganizationModel> getByPage(int page, int pageSize,OrganizationModel organizationModel);
	//******************menu*******************
	
	public int[] createUserMenu(MenuModel menuModel);
	
	public List<ChildMenuModel> getUserchildMenu(int[] childMenuOptions);

	public String getEmailId(OrganizationModel organizationModel);

	public String getAdminEmailId(OrganizationModel organizationModel);

	public String getBranchMailId(BranchModel branchModel);

	public String getOutletEmailId(OutletModel outletModel);
}
