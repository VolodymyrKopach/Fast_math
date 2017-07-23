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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by vova on 22.07.17.
 */

public class GameScreen3 implements Screen{

    private static final float BTN_POINT_X = 50;
    private static final float BTN_DOWN_POINT_Y = 140;
    private static final float vidstan_width = 20;
    private static final float vidstan_height = 30;
    private static final float btn_width = (720-(BTN_POINT_X*2+vidstan_width*2))/4;
    private static final float btn_height = btn_width;
    MyGameClass myGameClass;
    public GameWorld3 gameWorld3;

    public TextureAtlas textureAtlas_vg;
    public TextureRegion tr_fon, tr_X, tr_propusk;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_vg;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12, btn_13, btn_14, btn_15, btn_16, btn_17, btn_18, btn_19, btn_20, btn_21, btn_22, btn_23, btn_24;
    Skin skin;
    BitmapFont text_to_button;
    BitmapFont mGC_gs3_text_text_score, mGC_gs3_text_score, mGC_gs3_text_text_best_score, mGC_gs3_text_best_score, mGC_gs3_text_time;
    SpriteBatch mGC_spriteBatch;


    float screen_width = 720, screen_height = 1280;
    float tr_propusk_width, tr_propusk_height;
    float tr_propusk_x, tr_propusk_y, text_text_score_x, text_score_x, text_text_score_y, text_score_y, text_pryklad_x, text_pryklad_y, text_vidp_x, text_vidp_y, text_time_x, text_time_y, text_text_ne_prav_vidp_x, text_ne_prav_vidp_x, text_text_ne_prav_vidp_y, text_ne_prav_vidp_y;

