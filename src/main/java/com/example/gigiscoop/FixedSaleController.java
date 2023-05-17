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
import java.util.Random;
import java.util.ResourceBundle;

public class FixedSaleController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;



    String url = ("jdbc:mysql://localhost:3306/gigiscoop");
    String username = ("root");
    String password = ("1!");

    double realPrice;


    @FXML
    protected ComboBox customerSelector;
    @FXML
    protected ComboBox breedSelector;
    @FXML
    protected TextField hatchEggBox;
    @FXML
    protected TextField pulletBox;
    @FXML
    protected TextField henBox;
    @FXML
    protected TextField roosterBox;
    @FXML
    protected TextField cockerelBox;
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
    protected TableView<FixedCart> saleCart;
    @FXML
    protected TableColumn<FixedCart, String > customerCol;
    @FXML
    protected TableColumn<FixedCart, String > breedCol;
    @FXML
    protected TableColumn <FixedCart, Integer > hatchEggCol;
    @FXML
    protected TableColumn <FixedCart, Integer > pulletCol;
    @FXML
    protected TableColumn <FixedCart, Integer > cockerelCol;
    @FXML
    protected TableColumn <FixedCart, Integer > henCol;
    @FXML
    protected TableColumn <FixedCart, Integer > roosterCol;
    @FXML
    protected TableColumn <FixedCart, Integer > chicksCol;
    @FXML
    protected TableColumn <FixedCart, Integer > egg12Col;
    @FXML
    protected TableColumn <FixedCart, Integer > egg6Col;
    @FXML
    protected TableColumn <FixedCart, Double > costCol;
    @FXML
    protected TableColumn <FixedCart, String > dateCol;

    public ObservableList<Flock> data;


    FixedCart cart = new FixedCart("","",0,0,0,0,0,0,0,0,0);
    ObservableList<String> customers = FXCollections.observableArrayList();
    ObservableList<String> breeds = FXCollections.observableArrayList();
    ObservableList<FixedCart> cartItems = FXCollections.observableArrayList();
    PreparedStatement stmt = null;
    ResultSet rset = null;


    public void getFlockInfo()
    {
        try
        {

            String sqlCheck = ("SELECT * FROM NewFlock f INNER JOIN NewPricing p ON f.FlockID = p.FlockID  WHERE f.group_Ended = false AND p.Hatching_Eggs > 0 AND p.Pullets >0 AND p.chicks >0 AND p.Eating_Eggs12 >0 AND p.Eating_Eggs6 >0 ORDER BY f.Breed asc;");
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
            FixedFlock flock = new FixedFlock(0,0, "","", 0, 0,0,0,0, "",false);
            FixedPrices price = new FixedPrices(0,0,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00);
            Customer customer = new Customer(0,"","","");
            FixedSales sale = new FixedSales(0,0,0,0,0,0,0,0,0,0,0,0.00,"");
            FixedCart cart = new FixedCart("","",0,0,0,0,0,0,0,0,0);

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
                    String findBreed = " SELECT * FROM NewFlock WHERE breed = '"+flock.breed+"' AND Group_Ended=false;";
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
                                "+ sum(p.Cockerels * " + Integer.parseInt(cockerelBox.getText()) + ") " +
                                "+ sum(p.Hens * " + Integer.parseInt(henBox.getText()) + ") " +
                                "+ sum(p.Roosters * " + Integer.parseInt(roosterBox.getText()) + ") " +
                                "+ sum(p.Chicks * " + Integer.parseInt(chickBox.getText()) + ") " +
                                "+ sum(p.Eating_Eggs12 * " + Integer.parseInt(egg12Box.getText()) + ") " +
                                "+ sum(p.Eating_Eggs6 * " + Integer.parseInt(egg6Box.getText()) + ") " +
                                "+ "+cart.cost+""+
                                " FROM NewFlock f \n " +
                                " INNER JOIN NewPricing p ON f.flockID = p.flockID " +
                                " WHERE p.flockID = "+flock.flockID+" ");

                        ResultSet rs3 = ps.executeQuery();
                        rs3 = connection.createStatement().executeQuery(insertSales);
                        rs3.next();
                        cart.cost = rs3.getDouble(1);
                    }
                    catch (Exception e)
                    {
                        System.out.println("error 1");
                        e.printStackTrace();
                        System.out.println("nope");
                    }



                    cartItems.add(new FixedCart(customer.firstName.concat(" "+customer.lastName), flock.breed,Integer.parseInt(roosterBox.getText()),Integer.parseInt(henBox.getText()),Integer.parseInt(pulletBox.getText()), Integer.parseInt(cockerelBox.getText()),Integer.parseInt(chickBox.getText()),Integer.parseInt(hatchEggBox.getText()),Integer.parseInt(egg12Box.getText()),Integer.parseInt(egg6Box.getText()),cart.cost));
                }
                catch (Exception e)
                {
                    System.out.println("Error 2");
                    e.printStackTrace();
                }

                customerCol.setCellValueFactory(new PropertyValueFactory<>( "customer"));
                breedCol.setCellValueFactory(new PropertyValueFactory<>("breed"));
                hatchEggCol.setCellValueFactory(new PropertyValueFactory<>("hatchingEggs"));
                pulletCol.setCellValueFactory(new PropertyValueFactory<>("pullets"));
                henCol.setCellValueFactory(new PropertyValueFactory<>("hens"));
                roosterCol.setCellValueFactory(new PropertyValueFactory<>("roosters"));
                cockerelCol.setCellValueFactory(new PropertyValueFactory<>("cockerels"));
                chicksCol.setCellValueFactory(new PropertyValueFactory<>("chicks"));
                egg12Col.setCellValueFactory(new PropertyValueFactory<>("eatingEggs12"));
                egg6Col.setCellValueFactory(new PropertyValueFactory<>("eatingEggs6"));
                costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));


                saleCart.setItems(null);
                saleCart.setItems(cartItems);

            }
            else

            {
                System.out.println("Error 3");
                Alert finished = new Alert(Alert.AlertType.ERROR);
                finished.setTitle("Something Went Wrong");
                finished.setContentText("You must fill out all fields");
                finished.show();
            }



        }
        catch (Exception e)
        {
            System.out.println("error 4");
            Alert finished = new Alert(Alert.AlertType.ERROR);
            finished.setTitle("Something Went Wrong");
            finished.setContentText("You must fill out all fields");
            finished.show();
            e.printStackTrace();
        }
        try
        {

            if (saleCart.getItems().size() >= 1) {
                double profit = 0.0;
                for (FixedCart item : saleCart.getItems()) {
                    profit += item.cost;
                }
                profitLabel.setText("$ " + String.valueOf(profit));
            }



        }
        catch (Exception e)
        {
            System.out.println("Error 5");
            e.printStackTrace();
        }

        hatchEggBox.clear();
        pulletBox.clear();
        roosterBox.clear();
        cockerelBox.clear();
        henBox.clear();
        chickBox.clear();
        egg12Box.clear();
        egg6Box.clear();

        hatchEggBox.setText("0");
        pulletBox.setText("0");
        cockerelBox.setText("0");
        roosterBox.setText("0");
        henBox.setText("0");
        chickBox.setText("0");
        egg12Box.setText("0");
        egg6Box.setText("0");

    }



    public void submitSale()
    {

        FixedFlock flock = new FixedFlock(0,0, "","", 0, 0,0,0,0, "",false);
        FixedPrices price = new FixedPrices(0,0,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00);
        Customer customer = new Customer(0,"","","");
        FixedSales sale = new FixedSales(0,0,0,0,0,0,0,0,0,0,0,0.00,"");
        FixedCart cart = new FixedCart("","",0,0,0,0,0,0,0,0,0);



        try
        {

            flock.breed = breedSelector.getValue().toString();
            if (saleCart.getItems().size()!=0)
            {
                while (saleCart.getItems().size() > 0)
                {


                    String findFlock = ("SELECT * \n" +
                            "FROM NewFlock\n" +
                            "WHERE group_Ended = False AND breed = '" + saleCart.getItems().get(saleCart.getItems().size() - 1).breed + "';");

                    Connection connection = DriverManager.getConnection(url, username, password);
                    PreparedStatement ps = connection.prepareStatement(findFlock);
                    ResultSet rs = ps.executeQuery();
                    rs = connection.createStatement().executeQuery(findFlock);
                    rs.next();

                    flock.flockID = rs.getInt(1);
                    flock.breedID = rs.getInt(2);
                    flock.breed = rs.getString(3);
                    flock.eggColor = rs.getString(4);
                    flock.henNum = rs.getInt(5);
                    flock.roosterNum = rs.getInt(6);
                    flock.pulletNum = rs.getInt(7);
                    flock.cockerelNum = rs.getInt(8);
                    flock.chickNum = rs.getInt(9);




                    String findPrice = ("SELECT * \n" +
                            "FROM NewPricing\n" +
                            "WHERE FlockID = " + flock.flockID + ";");

                    ResultSet rs2 = ps.executeQuery();
                    rs2 = connection.createStatement().executeQuery(findPrice);
                    rs2.next();

                    price.priceID = rs2.getInt(1);
                    price.flockID = rs2.getInt(2);
                    price.hatchingEggs = rs2.getDouble(3);
                    price.hens = rs2.getDouble(4);
                    price.roosters = rs2.getDouble(5);
                    price.pullets = rs2.getDouble(6);
                    price.cockerels = rs2.getDouble(7);
                    price.chicks = rs2.getDouble(8);
                    price.eatingEggs12 = rs2.getDouble(9);
                    price.eatingEggs6 = rs2.getDouble(10);


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


                    String insertSales = ("INSERT INTO NewSales (PriceID, CustomerID, Hatching_Eggs, Pullets, Cockerels,Hens,Roosters, Chicks, Eating_Eggs12, Eating_Eggs6, Date_Sold, Sale_Price, Hen_Price, Rooster_Price, Pullet_Price, Cockerel_Price, Chick_Price, Hatching_Eggs_Price, Eating_Eggs12_Price, Eating_Eggs6_Price)" +
                            "VALUES ( " +
                            "" + price.priceID + "," + customer.customerID + "," +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).hatchingEggs + "," +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).pullets + ", " +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).cockerels + ", " +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).hens + ", " +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).roosters + ", " +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).chicks + "," +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).eatingEggs12 + ", " +
                            "" + saleCart.getItems().get(saleCart.getItems().size() - 1).eatingEggs6 + ", " +
                            "'" + saleDatePicker.getValue().toString() + "', " +
                            "" + saleCart.getItems().get(saleCart.getItems().size()-1).cost + ", "  +
                            "" + price.hens+ ", "  +
                            "" + price.roosters+ ", "  +
                            "" + price.pullets+ ", "  +
                            "" + price.cockerels+ ", "  +
                            "" + price.chicks+ ", "  +
                            "" + price.hatchingEggs+ ", "  +
                            "" + price.eatingEggs12+ ", "  +
                            "" + price.eatingEggs6+ ");");

                    try
                    {

                        String removeChicks = ("" +
                                " UPDATE NewFlock " +
                                " SET Pullets= " + flock.pulletNum +" - "+ saleCart.getItems().get(saleCart.getItems().size() - 1).pullets +"," +
                                " Cockerels= " + "" + flock.cockerelNum+" - "+ saleCart.getItems().get(saleCart.getItems().size() - 1).cockerels + "," +
                                " Hens= " + "" + flock.henNum+" - "+ saleCart.getItems().get(saleCart.getItems().size() - 1).hens+ "," +
                                " Roosters= " + "" + flock.roosterNum+" - "+ saleCart.getItems().get(saleCart.getItems().size() - 1).roosters + "," +
                                " Chicks= " + "" + flock.chickNum+" - "+ saleCart.getItems().get(saleCart.getItems().size() - 1).chicks +
                                " WHERE FlockID = "+flock.flockID+"; ");

                        PreparedStatement prepstate = connection.prepareStatement(removeChicks);
                        prepstate.execute();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        System.out.println("Update Error");
                    }

                    saleCart.getItems().remove(saleCart.getItems().size() - 1);

                    PreparedStatement ps2 = connection.prepareStatement(insertSales);
                    ps2.execute();








                }

                hatchEggBox.setText("0");
                pulletBox.setText("0");
                cockerelBox.setText("0");
                roosterBox.setText("0");
                henBox.setText("0");
                chickBox.setText("0");
                egg12Box.setText("0");
                egg6Box.setText("0");
                profitLabel.setText("$ 0.00");


                String[] success = {"HOORAH! You've added a Sale/Sales. KEEP IT UP!!!", "WOOHOO! You've added a Sale/Sales. YOU'RE KILLING IT!!!", "YIPPEE! You've added a Sale/Sales, LOOK AT YOU GO!!! "};
                Random random = new Random();
                int index = random.nextInt(success.length);

                Alert finished = new Alert(Alert.AlertType.CONFIRMATION);
                finished.setTitle("Sales Added");
                finished.setContentText(success[index]);
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
        getCustomerInfo();
        getFlockInfo();

    }
}
