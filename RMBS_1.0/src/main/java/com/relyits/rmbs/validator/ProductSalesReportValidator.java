package com.relyits.rmbs.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.relyits.rmbs.beans.product.ProductReportBean;

public class ProductSalesReportValidator implements Validator{

	@Override
	public boolean supports(Class cls) {
		return ProductReportBean.class.isAssignableFrom(cls);	
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "initial", "Please select reportsOn dropdown");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "Please select product name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "Please select category dropdown");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "initial", "Please select reportsOn dropdown");
		
	}
}
