package hello.model;

import org.springframework.stereotype.Component;

/**
 * Created by Кирилл on 05.01.2017.
 */
@Component
public class User {
    private String login;
    private String password;
    private String checkPassword;
    private boolean sex;
    private int auth = 0;
    private int wins;
    private int draws;
    private int defeats;
    private double points;
    private double winRate;

    public User() {

    }
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public User(String login, String password, String checkPassword, boolean sex) {
        this.login = login;
        this.password = password;
        this.checkPassword = checkPassword;
        this.sex = sex;
    }
    public User(String login, int wins, int draws, int defeats) {
        this.login = login;
        this.wins = wins;
        this.draws = draws;
        this.defeats = defeats;
    }
    public User(String login, String password, boolean sex, int auth, int wins, int draws, int defeats) {
        this.login = login;
        this.password = password;
        this.sex = sex;
        this.auth = auth;
        this.wins = wins;
        this.draws = draws;
        this.defeats = defeats;
    }
    public User(String login, String password, boolean sex, int auth, int wins, int draws, int defeats, double Points, double winRate) {
        this.login = login;
        this.password = password;
        this.sex = sex;
        this.auth = auth;
        this.wins = wins;
        this.draws = draws;
        this.defeats = defeats;
        this.points = points;
        this.winRate = winRate;
    }

    @Override
    public String toString(){
        return "Логин: " + this.getLogin() + "\nПароль: " + this.getPassword() + "\nПол: " + ((this.getSex() == true) ? "male" : "female")
                + "\nОчки: " + this.getPoints() + "\nПобеды: " + this.getWins() + "\nНичьи: " + this.getDraws()
                + "\nПоражения: " + this.getDefeats() + "\nПроцент побед: " + this.getWinRate() + "\nАвторизован: " + this.getAuth();
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCheckPassword() {
        return checkPassword;
    }
    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public boolean getSex() {
        return sex;
    }
    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getWins() { return wins; }
    public void setWins(int wins) { this.wins = wins; }

    public int getDraws() { return draws; }
    public void setDraws(int draws) { this.draws = draws; }

    public int getDefeats() { return defeats; }
    public void setDefeats(int defeats) { this.defeats = defeats; }

    public double getPoints() { return points; }
    public void setPoints() { points = 1 * wins + 0.5 * draws + 0 * defeats; }
//
    public double getWinRate() { return winRate; }
    public void setWinRate() { winRate = wins / (wins + draws + defeats); }
//
    public int getAuth() { return auth; }
    public void setAuth(int auth) { this.auth = auth; }
}
