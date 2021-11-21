package com.relyits.rmbs.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.util.StringTokenizer;
import com.relyits.rmbs.beans.notifications.MessageNotificationsBean;
import com.relyits.rmbs.beans.resources.CategoryBean;
import com.relyits.rmbs.beans.resources.RoleBean;
import com.relyits.rmbs.beans.resources.StatusBean;
import com.relyits.rmbs.beans.session.UserSessionBean;
import com.relyits.rmbs.beans_preparation.notifications.MessageNotificationsBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.BranchBeanPreparation;
import com.relyits.rmbs.beans_preparation.registration.OutletBeanPreparation;
import com.relyits.rmbs.model.notifications.MessageNotificationsModel;
import com.relyits.rmbs.model.refference.RoleModel;
import com.relyits.rmbs.model.refference.StatusModel;
import com.relyits.rmbs.model.registration.BranchModel;
import com.relyits.rmbs.model.registration.OrganizationModel;
import com.relyits.rmbs.model.registration.OutletModel;
import com.relyits.rmbs.model.registration.ResourceModel;
import com.relyits.rmbs.model_preparation.notifications.MessageNotificationsModelPreparation;
import com.relyits.rmbs.service.BranchService;
import com.relyits.rmbs.service.NotificationService;
import com.relyits.rmbs.service.OrganizationService;
import com.relyits.rmbs.service.OutletService;
import com.relyits.rmbs.service.PageNavigator;
import com.relyits.rmbs.utilities.DateAndTimeUtilities;
import com.relyits.rmbs.utilities.SessionUtilities;

@Controller
public class NotificationsController{


	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private OutletService outletService;
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
    private PageNavigator pageNavigator;
	
	private Long count = null;

	UserSessionBean userSessionBean = null;

	Map<String, Object> model = null;

	BranchModel branchModel = null;
	OrganizationModel organizationModel = null;
	OutletModel outletModel = null;
	ResourceModel resourceModel = null;
	StatusModel accountStatusModel = null;
	StatusModel loginStatusModel = null;
	MessageNotificationsModel messageNotificationsModel = null;
	RoleModel roleModel = null;
	RoleModel creatorRoleModel = null;
	RoleModel responsibleRoleModel = null;
	StatusModel statusModel = null;


	private static RoleBean responsibleRoleBean = null;
	private static RoleBean creatorRoleBean = null;
	private static StatusBean statusBean = null;
	private static CategoryBean categoryBean = null;

	Map<String, String> properties= null;
	int admin,organization,branch,outlet,inactive, account,messages,unread,read,hide;
	String msg = null;

	@Value("${role.admin}") String adminRole;
	@Value("${role.branch}") String branchRole;
	@Value("${role.outlet}") String outletRole;
	@Value("${role.organization}") String organizationRole;
	@Value("${status.inactive}") String inactiveStatus;
	@Value("${category.account}") String accountCat;
	@Value("${status.unread}") String unreadStatus;
	@Value("${status.read}") String readStatus;
	@Value("${status.hidden}") String hideStatus;
	@Value("${category.messages}") String messageCategory;
	@Value("${app.page.size.default}") private int pageSize;
    @Value("${app.page.nav.trail}") private int pageNavTrail;


	@Value("${message.messageSuccessfullySent}") String msgSent;
	public Map<String, String> initializeProperties(){

		admin=Integer.parseInt(adminRole);
		branch=Integer.parseInt(branchRole);
		outlet=Integer.parseInt(outletRole);
		organization = Integer.parseInt(organizationRole);
		inactive = Integer.parseInt(inactiveStatus);
		account = Integer.parseInt(accountCat);
		messages = Integer.parseInt(messageCategory);
		unread = Integer.parseInt(unreadStatus);
		read = Integer.parseInt(readStatus);
		hide = Integer.parseInt(hideStatus);
		
		msg = msgSent;

		return properties;
	}


	/////////////////*********************////////////////////

