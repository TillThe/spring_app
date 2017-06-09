package hello.model;

/**
 * Created by Кирилл on 25.03.2017.
 */
public class Game {
    // статус игры, 0 - идет, 1 - победа первого, 2 - победа второго, 3 - закончена ничьей, 4 - error
    private int status = 0;
    // кто ходит? 1 - первый игрок, 2 - второй игрок
    private int currentPlayer;

    public Game(int status, int currentPlayer) {
        this.status = status;
        this.currentPlayer = currentPlayer;
    }
    public Game() {

    }
    @Override
    public String toString() {
        return "Статус игры: " + this.status + "; Текущий ход: " + this.currentPlayer;
    }

    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int player) {
        currentPlayer = player;
    }
}
