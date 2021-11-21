package com.relyits.rmbs.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.relyits.rmbs.beans.notifications.MailComposeBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.sales.SalesLineItemsBeanPreparation1;
import com.relyits.rmbs.model.product.ProductInventoryModel;
import com.relyits.rmbs.model.product.ProductModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.model.sales.SalesLineItemsModel;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.OrganizationService;
import com.relyits.rmbs.service.OutletService;
import com.relyits.rmbs.utilities.FormUtilities;
import com.relyits.rmbs.utilities.SendMail;
import com.relyits.rmbs.utilities.SessionUtilities;

@Controller
public class MailNotificationController {
	
	@Autowired
	private BranchService branchService; 
	@Autowired
	private PurchaseOrderController purchaseController;
	@Autowired
	private OutletService outletService;
	@Autowired
	private OrganizationService organizationService;
	private UserSessionBean userSessionBean = null;
	private Map<String, Object> model=null;
	private BranchModel branchModel=null;
	private OutletModel outletModel=null;
	private OrganizationModel organizationModel=null;
	private StatusModel accountStatusModel = null;
	private int organization,branch,outlet,active,admin;
	private Map<String, String> properties = null;
	
	@Value("${role.organization}") String org;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${status.active}") String activeStatus;
	@Value("${role.admin}") String adminRole;
	@Value("${message.sentPasswordSuccessfully}") String sentPwSuccess;
	private ResourceModel resourceModel = null;
	String passwordSent = null;
	private RoleModel creatorRoleModel = null;
	public Map<String, String> initializeProperties(){

		organization = Integer.parseInt(org);
		branch = Integer.parseInt(branchRole);
		outlet = Integer.parseInt(outletRole);
		active = Integer.parseInt(activeStatus);
		admin = Integer.parseInt(adminRole);
		passwordSent = sentPwSuccess;
		return properties ;
	}
	@RequestMapping(value="/getComposeMailForm" , method = RequestMethod.GET)
	public ModelAndView getComposeMailForm(HttpSession session,@ModelAttribute("command")MailComposeBean composeBean,BindingResult result){
		initializeProperties();
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){

			
			model = new HashMap<String, Object>();
			outletModel = new OutletModel();
			branchModel = new BranchModel();
			organizationModel = new OrganizationModel();
			if(mode[1]==outlet){
				branchModel.setId(userSessionBean.getBranchBean().getId());
				
				branchModel = branchService.getBranchbyId(branchModel);
				
				model.put("branch", branchModel.getName());
				model.put("branchId", branchModel.getId());
				model.put("email",userSessionBean.getResourceBean().getAddressBean().getEmail());
				model.put("orgId", branchModel.getOrganizationModel().getId());
				model.put("outletId", mode[2]);
				model.put("mode", mode[1]);
				return new ModelAndView("mail",model);
			}else if(mode[1]==branch){
				branchModel.setId(mode[2]);
				outletModel.setBranchModel(branchModel);
				branchModel = branchService.getBranchbyId(branchModel);
				model.put("outlet", "--Select Outlet--");
				model.put("branch", branchModel.getName());
				model.put("branchId", branchModel.getId());
				model.put("orgId", branchModel.getOrganizationModel().getId());
				model.put("outletId", mode[2]);
				model.put("outletIds", this.getOutletsByBranch(outletModel));
				model.put("mode", mode[1]);
				return new ModelAndView("mail",model);
			}else if(mode[1]==organization){
				accountStatusModel  = new StatusModel();
				accountStatusModel.setId(active);
				resourceModel  = new ResourceModel();
				resourceModel.setAccountStatusModel(accountStatusModel);
				branchModel.setResourceModel(resourceModel);
				organizationModel.setId(mode[2]);
				branchModel.setOrganizationModel(organizationModel);
				model.put("branch", "--Select Branch--");
				model.put("branchId", 0);
				model.put("branches", purchaseController.getBranches(branchModel));
				model.put("orgId", mode[2]);
				model.put("outletId", 0);
				model.put("mode", mode[1]);
				return new ModelAndView("mail",model);
			}else if(mode[1]==admin){
				organizationModel = new OrganizationModel();
				accountStatusModel = new StatusModel();
				resourceModel =new ResourceModel();
				accountStatusModel.setId(active);
				resourceModel.setAccountStatusModel(accountStatusModel);
				organizationModel.setResourceModel(resourceModel);
				model.put("admin", "--Select Organization--");
				model.put("adminId", 0);
				model.put("orgs",this.getOrganizations(organizationModel) );
				model.put("mode", mode[1]);
				return new ModelAndView("mail",model);
			}
			
		}else{
		return new ModelAndView("error");
		}
		return null;
		
		
		
	}
	/*@RequestMapping(value="/getBatchOutlets",method=RequestMethod.POST)
	public @ResponseBody List<OutletModel> getOutletsByBranch(HttpSession session,@RequestParam int branchId){
		List<OutletModel> models = null;
		
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
		outletModel = new OutletModel();
		branchModel = new BranchModel();
		branchModel.setId(branchId);
		outletModel.setBranchModel(branchModel);
		models = outletService.getOutlets(outletModel);
		
		}
		return models;
	}*/
	public LinkedHashMap<Integer, String> getOutletsByBranch(OutletModel outletModel){
		List<OutletModel> models = null;
		LinkedHashMap<Integer, String> lmap = new LinkedHashMap<Integer,String>();
		/*List<OutletModel> models = null;
		LinkedHashMap<Integer, String> lmap = new LinkedHashMap<Integer,String>();
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
		outletModel = new OutletModel();
		branchModel = new BranchModel();
		branchModel.setId(userSessionBean.getBranchBean().getId());
		outletModel.setBranchModel(branchModel);*/
		models = outletService.getOutlets(outletModel);
		for(int i=0;i<models.size();i++){
			int id = models.get(i).getId();
			String name = models.get(i).getUserName();
			lmap.put(id, name);
		}
//		}
		return lmap;
	}
	public LinkedHashMap<Integer, String> getOrganizations(OrganizationModel organizationModel){
		
		LinkedHashMap<Integer, String> organizations = new LinkedHashMap<Integer,String>();
		List<OrganizationModel> orgsList =  organizationService.getAccountRequestsByOrganizations(organizationModel);
		for(int i=0;i<orgsList.size();i++){
			
			int orgId = orgsList.get(i).getId();
			String orgName = orgsList.get(i).getName();
			organizations.put(orgId, orgName);
		}
		return organizations;
	}
	
	@RequestMapping(value="/getOrgEmailId",method=RequestMethod.POST)
	public @ResponseBody String getOrgEmailId(HttpSession session,@RequestParam int orgId){
		
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			organizationModel  = new OrganizationModel();
			organizationModel.setId(orgId);
			String emailId = organizationService.getEmailId(organizationModel);
			return emailId;
		}
		return null;
		
		
	}
	
	@RequestMapping(value="/getBranchMailId", method=RequestMethod.POST)
	public @ResponseBody String getBranchMailId(HttpSession session,@RequestParam int orgId,@RequestParam int branchId){
		
		String branchMailId = null;
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			
			branchModel = new BranchModel();
			organizationModel = new OrganizationModel();
			organizationModel.setId(orgId);
			branchModel.setId(branchId);
			branchModel.setOrganizationModel(organizationModel);
			
			branchMailId = organizationService.getBranchMailId(branchModel);
		 
		}
		return branchMailId;
	}
	@RequestMapping(value="/getAdminMailId",method=RequestMethod.POST)
	public @ResponseBody String getAdminEmailId(HttpSession session,@RequestParam int orgId){
		String adminMailId = null;
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			organizationModel  = new OrganizationModel();
			resourceModel = new ResourceModel();
			creatorRoleModel   = new RoleModel();
			creatorRoleModel.setId(userSessionBean.getResourceBean().getCreatorRoleBean().getId());
			resourceModel.setCreatorRoleModel(creatorRoleModel);
			organizationModel.setResourceModel(resourceModel);
			organizationModel.setId(orgId);
			
			adminMailId = organizationService.getAdminEmailId(organizationModel);
			
		}
		return adminMailId;
		
	}
	@RequestMapping(value="/sendingMail",method=RequestMethod.POST)
	public @ResponseBody String sendingMail(HttpSession session,@ModelAttribute("command")MailComposeBean composeBean,BindingResult result) throws Exception{
		String result1=null;
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			 String username = composeBean.getUsername().trim();
			 String password = composeBean.getPassword().trim();
			 if(username!=null&&password!=null){
			SendMail.sendMail(composeBean.getMailTo().trim(), password, username, composeBean.getBody());
			result1 = passwordSent;
			 }
		}
		return result1;
	}
	
	@RequestMapping(value = "/getOutletEmailId",method = RequestMethod.POST)
	public @ResponseBody String getOutletMailId(HttpSession session,@RequestParam int branchId,@RequestParam int outletId){
		
		String result1=null;
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			
			outletModel = new OutletModel();
			branchModel = new BranchModel();
			outletModel.setId(outletId);
			branchModel.setId(branchId);
			outletModel.setBranchModel(branchModel);
			
			result1 = organizationService.getOutletEmailId(outletModel);
			
		}

		return result1;
		
		
	}
}
