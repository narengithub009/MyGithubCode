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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.relyits.rmbs.beans.purchase.PurchaseLineItemsBean;
import com.relyits.rmbs.beans.purchase.PurchaseReturnLineItemsBean;
import com.relyits.rmbs.beans.purchase.PurchaseReturnLineItemsBean1;
import com.relyits.rmbs.beans.purchase.PurchaseReturnOrderBean;
import com.relyits.rmbs.beans.purchase.PurchaseReturnOrderBean1;
import com.relyits.rmbs.beans.purchase.PurchaseReturnsFormBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseReturnLineItemsBeanPreparation1;
import com.relyits.rmbs.beans_preparation.purchase.PurchaseReturnOrderBeanPreparation1;
import com.relyits.rmbs.beans_preparation.resources.VatBeanPreparation;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.purchase.PurchaseLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseOrderModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnLineItemsModel;
import com.relyits.rmbs.model.purchase.PurchaseReturnOrderModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model_preparation.purchase.PurchaseReturnLineItemsModelPreparation;
import com.relyits.rmbs.model_preparation.sales.SalesLineItemsModelPreparation;
import com.relyits.rmbs.service.PageNavigator;
import com.relyits.rmbs.service.ProductService;
import com.relyits.rmbs.service.PurchaseReturnsService;
import com.relyits.rmbs.service.PurchaseService;
import com.relyits.rmbs.service.ResourcesService;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;
import com.relyits.rmbs.utilities.NumToWords;
import com.relyits.rmbs.utilities.SessionUtilities;
import com.relyits.rmbs.utilities.VatDetailsPreaperationService;
@Controller
public class PurchaseReturnOrderController {
	
	@Autowired
	private PurchaseReturnsService purchaseReturnsService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private ProductService productService;
	
	@Autowired
    private PageNavigator pageNavigator;
	
	private Map<String, String> properties= null;
	private UserSessionBean userSessionBean = null;
	private Long count = null;
	
	int medical,general,organization,branch,outlet ;

	@Value("${category.medical}") String categorymedical;
	@Value("${category.general}") String categorygeneral;
	@Value("${role.organization}") String org;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${app.page.size.default}") private int pageSize;
    @Value("${app.page.nav.trail}") private int pageNavTrail;
	
	


	public Map<String, String> initializeProperties(){

		medical=Integer.parseInt(categorymedical);
		general = Integer.parseInt(categorygeneral);
		organization = Integer.parseInt(org);
		branch = Integer.parseInt(branchRole);
		outlet = Integer.parseInt(outletRole);

		return properties;
	}

	private Map<String,Object> model =null;
	private PurchaseOrderModel purchaseOrderModel = null;
	private OrganizationModel organizationModel = null;
	private BranchModel branchModel = null;
	private PurchaseReturnLineItemsModel purchaseReturnLineItemsModel = null;
	private ProductInventoryModel productInventoryModel = null;
	private PurchaseLineItemsModel purchaseLineItemsModel = null;
	private PurchaseReturnOrderModel purchaseReturnOrderModel = null;
	private List<PurchaseReturnOrderModel> purchaseReturnOrderModels = null;

	private List<PurchaseReturnLineItemsModel> purchaseReturnLineItemsModels = null;
	private List<PurchaseLineItemsModel> oldPurchaseLineItemsModels = null;
	String returnMsg = null;
	@RequestMapping(value = "/createPurchasereturnOrder", 
			method = RequestMethod.POST, 
			headers="Accept=*/*")
	public ModelAndView createPurchaseReturnOrder(@RequestBody String str,HttpServletRequest request,HttpSession session) throws ParseException{
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);

