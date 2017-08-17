package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
<<<<<<< Updated upstream
 * Created by Руслан on 16.08.2017.
 */


 /* Created by vova on 17.05.17.
 */

public class GameScreen6 extends Stage implements Screen {
    private static final float BTN_X = 90;
    private static final float BTN_Y = 140;
    private static final float vidstan_width = 90;
    private static final float vidstan_height = 55;
    private static final float btn_width = 540;
    private static final float btn_height = 150;
    MyGameClass myGameClass;
    public GameWorld6 gameWorld6;

    boolean FLAG_SHOW_QUESTION_MARK = true;

    public TextureAtlas textureAtlas;
    public TextureRegion tr_fon, tr_screen_replay;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage, stageReplay;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_replay, btn_back, btn_function;
    Skin skin;
    BitmapFont pryklad_font, replay_score_value_font, replay_best_score_value_font, score_text_font, score_value_font, font_btn, best_score_text_font, best_score_value_font, text_vidp_right, text_vidp_wrong, time_font;
    SpriteBatch spriteBatch;

    float screen_width = 720, screen_height = 1280;
    float btn_replay_width, btn_replay_height, btn_function_width, btn_function_height, btn_back_width, btn_back_height, tr_propusk_width, tr_propusk_height, tr_screen_replay_width, tr_screen_replay_height;
    float btn_replay_x, btn_replay_y, replay_score_value_x, replay_score_value_y, replay_best_score_value_x, replay_best_score_value_y, btn_function_x, btn_function_y, btn_back_x, btn_back_y, best_score_text_x, best_score_text_y, best_score_value_x, best_score_value_y, tr_propusk_y, score_text_x, score_text_y, score_value_x, score_value_y, text_pryklad_y, text_vidp_y, text_time_x, text_time_y, tr_screen_replay_x, tr_screen_replay_y;

    public boolean bool_draw_replay_btn;

