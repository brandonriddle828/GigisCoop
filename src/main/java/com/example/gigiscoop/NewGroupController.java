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
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class NewGroupController implements Initializable
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
    protected TextField henCount;
    @FXML
    protected TextField roosterCount;
    @FXML
    protected DatePicker hatchDatePicker;


    ObservableList<String> breeds = FXCollections.observableArrayList();
    PreparedStatement stmt = null;
    ResultSet rset = null;


    public void getBreedList()
    {
        try
        {

            String sqlCheck = ("SELECT * FROM ChickenBreeds ORDER BY breed_Name asc;");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sqlCheck);
            rset = ps.executeQuery();
            breeds = FXCollections.observableArrayList();
            while (rset.next())
            {
               breeds.add(String.valueOf(rset.getString("Breed_Name")));
            }
            breedSelector.setItems(null);
            breedSelector.setItems(breeds);


        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void addNewGroup()
    {
        try
        {
            boolean checker;
            Flock group = new Flock(0,0, "","", 0, 0, "",false);
            ChickenBreed breed = new ChickenBreed(0,"","");


            group.breed = breedSelector.getValue().toString();
            group.henNum = Integer.parseInt(henCount.getText());
            group.roosterNum = Integer.parseInt(roosterCount.getText());
            group.hatchDate = hatchDatePicker.getValue().toString();


            int count = 0;
            PreparedStatement stmt = null;
            try
            {
                String sqlCheck = ("SELECT * FROM Flock WHERE Breed ='" + group.breed + "' AND group_Ended = false");
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = connection.prepareStatement(sqlCheck);
                rset = ps.executeQuery();
                if (rset.next())
                    count = rset.getInt(1);
            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            } finally
            {
                if (rset != null)
                {
                    try
                    {
                        rset.close();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
                if (stmt != null)
                {
                    try
                    {
                        stmt.close();
                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            if (count == 0)
            {
                checker = false;
            } else
            {
                checker = true;
            }

                if (breedSelector.getValue().toString().length() > 0 && henCount.getText().toString().length() > 0 && roosterCount.getText().length() > 0 && hatchDatePicker.getValue().toString().length() > 0)
                {
                    if (checker == true)
                    {
                        Alert finished1 = new Alert(Alert.AlertType.WARNING);
                        finished1.setTitle("Existing Group");
                        finished1.setContentText("A group with this breed already exist in the Flock.");
                        finished1.show();
                    }
                    else
                    {

                        try
                        {
                            String findEggColor = " SELECT * FROM ChickenBreeds WHERE breed_Name = '" + group.breed + "'";
                            Connection connection = DriverManager.getConnection(url, username, password);
                            PreparedStatement ps = connection.prepareStatement(findEggColor);
                            ResultSet rs = ps.executeQuery();

                            rs = connection.createStatement().executeQuery(findEggColor);

                            rs.next();
                            group.breedID = rs.getInt(1);
                            group.eggColor = rs.getString(3);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                            try
                            {
                                Connection connection = DriverManager.getConnection(url, username, password);
                                String query = "INSERT INTO Flock (BreedID,Breed, Egg_Color, Hens, Roosters, Hatch_Date,group_Ended) VALUES ('"+group.breedID+"','" + group.breed + "', '" + group.eggColor + "','" + group.henNum + "', '" + group.roosterNum + "', '" + group.hatchDate + "', False)";
                                PreparedStatement prepstate = connection.prepareStatement(query);
                                prepstate.execute();

                                henCount.clear();
                                roosterCount.clear();


                                Alert finished = new Alert(Alert.AlertType.CONFIRMATION);
                                finished.setTitle("Group Added");
                                finished.setContentText("Success! You've Added a group of "+group.breed+"'s to the Flock!");
                                finished.show();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                                try
                                {
                                    String findID = "SELECT * FROM Flock WHERE Breed = '"+group.breed+"';";
                                    Connection connection = DriverManager.getConnection(url, username, password);
                                    PreparedStatement ps = connection.prepareStatement(findID);
                                    ResultSet rs = ps.executeQuery();
                                    rs = connection.createStatement().executeQuery(findID);

                                    rs.next();
                                    group.flockID= rs.getInt(1);
                                    System.out.println(group.flockID);

                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }


                                    try
                                    {
                                        String query2 = "INSERT INTO Pricing (FlockID,Hatching_Eggs, Pullets, Chicks, Eating_Eggs12, Eating_Eggs6) VALUES ('"+group.flockID+"',0.00,0.00,0.00,0.00,0.00)";
                                        Connection connection = DriverManager.getConnection(url, username, password);
                                        PreparedStatement ps = connection.prepareStatement(query2);
                                        ps.execute();

                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }

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
        getBreedList();
    }
}


