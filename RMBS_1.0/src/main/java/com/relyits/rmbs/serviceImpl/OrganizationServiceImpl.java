package com.relyits.rmbs.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.relyits.rmbs.dao.OrganizationDAO;
import com.relyits.rmbs.model.menu.ChildMenuModel;
import com.relyits.rmbs.model.menu.MenuModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.DoctorModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.service.OrganizationService;

/**
 * @author Amar Errabelli
 *
 */
@Service("organizationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDAO organizationDAO;

	
	public OrganizationModel checkAvailabilty(OrganizationModel organizationModel) {
		return organizationDAO.checkAvailabilty(organizationModel);
	}
	
	//@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean  createOraganization(OrganizationModel organizationModel) {
		return organizationDAO.createOraganization(organizationModel);
	}
	
	public OrganizationModel loginValidate(OrganizationModel organizationModel){
		return organizationDAO.loginValidate(organizationModel);
		
	}
	
	public List<OrganizationModel> getlistOfOrganizations() {
		return organizationDAO.getlistOfOrganizations();
	}

	public OrganizationModel getUser(int empid) {
		return organizationDAO.getUser(empid);
	}
	
	public void deleteUser(OrganizationModel organizationModel) {
		organizationDAO.deleteUser(organizationModel);
	}
	
	public List<OrganizationModel> roleTwoUsersList(OrganizationModel organizationModel) {
		return organizationDAO.roleTwoUsersList(organizationModel);
		
	}

	public List<OrganizationModel> roleFourUsersList(OrganizationModel organizationModel) {
		return organizationDAO.roleTwoUsersList(organizationModel);
		
	}
	
	public OrganizationModel getOrganizationbyId(OrganizationModel organizationModel) {
		  return organizationDAO.getOrganizationbyId(organizationModel);
		}
	
	public List<OrganizationModel> getAccountRequestsByOrganizations(OrganizationModel organizationModel) {
		return organizationDAO.getAccountRequestsByOrganizations(organizationModel);
	}
	
	public int changeOrganizationStatus(OrganizationModel organizationModel) {
		return organizationDAO.changeOrganizationActiveStatus(organizationModel);
	}


	//**************************menu***************************
	
	public List<ChildMenuModel> getUserchildMenu(int[] childMenuOptions) {
		return organizationDAO.getUserchildMenu(childMenuOptions);
	}

	public int[] createUserMenu(MenuModel menuModel) {
		return organizationDAO.createUserMenu(menuModel);
	}

	@Override
	public int upadtePasswordById(OrganizationModel organizationModel) {
		return organizationDAO.upadtePasswordById(organizationModel);
	}

	@Override
	public boolean updateOraganization(OrganizationModel organizationModel) {
		return organizationDAO.updateOraganization(organizationModel);
	}

	@Override
	public Long countAll(OrganizationModel organizationModel) {
		return organizationDAO.countAll(organizationModel);
	}

	@Override
	public List<OrganizationModel> getByPage(int page, int pageSize,OrganizationModel organizationModel) {
		int firstResult = page * pageSize - pageSize;

	    return organizationDAO.listByPage(OrganizationModel.class, firstResult, pageSize,organizationModel);
	}

	@Override
	public String getEmailId(OrganizationModel organizationModel) {
		
		return organizationDAO.getEmailId(organizationModel);
	}

	@Override
	public String getAdminEmailId(OrganizationModel organizationModel) {
		
		return organizationDAO.getAdminEmailId(organizationModel);
	}

	@Override
	public String getBranchMailId(BranchModel branchModel) {
		
		return organizationDAO.getBranchMailId(branchModel);
	}

	@Override
	public String getOutletEmailId(OutletModel outletModel) {
		
		return organizationDAO.getOutletEmailId(outletModel);
	}

	


}
