package com.relyits.rmbs.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relyits.rmbs.dao.ConsumerDAO;
import com.relyits.rmbs.model.consumer.CustomerModel;
import com.relyits.rmbs.service.ConsumerService;

@Service("ConsumerService")
public class ConsumerServiceImpl implements ConsumerService{

	@Autowired
	private ConsumerDAO consumerDAO;

	
	public List<CustomerModel> getCustomer(CustomerModel customerModel) {
		return consumerDAO.getCustomer(customerModel);
	}
	
	public CustomerModel getCustomerById(CustomerModel customerModel){
		return consumerDAO.getCustomerById(customerModel);
	}

}
