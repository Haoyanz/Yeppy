package yeppy.service.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import yeppy.service.databaseUtility.*;
import yeppy.service.databaseUtility.mysql.MySQLConnection;
import yeppy.service.logger.ServiceLogger;
import yeppy.service.models.RegisterRequestModel;

import static yeppy.service.databaseUtility.DBConnectionFactory.getConnection;

public class Helper {
    public static String verifyLogin(String username, String password) {
        MySQLConnection db = new MySQLConnection();
        try {
            String sql = "SELECT user_id FROM yeppy.user WHERE username = ? AND password = ?";
            PreparedStatement stmt = db.getCon().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean existUser(String email){
        MySQLConnection db = new MySQLConnection();
        try {
            // Construct the query
            String query =
                    "SELECT COUNT(*) FROM yeppy.user WHERE username=?;";
            // Create the prepared statement
            PreparedStatement ps = db.getCon().prepareStatement(query);
            // Set the parameters
            ps.setString(1, email);

            // Execute query
            ResultSet rs = ps.executeQuery();
            rs.next();
            if(rs.getInt(1)>0){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean insertUserToDb(RegisterRequestModel requestModel){
        MySQLConnection db = new MySQLConnection();
        try {
            // Construct the query
            String query =
                    "INSERT INTO yeppy.user (user_id, username, password) VALUES (?, ?, ?);";
            // Create the prepared statement
            if(db == null){
                System.out.println("db is null");
            }
            if(db.getCon() == null){
                System.out.println("con is null");
            }
            PreparedStatement ps = db.getCon().prepareStatement(query);
            // Set the parameters
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, requestModel.getUsername());
            ps.setString(3, requestModel.getPassword());;

            // Execute query
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
