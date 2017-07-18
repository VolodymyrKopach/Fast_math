package kopach.fast.math.no.name.game;

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

import kopach.fast.math.GameWorld;
import kopach.fast.math.MenuScreen;
import kopach.fast.math.MyGameClass;
import kopach.fast.math.RestartScreen;

/**
 * Created by vova on 17.05.17.
 */

public class GameScreen1 implements Screen {
    MyGameClass myGameClass;
    public GameWorld1 gameWorld1;

    public TextureAtlas textureAtlas_vg;
    public TextureRegion tr_screen, tr_fon, tr_X;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_vg;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_answer, btn_C, btn_minus;
    public TextButton.TextButtonStyle btn1_style, btn2_style, btn3_style, btn4_style, btn5_style, btn6_style, btn7_style, btn8_style, btn9_style, btn_minus_style, btn0_style, btn_C_style, btn_answer_style;
    Skin skin;
    BitmapFont text_to_button;


    public float width_button, height_button, width_button_answer, height_button_answer, width_button_C, height_button_C;
    float width_X, height_X;
    public float vidstan_width, vidstan_height;
    public float btn_C_x,btn_C_y, btn0_x,btn0_y, btn1_x,btn1_y, btn2_x,btn2_y, btn3_x,btn3_y, btn4_x,btn4_y, btn5_x,btn5_y, btn6_x,btn6_y, btn7_x,btn7_y, btn8_x,btn8_y, btn9_x,btn9_y, btn_minus_x,btn_minus_y, btn_answer_x,btn_answer_y, tr_X_x, tr_X_y;

    public GameScreen1(final MyGameClass myGameClass) {
        this.myGameClass = myGameClass;
        gameWorld1 = new GameWorld1();

        Gdx.input.setCatchBackKey(true);

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(720, 1280, orthographicCamera);


        textureAtlas_vg = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_screen = new TextureRegion(textureAtlas_vg.findRegion("screen"));   tr_fon = new TextureRegion(textureAtlas_vg.findRegion("fon"));  tr_X = new TextureRegion(textureAtlas_vg.findRegion("x"));


        width_button = 128;  height_button = 128;
        width_button_answer = 128;  height_button_answer = 128;
        width_button_C = 75;  height_button_C = 75;
        width_X = 50; height_X = 50;
        vidstan_width = 12;  vidstan_height = 8;
        btn_minus_x = 33; btn7_x = btn_minus_x; btn4_x = btn_minus_x; btn1_x = btn_minus_x;  btn0_x = btn_minus_x+width_button+vidstan_width; btn8_x = btn_minus_x+width_button+vidstan_width; btn5_x = btn_minus_x+width_button+vidstan_width; btn2_x = btn_minus_x+width_button+vidstan_width;  btn_answer_x = btn0_x+width_button+vidstan_width; btn9_x = btn0_x+width_button+vidstan_width; btn6_x = btn0_x+width_button+vidstan_width; btn3_x = btn0_x+width_button+vidstan_width;
        btn_minus_y = 15; btn7_y = btn_minus_y+height_button+vidstan_height; btn4_y = btn7_y+height_button+vidstan_height; btn1_y = btn4_y+height_button+vidstan_height;  btn0_y = btn_minus_y; btn8_y = btn0_y+height_button+vidstan_height; btn5_y = btn8_y+height_button+vidstan_height; btn2_y = btn5_y+height_button+vidstan_height;  btn_answer_y = btn_minus_y; btn9_y = btn_answer_y+height_button+vidstan_height; btn6_y = btn9_y+height_button+vidstan_height; btn3_y = btn6_y+height_button+vidstan_height;
        btn_C_x = 400; btn_C_y =  625;
        tr_X_x = Gdx.graphics.getWidth()/2-(width_X/2);  tr_X_y = 740;


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
        gameWorld1.setString_timer(delta);

        orthographicCamera.update();

       // int i;

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_fon, 0, 0, 720, 1280);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        myGameClass.spriteBatch.draw(tr_screen, 10, 590, 460, 135);
        if (gameWorld1.getBoolean_X()){
            myGameClass.spriteBatch.draw(tr_X, tr_X_x, tr_X_y,width_X, height_X);
        }
        myGameClass.text_pryklad.draw(myGameClass.spriteBatch, gameWorld1.getString_result_to_screen(), 40, 680);
      //  myGameClass.text_vidp.draw(myGameClass.spriteBatch, gameWorld1.getString_input(), 350, 680);
        myGameClass.text_score.draw(myGameClass.spriteBatch, gameWorld1.getString_score(), 360, 780);
        myGameClass.text_time.draw(myGameClass.spriteBatch, gameWorld1.getString_timer(), 35, 780);
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

    public void game_level(){
        if(gameWorld1.float_timer < 0){
            gameWorld1.float_timer = 10;
            myGameClass.setScreen(new RestartScreen(myGameClass));
            Gdx.app.log("log","good");
        }
    }


