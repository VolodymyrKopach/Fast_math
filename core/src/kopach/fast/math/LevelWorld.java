package kopach.fast.math;

/**
 * Created by vova on 25.05.17.
 */

public class LevelWorld {
    GameWorld gameWorld;

    public LevelWorld(){
        gameWorld = new GameWorld();

    }

    public void btn_easy(){
        gameWorld.setGame("easy");
    }

    public void btn_normal(){
        gameWorld.setGame("normal");
    }

    public void btn_hard(){
        gameWorld.setGame("hard");
    }

}
