package kopach.fast.math;

/**
 * Created by vova on 25.05.17.
 */

public class RestartWorld {
    GameWorld gameWorld;
    public String string_score_text_level = " ", string_score_text_result = " ", string_score_result = " ", string_score_text_bestresult = " ", string_score_bestresult = " ";

    public RestartWorld(){
        gameWorld = new GameWorld();

    }

    public String getString_score_text_level() {
        if(gameWorld.getGame().equals("easy")){
            string_score_text_level = "EASY";

        }if(gameWorld.getGame().equals("normal")){
            string_score_text_level = "NORMAL";

        }if(gameWorld.getGame().equals("hard")){
            string_score_text_level = "HARD";
        }

        return string_score_text_level;
    }

    public String getString_score_text_result() {
        if(gameWorld.getGame().equals("easy")){
            string_score_text_result = "Your score:";

        }if(gameWorld.getGame().equals("normal")){
            string_score_text_result = "Your score:";

        }if(gameWorld.getGame().equals("hard")){
            string_score_text_result = "Your score:";
        }

        return string_score_text_result;
    }

    public String getString_score_result() {
        if(gameWorld.getGame().equals("easy")){
            string_score_result = gameWorld.int_score + "";

        }if(gameWorld.getGame().equals("normal")){
            string_score_result = gameWorld.int_score + "";

        }if(gameWorld.getGame().equals("hard")){
            string_score_result = gameWorld.int_score + "";
        }

        return string_score_result;
    }

    public String getString_score_text_bestresult() {
        if(gameWorld.getGame().equals("easy")){
            string_score_text_bestresult = "Your best score:";

        }if(gameWorld.getGame().equals("normal")){
            string_score_text_bestresult = "Your best score:";

        }if(gameWorld.getGame().equals("hard")){
            string_score_text_bestresult = "Your best score:";
        }

        return string_score_text_bestresult;
    }

    public String getString_score_bestresult() {
        if(gameWorld.getGame().equals("easy")){
            string_score_bestresult = gameWorld.getHighScore_easy() + "";

        }if(gameWorld.getGame().equals("normal")){
            string_score_bestresult = gameWorld.getHighScore_normal() + "";

        }if(gameWorld.getGame().equals("hard")){
            string_score_bestresult = gameWorld.getHighScore_hard() + "";
        }

        return string_score_bestresult;
    }
}
