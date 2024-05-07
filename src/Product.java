import java.io.Serializable;

public abstract class Product implements Serializable { //Serializable interface is implemented to be used when saving the products to file and reading from file
    private String productID;
    private String productName;
    private int noAvailable;
    private double price;
    private int quantity;

    //getters and setters
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNoAvailable() {
        return noAvailable;
    }

    public void setNoAvailable(int noAvailable) {
        this.noAvailable = noAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //called to keep count of the number of items of a particular product being added to cart
    public int getQuantity() {
        return quantity;
    }
    //called when the user adds a product to the cart, to increment the quantity
    public void addQuantity(){
        this.quantity++;
    }

    //to remove products or number of products from cart but this feature was not implemented as it was not shown in the specification
    public void substractQuantity(){
        this.quantity--;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //constructor
    public Product(String productID, String productName, int noAvailable, double price) {
        this.productID = productID;
        this.productName = productName;
        this.noAvailable = noAvailable;
        this.price = price;
    }
    //default constructor
    public Product(){
        productID = "";
        productName = "";
        noAvailable = 0;
        price = 0.0;

    }

    //methods with bodies defined in the subclasses to retrieve subclass specific information about the products for the gui
    public abstract String Info();

    public abstract String Info1();
    public abstract String Info2();


    @Override
    public String toString() {
        return "Product { " +
                "productID = '" + productID + '\'' +
                ", productName ='" + productName + '\'' +
                ", noAvailable = " + noAvailable +
                ", price = " + price +
                '}';
    }
}
