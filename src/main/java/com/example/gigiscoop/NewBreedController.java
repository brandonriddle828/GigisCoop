package com.example.gigiscoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class NewBreedController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    String url = ("jdbc:mysql://localhost:3306/gigiscoop");
    String username = ("root");
    String password = ("1!");


    @FXML
    protected TextField breednamebox;
    @FXML
    protected TextField eggColorBox;


    public void addNewBreed()
    {

            boolean checker;
            ChickenBreed breed = new ChickenBreed(0,"","");

            breed.breedName = breednamebox.getText().toUpperCase(Locale.ROOT);
            breed.eggColor = eggColorBox.getText().toUpperCase(Locale.ROOT);


            int count = 0;
            PreparedStatement stmt = null;
            ResultSet rset = null;
            try
            {
                String sqlCheck = ("SELECT * FROM ChickenBreeds WHERE Breed_Name ='" + breed.breedName + "'");
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


            if (breednamebox.getText().length() > 0 && eggColorBox.getText().length()>0)
            {
                if (checker == true)
                {
                    Alert finished1 = new Alert(Alert.AlertType.WARNING);
                    finished1.setTitle("Existing Breed");
                    finished1.setContentText("A Breed with this name already exist in the database.");
                    finished1.show();
                } else

                    try
                    {
                        Connection connection = DriverManager.getConnection(url, username, password);
                        String query = "INSERT INTO ChickenBreeds (Breed_Name , Egg_Color) VALUES ('" + breed.breedName + "','" + breed.eggColor + "')";
                        PreparedStatement prepstate = connection.prepareStatement(query);
                        prepstate.execute();

                        breednamebox.clear();
                        eggColorBox.clear();

                        Alert finished = new Alert(Alert.AlertType.CONFIRMATION);
                        finished.setTitle("Breed Added");
                        finished.setContentText("Success! You've Added a new Breed!");
                        finished.show();


                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            } else
            {
                Alert finished = new Alert(Alert.AlertType.ERROR);
                finished.setTitle("Something Went Wrong");
                finished.setContentText("You must have a Breed Name and Egg Color");
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

    }
}