    public GameScreen6(final MyGameClass myGameClass) {
        this.myGameClass = myGameClass;

        loadFont();

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
        stage = new Stage(viewport);

        stageReplay = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        spriteBatch = new SpriteBatch();

        skin = new Skin();
        textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");
        skin.addRegions(textureAtlas);
        createTextButtons();

        gameWorld6 = new GameWorld6(this);

        Gdx.input.setCatchBackKey(true);
        variables_x_y();

        tr_fon = new TextureRegion(textureAtlas.findRegion("fon"));
        tr_screen_replay = new TextureRegion(textureAtlas.findRegion("screen replay"));
        //  myGameClass.bannerAdShow()

        gameWorld6.startGame();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update_variables();

        if (gameWorld6.bool_timer_game){gameWorld6.timer_game(delta);}

        if (gameWorld6.bool_timer_wait_answer_right) {gameWorld6.timer_wait_answer_right(delta);}
        if (gameWorld6.bool_timer_wait_answer_wrong) {gameWorld6.timer_wait_answer_wrong(delta);}

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {    //  спрацьовує коли нажато Back
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        score_text_font.draw(spriteBatch, "Score: ", score_text_x, score_text_y);
        score_value_font.draw(spriteBatch, gameWorld6.getString_score(), score_value_x, score_value_y);
        best_score_text_font.draw(spriteBatch, "BS: ", best_score_text_x, best_score_text_y);
        best_score_value_font.draw(spriteBatch, String.valueOf(gameWorld6.getHighScore_game()), best_score_value_x, best_score_value_y);
        time_font.draw(spriteBatch, gameWorld6.getTimer_game(), text_time_x, text_time_y);
      //  drawPryklad(gameWorld5.getInt_pryklad_position_1_x());
        spriteBatch.end();

        stage.act(delta);
        stage.draw();

        stageReplay.getBatch().begin();
        if (gameWorld6.bool_replay){
            stageReplay.getBatch().draw(tr_screen_replay,tr_screen_replay_x, tr_screen_replay_y, tr_screen_replay_width, tr_screen_replay_height);
            replay_score_value_font.draw(stageReplay.getBatch(), gameWorld6.getString_score(), replay_score_value_x, replay_score_value_y);
            replay_best_score_value_font.draw(stageReplay.getBatch(), String.valueOf(gameWorld6.getHighScore_game()), replay_best_score_value_x, replay_best_score_value_y);
        }
        stageReplay.getBatch().end();

        if (gameWorld6.bool_replay){
            replay_true();
        }else {Gdx.input.setInputProcessor(stage);}

    }

    float getTextWidth(BitmapFont font, String text) {
        return new GlyphLayout(font, text).width;
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
        spriteBatch.dispose();
        best_score_value_font.dispose();
        best_score_text_font.dispose();
        score_value_font.dispose();
        pryklad_font.dispose();
        time_font.dispose();
        text_vidp_right.dispose();
        text_vidp_wrong.dispose();

    }

    void updateButtonText(GameWorld6 gameWorl6) {
        btn_1.setText(String.valueOf(gameWorl6.getString_btn_1()));
        btn_2.setText(String.valueOf(gameWorl6.getString_btn_2()));
        btn_3.setText(String.valueOf(gameWorl6.getString_btn_3()));
        btn_4.setText(String.valueOf(gameWorl6.getString_btn_4()));
        btn_5.setText(String.valueOf(gameWorl6.getString_btn_5()));
      //  btn_6.setText(String.valueOf(gameWorld.getInt_btn_6()));
    }

    void loadFont() {
        time_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        time_font.getData().setScale(0.7f, 0.7f);

        score_text_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        score_text_font.getData().setScale(0.5f, 0.5f);

        score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        score_value_font.getData().setScale(0.6f, 0.6f);

        best_score_text_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        best_score_text_font.getData().setScale(0.5f, 0.5f);

        best_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
        best_score_value_font.getData().setScale(0.6f, 0.6f);

        pryklad_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        pryklad_font.getData().setScale(1.4f, 1.4f);

        text_vidp_right = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        text_vidp_right.getData().setScale(1.6f, 1.6f);

        text_vidp_wrong = new BitmapFont(Gdx.files.internal("bitmapfont/red bold 70.fnt"), Gdx.files.internal("bitmapfont/red bold 70.png"), false);
        text_vidp_wrong.getData().setScale(1.5f, 1.5f);

        font_btn = new BitmapFont(Gdx.files.internal("bitmapfont/white bold 70.fnt"), Gdx.files.internal("bitmapfont/white bold 70.png"), false);
        font_btn.getData().setScale(0.9f, 0.9f);

        replay_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        replay_score_value_font.getData().setScale(0.8f, 0.8f);

        replay_best_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
        replay_best_score_value_font.getData().setScale(0.9f, 0.9f);

    }

    public void variables_x_y() {   // налаштування значень Х і У для прорисовки
        tr_propusk_width = 90;
        tr_propusk_height = 110;
        tr_screen_replay_width = 720;
        tr_screen_replay_height = 1280;
        btn_replay_width = 170;
        btn_replay_height = 170;
        btn_function_width = 100;
        btn_function_height = 100;
        btn_back_width = 100;
        btn_back_height = 100;

        tr_propusk_y = 900;

        best_score_text_x = 18;
        best_score_value_x = 90;
        text_time_x = screen_width / 2 - 27;
        score_text_x = screen_width - 180;
        score_value_x = score_text_x + 126;
        tr_screen_replay_x = screen_width/2 - tr_screen_replay_width/2;
        btn_replay_x = screen_width/2 - btn_replay_width/2;
        btn_function_x = btn_replay_x + btn_replay_width + 70;
        btn_back_x = btn_replay_x - 70 - btn_back_width;
        replay_score_value_x = screen_width/2 - 30;
        replay_best_score_value_x = screen_width/2 - 30;

        best_score_text_y = screen_height - 40;
        best_score_value_y = best_score_text_y + 4;
        text_pryklad_y = 980;
        text_vidp_y = text_pryklad_y;
        text_time_y = screen_height - 30;
        score_text_y = best_score_text_y;
        score_value_y = best_score_value_y + 4;
        tr_screen_replay_y = 10;
        replay_score_value_y = 600;
        replay_best_score_value_y = 760;
        btn_replay_y = 300;
        btn_back_y = 290;
        btn_function_y = 290;

    }


    public void createTextButtons() {   // налаштування кнопок
        btn_1 = drawButton(1);
        btn_2 = drawButton(2);
        btn_3 = drawButton(3);
        btn_4 = drawButton(4);
        btn_5 = drawButton(5);
       // btn_6 = drawButton(6);

    }

    void update_variables(){
        if (String.valueOf(gameWorld6.getHighScore_game()).length() == 2){
            replay_best_score_value_x = (screen_width/2 - getTextWidth(replay_best_score_value_font, String.valueOf(gameWorld6.getHighScore_game()))/2) - 20;
        }else if (String.valueOf(gameWorld6.getHighScore_game()).length() == 3){
            replay_best_score_value_x = (screen_width/2 - getTextWidth(replay_best_score_value_font, String.valueOf(gameWorld6.getHighScore_game()))/2) - 27;
        }

        if (String.valueOf(gameWorld6.getString_score()).length() == 2){
            replay_score_value_x = (screen_width/2 - getTextWidth(replay_best_score_value_font, String.valueOf(gameWorld6.getHighScore_game()))/2) - 20;
        }else if (String.valueOf(gameWorld6.getString_score()).length() == 3){
            replay_score_value_x = (screen_width/2 - getTextWidth(replay_best_score_value_font, String.valueOf(gameWorld6.getHighScore_game()))/2) - 27;
        }

    }

    private float getButtonX(int position) {
        switch (position) {
            case 1:
            case 4:
                return BTN_X;
            case 2:
            case 5:
                return BTN_X;
            case 3:
            case 6:
                return BTN_X;
        }
        return 0;
    }

    private float getButtonY(int position) {
        switch (position) {
            case 1: return BTN_Y;
            case 2: return BTN_Y + vidstan_height + btn_height;
            case 3: return BTN_Y + ((vidstan_height + btn_height)*2);
            case 4: return BTN_Y + ((vidstan_height + btn_height)*3);
            case 5: return BTN_Y + ((vidstan_height + btn_height)*4);
            case 6: return BTN_Y + ((vidstan_height + btn_height)*5);
        }
        return 0;
    }

    //Цей метод створює кожну кнопку по черзі
    TextButton drawButton(int position) {

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("btn blue");
        style.down = skin.getDrawable("btn blue press");
        style.font = font_btn;
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
                gameWorld6.setString_input(String.valueOf(finalTextButton.getText()));
                FLAG_SHOW_QUESTION_MARK = false;
                gameWorld6.answer(gameWorld6.getString_input());
            }
        });
        return textButton;
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
        gameWorld6.bool_replay = false;
        btn_replay.remove();  btn_back.remove();  btn_function.remove();
        gameWorld6.float_timer = 15;
        gameWorld6.startGame();
    }
}
