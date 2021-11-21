package com.relyits.rmbs.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relyits.rmbs.dao.ProductDAO;
import com.relyits.rmbs.model.product.ProductDamageModel;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.product.ProductLogModel;
import com.relyits.rmbs.model.product.ProductModel;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.refference.SubCategoryModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDAO productDAO;
	
	//********************** Product *************************
		public List<String> getAgenciesList(AgencyModel agencyModel) {
			return productDAO.getAgenciesList(agencyModel);
		 
		}

		public void addProduct(ProductInventoryModel productInventoryModel) {
			productDAO.addProduct(productInventoryModel);
			
		}

		public List<String> getProductsList(ProductModel productModel) {
			return productDAO.getProductsList(productModel);
		}
		public List<ProductModel> getInventroyProductsList(ProductInventoryModel productInventoryModel){
			return productDAO.getInventroyProductsList(productInventoryModel);
		}

		public ProductModel getProduct(ProductModel productModel) {
				return productDAO.getProduct(productModel);
		}
		public List<ProductInventoryModel> getProductBatches(ProductInventoryModel productInventoryModel){
			return productDAO.getProductBatches(productInventoryModel);
		}
		
		public ProductInventoryModel getProductInventoryModelByBatchNo(ProductInventoryModel productInventoryModel) {
			return productDAO.getProductInventoryModelByBatchNo(productInventoryModel);
		}
		
		public ProductInventoryModel loadProductInventoryModel(ProductInventoryModel productInventoryModel) {
			return productDAO.loadProductInventoryModel(productInventoryModel);
		}
		
		public int upadteProductInventoryQuantity(ProductInventoryModel productInventoryModel) {
			
			return productDAO.upadteProductInventoryQuantity(productInventoryModel);
		}

		public List<SubCategoryModel> getSubCategoryListByCategory(SubCategoryModel subCategoryModel){
		return productDAO.getSubCategoryListByCategory(subCategoryModel);
		}

		@Override
		public List<ProductModel> listProductsByCreator(ProductModel productModel) {
			
			return productDAO.listProductsByCreator(productModel);
		}

		
		@Override
		public List<ProductInventoryModel> listProductsInventory(ProductInventoryModel productInventoryModel) {
			return productDAO.listProductsInventory(productInventoryModel);
		}

		@Override
		public ProductInventoryModel getProductInventory(ProductInventoryModel productInventoryModel) {
			return productDAO.getProductInventory(productInventoryModel);
		}

		@Override
		public Integer updatePIQuantity(ProductInventoryModel productInventoryModel) {
			return productDAO.updatePIQuantity(productInventoryModel);
		}

		@Override
		public List<ProductLogModel> listProductsLog(ProductLogModel productLogModel) {
			return productDAO.listProductsLog(productLogModel);
		}

		

		@Override
		public List<ProductInventoryModel> getExpiredProducts(ProductInventoryModel productInventoryModel) {			
			return productDAO.getExpiredProducts(productInventoryModel);
		}

		@Override
		public List<ProductDamageModel> listProductsDamage(ProductDamageModel productDamageModel) {
			return productDAO.listProductsDamage(productDamageModel);
		}

		@Override
		public List<ProductInventoryModel> listProductsGoingToExpire(ProductInventoryModel productInventoryModel) {
			return productDAO.listProductsGoingToExpire(productInventoryModel);
		}

		@Override
		public List<ProductInventoryModel> lessQuantityProducts(ProductInventoryModel productInventoryModel) {
			return productDAO.lessQuantityProducts(productInventoryModel);
		}

		@Override
		 public void insertBulkProducts(List<ProductModel> list,AgencyModel agencyModel,int createdby,int createrRole){
			 productDAO.insertBulkProducts(list,agencyModel,createdby,createrRole); 
		 }
		
		@Override
		public List<AgencyModel> getAgencyList(AgencyModel agencyModel) {
			
			return (ArrayList<AgencyModel>) productDAO.getAgencyList(agencyModel);
		}

		@Override
		public boolean saveDamegedProducts(ProductDamageModel productDamageModel) {
			return productDAO.saveDamegedProducts(productDamageModel);
		}

		@Override
		public Integer getProductQuantityFromInv(
				PurchaseLineItemsModel purchaseLineItemsModel) {
			return productDAO.getProductQuantityFromInv(purchaseLineItemsModel);
		}

		@Override
		public List<ProductInventoryModel> getProductListFromInventory(int bid, int catId) {
			return productDAO.getProductListFromInventory(bid, catId);
		}
		
		@Override
		public Long countAll(ProductModel productModel) {
			return productDAO.countAll(productModel);
		}

		@Override
		public List<ProductModel> getByPage(int page, int pageSize,ProductModel productModel) {
			int firstResult = page * pageSize - pageSize;

		    return productDAO.listByPage(firstResult, pageSize,productModel);
		}
		
		@Override
		public Long countAll(ProductInventoryModel productInventoryModel) {
			return productDAO.countAll(productInventoryModel);
		}

		@Override
		public List<ProductInventoryModel> getByPage(int page, int pageSize,ProductInventoryModel productInventoryModel) {
			int firstResult = page * pageSize - pageSize;

		    return productDAO.listByPage(firstResult, pageSize,productInventoryModel);
		}
		
		@Override
		public Long countAll(ProductLogModel productLogModel) {
			return productDAO.countAll(productLogModel);
		}

		@Override
		public List<ProductLogModel> getByPage(int page, int pageSize,ProductLogModel productLogModel) {
			int firstResult = page * pageSize - pageSize;

		    return productDAO.listByPage(firstResult, pageSize,productLogModel);
		}

		@Override
		public List<ProductInventoryModel> getByPageOfExpiryProducts(int page,int pageSize, ProductInventoryModel productInventoryModel) {
			int firstResult = page * pageSize - pageSize;
			return productDAO.listByPageOfExpiryProducts(firstResult, pageSize,productInventoryModel);
		}

		@Override
		public Long countAllOfExpiredProducts(ProductInventoryModel productInventoryModel) {
			return productDAO.countAllOfExpiredProducts(productInventoryModel);
		}

		@Override
		public Long countAll(ProductDamageModel productDamageModel) {
			return productDAO.countAll(productDamageModel);
		}

		@Override
		public List<ProductDamageModel> getByPage(int page, int pageSize,ProductDamageModel productDamageModel) {
			int firstResult = page * pageSize - pageSize;
			return productDAO.listByPage(firstResult, pageSize,productDamageModel);
		}

		@Override
		public Long countAllOfGoingToExpiryProdcuts(ProductInventoryModel productInventoryModel) {
			return productDAO.countAllOfGoingToExpiryProducts(productInventoryModel);
		}

		@Override
		public List<ProductInventoryModel> getByPageOfGoingToExpiryProdcuts(int page, int pageSize,ProductInventoryModel productInventoryModel) {
			int firstResult = page * pageSize - pageSize;
			return productDAO.listByPageOfGoingToExpiryProducts(firstResult, pageSize,productInventoryModel);
		}

		@Override
		public Long countAllOfLessQuantityProdcuts(ProductInventoryModel productInventoryModel) {
			return productDAO.countAllOfLessQuantityProducts(productInventoryModel);
		}

		@Override
		public List<ProductInventoryModel> getByPageOfLessQuantityProdcuts(int page, int pageSize,ProductInventoryModel productInventoryModel) {
			int firstResult = page * pageSize - pageSize;
			return productDAO.listByPageOfLessQuantityProducts(firstResult, pageSize, productInventoryModel);
		}
}
