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
 * Created by vova on 20.07.17.
 */

public class GameScreen2 implements Screen{
    private static final float BTN_1_X = 50;
    private static final float BTN_1_Y = 200;
    private static final float vidstan_width = 40;
 //   private static final float vidstan_height = 50;
    private static final float btn_width = 173;
    private static final float btn_height = 173;
    MyGameClass myGameClass;
    public GameWorld2 gameWorld2;

    public TextureAtlas textureAtlas;
    public TextureRegion tr_fon, tr_X, tr_propusk;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage;

    public TextButton btn_less, btn_equal, btn_greater;
    Skin skin;
    BitmapFont text_to_button;
    BitmapFont mGC_gs2_text_text_score, mGC_gs2_text_score, mGC_gs2_text_text_best_score, mGC_gs2_text_best_score, mGC_gs2_text_znak, mGC_gs2_text_pryklad, mGC_gs2_text_vidp, mGC_gs2_text_time;
    SpriteBatch mGC_spriteBatch;


    float screen_width = 720, screen_height = 1280;
    float tr_propusk_width, tr_propusk_height;
    float text_text_best_score_x, text_text_best_score_y, text_best_score_x, text_best_score_y, tr_propusk_x, tr_propusk_y, text_text_score_x, text_score_x, text_text_score_y, text_score_y, text_vidp_x, text_vidp_y, text_time_x, text_time_y, text_left_znak_x, text_left_znak_y, text_right_znak_x, text_right_znak_y, text_left_number_1_x, text_left_number_1_y, text_left_number_2_x, text_left_number_2_y, text_right_number_1_x, text_right_number_1_y, text_right_number_2_x, text_right_number_2_y;

    public GameScreen2(final MyGameClass myGameClass) {   // метод що запускається відразу
        this.myGameClass = myGameClass;

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
        stage = new Stage(viewport);
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");
        skin.addRegions(textureAtlas);
        createTextButtons();

        gameWorld2 = new GameWorld2(this);

        Gdx.input.setCatchBackKey(true);
        variables_x_y();
        variables();

        Gdx.app.log("GameScreen2", "gw2 start game");


        tr_fon = new TextureRegion(textureAtlas.findRegion("fon 1"));
        tr_X = new TextureRegion(textureAtlas.findRegion("x"));
        tr_propusk = new TextureRegion(textureAtlas.findRegion("znak pytanya"));
        text_to_button = new BitmapFont();
        //  myGameClass.bannerAdShow();
    }

