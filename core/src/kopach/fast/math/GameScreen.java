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

public class GameScreen implements Screen {
    MyGameClass myGameClass;
    public GameWorld gameWorld;

    public TextureAtlas textureAtlas_vg;
    public TextureRegion tr_screen, tr_fon, tr_X, tr_left_border;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_vg;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_answer, btn_C, btn_minus;
    public TextButton.TextButtonStyle btn1_style, btn2_style, btn3_style, btn4_style, btn5_style, btn6_style, btn7_style, btn8_style, btn9_style, btn_minus_style, btn0_style, btn_C_style, btn_answer_style;
    Skin skin;
    BitmapFont text_to_button;
    BitmapFont mGC_gs_text_pryklad, mGC_gs_text_vidp, mGC_gs_text_score, mGC_gs_text_text_best_score, mGC_gs_text_best_score, mGC_gs_text_time;
    SpriteBatch mGC_spriteBatch;

    //цей прапор служить для перевірки, щоб завжди було тільки одне видалення
    boolean flag_is_scroll_to_left;

    float screen_width = 720, screen_height = 1280;
    public float width_btn, height_btn, width_btn_answer, height_btn_answer, width_btn_C, height_btn_C, tr_screen_width, tr_screen_height, tr_left_border_width, tr_left_border_height;
    float width_X, height_X;
    public float vidstan_width, vidstan_height;
    public float btn_C_x, btn_C_y, btn0_x, btn0_y, btn1_x, btn1_y, btn2_x, btn2_y, btn3_x, btn3_y, btn4_x, btn4_y, btn5_x, btn5_y, btn6_x, btn6_y, btn7_x, btn7_y, btn8_x, btn8_y, btn9_x, btn9_y, btn_minus_x, btn_minus_y, btn_answer_x, btn_answer_y, tr_X_x, tr_X_y, tr_screen_x, tr_screen_y, tr_left_border_x, tr_left_border_y;
    float text_text_best_score_x, text_text_best_score_y, text_best_score_x, text_best_score_y, text_score_x, text_score_y, text_pryklad_x, text_pryklad_y, text_vidp_x, text_vidp_y, text_time_x, text_time_y;

    public GameScreen(final MyGameClass myGameClass) {
        this.myGameClass = myGameClass;
        gameWorld = new GameWorld();

        Gdx.input.setCatchBackKey(true);

        variables_x_y();
        variables();

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);


        textureAtlas_vg = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_screen = new TextureRegion(textureAtlas_vg.findRegion("screen"));
        tr_fon = new TextureRegion(textureAtlas_vg.findRegion("fon"));
        tr_X = new TextureRegion(textureAtlas_vg.findRegion("x"));
        tr_left_border = new TextureRegion(textureAtlas_vg.findRegion("fon"));

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
        gameWorld.setString_timer(delta);

        // orthographicCamera.update();
        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        myGameClass.spriteBatch.draw(tr_screen, tr_screen_x, tr_screen_y, tr_screen_width, tr_screen_height);

        if (gameWorld.getBoolean_X()) {
            myGameClass.spriteBatch.draw(tr_X, tr_X_x, tr_X_y, width_X, height_X);
        }
        myGameClass.gs_text_pryklad.draw(myGameClass.spriteBatch, gameWorld.getString_to_screen(), text_pryklad_x, text_pryklad_y);
        myGameClass.gs_text_vidp.draw(myGameClass.spriteBatch, gameWorld.getString_input(), text_vidp_x, text_vidp_y);
        myGameClass.gs_text_score.draw(myGameClass.spriteBatch, gameWorld.getString_score(), text_score_x, text_score_y);
        myGameClass.gs_text_time.draw(myGameClass.spriteBatch, gameWorld.getString_timer(), text_time_x, text_time_y);
        myGameClass.gs_text_text_best_score.draw(mGC_spriteBatch, "BS:", text_text_best_score_x, text_text_best_score_y);
        myGameClass.gs_text_best_score.draw(mGC_spriteBatch, gameWorld.getString_best_score_this_level(), text_best_score_x, text_best_score_y);
        myGameClass.spriteBatch.draw(tr_left_border, tr_left_border_x, tr_left_border_y, tr_left_border_width, tr_left_border_height);
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


