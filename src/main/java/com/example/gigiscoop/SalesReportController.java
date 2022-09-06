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
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class SalesReportController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    String url = ("jdbc:mysql://localhost:3306/gigiscoop");
    String username = ("root");
    String password = ("password");


    @FXML
    protected ComboBox monthDropbox;
    @FXML
    protected ComboBox yearDropbox;

    @FXML
    protected DatePicker fromDate;
    @FXML
    protected DatePicker toDate;

    @FXML
    protected Label feedLabel;
    @FXML
    protected Label supplyLabel;
    @FXML
    protected Label gasLabel;
    @FXML
    protected Label structureLabel;
    @FXML
    protected Label hatchLabel;
    @FXML
    protected Label pulletLabel;
    @FXML
    protected Label chickLabel;
    @FXML
    protected Label egg12Label;
    @FXML
    protected Label egg6Label;
    @FXML
    protected Label otherLabel;
    @FXML
    protected Label profitLabel;

    ObservableList<String> sales = FXCollections.observableArrayList();

    public void loadYearlyReport()
    {
        if (yearDropbox.getValue() != null)
        {
            DecimalFormat f = new DecimalFormat("#.00");

            try
            {
                String hatchEggs = "SELECT sum(p.Hatching_Eggs * s.Hatching_Eggs) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                        "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE YEAR(s.Date_Sold) = " + yearDropbox.getValue() + ";";

                String pullets = "SELECT sum(p.pullets * s.pullets) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                        "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE YEAR(s.Date_Sold) = " + yearDropbox.getValue() + ";";

                String chicks = "SELECT sum(p.chicks * s.chicks) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                        "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE YEAR(s.Date_Sold) = " + yearDropbox.getValue() + ";";

                String egg12 = "SELECT sum(p.Eating_Eggs12 * s.Eating_Eggs12) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                        "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE YEAR(s.Date_Sold) = " + yearDropbox.getValue() + ";";

                String egg6 = "SELECT sum(p.Eating_Eggs6 * s.Eating_Eggs6) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                        "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE YEAR(s.Date_Sold) = " + yearDropbox.getValue() + ";";


                String feed = "SELECT sum(cost) FROM Expenses   WHERE type = 'feed' AND YEAR(Date_Purchased) = " + yearDropbox.getValue() + ";";

                String supply = "SELECT sum(cost) FROM Expenses   WHERE type = 'supply' AND YEAR(Date_Purchased) = " + yearDropbox.getValue() + ";";

                String gas = "SELECT sum(cost) FROM Expenses   WHERE type = 'gas' AND YEAR(Date_Purchased) = " + yearDropbox.getValue() + ";";

                String structure = "SELECT sum(cost) FROM Expenses   WHERE type = 'structure' AND YEAR(Date_Purchased) = " + yearDropbox.getValue() + ";";

                String other = "SELECT sum(cost) FROM Expenses   WHERE type = 'other' AND YEAR(Date_Purchased) = " + yearDropbox.getValue() + ";";


                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = connection.prepareStatement(hatchEggs);
                PreparedStatement ps1 = connection.prepareStatement(pullets);
                PreparedStatement ps2 = connection.prepareStatement(chicks);
                PreparedStatement ps3 = connection.prepareStatement(egg12);
                PreparedStatement ps4 = connection.prepareStatement(egg6);
                PreparedStatement ps5 = connection.prepareStatement(feed);
                PreparedStatement ps6 = connection.prepareStatement(supply);
                PreparedStatement ps7 = connection.prepareStatement(gas);
                PreparedStatement ps8 = connection.prepareStatement(structure);
                PreparedStatement ps9 = connection.prepareStatement(other);

                ResultSet rs = ps.executeQuery();
                rs = connection.createStatement().executeQuery(hatchEggs);

                ResultSet rs1 = ps1.executeQuery();
                rs1 = connection.createStatement().executeQuery(pullets);

                ResultSet rs2 = ps2.executeQuery();
                rs2 = connection.createStatement().executeQuery(chicks);

                ResultSet rs3 = ps3.executeQuery();
                rs3 = connection.createStatement().executeQuery(egg12);

                ResultSet rs4 = ps4.executeQuery();
                rs4 = connection.createStatement().executeQuery(egg6);

                ResultSet rs5 = ps5.executeQuery();
                rs5 = connection.createStatement().executeQuery(feed);

                ResultSet rs6 = ps6.executeQuery();
                rs6 = connection.createStatement().executeQuery(supply);

                ResultSet rs7 = ps7.executeQuery();
                rs7 = connection.createStatement().executeQuery(gas);

                ResultSet rs8 = ps8.executeQuery();
                rs8 = connection.createStatement().executeQuery(structure);

                ResultSet rs9 = ps9.executeQuery();
                rs9 = connection.createStatement().executeQuery(other);


                if (rs.next())
                {
                    hatchLabel.setText("$ " +f.format(rs.getDouble(1)));

                    if (rs1.next())
                        pulletLabel.setText("$ " +f.format(rs1.getDouble(1)));

                    if (rs2.next())
                        chickLabel.setText("$ " +f.format(rs2.getDouble(1)));

                    if (rs3.next())
                        egg12Label.setText("$ " +f.format(rs3.getDouble(1)));

                    if (rs4.next())
                        egg6Label.setText("$ " +f.format(rs4.getDouble(1)));

                    if (rs5.next())
                        feedLabel.setText("$ " +f.format(rs5.getDouble(1)));

                    if (rs6.next())
                        supplyLabel.setText("$ " +f.format(rs6.getDouble(1)));

                    if (rs7.next())
                        gasLabel.setText("$ " +f.format(rs7.getDouble(1)));

                    if (rs8.next())
                        structureLabel.setText("$ " +f.format(rs8.getDouble(1)));

                    if (rs9.next())
                        otherLabel.setText("$ " +f.format(rs9.getDouble(1)));

                    profitLabel.setText("$ " +f.format(rs.getDouble(1) + rs1.getDouble(1) + rs2.getDouble(1) + rs3.getDouble(1)
                            + rs4.getDouble(1) - rs5.getDouble(1) - rs6.getDouble(1) - rs7.getDouble(1) -
                            rs8.getDouble(1) - rs9.getDouble(1)));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public void loadMonthlyReport()
    {
        if (monthDropbox.getValue()!=null && yearDropbox.getValue()!=null)
        {
            DecimalFormat f = new DecimalFormat("#.00");

            String month = "0";

            if (monthDropbox.getValue().equals("January"))
            {
                month = "01";
            } else if (monthDropbox.getValue().equals("February"))
            {
                month = "02";
            } else if (monthDropbox.getValue().equals("March"))
            {
                month = "03";
            } else if (monthDropbox.getValue().equals("April"))
            {
                month = "04";
            } else if (monthDropbox.getValue().equals("May"))
            {
                month = "05";
            } else if (monthDropbox.getValue().equals("June"))
            {
                month = "06";
            } else if (monthDropbox.getValue().equals("July"))
            {
                month = "07";
            } else if (monthDropbox.getValue().equals("August"))
            {
                month = "08";
            } else if (monthDropbox.getValue().equals("September"))
            {
                month = "09";
            } else if (monthDropbox.getValue().equals("October"))
            {
                month = "10";
            } else if (monthDropbox.getValue().equals("November"))
            {
                month = "11";
            } else if (monthDropbox.getValue().equals("December"))
            {
                month = "12";
            }

        if (yearDropbox.getValue() != null)
        {


            try
            {
                String hatchEggs = "SELECT sum(p.Hatching_Eggs * s.Hatching_Eggs) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                        "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE YEAR(s.Date_Sold) = " + yearDropbox.getValue() + " AND MONTH(s.Date_Sold) = " + month + ";";

                String pullets = "SELECT sum(p.pullets * s.pullets) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                        "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE YEAR(s.Date_Sold) = " + yearDropbox.getValue() + " AND MONTH(s.Date_Sold) = " + month + ";";

                String chicks = "SELECT sum(p.chicks * s.chicks) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                        "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE YEAR(s.Date_Sold) = " + yearDropbox.getValue() + " AND MONTH(s.Date_Sold) = " + month + ";";

                String egg12 = "SELECT sum(p.Eating_Eggs12 * s.Eating_Eggs12) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                        "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE YEAR(s.Date_Sold) = " + yearDropbox.getValue() + " AND MONTH(s.Date_Sold) = " + month + ";";

                String egg6 = "SELECT sum(p.Eating_Eggs6 * s.Eating_Eggs6) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                        "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE YEAR(s.Date_Sold) = " + yearDropbox.getValue() + " AND MONTH(s.Date_Sold) = " + month + ";";


                String feed = "SELECT sum(cost) FROM Expenses   WHERE type = 'feed' AND YEAR(Date_Purchased) = " + yearDropbox.getValue() + " AND MONTH(Date_Purchased) = " + month + ";";

                String supply = "SELECT sum(cost) FROM Expenses   WHERE type = 'supply' AND YEAR(Date_Purchased) = " + yearDropbox.getValue() + " AND MONTH(Date_Purchased) = " + month + ";";

                String gas = "SELECT sum(cost) FROM Expenses   WHERE type = 'gas' AND YEAR(Date_Purchased) = " + yearDropbox.getValue() + " AND MONTH(Date_Purchased) = " + month + ";";

                String structure = "SELECT sum(cost) FROM Expenses   WHERE type = 'structure' AND YEAR(Date_Purchased) = " + yearDropbox.getValue() + " AND MONTH(Date_Purchased) = " + month + ";";

                String other = "SELECT sum(cost) FROM Expenses   WHERE type = 'other' AND YEAR(Date_Purchased) = " + yearDropbox.getValue() + " AND MONTH(Date_Purchased) = " + month + ";";


                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = connection.prepareStatement(hatchEggs);
                PreparedStatement ps1 = connection.prepareStatement(pullets);
                PreparedStatement ps2 = connection.prepareStatement(chicks);
                PreparedStatement ps3 = connection.prepareStatement(egg12);
                PreparedStatement ps4 = connection.prepareStatement(egg6);
                PreparedStatement ps5 = connection.prepareStatement(feed);
                PreparedStatement ps6 = connection.prepareStatement(supply);
                PreparedStatement ps7 = connection.prepareStatement(gas);
                PreparedStatement ps8 = connection.prepareStatement(structure);
                PreparedStatement ps9 = connection.prepareStatement(other);

                ResultSet rs = ps.executeQuery();
                rs = connection.createStatement().executeQuery(hatchEggs);

                ResultSet rs1 = ps1.executeQuery();
                rs1 = connection.createStatement().executeQuery(pullets);

                ResultSet rs2 = ps2.executeQuery();
                rs2 = connection.createStatement().executeQuery(chicks);

                ResultSet rs3 = ps3.executeQuery();
                rs3 = connection.createStatement().executeQuery(egg12);

                ResultSet rs4 = ps4.executeQuery();
                rs4 = connection.createStatement().executeQuery(egg6);

                ResultSet rs5 = ps5.executeQuery();
                rs5 = connection.createStatement().executeQuery(feed);

                ResultSet rs6 = ps6.executeQuery();
                rs6 = connection.createStatement().executeQuery(supply);

                ResultSet rs7 = ps7.executeQuery();
                rs7 = connection.createStatement().executeQuery(gas);

                ResultSet rs8 = ps8.executeQuery();
                rs8 = connection.createStatement().executeQuery(structure);

                ResultSet rs9 = ps9.executeQuery();
                rs9 = connection.createStatement().executeQuery(other);


                if (rs.next())
                {
                    hatchLabel.setText("$ " + f.format(rs.getDouble(1)));

                    if (rs1.next())
                        pulletLabel.setText("$ " + f.format(rs1.getDouble(1)));

                    if (rs2.next())
                        chickLabel.setText("$ " + f.format(rs2.getDouble(1)));

                    if (rs3.next())
                        egg12Label.setText("$ " + f.format(rs3.getDouble(1)));

                    if (rs4.next())
                        egg6Label.setText("$ " + f.format(rs4.getDouble(1)));

                    if (rs5.next())
                        feedLabel.setText("$ " + f.format(rs5.getDouble(1)));

                    if (rs6.next())
                        supplyLabel.setText("$ " + f.format(rs6.getDouble(1)));

                    if (rs7.next())
                        gasLabel.setText("$ " + f.format(rs7.getDouble(1)));

                    if (rs8.next())
                        structureLabel.setText("$ " + f.format(rs8.getDouble(1)));

                    if (rs9.next())
                        otherLabel.setText("$ " + f.format(rs9.getDouble(1)));

                    profitLabel.setText("$ " + f.format(rs.getDouble(1) + rs1.getDouble(1) + rs2.getDouble(1) + rs3.getDouble(1)
                            + rs4.getDouble(1) - rs5.getDouble(1) - rs6.getDouble(1) - rs7.getDouble(1) -
                            rs8.getDouble(1) - rs9.getDouble(1)));
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        }
    }

    public void loadDateEarningsReport()
    {
        DecimalFormat f = new DecimalFormat("#.00");

            if (fromDate != null && toDate!=null)
            {


                try
                {
                    String hatchEggs = "SELECT sum(p.Hatching_Eggs * s.Hatching_Eggs) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                            "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE s.Date_Sold BETWEEN '"+fromDate.getValue().toString()+"' AND '"+toDate.getValue().toString()+"';";

                    String pullets = "SELECT sum(p.pullets * s.pullets) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                            "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE s.Date_Sold BETWEEN '"+fromDate.getValue().toString()+"' AND '"+toDate.getValue().toString()+"';";

                    String chicks = "SELECT sum(p.chicks * s.chicks) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                            "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE s.Date_Sold BETWEEN '"+fromDate.getValue().toString()+"' AND '"+toDate.getValue().toString()+"';";

                    String egg12 = "SELECT sum(p.Eating_Eggs12 * s.Eating_Eggs12) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                            "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE s.Date_Sold BETWEEN '"+fromDate.getValue().toString()+"' AND '"+toDate.getValue().toString()+"';";

                    String egg6 = "SELECT sum(p.Eating_Eggs6 * s.Eating_Eggs6) FROM Flock f INNER JOIN Pricing p ON f.flockID = p.flockID " +
                            "INNER JOIN Sales s ON s.PriceID = p.PriceID  WHERE s.Date_Sold BETWEEN '"+fromDate.getValue().toString()+"' AND '"+toDate.getValue().toString()+"';";


                    String feed = "SELECT sum(cost) FROM Expenses   WHERE type = 'feed' AND Date_Purchased BETWEEN '"+fromDate.getValue().toString()+"' AND '"+toDate.getValue().toString()+"';";

                    String supply = "SELECT sum(cost) FROM Expenses   WHERE type = 'supply' AND Date_Purchased BETWEEN '"+fromDate.getValue().toString()+"' AND '"+toDate.getValue().toString()+"';";

                    String gas = "SELECT sum(cost) FROM Expenses   WHERE type = 'gas' AND Date_Purchased BETWEEN '"+fromDate.getValue().toString()+"' AND '"+toDate.getValue().toString()+"';";

                    String structure = "SELECT sum(cost) FROM Expenses   WHERE type = 'structure' AND Date_Purchased BETWEEN '"+fromDate.getValue().toString()+"' AND '"+toDate.getValue().toString()+"';";

                    String other = "SELECT sum(cost) FROM Expenses   WHERE type = 'other' AND Date_Purchased BETWEEN '"+fromDate.getValue().toString()+"' AND '"+toDate.getValue().toString()+"';";


                    Connection connection = DriverManager.getConnection(url, username, password);
                    PreparedStatement ps = connection.prepareStatement(hatchEggs);
                    PreparedStatement ps1 = connection.prepareStatement(pullets);
                    PreparedStatement ps2 = connection.prepareStatement(chicks);
                    PreparedStatement ps3 = connection.prepareStatement(egg12);
                    PreparedStatement ps4 = connection.prepareStatement(egg6);
                    PreparedStatement ps5 = connection.prepareStatement(feed);
                    PreparedStatement ps6 = connection.prepareStatement(supply);
                    PreparedStatement ps7 = connection.prepareStatement(gas);
                    PreparedStatement ps8 = connection.prepareStatement(structure);
                    PreparedStatement ps9 = connection.prepareStatement(other);

                    ResultSet rs = ps.executeQuery();
                    rs = connection.createStatement().executeQuery(hatchEggs);

                    ResultSet rs1 = ps1.executeQuery();
                    rs1 = connection.createStatement().executeQuery(pullets);

                    ResultSet rs2 = ps2.executeQuery();
                    rs2 = connection.createStatement().executeQuery(chicks);

                    ResultSet rs3 = ps3.executeQuery();
                    rs3 = connection.createStatement().executeQuery(egg12);

                    ResultSet rs4 = ps4.executeQuery();
                    rs4 = connection.createStatement().executeQuery(egg6);

                    ResultSet rs5 = ps5.executeQuery();
                    rs5 = connection.createStatement().executeQuery(feed);

                    ResultSet rs6 = ps6.executeQuery();
                    rs6 = connection.createStatement().executeQuery(supply);

                    ResultSet rs7 = ps7.executeQuery();
                    rs7 = connection.createStatement().executeQuery(gas);

                    ResultSet rs8 = ps8.executeQuery();
                    rs8 = connection.createStatement().executeQuery(structure);

                    ResultSet rs9 = ps9.executeQuery();
                    rs9 = connection.createStatement().executeQuery(other);


                    if (rs.next())
                    {
                        hatchLabel.setText("$ " + f.format(rs.getDouble(1)));

                        if (rs1.next())
                            pulletLabel.setText("$ " + f.format(rs1.getDouble(1)));

                        if (rs2.next())
                            chickLabel.setText("$ " + f.format(rs2.getDouble(1)));

                        if (rs3.next())
                            egg12Label.setText("$ " + f.format(rs3.getDouble(1)));

                        if (rs4.next())
                            egg6Label.setText("$ " + f.format(rs4.getDouble(1)));

                        if (rs5.next())
                            feedLabel.setText("$ " + f.format(rs5.getDouble(1)));

                        if (rs6.next())
                            supplyLabel.setText("$ " + f.format(rs6.getDouble(1)));

                        if (rs7.next())
                            gasLabel.setText("$ " + f.format(rs7.getDouble(1)));

                        if (rs8.next())
                            structureLabel.setText("$ " + f.format(rs8.getDouble(1)));

                        if (rs9.next())
                            otherLabel.setText("$ " + f.format(rs9.getDouble(1)));

                        profitLabel.setText("$ " + f.format(rs.getDouble(1) + rs1.getDouble(1) + rs2.getDouble(1) + rs3.getDouble(1)
                                + rs4.getDouble(1) - rs5.getDouble(1) - rs6.getDouble(1) - rs7.getDouble(1) -
                                rs8.getDouble(1) - rs9.getDouble(1)));
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }


        public void switchToHomepage (ActionEvent event)
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
