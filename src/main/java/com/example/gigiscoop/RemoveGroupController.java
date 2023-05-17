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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RemoveGroupController implements Initializable
{

    private Stage stage;
    private Scene scene;
    private Parent root;


    String url = ("jdbc:mysql://localhost:3306/gigiscoop");
    String username = ("root");
    String password = ("password");

    @FXML
    protected ComboBox breedSelector;



    ObservableList<String> breeds = FXCollections.observableArrayList();
    PreparedStatement stmt = null;
    ResultSet rset = null;

    public void getFlocks()
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

    public void removeGroup()
    {
        Flock group = new Flock(0,0, "", "", 0, 0, "", false);

        group.breed = breedSelector.getValue().toString();
        try
        {
            Connection connection = DriverManager.getConnection(url, username, password);
            String query = "UPDATE Flock SET group_Ended = true WHERE breed = '"+group.breed+"';";
            PreparedStatement prepstate = connection.prepareStatement(query);
            prepstate.execute();

            Alert finished = new Alert(Alert.AlertType.CONFIRMATION);
            finished.setTitle("Group Removed");
            finished.setContentText("Success! You've Removed your group of "+group.breed+"'s !");
            finished.show();

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }


    public void  switchToViewPastFlocks(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("fixedViewPastFlock.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void  switchToViewFlockChoices(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("viewFlockChoice.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
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
        getFlocks();
    }
}
