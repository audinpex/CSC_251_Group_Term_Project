package csc251.team.project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;

public class CarLotFX extends Application {

    private Car[] inventory; // Array to store cars
    private int numberOfCars = 0; // Number of cars currently in the inventory
    private int capacity = 0; // Capacity of the car lot

    // Default constructor, initializes the car lot with a capacity of 100
    public CarLotFX() {
        this(100);
    }

    // Constructor allowing specification of car lot capacity
    public CarLotFX(int capacity) {
        this.capacity = capacity;
        this.inventory = new Car[capacity]; // Initializing the car inventory array
    }

    // Method to add a new car to the inventory
    public void addCar(String id, int mileage, int mpg, double cost, double salesPrice) {
        if (numberOfCars < capacity) {
            this.inventory[numberOfCars] = new Car(id, mileage, mpg, cost, salesPrice);
            numberOfCars++;
        }
    }

    // Get all cars in the inventory
    public Car[] getInventory() {
        Car[] allCars = new Car[numberOfCars];
        System.arraycopy(this.inventory, 0, allCars, 0, numberOfCars);
        return allCars;
    }

    // Other methods...

    @Override
    public void start(Stage primaryStage) {
        BorderPane sellPanel = new BorderPane(); // Main panel to hold the sell components

        // Panel for search options (ID search)
        FlowPane searchPanel = new FlowPane();
        searchPanel.setPadding(new Insets(10));

        Label searchLabel = new Label("Search by ID:");
        searchPanel.getChildren().add(searchLabel);

        TextField searchField = new TextField();
        searchField.setPrefColumnCount(10);
        searchPanel.getChildren().add(searchField);

        Button searchButton = new Button("Search");
        searchPanel.getChildren().add(searchButton);

        sellPanel.setTop(searchPanel); // Adding search panel to the top of the main panel

        // List to display unsold cars
        ListView<Car> carList = new ListView<>();
        ObservableList<Car> items = FXCollections.observableArrayList();
        items.addAll(new UnsoldCarListModel(this));
        carList.setItems(items);
        sellPanel.setCenter(carList); // Adding the list to the center of the main panel

        // Button for selling selected car
        Button sellButton = new Button("Sell");
        sellPanel.setBottom(sellButton); // Adding the sell button to the bottom of the main panel

        // Action listener for the sell button
        sellButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Car selectedCar = carList.getSelectionModel().getSelectedItem();

                if (selectedCar != null) {
                    String sellingPriceString = JOptionPane.showInputDialog("Enter selling price:");

                    if (sellingPriceString != null) {
                        try {
                            double sellingPrice = Double.parseDouble(sellingPriceString);
                            addCar(selectedCar.getId(), sellingPrice);
                            items.setAll(new UnsoldCarListModel(CarLotFX.this)); // Update the car list model
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid selling price");
                        }
                    }
                }
            }
        });

        Scene scene = new Scene(sellPanel, 400, 300); // Creating the scene with the main panel
        primaryStage.setScene(scene);
        primaryStage.setTitle("Car Lot Application");
        primaryStage.show();
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}

