package com.relyits.rmbs.controller;



import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.relyits.rmbs.beans.product.ProductReportBean;
import com.relyits.rmbs.beans.purchase.PurchaseReportBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseOrderBeanPreparation1;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseReturnOrderBeanPreparation1;
import com.relyits.rmbs.beans_preparation.sales.SalesLineItemsBeanPreparation1;
import com.relyits.rmbs.beans_preparation.sales.SalesOrderBeanPreparation1;
import com.relyits.rmbs.beans_preparation.sales.SalesReturnOrderBeanPreparation1;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.product.ProductModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnLineItemsModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.ProductService;
import com.relyits.rmbs.service.ReportsService;
import com.relyits.rmbs.service.SalesReturnsService;
import com.relyits.rmbs.service.SalesService;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;
import com.relyits.rmbs.utilities.FormUtilities;
import com.relyits.rmbs.utilities.MasterProductsDownloadService;
import com.relyits.rmbs.utilities.ProductStockDownloadService;
import com.relyits.rmbs.utilities.PurchaeOrderLineitemsReportsDownloadService;
import com.relyits.rmbs.utilities.PurchaseOrderDownloadService;
import com.relyits.rmbs.utilities.PurchaseOrderListDownloadService;
import com.relyits.rmbs.utilities.PurchaseRetrunOrderDwnService;
import com.relyits.rmbs.utilities.PurchaseReturnOrderListDwdService;
import com.relyits.rmbs.utilities.PurchaseRtnOrderLineitemsDwnService;
import com.relyits.rmbs.utilities.SalesAndPreSalesOrderListDwnService;
import com.relyits.rmbs.utilities.SalesOrderByProductDwnService;
import com.relyits.rmbs.utilities.SalesOrderListDownloadService;
import com.relyits.rmbs.utilities.SalesReturnOrderListDownloadService;
import com.relyits.rmbs.utilities.SessionUtilities;
import com.relyits.rmbs.utilities.StatusResponse;
import com.relyits.rmbs.utilities.TokenService;

@Controller
public class ReportsController {

	//	************purchase order reports*************
	@Autowired
	private PurchaseOrderController purchaseController;

	@Autowired
	private ReportsService reportsService;

	@Autowired
	private BranchService branchService; 

	@Autowired
	private TokenService tokenService;
	@Autowired
	private MasterProductsDownloadService downloadService;

	@Autowired
	private ProductStockDownloadService productStockDownloadService;

	@Autowired
	private PurchaseOrderListDownloadService purchaseOrderListDownloadService;

	@Autowired
	private PurchaseOrderDownloadService purchaseOrderDownloadService;

	@Autowired
	private PurchaeOrderLineitemsReportsDownloadService pLineitemsReportsDownloadService;

	@Autowired
	private SalesService salesService;

	@Autowired
	private SalesOrderListDownloadService salesOrderListDownloadService;

	@Autowired
	private SalesReturnOrderListDownloadService salesReturnOrderListDownloadService;

	@Autowired
	private PurchaseReturnOrderListDwdService purchaseReturnOrderListDwdService;

	@Autowired
	private PurchaseRtnOrderLineitemsDwnService purchaseRtnOrderLineitemsDwnService;

	@Autowired
	private PurchaseRetrunOrderDwnService purchaseRetrunOrderDwnService;
	
	@Autowired
	private SalesAndPreSalesOrderListDwnService salesAndPreSalesOrderListDwnService;

	@Autowired
	private SalesReturnsService salesReturnsService;
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SalesOrderByProductDwnService salesOrderByProductDwnService;
	
	private BranchModel branchModel;
	private OrganizationModel organizationModel = null;
	private PurchaseOrderModel purchaseOrderModel = null;
	private OutletModel outletModel = null;
	private RoleModel creatorRoleModel = null;
	private ResourceModel resourceModel = null;
	private ProductModel productModel;

	private SalesLineItemsModel salesLineItemsModel = null;

	private UserSessionBean userSessionBean = null;
	private Map<String, Object> model=null;
	private int organization,branch,outlet,sales,preSales,active;
	private Map<String, String> properties= null;
	private Date fromDate = null;
	private Date toDate = null;
	private String purchase = null;
	private String purchaseReturns = null;
	private String salesOrder = null;
	private String salesReturn = null;
	private String preSaleOrd = null;
	@Value("${category.medical}") String categorymedical;
	@Value("${category.general}") String categorygeneral;
	@Value("${role.organization}") String org;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${category.sales}") String categorysales;
	@Value("${category.pre_sales}") String categorypresales;
	@Value("${orderType.purchase}") String purchaseOrder;
	@Value("${orderType.purchaseReturns}") String purchaseReturnOrder;
	@Value("${status.active}") String activeStatus;
	@Value("${orderType.sales}") String salesOrd;
	@Value("${orderType.salesReturns}") String salesReturnOrd;
	@Value("${orderType.preSales}") String preSalesOrd;
	private ProductInventoryModel productInventoryModel = null;

