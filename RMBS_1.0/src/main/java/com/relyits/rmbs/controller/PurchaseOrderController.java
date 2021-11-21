package com.relyits.rmbs.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.relyits.rmbs.beans.purchase.PurchaseLineItemsBean;
import com.relyits.rmbs.beans.purchase.PurchaseLineItemsBean1;
import com.relyits.rmbs.beans.purchase.PurchaseOrderBean;
import com.relyits.rmbs.beans.purchase.PurchaseOrderBean1;
import com.relyits.rmbs.beans.resources.PurchaseFormBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseLineItemsBeanPreparation1;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseOrderBeanPreparation1;
import com.relyits.rmbs.beans_preparation.resources.VatBeanPreparation;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.refference.SubCategoryModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.model_preparation.purchase.PurchaseLineItemsModelPreparation;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.PageNavigator;
import com.relyits.rmbs.service.ProductService;
import com.relyits.rmbs.service.PurchaseService;
import com.relyits.rmbs.service.ReportsService;
import com.relyits.rmbs.utilities.NumToWords;
import com.relyits.rmbs.utilities.SessionUtilities;
import com.relyits.rmbs.utilities.VatDetailsPreaperationService;

@Controller
public class PurchaseOrderController {
	@Autowired
	private BranchService branchService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
    private PageNavigator pageNavigator;

	@Autowired
	private ReportsService reportsService;
	
	
	private AgencyModel agencyModel=null;
	private BranchModel branchModel = null;
	private OrganizationModel organizationModel = null;
	private SubCategoryModel subCategoryModel = null;
	private CategoryModel categoryModel = null;
	private PurchaseOrderModel purchaseOrderModel = null;
	private PurchaseLineItemsModel purchaseLineItemsModel = null; 
	private ResourceModel resourceModel = null;
	private StatusModel accountStatusModel = null;
	
	List<PurchaseOrderModel> purchaseOrderModels = null;

	private UserSessionBean userSessionBean = null;

	Map<String, Object> model = null;
	
	Map<String, String> properties= null;
	
	private Long count = null;
	
  int medical,general,organization,branch,outlet,enable,active; 
	
	@Value("${category.medical}") String categorymedical;
	@Value("${category.general}") String categorygeneral;
	@Value("${role.organization}") String org;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${status.enable}") String enableStatus;
	@Value("${status.active}") String activeStatus;
	@Value("${app.page.size.default}") private int pageSize;
    @Value("${app.page.nav.trail}") private int pageNavTrail;


	public Map<String, String> initializeProperties(){

		medical=Integer.parseInt(categorymedical);
		general = Integer.parseInt(categorygeneral);
		organization = Integer.parseInt(org);
		branch = Integer.parseInt(branchRole);
		outlet = Integer.parseInt(outletRole);
		enable = Integer.parseInt(enableStatus);
		active = Integer.parseInt(activeStatus);
		return properties;
	}

