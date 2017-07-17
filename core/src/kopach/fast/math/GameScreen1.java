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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by vova on 17.05.17.
 */

public class GameScreen1 implements Screen {
    MyGameClass myGameClass;
    public GameWorld1 gameWorld1;

    public TextureAtlas textureAtlas_vg;
    public TextureRegion tr_fon, tr_X, tr_propusk;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_vg;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6;
    public TextButton.TextButtonStyle btn1_style, btn2_style, btn3_style, btn4_style, btn5_style, btn6_style;
    Skin skin;
    BitmapFont text_to_button;


    float screen_width = 720, screen_height = 1280;
    public float btn_width, btn_height;
    float X_width, X_height, tr_propusk_width, tr_propusk_height;
    public float vidstan_width, vidstan_height;
    public float btn1_x,btn1_y, btn2_x,btn2_y, btn3_x,btn3_y, btn4_x,btn4_y, btn5_x,btn5_y, btn6_x,btn6_y;
    float tr_propusk_x, tr_propusk_y, text_text_score_x, text_score_x, text_text_score_y, text_score_y, text_pryklad_x, text_pryklad_y,text_vidp_x, text_vidp_y, text_time_x, text_time_y, text_text_ne_prav_vidp_x, text_ne_prav_vidp_x, text_text_ne_prav_vidp_y, text_ne_prav_vidp_y;

    public GameScreen1(final MyGameClass myGameClass) {
        this.myGameClass = myGameClass;
        gameWorld1 = new GameWorld1();


        Gdx.input.setCatchBackKey(true);
        variables();

     //   gameWorld1.startGame();
        Gdx.app.log("GameScreen1","gw1 start game");

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);


        textureAtlas_vg = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_fon = new TextureRegion(textureAtlas_vg.findRegion("fon 1"));  tr_X = new TextureRegion(textureAtlas_vg.findRegion("x"));   tr_propusk = new TextureRegion(textureAtlas_vg.findRegion("znak pytanya"));

        stage_vg = new Stage(viewport);
        stage_vg.clear();
        Gdx.input.setInputProcessor(stage_vg);

        skin = new Skin();
        skin.addRegions(textureAtlas_vg);
        text_to_button = new BitmapFont();
        //  myGameClass.bannerAdShow();

        textButton();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameWorld1.setString_timer(delta);

        variables();
        setText_in_textButton();

        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        if (gameWorld1.getBoolean_X()){
           // myGameClass.spriteBatch.draw(tr_X, tr_X_x, tr_X_y,X_width, X_height);
        }

        myGameClass.spriteBatch.draw(tr_propusk, tr_propusk_x, tr_propusk_y, tr_propusk_width, tr_propusk_height);
      //  myGameClass.gs1_text_text_no_prav_vidp.draw(myGameClass.spriteBatch, "Wrong: ", text_text_ne_prav_vidp_x, text_text_ne_prav_vidp_y);
      //  myGameClass.gs1_text_no_prav_vidp.draw(myGameClass.spriteBatch, gameWorld1.getString_neprav_vidp(), text_ne_prav_vidp_x, text_ne_prav_vidp_y);
        myGameClass.gs1_text_text_score.draw(myGameClass.spriteBatch, "Score: ", text_text_score_x, text_text_score_y);
        myGameClass.gs1_text_score.draw(myGameClass.spriteBatch, gameWorld1.getString_score(), text_score_x, text_score_y);
        myGameClass.gs1_text_pryklad.draw(myGameClass.spriteBatch, gameWorld1.getString_to_screen(), text_pryklad_x, text_pryklad_y);
        myGameClass.gs1_text_vidp.draw(myGameClass.spriteBatch, gameWorld1.getString_input(), text_vidp_x, text_vidp_y);
        myGameClass.gs1_text_time.draw(myGameClass.spriteBatch, gameWorld1.getString_timer(), text_text_ne_prav_vidp_x, text_text_ne_prav_vidp_y);
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
        vidstan_width = 40;  vidstan_height = 50;
        X_width = 200;  X_height = 200;
        tr_propusk_width = 90; tr_propusk_height = 110;
        btn_width = 173;  btn_height = 173;

        tr_propusk_x = gameWorld1.getInt_tr_propusk_x();  tr_propusk_y = 900;


        btn1_x = 50;  btn2_x = btn1_x+btn_width+vidstan_width;  btn3_x = btn2_x+btn_width+vidstan_width;
        btn4_x = btn1_x;  btn5_x = btn2_x;  btn6_x = btn3_x;

        btn4_y = 140;   btn5_y = btn4_y;   btn6_y = btn4_y;
        btn1_y = btn4_y+btn_height+vidstan_height;   btn2_y = btn1_y;   btn3_y = btn1_y;


        text_pryklad_x = 75;  text_vidp_x = tr_propusk_x; // Буде залежати від того якої частини приклада не буде вистачати
        text_text_ne_prav_vidp_x = 20;  text_ne_prav_vidp_x = text_text_ne_prav_vidp_x + 140;  text_time_x = screen_width/2-6;   text_text_score_x = screen_width - 165;   text_score_x = text_text_score_x + 126;

