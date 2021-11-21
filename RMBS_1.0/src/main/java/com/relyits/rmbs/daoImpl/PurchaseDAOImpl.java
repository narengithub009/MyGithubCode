package com.relyits.rmbs.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.PurchaseDAO;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.product.ProductModel;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.SubCategoryModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.BranchModel;


@Repository("purchaseDAO")
public class PurchaseDAOImpl implements PurchaseDAO{

	Criteria criteria  = null;
	@Autowired
	private SessionFactory sessionFactory;
	PurchaseLineItemsModel purchaseLineItemsModel=null;
	PurchaseOrderModel purchaseOrderModel=null;

	ProductModel productModel = null;
	ProductInventoryModel productInventoryModel=null;
	CategoryModel categoryModel=null;
	SubCategoryModel subCategoryModel=null;
	RoleModel creatorRoleModel=null;
	AgencyModel agencyModel=null;
	BranchModel branchModel=null;
	Object obj = null;

	@SuppressWarnings("unused")
	public int createPurchaseOrder(List<PurchaseLineItemsModel> purchaseLineItemsModels){
		int count=0;
		int returnval=0;
		if(purchaseLineItemsModels!=null){

			int i=0;
			int j=0;
			int k=0;
			Session session=sessionFactory.openSession();
			session.beginTransaction();

			try{
				j=(int) session.createSQLQuery("select max(RMBS40101) from rmbs.rmbs401").list().get(i);
			}catch(Exception e){

			}

			purchaseOrderModel=purchaseLineItemsModels.get(i).getPurchaseOrderModel();

			productInventoryModel=new ProductInventoryModel();
				agencyModel=new AgencyModel();
				agencyModel=(AgencyModel)session.get(AgencyModel.class, purchaseLineItemsModels.get(i).getProductInventoryModel().getAgencyModel().getId());
				purchaseOrderModel.setAgencyModel(agencyModel);
				
				String oIdByDt="PO"+purchaseOrderModel.getOrderIdbyDate().replaceAll("-", "")+"/"
					+purchaseOrderModel.getBranchModel().getId()+"/"
					+purchaseOrderModel.getAgencyModel().getId()+"/"
					+(j+1);
			purchaseOrderModel.setOrderIdByDate(oIdByDt);
			purchaseOrderModel.setAgencyModel((AgencyModel)session.get(AgencyModel.class, purchaseLineItemsModels.get(i).getProductInventoryModel().getAgencyModel().getId()));
			purchaseOrderModel.setBranchModel((BranchModel)session.get(BranchModel.class, purchaseLineItemsModels.get(i).getPurchaseOrderModel().getBranchModel().getId()));
			session.save(purchaseOrderModel);
			returnval=purchaseOrderModel.getId();
			if(purchaseOrderModel.getId()!=null){
				for(i=0;i<purchaseLineItemsModels.size();i++){
					purchaseLineItemsModel=new PurchaseLineItemsModel();
					purchaseLineItemsModel=purchaseLineItemsModels.get(i);
					if(purchaseLineItemsModel!=null || !purchaseLineItemsModel.equals(null)){

						productModel = new ProductModel();
						productInventoryModel= new ProductInventoryModel();
						categoryModel = new CategoryModel();
						subCategoryModel = new SubCategoryModel();
						creatorRoleModel = new RoleModel();
						
						branchModel = new BranchModel();

						productInventoryModel=purchaseLineItemsModel.getProductInventoryModel();

						productModel = productInventoryModel.getProductModel();

					

						////////////////////////////////////////////
						if(productInventoryModel.getId()==null ){//validating in inventory

							if(productModel.getId()==null){//validating in products
								if(i==0){
									try{
										k=(int) session.createSQLQuery("select max(RMBS20101) from rmbs.rmbs201").list().get(i);

									}catch(Exception e){

									}
								}
								
								categoryModel=(CategoryModel) session.get(CategoryModel.class, productModel.getCategoryModel().getId());
								subCategoryModel=(SubCategoryModel) session.get(SubCategoryModel.class, productModel.getSubCategoryModel().getId());
								creatorRoleModel=(RoleModel) session.get(RoleModel.class, productModel.getCreatorRoleModel().getId());
								branchModel=(BranchModel) session.get(BranchModel.class, productInventoryModel.getBranchModel().getId());
								String pCode=productModel.getName().substring(0, 3)+"/"
										+productModel.getmFCompanay().substring(0, 3)+"/"
										+productModel.getCategoryModel().getId()+"/"
										+productModel.getSubCategoryModel().getId()+"/"
										+(k+1);
								productModel.setCode(pCode);
								productModel.setCategoryModel(categoryModel);
								productModel.setAgencyModel(agencyModel);
								productModel.setSubCategoryModel(subCategoryModel);
								productModel.setCreatorRoleModel(creatorRoleModel);
						
								session.save(productModel);
								k=productModel.getId();
								//	}.
								productInventoryModel.setProductModel(productModel);
								productInventoryModel.setCreatorRoleModel(creatorRoleModel);
								productInventoryModel.setAgencyModel(purchaseOrderModel.getAgencyModel());
								productInventoryModel.setBranchModel(purchaseOrderModel.getBranchModel());

								session.save(productInventoryModel);

								purchaseLineItemsModel.setPurchaseOrderModel(purchaseOrderModel);
								purchaseLineItemsModel.setProductInventoryModel(productInventoryModel);

								session.save(purchaseLineItemsModel);

								}else{

								productModel=(ProductModel) session.get(ProductModel.class, productModel.getId());
								creatorRoleModel=(RoleModel) session.get(RoleModel.class, productModel.getCreatorRoleModel().getId());
								productInventoryModel.setProductModel(productModel);
								productInventoryModel.setCreatorRoleModel(creatorRoleModel);
								productInventoryModel.setAgencyModel(purchaseOrderModel.getAgencyModel());
								productInventoryModel.setBranchModel(purchaseOrderModel.getBranchModel());
				
								session.save(productInventoryModel);

								purchaseLineItemsModel.setPurchaseOrderModel(purchaseOrderModel);
								purchaseLineItemsModel.setProductInventoryModel(productInventoryModel);

								session.save(purchaseLineItemsModel);

							}
						}else{
				
							Query query = session.createSQLQuery(
									"CALL updateProductInventoryByQuantity(:pIId,:quantity,:userId,:userRole,:branchId)")
									.addEntity(ProductInventoryModel.class)
									.setParameter("pIId",productInventoryModel.getId())
									.setParameter("quantity", productInventoryModel.getQuantity())
									.setParameter("userId", productInventoryModel.getCreatedBy())
									.setParameter("userRole",productInventoryModel.getCreatorRoleModel().getId())
									.setParameter("branchId",productInventoryModel.getBranchModel().getId());
							int l=query.executeUpdate();
							if(l!=0){
								productInventoryModel=(ProductInventoryModel) session.get(ProductInventoryModel.class, productInventoryModel.getId());
								purchaseLineItemsModel.setPurchaseOrderModel(purchaseOrderModel);
								purchaseLineItemsModel.setProductInventoryModel(productInventoryModel);

								session.save(purchaseLineItemsModel);

							
							}

						}
						/////////////////////////////////////////////

					}

					if(purchaseLineItemsModels.size()!=i+1){
						session.getTransaction().commit();
						count++;
						session.beginTransaction();
					}else{
						session.getTransaction().commit();
						count++;
						session.close();
					}

				}


			}


		}

		return returnval;

	}
	