		if(mode[0]==1){
			ObjectMapper objectMapper = new ObjectMapper();
			List<PurchaseReturnsFormBean> purchaseReturnsFormBeans=new ArrayList<PurchaseReturnsFormBean>();
			
			PurchaseReturnsFormBean purchaseReturnsFormBean = null;
			boolean flag = false;
			
			try{
				
				JsonNode node = objectMapper.readTree(str);
				for(int i=0;i<node.size();i++){
					
					purchaseReturnsFormBean = new PurchaseReturnsFormBean();
					purchaseReturnsFormBean = objectMapper.convertValue(node.get(i),PurchaseReturnsFormBean.class);
					
					purchaseReturnsFormBeans.add(purchaseReturnsFormBean);
					flag = true;
				}
			}
			catch(Exception e){
				flag = false;
			}
			
			if(flag){
				model = new HashMap<String,Object>();
				purchaseReturnLineItemsModels = new ArrayList<PurchaseReturnLineItemsModel>();
				oldPurchaseLineItemsModels = new ArrayList<PurchaseLineItemsModel>();
				
				purchaseOrderModel = new PurchaseOrderModel();
				
				for(PurchaseReturnsFormBean pReturnsFormBean:purchaseReturnsFormBeans){
					
					purchaseLineItemsModel = new PurchaseLineItemsModel();
					purchaseLineItemsModel.setId(pReturnsFormBean.getId());
					
					purchaseLineItemsModel = purchaseReturnsService.getPurchaseLineItemsById(purchaseLineItemsModel);
					purchaseOrderModel = new PurchaseOrderModel();
					purchaseOrderModel = purchaseLineItemsModel.getPurchaseOrderModel();
					
					Double payAmount=SalesLineItemsModelPreparation.getPayAmount(purchaseLineItemsModel.getUnitPrice(), pReturnsFormBean.getQty(), purchaseLineItemsModel.getVat(), purchaseLineItemsModel.getDiscount());
					Double vatAmount=SalesLineItemsModelPreparation.getLineItemVatPrice(purchaseLineItemsModel.getUnitPrice(), pReturnsFormBean.getQty(), purchaseLineItemsModel.getVat(), purchaseLineItemsModel.getDiscount());
					Double disAmount=SalesLineItemsModelPreparation.getLineItemDiscountPrice(purchaseLineItemsModel.getUnitPrice(), pReturnsFormBean.getQty(), purchaseLineItemsModel.getDiscount());
					Double amount=pReturnsFormBean.getQty()*purchaseLineItemsModel.getUnitPrice();

					purchaseLineItemsModel.setQuantity(pReturnsFormBean.getQty());
					purchaseLineItemsModel.setAmount(amount);
					purchaseLineItemsModel.setDeliverableQuantity(purchaseLineItemsModel.getDeliverableQuantity()-pReturnsFormBean.getQty());
					purchaseLineItemsModel.setPayAmount(payAmount);
					if(purchaseReturnLineItemsModels.size()!=0){
					
						purchaseOrderModel.setAmount(purchaseLineItemsModel.getAmount()+purchaseReturnOrderModel.getAmount());
						purchaseOrderModel.setBillingDateAndTime(purchaseReturnOrderModel.getBillingDateAndTime());
						purchaseOrderModel.setDiscountPrice(disAmount+purchaseReturnOrderModel.getDiscountPrice());
						purchaseOrderModel.setOrderIdByDate(purchaseReturnOrderModel.getOrderIdByDate());
						purchaseOrderModel.setTotalVAT(vatAmount+purchaseReturnOrderModel.getTotalVAT());
						purchaseOrderModel.setPayAmount(payAmount+purchaseReturnOrderModel.getPayAmount());
						
						purchaseLineItemsModel.setPurchaseOrderModel(purchaseOrderModel);
						
						productInventoryModel=new ProductInventoryModel();
						productInventoryModel=purchaseLineItemsModel.getProductInventoryModel();
						productInventoryModel.setQuantity(productInventoryModel.getQuantity()-purchaseLineItemsModel.getQuantity());
						
						purchaseLineItemsModel.setProductInventoryModel(productInventoryModel);
						
						purchaseReturnLineItemsModel=new PurchaseReturnLineItemsModel();
						try {
							purchaseReturnLineItemsModel=PurchaseReturnLineItemsModelPreparation.preparepurchaseReturnLineItemsModel(purchaseLineItemsModel);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						purchaseReturnOrderModel=new PurchaseReturnOrderModel();
						purchaseReturnOrderModel=purchaseReturnLineItemsModel.getPurchaseReturnOrderModel();
						purchaseReturnOrderModel.setPurchaseOrderModel(purchaseReturnOrderModel.getPurchaseOrderModel());

						
					}else{
						purchaseOrderModel.setAmount(amount);
						
						try {
							purchaseOrderModel.setBillingDateAndTime(DateAndTimeUtilities.getCurrentDateTimeInSqlFormat());
						} catch (ParseException e) {
							e.printStackTrace();
						}	
						
						purchaseOrderModel.setDiscountPrice(disAmount);
						
						purchaseOrderModel.setOrderIdByDate(""+purchaseOrderModel.getBillingDateAndTime());
						purchaseOrderModel.setPayAmount(payAmount);
						purchaseOrderModel.setTotalVAT(vatAmount);
						
								
						productInventoryModel=new ProductInventoryModel();
						productInventoryModel=purchaseLineItemsModel.getProductInventoryModel();
						productInventoryModel.setQuantity(productInventoryModel.getQuantity()-purchaseLineItemsModel.getQuantity());
						
						purchaseLineItemsModel.setProductInventoryModel(productInventoryModel);
						purchaseReturnLineItemsModel=new PurchaseReturnLineItemsModel();
						try {
							purchaseReturnLineItemsModel=PurchaseReturnLineItemsModelPreparation.preparepurchaseReturnLineItemsModel(purchaseLineItemsModel);
						}catch (ParseException e) {
							e.printStackTrace();
						}
						
						purchaseReturnOrderModel=new PurchaseReturnOrderModel();
						purchaseReturnOrderModel=purchaseReturnLineItemsModel.getPurchaseReturnOrderModel();
						purchaseReturnOrderModel.setPurchaseOrderModel(purchaseService.getPurchaseOrderModel(purchaseOrderModel));

						}
					PurchaseLineItemsModel oldPurchaseLineItemsModel=new PurchaseLineItemsModel();
					oldPurchaseLineItemsModel.setId(pReturnsFormBean.getId());
					oldPurchaseLineItemsModel=purchaseReturnsService.getPurchaseLineItemsById(oldPurchaseLineItemsModel);
					
					oldPurchaseLineItemsModel.setDeliverableQuantity(oldPurchaseLineItemsModel.getDeliverableQuantity()-pReturnsFormBean.getQty());
					purchaseReturnLineItemsModel.setPurchaseReturnOrderModel(purchaseReturnOrderModel);

					oldPurchaseLineItemsModels.add(oldPurchaseLineItemsModel);

					purchaseReturnLineItemsModels.add(purchaseReturnLineItemsModel);
					
				}
				
				purchaseReturnLineItemsModels = purchaseReturnsService.createPurchaseReturnOrder(purchaseReturnLineItemsModels, oldPurchaseLineItemsModels);
				List<PurchaseReturnLineItemsBean1> purchaseReturnLineItemsBeans1 = new ArrayList<PurchaseReturnLineItemsBean1>();
				purchaseReturnLineItemsBeans1 = PurchaseReturnLineItemsBeanPreparation1.prepareListOfPurchaseReturnLineitemBeans1(purchaseReturnLineItemsModels);
			
				if(purchaseReturnLineItemsBeans1!=null){
					returnMsg="Purchase Return Order Successfully Submitted";
					int count1=0;
					PurchaseReturnOrderBean1 returnOrderBean1 = null;
					for(PurchaseReturnLineItemsBean1 purchaseReturnLineItemsBean1:purchaseReturnLineItemsBeans1){
						if(count1>0)break;
						returnOrderBean1=purchaseReturnLineItemsBean1.getPurchaseReturnOrderBean1();
						count1++;
					}
		       
		        model.put("PODetails", returnOrderBean1);
		        model.put("POLItems", purchaseReturnLineItemsBeans1);
				model.put("POAmntInWords",NumToWords.convertToWord(Math.round(Double.parseDouble(returnOrderBean1.getPayAmount()+""))));
				 model.put("vatDetails", VatDetailsPreaperationService.prepareVatMap(VatBeanPreparation.prepareListOfVatBeanOfPurchaseReturnLineItems(purchaseReturnLineItemsBeans1)));
			  }
			}
			return new ModelAndView("pOInvoiceView",model);
		}else{
			return null;
		}
		
	}
	
//	****************************************************
	
