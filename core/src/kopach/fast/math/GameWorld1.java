package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Random;

/**
 * Created by vova on 18.05.17.
 */

public class GameWorld1 {

    int int_min_plus, int_max_plus, int_result, int_number_1, int_number_2;

    public int int_neprav_vidp = 3, int_score = 0, int_tr_propusk_x;
    public String string_to_screen = "0";
    public String string_input = "";

    public String  string_score, string_timer, string_neprav_vidp;

    public Preferences preferences_game, preferences_easy, preferences_normal, preferences_hard;

    public float float_timer = 15;
    int int_timer = 2;  //любе число, головне >0

    public boolean boolean_X = false, boolean_text_button = false;

    String string_znak = "", string_propusk = "     ", string_propusk_in_pryklad = "";
    public int int_btn_1 = 10, int_btn_2, int_btn_3, int_btn_4, int_btn_5, int_btn_6;


    public GameWorld1(){

        preferences_game = Gdx.app.getPreferences("My_preferences_game");

        preferences_easy = Gdx.app.getPreferences("My_preferences_easy");
        preferences_normal = Gdx.app.getPreferences("My_preferences_normal");
        preferences_hard = Gdx.app.getPreferences("My_preferences_hard");

        startGame();


        //  setString_neprav_vidp(String.valueOf(int_neprav_vidp));
    }

    public void startGame(){

        Gdx.app.log("GameWorld1","запуск гри");

        if(getGame().equals("easy")){
            game_easy();

        }if(getGame().equals("normal")){
            game_normal();

        }if(getGame().equals("hard")){
            game_hard();

        }
    }



    public  void game_easy() {

        Gdx.app.log("GameWorld1","game level");

        int prykladrandom = (int) (Math.random() * 2);

        switch (prykladrandom) {
            case 0:
                string_input = "";
                int_min_plus = 10;
                int_max_plus = 100;
                int_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = int_number_1 + int_number_2;
                string_znak = "+";

                break;

            case 1:
                string_input = "";
                int_min_plus = 10;
                int_max_plus = 100;
                int_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = int_number_1 - int_number_2;
                string_znak = "-";

                break;
        }

        boolean_text_button = false;

        pryklad_render();

    }

    public  void game_normal() {
        Gdx.app.log("GameWorld1","game normal");

        int prykladrandom = (int) (Math.random() * 2);

        switch (prykladrandom) {
            case 0:
                string_input = "";
                int_min_plus = 100;
                int_max_plus = 1000;
                int_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = int_number_1 + int_number_2;
                string_znak = "+";

                break;

            case 1:
                string_input = "";
                int_min_plus = 100;
                int_max_plus = 1000;
                int_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = int_number_1 - int_number_2;
                string_znak = "-";

                break;
        }

        boolean_text_button = false;
        pryklad_render();

    }

    public  void game_hard() {

        Gdx.app.log("GameWorld1","game hard");

        int prykladrandom = (int) (Math.random() * 2);

        switch (prykladrandom) {
            case 0:
                string_input = "";
                int_min_plus = 1000;
                int_max_plus = 10000;
                int_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = int_number_1 + int_number_2;
                string_znak = "+";

                break;

            case 1:
                string_input = "";
                int_min_plus = 1000;
                int_max_plus = 10000;
                int_number_1 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_number_2 = new Random().nextInt(int_max_plus - int_min_plus + 1) + int_min_plus;
                int_result = int_number_1 - int_number_2;
                string_znak = "-";

                break;
        }

        boolean_text_button = false;
        pryklad_render();
    }

