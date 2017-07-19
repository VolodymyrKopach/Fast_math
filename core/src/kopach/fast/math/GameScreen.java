package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by vova on 17.05.17.
 */

public class GameScreen implements Screen {
    MyGameClass myGameClass;
    public GameWorld gameWorld;

    public TextureAtlas textureAtlas_vg;
    public TextureRegion tr_screen, tr_fon, tr_X;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_vg;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_answer, btn_C, btn_minus;
    public TextButton.TextButtonStyle btn1_style, btn2_style, btn3_style, btn4_style, btn5_style, btn6_style, btn7_style, btn8_style, btn9_style, btn_minus_style, btn0_style, btn_C_style, btn_answer_style;
    Skin skin;
    BitmapFont text_to_button;
    BitmapFont mGC_gs_text_pryklad, mGC_gs_text_vidp, mGC_gs_text_score, mGC_gs_text_time;
    SpriteBatch mGC_spriteBatch;


    float screen_width = 720, screen_height = 1280;
    public float width_btn, height_btn, width_btn_answer, height_btn_answer, width_btn_C, height_btn_C, tr_screen_width, tr_screen_height;
    float width_X, height_X;
    public float vidstan_width, vidstan_height;
    public float btn_C_x,btn_C_y, btn0_x,btn0_y, btn1_x,btn1_y, btn2_x,btn2_y, btn3_x,btn3_y, btn4_x,btn4_y, btn5_x,btn5_y, btn6_x,btn6_y, btn7_x,btn7_y, btn8_x,btn8_y, btn9_x,btn9_y, btn_minus_x,btn_minus_y, btn_answer_x,btn_answer_y, tr_X_x, tr_X_y, tr_screen_x, tr_screen_y;
    float text_score_x, text_score_y, text_pryklad_x, text_pryklad_y,text_vidp_x, text_vidp_y, text_time_x, text_time_y;

    public GameScreen(final MyGameClass myGameClass) {
        this.myGameClass = myGameClass;
        gameWorld = new GameWorld();

        Gdx.input.setCatchBackKey(true);

        variables_x_y();
        variables();

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);


        textureAtlas_vg = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_screen = new TextureRegion(textureAtlas_vg.findRegion("screen"));   tr_fon = new TextureRegion(textureAtlas_vg.findRegion("fon"));  tr_X = new TextureRegion(textureAtlas_vg.findRegion("x"));

        stage_vg = new Stage(viewport);
        stage_vg.clear();
        Gdx.input.setInputProcessor(stage_vg);

        skin = new Skin();
        skin.addRegions(textureAtlas_vg);
        text_to_button = new BitmapFont();

        textButton();

      //  myGameClass.bannerAdShow();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
       // Gdx.gl.glClearColor(9, 9, 9, 5);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game_level();
        gameWorld.setString_timer(delta);

       // orthographicCamera.update();
        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        myGameClass.spriteBatch.draw(tr_screen, tr_screen_x, tr_screen_y, tr_screen_width, tr_screen_height);

        if (gameWorld.getBoolean_X()){
            myGameClass.spriteBatch.draw(tr_X, tr_X_x, tr_X_y,width_X, height_X);
        }
        myGameClass.gs_text_pryklad.draw(myGameClass.spriteBatch, gameWorld.getString_to_screen(), text_pryklad_x, text_pryklad_y);
        myGameClass.gs_text_vidp.draw(myGameClass.spriteBatch, gameWorld.getString_input(), text_vidp_x, text_vidp_y);
        myGameClass.gs_text_score.draw(myGameClass.spriteBatch, gameWorld.getString_score(), text_score_x, text_score_y);
        myGameClass.gs_text_time.draw(myGameClass.spriteBatch, gameWorld.getString_timer(), text_time_x, text_time_y);
        myGameClass.spriteBatch.end();

        stage_vg.act(delta);
        stage_vg.draw();
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
        mGC_spriteBatch = myGameClass.spriteBatch;

