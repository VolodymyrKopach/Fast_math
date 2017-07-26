package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Random;

/**
 * Created by vova on 20.07.17.
 */

public class GameWorld2 {
    int int_min_plus, int_max_plus, int_left_result, int_right_result, true_variant, int_left_number_1, int_left_number_2, int_right_number_1, int_right_number_2;

    public int int_score = 0;
    public String string_input = " ";
    public String string_answer;

    public String string_score = "0", string_timer, string_best_score_this_level;

    public Preferences preferences_game_gw2, preferences_easy_gw2, preferences_normal_gw2, preferences_hard_gw2;

    public float float_timer = 15;
    int int_timer = 2;  //любе число, головне >0

    public boolean boolean_X = false, boolean_text_button = false;

    String string_left_znak = "", string_right_znak = "";

    GameScreen2 gameScreen2;

    public GameWorld2(GameScreen2 gameScreen2) { // запускаться відразу при запуску класа
        this.gameScreen2 = gameScreen2;
        preferences_game_gw2 = Gdx.app.getPreferences("My_preferences_game");

        setGame_gw2("normal"); //по стандарту буде відкриватись рівень easy

        preferences_easy_gw2 = Gdx.app.getPreferences("My_preferences_score_easy");
        preferences_normal_gw2 = Gdx.app.getPreferences("My_preferences_score_normal");
        preferences_hard_gw2 = Gdx.app.getPreferences("My_preferences_score_hard");

        startGame();
    }

    public void startGame() {
        Gdx.app.log("GameWorld2", "запуск гри");

        boolean_text_button = false;
        // зчитує який рівен вибраний, і після запускає гру
        if (getGame_gw2().equals("easy")) {
            game_easy();
        } else if (getGame_gw2().equals("normal")) {
            game_normal();
        } else if (getGame_gw2().equals("hard")) {
            game_hard();
        }
    }


    public void game_easy() {
        Gdx.app.log("GameWorld2", "game level");

        setString_best_score_this_level(String.valueOf(getHighScore_easy_gw2()));

        // створення приклада
        int_min_plus = 10;
        int_max_plus = 100;

        int prykladrandom = new Random().nextInt(2);
        int_left_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        int_left_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        switch (prykladrandom) {
            case 0: // додавання
                int_left_result = int_left_number_1 + int_left_number_2;
                string_left_znak = "+";
                break;

            case 1:  // віднімання
                int_left_result = int_left_number_1 - int_left_number_2;
                string_left_znak = "-";
                break;
        }

        prykladrandom = new Random().nextInt(2);
        int_right_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        int_right_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        switch (prykladrandom) {
            case 0: // додавання
                int_right_result = int_right_number_1 + int_right_number_2;
                string_right_znak = "+";
                break;

            case 1:  // віднімання
                int_right_result = int_right_number_1 - int_right_number_2;
                string_right_znak = "-";
                break;
        }

        setString_answer(int_left_result, int_right_result);

    }

    public void game_normal() {
        Gdx.app.log("GameWorld2", "game level");

        setString_best_score_this_level(String.valueOf(getHighScore_normal_gw2()));

        // створення приклада
        int_min_plus = 100;
        int_max_plus = 1000;

        int prykladrandom = new Random().nextInt(2);
        int_left_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        int_left_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        switch (prykladrandom) {
            case 0: // додавання
                int_left_result = int_left_number_1 + int_left_number_2;
                string_left_znak = "+";
                break;

            case 1:  // віднімання
                int_left_result = int_left_number_1 - int_left_number_2;
                string_left_znak = "-";
                break;
        }

        prykladrandom = new Random().nextInt(2);
        int_right_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        int_right_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        switch (prykladrandom) {
            case 0: // додавання
                int_right_result = int_right_number_1 + int_right_number_2;
                string_right_znak = "+";
                break;

            case 1:  // віднімання
                int_right_result = int_right_number_1 - int_right_number_2;
                string_right_znak = "-";
                break;
        }

        setString_answer(int_left_result, int_right_result);
    }

    public void game_hard() {
        Gdx.app.log("GameWorld2", "game level");

        setString_best_score_this_level(String.valueOf(getHighScore_hard_gw2()));

        // створення приклада
        int_min_plus = 1000;
        int_max_plus = 10000;

        int prykladrandom = new Random().nextInt(2);
        int_left_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        int_left_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        switch (prykladrandom) {
            case 0: // додавання
                int_left_result = int_left_number_1 + int_left_number_2;
                string_left_znak = "+";
                break;

            case 1:  // віднімання
                int_left_result = int_left_number_1 - int_left_number_2;
                string_left_znak = "-";
                break;
        }

        prykladrandom = new Random().nextInt(2);
        int_right_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        int_right_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        switch (prykladrandom) {
            case 0: // додавання
                int_right_result = int_right_number_1 + int_right_number_2;
                string_right_znak = "+";
                break;

            case 1:  // віднімання
                int_right_result = int_right_number_1 - int_right_number_2;
                string_right_znak = "-";
                break;
        }

        setString_answer(int_left_result, int_right_result);

    }

