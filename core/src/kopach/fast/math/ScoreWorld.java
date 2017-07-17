package kopach.fast.math;

/**
 * Created by vova on 25.05.17.
 */

public class ScoreWorld {
    GameWorld gameWorld;
    public String string_score_text_level = " ", string_score_text_bestresult = " ", string_score_bestresult = " ";
    public String string_level = "easy";

    public ScoreWorld(){
        gameWorld = new GameWorld();

    }

    public void btn_easy(){
        string_level = "easy";
    }

    public void btn_normal(){
        string_level = "normal";
    }

    public void btn_hard(){
        string_level = "hard";
    }

    public String getString_score_text_level() {
        if(string_level.equals("easy")){
            string_score_text_level = "EASY";

        }if(string_level.equals("normal")){
            string_score_text_level = "NORMAL";

        }if(string_level.equals("hard")){
            string_score_text_level = "HARD";
        }

        return string_score_text_level;
    }

    public String getString_score_text_bestresult() {
        if(string_level.equals("easy")){
            string_score_text_bestresult = "Your best score:";

        }if(string_level.equals("normal")){
            string_score_text_bestresult = "Your best score:";

        }if(string_level.equals("hard")){
            string_score_text_bestresult = "Your best score:";
        }

        return string_score_text_bestresult;
    }

    public String getString_score_bestresult() {
        if(string_level.equals("easy")){
            string_score_bestresult = gameWorld.getHighScore_easy() + "";

        }if(string_level.equals("normal")){
            string_score_bestresult = gameWorld.getHighScore_normal() + "";

        }if(string_level.equals("hard")){
            string_score_bestresult = gameWorld.getHighScore_hard() + "";
        }

        return string_score_bestresult;
    }
}
