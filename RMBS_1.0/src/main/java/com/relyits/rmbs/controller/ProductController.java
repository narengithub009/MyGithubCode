package com.relyits.rmbs.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.relyits.rmbs.beans.product.FileBean;
import com.relyits.rmbs.beans.product.ProductBean;
import com.relyits.rmbs.beans.product.ProductDamageBean;
import com.relyits.rmbs.beans.product.ProductInventoryBean;
import com.relyits.rmbs.beans.product.ProductLogBean;
import com.relyits.rmbs.beans.registration.AgencyBean;
import com.relyits.rmbs.beans.registration.BranchBean;
import com.relyits.rmbs.beans.registration.BranchRegistrationFormBean;
import com.relyits.rmbs.beans.resources.CategoryBean;
import com.relyits.rmbs.beans.resources.RoleBean;
import com.relyits.rmbs.beans.resources.SubCategoryBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.product.ProductBeanPreparation;
import com.relyits.rmbs.beans_preparation.product.ProductDamageBeanPreparation;
import com.relyits.rmbs.beans_preparation.product.ProductInventoryBeanPreparation;
import com.relyits.rmbs.beans_preparation.product.ProductInventoryBeanPreparation1;
import com.relyits.rmbs.beans_preparation.product.ProductLogBeanPreparation;
import com.relyits.rmbs.beans_preparation.product.SupplierBeanPreparation;
import com.relyits.rmbs.model.product.FileDataModel;
import com.relyits.rmbs.model.product.ProductDamageModel;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.product.ProductLogModel;
import com.relyits.rmbs.model.product.ProductModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.ReasonModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.refference.SubCategoryModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.model_preparation.product.ProductInventoryModelPreparation;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.OrganizationService;
import com.relyits.rmbs.service.OutletService;
import com.relyits.rmbs.service.PageNavigator;
import com.relyits.rmbs.service.ProductService;
import com.relyits.rmbs.service.ResourcesService;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;
import com.relyits.rmbs.utilities.ExposablePropertyPaceholderConfigurer;
import com.relyits.rmbs.utilities.FormUtilities;
import com.relyits.rmbs.utilities.SessionUtilities;
import com.relyits.rmbs.validator.DamagedProductRegValidator;
import com.relyits.rmbs.validator.ProductFormValidator;


@Controller
public class ProductController {


	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private OutletService outletService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ResourcesService resourcesService;
	
	@Autowired
    private PageNavigator pageNavigator;
	
	@Autowired
	private ExposablePropertyPaceholderConfigurer properitesMap;

	@Autowired
	private ProductFormValidator productFormValidator;
	@Autowired
	private DamagedProductRegValidator damagedProductRegValidator;
	List<ProductInventoryModel> productInventoryModels = null;

	private ProductInventoryModel productInventoryModel=null;
	private ProductInventoryBean productInventoryBean=null;
	private ProductModel productModel=null;
	private ProductLogModel productLogModel=null;
	private ProductBean productBean=null;
	private CategoryBean categoryBean=null;
	private SubCategoryModel subCategoryModel=null;
	private List<ProductModel> list=null;

	private  AgencyModel agencyModel=null;
	private  BranchModel branchModel=null;
	private OutletModel outletModel = null;
	private  RoleModel creatorRoleModel=null;
	private OrganizationModel organizationModel = null;
	private ProductDamageModel productDamageModel = null;
	private ReasonModel reasonModel =null;
	private ResourceModel resourceModel = null;
	private StatusModel accountStatusModel = null;

	private SubCategoryBean subCategoryBean = null;
	private  RoleBean creatorRoleBean=null;
	private  AgencyBean agencyBean=null;
	@SuppressWarnings("unused")
	private  BranchBean branchBean=null;

	private UserSessionBean userSessionBean = null;

	private Long count = null;

	List<String> productsList = null;
	Map<Integer,String> categories;
	CategoryModel categoryModel = null;


	Map<String, String> properties= null;


	int medical,general,organization,branch,outlet,product,enable ;
	String msg = null;

	@Value("${category.medical}") String categorymedical;
	@Value("${category.general}") String categorygeneral;
	@Value("${role.organization}") String org;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${category.product}") String categoryProduct;
	@Value("${message.productDamageSuccess}") String message;
	@Value("${status.enable}") String enableStatus;
	@Value("${app.page.size.default}") private int pageSize;
    @Value("${app.page.nav.trail}") private int pageNavTrail;



	public Map<String, String> initializeProperties(){

		medical=Integer.parseInt(categorymedical);
		general = Integer.parseInt(categorygeneral);
		organization = Integer.parseInt(org);
		branch = Integer.parseInt(branchRole);
		outlet = Integer.parseInt(outletRole);
		product = Integer.parseInt(categoryProduct);
		enable = Integer.parseInt(enableStatus);
		msg = message;

		return properties;
	}



	//***************************Product*****************************

	Map<String, Object> model = null;

