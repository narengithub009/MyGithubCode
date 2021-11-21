package com.relyits.rmbs.controller;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
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
import org.springframework.web.servlet.ModelAndView;

import com.relyits.rmbs.beans.sales.SalesOrderBean;
import com.relyits.rmbs.beans.sales.SalesReturnLineItemsBean;
import com.relyits.rmbs.beans.sales.SalesReturnLineItemsBean1;
import com.relyits.rmbs.beans.sales.SalesReturnOrderBean;
import com.relyits.rmbs.beans.sales.SalesReturnOrderBean1;
import com.relyits.rmbs.beans.sales.SalesReturnsFormBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.resources.VatBeanPreparation;
import com.relyits.rmbs.beans_preparation.sales.SalesLineItemsBeanPreparation1;
import com.relyits.rmbs.beans_preparation.sales.SalesReturnLineItemsBeanPreparation1;
import com.relyits.rmbs.beans_preparation.sales.SalesReturnOrderBeanPreparation1;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.model.sales.SalesOrderModel;
import com.relyits.rmbs.model.sales.SalesReturnLineItemsModel;
import com.relyits.rmbs.model.sales.SalesReturnOrderModel;
import com.relyits.rmbs.model_preparation.sales.SalesLineItemsModelPreparation;
import com.relyits.rmbs.model_preparation.sales.SalesReturnLineItemsModelPreparation;
import com.relyits.rmbs.service.PageNavigator;
import com.relyits.rmbs.service.ResourcesService;
import com.relyits.rmbs.service.SalesReturnsService;
import com.relyits.rmbs.service.SalesService;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;
import com.relyits.rmbs.utilities.SessionUtilities;
import com.relyits.rmbs.utilities.VatDetailsPreaperationService;

@Controller
public class SalesReturnOrderController {

	@Autowired
	private SalesService salesService;
	@Autowired
	private SalesReturnsService salesReturnsService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
    private PageNavigator pageNavigator;

	private UserSessionBean userSessionBean = null;

	SalesLineItemsModel salesLineItemsModel=null;
	SalesOrderModel salesOrderModel=null;
	StatusModel statusModel=null;
	private BranchModel branchModel = null;

	private OrganizationModel organizationModel = null;

	SalesReturnOrderModel salesReturnOrderModel=null;
	SalesReturnLineItemsModel salesReturnLineItemsModel=null;
	ProductInventoryModel productInventoryModel=null;
	List<SalesReturnLineItemsModel> salesReturnLineItemsModels=null;
	List<SalesLineItemsModel> oldSalesLineItemsModels=null;
	List<SalesReturnLineItemsBean> salesReturnLineItemsBeans=null;

	Map<String, Object> model = null;
	
	private Long count = null;

	String fileName="";
	String path="";

	Map<String, String> properties= null;

	int organization,branch,outlet,sales;

	@Value("${role.organization}") String org;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${category.sales}") String categorysales;
	@Value("${app.page.size.default}") private int pageSize;
    @Value("${app.page.nav.trail}") private int pageNavTrail;


	public Map<String, String> initializeProperties(){

		organization = Integer.parseInt(org);
		branch = Integer.parseInt(branchRole);
		outlet = Integer.parseInt(outletRole);	
		sales = Integer.parseInt(categorysales);
		return properties;
	}

	List<SalesReturnLineItemsBean1> salesReturnLineItemsBeans1 = null;
	String returnMsg = null;
	@RequestMapping(value = "/createSalesReturnOrder", 
			method = RequestMethod.POST, 
			headers="Accept=*/*")

