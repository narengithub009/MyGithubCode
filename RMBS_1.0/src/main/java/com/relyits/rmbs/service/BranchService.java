package com.relyits.rmbs.service;

import java.util.List;

import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;

public interface BranchService {

	public BranchModel checkBranchAvailability(BranchModel branchModel);
	
	 public boolean createBranch(BranchModel branchModel);
	 
	 public List<BranchModel> getBranchesByOrganization(BranchModel userBranch);
	 
	 public List<BranchModel> getAccountRequestsByBranches(BranchModel branchModel);
		
	 public BranchModel getBranchbyId(BranchModel branchModel);	
		
	 public boolean changeBranchStatus(BranchModel branchModel);
	 
	 public BranchModel loginValidate(BranchModel branchModel);
	 
	 public int upadtePasswordByIdOfBranch(BranchModel branchModel);
	 
	 public boolean  updateBranch(BranchModel branchModel);
	 
	 public Long countAll(BranchModel branchModel);
	 
	public List<BranchModel> getByPage(int page, int pageSize,BranchModel branchModel);
	 
}
