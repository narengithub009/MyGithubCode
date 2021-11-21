package com.relyits.rmbs.controller;


import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.relyits.rmbs.beans.registration.BranchBean;
import com.relyits.rmbs.beans.registration.DoctorBean;
import com.relyits.rmbs.beans.registration.OrganizationBean;
import com.relyits.rmbs.beans.registration.OutletBean;
import com.relyits.rmbs.beans.registration.RegistrationBean;
import com.relyits.rmbs.beans.registration.ResourceBean;
import com.relyits.rmbs.beans.resources.AddressBean;
import com.relyits.rmbs.beans.resources.CategoryBean;
import com.relyits.rmbs.beans.resources.RoleBean;
import com.relyits.rmbs.beans.resources.StatusBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.OrganizationBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.OutletBeanPreparation;
import com.relyits.rmbs.model.refference.AddressModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.model_preparation.registration.BranchModelPreparation;
import com.relyits.rmbs.model_preparation.registration.OrganizationModelPreparation;
import com.relyits.rmbs.model_preparation.registration.OutletModelPreparation;
import com.relyits.rmbs.model_preparation.resource.AddressModelPreparation;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.OrganizationService;
import com.relyits.rmbs.service.OutletService;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;
import com.relyits.rmbs.utilities.FormUtilities;
import com.relyits.rmbs.utilities.JsonResponse;
import com.relyits.rmbs.utilities.SessionUtilities;
import com.relyits.rmbs.validator.BranchRegistrationValidator;
import com.relyits.rmbs.validator.OutletRegistrationValidator;
import com.relyits.rmbs.validator.UserRegistrationValidator;

@Controller
public class ValidationController {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private OutletService outletService;
	
	@Autowired
	private OutletRegistrationValidator outletValidator;
	@Autowired
	private BranchRegistrationValidator branchRegistrationValidator;
	@Autowired
	private UserRegistrationValidator userRegistrationValidator;
	private static ResourceBean resourceBean = null;
	private static RoleBean roleBean = null;
	private static RoleBean creatorRoleBean = null;
	private static StatusBean accountStatusBean = null;
	private static StatusBean loginStatusBean = null;
	private static CategoryBean roleCategoryBean = null;
	private static CategoryBean creatorRoleCategoryBean = null;
	private static CategoryBean accountStatusCategoryBean = null;
	private static CategoryBean loginstatusCategoryBean = null;
	private static UserSessionBean userSessionBean = null;
	private static BranchBean branchBean = null;
	private static AddressBean addressBean = null;

	private static OrganizationModel organizationModel = null;
	private static BranchModel branchModel = null;
	private static OutletModel outletModel = null;
	static ResourceModel resourceModel = null;
	static AddressModel addressModel = null;
	static RoleModel roleModel = null;
	static RoleModel creatorRoleModel = null;
	static StatusModel accountStatusModel = null;
	static StatusModel loginStatusModel = null;
	static CategoryModel categoryModel = null;
	static CategoryModel accountStatusCategoryModel = null;
	static CategoryModel loginStatusCategoryModel = null;
	static CategoryModel roleCategoryModel = null;
	static CategoryModel creatorRoleCategoryModel = null;

	Map<String, String> properties= null;

	int admin, organization,branch,outlet,inactive,active,offline,online,user,account,login;
	
	String organizationError = null;
	String branchError = null;
	String outletError = null;
	String loginError = null;
	String outletSuccess = null;
	String updateSuccessMsg = null;
	String updateFailMsg = null;

	/******************************************************************************************************/
	/*********************************    Application Launching methods    ***********************************/
	/******************************************************************************************************/


	@Value("${role.admin}") String adminRole;
	@Value("${role.organization}") String organizationRole;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${status.inactive}") String inactiveStatus;
	@Value("${status.active}") String activeStatus;
	@Value("${status.offline}") String offlineStatus;
	@Value("${status.online}") String onlineStatus;
	@Value("${category.user}") String userCategory;
	@Value("${category.account}") String accountCategory;
	@Value("${category.login}") String loginCategory;
	@Value("${message.organizationRegistrationError}") String orgError;
	@Value("${message.branchRegistrationError}") String branchError1;
	@Value("${message.outLetRegistrationError}") String outletError1;
	@Value("${message.outLetRegistrationSuccess}") String outletSuccess1;
	@Value("${message.loginerror}") String loginError1;
	@Value("${message.updateSuccess}") String updateSuccess;
	@Value("${message.updateFail}") String updateFail;
	
