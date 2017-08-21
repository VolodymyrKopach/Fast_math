package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

/**
 * Created by vova on 17.05.17.
 */

public class GameScreen1 implements Screen {
    private static boolean FLAG_CAN_MOVE = true;
    MyGameClass myGameClass;
    public GameWorld1 gameWorld1;

    public TextureAtlas textureAtlas;
    public TextureRegion tr_screen, tr_fon, tr_X, tr_left_border, tr_screen_replay;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage, stageReplay;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0,
            btn_C, btn_minus, btn_replay, btn_back, btn_function;
    Skin skin;
    BitmapFont font_btn, replay_score_value_font, replay_best_score_value_font, text_score, text_best_score, text_time;
    SpriteBatch spriteBatch;
    final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕ" +
            "ЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

    int num_of_char;

    BitmapFont mainFont, answerFont, score_text_font, score_value_font, best_score_text_font, best_score_value_font;

    public boolean bool_draw_replay_btn;

    float screen_width = 720, screen_height = 1280;
    float tr_screen_replay_width, tr_screen_replay_height, btn_replay_width, btn_replay_height, btn_function_width, btn_function_height, btn_back_width, btn_back_height,width_btn, height_btn, width_btn_C, height_btn_C, tr_screen_width, tr_screen_height, tr_left_border_width, tr_left_border_height;
    float width_X;
    float vidstan_width, vidstan_height;
    float btn_C_x, btn_C_y, btn_minus_x, btn_minus_y, btn_answer_x, btn_answer_y, tr_X_x, tr_X_y, tr_screen_x, tr_screen_y, tr_left_border_x, tr_left_border_y;
    float best_score_text_x, best_score_text_y, best_score_value_x, best_score_value_y, score_text_x, score_text_y, score_value_x, score_value_y, text_pryklad_x, text_pryklad_y, text_vidp_x, text_time_x, text_time_y;
    float tr_screen_replay_x, tr_screen_replay_y, btn_replay_x, btn_replay_y, replay_score_value_x, replay_score_value_y, replay_best_score_value_x, replay_best_score_value_y, btn_function_x, btn_function_y, btn_back_x, btn_back_y, width_btn_replay, height_btn_replay;
    float icon_y;

    int money;


    int bestScore, myScore;

    public GameScreen1(final MyGameClass myGameClass) {
        this.myGameClass = myGameClass;
        gameWorld1 = new GameWorld1();

        Gdx.input.setCatchBackKey(true);

        variables();

        spriteBatch = new SpriteBatch(); //myGameClass.spriteBatch;

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);

        textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_screen = new TextureRegion(textureAtlas.findRegion("screen"));
        tr_fon = new TextureRegion(textureAtlas.findRegion("fon"));
        tr_X = new TextureRegion(textureAtlas.findRegion("x"));
        tr_left_border = new TextureRegion(textureAtlas.findRegion("fon"));
        tr_screen_replay = new TextureRegion(textureAtlas.findRegion("screen replay"));

        stage = new Stage(viewport);
        stageReplay = new Stage(viewport);

        mainFont = createFont(Color.BLACK);
        answerFont = createFont(Color.WHITE);
        Gdx.input.setInputProcessor(stage);


        skin = new Skin();
        skin.addRegions(textureAtlas);
        font_btn = new BitmapFont();

        textButton();
        calculateCharCount();

        money = MyPreference.getMoney();
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

        if (gameWorld1.bool_timer_game){
            gameWorld1.timer_game(delta);}

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        // orthographicCamera.update();
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        spriteBatch.draw(tr_screen, tr_screen_x, tr_screen_y, tr_screen_width, tr_screen_height);

