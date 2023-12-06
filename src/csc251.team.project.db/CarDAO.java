package csc251.team.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/* THis could be way off but its a start for me and I will due this multiple times hopefully better. Learning as I goG*/
public class CarDAO {

    public void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS car_dealership.car_lot (" +
                "CarId VARCHAR(255) NOT NULL," +
                "Mileage INT DEFAULT NULL," +
                "Mpg INT DEFAULT NULL," +
                "Cost DOUBLE DEFAULT NULL," +
                "SalesPrice DOUBLE DEFAULT NULL," +
                "Sold BOOLEAN DEFAULT FALSE," +
                "PriceSold FLOAT DEFAULT NULL," +
                "PRIMARY KEY (CarId)" +
                ")";
        try {
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(createTableSQL);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addCarToLot(Car car) {
        String insertSQL = "INSERT INTO car_dealership.car_lot (CarId, Mileage, Mpg, Cost, SalesPrice, Sold, PriceSold) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(insertSQL)) {
            preparedStatement.setString(1, car.getId());
            preparedStatement.setInt(2, car.getMileage());
            preparedStatement.setInt(3, car.getMpg());
            preparedStatement.setDouble(4, car.getCost());
            preparedStatement.setDouble(5, car.getSalesPrice());
            preparedStatement.setBoolean(6, car.isSold());
            preparedStatement.setDouble(7, car.getPriceSold());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getCarsInLot() {
        List<Car> cars = new ArrayList<>();
        String selectSQL = "SELECT * FROM car_dealership.car_lot";
        try (PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String carId = resultSet.getString("CarId");
                int mileage = resultSet.getInt("Mileage");
                int mpg = resultSet.getInt("Mpg");
                double cost = resultSet.getDouble("Cost");
                double salesPrice = resultSet.getDouble("SalesPrice");
                boolean sold = resultSet.getBoolean("Sold");
                double priceSold = resultSet.getDouble("PriceSold");
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
        try (PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(selectInventorySQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String carId = resultSet.getString("CarId");
                int mileage = resultSet.getInt("Mileage");
                int mpg = resultSet.getInt("Mpg");
                double cost = resultSet.getDouble("Cost");
                double salesPrice = resultSet.getDouble("SalesPrice");
                boolean sold = resultSet.getBoolean("Sold");
                double priceSold = resultSet.getDouble("PriceSold");
                inventory.add(new Car(carId, mileage, mpg, cost, salesPrice, sold, priceSold));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public void removeCar(String carId){
        // Remove the car from the lot
        String sellCarSQL = "DELETE FROM car_dealership.car_lot WHERE CarId = ?";
        try (PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sellCarSQL)) {
            preparedStatement.setString(1, carId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sellCar(String carId, float priceSold) {
        // Mark the car as sold and set the selling price
        String sellCarSQL = "UPDATE car_dealership.car_lot SET Sold = TRUE, PriceSold = ? WHERE CarId = ?";
        try (PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(sellCarSQL)) {
            preparedStatement.setDouble(1, priceSold);
            preparedStatement.setString(2, carId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCar(String carId, Integer mileage, Integer mpg, Double salesPrice, Double cost) {
        String updatedDateSQL = "UPDATE car_dealership.car_lot SET Mileage = ?, MPG = ?, SalesPrice = ?, " +
                "Cost = ? WHERE CarId = ?";
        try (PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(updatedDateSQL)){
            preparedStatement.setInt(1, mileage);
            preparedStatement.setInt(2, mpg);
            preparedStatement.setDouble(3,salesPrice);
            preparedStatement.setDouble(4, cost);
            preparedStatement.setString(5, carId);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateCar(String carId, Integer mileage, Integer mpg, Double salesPrice, Double cost, Boolean sold,
                          Double priceSold) {
        String updatedDateSQL = "UPDATE car_dealership.car_lot SET Mileage = ?, MPG = ?, SalesPrice = ?, " +
                "Cost = ?, Sold = ?, PriceSold = ? WHERE CarId = ?";
        try (PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(updatedDateSQL)){
            preparedStatement.setInt(1, mileage);
            preparedStatement.setInt(2, mpg);
            preparedStatement.setDouble(3,salesPrice);
            preparedStatement.setDouble(4, cost);
            preparedStatement.setBoolean(5, sold);
            preparedStatement.setDouble(6, priceSold);
            preparedStatement.setString(7, carId);
            preparedStatement.execute();
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
        try (PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(selectSalesHistorySQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String carId = resultSet.getString("CarId");
                int mileage = resultSet.getInt("Mileage");
                int mpg = resultSet.getInt("Mpg");
                double cost = resultSet.getDouble("Cost");
                double salesPrice = resultSet.getDouble("SalesPrice");
                boolean sold = resultSet.getBoolean("Sold");
                double priceSold = resultSet.getDouble("PriceSold");
                salesHistory.add(new Car(carId, mileage, mpg, cost, salesPrice, sold, priceSold));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesHistory;
    }


}
