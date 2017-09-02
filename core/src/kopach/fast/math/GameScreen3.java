package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

public class GameScreen3 implements Screen {
    private static final float BTN_1_X = 50;
    private static final float BTN_1_Y = 200;
    private static final float vidstan_width = 40;
    //   private static final float vidstan_height = 50;
    private static final float btn_width = 173;
    private static final float btn_height = 173;
    MyGameClass myGameClass;
    public GameWorld3 gameWorld3;

    public TextureAtlas textureAtlas;
    public TextureRegion tr_fon, tr_X, tr_propusk, tr_screen_replay;
    ReplayDialog replay;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage;

    public TextButton btn_less, btn_equal, btn_greater;
    Skin skin;
    BitmapFont font_btn;
    public BitmapFont text_score_font, score_font, text_best_score_font, best_score_font, znak_font, pryklad_font, vidp_font, time_font, input_znak_font;
    SpriteBatch spriteBatch;

    float screen_width = 720, screen_height = 1280;
    float tr_propusk_width, tr_propusk_height;
    float replay_score_value_x, replay_score_value_y, replay_best_score_value_x, replay_best_score_value_y, text_text_best_score_x, text_text_best_score_y, text_best_score_x, text_best_score_y, tr_propusk_x, tr_propusk_y, text_text_score_x, text_score_x, text_text_score_y, text_score_y, text_vidp_x, text_vidp_y, text_time_x, text_time_y, text_left_znak_x, text_left_znak_y, text_right_znak_x, text_right_znak_y, text_left_number_1_x, text_left_number_1_y, text_left_number_2_x, text_left_number_2_y, text_right_number_1_x, text_right_number_1_y, text_right_number_2_x, text_right_number_2_y, text_input_znak_x, text_input_znak_y;

    public boolean bool_draw_replay_btn;

    public GameScreen3(final MyGameClass myGameClass) {   // метод що запускається відразу
        this.myGameClass = myGameClass;

        replay = new ReplayDialog();
        replay.setListener(new ReplayDialog.ReplayListener() {
            @Override
            void onReplay() {
                gameWorld3.int_score = 0;
                gameWorld3.game();
            }

            @Override
            void onBack() {
                myGameClass.setScreen(new MenuScreen(myGameClass));
            }
        });
        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        spriteBatch = new SpriteBatch();

        skin = new Skin();
        textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");
        skin.addRegions(textureAtlas);

        loadFont();

        gameWorld3 = new GameWorld3(this);

        Gdx.input.setCatchBackKey(true);

        variablesXY();
        actionVariablesXY();
        createTextButtons();

        Gdx.app.log("GameScreen3", "gw2 start game");


        tr_fon = new TextureRegion(textureAtlas.findRegion("fon"));
        tr_X = new TextureRegion(textureAtlas.findRegion("x"));
        tr_propusk = new TextureRegion(textureAtlas.findRegion("znak pytanya"));
        tr_screen_replay = new TextureRegion(textureAtlas.findRegion("screen replay"));
        //  myGameClass.bannerAdShow();

        MyPreference.setActiveGameAtTheMoment("game 3");
    }

    public void show() {

    }


    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        actionVariablesXY();

        if (gameWorld3.bool_timer_game) {
            gameWorld3.game_timer(delta);
        }

        if (gameWorld3.bool_timer_wait_time_out) {
            gameWorld3.timer_wait_time_out(delta);
        }
        if (gameWorld3.bool_timer_wait_answer_right) {
            gameWorld3.timer_wait_answer_right(delta);
        }
        if (gameWorld3.bool_timer_wait_answer_wrong) {
            gameWorld3.timer_wait_answer_wrong(delta);
        }

        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {    //  спрацьовує коли нажато Back
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        text_score_font.draw(spriteBatch, "Score: ", text_text_score_x, text_text_score_y);
        score_font.draw(spriteBatch, gameWorld3.getString_score(), text_score_x, text_score_y);
        text_best_score_font.draw(spriteBatch, "BS:", text_text_best_score_x, text_text_best_score_y);
        best_score_font.draw(spriteBatch, MyPreference.getBSGame3() + "", text_best_score_x, text_best_score_y);
        znak_font.draw(spriteBatch, gameWorld3.getString_left_znak(), text_left_znak_x, text_left_znak_y);
        znak_font.draw(spriteBatch, gameWorld3.getString_right_znak(), text_right_znak_x, text_right_znak_y);
        pryklad_font.draw(spriteBatch, String.valueOf(gameWorld3.getInt_left_number_1()), text_left_number_1_x, text_left_number_1_y);
        pryklad_font.draw(spriteBatch, String.valueOf(gameWorld3.getInt_left_number_2()), text_left_number_2_x, text_left_number_2_y);
        pryklad_font.draw(spriteBatch, String.valueOf(gameWorld3.getInt_right_number_1()), text_right_number_1_x, text_right_number_1_y);
        pryklad_font.draw(spriteBatch, String.valueOf(gameWorld3.getInt_right_number_2()), text_right_number_2_x, text_right_number_2_y);
        vidp_font.draw(spriteBatch, gameWorld3.getString_input(), text_vidp_x, text_vidp_y);
        time_font.draw(spriteBatch, gameWorld3.getInt_timer() + "", text_time_x, text_time_y);

        if (gameWorld3.bool_input) {
            input_znak_font.draw(spriteBatch, gameWorld3.getString_input(), text_input_znak_x, text_input_znak_y);

        } else
            spriteBatch.draw(tr_propusk, tr_propusk_x, tr_propusk_y, tr_propusk_width, tr_propusk_height);
        //TODO доробити збереження найкращого рахунку і виведенння знаку
        if (replay.isShow()) {
            replay.render(spriteBatch, gameWorld3.int_score, 0);
        } else {
            stage.act(delta);
            stage.draw();
            Gdx.input.setInputProcessor(stage);
        }
        spriteBatch.end();
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
        font_btn.dispose();
        text_score_font.dispose();
        score_font.dispose();
        text_best_score_font.dispose();
        best_score_font.dispose();
        znak_font.dispose();
        pryklad_font.dispose();
        vidp_font.dispose();
        time_font.dispose();
        spriteBatch.dispose();

    }

