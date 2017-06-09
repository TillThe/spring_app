package hello.jdbc.impl;

/**
 * Created by Кирилл on 12.01.2017.
 */

import hello.jdbc.UserJDBC;
import hello.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserJDBCimpl implements UserJDBC{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserJDBCimpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);}

    @Override
    public int insert(User user) throws SQLException {
        try {
            String sex = (user.getSex() == true) ? "male" : "female";
//            System.out.println(user);
            return jdbcTemplate.update("INSERT INTO CHESS_USERS (LOGIN, PASSWORD, SEX, WINS, DRAWS, DEFEATS, AUTH) VALUES (?, ?, ?, 0, 0, 0, 0)", new Object[]{user.getLogin(), user.getPassword(), sex});
        }
        catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int checkAuth(User user) throws SQLException {
        try {
//            System.out.println(user.getLogin() + " " + user.getPassword());
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM CHESS_USERS WHERE LOGIN = ? AND PASSWORD = ?", new Object[]{user.getLogin(), user.getPassword()});

            if (rowSet.first()) {
                String sex = rowSet.getString("SEX");
                boolean sexBool = false;
                if (sex == null) {
                    sexBool = true;
                    sex = "male";
                } else if (sex.equals("male")) {
                    sexBool = true;
                }
//                System.out.println("sex: " + sex);
                user.setSex(sexBool);
                user.setAuth(1);
                user.setWins(rowSet.getInt("WINS"));
                user.setDraws(rowSet.getInt("DRAWS"));
                user.setDefeats(rowSet.getInt("DEFEATS"));
                return 1;
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }
    @Override
    public int checkLogin(User user) throws SQLException {
        try {
            return jdbcTemplate.queryForObject("SELECT COUNT(ID) FROM CHESS_USERS WHERE LOGIN = ?", new Object[]{user.getLogin()}, Integer.class);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<User> selectAll() throws SQLException {
        List<User> rows = new ArrayList<User>();
        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT LOGIN, WINS, DRAWS, DEFEATS FROM CHESS_USERS");
            while (rowSet.next()) {
                User user = new User(rowSet.getString("login"),
                        rowSet.getInt("wins"),
                        rowSet.getInt("draws"),
                        rowSet.getInt("defeats"));
                rows.add(user);
            }
            return rows;
        } catch (Exception e) {
            return new ArrayList<User>();
        }

    }
}