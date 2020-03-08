package yeppy.service.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import yeppy.service.databaseUtility.*;
import yeppy.service.models.RegisterRequestModel;

import static yeppy.service.databaseUtility.DBConnectionFactory.getConnection;

public class Helper {
    public static String verifyLogin(String username, char[] password) {
        try {
            String sql = "SELECT user_id FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password.toString());
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
        try {
            // Construct the query
            String query =
                    "SELECT COUNT(*) FROM users WHERE username=?;";
            // Create the prepared statement
            PreparedStatement ps = getConnection().prepareStatement(query);

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
        try {

            char[] pword = requestModel.getPassword();
            // Construct the query
            String query =
                    "INSERT INTO users (username, password) VALUES (?, ?);";
            // Create the prepared statement
            PreparedStatement ps = getConnection().prepareStatement(query);
            // Set the parameters
            ps.setString(1, requestModel.getUsername());
            ps.setString(2, requestModel.getPassword().toString());;

            // Execute query
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
