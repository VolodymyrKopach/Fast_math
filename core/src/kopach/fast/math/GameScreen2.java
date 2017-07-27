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
    BitmapFont text_text_score, text_score, text_text_best_score, text_best_score, text_znak, text_pryklad, text_vidp, text_time;
    SpriteBatch spriteBatch;


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

        spriteBatch = new SpriteBatch();

        skin = new Skin();
        textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");
        skin.addRegions(textureAtlas);
        createTextButtons();

        gameWorld2 = new GameWorld2(this);

        Gdx.input.setCatchBackKey(true);
        variables_x_y();

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

        spriteBatch.draw(tr_propusk, tr_propusk_x, tr_propusk_y, tr_propusk_width, tr_propusk_height);
        text_text_score.draw(spriteBatch, "Score: ", text_text_score_x, text_text_score_y);
        text_score.draw(spriteBatch, gameWorld2.getString_score(), text_score_x, text_score_y);
        text_text_best_score.draw(spriteBatch, "BS:", text_text_best_score_x, text_text_best_score_y);
        text_best_score.draw(spriteBatch, gameWorld2.getString_best_score_this_level(), text_best_score_x, text_best_score_y);
        text_znak.draw(spriteBatch, gameWorld2.getString_left_znak(), text_left_znak_x, text_left_znak_y);
        text_znak.draw(spriteBatch, gameWorld2.getString_right_znak(), text_right_znak_x, text_right_znak_y);
        text_pryklad.draw(spriteBatch, String.valueOf(gameWorld2.getInt_left_number_1()), text_left_number_1_x, text_left_number_1_y);
        text_pryklad.draw(spriteBatch, String.valueOf(gameWorld2.getInt_left_number_2()), text_left_number_2_x, text_left_number_2_y);
        text_pryklad.draw(spriteBatch, String.valueOf(gameWorld2.getInt_right_number_1()), text_right_number_1_x, text_right_number_1_y);
        text_pryklad.draw(spriteBatch, String.valueOf(gameWorld2.getInt_right_number_2()), text_right_number_2_x, text_right_number_2_y);
        text_vidp.draw(spriteBatch, gameWorld2.getString_input(), text_vidp_x, text_vidp_y);
        text_time.draw(spriteBatch, gameWorld2.getString_timer(), text_time_x, text_time_y);
        spriteBatch.end();


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
        textureAtlas.dispose();
        stage.dispose();
        skin.dispose();
        text_to_button.dispose();
        text_text_score.dispose();
        text_score.dispose();
        text_text_best_score.dispose();
        text_best_score.dispose();
        text_znak.dispose();
        text_pryklad.dispose();
        text_vidp.dispose();
        text_time.dispose();
        spriteBatch.dispose();

    }

    public void variables_x_y() {   // налаштування значень Х і У для прорисовки
        text_text_score = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_score = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        text_text_best_score = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_best_score = new BitmapFont(Gdx.files.internal("bitmapfont/red bold 70.fnt"), Gdx.files.internal("bitmapfont/red bold 70.png"), false);
        text_time = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_pryklad = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_znak = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_vidp = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_text_score.getData().setScale(0.5f, 0.5f);
        text_score.getData().setScale(0.6f, 0.6f);
        text_text_best_score.getData().setScale(0.5f, 0.5f);
        text_best_score.getData().setScale(0.6f, 0.6f);
        text_time.getData().setScale(0.7f, 0.7f);
        text_pryklad.getData().setScale(1.6f, 1.6f);
        text_znak.getData().setScale(1.5f, 1.5f);
        text_vidp.getData().setScale(1.7f, 1.7f);


        tr_propusk_width = 90;  tr_propusk_height = 110;

        tr_propusk_x = screen_width/2-tr_propusk_width/2;  tr_propusk_y = 900;

        text_text_best_score_x = 20;  text_best_score_x = 90;
        text_left_znak_x = 20;
        text_right_znak_x = 450;
        text_left_number_1_x = 70;  text_left_number_2_x = text_left_number_1_x;
        text_right_number_1_x = 500;  text_right_number_2_x = text_right_number_1_x;
        text_vidp_x = tr_propusk_x; // Буде залежати від того якої частини приклада не буде вистачати
        text_time_x = 20;
        text_time_x = screen_width / 2 - 6;
        text_text_score_x = screen_width - 165;
        text_score_x = text_text_score_x + 126;

        text_text_best_score_y = screen_height - 40;  text_best_score_y = text_text_best_score_y + 4;
        text_left_znak_y = 1000;
        text_right_znak_y = text_left_znak_y;
        text_left_number_1_y = 1050;  text_left_number_2_y = 930;
        text_right_number_1_y = text_left_number_1_y;  text_right_number_2_y = text_left_number_2_y;
        text_vidp_y = 300;
        text_time_y = screen_height - 50;
        text_time_y = screen_height - 50;
        text_text_score_y = screen_height - 40;
        text_score_y = text_text_score_y + 4;


    }


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
            case 2:
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
        style.font = text_time;
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
