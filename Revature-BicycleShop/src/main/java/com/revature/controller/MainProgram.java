package com.revature.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.revature.beans.*;
import com.revature.services.*;

public class MainProgram {
	//note that as time goes on, this will be phased out for the database
	
	private static UserService userActions = new UserFunctions();
	private static EmployeeService employeeActions = new EmployeeFunctions();
	private static CustomerService customerActions = new CustomerFunctions();
	
	private static Customer currentCustomer;
	private static Employee currentEmployee;
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		loginRegistrationLoop(scanner);
		
		scanner.close();
	}
	
	private static void loginRegistrationLoop(Scanner scanner) {
		String option = "";
		
		while (true) {
			System.out.println("Welcome to Punk-Sure, the best Earthbound bike shop around!");
			System.out.println("Press 1 to register or 2 to log in. Press anything else to quit.");
			option = scanner.nextLine();
			
			if (option.equals("1")) {
				register(scanner);
			}
			else if (option.equals("2")) {
				System.out.print("Username: ");
				String username = scanner.nextLine();
				System.out.print("Password: ");
				String password = scanner.nextLine();
				User couldLogIn = userActions.validatePotentialUser(username, password);
				if (couldLogIn != null) {
					System.out.println("You have logged in.");
					if (couldLogIn instanceof Customer) {
						currentCustomer = (Customer) couldLogIn;
						customerActions.addCustomerization(currentCustomer);
						//System.out.println(currentCustomer.toString());
						customerMenuLoop(scanner);
					}
					else if (couldLogIn instanceof Employee) {
						currentEmployee = (Employee) couldLogIn;
						//System.out.println(currentEmployee.toString());
						employeeMenuLoop(scanner);
					}

					continue;
				}
				else {
					System.out.println("Incorrect username/password combo.\n");
				}
			}
			else {
				System.out.println("Thank you for using Punk-Sure.");
				break;
			}
			
		}
	}
	
	private static void register(Scanner scanner) {
		while (true) {
			System.out.print("Please enter your username: ");
			String newUsername = scanner.nextLine();
			System.out.print("Please enter your password: ");
			String newPassword = scanner.nextLine();
			
			System.out.println("Is this what you wanted? (Username: " + newUsername + "  Password: " + newPassword + ")");
			System.out.println("Press 1 to register. Press 2 to redo. Anything else to cancel.");
			String option = scanner.nextLine();
			
			if (option.equals("1")) {
				Customer newCustomer = new Customer(newUsername, newPassword);
				userActions.customerRegistration(newUsername, newPassword);
				System.out.println("You have been registered. Returning to the main menu...");
				break;
			}
			else if (option.equals("2")) {
				System.out.println("Sure. Make sure to think about choosing a strong password.");
			}
			else {
				System.out.println("Returning to the main menu.");
				break;
			}
		}
	}
	
	private static void customerMenuLoop(Scanner scanner) {
		//Set<Bicycle> availableBicycles = customerActions.viewAllAvailableBicycles();
		//Set<Bicycle> yourBicycles = customerActions.viewBicyclesYouOwn();
		//Set<Offer> yourOffers = customerActions.viewOffersYouMade();
		
		customerMenu: while(true) {
			System.out.println("Welcome, " + currentCustomer.getUsername());
			System.out.println("Please choose an option:");
			System.out.println("1: Make an offer for a bicycle.");
			System.out.println("2: Look at all available bicycles.");
			System.out.println("3: View bicycles I own.");
			System.out.println("4: View pending payments for bicycles I would like to buy.");
			System.out.println("q: Log out (quit).");
			String option = scanner.nextLine();
			
			Set<Bicycle> availableBicycles = customerActions.viewAllAvailableBicycles();
			Set<Bicycle> yourBicycles = customerActions.viewBicyclesYouOwn();
			Set<Offer> yourOffers = customerActions.viewOffersYouMade();
						
			System.out.println();
			switch (option) {
				case "1":
					System.out.println("Which bicycle would you like to make an offer on?");
					//int bicycleIndex = 1;
					for (Bicycle bicycle: availableBicycles) {
						System.out.print((bicycle.getStatus().equals("available")) ? bicycle.getId() + ": " + bicycle.getBikeModel() + " " + bicycle.getBikeType() + "\n" : "");
					
					}
					String bikeOption = scanner.nextLine();
					int bikeOptionAsNum = Integer.parseInt(bikeOption); //handle the NumberFormatException
					
					
					System.out.println("How much do you want to offer for the bike?");
					String potentialOffer = scanner.nextLine();
					double offer;
					offer = Double.parseDouble(potentialOffer);		//handle the NullPointerException and the NumberFormatException found here.
					
					Offer offerObject = customerActions.createOfferObject(bikeOptionAsNum, offer);
					offerObject.setOfferMaker(currentCustomer);
					int offerID = customerActions.makeAnOffer(offerObject);
					offerObject.setId(offerID);
					
					Bicycle bikeChosen = offerObject.getBicycleToBeSold();
					System.out.println("You have offered $" + offer + " for the " + bikeChosen.getBikeModel() + " " + bikeChosen.getBikeType() + " that was placed by " + bikeChosen.getSeller().getUsername());
					break;
				case "2":
					System.out.println("Here are all of the bicycles that you can make offers for: \n");
					for (Bicycle bicycle: availableBicycles) {
						System.out.println((bicycle.getStatus().equals("available")) ? bicycle + "\n" : "");
					}
					break;
				case "3":
					System.out.println("Here are all of the bicycles you own:\n");
					for (Bicycle bicycle: yourBicycles) {
						System.out.println(bicycle + "\n");
					}
					break;
				case "4":
					System.out.println("Here are all of the bicycles you have yet to pay for:");
					for (Offer o: yourOffers) {
						if (o.getStatus().equals("accepted")) {
							System.out.println("You must pay $" + o.getOffer() + " for the " + o.getBicycleToBeSold().getBikeModel() + " " + o.getBicycleToBeSold().getBikeType());
						}
					}
					break;
				case "q":
					System.out.println("Logging out. Goodbye...");
					break customerMenu;
				default:
					System.out.println("");
			}
			
			System.out.println();
		}
	}
	
	private static void employeeMenuLoop(Scanner scanner) {

		
		employeeMenu: while (true) {
			System.out.println("Welcome, " + currentEmployee.getUsername());
			System.out.println("Please choose an option:");
			System.out.println("1. Add a bicycle");
			System.out.println("2. Accept or reject an offer for a bicycle you have posted");
			System.out.println("3. Remove a bicycle");
			System.out.println("4. View all payments");
			System.out.println("q: Log out (quit)");
			
			String option = scanner.nextLine();
			
			Set<Offer> allOffers = employeeActions.getAllOffers();
			Set<Bicycle> allBicycles = employeeActions.getAllBicycles();
			
			switch(option) {
				case "1":
					System.out.println("Please enter the name of the model of bike.");
					String bikeModel = scanner.nextLine();
					System.out.println("Please enter the name of the type and name of bike.");
					String bikeType = scanner.nextLine();
					System.out.println("Please describe the bike well.");
					String bikeDescription = scanner.nextLine();
					System.out.println("Please list a price for the bike.");
					double bikePrice = Double.parseDouble(scanner.nextLine());
					
					Bicycle newBicycle = new Bicycle(bikeModel, bikeType, bikeDescription, currentEmployee, bikePrice);
					
					employeeActions.addABicycle(newBicycle);
					break;
				case "2":
					System.out.println("Please choose an offer.");
					
					for (Offer offer: allOffers) {
						System.out.println(offer.getId() + ". $" + offer.getOffer() + " for the " + offer.getBicycleToBeSold().getBikeModel() + " " + offer.getBicycleToBeSold().getBikeType());
					}
					String offerOption = scanner.nextLine();
					int offerOptionAsNum = Integer.parseInt(offerOption); //handle the NumberFormatException
					
					
					System.out.println("Will you accept it or reject it? Type \"accept\" to accept or \"reject\" to reject.");
					String acceptRejectOption = scanner.nextLine();
					
					if (acceptRejectOption.equals("accept")) {
						employeeActions.acceptAnOffer(offerOptionAsNum);
						System.out.println("You have successfully accepted offer #" + offerOptionAsNum + ".\n");
					}
					else if (acceptRejectOption.equals("reject")) {
						employeeActions.rejectAnOffer(offerOptionAsNum);
						System.out.println("You have successfully rejected offer #" + offerOptionAsNum + ".\n");
					}
					else {
						//nothing has happened with the thing is strange.
						System.out.println("You haven't done anything.\n");
					}
					break;
				case "3":
					System.out.println("Please choose a bike to remove.");
					for (Bicycle bicycle: allBicycles) {
						System.out.println(bicycle.getId()+ ". " + bicycle.getBikeModel() + bicycle.getBikeType());
					}
					
					String bikeOption = scanner.nextLine();
					int bikeOptionAsNum = Integer.parseInt(bikeOption); //handle the NumberFormatException
					
					employeeActions.removeABicycle(bikeOptionAsNum);
					break;
				case "4":
					System.out.println("Here are all the payments people will have to make:");
					for (Offer offer: allOffers) {
						System.out.print((offer.getStatus() == "accepted") ? offer.getOfferMaker().getUsername() + " will have to pay " + offer.getOffer() + " for " + offer.getBicycleToBeSold().getBikeModel() + " " + offer.getBicycleToBeSold().getBikeType() + "\n" : "");
					}
					break;
				case "q":
					System.out.println("Logging out. Goodbye...");
					break employeeMenu;
			}
		}
	}
	
}