    public void variables() {
        mGC_spriteBatch = myGameClass.spriteBatch;

        mGC_gs_text_time = myGameClass.gs_text_time;
        mGC_gs_text_score = myGameClass.gs_text_score;
        mGC_gs_text_text_best_score = myGameClass.gs_text_text_best_score;
        mGC_gs_text_best_score = myGameClass.gs_text_best_score;
        mGC_gs_text_pryklad = myGameClass.gs_text_pryklad;
        mGC_gs_text_vidp = myGameClass.gs_text_vidp;

    }

    public void variables_x_y() {
        width_btn = 185;
        height_btn = 185;
        width_btn_answer = 235;
        height_btn_answer = 235;
        width_btn_C = 76;
        height_btn_C = 110;
        width_X = 80;
        height_X = 80;
        tr_screen_width = 660;
        tr_screen_height = 200;
        vidstan_width = 20;
        vidstan_height = 18;
        tr_left_border_width = 30;
        tr_left_border_height = 200;


        tr_screen_x = screen_width / 2 - (tr_screen_width / 2);
        tr_left_border_x = 0;
        btn1_x = (screen_width - (width_btn * 3 + vidstan_width * 2)) / 2;
        btn4_x = btn1_x;
        btn7_x = btn1_x;
        btn_minus_x = btn1_x;
        btn2_x = btn1_x + width_btn + vidstan_width;
        btn5_x = btn4_x + width_btn + vidstan_width;
        btn8_x = btn7_x + width_btn + vidstan_width;
        btn0_x = btn_minus_x + width_btn + vidstan_width;
        btn3_x = btn2_x + width_btn + vidstan_width;
        btn6_x = btn5_x + width_btn + vidstan_width;
        btn9_x = btn8_x + width_btn + vidstan_width;
        btn_answer_x = btn0_x + width_btn + vidstan_width;
        btn_C_x = (screen_width - tr_screen_width) / 2 + tr_screen_width - 90;
        tr_X_x = screen_width / 2 - (width_X / 2);
        text_text_best_score_x = 20;
        text_best_score_x = 90;
        text_time_x = 35;
        text_score_x = screen_width - 160;
        text_pryklad_x = tr_screen_x + 20;
        text_vidp_x = btn_C_x - 200;

        tr_X_y = screen_height - width_X - 20;
        tr_screen_y = tr_X_y - 40 - tr_screen_height;
        tr_left_border_y = tr_screen_y;
        btn_C_y = tr_screen_y + (tr_screen_height / 2 - 60);
        btn1_y = tr_screen_y - height_btn - 98;
        btn2_y = btn1_y;
        btn3_y = btn1_y;
        btn4_y = btn1_y - vidstan_height - height_btn;
        btn5_y = btn2_y - vidstan_height - height_btn;
        btn6_y = btn3_y - vidstan_height - height_btn;
        btn7_y = btn4_y - vidstan_height - height_btn;
        btn8_y = btn5_y - vidstan_height - height_btn;
        btn9_y = btn6_y - vidstan_height - height_btn;
        btn_minus_y = btn7_y - vidstan_height - height_btn;
        btn0_y = btn8_y - vidstan_height - height_btn;
        btn_answer_y = btn9_y - vidstan_height - height_btn;
        text_time_y = screen_height - 20;
        text_score_y = text_time_y;
        text_text_best_score_y = 1240;  text_best_score_y = text_text_best_score_y + 4;
        text_pryklad_y = tr_screen_y + tr_screen_height / 2 + 25;
        text_vidp_y = text_pryklad_y;

    }