	@RequestMapping(value ="/openPurchaseReturnsForm", method = RequestMethod.GET)
	public ModelAndView purchaseReturnsForm(){

		return new ModelAndView("purchaseReturnsForm");
	}


	@RequestMapping(value = "/get_purchase_invoiceNo_list", 
			method = RequestMethod.GET, 
			headers="Accept=*/*")
	public @ResponseBody List<String> getPurchaseInvoiceNoList(HttpSession session,@RequestParam("term")String phrase){

		initializeProperties();
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			purchaseOrderModel = new PurchaseOrderModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			
			if(mode[1]==branch){
				branchModel.setId(mode[2]); 
				purchaseOrderModel.setBranchModel(branchModel);
				purchaseOrderModel.setOrderIdByDate(phrase);
				List<String> invoiceNoList = purchaseReturnsService.getPurchaseInvoiceNo(purchaseOrderModel);
				List<String> invoices = new ArrayList<String>();
				for(int i=0;i<invoiceNoList.size();i++){
					Object o = (Object)invoiceNoList.get(i);
					purchaseOrderModel = (PurchaseOrderModel)o;
					invoices.add(purchaseOrderModel.getOrderIdbyDate());
				}
				return invoices;

			}
		}
		return null;
	}

	@RequestMapping(value = "/dispalyPurchaseLineitems" , method = RequestMethod.POST)
	public ModelAndView purchaseOrderLineitemsDisplay(@RequestParam("invoiceNo")String invoiceNo){

		purchaseOrderModel = new PurchaseOrderModel();
		String[] id = invoiceNo.split("/");

		purchaseOrderModel.setId(Integer.parseInt(id[3]));

		List<PurchaseLineItemsBean> purchaseLineItemsBeans = new ArrayList<PurchaseLineItemsBean>();
		purchaseLineItemsBeans = purchaseReturnsService.getPurchaseLineItemsByPOId(purchaseOrderModel);

		model = new HashMap<String, Object>();
		model.put("purchaseLineItems", purchaseLineItemsBeans);
		return new ModelAndView("purchaseLineItemsDisplay",model);
	}
	
	@RequestMapping(value = "productQuantityCheck", method = RequestMethod.POST)
	public @ResponseBody int[] productQuantityCheck(@RequestParam("qty")int qtty,@RequestParam("id")String id){
		
		purchaseLineItemsModel = new PurchaseLineItemsModel();
		purchaseLineItemsModel.setId(Integer.parseInt(id));
		int qty1 = productService.getProductQuantityFromInv(purchaseLineItemsModel);
		int[] value={qty1,Integer.parseInt(id)};
		return value;
	}
	
