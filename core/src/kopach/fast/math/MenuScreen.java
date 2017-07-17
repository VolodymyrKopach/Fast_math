package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by vova on 25.05.17.
 */

public class MenuScreen implements Screen{
    MyGameClass myGameClass;
    MenuWorld menuWorld;

    public TextureAtlas textureAtlas_ms;
    public TextureRegion tr_level_fon;
    public TextButton btn_game, btn_best_score, btn_exit;
    public TextButton.TextButtonStyle btn_game_style, btn_best_score_style, btn_exit_style;
    Skin skin;
    BitmapFont text_to_button;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_ms;

    public float width_vidtstan, height_vidstan;
    public float width_btn_game, height_btn_game, width_btn_best_score, height_btn_best_score, width_btn_exit, height_btn_exit, width_tr_level_text, height_tr_level_text;
    public float btn_game_x,btn_game_y, btn_best_score_x,btn_best_score_y, btn_exit_x,btn_exit_y; //tr_level_text_x, tr_level_text_y;

    public MenuScreen(MyGameClass myGameClass){
        this.myGameClass = myGameClass;
        menuWorld = new MenuWorld();

        variables();

        Gdx.input.setCatchBackKey(true);

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(720, 1280, orthographicCamera);

        textureAtlas_ms = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_level_fon = new TextureRegion(textureAtlas_ms.findRegion("level fon"));
      //  tr_level_text = new TextureRegion(textureAtlas_ss.findRegion("level text"));

        stage_ms = new Stage(viewport);
        stage_ms.clear();
        Gdx.input.setInputProcessor(stage_ms);

        skin = new Skin();
        skin.addRegions(textureAtlas_ms);
        text_to_button = new BitmapFont();

        textButton();
      //  myGameClass.bannerAdShow();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(9, 9, 9, 5);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            Gdx.app.exit();
        }

        orthographicCamera.update();
        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_level_fon, 0, 0, 720, 1280);
      //  myGameClass.spriteBatch.draw(tr_level_text, tr_level_text_x, tr_level_text_y, width_tr_level_text, height_tr_level_text);
        myGameClass.spriteBatch.end();

        stage_ms.act(delta);
        stage_ms.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void variables(){
        width_vidtstan = 60;  height_vidstan = 70;

        width_btn_game = 540;  height_btn_game = 180;
        width_btn_best_score = 70; height_btn_best_score = 70;
        width_btn_exit = 70; height_btn_exit = 70;
        width_tr_level_text = 480; height_tr_level_text = 60;
        btn_game_x = 360-(width_btn_game/2); btn_game_y = 220;
        btn_best_score_x = 615; btn_best_score_y = 1180;
        btn_exit_x = 35; btn_exit_y = 1180;
        // tr_level_text_x = Gdx.graphics.getWidth()/2-(width_tr_level_text/2); tr_level_text_y = 650;
    }


    public void textButton() {
        Gdx.app.log("log", "text button level");

        btn_game_style = new TextButton.TextButtonStyle();
        btn_game_style.up = skin.getDrawable("btn start");
        btn_game_style.down = skin.getDrawable("btn start press");
        btn_game_style.font = text_to_button;
        btn_game = new TextButton(" ", btn_game_style);
        btn_game.setSize(width_btn_game, height_btn_game);
        btn_game.setPosition(btn_game_x, btn_game_y);
        btn_game.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                menuWorld.btn_game();
                myGameClass.setScreen(new LevelScreen(myGameClass));

                // dispose();

            }
        });
        stage_ms.addActor(btn_game);


        btn_best_score_style = new TextButton.TextButtonStyle();
        btn_best_score_style.up = skin.getDrawable("btn best score");
        btn_best_score_style.down = skin.getDrawable("btn best score press");
        btn_best_score_style.font = text_to_button;
        btn_best_score = new TextButton(" ", btn_best_score_style);
        btn_best_score.setSize(width_btn_best_score, height_btn_best_score);
        btn_best_score.setPosition(btn_best_score_x, btn_best_score_y);
        btn_best_score.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                menuWorld.btn_best_score();
                myGameClass.setScreen(new ScoreScreen(myGameClass));
                // dispose();

            }
        });
        stage_ms.addActor(btn_best_score);


        btn_exit_style = new TextButton.TextButtonStyle();
        btn_exit_style.up = skin.getDrawable("btn exit");
        btn_exit_style.down = skin.getDrawable("btn exit press");
        btn_exit_style.font = text_to_button;
        btn_exit = new TextButton(" ", btn_exit_style);
        btn_exit.setSize(width_btn_exit, height_btn_exit);
        btn_exit.setPosition(btn_exit_x, btn_exit_y);
        btn_exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                menuWorld.btn_exit();
                Gdx.app.exit();
                // dispose();

            }
        });
        stage_ms.addActor(btn_exit);
    }


}
