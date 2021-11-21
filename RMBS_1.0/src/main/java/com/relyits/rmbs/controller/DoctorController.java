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

import com.relyits.rmbs.beans.registration.DoctorBean;
import com.relyits.rmbs.beans.registration.ResourceBean;
import com.relyits.rmbs.beans.resources.AddressBean;
import com.relyits.rmbs.beans.resources.CategoryBean;
import com.relyits.rmbs.beans.resources.RoleBean;
import com.relyits.rmbs.beans.resources.StatusBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.registration.DoctorBeanPreparation;
import com.relyits.rmbs.model.refference.AddressModel;
import com.relyits.rmbs.model.refference.CategoryModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.AgencyModel;
import com.relyits.rmbs.model.registration.DoctorModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.model_preparation.registration.DoctorModelPreparation;
import com.relyits.rmbs.service.DoctorService;
import com.relyits.rmbs.service.PageNavigator;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;
import com.relyits.rmbs.utilities.FormUtilities;
import com.relyits.rmbs.utilities.SessionUtilities;
import com.relyits.rmbs.validator.DoctorRegistrationValidator;



@Controller
public class DoctorController {

	@Autowired
	private DoctorService doctorService;	
	
	@Autowired
    private PageNavigator pageNavigator;

	@Autowired
	private DoctorRegistrationValidator doctorValidator;
	
	private static DoctorModel doctorModel = null;
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
	private List<DoctorModel> doctors = null;

	Map<String, Object> model = null;

	Map<String, String> properties= null;

	int admin,doctor,disable,enable,delete,offline,inactive,active,account,login,user;
	String doctorSuccess = null;

	@Value("${role.admin}") String adminRole;
	@Value("${role.doctor}") String doctorRole;
	@Value("${status.disable}") String disableStatus;
	@Value("${status.enable}") String enableStatus;
	@Value("${status.delete}") String deleteStatus;
	@Value("${status.offline}") String offlineStatus;
	@Value("${status.inactive}") String inactiveStatus;
	@Value("${status.active}") String activeStatus;	
	@Value("${category.account}") String accountCategory;
	@Value("${category.login}") String loginCategory;
	@Value("${category.user}") String userCategory;
	@Value("${message.registerDoctorSuccess}") String doctorSuccess1;
	@Value("${app.page.size.default}") private int pageSize;
    @Value("${app.page.nav.trail}") private int pageNavTrail;


	public Map<String, String> initializeProperties(){

		admin=Integer.parseInt(adminRole);
		doctor = Integer.parseInt(doctorRole);
		enable = Integer.parseInt(enableStatus);
		disable = Integer.parseInt(disableStatus);
		delete = Integer.parseInt(deleteStatus);
		inactive = Integer.parseInt(inactiveStatus);
		active = Integer.parseInt(activeStatus);
		offline = Integer.parseInt(offlineStatus);
		user = Integer.parseInt(userCategory);
		account = Integer.parseInt(accountCategory);
		login = Integer.parseInt(loginCategory);
		doctorSuccess = doctorSuccess1;


		return properties;
	}

	/////////////////////////***********************///////////////////////

