package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Random;

/**
 * Created by vova on 18.05.17.
 */

public class GameWorld1 {

    int int_min_plus, int_max_plus, int_result, true_variant, int_number_1, int_number_2;

    public int int_score = 0;
    public String string_to_screen = "";
    public String string_input = "";

    public String string_score, string_timer_game, string_best_score_this_level;

    public Preferences preferences_game_gw1, preferences_easy_gw1, preferences_normal_gw1, preferences_hard_gw1;

    public float float_timer = 15, float_timer_wait = 0.5f;
    int int_timer = 2;  //любе число, головне >0

    public boolean bool_answer_right = false, bool_timer_wait_start = false;

    String string_znak = "", string_propusk_in_pryklad = "";
    public int int_btn_1, int_btn_2, int_btn_3, int_btn_4, int_btn_5, int_btn_6;

    GameScreen1 gameScreen1;
    String firstPart;

    public String getSecondPart() {
        return secondPart;
    }

    public String getFirstPart() {

        return firstPart;
    }

    String secondPart;
    private int questionMarkPosition;

    public GameWorld1(GameScreen1 gameScreen1) { // запускаться відразу при запуску класа
        this.gameScreen1 = gameScreen1;
        preferences_game_gw1 = Gdx.app.getPreferences("My_preferences_game_gw1");

        setGame("normal"); //по стандарту буде відкриватись рівень easy

        preferences_easy_gw1 = Gdx.app.getPreferences("My_preferences_score_easy_gw1");
        preferences_normal_gw1 = Gdx.app.getPreferences("My_preferences_score_normal_gw1");
        preferences_hard_gw1 = Gdx.app.getPreferences("My_preferences_score_hard_gw1");

        startGame();
    }

    public void startGame() {
        Gdx.app.log("GameWorld1", "запуск гри");

        string_input = "";
        // зчитує який рівен вибраний, і після запускає гру
        if (getGame().equals("easy")) {
            setHighScore_easy_gw1(int_score);
            game_easy();
        } else if (getGame().equals("normal")) {
            setHighScore_normal_gw1(int_score);
            game_normal();
        } else if (getGame().equals("hard")) {
            setHighScore_hard_gw1(int_score);
            game_hard();
        }
    }


    public void game_easy() {
        Gdx.app.log("GameWorld1", "game level");

        setString_best_score_this_level(String.valueOf(getHighScore_easy_gw1()));

        int prykladrandom = new Random().nextInt(2);
        // створення приклада
        int_min_plus = 10;
        int_max_plus = 100;

        int_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        int_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        switch (prykladrandom) {
            case 0: // додавання
                int_result = int_number_1 + int_number_2;
                string_znak = "+";
                break;

            case 1:  // віднімання
                int_result = int_number_1 - int_number_2;
                string_znak = "-";
                break;
        }
        make_pryklad();

    }

    public void game_normal() {
        Gdx.app.log("GameWorld1", "game normal");

        setString_best_score_this_level(String.valueOf(getHighScore_normal_gw1()));

        int prykladrandom = (int) (Math.random() * 2);

        int_min_plus = 100;
        int_max_plus = 1000;
        int_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        int_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;

        switch (prykladrandom) {
            case 0:
                int_result = int_number_1 + int_number_2;
                string_znak = "+";
                break;

            case 1:
                int_result = int_number_1 - int_number_2;
                string_znak = "-";
                break;
        }
        make_pryklad();

    }

    public void game_hard() {
        Gdx.app.log("GameWorld1", "game hard");

        setString_best_score_this_level(String.valueOf(getHighScore_hard_gw1()));

        int prykladrandom = new Random().nextInt(2);

        int_min_plus = 1000;
        int_max_plus = 10000;
        int_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        int_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        switch (prykladrandom) {
            case 0:
                int_result = int_number_1 + int_number_2;
                string_znak = "+";
                break;

            case 1:
                int_result = int_number_1 - int_number_2;
                string_znak = "-";
                break;
        }
        make_pryklad();
    }


    public void make_pryklad() { // метод в якому створюється приклад
        gameScreen1.FLAG_SHOW_QUESTION_MARK = true;

        Gdx.app.log("GameWorld1", "pryklad render");

        setString_score(String.valueOf(int_score));

        int int_pryklad_random = new Random().nextInt(3) + 1;   // рандомний вибір де буде пропуск
        int int_true_value = new Random().nextInt(6) + 1;   // рандомний вибір, якій переміній з 6 буде присвоєно правильну відповідь

        switch (int_pryklad_random) {
            case 1:  // пропуск в першого числа
                questionMarkPosition = 1;
                setString_to_screen(string_znak + " " + int_number_2 + " = " + int_result);
                string_propusk_in_pryklad = "number_1";

                true_variant = int_number_1;
                break;

            case 2:
                questionMarkPosition = 2;
                firstPart = int_number_1 + " " + string_znak;
                secondPart = "= " + int_number_2;
                string_propusk_in_pryklad = "number_2";

                true_variant = int_number_2;
                break;

            case 3:
                questionMarkPosition = 3;
                setString_to_screen(int_number_1 + " " + string_znak + " " + int_number_2 + " =");
                string_propusk_in_pryklad = "result";

                true_variant = int_result;
                break;
        }

        setRandomValues();
        setTrueValue(int_true_value);
        gameScreen1.updateButtonText(this);

        Gdx.app.log("GameWorld1", "updateButtonText");

    }


    void setRandomValues() {
        // присвоєння рандомних значень варіантів відповідей
        setInt_btn_1(calculateValue());
        setInt_btn_2(calculateValue());
        setInt_btn_3(calculateValue());
        setInt_btn_4(calculateValue());
        setInt_btn_5(calculateValue());
        setInt_btn_6(calculateValue());
    }

