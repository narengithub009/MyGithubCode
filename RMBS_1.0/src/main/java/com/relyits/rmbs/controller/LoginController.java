package com.relyits.rmbs.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.relyits.rmbs.beans.menu.MenuBean;
import com.relyits.rmbs.beans.registration.RegistrationBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.model.menu.ChildMenuModel;
import com.relyits.rmbs.model.menu.MenuModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.OrganizationService;
import com.relyits.rmbs.service.OutletService;
import com.relyits.rmbs.utilities.FormUtilities;
import com.relyits.rmbs.utilities.SendMail;
import com.relyits.rmbs.utilities.SessionUtilities;

@Controller
public class LoginController {

	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private OutletService outletService;
	
	
	private UserSessionBean userSessionBean = null;
	private Map<String, Object> model = null;

	private OrganizationModel organizationModel = null;
	private BranchModel branchModel = null;
	private OutletModel outletModel = null;


	Map<String, String> properties= null;

	int admin, organization, branch, outlet, inactive, active;
	String loginError = null;
	String passwordSent = null;
	String wrongInform = null;

	@Value("${role.admin}") String adminRole;
	@Value("${role.organization}") String organizationRole;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${status.inactive}") String inactiveStatus;
	@Value("${status.active}") String activeStatus;	
	@Value("${message.loginerror}") String loginError1;
	@Value("${message.sentPasswordSuccessfully}") String sentPwSuccess;
	@Value("${message.wrongInformation}") String wrngInfo;

	public Map<String, String> initializeProperties(){

		admin=Integer.parseInt(adminRole);
		organization = Integer.parseInt(organizationRole);
		branch = Integer.parseInt(branchRole);
		outlet = Integer.parseInt(outletRole);
		inactive = Integer.parseInt(inactiveStatus);
		active = Integer.parseInt(activeStatus);		
		loginError = loginError1;
		passwordSent = sentPwSuccess;
		wrongInform = wrngInfo;

		return properties;
	}


	////////////*************************////////////////////////


