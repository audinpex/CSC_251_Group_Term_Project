package csc251.team.project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
										/* THis could be way off but its a start for me and I will due this multiple times hopefully better. Learning as I goG*/
public class CarDAO {
    private Connection connection;

    public CarDAO() {
        connect();
        createTableIfNotExists();
    }

    private void connect() {
        try {
            String url = "jdbc:sqlite:DatabaseConnection.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS car_dealership.car_lot (" +
                "CarId VARCHAR(255) NOT NULL," +
                "Mileage INT DEFAULT NULL," +
                "Mpg INT DEFAULT NULL," +
                "Cost DOUBLE DEFAULT NULL," +
                "SalesPrice FLOAT DEFAULT NULL," +
                "Sold BOOLEAN DEFAULT FALSE," +
                "PriceSold FLOAT DEFAULT NULL," +
                "PRIMARY KEY (CarId)" +
                ")";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSQL)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCarToLot(Car car) {
        String insertSQL = "INSERT INTO car_dealership.car_lot (CarId, Mileage, Mpg, Cost, SalesPrice, Sold, PriceSold) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, car.getCarId());
            preparedStatement.setInt(2, car.getMileage());
            preparedStatement.setInt(3, car.getMpg());
            preparedStatement.setDouble(4, car.getCost());
            preparedStatement.setFloat(5, car.getSalesPrice());
            preparedStatement.setBoolean(6, car.isSold());
            preparedStatement.setFloat(7, car.getPriceSold());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getCarsInLot() {
        List<Car> cars = new ArrayList<>();
        String selectSQL = "SELECT * FROM car_dealership.car_lot";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String carId = resultSet.getString("CarId");
                int mileage = resultSet.getInt("Mileage");
                int mpg = resultSet.getInt("Mpg");
                double cost = resultSet.getDouble("Cost");
                float salesPrice = resultSet.getFloat("SalesPrice");
                boolean sold = resultSet.getBoolean("Sold");
                float priceSold = resultSet.getFloat("PriceSold");
                cars.add(new Car(carId, mileage, mpg, cost, salesPrice, sold, priceSold));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
			
    public List<Car> getInventory() {
        List<Car> inventory = new ArrayList<>();
        // Fetch cars that are not sold
        String selectInventorySQL = "SELECT * FROM car_dealership.car_lot WHERE Sold = FALSE";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectInventorySQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String carId = resultSet.getString("CarId");
                int mileage = resultSet.getInt("Mileage");
                int mpg = resultSet.getInt("Mpg");
                double cost = resultSet.getDouble("Cost");
                float salesPrice = resultSet.getFloat("SalesPrice");
                boolean sold = resultSet.getBoolean("Sold");
                float priceSold = resultSet.getFloat("PriceSold");
                inventory.add(new Car(carId, mileage, mpg, cost, salesPrice, sold, priceSold));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public void sellCar(String carId, float priceSold) {
        // Mark the car as sold and set the selling price
        String sellCarSQL = "UPDATE car_dealership.car_lot SET Sold = TRUE, PriceSold = ? WHERE CarId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sellCarSQL)) {
            preparedStatement.setFloat(1, priceSold);
            preparedStatement.setString(2, carId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean compareCars(Car car1, Car car2) {
        // Compare cars based on some criteria (e.g., cost, mileage, etc.)
        
        return car1.getCost() < car2.getCost();
    }

    public List<Car> getSalesHistory() {
        List<Car> salesHistory = new ArrayList<>();
        // Fetch cars that are sold
        String selectSalesHistorySQL = "SELECT * FROM car_dealership.car_lot WHERE Sold = TRUE";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSalesHistorySQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String carId = resultSet.getString("CarId");
                int mileage = resultSet.getInt("Mileage");
                int mpg = resultSet.getInt("Mpg");
                double cost = resultSet.getDouble("Cost");
                float salesPrice = resultSet.getFloat("SalesPrice");
                boolean sold = resultSet.getBoolean("Sold");
                float priceSold = resultSet.getFloat("PriceSold");
                salesHistory.add(new Car(carId, mileage, mpg, cost, salesPrice, sold, priceSold));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesHistory;
    }
}
