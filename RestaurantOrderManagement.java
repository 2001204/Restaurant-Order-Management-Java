

import java.util.Scanner;
import java.util.ArrayList;


public class RestaurantOrderManagement {
    public static void main(String[] args)
{

        MenuItem[] menuItems = new MenuItem[] {
            new Appetizer(1, "Salad", 99.99, 150),
            new MainCourse(2, "Burger", 210.75, 500),
            new Dessert(3, "Cake", 549.99, "Chocolate"),
        };

        OrderManagementSystem orderSystem = new OrderManagementSystem(menuItems);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu-Driven Order System");
            System.out.println("1. View Menu");
            System.out.println("2. Display Special Offers");
            System.out.println("3. Add Item to Order");
            System.out.println("4. Display Order");
            System.out.println("5. Generate Bill");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    orderSystem.viewMenu();
                    break;
                case 2:
                	orderSystem.displaySpecialOffers();
                	break;
                case 3:
                    System.out.print("Enter the item number to add to the order: ");
                    int itemNumber = scanner.nextInt();
                    if (itemNumber >= 1 && itemNumber <= menuItems.length) {
                        orderSystem.addToOrder(menuItems[itemNumber - 1]);
                        System.out.println("Item added to the order.");
                    } else {
                        System.out.println("Invalid item number.");
                    }
                    break;
                case 4:
                    orderSystem.displayOrderDetails();
                    break;
                case 5:
                    orderSystem.generateBill();
                    break;
                case 6:
                    System.out.println("Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}


abstract class MenuItem {
    int itemId;
    String itemName;
    double itemPrice;

    public MenuItem(int itemId, String itemName, double itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    abstract void displayItemDetails();
}


class Appetizer extends MenuItem {
    int calories;

    public Appetizer(int itemId, String itemName, double itemPrice, int calories) {
        super(itemId, itemName, itemPrice);
        this.calories = calories;
    }


    void displayItemDetails() {
        System.out.println("Appetizer: " + itemName);
        System.out.println("Price: " + itemPrice);
        System.out.println("Calories: " + calories);
    }
}


class MainCourse extends MenuItem {
    int calories;

    public MainCourse(int itemId, String itemName, double itemPrice, int calories) {
        super(itemId, itemName, itemPrice);
        this.calories = calories;
    }


    void displayItemDetails() {
        System.out.println("Main Course: " + itemName);
        System.out.println("Price: " + itemPrice);
        System.out.println("Calories: " + calories);
    }
}

class Dessert extends MenuItem {
    String ingredients;

    public Dessert(int itemId, String itemName, double itemPrice, String ingredients) {
        super(itemId, itemName, itemPrice);
        this.ingredients = ingredients;
    }


    void displayItemDetails() {
        System.out.println("Dessert: " + itemName);
        System.out.println("Price: " + itemPrice);
        System.out.println("Ingredients: " + ingredients);
    }
}


interface OrderOperations {
    void addToOrder(MenuItem item);

    void calculateTotalBill();

    void displayOrderDetails();
}


class Bill implements OrderOperations {
    private int billId;
    private double amount;
    static final double TAX_RATE = 0.10;
    static final double DISCOUNT_RATE = 0.05;
    static final double SPECIAL_OFFER_DISCOUNT = 0.15;

    private MenuItem[] customerOrder;
    private int orderSize = 0;

    public Bill() {
        customerOrder = new MenuItem[10];
    }

    public void addToOrder(MenuItem item) {
    	if (orderSize < customerOrder.length) {
            customerOrder[orderSize] = item;
            orderSize++;
        } else {
            System.out.println("Order is full. Cannot add more items.");
        }
    }


    public void calculateTotalBill() {
        double subtotal = 0;
        for (int i = 0; i < orderSize; i++) {
            subtotal += customerOrder[i].itemPrice;
        }

        double tax = subtotal * TAX_RATE;
        double specialOfferDiscount = 0;

        if (orderSize >= 3) {
            specialOfferDiscount = subtotal * SPECIAL_OFFER_DISCOUNT;
        }

        amount = subtotal + tax - applyDiscounts() - specialOfferDiscount;
    }


    public void displayOrderDetails() {
        System.out.println("Customer Order:");
        for (int i = 0; i < orderSize; i++) {
            customerOrder[i].displayItemDetails();
        }
        System.out.println("Total Amount: Rs." + amount);
    }

    public double applyDiscounts() {
    	double subtotal = 0;
    	for (int i = 0; i < orderSize; i++) {
            subtotal += customerOrder[i].itemPrice;
        }

        double discount = subtotal * DISCOUNT_RATE;
        return discount;
    }

    public void displayBill() {
        System.out.println("Bill ID: " + billId);
        displayOrderDetails();
    }
}


class OrderManagementSystem {
    private MenuItem[] menuItems;
    private MenuItem[] customerOrder;
    private Bill currentBill;
    private int orderSize = 0;

    public OrderManagementSystem(MenuItem[] menuItems) throws NullPointerException {
        this.menuItems = menuItems;
        currentBill = new Bill();
        customerOrder = new MenuItem[10];
    }

    public void viewMenu() {
        System.out.println("Menu Items:");
        for (MenuItem item : menuItems) {
            item.displayItemDetails();
        }
    }

    public void addToOrder(MenuItem item) {
    	if (orderSize < customerOrder.length) {
            customerOrder[orderSize] = item;
            orderSize++;
            currentBill.addToOrder(item);
        } else {
            System.out.println("Order is full. Cannot add more items.");
        }
    }

    public void displayOrderDetails() {
        System.out.println("Customer Order:");
        for (MenuItem item : customerOrder) {
            item.displayItemDetails();
        }
    }

    public void displaySpecialOffers() {
    	System.err.println("Buy more than 3 items and get extra 15% discount!");
    }

    public void generateBill() {
        currentBill.calculateTotalBill();
        currentBill.displayBill();
    }
}

/*
D:\JAVA Workspace\Assignments\Assignment3\RestaurantOrderManagement>javac RestaurantorderManagement.java

D:\JAVA Workspace\Assignments\Assignment3\RestaurantOrderManagement>java RestaurantorderManagement.java

Menu-Driven Order System
1. View Menu
2. Display Special Offers
3. Add Item to Order
4. Display Order
5. Generate Bill
6. Exit
Enter your choice: 1
Menu Items:
Appetizer: Salad
Price: 99.99
Calories: 150
Main Course: Burger
Price: 210.75
Calories: 500
Dessert: Cake
Price: 549.99
Ingredients: Chocolate

Menu-Driven Order System
1. View Menu
2. Display Special Offers
3. Add Item to Order
4. Display Order
5. Generate Bill
6. Exit
Enter your choice: 2
Buy more than 3 items and get extra 15% discount!

Menu-Driven Order System
1. View Menu
2. Display Special Offers
3. Add Item to Order
4. Display Order
5. Generate Bill
6. Exit
Enter your choice: 3
Enter the item number to add to the order: 2
Item added to the order.

Menu-Driven Order System
1. View Menu
2. Display Special Offers
3. Add Item to Order
4. Display Order
5. Generate Bill
6. Exit
Enter your choice: 3
Enter the item number to add to the order: 1
Item added to the order.

Menu-Driven Order System
1. View Menu
2. Display Special Offers
3. Add Item to Order
4. Display Order
5. Generate Bill
6. Exit
Enter your choice: 5
Bill ID: 0
Customer Order:
Main Course: Burger
Price: 210.75
Calories: 500
Appetizer: Salad
Price: 99.99
Calories: 150
Total Amount: Rs.326.27700000000004

Menu-Driven Order System
1. View Menu
2. Display Special Offers
3. Add Item to Order
4. Display Order
5. Generate Bill
6. Exit
Enter your choice: 6
Thank you!
*/