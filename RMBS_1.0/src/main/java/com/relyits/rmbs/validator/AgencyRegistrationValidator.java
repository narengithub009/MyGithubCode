package com.relyits.rmbs.validator;

import java.sql.Date;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.relyits.rmbs.beans.registration.AgencyBean;

public class AgencyRegistrationValidator implements Validator{

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";  
	String ID_PATTERN = "[0-9]+";  
	String STRING_PATTERN = "[a-zA-Z]+";  
	String MOBILE_PATTERN = "[0-9]{10,13}";  
	String NUM_PATTERN="[0-9]+";
	
	@Override
	public boolean supports(Class cls) {
		return AgencyBean.class.isAssignableFrom(cls);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agencyName", "Please enter agencyName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cstNo", "Please provide cstNo");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dlNo1", "Please provide dlNo1");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dlNo2", "Please provide dlNo2");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.address", "Please enter address");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.mobile", "Please enter mobile number");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.email", "Please enter email");
		
		AgencyBean agencyBean = (AgencyBean)target;
		if (!(agencyBean.getAgencyName() != null && agencyBean.getAgencyName().isEmpty())) {  
			pattern = Pattern.compile(STRING_PATTERN);  
			matcher = pattern.matcher(agencyBean.getAgencyName() );  
			if (!matcher.matches()) {  
				errors.rejectValue("agencyName", /*"doctorName.containNonChar",*/  
						"Agency name should contains only characters");  
			}  
		}
		
		if(!(agencyBean.getResourceBean().getAddressBean().getEmail()!= null && agencyBean.getResourceBean().getAddressBean().getEmail().isEmpty())){
			pattern=Pattern.compile(EMAIL_PATTERN);
			matcher=pattern.matcher(agencyBean.getResourceBean().getAddressBean().getEmail());
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.email",/*"addressBean.email.incorrect",*/ "Enter correct email");
			}
		}
		String mobileNumber = String.valueOf(agencyBean.getResourceBean().getAddressBean().getMobile());
		if(!(mobileNumber.equals(null)&&mobileNumber.isEmpty()))
		{
//			String s = String.valueOf(agencyBean.getResourceBean().getAddressBean().getMobile());
			pattern=Pattern.compile(MOBILE_PATTERN);
			matcher=pattern.matcher(mobileNumber);
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.mobile", /*"addressBean.mobile.incorrect",*/ "Mobile number should contains only 10 - 13 numbers");
			}
		}
		if(!(mobileNumber.equals(null)&&mobileNumber.isEmpty()))
		{
//			String s = String.valueOf(agencyBean.getResourceBean().getAddressBean().getMobile());
			pattern=Pattern.compile(ID_PATTERN);
			matcher=pattern.matcher(mobileNumber);
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.mobile", /*"addressBean.mobile.incorrect",*/ "Mobile number should allow only numaric values");
			}
		}
	}
}
