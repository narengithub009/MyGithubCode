package com.relyits.rmbs.validator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.relyits.rmbs.beans.registration.DoctorBean;
import com.relyits.rmbs.beans.registration.RegistrationBean;

public class DoctorRegistrationValidator implements Validator{
	String doctorSuccess = null;
	@Value("${message.registerDoctorSuccess}") String doctorSuccess1;
	Map<String, String> properties= null;
	public Map<String, String> initializeProperties(){

		
		doctorSuccess = doctorSuccess1;


		return properties;
	}
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";  
	String ID_PATTERN = "[0-9]+";  
	String STRING_PATTERN = "[a-zA-Z]+";  
	String MOBILE_PATTERN = "[0-9]{10,13}";  

	@Override
	public boolean supports(Class cls) {
		return DoctorBean.class.isAssignableFrom(cls);
	}
	@Override
	public void validate(Object target, Errors errors) 
	{
		initializeProperties();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "doctorName", /*"doctorName.required",*/ doctorSuccess);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "qualification", /*"qualification.required",*/ "Qualification is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "specialization", /*"specialization.required",*/ "Specialization is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hospitalName", /*"hospitalName.required",*/ "HospitalName is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "initial", /*"initial.required",*/ "Initial is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.mobile", /*"addressBean.mobile.required",*/ "Mobile number is required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.email", /*"addressBean.email.required",*/ "EmailId is  required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceBean.addressBean.address", /*"addressBean.address.required",*/ "Address is required");


		DoctorBean doctorBean = (DoctorBean) target;
		
		if (!(doctorBean.getDoctorName() != null && doctorBean.getDoctorName().isEmpty())) {  
			pattern = Pattern.compile(STRING_PATTERN);  
			matcher = pattern.matcher(doctorBean.getDoctorName() );  
			if (!matcher.matches()) {  
				errors.rejectValue("doctorName", /*"doctorName.containNonChar",*/  
						"Doctor name should contain only characters");  
			}  
		}
		if(doctorBean.getInitial()=="--select--"){
			
			errors.rejectValue("initial", "Please select gender");
			
		}
		if(!(doctorBean.getResourceBean().getAddressBean().getEmail()!= null && doctorBean.getResourceBean().getAddressBean().getEmail().isEmpty())){
			pattern=Pattern.compile(EMAIL_PATTERN);
			matcher=pattern.matcher(doctorBean.getResourceBean().getAddressBean().getEmail());
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.email",/*"addressBean.email.incorrect",*/ "Enter correct email");
			}
		}
		String mobileNumber = String.valueOf(doctorBean.getResourceBean().getAddressBean().getMobile());
		if(!(mobileNumber.equals(null)&&mobileNumber.isEmpty()))
		{
//			String s = String.valueOf(doctorBean.getResourceBean().getAddressBean().getMobile());
			pattern=Pattern.compile(MOBILE_PATTERN);
			matcher=pattern.matcher(mobileNumber);
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.mobile", /*"addressBean.mobile.incorrect",*/ "Mobile number should contains only 10 - 13 numbers");
			}
		}
		if(!(mobileNumber.equals(null)&&mobileNumber.isEmpty()))
		{
//			String s = String.valueOf(doctorBean.getResourceBean().getAddressBean().getMobile());
			pattern=Pattern.compile(ID_PATTERN);
			matcher=pattern.matcher(mobileNumber);
			if(!matcher.matches())
			{
				errors.rejectValue("resourceBean.addressBean.mobile", /*"addressBean.mobile.incorrect",*/ "Mobile number should allow only numaric values");
			}
		}
		
	}
}