	@RequestMapping(value="/productEntryForm" ,
			method=RequestMethod.GET)
	public ModelAndView productEntryForm(HttpSession session,
			@ModelAttribute("command") ProductInventoryBean productInventoryBean,BindingResult result,
			@RequestParam("task_form_type") String task_form_type,
			@RequestParam("task_type") String task_type){
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		initializeProperties();
		if(mode[0]==1){

			model = new HashMap<String, Object>();	
			String returnMessage=(String) session.getAttribute("msg");
			session.removeAttribute("msg");
			BranchModel branchModel = new BranchModel();
			OrganizationModel organizationModel = new OrganizationModel();
			organizationModel.setId(userSessionBean.getId());
			branchModel.setOrganizationModel(organizationModel);
			SubCategoryModel subCategoryModel = new SubCategoryModel();
			CategoryModel categoryModel = new CategoryModel();

			categoryModel.setId(medical);
			subCategoryModel.setCategoryModel(categoryModel);

			try {

				model.put("categories", getSubCategoryList(subCategoryModel));
				if(mode[1]== outlet){
					model.put("branch", userSessionBean.getBranchBean().getName());
					model.put("branchId", userSessionBean.getBranchBean().getId());
					model.put("organization", userSessionBean.getOrganizationBean().getName());
					model.put("organizationId", userSessionBean.getOrganizationBean().getId());
				}else if(mode[1]== branch){
					model.put("branch", userSessionBean.getName());
					model.put("branchId", userSessionBean.getId());
					model.put("organization", userSessionBean.getOrganizationBean().getName());
					model.put("organizationId", userSessionBean.getOrganizationBean().getId());
				}else{
					model.put("branch", "--Select Branch--");
					model.put("branchId", 0);
					model.put("organization", userSessionBean.getName());
					model.put("organizationId", userSessionBean.getId());
					model.put("branches", getBranches(branchModel));  
				}

				model.put("createdby",userSessionBean.getId());				
			} catch (Exception e) {

				e.printStackTrace();
			}
			model.put("msg", returnMessage);
			if(task_type.contentEquals("main") && task_form_type.contentEquals("medical")){

				if(mode[1]== organization){
					return new ModelAndView("openMedicalProductEntryFormByOrganization", model);
				}else if(mode[1]== branch){
					return new ModelAndView("openMedicalProductEntryFormByBranch", model);
				}else if(mode[1]== outlet){
					return new ModelAndView("openMedicalProductEntryFormByOutlet", model);
				}
			}else if(task_type.contentEquals("main") && task_form_type.contentEquals("general")){
				if(mode[1]==organization){
					return new ModelAndView("openGeneralProductEntryFormByOrganization", model);
				}else if(mode[1]== branch){
					return new ModelAndView("openGeneralProductEntryFormByBranch", model);
				}else if(mode[1]== outlet){
					return new ModelAndView("openGeneralProductEntryFormByOutlet", model);
				}
			}else if(task_type.contentEquals("sub") && task_form_type.contentEquals("medical")){
				result=(BindingResult) session.getAttribute("error");
				session.setAttribute("msg", returnMessage);
				session.setAttribute("error", result);
				return new ModelAndView("redirect:/userValidate.html");
//				return new ModelAndView("reOpenMedicalProductEntryForm", model);
			}else if(task_type.contentEquals("sub") && task_form_type.contentEquals("general")){
				session.setAttribute("msg", returnMessage);
				result=(BindingResult) session.getAttribute("error");
				session.setAttribute("error", result);
				return new ModelAndView("redirect:/userValidate.html");
//				return new ModelAndView("reOpenGeneralProductEntryForm", model);
			}
		}else{
			return new ModelAndView("error");
		}
		return null;
	

	}
	
	
	@RequestMapping(value = "/get_medical_agency_list", 
			method = RequestMethod.GET, 
			headers="Accept=*/*")
	public @ResponseBody List<String> getAgenciesLists(@RequestParam("term") String phrase) {
		agencyModel = new AgencyModel();
		resourceModel = new ResourceModel();
		accountStatusModel = new StatusModel();
		agencyModel.setAgencyName(phrase);
		accountStatusModel.setId(enable);
		resourceModel.setAccountStatusModel(accountStatusModel);
		agencyModel.setResourceModel(resourceModel);
		
		List<String> agenciesList = productService.getAgenciesList(agencyModel);
		List<String> agencies = new ArrayList<>();
		for(int i=0;i<agenciesList.size();i++){
			Object o=(Object)agenciesList.get(i);
			agencyModel =(AgencyModel)o;
			agencies.add(agencyModel.getAgencyName()+"_"+agencyModel.getId());

		}
		return agencies;
	}

	

	@RequestMapping(value = "/get_products_list", 
			method = RequestMethod.GET, 
			headers="Accept=*/*")
	public @ResponseBody List<String> getProductsList(@RequestParam("term") String phrase,
			@RequestParam("type") String type,
			@RequestParam("bId") String shopid,
			@RequestParam("flag") String flag) {
		productModel = new ProductModel();
		categoryModel = new CategoryModel();

		categoryModel.setId(Integer.parseInt(type));
		productModel.setCategoryModel(categoryModel);

		productModel.setName(phrase);
		List<String> products = new ArrayList<>();
		if(flag.contentEquals("P")){
			productsList = productService.getProductsList(productModel);
			for(int i=0;i<productsList.size();i++){
				Object o=(Object)productsList.get(i);
				productModel =(ProductModel)o;
				products.add(productModel.getName()+"_"+productModel.getSubCategoryModel().getCategory()+"_"+productModel.getId());
			}
		}else{
			productInventoryModel=new ProductInventoryModel();
			branchModel = new BranchModel();
			branchModel.setId(Integer.parseInt(shopid));
			productInventoryModel.setProductModel(productModel);
			productInventoryModel.setBranchModel(branchModel);
			List<ProductModel> productModels=productService.getInventroyProductsList(productInventoryModel);
			for(int i=0;i<productModels.size();i++){
				products.add(productModels.get(i).getName()+"_"+productModels.get(i).getSubCategoryModel().getCategory()+"_"+productModels.get(i).getId());
			}
		}


		return products;
	}

	public LinkedHashMap<Integer, String> getSubCategoryList(SubCategoryModel subCategoryModel){
		LinkedHashMap<Integer, String> categories = new LinkedHashMap<Integer, String>();
		List<SubCategoryModel> subCategory= productService.getSubCategoryListByCategory(subCategoryModel);
		for(int i=0; i<subCategory.size(); i++){
			int subCategoryid=subCategory.get(i).getId();
			String sCName=subCategory.get(i).getCategory();
			categories.put(subCategoryid, sCName);
		}
		return categories;
	}


	public LinkedHashMap<Integer, String> getBranches(BranchModel branchModel){
		LinkedHashMap<Integer, String> branches = new LinkedHashMap<Integer, String>();
		List<BranchModel> branchModels= branchService.getBranchesByOrganization(branchModel);
		for(int i=0; i<branchModels.size(); i++){
			int bid=branchModels.get(i).getId();
			String bname=branchModels.get(i).getName();
			branches.put(bid, bname);
		}
		return branches;
	}



