package com.payment.gateway.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.gateway.controller.CreditCard;


@Service
@Transactional
public class RestService {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<CreditCard> checkCard(String ccn) {

		Session session = this.sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(CreditCard.class).add(
				Restrictions.eq("creditCardNumber", ccn));
		List<CreditCard> cards = criteria.list();
		return cards;
	}
	
	
	public void updateCreditCard(CreditCard c) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(c);
	}
}