	@SuppressWarnings("rawtypes")
	public PurchaseOrderModel validateInvoice(PurchaseOrderModel purchaseOrderModel){
		Session session = sessionFactory.openSession();
		if(purchaseOrderModel.getId()!=null){
			 return (PurchaseOrderModel) sessionFactory.getCurrentSession().get(PurchaseOrderModel.class, purchaseOrderModel.getId());
		}else{
			
			Criteria criteria = session.createCriteria(PurchaseOrderModel.class,"purchaseOrder")
					.add(Restrictions.eq("invoiceNo",purchaseOrderModel.getInvoiceNo()));	        
	           List invoice = criteria.list();					
	           PurchaseOrderModel purchaseOrderModel1 = null;
						for(int i=0;i<invoice.size();i++){
							purchaseOrderModel1=(PurchaseOrderModel) invoice.get(i);
						}
						
						return purchaseOrderModel1;
	    }
	}	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PurchaseLineItemsModel> getPurchaseLineItemsModelsByPurcheseOrderModel(
			PurchaseOrderModel purchaseOrderModel) {
		Session session=sessionFactory.openSession();
		Criteria cr = session.createCriteria(PurchaseLineItemsModel.class,"POLI")
				.createAlias("POLI.purchaseOrderModel", "PO")
				.add(Restrictions.eq("PO.id", purchaseOrderModel.getId()));
		return cr.list();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PurchaseOrderModel> getPurchaseOrderList(PurchaseOrderModel purchaseOrderModel) {
		try{
			Session session = sessionFactory.openSession();
			if(purchaseOrderModel.getBranchModel().getOrganizationModel().getId()!= null){
			criteria = session.createCriteria(PurchaseOrderModel.class,"purchaseOrder")				
					.createAlias("purchaseOrder.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",purchaseOrderModel.getBranchModel().getOrganizationModel().getId()));
			}else{
				criteria = session.createCriteria(PurchaseOrderModel.class,"purchaseOrder")				
						.createAlias("purchaseOrder.branchModel", "branch")
						.add(Restrictions.eq("branch.id",purchaseOrderModel.getBranchModel().getId()));
			}

			List list = criteria.list();
			return list;


		}catch(Exception e){
			return null;
		}
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PurchaseLineItemsModel> getPurchaseLineItems(PurchaseLineItemsModel purchaseLineItemsModel) {
		Session session = sessionFactory.openSession();
		try{
			Criteria criteria = session.createCriteria(PurchaseLineItemsModel.class,"purchaseLineItems")
					.createAlias("purchaseLineItems.purchaseOrderModel", "purchaseOrder")
					.add(Restrictions.eq("purchaseOrder.id",purchaseLineItemsModel.getPurchaseOrderModel().getId()));		        

			List purchaseLineItems = criteria.list();
			
			return purchaseLineItems;
		}catch(Exception e){
			return null;
		}


	}



//	**************purchase returns stuff*******************
	
	public List<String> getPurchaseInvoiceNo(PurchaseOrderModel purchaseOrderModel){
		
		Session session = sessionFactory.openSession();
		
		Criteria criteria = session.createCriteria(PurchaseOrderModel.class , "purchaseModel")
		.add(Restrictions.like("invoiceNo", purchaseOrderModel.getInvoiceNo(),MatchMode.ANYWHERE));
		@SuppressWarnings("unchecked")
		List<String> list = criteria.list();
		return list;
	}

	@Override
	public PurchaseOrderModel getPurchaseOrderModel(PurchaseOrderModel purchaseOrderModel1) {
		
		Session session = sessionFactory.openSession();
		
		purchaseOrderModel = (PurchaseOrderModel) session.get(PurchaseOrderModel.class, purchaseOrderModel1.getId());
		session.close();
		return purchaseOrderModel;
	}

	@Override
	public Long countAll(PurchaseOrderModel purchaseOrderModel) {
		try{
			Session session = sessionFactory.openSession();
			if(purchaseOrderModel.getBranchModel().getOrganizationModel().getId()!= null){
			obj = session.createCriteria(PurchaseOrderModel.class,"purchaseOrder")				
					.createAlias("purchaseOrder.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",purchaseOrderModel.getBranchModel().getOrganizationModel().getId()))
					.setProjection(Projections.rowCount()).uniqueResult();
			}else{
				obj = session.createCriteria(PurchaseOrderModel.class,"purchaseOrder")				
						.createAlias("purchaseOrder.branchModel", "branch")
						.add(Restrictions.eq("branch.id",purchaseOrderModel.getBranchModel().getId()))
						.setProjection(Projections.rowCount()).uniqueResult();
			}

			
			return ((Number)obj).longValue();


		}catch(Exception e){
			return null;
		}
	}
	

