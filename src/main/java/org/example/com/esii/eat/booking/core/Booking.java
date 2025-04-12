package org.example.com.esii.eat.booking.core;

import javax.swing.*;
import java.awt.*;

public class Booking extends JFrame {
    private JComboBox<String> restaurantComboBox;
    private JTextField dinersCountTextField;
    private JTextField nameTextField;
    private JButton checkButton;
    private JButton reserveButton;
    private JButton searchAlternativeButton;
    private JTextArea restaurantTableTextArea;
    private JTextArea suggestedTablesTextArea;
    private JLabel noTablesAvailableLabel;

    private JPanel mainPanel;

    private Chain gourmetChain;
    private Restaurant italianBistro, sushiPalace, steakHouse;

    public Booking() {
        initializeRestaurants();
        setupUI();
        populateRestaurantComboBox();
        addActionListeners();
    }

    private void initializeRestaurants() {
        gourmetChain = new Chain("Gourmet Delights", 3);
        italianBistro = new Restaurant("Italian Bistro", 5);
        sushiPalace = new Restaurant("Sushi Palace", 4);
        steakHouse = new Restaurant("Steak House", 6);

        gourmetChain.addRestaurant(italianBistro);
        gourmetChain.addRestaurant(sushiPalace);
        gourmetChain.addRestaurant(steakHouse);
    }

    private void setupUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        restaurantComboBox = new JComboBox<>();
        dinersCountTextField = new JTextField();
        nameTextField = new JTextField();
        checkButton = new JButton("Check Availability");
        reserveButton = new JButton("Reserve");
        searchAlternativeButton = new JButton("Search Alternative");
        noTablesAvailableLabel = new JLabel("No tables available.");
        noTablesAvailableLabel.setForeground(Color.RED);
        noTablesAvailableLabel.setVisible(false);

        restaurantTableTextArea = new JTextArea(10, 20);
        restaurantTableTextArea.setEditable(false);
        suggestedTablesTextArea = new JTextArea(10, 20);
        suggestedTablesTextArea.setEditable(false);

        topPanel.add(new JLabel("Select Restaurant:"));
        topPanel.add(restaurantComboBox);
        topPanel.add(new JLabel("Number of Diners:"));
        topPanel.add(dinersCountTextField);
        topPanel.add(new JLabel("Name:"));
        topPanel.add(nameTextField);
        topPanel.add(checkButton);
        topPanel.add(reserveButton);
        topPanel.add(searchAlternativeButton);
        topPanel.add(noTablesAvailableLabel);

        bottomPanel.add(new JScrollPane(restaurantTableTextArea));
        bottomPanel.add(new JScrollPane(suggestedTablesTextArea));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.CENTER);

        setTitle("Restaurant Booking System");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateRestaurantComboBox() {
        restaurantComboBox.addItem("Italian Bistro");
        restaurantComboBox.addItem("Sushi Palace");
        restaurantComboBox.addItem("Steak House");
    }

    private void addActionListeners() {
        restaurantComboBox.addActionListener(e -> updateRestaurantTableDisplay());
        checkButton.addActionListener(e -> checkTableAvailability());
        reserveButton.addActionListener(e -> makeReservation());
        searchAlternativeButton.addActionListener(e -> searchAlternativeRestaurant());
    }

    private void updateRestaurantTableDisplay() {
        String selected = (String) restaurantComboBox.getSelectedItem();
        Restaurant restaurant = gourmetChain.getRestaurant(selected);
        if (restaurant != null) {
            restaurantTableTextArea.setText(restaurant.toString());
        }
    }

    private void checkTableAvailability() {
        try {
            String selected = (String) restaurantComboBox.getSelectedItem();
            int diners = Integer.parseInt(dinersCountTextField.getText());
            Restaurant restaurant = gourmetChain.getRestaurant(selected);

            if (restaurant != null && restaurant.hasAvailableTables(diners)) {
                suggestedTablesTextArea.setText(restaurant.availableTablesInfo(diners));
                reserveButton.setEnabled(true);
                noTablesAvailableLabel.setVisible(false);
            } else {
                suggestedTablesTextArea.setText("");
                reserveButton.setEnabled(false);
                noTablesAvailableLabel.setVisible(true);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of diners.");
        }
    }

    private void makeReservation() {
        try {
            String selected = (String) restaurantComboBox.getSelectedItem();
            int diners = Integer.parseInt(dinersCountTextField.getText());
            String name = nameTextField.getText();

            boolean success = gourmetChain.reserveRestaurant(diners, selected, name);
            if (success) {
                JOptionPane.showMessageDialog(this, "Reservation successful!");
                updateRestaurantTableDisplay();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Reservation failed. No tables available.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of diners.");
        }
    }

    private void searchAlternativeRestaurant() {
        try {
            String selected = (String) restaurantComboBox.getSelectedItem();
            int diners = Integer.parseInt(dinersCountTextField.getText());

            Restaurant altRestaurant = gourmetChain.searchRestaurant(diners, selected);
            if (altRestaurant != null) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "Alternative found: " + altRestaurant.getName() + ". Reserve here?",
                        "Alternative Available", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    restaurantComboBox.setSelectedItem(altRestaurant.getName());
                    checkTableAvailability();
                }
            } else {
                JOptionPane.showMessageDialog(this, "No alternative restaurants available.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of diners.");
        }
    }

    private void resetForm() {
        dinersCountTextField.setText("");
        nameTextField.setText("");
        reserveButton.setEnabled(false);
        suggestedTablesTextArea.setText("");
        noTablesAvailableLabel.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Booking::new);
    }
}