    private int calculateValue() {
        if (new Random().nextInt(2) == 0) {
            return true_variant + new Random().nextInt(25);
        } else {
            return true_variant - new Random().nextInt(25);
        }
    }

    // призначення правильної відповіді, рандомно вибраній змінні
    void setTrueValue(int trueValue) {
        switch (trueValue) {  // призначення правильної відповіді, рандомно вибраній змінні
            case 1:
                setInt_btn_1(true_variant);
                break;
            case 2:
                setInt_btn_2(true_variant);
                break;
            case 3:
                setInt_btn_3(true_variant);
                break;
            case 4:
                setInt_btn_4(true_variant);
                break;
            case 5:
                setInt_btn_5(true_variant);
                break;
            case 6:
                setInt_btn_6(true_variant);
                break;
        }
    }


    public void answer() {  // метод який виконується коли вибираєш якусь відповідь
        Gdx.app.log("GameWorld1", "answer");

        if (string_propusk_in_pryklad.equals("number_1")) {  //  зчитка де немає числа
            if (string_input.equals(String.valueOf(int_number_1))) {
                answer_right();
            } else {
                answer_wrong();
            }

        } else if (string_propusk_in_pryklad.equals("number_2")) {
            if (string_input.equals(String.valueOf(int_number_2))) {
                answer_right();
            } else {
                answer_wrong();
            }

        } else if (string_propusk_in_pryklad.equals("result")) {
            if (string_input.equals(String.valueOf(int_result))) {
                answer_right();
            } else {
                answer_wrong();
            }
        }
    }

    private void answer_right() { //викликаєтьсчя після того, як ти вибрав правильну відповідь
        bool_timer_wait_start = true;
        bool_answer_right = true;
        int_score++;
        float_timer = 15;
    }

    public void answer_wrong() {
        bool_answer_right = false;
    }


    public void setInt_btn_1(int int_btn_1) {
        this.int_btn_1 = int_btn_1;
    }

    public int getInt_btn_1() {
        return int_btn_1;
    }


    public void setInt_btn_2(int int_btn_2) {
        this.int_btn_2 = int_btn_2;
    }

    public int getInt_btn_2() {
        return int_btn_2;
    }


    public void setInt_btn_3(int int_btn_3) {
        this.int_btn_3 = int_btn_3;
    }

    public int getInt_btn_3() {
        return int_btn_3;
    }


    public void setInt_btn_4(int int_btn_4) {
        this.int_btn_4 = int_btn_4;
    }

    public int getInt_btn_4() {
        return int_btn_4;
    }


    public void setInt_btn_5(int int_btn_5) {
        this.int_btn_5 = int_btn_5;
    }

    public int getInt_btn_5() {
        return int_btn_5;
    }


    public void setInt_btn_6(int int_btn_6) {
        this.int_btn_6 = int_btn_6;
    }

    public int getInt_btn_6() {
        return int_btn_6;
    }


    public void setGame(String string_game) {  //  запам'ятовування вибраного рівня
        preferences_game_gw1.putString("save_game", string_game);
        preferences_game_gw1.flush();

    }

    public String getGame() {
        return preferences_game_gw1.getString("save_game");
    }


    public void setHighScore_easy_gw1(int int_score_easy_to_save) {
        if (int_score_easy_to_save > getHighScore_easy_gw1()) {
            preferences_easy_gw1.putInteger("save_int_score", int_score_easy_to_save);
            preferences_easy_gw1.flush();
        }
    }

    public int getHighScore_easy_gw1() {
        return preferences_easy_gw1.getInteger("save_int_score", 0);

    }

    public void setHighScore_normal_gw1(int int_score_normal_to_save) {
        if (int_score_normal_to_save > getHighScore_normal_gw1()) {
            preferences_normal_gw1.putInteger("save_int_score", int_score_normal_to_save);
            preferences_normal_gw1.flush();
        }
    }

    public int getHighScore_normal_gw1() {
        return preferences_normal_gw1.getInteger("save_int_score", 0);

    }

    public void setHighScore_hard_gw1(int int_score_hard_to_save) {
        if (int_score_hard_to_save > getHighScore_hard_gw1()) {
            preferences_hard_gw1.putInteger("save_int_score", int_score_hard_to_save);
            preferences_hard_gw1.flush();
        }
    }

    public int getHighScore_hard_gw1() {
        return preferences_hard_gw1.getInteger("save_int_score", 0);
    }

    public void setString_to_screen(String string_to_screen) {
        this.string_to_screen = string_to_screen;
    }

    public String getString_pryklad() {
        return string_to_screen;
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

    public void setString_best_score_this_level(String string_best_result_this_level) {
        this.string_best_score_this_level = string_best_result_this_level;
    }

    public String getString_best_score_this_level() {
        return string_best_score_this_level;
    }

    public void timer_game(float dt) {
        float_timer -= dt;

        int_timer = (int) float_timer;

        if (int_timer < 0) {
            // що відбудеться коли закінчиться час
        } else {
            string_timer_game = int_timer + "";
        }
    }

    public String getTimer_game() {
        return string_timer_game;
    }

    public void timer_wait(float dt) {
        float_timer_wait -= dt;

        Gdx.app.log(" ", float_timer_wait + "");

        if (float_timer_wait < 0) {
            float_timer_wait = 0.5f;
            bool_timer_wait_start = false;
            startGame();
        }
    }

    public int getQuestionMarkPosition() {
        return questionMarkPosition;
    }
    //597
}
