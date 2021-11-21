package com.relyits.rmbs.service;

import java.util.List;

import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OutletModel;

public interface OutletService {

	 public OutletModel checkOutletAvailability(OutletModel outletModel);
	 
	 public boolean createOutlet(OutletModel outletModel);
	 
	 public OutletModel getOutlet(OutletModel outletModel);
	 
	 public boolean changeOutletStatus(OutletModel outletModel);
	 
	 public List<OutletModel> getOutletListsByBranch(OutletModel outletModel);
	 
	 public List<OutletModel> getOutletListsByOrganization(OutletModel outletModel);
	 
	 public OutletModel loginValidate(OutletModel outletModel);
	 
	 public int upadtePasswordByIdOfOutlet(OutletModel outletModel);
	 
	 public boolean  updateOutlet(OutletModel outletModel);
	 
	 public Long countAll(OutletModel outletModel);
	 
	 public List<OutletModel> getByPage(int page, int pageSize,OutletModel outletModel);
	 
	 public List<OutletModel> getOutlets(OutletModel outletModel);
}
