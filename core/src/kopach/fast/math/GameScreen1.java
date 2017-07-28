package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
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
    BitmapFont score_value_font, btn_text, best_scrore_text_font, best_score_value_font, text_pryklad_font, mGC_gs1_text_vidp_right, mGC_gs1_text_vidp_wrong, time_font;
    SpriteBatch spriteBatch;

    float screen_width = 720, screen_height = 1280;
    float tr_propusk_width, tr_propusk_height;
    float best_score_text_x, best_score_text_y, best_score_value_x, best_score_value_y, tr_propusk_y, text_text_score_x, text_score_x, text_text_score_y, text_score_y, text_pryklad_y, text_vidp_y, text_time_x, text_time_y;

    public GameScreen1(final MyGameClass myGameClass) {
        this.myGameClass = myGameClass;
        loadFont();

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
        stage_vg = new Stage(viewport);
        stage_vg.clear();
        Gdx.input.setInputProcessor(stage_vg);
        spriteBatch = new SpriteBatch();

        skin = new Skin();
        textureAtlas_vg = new TextureAtlas("texture/TextureAtlas.atlas");
        skin.addRegions(textureAtlas_vg);
        createTextButtons();

        gameWorld1 = new GameWorld1(this);

        Gdx.input.setCatchBackKey(true);
        variables_x_y();

        Gdx.app.log("GameScreen1", "gw1 start game");


        tr_fon = new TextureRegion(textureAtlas_vg.findRegion("fon 1"));
        tr_X = new TextureRegion(textureAtlas_vg.findRegion("x"));
        tr_propusk = new TextureRegion(textureAtlas_vg.findRegion("znak pytanya"));
        //  myGameClass.bannerAdShow()
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

        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        score_value_font.draw(spriteBatch, gameWorld1.getString_score(), text_score_x, text_score_y);
        best_scrore_text_font.draw(spriteBatch, "BS: ", best_score_text_x, best_score_text_y);
        best_score_value_font.draw(spriteBatch, gameWorld1.getString_best_score_this_level(), best_score_value_x, best_score_value_y);
        time_font.draw(spriteBatch, gameWorld1.getTimer_game(), text_time_x, text_time_y);
        drawPryklad();


        spriteBatch.end();

        stage_vg.act(delta);
        stage_vg.draw();
    }

    private void drawPryklad() {
        int position = gameWorld1.getQuestionMarkPosition(); //отримуємо позицію де буде зявлятися знак питання(1,2,3)
        if (position == 1) {
            if (FLAG_SHOW_QUESTION_MARK) {
                //малюємо текст з знаком питання
                spriteBatch.draw(tr_propusk, 30, tr_propusk_y, tr_propusk_width, tr_propusk_height);
                text_pryklad_font.draw(spriteBatch, gameWorld1.getString_pryklad(), 30 + tr_propusk_width + 20, text_pryklad_y);
            } else {
                //текст з відповіддю користувача
                if (gameWorld1.bool_answer_right) {
                    mGC_gs1_text_vidp_right.draw(spriteBatch, gameWorld1.getString_input(), 30, text_vidp_y);
                } else {
                    mGC_gs1_text_vidp_wrong.draw(spriteBatch, gameWorld1.getString_input(), 30, text_vidp_y);
                }
                text_pryklad_font.draw(spriteBatch, gameWorld1.getString_pryklad(), 30 + getTextWidth(text_pryklad_font, gameWorld1.getString_input()) + 20, text_pryklad_y);
            }
        } else if (position == 2) {
            if (FLAG_SHOW_QUESTION_MARK) {
                //малюємо текст з знаком питання
                text_pryklad_font.draw(spriteBatch, gameWorld1.getFirstPart(), 30, text_pryklad_y);
                spriteBatch.draw(tr_propusk, 30 + getTextWidth(text_pryklad_font, gameWorld1.getFirstPart()) + 30, tr_propusk_y, tr_propusk_width, tr_propusk_height);
                text_pryklad_font.draw(spriteBatch, gameWorld1.getSecondPart(), 30 + getTextWidth(text_pryklad_font, gameWorld1.getFirstPart()) + 60 + tr_propusk_width, text_pryklad_y);
            } else {
                //текст з відповіддю користувача
                text_pryklad_font.draw(spriteBatch, gameWorld1.getFirstPart(), 30, text_pryklad_y);
                if (gameWorld1.bool_answer_right) {
                    mGC_gs1_text_vidp_right.draw(spriteBatch, gameWorld1.getString_input(), getTextWidth(text_pryklad_font, gameWorld1.getFirstPart()) + 60, text_vidp_y);
                } else {
                    mGC_gs1_text_vidp_wrong.draw(spriteBatch, gameWorld1.getString_input(), getTextWidth(text_pryklad_font, gameWorld1.getFirstPart()) + 60, text_vidp_y);
                }
                text_pryklad_font.draw(spriteBatch, gameWorld1.getSecondPart(), getTextWidth(text_pryklad_font, gameWorld1.getFirstPart())+ getTextWidth(mGC_gs1_text_vidp_wrong, gameWorld1.getSecondPart()), text_pryklad_y);

            }
        } else {
            if (FLAG_SHOW_QUESTION_MARK) {
                //малюємо текст з знаком питання
                text_pryklad_font.draw(spriteBatch, gameWorld1.getString_pryklad(), 30, text_pryklad_y);
                spriteBatch.draw(tr_propusk, getTextWidth(text_pryklad_font, gameWorld1.getString_pryklad()) + 60, tr_propusk_y, tr_propusk_width, tr_propusk_height);
            } else {
                //текст з відповіддю користувача
                text_pryklad_font.draw(spriteBatch, gameWorld1.getString_pryklad(), 30, text_pryklad_y);
                if (gameWorld1.bool_answer_right) {
                    mGC_gs1_text_vidp_right.draw(spriteBatch, gameWorld1.getString_input(), getTextWidth(text_pryklad_font, gameWorld1.getString_pryklad()) + 60, text_vidp_y);
                } else {
                    mGC_gs1_text_vidp_wrong.draw(spriteBatch, gameWorld1.getString_input(), getTextWidth(text_pryklad_font, gameWorld1.getString_pryklad()) + 60, text_vidp_y);
                }
            }
        }
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
        best_scrore_text_font.dispose();
        score_value_font.dispose();
        text_pryklad_font.dispose();
        time_font.dispose();
        mGC_gs1_text_vidp_right.dispose();
        mGC_gs1_text_vidp_wrong.dispose();

    }

    void updateButtonText(GameWorld1 gameWorld) {
        Gdx.app.log("tag", "update");
        btn_1.setText(String.valueOf(gameWorld.getInt_btn_1()));
        btn_2.setText(String.valueOf(gameWorld.getInt_btn_2()));
        btn_3.setText(String.valueOf(gameWorld.getInt_btn_3()));
        btn_4.setText(String.valueOf(gameWorld.getInt_btn_4()));
        btn_5.setText(String.valueOf(gameWorld.getInt_btn_5()));
        btn_6.setText(String.valueOf(gameWorld.getInt_btn_6()));
    }

    void loadFont() {
        time_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        time_font.getData().setScale(0.7f, 0.7f);

        score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        score_value_font.getData().setScale(0.6f, 0.6f);

        best_scrore_text_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        best_scrore_text_font.getData().setScale(0.5f, 0.5f);

        best_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/red bold 70.fnt"), Gdx.files.internal("bitmapfont/red bold 70.png"), false);
        best_score_value_font.getData().setScale(0.6f, 0.6f);

        text_pryklad_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_pryklad_font.getData().setScale(1.4f, 1.4f);

        mGC_gs1_text_vidp_right = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        mGC_gs1_text_vidp_right.getData().setScale(1.6f, 1.6f);

        mGC_gs1_text_vidp_wrong = new BitmapFont(Gdx.files.internal("bitmapfont/red bold 70.fnt"), Gdx.files.internal("bitmapfont/red bold 70.png"), false);
        mGC_gs1_text_vidp_wrong.getData().setScale(1.5f, 1.5f);

        btn_text = new BitmapFont(Gdx.files.internal("bitmapfont/white bold 70.fnt"), Gdx.files.internal("bitmapfont/white bold 70.png"), false);
        btn_text.getData().setScale(0.8f, 0.8f);

    }

    public void variables_x_y() {   // налаштування значень Х і У для прорисовки
        tr_propusk_width = 90;
        tr_propusk_height = 110;

        //   tr_propusk_x = gameWorld1.getInt_tr_propusk_x();
        tr_propusk_y = 900;

        best_score_text_x = 20;
        best_score_value_x = 90;
        text_time_x = screen_width / 2 - 6;
        text_text_score_x = screen_width - 165;
        text_score_x = text_text_score_x + 126;

        best_score_text_y = screen_height - 40;
        best_score_value_y = best_score_text_y + 4;
        text_pryklad_y = 980;
        text_vidp_y = text_pryklad_y;
        text_time_y = screen_height - 50;
        text_text_score_y = screen_height - 40;
        text_score_y = text_text_score_y + 4;


    }


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
            case 4:
                return BTN_1_X;
            case 2:
            case 5:
                return BTN_1_X + btn_width + vidstan_width;
            case 3:
            case 6:
                return BTN_1_X + btn_width * 2 + vidstan_width * 2;
        }
        return 0;
    }

    private float getButtonY(int position) {
        switch (position) {
            case 1:
            case 2:
            case 3:
                return BTN_4_Y + btn_height + vidstan_height;
            case 4:
            case 5:
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
        style.font = btn_text;
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
                FLAG_SHOW_QUESTION_MARK = false;
                gameWorld1.answer();
            }
        });
        return textButton;
    }
}
