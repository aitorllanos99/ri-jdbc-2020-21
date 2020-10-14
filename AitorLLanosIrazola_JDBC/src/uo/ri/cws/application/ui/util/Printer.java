package uo.ri.cws.application.ui.util;

import java.util.List;

import alb.util.console.Console;
import uo.ri.cws.application.business.invoice.InvoiceDto;
import uo.ri.cws.application.business.mechanic.MechanicDto;

public class Printer {

	public static void printMechanic(MechanicDto m) {

		Console.printf("\t%s %-10.10s %-25.25s %-25.25s%n",  
				m.id
				, m.dni
				, m.name
				, m.surname
				);
	}

	public static void printMechanics(List<MechanicDto> list) {

		Console.printf("\t%-36s %-10.10s %-25.25s %-25.25s%n",  
				"Mechanic identifier"
				, "DNI"
				, "Name"
				, "Surname"
				);
		for (MechanicDto m : list )
			printMechanic(m);
	}
	
	public static void printInvoice(InvoiceDto invoice) {

		double importeConIVa = invoice.total;
		double iva =  invoice.vat;
		double importeSinIva = importeConIVa / (1 + iva / 100);

		Console.printf("Invoice number: %d%n", 				invoice.number );
		Console.printf("\tDate: %1$td/%1$tm/%1$tY%n", 	invoice.date);
		Console.printf("\tTotal: %.2f %n", 			importeSinIva);
		Console.printf("\tVat: %.1f %% %n", 			invoice.vat );
		Console.printf("\tTotal (vat included): %.2f %n", 	invoice.total );
		Console.printf("\tStatus: %s%n", 				invoice.status );
	}
	

}