	@RequestMapping(value = "/loadAccountReguests",method = RequestMethod.GET)
	public ModelAndView getAccountReguests(HttpSession session,HttpServletRequest request
		//	@RequestParam(value = "owner") String owner,
		//	@RequestParam(value = "flag") String flag
			) throws Exception {
		initializeProperties();
		
		int pg = ServletRequestUtils.getIntParameter(request, "pg", 1);
		
		userSessionBean=SessionUtilities.giveMeSession(session);
		organizationModel=new OrganizationModel();
		resourceModel = new ResourceModel();
		accountStatusModel = new StatusModel();
		roleModel = new RoleModel();
		accountStatusModel.setId(inactive);
		resourceModel.setAccountStatusModel(accountStatusModel);

		model = new HashMap<String, Object>();
		if(userSessionBean.getResourceBean().getRoleBean().getId()==admin){
			roleModel.setId(organization);
			resourceModel.setRoleModel(roleModel);
			organizationModel.setId(userSessionBean.getId());
			organizationModel.setResourceModel(resourceModel);
			count = organizationService.countAll(organizationModel);
			model.put("users", organizationService.getByPage(pg, pageSize, organizationModel));
		
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));
            model.put("inactive", inactive);
            model.put("status", organizationModel.getResourceModel().getAccountStatusModel().getId());
            
