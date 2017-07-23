package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

/**
 * Created by vova on 18.05.17.
 */

public class GameWorld {
    int int_min_plus, int_max_plus, int_result, number_1, number_2;

    public int int_score = 0;
    public String string_to_screen = "0";
    public String string_input = "";

    public String string_result = "", string_score, string_best_score_this_level;

    public Preferences preferences_game, preferences_easy, preferences_normal, preferences_hard;
    public int int_save_pref_easy, int_save_pref_normal, int_save_pref_hard;

    public float float_timer = 15;
    int int_timer = 2;  //любе число, головне >0
    public String string_timer;

    public boolean boolean_X = false;

    public GameWorld(){

        preferences_game = Gdx.app.getPreferences("My_preferences_game");

        preferences_easy = Gdx.app.getPreferences("My_preferences_easy");
        preferences_normal = Gdx.app.getPreferences("My_preferences_normal");
        preferences_hard = Gdx.app.getPreferences("My_preferences_hard");


        if(getGame().equals("easy")){
            game_easy();

        }if(getGame().equals("normal")){
            game_normal();

        }if(getGame().equals("hard")){
            game_hard();

        }
    }



    public  void game_easy() {
        Gdx.app.log("log","game easy");

        setString_best_score_this_level(String.valueOf(getHighScore_easy()));

        int prykladrandom = (int) (Math.random() * 2);

        string_score = "Score: " + int_score+"";

        switch (prykladrandom) {
            case 0:
                string_input = "";
                int_min_plus = 10;
                int_max_plus = 100;
                number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = number_1 + number_2;
                string_to_screen = number_1 + "+" + number_2+"=";
                string_result = String.valueOf(int_result);


                break;

            case 1:
                string_input = "";
                int_min_plus = 10;
                int_max_plus = 100;
                number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = number_1 - number_2;
                string_to_screen = number_1 + "-" + number_2+"=";
                string_result = String.valueOf(int_result);


                break;
        }

    }

    public  void game_normal() {

        Gdx.app.log("log","game normal");

        setString_best_score_this_level(String.valueOf(getHighScore_normal()));

        int prykladrandom = (int) (Math.random() * 2);

        string_score = "Score: " + int_score+"";

        switch (prykladrandom) {
            case 0:
                string_input = "";
                int_min_plus = 100;
                int_max_plus = 1000;
                number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = number_1 + number_2;
                string_to_screen = number_1 + "+" + number_2+"=";
                string_result = String.valueOf(int_result);


                break;

            case 1:
                string_input = "";
                int_min_plus = 100;
                int_max_plus = 1000;
                number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = number_1 - number_2;
                string_to_screen = number_1 + "-" + number_2+"=";
                string_result = String.valueOf(int_result);


                break;
        }

    }

    public  void game_hard() {

        Gdx.app.log("log","game hard");

        setString_best_score_this_level(String.valueOf(getHighScore_hard()));

        int prykladrandom = (int) (Math.random() * 2);

        string_score = "Score: " + int_score+"";

        switch (prykladrandom) {
            case 0:
                string_input = "";
                int_min_plus = 1000;
                int_max_plus = 10000;
                number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = number_1 + number_2;
                string_to_screen = number_1 + "+" + number_2+"=";
                string_result = String.valueOf(int_result);


                break;

            case 1:
                string_input = "";
                int_min_plus = 1000;
                int_max_plus = 10000;
                number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = number_1 - number_2;
                string_to_screen = number_1 + "-" + number_2+"=";
                string_result = String.valueOf(int_result);


                break;
        }

    }

    public void btn_answer(){

        if (string_input.equals(string_result)) {
            boolean_X = false;
            int_score++;
            float_timer = 15;

            if(getGame().equals("easy")){
                setHighScore_easy(int_score);
                game_easy();

            }if(getGame().equals("normal")){
                setHighScore_normal(int_score);
                game_normal();

            }if(getGame().equals("hard")){
                setHighScore_hard(int_score);
                game_hard();

            }
        }else {
            if(string_result.equals("")){

            } else {
                boolean_X = true;            }
        }
    }


    public void setString_timer(float dt) {
        float_timer -= dt;

        int_timer = (int)float_timer;

        string_timer = int_timer + "";
    }

    public String getString_timer() {
        return string_timer;
    }


    public void setGame(String string_game){
        preferences_game.putString("save_game", string_game);
        preferences_game.flush();

    }

    public String getGame(){
        return preferences_game.getString("save_game");
    }


    public void setHighScore_easy(int int_score_easy_to_save){
        if (int_score_easy_to_save > getHighScore_easy()) {
            preferences_easy.putInteger("save_int_score", int_score_easy_to_save);
            preferences_easy.flush();
        }
    }

    public int getHighScore_easy(){
        return preferences_easy.getInteger("save_int_score", 0);

    }

    public void setHighScore_normal(int int_score_normal_to_save){
        if (int_score_normal_to_save > getHighScore_normal()) {
            preferences_normal.putInteger("save_int_score", int_score_normal_to_save);
            preferences_normal.flush();
        }
    }

    public int getHighScore_normal(){
        return preferences_normal.getInteger("save_int_score", 0);

    }

    public void setHighScore_hard(int int_score_hard_to_save){
        if (int_score_hard_to_save > getHighScore_hard()) {
            preferences_hard.putInteger("save_int_score", int_score_hard_to_save);
            preferences_hard.flush();
        }
    }

    public int getHighScore_hard(){
        return preferences_hard.getInteger("save_int_score", 0);

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
        string_input = string_input+number;
        inputNumber();
    }

    public void setString_best_score_this_level(String string_best_result_this_level) {this.string_best_score_this_level = string_best_result_this_level;}

    public String getString_best_score_this_level() {return string_best_score_this_level;}


    public boolean getBoolean_X(){
        return boolean_X;
    }

    public void inputNumber(){
        if(getGame().equals("easy")){
            for (string_input.length(); string_input.length() > 4; string_input = string_input.substring(0, string_input.length()-1));

        }if(getGame().equals("normal")){
            for (string_input.length(); string_input.length() > 5; string_input = string_input.substring(0, string_input.length()-1));

        }if(getGame().equals("hard")){
            for (string_input.length(); string_input.length() > 6; string_input = string_input.substring(0, string_input.length()-1));

        }
    }

    public void prees_C(){
       if(string_input.equals("")){

       }else {string_input = string_input.substring(0, string_input.length()-1);}
    }
}
