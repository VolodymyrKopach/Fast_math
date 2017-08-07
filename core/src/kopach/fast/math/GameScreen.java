package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
 * Created by vova on 17.05.17.
 */

public class GameScreen implements Screen {
    private static boolean FLAG_CAN_MOVE = true;
    MyGameClass myGameClass;
    public GameWorld gameWorld;

    public TextureAtlas textureAtlas_vg;
    public TextureRegion tr_screen, tr_fon, tr_X, tr_left_border, tr_screen_replay;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage, stage_replay;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_answer, btn_C, btn_minus, btn_replay;
    Skin skin;
    BitmapFont text_to_button;
    BitmapFont text_to_btn, text_pryklad, text_vidp, text_score, text_best_score, text_time;
    SpriteBatch spriteBatch;
    Texture icon, cup;

    int num_of_char;

    //цей прапор служить для перевірки, щоб завжди було тільки одне видалення
    public boolean bool_replay;

    float screen_width = 720, screen_height = 1280;
    float width_btn, height_btn, width_btn_answer, height_btn_answer, width_btn_C, height_btn_C, tr_screen_width, tr_screen_height, tr_left_border_width, tr_left_border_height;
    float width_X, height_X;
    float vidstan_width, vidstan_height;
    float btn_C_x, btn_C_y, btn_minus_x, btn_minus_y, btn_answer_x, btn_answer_y, tr_X_x, tr_X_y, tr_screen_x, tr_screen_y, tr_left_border_x, tr_left_border_y;
    float text_text_best_score_x, text_best_score_x, text_best_score_y, text_score_x, text_score_y, text_pryklad_x, text_pryklad_y, text_vidp_x, text_vidp_y, text_time_x, text_time_y;
    float btn_replay_x, btn_replay_y, width_btn_replay, height_btn_replay;
    float icon_y;

    int bestScore, myScore;

    public GameScreen(final MyGameClass myGameClass) {
        this.myGameClass = myGameClass;
        gameWorld = new GameWorld();

        Gdx.input.setCatchBackKey(true);

        variables();

        spriteBatch = new SpriteBatch(); //myGameClass.spriteBatch;

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);

        icon = new Texture("icon.png");
        cup = new Texture("cup_white.png");
        textureAtlas_vg = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_screen = new TextureRegion(textureAtlas_vg.findRegion("screen"));
        tr_fon = new TextureRegion(textureAtlas_vg.findRegion("fon"));
        tr_X = new TextureRegion(textureAtlas_vg.findRegion("x"));
        tr_left_border = new TextureRegion(textureAtlas_vg.findRegion("fon"));
        tr_screen_replay = new TextureRegion(textureAtlas_vg.findRegion("screen replay"));

