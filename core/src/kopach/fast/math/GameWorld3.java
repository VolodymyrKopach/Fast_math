package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Random;

/**
 * Created by vova on 22.07.17.
 */

public class GameWorld3 {

    int int_min_plus, int_max_plus, int_result, true_variant, int_number_1, int_number_2;

    public int int_score = 0, int_tr_propusk_x;
    public String string_to_screen = "";
    public String string_input = "";

    public String string_score, string_timer_game;

    public Preferences preferences_game, preferences_easy, preferences_normal, preferences_hard;

    public float float_timer = 15, float_timer_wait = 0.5f;
    int int_timer = 2;  //любе число, головне >0

    public boolean boolean_X = false, bool_answer_right = false, bool_timer_wait_start = false;

    String string_znak = "", string_propusk = "     ", string_propusk_in_pryklad = "";
    public int int_btn_1, int_btn_2, int_btn_3, int_btn_4, int_btn_5, int_btn_6, int_btn_7, int_btn_8, int_btn_9, int_btn_10, int_btn_11, int_btn_12, int_btn_13, int_btn_14, int_btn_15, int_btn_16, int_btn_17, int_btn_18, int_btn_19, int_btn_20, int_btn_21, int_btn_22, int_btn_23, int_btn_24;

    GameScreen3 gameScreen3;

    public GameWorld3(GameScreen3 gameScreen3) { // запускаться відразу при запуску класа
        this.gameScreen3 = gameScreen3;
        preferences_game = Gdx.app.getPreferences("My_preferences_game");

        preferences_easy = Gdx.app.getPreferences("My_preferences_score_easy");
        preferences_normal = Gdx.app.getPreferences("My_preferences_score_normal");
        preferences_hard = Gdx.app.getPreferences("My_preferences_score_hard");

        startGame();
    }

    public void startGame() {
        Gdx.app.log("GameWorld1", "запуск гри");

        string_input = "";
        // зчитує який рівен вибраний, і після запускає гру
        if (getGame().equals("easy")) {
            setHighScore_easy(int_score);
            game_easy();
        } else if (getGame().equals("normal")) {
            setHighScore_normal(int_score);
            game_normal();
        } else if (getGame().equals("hard")) {
            setHighScore_hard(int_score);
            game_hard();
        }
    }


    public void game_easy() {
        Gdx.app.log("GameWorld1", "game level");
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

        Gdx.app.log("GameWorld1", "pryklad render");

        setString_score(String.valueOf(int_score));

        int int_pryklad_random = 3; //(int) (Math.random() * 3);  // рандомний вибір де буде пропуск
        int int_true_value = new Random().nextInt(6) + 1; //(int) (Math.random() * 6);  // рандомний вибір, якій переміній з 6 буде присвоєно правильну відповідь

        if (getGame().equals("easy")) {  // перевірка чи рівень easy

            switch (int_pryklad_random) {
                case 1:  // пропуск в першого числа
                    setString_to_screen(string_propusk + " " + string_znak + " " + int_number_2 + " = " + int_result);
                    string_propusk_in_pryklad = "number_1";

                    true_variant = int_number_1;

                    break;


                case 2:
                    setString_to_screen(int_number_1 + " " + string_znak + " " + string_propusk + " = " + int_result);
                    string_propusk_in_pryklad = "number_2";

                    true_variant = int_number_2;

                    break;

                case 3:
                    setString_to_screen(int_number_1 + " " + string_znak + " " + int_number_2 + " = " + string_propusk);
                    string_propusk_in_pryklad = "result";

                    true_variant = int_result;

                    break;
            }

            Gdx.app.log("GameWorld1", "pryklad render/switch easy");


        } else if (getGame().equals("normal")) {
            switch (int_pryklad_random) {
                case 1:
                    setString_to_screen(string_propusk + " " + string_znak + " " + int_number_2 + " = " + int_result);
                    string_propusk_in_pryklad = "number_1";

                    true_variant = int_number_1;

                    break;

                case 2:
                    setString_to_screen(int_number_1 + " " + string_znak + " " + string_propusk + " = " + int_result);
                    string_propusk_in_pryklad = "number_2";

                    true_variant = int_number_2;

                    break;

                case 3:
                    setString_to_screen(int_number_1 + " " + string_znak + " " + int_number_2 + " = " + string_propusk);
                    string_propusk_in_pryklad = "result";

                    true_variant = int_result;

                    break;
            }

            Gdx.app.log("GameWorld1", "switch normal");


        } else if (getGame().equals("hard")) {
            switch (int_pryklad_random) {
                case 1:
                    setString_to_screen(string_propusk + " " + string_znak + " " + int_number_2 + " = " + int_result);
                    string_propusk_in_pryklad = "number_1";

                    true_variant = int_number_1;

                    break;

                case 2:
                    setString_to_screen(int_number_1 + " " + string_znak + " " + string_propusk + " = " + int_result);
                    string_propusk_in_pryklad = "number_2";

                    true_variant = int_number_2;

                    break;

                case 3:
                    setString_to_screen(int_number_1 + " " + string_znak + " " + int_number_2 + " = " + string_propusk);
                    string_propusk_in_pryklad = "result";

                    true_variant = int_result;

                    break;
            }

            Gdx.app.log("GameWorld1", "switch hard");

        }
        setInt_tr_propusk_x();

        setRandomValues();
        setTrueValue(int_true_value);
        gameScreen3.updateButtonText(this);

        Gdx.app.log("GameWorld1", "updateButtonText");

    }


