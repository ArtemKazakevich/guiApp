package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.animations.Shake;
import sample.config.DatabaseHandler;
import sample.model.User;

public class Controller {
     
     @FXML
     private ResourceBundle resources;
     
     @FXML
     private URL location;
     
     @FXML
     private TextField login_field;
     
     @FXML
     private PasswordField password_field;
     
     @FXML
     private Button authSignInButton;
     
     @FXML
     private Button loginSignUpButton;
     
     @FXML
     void initialize() {
          
          //  проверяем login и password при заполнении полей
          authSignInButton.setOnAction(event -> {
               String loginText = login_field.getText().trim();
               String loginPassword = password_field.getText().trim();
               
               if (!loginText.equals("") && !loginPassword.equals("")) {
                    loginUser(loginText, loginPassword);
               } else {
                    System.out.println("Login and password is empty");
               }
          });
          
          // переход на новое окно при нажатии кнопки "Зарегестрироваться"
          loginSignUpButton.setOnAction(event -> {
               openNewScene("/sample/views/signUp.fxml");
          });
     }
     
     private void loginUser(String loginText, String loginPassword) {
          DatabaseHandler dbHandler = new DatabaseHandler();
          User user = new User();
          
          user.setUserName(loginText);
          user.setPassword(loginPassword);
     
          ResultSet resultSet = dbHandler.getUser(user);
          
          int counter = 0;
          
          try {
               while (resultSet.next()) {
                    counter++;
               }
          } catch (SQLException e) {
               e.printStackTrace();
          }
          
          if (counter >=1) {
               openNewScene("/sample/views/app.fxml");
          } else {
               //если пользователь не найден, сработает анимация
               Shake userLoginAnim = new Shake(login_field);
               Shake userPassAnim = new Shake(password_field);
               userLoginAnim.playAnim();
               userPassAnim.playAnim();
          }
     }
     
     public void openNewScene(String window) {
          loginSignUpButton.getScene().getWindow().hide(); // прячем окно
     
          // указываем путь для новго окна, которое появится после нажатия кнопки "Зарегестрироваться"
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource(window));
     
          // загружаем новый файл
          try {
               loader.load();
          } catch (IOException e) {
               e.printStackTrace();
          }
     
          Parent root = loader.getRoot(); // путь к новому файлу
          Stage stage = new Stage();
          stage.setScene(new Scene(root)); // указываем необходимое окно, которое нужно загрузить
          stage.showAndWait(); // показать данное окно
     }
}


