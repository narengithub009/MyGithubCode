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
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.relyits.rmbs.beans.registration.AgencyBean;
import com.relyits.rmbs.beans.registration.ResourceBean;
import com.relyits.rmbs.beans.resources.AddressBean;
import com.relyits.rmbs.beans.resources.CategoryBean;
import com.relyits.rmbs.beans.resources.RoleBean;
import com.relyits.rmbs.beans.resources.StatusBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.registration.AgencyBeanPreparation;
import com.relyits.rmbs.model.refference.AddressModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.model_preparation.registration.AgencyModelPreparation;
import com.relyits.rmbs.service.AgencyService;
import com.relyits.rmbs.service.PageNavigator;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;
import com.relyits.rmbs.utilities.SessionUtilities;
import com.relyits.rmbs.validator.AgencyRegistrationValidator;

@Controller
public class AgencyController {

	@Autowired
	private AgencyService agencyService;
	
	@Autowired
    private PageNavigator pageNavigator;

	@Autowired
	private AgencyRegistrationValidator agencyRegistrationValidator;

	private static AgencyModel agencyModel = null;
	private static ResourceBean resourceBean = null;
	private static AddressBean addressBean = null; 
	private static RoleBean roleBean = null;
	private static RoleBean creatorRoleBean = null;
	private static StatusBean accountStatusBean = null;
	private static StatusBean loginStatusBean = null;
	private static CategoryBean roleCategoryBean = null;
	private static CategoryBean creatorRoleCategoryBean = null;
	private static CategoryBean accountStatusCategoryBean = null;
	private static CategoryBean loginStatusCategoryBean = null;

	static ResourceModel resourceModel = null;
	static AddressModel addressModel = null;
	static RoleModel roleModel = null;
	static RoleModel creatorRoleModel = null;
	static StatusModel accountStatusModel = null;
	static StatusModel loginStatusModel = null;
	static CategoryModel categoryModel = null;
	
	private Long count = null;
	private List<AgencyModel> agencies = null;

	Map<String, Object> model = null;

	Map<String, String> properties= null;

	int admin,agency, disable, enable, delete, offline,inactive,active,account,login,user;	
	String agencySuccess = null;

	@Value("${role.admin}") String adminRole;
	@Value("${role.agency}") String agencyRole;
	@Value("${status.disable}") String disableStatus;
	@Value("${status.enable}") String enableStatus;
	@Value("${status.delete}") String deleteStatus;
	@Value("${status.offline}") String offlineStatus;
	@Value("${status.inactive}") String inactiveStatus;
	@Value("${status.active}") String activeStatus;	
	@Value("${category.account}") String accountCategory;
	@Value("${category.login}") String loginCategory;
	@Value("${category.user}") String userCategory;
	@Value("${message.registerAgencySuccess}") String agencySuccess1;
	@Value("${app.page.size.default}") private int pageSize;
    @Value("${app.page.nav.trail}") private int pageNavTrail;


	public Map<String, String> initializeProperties(){

		admin=Integer.parseInt(adminRole);
		agency = Integer.parseInt(agencyRole);
		enable = Integer.parseInt(enableStatus);
		disable = Integer.parseInt(disableStatus);
		delete = Integer.parseInt(deleteStatus);
		inactive = Integer.parseInt(inactiveStatus);
		active = Integer.parseInt(activeStatus);
		offline = Integer.parseInt(offlineStatus);
		user = Integer.parseInt(userCategory);
		account = Integer.parseInt(accountCategory);
		login = Integer.parseInt(loginCategory);
		agencySuccess = agencySuccess1;


		return properties;
	}
	////////////////////////////********************/////////////////////////////