        mainFont.draw(spriteBatch, gameWorld1.getString_to_screen(), text_pryklad_x, text_pryklad_y);
        mainFont.draw(spriteBatch, gameWorld1.getString_input(), text_pryklad_x + 20 + Utill.getTextWidth(mainFont, gameWorld1.getString_to_screen()), text_pryklad_y);
        text_time.draw(spriteBatch, String.valueOf(gameWorld1.int_timer) , text_time_x, text_time_y);
        score_text_font.draw(spriteBatch, "Score: ", score_text_x, score_text_y);
        score_value_font.draw(spriteBatch, gameWorld1.getString_score(), score_value_x, score_value_y);
        best_score_text_font.draw(spriteBatch, "BS: ", best_score_text_x, best_score_text_y);
        best_score_value_font.draw(spriteBatch, bestScore + "", best_score_value_x, best_score_value_y);
        spriteBatch.draw(tr_left_border, tr_left_border_x, tr_left_border_y, tr_left_border_width, tr_left_border_height);
        spriteBatch.end();

        stage.act(delta);
        stage.draw();

        stageReplay.getBatch().begin();
        if (gameWorld1.bool_replay){
            stageReplay.getBatch().draw(tr_screen_replay,tr_screen_replay_x, tr_screen_replay_y, tr_screen_replay_width, tr_screen_replay_height);
            replay_score_value_font.draw(stageReplay.getBatch(), gameWorld1.getString_score(), replay_score_value_x, replay_score_value_y);
            replay_best_score_value_font.draw(stageReplay.getBatch(), String.valueOf(bestScore), replay_best_score_value_x, replay_best_score_value_y);
        }
        stageReplay.getBatch().end();

        if (gameWorld1.bool_replay){
            bool_draw_replay_btn = true;
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
        mainFont.dispose();
        text_score.dispose();
        text_best_score.dispose();
        text_time.dispose();
        spriteBatch.dispose();
    }