        stage_replay = new Stage(viewport);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);


        skin = new Skin();
        skin.addRegions(textureAtlas_vg);
        text_to_button = new BitmapFont();

        textButton();
        calculateCharCount();
        //  myGameClass.bannerAdShow();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);

        game_level();
        gameWorld.setInt_timer(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        // orthographicCamera.update();
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        spriteBatch.draw(tr_screen, tr_screen_x, tr_screen_y, tr_screen_width, tr_screen_height);

        spriteBatch.draw(icon, screen_width - 160, icon_y);
        spriteBatch.draw(cup, 50, icon_y);
        text_pryklad.draw(spriteBatch, gameWorld.getString_to_screen(), text_pryklad_x, text_pryklad_y);
        text_vidp.draw(spriteBatch, gameWorld.getString_input(), text_pryklad_x + 20 + getTextWidth(text_pryklad, gameWorld.getString_to_screen()), text_vidp_y);
        text_score.draw(spriteBatch, gameWorld.getString_score(), text_score_x, text_score_y - 20);
        text_time.draw(spriteBatch, gameWorld.getInt_timer() + "", text_time_x, text_time_y);
        spriteBatch.draw(tr_left_border, tr_left_border_x, tr_left_border_y, tr_left_border_width, tr_left_border_height);
        text_best_score.draw(spriteBatch, String.valueOf(gameWorld.getBestScore()), text_best_score_x, text_best_score_y);

        if (bool_replay) {
            spriteBatch.draw(tr_screen_replay, 0, 0, screen_width, screen_height);
        }

        spriteBatch.end();

        if (bool_replay) {
            btn_replay();

        } else {
            stage.act(delta);
            stage.draw();
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
        textureAtlas_vg.dispose();
        stage.dispose();
        skin.dispose();
        text_to_button.dispose();
        text_to_btn.dispose();
        text_pryklad.dispose();
        text_vidp.dispose();
        text_score.dispose();
        text_best_score.dispose();
        text_time.dispose();
        spriteBatch.dispose();
    }

    public void variables() {

        text_to_btn = new BitmapFont(Gdx.files.internal("bitmapfont/text.fnt"), Gdx.files.internal("bitmapfont/text.png"), false);
        text_vidp = new BitmapFont(Gdx.files.internal("bitmapfont/text.fnt"), Gdx.files.internal("bitmapfont/text.png"), false);
        text_pryklad = new BitmapFont(Gdx.files.internal("bitmapfont/text.fnt"), Gdx.files.internal("bitmapfont/text.png"), false);
        text_score = new BitmapFont(Gdx.files.internal("bitmapfont/text.fnt"), Gdx.files.internal("bitmapfont/text.png"), false);
        text_best_score = new BitmapFont(Gdx.files.internal("bitmapfont/red bold 70.fnt"), Gdx.files.internal("bitmapfont/red bold 70.png"), false);
        text_time = new BitmapFont(Gdx.files.internal("bitmapfont/game text time.fnt"), Gdx.files.internal("bitmapfont/game text time.png"), false);
        text_vidp.getData().setScale(1.5f, 1.5f);
        text_to_btn.getData().setScale(1.4f, 1.4f);
        text_pryklad.getData().setScale(1.4f, 1.4f);
        text_score.getData().setScale(0.5f, 0.5f);
        text_best_score.getData().setScale(0.6f, 0.6f);
        text_time.getData().setScale(1.3f, 1.3f);


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
        btn_minus_x = (screen_width - (width_btn * 3 + vidstan_width * 2)) / 2;

        btn_answer_x = (screen_width - (width_btn * 3 + vidstan_width * 2)) / 2 + width_btn * 2 + vidstan_width * 2;
        btn_C_x = (screen_width - tr_screen_width) / 2 + tr_screen_width - 90;
        tr_X_x = screen_width / 2 - (width_X / 2);
        text_text_best_score_x = 20;
        text_best_score_x = 124;
        text_time_x = screen_width / 2 - 27;
        text_score_x = screen_width - 80;
        text_pryklad_x = tr_screen_x + 20;
        text_vidp_x = btn_C_x - 200;

        tr_X_y = screen_height - width_X - 20;
        tr_screen_y = tr_X_y - 40 - tr_screen_height;
        tr_left_border_y = tr_screen_y;
        btn_C_y = tr_screen_y + (tr_screen_height / 2 - 60);


        btn_minus_y = tr_screen_y - height_btn - 98 - vidstan_height * 3 - height_btn * 3;
        btn_answer_y = tr_screen_y - height_btn - 98 - vidstan_height * 3 - height_btn * 3;
        text_time_y = screen_height - 20;
        text_score_y = screen_height - 40;
        text_best_score_y = screen_height - 60;
        text_pryklad_y = tr_screen_y + tr_screen_height / 2 + 25;
        text_vidp_y = text_pryklad_y + 4;


        width_btn_replay = 400;
        height_btn_replay = 100;
        btn_replay_x = screen_width / 2 - width_btn_replay / 2;
        btn_replay_y = 340;
        icon_y = text_score_y - 64;

        bestScore = gameWorld.getBestScore();
        myScore = 0;
    }

    float getButtonX(int number) {
        switch (number) {
            case 1:
            case 4:
            case 7:
                return (screen_width - (width_btn * 3 + vidstan_width * 2)) / 2;

            case 0:
            case 2:
            case 5:
            case 8:
                return (screen_width - (width_btn * 3 + vidstan_width * 2)) / 2 + width_btn + vidstan_width;
            case 3:
            case 6:
            case 9:
                return (screen_width - (width_btn * 3 + vidstan_width * 2)) / 2 + width_btn * 2 + vidstan_width * 2;

        }
        return 0;
    }

    float getButtonY(int number) {
        switch (number) {
            case 1:
            case 2:
            case 3:
                return tr_screen_y - height_btn - 98;
            case 4:
            case 5:
            case 6:
                return tr_screen_y - height_btn - 98 - vidstan_height - height_btn;
            case 7:
            case 8:
            case 9:
                return tr_screen_y - height_btn - 98 - vidstan_height * 2 - height_btn * 2;
            case 0:
                return tr_screen_y - height_btn - 98 - vidstan_height * 3 - height_btn * 3;

        }
        return 0;
    }

    public void game_level() {
        if (gameWorld.float_timer < 0) {
            gameWorld.float_timer = 0;
            bool_replay = true;
        }
    }


    public void textButton() {
        Gdx.app.log("log", "text button");

        //остання цифра в методі CreateButton це цифра яку ми будемо додаватив текстове поле,
        // якщо -1 то не додаємо нічого
        btn_answer = createButton("btn answer press", "btn answer", -1, " ");
        btn_answer.setPosition(btn_answer_x, btn_answer_y);
        btn_answer.setSize(width_btn, height_btn);
        btn_answer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (gameWorld.checkIsAnswerTrue()) {
                    text_pryklad_x = tr_screen_x + 20;
                    ++myScore;
                    if (myScore > bestScore) {
                        cup = new Texture("cup_yellow.png");
                        bestScore = myScore;
                        gameWorld.setBestScore(bestScore);
                    }
                }
                calculateCharCount();
            }
        });


        btn_0 = createButton("btn press", "btn", 0, "0");
        btn_0.setSize(width_btn, height_btn);

        btn_minus = createButton("btn press", "btn", -1, "-");
        btn_minus.setPosition(btn_minus_x, btn_minus_y);
        btn_minus.setSize(width_btn, height_btn);
        btn_minus.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWorld.setString_input("-");
                calcuclateButtonTouch();
            }
        });

        btn_9 = createButton("btn press", "btn", 9, "9");
        btn_9.setSize(width_btn, height_btn);

        btn_8 = createButton("btn press", "btn", 8, "8");
        btn_8.setSize(width_btn, height_btn);

        btn_7 = createButton("btn press", "btn", 7, "7");
        btn_7.setSize(width_btn, height_btn);

        btn_6 = createButton("btn press", "btn", 6, "6");
        btn_6.setSize(width_btn, height_btn);

        btn_5 = createButton("btn press", "btn", 5, "5");
        btn_5.setSize(width_btn, height_btn);

        btn_4 = createButton("btn press", "btn", 4, "4");
        btn_4.setSize(width_btn, height_btn);

        btn_3 = createButton("btn press", "btn", 3, "3");
        btn_3.setSize(width_btn, height_btn);


        btn_2 = createButton("btn press", "btn", 2, "2");
        btn_2.setSize(width_btn, height_btn);

        btn_1 = createButton("btn press", "btn", 1, "1");
        btn_1.setSize(width_btn, height_btn);

        btn_C = createButton("btn C press", "btn C", -1, " ");
        btn_C.setSize(width_btn_C, height_btn_C);
        btn_C.setPosition(btn_C_x, btn_C_y);
        btn_C.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWorld.prees_C();
                if (text_pryklad_x < (tr_screen_x + 20)) {
                    text_pryklad_x += 60;
                    text_vidp_x += 60;
                    FLAG_CAN_MOVE = true;
                }
            }
        });
    }

    TextButton createButton(String drawableDown, String drawableUp, final int number, String text_to_number) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable(drawableUp);
        style.down = skin.getDrawable(drawableDown);
        style.font = text_to_btn;
        TextButton textButton = new TextButton(text_to_number, style);
        textButton.setPosition(getButtonX(number), getButtonY(number));
        stage.addActor(textButton);
        if (number >= 0) {
            textButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if (gameWorld.getString_input().length() < (String.valueOf(gameWorld.int_result).length() + 1)) {
                        gameWorld.string_input += number;
                        calcuclateButtonTouch();
                    }
                }
            });
        }
        return textButton;
    }

    void calcuclateButtonTouch() {
        int length = gameWorld.getString_input().length();
        if (length > num_of_char) {
            movePrykladLeft();
        }
    }

    void movePrykladLeft() {
        if (FLAG_CAN_MOVE) {
            text_pryklad_x -= 60;
            text_vidp_x -= 60;
        }
    }

    void btn_replay() {
        TextButton.TextButtonStyle btn_replay_style = new TextButton.TextButtonStyle();
        btn_replay_style.up = skin.getDrawable("btn blue");
        btn_replay_style.down = skin.getDrawable("btn blue press");
        btn_replay_style.font = text_to_button;

        btn_replay = new TextButton("Replay", btn_replay_style);
        btn_replay.setSize(width_btn_replay, height_btn_replay);
        btn_replay.setPosition(btn_replay_x, btn_replay_y);
        btn_replay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWorld.float_timer = 15;
                bool_replay = false;
                Gdx.input.setInputProcessor(stage);
            }
        });

        if (bool_replay) {
            stage_replay.addActor(btn_replay);
            Gdx.input.setInputProcessor(stage_replay);
            stage_replay.act();
            stage_replay.draw();
        }
    }

    //Вираховує скільки символів може вміститися без зсуву
    void calculateCharCount() {
        float size_to_answer = btn_C_x - 40 - 50 - getTextWidth(text_pryklad, gameWorld.getString_to_screen());
        num_of_char = (int) (size_to_answer / getTextWidth(text_vidp, "8"));
    }
}