	public ModelAndView createOrder(@RequestBody String str,HttpServletRequest request,HttpSession session){
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			ObjectMapper mapper = new ObjectMapper();

			List<SalesReturnsFormBean> salesReturnsFormBeans=new ArrayList<SalesReturnsFormBean>();

			SalesReturnsFormBean salesReturnsFormBean=null;
			boolean flag=false;
			try {
				JsonNode node = mapper.readTree(str);
				for(int i=0;i<node.size();i++){
					salesReturnsFormBean=new SalesReturnsFormBean();
					salesReturnsFormBean = mapper.convertValue(node.get(i), SalesReturnsFormBean.class);
					salesReturnsFormBeans.add(salesReturnsFormBean);
					flag=true;
				}
			}catch(Exception e){
				flag=false;
			}




			if(flag){
				model = new HashMap<String, Object>();

				salesReturnLineItemsModels=new ArrayList<SalesReturnLineItemsModel>();
				oldSalesLineItemsModels=new ArrayList<SalesLineItemsModel>();

				salesReturnOrderModel=new SalesReturnOrderModel();
				for(SalesReturnsFormBean formBean:salesReturnsFormBeans){

					salesLineItemsModel=new SalesLineItemsModel();
					salesLineItemsModel.setId(formBean.getId());
					salesLineItemsModel=salesService.getSalesOrderLineItemById(salesLineItemsModel);
					salesOrderModel=new SalesOrderModel();
					salesOrderModel=salesLineItemsModel.getSalesOrderModel();

					Double payAmount=SalesLineItemsModelPreparation.getPayAmount(salesLineItemsModel.getUnitPrice(), formBean.getQty(), salesLineItemsModel.getVat(), salesLineItemsModel.getDiscount());
					Double vatAmount=SalesLineItemsModelPreparation.getLineItemVatPrice(salesLineItemsModel.getUnitPrice(), formBean.getQty(), salesLineItemsModel.getVat(), salesLineItemsModel.getDiscount());
					Double disAmount=SalesLineItemsModelPreparation.getLineItemDiscountPrice(salesLineItemsModel.getUnitPrice(), formBean.getQty(), salesLineItemsModel.getDiscount());
					Double margin=SalesLineItemsModelPreparation.getMargin(salesLineItemsModel.getProductInventoryModel().getDlPrice(), salesLineItemsModel.getUnitPrice(), formBean.getQty(), salesLineItemsModel.getVat(), salesLineItemsModel.getDiscount());
					Double netPrice=SalesLineItemsModelPreparation.getNetPrice(salesLineItemsModel.getProductInventoryModel().getPrice(), salesLineItemsModel.getVat(), salesLineItemsModel.getDiscount());
					Double amount=formBean.getQty()*salesLineItemsModel.getUnitPrice();


					salesLineItemsModel.setQuantity(formBean.getQty());
					salesLineItemsModel.setAmount(amount);
					salesLineItemsModel.setDeliverableQuantity(salesLineItemsModel.getDeliverableQuantity()-formBean.getQty());
					salesLineItemsModel.setMargin(margin);
					salesLineItemsModel.setNetPrice(netPrice); 


					if(salesReturnLineItemsModels.size()!=0){



						salesOrderModel.setAmount(salesLineItemsModel.getAmount()+salesReturnOrderModel.getAmount());
						salesOrderModel.setBillingDateAndTime(salesReturnOrderModel.getBillingDateAndTime());
						salesOrderModel.setDiscountPrice(disAmount+salesReturnOrderModel.getDiscountPrice());
						salesOrderModel.setMargin(margin+salesReturnOrderModel.getDeductionOnMargin());
						salesOrderModel.setOrderIdByDate(salesReturnOrderModel.getOrderIdByDate());
						salesOrderModel.setTotalVAT(vatAmount+salesReturnOrderModel.getTotalVAT());
						salesOrderModel.setStatusModel(salesReturnOrderModel.getStatusModel());
						salesOrderModel.setPayAmount(payAmount+salesReturnOrderModel.getPayAmount());

						salesLineItemsModel.setStatusModel(salesReturnOrderModel.getStatusModel());
						salesLineItemsModel.setSalesOrderModel(salesOrderModel);

						productInventoryModel=new ProductInventoryModel();
						productInventoryModel=salesLineItemsModel.getProductInventoryModel();
						productInventoryModel.setQuantity(productInventoryModel.getQuantity()+salesLineItemsModel.getQuantity());

						salesLineItemsModel.setProductInventoryModel(productInventoryModel);

						salesReturnLineItemsModel=new SalesReturnLineItemsModel();
						try {
							salesReturnLineItemsModel=SalesReturnLineItemsModelPreparation.prepareSalesReturnLineItemsModel(salesLineItemsModel);
						} catch (ParseException e) {
							e.printStackTrace();
						}

						salesReturnOrderModel=new SalesReturnOrderModel();
						salesReturnOrderModel=salesReturnLineItemsModel.getSalesReturnOrderModel();
						salesReturnOrderModel.setSalesOrderModel(salesReturnOrderModel.getSalesOrderModel());


					}else{



						salesOrderModel.setAmount(salesLineItemsModel.getAmount());
						try {
							salesOrderModel.setBillingDateAndTime(DateAndTimeUtilities.getCurrentDateTimeInSqlFormat());
						} catch (ParseException e) {
							e.printStackTrace();
						}
						salesOrderModel.setDiscountPrice(disAmount);
						salesOrderModel.setMargin(margin);
						salesOrderModel.setOrderIdByDate(""+salesOrderModel.getBillingDateAndTime());
						salesOrderModel.setPayAmount(payAmount);
						salesOrderModel.setTotalVAT(vatAmount);

						statusModel=new StatusModel();
						statusModel.setId(10);
						statusModel=resourcesService.getStatus(statusModel);

						salesOrderModel.setStatusModel(statusModel);


						salesLineItemsModel.setStatusModel(statusModel);
						salesLineItemsModel.setSalesOrderModel(salesOrderModel);

						productInventoryModel=new ProductInventoryModel();
						productInventoryModel=salesLineItemsModel.getProductInventoryModel();
						productInventoryModel.setQuantity(productInventoryModel.getQuantity()+salesLineItemsModel.getQuantity());

						salesLineItemsModel.setProductInventoryModel(productInventoryModel);

						salesReturnLineItemsModel=new SalesReturnLineItemsModel();
						try {
							salesReturnLineItemsModel=SalesReturnLineItemsModelPreparation.prepareSalesReturnLineItemsModel(salesLineItemsModel);
						} catch (ParseException e) {
							e.printStackTrace();
						}

						salesReturnOrderModel=new SalesReturnOrderModel();
						salesReturnOrderModel=salesReturnLineItemsModel.getSalesReturnOrderModel();
						salesReturnOrderModel.setSalesOrderModel(salesService.getSalesOrder(salesOrderModel));



					}
					SalesLineItemsModel oldSalesLineItemsModel=new SalesLineItemsModel();
					oldSalesLineItemsModel.setId(formBean.getId());
					oldSalesLineItemsModel=salesService.getSalesOrderLineItemById(oldSalesLineItemsModel);

					oldSalesLineItemsModel.setDeliverableQuantity(oldSalesLineItemsModel.getDeliverableQuantity()-formBean.getQty());
					salesReturnLineItemsModel.setSalesReturnOrderModel(salesReturnOrderModel);

					oldSalesLineItemsModels.add(oldSalesLineItemsModel);
					salesReturnLineItemsModels.add(salesReturnLineItemsModel);
				}


				salesReturnLineItemsModels=salesReturnsService.createSalesReturnOrder(salesReturnLineItemsModels,oldSalesLineItemsModels);


				salesReturnLineItemsBeans1=new ArrayList<SalesReturnLineItemsBean1>();
				salesReturnLineItemsBeans1=SalesReturnLineItemsBeanPreparation1.prepareListOfSalesReturnLineItemsBeans1(salesReturnLineItemsModels);
				if(salesReturnLineItemsBeans1!=null){
					returnMsg="Sales Order Successfully Submitted";
					int count1=0;
					SalesReturnOrderBean1 salesReturnOrderBean1=null;
					for(SalesReturnLineItemsBean1 salesReturnLineItemsBean1:salesReturnLineItemsBeans1){
						if(count1>0)break;
						salesReturnOrderBean1=salesReturnLineItemsBean1.getSalesReturnOrderBean1();
						count1++;
					}



					model.put("Order", salesReturnOrderBean1);
					model.put("Items", salesReturnLineItemsBeans1);
					model.put("vatDetails", VatDetailsPreaperationService.prepareVatMap(VatBeanPreparation.prepareListOfVatBeanOfSalesReturnLineItems(salesReturnLineItemsBeans1)));
				}
			}
			model.put("type","bean");
			model.put("flag", "SRO");
			return new ModelAndView("sOInvoiceView",model);
		}else{
			return null;
		}
	}
	List<SalesReturnOrderModel> salesReturnOrderModels = null;
	@RequestMapping(value = "/salesReturnOrderList", method = RequestMethod.GET)
	public ModelAndView salesReturnOrderList(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") SalesReturnOrderBean salesReturnOrderBean ,BindingResult result,
			@RequestParam(value="flag") String flag
			){
		initializeProperties();
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){  
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
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
					count = salesReturnsService.countAll(salesReturnOrderModel);
			//		salesReturnOrderModels = salesReturnsService.getSalesReturnOrderList(salesReturnOrderModel);
					salesReturnOrderModels = salesReturnsService.getByPage(pg, pageSize, salesReturnOrderModel);

				}else 
					if(mode[1]== branch){
						branchModel.setId(mode[2]);
						branchModel.setOrganizationModel(organizationModel);
						salesReturnOrderModel.setBranchModel(branchModel); 
						count = salesReturnsService.countAll(salesReturnOrderModel);
						salesReturnOrderModels = salesReturnsService.getByPage(pg, pageSize, salesReturnOrderModel);

					}else if(mode[1]== organization){
						organizationModel.setId(mode[2]);
						branchModel.setOrganizationModel(organizationModel);
						salesReturnOrderModel.setBranchModel(branchModel); 
						count = salesReturnsService.countAll(salesReturnOrderModel);
						salesReturnOrderModels = salesReturnsService.getByPage(pg, pageSize, salesReturnOrderModel);

					}
 
			} catch (Exception e) {

				e.printStackTrace();
			}

			model.put("salesReturnOrders", SalesReturnOrderBeanPreparation1.prepareListOfSalesReturnOrderBean1(salesReturnOrderModels));
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
			model.put("subtractor", (pg*pageSize - pageSize));


			if(flag.contentEquals("task_main")){
				return new ModelAndView("salesReturnOrderList",model);
			}else {
				return new ModelAndView("reOpenSalesReturnOrderList",model);
			}
		}else{
			return new ModelAndView("error");
		}  



	}
	@RequestMapping(value = "/getSalesReturnLineItems",
			method = RequestMethod.GET)
	public ModelAndView  getSalesLineItems(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command")                     
	SalesReturnLineItemsBean salesReturnLineItemsBean,BindingResult result,
	@RequestParam(value="sOId") String sOId)	
			throws Exception {
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){  
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			salesReturnLineItemsModel = new SalesReturnLineItemsModel();
			salesReturnOrderModel = new SalesReturnOrderModel();
			salesReturnOrderModel.setId(Integer.parseInt(sOId));
			salesReturnLineItemsModel.setSalesReturnOrderModel(salesReturnOrderModel);
			count = salesReturnsService.countAll(salesReturnLineItemsModel);
			model = new HashMap<String, Object>();

		//	model.put("salesReturnLineItems", SalesReturnLineItemsBeanPreparation1.prepareListOfSalesReturnLineItemsBeans1(salesReturnsService.getSalesReturnLineItemsModelsBySalesReturnOrderModel(salesReturnOrderModel)));
			model.put("salesReturnLineItems", SalesReturnLineItemsBeanPreparation1.prepareListOfSalesReturnLineItemsBeans1(salesReturnsService.getByPage(pg, pageSize, salesReturnLineItemsModel)));
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
			model.put("subtractor", (pg*pageSize - pageSize));
			model.put("sROID", sOId);
			return new ModelAndView("salesReturnLineItemsList",model);
		}else{
			return new ModelAndView("error");
		}  
	}

	@RequestMapping(value = "/loadInvoiceBySalesReturn", 
			method = RequestMethod.POST, 
			headers="Accept=*/*")
	public ModelAndView loadInvoiceBySalesReturn(HttpServletRequest request,HttpSession session,
			@RequestParam("sOId") String sOID){

		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		
		if(mode[0]==1){
			salesReturnOrderModel = new SalesReturnOrderModel();
			salesReturnOrderModel.setId(Integer.parseInt(sOID));
			salesReturnLineItemsModels=salesReturnsService.getSalesReturnLineItemsModelsBySalesReturnOrderModel(salesReturnOrderModel);

			List<SalesReturnLineItemsBean1> salesReturnLineItemsBean1List=SalesReturnLineItemsBeanPreparation1.prepareListOfSalesReturnLineItemsBeans1(salesReturnLineItemsModels);
			if(salesReturnLineItemsBean1List!=null){			
				int count2=0;
				SalesReturnOrderBean1 salesReturnOrderBean1=null;
				for(SalesReturnLineItemsBean1 salesReturnLineItemsBean1:salesReturnLineItemsBean1List){
					if(count2>0)break;
					salesReturnOrderBean1=salesReturnLineItemsBean1.getSalesReturnOrderBean1();
					count2++;
				}
				model = new HashMap<String, Object>();
				model.put("Items", salesReturnLineItemsBean1List);
				model.put("Order", salesReturnOrderBean1);
				model.put("vatDetails", VatDetailsPreaperationService.prepareVatMap(VatBeanPreparation.prepareListOfVatBeanOfSalesReturnLineItems(salesReturnLineItemsBean1List)));
			}
		}
		model.put("type","bean");
		model.put("flag", "SRO");
		return new ModelAndView("sROInvoiceView", model);
	}

	@RequestMapping(value="/salesReturnEntryForm" , method=RequestMethod.GET)
	public ModelAndView salesReturnEntryForm(HttpSession session,
			@ModelAttribute("command") SalesOrderBean salesOrderBean){
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){		

			try {
				model = new HashMap<String, Object>();

				model.put("createdby",userSessionBean.getId());

			} catch (Exception e) {
				e.printStackTrace();
			}

			return new ModelAndView("openSalesReturnEntryForm", model);
		}else{
			return new ModelAndView("error");
		}

	}
	@RequestMapping(value = "/salesReturnLineItemsByOrderId",
			method = RequestMethod.GET)
	public ModelAndView  salesReturnLineItemsByOrderId(HttpSession session,@ModelAttribute("command")                     
	SalesOrderBean salesOrderBean,BindingResult result)							
			throws Exception {
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){  
			salesLineItemsModel = new SalesLineItemsModel();
			salesOrderModel = new SalesOrderModel();
			salesOrderModel.setOrderIdByDate(salesOrderBean.getOrderIdByDate());
			salesLineItemsModel.setSalesOrderModel(salesOrderModel);

			model = new HashMap<String, Object>();

			model.put("salesLineItems", SalesLineItemsBeanPreparation1.prepareListOfSalesLineItemsBean1(salesReturnsService.getSalesLineItemsModelsByOrderIdByDate(salesOrderModel)));
			model.put("sale", sales);	
			model.put("outlet", outlet);
			model.put("flag", 2);


			return new ModelAndView("salesLineItemsList",model);
		}else{
			return new ModelAndView("error");
		}  
	}
}
