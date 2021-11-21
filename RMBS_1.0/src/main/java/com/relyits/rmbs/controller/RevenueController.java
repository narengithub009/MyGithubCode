package com.relyits.rmbs.controller;


import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.relyits.rmbs.beans.sales.RevenueBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseOrderBeanPreparation1;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseReturnOrderBeanPreparation1;
import com.relyits.rmbs.beans_preparation.sales.SalesOrderBeanPreparation1;
import com.relyits.rmbs.beans_preparation.sales.SalesReturnOrderBeanPreparation1;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.OrganizationService;
import com.relyits.rmbs.service.OutletService;
import com.relyits.rmbs.service.RevenueService;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;
import com.relyits.rmbs.utilities.FormUtilities;
import com.relyits.rmbs.utilities.SessionUtilities;

@Controller
public class RevenueController {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private OutletService outletService;
	
	@Autowired
	private RevenueService revenueService;
	
	@Autowired
	private PurchaseOrderController purchaseController;

	
	private UserSessionBean userSessionBean = null;
	
	private BranchModel branchModel = null;
	private OrganizationModel organizationModel = null;
	private CategoryModel categoryModel = null;
	private OutletModel outletModel = null;
	private ResourceModel resourceModel = null;
	private StatusModel accountStatusModel = null;
	
	
	private Map<String, Object> model=null;
	private Map<String, String> properties= null;
	
	private int organization,branch,outlet,salesId,preSalesId,active;	
	
	private String purchase = null;
	private String purchaseReturns = null;
	private String sales = null;
	private String preSales = null;
	private String salesReturns = null;
	
	private Date fromDate = null;
	private Date toDate = null;
	@Value("${category.medical}") String categorymedical;
	@Value("${category.general}") String categorygeneral;
	@Value("${category.sales}") String categorysales;
	@Value("${category.pre_sales}") String categorypresales;
	@Value("${role.organization}") String org;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${period.today}") String todayRevenue;
	@Value("${period.month}") String monthlyRevenue;
	@Value("${period.quarter}") String quarterlyRevenue;
	@Value("${period.halfyear}") String halfRevenue;
	@Value("${period.year}") String yearlyRevenue;
	@Value("${revenue.sales}") String salesRevenue;
	@Value("${revenue.preSales}") String preSalesRevenue;
	@Value("${revenue.purchase}") String purchaseRevenue;
	@Value("${revenue.purchaseReturns}") String purchaseReturnRevenue;	
	@Value("${revenue.salesReturns}") String salesReturnRevenue;
	@Value("${status.active}") String activeStatus;
	public Map<String, String> initializeProperties(){

		organization = Integer.parseInt(org);
		branch = Integer.parseInt(branchRole);
		outlet = Integer.parseInt(outletRole);
		salesId = Integer.parseInt(categorysales);
		preSalesId = Integer.parseInt(categorypresales);
		sales = salesRevenue.trim();
		purchase = purchaseRevenue.trim();
		preSales = preSalesRevenue.trim();
		salesReturns = salesReturnRevenue.trim();
		purchaseReturns = purchaseReturnRevenue.trim();
		active = Integer.parseInt(activeStatus);
		return properties;
	}
	
