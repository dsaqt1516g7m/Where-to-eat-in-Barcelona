package edu.upc.eetac.dsa.group7.dao;

import edu.upc.eetac.dsa.group7.auth.UserInfo;
import edu.upc.eetac.dsa.group7.entity.AuthToken;

import java.sql.SQLException;

/**
 * Created by Alex on 29/11/15.
 */
public interface AuthTokenDAO {
    public UserInfo getUserByAuthToken(String token) throws SQLException;
    public AuthToken createAuthToken(String userid) throws SQLException;
    public void deleteToken(String userid) throws SQLException;
}