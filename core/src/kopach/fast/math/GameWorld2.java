package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Random;

/**
 * Created by vova on 18.05.17.
 */

public class GameWorld2 {

    int int_min_plus, int_max_plus, int_result, true_variant, int_number_1, int_number_2;

    public int int_score = 0;
    public String string_to_screen = "";
    public String string_input = "";

    public String string_score;

    public float float_timer = 15, float_timer_wait = 0.5f;
    int int_timer = 2;  //любе число, головне >0
    public int int_pryklad_position_1_x;

    public boolean bool_answer_right, bool_timer_game, bool_replay, bool_timer_wait_answer_wrong, bool_timer_wait_answer_right, bool_timer_wait_time_out;

    String string_znak = "", string_propusk_in_pryklad = "";
    public int int_btn_1, int_btn_2, int_btn_3, int_btn_4, int_btn_5, int_btn_6;

    GameScreen2 gameScreen2;
    String firstPart ;

    public String getSecondPart() {
        return secondPart;
    }

    public String getFirstPart() {

        return firstPart;
    }

    String secondPart;
    private int questionMarkPosition;

    public GameWorld2(GameScreen2 gameScreen2) { // запускаться відразу при запуску класа
        this.gameScreen2 = gameScreen2;

        startGame();
    }

    public void startGame() {
        int_score = 0;
        game();
    }


