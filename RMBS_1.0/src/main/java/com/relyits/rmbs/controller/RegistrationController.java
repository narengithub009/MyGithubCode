package com.relyits.rmbs.controller;

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

import com.relyits.rmbs.beans.registration.BranchBean;
import com.relyits.rmbs.beans.registration.OutletBean;
import com.relyits.rmbs.beans.registration.RegistrationBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.model.refference.AddressModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.OrganizationService;
import com.relyits.rmbs.service.OutletService;
import com.relyits.rmbs.utilities.FormUtilities;
import com.relyits.rmbs.utilities.SessionUtilities;

@Controller
public class RegistrationController {

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private BranchService branchService;

	@Autowired
	private OutletService outletService;	

	private static UserSessionBean userSessionBean = null;

	static ResourceModel resourceModel = null;
	static AddressModel addressModel = null;
	static RoleModel roleModel = null;
	static RoleModel creatorRoleModel = null;
	static StatusModel accountStatusModel = null;
	static StatusModel loginStatusModel = null;
	static CategoryModel categoryModel = null;


	Map<String, String> properties= null;

	String organizationError = null;
	String branchError = null;
	String outletError = null;
	String loginError = null;
	String organizationSuccess = null;
	String branchSuccess= null;
	String outletSuccess = null;

	@Value("${message.organizationRegistrationError}") String orgError;
	@Value("${message.branchRegistrationError}") String branchError1;
	@Value("${message.outLetRegistrationError}") String outletError1;
	@Value("${message.loginerror}") String loginError1;
	@Value("${message.organizationRegistrationSuccess}") String orgSuccess;
	@Value("${message.branchRegistrationSuccess}") String branchSuccess1;
	@Value("${message.outLetRegistrationSuccess}") String outletSuccess1;


	public Map<String, String> initializeProperties(){

		organizationError = orgError;
		branchError = branchError1;
		outletError = outletError1;
		loginError = loginError1;
		organizationSuccess = orgSuccess;
		branchSuccess = branchSuccess1;
		outletSuccess = outletSuccess1;

		return properties;
	}


	@RequestMapping(value ="/OraganizationRegistrationValidationSuccess", method = RequestMethod.GET)
	public ModelAndView registerUser(HttpSession session,@ModelAttribute("command") RegistrationBean registrationBean, BindingResult result)
	{
		initializeProperties();  
		
		OrganizationModel organizationModel=(OrganizationModel) session.getAttribute("registrationModel");
		session.removeAttribute("registrationModel");
		Map<String, Object> model = new HashMap<String, Object>();
		if(organizationModel!=null){

			boolean retutnResult=organizationService.createOraganization(organizationModel);
			if(retutnResult){
				model.put("message",organizationSuccess);
				model.put("initials",FormUtilities.getInitial());
				return new ModelAndView("login", model);
			}
		}	
			
				model.put("message",organizationError);
				model.put("initials",FormUtilities.getInitial());
				return new ModelAndView("login", model);	
			
		}


	@RequestMapping(value ="/openBranchFrom1", 
			method = RequestMethod.GET)
	public ModelAndView openBranch(HttpSession session,
			@ModelAttribute("command") 
	BranchBean branchBean, 
	BindingResult result)
	{

	     userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("createdby",userSessionBean.getId());
			model.put("shopname",mode[2]);
			model.put("initials",FormUtilities.getInitial());

			return new ModelAndView("openBranchRegisterForm",model);
		}else{
			return new ModelAndView("error");
		}



	}

	@RequestMapping(value ="/BranchRegistrationValidationSuccess", method = RequestMethod.GET)
	public ModelAndView registerBranch(/*HttpServletRequest request,*/HttpSession session,@ModelAttribute("command") BranchBean branchBean, BindingResult result)
	{
		initializeProperties();
		
		BranchModel branchModel=(BranchModel) session.getAttribute("branchModel");
		session.removeAttribute("branchModel");
		Map<String, Object> model = new HashMap<String, Object>();
		if(branchModel!=null){

			boolean retutnResult=branchService.createBranch(branchModel);
			if(retutnResult){
				session.setAttribute("error", result);
				session.setAttribute("msg", branchSuccess);
				return new ModelAndView("redirect:/userValidate.html");			}

		}	
		model.put("message",branchError);
		model.put("initials",FormUtilities.getInitial());
		return new ModelAndView("reOpenBranchRegisterForm", model);

	}
	
	@RequestMapping(value ="/openOutletForm", 
			method = RequestMethod.GET)
	public ModelAndView openOutlet(HttpSession session,@ModelAttribute("command") OutletBean outletBean,BindingResult result)
	{
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("initials",FormUtilities.getInitial());
			
			return new ModelAndView("openOutletRegisterForm",model);
		}else{
			return new ModelAndView("error");
		}



	}

	@RequestMapping(value ="/OutletRegistrationValidationSuccess", method = RequestMethod.GET)
	public ModelAndView registerOutlet(HttpSession session,@ModelAttribute("command") OutletBean outletBean, BindingResult result)
	{
		initializeProperties();

		OutletModel outletModel=(OutletModel) session.getAttribute("OutletModel");
		session.removeAttribute("OutletModel");
		Map<String, Object> model = new HashMap<String, Object>();
		if(outletModel!=null){

			boolean retutnResult=outletService.createOutlet(outletModel);
			if(retutnResult){
				session.setAttribute("msg", outletSuccess);
				session.setAttribute("error",result);
				return new ModelAndView("redirect:/userValidate.html");
			}

		}	
		model.put("message",outletError);
		model.put("initials",FormUtilities.getInitial());
		return new ModelAndView("openOutletRegisterForm", model);

	}

}