    void setRandomValues() {
        // присвоєння рандомних значень варіантів відповідей
        setInt_btn_1(true_variant + new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus);
        setInt_btn_2(true_variant + new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus);
        setInt_btn_3(true_variant + new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus);
        setInt_btn_4(true_variant + new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus);
        setInt_btn_5(true_variant + new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus);
        setInt_btn_6(true_variant + new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus);
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


    public void setInt_tr_propusk_x() {  // налаштування, де буде з'являтись знак питання

        Gdx.app.log("GameWorld1", "set propusk");

        if (getGame().equals("easy")) {
            if (string_propusk_in_pryklad.equals("number_1")) {
                int_tr_propusk_x = 75;

            } else if (string_propusk_in_pryklad.equals("number_2")) {
                int_tr_propusk_x = 300;

            } else if (string_propusk_in_pryklad.equals("result")) {
                int_tr_propusk_x = 600;
            }


        } else if (getGame().equals("normal")) {
            if (string_propusk_in_pryklad.equals("number_1")) {
                int_tr_propusk_x = 75;

            } else if (string_propusk_in_pryklad.equals("number_2")) {
                int_tr_propusk_x = 350;

            } else if (string_propusk_in_pryklad.equals("result")) {
                int_tr_propusk_x = 600;
            }


        } else if (getGame().equals("hard")) {
            if (string_propusk_in_pryklad.equals("number_1")) {
                int_tr_propusk_x = 75; //можна витягнути через static text_pryklad_x з GameScreen_1

            } else if (string_propusk_in_pryklad.equals("number_2")) {
                int_tr_propusk_x = 350;

            } else if (string_propusk_in_pryklad.equals("result")) {
                int_tr_propusk_x = 600;
            }

        }
    }

    public int getInt_tr_propusk_x() {  //зчитка, де буде з'являтись знак питання
        return int_tr_propusk_x;
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

    public void answer_wrong(){
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

    public void setInt_btn_7(int int_btn_7) {
        this.int_btn_7 = int_btn_7;
    }
    public int getInt_btn_7() {
        return int_btn_7;
    }

    public void setInt_btn_8(int int_btn_8) {
        this.int_btn_8 = int_btn_8;
    }
    public int getInt_btn_8() {
        return int_btn_8;
    }

    public void setInt_btn_9(int int_btn_9) {
        this.int_btn_9 = int_btn_9;
    }
    public int getInt_btn_9() {
        return int_btn_9;
    }

    public void setInt_btn_10(int int_btn_10) {
        this.int_btn_10 = int_btn_10;
    }
    public int getInt_btn_10() {
        return int_btn_10;
    }

    public void setInt_btn_11(int int_btn_11) {
        this.int_btn_11 = int_btn_11;
    }
    public int getInt_btn_11() {
        return int_btn_11;
    }

    public void setInt_btn_12(int int_btn_12) {
        this.int_btn_12 = int_btn_12;
    }
    public int getInt_btn_12() {
        return int_btn_12;
    }

    public void setInt_btn_13(int int_btn_13) {
        this.int_btn_13 = int_btn_13;
    }
    public int getInt_btn_13() {
        return int_btn_13;
    }

    public void setInt_btn_14(int int_btn_14) {
        this.int_btn_14 = int_btn_14;
    }
    public int getInt_btn_14() {
        return int_btn_14;
    }

    public void setInt_btn_15(int int_btn_15) {
        this.int_btn_15 = int_btn_15;
    }
    public int getInt_btn_15() {
        return int_btn_15;
    }

    public void setInt_btn_16(int int_btn_16) {
        this.int_btn_16 = int_btn_16;
    }
    public int getInt_btn_16() {
        return int_btn_16;
    }

    public void setInt_btn_17(int int_btn_17) {
        this.int_btn_17 = int_btn_17;
    }
    public int getInt_btn_17() {
        return int_btn_17;
    }

    public void setInt_btn_18(int int_btn_18) {
        this.int_btn_18 = int_btn_18;
    }
    public int getInt_btn_18() {
        return int_btn_18;
    }

    public void setInt_btn_19(int int_btn_19) {
        this.int_btn_19 = int_btn_19;
    }
    public int getInt_btn_19() {
        return int_btn_19;
    }

    public void setInt_btn_20(int int_btn_20) {
        this.int_btn_20 = int_btn_20;
    }
    public int getInt_btn_20() {
        return int_btn_20;
    }

    public void setInt_btn_21(int int_btn_21) {
        this.int_btn_21 = int_btn_21;
    }
    public int getInt_btn_21() {
        return int_btn_21;
    }

    public void setInt_btn_22(int int_btn_22) {
        this.int_btn_22 = int_btn_22;
    }
    public int getInt_btn_22() {
        return int_btn_22;
    }

    public void setInt_btn_23(int int_btn_23) {
        this.int_btn_23 = int_btn_23;
    }
    public int getInt_btn_23() {
        return int_btn_23;
    }

    public void setInt_btn_24(int int_btn_24) {
        this.int_btn_24 = int_btn_24;
    }
    public int getInt_btn_24() {
        return int_btn_24;
    }


    public void setGame(String string_game) {  //  запам'ятовування вибраного рівня
        preferences_game.putString("save_game", string_game);
        preferences_game.flush();

    }

    public String getGame() {
        return preferences_game.getString("save_game");
    }


    public void setHighScore_easy(int int_score_easy_to_save) {
        if (int_score_easy_to_save > getHighScore_easy()) {
            preferences_easy.putInteger("save_int_score", int_score_easy_to_save);
            preferences_easy.flush();
        }
    }

    public int getHighScore_easy() {
        return preferences_easy.getInteger("save_int_score", 0);

    }

    public void setHighScore_normal(int int_score_normal_to_save) {
        if (int_score_normal_to_save > getHighScore_normal()) {
            preferences_normal.putInteger("save_int_score", int_score_normal_to_save);
            preferences_normal.flush();
        }
    }

    public int getHighScore_normal() {
        return preferences_normal.getInteger("save_int_score", 0);

    }

    public void setHighScore_hard(int int_score_hard_to_save) {
        if (int_score_hard_to_save > getHighScore_hard()) {
            preferences_hard.putInteger("save_int_score", int_score_hard_to_save);
            preferences_hard.flush();
        }
    }

    public int getHighScore_hard() {
        return preferences_hard.getInteger("save_int_score", 0);

    }


    public void setString_to_screen(String string_to_screen) {
        this.string_to_screen = string_to_screen;
    }

    public String getString_to_screen() {
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


    public void timer_game(float dt) {
        float_timer -= dt;

        int_timer = (int) float_timer;

        if(int_timer<0){
            // що відбудеться коли закінчиться час
        }else {string_timer_game = int_timer + "";}
    }

    public String getTimer_game() {
        return string_timer_game;
    }

    public void timer_wait(float dt) {
        float_timer_wait -= dt;

        Gdx.app.log(" ", float_timer_wait + "");

        if(float_timer_wait<0){
            float_timer_wait = 0.5f;
            bool_timer_wait_start = false;
            startGame();
        }
    }
}