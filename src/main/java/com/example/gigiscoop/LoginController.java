package com.example.gigiscoop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;;
import java.util.ResourceBundle;

public class LoginController implements Initializable
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    protected TextField username;
    @FXML
    protected PasswordField password;
    @FXML
    protected ImageView myImageView;
    boolean check;

    ArrayList <Users> users;



    public void loginPageSuccess(ActionEvent event)
    {
        users = new ArrayList<>();
        Users gina = new Users("Gina", "1","1");
        Users brandon = new Users("Brandon","1","1");
        // trial = new Users("trial","1","1");

        Users loginUser = null;
        users.add(gina);
        users.add(brandon);
        //users.add(trial);



        for (Users user: users)
        {

            if (user.getUsername().equals(username.getText()))
            {
                if (user.getPassword().equals(password.getText()))
                {
                    loginUser = user;
                    check = true;
                    break;
                }
            }
            else
            {
                check = false;
            }

        }

        if (check == true)
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

        } else
        {
            Alert finished = new Alert(Alert.AlertType.ERROR);
            finished.setTitle("Wrong Password or Username");
            finished.setContentText("This Username and Password are not registered.");
            finished.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
}