	@Override
	public List<PurchaseOrderModel> listByPage(int firstResult, int pageSize,PurchaseOrderModel purchaseOrderModel) {
		try{
			Session session = sessionFactory.openSession();
			if(purchaseOrderModel.getBranchModel().getOrganizationModel().getId()!= null){
			criteria = session.createCriteria(PurchaseOrderModel.class,"purchaseOrder")				
					.createAlias("purchaseOrder.branchModel", "branch")
					.createAlias("branch.organizationModel", "org")
					.add(Restrictions.eq("org.id",purchaseOrderModel.getBranchModel().getOrganizationModel().getId()))
					.setFirstResult(firstResult)
					.setMaxResults(pageSize)
					.addOrder(Order.desc("purchaseOrder.id"));
			}else{
				criteria = session.createCriteria(PurchaseOrderModel.class,"purchaseOrder")				
						.createAlias("purchaseOrder.branchModel", "branch")
						.add(Restrictions.eq("branch.id",purchaseOrderModel.getBranchModel().getId()))
						.setFirstResult(firstResult)
						.setMaxResults(pageSize)
						.addOrder(Order.desc("purchaseOrder.id"));
			}

			List list = criteria.list();
			return list;


		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Long countAll(PurchaseLineItemsModel purchaseLineItemsModel) {
		Session session = sessionFactory.openSession();
		try{
			obj = session.createCriteria(PurchaseLineItemsModel.class,"purchaseLineItems")
					.createAlias("purchaseLineItems.purchaseOrderModel", "purchaseOrder")
					.add(Restrictions.eq("purchaseOrder.id",purchaseLineItemsModel.getPurchaseOrderModel().getId()))
					.setProjection(Projections.rowCount()).uniqueResult();		        

		
			return ((Number)obj).longValue();
		}catch(Exception e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseLineItemsModel> listByPage(int firstResult,	int pageSize, PurchaseLineItemsModel purchaseLineItemsModel) {
		Session session = sessionFactory.openSession();
		try{
			Criteria criteria = session.createCriteria(PurchaseLineItemsModel.class,"purchaseLineItems")
					.createAlias("purchaseLineItems.purchaseOrderModel", "purchaseOrder")
					.add(Restrictions.eq("purchaseOrder.id",purchaseLineItemsModel.getPurchaseOrderModel().getId()))
					.setFirstResult(firstResult)
					.setMaxResults(pageSize)
					.addOrder(Order.desc("purchaseLineItems.id"));		        

			List<PurchaseLineItemsModel> purchaseLineItems = criteria.list();
			
			return purchaseLineItems;
		}catch(Exception e){
			return null;
		}
	}
}