	@RequestMapping(value = "/openRevenueAccounts",method = RequestMethod.GET)
	public ModelAndView openRevenueAccountsForm(HttpSession session,
			@ModelAttribute("command") RevenueBean revenueBean,
			BindingResult result){
	
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		initializeProperties();
		if(mode[0]==1){
			outletModel = new OutletModel();
			branchModel = new BranchModel();
			accountStatusModel = new StatusModel();
			accountStatusModel.setId(active);
			resourceModel = new ResourceModel();
			resourceModel.setAccountStatusModel(accountStatusModel);
			branchModel.setResourceModel(resourceModel);
			organizationModel = new OrganizationModel();				
			model=new HashMap<String , Object>();
			if(mode[1] == outlet){
				branchModel.setId(mode[2]);				
				branchModel = branchService.getBranchbyId(branchModel);
				model.put("branch", userSessionBean.getBranchBean().getName());	
				model.put("branchId", userSessionBean.getBranchBean().getId());	
				model.put("select", "--select--");
				model.put("revenueId", 0);
				model.put("timePeriod", FormUtilities.getPeriodOfTime());
				model.put("categories", FormUtilities.getCategories(outlet));
				

			}else if(mode[1] == branch){
				branchModel.setId(mode[2]);
				branchModel = branchService.getBranchbyId(branchModel);
				model.put("branch", userSessionBean.getName());	
				model.put("branchId", userSessionBean.getId());	
				model.put("select", "--select--");
				model.put("revenueId", 0);
				model.put("timePeriod", FormUtilities.getPeriodOfTime());
				model.put("categories", FormUtilities.getCategories(0));
				

			}
			else if(mode[1] == organization){
				organizationModel.setId(userSessionBean.getId());
				branchModel.setOrganizationModel(organizationModel);
				model.put("branch", "--Select Branch--");
				model.put("branchId", 0);				
				model.put("branches", purchaseController.getBranches(branchModel));
				model.put("select", "--select--");
				model.put("timePeriod", FormUtilities.getPeriodOfTime());
				model.put("categories", FormUtilities.getCategories(0));
				
			}				
				return new ModelAndView("openRevenueAccountsForm",model);
				
		}
		else{
			return new ModelAndView("error",model);
		}
	}
	
	
	@RequestMapping(value = "/revenueAccounts",method = RequestMethod.POST)
	public ModelAndView revenueAccountsOfPurchase(HttpSession session,@ModelAttribute("command")RevenueBean revenueBean,
			BindingResult result) throws ParseException{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			
		branchModel = new BranchModel();
		categoryModel = new CategoryModel();
		branchModel.setId(revenueBean.getBranchBean().getId());	
		Calendar cal = Calendar.getInstance();
		String key = revenueBean.getRevenueAccount();
		String type = revenueBean.getType();
		
		switch (key) {
		case "Today":
			fromDate = DateAndTimeUtilities.parseStringDateToDateFormat(DateAndTimeUtilities.getCurrentDate());
			toDate=DateAndTimeUtilities.parseStringDateToDateFormat(DateAndTimeUtilities.getCurrentDate());
			break;
					
		case "Month":
			toDate=DateAndTimeUtilities.parseStringDateToDateFormat(DateAndTimeUtilities.getCurrentDate());
			fromDate = DateAndTimeUtilities.parseStringDateToDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,0));
			break;
		case "Quarter":
			toDate=DateAndTimeUtilities.parseStringDateToDateFormat(DateAndTimeUtilities.getCurrentDate());
			fromDate = DateAndTimeUtilities.parseStringDateToDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,2));
			break;
		case "HalfYear":
			toDate=DateAndTimeUtilities.parseStringDateToDateFormat(DateAndTimeUtilities.getCurrentDate());
			fromDate = DateAndTimeUtilities.parseStringDateToDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,5));
			break;
		case "Year":
			toDate=DateAndTimeUtilities.parseStringDateToDateFormat(DateAndTimeUtilities.getCurrentDate());
			fromDate = DateAndTimeUtilities.parseStringDateToDateFormat(DateAndTimeUtilities.getSubtractedDate(cal,11));
			break;
		
		default:
			fromDate = DateAndTimeUtilities.parseStringDateToDateFormat(revenueBean.getFromDate());
			toDate=DateAndTimeUtilities.parseStringDateToDateFormat(revenueBean.getToDate());
			break;
		}
		
		model = new HashMap<String , Object>();
		if(mode[1]==outlet){
			outletModel.setId(mode[2]);
		}
		if(type.contentEquals(purchase)){
			
			model.put("revenueAccounts", PurchaseOrderBeanPreparation1.prepareListOfPurchaseOrderBeanForRevenueAccounts1(revenueService.revenuePurchaseOrder(fromDate, toDate, branchModel)));
			model.put("type", purchase);
			model.put("Purchase", purchase);
		}else if(type.contentEquals(purchaseReturns)){			
			model.put("revenueAccounts",PurchaseReturnOrderBeanPreparation1.prepareListOfPurchaseReturnOrderBeanForRevenueAccounts1(revenueService.revenuePurchaseReturnOrder(fromDate, toDate, branchModel)));
			model.put("type", purchaseReturns);
			model.put("PurchaseReturn", purchaseReturns);
			
		}else
		if(type.contentEquals(sales)){
			categoryModel.setId(salesId);			
			model.put("revenueAccounts", SalesOrderBeanPreparation1.prepareListOfSalesOrderBeanForRevenueAccounts1(revenueService.revenueSalesOrder(fromDate, toDate, branchModel,categoryModel,outletModel)));
			model.put("type", sales);
			model.put("Sales", sales);
		}else
			if(type.contentEquals(preSales)){
				categoryModel.setId(preSalesId);				
				model.put("revenueAccounts", SalesOrderBeanPreparation1.prepareListOfSalesOrderBeanForRevenueAccounts1(revenueService.revenueSalesOrder(fromDate, toDate, branchModel,categoryModel,outletModel)));
				model.put("type", preSales);
				model.put("PreSales", preSales);
			}else
				if(type.contentEquals(salesReturns)){			
					
					model.put("revenueAccounts", SalesReturnOrderBeanPreparation1.prepareListOfSalesReturnOrderBeanForRevenueAccounts1(revenueService.revenueSalesReturnOrder(fromDate, toDate, branchModel,outletModel)));
					model.put("type", salesReturns);
					model.put("SalesReturn", salesReturns);
				}
	
		
		return new ModelAndView("revenueAccounts",model);
	}else{
		return new ModelAndView("error");
	}
	}

}
