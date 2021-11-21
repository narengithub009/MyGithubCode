package com.relyits.rmbs.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.relyits.rmbs.dao.ConsumerDAO;
import com.relyits.rmbs.model.consumer.CustomerModel;


@Repository("ConsumerDAO")
public class ConsumerDAOImpl implements ConsumerDAO{

	@Autowired
	private SessionFactory sessionFactory;

	
	@SuppressWarnings("unchecked")
	public List<CustomerModel> getCustomer(CustomerModel customerModel) {
		
		Session session=sessionFactory.openSession();
		Criteria customerCriteria=session.createCriteria(CustomerModel.class,"customer")
				.createAlias("customer.addressModel", "address")
				.add(Restrictions.eq("address.mobile",customerModel.getAddressModel().getMobile()));
		
		return customerCriteria.list();
	}

	public CustomerModel getCustomerById(CustomerModel customerModel) {
		
		Session session=sessionFactory.openSession();
		CustomerModel customerModel2=(CustomerModel) session.get(CustomerModel.class, customerModel.getId());
		session.close();
		return customerModel2;
	}
	

}
