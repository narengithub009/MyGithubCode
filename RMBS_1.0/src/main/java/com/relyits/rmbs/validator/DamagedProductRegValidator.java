package com.relyits.rmbs.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.relyits.rmbs.beans.product.ProductDamageBean;

public class DamagedProductRegValidator implements Validator{
	String s1;
	private Pattern pattern;
	private Matcher matcher;
	String NUM_PATTERN="[0-9]+";
	@Override
	public boolean supports(Class cls) {
		
		return ProductDamageBean.class.isAssignableFrom(cls);
	}
	public String toString(){
		System.out.println(s1);
		return s1;
		
	}
	@Override
	public void validate(Object object, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productInventoryBean.productBean.name", "Please enter productname");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productInventoryBean.id", "Please enter batchNo");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "Please enter quantity");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "damagedDate", "Please enter damaged date");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reasonBean.reason", "Please type the reason");
		ProductDamageBean damageBean = (ProductDamageBean)object;
		s1 = String.valueOf(damageBean.getQuantity());
		if(!(s1.equals(null)&& s1.isEmpty())){
			String s = String.valueOf(damageBean.getQuantity());
			pattern = pattern.compile(NUM_PATTERN);
			matcher = pattern.matcher(s);
			if(!matcher.matches()){
				
				errors.rejectValue("quantity", "Please enter quantity as numeric value");
			}
		}
	}
}
