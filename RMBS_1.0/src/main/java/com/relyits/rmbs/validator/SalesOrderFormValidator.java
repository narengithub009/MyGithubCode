package com.relyits.rmbs.validator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.relyits.rmbs.beans.sales.SalesLineItemsBean;
import com.relyits.rmbs.beans.sales.SalesOrderBean;

public class SalesOrderFormValidator implements Validator{
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String NUM_PATTREN = "^[1-9]([0-9]*,?)*$";
	private static final String DEC_PATTREN = "^\\d*(\\.\\d{1,4})?$";
	int medical,tablet;
	@Value("${category.medical}") String categorymedical;
	@Value("${TABLET}") String subCategoryTab;
	Map<String, String> properties= null;
	public Map<String, String> initializeProperties(){

		medical=Integer.parseInt(categorymedical);
		tablet=Integer.parseInt(subCategoryTab);
		return properties;
	}

	@Override
	public boolean supports(Class cls) {
		
		return SalesLineItemsBean.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object object, Errors errors) {
		SalesLineItemsBean orderBean = (SalesLineItemsBean)object;
		if(orderBean.getProductInventoryBean().getProductBean().getCategoryBean().getId()==medical && orderBean.getProductInventoryBean().getProductBean().getSubCategoryBean().getId()==tablet){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agencyBean.agencyName", "Please enter agency name");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "batchNo", "Please enter batchno");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productBean.name", "Please enter product name");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productBean.mFCompanay", "Please enter Manufacturing company");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Please enter price");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dlPrice", "Please enter dealer price");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "Please enter quantity");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expiryDate", "Please enter expiryDate");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "noOfstrips", "Please enter strips");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityPerStrip", "Please enter quantity per strip");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pricePerStrip", "Please enter price per strips");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dlPricePerstrip", "Please enter dl strip price");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "discount", "Please enter discount");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vat", "Please enter vat");

			
			String s1 = String.valueOf(orderBean.getQuantity());
			String s3 = String.valueOf(orderBean.getProductInventoryBean().getNoOfstrips());
			String s4 = String.valueOf(orderBean.getProductInventoryBean().getQuantityPerStrip());
			if((s1!=null && !s1.equals("0"))||(s3!=null && !s3.equals("0"))||(s4!=null && !s4.equals("0"))){
				pattern = Pattern.compile(NUM_PATTREN);
//				String s = String.valueOf(inventoryBean.getQuantity());
				matcher=pattern.matcher(s1);
				if(!matcher.matches()){
					errors.rejectValue("price", "Please enter quantity as non-negative value");
				}
				matcher=pattern.matcher(s1);
				if(!matcher.matches()){
					errors.rejectValue("noOfstrips", "Please enter noOfstrips as non-negative value");
				}
				matcher=pattern.matcher(s1);
				if(!matcher.matches()){
					errors.rejectValue("quantityPerStrip", "Please enter quantityPerStrip as non-negative value");
				}
			}
			
			String s2 = String.valueOf(orderBean.getProductInventoryBean().getPricePerStrip());
			if(s2!=null && !s2.equals("0")){
				pattern = Pattern.compile(DEC_PATTREN);
//				String s = String.valueOf(inventoryBean.getPricePerStrip());
				matcher=pattern.matcher(s2);
				if(!matcher.matches()){
					errors.rejectValue("price", "Please enter price as non-negative value");
				}
			}
			}else{
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agencyBean.agencyName", "Please enter agency name");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "batchNo", "Please enter batchno");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productBean.name", "Please enter product name");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productBean.mFCompanay", "Please enter Manufacturing company");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "Please enter price");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dlPrice", "Please enter dealer price");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "Please enter quantity");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expiryDate", "Please enter expiryDate");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "discount", "Please enter discount");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vat", "Please enter vat");
	
				String s1 = String.valueOf(orderBean.getQuantity());
				if(s1!=null && !s1.equals("0")){
					pattern = Pattern.compile(NUM_PATTREN);
//					String s = String.valueOf(inventoryBean.getQuantity());
					matcher=pattern.matcher(s1);
					if(!matcher.matches()){
						errors.rejectValue("price", "Please enter quantity as non-negative value");
					}
				}
				String s2 = String.valueOf(orderBean.getProductInventoryBean().getPrice());
				if(s2!=null && !s2.equals("0")){
					pattern = Pattern.compile(DEC_PATTREN);
//					String s = String.valueOf(inventoryBean.getPrice());
					matcher=pattern.matcher(s2);
					if(!matcher.matches()){
						errors.rejectValue("price", "Please enter price as non-negative value");
					}
				}
			}
		
	}

}
