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
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.OutletBeanPreparation;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.OrganizationService;
import com.relyits.rmbs.service.OutletService;
import com.relyits.rmbs.service.PageNavigator;
import com.relyits.rmbs.utilities.SessionUtilities;

@Controller
public class BranchController {



	@Autowired
	private BranchService branchService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private OutletService outletService;
	
	@Autowired
    private PageNavigator pageNavigator;

	BranchModel branchModel = null;
	OrganizationModel organizationModel = null;
	UserSessionBean userSessionBean = null;

	ResourceModel resourceModel = null;
	StatusModel accountStatusModel = null;
	StatusModel loginStatusModel = null;
	OutletModel outletModel = null;
	
	private Long count = null;

	Map<String, Object> model = null;	

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

	///////////////////*********************///////////////////////

	
	
	@RequestMapping(value = "/branchesList", method = RequestMethod.GET)
	public ModelAndView branchesList(HttpSession session,HttpServletRequest request,
			@ModelAttribute("command") RegistrationBean registrationBean,BindingResult result)
	{
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(""+userSessionBean.getId()+"", session);
		System.out.println("user session "+mode[0]+", user role "+mode[1]);
		if(mode[0]==1){
			
			int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
			
			organizationModel=new OrganizationModel();
			organizationModel.setId(userSessionBean.getId());
			resourceModel = new ResourceModel();
			accountStatusModel = new StatusModel();

			accountStatusModel.setId(active);

			resourceModel.setAccountStatusModel(accountStatusModel);


			model = new HashMap<String, Object>();
			if(mode[1]==admin){

				organizationModel.setResourceModel(resourceModel);
				model.put("users", organizationService.getAccountRequestsByOrganizations(organizationModel));
			
				return new ModelAndView("OrganizationsList",model);


			}else if(mode[1]==organization){
				branchModel = new BranchModel();
				branchModel.setOrganizationModel(organizationModel);				
				branchModel.setResourceModel(resourceModel);
				count = branchService.countAll(branchModel);
			//	model.put("branches", BranchBeanPreparation.prepareListofBranchesBeans(branchService.getAccountRequestsByBranches(branchModel)));
				model.put("branches", BranchBeanPreparation.prepareListofBranchesBeans(branchService.getByPage(pg, pageSize, branchModel))); 
				model.put("org", organization);
				model.put("outlet1", outlet);
				model.put("branch1", branch);
				model.put("active", active);
	            model.put("status", branchModel.getResourceModel().getAccountStatusModel().getId());
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
				return new ModelAndView("roleThreeUsersList",model); 

			}else if(mode[1]==branch){
				outletModel = new OutletModel();
				branchModel = new BranchModel();
				branchModel.setId(userSessionBean.getId());
				outletModel.setBranchModel(branchModel);
				outletModel.setResourceModel(resourceModel);
				count = outletService.countAll(outletModel);
			    model.put("outlets", OutletBeanPreparation.prepareListofOutletsBeans(outletService.getByPage(pg, pageSize, outletModel)));
			    model.put("outlet1", outlet);
			    model.put("branch1", branch);
			    
			    model.put("active", active);
	            model.put("status", outletModel.getResourceModel().getAccountStatusModel().getId());
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
			    
				return new ModelAndView("outletsList",model);
			}else{
				outletModel = new OutletModel();
				branchModel = new BranchModel();
				branchModel.setId(userSessionBean.getBranchBean().getId());
				outletModel.setBranchModel(branchModel);
				outletModel.setResourceModel(resourceModel);	
				count = outletService.countAll(outletModel);
                model.put("outlets", OutletBeanPreparation.prepareListofOutletsBeans(outletService.getByPage(pg, pageSize, outletModel)));
                
                model.put("branch1", branch);
                model.put("outlet1", outlet);
                
                model.put("active", active);
	            model.put("status", outletModel.getResourceModel().getAccountStatusModel().getId());
				model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
	            model.put("subtractor", (pg*pageSize - pageSize));
				return new ModelAndView("outletsList",model);
			}
		}else{
			return new ModelAndView("error");
		}

	}

	@RequestMapping(value = "/getBranch",method = RequestMethod.POST)
	public @ResponseBody BranchModel getBranch(@RequestParam(value = "branch") String branchid,
			HttpSession session)
					throws Exception {
		branchModel=new BranchModel();
		int bid=Integer.parseInt(branchid);
		branchModel.setId(bid);
		branchModel = branchService.getBranchbyId(branchModel);
		return branchModel;

	}

	@RequestMapping(value = "/updateBranchStatusByAdmin",
			method = RequestMethod.POST)
	public @ResponseBody BranchModel updateBranchStatusByOwner(@RequestParam(value = "branch") String branchId,
			@RequestParam(value = "flag") String flag,
			HttpSession session)
					throws Exception {
		initializeProperties();
		branchModel=new BranchModel();
		int bid=Integer.parseInt(branchId);
		branchModel.setId(bid);
		resourceModel = new ResourceModel();
		accountStatusModel = new StatusModel();

		branchModel=branchService.getBranchbyId(branchModel);

		resourceModel=branchModel.getResourceModel();

		if(flag.contentEquals("0")){
			accountStatusModel.setId(active);
			resourceModel.setAccountStatusModel(accountStatusModel);
		}else{
			accountStatusModel.setId(inactive);
			resourceModel.setAccountStatusModel(accountStatusModel); 

		}
		branchModel.setResourceModel(resourceModel);
		boolean returnresult=branchService.changeBranchStatus(branchModel);
		if(returnresult){
			branchModel=branchService.getBranchbyId(branchModel);
		}

		return branchModel; 

	}

}