//	**************purchase return list ******************
	
	@RequestMapping(value = "/purchaseReturnOrderList",method = RequestMethod.GET)
	public ModelAndView getPurchaseReturnOrderList(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command")PurchaseReturnOrderBean returnOrderBean,
			BindingResult result,@RequestParam(value="flag")String flag){
		initializeProperties();
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){  
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			model = new HashMap<String, Object>();
			purchaseReturnOrderModel = new PurchaseReturnOrderModel();
			organizationModel = new OrganizationModel();
			branchModel = new BranchModel();
			purchaseReturnOrderModels = new ArrayList<PurchaseReturnOrderModel>();
			try {   
				if(mode[1]== branch){
					branchModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					purchaseReturnOrderModel.setBranchModel(branchModel); 
					count = purchaseReturnsService.countAll(purchaseReturnOrderModel);
				//	purchaseReturnOrderModels = purchaseReturnsService.getPurchaseReturnOrderList(purchaseReturnOrderModel);
					purchaseReturnOrderModels = purchaseReturnsService.getByPage(pg, pageSize, purchaseReturnOrderModel);

					
				}else if(mode[1]== organization){
					organizationModel.setId(mode[2]);
					branchModel.setOrganizationModel(organizationModel);
					purchaseReturnOrderModel.setBranchModel(branchModel); 
					count = purchaseReturnsService.countAll(purchaseReturnOrderModel);
					purchaseReturnOrderModels = purchaseReturnsService.getByPage(pg, pageSize, purchaseReturnOrderModel);
					
				}
				
				model.put("createdRole", mode[1]);
				model.put("purchaseCreatedBy", mode[2]);
				model.put("purchaseReturnOrders",PurchaseReturnOrderBeanPreparation1.preparePurchaseReturnOrderBeanFromPurchaseReturnOrderModel1(purchaseReturnOrderModels));
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(flag.contentEquals("task_main")){
				return new ModelAndView("purchaseReturnOrderList",model);
			}else{
				return new ModelAndView("reOpenPurchaseReturnOrderList",model);
			}
		}else{
			return new ModelAndView("error");
	}
		}
	
	@RequestMapping(value = "/getPurchaseReturnLineItems",method = RequestMethod.POST)
	public ModelAndView  getPurchaseReturnLineItems(HttpSession session,@RequestParam(value="id")int id){
		
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){  
			model = new HashMap<String, Object>();
		purchaseReturnLineItemsModel =new PurchaseReturnLineItemsModel();
		purchaseReturnOrderModel = new PurchaseReturnOrderModel();
		purchaseReturnOrderModel.setId(id);
		purchaseReturnLineItemsModel.setPurchaseReturnOrderModel(purchaseReturnOrderModel);
		purchaseReturnLineItemsModels = purchaseReturnsService.getPurchaseReturnLineItems(purchaseReturnLineItemsModel);
		List<PurchaseReturnLineItemsBean1> purchaseReturnLineItemsBean1 = null;
		purchaseReturnLineItemsBean1 = PurchaseReturnLineItemsBeanPreparation1.prepareListOfPurchaseReturnLineitemBeans1(purchaseReturnLineItemsModels);
		model.put("purchaseReturnLineItemsBean",purchaseReturnLineItemsBean1);
		return new ModelAndView("purchaseReturnLineItemsList",model);
		}else{
			return new ModelAndView("error");
		}
		
	}
	
	@RequestMapping(value = "/loadInvoiceByPurchaseReturn",
			method = RequestMethod.POST)
	public ModelAndView  getInvoiceByPurchaseOrder(HttpSession session,@ModelAttribute("command")                     
	PurchaseReturnLineItemsBean purchaseReturnLineItemsBean,BindingResult result,
	@RequestParam(value="id") String pOId)
			throws Exception {
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			purchaseReturnLineItemsModel = new PurchaseReturnLineItemsModel();
			purchaseReturnOrderModel = new PurchaseReturnOrderModel();
			purchaseReturnOrderModel.setId(Integer.parseInt(pOId));
			purchaseReturnLineItemsModel.setPurchaseReturnOrderModel(purchaseReturnOrderModel);
			purchaseReturnLineItemsModels = purchaseReturnsService.getPurchaseReturnLineItems(purchaseReturnLineItemsModel);
			List<PurchaseReturnLineItemsBean1> purchaseReturnLineItemsBean1List=PurchaseReturnLineItemsBeanPreparation1.prepareListOfPurchaseReturnLineitemBeans1(purchaseReturnLineItemsModels);
			if(purchaseReturnLineItemsBean1List!=null){
				returnMsg="Purchase Return Order Successfully Submitted";
				int count2=0;
				PurchaseReturnOrderBean1 purchaseReturnOrderBean1=null;
				for(PurchaseReturnLineItemsBean1 purchaseReturnLineItemsBean1:purchaseReturnLineItemsBean1List){
					if(count2>0)break;
					purchaseReturnOrderBean1=purchaseReturnLineItemsBean1.getPurchaseReturnOrderBean1();
					count2++;
				}
				model = new HashMap<String, Object>();	
				
				model.put("POAmntInWords",NumToWords.convertToWord(Math.round(Double.parseDouble(purchaseReturnOrderBean1.getPayAmount().toString()))));
				model.put("POLItems", purchaseReturnLineItemsBean1List);
				model.put("PODetails", purchaseReturnOrderBean1);
				model.put("vatDetails", VatDetailsPreaperationService.prepareVatMap(VatBeanPreparation.prepareListOfVatBeanOfPurchaseReturnLineItems(purchaseReturnLineItemsBean1List)));
			
			
		    }
		}
			return new ModelAndView("pOInvoiceView",model);
		
	}
}