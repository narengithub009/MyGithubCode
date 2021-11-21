package com.relyits.rmbs.service;

import java.util.List;

import com.relyits.rmbs.model.product.ProductDamageModel;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.product.ProductLogModel;
import com.relyits.rmbs.model.product.ProductModel;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.refference.SubCategoryModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.BranchModel;

public interface ProductService {
	
		public List<String> getAgenciesList(AgencyModel agencyModel);
		
		public void addProduct(ProductInventoryModel productInventoryModel);
		
		public List<String> getProductsList(ProductModel productModel);
		
		public List<ProductModel> getInventroyProductsList(ProductInventoryModel productInventoryModel);
		
		public ProductModel getProduct(ProductModel productModel);
		
		public List<ProductInventoryModel> getProductBatches(ProductInventoryModel productInventoryModel);
		
		public ProductInventoryModel getProductInventoryModelByBatchNo(ProductInventoryModel productInventoryModel);
		
		public ProductInventoryModel loadProductInventoryModel(ProductInventoryModel productInventoryModel);
		
		public int upadteProductInventoryQuantity(ProductInventoryModel productInventoryModel);
		
		public List<SubCategoryModel> getSubCategoryListByCategory(SubCategoryModel subCategoryModel);
		
		public List<ProductModel> listProductsByCreator(ProductModel productModel) ;
		
		public List<ProductInventoryModel> listProductsInventory(ProductInventoryModel productInventoryModel);
		
		public ProductInventoryModel getProductInventory(ProductInventoryModel productInventoryModel);

		public Integer updatePIQuantity(ProductInventoryModel productInventoryModel);
		
		public List<ProductLogModel> listProductsLog(ProductLogModel productLogModel);
		
		public List<ProductInventoryModel> getExpiredProducts(ProductInventoryModel productInventoryModel);

		public List<ProductDamageModel> listProductsDamage(ProductDamageModel productDamageModel);
	
		public List<ProductInventoryModel> listProductsGoingToExpire(ProductInventoryModel productInventoryModel);
		
		public List<ProductInventoryModel> lessQuantityProducts(ProductInventoryModel productInventoryModel);
	
	    public void insertBulkProducts(List<ProductModel> list, AgencyModel agencyModel,int createdby,int createrRole);

		public List<AgencyModel> getAgencyList(AgencyModel agencyModel);
		
		public boolean saveDamegedProducts(ProductDamageModel productDamageModel);
		
		public Integer getProductQuantityFromInv(PurchaseLineItemsModel purchaseLineItemsModel);
		
		public List<ProductInventoryModel> getProductListFromInventory(int bid,int catId);
		
		
		//************************Pagination*******************************
		
		public Long countAll(ProductModel productModel);
		 
		public List<ProductModel> getByPage(int page, int pageSize,ProductModel productModel);
		
		public Long countAll(ProductInventoryModel productInventoryModel);
		 
		public List<ProductInventoryModel> getByPage(int page, int pageSize,ProductInventoryModel productInventoryModel);
		
		public Long countAll(ProductLogModel productLogModel);
		 
		public List<ProductLogModel> getByPage(int page, int pageSize,ProductLogModel productLogModel);
		
		public Long countAllOfExpiredProducts(ProductInventoryModel productInventoryModel);
		 
		public List<ProductInventoryModel> getByPageOfExpiryProducts(int page, int pageSize,ProductInventoryModel productInventoryModel);

		public Long countAll(ProductDamageModel productDamageModel);
		
		public List<ProductDamageModel> getByPage(int page, int pageSize,ProductDamageModel productDamageModel);

		public Long countAllOfGoingToExpiryProdcuts(ProductInventoryModel productInventoryModel);
		 
		public List<ProductInventoryModel> getByPageOfGoingToExpiryProdcuts(int page, int pageSize,ProductInventoryModel productInventoryModel);
		
		public Long countAllOfLessQuantityProdcuts(ProductInventoryModel productInventoryModel);
		 
		public List<ProductInventoryModel> getByPageOfLessQuantityProdcuts(int page, int pageSize,ProductInventoryModel productInventoryModel);
		
}
