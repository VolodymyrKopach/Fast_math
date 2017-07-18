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

public class LevelScreen implements Screen{
    MyGameClass myGameClass;
    LevelWorld levelWorld;

    public TextureAtlas textureAtlas_ss;
    public TextureRegion tr_level_fon, tr_level_text;
    public TextButton btn_easy, btn_normal, btn_hard;
    public TextButton.TextButtonStyle btn_easy_style, btn_normal_style, btn_hard_style;
    Skin skin;
    BitmapFont text_to_button;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_ls;

    float screen_width = 720, screen_height = 1280;

    float vidstan_width, vidstan_height;
    float width_button, height_button, width_tr_level_text, height_tr_level_text;
    float btn_easy_x,btn_easy_y, btn_normal_x,btn_normal_y, btn_hard_x,btn_hard_y, tr_level_text_x, tr_level_text_y;

    public LevelScreen(MyGameClass myGameClass){
        this.myGameClass = myGameClass;
        levelWorld = new LevelWorld();

        Gdx.input.setCatchBackKey(true);

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);

        textureAtlas_ss = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_level_fon = new TextureRegion(textureAtlas_ss.findRegion("level fon"));
        tr_level_text = new TextureRegion(textureAtlas_ss.findRegion("level text"));

        variables();

        stage_ls = new Stage(viewport);
        stage_ls.clear();
        Gdx.input.setInputProcessor(stage_ls);

        skin = new Skin();
        skin.addRegions(textureAtlas_ss);
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
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        orthographicCamera.update();
        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_level_fon, 0, 0, screen_width, screen_height);
        myGameClass.spriteBatch.draw(tr_level_text, tr_level_text_x, tr_level_text_y, width_tr_level_text, height_tr_level_text);
        myGameClass.spriteBatch.end();

        stage_ls.act(delta);
        stage_ls.draw();
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
        vidstan_width = 60;  vidstan_height = 70;

        width_button = 360;  height_button = 80;
        width_tr_level_text = 480; height_tr_level_text = 60;

        tr_level_text_x = Gdx.graphics.getWidth()/2-(width_tr_level_text/2);
        btn_hard_x = Gdx.graphics.getWidth()/2-(width_button/2);   btn_normal_x = btn_hard_x;    btn_easy_x = btn_hard_x;

        tr_level_text_y = 850;
        btn_hard_y = 300;   btn_normal_y = btn_hard_y+height_button+vidstan_height;   btn_easy_y = btn_normal_y+height_button+vidstan_height;



    }


    public void textButton() {
        Gdx.app.log("log", "text button level");

        btn_easy_style = new TextButton.TextButtonStyle();
        btn_easy_style.up = skin.getDrawable("btn level easy");
        btn_easy_style.down = skin.getDrawable("btn level easy press");
        btn_easy_style.font = text_to_button;
        btn_easy = new TextButton(" ", btn_easy_style);
        btn_easy.setSize(width_button, height_button);
        btn_easy.setPosition(btn_easy_x, btn_easy_y);
        btn_easy.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                levelWorld.btn_easy();
                myGameClass.setScreen(new GameScreen(myGameClass));

                // dispose();

            }
        });
        stage_ls.addActor(btn_easy);


        btn_normal_style = new TextButton.TextButtonStyle();
        btn_normal_style.up = skin.getDrawable("btn level normal");
        btn_normal_style.down = skin.getDrawable("btn level normal press");
        btn_normal_style.font = text_to_button;
        btn_normal = new TextButton(" ", btn_normal_style);
        btn_normal.setSize(width_button, height_button);
        btn_normal.setPosition(btn_normal_x, btn_normal_y);
        btn_normal.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                levelWorld.btn_normal();
                myGameClass.setScreen(new GameScreen(myGameClass));
                // dispose();

            }
        });
        stage_ls.addActor(btn_normal);


        btn_hard_style = new TextButton.TextButtonStyle();
        btn_hard_style.up = skin.getDrawable("btn level hard");
        btn_hard_style.down = skin.getDrawable("btn level easy press");
        btn_hard_style.font = text_to_button;
        btn_hard = new TextButton(" ", btn_hard_style);
        btn_hard.setSize(width_button, height_button);
        btn_hard.setPosition(btn_hard_x, btn_hard_y);
        btn_hard.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                levelWorld.btn_hard();
                myGameClass.setScreen(new GameScreen(myGameClass));
                // dispose();

            }
        });
        stage_ls.addActor(btn_hard);
    }


}