	private SalesOrderModel salesOrderModel = null;

	private CategoryModel categoryModel = null;


	private SalesReturnOrderModel salesReturnOrderModel = null;

	private PurchaseReturnOrderModel purchaseReturnOrderModel = null;

	private PurchaseReturnLineItemsModel purchaseReturnLineItemsModel;

	private StatusModel accountStatusModel = null;

	private List<SalesOrderModel> salesOrderModels = null;

	private List<SalesReturnOrderModel> salesReturnOrderModels = null;

	private ArrayList<SalesLineItemsModel> itemsModels = null;

	private SalesReturnLineItemsModel salesReturnLineItemsModel = null;

	
	
	

	public Map<String, String> initializeProperties(){

		organization = Integer.parseInt(org);
		branch = Integer.parseInt(branchRole);
		outlet = Integer.parseInt(outletRole);
		sales = Integer.parseInt(categorysales);
		preSales = Integer.parseInt(categorypresales);
		purchase = purchaseOrder.trim();
		purchaseReturns = purchaseReturnOrder.trim();
		salesOrder = salesOrd.trim();
		salesReturn = salesReturnOrd.trim();
		preSaleOrd = preSalesOrd.trim();
		active = Integer.parseInt(activeStatus);
		return properties;
	}

	//**************jasper reports for master products******************

	@RequestMapping(value="/download/progress")
	public @ResponseBody StatusResponse checkDownloadProgress(@RequestParam String token) {
		return new StatusResponse(true, tokenService.check(token));
	}

	@RequestMapping(value="/download/token")
	public @ResponseBody StatusResponse getDownloadToken() {
		return new StatusResponse(true, tokenService.generate());
	}

	@RequestMapping(value="productMasterReports/download")
	public void download(HttpSession  session,@RequestParam String type,
			@RequestParam String token, 
			HttpServletResponse response) {
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		if(mode[0]==1){
		downloadService.download(user,type, token, response);
		}
	}

	//		**********Jasper reports for product stock ***********

	@RequestMapping(value="/productStockReports/download")
	public void productStockDownloadReportsByBranch(HttpSession session,@RequestParam String type,
			@RequestParam String token,
			HttpServletResponse response){
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		initializeProperties();
		if(mode[0]==1){
			branchModel = new BranchModel();
			organizationModel = new OrganizationModel();
			productInventoryModel  = new ProductInventoryModel();
			if(mode[1]==outlet){

				branchModel.setId(userSessionBean.getBranchBean().getId());
				branchModel.setUserName(userSessionBean.getUserName());
				branchModel.setOrganizationModel(organizationModel);
				productInventoryModel.setBranchModel(branchModel);
			}else if(mode[1]==branch){
				branchModel.setId(mode[2]);
				branchModel.setUserName(userSessionBean.getUserName());

				branchModel.setOrganizationModel(organizationModel);
				productInventoryModel.setBranchModel(branchModel);
			}else{
				organizationModel.setId(mode[2]);
				branchModel.setUserName(userSessionBean.getUserName());

				branchModel.setOrganizationModel(organizationModel);
				productInventoryModel.setBranchModel(branchModel);

			}
			productStockDownloadService.download(user,type, token, response, productInventoryModel);

		}
	} 

	//***************purchase order Reports stuff****************

