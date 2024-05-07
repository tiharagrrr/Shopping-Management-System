public class Electronics extends Product{
    private String brand;
    private int warrantyPeriod;

    //getters and setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }


    //constructors
    public Electronics(String productID, String productName, int noAvailable, double price, String brand, int warrantyPeriod) {
        super(productID, productName, noAvailable, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public Electronics(String brand, int warrantyPeriod) {
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //return brand and warranty information as needed to the gui
    public String Info(){
        return (brand+", "+warrantyPeriod+ " weeks warranty");
    }

    public String Info1(){
        return brand;
    }

    public String Info2(){
        return String.valueOf(warrantyPeriod);
    }



    @Override
    public String toString() {
        return super.toString() +"type = Electronics "+
                "brand = '" + brand + '\'' +
                ", warrantyPeriod = '" + warrantyPeriod + '\'' +
                "} " ;
    }
}