    public GameScreen3(final MyGameClass myGameClass) {   // метод що запускається відразу
        this.myGameClass = myGameClass;

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
        stage_vg = new Stage(viewport);
        stage_vg.clear();
        Gdx.input.setInputProcessor(stage_vg);

        skin = new Skin();
        textureAtlas_vg = new TextureAtlas("texture/TextureAtlas.atlas");
        skin.addRegions(textureAtlas_vg);
        createTextButtons();

        gameWorld3 = new GameWorld3(this);

        Gdx.input.setCatchBackKey(true);
        variables_x_y();
        variables();

        Gdx.app.log("GameScreen1", "gw1 start game");


        tr_fon = new TextureRegion(textureAtlas_vg.findRegion("fon 1"));
        tr_X = new TextureRegion(textureAtlas_vg.findRegion("x"));
        tr_propusk = new TextureRegion(textureAtlas_vg.findRegion("znak pytanya"));
        text_to_button = new BitmapFont();
        //  myGameClass.bannerAdShow();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameWorld3.timer_game(delta);

        if (gameWorld3.bool_timer_wait_start){
            gameWorld3.timer_wait(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {    //  спрацьовує коли нажато Back
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        mGC_gs3_text_text_score.draw(mGC_spriteBatch, "Score: ", text_text_score_x, text_text_score_y);
        mGC_gs3_text_score.draw(mGC_spriteBatch, gameWorld3.getString_score(), text_score_x, text_score_y);
        mGC_gs3_text_time.draw(mGC_spriteBatch, gameWorld3.getTimer_game(), text_text_ne_prav_vidp_x, text_text_ne_prav_vidp_y);
     //   mGC_gs3_text_text_best_score.draw(mGC_spriteBatch, "BS:", text_text_score_x, text_text_score_y);              //Дороблю
     //   mGC_gs3_text_best_score.draw(mGC_spriteBatch, gameWorld3.getString_score(), text_score_x, text_score_y);      //Дороблю


        mGC_spriteBatch.end();

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

    void updateButtonText(GameWorld3 gameWorld3) {
        Gdx.app.log("tag", "update");
        // Gdx.app.log("tag", "null " + gameWorld.getInt_btn_1()); А для чого це?
        btn_1.setText(String.valueOf(gameWorld3.getInt_btn_1()));
        btn_2.setText(String.valueOf(gameWorld3.getInt_btn_2()));
        btn_3.setText(String.valueOf(gameWorld3.getInt_btn_3()));
        btn_4.setText(String.valueOf(gameWorld3.getInt_btn_4()));
        btn_5.setText(String.valueOf(gameWorld3.getInt_btn_5()));
        btn_6.setText(String.valueOf(gameWorld3.getInt_btn_6()));
        btn_7.setText(String.valueOf(gameWorld3.getInt_btn_7()));
        btn_8.setText(String.valueOf(gameWorld3.getInt_btn_8()));
        btn_9.setText(String.valueOf(gameWorld3.getInt_btn_9()));
        btn_10.setText(String.valueOf(gameWorld3.getInt_btn_10()));
        btn_11.setText(String.valueOf(gameWorld3.getInt_btn_11()));
        btn_12.setText(String.valueOf(gameWorld3.getInt_btn_12()));
        btn_13.setText(String.valueOf(gameWorld3.getInt_btn_13()));
        btn_14.setText(String.valueOf(gameWorld3.getInt_btn_14()));
        btn_15.setText(String.valueOf(gameWorld3.getInt_btn_15()));
        btn_16.setText(String.valueOf(gameWorld3.getInt_btn_16()));
        btn_17.setText(String.valueOf(gameWorld3.getInt_btn_17()));
        btn_18.setText(String.valueOf(gameWorld3.getInt_btn_18()));
        btn_19.setText(String.valueOf(gameWorld3.getInt_btn_19()));
        btn_20.setText(String.valueOf(gameWorld3.getInt_btn_20()));
        btn_21.setText(String.valueOf(gameWorld3.getInt_btn_21()));
        btn_22.setText(String.valueOf(gameWorld3.getInt_btn_22()));
        btn_23.setText(String.valueOf(gameWorld3.getInt_btn_23()));
        btn_24.setText(String.valueOf(gameWorld3.getInt_btn_24()));
    }


    public void variables() {    // ініціалізація переміних і т.д.
        mGC_spriteBatch = myGameClass.spriteBatch;

        mGC_gs3_text_time = myGameClass.gs3_text_time;
        mGC_gs3_text_text_score = myGameClass.gs3_text_text_score;
        mGC_gs3_text_score = myGameClass.gs3_text_score;
        mGC_gs3_text_text_best_score = myGameClass.gs3_text_text_best_score;
        mGC_gs3_text_best_score = myGameClass.gs3_text_best_score;

    }

    public void variables_x_y() {   // налаштування значень Х і У для прорисовки
        tr_propusk_width = 90;
        tr_propusk_height = 110;

        //   tr_propusk_x = gameWorld1.getInt_tr_propusk_x();
        tr_propusk_y = 900;

        text_pryklad_x = 75;
        text_vidp_x = tr_propusk_x; // Буде залежати від того якої частини приклада не буде вистачати
        text_text_ne_prav_vidp_x = 20;
        text_ne_prav_vidp_x = text_text_ne_prav_vidp_x + 140;
        text_time_x = screen_width / 2 - 6;
        text_text_score_x = screen_width - 165;
        text_score_x = text_text_score_x + 126;

        text_pryklad_y = 980;
        text_vidp_y = text_pryklad_y;
        text_text_ne_prav_vidp_y = screen_height - 50;
        text_ne_prav_vidp_y = text_text_ne_prav_vidp_y + 4;
        text_time_y = screen_height - 50;
        text_text_score_y = text_ne_prav_vidp_y;
        text_score_y = text_text_score_y + 4;


    }

 /*   public void game_level(){
        if(gameWorld1.float_timer < 0){
            gameWorld1.float_timer = 10;
            myGameClass.setScreen(new RestartScreen(myGameClass));
            Gdx.app.log("log","good");
        }
    } */


    public void createTextButtons() {   // налаштування кнопок
        Gdx.app.log("GameScreen3", "create text button");
        btn_1 = drawButton("btn krug press", 1);
        btn_2 = drawButton("btn krug press", 2);
        btn_3 = drawButton("btn krug press", 3);
        btn_4 = drawButton("btn krug press", 4);
        btn_5 = drawButton("btn krug press", 5);
        btn_6 = drawButton("btn krug press", 6);

    }

    private float getButtonX(int position) {
        switch (position) {
            case 1:
                return BTN_POINT_X;
            case 2:
                return BTN_POINT_X + btn_width + vidstan_width;
            case 3:
                return BTN_POINT_X + btn_width * 2 + vidstan_width * 2;
            case 4:
                return BTN_POINT_X;
            case 5:
                return BTN_POINT_X + btn_width + vidstan_width;
            case 6:
                return BTN_POINT_X + btn_width * 2 + vidstan_width * 2;
            case 7:
                return BTN_POINT_X;
            case 8:
                return BTN_POINT_X + btn_width + vidstan_width;
            case 9:
                return BTN_POINT_X + btn_width * 2 + vidstan_width * 2;
            case 10:
                return BTN_POINT_X;
            case 11:
                return BTN_POINT_X + btn_width + vidstan_width;
            case 12:
                return BTN_POINT_X + btn_width * 2 + vidstan_width * 2;
            case 13:
                return BTN_POINT_X;
            case 14:
                return BTN_POINT_X + btn_width + vidstan_width;
            case 15:
                return BTN_POINT_X + btn_width * 2 + vidstan_width * 2;
            case 16:
                return BTN_POINT_X;
            case 17:
                return BTN_POINT_X + btn_width + vidstan_width;
            case 18:
                return BTN_POINT_X + btn_width * 2 + vidstan_width * 2;
            case 19:
                return BTN_POINT_X;
            case 20:
                return BTN_POINT_X + btn_width + vidstan_width;
            case 21:
                return BTN_POINT_X + btn_width * 2 + vidstan_width * 2;
            case 22:
                return BTN_POINT_X;
            case 23:
                return BTN_POINT_X + btn_width + vidstan_width;
            case 24:
                return BTN_POINT_X + btn_width * 2 + vidstan_width * 2;
        }
        return 0;
    }

    private float getButtonY(int position) {
        switch (position) {
            case 1:
                return BTN_DOWN_POINT_Y + btn_height*5 + vidstan_height*5;
            case 2:
                return BTN_DOWN_POINT_Y + btn_height*5 + vidstan_height*5;
            case 3:
                return BTN_DOWN_POINT_Y + btn_height*5 + vidstan_height*5;
            case 4:
                return BTN_DOWN_POINT_Y + btn_height*5 + vidstan_height*5;
            case 5:
                return BTN_DOWN_POINT_Y + btn_height*4 + vidstan_height*4;
            case 6:
                return BTN_DOWN_POINT_Y + btn_height*4 + vidstan_height*4;
            case 7:
                return BTN_DOWN_POINT_Y + btn_height*4 + vidstan_height*4;
            case 8:
                return BTN_DOWN_POINT_Y + btn_height*4 + vidstan_height*4;
            case 9:
                return BTN_DOWN_POINT_Y + btn_height*3 + vidstan_height*3;
            case 10:
                return BTN_DOWN_POINT_Y + btn_height*3 + vidstan_height*3;
            case 11:
                return BTN_DOWN_POINT_Y + btn_height*3 + vidstan_height*3;
            case 12:
                return BTN_DOWN_POINT_Y + btn_height*3 + vidstan_height*3;
            case 13:
                return BTN_DOWN_POINT_Y + btn_height*2 + vidstan_height*2;
            case 14:
                return BTN_DOWN_POINT_Y + btn_height*2 + vidstan_height*2;
            case 15:
                return BTN_DOWN_POINT_Y + btn_height*2 + vidstan_height*2;
            case 16:
                return BTN_DOWN_POINT_Y + btn_height*2 + vidstan_height*2;
            case 17:
                return BTN_DOWN_POINT_Y + btn_height + vidstan_height*2;
            case 18:
                return BTN_DOWN_POINT_Y + btn_height + vidstan_height;
            case 19:
                return BTN_DOWN_POINT_Y + btn_height + vidstan_height;
            case 20:
                return BTN_DOWN_POINT_Y + btn_height + vidstan_height;
            case 21:
                return BTN_DOWN_POINT_Y + btn_height + vidstan_height;
            case 22:
                return BTN_DOWN_POINT_Y;
            case 23:
                return BTN_DOWN_POINT_Y;
            case 24:
                return BTN_DOWN_POINT_Y;

        }
        return 0;
    }

    //Цей метод створює кожну кнопку по черзі
    TextButton drawButton(String down, int position) {

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("btn krug");
        style.down = skin.getDrawable(down);
        style.font = myGameClass.gs1_text_btn;
        TextButton textButton = new TextButton("", style);
        textButton.setSize(btn_width, btn_height);
        stage_vg.addActor(textButton);
        final TextButton finalTextButton = textButton;
        textButton.setPosition(getButtonX(position), getButtonY(position));
        textButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               // gameWorld3.setString_input(String.valueOf(finalTextButton.getText()));
               // gameWorld3.answer();
            }
        });
        return textButton;
    }
}
