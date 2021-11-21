package com.relyits.rmbs.dao;

import java.util.List;


import com.relyits.rmbs.model.product.ProductModel;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;

public interface PurchaseDAO {
	public int createPurchaseOrder(List<PurchaseLineItemsModel> purchaseLineItemsModel);
	
	public List<PurchaseOrderModel> getPurchaseOrderList(PurchaseOrderModel purchaseOrderModel);
	
	public List<PurchaseLineItemsModel> getPurchaseLineItems(PurchaseLineItemsModel purchaseLineItemsModel);
		
	public List<PurchaseLineItemsModel> getPurchaseLineItemsModelsByPurcheseOrderModel(PurchaseOrderModel purchaseOrderModel) ;
	
    public List<String> getPurchaseInvoiceNo(PurchaseOrderModel purchaseOrderModel);

    public PurchaseOrderModel getPurchaseOrderModel(PurchaseOrderModel purchaseOrderModel);
    
    public PurchaseOrderModel validateInvoice(PurchaseOrderModel purchaseOrderModel);

    public Long countAll(PurchaseOrderModel purchaseOrderModel);
	 
	public List<PurchaseOrderModel> listByPage(int firstResult, int pageSize,PurchaseOrderModel purchaseOrderModel);
	
	public Long countAll(PurchaseLineItemsModel purchaseLineItemsModel);
	 
	public List<PurchaseLineItemsModel> listByPage(int firstResult, int pageSize,PurchaseLineItemsModel purchaseLineItemsModel);
}
