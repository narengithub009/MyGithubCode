package com.relyits.rmbs.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.relyits.rmbs.beans.registration.BranchBean;

public class BranchRegistrationValidator implements Validator{

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
		return BranchBean.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", /*"userName.branch.required",*/ "Username is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", /*"firstName.branch.required",*/ "FirstName is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", /*"lastName.branch.required",*/ "LastName is required1");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", /*"password.branch.required",*/ "Password is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", /*"confirmPassword.branch.required",*/ "ConfirmPassword is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dob", /*"dob.branch.required",*/ "Date of birth is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", /*"name.branch.required",*/ "Shopname is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tinNo", /*"tinNo.branch.required",*/ "Tinno is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.address", /*"addressBean.address.branch.required",*/ "Address is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.mobile", /*"addressBean.mobile.branch.required",*/ "Mobile number is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.email", /*"addressBean.email.branch.required",*/ "Emailid is required");

		BranchBean branchBean=(BranchBean) object;
		if(!(branchBean.getUserName()!=null && branchBean.getUserName().isEmpty())){
			if(branchBean.getUserName().length()<5 || branchBean.getUserName().length()>12 ){
				errors.rejectValue("userName", /*"userName.branch.length",*/ "Username should be between 5 to 12 characters");
			}
		}
		if(!(branchBean.getPassword()!=null && branchBean.getPassword().isEmpty())){
			if(branchBean.getPassword().length()<6 || branchBean.getPassword().length()>15)
			{
				errors.rejectValue("password", /*"password.branch.length",*/ "password should be between 6 to 15 characters");
			}
		}
		if(!branchBean.getPassword().equals(branchBean.getConfirmPassword()))
		{
			errors.rejectValue("confirmPassword", /*"confirmPassword.branch.notmatch",*/ "password not match");
		}
		if(!(branchBean.getFirstName()!=null && branchBean.getFirstName().isEmpty()))
		{
			pattern=Pattern.compile(STRING_PATTERN);
			matcher=pattern.matcher(branchBean.getFirstName());
			if(!matcher.matches())
			{
				errors.rejectValue("firstName", /*"firstName.branch.containNonChar",*/  
						"Enter a valid name");  
			}
		}
		if(!(branchBean.getLastName()!=null && branchBean.getLastName().isEmpty()))
		{
			pattern=Pattern.compile(STRING_PATTERN);
			matcher=pattern.matcher(branchBean.getLastName());
			if(!matcher.matches())
			{
				errors.rejectValue("lastName", /*"lastName.branch.containNonChar",*/  
						"Enter a valid name");   
			}
		}
		if(!(branchBean.getResourceBean().getAddressBean().getEmail()!= null && branchBean.getResourceBean().getAddressBean().getEmail().isEmpty())){
			pattern=pattern.compile(EMAIL_PATTERN);
			matcher=pattern.matcher(branchBean.getResourceBean().getAddressBean().getEmail());
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.email",/*"addressBean.branch.email.incorrect",*/ "Enter correct email");
			}
		}
		
		String mobileNumber = String.valueOf(branchBean.getResourceBean().getAddressBean().getMobile());
		if(!(mobileNumber.equals(null)&&mobileNumber.isEmpty()))
		{
//			String s = String.valueOf(branchBean.getResourceBean().getAddressBean().getMobile());
			pattern=Pattern.compile(MOBILE_PATTERN);
			matcher=pattern.matcher(mobileNumber);
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.mobile", /*"addressBean.mobile.incorrect",*/ "Mobile number should contains only 10 - 13 numbers");
			}
		}
		if(!(mobileNumber.equals(null)&&mobileNumber.isEmpty()))
		{
//			String s = String.valueOf(branchBean.getResourceBean().getAddressBean().getMobile());
			pattern=Pattern.compile(ID_PATTERN);
			matcher=pattern.matcher(mobileNumber);
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.mobile", /*"addressBean.mobile.incorrect",*/ "Mobile number should allow only numaric values");
			}
		}
		if(!(branchBean.getTinNo()!=null && branchBean.getTinNo().isEmpty()))
		{
			pattern=pattern.compile(NUM_PATTERN);
			matcher=pattern.matcher(branchBean.getTinNo());
			if(!matcher.matches())
			{
				errors.rejectValue("tinNo", /*"tin.branch.containNonNum",*/ "tin number should contain numbers");
			}
		}
		if(!(branchBean.getName()!= null && branchBean.getName().isEmpty()))
		{
			pattern=pattern.compile(STRING_PATTERN);
			matcher=pattern.matcher(branchBean.getName());
			if(!matcher.matches())
			{
				errors.rejectValue("name", /*"name.branch.shopNameNonNum",*/ "Shopname should contain characters");
			}
		}
	}

}