	@RequestMapping(value = "/openPurchaseReportForm",method = RequestMethod.GET)
	public ModelAndView openPurchaseReportForm(HttpSession session,@ModelAttribute("command")PurchaseReportBean purchaseReportBean,BindingResult result,@RequestParam("type")String type){
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		initializeProperties();
		if(mode[0]==1){
			branchModel = new BranchModel();
			accountStatusModel  = new StatusModel();
			accountStatusModel.setId(active);
			resourceModel = new ResourceModel();
			resourceModel.setAccountStatusModel(accountStatusModel);
			branchModel.setResourceModel(resourceModel);
			organizationModel = new OrganizationModel();

			model=new HashMap<String , Object>();
			if(mode[1]== outlet){
				branchModel.setId(userSessionBean.getBranchBean().getId());
				branchModel = branchService.getBranchbyId(branchModel);
				model.put("branch", branchModel.getName());
				model.put("branchId", branchModel.getId());
				model.put("report", "--select--");
				model.put("reportId", 0);
				model.put("reportInitials", FormUtilities.getreportInitial());

			}else if(mode[1]== branch){
				branchModel.setId(mode[2]);
				branchModel = branchService.getBranchbyId(branchModel);
				model.put("branch", userSessionBean.getName());
				model.put("branchId", userSessionBean.getId());
				model.put("report", "--select--");
				model.put("reportId", 0);
				model.put("reportInitials", FormUtilities.getreportInitial());

			}
			else if(mode[1]==organization){
				organizationModel.setId(userSessionBean.getId());
				branchModel.setOrganizationModel(organizationModel);
				model.put("branch", "--Select Branch--");
				model.put("branchId", 0);
				model.put("branches", purchaseController.getBranches(branchModel));
				model.put("report", "--select--");
				model.put("reportId", 0);
				model.put("reportInitials", FormUtilities.getreportInitial());
			}
			if(type.contentEquals("purchase")){
				model.put("type", purchase);
				return new ModelAndView("openPurchaseReportForm",model);
			}else if(type.contentEquals("purchaseReturns")){
				model.put("type", purchaseReturns);
				return new ModelAndView("openPurchaseRtnReportForm",model);
			}else if(type.contentEquals("salesOrder")){
				model.put("type", salesOrder);
				return new ModelAndView("openSalesReportForm",model);
			}else if(type.contentEquals("salesReturn")){
				model.put("type", salesReturn);
				return new ModelAndView("openSalesReturnReportsForm",model);
			}else if(type.contentEquals("PreSales")){
				model.put("type", preSaleOrd);
				return new ModelAndView("openPreSalesReportsForm",model);
			}
		}
		else{
			return new ModelAndView("error");
		}
		return null;
	}
	@RequestMapping(value = "/openPurchaseOrdersView",method = RequestMethod.POST)
	public ModelAndView purchaseOrderReports(@ModelAttribute("command")PurchaseReportBean purchaseReportBean,BindingResult result) throws ParseException{

		branchModel = new BranchModel();
		branchModel.setId(purchaseReportBean.getOutletBean().getId());
	//	Calendar cal = Calendar.getInstance();
		String key = purchaseReportBean.getInitial();
		switchCodn(purchaseReportBean.getFromDate(),purchaseReportBean.getToDate(), key);

		String type = purchaseReportBean.getType();
		model=new HashMap<String , Object>();
		if(type.contentEquals(purchase)){
			model.put("type",purchase);
			model.put("purchase",purchase);
			model.put("purchaseOrderBean",PurchaseOrderBeanPreparation1.prepareListOfPurchaseOrderBeanForReports1(reportsService.purchaseOrderReports(fromDate, toDate,branchModel)));
			return new ModelAndView("purchaseOrdersView",model);
		}else if(type.contentEquals(purchaseReturns)){
			model.put("type", purchaseReturns);
			model.put("purchaseReturns",purchaseReturns);
			model.put("purchaseOrderBean", PurchaseReturnOrderBeanPreparation1.preparePurchaseReturnOrderBeanFromPurchaseReturnOrderModel1(reportsService.purchaseReturnOrderReports(fromDate,toDate,branchModel)));
			return new ModelAndView("purchaseOrdersView",model);
		}
		return null;
		
	}