	@RequestMapping(value = "saveProduct", 
			method = RequestMethod.POST)
	public  ModelAndView saveProduct(HttpSession session,@ModelAttribute("command") ProductInventoryBean productInventoryBean,BindingResult result,
			@RequestParam(value="uid") String uid) throws Exception {
		productFormValidator.validate(productInventoryBean, result);
		if(!result.hasErrors()){
		int mode[]=SessionUtilities.validateSession(uid, session);
		if(mode[0]==1){

			model = new HashMap<String, Object>();

			agencyModel = new AgencyModel();
			branchModel = new BranchModel();
			creatorRoleModel = new RoleModel();
			productInventoryModel = new ProductInventoryModel();
			productModel = new ProductModel();
			productBean = new ProductBean();
			creatorRoleBean = new RoleBean();

			agencyBean = new AgencyBean();
			branchBean = new BranchBean();

			if(productInventoryBean.getId()==null){
				creatorRoleBean.setId(mode[1]);				
				productInventoryBean.setCreatorRoleBean(creatorRoleBean);
				productInventoryBean.setCreatedBy(mode[2]);
				productBean=productInventoryBean.getProductBean();
				productBean.setCreatedBy(mode[2]);
				productBean.setCreatorRoleBean(creatorRoleBean);
				productInventoryBean.setProductBean(productBean);

				productInventoryModel=ProductInventoryModelPreparation.prepareProductInventoryModel(productInventoryBean);

				productService.addProduct(productInventoryModel);
				model.put("msg", "Product Successfully Saved");

			}else{

				productInventoryModel.setId(productInventoryBean.getId());
				productInventoryModel.setQuantity(productInventoryBean.getQuantity());
				int i=productService.upadteProductInventoryQuantity(productInventoryModel);
				if(i>0){
					model.put("msg", "Product Quantity Successfully Updated");
				}else{
					model.put("msg", "Some Problem: Upadate failed");
				}

			}
			session.setAttribute("msg", model.get("msg"));
			session.setAttribute("error", result);
			return new ModelAndView("redirect:/productEntryForm.html?task_type=sub&task_form_type=medical");



		}else{
			return new ModelAndView("error");
		}
		}else{
			session.setAttribute("error", result);
			return new ModelAndView("redirect:/userValidate.html");
		}
		

	} 
	@RequestMapping(value = "/getProduct",
			method = RequestMethod.POST)
	public @ResponseBody ProductModel getProduct(HttpSession session,
			@RequestParam(value="pid") String productid,
			@RequestParam(value="flag") String flag)
					throws Exception {
		ProductModel productModel=new ProductModel();
		productModel.setId(Integer.parseInt(productid));
		productModel=productService.getProduct(productModel);


		return productModel;
	}

	@RequestMapping(value = "/getBatches",
			method = RequestMethod.POST)
	public @ResponseBody List<ProductInventoryBean> getProductBatches(HttpSession session,
			@RequestParam(value="pid") String productid,
			@RequestParam("bId") String shopid,
			@RequestParam(value="Qty") String quantity)
					throws Exception {
		productModel=new ProductModel();
		productInventoryModel=new ProductInventoryModel();
		branchModel=new BranchModel();
		productInventoryBeans=new ArrayList<ProductInventoryBean>();
		productInventoryModels=new ArrayList<ProductInventoryModel>();

		productModel.setId(Integer.parseInt(productid));
		branchModel.setId(Integer.parseInt(shopid));

		productInventoryModel.setQuantity(Integer.parseInt(quantity));
		productInventoryModel.setBranchModel(branchModel);
		productInventoryModel.setProductModel(productModel);

		productInventoryModels=productService.getProductBatches(productInventoryModel);
		productInventoryBeans=ProductInventoryBeanPreparation.prepareListofProductInventoryBean(productInventoryModels);


		return productInventoryBeans;
	}