	public Map<String, String> initializeProperties(){

		admin=Integer.parseInt(adminRole);
		organization = Integer.parseInt(organizationRole);
		branch = Integer.parseInt(branchRole);
		outlet = Integer.parseInt(outletRole);
		inactive = Integer.parseInt(inactiveStatus);
		active = Integer.parseInt(activeStatus);
		offline = Integer.parseInt(offlineStatus);
		online = Integer.parseInt(onlineStatus);
		user = Integer.parseInt(userCategory);
		account = Integer.parseInt(accountCategory);
		login = Integer.parseInt(loginCategory);
		organizationError = orgError;
		branchError = branchError1;
		outletError = outletError1;
		loginError = loginError1;
		outletSuccess = outletSuccess1;
		updateSuccessMsg = updateSuccess;
		updateFailMsg = updateFail;
		return properties;
	}
	Map<String, Object> model = null;

	//Application starting here
	@RequestMapping(value = "/index",  method = RequestMethod.GET)
	public ModelAndView welcome() {

		
		final Logger logger = Logger.getLogger(ValidationController.class);
	
		logger.debug("Calling ValidationController start....");
		logger.debug("Calling ValidationController end....");
		
		
		initializeProperties();
		
		return new ModelAndView("redirect:/home.html");
	}




	//launching home page  
	@RequestMapping(value = "/home",  method = RequestMethod.GET)
	public ModelAndView Home(@ModelAttribute("command")  RegistrationBean regitrationBean,BindingResult result) 
	{
		model = new HashMap<String, Object>();
		model.put("initials",FormUtilities.getInitial());//getting initials from get initial method to registration form initial drop down
		return new ModelAndView("login", model);
	}

	/*******************************************************************************************************/
	/********************** Registration Availability Validation Methods  ***********************************/
	/**
	 * @throws IOException *****************************************************************************************************/

	@RequestMapping(value ="/validateRegistration",method = RequestMethod.POST)
	public ModelAndView orgnizationRegistrationValidation(HttpSession session,@ModelAttribute("command") RegistrationBean regitrastionBean,BindingResult result,HttpServletRequest request) throws ParseException, IOException 
	{
		
		initializeProperties();
		userSessionBean = (UserSessionBean) session.getAttribute("user");
		organizationModel = new OrganizationModel();
		FormUtilities.generateImagePath(request, regitrastionBean, session);

		organizationModel = OrganizationModelPreparation.prepareOrganizationModel(regitrastionBean);
		OrganizationModel organizationModel1=organizationService.checkAvailabilty(organizationModel);

		if(organizationModel1 == null){
			branchModel = new BranchModel();
			branchModel.setUserName(regitrastionBean.getUserName());
			BranchModel branchModel1 = branchService.checkBranchAvailability(branchModel);
			if(branchModel1==null){
				outletModel = new OutletModel();
				outletModel.setUserName(regitrastionBean.getUserName());
				OutletModel outletModel1=outletService.checkOutletAvailability(outletModel);
				if(outletModel1==null){
					
                     resourceModel = new ResourceModel();
                     creatorRoleModel = new RoleModel();
                     creatorRoleCategoryModel = new CategoryModel();
                     roleModel = new RoleModel();
                     roleCategoryModel = new CategoryModel();
                     accountStatusModel = new StatusModel();
                     accountStatusCategoryModel = new CategoryModel();
                     loginStatusModel = new StatusModel();
                     loginStatusCategoryModel = new CategoryModel();
                     addressModel = new AddressModel();
                                         
                     roleCategoryModel.setId(user);
                     roleModel.setId(organization);
                     roleModel.setCategoryModel(roleCategoryModel);
                     resourceModel.setRoleModel(roleModel);
                     
                     creatorRoleCategoryModel.setId(user);
                     creatorRoleModel.setId(admin);                     
                     creatorRoleModel.setCategoryModel(creatorRoleCategoryModel);
                     resourceModel.setCreatorRoleModel(creatorRoleModel);
                                         
                     accountStatusCategoryModel.setId(account);
                     accountStatusModel.setId(inactive);
                     accountStatusModel.setCategoryModel(accountStatusCategoryModel);
                     resourceModel.setAccountStatusModel(accountStatusModel);
                                         
                     loginStatusCategoryModel.setId(login);
                     loginStatusModel.setId(offline);
                     loginStatusModel.setCategoryModel(loginStatusCategoryModel);
                     resourceModel.setLoginStatusModel(loginStatusModel);
                     
                    
                     
                     resourceModel.setAddressModel(AddressModelPreparation.prepareAddressModel(regitrastionBean.getAddressBean()));
                     
                     organizationModel.setResourceModel(resourceModel);
                     organizationModel.setNoOfBranches(0);
                     
                     
                    
					session.setAttribute("registrationModel", organizationModel);
					return new ModelAndView("redirect:/OraganizationRegistrationValidationSuccess.html");
				}
			}
		}
		model = new HashMap<String, Object>();
		model.put("message",organizationError);
		model.put("initials",FormUtilities.getInitial());
		return new ModelAndView("login", model);
	}