    public void variables() {
        score_text_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        score_text_font.getData().setScale(0.5f, 0.5f);

        score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        score_value_font.getData().setScale(0.6f, 0.6f);

        best_score_text_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        best_score_text_font.getData().setScale(0.5f, 0.5f);

        best_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
        best_score_value_font.getData().setScale(0.6f, 0.6f);

        text_time = new BitmapFont(Gdx.files.internal("bitmapfont/game text time.fnt"), Gdx.files.internal("bitmapfont/game text time.png"), false);
        text_time.getData().setScale(1.3f, 1.3f);

        replay_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        replay_score_value_font.getData().setScale(0.8f, 0.8f);

        replay_best_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
        replay_best_score_value_font.getData().setScale(0.9f, 0.9f);



        width_btn = 185;
        height_btn = 185;
        width_btn_C = 76;
        height_btn_C = 110;
        width_X = 80;
        tr_screen_width = 660;
        tr_screen_height = 200;
        vidstan_width = 20;
        vidstan_height = 18;
        tr_left_border_width = 30;
        tr_left_border_height = 200;
        tr_screen_replay_width = 720;
        tr_screen_replay_height = 1280;
        btn_replay_width = 170;
        btn_replay_height = 170;
        btn_function_width = 100;
        btn_function_height = 100;
        btn_back_width = 100;
        btn_back_height = 100;


        tr_screen_x = screen_width / 2 - (tr_screen_width / 2);
        tr_left_border_x = 0;
        btn_minus_x = (screen_width - (width_btn * 3 + vidstan_width * 2)) / 2;

        btn_answer_x = (screen_width - (width_btn * 3 + vidstan_width * 2)) / 2 + width_btn * 2 + vidstan_width * 2;
        btn_C_x = (screen_width - tr_screen_width) / 2 + tr_screen_width - 90;
        tr_X_x = screen_width / 2 - (width_X / 2);
        best_score_text_x = 18;
        best_score_value_x = 90;
        score_text_x = screen_width - 180;
        score_value_x = score_text_x + 126;
        text_time_x = screen_width / 2 - 27;
        text_pryklad_x = tr_screen_x + 20;
        text_vidp_x = btn_C_x - 200;

        tr_X_y = screen_height - width_X - 20;
        tr_screen_y = tr_X_y - 40 - tr_screen_height;
        tr_left_border_y = tr_screen_y;
        btn_C_y = tr_screen_y + (tr_screen_height / 2 - 60);

        tr_screen_replay_x = screen_width/2 - tr_screen_replay_width/2;
        btn_replay_x = screen_width/2 - btn_replay_width/2;
        btn_function_x = btn_replay_x + btn_replay_width + 70;
        btn_back_x = btn_replay_x - 70 - btn_back_width;
        replay_score_value_x = screen_width/2 - 30;
        replay_best_score_value_x = screen_width/2 - 30;


        btn_minus_y = tr_screen_y - height_btn - 98 - vidstan_height * 3 - height_btn * 3;
        btn_answer_y = tr_screen_y - height_btn - 98 - vidstan_height * 3 - height_btn * 3;
        text_time_y = screen_height - 20;
        score_text_y = screen_height - 40;
        score_value_y = score_text_y + 4;
        best_score_text_y = screen_height - 40;
        best_score_value_y = best_score_text_y + 4;
        text_pryklad_y = tr_screen_y + tr_screen_height / 2 + 25;

        width_btn_replay = 400;
        height_btn_replay = 100;
        btn_replay_y = 340;
        icon_y = score_value_y - 64;

        tr_screen_replay_y = 10;
        replay_score_value_y = 600;
        replay_best_score_value_y = 760;
        btn_replay_y = 300;
        btn_back_y = 290;
        btn_function_y = 290;

        bestScore = MyPreference.getBSGame1();
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


    public void textButton() {
        Gdx.app.log("log", "text button");

        //остання цифра в методі CreateButton це цифра яку ми будемо додаватив текстове поле,
        // якщо -1 то не додаємо нічого
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("btn answer");
        style.down = skin.getDrawable("btn answer press");
        style.font = answerFont;
        TextButton btn_answer = new TextButton(" ", style);
        stage.addActor(btn_answer);
        btn_answer.setPosition(btn_answer_x, btn_answer_y);
        btn_answer.setSize(width_btn, height_btn);
        btn_answer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (gameWorld1.checkIsAnswerTrue()) {
                    text_pryklad_x = tr_screen_x + 20;
                    ++myScore;
                    if (myScore > bestScore) {
                      //  tr_cup = new Texture("tr_cup_yellow.png");
                        bestScore = myScore;
                        MyPreference.setBSGame1(bestScore);
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
                gameWorld1.setString_input("-");
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
                gameWorld1.prees_C();
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
        style.font = mainFont;
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

    void btnInReplay(){
        TextButton.TextButtonStyle btn_replay_style = new TextButton.TextButtonStyle();
        btn_replay_style.up = skin.getDrawable("btn replay");
        btn_replay_style.down = skin.getDrawable("btn replay press");
        btn_replay_style.font = font_btn;

        btn_replay = new TextButton(" ", btn_replay_style);
        btn_replay.setSize(btn_replay_width, btn_replay_height);
        btn_replay.setPosition(btn_replay_x, btn_replay_y);
        Gdx.app.log("", btn_replay_x + "");
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
            btnInReplay();
            stageReplay.addActor(btn_replay);   stageReplay.addActor(btn_back);   stageReplay.addActor(btn_function);
            bool_draw_replay_btn = false;
        }

        stageReplay.act();
        stageReplay.draw();
    }

    void replay_false(){
        gameWorld1.bool_replay = false;
        btn_replay.remove();  btn_back.remove();  btn_function.remove();
        gameWorld1.float_timer = 15;
        gameWorld1.buildGame();
    }

    //Вираховує скільки символів може вміститися без зсуву
    void calculateCharCount() {
        float size_to_answer = btn_C_x - 40 - 50 - Utill.getTextWidth(mainFont, gameWorld1.getString_to_screen());
        num_of_char = (int) (size_to_answer / Utill.getTextWidth(mainFont, "8"));
    }
}
