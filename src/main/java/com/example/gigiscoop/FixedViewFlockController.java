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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class FixedViewFlockController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    String url = ("jdbc:mysql://localhost:3306/gigiscoop");
    String username = ("root");
    String password = ("Roxysdad828!");

    @FXML
    protected TableView<FixedFlock> flockTable;
    @FXML
    protected TableColumn<FixedFlock, Integer > idCol;
    @FXML
    protected TableColumn <FixedFlock, String > breedCol;
    @FXML
    protected TableColumn <FixedFlock, String > eggCol;
    @FXML
    protected TableColumn <FixedFlock, Integer > henCol;
    @FXML
    protected TableColumn <FixedFlock, Integer > roosterCol;
    @FXML
    protected TableColumn <FixedFlock, Integer > pulletCol;
    @FXML
    protected TableColumn <FixedFlock, Integer > cockerelCol;
    @FXML
    protected TableColumn <FixedFlock, Integer > chickCol;
    @FXML
    protected TableColumn <FixedFlock, String > hatchCol;

    public ObservableList<FixedFlock> data;


    public void loadFlockTable()
    {
        try
        {
            String sqlCheck = ("SELECT * FROM NewFlock WHERE group_Ended = false;");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sqlCheck);
            ResultSet rs = ps.executeQuery();

            data = FXCollections.observableArrayList();
            rs=connection.createStatement().executeQuery(sqlCheck);

            while (rs.next())
            {
                data.add(new FixedFlock(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getString(10),rs.getBoolean(11)));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("flockID"));
        breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
        eggCol.setCellValueFactory(new PropertyValueFactory<>("eggColor"));
        henCol.setCellValueFactory(new PropertyValueFactory<>("henNum"));
        roosterCol.setCellValueFactory(new PropertyValueFactory<>("roosterNum"));
        pulletCol.setCellValueFactory(new PropertyValueFactory<>("pulletNum"));
        cockerelCol.setCellValueFactory(new PropertyValueFactory<>("cockerelNum"));
        chickCol.setCellValueFactory(new PropertyValueFactory<>("chickNum"));
        hatchCol.setCellValueFactory(new PropertyValueFactory<>("hatchDate"));

        flockTable.setItems(null);
        flockTable.setItems(data);
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
    public void  switchToCreateCustomer(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("createClient1.fxml"));
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
        loadFlockTable();
    }
}