    public void show() {

    }


    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameWorld2.setString_timer(delta);

        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {    //  спрацьовує коли нажато Back
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        if (gameWorld2.getBoolean_X()) {
            // myGameClass.spriteBatch.draw(tr_X, tr_X_x, tr_X_y,X_width, X_height);
        }

        mGC_spriteBatch.draw(tr_propusk, tr_propusk_x, tr_propusk_y, tr_propusk_width, tr_propusk_height);
        mGC_gs2_text_text_score.draw(mGC_spriteBatch, "Score: ", text_text_score_x, text_text_score_y);
        mGC_gs2_text_score.draw(mGC_spriteBatch, gameWorld2.getString_score(), text_score_x, text_score_y);
        mGC_gs2_text_text_best_score.draw(mGC_spriteBatch, "BS:", text_text_best_score_x, text_text_best_score_y);
        mGC_gs2_text_best_score.draw(mGC_spriteBatch, gameWorld2.getString_best_score_this_level(), text_best_score_x, text_best_score_y);
        mGC_gs2_text_znak.draw(mGC_spriteBatch, gameWorld2.getString_left_znak(), text_left_znak_x, text_left_znak_y);
        mGC_gs2_text_znak.draw(mGC_spriteBatch, gameWorld2.getString_right_znak(), text_right_znak_x, text_right_znak_y);
        mGC_gs2_text_pryklad.draw(mGC_spriteBatch, String.valueOf(gameWorld2.getInt_left_number_1()), text_left_number_1_x, text_left_number_1_y);
        mGC_gs2_text_pryklad.draw(mGC_spriteBatch, String.valueOf(gameWorld2.getInt_left_number_2()), text_left_number_2_x, text_left_number_2_y);
        mGC_gs2_text_pryklad.draw(mGC_spriteBatch, String.valueOf(gameWorld2.getInt_right_number_1()), text_right_number_1_x, text_right_number_1_y);
        mGC_gs2_text_pryklad.draw(mGC_spriteBatch, String.valueOf(gameWorld2.getInt_right_number_2()), text_right_number_2_x, text_right_number_2_y);
        mGC_gs2_text_vidp.draw(mGC_spriteBatch, gameWorld2.getString_input(), text_vidp_x, text_vidp_y);
        mGC_gs2_text_time.draw(mGC_spriteBatch, gameWorld2.getString_timer(), text_time_x, text_time_y);
        mGC_spriteBatch.end();


        stage.act(delta);
        stage.draw();
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

    public void variables() {    // ініціалізація переміних і т.д.
        mGC_spriteBatch = myGameClass.spriteBatch;

        mGC_gs2_text_time = myGameClass.gs2_text_time;
        mGC_gs2_text_text_score = myGameClass.gs2_text_text_score;
        mGC_gs2_text_score = myGameClass.gs2_text_score;
        mGC_gs2_text_text_best_score = myGameClass.gs2_text_text_best_score;
        mGC_gs2_text_best_score = myGameClass.gs2_text_best_score;
        mGC_gs2_text_znak = myGameClass.gs2_text_znak;
        mGC_gs2_text_pryklad = myGameClass.gs2_text_pryklad;
        mGC_gs2_text_vidp = myGameClass.gs2_text_vidp;
    }

    public void variables_x_y() {   // налаштування значень Х і У для прорисовки
        tr_propusk_width = 90;  tr_propusk_height = 110;

        tr_propusk_x = screen_width/2-tr_propusk_width/2;  tr_propusk_y = 900;

        text_text_best_score_x = 20;  text_best_score_x = 90;
        text_left_znak_x = 100;
        text_right_znak_x = 450;
        text_left_number_1_x = 150;  text_left_number_2_x = text_left_number_1_x;
        text_right_number_1_x = 500;  text_right_number_2_x = text_right_number_1_x;
        text_vidp_x = tr_propusk_x; // Буде залежати від того якої частини приклада не буде вистачати
        text_time_x = 20;
        text_time_x = screen_width / 2 - 6;
        text_text_score_x = screen_width - 165;
        text_score_x = text_text_score_x + 126;

        text_text_best_score_y = 1240;  text_best_score_y = text_text_best_score_y + 4;
        text_left_znak_y = 1000;
        text_right_znak_y = text_left_znak_y;
        text_left_number_1_y = 1050;  text_left_number_2_y = 930;
        text_right_number_1_y = text_left_number_1_y;  text_right_number_2_y = text_left_number_2_y;
        text_vidp_y = 300;
        text_time_y = screen_height - 50;
        text_time_y = screen_height - 50;
        text_text_score_y = screen_height - 60;
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
        btn_less = drawButton("btn menjshe", "btn menjshe press", 1, "<");
        btn_equal = drawButton("btn dorivnjue", "btn dorivnjue press", 2, "=");
        btn_greater = drawButton("btn biljshe", "btn biljshe press", 3, ">");
    }

    private float getButtonX(int position) {
        switch (position) {
            case 1:
                return BTN_1_X;
            case 2:
                return BTN_1_X + btn_width + vidstan_width;
            case 3:
                return BTN_1_X + btn_width * 2 + vidstan_width * 2;

        }
        return 0;
    }

    private float getButtonY(int position) {
        switch (position) {
            case 1:
                return BTN_1_Y;
            case 2:
                return BTN_1_Y;
            case 3:
                return BTN_1_Y;

        }
        return 0;
    }

    //Цей метод створює кожну кнопку по черзі
    TextButton drawButton(String up, String down, int position, final String answer) {

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable(up);
        style.down = skin.getDrawable(down);
        style.font = myGameClass.gs1_text_btn; // Поки що любий текст, так як тексту на кнопці не буде
        TextButton textButton = new TextButton("", style);
        textButton.setSize(btn_width, btn_height);
        stage.addActor(textButton);
        final TextButton finalTextButton = textButton;
        textButton.setPosition(getButtonX(position), getButtonY(position));
        textButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWorld2.setString_input(String.valueOf(finalTextButton.getText()));
                gameWorld2.answer(answer);
            }
        });
        return textButton;
    }
}