	@RequestMapping(value="/doctorForm" , method=RequestMethod.GET)
	public ModelAndView doctorRegistrationForm(
			HttpSession session,
			@ModelAttribute("command") DoctorBean doctorBean){
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){		

			try {
				model = new HashMap<String, Object>();
				model.put("createdby",userSessionBean.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			model.put("initials",FormUtilities.getInitial());
			return new ModelAndView("openDoctorForm", model);
		}else{
			return new ModelAndView("error");
		}

	}



	@RequestMapping(value = "/addDoctor",method = RequestMethod.POST)
	public ModelAndView addDoctor(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") DoctorBean doctorBean,BindingResult result,
			@RequestParam(value="flag") String flag)
					throws Exception  {	
		doctorValidator.validate(doctorBean, result);
		if(!result.hasErrors()){
		initializeProperties();

		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){	
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			count = doctorService.countAll();
			
			int returnMode=0;
			doctorBean.getId();
			if(doctorBean!=null){

				if(doctorBean.getId()!=null){
					returnMode=1;
					doctorModel = new DoctorModel();
					doctorModel.setId(doctorBean.getId());
					doctorModel=doctorService.getDoctor(doctorModel);

					DoctorBean oldDoctorBean=DoctorBeanPreparation.prepareDoctorBean(doctorModel);

					resourceBean= oldDoctorBean.getResourceBean();              			

					addressBean = doctorBean.getResourceBean().getAddressBean();//new address
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

					doctorBean.setCreatedDate(oldDoctorBean.getCreatedDate());
					doctorBean.setResourceBean(resourceBean);


					doctorModel = DoctorModelPreparation.prepareDoctorModel(doctorBean);


				}else{

					resourceBean= doctorBean.getResourceBean();

					addressBean = doctorBean.getResourceBean().getAddressBean();
				
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
					roleBean.setId(doctor);
					roleBean.setCategoryBean(roleCategoryBean);

					creatorRoleCategoryBean.setId(user);
					creatorRoleBean.setId(mode[1]);
					creatorRoleBean.setCategoryBean(creatorRoleCategoryBean);

					resourceBean.setAddressBean(addressBean);
					resourceBean.setAccountStatusBean(accountStatusBean);
					resourceBean.setLoginStatusBean(loginStatusBean);
					resourceBean.setRoleBean(roleBean);
					resourceBean.setCreatorRoleBean(creatorRoleBean);
					resourceBean.setCreatedBy(doctorBean.getResourceBean().getCreatedBy());


					doctorBean.setResourceBean(resourceBean);
					doctorBean.setCreatedDate(DateAndTimeUtilities.parseStringDateToSqlDate(DateAndTimeUtilities.getCurrentDateTime()));


					doctorModel = DoctorModelPreparation.prepareDoctorModel(doctorBean);		

				}

				model = new HashMap<String, Object>();
				if(doctorModel!=null){

					boolean returnResult = doctorService.registerDoctor(doctorModel);
					if(returnResult){
						if(returnMode==0){
							session.setAttribute("msg", doctorSuccess);
							session.setAttribute("error", result);
							return new ModelAndView("redirect:/userValidate.html",model);
						}else{
						//	model.put("doctors",  DoctorBeanPreparation.prepareListofDoctorBean(doctorService.listDoctorsByCreator(doctorModel)));
							
							model.put("doctors",  DoctorBeanPreparation.prepareListofDoctorBean(doctorService.getByPage(pg, pageSize)));
							model.put("Enable",enable);
							model.put("Disable",disable);
							model.put("Delete",delete);
							model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
							
							model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
				            model.put("subtractor", (pg*pageSize - pageSize));
				            
							return new ModelAndView("doctorsList",model);
						}
					}
				}

			}



		}else{
		return new ModelAndView("error");
		}}else{
			model.put("error", result);
			model.put("errors", "fale");
			session.setAttribute("error", result);
			return new ModelAndView("redirect:/userValidate.html");
		}
		return null;
	}	



	@RequestMapping(value = "/validateDoctor",method = RequestMethod.POST)
	public @ResponseBody String validateDoctor(HttpSession session,
			@RequestParam(value="doctorName") String doctorName,
			@RequestParam(value="uid") String id )
					throws Exception{
		 initializeProperties();
		 doctorModel = new DoctorModel();
		 resourceModel.setCreatedBy(Integer.parseInt(id));
		 accountStatusModel.setId(enable);
		 resourceModel.setAccountStatusModel(accountStatusModel);
		 doctorModel.setResourceModel(resourceModel);
		 doctorModel.setDoctorName(doctorName);
		 String doctor=null;


		 DoctorModel doctorModel1=doctorService.getDoctor(doctorModel);


		 if(doctorModel1!=null){
			 doctor=doctorModel1.getDoctorName();
		 }else{

		 }
		 System.out.println(doctor);

		 return doctor;
		 
	}


