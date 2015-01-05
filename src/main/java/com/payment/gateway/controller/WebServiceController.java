package com.payment.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.payment.gateway.service.RestService;

@Controller
public class WebServiceController {

	@Autowired
	private RestService service;

	public void setService(RestService service) {
		this.service = service;
	}

	@RequestMapping("/validate")
	public @ResponseBody String Validate(
			@RequestParam("ccn") String creditCardNumber,
			@RequestParam("amount") int amount) {

		CreditCard creditCard = null;
		List<CreditCard> cards = service.checkCard(creditCardNumber);
		
		if (cards.size() != 0) {
			creditCard = cards.get(0);

			if (creditCard.getAmount() > amount) {
				creditCard.setAmount(creditCard.getAmount() - amount);
				service.updateCreditCard(creditCard);

				return "y";
			}
		}
		return "n";
	}
}