	@RequestMapping(value = "/purchaseReports/download")
	public void purchaseReportsDownloads(HttpSession session,@RequestParam String type,
			@RequestParam String token,@RequestParam Integer bId,@RequestParam String cat,
			@RequestParam String frm,@RequestParam String todate,@RequestParam String orderType,
			HttpServletResponse response) throws ParseException{
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		initializeProperties();
		if(mode[0]==1){

			branchModel = new BranchModel();
			branchModel.setId(bId);

			Calendar cal = Calendar.getInstance();
			switch (cat) {
			case "1":
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				break;
			case "2":
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,0));
				break;
			case "3":
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,2));
				break;
			case "4":
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(frm);
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(todate);
			default:
				break;
			}
			branchModel.setId(branchModel.getId());
			if(orderType.contentEquals(purchase)){
				purchaseOrderDownloadService.download(user,type, token, response,fromDate,toDate,branchModel);
			}else if(orderType.contentEquals(purchaseReturns)){
				purchaseRetrunOrderDwnService.download(user,type, token, response, fromDate, toDate, branchModel);
			}
		}
	} 
	@RequestMapping(value = "/openSalesOrdersView" , method = RequestMethod.POST)
	public ModelAndView salesOrderReportsData(HttpSession session,@ModelAttribute("command")PurchaseReportBean purchaseReportBean,BindingResult result) throws ParseException{
		
		initializeProperties();
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
	//	Calendar cal = Calendar.getInstance();
		String key = purchaseReportBean.getInitial();
		switchCodn(purchaseReportBean.getFromDate(),purchaseReportBean.getToDate(), key);

		if(mode[0]==1){  
			model = new HashMap<String, Object>();
			salesOrderModel = new SalesOrderModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			salesOrderModels = new ArrayList<SalesOrderModel>();
			categoryModel = new CategoryModel();
			if(purchaseReportBean.getType().contentEquals("SalesOrder")){
				categoryModel.setId(sales);
			}
			else if(purchaseReportBean.getType().contentEquals("PreSales")){
				categoryModel.setId(preSales);
			}
			salesOrderModel.setCategoryModel(categoryModel);
			try {   

				if(mode[1]== outlet){			     
					branchModel.setId(userSessionBean.getBranchBean().getId());	
					branchModel.setOrganizationModel(organizationModel);
					salesOrderModel.setBranchModel(branchModel); 
					salesOrderModels  = reportsService.getSalesOrderReportsList(fromDate, toDate,salesOrderModel);

				}else 
					if(mode[1]== branch){
						branchModel.setId(mode[2]);
						branchModel.setOrganizationModel(organizationModel);
						salesOrderModel.setBranchModel(branchModel); 
						salesOrderModels = reportsService.getSalesOrderReportsList(fromDate, toDate,salesOrderModel);

					}else if(mode[1]== organization){
						organizationModel.setId(mode[2]);
						branchModel.setOrganizationModel(organizationModel);
						branchModel.setId(purchaseReportBean.getOutletBean().getId());
						salesOrderModel.setBranchModel(branchModel); 

						salesOrderModels = reportsService.getSalesOrderReportsList(fromDate, toDate,salesOrderModel);

					}

			} catch (Exception e) {

				e.printStackTrace();
			}

			model.put("salesOrders", SalesOrderBeanPreparation1.prepareListOfSalesOrderBean1(salesOrderModels));
			model.put("sale", sales);
			model.put("preSale", preSales);
			model.put("outlet", outlet);
			return new ModelAndView("salesOrderReportsView",model);
			
		}
		return null;
	}
	@RequestMapping(value = "/salesReports/download")
	public void salesAndPreSalesOrderListReports(HttpSession session,@RequestParam String type,
			@RequestParam String token,@RequestParam Integer bId,@RequestParam String cat,
			@RequestParam String frm,@RequestParam String todate,@RequestParam String orderType,
			HttpServletResponse response) throws ParseException{
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		initializeProperties();
		if(mode[0]==1){

			branchModel = new BranchModel();
			salesOrderModel = new SalesOrderModel();
			organizationModel = new OrganizationModel();
			categoryModel = new CategoryModel();
			branchModel.setId(bId);
			branchModel.setOrganizationModel(organizationModel);
			salesOrderModel.setBranchModel(branchModel);
			if(orderType.contentEquals(salesOrder)){
				categoryModel.setId(sales);
			}else if(orderType.contentEquals(preSalesOrd)){
				categoryModel.setId(preSales);
			}
			salesOrderModel.setCategoryModel(categoryModel);
			Calendar cal = Calendar.getInstance();
			switch (cat) {
			case "1":
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				break;
			case "2":
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,0));
				break;
			case "3":
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,2));
				break;
			case "4":
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(frm);
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(todate);
			default:
				break;
			}
			
				salesAndPreSalesOrderListDwnService.download(user,type, token, response, fromDate, toDate, salesOrderModel);
			
		}
	}
	public void switchCodn(String frmDate1,String toDate1,String key) throws ParseException{
		Calendar cal = Calendar.getInstance();
		
		switch (key) {
		case "1":
			fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
			toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
			break;

		case "2":
			toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
			fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,0));
			break;
		case "3":
			toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
			fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,2));
			break;
		default:
			fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(frmDate1);
			toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(toDate1);
			break;
		}
	}
	@RequestMapping(value = "/openSalesReturnOrdersView" , method = RequestMethod.POST)
	public ModelAndView purchaseReturnsOrderView(HttpSession session,@ModelAttribute("command")PurchaseReportBean purchaseReportBean,BindingResult result) throws ParseException{
		
		initializeProperties();
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
	//	Calendar cal = Calendar.getInstance();
		String key = purchaseReportBean.getInitial();
		
		switchCodn(purchaseReportBean.getFromDate(),purchaseReportBean.getToDate(), key);
		
		if(mode[0]==1){
			model = new HashMap<String, Object>();
			salesReturnOrderModel = new SalesReturnOrderModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			salesReturnOrderModels = new ArrayList<SalesReturnOrderModel>();
			
			try {   

			if(mode[1]  == outlet){			     
					branchModel.setId(userSessionBean.getBranchBean().getId());	
					branchModel.setOrganizationModel(organizationModel);
					salesReturnOrderModel.setBranchModel(branchModel); 
					salesReturnOrderModels = reportsService.getSalesReturnOrderList(fromDate, toDate,salesReturnOrderModel);

				}else 
				if(mode[1]== branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					salesReturnOrderModel.setBranchModel(branchModel); 
					salesReturnOrderModels = reportsService.getSalesReturnOrderList(fromDate, toDate,salesReturnOrderModel);

				}else if(mode[1]== organization){
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					branchModel.setId(purchaseReportBean.getOutletBean().getId());
					salesReturnOrderModel.setBranchModel(branchModel); 
					
					salesReturnOrderModels = reportsService.getSalesReturnOrderList(fromDate, toDate,salesReturnOrderModel);
					
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
						
			model.put("salesReturnOrders", SalesReturnOrderBeanPreparation1.prepareListOfSalesReturnOrderBean1(salesReturnOrderModels));
			return new ModelAndView("openSalesRtnOrderView",model);

			
		}else{
			return new ModelAndView("error");
		}
	}
	
	@RequestMapping(value = "/salesReturnReports/download")
	public void purchaseRtnOrderListReportsDownload(HttpSession session,@RequestParam String type,
			@RequestParam String token,@RequestParam Integer bId,@RequestParam String cat,
			@RequestParam String frm,@RequestParam String todate,@RequestParam String orderType,
			HttpServletResponse response) throws ParseException{
		initializeProperties();
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		if(mode[0]==1){  
			model = new HashMap<String, Object>();
			salesReturnOrderModel = new SalesReturnOrderModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			salesReturnOrderModels = new ArrayList<SalesReturnOrderModel>();
			Calendar cal = Calendar.getInstance();
			switch (cat) {
			case "1":
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				break;
			case "2":
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,0));
				break;
			case "3":
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getCurrentDate());
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,2));
				break;
			case "4":
				fromDate = DateAndTimeUtilities.parseStringDateToSqlDateFormat(frm);
				toDate=DateAndTimeUtilities.parseStringDateToSqlDateFormat(todate);
			default:
				break;
			}
			try {   

			if(mode[1]  == outlet){			     
					branchModel.setId(userSessionBean.getBranchBean().getId());	
					branchModel.setOrganizationModel(organizationModel);
					salesReturnOrderModel.setBranchModel(branchModel); 
			}else 
				if(mode[1]== branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					salesReturnOrderModel.setBranchModel(branchModel); 
				}else if(mode[1]== organization){
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					branchModel.setId(bId);
					salesReturnOrderModel.setBranchModel(branchModel); 
				}

			} catch (Exception e) {

				e.printStackTrace();
			}
					salesReturnOrderListDownloadService.download(user,type, token, response, fromDate, toDate, salesReturnOrderModel);	
		}

	}
	@RequestMapping(value = "/purchaseOrderListReports/download")
	public void purchaseOrderListReports(HttpSession session,@RequestParam String type,
			@RequestParam String token,
			HttpServletResponse response){
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		initializeProperties();
		if(mode[0]==1){
			purchaseOrderModel = new PurchaseOrderModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			creatorRoleModel = new RoleModel();
			resourceModel = new ResourceModel();
			if(mode[1]==branch){
				creatorRoleModel.setId(mode[1]);
				resourceModel.setCreatorRoleModel(creatorRoleModel);
				branchModel.setResourceModel(resourceModel);
				branchModel.setId(mode[2]);
				purchaseOrderModel.setBranchModel(branchModel);
			}if(mode[1]==organization){
				creatorRoleModel.setId(mode[1]);
				resourceModel.setCreatorRoleModel(creatorRoleModel);
				organizationModel.setResourceModel(resourceModel);
				organizationModel.setId(mode[2]);
				branchModel.setOrganizationModel(organizationModel);
				purchaseOrderModel.setBranchModel(branchModel);
			}
			purchaseOrderListDownloadService.download(user,type, token, response, purchaseOrderModel);

		}
	}

	@RequestMapping(value = "/purchaseOrderLineitemsReports/download")
	public void purchaseOrderLineitemsReports(HttpSession session,@RequestParam String type,
			@RequestParam String token,@RequestParam String invoice,
			HttpServletResponse response){
		userSessionBean = SessionUtilities.giveMeSession(session);
		String user = userSessionBean.getUserName();
		int mode[] = SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			purchaseOrderModel = new PurchaseOrderModel();
			purchaseOrderModel.setInvoiceNo(invoice);
			pLineitemsReportsDownloadService.download(user,type, token, response, purchaseOrderModel);
		}
	}

	//	********************Sales Order reports***************************
	@RequestMapping(value = "/salesOrderListReports/download")
	public void salesOrderListReports(HttpSession session,@RequestParam String type,
			@RequestParam String token,@RequestParam String task_type,
			HttpServletResponse response){
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[] = SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		initializeProperties();
		if(mode[0]==1){
			salesOrderModel  = new SalesOrderModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			categoryModel  = new CategoryModel();
			if(task_type.contentEquals("sales")){
				categoryModel.setId(sales);
			}
			else if(task_type.contentEquals("preSales")){
				categoryModel.setId(preSales);
			}
			salesOrderModel.setCategoryModel(categoryModel);
			if(mode[1]==outlet){

				branchModel.setId(userSessionBean.getBranchBean().getId());
				branchModel.setOrganizationModel(organizationModel);
				salesOrderModel.setBranchModel(branchModel); 
			}else if(mode[1]==branch){
				branchModel.setId(mode[2]);
				branchModel.setOrganizationModel(organizationModel);
				salesOrderModel.setBranchModel(branchModel);
			}else if(mode[1]==organization){
				organizationModel.setId(mode[2]);
				branchModel.setOrganizationModel(organizationModel);
				salesOrderModel.setBranchModel(branchModel);
			}
			salesOrderListDownloadService.download(user,type, token, response, salesOrderModel);

		}
	}


	@RequestMapping(value = "/salesReturnsOrderListReports/download")
	public void salesReturnOrderListReports(HttpSession session,@RequestParam String type,@RequestParam String token,HttpServletResponse response){
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		initializeProperties();
		String user = userSessionBean.getUserName();
		if(mode[0]==1){  
			model = new HashMap<String, Object>();
			salesReturnOrderModel  = new SalesReturnOrderModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();


			try {   

				if(mode[1]  == outlet){			     
					branchModel.setId(userSessionBean.getBranchBean().getId());	
					branchModel.setOrganizationModel(organizationModel);
					salesReturnOrderModel.setBranchModel(branchModel); 
				}else if(mode[1]== branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					salesReturnOrderModel.setBranchModel(branchModel); 
				}else if(mode[1]== organization){
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					salesReturnOrderModel.setBranchModel(branchModel); 
				}
				salesReturnOrderListDownloadService.download(user,type, token, response, salesReturnOrderModel);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}}


	@RequestMapping(value = "/purchaseReturnOrderListReports/download")
	public void purchaseReturnOrderListReports(HttpSession session,@RequestParam String type,
			@RequestParam String token,HttpServletResponse response){
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		initializeProperties();
		if(mode[0]==1){
			model = new HashMap<String, Object>();
			purchaseReturnOrderModel  = new PurchaseReturnOrderModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			try { 
				if(mode[1]==outlet){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					purchaseReturnOrderModel.setBranchModel(branchModel); 
				}
				if(mode[1]== branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					purchaseReturnOrderModel.setBranchModel(branchModel);
				}else if(mode[1]== organization){
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					purchaseReturnOrderModel.setBranchModel(branchModel); 
				}
				purchaseReturnOrderListDwdService.download(user,type, token, response, purchaseReturnOrderModel);

			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	@RequestMapping(value = "/purchaseReturnOrderLineitemsReports/download")
	public void purchaseRtnOrderLineitemsListReports(HttpSession session,@RequestParam String type,
			@RequestParam String token,@RequestParam Integer invoice,
			HttpServletResponse response){
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		if(mode[0]==1){  
			purchaseReturnLineItemsModel =new PurchaseReturnLineItemsModel();
			purchaseReturnOrderModel = new PurchaseReturnOrderModel();
			purchaseReturnOrderModel.setId(invoice);
			purchaseReturnLineItemsModel.setPurchaseReturnOrderModel(purchaseReturnOrderModel);
			
			purchaseRtnOrderLineitemsDwnService.download(user,type, token, response, purchaseReturnLineItemsModel);
			
		}
	}
	
	@RequestMapping(value = "/salesReturnOrderLineitemsReports/download")
	public void salesReturnLineItemsList(HttpSession session,@RequestParam String type,
			@RequestParam String token,@RequestParam Integer invoice,
			HttpServletResponse response){
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		if(mode[0]==1){ 
			salesReturnLineItemsModel  = new SalesReturnLineItemsModel();
			salesReturnOrderModel = new SalesReturnOrderModel();
			salesReturnOrderModel.setId(invoice);
			salesReturnLineItemsModel.setSalesReturnOrderModel(salesReturnOrderModel);
			
			salesReturnOrderListDownloadService.downloadForSalesRtnLineitems(user, type, token, response,salesReturnOrderModel);
		}

	}
//	***************Product Sales Reports********************
	
	@RequestMapping(value = "/openProductSalesReportForm",method = RequestMethod.GET)
	public ModelAndView productSalesReportForm(HttpSession session,@ModelAttribute("command")ProductReportBean productReportBean){
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			initializeProperties();
			model = new HashMap<String, Object>();
			branchModel = new BranchModel();
			productInventoryModel = new ProductInventoryModel();
			organizationModel = new OrganizationModel();
			creatorRoleModel = new RoleModel();
			resourceModel = new ResourceModel();
			if(mode[1]==outlet){
				branchModel.setId(userSessionBean.getBranchBean().getId());
				productInventoryModel.setBranchModel(branchModel);
				branchModel = branchService.getBranchbyId(branchModel);
				model.put("category", "--select--");
				model.put("catId", 0);
				model.put("product", "--select--");
				model.put("proNameId", 0);
				model.put("branch", branchModel.getName());
				model.put("branchId", branchModel.getId());
				model.put("report", "--select--");
				model.put("reportId", 0);
				model.put("reportInitials", FormUtilities.getreportInitial());

			}else if(mode[1]==branch){
				branchModel.setId(mode[2]);
				productInventoryModel.setBranchModel(branchModel);
				branchModel = branchService.getBranchbyId(branchModel);
				model.put("category", "--select--");
				model.put("catId", 0);
				model.put("product", "--select--");
				model.put("proNameId", 0);
				model.put("branch", branchModel.getName());
				model.put("branchId", branchModel.getId());
				model.put("report", "--select--");
				model.put("reportId", 0);
				model.put("reportInitials", FormUtilities.getreportInitial());

			}else if(mode[1]==organization){
				accountStatusModel  = new StatusModel();
				accountStatusModel.setId(active);
				resourceModel = new ResourceModel();
				resourceModel.setAccountStatusModel(accountStatusModel);
				branchModel.setResourceModel(resourceModel);
				organizationModel.setId(mode[2]);
				branchModel.setOrganizationModel(organizationModel);
				model.put("category", "--select--");
				model.put("catId", 0);
				model.put("product", "--select--");
				model.put("proNameId", 0);
				model.put("branch", "--Select Branch--");
				model.put("branchId", 0);
				model.put("branches", purchaseController.getBranches(branchModel));
				model.put("report", "--select--");
				model.put("reportId", 0);
				model.put("reportInitials", FormUtilities.getreportInitial());

			}
			return new ModelAndView("openProductReportsForm",model);

		}
		return null;
	}
	
	@RequestMapping(value = "/getProdctNamesFromInv" , method = RequestMethod.POST)
	public @ResponseBody List<ProductInventoryModel> getProductListFromInv(HttpSession session,@RequestParam("bId") int bid,@RequestParam("catId") int catId){
		
		categoryModel = new CategoryModel();
		branchModel = new BranchModel();
		productModel = new ProductModel();
		branchModel.setId(bid);
		categoryModel.setId(catId);
		productModel.setCategoryModel(categoryModel);
		productInventoryModel.setProductModel(productModel);
		productInventoryModel.setBranchModel(branchModel);
		
		List<ProductInventoryModel> inventoryModels=productService.getProductListFromInventory(bid, catId);
		
		return inventoryModels;
	}
	
	@RequestMapping(value = "/openProductSalesView")
	public ModelAndView getSalesByProduct(HttpSession session,@ModelAttribute("command")ProductReportBean productReportBean,BindingResult result) throws ParseException{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
		//	Calendar cal = Calendar.getInstance();
			String key = productReportBean.getInitial();
			
			switchCodn(productReportBean.getFromDate(),productReportBean.getToDate(), key);
			model = new HashMap<String, Object>();
			outletModel = new OutletModel();
			branchModel = new BranchModel();
			productInventoryModel = new ProductInventoryModel();
			productModel = new ProductModel();
			salesLineItemsModel  = new SalesLineItemsModel();
			organizationModel = new OrganizationModel();
			if(mode[1]==outlet){
				branchModel.setId(userSessionBean.getBranchBean().getId());
				branchModel.setOrganizationModel(organizationModel);
				outletModel.setBranchModel(branchModel);
				productModel.setName(productReportBean.getProductName());
				productInventoryModel.setProductModel(productModel);
				salesLineItemsModel.setOutletModel(outletModel);
				salesLineItemsModel.setProductInventoryModel(productInventoryModel);
				itemsModels  = reportsService.getProductSales(fromDate,toDate,salesLineItemsModel);
				
			}else if(mode[1]==branch){
				branchModel.setId(mode[2]);
				branchModel.setOrganizationModel(organizationModel);
				outletModel.setBranchModel(branchModel);
				productModel.setName(productReportBean.getProductName());
				productInventoryModel.setProductModel(productModel);
				salesLineItemsModel.setOutletModel(outletModel);
				salesLineItemsModel.setProductInventoryModel(productInventoryModel);
				itemsModels  = reportsService.getProductSales(fromDate,toDate,salesLineItemsModel);

			}else if(mode[1]==organization){
				organizationModel.setId(mode[2]);
				branchModel.setId(productReportBean.getOutletBean().getId());
				branchModel.setOrganizationModel(organizationModel);
				outletModel.setBranchModel(branchModel);
				productModel.setName(productReportBean.getProductName());
				productInventoryModel.setProductModel(productModel);
				salesLineItemsModel.setOutletModel(outletModel);
				salesLineItemsModel.setProductInventoryModel(productInventoryModel);
				itemsModels  = reportsService.getProductSales(fromDate,toDate,salesLineItemsModel);

			}
			model.put("salesLineItems", SalesLineItemsBeanPreparation1.prepareListOfSalesLineItemsBean1(itemsModels));
			return new ModelAndView("openSalesByProduct",model);
		}else{
		return new ModelAndView("error");
		}
	}
	
	@RequestMapping(value = "/productSalesReports/download")
	public void getSalesByProductReports(HttpSession session,@RequestParam String type,@RequestParam String proName,
			@RequestParam String token,@RequestParam Integer bId,@RequestParam String cat,
			@RequestParam String frm,@RequestParam String todate,
			HttpServletResponse response) throws ParseException{
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		String user = userSessionBean.getUserName();
		initializeProperties();
		if(mode[0]==1){
			model = new HashMap<String, Object>();
			outletModel = new OutletModel();
			branchModel = new BranchModel();
			productInventoryModel = new ProductInventoryModel();
			productModel = new ProductModel();
			salesLineItemsModel  = new SalesLineItemsModel();
			organizationModel = new OrganizationModel();
			if(mode[1]==outlet){
				branchModel.setId(userSessionBean.getBranchBean().getId());
				branchModel.setOrganizationModel(organizationModel);
				outletModel.setBranchModel(branchModel);
				productModel.setName(proName);
				productInventoryModel.setProductModel(productModel);
				salesLineItemsModel.setOutletModel(outletModel);
				salesLineItemsModel.setProductInventoryModel(productInventoryModel);
			}else if(mode[1]==branch){
				branchModel.setId(mode[2]);
				branchModel.setOrganizationModel(organizationModel);
				outletModel.setBranchModel(branchModel);
				productModel.setName(proName);
				productInventoryModel.setProductModel(productModel);
				salesLineItemsModel.setOutletModel(outletModel);
				salesLineItemsModel.setProductInventoryModel(productInventoryModel);
			}else if(mode[1]==organization){
				organizationModel.setId(mode[2]);
				branchModel.setId(bId);
				branchModel.setOrganizationModel(organizationModel);
				outletModel.setBranchModel(branchModel);
				productModel.setName(proName);
				productInventoryModel.setProductModel(productModel);
				salesLineItemsModel.setOutletModel(outletModel);
				salesLineItemsModel.setProductInventoryModel(productInventoryModel);
			}
			salesOrderByProductDwnService.download(user,type, token, response, fromDate, toDate, salesLineItemsModel);
		}
	}
}