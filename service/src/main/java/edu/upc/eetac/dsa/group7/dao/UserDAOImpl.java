package edu.upc.eetac.dsa.group7.dao;

import edu.upc.eetac.dsa.group7.db.Database;
import edu.upc.eetac.dsa.group7.entity.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alex on 29/11/15.
 */
public class UserDAOImpl implements UserDAO {
    @Override
    public User getUserById(String id) throws SQLException {
        User user = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            //Stablish the connection
            connection = Database.getConnection();

            //Prepare the query
            stmt = connection.prepareStatement(UserDAOQuery.GET_USER_BY_ID);
            //Set values
            stmt.setString(1, id);

            //Execute the query
            ResultSet rs = stmt.executeQuery();
            //Store the results
            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setLoginid(rs.getString("loginid"));
                user.setEmail(rs.getString("email"));
                user.setFullname(rs.getString("fullname"));
            }
        } catch (SQLException e) {
            //Catch errors
            throw e;
        } finally {
            //Close the connection
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        // Returns the user for printing it
        return user;
    }

    @Override
    public User updateProfile(String id, String email, String fullname) throws SQLException {
        User user = null;
        //Prepare the connection

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            //prepare and set parameters of the query
            stmt = connection.prepareStatement(UserDAOQuery.UPDATE_USER);
            stmt.setString(1, email);
            stmt.setString(2, fullname);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                user = getUserById(id);
        } catch (SQLException e) {
            //catch errors
            throw e;
        } finally {
            //close the connection
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        //return user for printing
        return user;
    }

    @Override
    //get user by loginid
    public User getUserByLoginid(String loginid) throws SQLException {
        User user = null;
        //prepare the connection

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            //prepare and set the parameters of the query
            stmt = connection.prepareStatement(UserDAOQuery.GET_USER_BY_USERNAME);
            stmt.setString(1, loginid);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                //Store the results
                user.setId(rs.getString("id"));
                user.setLoginid(rs.getString("loginid"));
                user.setEmail(rs.getString("email"));
                user.setFullname(rs.getString("fullname"));
            }
        } catch (SQLException e) {
            //catch exceptions
            throw e;
        } finally {
            //close connection
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        //return user for printing
        return user;
    }

    @Override
    public boolean deleteUser(String id) throws SQLException {
        //prepare the connection with database
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            //set is and execute the query
            stmt = connection.prepareStatement(UserDAOQuery.DELETE_USER);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            //catch errors
            throw e;
        } finally {
            //close connection
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    //check password for login
    public boolean checkPassword(String id, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        //prepare the connection
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.GET_PASSWORD);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                try {
                    //encrypt the password
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(password.getBytes());
                    String passedPassword = new BigInteger(1, md.digest()).toString(16);

                    return passedPassword.equalsIgnoreCase(storedPassword);
                } catch (NoSuchAlgorithmException e) {
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public User createUser(String loginid, String password, String email, String fullname) throws SQLException, UserAlreadyExistsException {
        Connection connection = null;
        PreparedStatement stmt = null;
        //prepare the connection
        String id = null;
        try {
            User user = getUserByLoginid(loginid);
            if (user != null)
                throw new UserAlreadyExistsException();

            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);

            stmt.close();
            stmt = connection.prepareStatement(UserDAOQuery.CREATE_USER);
            //store data in database
            stmt.setString(1, id);
            stmt.setString(2, loginid);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setString(5, fullname);
            stmt.executeUpdate();

            stmt.close();
            stmt = connection.prepareStatement(UserDAOQuery.ASSIGN_ROLE_REGISTERED);
            //call to set the role registered
            stmt.setString(1, id);
            stmt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw e;
            //catch errors
        } finally {
            //close connection
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        //return the user for printing
        return getUserById(id);
    }

    @Override
    //method to create an admin user. The same as normal user
    public User createAdmin() throws SQLException {
        String loginid = "admin", password = "admin", email = "admin@admin", fullname = "I'm the admin.";

        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);


            stmt.close();
            stmt = connection.prepareStatement(UserDAOQuery.CREATE_USER);
            stmt.setString(1, id);
            stmt.setString(2, loginid);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setString(5, fullname);
            stmt.executeUpdate();

            stmt.close();
            stmt = connection.prepareStatement(UserDAOQuery.ASSIGN_ROLE_ADMIN);
            stmt.setString(1, id);
            stmt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getUserByLoginid(loginid);
    }

    @Override
    //method to create an owner user. The same as normal user
    public User createOwner() throws SQLException {
        String loginid = "alex", password = "alex", email = "alex@alex", fullname = "Hi, I'm Alex";

        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            connection.setAutoCommit(false);


            stmt.close();
            stmt = connection.prepareStatement(UserDAOQuery.CREATE_USER);
            stmt.setString(1, id);
            stmt.setString(2, loginid);
            stmt.setString(3, password);
            stmt.setString(4, email);
            stmt.setString(5, fullname);
            stmt.executeUpdate();

            stmt.close();
            stmt = connection.prepareStatement(UserDAOQuery.ASSIGN_ROLE_OWNER);
            stmt.setString(1, id);
            stmt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getUserByLoginid(loginid);
    }

    @Override
    //method to check if a user is an owner or an admin
    public boolean owner(String id) throws SQLException {
        Connection connection = null;
        String auth = null;
        String owner = "owner";
        String admin = "admin";
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(UserDAOQuery.GET_ROLES_OF_USER);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                auth = rs.getString("role");
            }
            if (auth.equals(admin) || auth.equals(owner)) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    //method to check if a user is an admin
    public boolean admin(String id) throws SQLException {
        Connection connection = null;
        String auth = null;
        String admin = "admin";
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(UserDAOQuery.GET_ROLES_OF_USER);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                auth = rs.getString("role");
            }
            if (auth.equals(admin)) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}