    public void textButton(){
        Gdx.app.log("log","text button");

      /*  btn_answer_style = new TextButton.TextButtonStyle();
        btn_answer_style.up = skin.getDrawable("btn_answer");
        btn_answer_style.down = skin.getDrawable("btn_answer_press");
        btn_answer_style.font = text_to_button;
        btn_answer = new TextButton(" ", btn_answer_style);
        btn_answer.setSize(width_button, height_button);
        btn_answer.setPosition(btn_answer_x, btn_answer_y);
        btn_answer.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.btn_answer();
            }
        });
        stage_vg.addActor(btn_answer);


        btn0_style = new TextButton.TextButtonStyle();
        btn0_style.up = skin.getDrawable("btn0");
        btn0_style.down = skin.getDrawable("btn0_press");
        btn0_style.font = text_to_button;
        btn_0 = new TextButton(" ", btn0_style);
        btn_0.setSize(width_button, height_button);
        btn_0.setPosition(btn0_x, btn0_y);
        btn_0.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input("0");
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
        btn_minus.setSize(width_button, height_button);
        btn_minus.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input("-");
                // dispose();

            }
        });
        stage_vg.addActor(btn_minus);


        btn9_style = new TextButton.TextButtonStyle();
        btn9_style.up = skin.getDrawable("btn9");
        btn9_style.down = skin.getDrawable("btn9_press");
        btn9_style.font = text_to_button;
        btn_9 = new TextButton(" ", btn9_style);
        btn_9.setSize(width_button, height_button);
        btn_9.setPosition(btn9_x, btn9_y);
        btn_9.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input("9");
                // dispose();

            }
        });
        stage_vg.addActor(btn_9);


        btn8_style = new TextButton.TextButtonStyle();
        btn8_style.up = skin.getDrawable("btn8");
        btn8_style.down = skin.getDrawable("btn8_press");
        btn8_style.font = text_to_button;
        btn_8 = new TextButton(" ",btn8_style);
        btn_8.setSize(width_button, height_button);
        btn_8.setPosition(btn8_x, btn8_y);
        btn_8.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input("8");
                // dispose();

            }
        });
        stage_vg.addActor(btn_8);


        btn7_style = new TextButton.TextButtonStyle();
        btn7_style.up = skin.getDrawable("btn7");
        btn7_style.down = skin.getDrawable("btn7_press");
        btn7_style.font = text_to_button;
        btn_7 = new TextButton(" ", btn7_style);
        btn_7.setSize(width_button, height_button);
        btn_7.setPosition(btn7_x, btn7_y);
        btn_7.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input("7");
                // dispose();

            }
        });
        stage_vg.addActor(btn_7);   */


        btn6_style = new TextButton.TextButtonStyle();
        btn6_style.up = skin.getDrawable("btn6");
        btn6_style.down = skin.getDrawable("btn6_press");
        btn6_style.font = text_to_button;
        btn_6 = new TextButton(" ", btn6_style);
        btn_6.setSize(width_button, height_button);
        btn_6.setPosition(btn6_x, btn6_y);
        btn_6.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_variant_answer("------------");
                // dispose();

            }
        });
        stage_vg.addActor(btn_6);


        btn5_style = new TextButton.TextButtonStyle();
        btn5_style.up = skin.getDrawable("btn5");
        btn5_style.down = skin.getDrawable("btn5_press");
        btn5_style.font = text_to_button;
        btn_5 = new TextButton(" ", btn5_style);
        btn_5.setSize(width_button, height_button);
        btn_5.setPosition(btn5_x, btn5_y);
        btn_5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_variant_answer("---------");
                // dispose();

            }
        });
        stage_vg.addActor(btn_5);


        btn4_style = new TextButton.TextButtonStyle();
        btn4_style.up = skin.getDrawable("btn4");
        btn4_style.down = skin.getDrawable("btn4_press");
        btn4_style.font = text_to_button;
        btn_4 = new TextButton(" ", btn4_style);
        btn_4.setSize(width_button, height_button);
        btn_4.setPosition(btn4_x, btn4_y);
        btn_4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_variant_answer("----------");
                // dispose();

            }
        });
        stage_vg.addActor(btn_4);


        btn3_style = new TextButton.TextButtonStyle();
        btn3_style.up = skin.getDrawable("btn3");
        btn3_style.down = skin.getDrawable("btn3_press");
        btn3_style.font = text_to_button;
        btn_3 = new TextButton(" ", btn3_style);
        btn_3.setSize(width_button, height_button);
        btn_3.setPosition(btn3_x, btn3_y);
        btn_3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_variant_answer("--------------");
                // dispose();

            }
        });
        stage_vg.addActor(btn_3);


        btn2_style = new TextButton.TextButtonStyle();
        btn2_style.up = skin.getDrawable("btn2");
        btn2_style.down = skin.getDrawable("btn2_press");
        btn2_style.font = text_to_button;
        btn_2 = new TextButton(" ", btn2_style);
        btn_2.setSize(width_button, height_button);
        btn_2.setPosition(btn2_x, btn2_y);
        btn_2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_variant_answer("----------");
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
        btn_1.setSize(width_button, height_button);
        btn_1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_variant_answer("---------");
                // dispose();

            }
        });
        stage_vg.addActor(btn_1);
    }




}