    public void game() {

        setDifficulti();

        bool_timer_game = true;
        string_input = "";

        int prykladrandom = new Random().nextInt(2);
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

    void setDifficulti(){
        if (int_score > 100){set_min_and_max(500, 999);
        }else if (int_score > 9){set_min_and_max(450, 900);
        }else if (int_score > 8){set_min_and_max(400, 800);
        }else if (int_score > 7){set_min_and_max(350, 700);
        }else if (int_score > 6){set_min_and_max(300, 600);
        }else if (int_score > 5){set_min_and_max(250, 500);
        }else if (int_score > 4){set_min_and_max(200, 400);
        }else if (int_score > 3){set_min_and_max(150, 300);
        }else if (int_score > 2){set_min_and_max(100, 200);
        }else if (int_score > 1){set_min_and_max(50, 150);
        }else if (int_score > 0 || int_score == 0){set_min_and_max(10, 100);}

    }

    void set_min_and_max(int min, int max){
        int_min_plus = min;
        int_max_plus = max;
    }

    public void make_pryklad() { // метод в якому створюється приклад

        setInt_pryklad_position_1_x();

        gameScreen2.FLAG_SHOW_QUESTION_MARK = true;

        Gdx.app.log("GameWorld2", "pryklad render");

        setString_score(String.valueOf(int_score));

        int int_pryklad_random = 2;//new Random().nextInt(3) + 1;
        int int_true_value = new Random().nextInt(6) + 1;   // рандомний вибір, якій переміній з 6 буде присвоєно правильну відповідь

        switch (int_pryklad_random) {
            case 1:  // пропуск в першого числа
                questionMarkPosition = 1;
                setString_to_screen(" " + string_znak + " " + int_number_2 + " = " + int_result);
                string_propusk_in_pryklad = "number_1";

                true_variant = int_number_1;
                break;

            case 2:
                questionMarkPosition = 2;
                firstPart = int_number_1 + " " + string_znak + " ";
                secondPart = " = " + int_result;
                string_propusk_in_pryklad = "number_2";

                true_variant = int_number_2;
                break;

            case 3:
                questionMarkPosition = 3;
                setString_to_screen(int_number_1 + " " + string_znak + " " + int_number_2 + " = ");
                string_propusk_in_pryklad = "result";

                true_variant = int_result;
                break;
        }

        setRandomValues();
        setTrueValue(int_true_value);
        gameScreen2.updateButtonText(this);

        Gdx.app.log("GameWorld2", "updateButtonText");

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
        Gdx.app.log("GameWorld2", "answer");

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
        //  bool_timer_wait_start = true;
        bool_answer_right = true;
        int_score++;
        setHighScore_game(int_score);
        float_timer = 15;

        bool_timer_wait_answer_right = true;
    }

    public void answer_wrong() {
        bool_answer_right = false;
        gameScreen2.bool_draw_replay_btn = true;
        bool_timer_wait_answer_wrong = true;
        bool_timer_game = false;
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

    public void setHighScore_game(int int_score_to_save) {
        if (int_score_to_save > MyPreference.getBSGame2()) {
            MyPreference.setBSGame2(int_score_to_save);
        }
    }

    public void setInt_pryklad_position_1_x() {
        int int_example_length = String.valueOf(int_number_1).length() + String.valueOf(int_number_2).length() + String.valueOf(int_result).length();

        if (int_example_length == 6){int_pryklad_position_1_x = 75;   gameScreen2.text_pryklad_font.getData().setScale(1.4f, 1.4f); gameScreen2.text_vidp_right.getData().setScale(1.6f, 1.6f); gameScreen2.text_vidp_wrong.getData().setScale(1.5f, 1.5f);}
        if (int_example_length == 7){int_pryklad_position_1_x = 65;   gameScreen2.text_pryklad_font.getData().setScale(1.4f, 1.4f); gameScreen2.text_vidp_right.getData().setScale(1.6f, 1.6f); gameScreen2.text_vidp_wrong.getData().setScale(1.5f, 1.5f);}
        if (int_example_length == 8){int_pryklad_position_1_x = 55;   gameScreen2.text_pryklad_font.getData().setScale(1.2f, 1.2f); gameScreen2.text_vidp_right.getData().setScale(1.4f, 1.4f); gameScreen2.text_vidp_wrong.getData().setScale(1.3f, 1.3f);}
        if (int_example_length == 9){int_pryklad_position_1_x = 45;   gameScreen2.text_pryklad_font.getData().setScale(1.1f, 1.1f); gameScreen2.text_vidp_right.getData().setScale(1.3f, 1.3f); gameScreen2.text_vidp_wrong.getData().setScale(1.2f, 1.2f);}
        if (int_example_length == 10){int_pryklad_position_1_x = 35;   gameScreen2.text_pryklad_font.getData().setScale(1.1f, 1.1f); gameScreen2.text_vidp_right.getData().setScale(1.3f, 1.3f); gameScreen2.text_vidp_wrong.getData().setScale(1.2f, 1.2f);}
        if (int_example_length == 11){int_pryklad_position_1_x = 25;   gameScreen2.text_pryklad_font.getData().setScale(1.1f, 1.1f); gameScreen2.text_vidp_right.getData().setScale(1.3f, 1.3f); gameScreen2.text_vidp_wrong.getData().setScale(1.2f, 1.2f);}
        if (int_example_length == 12){int_pryklad_position_1_x = 15;   gameScreen2.text_pryklad_font.getData().setScale(1.1f, 1.1f); gameScreen2.text_vidp_right.getData().setScale(1.3f, 1.3f); gameScreen2.text_vidp_wrong.getData().setScale(1.2f, 1.2f);}

    }

    public int getInt_pryklad_position_1_x() {return int_pryklad_position_1_x;}

    public void timer_game(float dt) {
        float_timer -= dt;
        int_timer = (int) float_timer;

        if (float_timer < 0) {
            bool_timer_wait_time_out = true;
            bool_timer_game = false;
            // що відбудеться коли закінчиться час
        } else {
        }
    }

    public int getTimer_game() {
        return int_timer;
    }

    public void timer_wait_time_out(float dt) {
        float_timer_wait -= dt;

        if (float_timer_wait < 0) {
            float_timer_wait = 0.5f;
            gameScreen2.bool_draw_replay_btn = true;
            bool_replay = true;

        }
    }

    public void timer_wait_answer_right(float dt) {
        float_timer_wait -= dt;

        if (float_timer_wait < 0) {
            float_timer_wait = 0.5f;
            bool_timer_wait_answer_right = false;
            game();
        }
    }

    public void timer_wait_answer_wrong(float dt) {
        float_timer_wait -= dt;

        if (float_timer_wait < 0) {
            float_timer_wait = 0.5f;
            bool_timer_wait_time_out = false;
            bool_replay = true;
            startGame();

        }
    }

    public int getQuestionMarkPosition() {
        return questionMarkPosition;
    }


}