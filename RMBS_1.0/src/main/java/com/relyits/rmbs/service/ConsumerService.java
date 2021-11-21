package com.relyits.rmbs.service;


import java.util.List;

import com.relyits.rmbs.model.consumer.CustomerModel;


public interface ConsumerService {

	public List<CustomerModel> getCustomer(CustomerModel customerModel);
	
	public CustomerModel getCustomerById(CustomerModel customerModel);
}
