package com.relyits.rmbs.dao;

import java.util.List;

import com.relyits.rmbs.model.consumer.CustomerModel;


public interface ConsumerDAO {
	public List<CustomerModel> getCustomer(CustomerModel customerModel);
	
	public CustomerModel getCustomerById(CustomerModel customerModel);
}