	@RequestMapping(value ="/validateBranchRegistration",method = RequestMethod.POST)
	public ModelAndView branchRegistrationValidation(/*HttpServletRequest request,*/HttpSession session,@ModelAttribute("command") BranchBean branchBean,BindingResult result) throws ParseException 
	{
		branchRegistrationValidator.validate(branchBean, result);
		if(!result.hasErrors()){
		initializeProperties();
		userSessionBean = (UserSessionBean) session.getAttribute("user");
		organizationModel=new OrganizationModel();
		organizationModel.setUserName(branchBean.getUserName());
		OrganizationModel organizationModel1=organizationService.checkAvailabilty(organizationModel);

		if(organizationModel1==null){
			branchModel = new BranchModel();
			branchModel.setUserName(branchBean.getUserName());
			BranchModel branchModel1 = branchService.checkBranchAvailability(branchModel);
			if(branchModel1==null){
				outletModel = new OutletModel();
				outletModel.setUserName(branchBean.getUserName());
				OutletModel outletModel1=outletService.checkOutletAvailability(outletModel);
				if(outletModel1==null){
					accountStatusBean = new StatusBean();
					accountStatusCategoryBean = new CategoryBean();
					loginStatusBean = new StatusBean();
					loginstatusCategoryBean = new CategoryBean();
					roleBean = new RoleBean();
					creatorRoleBean = new RoleBean();
					resourceBean = new ResourceBean();
					roleCategoryBean = new CategoryBean();
					creatorRoleCategoryBean = new CategoryBean();
					

					accountStatusBean.setId(inactive);
					accountStatusCategoryBean.setId(account);
					accountStatusBean.setCategoryBean(accountStatusCategoryBean);

					loginStatusBean.setId(offline);
					loginstatusCategoryBean.setId(login);
					loginStatusBean.setCategoryBean(loginstatusCategoryBean);

					roleCategoryBean.setId(user);
					roleBean.setId(branch);
					roleBean.setCategoryBean(roleCategoryBean);

					creatorRoleCategoryBean.setId(userSessionBean.getResourceBean().getRoleBean().getCategoryBean().getId());
					creatorRoleBean.setId(userSessionBean.getResourceBean().getRoleBean().getId());
					creatorRoleBean.setCategoryBean(creatorRoleCategoryBean);

					resourceBean=branchBean.getResourceBean();
					resourceBean.setCreatedBy(userSessionBean.getId());
					resourceBean.setAccountStatusBean(accountStatusBean);
					resourceBean.setLoginStatusBean(loginStatusBean);
					resourceBean.setRoleBean(roleBean);
					resourceBean.setCreatorRoleBean(creatorRoleBean);

					branchBean.setDaysCount(0);
					branchBean.setNoOfOutlets(0);
					branchBean.setResourceBean(resourceBean);

					branchModel = BranchModelPreparation.prepareBranchModel(branchBean);
					organizationModel= new OrganizationModel(); 
					organizationModel.setId(userSessionBean.getId());
					branchModel.setOrganizationModel(organizationModel);

					session.setAttribute("branchModel", branchModel);
					return new ModelAndView("redirect:/BranchRegistrationValidationSuccess.html");
				}
			}
		}else{
		model = new HashMap<String, Object>();
		model.put("message",branchError);
		model.put("initials",FormUtilities.getInitial());
		return new ModelAndView("reOpenBranchRegisterForm", model);
		}}else{
			session.setAttribute("error", result);
			return new ModelAndView("redirect:/userValidate.html");
		}
		return null;



	}
	@RequestMapping(value = "/userValidate" , method=RequestMethod.GET)
	public @ResponseBody JsonResponse formValidationErrors(HttpSession session,
			@ModelAttribute("command") DoctorBean doctorBean,BindingResult result){
		model=new HashMap<String,Object>();
		result = (BindingResult) session.getAttribute("error");
		String msg = (String) session.getAttribute("msg");
		JsonResponse res = new JsonResponse();
		if(result.hasErrors()){
			
			res.setFlag("FAIL");
			res.setResult(result.getAllErrors());
		}else{
			res.setFlag("Success");
			res.setResult(msg);
		}
		
		return res;		
	}
	@RequestMapping(value ="/validateOutletRegistration",method = RequestMethod.POST)
	public ModelAndView outletRegistrationValidation(/*HttpServletRequest request,*/HttpSession session,@ModelAttribute("command") OutletBean outletBean,BindingResult result) throws ParseException 
	{
		outletValidator.validate(outletBean, result);

		if(!result.hasErrors()){
		initializeProperties();
		userSessionBean = (UserSessionBean) session.getAttribute("user");
		organizationModel=new OrganizationModel();
		organizationModel.setUserName(outletBean.getUserName());
		OrganizationModel organizationModel1=organizationService.checkAvailabilty(organizationModel);

		if(organizationModel1==null){
			branchModel = new BranchModel();
			branchModel.setUserName(outletBean.getUserName());
			BranchModel branchModel1 = branchService.checkBranchAvailability(branchModel);
			if(branchModel1==null){
				outletModel = new OutletModel();
				outletModel.setUserName(outletBean.getUserName());
				OutletModel outletModel1=outletService.checkOutletAvailability(outletModel);
				if(outletModel1==null){

					accountStatusBean = new StatusBean();
					accountStatusCategoryBean = new CategoryBean();
					loginStatusBean = new StatusBean();
					loginstatusCategoryBean = new CategoryBean();
					roleBean = new RoleBean();
					creatorRoleBean = new RoleBean();
					resourceBean = new ResourceBean();
					roleCategoryBean = new CategoryBean();
					creatorRoleCategoryBean = new CategoryBean();
					branchBean = new BranchBean();
					addressBean = new AddressBean();

					branchBean.setId(userSessionBean.getId());
					accountStatusBean.setId(inactive);
					accountStatusCategoryBean.setId(account);
					accountStatusBean.setCategoryBean(accountStatusCategoryBean);

					loginStatusBean.setId(offline);
					loginstatusCategoryBean.setId(login);
					loginStatusBean.setCategoryBean(loginstatusCategoryBean);

					roleCategoryBean.setId(user);
					roleBean.setId(outlet);
					roleBean.setCategoryBean(roleCategoryBean);

					creatorRoleCategoryBean.setId(userSessionBean.getResourceBean().getRoleBean().getCategoryBean().getId());
					creatorRoleBean.setId(userSessionBean.getResourceBean().getRoleBean().getId());
					creatorRoleBean.setCategoryBean(creatorRoleCategoryBean);


					addressBean.setAddress(userSessionBean.getResourceBean().getAddressBean().getAddress());
					addressBean.setEmail(outletBean.getResourceBean().getAddressBean().getEmail());
					addressBean.setMobile(outletBean.getResourceBean().getAddressBean().getMobile());

					resourceBean=outletBean.getResourceBean();
					resourceBean.setAddressBean(addressBean);
					resourceBean.setCreatedBy(userSessionBean.getId());
					resourceBean.setAccountStatusBean(accountStatusBean);
					resourceBean.setLoginStatusBean(loginStatusBean);
					resourceBean.setRoleBean(roleBean);
					resourceBean.setCreatorRoleBean(creatorRoleBean);

					outletBean.setDaysCount(0);
					outletBean.setResourceBean(resourceBean);
					outletBean.setBranchBean(branchBean);
					
					outletModel = OutletModelPreparation.prepareOutletModel(outletBean);


					session.setAttribute("OutletModel", outletModel);
					return new ModelAndView("redirect:/OutletRegistrationValidationSuccess.html");
				}	
			}
		}
		else{
		model = new HashMap<String, Object>();
		model.put("message",outletError);
		model.put("initials",FormUtilities.getInitial());
		return new ModelAndView("openOutletRegisterForm", model);
		}
		}else{
			session.setAttribute("error", result);
			return new ModelAndView("redirect:/userValidate.html");
		}
		return null;

	}


