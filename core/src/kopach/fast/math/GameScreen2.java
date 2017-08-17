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
    public TextureRegion tr_fon, tr_X, tr_propusk, tr_screen_replay;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage, stageReplay;

    public TextButton btn_less, btn_equal, btn_greater, btn_replay, btn_back, btn_function;
    Skin skin;
    BitmapFont font_btn;
    public BitmapFont replay_score_value_font, replay_best_score_value_font, text_score_font, score_font, text_best_score_font, best_score_font, znak_font, pryklad_font, vidp_font, time_font, input_znak_font;
    SpriteBatch spriteBatch;

    float screen_width = 720, screen_height = 1280;
    float tr_propusk_width, tr_propusk_height, tr_screen_replay_width, tr_screen_replay_height, btn_replay_width, btn_replay_height, btn_back_width, btn_back_height, btn_function_width, btn_function_height;
    float btn_replay_x, btn_replay_y, btn_function_x, btn_function_y, btn_back_x, btn_back_y, tr_screen_replay_x, tr_screen_replay_y, replay_score_value_x, replay_score_value_y, replay_best_score_value_x, replay_best_score_value_y, text_text_best_score_x, text_text_best_score_y, text_best_score_x, text_best_score_y, tr_propusk_x, tr_propusk_y, text_text_score_x, text_score_x, text_text_score_y, text_score_y, text_vidp_x, text_vidp_y, text_time_x, text_time_y, text_left_znak_x, text_left_znak_y, text_right_znak_x, text_right_znak_y, text_left_number_1_x, text_left_number_1_y, text_left_number_2_x, text_left_number_2_y, text_right_number_1_x, text_right_number_1_y, text_right_number_2_x, text_right_number_2_y, text_input_znak_x, text_input_znak_y;

    public boolean bool_draw_replay_btn;

    public GameScreen2(final MyGameClass myGameClass) {   // метод що запускається відразу
        this.myGameClass = myGameClass;

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
        stage = new Stage(viewport);
        stageReplay = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        spriteBatch = new SpriteBatch();

        skin = new Skin();
        textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");
        skin.addRegions(textureAtlas);

        loadFont();

        gameWorld2 = new GameWorld2(this);

        Gdx.input.setCatchBackKey(true);

        variablesXY();
        actionVariablesXY();
        createTextButtons();

        Gdx.app.log("GameScreen2", "gw2 start game");


        tr_fon = new TextureRegion(textureAtlas.findRegion("fon"));
        tr_X = new TextureRegion(textureAtlas.findRegion("x"));
        tr_propusk = new TextureRegion(textureAtlas.findRegion("znak pytanya"));
        tr_screen_replay = new TextureRegion(textureAtlas.findRegion("screen replay"));
        //  myGameClass.bannerAdShow();
    }

    public void show() {

    }


    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        actionVariablesXY();

        if (gameWorld2.bool_timer_game){gameWorld2.setString_timer(delta);}

        if (gameWorld2.bool_timer_wait_answer_right) {gameWorld2.timer_wait_answer_right(delta);}
        if (gameWorld2.bool_timer_wait_answer_wrong) {gameWorld2.timer_wait_answer_wrong(delta);}

        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {    //  спрацьовує коли нажато Back
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        text_score_font.draw(spriteBatch, "Score: ", text_text_score_x, text_text_score_y);
        score_font.draw(spriteBatch, gameWorld2.getString_score(), text_score_x, text_score_y);
        text_best_score_font.draw(spriteBatch, "BS:", text_text_best_score_x, text_text_best_score_y);
        best_score_font.draw(spriteBatch, String.valueOf(gameWorld2.getHighScore()), text_best_score_x, text_best_score_y);
        znak_font.draw(spriteBatch, gameWorld2.getString_left_znak(), text_left_znak_x, text_left_znak_y);
        znak_font.draw(spriteBatch, gameWorld2.getString_right_znak(), text_right_znak_x, text_right_znak_y);
        pryklad_font.draw(spriteBatch, String.valueOf(gameWorld2.getInt_left_number_1()), text_left_number_1_x, text_left_number_1_y);
        pryklad_font.draw(spriteBatch, String.valueOf(gameWorld2.getInt_left_number_2()), text_left_number_2_x, text_left_number_2_y);
        pryklad_font.draw(spriteBatch, String.valueOf(gameWorld2.getInt_right_number_1()), text_right_number_1_x, text_right_number_1_y);
        pryklad_font.draw(spriteBatch, String.valueOf(gameWorld2.getInt_right_number_2()), text_right_number_2_x, text_right_number_2_y);
        vidp_font.draw(spriteBatch, gameWorld2.getString_input(), text_vidp_x, text_vidp_y);
        time_font.draw(spriteBatch, gameWorld2.getString_timer(), text_time_x, text_time_y);

        if (gameWorld2.bool_input){
            input_znak_font.draw(spriteBatch, gameWorld2.getString_input(), text_input_znak_x, text_input_znak_y);

        }else spriteBatch.draw(tr_propusk, tr_propusk_x, tr_propusk_y, tr_propusk_width, tr_propusk_height);

        spriteBatch.end();

        stage.act(delta);
        stage.draw();

        stageReplay.getBatch().begin();
        if (gameWorld2.bool_replay){
            stageReplay.getBatch().draw(tr_screen_replay,tr_screen_replay_x, tr_screen_replay_y, tr_screen_replay_width, tr_screen_replay_height);
            replay_score_value_font.draw(stageReplay.getBatch(), gameWorld2.getString_score(), replay_score_value_x, replay_score_value_y);
            replay_best_score_value_font.draw(stageReplay.getBatch(), String.valueOf(gameWorld2.getHighScore()), replay_best_score_value_x, replay_best_score_value_y);
        }
        stageReplay.getBatch().end();

        if (gameWorld2.bool_replay){
            replay_true();
        }else {Gdx.input.setInputProcessor(stage);}
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

    void loadFont(){
        text_score_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_score_font.getData().setScale(0.5f, 0.5f);

        score_font= new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        score_font.getData().setScale(0.6f, 0.6f);

        text_best_score_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_best_score_font.getData().setScale(0.5f, 0.5f);

        best_score_font = new BitmapFont(Gdx.files.internal("bitmapfont/red bold 70.fnt"), Gdx.files.internal("bitmapfont/red bold 70.png"), false);
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

        replay_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        replay_score_value_font.getData().setScale(0.8f, 0.8f);

        replay_best_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
        replay_best_score_value_font.getData().setScale(0.9f, 0.9f);
    }

    public void variablesXY() {   // налаштування значень Х і У для прорисовки

        tr_propusk_width = 90;  tr_propusk_height = 110;
        tr_propusk_x = screen_width/2-tr_propusk_width/2;  tr_propusk_y = 900;
        tr_screen_replay_width = 720;
        tr_screen_replay_height = 1280;
        btn_replay_width = 170;
        btn_replay_height = 170;
        btn_function_width = 100;
        btn_function_height = 100;
        btn_back_width = 100;
        btn_back_height = 100;

        text_text_best_score_x = 20;  text_best_score_x = 90;
        text_vidp_x = tr_propusk_x; // Буде залежати від того якої частини приклада не буде вистачати
        text_time_x = 20;
        text_time_x = screen_width / 2 - 27;
        text_text_score_x = screen_width - 165;
        text_score_x = text_text_score_x + 126;
        text_input_znak_x = 0;
        tr_screen_replay_x = screen_width/2 - tr_screen_replay_width/2;
        btn_replay_x = screen_width/2 - btn_replay_width/2;
        btn_function_x = btn_replay_x + btn_replay_width + 70;
        btn_back_x = btn_replay_x - 70 - btn_back_width;
        replay_score_value_x = screen_width/2 - 35;
        replay_best_score_value_x = screen_width/2 - 35;

        text_text_best_score_y = screen_height - 40;  text_best_score_y = text_text_best_score_y + 4;
        text_vidp_y = 300;
        text_time_y = screen_height - 50;
        text_text_score_y = screen_height - 40;
        text_score_y = text_text_score_y + 4;
        text_input_znak_y = 994;
        tr_screen_replay_y = 10;
        replay_score_value_y = 600;
        replay_best_score_value_y = 760;
        btn_replay_y = 300;
        btn_back_y = 290;
        btn_function_y = 290;

    }

    void actionVariablesXY(){

        text_left_number_1_x = gameWorld2.int_left_pryklad_1_position_x;  text_left_number_2_x = text_left_number_1_x;
        text_right_number_1_x = gameWorld2.int_right_pryklad_1_position_x;  text_right_number_2_x = text_right_number_1_x;
        text_left_znak_x = text_left_number_1_x - 50;
        text_right_znak_x = text_right_number_2_x - 50;

        text_left_number_1_y = 1050;  text_left_number_2_y = 930;
        text_right_number_1_y = text_left_number_1_y;  text_right_number_2_y = text_left_number_2_y;
        text_left_znak_y = 1000;
        text_right_znak_y = text_left_znak_y;

        if (String.valueOf(gameWorld2.getHighScore()).length() == 2){
            replay_best_score_value_x = (screen_width/2 - getTextWidth(replay_best_score_value_font, String.valueOf(gameWorld2.getHighScore()))/2) - 20;
        }else if (String.valueOf(gameWorld2.getHighScore()).length() == 3){
            replay_best_score_value_x = (screen_width/2 - getTextWidth(replay_best_score_value_font, String.valueOf(gameWorld2.getHighScore()))/2) - 27;
        }

        if (String.valueOf(gameWorld2.getString_score()).length() == 2){
            replay_score_value_x = (screen_width/2 - getTextWidth(replay_best_score_value_font, String.valueOf(gameWorld2.getHighScore()))/2) - 20;
        }else if (String.valueOf(gameWorld2.getString_score()).length() == 3){
            replay_score_value_x = (screen_width/2 - getTextWidth(replay_best_score_value_font, String.valueOf(gameWorld2.getHighScore()))/2) - 27;
        }

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
                gameWorld2.setString_input(String.valueOf(finalTextButton.getText()));
                gameWorld2.answer(answer);
            }
        });
        return textButton;
    }

    public float getTextWidth(BitmapFont font, String text) {
        return new GlyphLayout(font, text).width;
    }


    void btnInReplay(){
        TextButton.TextButtonStyle btn_replay_style = new TextButton.TextButtonStyle();
        btn_replay_style.up = skin.getDrawable("btn replay");
        btn_replay_style.down = skin.getDrawable("btn replay press");
        btn_replay_style.font = font_btn;

        btn_replay = new TextButton(" ", btn_replay_style);
        btn_replay.setSize(btn_replay_width, btn_replay_height);
        btn_replay.setPosition(btn_replay_x, btn_replay_y);
        btn_replay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                replay_false();
                // Gdx.input.setInputProcessor(stage);
            }
        });

        TextButton.TextButtonStyle btn_back_style = new TextButton.TextButtonStyle();
        btn_back_style.up = skin.getDrawable("btn back");
        btn_back_style.down = skin.getDrawable("btn back press");
        btn_back_style.font = font_btn;

        btn_back = new TextButton(" ", btn_back_style);
        btn_back.setSize(btn_back_width, btn_back_height);
        btn_back.setPosition(btn_back_x, btn_back_y);
        btn_back.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                myGameClass.setScreen(new MenuScreen(myGameClass));
                // Gdx.input.setInputProcessor(stage);
            }
        });

        TextButton.TextButtonStyle btn_function_style = new TextButton.TextButtonStyle();
        btn_function_style.up = skin.getDrawable("btn function");
        btn_function_style.down = skin.getDrawable("btn function press");
        btn_function_style.font = font_btn;

        btn_function = new TextButton(" ", btn_function_style);
        btn_function.setSize(btn_function_width, btn_function_height);
        btn_function.setPosition(btn_function_x, btn_function_y);
        btn_function.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // Gdx.input.setInputProcessor(stage);
            }
        });
    }

    void replay_true(){
        if (bool_draw_replay_btn){
            Gdx.input.setInputProcessor(stageReplay);
            //  btn_1.setTouchable(Touchable.disabled);  btn_2.setTouchable(Touchable.disabled);  btn_3.setTouchable(Touchable.disabled);  btn_4.setTouchable(Touchable.disabled);  btn_5.setTouchable(Touchable.disabled);  btn_6.setTouchable(Touchable.disabled);
            btnInReplay();
            stageReplay.addActor(btn_replay);   stageReplay.addActor(btn_back);   stageReplay.addActor(btn_function);
            bool_draw_replay_btn = false;
        }

        stageReplay.act();
        stageReplay.draw();
    }

    void replay_false(){
        gameWorld2.bool_replay = false;
        btn_replay.remove();  btn_back.remove();  btn_function.remove();
        gameWorld2.float_timer = 15;
        gameWorld2.startGame();

    }
}
