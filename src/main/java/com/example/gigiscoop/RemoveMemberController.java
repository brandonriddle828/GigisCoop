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

public class RemoveMemberController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;



    String url = ("jdbc:mysql://localhost:3306/gigiscoop");
    String username = ("root");
    String password = ("password");

    @FXML
    protected ComboBox breedSelector;
    @FXML
    protected TextField henCount;
    @FXML
    protected TextField roosterCount;
    @FXML
    protected DatePicker hatchDatePicker;
    @FXML
    protected Label breedNameLabel;
    @FXML
    protected Label henNumLabel;
    @FXML
    protected Label roosterNumLabel;


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

    public void loadGroup()
    {
        boolean checker;
        Flock group = new Flock(0,0, "","", 0, 0, "",false);
        group.breed = breedSelector.getValue().toString();
        try
        {
            String groupQuery = " SELECT * FROM Flock WHERE breed = '" + group.breed + "'";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(groupQuery);
            ResultSet rs = ps.executeQuery();

            rs = connection.createStatement().executeQuery(groupQuery);

            rs.next();
            group.henNum= rs.getInt(5);
            group.roosterNum= rs.getInt(6);

            breedNameLabel.setText(group.breed);
            henNumLabel.setText(String.valueOf(group.henNum));
            roosterNumLabel.setText(String.valueOf(group.roosterNum));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }






    }

    public void removeMember()
    {
        try
        {
            Flock group = new Flock(0,0, "","", 0, 0, "",false);

            try
            {
                String sqlCheck = ("SELECT * FROM Flock WHERE Breed ='" + group.breed + "' AND group_Ended = false");
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = connection.prepareStatement(sqlCheck);
                rset = ps.executeQuery();

            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }

            if (breedSelector.getValue().toString().length() > 0 && henCount.getText().toString().length() > 0 && roosterCount.getText().length() > 0)
            {
                try
                {
                    Connection connection = DriverManager.getConnection(url, username, password);
                    group.breed = breedSelector.getValue().toString();
                    String groupQuery = " SELECT * FROM Flock WHERE breed = '" + group.breed + "'";
                    PreparedStatement ps = connection.prepareStatement(groupQuery);
                    ResultSet rs = ps.executeQuery();

                    rs = connection.createStatement().executeQuery(groupQuery);

                    rs.next();
                    group.henNum = rs.getInt(5);
                    group.roosterNum = rs.getInt(6);
                    group.flockID = rs.getInt(1);

                    breedNameLabel.setText(group.breed);
                    henNumLabel.setText(String.valueOf(group.henNum));
                    roosterNumLabel.setText(String.valueOf(group.roosterNum));

                    try
                    {

                        String query = "UPDATE Flock SET Hens = " + group.henNum + " - " + henCount.getText()
                                + ",  Roosters = " + group.roosterNum + " - " + roosterCount.getText()  + "  WHERE flockID = "+ group.flockID +";";
                        PreparedStatement prepstate = connection.prepareStatement(query);
                        prepstate.execute();



                        Alert finished = new Alert(Alert.AlertType.CONFIRMATION);
                        finished.setTitle("Group Added");
                        finished.setContentText("Success! You've Removed Chickens from the \n" + breedSelector.getValue() + " Group!");
                        finished.show();

                        loadGroup();
                    }

                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
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




    public void  switchToHomepage(ActionEvent event)
    {

        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
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

    public void  switchToEditFlockChoice(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("editFlockChoice.fxml"));
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

    public void  switchToCustomers(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("customerChoice.fxml"));
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

    public void  switchToSales(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("salesChoice.fxml"));
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

    public void  switchToFlock(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("flockChoice.fxml"));
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

    public void  switchToPricing(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("priceChoice.fxml"));
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

    public void  switchToExpenses(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("expenseChoice.fxml"));
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

    public void  logout(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        getFlockInfo();
    }
}
