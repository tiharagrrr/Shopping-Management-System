import java.util.ArrayList;
import java.util.List;
public class ShoppingCart {

    private  ArrayList<Product> productsInCart;
    private double totalCost;
    private int quantity;

    //constructor
    public ShoppingCart(){
        productsInCart = new ArrayList<>();
        totalCost = 0.0;
        quantity = 0;

    }

    //getters and setters
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ArrayList<Product> getProductsInCart() {
        return productsInCart;
    }

    public void setProductsInCart(ArrayList<Product> productsInCart) {
        this.productsInCart = productsInCart;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    //add a product to cart(productsInCart), and increase the quantity if its already been added once
    public void addProduct(Product prod){
        if(productsInCart!=null) {
            for (Product product : productsInCart) {
                if (prod.getProductID().equals(product.getProductID())) {
                    product.addQuantity();
                    return;
                }
            }
        }
        productsInCart.add(prod);
        prod.addQuantity();

    }

    //totalPerProduct to be displayed in the shopping cart gui table
    public double  getTotalPerProduct(Product prod){
        return (prod.getPrice()*prod.getQuantity());
    }

    //method to remove products but not used as this option was not shown in the specification gui example
    public void removeProd(Product prod){
        for(Product product : productsInCart){
            if(prod.getProductID().equals(product.getProductID())){
                product.substractQuantity();
                return;
            }
        }
        productsInCart.remove(prod);
    }

    //calculate the total of all products and their quantity for the shopping cart
    public double calcTotal(){
        totalCost = 0.0;
        for (Product prod : productsInCart) {
            totalCost += prod.getPrice()*prod.getQuantity();
        }
        return totalCost;
    }




}

