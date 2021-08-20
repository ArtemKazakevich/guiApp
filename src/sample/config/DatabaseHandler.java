package sample.config;

import sample.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DatabaseHandler extends Configs {
     Connection dbConnection;
     
     // подключение к БД
     public Connection getDbConnection() throws ClassNotFoundException, SQLException {
          String connectionString =  "jdbc:mysql://" + dbHost + ":" + dbPort + "/" +dbName;
          Class.forName("com.mysql.jdbc.Driver");
          
          dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
          
          return dbConnection;
     }
     
     // запись пользователя в БД
     public void signUpUser(User user) {
     
          // SQL-запрос
          String insert = "INSERT INTO " + Const.USER_TABLE + "(" +
                  Const.USERS_FIRSTNAME + "," +
                  Const.USERS_LASTNAME + "," +
                  Const.USERS_USERNAME + "," +
                  Const.USERS_PASSWORD + "," +
                  Const.USERS_LOCATION + "," +
                  Const.USERS_GENDER + ")" +
                  "VALUES(?,?,?,?,?,?)";
     
          try {
               PreparedStatement prSt = getDbConnection().prepareStatement(insert);
     
               // записываем параметры вместо "?"
               prSt.setString(1, user.getFirstName());
               prSt.setString(2, user.getLastName());
               prSt.setString(3, user.getUserName());
               prSt.setString(4, user.getPassword());
               prSt.setString(5, user.getLocation());
               prSt.setString(6, user.getGender());
               
               prSt.executeUpdate(); // выполнение sql-запроса. Добавляем в БД
          } catch (SQLException e) {
               e.printStackTrace();
          } catch (ClassNotFoundException e) {
               e.printStackTrace();
          }
     }
     
     // берем пользователя из БД
     public ResultSet getUser(User user) {
          ResultSet resultSet = null;
          
          String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USERS_USERNAME + "=? AND " + Const.USERS_PASSWORD + "=?";
     
          try {
               PreparedStatement prSt = getDbConnection().prepareStatement(select);
          
               // записываем параметры вместо "?"
               prSt.setString(1, user.getUserName());
               prSt.setString(2, user.getPassword());
               
               resultSet = prSt.executeQuery(); // получаем данные из БД
          } catch (SQLException e) {
               e.printStackTrace();
          } catch (ClassNotFoundException e) {
               e.printStackTrace();
          }
     
          return resultSet;
     }
}
