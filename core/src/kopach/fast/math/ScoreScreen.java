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

public class ScoreScreen implements Screen{
    MyGameClass myGameClass;
    ScoreWorld scoreWorld;

    public TextureAtlas textureAtlas_ss;
    TextureRegion tr_score_fon;
    public TextButton btn_easy, btn_normal, btn_hard;
    public TextButton.TextButtonStyle btn_easy_style, btn_normal_style, btn_hard_style;
    Skin skin;
    BitmapFont text_to_button;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_ss;

    float screen_width = 720, screen_height = 1280;

    float width_button, height_button;
    float btn_easy_x,btn_easy_y, btn_normal_x,btn_normal_y, btn_hard_x,btn_hard_y;
    float text_level_x, text_tbs_x, text_bs_x, text_level_y, text_tbs_y, text_bs_y;

    public ScoreScreen(MyGameClass myGameClass){
        this.myGameClass = myGameClass;
        scoreWorld = new ScoreWorld();

        Gdx.input.setCatchBackKey(true);

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);

        textureAtlas_ss = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_score_fon = new TextureRegion(textureAtlas_ss.findRegion("score fon"));

        variables();

        stage_ss = new Stage(viewport);
        stage_ss.clear();
        Gdx.input.setInputProcessor(stage_ss);

        skin = new Skin();
        skin.addRegions(textureAtlas_ss);
        text_to_button = new BitmapFont();

        textButton();

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

        orthographicCamera.update();
        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_score_fon, 0, 0, screen_width, screen_height);
      //  myGameClass.score_textlevel.draw(myGameClass.spriteBatch, scoreWorld.getString_score_text_level(), text_level_x, text_level_y);
        myGameClass.score_t_b_s.draw(myGameClass.spriteBatch,scoreWorld.getString_score_text_bestresult(), text_tbs_x, text_tbs_y);
        myGameClass.score_b_s.draw(myGameClass.spriteBatch,scoreWorld.getString_score_bestresult(), text_bs_x, text_bs_y);
        myGameClass.spriteBatch.end();

        stage_ss.act(delta);
        stage_ss.draw();

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
        width_button = 240;  height_button = 100;
        btn_easy_x = 0;   btn_normal_x = btn_easy_x+width_button;   btn_hard_x = btn_normal_x+width_button;
        text_bs_x = screen_width/2-15;  text_tbs_x =screen_width/2-216;  text_level_x = screen_width/2-78;

        btn_easy_y = screen_height-height_button;    btn_normal_y = btn_easy_y;   btn_hard_y = btn_easy_y;
        text_bs_y = 660;  text_tbs_y = text_bs_y + 310;  text_level_y = text_tbs_y + 145;
    }

    public void textButton() {
        Gdx.app.log("log", "text button score");

        btn_easy_style = new TextButton.TextButtonStyle();
        btn_easy_style.up = skin.getDrawable("score easy button");
       // btn_easy_style.checked = skin.getDrawable("score easy button press");
        btn_easy_style.font = text_to_button;
        btn_easy = new TextButton(" ", btn_easy_style);
        btn_easy.setSize(width_button, height_button);
        btn_easy.setPosition(btn_easy_x, btn_easy_y);
        btn_easy.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                scoreWorld.btn_easy();
                button_action();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                // dispose();

            }
        });
        stage_ss.addActor(btn_easy);

        Gdx.input.setCatchBackKey(true);

        btn_normal_style = new TextButton.TextButtonStyle();
        btn_normal_style.up = skin.getDrawable("score normal button");
       // btn_normal_style.checked = skin.getDrawable("score normal button press");
        btn_normal_style.font = text_to_button;
        btn_normal = new TextButton(" ", btn_normal_style);
        btn_normal.setSize(width_button, height_button);
        btn_normal.setPosition(btn_normal_x, btn_normal_y);
        btn_normal.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                scoreWorld.btn_normal();
                button_action();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                // dispose();

            }
        });
        stage_ss.addActor(btn_normal);


        btn_hard_style = new TextButton.TextButtonStyle();
        btn_hard_style.up = skin.getDrawable("score hard button");
       // btn_hard_style.checked = skin.getDrawable("score hard button press");
        btn_hard_style.font = text_to_button;
        btn_hard = new TextButton(" ", btn_hard_style);
        btn_hard.setSize(width_button, height_button);
        btn_hard.setPosition(btn_hard_x, btn_hard_y);
        btn_hard.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                scoreWorld.btn_hard();
                button_action();
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                // dispose();

            }
        });
        stage_ss.addActor(btn_hard);
    }

    public void button_action(){
        if(scoreWorld.string_level.equals("easy")) {
            btn_easy_style.up = skin.getDrawable("score easy button press");
            btn_normal_style.up = skin.getDrawable("score normal button");
            btn_hard_style.up = skin.getDrawable("score hard button");
        }else{
            btn_easy_style.up = skin.getDrawable("score easy button");
        }

        if(scoreWorld.string_level.equals("normal")) {
            btn_normal_style.up = skin.getDrawable("score normal button press");
            btn_easy_style.up = skin.getDrawable("score easy button");
            btn_hard_style.up = skin.getDrawable("score hard button");
        }else{
            btn_normal_style.up = skin.getDrawable("score normal button");
        }

        if(scoreWorld.string_level.equals("hard")) {
            btn_hard_style.up = skin.getDrawable("score hard button press");
            btn_easy_style.up = skin.getDrawable("score easy button");
            btn_normal_style.up = skin.getDrawable("score normal button");
        }else{
            btn_hard_style.up = skin.getDrawable("score hard button");
        }
    }


}