        text_pryklad_y = 980;   text_vidp_y = text_pryklad_y;
        text_text_ne_prav_vidp_y = screen_height - 50;  text_ne_prav_vidp_y = text_text_ne_prav_vidp_y + 4;   text_time_y = screen_height - 50;   text_text_score_y = text_ne_prav_vidp_y;    text_score_y = text_text_score_y + 4;


    }

 /*   public void game_level(){
        if(gameWorld1.float_timer < 0){
            gameWorld1.float_timer = 10;
            myGameClass.setScreen(new RestartScreen(myGameClass));
            Gdx.app.log("log","good");
        }
    } */


    public void textButton(){
        Gdx.app.log("GameScreen1","text button");

        btn6_style = new TextButton.TextButtonStyle();
        btn6_style.up = skin.getDrawable("btn krug");
        btn6_style.down = skin.getDrawable("btn6_press");
        btn6_style.font = myGameClass.gs1_text_btn;
      //  Gdx.app.log("","считка");
        btn_6 = new TextButton(String.valueOf(gameWorld1.getInt_btn_6()), btn6_style);
        Gdx.app.log(" ", String.valueOf(gameWorld1.getInt_btn_6()));
        btn_6.setSize(btn_width, btn_height);
        btn_6.setPosition(btn6_x, btn6_y);
        btn_6.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input(String.valueOf(gameWorld1.int_btn_6));
                gameWorld1.answer();
                // dispose();

            }
        });
        stage_vg.addActor(btn_6);


        btn5_style = new TextButton.TextButtonStyle();
        btn5_style.up = skin.getDrawable("btn krug");
        btn5_style.down = skin.getDrawable("btn5_press");
        btn5_style.font = myGameClass.gs1_text_btn;
        btn_5 = new TextButton(String.valueOf(gameWorld1.getInt_btn_5()), btn5_style);
        btn_5.setSize(btn_width, btn_height);
        btn_5.setPosition(btn5_x, btn5_y);
        btn_5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input(String.valueOf(gameWorld1.int_btn_5));
                gameWorld1.answer();
                // dispose();

            }
        });
        stage_vg.addActor(btn_5);


        btn4_style = new TextButton.TextButtonStyle();
        btn4_style.up = skin.getDrawable("btn krug");
        btn4_style.down = skin.getDrawable("btn4_press");
        btn4_style.font = myGameClass.gs1_text_btn;
        btn_4 = new TextButton(String.valueOf(gameWorld1.getInt_btn_4()), btn4_style);
        btn_4.setSize(btn_width, btn_height);
        btn_4.setPosition(btn4_x, btn4_y);
        btn_4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input(String.valueOf(gameWorld1.int_btn_4));
                gameWorld1.answer();
                // dispose();

            }
        });
        stage_vg.addActor(btn_4);


        btn3_style = new TextButton.TextButtonStyle();
        btn3_style.up = skin.getDrawable("btn krug");
        btn3_style.down = skin.getDrawable("btn3_press");
        btn3_style.font = myGameClass.gs1_text_btn;
        btn_3 = new TextButton(String.valueOf(gameWorld1.getInt_btn_3()), btn3_style);
        btn_3.setSize(btn_width, btn_height);
        btn_3.setPosition(btn3_x, btn3_y);
        btn_3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input(String.valueOf(gameWorld1.int_btn_3));
                gameWorld1.answer();
                // dispose();

            }
        });
        stage_vg.addActor(btn_3);


        btn2_style = new TextButton.TextButtonStyle();
        btn2_style.up = skin.getDrawable("btn krug");
        btn2_style.down = skin.getDrawable("btn2_press");
        btn2_style.font = myGameClass.gs1_text_btn;
        btn_2 = new TextButton(String.valueOf(gameWorld1.getInt_btn_2()), btn2_style);
        btn_2.setSize(btn_width, btn_height);
        btn_2.setPosition(btn2_x, btn2_y);
        btn_2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input(String.valueOf(gameWorld1.int_btn_2));
                gameWorld1.answer();
                // dispose();

            }
        });
        stage_vg.addActor(btn_2);


        btn1_style = new TextButton.TextButtonStyle();
        btn1_style.up = skin.getDrawable("btn krug");
        btn1_style.down = skin.getDrawable("btn1_press");
        btn1_style.font = myGameClass.gs1_text_btn;
        btn_1 = new TextButton(String.valueOf(gameWorld1.getInt_btn_1()),btn1_style);
        btn_1.setPosition(btn1_x, btn1_y);
        btn_1.setSize(btn_width, btn_height);
        btn_1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                gameWorld1.setString_input(String.valueOf(gameWorld1.int_btn_1));
                gameWorld1.answer();
                // dispose();

            }
        });
        stage_vg.addActor(btn_1);

    }

    public void setText_in_textButton(){

      //  Gdx.app.log("GameScreen1","setText text button");

        btn_1.setText(String.valueOf(gameWorld1.getInt_btn_1()));
        btn_2.setText(String.valueOf(gameWorld1.getInt_btn_2()));
        btn_3.setText(String.valueOf(gameWorld1.getInt_btn_3()));
        btn_4.setText(String.valueOf(gameWorld1.getInt_btn_4()));
        btn_5.setText(String.valueOf(gameWorld1.getInt_btn_5()));
        btn_6.setText(String.valueOf(gameWorld1.getInt_btn_6()));
    }




}