    public void pryklad_render() {

        Gdx.app.log("GameWorld1","pryklad render");

        setString_neprav_vidp(String.valueOf(int_neprav_vidp));
        setString_score(String.valueOf(int_score));

        int int_pryklad_random = (int) (Math.random() * 2);
        int int_answer_random = (int) (Math.random() * 5);

        if(getGame().equals("easy")){
            int int_min = 10;
            int int_max = 100;

            switch (int_pryklad_random) {
                case 1:
                    string_to_screen = string_propusk + " " + string_znak + " " + int_number_2 + " = " + int_result;
                    string_propusk_in_pryklad = "number_1";

                    setInt_btn_1(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_2(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_3(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_4(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_5(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_6(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);

                    switch (int_answer_random){
                        case 1: setInt_btn_1(int_number_1); break;
                        case 2: setInt_btn_2(int_number_1); break;
                        case 3: setInt_btn_3(int_number_1); break;
                        case 4: setInt_btn_4(int_number_1); break;
                        case 5: setInt_btn_5(int_number_1); break;
                        case 6: setInt_btn_6(int_number_1); break;

                    }

                    break;

                case 2:
                    string_to_screen = int_number_1 + " " + string_znak + " " + string_propusk + " = " + int_result;
                    string_propusk_in_pryklad = "number_2";

                    setInt_btn_1(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_2(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_3(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_4(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_5(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_6(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);

                    switch (int_answer_random){
                        case 1: setInt_btn_1(int_number_2); break;
                        case 2: setInt_btn_2(int_number_2); break;
                        case 3: setInt_btn_3(int_number_2); break;
                        case 4: setInt_btn_4(int_number_2); break;
                        case 5: setInt_btn_5(int_number_2); break;
                        case 6: setInt_btn_6(int_number_2); break;
                    }

                    break;

                case 3:
                    string_to_screen = int_number_1 + " " + string_znak + " " + int_number_2 + " = " + string_propusk;
                    string_propusk_in_pryklad = "result";

                    setInt_btn_1(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_2(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_3(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_4(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_5(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_6(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);

                    switch (int_answer_random){
                        case 1: setInt_btn_1(int_result); break;
                        case 2: setInt_btn_2(int_result); break;
                        case 3: setInt_btn_3(int_result); break;
                        case 4: setInt_btn_4(int_result); break;
                        case 5: setInt_btn_5(int_result); break;
                        case 6: setInt_btn_6(int_result); break;
                    }

                    break;
            }

            Gdx.app.log("GameWorld1","pryklad render/switch easy");


        }else if(getGame().equals("normal")){

            int int_min = 100;
            int int_max = 1000;

            switch (int_pryklad_random) {
                case 1:
                    string_to_screen = string_propusk + " " + string_znak + " " + int_number_2 + " = " + int_result;
                    string_propusk_in_pryklad = "number_1";

                    setInt_btn_1(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_2(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_3(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_4(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_5(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_6(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);

                    switch (int_answer_random){
                        case 1: int_btn_1 = int_number_1; break;
                        case 2: int_btn_2 = int_number_1; break;
                        case 3: int_btn_3 = int_number_1; break;
                        case 4: int_btn_4 = int_number_1; break;
                        case 5: int_btn_5 = int_number_1; break;
                        case 6: int_btn_6 = int_number_1; break;
                    }

                    break;

                case 2:
                    string_to_screen = int_number_1 + " " + string_znak + " " + string_propusk + " = " + int_result;
                    string_propusk_in_pryklad = "number_2";

                    setInt_btn_1(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_2(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_3(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_4(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_5(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_6(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);

                    switch (int_answer_random){
                        case 1: int_btn_1 = int_number_2; break;
                        case 2: int_btn_2 = int_number_2; break;
                        case 3: int_btn_3 = int_number_2; break;
                        case 4: int_btn_4 = int_number_2; break;
                        case 5: int_btn_5 = int_number_2; break;
                        case 6: int_btn_6 = int_number_2; break;
                    }

                    break;

                case 3:
                    string_to_screen = int_number_1 + " " + string_znak + " " + int_number_2 + " = " + string_propusk;
                    string_propusk_in_pryklad = "result";

                    setInt_btn_1(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_2(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_3(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_4(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_5(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_6(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);

                    switch (int_answer_random){
                        case 1: int_btn_1 = int_result; break;
                        case 2: int_btn_2 = int_result; break;
                        case 3: int_btn_3 = int_result; break;
                        case 4: int_btn_4 = int_result; break;
                        case 5: int_btn_5 = int_result; break;
                        case 6: int_btn_6 = int_result; break;
                    }

                    break;
            }

            Gdx.app.log("GameWorld1","switch normal");


        }else if(getGame().equals("hard")){

            int int_min = 1000;
            int int_max = 10000;

            switch (int_pryklad_random) {
                case 1:
                    string_to_screen = string_propusk + " " + string_znak + " " + int_number_2 + " = " + int_result;
                    string_propusk_in_pryklad = "number_1";

                    setInt_btn_1(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_2(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_3(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_4(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_5(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_6(int_number_1 + new Random().nextInt(int_max - int_min + 1) + int_min);

                    switch (int_answer_random){
                        case 1: int_btn_1 = int_number_1; break;
                        case 2: int_btn_2 = int_number_1; break;
                        case 3: int_btn_3 = int_number_1; break;
                        case 4: int_btn_4 = int_number_1; break;
                        case 5: int_btn_5 = int_number_1; break;
                        case 6: int_btn_6 = int_number_1; break;
                    }

                    break;

                case 2:
                    string_to_screen = int_number_1 + " " + string_znak + " " + string_propusk + " = " + int_result;
                    string_propusk_in_pryklad = "number_2";

                    setInt_btn_1(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_2(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_3(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_4(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_5(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_6(int_number_2 + new Random().nextInt(int_max - int_min + 1) + int_min);

                    switch (int_answer_random){
                        case 1: int_btn_1 = int_number_2; break;
                        case 2: int_btn_2 = int_number_2; break;
                        case 3: int_btn_3 = int_number_2; break;
                        case 4: int_btn_4 = int_number_2; break;
                        case 5: int_btn_5 = int_number_2; break;
                        case 6: int_btn_6 = int_number_2; break;
                    }

                    break;

                case 3:
                    string_to_screen = int_number_1 + " " + string_znak + " " + int_number_2 + " = " + string_propusk;
                    string_propusk_in_pryklad = "result";

                    setInt_btn_1(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_2(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_3(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_4(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_5(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);
                    setInt_btn_6(int_result + new Random().nextInt(int_max - int_min + 1) + int_min);

                    switch (int_answer_random){
                        case 1: int_btn_1 = int_result; break;
                        case 2: int_btn_2 = int_result; break;
                        case 3: int_btn_3 = int_result; break;
                        case 4: int_btn_4 = int_result; break;
                        case 5: int_btn_5 = int_result; break;
                        case 6: int_btn_6 = int_result; break;
                    }

                    break;
            }

            Gdx.app.log("GameWorld1","switch hard");

        }

        setInt_tr_propusk_x();

    }

    public void setInt_tr_propusk_x() {

        Gdx.app.log("GameWorld1","set propusk");

        if(getGame().equals("easy")){
            if (string_propusk_in_pryklad.equals("number_1")){
                int_tr_propusk_x = 75;

            }else if (string_propusk_in_pryklad.equals("number_2")){
                int_tr_propusk_x = 300;

            }else if (string_propusk_in_pryklad.equals("result")){
                int_tr_propusk_x = 600;
            }


        }else if(getGame().equals("normal")){
            if (string_propusk_in_pryklad.equals("number_1")){
                int_tr_propusk_x = 75;

            }else if (string_propusk_in_pryklad.equals("number_2")){
                int_tr_propusk_x = 350;

            }else if (string_propusk_in_pryklad.equals("result")){
                int_tr_propusk_x = 600;
            }


        }else if(getGame().equals("hard")){
            if (string_propusk_in_pryklad.equals("number_1")){
                int_tr_propusk_x = 75; //можна витягнути через static text_pryklad_x з GameScreen_1

            }else if (string_propusk_in_pryklad.equals("number_2")){
                int_tr_propusk_x = 350;

            }else if (string_propusk_in_pryklad.equals("result")){
                int_tr_propusk_x = 600;
            }

        }

    }

    public int getInt_tr_propusk_x() {

      //  Gdx.app.log("GameWorld1","get propusk");

        return int_tr_propusk_x;
    }

    public void answer(){

        Gdx.app.log("GameWorld1","answer");

        if(string_propusk_in_pryklad.equals("number_1")){
            if (string_input.equals(String.valueOf(int_number_1))) {
                boolean_X = false;
                boolean_text_button = true;
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
                boolean_X = true;
            }



        }else if(string_propusk_in_pryklad.equals("number_2")){
            if (string_input.equals(String.valueOf(int_number_2))) {
                boolean_X = false;
                boolean_text_button = true;
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
            }else{
                boolean_X = true;
            }


        }else if(string_propusk_in_pryklad.equals("result")){
            if (string_input.equals(String.valueOf(int_result))) {
                boolean_X = false;
                boolean_text_button = true;
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
                    boolean_X = true;
            }

        }
    }


    public void setInt_btn_1(int int_btn_1) {this.int_btn_1 = int_btn_1;}

    public int getInt_btn_1() {return int_btn_1;}


    public void setInt_btn_2(int int_btn_2) {this.int_btn_2 = int_btn_2;}

    public int getInt_btn_2() {return int_btn_2;}


    public void setInt_btn_3(int int_btn_3) {this.int_btn_3 = int_btn_3;}

    public int getInt_btn_3() {return int_btn_3;}


    public void setInt_btn_4(int int_btn_4) {this.int_btn_4 = int_btn_4;}

    public int getInt_btn_4() {return int_btn_4;}


    public void setInt_btn_5(int int_btn_5) {this.int_btn_5 = int_btn_5;}

    public int getInt_btn_5() {return int_btn_5;}


    public void setInt_btn_6(int int_btn_6) {this.int_btn_6 = int_btn_6;}

    public int getInt_btn_6() {return int_btn_6;}


    public void setString_neprav_vidp(String string_neprav_vidp) {this.string_neprav_vidp = string_neprav_vidp;}

    public String getString_neprav_vidp() {return string_neprav_vidp;}






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


    public void setString_to_screen(String string_to_screen) {this.string_to_screen = string_to_screen;}

    public String getString_to_screen() {
        return string_to_screen;
    }


    public void setString_input(String string_input) {
        this.string_input = string_input;
    }

    public String getString_input() {
        return string_input;
    }


    public void setString_score(String string_score) {this.string_score = string_score;}

    public String getString_score() {
        return string_score;
    }


    public void setString_timer(float dt) {
        float_timer -= dt;

        int_timer = (int)float_timer;

        string_timer = int_timer + "";
    }

    public String getString_timer() {
        return string_timer;
    }



    public boolean getBoolean_X(){
        return boolean_X;
    }

    public boolean getBoolean_text_button() {
        return boolean_text_button;
    }

    /* public void inputNumber(){
        if(getGame().equals("easy")){
            for (string_input.length(); string_input.length() > 4; string_input = string_input.substring(0, string_input.length()-1));

        }if(getGame().equals("normal")){
            for (string_input.length(); string_input.length() > 5; string_input = string_input.substring(0, string_input.length()-1));

        }if(getGame().equals("hard")){
            for (string_input.length(); string_input.length() > 6; string_input = string_input.substring(0, string_input.length()-1));

        }
    }  */



}
