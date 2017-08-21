package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Random;

/**
 * Created by vova on 18.05.17.
 */

public class GameWorld1 {
    int int_min_plus, int_max_plus, int_result, number_1, number_2;

    public int int_score = 0;
    public String string_to_screen = "0";
    public String string_input = "";

    public String string_score;

    public Preferences preferences_game1;

    public float float_timer = 15;
    //TODO переробити
    int int_timer = 2;  //любе число, головне >0
    int bestScore;

    int money;

    public boolean bool_replay, bool_timer_game;

    public GameWorld1() {

        preferences_game1 = Gdx.app.getPreferences("pref1");
        string_input = "";
        bestScore = getBestScore();
        buildGame();
        money = MyPreference.getMoney();
    }

    public boolean checkIsAnswerTrue() {
        if (string_input.equals(String.valueOf(int_result))) {
            answerRight();
            return true;
        } else {
            Gdx.app.log("tag", "Answer Wrong");
            answerWrong();
            return false;
        }
    }

    void answerRight(){
        ++int_score;
        float_timer = 15;
        calculateMoney();
        buildGame();
        string_input = "";

        setBestScore(int_score);
    }

    void answerWrong(){
        //Що буде якщо введено не правильну відповідь
    }

    private void calculateMoney() {
        switch (int_score){
            case 10:
                money+=5;
                //зберігаємо к-ть монет в преференс
                MyPreference.setMoney(money);
                break;
            case 20:
                money+=10;
                MyPreference.setMoney(money);
                break;
            case 50:
                money+=20;
                MyPreference.setMoney(money);
                break;
            case 100:
                money+=50;
                MyPreference.setMoney(money);
                break;
        }
    }

    public void setBestScore(int bestScore) {
        preferences_game1.putInteger("best_score", bestScore);
        preferences_game1.flush();
    }

    public int getBestScore() {
        return preferences_game1.getInteger("best_score", 0);
    }

    public void buildGame() {
        bool_timer_game = true;
        string_input = "";

        int prykladrandom = (int) (Math.random() * 2);

        string_score = int_score + "";

        int_min_plus = 50 + int_score * 15;
        int_max_plus = 70 + int_score * 15;
        number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;

        switch (prykladrandom) {
            case 0:
                int_result = number_1 + number_2;
                string_to_screen = number_1 + "+" + number_2 + "=";
                break;

            case 1:
                int_result = number_1 - number_2;
                string_to_screen = number_1 + "-" + number_2 + "=";
                break;
        }

    }


    public void timer_game(float dt) {
        float_timer -= dt;
        int_timer = (int) float_timer;

        if (float_timer < 0) {
            bool_replay = true;
            bool_timer_game = false;
        } else {
        }
    }


    public String getString_to_screen() {
        return string_to_screen;
    }

    public String getString_input() {
        return string_input;
    }

    public String getString_score() {
        return string_score;
    }

    public void setString_input(String number) {
        if (string_input.length() < String.valueOf(int_result).length()) {
            string_input = string_input + number;
        }
    }

    public void prees_C() {
        if (!string_input.equals("")) {
            string_input = string_input.substring(0, string_input.length() - 1);
        }
    }
}
