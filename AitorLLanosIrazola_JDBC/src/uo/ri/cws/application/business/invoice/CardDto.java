package uo.ri.cws.application.business.invoice;

import java.time.LocalDate;

public class CardDto extends PaymentMeanDto {

	public String cardNumber;
	public LocalDate cardExpiration;
	public String cardType;
}