	@RequestMapping(value="/openPurchaseEntryForm" , method=RequestMethod.GET)
	public ModelAndView purchaseEntryForm(@ModelAttribute("command")PurchaseLineItemsBean purchaseLineItemsBean,BindingResult result,
			@RequestParam("task_form_type") String task_form_type,
			@RequestParam("task_type") String task_type,HttpSession session){
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			branchModel = new BranchModel();
			accountStatusModel = new StatusModel();
			accountStatusModel.setId(active);
			resourceModel = new ResourceModel();
			resourceModel.setAccountStatusModel(accountStatusModel);
			branchModel.setResourceModel(resourceModel);
			organizationModel = new OrganizationModel();
			organizationModel.setId(userSessionBean.getId());
			branchModel.setOrganizationModel(organizationModel);
			subCategoryModel = new SubCategoryModel();
			categoryModel = new CategoryModel();
			categoryModel.setId(medical);
			subCategoryModel.setCategoryModel(categoryModel);
			
			try {
				model = new HashMap<String, Object>();				
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
			if(task_type.contentEquals("main") && task_form_type.contentEquals("medical")){

				if(mode[1]== organization){
					return new ModelAndView("openMedicalProductPurchaseFormByOrganization", model);
				}else if(mode[1]== branch){
					return new ModelAndView("openMedicalProductPurchaseFormByBranch", model);
				}else if(mode[1]== outlet){
					return new ModelAndView("openMedicalProductPurchaseFormByOutlet", model);
				}
			}else if(task_type.contentEquals("main") && task_form_type.contentEquals("general")){
				if(mode[1]== organization){
					return new ModelAndView("openGeneralProductPurchaseFormByOrganization", model);
				}else if(mode[1]== branch){
					return new ModelAndView("openGeneralProductPurchaseFormByBranch", model);
				}else if(mode[1]== outlet){
					return new ModelAndView("openGeneralProductPurchaseFormByOutlet", model);
				}
				
			}else if(task_type.contentEquals("sub") && task_form_type.contentEquals("medical")){
				return new ModelAndView("reOpenMedicalProductPurchaseForm", model);
			}else if(task_type.contentEquals("sub") && task_form_type.contentEquals("general")){
				return new ModelAndView("reOpenMedicalProductPurchaseForm", model);
			}
		}else{
			return new ModelAndView("error");
		}
		return null;

	}
	public LinkedHashMap<Integer, String> getBranches(BranchModel branchModel){
		LinkedHashMap<Integer, String> branches = new LinkedHashMap<Integer, String>();
		List<BranchModel> branchModels= branchService.getBranchesByOrganization(branchModel);
		for(int i=0; i<branchModels.size(); i++){
			int bid=branchModels.get(i).getId();
			String bname=branchModels.get(i).getName();
			branches.put(bid, bname);
		}
		System.out.println(branches.keySet().toString());
		return branches;
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
	@RequestMapping(value = "/get_purchase_medical_agency_list", 
			method = RequestMethod.GET, 
			headers="Accept=*/*")
	public @ResponseBody List<String> getAgenciesLists(@RequestParam("term") String phrase){

		agencyModel=new AgencyModel();
		resourceModel = new ResourceModel();
		accountStatusModel = new StatusModel();
		agencyModel.setAgencyName(phrase);
		accountStatusModel.setId(enable);
		resourceModel.setAccountStatusModel(accountStatusModel);
		agencyModel.setResourceModel(resourceModel);
		List<String> agenciesList=productService.getAgenciesList(agencyModel);
		List<String> agencies=new ArrayList<String>();
		for (int i = 0; i < agenciesList.size(); i++) {
			Object o=(Object)agenciesList.get(i);
			agencyModel=(AgencyModel)o;
			agencies.add(agencyModel.getAgencyName()+"_"+agencyModel.getId());
		}
		return agencies;
	}


	PurchaseFormBean purchaseFormBean = null;
	List<PurchaseLineItemsModel> purchaseLineItemsModels=null;
	List<PurchaseFormBean> purchaseFormBeans=null;
	List<PurchaseLineItemsBean1> purchaseLineItemsBeans1=null;
	@RequestMapping(value = "/createOrder", 
			method = RequestMethod.POST, 
			headers="Accept=*/*")
	public ModelAndView createOrder(@RequestBody String str,HttpSession session) throws JsonProcessingException, ParseException{
		int POID=0;
	
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			ObjectMapper mapper = new ObjectMapper();
			purchaseFormBeans=new ArrayList<>();
			try {
				JsonNode node = mapper.readTree(str);
				for(int i=0;i<node.size();i++){
					purchaseFormBean=new PurchaseFormBean();
					purchaseFormBean = mapper.convertValue(node.get(i), PurchaseFormBean.class);
					purchaseFormBean.setcRId(mode[1]);
					purchaseFormBean.setcById(mode[2]);
					purchaseFormBeans.add(purchaseFormBean);
					
				}
				
				if(purchaseFormBeans.size()>0){
					purchaseLineItemsModels=new ArrayList<PurchaseLineItemsModel>();
					purchaseLineItemsModels=PurchaseLineItemsModelPreparation.preparepurchaseLineItemsModelsListFromPurchaseFormBeansList(purchaseFormBeans);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			POID=purchaseService.createPurchaseOrder(purchaseLineItemsModels);
			purchaseOrderModel=new PurchaseOrderModel();
			purchaseOrderModel.setId(POID);
			purchaseLineItemsModel=new PurchaseLineItemsModel();
			purchaseLineItemsModel.setPurchaseOrderModel(purchaseOrderModel);
		}
		purchaseLineItemsBeans1=PurchaseLineItemsBeanPreparation1.prepareListOfPurchaseLineItemsBean1(purchaseService.getPurchaseLineItems(purchaseLineItemsModel));
		if(purchaseLineItemsBeans1!=null){
		
			int count1=0;
			PurchaseOrderBean1 purchaseOrderBean1=null;
			for(PurchaseLineItemsBean1 purchaseLineItemsBean1:purchaseLineItemsBeans1){
				if(count1>0)break;
				purchaseOrderBean1=purchaseLineItemsBean1.getPurchaseOrderBean1();
				count1++;
			}
		
	
			model = new HashMap<String, Object>();
			model.put("POLItems", purchaseLineItemsBeans1);
			model.put("PODetails", purchaseOrderBean1);
			model.put("POAmntInWords",NumToWords.convertToWord(Math.round(Double.parseDouble(purchaseOrderBean1.getPayAmount()+""))));
			model.put("vatDetails", VatDetailsPreaperationService.prepareVatMap(VatBeanPreparation.prepareListOfVatBeanOfPurchaseLineItems(purchaseLineItemsBeans1)));		
		
		}
		return new ModelAndView("pOInvoiceView", model);
	}


	@RequestMapping(value = "/purchaseOrderList", method = RequestMethod.GET)
	public ModelAndView purchaseOrderList(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") PurchaseOrderBean purchaseOrderBean ,BindingResult result,
			@RequestParam(value="flag") String flag
			){
		initializeProperties();
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		
		if(mode[0]==1){  
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			model = new HashMap<String, Object>();
			purchaseOrderModel = new PurchaseOrderModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			purchaseOrderModels = new ArrayList<PurchaseOrderModel>();
			try {   

		
				if(mode[1]== branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					purchaseOrderModel.setBranchModel(branchModel); 
					count = purchaseService.countAll(purchaseOrderModel);
				//	purchaseOrderModels = purchaseService.getPurchaseOrderList(purchaseOrderModel);
					purchaseOrderModels = purchaseService.getByPage(pg, pageSize, purchaseOrderModel);

					
				}else if(mode[1]== organization){
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					purchaseOrderModel.setBranchModel(branchModel); 
					count = purchaseService.countAll(purchaseOrderModel);
					purchaseOrderModels = purchaseService.getByPage(pg, pageSize, purchaseOrderModel);
					
								
				}
				model.put("createdRole", mode[1]);
				model.put("purchaseCreatedBy", mode[2]);
				model.put("purchaseOrders",PurchaseOrderBeanPreparation1.prepareListOfPurchaseOrderBean1(purchaseOrderModels));
				model.put("branch1", branch);
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
			} catch (Exception e) {

				e.printStackTrace();
			}
			if(flag.contentEquals("task_main")){
				return new ModelAndView("purchaseOrderList",model);
			}else{
				return new ModelAndView("reOpenPurchaseOrderList",model);
			}
		}else{
			return new ModelAndView("error");
		}  



	}
	@RequestMapping(value = "/validateInvoice",
			method = RequestMethod.POST)
	public @ResponseBody String validateInvoice(HttpSession session,
			@RequestParam(value="invoice") String invoiceNo)
					throws Exception{
		initializeProperties();
		purchaseOrderModel = new PurchaseOrderModel();	
		purchaseOrderModel.setInvoiceNo(invoiceNo);
		String invoice="invoice";


		PurchaseOrderModel purchaseOrderModel1=purchaseService.validateInvoice(purchaseOrderModel);

		if(purchaseOrderModel1!=null){
			invoice=purchaseOrderModel1.getInvoiceNo();
		}else{

		}

		return invoice;

	}


	
	List<PurchaseLineItemsBean> purchaseLineItemsBeans=null;
	PurchaseOrderBean purchaseOrderBean=null;
	
	@RequestMapping(value = "/loadInvoiceByPurchase",
			method = RequestMethod.POST)
	public ModelAndView  getInvoiceByPurchaseOrder(HttpSession session,@ModelAttribute("command")                     
	PurchaseLineItemsBean purchaseLineItemsBean,BindingResult result,
	@RequestParam(value="id") String pOId)
			throws Exception {
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
	
		if(mode[0]==1){
			purchaseLineItemsModel = new PurchaseLineItemsModel();
			purchaseOrderModel = new PurchaseOrderModel();
			purchaseOrderModel.setId(Integer.parseInt(pOId));
			purchaseLineItemsModels = purchaseService.getPurchaseLineItemsModelsByPurcheseOrderModel(purchaseOrderModel);
			List<PurchaseLineItemsBean1> purchaseLineItemsBean1List=PurchaseLineItemsBeanPreparation1.prepareListOfPurchaseLineItemsBean1(purchaseLineItemsModels);
			if(purchaseLineItemsBean1List!=null){
			
				int count2=0;
				PurchaseOrderBean1 purchaseOrderBean1=null;
				for(PurchaseLineItemsBean1 purchaseLineItemsBean1:purchaseLineItemsBean1List){
					if(count2>0)break;
					purchaseOrderBean1=purchaseLineItemsBean1.getPurchaseOrderBean1();
					count2++;
				}
				model = new HashMap<String, Object>();	
				
				model.put("POAmntInWords",NumToWords.convertToWord(Math.round(Double.parseDouble(purchaseOrderBean1.getPayAmount().toString()))));
				model.put("POLItems", purchaseLineItemsBean1List);
				model.put("PODetails", purchaseOrderBean1);
				model.put("vatDetails", VatDetailsPreaperationService.prepareVatMap(VatBeanPreparation.prepareListOfVatBeanOfPurchaseLineItems(purchaseLineItemsBean1List)));
			
			
		    }
		}
		return new ModelAndView("pOInvoiceView",model);
	}
	
	
	
	
	@RequestMapping(value = "/getPurchaseLineItems",
			method = RequestMethod.GET)
	public ModelAndView  getpurchaseLineItems(HttpSession session,HttpServletRequest request,
							@ModelAttribute("command") PurchaseLineItemsBean purchaseLineItemsBean, 
							   BindingResult result,@RequestParam(value="id") String pOId, 
							   @RequestParam(value="flag") int flag,
							   @RequestParam(value="task") String task)throws Exception {
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){  
			model = new HashMap<String, Object>();
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			purchaseLineItemsModel = new PurchaseLineItemsModel();
			purchaseOrderModel = new PurchaseOrderModel();
			purchaseOrderModel.setId(Integer.parseInt(pOId));
			purchaseLineItemsModel.setPurchaseOrderModel(purchaseOrderModel);
			if(flag==0){
				count = purchaseService.countAll(purchaseLineItemsModel);
				model.put("purchaseLineItems", PurchaseLineItemsBeanPreparation1.prepareListOfPurchaseLineItemsBean1(purchaseService.getByPage(pg, pageSize, purchaseLineItemsModel)));
			}
			else{
				model.put("purchaseLineItems", PurchaseLineItemsBeanPreparation1.prepareListOfPurchaseLineItemsBean1(purchaseService.getPurchaseLineItems(purchaseLineItemsModel)));
			}
			
			model.put("flag", flag);
			model.put("purchaseOrderId", purchaseLineItemsModel.getPurchaseOrderModel().getId());
			
			
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));
            
            if(task.contentEquals("page")){
            	return new ModelAndView("purchaseLineItemsListOfBaseDefination",model);
            }else{
			return new ModelAndView("purchaseLineItemsList",model);
            }
		}else{
			return new ModelAndView("error");
		}  
	}

}
