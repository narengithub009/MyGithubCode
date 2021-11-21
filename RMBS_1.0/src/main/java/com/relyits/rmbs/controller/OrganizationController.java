
package com.relyits.rmbs.controller;

import java.util.HashMap;
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

import com.relyits.rmbs.beans.registration.RegistrationBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.OrganizationService;
import com.relyits.rmbs.service.PageNavigator;
import com.relyits.rmbs.utilities.SessionUtilities;

@Controller
public class OrganizationController {


	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private BranchService branchService;
	
	@Autowired
    private PageNavigator pageNavigator;


	BranchModel branchModel = null;
	OrganizationModel organizationModel = null;
	UserSessionBean userSessionBean = null;

	ResourceModel resourceModel = null;
	StatusModel accountStatusModel = null;
	StatusModel loginStatusModel = null;
	RoleModel roleModel = null;
	
	private Long count = null;

	Map<String, String> properties= null;
	int inactive,active,admin,organization,branch,outlet ;
	
	
	

	@Value("${status.inactive}") String inactiveStatus;
	@Value("${status.active}") String activeStatus;
	@Value("${role.organization}") String org;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${role.admin}") String adminRole;
	@Value("${app.page.size.default}") private int pageSize;
    @Value("${app.page.nav.trail}") private int pageNavTrail;

	public Map<String, String> initializeProperties(){

		inactive = Integer.parseInt(inactiveStatus);
		active = Integer.parseInt(activeStatus);
		organization = Integer.parseInt(org);
		branch = Integer.parseInt(branchRole);
		outlet = Integer.parseInt(outletRole);
		admin = Integer.parseInt(adminRole);
		
		return properties;
	}

	//////////////////////**********************////////////////////

	@RequestMapping(value = "/updateOrgStatusByAdmin",
			method = RequestMethod.POST)
	public @ResponseBody OrganizationModel updateOwnerStatusByAdmin(@RequestParam(value = "owner") String Organization,
			@RequestParam(value = "menu") String menuOptions,
			@RequestParam(value = "flag") String flag,
			HttpSession session)
					throws Exception {
		userSessionBean = (UserSessionBean) session.getAttribute("user");
		initializeProperties();
		organizationModel=new OrganizationModel();
		int OrgId=Integer.parseInt(Organization);
		organizationModel.setId(OrgId);
		organizationModel=organizationService.getOrganizationbyId(organizationModel);
		accountStatusModel = new StatusModel();
		resourceModel = new ResourceModel();
		
       
		accountStatusModel=organizationModel.getResourceModel().getAccountStatusModel();
		resourceModel=organizationModel.getResourceModel();

		int returnResult=0;
		if(flag.contentEquals("0")){
			accountStatusModel.setId(active);
			 resourceModel.setCreatedBy(userSessionBean.getId());
			resourceModel.setAccountStatusModel(accountStatusModel);

		}else{
			accountStatusModel.setId(inactive);
			resourceModel.setAccountStatusModel(accountStatusModel);

		}


		organizationModel.setResourceModel(resourceModel);
		returnResult=organizationService.changeOrganizationStatus(organizationModel);
		if(returnResult!=0){
			organizationModel=organizationService.getOrganizationbyId(organizationModel);

		}else{

		}
		return organizationModel; 

	}


	
	@RequestMapping(value = "/organizationsList", method = RequestMethod.GET)
	public ModelAndView ownersList(HttpSession session,HttpServletRequest request,@ModelAttribute("command")  
	RegistrationBean registrationBean,
	BindingResult result)
	{
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(""+userSessionBean.getId()+"", session);
		if(mode[0]==1){
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			organizationModel=new OrganizationModel();

			resourceModel = new ResourceModel();
			accountStatusModel = new StatusModel();
			roleModel = new RoleModel();

			accountStatusModel.setId(active);

			resourceModel.setAccountStatusModel(accountStatusModel);


			Map<String, Object> model = new HashMap<String, Object>();
			if(mode[1]==admin){
				roleModel.setId(organization);
				resourceModel.setRoleModel(roleModel);
				organizationModel.setResourceModel(resourceModel);
				count = organizationService.countAll(organizationModel);
				//model.put("users", organizationService.getAccountRequestsByOrganizations(organizationModel));
				
				model.put("users", organizationService.getByPage(pg, pageSize, organizationModel));
				
				
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
	            
				return new ModelAndView("OrganizationsList",model);


			}else if(mode[1]==organization){
				branchModel.setResourceModel(resourceModel);
				count = branchService.countAll(branchModel);
			//	model.put("branches", branchService.getAccountRequestsByBranches(branchModel)); 
				model.put("branches", branchService.getByPage(pg, pageSize, branchModel)); 
				
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
	            
				return new ModelAndView("roleThreeUsersList",model); 

			}else{
				branchModel.setResourceModel(resourceModel); 
				model.put("branches", branchService.getAccountRequestsByBranches(branchModel)); 

				return new ModelAndView("roleThreeUsersList",model);
			}
		}else{
			return new ModelAndView("error");
		}

	}

	@RequestMapping(value = "/getUser",
			method = RequestMethod.POST)
	public @ResponseBody OrganizationModel getUser(@RequestParam(value = "owner") String userid,
			HttpSession session)
					throws Exception {
		organizationModel=new OrganizationModel();
		int orgId=Integer.parseInt(userid);
		organizationModel.setId(orgId);
		return organizationService.getOrganizationbyId(organizationModel);

	}




}
