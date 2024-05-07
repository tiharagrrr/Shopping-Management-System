import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class ShoppingCartGUI extends JFrame {
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private ShoppingCart shoppingCart;
    private ArrayList<Product> products = new ArrayList<>();

    char euroChar = '€';

    public ShoppingCartGUI(ShoppingCart shoppingCart, boolean firstPurchaseDiscount) {
        // Initialize components
        this.shoppingCart = shoppingCart;

        setVisible(true);
        setSize(600, 700);

        //makes the table not editable
        cartTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cartTableModel.addColumn("Product");
        cartTableModel.addColumn("Quantity");
        cartTableModel.addColumn("Price");
        products = shoppingCart.getProductsInCart();


        cartTable = new JTable(cartTableModel);
        cartTable.setVisible(true);
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(500, 150));
        cartTable = new JTable(cartTableModel);

        // Set up the layout
        setLayout(new BorderLayout());

        // Add the cart table to the frame's NORTH position
        add(scrollPane, BorderLayout.NORTH);

        ArrayList<Product> cart = shoppingCart.getProductsInCart();

        if (products != null) {
            for (Product product : products) {

                Object[] rowData = {product.getProductID() + " " + product.getProductName() + " " + product.Info(), product.getQuantity(), shoppingCart.getTotalPerProduct(product)};
                cartTableModel.addRow(rowData);

            }
        }
        JPanel totalPanel = new JPanel();

        add(totalPanel, BorderLayout.CENTER);
        totalPanel.setVisible(true);
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));

        // Creating a DecimalFormat with two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        JLabel totalCostLabel = new JLabel("Total:  " + shoppingCart.calcTotal() + " " + euroChar);

        double fpd = 0.00; //first person discount value if any
        double tpd = 0.00; //3 items in same category discount if any

        if (firstPurchaseDiscount) {
            fpd = shoppingCart.calcTotal() * 0.1;
        }

        int electronicsCount = 0;
        int clothingCount = 0;

        for (Product prod : shoppingCart.getProductsInCart()) {
            if (prod.getClass().getSimpleName().equals("Electronics")) {
                electronicsCount += prod.getQuantity();
            } else {
                clothingCount += prod.getQuantity();
            }
        }

        if (electronicsCount > 2 | clothingCount > 2) {
            tpd = shoppingCart.calcTotal() * 0.2;
        }

        JLabel firstDiscount = new JLabel("First Purchase Discount(10%)  -" + fpd + " " + euroChar);
        JLabel secondDiscount = new JLabel("Three items in Same Category Discount (20%)  -" + tpd + " " + euroChar);
        JLabel finalTotalLabel = new JLabel("Final Total  " + (shoppingCart.calcTotal() - fpd - tpd) + " " + euroChar);

        totalPanel.add(totalCostLabel);
        totalPanel.add(firstDiscount);
        totalPanel.add(secondDiscount);
        totalPanel.add(finalTotalLabel);
        // Set up the frame
        setTitle("Shopping Cart");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


   /*

   public void updateTable() {


    public void updateTotalCost() {

    }*/
    }
}

