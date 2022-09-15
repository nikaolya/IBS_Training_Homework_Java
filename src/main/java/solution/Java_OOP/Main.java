package solution.Java_OOP;

import java.util.Scanner;

public class Main {
	private static final Scanner scanner = new Scanner(System.in);
	private static IBox<Sweet> present = new BoxImplementation();

	public static void main(String[] args) {

		String action = "help";
		printInstructions();
		while (!(action.equals("quit"))) {
			System.out.print("\nEnter your desired action: ");
			action = scanner.nextLine().toLowerCase();

			switch (action) {
				case "help":
					printInstructions();
					break;
				case "add":
					addNewItem();
					break;
				case "remove":
					removeItem();
					break;
				case "weight":
					getTotalWeight();
					break;
				case "price":
					getTotalPrice();
					break;
				case "content":
					printPresentContent();
					break;
				case "optimize":
					optimize();
					break;
				case "quit":
					break;
				default:
					System.out.println("Illegal command. Please try again.");
			}
		}
	}

	public static void printInstructions(){
		System.out.println("\nHelp: Please type");
		System.out.println("\t \"help\" - To print action options;");
		System.out.println("\t \"add\" - To add an item;");
		System.out.println("\t \"remove\" - To remove an item;");
		System.out.println("\t \"weight\" - To get current total weight;");
		System.out.println("\t \"price\" - To get current total price;");
		System.out.println("\t \"content\" - To get information about items included;");
		System.out.println("\t \"optimize\" - To optimize present content either by weight or price;");
		System.out.println("\t \"quit\" - To quit the program.");
	}

	public static void addNewItem(){
		System.out.print("Sweet to add (caramel, chocolate, marmalade): ");
		try{
			SweetName newSweetName = SweetName.valueOf(scanner.nextLine().toUpperCase());
			switch (newSweetName) {
				case CARAMEL:
					present.addSweet(new Caramel());
					break;
				case CHOCOLATE:
					present.addSweet(new Chocolate());
					break;
				case MARMALADE:
					present.addSweet(new Marmalade());
					break;
				default:
			}
		} catch (IllegalArgumentException e){
			System.out.println("Illegal sweet name.");
		}
	}

	public static void removeItem(){
		System.out.print("Sweet to remove (caramel, chocolate, marmalade): ");
		try{
			SweetName sweetToRemove = SweetName.valueOf(scanner.nextLine().toUpperCase());
			present.removeSweet(sweetToRemove);
		} catch (IllegalArgumentException e){
			System.out.println("Illegal sweet name.");
		}
	}

	public static double getTotalWeight(){
		return present.getTotalWeight();
	}

	public static int getTotalPrice(){
		return present.getTotalPrice();
	}

	public static void printPresentContent(){
		present.printBoxContent();
	}

	public static void optimize(){
		System.out.println("Weight limit: ");
		double maxWeight = Double.parseDouble(scanner.nextLine());
		System.out.println("Optimization mode (weight, price): ");
		try{
			OptimizeBy mode = OptimizeBy.valueOf(scanner.nextLine().toUpperCase());
			present.optimize(maxWeight, mode);
		} catch (IllegalArgumentException e){
			System.out.println("Illegal optimization mode.");
		}
	}
}
