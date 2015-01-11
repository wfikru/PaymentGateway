package com.payment.gateway.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.payment.gateway.service.RestService;
import com.payment.gateway.service.encryptionService;

@Controller
public class WebServiceController {

	@Autowired
	private RestService service;

	@Autowired
	private encryptionService encryptor;
	
	public void setService(RestService service) {
		this.service = service;
	}

	@RequestMapping("/team6_paymentgateway")
	public @ResponseBody String Validate(
			@RequestParam("ccn") String encCreditCardNumber,
			@RequestParam("amount") double amount) {

		String creditCardNumber = null;
		try {
			creditCardNumber = encryptor.decrypt(encCreditCardNumber);
			System.out.print(creditCardNumber+" #######################");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
				
		CreditCard creditCard = null;
		List<CreditCard> cards = service.checkCard(creditCardNumber);
		
		if (cards.size() != 0) {
			creditCard = cards.get(0);

			if (creditCard.getAmount() > amount) {
				creditCard.setAmount((creditCard.getAmount() - amount));
				service.updateCreditCard(creditCard);

				return "y";
			}
			else {return "n";}
		}
		return "N";
	}
}
