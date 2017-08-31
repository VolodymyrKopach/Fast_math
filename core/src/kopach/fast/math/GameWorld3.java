package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.Random;

/**
 * Created by vova on 20.07.17.
 */

public class GameWorld3 {
    int int_min_plus, int_max_plus, int_left_result, int_right_result, true_variant, int_left_number_1, int_left_number_2, int_right_number_1, int_right_number_2, int_left_pryklad_1_position_x, int_right_pryklad_1_position_x;

    public int int_score = 0;
    public String string_input = "=";
    public String string_answer;

    public String string_score = "0", string_timer, string_best_score_this_level;

    public float float_timer = 15, float_timer_wait = 0.5f;
    ;
    int int_timer = 2;  //любе число, головне >0

    public boolean bool_timer_game, boolean_text_button, bool_input, bool_timer_wait_answer_right, bool_timer_wait_time_out, bool_timer_wait_answer_wrong;

    String string_left_znak = "", string_right_znak = "";

    GameScreen3 gameScreen3;

    public GameWorld3(GameScreen3 gameScreen3) { // запускаться відразу при запуску класа
        this.gameScreen3 = gameScreen3;

        startGame();
    }

    public void startGame() {
        Gdx.app.log("GameWorld3", "запуск гри");

        bool_timer_game = true;
        boolean_text_button = false;
        game();
    }