			return new ModelAndView("accountRequetsbyOwner",model);
		}else if(userSessionBean.getResourceBean().getRoleBean().getId()==organization){
			organizationModel.setId(userSessionBean.getId());
			branchModel=new BranchModel();
		
			branchModel.setOrganizationModel(organizationModel);
			branchModel.setResourceModel(resourceModel);
			count = branchService.countAll(branchModel);
			model.put("branches", BranchBeanPreparation.prepareListofBranchesBeans(branchService.getByPage(pg, pageSize, branchModel))); 
			model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));
            model.put("inactive", inactive);
            model.put("status", branchModel.getResourceModel().getAccountStatusModel().getId());
			
			
			return new ModelAndView("accountRequetsByBranch",model); 
		}else if(userSessionBean.getResourceBean().getRoleBean().getId()==branch){
			outletModel = new OutletModel();
			branchModel = new BranchModel();
			branchModel.setId(userSessionBean.getId());
			outletModel.setBranchModel(branchModel);
			outletModel.setResourceModel(resourceModel);
			count = outletService.countAll(outletModel);
		//	model.put("outlets", OutletBeanPreparation.prepareListofOutletsBeans(outletService.getOutletListsByBranch(outletModel)));
			model.put("outlets", OutletBeanPreparation.prepareListofOutletsBeans(outletService.getByPage(pg, pageSize, outletModel)));
		
			model.put("inactive", inactive);
            model.put("status", outletModel.getResourceModel().getAccountStatusModel().getId());
            model.put("pageNav", pageNavigator.buildPageNav("#", count.intValue(), pg, pageSize, pageNavTrail));
            model.put("subtractor", (pg*pageSize - pageSize));
			return new ModelAndView("accountRequetsByOutlet",model);
		}else{
			return new ModelAndView("redirect:/expiredProducts.html?task_list_type=sub");
		}

	}
	LinkedHashMap<Integer, String> user = null;
	@RequestMapping(value="/openMesssageForm" , method=RequestMethod.GET)
	public ModelAndView messageForm(HttpSession session,
			@ModelAttribute("command") MessageNotificationsBean messageNotificationsBean,
			@RequestParam(value="flag")String flag){
		initializeProperties();
		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){		
			user = new LinkedHashMap<Integer, String>();
			try {
				organizationModel = new OrganizationModel();
				branchModel = new BranchModel();
				resourceModel = new ResourceModel();
				roleModel = new RoleModel();
				if(flag.contentEquals("mainorgmain")){					
					roleModel.setId(admin);
					resourceModel.setRoleModel(roleModel);
					organizationModel.setResourceModel(resourceModel);
					user = getAdminList(organizationModel);
				}
				else if(flag.contentEquals("mainbranchmain")){
					roleModel.setId(organization);
					organizationModel.setId(userSessionBean.getOrganizationBean().getId());
					resourceModel.setRoleModel(roleModel);
					organizationModel.setResourceModel(resourceModel);
					user = getOrganizationList(organizationModel);
				} else {
					roleModel.setId(branch);
					branchModel.setId(userSessionBean.getBranchBean().getId());
					resourceModel.setRoleModel(roleModel);
					branchModel.setResourceModel(resourceModel);
					user = getBranchesList(branchModel);
				}
			
				
				model = new HashMap<String, Object>();
				
				
			
				model.put("sendTo",user );
				model.put("createdby",userSessionBean.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}

			return new ModelAndView("messageForm", model);
		}else{
			return new ModelAndView("error");
		}

	}
	public LinkedHashMap<Integer, String> getAdminList(OrganizationModel organizationModel){
		LinkedHashMap<Integer, String> admins = new LinkedHashMap<Integer, String>();
		List<OrganizationModel> admin= notificationService.getAdminByRole(organizationModel);
		for(int i=0; i<admin.size(); i++){
			int adminid=admin.get(i).getId();
			String name=admin.get(i).getName();
			admins.put(adminid, name);
		}
		return admins;
	}

	public LinkedHashMap<Integer, String> getOrganizationList(OrganizationModel organizationModel){
		LinkedHashMap<Integer, String> orgs = new LinkedHashMap<Integer, String>();
		List<OrganizationModel> organization= notificationService.getOrganizationOfselectedBranch(organizationModel);
		for(int i=0; i<organization.size(); i++){
			int adminid=organization.get(i).getId();
			String name=organization.get(i).getName();
			orgs.put(adminid, name);
		}
		return orgs;
	}

	public LinkedHashMap<Integer, String> getBranchesList(BranchModel branchModel){
		LinkedHashMap<Integer, String> branches = new LinkedHashMap<Integer, String>();
		List<BranchModel> branch= notificationService.getBranchOfselectedOutlet(branchModel);
		for(int i=0; i<branch.size(); i++){
			int adminid=branch.get(i).getId();
			String name=branch.get(i).getName();
			branches.put(adminid, name);
		}
		return branches;
	}


	@RequestMapping(value = "/saveMessage",method = RequestMethod.POST)
	public ModelAndView addDoctor(HttpSession session,
			@ModelAttribute("command") MessageNotificationsBean messageNotificationsBean,BindingResult result)
				//@RequestParam(value="flag") String flag
					throws Exception  {	

		initializeProperties();

		UserSessionBean userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){	

			MessageNotificationsModel messageNotificationsModel = new MessageNotificationsModel();

			statusBean = new StatusBean();
			responsibleRoleBean = new RoleBean();
			creatorRoleBean = new RoleBean();
			categoryBean = new CategoryBean();

			statusBean.setId(unread);
			if(mode[1] == organization){
				responsibleRoleBean.setId(admin);
			}else if(mode[1] == branch){
				responsibleRoleBean.setId(organization);
			}else{
				responsibleRoleBean.setId(branch);
			}
			creatorRoleBean.setId(mode[1]);
			categoryBean.setId(messages);

			messageNotificationsBean.setStatusBean(statusBean);
			messageNotificationsBean.setCategoryBean(categoryBean);
			messageNotificationsBean.setCreatorRoleBean(creatorRoleBean);
			messageNotificationsBean.setResponsibleRoleBean(responsibleRoleBean);
			messageNotificationsBean.setCreatedDate(DateAndTimeUtilities.parseStringDateToSqlDate(DateAndTimeUtilities.getCurrentDateTime()));
			messageNotificationsBean.setCreatedBy(mode[2]);

			messageNotificationsModel = MessageNotificationsModelPreparation.prepareMessageNotificationsModel(messageNotificationsBean);			

			model = new HashMap<String, Object>();

			if(messageNotificationsModel!=null){

				boolean returnResult = notificationService.saveMessage(messageNotificationsModel);


				if(returnResult){						
					model.put("message", msg);	
					return new ModelAndView("openMesssageForm",model); 
				}

			}	

		}
		return new ModelAndView("error");

	}
	List<MessageNotificationsBean> messageNotificationsBeans = null;
	@RequestMapping(value = "/messageNotificationsList", method = RequestMethod.GET)
	public ModelAndView lessQuantityProducts(HttpSession session,
			@ModelAttribute("command") MessageNotificationsBean messageNotificationsBean,
			BindingResult result)throws ParseException{
		initializeProperties();
		userSessionBean=SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){		
			model = new HashMap<String, Object>();		
			messageNotificationsModel = new MessageNotificationsModel();
			responsibleRoleModel = new RoleModel();
			organizationModel = new OrganizationModel();
			responsibleRoleModel.setId(mode[1]);
			messageNotificationsModel.setResponsibility(mode[2]);
			messageNotificationsModel.setResponsibleRoleModel(responsibleRoleModel);
			messageNotificationsBeans = new ArrayList<MessageNotificationsBean>();

			try{					     
					messageNotificationsBeans = MessageNotificationsBeanPreparation.prepareListOfMessageNotificationsBean(notificationService.listOfMessageNotificationsToAdmin(messageNotificationsModel));
		
			}catch(Exception e){
				e.printStackTrace();
			}
			if(messageNotificationsBeans!=null){
			for(MessageNotificationsBean messageNotificationsBean1 : messageNotificationsBeans ){
			if(messageNotificationsBean1.getCreatorRoleBean().getId() == organization){
					organizationModel.setId(messageNotificationsBean1.getCreatedBy());
					organizationModel = organizationService.getOrganizationbyId(organizationModel);
					messageNotificationsBean1.setCreatorName(organizationModel.getName());
				}
				if(messageNotificationsBean1.getCreatorRoleBean().getId() == branch){
					branchModel.setId(messageNotificationsBean1.getCreatedBy());
					branchModel = branchService.getBranchbyId(branchModel);
					messageNotificationsBean1.setCreatorName(branchModel.getName());
				}else if(messageNotificationsBean1.getCreatorRoleBean().getId() == outlet){
					outletModel.setId(messageNotificationsBean1.getCreatedBy());
					outletModel = outletService.getOutlet(outletModel);
					messageNotificationsBean1.setCreatorName(outletModel.getUserName());
				}
				

			   }
			}

			model.put("messageNotificationsToAdmin",messageNotificationsBeans);
			model.put("unread", unread);
			model.put("read",read);
			model.put("hide", hide);

			return new ModelAndView("messageNotificationsList",model);

		}else{
			return new ModelAndView("error");
		}  
	}

	@RequestMapping(value = "/updateMessageStatus",	method = RequestMethod.POST,headers="Accept=*/*")
	public ModelAndView updateMessageStatus(HttpSession session,
			@RequestParam(value="ids") String str,
			@RequestParam(value="flag") String flag)
					throws Exception {
		
		
		initializeProperties();
		userSessionBean = SessionUtilities.giveMeSession(session);
		int mode[]=SessionUtilities.validateSession(userSessionBean.getId()+"", session);
		if(mode[0]==1){	
			StringTokenizer st = new StringTokenizer(str,",");
			List<Integer> idList=new ArrayList<>();
			List<MessageNotificationsModel> messageNotificationsModels=new ArrayList<MessageNotificationsModel>();
			while (st.hasMoreElements()) {
	
				idList.add(Integer.parseInt((String) st.nextElement()));
				
			if(flag.contentEquals("read")){
			
			for(int i=0;i<idList.size();i++ ){
				messageNotificationsModel=new MessageNotificationsModel();
				messageNotificationsModel.setId(idList.get(i));
				
				statusModel = new StatusModel();			
				statusModel.setId(read);				
				messageNotificationsModel.setStatusModel(statusModel);
				messageNotificationsModels.add(messageNotificationsModel);
				
			}
			}else if(flag.contentEquals("unread")){
				for(int i=0;i<idList.size();i++ ){
					messageNotificationsModel=new MessageNotificationsModel();
					messageNotificationsModel.setId(idList.get(i));
					
					statusModel = new StatusModel();			
					statusModel.setId(unread);				
					messageNotificationsModel.setStatusModel(statusModel);
					messageNotificationsModels.add(messageNotificationsModel);
					
				}
				}else{
					for(int i=0;i<idList.size();i++ ){
						messageNotificationsModel=new MessageNotificationsModel();
						messageNotificationsModel.setId(idList.get(i));
						
						statusModel = new StatusModel();			
						statusModel.setId(hide);				
						messageNotificationsModel.setStatusModel(statusModel);
						messageNotificationsModels.add(messageNotificationsModel);
						
					}
					
				}
			}
			
			int i = notificationService.updateMessageStatus(messageNotificationsModels);
			
		    
			model = new HashMap<String, Object>();

			if(i>0){
				
			return new ModelAndView("redirect:/messageNotificationsList.html");
			}
			
			
		}
		return new ModelAndView("error");
	}
}



