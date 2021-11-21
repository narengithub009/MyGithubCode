package com.relyits.rmbs.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.relyits.rmbs.beans.registration.OutletBean;

public class OutletRegistrationValidator implements Validator{
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
		// TODO Auto-generated method stub
		return OutletBean.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", /*"userName.outlet.required",*/ "Username is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", /*"firstName.outlet.required",*/ "Firstname is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", /*"lastName.outlet.required",*/ "Lastname is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", /*"password.outlet.required",*/ "Password is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", /*"confirmPassword.outlet.required",*/ "Confirm password is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", /*"gender.outlet.required",*/ "Gender is required");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dob", /*"dob.outlet.required",*/ "Date of birth is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.mobile", /*"resourceBean.addressBean.mobile.outlet.required",*/ "Mobile number is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.email", /*"resourceBean.addressBean.email.outlet.required",*/ "Emailid is required");
		
		OutletBean outletBean=(OutletBean) object;
		System.out.println("*****outletBean****** "+outletBean.toString());
		
		if(!(outletBean.getConfirmPassword().isEmpty() && outletBean.getConfirmPassword()!=null)){
			if(!outletBean.getPassword().equals(outletBean.getConfirmPassword())){
				errors.rejectValue("confirmPassword", /*"confirmPassword.outlet.notmatch",*/ "Password not match");
			}
		}
		if(!(outletBean.getFirstName().isEmpty() && outletBean.getFirstName()!=null)){
			pattern=Pattern.compile(STRING_PATTERN);
			matcher=pattern.matcher(outletBean.getFirstName());
					if(!matcher.matches())
					{
						errors.rejectValue("firstName", /*"firstName.outlet.containNonChar",*/ "Enter a valid name");
					}
		}
		if(!(outletBean.getLastName().isEmpty() && outletBean.getLastName()!=null))
		{
			pattern=Pattern.compile(STRING_PATTERN);
			matcher=pattern.matcher(outletBean.getLastName());
					if(!matcher.matches())
					{
						errors.rejectValue("lastName", /*"lastName.outlet.containNonChar",*/ "Enter a valid name");
					}
		}
		if(!(outletBean.getResourceBean().getAddressBean().getEmail()!= null && outletBean.getResourceBean().getAddressBean().getEmail().isEmpty())){
			pattern=pattern.compile(EMAIL_PATTERN);
			matcher=pattern.matcher(outletBean.getResourceBean().getAddressBean().getEmail());
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.email",/*"addressBean.outlet.email.incorrect",*/ "Enter correct email");
			}
		}
		if((outletBean.getResourceBean().getAddressBean().getMobile()!= null))
		{
			String s = String.valueOf(outletBean.getResourceBean().getAddressBean().getMobile());
			pattern=pattern.compile(MOBILE_PATTERN);
			matcher=pattern.matcher(s);
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.mobile", /*"addressBean.outlet.mobile.incorrect",*/ "Enter correct mobile number");
			}
		}
		String mobileNumber = String.valueOf(outletBean.getResourceBean().getAddressBean().getMobile());
		if(!(mobileNumber.equals(null)&&mobileNumber.isEmpty()))
		{
//			String s = String.valueOf(outletBean.getResourceBean().getAddressBean().getMobile());
			pattern=Pattern.compile(MOBILE_PATTERN);
			matcher=pattern.matcher(mobileNumber);
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.mobile", /*"addressBean.mobile.incorrect",*/ "Mobile number should contains only 10 - 13 numbers");
			}
		}
		if(!(mobileNumber.equals(null)&&mobileNumber.isEmpty()))
		{
//			String s = String.valueOf(outletBean.getResourceBean().getAddressBean().getMobile());
			pattern=Pattern.compile(ID_PATTERN);
			matcher=pattern.matcher(mobileNumber);
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.mobile", /*"addressBean.mobile.incorrect",*/ "Mobile number should allow only numaric values");
			}
		}
		if(!(outletBean.getUserName()!=null && outletBean.getUserName().isEmpty())){
			if(outletBean.getUserName().length()<5 || outletBean.getUserName().length()>12 ){
				errors.rejectValue("userName", /*"userName.outlet.length",*/ "Username should be between 5 to 12 characters");
			}
		}
		if(!(outletBean.getPassword()!=null && outletBean.getPassword().isEmpty())){
			if(outletBean.getPassword().length()<6 || outletBean.getPassword().length()>15)
			{
				errors.rejectValue("password", /*"password.outlet.length",*/ "password should be between 6 to 15 characters");
			}
		}
	}
}
