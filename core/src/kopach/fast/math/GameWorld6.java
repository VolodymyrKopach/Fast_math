package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Random;

/**
 * Created by vova on 18.05.17.
 */

public class GameWorld6 {

    int int_min_plus, int_max_plus, int_result, int_number_1, int_number_2;

    public int int_score = 0;
    public int trueValue;
    public String string_to_screen = "";
    public String string_input = "";

    public String string_score = "0", string_timer_game;

    public float float_timer = 60, float_timer_wait = 0.5f;
    int int_timer = 2;  //любе число, головне >0

    public boolean bool_answer_right, bool_timer_game, bool_timer_wait_start, bool_timer_wait_answer_wrong, bool_timer_wait_answer_right;

    String string_znak = "";
    public String string_btn_1, string_btn_2, string_btn_3, string_btn_4, string_btn_5, string_btn_6, string_incorrect_pryklad;

    GameScreen6 gameScreen6;

    public GameWorld6(GameScreen6 gameScreen6) { // запускаться відразу при запуску класа
        this.gameScreen6 = gameScreen6;

        // startGame();
    }

    public void startGame() {
        game();
        int_score = 0;
    }


    public void game() {
        bool_timer_game = true;
        string_input = "";
        gameScreen6.enableTouchableAllBtn();
        gameScreen6.createTextButtons();

        setDifficulti();

        setString_btn_1(get_create_example());
        setString_btn_2(get_create_example());
        setString_btn_3(get_create_example());
        setString_btn_4(get_create_example());
        setString_btn_5(get_create_example());

        setString_incorrect_pryklad();
        setIncorrectPryklad();

        setString_score(String.valueOf(int_score));
        gameScreen6.updateButtonText(this);
    }

    void setDifficulti() {
        if (int_score > 100) {
            set_min_and_max(500, 999);
        } else if (int_score > 9) {
            set_min_and_max(450, 900);
        } else if (int_score > 8) {
            set_min_and_max(400, 800);
        } else if (int_score > 7) {
            set_min_and_max(350, 700);
        } else if (int_score > 6) {
            set_min_and_max(300, 600);
        } else if (int_score > 5) {
            set_min_and_max(250, 500);
        } else if (int_score > 4) {
            set_min_and_max(200, 400);
        } else if (int_score > 3) {
            set_min_and_max(150, 300);
        } else if (int_score > 2) {
            set_min_and_max(100, 200);
        } else if (int_score > 1) {
            set_min_and_max(50, 150);
        } else if (int_score > 0 || int_score == 0) {
            set_min_and_max(10, 50);
        }

    }

    void set_min_and_max(int min, int max) {
        int_min_plus = min;
        int_max_plus = max;
    }

    String get_create_example() {
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

        String created_pryklad = int_number_1 + string_znak + int_number_2 + "=" + int_result;

        return created_pryklad;
    }


    void setIncorrectPryklad() {

        trueValue = new Random().nextInt(5) + 1;

        switch (trueValue) {  // призначення правильної відповіді, рандомно вибраній змінні
            case 1:
                setString_btn_1(getString_incorrect_pryklad());
                Gdx.app.log("", "1");
                break;
            case 2:
                setString_btn_2(getString_incorrect_pryklad());
                Gdx.app.log("", "2");
                break;
            case 3:
                setString_btn_3(getString_incorrect_pryklad());
                Gdx.app.log("", "3");
                break;
            case 4:
                setString_btn_4(getString_incorrect_pryklad());
                Gdx.app.log("", "4");
                break;
            case 5:
                setString_btn_5(getString_incorrect_pryklad());
                Gdx.app.log("", "5");
                break;
            case 6:
                setString_btn_6(getString_incorrect_pryklad());
                break;
        }
    }


    public void answer(TextButton button) {  // метод який виконується коли вибираєш якусь відповідь
        Gdx.app.log("GameWorld2", "answer");

        if (String.valueOf(button.getText()).equals(getString_incorrect_pryklad())) {
            answer_right(button);

        } else {
            answer_wrong(button);
        }

        gameScreen6.disabledTouchableAllBtn();
    }

    private void answer_right(TextButton button) { //викликаєтьсчя після того, як ти вибрав правильну відповідь
        bool_timer_wait_start = true;
        bool_answer_right = true;
        int_score++;
        setHighScore_game(int_score);
        float_timer = 60;

        button.getStyle().checked = gameScreen6.skin.getDrawable("btn green");

        bool_timer_wait_answer_right = true;
        Gdx.app.log("", "answer right");
    }

    public void answer_wrong(TextButton button) {
        bool_answer_right = false;
        gameScreen6.bool_draw_replay_btn = true;
        bool_timer_wait_answer_wrong = true;
        bool_timer_game = false;

        button.getStyle().checked = gameScreen6.skin.getDrawable("btn red");
        gameScreen6.showRightBtn(trueValue);
    }


    public void setString_btn_1(String string_btn_1) {
        this.string_btn_1 = string_btn_1;
    }

    public String getString_btn_1() {
        return string_btn_1;
    }

    public void setString_btn_2(String string_btn_2) {
        this.string_btn_2 = string_btn_2;
    }

    public String getString_btn_2() {
        return string_btn_2;
    }

    public void setString_btn_3(String string_btn_3) {
        this.string_btn_3 = string_btn_3;
    }

    public String getString_btn_3() {
        return string_btn_3;
    }

    public void setString_btn_4(String string_btn_4) {
        this.string_btn_4 = string_btn_4;
    }

    public String getString_btn_4() {
        return string_btn_4;
    }

    public void setString_btn_5(String string_btn_5) {
        this.string_btn_5 = string_btn_5;
    }

    public String getString_btn_5() {
        return string_btn_5;
    }

    public void setString_btn_6(String string_btn_6) {
        this.string_btn_6 = string_btn_6;
    }

    public String getString_btn_6() {
        return string_btn_6;
    }

    void setString_incorrect_pryklad() {

        int prykladrandom = new Random().nextInt(1);
        int_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        int_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
        switch (prykladrandom) {
            case 1: // додавання
                int_result = int_number_1 + int_number_2;
                string_znak = "+";
                break;

            case 2:  // віднімання
                int_result = int_number_1 - int_number_2;
                string_znak = "-";
                break;
        }

        int int_number_to_incorrect = new Random().nextInt(10 - (-10) + 1) + (-10);
        int int_incorrect_result = int_result + int_number_to_incorrect;

        string_incorrect_pryklad = int_number_1 + string_znak + int_number_2 + "=" + int_incorrect_result;
    }

    public String getString_incorrect_pryklad() {
        return string_incorrect_pryklad;
    }

    public void setString_score(String string_score) {
        this.string_score = string_score;
    }

    public String getString_score() {
        return string_score;
    }

    public void setString_input(String string_input) {
        this.string_input = string_input;
    }

    public String getString_input() {
        return string_input;
    }

    public void setHighScore_game(int int_score_to_save) {
        if (int_score_to_save > MyPreference.getBSGame6()) {
            MyPreference.setBSGame6(int_score_to_save);
        }
    }

    public void timer_game(float dt) {
        float_timer -= dt;

        if (float_timer < 0) {
            // що відбудеться коли закінчиться час
        } else {
            string_timer_game = int_timer + "";
        }
    }

    public String getTimer_game() {
        return string_timer_game;
    }

    public void timer_wait_answer_right(float dt) {
        float_timer_wait -= dt;

        //  Gdx.app.log(" ", float_timer_wait + "");

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
            bool_timer_wait_answer_wrong = false;
            gameScreen6.replay.show();

        }
    }
}
