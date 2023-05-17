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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class NewSaleController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;



    String url = ("jdbc:mysql://localhost:3306/gigiscoop");
    String username = ("root");
    String password = ("password");


    @FXML
    protected ComboBox customerSelector;
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
    protected DatePicker saleDatePicker;
    @FXML
    protected Label profitLabel;
    @FXML
    protected TableView<Cart> saleCart;
    @FXML
    protected TableColumn<Cart, String > customerCol;
    @FXML
    protected TableColumn<Cart, String > breedCol;
    @FXML
    protected TableColumn <Cart, Integer > hatchEggCol;
    @FXML
    protected TableColumn <Cart, Integer > pulletCol;
    @FXML
    protected TableColumn <Cart, Integer > chickCol;
    @FXML
    protected TableColumn <Cart, Integer > egg12Col;
    @FXML
    protected TableColumn <Cart, Integer > egg6Col;
    @FXML
    protected TableColumn <Cart, Double > costCol;
    @FXML
    protected TableColumn <Cart, String > dateCol;

    public ObservableList<Flock> data;


    Cart cart = new Cart("","",0,0,0,0,0,0);
    ObservableList<String> customers = FXCollections.observableArrayList();
    ObservableList<String> breeds = FXCollections.observableArrayList();
    ObservableList<Cart> cartItems = FXCollections.observableArrayList();
    PreparedStatement stmt = null;
    ResultSet rset = null;


    public void getFlockInfo()
    {
        try
        {

            String sqlCheck = ("SELECT * FROM Flock f INNER JOIN Pricing p ON f.FlockID = p.FlockID  WHERE f.group_Ended = false AND p.Hatching_Eggs > 0 AND p.Pullets >0 AND p.chicks >0 AND p.Eating_Eggs12 >0 AND p.Eating_Eggs6 >0 ORDER BY f.Breed asc;");
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
    public void getCustomerInfo()
    {
        try
        {

            String sqlCheck = ("SELECT * FROM Customers ORDER BY Last_Name asc;");
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sqlCheck);
            rset = ps.executeQuery();
            customers = FXCollections.observableArrayList();
            while (rset.next())
            {
                customers.add(String.valueOf(rset.getString("first_name") +" "+ String.valueOf(rset.getString("last_name"))));

            }
            customerSelector.setItems(null);
            customerSelector.setItems(customers);


        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }


    public void addToCart()
    {
        try
        {
            Flock flock = new Flock(0,0, "","", 0, 0, "",false);
            Prices price = new Prices(0,0,0.00,0.00,0.00,0.00,0.00);
            Customer customer = new Customer(0,"","","");
            Sales sale = new Sales(0,0,0,0,0,0,0,0,"");
            Cart cart = new Cart("","",0,0,0,0,0,0);

            flock.breed = breedSelector.getValue().toString();
            String name = String.valueOf(customerSelector.getValue().toString());
            String[] words = name.split("\\s");
            customer.firstName = words[0];
            customer.lastName = words[1];


            int count = 0;
            PreparedStatement stmt = null;



            if (customerSelector.getValue().toString().length() > 0 && breedSelector.getValue().toString().length() > 0 && hatchEggBox.getText().toString().length() > 0
                    && pulletBox.getText().length() > 0 && chickBox.getText().length() > 0 && egg12Box.getText().length() > 0
                    && egg6Box.getText().length() > 0)
            {
                    try
                    {
                        String findBreed = " SELECT * FROM Flock WHERE breed = '"+flock.breed+"' AND Group_Ended=false;";
                        Connection connection = DriverManager.getConnection(url, username, password);
                        PreparedStatement ps = connection.prepareStatement(findBreed);
                        ResultSet rs = ps.executeQuery();

                        rs = connection.createStatement().executeQuery(findBreed);

                        rs.next();
                        flock.flockID = rs.getInt(1);



                        try
                        {

                            String insertSales = ("SELECT sum(p.Hatching_Eggs * " + Integer.parseInt(hatchEggBox.getText()) + ") " +
                                    "+ sum(p.Pullets * " + Integer.parseInt(pulletBox.getText()) + ") " +
                                    "+ sum(p.Chicks * " + Integer.parseInt(chickBox.getText()) + ") " +
                                    "+ sum(p.Eating_Eggs12 * " + Integer.parseInt(egg12Box.getText()) + ") " +
                                    "+ sum(p.Eating_Eggs6 * " + Integer.parseInt(egg6Box.getText()) + ") " +
                                    " FROM Flock f \n " +
                                    " INNER JOIN Pricing p ON f.flockID = p.flockID " +
                                    " WHERE p.flockID = "+flock.flockID+" ");

                            ResultSet rs3 = ps.executeQuery();
                            rs3 = connection.createStatement().executeQuery(insertSales);
                            rs3.next();
                            cart.cost = rs3.getDouble(1);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            System.out.println("nope");
                        }



                        cartItems.add(new Cart(customer.firstName.concat(" "+customer.lastName), flock.breed,Integer.parseInt(hatchEggBox.getText()),Integer.parseInt(pulletBox.getText()),Integer.parseInt(chickBox.getText()),Integer.parseInt(egg12Box.getText()),Integer.parseInt(egg6Box.getText()),cart.cost));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    customerCol.setCellValueFactory(new PropertyValueFactory<>( "customer"));
                    breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
                    hatchEggCol.setCellValueFactory(new PropertyValueFactory<>("hatchingEggs"));
                    pulletCol.setCellValueFactory(new PropertyValueFactory<>("pullets"));
                    chickCol.setCellValueFactory(new PropertyValueFactory<>("chicks"));
                    egg12Col.setCellValueFactory(new PropertyValueFactory<>("eatingEggs12"));
                    egg6Col.setCellValueFactory(new PropertyValueFactory<>("eatingEggs6"));
                    costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));


                    saleCart.setItems(null);
                    saleCart.setItems(cartItems);

            }
            else

                {
                    Alert finished = new Alert(Alert.AlertType.ERROR);
                    finished.setTitle("Something Went Wrong");
                    finished.setContentText("You must fill out all fields");
                    finished.show();
                }



        }
        catch (Exception e)
        {
            Alert finished = new Alert(Alert.AlertType.ERROR);
            finished.setTitle("Something Went Wrong");
            finished.setContentText("You must fill out all fields");
            finished.show();
        }
        
        // Fix this mess
        //      |
        //      |
        //     \ /
        //      V
        
        try
        {

            if (saleCart.getItems().size() == 1)
            {
                double a = saleCart.getItems().get(saleCart.getItems().size()-1).cost;
                profitLabel.setText("$ "+String.valueOf(a));

            }
            else if (saleCart.getItems().size() ==2)
            {
                double a = saleCart.getItems().get(saleCart.getItems().size()-1).cost+saleCart.getItems().get(saleCart.getItems().size()-2).cost;
                profitLabel.setText("$ "+String.valueOf(a));
            }
            else if (saleCart.getItems().size() ==3)
            {
                double a = saleCart.getItems().get(saleCart.getItems().size()-1).cost+saleCart.getItems().get(saleCart.getItems().size()-2).cost+saleCart.getItems().get(saleCart.getItems().size()-3).cost;
                profitLabel.setText("$ "+String.valueOf(a));
            }
            else if (saleCart.getItems().size() ==4)
            {
                double a = saleCart.getItems().get(saleCart.getItems().size()-1).cost+saleCart.getItems().get(saleCart.getItems().size()-2).cost+saleCart.getItems().get(saleCart.getItems().size()-3).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-4).cost;
                profitLabel.setText("$ "+String.valueOf(a));
            }
            else if (saleCart.getItems().size() ==5)
            {
                double a = saleCart.getItems().get(saleCart.getItems().size()-1).cost+saleCart.getItems().get(saleCart.getItems().size()-2).cost+saleCart.getItems().get(saleCart.getItems().size()-3).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-4).cost+saleCart.getItems().get(saleCart.getItems().size()-5).cost;
                profitLabel.setText("$ "+String.valueOf(a));
            }
            else if (saleCart.getItems().size() ==6)
            {
                double a = saleCart.getItems().get(saleCart.getItems().size()-1).cost+saleCart.getItems().get(saleCart.getItems().size()-2).cost+saleCart.getItems().get(saleCart.getItems().size()-3).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-4).cost+saleCart.getItems().get(saleCart.getItems().size()-5).cost+saleCart.getItems().get(saleCart.getItems().size()-6).cost;
                profitLabel.setText("$ "+String.valueOf(a));
            }
            else if (saleCart.getItems().size() ==7)
            {
                double a = saleCart.getItems().get(saleCart.getItems().size()-1).cost+saleCart.getItems().get(saleCart.getItems().size()-2).cost+saleCart.getItems().get(saleCart.getItems().size()-3).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-4).cost+saleCart.getItems().get(saleCart.getItems().size()-5).cost+saleCart.getItems().get(saleCart.getItems().size()-6).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-7).cost;
                profitLabel.setText("$ "+String.valueOf(a));
            }
            else if (saleCart.getItems().size() ==8)
            {
                double a = saleCart.getItems().get(saleCart.getItems().size()-1).cost+saleCart.getItems().get(saleCart.getItems().size()-2).cost+saleCart.getItems().get(saleCart.getItems().size()-3).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-4).cost+saleCart.getItems().get(saleCart.getItems().size()-5).cost+saleCart.getItems().get(saleCart.getItems().size()-6).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-7).cost+saleCart.getItems().get(saleCart.getItems().size()-8).cost;
                profitLabel.setText("$ "+String.valueOf(a));
            }
            else if (saleCart.getItems().size() ==9)
            {
                double a = saleCart.getItems().get(saleCart.getItems().size()-1).cost+saleCart.getItems().get(saleCart.getItems().size()-2).cost+saleCart.getItems().get(saleCart.getItems().size()-3).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-4).cost+saleCart.getItems().get(saleCart.getItems().size()-5).cost+saleCart.getItems().get(saleCart.getItems().size()-6).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-7).cost+saleCart.getItems().get(saleCart.getItems().size()-8).cost+saleCart.getItems().get(saleCart.getItems().size()-9).cost;
                profitLabel.setText("$ "+String.valueOf(a));
            }
            else if (saleCart.getItems().size() ==10)
            {
                double a = saleCart.getItems().get(saleCart.getItems().size()-1).cost+saleCart.getItems().get(saleCart.getItems().size()-2).cost+saleCart.getItems().get(saleCart.getItems().size()-3).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-4).cost+saleCart.getItems().get(saleCart.getItems().size()-5).cost+saleCart.getItems().get(saleCart.getItems().size()-6).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-7).cost+saleCart.getItems().get(saleCart.getItems().size()-8).cost+saleCart.getItems().get(saleCart.getItems().size()-9).cost
                        +saleCart.getItems().get(saleCart.getItems().size()-10).cost;
                profitLabel.setText("$ "+String.valueOf(a));

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        hatchEggBox.clear();
        pulletBox.clear();
        chickBox.clear();
        egg12Box.clear();
        egg6Box.clear();
    }



    public void submitSale()
    {

        Flock flock = new Flock(0,0, "","", 0, 0, "",false);
        Prices price = new Prices(0,0,0.00,0.00,0.00,0.00,0.00);
        Customer customer = new Customer(0,"","","");
        Sales sale = new Sales(0,0,0,0,0,0,0,0,"");
        Cart cart = new Cart("","",0,0,0,0,0,0);



        try
        {

            flock.breed = breedSelector.getValue().toString();
            if (saleCart.getItems().size()!=0)
            {
                while (saleCart.getItems().size() > 0)
                {


                    String findFlock = ("SELECT * \n" +
                            "FROM Flock\n" +
                            "WHERE group_Ended = False AND breed = '" + saleCart.getItems().get(saleCart.getItems().size() - 1).breed + "';");

                    Connection connection = DriverManager.getConnection(url, username, password);
                    PreparedStatement ps = connection.prepareStatement(findFlock);
                    ResultSet rs = ps.executeQuery();
                    rs = connection.createStatement().executeQuery(findFlock);
                    rs.next();

                    flock.flockID = rs.getInt(1);
                    flock.breedID = rs.getInt(2);
                    flock.eggColor = rs.getString(4);
                    flock.henNum = rs.getInt(5);


                    String findPrice = ("SELECT * \n" +
                            "FROM Pricing\n" +
                            "WHERE FlockID = " + flock.flockID + ";");

                    ResultSet rs2 = ps.executeQuery();
                    rs2 = connection.createStatement().executeQuery(findPrice);
                    rs2.next();

                    price.priceID = rs2.getInt(1);
                    price.flockID = rs2.getInt(2);
                    price.hatchingEggs = rs2.getDouble(3);
                    price.pullets = rs2.getDouble(4);
                    price.chicks = rs2.getDouble(5);
                    price.eatingEggs12 = rs2.getDouble(6);
                    price.eatingEggs6 = rs2.getDouble(7);


                    String name = saleCart.getItems().get(saleCart.getItems().size() - 1).customer;
                    String[] words = name.split("\\s");
                    customer.firstName = words[0];
                    customer.lastName = words[1];

                    String findCustomer = ("SELECT * \n" +
                            "FROM Customers\n" +
                            "WHERE First_Name = '" + customer.firstName + "' AND Last_Name = '" + customer.lastName + "';");

                    ResultSet rs3 = ps.executeQuery();
                    rs3 = connection.createStatement().executeQuery(findCustomer);
                    rs3.next();

                    customer.customerID = rs3.getInt(1);
                    customer.phone = rs3.getString(4);


                    String insertSales = ("INSERT INTO Sales (PriceID, CustomerID, Hatching_Eggs, Pullets, Chicks, Eating_Eggs12, Eating_Eggs6, Date_Sold)" +
                            "VALUES ( " +
                            "" + price.priceID + "," + customer.customerID + "," +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).hatchingEggs + "," +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).pullets + ", " +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).chicks + "," +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).eatingEggs12 + ", " +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).eatingEggs6 + ", " +
                            "'" + saleDatePicker.getValue().toString() + "');");

                    saleCart.getItems().remove(saleCart.getItems().size() - 1);

                    PreparedStatement ps2 = connection.prepareStatement(insertSales);
                    ps2.execute();




                }

                hatchEggBox.clear();
                pulletBox.clear();
                chickBox.clear();
                egg12Box.clear();
                egg6Box.clear();
                profitLabel.setText("$ 0.00");

                Alert finished = new Alert(Alert.AlertType.CONFIRMATION);
                finished.setTitle("Sales Added");
                finished.setContentText("Success! You've added a Sale/Sales. KEEP IT UP!!!");
                finished.show();
            }
            else
            {
                Alert finished = new Alert(Alert.AlertType.ERROR);
                finished.setTitle("Cart Empty");
                finished.setContentText("Your Cart is Empty!");
                finished.show();
            }
        }
        catch (Exception e)
        {
            Alert finished = new Alert(Alert.AlertType.ERROR);
            finished.setTitle("Something Went Wrong");
            finished.setContentText("You must fill out all fields!");
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
        getCustomerInfo();
        getFlockInfo();

    }
}