    public void game_level() {
        if (gameWorld.float_timer < 0) {
            gameWorld.float_timer = 10;
            myGameClass.setScreen(new RestartScreen(myGameClass));
            Gdx.app.log("log", "good");
        }
    }


    public void textButton() {
        Gdx.app.log("log", "text button");

        // скоротив ініціалізацію кнопок

        //остання цифра в методі CreateButton це цифра яку ми будемо додаватив текстове поле,
        // якщо -1 то не додаємо нічого
        btn_answer = createButton("btn_answer_press", "btn_answer", -1);
        btn_answer.setPosition(btn_answer_x, btn_answer_y);
        btn_answer.setSize(width_btn, height_btn);
        btn_answer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWorld.btn_answer();
            }
        });


        btn_0 = createButton("btn0_press", "btn0", 0);
        btn_0.setSize(width_btn, height_btn);
        btn_0.setPosition(btn0_x, btn0_y);

        btn_minus = createButton("btnminus_press", "btnminus", -1);
        btn_minus.setPosition(btn_minus_x, btn_minus_y);
        btn_minus.setSize(width_btn, height_btn);
        btn_minus.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWorld.setString_input("-");
                // dispose();
            }
        });

        btn_9 = createButton("btn9_press", "btn9", 9);
        btn_9.setSize(width_btn, height_btn);
        btn_9.setPosition(btn9_x, btn9_y);


        btn_8 = createButton("btn8_press", "btn8", 8);
        btn_8.setSize(width_btn, height_btn);
        btn_8.setPosition(btn8_x, btn8_y);


        btn_7 = createButton("btn7_press", "btn7", 7);
        btn_7.setSize(width_btn, height_btn);
        btn_7.setPosition(btn7_x, btn7_y);


        btn_6 = createButton("btn6_press", "btn6", 6);
        btn_6.setSize(width_btn, height_btn);
        btn_6.setPosition(btn6_x, btn6_y);


        btn_5 = createButton("btn5_press", "btn5", 5);
        btn_5.setSize(width_btn, height_btn);
        btn_5.setPosition(btn5_x, btn5_y);

        btn_4 = createButton("btn4_press", "btn4", 4);
        btn_4.setSize(width_btn, height_btn);
        btn_4.setPosition(btn4_x, btn4_y);

        btn_3 = createButton("btn3_press", "btn3", 3);
        btn_3.setSize(width_btn, height_btn);
        btn_3.setPosition(btn3_x, btn3_y);


        btn_2 = createButton("btn2_press", "btn2", 2);
        btn_2.setSize(width_btn, height_btn);
        btn_2.setPosition(btn2_x, btn2_y);

        btn_1 = createButton("btn1_press", "btn1", 1);
        btn_1.setSize(width_btn, height_btn);
        btn_1.setPosition(btn1_x, btn1_y);

        btn_C = createButton("btn_C_press", "btn_C", -1);
        btn_C.setSize(width_btn_C, height_btn_C);
        btn_C.setPosition(btn_C_x, btn_C_y);
        btn_C.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWorld.prees_C();
                if (flag_is_scroll_to_left) {
                    text_pryklad_x += 50;
                    text_vidp_x += 50;
                }
                flag_is_scroll_to_left = false;
            }
        });
    }

    TextButton createButton(String drawableDown, String drawableUp, final int number) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable(drawableUp);
        style.down = skin.getDrawable(drawableDown);
        style.font = text_to_button;
        TextButton textButton = new TextButton(" ", style);
        stage_vg.addActor(textButton);
        if (number >= 0) {
            textButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    gameWorld.setString_input(String.valueOf(number));
                    if (gameWorld.getString_input().length() == 4) {
                        //цей прапор служить для перевірки, щоб завжди було тільки одне видалення
                        if (!flag_is_scroll_to_left) {
                            text_pryklad_x -= 50;
                            text_vidp_x -= 50;
                            flag_is_scroll_to_left = true;
                        }
                    }
                }
            });
        }
        return textButton;
    }
}
//527
