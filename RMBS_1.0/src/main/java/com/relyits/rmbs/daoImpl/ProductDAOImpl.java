package com.relyits.rmbs.daoImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.ProductDAO;
import com.relyits.rmbs.model.product.ProductDamageModel;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.product.ProductLogModel;
import com.relyits.rmbs.model.product.ProductModel;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.SubCategoryModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;

@Repository("productDAO")
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	Criteria criteria = null;
	Object obj=null;
	@SuppressWarnings("unchecked")
	public List<String> getAgenciesList(AgencyModel agencyModel) {
		Session session = sessionFactory.openSession();
		List<String> list=null;
		try{
			Criteria cr = session.createCriteria(AgencyModel.class,"agency")
					  .createAlias("agency.resourceModel", "resource")
					  .createAlias("resource.accountStatusModel", "status")
					  .add(Restrictions.eq("status.id", agencyModel.getResourceModel().getAccountStatusModel().getId()))
					.add(Restrictions.like("agencyName", agencyModel.getAgencyName(), MatchMode.ANYWHERE));
		
			list = cr.list();
			session.close();
			return list;
		}catch(Exception e){
		}
		session.close();
		return list;
	}

	ProductModel productModel = null;
	CategoryModel categoryModel=null;
	SubCategoryModel subCategoryModel=null;
	RoleModel creatorRoleModel=null;
	AgencyModel agencyModel=null;
	BranchModel branchModel=null;

	public void addProduct(ProductInventoryModel productInventoryModel) {

		Session session = sessionFactory.openSession();
		Session session1 = sessionFactory.openSession();
		session.beginTransaction();
		int i=0;
		int j=0;
		try{
			j=(int) session1.createSQLQuery("select max(RMBS20101) from rmbs.rmbs201").list().get(i);

		}catch(Exception e){
			
		}
		session1.close();

		productModel = new ProductModel();
		categoryModel = new CategoryModel();
		subCategoryModel = new SubCategoryModel();
		creatorRoleModel = new RoleModel();
		agencyModel = new AgencyModel();
		branchModel = new BranchModel();

		productModel = productInventoryModel.getProductModel();

		categoryModel=(CategoryModel) session.get(CategoryModel.class, productModel.getCategoryModel().getId());
		subCategoryModel=(SubCategoryModel) session.get(SubCategoryModel.class, productModel.getSubCategoryModel().getId());
		creatorRoleModel=(RoleModel) session.get(RoleModel.class, productModel.getCreatorRoleModel().getId());
		agencyModel=(AgencyModel) session.get(AgencyModel.class, productInventoryModel.getAgencyModel().getId());
		branchModel=(BranchModel) session.get(BranchModel.class, productInventoryModel.getBranchModel().getId());


		if(productModel.getId()==null){
			String code=productModel.getName().substring(0, 3)+"/"
					+productModel.getmFCompanay().substring(0, 3)+"/"
					+productModel.getCategoryModel().getId()+"/"
					+productModel.getSubCategoryModel().getId()+"/"
					+(j+1);
			productModel.setCode(code);
			productModel.setCategoryModel(categoryModel);
			productModel.setAgencyModel(agencyModel);
			productModel.setSubCategoryModel(subCategoryModel);
			productModel.setCreatorRoleModel(creatorRoleModel);

			session.save(productModel);
		}
		productInventoryModel.setProductModel(productModel);
		productInventoryModel.setCreatorRoleModel(creatorRoleModel);
		productInventoryModel.setAgencyModel(agencyModel);
		productInventoryModel.setBranchModel(branchModel);

		session.save(productInventoryModel);
		session.getTransaction().commit();
	}


	@SuppressWarnings("unchecked")
	public List<String> getProductsList(ProductModel productModel) {
		Session session = sessionFactory.openSession();
		List<String> list=null;
		try{
			Criteria cr = session.createCriteria(ProductModel.class,"product")
					.createAlias("product.categoryModel", "category")
					.add(Restrictions.like("name", productModel.getName(), MatchMode.ANYWHERE))
					.add(Restrictions.eq("category.id",productModel.getCategoryModel().getId()));
			list = cr.list();
			session.close();
			return list;
		}catch(Exception e){
		}
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ProductModel> getInventroyProductsList(ProductInventoryModel productInventoryModel){
		Session session = sessionFactory.openSession();
		List<ProductModel> list=null;
	try{
			Criteria cr = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.productModel", "product")
					.createAlias("productInventory.branchModel", "branch")
					.createAlias("product.categoryModel", "category")
					.setProjection(Projections.projectionList()
							   .add(Projections.property("product.id"), "id")
							   .add(Projections.property("product.name"), "name")
							  .add(Projections.property("product.subCategoryModel"), "subCategoryModel")
							  .add(Projections.groupProperty("product.name")))
					.add(Restrictions.eq("branch.id", productInventoryModel.getBranchModel().getId()))
					.add(Restrictions.like("product.name", productInventoryModel.getProductModel().getName(), MatchMode.ANYWHERE))
					.add(Restrictions.eq("category.id",productInventoryModel.getProductModel().getCategoryModel().getId()))
					.setResultTransformer(Transformers.aliasToBean(ProductModel.class));
			list = cr.list();
			session.close();
			return list;
		}catch(Exception e){
}
		session.close();
		return list;
	}


	public ProductModel getProduct(ProductModel productModel) {
	
		Session session = sessionFactory.openSession();
		return (ProductModel) session.get(ProductModel.class, productModel.getId());

	}
	@SuppressWarnings("unchecked")
	public List<ProductInventoryModel> getProductBatches(ProductInventoryModel productInventoryModel) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
						.createAlias("productInventory.productModel", "product")
						.createAlias("productInventory.branchModel", "branch")
						.add(Restrictions.eq("product.id",productInventoryModel.getProductModel().getId()))
						.add(Restrictions.eq("branch.id",productInventoryModel.getBranchModel().getId()))
		                .add(Restrictions.gt("productInventory.quantity",productInventoryModel.getQuantity()));
			

		
		
		List<ProductInventoryModel> productInventoryModels=criteria.list();
		session.close();
		return productInventoryModels;

	}

	@SuppressWarnings("rawtypes")
	public ProductInventoryModel getProductInventoryModelByBatchNo(ProductInventoryModel productInventoryModel) {
		ProductInventoryModel productInventoryModel1 = null;
		try {
			Session session = sessionFactory.openSession();
				Criteria cr = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.productModel", "product")
					.createAlias("productInventory.branchModel", "branch")
					.createAlias("product.categoryModel", "category")
					.createAlias("productInventory.agencyModel", "agency")
					.add(Restrictions.eq("productInventory.batchNo",productInventoryModel.getBatchNo()))
					.add(Restrictions.eq("category.id",productInventoryModel.getProductModel().getCategoryModel().getId()))
					.add(Restrictions.eq("branch.id",productInventoryModel.getBranchModel().getId()))
					.add(Restrictions.eq("agency.id",productInventoryModel.getAgencyModel().getId()))
					;


			List list = cr.list();
			for(int i=0;i<list.size();i++){
				productInventoryModel1=(ProductInventoryModel) list.get(i);
			}

		} catch (Exception e) {
		}



		return productInventoryModel1;
	}
	public ProductInventoryModel loadProductInventoryModel(ProductInventoryModel productInventoryModel){
		Session session=sessionFactory.openSession();
		ProductInventoryModel productInventoryModel1=new ProductInventoryModel();
		productInventoryModel1=(ProductInventoryModel) session.get(ProductInventoryModel.class, productInventoryModel.getId());
		session.close();
		return productInventoryModel1;
		
	}
	public int upadteProductInventoryQuantity(ProductInventoryModel productInventoryModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		int result=0;
		try {
		Query query1=session.createSQLQuery("UPDATE rmbs.rmbs202 SET rmbs20202 = rmbs20202+"+productInventoryModel.getQuantity()
				+" where rmbs20201="+productInventoryModel.getId()+";");

		result=query1.executeUpdate();
		} catch (Exception e) {
		session.cancelQuery();
	}

		session.getTransaction().commit();
		session.close();
		return result;
	}



	@SuppressWarnings({ "unchecked" })
	public List<SubCategoryModel> getSubCategoryListByCategory(SubCategoryModel subCategoryModel){

		Session session = sessionFactory.openSession();
		return (List<SubCategoryModel>) session.createCriteria(SubCategoryModel.class,"subCategory")
				.createAlias("subCategory.categoryModel", "category")												
				.list();


	}	


	@SuppressWarnings("unchecked")
	public List<ProductModel> listProductsByCreator(ProductModel productModel) {
		try{
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(ProductModel.class);
		return criteria.list();
			}catch(Exception e){
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductInventoryModel> listProductsInventory(ProductInventoryModel productInventoryModel) {
		try{
			Session session = sessionFactory.openSession();
			if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
			criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")				
					.createAlias("productInventory.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",productInventoryModel.getBranchModel().getOrganizationModel().getId()));
			}else{
				criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")				
						.createAlias("productInventory.branchModel", "branch")
						.add(Restrictions.eq("branch.id",productInventoryModel.getBranchModel().getId()));
			}
			
			List list = criteria.list();
			
			return list;


		}catch(Exception e){

			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public ProductInventoryModel getProductInventory(ProductInventoryModel productInventoryModel) {
		Session session = sessionFactory.openSession();
		if(productInventoryModel.getId()!=null){
			return (ProductInventoryModel) session.get(ProductInventoryModel.class, productInventoryModel.getId());
		}else{

			Criteria criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.productModel", "product")
					.add(Restrictions.eq("productInventory.createdBy",productInventoryModel.getCreatedBy()))
					.add(Restrictions.eq("product.name",productInventoryModel.getProductModel().getName()))	;		        
			
			List products = criteria.list();
			ProductInventoryModel productInventoryModel1 = null;
			for(int i=0;i<products.size();i++){
				productInventoryModel1=(ProductInventoryModel) products.get(i);
			}

			return productInventoryModel1;
		}

	}

	public Integer updatePIQuantity(ProductInventoryModel productInventoryModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery(
				"CALL updateProductInventoryByQuantity(:pIId,:quantity,:userId,:userRole,:branchId)")
				.addEntity(ProductInventoryModel.class)
				.setParameter("pIId",productInventoryModel.getId())
				.setParameter("quantity", productInventoryModel.getQuantity())
				.setParameter("userId", productInventoryModel.getCreatedBy())
				.setParameter("userRole",productInventoryModel.getCreatorRoleModel().getId())
				.setParameter("branchId",productInventoryModel.getBranchModel().getId());
		query.executeUpdate();
		session.getTransaction().commit();
		ProductInventoryModel productInventoryModel2 =(ProductInventoryModel) session.get(ProductInventoryModel.class, productInventoryModel.getId());
		return productInventoryModel2.getQuantity();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductLogModel> listProductsLog(ProductLogModel productLogModel) {
		try{
			Session session = sessionFactory.openSession();
			if(productLogModel.getBranchModel().getOrganizationModel().getId()!=null){
			criteria = session.createCriteria(ProductLogModel.class,"productLog")				
					.createAlias("productLog.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",productLogModel.getBranchModel().getOrganizationModel().getId()));
			}else{
				criteria = session.createCriteria(ProductLogModel.class,"productLog")				
						.createAlias("productLog.branchModel", "branch")
						.add(Restrictions.eq("branch.id",productLogModel.getBranchModel().getId()));
			}

			
			List list = criteria.list();
			return list;


		}catch(Exception e){
			return null;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductInventoryModel> getExpiredProducts(ProductInventoryModel productInventoryModel) {
		
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
		criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
				.createAlias("productInventory.branchModel", "branch")
				.createAlias("branch.organizationModel", "org")
				.add(Restrictions.eq("org.id",productInventoryModel.getBranchModel().getOrganizationModel().getId()))
				.add(Restrictions.lt("productInventory.expiryDate", productInventoryModel.getExpiryDate()));
		}else{
			criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.branchModel", "branch")
					.add(Restrictions.eq("branch.id",productInventoryModel.getBranchModel().getId()))
					.add(Restrictions.lt("productInventory.expiryDate", productInventoryModel.getExpiryDate()));
		}
		List list = criteria.list();
	
			return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductInventoryModel> listProductsGroupByOrganization(ProductInventoryModel productInventoryModel) {
		try{
			Session session = sessionFactory.openSession();
					
			List result = session.createCriteria(ProductInventoryModel.class, "productInventory")
					.createAlias("productInventory.productModel", "product")
					.createAlias("productInventory.agencyModel", "agency")
					.createAlias("productInventory.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
				    .setProjection( Projections.projectionList()				    	
				        .add( Projections.property("product.id"), "productInventoryModel.getProductModel().getId()" )
				        .add(Projections.groupProperty("product.id")))
				   .add( Restrictions.eq("org.id", productInventoryModel.getBranchModel().getOrganizationModel().getId()) )
				    .list();

			return result;


		}catch(Exception e){

			return null;
		}
	
}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductDamageModel> listProductsDamage(ProductDamageModel productDamageModel) {
		try{
			Session session = sessionFactory.openSession();
			if(productDamageModel.getBranchModel().getOrganizationModel().getId()!=null){
			criteria = session.createCriteria(ProductDamageModel.class,"productDamage")
					.createAlias("productDamage.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",productDamageModel.getBranchModel().getOrganizationModel().getId()));
			}else{
				 criteria = session.createCriteria(ProductDamageModel.class,"productDamage")					
							.createAlias("productDamage.branchModel", "branch")					
							.add(Restrictions.eq("branch.id",productDamageModel.getBranchModel().getId()));
								
			}		
			List list = criteria.list();		
			return list;

		}catch(Exception e){
			return null;
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductInventoryModel> listProductsGoingToExpire(ProductInventoryModel productInventoryModel) {
		try{
			Session session=sessionFactory.openSession();
			session.beginTransaction();		
			Query query = null;
			if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
			 query = session.createSQLQuery(
					"CALL productNotifications(:id,:role)")
					.addEntity(ProductInventoryModel.class)
					.setParameter("id",productInventoryModel.getBranchModel().getOrganizationModel().getId())
					.setParameter("role",productInventoryModel.getCreatorRoleModel().getId());
			}else{
				 query = session.createSQLQuery(
							"CALL productNotifications(:id,:role)")
							.addEntity(ProductInventoryModel.class)
							.setParameter("id",productInventoryModel.getBranchModel().getId())
							.setParameter("role",productInventoryModel.getCreatorRoleModel().getId())
							;
			}
			List list = query.list();
						
			session.getTransaction().commit();
			session.close();
			return list;
			
			}catch(Exception e){
				return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductInventoryModel> lessQuantityProducts(ProductInventoryModel productInventoryModel) {
		
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria=null;
		if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
			 criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",productInventoryModel.getBranchModel().getOrganizationModel().getId()))
					.add(Restrictions.lt("productInventory.quantity", 20));
		}else{
			criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.branchModel", "branch")
					.add(Restrictions.eq("branch.id",productInventoryModel.getBranchModel().getId()))
					.add(Restrictions.lt("productInventory.quantity", 20));
		}
		
		List list = criteria.list();
		session.close();
			return list;
	}

@SuppressWarnings({ "rawtypes", "unchecked" })
public ArrayList<ProductModel> productAvailabilityCheck(int agencyId,String productName,int subCat){
		
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(ProductModel.class,"productModel")
									.createAlias("productModel.agencyModel", "agency")
									.createAlias("productModel.subCategoryModel", "sucCatModel")
									.add(Restrictions.eq("agency.id", agencyId))
									.add(Restrictions.eq("name", productName))
									.add(Restrictions.eq("sucCatModel.id", subCat));
		List list=criteria.list();
		session.close();
		return (ArrayList<ProductModel>) list;
		}

	@SuppressWarnings("rawtypes")
	public void insertBulkProducts(List<ProductModel> proBulklist,AgencyModel agencyModel1,int createdby,int createrRole) {
		productModel = new ProductModel();
		categoryModel = new CategoryModel();
		subCategoryModel = new SubCategoryModel();
		creatorRoleModel = new RoleModel();
		agencyModel = new AgencyModel();
		
		Session session1 = sessionFactory.openSession();
		Session session = sessionFactory.openSession();
		session1.beginTransaction();
		int j=0;
		try{
			List list=(List)session1.createSQLQuery("select max(RMBS20101) from rmbs.rmbs201").list();
			j=(int)list.get(j);
		}
		catch(Exception e){

		}session1.close();
		session.beginTransaction();
		for (int i =0;i<proBulklist.size();i++)
		{
			productModel.setName(proBulklist.get(i).getName());
			productModel.setmFCompanay(proBulklist.get(i).getmFCompanay());
			productModel.setSchDrug(proBulklist.get(i).getSchDrug());
			subCategoryModel.setId(proBulklist.get(i).getSubCategoryModel().getId());
			productModel.setSubCategoryModel(subCategoryModel);
			categoryModel.setId(proBulklist.get(i).getCategoryModel().getId());
			productModel.setCategoryModel(categoryModel);
			agencyModel.setId(agencyModel1.getId());
			productModel.setAgencyModel(agencyModel);
			try {
				productModel.setCreatedDate(DateAndTimeUtilities.parseStringDateToSqlDate(DateAndTimeUtilities.getCurrentDateTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			productModel.setCreatedBy(createdby);
			creatorRoleModel.setId(createrRole);
			productModel.setCreatorRoleModel(creatorRoleModel);
			String code=productModel.getName().substring(0, 2)+"/"
					+productModel.getmFCompanay().substring(0, 2)+"/"
					+productModel.getCategoryModel().getId()+"/"
					+productModel.getSubCategoryModel().getId()+"/"
					+(j+1);
			productModel.setCode(code);
			
			List li=productAvailabilityCheck(agencyModel.getId(), proBulklist.get(i).getName(), subCategoryModel.getId());
			if(li.size()!=0){
				
				throw new IllegalArgumentException("Received file does not have a standard excel extension.");
			}
			else{
				session.save(productModel);
				j=productModel.getId();
			}
			if (i%1 == 0)
			{
				session.flush();
				session.clear();
			}
		}
		session.getTransaction().commit();
		session.close();
		
	}

	@SuppressWarnings("unchecked")
	public ArrayList<AgencyModel> getAgencyList(AgencyModel agencyModel) {
		Session session=sessionFactory.openSession();
		
		Criteria cr = session.createCriteria(AgencyModel.class,"agency")
				.createAlias("agency.resourceModel", "resource")
				.createAlias("resource.accountStatusModel", "status")
				.add(Restrictions.eq("status.id", agencyModel.getResourceModel().getAccountStatusModel().getId()))
				.add(Restrictions.gt("id", 0))
				;
			   
		return (ArrayList<AgencyModel>) cr.list();
	}
	
	
	public boolean saveDamegedProducts(ProductDamageModel productDamageModel) {
		  boolean success = false;  
		 
				Session session= sessionFactory.openSession();
					session.beginTransaction();	
					
					
			try {
				session.saveOrUpdate(productDamageModel.getProductInventoryModel());
				session.saveOrUpdate(productDamageModel.getReasonModel());		
				session.saveOrUpdate(productDamageModel);
				
				
				session.getTransaction().commit();
			} catch (Exception e) {
				session.cancelQuery();
			}finally{
				session.close();
			}
				
													
			
				    success = true;
			
						
				
	//		}
		return success;
				
	}	
	
	@SuppressWarnings({ "rawtypes", "unused" })
	public Integer getProductQuantityFromInv(PurchaseLineItemsModel purchaseLineItemsModel) {
		
		Session session = sessionFactory.openSession();
		int i=0;
		Criteria criteria = session.createCriteria(PurchaseLineItemsModel.class,"pLineItems")
							.createAlias("pLineItems.productInventoryModel", "pInv")
							.setProjection(Projections.projectionList()
									.add(Projections.property("pInv.quantity"),"quantity"))
									.add(Restrictions.eq("pLineItems.id", purchaseLineItemsModel.getId()));
							
				List list = criteria.list();
				ProductInventoryModel productInventoryModel = new ProductInventoryModel();
				Integer qty = (Integer) list.get(i);
					session.close();		
		return qty;
	}


	@SuppressWarnings("unchecked")
	public List<ProductInventoryModel> getProductListFromInventory(int bid, int catId) {
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(ProductInventoryModel.class,"ProductInvModel")
				.createAlias("ProductInvModel.branchModel", "branch")
				.createAlias("ProductInvModel.productModel", "proModel")
				.createAlias("proModel.categoryModel", "category")
				.add(Restrictions.eq("branch.id", bid))
				.add(Restrictions.eq("category.id", catId));
		List<ProductInventoryModel> inventoryModels = criteria.list();
		
		return inventoryModels;
	}
	
	
	@Override
	public Long countAll(ProductModel productModel) {
		Session session = sessionFactory.openSession();
		 Object obj = session.createCriteria(ProductModel.class)			 	
					.setProjection(Projections.rowCount()).uniqueResult();
	
		 return ((Number) obj).longValue();
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductModel> listByPage(int firstResult,
			int maxResult, ProductModel productModel) {
		Session session = sessionFactory.openSession();
		
		Criteria criteria = session.createCriteria(ProductModel.class,"product")

				.setFirstResult(firstResult)
				.setMaxResults(maxResult)
				.addOrder(Order.desc("product.id"))
				
				;   	       

        return criteria.list();
	}
	
	@Override
	public Long countAll(ProductInventoryModel productInventoryModel) {
		Session session = sessionFactory.openSession();
		 Object obj = session.createCriteria(ProductInventoryModel.class)			 	
					.setProjection(Projections.rowCount()).uniqueResult();
	
		 return ((Number) obj).longValue();
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductInventoryModel> listByPage(int firstResult,
			int maxResult, ProductInventoryModel productInventoryModel) {
		
		try{
			Session session = sessionFactory.openSession();
			if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
			criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")				
					.createAlias("productInventory.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",productInventoryModel.getBranchModel().getOrganizationModel().getId()))
					.setFirstResult(firstResult)
					.setMaxResults(maxResult)
					.addOrder(Order.desc("productInventory.id"))
					
					;
			}else{
				criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")				
						.createAlias("productInventory.branchModel", "branch")
						.add(Restrictions.eq("branch.id",productInventoryModel.getBranchModel().getId()))
						.setFirstResult(firstResult)
						.setMaxResults(maxResult)
						.addOrder(Order.desc("productInventory.id"))
						
						;
			}
			
			List list = criteria.list();
			
			return list;


		}catch(Exception e){

			return null;
		}

				   	       

        
	}
	
	@Override
	public Long countAll(ProductLogModel productLogModel) {
		Session session = sessionFactory.openSession();
		 Object obj = session.createCriteria(ProductLogModel.class)			 	
					.setProjection(Projections.rowCount()).uniqueResult();
	
		 return ((Number) obj).longValue();
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductLogModel> listByPage(int firstResult,
			int maxResult, ProductLogModel productLogModel) {
		
		try{
			Session session = sessionFactory.openSession();
			if(productLogModel.getBranchModel().getOrganizationModel().getId()!=null){
			criteria = session.createCriteria(ProductLogModel.class,"productLog")				
					.createAlias("productLog.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",productLogModel.getBranchModel().getOrganizationModel().getId()))
					.setFirstResult(firstResult)
					.setMaxResults(maxResult)
					.addOrder(Order.desc("productLog.id"))
						;
			}else{
				criteria = session.createCriteria(ProductLogModel.class,"productLog")				
						.createAlias("productLog.branchModel", "branch")
						.add(Restrictions.eq("branch.id",productLogModel.getBranchModel().getId()))
						.setFirstResult(firstResult)
						.setMaxResults(maxResult)
						.addOrder(Order.desc("productLog.id"))
						;
			}

			
			List list = criteria.list();
			return list;


		}catch(Exception e){
			return null;
		}   	       

        
	}
	
	@Override
	public Long countAllOfExpiredProducts(ProductInventoryModel productInventoryModel) {
		Session session = sessionFactory.openSession();
		if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
		 Object obj = session.createCriteria(ProductInventoryModel.class,"productInventory")	
				 .createAlias("productInventory.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",productInventoryModel.getBranchModel().getOrganizationModel().getId()))
				 .add(Restrictions.lt("productInventory.expiryDate", productInventoryModel.getExpiryDate()))
					.setProjection(Projections.rowCount()).uniqueResult();
	
		 return ((Number) obj).longValue();
		}else{
			Object obj = session.createCriteria(ProductInventoryModel.class,"productInventory")	
					 .createAlias("productInventory.branchModel", "branch")						
					 .add(Restrictions.eq("branch.id",productInventoryModel.getBranchModel().getId()))
					 .add(Restrictions.lt("productInventory.expiryDate", productInventoryModel.getExpiryDate()))
						.setProjection(Projections.rowCount()).uniqueResult();
		
			 return ((Number) obj).longValue();
		}
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ProductInventoryModel> listByPageOfExpiryProducts(int firstResult,
			int maxResult, ProductInventoryModel productInventoryModel) {
		
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
		criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
				.createAlias("productInventory.branchModel", "branch")
				.createAlias("branch.organizationModel", "org")
				.add(Restrictions.eq("org.id",productInventoryModel.getBranchModel().getOrganizationModel().getId()))
				.add(Restrictions.lt("productInventory.expiryDate", productInventoryModel.getExpiryDate()))
				.setFirstResult(firstResult)
				.setMaxResults(maxResult)
				.addOrder(Order.desc("productInventory.id"));
		}else{
			criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.branchModel", "branch")
					.add(Restrictions.eq("branch.id",productInventoryModel.getBranchModel().getId()))
					.add(Restrictions.lt("productInventory.expiryDate", productInventoryModel.getExpiryDate()))
					.setFirstResult(firstResult)
					.setMaxResults(maxResult)
					.addOrder(Order.desc("productInventory.id"));
		}
		List list = criteria.list();
	
			return list;   	       

        
	}


	@Override
	public Long countAll(ProductDamageModel productDamageModel) {
		Session session = sessionFactory.openSession();
		 Object obj = session.createCriteria(ProductDamageModel.class,"productDamage")					
					.setProjection(Projections.rowCount()).uniqueResult();
	
		 return ((Number) obj).longValue();
	}


	@Override
	public List<ProductDamageModel> listByPage(int firstResult,int maxResult, ProductDamageModel productDamageModel) {
		try{
			Session session = sessionFactory.openSession();
			if(productDamageModel.getBranchModel().getOrganizationModel().getId()!=null){
			criteria = session.createCriteria(ProductDamageModel.class,"productDamage")
					.createAlias("productDamage.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",productDamageModel.getBranchModel().getOrganizationModel().getId()))
					.setFirstResult(firstResult)
					.setMaxResults(maxResult)
					.addOrder(Order.desc("productDamage.id"));
			}else{
				 criteria = session.createCriteria(ProductDamageModel.class,"productDamage")					
							.createAlias("productDamage.branchModel", "branch")					
							.add(Restrictions.eq("branch.id",productDamageModel.getBranchModel().getId()))
							.setFirstResult(firstResult)
							.setMaxResults(maxResult)
							.addOrder(Order.desc("productDamage.id"));
								
			}		
			List list = criteria.list();		
			return list;

		}catch(Exception e){
			return null;
		}
	}


	@Override
	public Long countAllOfGoingToExpiryProducts(
			ProductInventoryModel productInventoryModel) {	
		try{
			Session session=sessionFactory.openSession();
			session.beginTransaction();		
			Query query = null;
			if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
			 query = session.createSQLQuery(
					"CALL productNotifications(:id,:role)")
					.addEntity(ProductInventoryModel.class)
					.setParameter("id",productInventoryModel.getBranchModel().getOrganizationModel().getId())
					.setParameter("role",productInventoryModel.getCreatorRoleModel().getId())
					;
			 		
			}else{
				 query = session.createSQLQuery(
							"CALL productNotifications(:id,:role)")
							.addEntity(ProductInventoryModel.class)
							.setParameter("id",productInventoryModel.getBranchModel().getId())
							.setParameter("role",productInventoryModel.getCreatorRoleModel().getId())
							;
			}
			List list = query.list();
			System.out.println("list size"+list.size());		
			session.getTransaction().commit();
			session.close();
			return ((Number) list.size()).longValue();
			
			}catch(Exception e){
				return null;
		}
	
	
	}
	
	

	@Override
	public List<ProductInventoryModel> listByPageOfGoingToExpiryProducts(int firstResult, int maxResult,
			ProductInventoryModel productInventoryModel) {
		try{
			Session session=sessionFactory.openSession();
			session.beginTransaction();		
			Query query = null;
			if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
			 query = session.createSQLQuery(
					"CALL productNotificationsByLimit(:id,:role,:firstresult,:maxresult)")
					.addEntity(ProductInventoryModel.class)
					.setParameter("id",productInventoryModel.getBranchModel().getOrganizationModel().getId())
					.setParameter("role",productInventoryModel.getCreatorRoleModel().getId())
					.setParameter("firstresult",firstResult)
					.setParameter("maxresult",maxResult)	
			 ;
			 		
			}else{
				 query = session.createSQLQuery(
							"CALL productNotificationsByLimit(:id,:role,:firstresult,:maxresult)")
							.addEntity(ProductInventoryModel.class)
							.setParameter("id",productInventoryModel.getBranchModel().getId())
							.setParameter("role",productInventoryModel.getCreatorRoleModel().getId())
							.setParameter("firstresult",firstResult)
							.setParameter("maxresult",maxResult)							
							;
			}
			
			
			List<ProductInventoryModel> list = query.list();
			
			session.getTransaction().commit();
			session.close();
			return list;
			
			}catch(Exception e){
				return null;
		}
	}


	@Override
	public Long countAllOfLessQuantityProducts(
			ProductInventoryModel productInventoryModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		
		if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
			obj = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",productInventoryModel.getBranchModel().getOrganizationModel().getId()))
					.add(Restrictions.lt("productInventory.quantity", 20))
					.setProjection(Projections.rowCount()).uniqueResult()
					;
		}else{
			obj = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.branchModel", "branch")
					.add(Restrictions.eq("branch.id",productInventoryModel.getBranchModel().getId()))
					.add(Restrictions.lt("productInventory.quantity", 20))
					.setProjection(Projections.rowCount()).uniqueResult();
		}
		
		
		session.close();
			return ((Number) obj).longValue();
	}


	@Override
	public List<ProductInventoryModel> listByPageOfLessQuantityProducts(
			int firstResult, int pageSize,
			ProductInventoryModel productInventoryModel) {
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria=null;
		if(productInventoryModel.getBranchModel().getOrganizationModel().getId()!=null){
			 criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",productInventoryModel.getBranchModel().getOrganizationModel().getId()))
					.add(Restrictions.lt("productInventory.quantity", 20))
					.setFirstResult(firstResult)
					.setMaxResults(pageSize)
					.addOrder(Order.desc("productInventory.id"));
		}else{
			criteria = session.createCriteria(ProductInventoryModel.class,"productInventory")
					.createAlias("productInventory.branchModel", "branch")
					.add(Restrictions.eq("branch.id",productInventoryModel.getBranchModel().getId()))
					.add(Restrictions.lt("productInventory.quantity", 20))
					.setFirstResult(firstResult)
					.setMaxResults(pageSize)
					.addOrder(Order.desc("productInventory.id"));
		}
		
		List list = criteria.list();
		session.close();
			return list;
	}
	
	

}