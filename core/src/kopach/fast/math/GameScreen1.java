package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import sun.awt.X11.XWMHints;

/**
 * Created by vova on 17.05.17.
 */

public class GameScreen1 implements Screen {
    private static boolean FLAG_CAN_MOVE = true;
    MyGameClass myGameClass;
    public GameWorld1 gameWorld1;

    public TextureAtlas textureAtlas;
    public TextureRegion tr_screen, tr_fon, tr_X, tr_leftBorder, tr_screen_replay;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0,
            btn_C, btn_minus;
    Skin skin;
    BitmapFont font_btn, font_score, text_bestScore;
    SpriteBatch spriteBatch;
    final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕ" +
            "ЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

    int numOfChar;

    BitmapFont font_time, font_main, font_answer, font_scoreText, font_scoreValue, font_bestScoreText, font_bestScoreValue;

    float screenWidth = 720, screenHeight = 1280;
    float btnWidth, btnHeight, btnCWidth, btnCHeight, tr_screenWidth, tr_screenHeight, tr_leftBorderWidth, tr_leftBorderHeight;
    float XWidth;
    float vidstanWidth, vidstanHeight;
    float btn_C_x, btn_C_y, btn_minus_x, btn_minus_y, btn_answer_x, btn_answer_y, tr_X_x, tr_X_y, tr_screen_x, tr_screen_y, tr_left_border_x, tr_left_border_y;
    float bestScoreText_x, bestScoreText_y, bestScoreValue_x, bestScoreValue_y, scoreText_x, scoreText_y, scoreValue_x, scoreValue_y, prykladText_x, prykladText_y, vidpText_x, fontTime_x, fontTime_y;
    float icon_y;

    int money;


    int bestScore, myScore;
    ReplayDialog replay;

    public GameScreen1(final MyGameClass myGameClass) {
        this.myGameClass = myGameClass;
        gameWorld1 = new GameWorld1(this);
        //створюємо ре-плей
        replay = new ReplayDialog();
        //ставимо слухач, якщо користувач натискає на кнопку ре-плей,викликається метод OnReplay, так само onBack
        replay.setListener(new ReplayDialog.ReplayListener() {
            @Override
            void onReplay() {
                gameWorld1.buildGame();
                gameWorld1.float_timer = 15;
            }

            @Override
            void onBack() {
                myGameClass.setScreen(new MenuScreen(myGameClass));
            }
        });
        // Gdx.input.setCatchBackKey(true);

        variables();

        spriteBatch = new SpriteBatch();

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screenWidth, screenHeight, orthographicCamera);

        textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_screen = new TextureRegion(textureAtlas.findRegion("screen"));
        tr_fon = new TextureRegion(textureAtlas.findRegion("fon"));
        tr_X = new TextureRegion(textureAtlas.findRegion("x"));
        tr_leftBorder = new TextureRegion(textureAtlas.findRegion("fon"));
        tr_screen_replay = new TextureRegion(textureAtlas.findRegion("screen replay"));

        stage = new Stage(viewport);

        font_main = createFont(Color.BLACK);
        font_answer = createFont(Color.WHITE);
        Gdx.input.setInputProcessor(stage);


        skin = new Skin();
        skin.addRegions(textureAtlas);
        font_btn = new BitmapFont();

        textButton();
        calculateCharCount();

        money = MyPreference.getMoney();

        MyPreference.setActiveGameAtTheMoment("game 1");

