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
 * Created by vova on 17.05.17.
 */

public class GameScreen1 implements Screen {
    private static final float BTN_1_X = 50;
    private static final float BTN_4_Y = 140;
    private static final float vidstan_width = 40;
    private static final float vidstan_height = 50;
    private static final float btn_width = 173;
    private static final float btn_height = 173;
    MyGameClass myGameClass;
    public GameWorld1 gameWorld1;

    boolean FLAG_SHOW_QUESTION_MARK = true;

    public TextureAtlas textureAtlas_vg;
    public TextureRegion tr_fon, tr_X, tr_propusk;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_vg;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6;
    Skin skin;
    BitmapFont text_to_button;
    BitmapFont mGC_gs1_text_text_score, mGC_gs1_text_score, mGC_gs1_text_text_best_score, mGC_gs1_text_best_score, mGC_gs1_text_pryklad, mGC_gs1_text_vidp_right, mGC_gs1_text_vidp_wrong, mGC_gs1_text_time;
    SpriteBatch mGC_spriteBatch;


    float screen_width = 720, screen_height = 1280;
    float tr_propusk_width, tr_propusk_height;
    float text_text_best_score_x, text_text_best_score_y, text_best_score_x, text_best_score_y, tr_propusk_x, tr_propusk_y, text_text_score_x, text_score_x, text_text_score_y, text_score_y, text_pryklad_x, text_pryklad_y, text_vidp_x, text_vidp_y, text_time_x, text_time_y, text_text_ne_prav_vidp_x, text_ne_prav_vidp_x, text_text_ne_prav_vidp_y, text_ne_prav_vidp_y;

    public GameScreen1(final MyGameClass myGameClass) {   // метод що запускається відразу
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

        gameWorld1 = new GameWorld1(this);

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

        gameWorld1.timer_game(delta);

        if (gameWorld1.bool_timer_wait_start) {
            gameWorld1.timer_wait(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {    //  спрацьовує коли нажато Back
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        if (FLAG_SHOW_QUESTION_MARK)
            mGC_spriteBatch.draw(tr_propusk, gameWorld1.getInt_tr_propusk_x(), tr_propusk_y, tr_propusk_width, tr_propusk_height);
        mGC_gs1_text_text_score.draw(mGC_spriteBatch, "Score: ", text_text_score_x, text_text_score_y);
        mGC_gs1_text_score.draw(mGC_spriteBatch, gameWorld1.getString_score(), text_score_x, text_score_y);
        mGC_gs1_text_text_best_score.draw(mGC_spriteBatch, "BS: ", text_text_best_score_x, text_text_best_score_y);
        mGC_gs1_text_best_score.draw(mGC_spriteBatch, gameWorld1.getString_best_score_this_level(), text_best_score_x, text_best_score_y);
        mGC_gs1_text_pryklad.draw(mGC_spriteBatch, gameWorld1.getString_to_screen(), text_pryklad_x, text_pryklad_y);
        mGC_gs1_text_time.draw(mGC_spriteBatch, gameWorld1.getTimer_game(), text_time_x, text_time_y);

        if (gameWorld1.bool_answer_right) {
            mGC_gs1_text_vidp_right.draw(mGC_spriteBatch, gameWorld1.getString_input(), gameWorld1.getInt_tr_propusk_x(), text_vidp_y);
        } else {
            mGC_gs1_text_vidp_wrong.draw(mGC_spriteBatch, gameWorld1.getString_input(), gameWorld1.getInt_tr_propusk_x(), text_vidp_y);
        }

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

    void updateButtonText(GameWorld1 gameWorld) {
        Gdx.app.log("tag", "update");
        // Gdx.app.log("tag", "null " + gameWorld.getInt_btn_1()); А для чого це?
        btn_1.setText(String.valueOf(gameWorld.getInt_btn_1()));
        btn_2.setText(String.valueOf(gameWorld.getInt_btn_2()));
        btn_3.setText(String.valueOf(gameWorld.getInt_btn_3()));
        btn_4.setText(String.valueOf(gameWorld.getInt_btn_4()));
        btn_5.setText(String.valueOf(gameWorld.getInt_btn_5()));
        btn_6.setText(String.valueOf(gameWorld.getInt_btn_6()));
    }


    public void variables() {    // ініціалізація переміних і т.д.
        mGC_spriteBatch = myGameClass.spriteBatch;

        mGC_gs1_text_time = myGameClass.gs1_text_time;
        mGC_gs1_text_text_score = myGameClass.gs1_text_text_score;
        mGC_gs1_text_score = myGameClass.gs1_text_score;
        mGC_gs1_text_text_best_score = myGameClass.gs1_text_text_best_score;
        mGC_gs1_text_best_score = myGameClass.gs1_text_best_score;
        mGC_gs1_text_pryklad = myGameClass.gs1_text_pryklad;
        mGC_gs1_text_vidp_right = myGameClass.gs1_text_vidp_right;
        mGC_gs1_text_vidp_wrong = myGameClass.gs1_text_vidp_wrong;
    }

    public void variables_x_y() {   // налаштування значень Х і У для прорисовки
        tr_propusk_width = 90;
        tr_propusk_height = 110;

        //   tr_propusk_x = gameWorld1.getInt_tr_propusk_x();
        tr_propusk_y = 900;

        text_text_best_score_x = 20;   text_best_score_x = 90;
        text_pryklad_x = 75;
        text_vidp_x = tr_propusk_x; // Буде залежати від того якої частини приклада не буде вистачати
      //  text_text_ne_prav_vidp_x = 20;
      //  text_ne_prav_vidp_x = text_text_ne_prav_vidp_x + 140;
        text_time_x = screen_width / 2 - 6;
        text_text_score_x = screen_width - 165;
        text_score_x = text_text_score_x + 126;

        text_text_best_score_y = screen_height - 40;  text_best_score_y = text_text_best_score_y + 4;
        text_pryklad_y = 980;
        text_vidp_y = text_pryklad_y;
      //  text_text_ne_prav_vidp_y = screen_height - 50;
      //  text_ne_prav_vidp_y = text_text_ne_prav_vidp_y + 4;
        text_time_y = screen_height - 50;
        text_text_score_y = screen_height - 40;
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
        Gdx.app.log("GameScreen1", "text button");
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
                return BTN_1_X;
            case 2:
                return BTN_1_X + btn_width + vidstan_width;
            case 3:
                return BTN_1_X + btn_width * 2 + vidstan_width * 2;
            case 4:
                return BTN_1_X;
            case 5:
                return BTN_1_X + btn_width + vidstan_width;
            case 6:
                return BTN_1_X + btn_width * 2 + vidstan_width * 2;
        }
        return 0;
    }

    private float getButtonY(int position) {
        switch (position) {
            case 1:
                return BTN_4_Y + btn_height + vidstan_height;
            case 2:
                return BTN_4_Y + btn_height + vidstan_height;
            case 3:
                return BTN_4_Y + btn_height + vidstan_height;
            case 4:
                return BTN_4_Y;
            case 5:
                return BTN_4_Y;
            case 6:
                return BTN_4_Y;
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
                gameWorld1.setString_input(String.valueOf(finalTextButton.getText()));
                gameWorld1.answer();
            }
        });
        return textButton;
    }
}
