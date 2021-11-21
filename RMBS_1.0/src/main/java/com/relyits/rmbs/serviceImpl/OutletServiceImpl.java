package com.relyits.rmbs.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.relyits.rmbs.dao.OutletDao;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.service.OutletService;

/**
 * @author Amar Errabelli
 *
 */
@Service("outletService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OutletServiceImpl implements OutletService{

	@Autowired
	private OutletDao outletDao;

	public OutletModel checkOutletAvailability(OutletModel outletModel) {
		return outletDao.checkOutletAvailability(outletModel);
	}

	public boolean createOutlet(OutletModel outletModel) {
		return outletDao.createOutlet(outletModel);
	}

    public OutletModel getOutlet(OutletModel outletModel) {
		return outletDao.getOutlet(outletModel);
	}

	public boolean changeOutletStatus(OutletModel outletModel) {
		return outletDao.changeOutletStatus(outletModel);
	}
    
	public List<OutletModel> getOutletListsByBranch(OutletModel outletModel) {
		return outletDao.getOutletListsByBranch(outletModel);
	}

	public List<OutletModel> getOutletListsByOrganization(OutletModel outletModel) {
		return outletDao.getOutletListsByOrganization(outletModel);
	}

	@Override
	public OutletModel loginValidate(OutletModel outletModel) {
		return outletDao.loginValidate(outletModel);
	}

	@Override
	public int upadtePasswordByIdOfOutlet(OutletModel outletModel) {
		return outletDao.upadtePasswordByIdOfOutlet(outletModel);
	}

	@Override
	public boolean updateOutlet(OutletModel outletModel) {
		return outletDao.updateOutlet(outletModel);
	}
	
	@Override
	public Long countAll(OutletModel outletModel) {
		return outletDao.countAll(outletModel);
	}

	@Override
	public List<OutletModel> getByPage(int page, int pageSize,OutletModel outletModel) {
		int firstResult = page * pageSize - pageSize;

	    return outletDao.listByPage(OrganizationModel.class, firstResult, pageSize,outletModel);
	}

	@Override
	public List<OutletModel> getOutlets(OutletModel outletModel) {
		
		return outletDao.getOutlets(outletModel);
	}


}