	@RequestMapping(value = "/loadProductInventory", method = RequestMethod.POST)
	public @ResponseBody ProductInventoryBean getProductByBatchNo(
			@RequestParam(value="bNo") String InvId){
		productInventoryBean=new ProductInventoryBean();
		productInventoryModel=new ProductInventoryModel();
		productInventoryModel.setId(Integer.parseInt(InvId));
		productInventoryModel=productService.loadProductInventoryModel(productInventoryModel);
		productInventoryBean=ProductInventoryBeanPreparation.prepareProductInventoryBean(productInventoryModel);
		return productInventoryBean;
	}
	@RequestMapping(value = "/validateBatchNo", method = RequestMethod.POST)
	public @ResponseBody ProductInventoryBean getProductByBatchNo(
			@RequestParam(value="batchNo") String batchNo,
			@RequestParam(value="bId") String branchId,
			@RequestParam(value="type") String type,
			@RequestParam(value="aId") String agencyId)
					throws Exception {
		initializeProperties();
		productInventoryModel=new ProductInventoryModel();
		productModel=new ProductModel();
		categoryModel = new CategoryModel();
		branchModel = new BranchModel();
		subCategoryBean = new SubCategoryBean();
		agencyModel=new AgencyModel();

		categoryModel.setId(Integer.parseInt(type));
		productModel.setCategoryModel(categoryModel);
		agencyModel.setId(Integer.parseInt(agencyId));
		productInventoryModel.setBatchNo(batchNo);
		productInventoryModel.setProductModel(productModel);
		productInventoryModel.setAgencyModel(agencyModel);
		branchModel.setId(Integer.parseInt(branchId));
		productInventoryModel.setBranchModel(branchModel);

		ProductInventoryModel productInventoryModel1=productService.getProductInventoryModelByBatchNo(productInventoryModel);


		agencyBean = new AgencyBean();
		categoryBean = new CategoryBean();
		productBean = new ProductBean();
		productInventoryBean = new ProductInventoryBean();

		if(productInventoryModel1!=null){

			categoryBean.setCategory(productInventoryModel1.getProductModel().getCategoryModel().getCategory());
			categoryBean.setId(productInventoryModel1.getProductModel().getCategoryModel().getId());

			subCategoryBean.setId(productInventoryModel1.getProductModel().getSubCategoryModel().getId());
			subCategoryBean.setSubCategory(productInventoryModel1.getProductModel().getSubCategoryModel().getCategory());

			agencyBean.setAgencyName(productInventoryModel1.getAgencyModel().getAgencyName());
			agencyBean.setId(productInventoryModel1.getAgencyModel().getId());

			productBean.setName(productInventoryModel1.getProductModel().getName());
			productBean.setSchDrug(productInventoryModel1.getProductModel().getSchDrug());
			productBean.setmFCompanay(productInventoryModel1.getProductModel().getmFCompanay());
			productBean.setId(productInventoryModel1.getProductModel().getId());

		   productInventoryBean.setMessage("Batch  existed in "+productInventoryModel1.getProductModel().getCategoryModel().getCategory()+" Products");

		}else{
			if(type.contentEquals(""+medical)){
				categoryModel.setId(general);
				productModel.setCategoryModel(categoryModel);
			}else if(type.contentEquals(""+general)){
				categoryModel.setId(medical);
				productModel.setCategoryModel(categoryModel);   
			}
			productInventoryModel.setProductModel(productModel);
			productInventoryModel1=productService.getProductInventoryModelByBatchNo(productInventoryModel);


			if(productInventoryModel1!=null){


				categoryBean.setCategory(productInventoryModel1.getProductModel().getCategoryModel().getCategory());
				categoryBean.setId(productInventoryModel1.getProductModel().getCategoryModel().getId());

				subCategoryBean.setId(productInventoryModel1.getProductModel().getSubCategoryModel().getId());
				subCategoryBean.setSubCategory(productInventoryModel1.getProductModel().getSubCategoryModel().getCategory());

				agencyBean.setAgencyName(productInventoryModel1.getAgencyModel().getAgencyName());
				agencyBean.setId(productInventoryModel1.getAgencyModel().getId());

				productBean.setName(productInventoryModel1.getProductModel().getName());
				productBean.setSchDrug(productInventoryModel1.getProductModel().getSchDrug());
				productBean.setmFCompanay(productInventoryModel1.getProductModel().getmFCompanay());
				productBean.setId(productInventoryModel1.getProductModel().getId());

				productBean.setCategoryBean(categoryBean);
				productInventoryBean.setProductBean(productBean);
				productInventoryBean.setMessage("Batch  existed in "+productInventoryModel1.getProductModel().getCategoryModel().getCategory()+" Products");
			}else{

				categoryBean.setId(Integer.parseInt(type));
				productBean.setCategoryBean(categoryBean);
				productInventoryBean.setProductBean(productBean);
				productInventoryBean.setMessage("Batch not existed in your inventory");
			}
		}

		productBean.setCategoryBean(categoryBean);
		productBean.setSubCategoryBean(subCategoryBean);
		productInventoryBean.setAgencyBean(agencyBean);
		productInventoryBean.setProductBean(productBean);
		if(productInventoryModel1!=null){
			productInventoryBean.setId(productInventoryModel1.getId());
			productInventoryBean.setPrice(productInventoryModel1.getPrice());
			productInventoryBean.setDlPrice(productInventoryModel1.getDlPrice());
			productInventoryBean.setBatchNo(productInventoryModel1.getBatchNo());
			productInventoryBean.setVat(productInventoryModel1.getVat());
			productInventoryBean.setPwVat(productInventoryModel1.getPwVat());
			productInventoryBean.setExpiryDate(productInventoryModel1.getExpiryDate()+"");
		}
		
		return productInventoryBean;
	}

	@RequestMapping(value ="/openBranchFrom", 
			method = RequestMethod.GET)
	public ModelAndView openBranch(HttpSession session,
			@RequestParam(value="uid") String uid,
			@ModelAttribute("command") 
	BranchRegistrationFormBean branchRegistrationFormBean, 
	BindingResult result)
	{

		int mode[]=SessionUtilities.validateSession(uid, session);
		if(mode[0]==1){

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("createdby",Integer.parseInt(uid));
			model.put("shopname",mode[2]);
			model.put("initials",FormUtilities.getInitial());
			return new ModelAndView("openBranchRegisterForm",model);
		}else{
			return new ModelAndView("error");
		}



	}

