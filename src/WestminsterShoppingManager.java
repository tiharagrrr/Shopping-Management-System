import java.io.*;
import java.util.*;
import java.util.ArrayList;


public class WestminsterShoppingManager implements ShoppingManager {

    //Store all the products tht should be in the system
    static ArrayList<Product> productsInSys = new ArrayList<>();

    //to read user inputs
    Scanner input1 = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner input2 = new Scanner(System.in);
        int choice = 0;

        //creating an instance of the class to run all the necessary methods as needed, because they cannot be directly called unless they are static methods
        WestminsterShoppingManager run = new WestminsterShoppingManager();

        //Loading any previously stored product details from file if saved to file
        run.loadFromFile();

        //do-while loop to ask for an input until a valid one is given
        do{
            //try catch for error handling
            try{
                System.out.println("\nMenu:");
                System.out.println("1. Add a new product to the system");
                System.out.println("2. Delete a product from the system");
                System.out.println("3. Print the list of products in the system");
                System.out.println("4. Save product list to file");
                System.out.println("5. Exit");
                System.out.println("6. For clients to open the GUI");
                System.out.print("Enter your choice: ");
                choice = input2.nextInt();
                switch(choice) {
                    case 1 -> run.addProductSys();
                    case 2 -> run.deleteProductSys();
                    case 3 -> run.printPList();
                    case 4 -> run.saveToFile();
                    case 5 -> {
                        System.out.println("Program successfully terminated.");
                        choice = 5;
                    }
                    case 6 -> {
                        CartGUI gui = new CartGUI(run);
                    }
                    default -> System.out.println("Invalid input. Please select one of the options.");

                }


            } catch (InputMismatchException e){
                System.out.println("That's not a valid input. Please enter a number.");
                input2.nextLine(); //to clear the input previously given

            }

        } while (choice!= 5);




    }

    //method to add products to the system
    public void addProductSys(){
        if (productsInSys.size()<50) {
            System.out.println("Please enter the name of the product : ");
            String productName = input1.next();
            System.out.println("Please enter the product ID : ");
            String productID = input1.next();
            System.out.println("Please enter the price of the product : ");
            double price = input1.nextDouble();
            System.out.println("Please enter the number of products available : ");
            int noAvailable = input1.nextInt();
            System.out.println("Is the product an Electronic or Clothing? \n Enter: \n  1 for Electronic \n  2 for Clothing : ");
            int productType = input1.nextInt();
            if (productType == 1) {
                System.out.println("Enter the brand : ");
                String brand = input1.next();
                System.out.println("Enter the warranty period in weeks: ");
                int warrantyPeriod = input1.nextInt();
                Product product = new Electronics(productID, productName, noAvailable, price, brand, warrantyPeriod);
                productsInSys.add(product);
            } else if (productType == 2) {
                System.out.println("Enter the size : ");
                String size = input1.next();
                System.out.println("Enter the colour : ");
                String colour = input1.next();
                Product product = new Clothing(productID, productName, noAvailable, price, size, colour);
                productsInSys.add(product);
            } else{
                System.out.println("Invalid option.");
                return;
            }
            System.out.println("Successfully added product");
        } else {
            System.out.println("The system cannot have more than 50 products.");
        }


    }

    //method to delete products from system (assumes that no 2 products will have the same ID)
    public void deleteProductSys(){
        System.out.println("Enter the Product ID of the item that should be deleted : ");
        String productID = input1.next();
        Iterator <Product> iterator = productsInSys.iterator();
        while (iterator.hasNext()){
            Product prod = (Product)iterator.next();
            if (prod.getProductID().equals(productID)){
                iterator.remove();
                System.out.println("Product removed successfully. The total number of products in the system is : "+productsInSys.size());
                return;
            }
        }
        System.out.println("Product not found. ");

    }


    //to sort according to productID and print the product list
    public void printPList(){
        if (!productsInSys.isEmpty()) {
            productsInSys.sort(Comparator.comparing(Product::getProductID));

            for (Product product : productsInSys) {
                System.out.println(product.toString());
            }
        } else {
            System.out.println("No products found");
        }

    }

    //save product list to file with exception handling, The try-with-resources statement is used to automatically close resources
    //(in this case, the ObjectOutputStream).
    public void saveToFile(){
        //this writes a serialized version of the ArrayList. FileOutputStream is used to write the raw bytes to the file
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("productList.ser"))) {
            objectOutputStream.writeObject(productsInSys);
            System.out.println("Product list saved to file.");

        } catch (IOException e) {
            e.printStackTrace(); //prints the stack trace of the exception, to help diagnose errors if any
        }

    }

    //method to load previously saved product list (called automatically in the main method)
    public void loadFromFile(){
        //ObjectInputStream to deserialize Java objects
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("productList.ser"))) {
            // reads the serialized object from the file and casts it to an ArrayList<Product>
            productsInSys = (ArrayList<Product>) objectInputStream.readObject();
            System.out.println("Product list loaded from file.");
        } catch (FileNotFoundException e) {
            //to inform manager that there is no previous data saved to file to load.
            System.err.println("No previous data to load from file: " + e.getMessage());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



}