	@RequestMapping(value="/agencyForm" ,
			method=RequestMethod.GET)
	public ModelAndView agencyForm(HttpSession session,
			@ModelAttribute("command") AgencyBean agencyBean)
	{

		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){
			try {
				model = new HashMap<String, Object>();
				model.put("createdBy",userSessionBean.getId());
			} catch (Exception e) {

				e.printStackTrace();
			}
			return new ModelAndView("openAgencyForm", model);
		}else{
			return new ModelAndView("error");
		}

	}

	@RequestMapping(value = "/validateagency",
			method = RequestMethod.POST)
	public @ResponseBody String validateagency(HttpSession session,
			@RequestParam(value="agency") String agencyName,
			@RequestParam(value="uid") String userid )
					throws Exception{
		initializeProperties();
		agencyModel = new AgencyModel();
		resourceModel = new ResourceModel();
		accountStatusModel = new StatusModel();

	    accountStatusModel.setId(delete);
		resourceModel.setAccountStatusModel(accountStatusModel);
		agencyModel.setAgencyName(agencyName.trim());
		agencyModel.setResourceModel(resourceModel);
		String agency="E";


		AgencyModel agencyModel1=agencyService.validateAgency(agencyModel);

		if(agencyModel1!=null){
			agency=agencyModel1.getAgencyName();
		}else{

		}

		System.out.println(agency);
		return agency;

	}


	@RequestMapping(value = "/addAgency",
			method = RequestMethod.POST)
	public ModelAndView add(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") AgencyBean agencyBean,BindingResult result,
			@RequestParam(value="flag") String flag)
					throws Exception  {
		agencyRegistrationValidator.validate(agencyBean, result);
		if(!result.hasErrors()){
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			count = agencyService.countAll();
		//	List<AgencyModel> agencies = agencyService.getByPage(pg, pageSize);
			
			
			agencyBean.getId();
			int returnMode = 0;
			if(agencyBean!=null){
				if(agencyBean.getId()!=null){
					returnMode = 1;
					agencyModel = new AgencyModel();
					agencyModel.setId(agencyBean.getId());
					agencyModel=agencyService.getAgency(agencyModel);

					AgencyBean oldAgencyBean = AgencyBeanPreparation.prepareAgencyBean(agencyModel);

					resourceBean= oldAgencyBean.getResourceBean();              			

					addressBean = agencyBean.getResourceBean().getAddressBean();//new address
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

					agencyBean.setCreatedDate(oldAgencyBean.getCreatedDate());
					agencyBean.setResourceBean(resourceBean);


					agencyModel = AgencyModelPreparation.prepareAgencyModel(agencyBean);


				}else{

					
					resourceBean= agencyBean.getResourceBean();

					addressBean = agencyBean.getResourceBean().getAddressBean();

					accountStatusBean = new StatusBean();
					accountStatusCategoryBean = new CategoryBean();
					loginStatusBean = new StatusBean();
					loginStatusCategoryBean = new CategoryBean();
					roleBean = new RoleBean();
					creatorRoleBean = new RoleBean();
					resourceBean = new ResourceBean();
					roleCategoryBean = new CategoryBean();
					creatorRoleCategoryBean = new CategoryBean();

					accountStatusBean.setId(enable);
					accountStatusCategoryBean.setId(account);
					accountStatusBean.setCategoryBean(accountStatusCategoryBean);

					loginStatusBean.setId(offline);
					loginStatusCategoryBean.setId(login);
					loginStatusBean.setCategoryBean(loginStatusCategoryBean);

					roleCategoryBean.setId(user);
					roleBean.setId(agency);
					roleBean.setCategoryBean(roleCategoryBean);

					creatorRoleCategoryBean.setId(user);
					creatorRoleBean.setId(mode[1]);
					creatorRoleBean.setCategoryBean(creatorRoleCategoryBean);

					resourceBean.setAddressBean(addressBean);
					resourceBean.setAccountStatusBean(accountStatusBean);
					resourceBean.setLoginStatusBean(loginStatusBean);
					resourceBean.setRoleBean(roleBean);
					resourceBean.setCreatorRoleBean(creatorRoleBean);
					resourceBean.setCreatedBy(agencyBean.getResourceBean().getCreatedBy());


					agencyBean.setResourceBean(resourceBean);
					agencyBean.setCreatedDate(DateAndTimeUtilities.parseStringDateToSqlDate(DateAndTimeUtilities.getCurrentDateTime()));


					agencyModel = AgencyModelPreparation.prepareAgencyModel(agencyBean);		

				}


				model = new HashMap<String, Object>();
				if(agencyModel!=null){
					boolean returnResult = agencyService.registerAgency(agencyModel);

					if(returnResult){  
						if(returnMode==0){
							session.setAttribute("msg", agencySuccess);
							session.setAttribute("error", result);
							return new ModelAndView("redirect:/userValidate.html");
						}else{
							model.put("agencies",  AgencyBeanPreparation.prepareListofAgencyBean(agencyService.getByPage(pg, pageSize)));
							model.put("Enable",enable);
							model.put("Disable",disable);
							model.put("Delete",delete);
							model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
							
							model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
				            model.put("subtractor", (pg*pageSize - pageSize));

							return new ModelAndView("agenciesList",model);
						}    
					}
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


	@RequestMapping(value = "/agenciesList",
			method = RequestMethod.GET)
	public ModelAndView agenciesList(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") AgencyBean agencyBean,	
			BindingResult result) {
		//	@RequestParam(value="uid") String id
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
		//	List<AgencyModel> agencies = agencyService.getByPage(pg, pageSize);
			
			model = new HashMap<String, Object>();
			agencyModel = new AgencyModel();		   
			resourceModel = new ResourceModel();
			accountStatusModel = new StatusModel();

			resourceModel.setCreatedBy(userSessionBean.getId());
			accountStatusModel.setId(delete); 
			resourceModel.setAccountStatusModel(accountStatusModel);
			agencyModel.setResourceModel(resourceModel);
			//model.put("agencies",  AgencyBeanPreparation.prepareListofAgencyBean(agencyService.listAgenciesByCreator(agencyModel)));
			count = agencyService.countAll(agencyModel);
			model.put("agencies",  AgencyBeanPreparation.prepareListofAgencyBean(agencyService.getByPage(pg, pageSize,agencyModel)));
			model.put("Enable",enable);
			model.put("Disable",disable);
			model.put("Delete",delete);
			model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
			
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));

			return new ModelAndView("agenciesLists", model);
		}else{
			return new ModelAndView("error");
		}
		

	}


	@RequestMapping(value = "/editAgency",				
			method = RequestMethod.POST)
	public ModelAndView editDoctor(HttpSession session, HttpServletRequest request,
			@ModelAttribute("command")
	AgencyBean agencyBean,BindingResult result,
	@RequestParam(value="id") String agencyId)
			throws Exception {

		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){	
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			count = agencyService.countAll();
		//	List<AgencyModel> agencies = agencyService.getByPage(pg, pageSize);
			
			agencyModel = new AgencyModel();
			agencyModel.setId(Integer.parseInt(agencyId));
			agencyModel=agencyService.getAgency(agencyModel);

			model = new HashMap<String, Object>();         
			model.put("agency",  AgencyBeanPreparation.prepareAgencyBean(agencyModel)); 
			model.put("agencies", AgencyBeanPreparation.prepareListofAgencyBean(agencyService.getByPage(pg, pageSize)));
			model.put("Enable",enable);
			model.put("Disable",disable);
			model.put("Delete",delete);
			model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
			
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));
            
			return new ModelAndView("EditAgencyForm", model);
		}else{
			return new ModelAndView("error");
		}

	}	

	@RequestMapping(value = "/changeAgencyStatus",
			method = RequestMethod.POST)
	public ModelAndView agencyStatusUpdate(HttpSession session, HttpServletRequest request,
			@ModelAttribute("command") AgencyBean agencyBean,
			@RequestParam(value="uid") String createdBy,
			@RequestParam(value="id") String id,
			@RequestParam(value="flag") String flag)
					throws Exception {
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
		//	List<AgencyModel> agencies = agencyService.getByPage(pg, pageSize);
			
			agencyModel=new AgencyModel();
			agencyModel.setId(Integer.parseInt(id));
			agencyModel = agencyService.getAgency(agencyModel);

			resourceModel = agencyModel.getResourceModel();
			accountStatusModel = new StatusModel();	 		

			if(flag.contentEquals("0")){
				accountStatusModel.setId(disable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				agencyModel.setResourceModel(resourceModel);                        	 
				agencyService.disableAgency(agencyModel);
			}else if(flag.contentEquals("1")){
				accountStatusModel.setId(delete);
				resourceModel.setAccountStatusModel(accountStatusModel);
				agencyModel.setResourceModel(resourceModel);		        	
				agencyService.deleteAgency(agencyModel);
			}else{   
				accountStatusModel.setId(enable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				agencyModel.setResourceModel(resourceModel);
				agencyService.enableAgency(agencyModel);
			}

			model = new HashMap<String, Object>();
			AgencyModel agencyModel1 = new AgencyModel();
			resourceModel = new ResourceModel();  
			accountStatusModel = new StatusModel();

			resourceModel.setCreatedBy(Integer.parseInt(createdBy));		     
			accountStatusModel.setId(delete);     
			resourceModel.setAccountStatusModel(accountStatusModel);
			agencyModel1.setResourceModel(resourceModel); 		
			
			count = agencyService.countAll(agencyModel1);

			model.put("msg", agencySuccess);
			model.put("agencies",  AgencyBeanPreparation.prepareListofAgencyBean(agencyService.getByPage(pg, pageSize,agencyModel1)));
			model.put("Enable",enable);
			model.put("Disable",disable);
			model.put("Delete",delete);
			model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
			
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));
            
			return new ModelAndView("agenciesList",model);
		}else{
			return new ModelAndView("error");                                                                                                
		}

	}

	@RequestMapping(value = "/updateAgencies", 
			method = RequestMethod.POST)
	public ModelAndView updateAgenciesStatus(HttpSession session, HttpServletRequest request,
			@ModelAttribute("command") AgencyBean agencyBean,
			@RequestParam(value="uid") String uid,
			@RequestParam(value="flag") String flag)
					throws Exception {
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
		//	List<AgencyModel> agencies = agencyService.getByPage(pg, pageSize);
			
			agencyModel = new AgencyModel();
			resourceModel = new ResourceModel();
			accountStatusModel = new StatusModel();

			resourceModel.setCreatedBy(Integer.parseInt(uid));

			if(flag.contentEquals("0")){
				accountStatusModel.setId(enable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				agencyModel.setResourceModel(resourceModel);
				agencyService.enableAllAgencies(agencyModel);
			}else if(flag.contentEquals("1")){
				accountStatusModel.setId(disable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				agencyModel.setResourceModel(resourceModel);
				agencyService.disableAllAgencies(agencyModel);
			}else if(flag.contentEquals("2")){
				accountStatusModel.setId(delete);
				resourceModel.setAccountStatusModel(accountStatusModel);
				agencyModel.setResourceModel(resourceModel);
				agencyService.deleteAllAgencies(agencyModel);
			}
			model = new HashMap<String, Object>();

			AgencyModel agencyModel1 = new AgencyModel();
			resourceModel = new ResourceModel();  
			accountStatusModel = new StatusModel();
			     
			accountStatusModel.setId(delete);     
			resourceModel.setAccountStatusModel(accountStatusModel);
			agencyModel1.setResourceModel(resourceModel); 		
			 
			count = agencyService.countAll(agencyModel1);
			
			model.put("msg", agencySuccess);
			model.put("agencies",  AgencyBeanPreparation.prepareListofAgencyBean(agencyService.getByPage(pg, pageSize, agencyModel1)));
			model.put("Enable",enable);
			model.put("Disable",disable);
			model.put("Delete",delete);
			model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
			
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));

			return new ModelAndView("agenciesList",model);
		}else{
			return new ModelAndView("error");
		}
	}

	@RequestMapping(value = "/getAgenciesByCategory", 
			method = RequestMethod.POST)
	public ModelAndView agenciesListByCategory(HttpSession session, HttpServletRequest request,
			@ModelAttribute("command") AgencyBean agencyBean,
			BindingResult result,

			@RequestParam(value="uid") String uid,
			@RequestParam(value="flag") String flag) {
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);  	
					
					
			Map<String, Object> model = new HashMap<String, Object>();
			agencyModel = new AgencyModel();
			resourceModel = new ResourceModel();
			accountStatusModel = new StatusModel();

			resourceModel.setCreatedBy(Integer.parseInt(uid));


			if(flag.contentEquals("T")){
				accountStatusModel.setId(delete);
				resourceModel.setAccountStatusModel(accountStatusModel);
				agencyModel.setResourceModel(resourceModel);
				count = agencyService.countAll(agencyModel);
				model.put("agencies",  AgencyBeanPreparation.prepareListofAgencyBean(agencyService.getByPage(pg, pageSize,agencyModel)));
				
				
			}else if(flag.contentEquals("0")){
				accountStatusModel.setId(enable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				agencyModel.setResourceModel(resourceModel);
				count = agencyService.countAll(agencyModel);
				model.put("agencies",  AgencyBeanPreparation.prepareListofAgencyBean(agencyService.getByPage(pg, pageSize,agencyModel)));
			//	model.put("Enable",enable);
			//	model.put("Disable",disable);
			//	model.put("Delete",delete);
			//	model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
			}else{   
				accountStatusModel.setId(disable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				agencyModel.setResourceModel(resourceModel);
				count = agencyService.countAll(agencyModel);
				agencies = agencyService.getByPage(pg, pageSize,agencyModel);
				model.put("agencies",  AgencyBeanPreparation.prepareListofAgencyBean(agencies)); 
			//	model.put("Enable",enable);
			//	model.put("Disable",disable);
			//	model.put("Delete",delete);
			//	model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());				
			}
			
			model.put("Enable",enable);
			model.put("Disable",disable);
			model.put("Delete",delete);
			model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
			
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));
            
			return new ModelAndView("agenciesList", model);
		}else{
			return new ModelAndView("error");
		}

	}

}
