package com.relyits.rmbs.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.relyits.rmbs.beans.registration.RegistrationBean;

public class ChangePasswordValidator implements Validator{
	
	private Pattern pattern;
	private Matcher matcher;
	private static final String PWD_PATTREN= "((?=.*\\d)(?=.*[a-zA-Z])(?=.*[~'!@#$%?\\\\/&*\\]|\\[=()}\"{+_:;,.><'-])).{8,}";
	@Override
	public boolean supports(Class cls) {
		
		return RegistrationBean.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object object, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Please enter current password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassWord", "Please enter new password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "Please enter confirm password");
		
		RegistrationBean bean = (RegistrationBean)object;
		
		if(!(bean.getNewPassWord()!=null && bean.getNewPassWord().isEmpty())){
			pattern = Pattern.compile(PWD_PATTREN);
			matcher = pattern.matcher(bean.getNewPassWord());
			if(!matcher.matches()){
				errors.rejectValue("newPassWord","Password should  contains one special character and character and number");
			}
		}
		if(!(bean.getNewPassWord()!=null && bean.getNewPassWord().isEmpty())&&!(bean.getConfirmPassword()!=null&& bean.getConfirmPassword().isEmpty())){
			if(!bean.getNewPassWord().equals(bean.getConfirmPassword())){
				errors.rejectValue("confirmPassword","Password did not matched");
			}
		}
	}

}
