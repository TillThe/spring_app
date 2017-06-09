package hello.jdbc;

/**
 * Created by Кирилл on 12.01.2017.
 */

import hello.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserJDBC {

    int insert(User user) throws SQLException;
    int checkAuth(User user) throws SQLException;
//    boolean checkPass(User user) throws SQLException;
    int checkLogin(User user) throws SQLException;
    List<User> selectAll() throws SQLException;

}