	@RequestMapping(value = "/doctorsList", method = RequestMethod.GET)
	public ModelAndView doctorsList(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") DoctorBean doctorBean,	
			BindingResult result){
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){	
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			
			
			Map<String, Object> model = new HashMap<String, Object>();
			doctorModel = new DoctorModel();
			resourceModel = new ResourceModel();
			accountStatusModel = new StatusModel();

			resourceModel.setCreatedBy(userSessionBean.getId());		     
			accountStatusModel.setId(delete);     
			resourceModel.setAccountStatusModel(accountStatusModel);
			doctorModel.setResourceModel(resourceModel);
			count = doctorService.countAll(doctorModel);
		//	model.put("doctors",  DoctorBeanPreparation.prepareListofDoctorBean(doctorService.listDoctorsByCreator(doctorModel)));
		
			model.put("doctors",  DoctorBeanPreparation.prepareListofDoctorBean(doctorService.getByPage(pg, pageSize,doctorModel)));
			
			model.put("Enable",enable);
			model.put("Disable",disable);
			model.put("Delete",delete);
			model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
			
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));

			return new ModelAndView("doctorsLists", model);
		}else{
			return new ModelAndView("error");
		}


	}
	
	@RequestMapping(value = "/getDoctor",
			method = RequestMethod.POST)
	public ModelAndView editDoctor(HttpSession session, HttpServletRequest request,
			@ModelAttribute("command")
	DoctorBean doctorBean,BindingResult result,
	@RequestParam(value="id") String doctorid)
			throws Exception {
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){	
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			count = doctorService.countAll();
			
			doctorModel = new DoctorModel();
			doctorModel.setId(Integer.parseInt(doctorid));
			doctorModel=doctorService.getDoctor(doctorModel);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("initials",FormUtilities.getInitial());

			model.put("doctor",  DoctorBeanPreparation.prepareDoctorBean(doctorModel)); 
			model.put("doctors", DoctorBeanPreparation.prepareListofDoctorBean(doctorService.getByPage(pg, pageSize)));
			model.put("Enable",enable);
			model.put("Disable",disable);
			model.put("Delete",delete);
			model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
			
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));	
			
			return new ModelAndView("editDoctorForm", model);
		}else{
			return new ModelAndView("error");
		}

	}



	@RequestMapping(value = "/changeDoctorStatus",
			method = RequestMethod.POST)
	public ModelAndView updateDoctorStatus(HttpSession session, HttpServletRequest request,
			@ModelAttribute("command") DoctorBean doctorBean,
			@RequestParam(value="uid") String createdBy,
			@RequestParam(value="id") String doctorid,
			@RequestParam(value="flag") String flag)
					throws Exception {
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){	

			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			count = doctorService.countAll();
			
			doctorModel = new DoctorModel();
			doctorModel.setId(Integer.parseInt(doctorid));
			doctorModel=doctorService.getDoctor(doctorModel);	

			resourceModel = doctorModel.getResourceModel();
			accountStatusModel = new StatusModel();		 
			
			if(flag.contentEquals("0")){
				accountStatusModel.setId(disable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				doctorModel.setResourceModel(resourceModel);
				doctorService.disableDoctor(doctorModel);
			}else if(flag.contentEquals("1")){
				accountStatusModel.setId(enable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				doctorModel.setResourceModel(resourceModel);
				doctorService.enableDoctor(doctorModel);
			}else{
				accountStatusModel.setId(delete);
				resourceModel.setAccountStatusModel(accountStatusModel);
				doctorModel.setResourceModel(resourceModel);
				doctorService.deleteDoctor(doctorModel);
			}
			model = new HashMap<String, Object>();
			DoctorModel doctorModel1 = new DoctorModel();
			resourceModel = new ResourceModel();
			accountStatusModel = new StatusModel();

			resourceModel.setCreatedBy(Integer.parseInt(createdBy));		     
			accountStatusModel.setId(delete);     
			resourceModel.setAccountStatusModel(accountStatusModel);
			doctorModel1.setResourceModel(resourceModel);
			
			model.put("doctors",  DoctorBeanPreparation.prepareListofDoctorBean(doctorService.getByPage(pg, pageSize, doctorModel1)));
			model.put("Enable",enable);
			model.put("Disable",disable);
			model.put("Delete",delete);
			model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
			
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));	

			return new ModelAndView("doctorsList",model);
		     
		}else{
			return new ModelAndView("error");
		}

	}		

	@RequestMapping(value = "/updateDoctors", 
			method = RequestMethod.POST)
	public ModelAndView updateDoctorsStatus(HttpSession session, HttpServletRequest request,
			@ModelAttribute("command") DoctorBean doctorBean,
			@RequestParam(value="uid") String uid,
			@RequestParam(value="flag") String flag)
					throws Exception {
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){	
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			count = doctorService.countAll();
			
			doctorModel = new DoctorModel(); 
			accountStatusModel = new StatusModel();
			resourceModel = new ResourceModel();

			resourceModel.setCreatedBy(Integer.parseInt(uid));

			if(flag.contentEquals("0")){
				accountStatusModel.setId(enable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				doctorModel.setResourceModel(resourceModel);
				doctorService.enableAllDoctors(doctorModel);
			}else if(flag.contentEquals("1")){
				accountStatusModel.setId(disable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				doctorModel.setResourceModel(resourceModel);    
				doctorService.disableAllDoctors(doctorModel);
			}else if(flag.contentEquals("2")){
				accountStatusModel.setId(delete);
				resourceModel.setAccountStatusModel(accountStatusModel);
				doctorModel.setResourceModel(resourceModel);
				doctorService.deleteAllDoctors(doctorModel);
			}
			model = new HashMap<String, Object>();

			model.put("msg", doctorSuccess);
		//	model.put("doctors",  DoctorBeanPreparation.prepareListofDoctorBean(doctorService.listDoctorsByCreator(doctorModel)));
			
			model.put("doctors",  DoctorBeanPreparation.prepareListofDoctorBean(doctorService.getByPage(pg, pageSize, doctorModel)));
			model.put("Enable",enable);
			model.put("Disable",disable);
			model.put("Delete",delete);
			model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());

			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));	
            
			return new ModelAndView("doctorsList",model);

		}else{
			return new ModelAndView("error");
		}
	}

	@RequestMapping(value = "/getDoctorssByCategory", 
			method = RequestMethod.POST)
	public ModelAndView doctorsListByCategory(HttpSession session, HttpServletRequest request,
			@ModelAttribute("command") DoctorBean doctorBean,
			BindingResult result,
			@RequestParam(value="uid") String uid,
			@RequestParam(value="flag") String flag) {
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){	
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
		
			
			model = new HashMap<String, Object>();
			doctorModel = new DoctorModel();
			resourceModel = new ResourceModel();
			accountStatusModel = new StatusModel();

			resourceModel.setCreatedBy(Integer.parseInt(uid));

			if(flag.contentEquals("T")){
				accountStatusModel.setId(delete);
				resourceModel.setAccountStatusModel(accountStatusModel);
				doctorModel.setResourceModel(resourceModel);
				count = doctorService.countAll(doctorModel);
				model.put("doctors",  DoctorBeanPreparation.prepareListofDoctorBean(doctorService.getByPage(pg, pageSize, doctorModel)));
				
			}else if(flag.contentEquals("0")){
				accountStatusModel.setId(enable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				doctorModel.setResourceModel(resourceModel);
				count = doctorService.countAll(doctorModel);
				model.put("doctors",  DoctorBeanPreparation.prepareListofDoctorBean(doctorService.getByPage(pg, pageSize, doctorModel)));
				
			}else{
				accountStatusModel.setId(disable);
				resourceModel.setAccountStatusModel(accountStatusModel);
				doctorModel.setResourceModel(resourceModel);
				count = doctorService.countAll(doctorModel);
				model.put("doctors",  DoctorBeanPreparation.prepareListofDoctorBean(doctorService.getByPage(pg, pageSize, doctorModel)));
				
			}
			model.put("Enable",enable);
			model.put("Disable",disable);
			model.put("Delete",delete);
			model.put("role", userSessionBean.getResourceBean().getRoleBean().getId());
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));	
			
			return new ModelAndView("doctorsList", model);
		}else{
			return new ModelAndView("error");
		}

	}





}