    private void correctAnswer() { //викликаєтьсчя після того, як ти вибрав правильну відповідь
        boolean_X = false;
        boolean_text_button = true;
        int_score++;
        setString_score(String.valueOf(int_score));
        float_timer = 15;
        setString_input("");

        if (getGame_gw2().equals("easy")) {
            setHighScore_easy_gw2(int_score);
            game_easy();

        } else if (getGame_gw2().equals("normal")) {
            setHighScore_normal_gw2(int_score);
            game_normal();

        } else if (getGame_gw2().equals("hard")) {
            setHighScore_hard_gw2(int_score);
            game_hard();
        }
    }

    private void incorrectAnswer(){

    }


    public void setString_answer(int int_left_result, int int_right_result ) {
        if(int_left_result<int_right_result){
            string_answer = "<";

        }else if(int_left_result>int_right_result){
            string_answer = ">";

        }else if(int_left_result==int_right_result){
            string_answer = "=";
        }

    }


    public void answer(String answer) {  // метод який виконується коли вибираєш якусь відповідь

        Gdx.app.log("GameWorld2", "answer");

        if(answer.equals(getString_answer())){
            correctAnswer();
        }else {
            incorrectAnswer();
        }

    }



    public String getString_answer() {return string_answer;}


    public void setInt_left_number_1(int int_left_number_1) {this.int_left_number_1 = int_left_number_1;}

    public int getInt_left_number_1() {return int_left_number_1;}


    public void setInt_left_number_2(int int_left_number_2) {this.int_left_number_2 = int_left_number_2;}

    public int getInt_left_number_2() {return int_left_number_2;}


    public void setInt_right_number_1(int int_right_number_1) {this.int_right_number_1 = int_right_number_1;}

    public int getInt_right_number_1() {return int_right_number_1;}


    public void setInt_right_number_2(int int_right_number_2) {this.int_right_number_2 = int_right_number_2;}

    public int getInt_right_number_2() {return int_right_number_2;}


    public void setString_left_znak(String string_left_znak) {this.string_left_znak = string_left_znak;}

    public String getString_left_znak() {return string_left_znak;}


    public void setString_right_znak(String string_right_znak) {this.string_right_znak = string_right_znak;}

    public String getString_right_znak() {return string_right_znak;}

    public void setGame_gw2(String string_game) {  //  запам'ятовування вибраного рівня
        preferences_game_gw2.putString("save_game", string_game);
        preferences_game_gw2.flush();

    }

    public String getGame_gw2() {
        return preferences_game_gw2.getString("save_game");
    }


    public void setHighScore_easy_gw2(int int_score_easy_to_save) {
        if (int_score_easy_to_save > getHighScore_easy_gw2()) {
            preferences_easy_gw2.putInteger("save_int_score", int_score_easy_to_save);
            preferences_easy_gw2.flush();
        }
    }

    public int getHighScore_easy_gw2() {
        return preferences_easy_gw2.getInteger("save_int_score", 0);

    }

    public void setHighScore_normal_gw2(int int_score_normal_to_save) {
        if (int_score_normal_to_save > getHighScore_normal_gw2()) {
            preferences_normal_gw2.putInteger("save_int_score", int_score_normal_to_save);
            preferences_normal_gw2.flush();
        }
    }

    public int getHighScore_normal_gw2() {
        return preferences_normal_gw2.getInteger("save_int_score", 0);

    }

    public void setHighScore_hard_gw2(int int_score_hard_to_save) {
        if (int_score_hard_to_save > getHighScore_hard_gw2()) {
            preferences_hard_gw2.putInteger("save_int_score", int_score_hard_to_save);
            preferences_hard_gw2.flush();
        }
    }

    public int getHighScore_hard_gw2() {
        return preferences_hard_gw2.getInteger("save_int_score", 0);

    }


    public void setString_input(String string_input) {
        this.string_input = string_input;
    }

    public String getString_input() {
        return string_input;
    }


    public void setString_score(String string_score) {
        this.string_score = string_score;
    }

    public String getString_score() {
        return string_score;
    }

    public void setString_best_score_this_level(String string_best_result_this_level) {this.string_best_score_this_level = string_best_result_this_level;}

    public String getString_best_score_this_level() {return string_best_score_this_level;}


    public void setString_timer(float dt) {
        float_timer -= dt;

        int_timer = (int) float_timer;

        string_timer = int_timer + "";
    }

    public String getString_timer() {
        return string_timer;
    }


    public boolean getBoolean_X() {
        return boolean_X;
    }

    public boolean getBoolean_text_button() {
        return boolean_text_button;
    }

}