        mGC_gs_text_time = myGameClass.gs_text_time;
        mGC_gs_text_score = myGameClass.gs_text_score;
        mGC_gs_text_pryklad = myGameClass.gs_text_pryklad;
        mGC_gs_text_vidp = myGameClass.gs_text_vidp;

    }

    public void variables_x_y(){
        width_btn = 185;  height_btn = 185;
        width_btn_answer = 235;  height_btn_answer = 235;
        width_btn_C = 76;  height_btn_C = 110;
        width_X = 80; height_X = 80;
        tr_screen_width = 660; tr_screen_height = 200;
        vidstan_width = 20;  vidstan_height = 18;


        tr_screen_x = screen_width/2-(tr_screen_width/2);
        btn1_x = (screen_width-(width_btn*3+vidstan_width*2))/2; btn4_x=btn1_x; btn7_x=btn1_x; btn_minus_x=btn1_x;
        btn2_x = btn1_x+width_btn+vidstan_width;  btn5_x = btn4_x+width_btn+vidstan_width;   btn8_x = btn7_x+width_btn+vidstan_width;  btn0_x = btn_minus_x+width_btn+vidstan_width;
        btn3_x = btn2_x+width_btn+vidstan_width;  btn6_x = btn5_x+width_btn+vidstan_width;   btn9_x = btn8_x+width_btn+vidstan_width;  btn_answer_x = btn0_x+width_btn+vidstan_width;
        btn_C_x = (screen_width-tr_screen_width)/2+tr_screen_width - 90;
        tr_X_x = screen_width/2-(width_X/2);
        text_time_x = 35; text_score_x = screen_width-160;  text_pryklad_x = tr_screen_x+20; text_vidp_x = btn_C_x-200;

        tr_X_y = screen_height-width_X-20;
        tr_screen_y = tr_X_y-40-tr_screen_height;
        btn_C_y =  tr_screen_y+(tr_screen_height/2-60);
        btn1_y = tr_screen_y-height_btn-98;  btn2_y = btn1_y;  btn3_y = btn1_y;
        btn4_y = btn1_y-vidstan_height-height_btn;   btn5_y = btn2_y-vidstan_height-height_btn;   btn6_y = btn3_y-vidstan_height-height_btn;
        btn7_y = btn4_y-vidstan_height-height_btn;   btn8_y = btn5_y-vidstan_height-height_btn;   btn9_y = btn6_y-vidstan_height-height_btn;
        btn_minus_y = btn7_y-vidstan_height-height_btn;   btn0_y = btn8_y-vidstan_height-height_btn;   btn_answer_y = btn9_y-vidstan_height-height_btn;
        text_time_y = screen_height-20; text_score_y = text_time_y;  text_pryklad_y = tr_screen_y+tr_screen_height/2+25; text_vidp_y = text_pryklad_y;

    }

    public void game_level(){
        if(gameWorld.float_timer < 0){
            gameWorld.float_timer = 10;
            myGameClass.setScreen(new RestartScreen(myGameClass));
            Gdx.app.log("log","good");
        }
    }


    public void textButton(){
        Gdx.app.log("log","text button");

        btn_answer_style = new TextButton.TextButtonStyle();
        btn_answer_style.up = skin.getDrawable("btn_answer");
        btn_answer_style.down = skin.getDrawable("btn_answer_press");
        btn_answer_style.font = text_to_button;
        btn_answer = new TextButton(" ", btn_answer_style);
        btn_answer.setSize(width_btn, height_btn);
        btn_answer.setPosition(btn_answer_x, btn_answer_y);
        btn_answer.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.btn_answer();
            }
        });
        stage_vg.addActor(btn_answer);


        btn0_style = new TextButton.TextButtonStyle();
        btn0_style.up = skin.getDrawable("btn0");
        btn0_style.down = skin.getDrawable("btn0_press");
        btn0_style.font = text_to_button;
        btn_0 = new TextButton(" ", btn0_style);
        btn_0.setSize(width_btn, height_btn);
        btn_0.setPosition(btn0_x, btn0_y);
        btn_0.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("0");
                // dispose();

            }
        });
        stage_vg.addActor(btn_0);


        btn_minus_style = new TextButton.TextButtonStyle();
        btn_minus_style.up = skin.getDrawable("btnminus");
        btn_minus_style.down = skin.getDrawable("btnminus_press");
        btn_minus_style.font = text_to_button;

        btn_minus = new TextButton(" ",btn_minus_style);
        btn_minus.setPosition(btn_minus_x, btn_minus_y);
        btn_minus.setSize(width_btn, height_btn);
        btn_minus.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("-");
                // dispose();

            }
        });
        stage_vg.addActor(btn_minus);


        btn9_style = new TextButton.TextButtonStyle();
        btn9_style.up = skin.getDrawable("btn9");
        btn9_style.down = skin.getDrawable("btn9_press");
        btn9_style.font = text_to_button;
        btn_9 = new TextButton(" ", btn9_style);
        btn_9.setSize(width_btn, height_btn);
        btn_9.setPosition(btn9_x, btn9_y);
        btn_9.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("9");
                // dispose();

            }
        });
        stage_vg.addActor(btn_9);


        btn8_style = new TextButton.TextButtonStyle();
        btn8_style.up = skin.getDrawable("btn8");
        btn8_style.down = skin.getDrawable("btn8_press");
        btn8_style.font = text_to_button;
        btn_8 = new TextButton(" ",btn8_style);
        btn_8.setSize(width_btn, height_btn);
        btn_8.setPosition(btn8_x, btn8_y);
        btn_8.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("8");
                // dispose();

            }
        });
        stage_vg.addActor(btn_8);


        btn7_style = new TextButton.TextButtonStyle();
        btn7_style.up = skin.getDrawable("btn7");
        btn7_style.down = skin.getDrawable("btn7_press");
        btn7_style.font = text_to_button;
        btn_7 = new TextButton(" ", btn7_style);
        btn_7.setSize(width_btn, height_btn);
        btn_7.setPosition(btn7_x, btn7_y);
        btn_7.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("7");
                // dispose();

            }
        });
        stage_vg.addActor(btn_7);


        btn6_style = new TextButton.TextButtonStyle();
        btn6_style.up = skin.getDrawable("btn6");
        btn6_style.down = skin.getDrawable("btn6_press");
        btn6_style.font = text_to_button;
        btn_6 = new TextButton(" ", btn6_style);
        btn_6.setSize(width_btn, height_btn);
        btn_6.setPosition(btn6_x, btn6_y);
        btn_6.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("6");
                // dispose();

            }
        });
        stage_vg.addActor(btn_6);


        btn5_style = new TextButton.TextButtonStyle();
        btn5_style.up = skin.getDrawable("btn5");
        btn5_style.down = skin.getDrawable("btn5_press");
        btn5_style.font = text_to_button;
        btn_5 = new TextButton(" ", btn5_style);
        btn_5.setSize(width_btn, height_btn);
        btn_5.setPosition(btn5_x, btn5_y);
        btn_5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("5");
                // dispose();

            }
        });
        stage_vg.addActor(btn_5);


        btn4_style = new TextButton.TextButtonStyle();
        btn4_style.up = skin.getDrawable("btn4");
        btn4_style.down = skin.getDrawable("btn4_press");
        btn4_style.font = text_to_button;
        btn_4 = new TextButton(" ", btn4_style);
        btn_4.setSize(width_btn, height_btn);
        btn_4.setPosition(btn4_x, btn4_y);
        btn_4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("4");
                // dispose();

            }
        });
        stage_vg.addActor(btn_4);


        btn3_style = new TextButton.TextButtonStyle();
        btn3_style.up = skin.getDrawable("btn3");
        btn3_style.down = skin.getDrawable("btn3_press");
        btn3_style.font = text_to_button;
        btn_3 = new TextButton(" ", btn3_style);
        btn_3.setSize(width_btn, height_btn);
        btn_3.setPosition(btn3_x, btn3_y);
        btn_3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("3");
                // dispose();

            }
        });
        stage_vg.addActor(btn_3);


        btn2_style = new TextButton.TextButtonStyle();
        btn2_style.up = skin.getDrawable("btn2");
        btn2_style.down = skin.getDrawable("btn2_press");
        btn2_style.font = text_to_button;
        btn_2 = new TextButton(" ", btn2_style);
        btn_2.setSize(width_btn, height_btn);
        btn_2.setPosition(btn2_x, btn2_y);
        btn_2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("2");
                // dispose();

            }
        });
        stage_vg.addActor(btn_2);


        btn1_style = new TextButton.TextButtonStyle();
        btn1_style.up = skin.getDrawable("btn1");
        btn1_style.down = skin.getDrawable("btn1_press");
        btn1_style.font = text_to_button;

        btn_1 = new TextButton(" ",btn1_style);
        btn_1.setPosition(btn1_x, btn1_y);
        btn_1.setSize(width_btn, height_btn);
        btn_1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.setString_input("1");
                // dispose();

            }
        });
        stage_vg.addActor(btn_1);


        btn_C_style = new TextButton.TextButtonStyle();
        btn_C_style.up = skin.getDrawable("btn_C");
        btn_C_style.down = skin.getDrawable("btn_C_press");
        btn_C_style.font = text_to_button;
        btn_C = new TextButton(" ", btn_C_style);
        btn_C.setSize(width_btn_C, height_btn_C);
        btn_C.setPosition(btn_C_x, btn_C_y);
        btn_C.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld.prees_C();
                // dispose();

            }
        });
        stage_vg.addActor(btn_C);
    }




}
