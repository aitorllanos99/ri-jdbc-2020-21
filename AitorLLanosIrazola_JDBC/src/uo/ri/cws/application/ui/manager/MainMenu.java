package uo.ri.cws.application.ui.manager;

import alb.util.menu.BaseMenu;
import uo.ri.cws.application.ui.manager.spares.SparePartsManagementMenu;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Administrator", null },
			{ "Mechanics management", 			MechanicMenu.class }, 
			{ "Spare parts management", 			SparePartsManagementMenu.class },
			{ "Vehicle types management", 	VehicleTypeMenu.class },
		};
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}

}