	@RequestMapping(value = "/masterProductsList", method = RequestMethod.GET)
	public ModelAndView medicalProductsList(HttpSession session, HttpServletRequest request,
			@ModelAttribute("command") ProductBean productBean,	
			BindingResult result){
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){		
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			model = new HashMap<String, Object>();
			productModel = new ProductModel();
			count = productService.countAll(productModel);
			model.put("createdRole", mode[1]);
			model.put("productCreatedBy", mode[2]);

			model.put("products", ProductBeanPreparation.prepareListofProductBean(productService.getByPage(pg, pageSize, productModel)));

			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));
            
			return new ModelAndView("masterProductsList",model);
		}else{
			return new ModelAndView("error");
		}		



	}

	@RequestMapping(value = "/productStockList", method = RequestMethod.GET)
	public ModelAndView productStockList(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") ProductInventoryBean productInventoryBean,	
			BindingResult result)
		{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){		
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			
			model = new HashMap<String, Object>();
			productInventoryModel = new ProductInventoryModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			productInventoryModels = new ArrayList<ProductInventoryModel>();
			try {			

				if(mode[1]== outlet){			     
					branchModel.setId(userSessionBean.getBranchBean().getId());
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel); 
					count = productService.countAll(productInventoryModel);
				//	productInventoryModels =productService.listProductsInventory(productInventoryModel);
					productInventoryModels =productService.getByPage(pg, pageSize, productInventoryModel);

				//	model.put("createdRole", mode[1]);
				//	model.put("productCreatedBy", mode[2]);
				//	model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
		        //    model.put("subtractor", (pg*pageSize - pageSize));
				}else if(mode[1]== branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel);
					count = productService.countAll(productInventoryModel);
				//	productInventoryModels =productService.listProductsInventory(productInventoryModel);
					productInventoryModels =productService.getByPage(pg, pageSize, productInventoryModel);

				//	model.put("createdRole", mode[1]);
				//	model.put("productCreatedBy", mode[2]);
				//	model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
		       //     model.put("subtractor", (pg*pageSize - pageSize));
				}else{
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel); 
					count = productService.countAll(productInventoryModel);
				//	productInventoryModels =productService.listProductsInventory(productInventoryModel);
					productInventoryModels =productService.getByPage(pg, pageSize, productInventoryModel);


				//	model.put("createdRole", mode[1]);
				//	model.put("productCreatedBy", mode[2]);
				//	model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
		        //  model.put("subtractor", (pg*pageSize - pageSize));
				}
				model.put("products",ProductInventoryBeanPreparation1.prepareListofProductInventoryBean1(productInventoryModels));
				model.put("createdby",userSessionBean.getId());
			} catch (Exception e) {

				e.printStackTrace();
			}
			model.put("createdRole", mode[1]);
			model.put("productCreatedBy", mode[2]);
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));
			model.put("outlet", outlet);
			return new ModelAndView("productsStockList",model);
			
		}else{
			return new ModelAndView("error");
		}		



	}

	@RequestMapping(value = "/getProductInventory",
			method = RequestMethod.POST)
	public @ResponseBody ProductInventoryBean getProductInventory(HttpSession session,
			@ModelAttribute("command")
	ProductInventoryBean productInventoryBean,BindingResult result,
	@RequestParam(value="id") String productInventoryid)
			throws Exception {
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){		
			productInventoryModel = new ProductInventoryModel();
			productInventoryModel.setId(Integer.parseInt(productInventoryid));

			productInventoryModel=productService.getProductInventory(productInventoryModel);

		}
		return ProductInventoryBeanPreparation.prepareProductInventoryBean(productInventoryModel);
	}

	@RequestMapping(value = "/updateProductInventoryByQuantity", method = RequestMethod.POST)
	public @ResponseBody int updateProductInventoryByQuantity(HttpSession session,
			@RequestParam(value="id") String id,
			@RequestParam(value="bid") String bid,
			@RequestParam(value="qty") String qty){
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		Integer i = 0;
		if(mode[0]==1){		
			model = new HashMap<String, Object>();
			productInventoryModel = new ProductInventoryModel();	
			creatorRoleModel = new RoleModel();
			branchModel = new BranchModel();
			productInventoryModel.setId(Integer.parseInt(id));
			productInventoryModel.setQuantity(Integer.parseInt(qty.trim()));
			creatorRoleModel.setId(mode[1]);			
			productInventoryModel.setCreatorRoleModel(creatorRoleModel);
			productInventoryModel.setCreatedBy(mode[2]);
			branchModel.setId(Integer.parseInt(bid));
			productInventoryModel.setBranchModel(branchModel);
			i=productService.updatePIQuantity(productInventoryModel);
			
		}
		return i;		


	}

	List<ProductLogBean> productLogBeans=null;

	@RequestMapping(value = "/productLogList", method = RequestMethod.GET)
	public ModelAndView productLogList(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") ProductLogBean productLogBean, 
			BindingResult result){
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){ 
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			model = new HashMap<String, Object>();
			productLogModel = new ProductLogModel();   
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();

			productLogBeans=new ArrayList<ProductLogBean>();
			try {   
				if(mode[1] == branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productLogModel.setBranchModel(branchModel); 
					count = productService.countAll(productLogModel);
				//	model.put("createdRole", mode[1]);
				//	model.put("productCreatedBy", mode[2]);
				//	productLogBeans=ProductLogBeanPreparation.prepareListofProductLogBean(productService.listProductsLog(productLogModel));
					productLogBeans=ProductLogBeanPreparation.prepareListofProductLogBean(productService.getByPage(pg, pageSize, productLogModel));


				}else if(mode[1] == organization){
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productLogModel.setBranchModel(branchModel); 
					count = productService.countAll(productLogModel);
				//	model.put("createdRole", mode[1]);
				//	model.put("productCreatedBy", mode[2]);
					productLogBeans=ProductLogBeanPreparation.prepareListofProductLogBean(productService.getByPage(pg, pageSize, productLogModel));
				//	productLogBeans=ProductLogBeanPreparation.prepareListofProductLogBean(productService.listProductsLog(productLogModel));
				}

				for(ProductLogBean productLogBean2:productLogBeans){
					if(productLogBean2.getCreatorRoleBean().getId() == organization){
						organizationModel.setId(productLogBean2.getUpdater());
						organizationModel = organizationService.getOrganizationbyId(organizationModel);
						productLogBean2.setUpdaterName(organizationModel.getName());
					}else if(productLogBean2.getCreatorRoleBean().getId() == branch){
						branchModel.setId(productLogBean2.getUpdater());
						branchModel = branchService.getBranchbyId(branchModel);
						productLogBean2.setUpdaterName(branchModel.getName());

					}

				}
				model.put("createdRole", mode[1]);
				model.put("productCreatedBy", mode[2]);
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
				model.put("outlet", outlet);
				model.put("productLogs", productLogBeans);    

			} catch (Exception e) {

				e.printStackTrace();
			}

			return new ModelAndView("ProductsLogList",model);
		}else{
			return new ModelAndView("error");
		}  



	}

	@RequestMapping(value = "/suppliersList", method = RequestMethod.GET)
	public ModelAndView suppliersList(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") ProductInventoryBean productInventoryBean,	
			BindingResult result){
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){		
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			model = new HashMap<String, Object>();
			productInventoryModel = new ProductInventoryModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			productInventoryBeans=new ArrayList<ProductInventoryBean>();
			try {	
				if(mode[1]==outlet){
					branchModel.setId(userSessionBean.getBranchBean().getId());
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel);
					count = productService.countAll(productInventoryModel);					
					productInventoryBeans=ProductInventoryBeanPreparation.prepareListofProductInventoryBean(productService.getByPage(pg, pageSize, productInventoryModel));
					model.put("suppliers",SupplierBeanPreparation.prepareListOfSupplierBeansformProductInventoryBeans(productInventoryBeans));
				//	model.put("createdRole", mode[1]);
				//	model.put("productCreatedBy", mode[2]);	

				}else if(mode[1]==branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel);
					count = productService.countAll(productInventoryModel);					
					//productInventoryBeans=ProductInventoryBeanPreparation.prepareListofProductInventoryBean(productService.listProductsInventory(productInventoryModel));
					productInventoryBeans=ProductInventoryBeanPreparation.prepareListofProductInventoryBean(productService.getByPage(pg, pageSize, productInventoryModel));
					model.put("suppliers",SupplierBeanPreparation.prepareListOfSupplierBeansformProductInventoryBeans(productInventoryBeans));
				//	model.put("createdRole", mode[1]);
				//	model.put("productCreatedBy", mode[2]);
				}else if(mode[1]==organization){
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel); 
					count = productService.countAll(productInventoryModel);					
					productInventoryBeans= ProductInventoryBeanPreparation.prepareListofProductInventoryBean(productService.getByPage(pg, pageSize, productInventoryModel));
					model.put("suppliers",SupplierBeanPreparation.prepareListOfSupplierBeansformProductInventoryBeans(productInventoryBeans));
				//	model.put("createdRole", mode[1]);
				//	model.put("productCreatedBy", mode[2]);
				}					


			} catch (Exception e) {

				e.printStackTrace();
			}	
			model.put("createdRole", mode[1]);
			model.put("productCreatedBy", mode[2]);	
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));
			return new ModelAndView("suppliersList",model);
		}else{
			return new ModelAndView("error");
		}		



	}

	List<ProductInventoryBean> productInventoryBeans=null;
	@RequestMapping(value = "/expiredProducts", method = RequestMethod.GET)
	public ModelAndView expiredProducts(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") ProductInventoryBean productInventoryBean,BindingResult result
		//	@RequestParam(value="task_list_type") String task_list_type
			) throws ParseException{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){		
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			model = new HashMap<String, Object>();
			productInventoryModel = new ProductInventoryModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();			
			productInventoryModel.setExpiryDate(DateAndTimeUtilities.getCurrentDateTimeInSqlFormat());
			productInventoryModels = new ArrayList<ProductInventoryModel>();
			try {		
				if(mode[1]== outlet){			     
					branchModel.setId(userSessionBean.getBranchBean().getId());	
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel); 
					count = productService.countAllOfExpiredProducts(productInventoryModel);			
			//		productInventoryModels = productService.getExpiredProducts(productInventoryModel);
					productInventoryModels = productService.getByPageOfExpiryProducts(pg, pageSize, productInventoryModel);
			//		model.put("type", task_list_type);
			//		model.put("createdRole", mode[1]);
			//		model.put("productCreatedBy", mode[2]);
				}else if(mode[1]== branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel); 
					count = productService.countAllOfExpiredProducts(productInventoryModel);		
					productInventoryModels = productService.getByPageOfExpiryProducts(pg, pageSize, productInventoryModel);

					
				}else{
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel); 
					count = productService.countAllOfExpiredProducts(productInventoryModel);	
					productInventoryModels = productService.getByPageOfExpiryProducts(pg, pageSize, productInventoryModel);
					
				}
				
				model.put("expiredProducts",ProductInventoryBeanPreparation1.prepareListofProductInventoryBean1(productInventoryModels));
			//	model.put("type", task_list_type);
				model.put("createdRole", mode[1]);
				model.put("productCreatedBy", mode[2]);
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
				
			} catch (Exception e) {

				e.printStackTrace();
			}	

		//	if(task_list_type.contentEquals("main") || task_list_type.contentEquals("main_pagination") ){
				return new ModelAndView("expiredProducts",model);
		//	}else{
		//		return new ModelAndView("expiredProductsNotification",model);
		//	}
		}else{
			return new ModelAndView("error");
		}  
	}

	List<ProductDamageBean> productDamageBeans=null;
	@RequestMapping(value = "/damagedProducts", method = RequestMethod.GET)
	public ModelAndView damagedProducts(HttpSession session, HttpServletRequest request,
			@ModelAttribute("command") ProductDamageBean productDamageBean,
			BindingResult result)throws ParseException{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){	
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			model = new HashMap<String, Object>();
			productDamageModel = new ProductDamageModel();
			productInventoryModel = new ProductInventoryModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			outletModel = new OutletModel();
			productDamageBeans = new ArrayList<ProductDamageBean>();			

			try {		
				if(mode[1]== outlet){			     
					branchModel.setId(userSessionBean.getBranchBean().getId());	
					branchModel.setOrganizationModel(organizationModel);
					productDamageModel.setBranchModel(branchModel); 
					count = productService.countAll(productDamageModel);		
				//	productDamageBeans = ProductDamageBeanPreparation.prepareListOfProductDamageBean(productService.listProductsDamage(productDamageModel));
					productDamageBeans = ProductDamageBeanPreparation.prepareListOfProductDamageBean(productService.getByPage(pg, pageSize, productDamageModel));
				}else if(mode[1]== branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);					
					productDamageModel.setBranchModel(branchModel); 
					count = productService.countAll(productDamageModel);	
					productDamageBeans = ProductDamageBeanPreparation.prepareListOfProductDamageBean(productService.getByPage(pg, pageSize, productDamageModel));

				}else{
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productDamageModel.setBranchModel(branchModel); 
					count = productService.countAll(productDamageModel);	
					productDamageBeans = ProductDamageBeanPreparation.prepareListOfProductDamageBean(productService.getByPage(pg, pageSize, productDamageModel));
				}
				for(ProductDamageBean productDamageBean1:productDamageBeans){
					if(productDamageBean1.getCreatorRoleBean().getId() == organization){
						organizationModel.setId(productDamageBean1.getCreator());
						organizationModel = organizationService.getOrganizationbyId(organizationModel);
						productDamageBean1.setCreatorName(organizationModel.getName());
					}else if(productDamageBean1.getCreatorRoleBean().getId() == branch){
						branchModel.setId(productDamageBean1.getCreator());
						branchModel = branchService.getBranchbyId(branchModel);
						productDamageBean1.setCreatorName(branchModel.getName());

					}else if(productDamageBean1.getCreatorRoleBean().getId() == outlet){
						outletModel.setId(productDamageBean1.getCreator());
						outletModel = outletService.getOutlet(outletModel);
						productDamageBean1.setCreatorName(outletModel.getFirstName()+" "+outletModel.getLastName());
					}

				}
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
				model.put("productDamages",productDamageBeans);

			} catch (Exception e) {

				e.printStackTrace();
			}	


			return new ModelAndView("damageProducts",model);

		}else{
			return new ModelAndView("error");
		}  
	}


	@RequestMapping(value = "/expiryProducts", method = RequestMethod.GET)
	public ModelAndView expiryProducts(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") ProductInventoryBean productInventoryBean,
			BindingResult result)throws ParseException{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){	
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			model = new HashMap<String, Object>();		
			productInventoryModel = new ProductInventoryModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			creatorRoleModel = new RoleModel();
			creatorRoleModel.setId(mode[1]);
			productInventoryModel.setCreatorRoleModel(creatorRoleModel);			
			productInventoryModels = new ArrayList<ProductInventoryModel>();
			try {		
				if(mode[1]== outlet){			     
					branchModel.setId(userSessionBean.getBranchBean().getId());	
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel); 
					count = productService.countAllOfGoingToExpiryProdcuts(productInventoryModel);	
				//	productInventoryModels =productService.listProductsGoingToExpire(productInventoryModel);
					productInventoryModels =productService.getByPageOfGoingToExpiryProdcuts(pg, pageSize, productInventoryModel);

				}else if(mode[1]== branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel); 
					count = productService.countAllOfGoingToExpiryProdcuts(productInventoryModel);	
					productInventoryModels =productService.getByPageOfGoingToExpiryProdcuts(pg, pageSize, productInventoryModel);



				}else{
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel); 	
					count = productService.countAllOfGoingToExpiryProdcuts(productInventoryModel);
					productInventoryModels =productService.getByPageOfGoingToExpiryProdcuts(pg, pageSize, productInventoryModel);


				}				

				model.put("productsOfNotification", ProductInventoryBeanPreparation1.prepareListofProductInventoryBeanromExpairyProductInventoryModel1(productInventoryModels));
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
			} catch (Exception e) {

				e.printStackTrace();
			}	

			
			return new ModelAndView("goingToExpireProducts",model);

		}else{
			return new ModelAndView("error");
		}  
	}
	@RequestMapping(value = "/lessQuantityProducts", method = RequestMethod.GET)
	public ModelAndView lessQuantityProducts(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") ProductInventoryBean productInventoryBean,
			BindingResult result)throws ParseException{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){	
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			model = new HashMap<String, Object>();		
			productInventoryModel = new ProductInventoryModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			productInventoryModels = new ArrayList<ProductInventoryModel>();
			try {
				if(mode[1]== outlet){			     
					branchModel.setId(userSessionBean.getBranchBean().getId());	
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel); 
					count = productService.countAllOfLessQuantityProdcuts(productInventoryModel);
				//	productInventoryModels =productService.lessQuantityProducts(productInventoryModel);
					productInventoryModels =productService.getByPageOfLessQuantityProdcuts(pg, pageSize, productInventoryModel);

				}else if(mode[1] == branch){			     
					branchModel.setId(mode[2]);	
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel);
					count = productService.countAllOfLessQuantityProdcuts(productInventoryModel);
					productInventoryModels =productService.getByPageOfLessQuantityProdcuts(pg, pageSize, productInventoryModel);

				}else{
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					productInventoryModel.setBranchModel(branchModel);
					count = productService.countAllOfLessQuantityProdcuts(productInventoryModel);
					productInventoryModels =productService.getByPageOfLessQuantityProdcuts(pg, pageSize, productInventoryModel);

				}				

				model.put("lessQuantityProducts", ProductInventoryBeanPreparation1.prepareListofProductInventoryBean1(productInventoryModels));
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
			} catch (Exception e) {

				e.printStackTrace();
			}	


			return new ModelAndView("lessQuantityProducts",model);

		}else{
			return new ModelAndView("error");
		}  
	}


	//**************product upload****************
	@RequestMapping(value="/productsUpload" , method= RequestMethod.GET)
	public ModelAndView uploadForm(HttpSession session,@ModelAttribute("command")FileBean uploadItem, BindingResult result){
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){

			model = new HashMap<String, Object>();
			agencyModel = new AgencyModel();
			resourceModel =  new ResourceModel();
			accountStatusModel = new StatusModel();
			accountStatusModel.setId(enable);
			resourceModel.setAccountStatusModel(accountStatusModel);
			agencyModel.setResourceModel(resourceModel);
			if(mode[1]==branch){

				model.put("data", uploadItem);
				model.put("agencyId", 0);
				model.put("agency", "--Select Agency--");
				model.put("agencyList", getAgencies(agencyModel));
				model.put("createdby", userSessionBean.getId());
				return new ModelAndView("productsUploadByBranch",model);
			}else if(mode[1]==outlet){
				model.put("data", uploadItem);
				model.put("agencyId", 0);
				model.put("agency", "--Select Agency--");
				model.put("agencyList", getAgencies(agencyModel));
				model.put("createdby", userSessionBean.getId());
				return new ModelAndView("productsUploadByOutlet",model);
			}else{
				model.put("data", uploadItem);
				model.put("agencyId", 0);
				model.put("agency", "--Select Agency--");
				model.put("agencyList", getAgencies(agencyModel));
				model.put("createdby", 	userSessionBean.getId());
				return new ModelAndView("productsUploadByOrg",model);
			}
		}
		else{
			return new ModelAndView("error");
		}

	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView upload(HttpSession session,@RequestParam(value="uid") String uid,@ModelAttribute("command")FileBean uploadItem, BindingResult result) {
		int mode[]=SessionUtilities.validateSession(uid, session);
		if(mode[0]==1){
			agencyModel=new AgencyModel();
			creatorRoleBean=new RoleBean();
			creatorRoleBean.setId(mode[1]);
			agencyModel.setId(uploadItem.getAgencyBean().getId());
			int createdby=Integer.parseInt(uid);
			int createrRole=creatorRoleBean.getId();
			FileDataModel fdm=new FileDataModel();
			fdm.setFileData(uploadItem.getFileData());
			fdm.setAgencyModel(agencyModel);
			ByteArrayInputStream bis = new ByteArrayInputStream(FileDataModel.getFileData().getBytes());
			HSSFWorkbook workbook;
			try {
				if (fdm.getFileData().getOriginalFilename().endsWith("xls")) {
					workbook = new HSSFWorkbook(bis);
				} else if (fdm.getFileData().getOriginalFilename().endsWith("xlsx")) {
					workbook = new HSSFWorkbook(bis);
				} else {
					throw new IllegalArgumentException("Received file does not have a standard excel extension.");
				}
				HSSFSheet sheet = workbook.getSheetAt(0);

				list=new ArrayList<ProductModel>();
				Iterator<Row> rowIterator = sheet.iterator();
				while(rowIterator.hasNext()) {
					Row row = rowIterator.next();
					productModel=new ProductModel();

					Iterator<Cell> cellIterator = row.cellIterator();
					while(cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						if(cell.getColumnIndex()==0)
						{
							productModel.setName(cell.toString());
						}else if(cell.getColumnIndex()==1)
						{
							productModel.setmFCompanay(cell.toString());
						}else if(cell.getColumnIndex()==2)
						{
							subCategoryModel=new SubCategoryModel();
							subCategoryModel.setCategory(cell.toString().toUpperCase());
							subCategoryModel.setId(properitesMap.getSubCategoryId(subCategoryModel.getCategory()));
							productModel.setSubCategoryModel(subCategoryModel);

						}else if(cell.getColumnIndex()==3)
						{
							productModel.setSchDrug(cell.toString());
						}
						else if(cell.getColumnIndex()==4)
						{
							categoryModel=new CategoryModel();
							categoryModel.setCategory(cell.toString());
							String catId=properitesMap.getCategoryId("category"+"."+""+categoryModel.getCategory())+"";
							categoryModel.setId(Integer.parseInt(catId));
							productModel.setCategoryModel(categoryModel);
						}

					}   list.add(productModel);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			agencyModel=new AgencyModel();
			agencyModel.setId(fdm.getAgencyModel().getId());
			productService.insertBulkProducts(list,agencyModel,createdby,createrRole);
			model=new HashMap<String, Object>();
			if(mode[1]==branch){
				model.put("message", "upload successfull");
				return new ModelAndView("productsUploadByBranch",model);
			}else if(mode[1]==outlet){
				model.put("message", "upload successfull");
				return new ModelAndView("productsUploadByOutlet",model);
			}else {
				model.put("message", "upload successfull");
				return new ModelAndView("productsUploadByOrg",model);
			}
		}
		else{
			return new ModelAndView("error");
		}
	}

	
	public LinkedHashMap<Integer, String> getAgencies(AgencyModel agencyModel){
		LinkedHashMap<Integer, String> agencies=new LinkedHashMap<Integer,String>();
		List<AgencyModel> list= productService.getAgencyList(agencyModel);
		for(int i=0; i<list.size(); i++){			
			int aid=list.get(i).getId();
			String aname=list.get(i).getAgencyName();			
			agencies.put(aid, aname);
		}
		return agencies;
	}

	@RequestMapping(value="/openDamagedForm" , method=RequestMethod.GET)
	public ModelAndView damageEntryForm(HttpSession session,
			@ModelAttribute("command") ProductDamageBean productDamageBean,BindingResult result,
			@RequestParam(value="flag") Integer flag){

		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			BranchModel branchModel = new BranchModel();
			OrganizationModel organizationModel = new OrganizationModel();
			organizationModel.setId(userSessionBean.getId());
			branchModel.setOrganizationModel(organizationModel);
			try {
				model = new HashMap<String, Object>();				

				if(mode[1]== outlet){
					model.put("branch", userSessionBean.getBranchBean().getName());
					model.put("branchId", userSessionBean.getBranchBean().getId());
					model.put("organization", userSessionBean.getOrganizationBean().getName());
					model.put("organizationId", userSessionBean.getOrganizationBean().getId());
				}else if(mode[1]== branch){
					model.put("branch", userSessionBean.getName());
					model.put("branchId", userSessionBean.getId());
					model.put("organization", userSessionBean.getOrganizationBean().getName());
					model.put("organizationId", userSessionBean.getOrganizationBean().getId());
				}else{
					model.put("branch", "--Select Branch--");
					model.put("branchId", 0);
					model.put("organization", userSessionBean.getName());
					model.put("organizationId", userSessionBean.getId());
					model.put("branches", getBranches(branchModel));  
				}

				model.put("createdby",userSessionBean.getId());
			} catch (Exception e) {

				e.printStackTrace();
			}

			if(flag==0){
			return new ModelAndView("openDamageProductForm", model);
			}else{				
				model.put("message", msg);
				return new ModelAndView("openDamagedForm", model);
			}
		}else{
			return new ModelAndView("error");

		}

	}


	@RequestMapping(value = "/saveDamageProducts",method = RequestMethod.POST)
	public ModelAndView addDamageProducts(HttpSession session,
			@ModelAttribute("command") ProductDamageBean productDamageBean,BindingResult result)
					throws Exception  {	

		damagedProductRegValidator.validate(productDamageBean, result);
		if(!result.hasErrors()){
		initializeProperties();

		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){	

			productDamageModel = new ProductDamageModel();	
			reasonModel = new ReasonModel();

			RoleModel roleModel=new RoleModel();
			roleModel.setId(mode[1]);
			roleModel=resourcesService.getRole(roleModel);

			productInventoryModel.setId(productDamageBean.getProductInventoryBean().getId());
			productInventoryModel = productService.loadProductInventoryModel(productInventoryModel);

			CategoryModel categoryModel=new CategoryModel();
			categoryModel.setId(product);
			categoryModel=resourcesService.getCategory(categoryModel);

			reasonModel.setReason(productDamageBean.getReasonBean().getReason());
			reasonModel.setCategoryModel(categoryModel);

			productInventoryModel.setQuantity(productInventoryModel.getQuantity()-productDamageBean.getQuantity());

			ProductDamageModel productDamageModel=new ProductDamageModel();
			productDamageModel.setQuantity(productDamageBean.getQuantity());
			productDamageModel.setCreator(mode[2]);
			productDamageModel.setCreatorRoleModel(roleModel);
			productDamageModel.setAgencyModel(productInventoryModel.getAgencyModel());
			productDamageModel.setProductInventoryModel(productInventoryModel);
			productDamageModel.setReasonModel(reasonModel);
			productDamageModel.setDamagedDate(productDamageBean.getDamagedDate());

			branchModel.setId(productDamageBean.getBranchBean().getId());
			productDamageModel.setBranchModel(branchModel);



			model = new HashMap<String, Object>();

			if(productDamageModel!=null){

				boolean returnResult = productService.saveDamegedProducts(productDamageModel);


				if(returnResult){				
					session.setAttribute("error", result);
					return new ModelAndView("redirect:/openDamagedForm.html?flag=1"); 
				}

			}	

		}else{
			return new ModelAndView("error");
			}}else{
				session.setAttribute("error", result);
				return new ModelAndView("redirect:/userValidate.html");
			}
			return null;

	}
}