	@RequestMapping(value ="/AdminLoginValidationSuccess", method = RequestMethod.GET)
	public ModelAndView adminLogin(@ModelAttribute("command") RegistrationBean registrationBean,BindingResult result,HttpSession session)
	{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int[] mode=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1 && mode[1]==1){
			model = new HashMap<String, Object>();
			if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==inactive && userSessionBean.getResourceBean().getRoleBean().getId()==admin){
				model.put("Logger",  userSessionBean);
				return new ModelAndView("adminInactiveLogin", model);
			}else if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==active && userSessionBean.getResourceBean().getRoleBean().getId()==admin){
				model.put("Logger",  userSessionBean);
				return new ModelAndView("adminLoginSucces", model);
			}else{
				model.put("Logger",  userSessionBean);
				model.put("message",loginError);
				model.put("initials",FormUtilities.getInitial());
				return new ModelAndView("login", model);
			}
		} 
		return new ModelAndView("error");
	}

	@RequestMapping(value ="/OrganizationLoginValidationSuccess",method = RequestMethod.GET)
	public ModelAndView organizationLogin(@ModelAttribute("command") RegistrationBean registrationBean,BindingResult result,HttpSession session)
	{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int[] mode=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1 && mode[1]==2){
			model = new HashMap<String, Object>();
			if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==inactive && userSessionBean.getResourceBean().getRoleBean().getId()==organization){
				model.put("Logger",  userSessionBean);
				return new ModelAndView("organizationInactiveLogin", model);
			}else if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==active && userSessionBean.getResourceBean().getRoleBean().getId()==organization){
				model.put("Logger",  userSessionBean);
				return new ModelAndView("organizationLoginSucces", model);
			}else{
				model.put("Logger",  userSessionBean);
				model.put("message",loginError);
				model.put("initials",FormUtilities.getInitial());
				return new ModelAndView("login", model);
			}
		} 
		return new ModelAndView("error");
	}

	@RequestMapping(value ="/BranchLoginValidationSuccess",  method = RequestMethod.GET)
	public ModelAndView branchLogin(HttpSession session,@ModelAttribute("command") RegistrationBean registrationBean,BindingResult result)
	{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int[] mode=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1 && mode[1]==3){
			model = new HashMap<String, Object>();
			if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==inactive && userSessionBean.getResourceBean().getRoleBean().getId()==branch){
				model.put("Logger",  userSessionBean);
				return new ModelAndView("branchInactiveLogin", model);
			}else if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==active&& userSessionBean.getResourceBean().getRoleBean().getId()==branch){
				model.put("Logger",  userSessionBean);
				return new ModelAndView("branchLoginSucces", model);
			}else{
				model.put("Logger",  userSessionBean);
				model.put("message",loginError);
				model.put("initials",FormUtilities.getInitial());
				return new ModelAndView("login", model);
			}
		} 
		return new ModelAndView("error");
	}

	@RequestMapping(value ="/OutletLoginValidationSuccess", method = RequestMethod.GET)
	public ModelAndView outletLogin(HttpSession session,@ModelAttribute("command") RegistrationBean registrationBean,BindingResult result)
	{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int[] mode=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1 && mode[1]==4){
			model = new HashMap<String, Object>();
			if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==inactive && userSessionBean.getResourceBean().getRoleBean().getId()==outlet){
				model.put("Logger",  userSessionBean);
				return new ModelAndView("outLetInactiveLogin", model);
			}else if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==active&& userSessionBean.getResourceBean().getRoleBean().getId()==outlet){
				model.put("Logger",  userSessionBean);
				return new ModelAndView("outletLoginSucces", model);
			}else{
				model.put("Logger",  userSessionBean);
				model.put("message",loginError);
				model.put("initials",FormUtilities.getInitial());
				return new ModelAndView("login", model);
			}
		} 
		return new ModelAndView("error");
	}


	@RequestMapping(value ="/changePassword", 
			method = RequestMethod.GET)
	public ModelAndView changePassword(HttpSession session,@ModelAttribute("command") RegistrationBean registrationBean, BindingResult result)
	{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int[] mode=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			model = new HashMap<String, Object>();
			organizationModel=organizationService.getUser(userSessionBean.getId());
			if(organizationModel.getResourceModel().getAccountStatusModel().getId()==active){
				return new ModelAndView("activeUserPwdChange",model);	
			}
		}
		return new ModelAndView("error");
	}


	@RequestMapping(value ="/changeToNewPassword", 
			method = RequestMethod.POST)
	public ModelAndView changeToNewPassword(HttpSession session,@ModelAttribute("command") RegistrationBean registrationBean, BindingResult result)
	{
		initializeProperties();
		String returnMsg="";
		userSessionBean=SessionUtilities.giveMeSession(session);
		int[] mode=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		Integer i = 0;
		if(mode[0]==1){
			model = new HashMap<String, Object>();
			organizationModel =  new OrganizationModel();
			branchModel = new BranchModel();
			outletModel = new OutletModel();
		if(mode[1]==organization){
			organizationModel=organizationService.getUser(userSessionBean.getId());			
			if(organizationModel.getPassword().contentEquals(registrationBean.getPassword().trim())){
			organizationModel.setPassword(registrationBean.getNewPassWord());
			     i = organizationService.upadtePasswordById(organizationModel); 
			}
		}
		else if(mode[1]==branch){
			branchModel.setId(mode[2]);
			branchModel = branchService.getBranchbyId(branchModel);
			if(branchModel.getPassword().contentEquals(registrationBean.getPassword().trim())){
				branchModel.setPassword(registrationBean.getNewPassWord());
				i = branchService.upadtePasswordByIdOfBranch(branchModel);
			}
		}else if(mode[1]==outlet){
			outletModel.setId(mode[2]);
			outletModel = outletService.getOutlet(outletModel);
			if(outletModel.getPassword().contentEquals(registrationBean.getPassword().trim())){
				outletModel.setPassword(registrationBean.getNewPassWord());
				i = outletService.upadtePasswordByIdOfOutlet(outletModel);
			}
		}
		}
			if(i>0){
				returnMsg="Password Successfully Updated"; 
			}else{
				returnMsg="Enter the valid current password";
			}
			model.put("msg", returnMsg);
		return new ModelAndView("reOpenActiveUserPwdChange",model);
	}
	
	@RequestMapping(value ="/forgotPasswordSendMail",method = RequestMethod.POST)
	public @ResponseBody String forgotPasswordSendMail(HttpServletRequest request,
			@RequestParam("userName") String uName,
			@RequestParam("mail") String reciever,
			@RequestParam("type") String userType) {
		initializeProperties();
		
		   String result =" ";		
			try{
		
				if(userType.contentEquals("1")){
					organizationModel =  new OrganizationModel();
					organizationModel.setUserName(uName.trim());
					organizationModel=organizationService.checkAvailabilty(organizationModel);	
					if(organizationModel!=null && organizationModel.getResourceModel().getAddressModel().getEmail().contentEquals(reciever.trim())){
					SendMail.sendMail(reciever,organizationModel.getPassword(),organizationModel.getFirstName()+" "+organizationModel.getLastName());
					result = passwordSent;
					}
					else{
						result = wrongInform;
					}
					
				}
				else if(userType.contentEquals("2")){
					branchModel = new BranchModel();
					branchModel.setUserName(uName.trim());
					branchModel=branchService.checkBranchAvailability(branchModel);
					if(branchModel!=null && branchModel.getResourceModel().getAddressModel().getEmail().contentEquals(reciever.trim())){
					SendMail.sendMail(reciever,branchModel.getPassword(),branchModel.getFirstName()+" "+branchModel.getLastName());
					result = passwordSent;
					}
					else{
						result = wrongInform;
					}
				}else if(userType.contentEquals("3")){
					outletModel = new OutletModel();
					outletModel.setUserName(uName.trim());
					outletModel=outletService.checkOutletAvailability(outletModel);	
					if(outletModel!=null && outletModel.getResourceModel().getAddressModel().getEmail().contentEquals(reciever.trim())){
					SendMail.sendMail(reciever,outletModel.getPassword(),outletModel.getFirstName()+" "+outletModel.getLastName());
					result = passwordSent;
					}else{
						result = wrongInform;
					}
				}
								 
				
			}catch(Exception e){
				e.printStackTrace();
				
			}		
			
		return result;
}


	@RequestMapping(value = "/saveMenu",
			method = RequestMethod.POST)
	public @ResponseBody String createMenu(HttpSession session,
			@ModelAttribute("command") MenuBean menuBean,
			@RequestParam(value="menu") String menu,
			@RequestParam(value="flag") String flag)
					throws Exception {

		MenuModel menuModel = new MenuModel();
		menuModel.setChildMenuIds(menu);


		organizationService.createUserMenu(menuModel);
		return "amar";
	}

	@RequestMapping(value = "/loadMenu",
			method = RequestMethod.POST)
	public @ResponseBody List<ChildMenuModel> getChildMenu(HttpSession session,
			@ModelAttribute("command") ChildMenuModel childMenuModel,
			@RequestParam(value="flag") String flag)
					throws Exception {
		return null;
		
	}


	public static void getUserMenu(OrganizationModel organizationModel){
	
	}

	
}