	/******************************************************************************************************/
	/*********************************        Login Validations          **********************************/
	/******************************************************************************************************/

	@RequestMapping(value ="/validateLogin",method = RequestMethod.POST)
	public ModelAndView LoginValidation(HttpSession session,@ModelAttribute("command") RegistrationBean registrationBean,BindingResult result) 
	{

		initializeProperties();
		organizationModel=new OrganizationModel();
		organizationModel.setUserName(registrationBean.getUserName());
		organizationModel.setPassword(registrationBean.getPassword());
		OrganizationModel organizationModel1=organizationService.loginValidate(organizationModel);
		Map<String, Object> model = new HashMap<String, Object>();

		if(organizationModel1!=null){
			model.put("logger", organizationModel);
			boolean userSession=SessionUtilities.createOrganizationSession(organizationModel1, session);
			if(userSession){
				UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
				if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==active&& userSessionBean.getResourceBean().getRoleBean().getId()==admin){

					return new ModelAndView("redirect:/AdminLoginValidationSuccess.html");

				}else if(userSessionBean.getResourceBean().getRoleBean().getId()==organization){

					return new ModelAndView("redirect:/OrganizationLoginValidationSuccess.html");

				}else{
					model.put("loginerrormessage",loginError);
					model.put("initials",FormUtilities.getInitial());
					return new ModelAndView("login", model);
				}
			}

		}else{
			branchModel = new BranchModel();
			branchModel.setUserName(registrationBean.getUserName());
			branchModel.setPassword(registrationBean.getPassword());
			BranchModel branchModel1 = branchService.loginValidate(branchModel);
			if(branchModel1!=null){

				boolean userSession=SessionUtilities.createBranchSession(branchModel1, session);
				if(userSession){
					return new ModelAndView("redirect:/BranchLoginValidationSuccess.html");
				}else{
					model.put("loginerrormessage",loginError);
					model.put("initials",FormUtilities.getInitial());
					return new ModelAndView("login", model);
				}
			}else{
				outletModel = new OutletModel();
				outletModel.setUserName(registrationBean.getUserName());
				outletModel.setPassword(registrationBean.getPassword());
				OutletModel outletModel1 = outletService.loginValidate(outletModel);
				if(outletModel1!=null){
					boolean userSession=SessionUtilities.createOutletSession(outletModel1, session);
					if(userSession){
						return new ModelAndView("redirect:/OutletLoginValidationSuccess.html");
					}else{
						model.put("loginerrormessage",loginError);
						model.put("initials",FormUtilities.getInitial());
						return new ModelAndView("login", model);
					}

				}

			}
		}
		model.put("loginerrormessage",loginError);
		model.put("initials",FormUtilities.getInitial());
		return new ModelAndView("login", model);

	}

	//////////////////////////////////// /////////////////////////////////////////////////////
	//////////////////////////    Open Dash Board   //////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value ="/userHome",method = RequestMethod.GET)
	public ModelAndView openDashBoard(HttpSession session,@ModelAttribute("command") RegistrationBean registrationBean,BindingResult result)
	{
		initializeProperties();
		model = new HashMap<String, Object>();

		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		if(userSessionBean!=null){
			model.put("logger", userSessionBean);
			if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==active&& userSessionBean.getResourceBean().getRoleBean().getId()==admin){
				return new ModelAndView("redirect:/AdminLoginValidationSuccess.html");
			}else if(userSessionBean.getResourceBean().getRoleBean().getId()==organization){
				return new ModelAndView("redirect:/OrganizationLoginValidationSuccess.html");
			}else if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==active&& userSessionBean.getResourceBean().getRoleBean().getId()==branch){
				return new ModelAndView("redirect:/BranchLoginValidationSuccess.html");
			}else if(userSessionBean.getResourceBean().getAccountStatusBean().getId()==active&& userSessionBean.getResourceBean().getRoleBean().getId()==outlet){
				return new ModelAndView("redirect:/OutletLoginValidationSuccess.html");
			}else{
	 		model.put("loginerrormessage",loginError);
	 		model.put("initials",FormUtilities.getInitial());
	 		return new ModelAndView("login", model);
	 	}
		}else{
			model.put("loginerrormessage",loginError);
			model.put("initials",FormUtilities.getInitial());
			return new ModelAndView("login", model);
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////                 Logout          //////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////	

	@RequestMapping(value ="/logout",
			method = RequestMethod.GET)
	public ModelAndView doLogout(HttpSession session,
			@ModelAttribute("command") 
	RegistrationBean registrationBean, 
	BindingResult result)
	{
		session.invalidate();
		return new ModelAndView("redirect:/home.html");
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////  List Validations  /////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////

	@RequestMapping(value ="/getUserList",
			method = RequestMethod.GET)
	public ModelAndView generateUserListByRole(HttpSession session,
			@ModelAttribute("command") 
	RegistrationBean registrationBean, 
	BindingResult result)
	{
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		if(userSessionBean.getResourceBean().getRoleBean().getId()==admin){
			return new ModelAndView("redirect:/organizationsList.html");
		}else if(userSessionBean.getResourceBean().getRoleBean().getId()==organization){
			return new ModelAndView("redirect:/branchesList.html");
		}else if(userSessionBean.getResourceBean().getRoleBean().getId()==branch){
			return new ModelAndView("redirect:/outletsList.html");
		}
		return new ModelAndView("error");

	}

	@RequestMapping(value="/profileEditFormOfOrganization" ,
			method=RequestMethod.GET)
	public ModelAndView profileEditFormOfOrganization( //@RequestParam(value = "uid") String id,
			HttpSession session,
			@ModelAttribute("command") OrganizationBean organizationBean) throws ParseException
	{

		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			try {
				model = new HashMap<String, Object>();		
				organizationModel = new OrganizationModel();
				organizationModel.setId(mode[2]);
				organizationModel = organizationService.getOrganizationbyId(organizationModel);
			} catch (Exception e) {

				e.printStackTrace();
				
			}
			OrganizationBean organizationBean1 = OrganizationBeanPreparation.prepareOrganizationBean(organizationModel);					
			organizationBean1.setDob(DateAndTimeUtilities.parseSqlDateToString(DateAndTimeUtilities.parseStringDateToSqlDateFormat(organizationBean1.getDob())));
			model.put("org", organizationBean1);
			model.put("initials",FormUtilities.getInitial());
			return new ModelAndView("openRegistrationForm", model);
		}else{
			return new ModelAndView("error");
		}

	}
	@RequestMapping(value ="/updateOrganization",method = RequestMethod.POST)
	public ModelAndView updateOrganization(HttpSession session,@ModelAttribute("command") OrganizationBean organizationBean,BindingResult result,HttpServletRequest request) throws ParseException, IOException 
	{
		model = new HashMap<String, Object>();
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			organizationBean.getId();
			
			if(organizationBean!=null){
				if(organizationBean.getId()!=null){
					
					OrganizationModel organizationModel = new OrganizationModel();
					organizationModel.setId(organizationBean.getId());
					organizationModel = organizationService.getOrganizationbyId(organizationModel);					
					FormUtilities.generateImagePath(request, organizationBean, session);
					OrganizationBean oldOrganizationBean = OrganizationBeanPreparation.prepareOrganizationBean(organizationModel);
					
					resourceBean= oldOrganizationBean.getResourceBean();              			
					
					addressBean = organizationBean.getResourceBean().getAddressBean();//new address
					addressBean.setId(resourceBean.getAddressBean().getId()); // assigning old address id to the new address

					accountStatusBean = resourceBean.getAccountStatusBean();

					loginStatusBean = resourceBean.getLoginStatusBean();

					roleBean = resourceBean.getRoleBean();

					creatorRoleBean = resourceBean.getCreatorRoleBean();


					resourceBean.setAddressBean(addressBean);
					resourceBean.setAccountStatusBean(accountStatusBean);
					resourceBean.setLoginStatusBean(loginStatusBean);
					resourceBean.setRoleBean(roleBean);
					resourceBean.setCreatorRoleBean(creatorRoleBean);
					organizationBean.setPassword(organizationModel.getPassword());
					organizationBean.setResourceBean(resourceBean);
					organizationBean.setNoOfBranches(0);
					
					if(organizationBean.getInitial().contentEquals("Select")){
						organizationBean.setInitial(oldOrganizationBean.getInitial());
					}
					
					if(organizationBean.getImagePath()==null){
						organizationBean.setImagePath(oldOrganizationBean.getImagePath());
					}

					organizationModel = OrganizationModelPreparation.prepareOrganizationModelByOrgBean(organizationBean);

					if(organizationModel!=null){
						boolean returnResult = organizationService.updateOraganization(organizationModel);

						if(returnResult){  							
								model.put("message", updateSuccessMsg);
								model.put("initials",FormUtilities.getInitial());
								return new ModelAndView("updateSuccessMessage",model); 
			        	}else{
			        		model.put("message", updateFailMsg);			 
							return new ModelAndView("redirect:/profileEditFormOfOrganization.html"); 
			        	}
			     	}
		       }
		
		    }

		 
		}
		return new ModelAndView("error");
	}
	

	@RequestMapping(value="/profileEditFormOfBranch" ,
			method=RequestMethod.GET)
	public ModelAndView profileEditFormOfBranch( 
			HttpSession session,
			@ModelAttribute("command") BranchBean branchBean) throws ParseException
	{

		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			try {
				model = new HashMap<String, Object>();		
				branchModel = new BranchModel();
				branchModel.setId(mode[2]);
				branchModel = branchService.getBranchbyId(branchModel);				
			} catch (Exception e) {

				e.printStackTrace();
				
			}
			BranchBean branchBean1 = BranchBeanPreparation.prepareBranchBean(branchModel);
			branchBean1.setDob(DateAndTimeUtilities.parseSqlDateToString(DateAndTimeUtilities.parseStringDateToSqlDateFormat(branchBean1.getDob())));
			model.put("branch", branchBean1);
			model.put("initials",FormUtilities.getInitial());
			return new ModelAndView("openBranchEditForm", model);
		}else{
			return new ModelAndView("error");
		}

	}

	@RequestMapping(value ="/updateBranch",method = RequestMethod.POST)
	public ModelAndView updateBranch(HttpSession session,@ModelAttribute("command") BranchBean branchBean,BindingResult result,HttpServletRequest request) throws ParseException, IOException 
	{
		model = new HashMap<String, Object>();
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			branchBean.getId();
			
			if(branchBean!=null){
				if(branchBean.getId()!=null){
					BranchModel branchModel = new BranchModel();					
					branchModel.setId(branchBean.getId());
					branchModel = branchService.getBranchbyId(branchModel);		
					
					BranchBean oldBranchBean = BranchBeanPreparation.prepareBranchBean(branchModel);
				
					resourceBean= oldBranchBean.getResourceBean();              			
					
					addressBean = branchBean.getResourceBean().getAddressBean();//new address
					addressBean.setId(resourceBean.getAddressBean().getId()); // assigning old address id to the new address

					accountStatusBean = resourceBean.getAccountStatusBean();

					loginStatusBean = resourceBean.getLoginStatusBean();

					roleBean = resourceBean.getRoleBean();

					creatorRoleBean = resourceBean.getCreatorRoleBean();


					resourceBean.setAddressBean(addressBean);
					resourceBean.setAccountStatusBean(accountStatusBean);
					resourceBean.setLoginStatusBean(loginStatusBean);
					resourceBean.setRoleBean(roleBean);
					resourceBean.setCreatorRoleBean(creatorRoleBean);
					branchBean.setPassword(organizationModel.getPassword());
					branchBean.setResourceBean(resourceBean);
					branchBean.setNoOfOutlets(0);
					branchBean.setDaysCount(0);
					branchBean.setOrganizationBean(oldBranchBean.getOrganizationBean());
					
					if(branchBean.getInitial().contentEquals("Select")){
						branchBean.setInitial(oldBranchBean.getInitial());
					}
					
					branchModel = BranchModelPreparation.prepareBranchModelForEdit(branchBean);					

					if(branchModel!=null){
						boolean returnResult = branchService.updateBranch(branchModel);
						if(returnResult){  							
								model.put("message", updateSuccessMsg);
								model.put("initials",FormUtilities.getInitial());
								return new ModelAndView("updateSuccessMessage",model); 
			        	}else{
			        		model.put("message", updateFailMsg);			 
							return new ModelAndView("redirect:/profileEditFormOfBranch.html"); 
			        	}
			     	}
		       }
		
		    }

		 
		}
		return new ModelAndView("error");
	}
	@RequestMapping(value="/profileEditFormOfOutlet" ,
			method=RequestMethod.GET)
	public ModelAndView profileEditFormOfOutlet( 
			HttpSession session,
			@ModelAttribute("command") OutletBean outletBean) throws ParseException
	{

		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			try {
				model = new HashMap<String, Object>();		
				outletModel = new OutletModel();				
				outletModel.setId(mode[2]);
				outletModel = outletService.getOutlet(outletModel);		
			} catch (Exception e) {

				e.printStackTrace();
				
			}
			OutletBean outletBean1 = OutletBeanPreparation.prepareOutLetBean(outletModel); 
			outletBean1.setDob(DateAndTimeUtilities.parseSqlDateToString(DateAndTimeUtilities.parseStringDateToSqlDateFormat(outletBean1.getDob())));
			model.put("outlet",outletBean1);
			
			model.put("initials",FormUtilities.getInitial());
			return new ModelAndView("openOutletEditForm", model);
		}else{
			return new ModelAndView("error");
		}

	}
	@RequestMapping(value ="/updateOutlet",method = RequestMethod.POST)
	public ModelAndView updateOutlet(HttpSession session,@ModelAttribute("command") OutletBean outletBean,BindingResult result,HttpServletRequest request) throws ParseException, IOException 
	{
		model = new HashMap<String, Object>();
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){
			outletBean.getId();
			
			if(outletBean!=null){
				if(outletBean.getId()!=null){
					OutletModel outletModel= new OutletModel();					
					outletModel.setId(outletBean.getId());
					outletModel = outletService.getOutlet(outletModel);		
					OutletBean oldOutletBean = OutletBeanPreparation.prepareOutLetBean(outletModel);
					
					resourceBean= oldOutletBean.getResourceBean();              			
					addressBean = outletBean.getResourceBean().getAddressBean();//new address
					addressBean.setAddress(oldOutletBean.getBranchBean().getResourceBean().getAddressBean().getAddress());
					addressBean.setId(resourceBean.getAddressBean().getId()); // assigning old address id to the new address

					accountStatusBean = resourceBean.getAccountStatusBean();

					loginStatusBean = resourceBean.getLoginStatusBean();

					roleBean = resourceBean.getRoleBean();

					creatorRoleBean = resourceBean.getCreatorRoleBean();


					resourceBean.setAddressBean(addressBean);
					resourceBean.setAccountStatusBean(accountStatusBean);
					resourceBean.setLoginStatusBean(loginStatusBean);
					resourceBean.setRoleBean(roleBean);
					resourceBean.setCreatorRoleBean(creatorRoleBean);
					outletBean.setPassword(organizationModel.getPassword());
					outletBean.setResourceBean(resourceBean);
					outletBean.setDaysCount(0);
					
					outletBean.setBranchBean(oldOutletBean.getBranchBean());
					
					if(outletBean.getInitial().contentEquals("Select")){
						outletBean.setInitial(oldOutletBean.getInitial());
					}
					outletModel = OutletModelPreparation.prepareOutletModelForEdit(outletBean);								

					if(outletModel!=null){
						boolean returnResult = outletService.updateOutlet(outletModel);
						if(returnResult){  							
								model.put("message", updateSuccessMsg);
								model.put("initials",FormUtilities.getInitial());
								return new ModelAndView("updateSuccessMessage",model); 
			        	}else{
			        		model.put("message", updateFailMsg);			 
							return new ModelAndView("redirect:/profileEditFormOfOutlet.html"); 
			        	}
			     	}
		       }
		
		    }

		 
		}
		return new ModelAndView("error");
	}

}
