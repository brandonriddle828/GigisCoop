package com.example.gigiscoop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SetEditPriceController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;


    String url = ("jdbc:mysql://localhost:3306/gigiscoop");
    String username = ("root");
    String password = ("1!");

    @FXML
    protected ComboBox breedSelector;
    @FXML
    protected TextField hatchEggBox;
    @FXML
    protected TextField pulletBox;
    @FXML
    protected TextField chickBox;
    @FXML
    protected TextField egg12Box;
    @FXML
    protected TextField egg6Box;

    @FXML
    protected Label breedNameLabel;
    @FXML
    protected Label hatchEggLabel;
    @FXML
    protected Label pulletLabel;
    @FXML
    protected Label chickLabel;
    @FXML
    protected Label egg12Label;
    @FXML
    protected Label egg6Label;



    ObservableList<String> breeds = FXCollections.observableArrayList();
    PreparedStatement stmt = null;
    ResultSet rset = null;


    public void getFlockInfo()
    {
        try
        {

            String sqlCheck = ("SELECT * FROM Flock WHERE group_Ended = false ORDER BY Breed asc;");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sqlCheck);
            rset = ps.executeQuery();
            breeds = FXCollections.observableArrayList();
            while (rset.next())
            {
                breeds.add(String.valueOf(rset.getString("breed")));
            }
            breedSelector.setItems(null);
            breedSelector.setItems(breeds);


        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void loadPrices()
    {
        boolean checker;
        Flock group = new Flock(0,0, "", "", 0, 0, "", false);
        Prices price = new Prices(0,0,0.00,0.00,0.00,0.00,0.00);
        group.breed = breedSelector.getValue().toString();

        try
        {
            String groupQuery = " SELECT * FROM Flock WHERE breed = '" + group.breed + "'";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(groupQuery);
            ResultSet rs = ps.executeQuery();

            rs = connection.createStatement().executeQuery(groupQuery);

            rs.next();
            group.flockID = rs.getInt(1);
            group.breedID = rs.getInt(2);
            group.breed = rs.getString(3);
            group.eggColor = rs.getString(4);
            group.henNum = rs.getInt(5);
            group.roosterNum = rs.getInt(6);
            group.hatchDate = rs.getString(7);
            group.groupEnded = rs.getBoolean(8);

            breedNameLabel.setText(group.breed);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            String groupQuery = " SELECT * FROM Pricing WHERE flockID = '" + group.flockID + "'";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(groupQuery);
            ResultSet rs = ps.executeQuery();

            rs = connection.createStatement().executeQuery(groupQuery);

            rs.next();
            price.priceID = rs.getInt(1);
            price.flockID = rs.getInt(2);
            price.hatchingEggs = rs.getDouble(3);
            price.pullets = rs.getDouble(4);
            price.chicks = rs.getDouble(5);
            price.eatingEggs12 = rs.getDouble(6);
            price.eatingEggs6 = rs.getDouble(7);

            hatchEggLabel.setText("$ "+String.valueOf(price.hatchingEggs));
            pulletLabel.setText("$ "+String.valueOf(price.pullets));
            chickLabel.setText("$ "+String.valueOf(price.chicks));
            egg12Label.setText("$ "+String.valueOf(price.eatingEggs12));
            egg6Label.setText("$ "+String.valueOf(price.eatingEggs6));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public void changePrices()
    {
        try
        {
            Flock group = new Flock(0,0, "", "", 0, 0, "", false);
            Prices price = new Prices(0,0,0.00,0.00,0.00,0.00,0.00);

            group.breed = breedSelector.getValue().toString();
            try
            {
                String sqlCheck = ("SELECT * FROM Flock WHERE Breed ='" + group.breed + "' AND group_Ended = false;");
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = connection.prepareStatement(sqlCheck);
                ResultSet rs = ps.executeQuery();

                rs = connection.createStatement().executeQuery(sqlCheck);

                rs.next();
                group.flockID = rs.getInt(1);
                group.breedID = rs.getInt(2);
                group.eggColor = rs.getString(4);
                group.henNum = rs.getInt(5);
                group.roosterNum = rs.getInt(6);
                group.hatchDate = rs.getString(7);
                group.groupEnded = rs.getBoolean(8);

            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
                Alert finished = new Alert(Alert.AlertType.ERROR);
                finished.setTitle("Something Went Wrong");
                finished.setContentText("You must fill out all the fields");
                finished.show();
            }
            try
            {
                String sqlCheck = ("SELECT * FROM Pricing WHERE FlockID ='" + group.flockID + "';");
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = connection.prepareStatement(sqlCheck);
                ResultSet rs = ps.executeQuery();

                rs = connection.createStatement().executeQuery(sqlCheck);

                rs.next();
                price.priceID=rs.getInt(1);
                price.flockID=rs.getInt(2);
                price.hatchingEggs=rs.getDouble(3);
                price.pullets=rs.getDouble(4);
                price.chicks=rs.getDouble(5);
                price.eatingEggs12=rs.getDouble(6);
                price.eatingEggs6=rs.getDouble(7);

            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
                Alert finished = new Alert(Alert.AlertType.ERROR);
                finished.setTitle("Something Went Wrong");
                finished.setContentText("You must fill out all the fields");
                finished.show();
            }

            if (breedSelector.getValue().toString().length() > 0)
            {

                    try
                    {
                        breedNameLabel.setText(group.breed);
                        String query = "UPDATE Pricing SET " +
                                "Hatching_Eggs = '"+hatchEggBox.getText()+"', " +
                                "Pullets = '"+pulletBox.getText()+"'," +
                                "Chicks = '"+chickBox.getText()+"'," +
                                "Eating_Eggs12 = '"+egg12Box.getText()+"'," +
                                "Eating_Eggs6 = '"+egg6Box.getText()+"'" +
                                "WHERE FlockID = '"+price.flockID+"'";
                        Connection connection = DriverManager.getConnection(url, username, password);
                        PreparedStatement prepstate = connection.prepareStatement(query);
                        prepstate.execute();


                        Alert finished = new Alert(Alert.AlertType.CONFIRMATION);
                        finished.setTitle("Prices Set!");
                        finished.setContentText("Success! You've Set the Prices for the \n" + breedSelector.getValue() + " Group!");
                        finished.show();

                        loadPrices();
                    }

                    catch (Exception e)
                    {
                        e.printStackTrace();
                        Alert finished = new Alert(Alert.AlertType.ERROR);
                        finished.setTitle("Something Went Wrong");
                        finished.setContentText("You must fill out all the fields");
                        finished.show();
                    }

            }

        }
        catch (Exception e)
        {
            Alert finished = new Alert(Alert.AlertType.ERROR);
            finished.setTitle("Something Went Wrong");
            finished.setContentText("You must fill out all the fields");
            finished.show();

        }
    }


    public void switchToHomepage(ActionEvent event)
    {

        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void switchToEditFlockChoice(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("editFlockChoice.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void switchToCustomers(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("customerChoice.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void switchToSales(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("salesChoice.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void switchToFlock(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("flockChoice.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void switchToPricing(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("priceChoice.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void switchToExpenses(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("expenseChoice.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void logout(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        getFlockInfo();
    }
}
