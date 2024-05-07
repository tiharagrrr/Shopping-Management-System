public class Clothing extends Product {
    private String size;
    private String colour;

    //constructor
    public Clothing(String productID, String productName, int noAvailable, double price, String size, String colour) {
        super(productID, productName, noAvailable, price);
        this.size = size;
        this.colour = colour;
    }
    //getters and setters
    public Clothing(String size, String colour) {
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
    //return size and colour information as needed to the gui
    public String Info(){
        return (size+", "+colour);
    }

    public String Info1(){
        return size;
    }

    public String Info2(){
        return colour;
    }


    @Override
    public String toString() {
        return super.toString() +"type = Clothing "+
                "size='" + size + '\'' +
                ", colour='" + colour + '\'' +
                "} " ;
    }
}
