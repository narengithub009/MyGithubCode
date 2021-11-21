package com.relyits.rmbs.service;

import java.util.List;

import com.relyits.rmbs.beans.purchase.PurchaseLineItemsBean;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;

public interface PurchaseReturnsService {

	public List<String> getPurchaseInvoiceNo(PurchaseOrderModel purchaseOrderModel);

	public PurchaseLineItemsModel getPurchaseLineItemsById(PurchaseLineItemsModel purchaseLineItemsModel);

	public List<PurchaseLineItemsBean> getPurchaseLineItemsByPOId(PurchaseOrderModel purchaseOrderModel);

	public List<PurchaseReturnLineItemsModel> createPurchaseReturnOrder(List<PurchaseReturnLineItemsModel> purchaseReturnLineItemsModels, List<PurchaseLineItemsModel> purchaseLineItemsModels);

	public List<PurchaseReturnOrderModel> getPurchaseReturnOrderList(PurchaseReturnOrderModel purchaseReturnOrderModel);

	public List<PurchaseReturnLineItemsModel> getPurchaseReturnLineItems(PurchaseReturnLineItemsModel purchaseReturnLineItemsModel);

	public Long countAll(PurchaseReturnOrderModel purchaseReturnOrderModel);
	 
	public List<PurchaseReturnOrderModel> getByPage(int page, int pageSize,PurchaseReturnOrderModel purchaseReturnOrderModel);
}