    void loadFont() {
        text_score_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_score_font.getData().setScale(0.5f, 0.5f);

        score_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        score_font.getData().setScale(0.6f, 0.6f);

        text_best_score_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_best_score_font.getData().setScale(0.5f, 0.5f);

        best_score_font = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
        best_score_font.getData().setScale(0.6f, 0.6f);

        time_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        time_font.getData().setScale(0.7f, 0.7f);

        pryklad_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        pryklad_font.getData().setScale(1.6f, 1.6f);

        znak_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        znak_font.getData().setScale(1.5f, 1.5f);

        vidp_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        vidp_font.getData().setScale(1.7f, 1.7f);

        input_znak_font = new BitmapFont(Gdx.files.internal("bitmapfont/white bold 70.fnt"), Gdx.files.internal("bitmapfont/white bold 70.png"), false);
        input_znak_font.getData().setScale(3.1f, 3.1f);

        font_btn = new BitmapFont(Gdx.files.internal("bitmapfont/white bold 70.fnt"), Gdx.files.internal("bitmapfont/white bold 70.png"), false);
        font_btn.getData().setScale(0.8f, 0.8f);
    }

    public void variablesXY() {   // налаштування значень Х і У для прорисовки

        tr_propusk_width = 90;
        tr_propusk_height = 110;
        tr_propusk_x = screen_width / 2 - tr_propusk_width / 2;
        tr_propusk_y = 900;

        text_text_best_score_x = 20;
        text_best_score_x = 90;
        text_vidp_x = tr_propusk_x; // Буде залежати від того якої частини приклада не буде вистачати
        text_time_x = 20;
        text_time_x = screen_width / 2 - 27;
        text_text_score_x = screen_width - 165;
        text_score_x = text_text_score_x + 126;
        text_input_znak_x = 0;
        replay_score_value_x = screen_width / 2 - 35;
        replay_best_score_value_x = screen_width / 2 - 35;

        text_text_best_score_y = screen_height - 40;
        text_best_score_y = text_text_best_score_y + 4;
        text_vidp_y = 300;
        text_time_y = screen_height - 50;
        text_text_score_y = screen_height - 40;
        text_score_y = text_text_score_y + 4;
        text_input_znak_y = 994;
        replay_score_value_y = 600;
        replay_best_score_value_y = 760;

    }

    void actionVariablesXY() {

        text_left_number_1_x = gameWorld3.int_left_pryklad_1_position_x;
        text_left_number_2_x = text_left_number_1_x;
        text_right_number_1_x = gameWorld3.int_right_pryklad_1_position_x;
        text_right_number_2_x = text_right_number_1_x;
        text_left_znak_x = text_left_number_1_x - 50;
        text_right_znak_x = text_right_number_2_x - 50;

        text_left_number_1_y = 1050;
        text_left_number_2_y = 930;
        text_right_number_1_y = text_left_number_1_y;
        text_right_number_2_y = text_left_number_2_y;
        text_left_znak_y = 1000;
        text_right_znak_y = text_left_znak_y;
    }


    public void createTextButtons() {   // налаштування кнопок
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
        style.font = time_font;
        TextButton textButton = new TextButton(" ", style);
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
                gameWorld3.setString_input(String.valueOf(finalTextButton.getText()));
                gameWorld3.answer(answer);
            }
        });
        return textButton;
    }

    public float getTextWidth(BitmapFont font, String text) {
        return new GlyphLayout(font, text).width;
    }
}
