package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vova on 22.07.17.
 */

public class GameWorld3 {
    ArrayList<Integer> values;
    int int_min_plus, int_max_plus, int_result, true_variant, int_number_1, int_number_2;

    public int int_score = 0;
    public String string_to_screen = "";
    public String string_input = "";

    public String string_score, string_timer_game, string_best_score_this_level;

    public Preferences preferences_game, preferences_easy, preferences_normal, preferences_hard;

    public float float_timer = 15, float_timer_wait = 0.5f;
    int int_timer = 2;  //любе число, головне >0

    public boolean bool_answer_right = false, bool_timer_wait_start = false; // колит нажав на відповідь, і відповідь правильна, то чекає пів секунди і потім виводить новий приклад
    GameScreen3 gameScreen3;

    public GameWorld3(GameScreen3 gameScreen3) { // запускаться відразу при запуску класа
        this.gameScreen3 = gameScreen3;
        values = new ArrayList<Integer>();
        preferences_game = Gdx.app.getPreferences("My_preferences_game");

        preferences_easy = Gdx.app.getPreferences("My_preferences_score_easy");
        preferences_normal = Gdx.app.getPreferences("My_preferences_score_normal");
        preferences_hard = Gdx.app.getPreferences("My_preferences_score_hard");

        startGame();
    }

    public void generateValue(int num_of_btn) {
        // заповнюємо масив числами
        for (int i = 0; i < 24; i++) {
            values.add(new Random().nextInt(100));
        }
        checkIsUnique();
    }

    private void checkIsUnique() {
        for (int i = 0; i < values.size(); i++) {
            for (int j = 1 + i; j < values.size(); j++) {
                if (values.get(i) == values.get(j)) {
                    Gdx.app.log("GameScreen1", "Знайдено однакові значення " + i);
                    values.set(i, new Random().nextInt(100));
                }
            }
        }
    }

    //Витягуємо звідси число для кнопки
    public String getValue(int position) {
        return String.valueOf(values.get(position));
    }

    public void startGame() {
        Gdx.app.log("GameWorld3", "запуск гри");

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
        setString_best_score_this_level(String.valueOf(getHighScore_easy()));
        Gdx.app.log("GameWorld1", "game level");
    }

    public void game_normal() {
        setString_best_score_this_level(String.valueOf(getHighScore_normal()));
        Gdx.app.log("GameWorld1", "game normal");
    }

    public void game_hard() {
        setString_best_score_this_level(String.valueOf(getHighScore_hard()));
        Gdx.app.log("GameWorld1", "game hard");
    }

    private void answer_right() { //викликаєтьсчя після того, як ти вибрав правильну відповідь
        bool_timer_wait_start = true;
        bool_answer_right = true;
        int_score++;
        float_timer = 15;
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

    public void setString_score(String string_score) {
        this.string_score = string_score;
    }

    public String getString_score() {
        return "20";
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
}