    public void game() {
        Gdx.app.log("GameWorld3", "game level");

        setString_input("");

        setDifficulti();

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

        setSizeTextPryklad();

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
            set_min_and_max(10, 100);
        }

    }

    void set_min_and_max(int min, int max) {
        int_min_plus = min;
        int_max_plus = max;
    }

    public void setSizeTextPryklad() {
        int int_left_number_1_lenght = String.valueOf(int_left_number_1).length();
        int int_left_number_2_lenght = String.valueOf(int_left_number_2).length();
        int int_right_number_1_lenght = String.valueOf(int_right_number_1).length();
        int int_right_number_2_lenght = String.valueOf(int_right_number_2).length();
        int int_left_number_lenght = 1;
        int int_right_number_lenght = 1;
        int int_sizeTextPryklad = 1;

        if (int_left_number_1_lenght == int_left_number_2_lenght) {
            int_left_number_lenght = int_left_number_1_lenght;
        } else if (int_left_number_1_lenght > int_left_number_2_lenght) {
            int_left_number_lenght = int_left_number_1_lenght;
        } else if (int_left_number_1_lenght < int_left_number_2_lenght) {
            int_left_number_lenght = int_left_number_2_lenght;
        }

        if (int_right_number_1_lenght == int_right_number_2_lenght) {
            int_right_number_lenght = int_right_number_1_lenght;
        } else if (int_right_number_1_lenght > int_right_number_2_lenght) {
            int_right_number_lenght = int_right_number_1_lenght;
        } else if (int_right_number_1_lenght < int_right_number_2_lenght) {
            int_right_number_lenght = int_right_number_2_lenght;
        }


        if (int_left_number_lenght == int_right_number_lenght) {
            int_sizeTextPryklad = int_left_number_lenght;
        } else if (int_left_number_lenght > int_right_number_lenght) {
            int_sizeTextPryklad = int_left_number_lenght;
        } else if (int_left_number_lenght < int_right_number_lenght) {
            int_sizeTextPryklad = int_right_number_lenght;
        }

        if (int_sizeTextPryklad == 1) {
            int_left_pryklad_1_position_x = 140;
            int_right_pryklad_1_position_x = 510;
            gameScreen3.pryklad_font.getData().setScale(1.4f, 1.4f);
            gameScreen3.input_znak_font.getData().setScale(1.6f, 1.6f);
            // gameScreen3.idp_wrong.getData().setScale(1.5f, 1.5f);
        }

        if (int_sizeTextPryklad == 2) {
            int_left_pryklad_1_position_x = 140;
            int_right_pryklad_1_position_x = 510;
            gameScreen3.pryklad_font.getData().setScale(1.4f, 1.4f);
            gameScreen3.input_znak_font.getData().setScale(1.6f, 1.6f);
            //  gameScreen3.vidp_wrong.getData().setScale(1.5f, 1.5f);
        }

        if (int_sizeTextPryklad == 3) {
            int_left_pryklad_1_position_x = 90;
            int_right_pryklad_1_position_x = 510;
            gameScreen3.pryklad_font.getData().setScale(1.4f, 1.4f);
            gameScreen3.input_znak_font.getData().setScale(1.6f, 1.6f);
            //  gameScreen3.vidp_wrong.getData().setScale(1.3f, 1.3f);

        }

        if (int_sizeTextPryklad == 4) {
            int_left_pryklad_1_position_x = 45;
            int_right_pryklad_1_position_x = 510;
            gameScreen3.pryklad_font.getData().setScale(1.4f, 1.4f);
            gameScreen3.input_znak_font.getData().setScale(1.6f, 1.6f);
            //   gameScreen3.vidp_wrong.getData().setScale(1.2f, 1.2f);
        }

        Gdx.app.log("", " size text pryklad = " + int_sizeTextPryklad);

    }

    private void correctAnswer() { //викликаєтьсчя після того, як ти вибрав правильну відповідь
        boolean_text_button = true;
        int_score++;
        setHighScore_game(int_score);
        setString_score(String.valueOf(int_score));
        float_timer = 15;
        gameScreen3.input_znak_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        gameScreen3.input_znak_font.getData().setScale(2.2f, 2.2f);
        gameScreen3.text_input_znak_x = gameScreen3.screen_width / 2 - gameScreen3.getTextWidth(gameScreen3.input_znak_font, getString_input()) / 2;

        bool_timer_wait_answer_right = true;
    }

    private void incorrectAnswer() {
        gameScreen3.input_znak_font = new BitmapFont(Gdx.files.internal("bitmapfont/red bold 70.fnt"), Gdx.files.internal("bitmapfont/red bold 70.png"), false);
        gameScreen3.input_znak_font.getData().setScale(2f, 2f);
        gameScreen3.text_input_znak_x = gameScreen3.screen_width / 2 - gameScreen3.getTextWidth(gameScreen3.input_znak_font, getString_input()) / 2;

        bool_timer_game = false;

        bool_timer_wait_answer_wrong = true;
        gameScreen3.bool_draw_replay_btn = true;

    }


    public void answer(String input_answer) {  // метод який виконується коли вибираєш якусь відповідь
        string_input = input_answer;
        bool_input = true;

        if (input_answer.equals(getString_answer())) {
            correctAnswer();
        } else {
            incorrectAnswer();
        }

    }


    public void setString_answer(int int_left_result, int int_right_result) {
        if (int_left_result < int_right_result) {
            string_answer = "<";

        } else if (int_left_result > int_right_result) {
            string_answer = ">";

        } else if (int_left_result == int_right_result) {
            string_answer = "=";
        }
    }


    public String getString_answer() {
        return string_answer;
    }

    public int getInt_left_number_1() {
        return int_left_number_1;
    }

    public int getInt_left_number_2() {
        return int_left_number_2;
    }

    public int getInt_right_number_1() {
        return int_right_number_1;
    }

    public int getInt_right_number_2() {
        return int_right_number_2;
    }

    public String getString_left_znak() {
        return string_left_znak;
    }


    public String getString_right_znak() {
        return string_right_znak;
    }

    public void setHighScore_game(int int_score_to_save) {
        if (int_score_to_save > MyPreference.getBSGame3()) {
            MyPreference.setBSGame3(int_score_to_save);
        }
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


    public void game_timer(float dt) {
        float_timer -= dt;
        int_timer = (int) float_timer;

        if (float_timer < 0) {
            bool_timer_wait_time_out = true;
            bool_timer_game = false;
            // що відбудеться коли закінчиться час
        } else {
        }

    }

    public int getInt_timer() {
        return int_timer;
    }

    public void timer_wait_time_out(float dt) {
        float_timer_wait -= dt;

        if (float_timer_wait < 0) {
            float_timer_wait = 0.5f;
            gameScreen3.replay.show();
            gameScreen3.bool_draw_replay_btn = true;
        }
    }

    public void timer_wait_answer_right(float dt) {
        float_timer_wait -= dt;

        Gdx.app.log(" ", float_timer_wait + "");

        if (float_timer_wait < 0) {
            float_timer_wait = 0.5f;
            bool_timer_wait_answer_right = false;
            startGame();
        }
    }

    public void timer_wait_answer_wrong(float dt) {
        float_timer_wait -= dt;

        Gdx.app.log(" ", float_timer_wait + "");

        if (float_timer_wait < 0) {
            float_timer_wait = 0.5f;
            bool_timer_wait_answer_wrong = false;
            gameScreen3.replay.show();

        }
    }

}
