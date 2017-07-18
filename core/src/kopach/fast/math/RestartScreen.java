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

public class RestartScreen implements Screen{
    MyGameClass myGameClass;
    RestartWorld restartWorld;

    public TextureAtlas textureAtlas_ss;
    TextureRegion tr_score_fon;
    public TextButton btn_restart;
    public TextButton.TextButtonStyle btn_restart_style;
    Skin skin;
    BitmapFont text_to_button;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_rs;

    float screen_width = 720, screen_height = 1280;

    float width_button, height_button;
    float btn_restart_X, btn_restart_Y, text_level_X, text_level_Y, text_t_s_X, text_t_s_Y, text_s_X, text_s_Y, text_t_b_s_X, text_t_b_s_Y, text_b_s_X, text_b_s_Y;

    public RestartScreen(MyGameClass myGameClass){
        this.myGameClass = myGameClass;
        restartWorld = new RestartWorld();

        Gdx.input.setCatchBackKey(true);

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);

        textureAtlas_ss = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_score_fon = new TextureRegion(textureAtlas_ss.findRegion("score fon"));

        stage_rs = new Stage(viewport);
        stage_rs.clear();
        Gdx.input.setInputProcessor(stage_rs);

        skin = new Skin();
        skin.addRegions(textureAtlas_ss);
        text_to_button = new BitmapFont();

        variables();

        textButton();

       // myGameClass.interstitialAdShow();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
       // Gdx.gl.glClearColor(9, 9, 9, 5);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

      //  orthographicCamera.update();
        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_score_fon, 0, 0, screen_width, screen_height);
        myGameClass.score_textlevel.draw(myGameClass.spriteBatch, restartWorld.getString_score_text_level(),text_level_X, text_level_Y);
        myGameClass.text_restart_t_s.draw(myGameClass.spriteBatch, restartWorld.getString_score_text_result(),text_t_s_X, text_t_s_Y);
        myGameClass.text_restart_s.draw(myGameClass.spriteBatch, restartWorld.getString_score_result(),text_s_X, text_s_Y);
        myGameClass.text_restart_t_b_s.draw(myGameClass.spriteBatch, restartWorld.getString_score_text_bestresult(), text_t_b_s_X, text_t_b_s_Y);
        myGameClass.text_restart_b_s.draw(myGameClass.spriteBatch, restartWorld.getString_score_bestresult(),text_b_s_X, text_b_s_Y);
        myGameClass.spriteBatch.end();

        stage_rs.act(delta);
        stage_rs.draw();

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
        width_button = 360f;  height_button = 120f;
        btn_restart_X = screen_width/2-(width_button/2); btn_restart_Y = 100;

        text_b_s_X = screen_width/2-30;   text_t_b_s_X = screen_width/2-210;   text_s_X = screen_width/2-20;  text_t_s_X = screen_width/2-145;  text_level_X = screen_width/2-70;
        text_b_s_Y = btn_restart_Y + 620;  text_t_b_s_Y = text_b_s_Y+125;  text_s_Y = text_t_b_s_Y+160;  text_t_s_Y = text_s_Y + 105;  text_level_Y = text_t_s_Y+115;

    }


    public void textButton() {
        Gdx.app.log("log", "text button score");

        btn_restart_style = new TextButton.TextButtonStyle();
        btn_restart_style.up = skin.getDrawable("btn restart");
        btn_restart_style.checked = skin.getDrawable("btn restart press");
        btn_restart_style.font = text_to_button;
        btn_restart = new TextButton(" ", btn_restart_style);
        btn_restart.setSize(width_button, height_button);
        btn_restart.setPosition(btn_restart_X, btn_restart_Y);
        btn_restart.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                myGameClass.setScreen(new GameScreen(myGameClass));
                // dispose();

            }
        });
        stage_rs.addActor(btn_restart);
    }


}
