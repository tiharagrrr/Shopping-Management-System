import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.Comparator;



public class CartGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JTable productTable;
    private DefaultTableModel tableModel;

    private JButton addToCartButton;
    private JButton viewShoppingCartButton;

    private ShoppingCart shoppingCart = new ShoppingCart();
    private WestminsterShoppingManager westminsterShoppingManager;
    private boolean firstPurchaseDiscount;

    //first opens the login frame and then goes to the main frame
    public CartGUI(WestminsterShoppingManager westminsterShoppingManager) {
        this.westminsterShoppingManager = westminsterShoppingManager;

        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel); //login page

        setVisible(true);
    }


    //components for the login page
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        //action to be performed when the login button is clicked.
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                User user = new User(username, password);
                if(user.checkUser(username)){
                    firstPurchaseDiscount = false;
                } else {
                    firstPurchaseDiscount = true;
                }
                user.storeUserInfo(user);

                JOptionPane.showMessageDialog(null, "Login successful!");

                //opens shopping table and closes this window
                ShoppingTableGUI();
                dispose();

            }
        });
    }



    public void ShoppingTableGUI() {
        JFrame frame = new JFrame("Westminster Shopping Centre");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());

        //mainPanel will contain the product table, dropdown menu and shopping cart button
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.setSize(700, 600);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setVisible(true);
        frame.add(detailsPanel, BorderLayout.CENTER);

        FlowLayout flow = new FlowLayout();
        //for gaps between the elements
        flow.setHgap(30);
        flow.setVgap(40);
        JPanel panel1 = new JPanel();
        panel1.setLayout(flow);
        mainPanel.add(panel1, BorderLayout.NORTH);
        JLabel label = new JLabel("Select Product Category ");
        label.setVisible(true);

        panel1.add(label);

        String[] types = {"All", "Electronics", "Clothing"};
        JComboBox<String> dropdown = new JComboBox<String>(types);
        panel1.add(dropdown);
        dropdown.repaint();
        JButton sortButton = new JButton("Sort");
        panel1.add(sortButton);


        //listener for drop down menu to show the selected category of products
        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable(dropdown.getSelectedItem());

            }
        });

        //to make the table not editable
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableModel.addColumn("Product ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price");
        tableModel.addColumn("Info");


        productTable = new JTable(tableModel);
        productTable.setVisible(true);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(600,250));

        //custom row renderer to check each row and highlight row if quantity of product is less than 3 (inner class for this is written at the end of this class)
        CustomRowRenderer rowRenderer = new CustomRowRenderer();
        productTable.setDefaultRenderer(Object.class, rowRenderer);

        ArrayList<Product> products = westminsterShoppingManager.productsInSys;

        //adding the products to the table row by row
        for (Product product : products) {

            Object[] rowData = {product.getProductID(), product.getProductName(), product.getClass().getSimpleName(), product.getPrice(), product.Info()};
            tableModel.addRow(rowData);

        }


        JPanel panel3 = new JPanel();
        panel3.add(scrollPane);

        //horizontal line after the product table
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);


        mainPanel.add(panel3, BorderLayout.CENTER);
        mainPanel.add(separator, BorderLayout.SOUTH);

        //sorter for the table to sort it alphabetically according to product name
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        productTable.setRowSorter(sorter);

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                products.sort(Comparator.comparing(Product::getProductName));

                for (Product product : products) {
                    Object[] rowData = {product.getProductID(), product.getProductName(), product.getClass().getSimpleName(), product.getPrice(), product.Info()};
                    tableModel.addRow(rowData);
                }


            }
        });


        //for when a row with products is selected, to display its details in the detailsPanel below it
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //the condition is to make sure the user is not in the process of making a selection and it is stable
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = productTable.getSelectedRow();
                    if (selectedRow != -1 & selectedRow<productTable.getRowCount()) {
                        String productId = (String) productTable.getValueAt(selectedRow, 0);
                        for (Product product : products) {
                            if (product.getProductID().equals(productId)) {
                                detailsPanel.removeAll(); // Clear existing components

                                JLabel title = new JLabel("Selected Product - Details");
                                JLabel id = new JLabel("Product ID : "+ product.getProductID());
                                JLabel categoryLabel = new JLabel("Category: " + product.getClass().getSimpleName());
                                JLabel nameLabel = new JLabel("Name: " + product.getProductName());
                                JLabel infoLabel1;
                                JLabel infoLabel2;
                                if (product.getClass().getSimpleName().equals("Electronics")){
                                    infoLabel1 = new JLabel("Brand: "+product.Info1());
                                    infoLabel2 = new JLabel("Warranty in weeks: "+product.Info2());

                                }else {
                                    infoLabel1 = new JLabel("Size: "+product.Info1());
                                    infoLabel2 = new JLabel("Colour: "+product.Info2());
                                }
                                JLabel itemsavail = new JLabel("Items available : "+ product.getNoAvailable());

                                detailsPanel.add(title);
                                detailsPanel.add(id);
                                detailsPanel.add(categoryLabel);
                                detailsPanel.add(nameLabel);
                                detailsPanel.add(infoLabel1);
                                detailsPanel.add(infoLabel2);
                                detailsPanel.add(itemsavail);

                                detailsPanel.revalidate(); // Refresh the panel
                                detailsPanel.repaint(); // Repaint the panel

                            }
                        }
                    }
                }
            }
        });



        

        addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(new ActionListener() { //selection to add products
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1 & selectedRow < productTable.getRowCount()) {
                    String productId = (String) productTable.getValueAt(selectedRow, 0);
                    for (Product product : products) {
                        if (product.getProductID().equals(productId)) {
                            shoppingCart.addProduct(product);
                        }
                    }
                } else {
                    JOptionPane optionPane = new JOptionPane("Please select a product first.", JOptionPane.INFORMATION_MESSAGE);
                    JDialog dialog = optionPane.createDialog(detailsPanel, "Message");
                    dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
                    dialog.setVisible(true);
                    dialog.dispose();

                }
            }
        });

        //button to view the shopping cart
        viewShoppingCartButton = new JButton("Shopping Cart");
        viewShoppingCartButton.setBounds(500,10,10,10);
        viewShoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(shoppingCart, firstPurchaseDiscount);

            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addToCartButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        panel1.add(viewShoppingCartButton);


        frame.setVisible(true);



    }
    //method to filter the table based on selection
    private void filterTable(Object selection){
        String category = (String) selection;
        tableModel.setRowCount(0);
        if(category.equals("Electronics")){
            for (Product product : westminsterShoppingManager.productsInSys) {
                if(product.getClass().getSimpleName().equals("Electronics")) {
                    Object[] rowData = {product.getProductID(), product.getProductName(), product.getClass().getSimpleName(), product.getPrice(), product.Info()};
                    tableModel.addRow(rowData);
                }
            }
        } else if (category.equals("Clothing")) {
            for (Product product : westminsterShoppingManager.productsInSys) {
                if(product.getClass().getSimpleName().equals("Clothing")) {
                    Object[] rowData = {product.getProductID(), product.getProductName(), product.getClass().getSimpleName(), product.getPrice(), product.Info()};
                    tableModel.addRow(rowData);
                }
            }

        } else if (category.equals("All")) {
            for (Product product : westminsterShoppingManager.productsInSys) {
                Object[] rowData = {product.getProductID(), product.getProductName(), product.getClass().getSimpleName(), product.getPrice(), product.Info()};
                tableModel.addRow(rowData);

            }

        }
    }


    //inner class to highlight the row as needed
    public class CustomRowRenderer extends DefaultTableCellRenderer {
        ArrayList<Product> products = westminsterShoppingManager.productsInSys;


        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Check the condition based on the data object (Product in this case)
            if (row < products.size()) {
                Object obj = tableModel.getValueAt(row,0);
                String prodid = value.toString();
                for (Product prod : products){
                    if (prodid.equals(prod.getProductID())){
                        if (prod.getNoAvailable() < 3) {
                            component.setBackground(Color.RED);
                            component.setForeground(Color.WHITE); // Optional: Set text color to white for better visibility
                        } else {
                            // Reset to default colors for other rows
                            component.setBackground(table.getBackground());
                            component.setForeground(table.getForeground());
                        }
                    }
                }
            }

            return component;
        }
    }


}