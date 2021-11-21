package com.relyits.rmbs.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relyits.rmbs.dao.PurchaseDAO;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.service.PurchaseService;

@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService{

	@Autowired
	private PurchaseDAO purchaseDAO;
	
	
	public int createPurchaseOrder(List<PurchaseLineItemsModel> purchaseLineItemsModel){
    return purchaseDAO.createPurchaseOrder(purchaseLineItemsModel);
	}


	@Override
	public List<PurchaseOrderModel> getPurchaseOrderList(PurchaseOrderModel purchaseOrderModel) {
		return purchaseDAO.getPurchaseOrderList(purchaseOrderModel);
	}

	@Override
	public List<PurchaseLineItemsModel> getPurchaseLineItems(PurchaseLineItemsModel purchaseLineItemsModel) {
			return purchaseDAO.getPurchaseLineItems(purchaseLineItemsModel);
	}


	

	@Override
	public List<PurchaseLineItemsModel> getPurchaseLineItemsModelsByPurcheseOrderModel(PurchaseOrderModel purchaseOrderModel) {
		return purchaseDAO.getPurchaseLineItemsModelsByPurcheseOrderModel(purchaseOrderModel);
	}
	
	@Override
	public List<String> getPurchaseInvoiceNo(PurchaseOrderModel purchaseOrderModel) {
		return purchaseDAO.getPurchaseInvoiceNo(purchaseOrderModel);
	}
	@Override
	public PurchaseOrderModel getPurchaseOrderModel(PurchaseOrderModel purchaseOrderModel) {
		return purchaseDAO.getPurchaseOrderModel(purchaseOrderModel);
	}


	@Override
	public PurchaseOrderModel validateInvoice(PurchaseOrderModel purchaseOrderModel) {
		return purchaseDAO.validateInvoice(purchaseOrderModel);
	}


	@Override
	public Long countAll(PurchaseOrderModel purchaseOrderModel) {
		return purchaseDAO.countAll(purchaseOrderModel);
	}


	@Override
	public List<PurchaseOrderModel> getByPage(int page, int pageSize,PurchaseOrderModel purchaseOrderModel) {
		int firstResult = page * pageSize - pageSize; 
		return purchaseDAO.listByPage(firstResult, pageSize, purchaseOrderModel);
	}


	@Override
	public Long countAll(PurchaseLineItemsModel purchaseLineItemsModel) {
		return purchaseDAO.countAll(purchaseLineItemsModel);
	}


	@Override
	public List<PurchaseLineItemsModel> getByPage(int page, int pageSize,PurchaseLineItemsModel purchaseLineItemsModel) {
		int firstResult = page * pageSize - pageSize; 
		return purchaseDAO.listByPage(firstResult, pageSize, purchaseLineItemsModel);
	}
	
}