        //  myGameClass.bannerAdShow();
    }

    @Override
    public void show() {

    }

    public BitmapFont createFont(Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = font_chars;
        parameter.size = 84;
        parameter.color = color;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);

        if (gameWorld1.bool_timer_game) {
            gameWorld1.timer_game(delta);
        }

        if (gameWorld1.bool_timer_wait_answer_right) {
            gameWorld1.timer_wait_answer_right(delta);
        }
        if (gameWorld1.bool_timer_wait_answer_wrong) {
            gameWorld1.timer_wait_answer_wrong(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        // orthographicCamera.update();
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(tr_fon, 0, 0, screenWidth, screenHeight);

        spriteBatch.draw(tr_screen, tr_screen_x, tr_screen_y, tr_screenWidth, tr_screenHeight);

        font_main.draw(spriteBatch, gameWorld1.getString_to_screen(), prykladText_x, prykladText_y);
        font_main.draw(spriteBatch, gameWorld1.getString_input(), prykladText_x + 20 + Utill.getTextWidth(font_main, gameWorld1.getString_to_screen()), prykladText_y);
        font_time.draw(spriteBatch, String.valueOf(gameWorld1.int_timer), fontTime_x, fontTime_y);
        font_scoreText.draw(spriteBatch, "Score: ", scoreText_x, scoreText_y);
        font_scoreValue.draw(spriteBatch, gameWorld1.getString_score(), scoreValue_x, scoreValue_y);
        font_bestScoreText.draw(spriteBatch, "BS: ", bestScoreText_x, bestScoreText_y);
        font_bestScoreValue.draw(spriteBatch, bestScore + "", bestScoreValue_x, bestScoreValue_y);
        spriteBatch.draw(tr_leftBorder, tr_left_border_x, tr_left_border_y, tr_leftBorderWidth, tr_leftBorderHeight);
        //перевіряємо, якщо потрібно показувати реплей,то показуємо

        stage.act(delta);
        stage.draw();
        Gdx.input.setInputProcessor(stage);

        if (replay.isShow()) {
            replay.render(myScore, bestScore);
        }

        spriteBatch.end();

        Gdx.app.log("getStringInput", gameWorld1.getString_input());

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
        font_main.dispose();
        font_scoreText.dispose();
        font_bestScoreText.dispose();
        font_time.dispose();
        spriteBatch.dispose();
    }

    public void variables() {
        font_scoreText = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        font_scoreText.getData().setScale(0.5f, 0.5f);

        font_scoreValue = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        font_scoreValue.getData().setScale(0.6f, 0.6f);

        font_bestScoreText = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        font_bestScoreText.getData().setScale(0.5f, 0.5f);

        font_bestScoreValue = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
        font_bestScoreValue.getData().setScale(0.6f, 0.6f);

        font_time = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        font_time.getData().setScale(0.7f, 0.7f);

        btnWidth = 185;
        btnHeight = 185;
        btnCWidth = 76;
        btnCHeight = 110;
        XWidth = 80;
        tr_screenWidth = 660;
        tr_screenHeight = 200;
        vidstanWidth = 20;
        vidstanHeight = 18;
        tr_leftBorderWidth = 30;
        tr_leftBorderHeight = 200;

        tr_screen_x = screenWidth / 2 - (tr_screenWidth / 2);
        tr_left_border_x = 0;
        btn_minus_x = (screenWidth - (btnWidth * 3 + vidstanWidth * 2)) / 2;

        btn_answer_x = (screenWidth - (btnWidth * 3 + vidstanWidth * 2)) / 2 + btnWidth * 2 + vidstanWidth * 2;
        btn_C_x = (screenWidth - tr_screenWidth) / 2 + tr_screenWidth - 90;
        tr_X_x = screenWidth / 2 - (XWidth / 2);
        bestScoreText_x = 18;
        bestScoreValue_x = 90;
        scoreText_x = screenWidth - 180;
        scoreValue_x = scoreText_x + 126;
        fontTime_x = screenWidth / 2 - 27;
        prykladText_x = tr_screen_x + 20;
        vidpText_x = btn_C_x - 200;

        tr_X_y = screenHeight - XWidth - 20;
        tr_screen_y = tr_X_y - 40 - tr_screenHeight;
        tr_left_border_y = tr_screen_y;
        btn_C_y = tr_screen_y + (tr_screenHeight / 2 - 60);

        btn_minus_y = tr_screen_y - btnHeight - 98 - vidstanHeight * 3 - btnHeight * 3;
        btn_answer_y = tr_screen_y - btnHeight - 98 - vidstanHeight * 3 - btnHeight * 3;
        fontTime_y = screenHeight - 20;
        scoreText_y = screenHeight - 40;
        scoreValue_y = scoreText_y + 4;
        bestScoreText_y = screenHeight - 40;
        bestScoreValue_y = bestScoreText_y + 4;
        prykladText_y = tr_screen_y + tr_screenHeight / 2 + 25;
        icon_y = scoreValue_y - 64;

        bestScore = MyPreference.getBSGame1();
        myScore = 0;
    }

    float getButtonX(int number) {
        switch (number) {
            case 1:
            case 4:
            case 7:
                return (screenWidth - (btnWidth * 3 + vidstanWidth * 2)) / 2;

            case 0:
            case 2:
            case 5:
            case 8:
                return (screenWidth - (btnWidth * 3 + vidstanWidth * 2)) / 2 + btnWidth + vidstanWidth;
            case 3:
            case 6:
            case 9:
                return (screenWidth - (btnWidth * 3 + vidstanWidth * 2)) / 2 + btnWidth * 2 + vidstanWidth * 2;

        }
        return 0;
    }

    float getButtonY(int number) {
        switch (number) {
            case 1:
            case 2:
            case 3:
                return tr_screen_y - btnHeight - 98;
            case 4:
            case 5:
            case 6:
                return tr_screen_y - btnHeight - 98 - vidstanHeight - btnHeight;
            case 7:
            case 8:
            case 9:
                return tr_screen_y - btnHeight - 98 - vidstanHeight * 2 - btnHeight * 2;
            case 0:
                return tr_screen_y - btnHeight - 98 - vidstanHeight * 3 - btnHeight * 3;

        }
        return 0;
    }


    public void textButton() {
        Gdx.app.log("log", "text button");

        //остання цифра в методі CreateButton це цифра яку ми будемо додаватив текстове поле,
        // якщо -1 то не додаємо нічого
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("btn answer");
        style.down = skin.getDrawable("btn answer press");
        style.font = font_answer;
        TextButton btn_answer = new TextButton(" ", style);
        stage.addActor(btn_answer);
        btn_answer.setPosition(btn_answer_x, btn_answer_y);
        btn_answer.setSize(btnWidth, btnHeight);
        btn_answer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (gameWorld1.checkIsAnswerTrue()) {
                    prykladText_x = tr_screen_x + 20;
                    ++myScore;
                    if (myScore > bestScore) {
                        bestScore = myScore;
                        MyPreference.setBSGame1(bestScore);
                    }
                }
                calculateCharCount();
            }
        });


        btn_0 = createButton("btn press", "btn", 0, "0");
        btn_0.setSize(btnWidth, btnHeight);

        btn_minus = createButton("btn press", "btn", -1, "-");
        btn_minus.setPosition(btn_minus_x, btn_minus_y);
        btn_minus.setSize(btnWidth, btnHeight);
        btn_minus.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWorld1.setString_input("-");
                calcuclateButtonTouch();
            }
        });

        btn_9 = createButton("btn press", "btn", 9, "9");
        btn_9.setSize(btnWidth, btnHeight);

        btn_8 = createButton("btn press", "btn", 8, "8");
        btn_8.setSize(btnWidth, btnHeight);

        btn_7 = createButton("btn press", "btn", 7, "7");
        btn_7.setSize(btnWidth, btnHeight);

        btn_6 = createButton("btn press", "btn", 6, "6");
        btn_6.setSize(btnWidth, btnHeight);

        btn_5 = createButton("btn press", "btn", 5, "5");
        btn_5.setSize(btnWidth, btnHeight);

        btn_4 = createButton("btn press", "btn", 4, "4");
        btn_4.setSize(btnWidth, btnHeight);

        btn_3 = createButton("btn press", "btn", 3, "3");
        btn_3.setSize(btnWidth, btnHeight);


        btn_2 = createButton("btn press", "btn", 2, "2");
        btn_2.setSize(btnWidth, btnHeight);

        btn_1 = createButton("btn press", "btn", 1, "1");
        btn_1.setSize(btnWidth, btnHeight);

        btn_C = createButton("btn C press", "btn C", -1, " ");
        btn_C.setSize(btnCWidth, btnCHeight);
        btn_C.setPosition(btn_C_x, btn_C_y);
        btn_C.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameWorld1.prees_C();
                if (prykladText_x < (tr_screen_x + 20)) {
                    prykladText_x += 60;
                    vidpText_x += 60;
                    FLAG_CAN_MOVE = true;
                }
            }
        });
    }

    TextButton createButton(String drawableDown, String drawableUp, final int number, String text_to_number) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable(drawableUp);
        style.down = skin.getDrawable(drawableDown);
        style.font = font_main;
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
                    if (gameWorld1.getString_input().length() < (String.valueOf(gameWorld1.int_result).length() + 1)) {
                        gameWorld1.string_input += number;
                        calcuclateButtonTouch();
                    }
                }
            });
        }
        return textButton;
    }

    void calcuclateButtonTouch() {
        int length = gameWorld1.getString_input().length();
        if (length > numOfChar) {
            movePrykladLeft();
        }
    }

    void movePrykladLeft() {
        if (FLAG_CAN_MOVE) {
            prykladText_x -= 60;
            vidpText_x -= 60;
        }
    }

    //Вираховує скільки символів може вміститися без зсуву
    void calculateCharCount() {
        float size_to_answer = btn_C_x - 40 - 50 - Utill.getTextWidth(font_main, gameWorld1.getString_to_screen());
        numOfChar = (int) (size_to_answer / Utill.getTextWidth(font_main, "8"));
    }
}
