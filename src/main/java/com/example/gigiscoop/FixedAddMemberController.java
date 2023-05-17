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

public class FixedAddMemberController implements Initializable
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
    protected TextField pulletCount;
    @FXML
    protected TextField cockerelCount;
    @FXML
    protected TextField chickCount;
    @FXML
    protected DatePicker hatchDatePicker;
    @FXML
    protected Label breedNameLabel;
    @FXML
    protected Label henNumLabel;
    @FXML
    protected Label roosterNumLabel;
    @FXML
    protected Label pulletNumLabel;
    @FXML
    protected Label cockerelNumLabel;
    @FXML
    protected Label chickNumLabel;

    ObservableList<String> breeds = FXCollections.observableArrayList();
    PreparedStatement stmt = null;
    ResultSet rset = null;
    public void getFlockInfo()
    {
        try
        {

            String sqlCheck = ("SELECT * FROM NewFlock WHERE group_Ended = false ORDER BY Breed asc;");
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
        FixedFlock group = new FixedFlock(0,0, "","", 0,0,0,0, 0, "",false);
        group.breed = breedSelector.getValue().toString();
        try
        {
            String groupQuery = " SELECT * FROM NewFlock WHERE breed = '" + group.breed + "'";
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(groupQuery);
            ResultSet rs = ps.executeQuery();

            rs = connection.createStatement().executeQuery(groupQuery);

            rs.next();
            group.henNum= rs.getInt(5);
            group.roosterNum= rs.getInt(6);
            group.pulletNum= rs.getInt(7);
            group.cockerelNum= rs.getInt(8);
            group.chickNum= rs.getInt(9);

            breedNameLabel.setText(group.breed);
            henNumLabel.setText(String.valueOf(group.henNum));
            roosterNumLabel.setText(String.valueOf(group.roosterNum));
            pulletNumLabel.setText(String.valueOf(group.pulletNum));
            cockerelNumLabel.setText(String.valueOf(group.cockerelNum));
            chickNumLabel.setText(String.valueOf(group.chickNum));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }






    }

    public void addMember()
    {
        try
        {
            FixedFlock group = new FixedFlock(0,0, "","", 0,0,0,0, 0, "",false);

            try
            {
                String sqlCheck = ("SELECT * FROM NewFlock WHERE Breed ='" + group.breed + "' AND group_Ended = false");
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = connection.prepareStatement(sqlCheck);
                rset = ps.executeQuery();

            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }

            if (breedSelector.getValue().toString().length() > 0 && henCount.getText().toString().length() > 0 && chickCount.getText().toString().length() > 0 && cockerelCount.getText().toString().length() > 0 && pulletCount.getText().toString().length() > 0 && roosterCount.getText().length() > 0 && hatchDatePicker.getValue().toString().length() > 0)
            {
                try
                {
                    Connection connection = DriverManager.getConnection(url, username, password);
                    group.breed = breedSelector.getValue().toString();
                    String groupQuery = " SELECT * FROM NewFlock WHERE breed = '" + group.breed + "'";
                    PreparedStatement ps = connection.prepareStatement(groupQuery);
                    ResultSet rs = ps.executeQuery();

                    rs = connection.createStatement().executeQuery(groupQuery);

                    rs.next();
                    group.henNum = rs.getInt(5);
                    group.roosterNum = rs.getInt(6);
                    group.pulletNum= rs.getInt(7);
                    group.cockerelNum= rs.getInt(8);
                    group.chickNum= rs.getInt(9);
                    group.flockID = rs.getInt(1);


                    breedNameLabel.setText(group.breed);
                    henNumLabel.setText(String.valueOf(group.henNum));
                    roosterNumLabel.setText(String.valueOf(group.roosterNum));
                    pulletNumLabel.setText(String.valueOf(group.pulletNum));
                    cockerelNumLabel.setText(String.valueOf(group.cockerelNum));
                    chickNumLabel.setText(String.valueOf(group.chickNum));

                    try
                    {

                        String query = "UPDATE NewFlock SET Hens = " + henCount.getText() + " + " + group.henNum + "," +
                                "Chicks = " + chickCount.getText() + " + " + group.chickNum + "," +
                                "Pullets = " + pulletCount.getText() + " + " + group.pulletNum + "," +
                                "Cockerels = " + cockerelCount.getText() + " + " + group.cockerelNum + "," +
                                "  Roosters = " + roosterCount.getText() + " + " + group.roosterNum +
                                ", Hatch_Date = CONCAT(hatch_Date,   '   |   "+ hatchDatePicker.getValue().toString()+ "')  " +
                                "WHERE flockID = "+ group.flockID +";";
                        PreparedStatement prepstate = connection.prepareStatement(query);
                        prepstate.execute();



                        Alert finished = new Alert(Alert.AlertType.CONFIRMATION);
                        finished.setTitle("Group Added");
                        finished.setContentText("Success! You've Added Chickens to the \n" + breedSelector.getValue() + " Group! \nKeep Growing that flock Gigi!!!");
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
            e.printStackTrace();

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