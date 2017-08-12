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

    public String string_timer_game;

    public Preferences preferences_game, preferences_easy, preferences_normal, preferences_hard;

    public float float_timer = 60, float_timer_wait = 0.5f;
    int int_timer = 2;  //любе число, головне >0

    public boolean  bool_timer_wait_start = false; // колит нажав на відповідь, і відповідь правильна, то чекає пів секунди і потім виводить новий приклад
    GameScreen3 gameScreen3;
    Listener listener;

    public GameWorld3(GameScreen3 gameScreen3,Listener listener) { // запускаться відразу при запуску класа
        this.gameScreen3 = gameScreen3;
        values = new ArrayList<Integer>();
        preferences_game = Gdx.app.getPreferences("My_preferences_game");

        preferences_easy = Gdx.app.getPreferences("My_preferences_score_easy");
        preferences_normal = Gdx.app.getPreferences("My_preferences_score_normal");
        preferences_hard = Gdx.app.getPreferences("My_preferences_score_hard");

        setGame("easy");
        this.listener = listener;
    }

    public void generateValue(int num_of_btn) {
        // заповнюємо масив числами
        values.clear();
        for (int i = 0; i < num_of_btn; i++) {
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
                    checkIsUnique();
                }
            }
        }
    }

    //Витягуємо звідси число для кнопки
    public String getValue(int position) {
        return String.valueOf(values.get(position));
    }

    public void setGame(String string_game) {  //  запам'ятовування вибраного рівня
        preferences_game.putString("save_game", string_game);
        preferences_game.flush();

    }

    public String getGame() {
        return preferences_game.getString("save_game");
    }


    public void timer_game(float dt) {
        float_timer -= dt;

        int_timer = (int) float_timer;

        if (int_timer == 0) {
            // що відбудеться коли закінчиться час
            listener.time_tick();
            int_timer = 60;
            float_timer = 60;
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
        }
    }
}
