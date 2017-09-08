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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**
 * Created by vova on 22.07.17.
 */

public class GameScreen4 implements Screen {
    private static final float BTN_POINT_X = 50;
    private static final float BTN_DOWN_POINT_Y = 140;
    private static final float vidstan_width = 20;
    private static float btn_diametr;
    MyGameClass myGameClass;
    public boolean canClick = true;
    int level;
    int num_of_btn;
    public GameWorld4 gameWorld4;
    ArrayList<Integer> valuesForCheck;//використовуємо цей аррей для перевірки чи вибрана кнопка є правильною

    int trueAnswer;
    int money;

    public TextureAtlas textureAtlas;
    public TextureRegion tr_fon, tr_X, tr_propusk, tr_screen_replay;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage;

    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12, btn_13, btn_14, btn_15, btn_16, btn_17, btn_18, btn_19, btn_20, btn_21, btn_22, btn_23, btn_24, btn_25, btn_26, btn_27, btn_28, btn_29, btn_30, btn_replay, btn_back, btn_function;
    TextButton[] textButtons = new TextButton[]{btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12, btn_13, btn_14, btn_15, btn_16, btn_17, btn_18, btn_19, btn_20, btn_21, btn_22, btn_23, btn_24, btn_25, btn_26, btn_27, btn_28, btn_29, btn_30};
    Skin skin;
    BitmapFont text_score, text_best_score, text_time, font_btn, score_text_font, score_value_font, best_score_text_font, best_score_value_font;
    SpriteBatch spriteBatch;
    int bestScore;
    int myScore;
    ReplayDialog replay;

    public boolean bool_timer_game;

    float screen_width = 720, screen_height = 1280;
    int text_best_score_x;
    float best_score_text_x, best_score_text_y, best_score_value_x, best_score_value_y, score_text_x, score_text_y, score_value_x, score_value_y, text_time_x, text_time_y, text_text_ne_prav_vidp_x, text_text_ne_prav_vidp_y, text_ne_prav_vidp_y;

    public GameScreen4(final MyGameClass myGameClass) {   // метод що запускається відразу
        this.myGameClass = myGameClass;

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        level = 3;

        spriteBatch = new SpriteBatch();

        skin = new Skin();
        textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");
        skin.addRegions(textureAtlas);

        btn_diametr = (720 - (BTN_POINT_X * 2 + vidstan_width * (level))) / (level + 1);
        if (level == 1) {
            num_of_btn = 6;
        } else if (level == 2) {
            num_of_btn = 12;
        } else if (level == 3) {
            num_of_btn = 24;
        }
        variables();
        gameWorld4 = new GameWorld4(this, new GameWorld4.TimeListener() {
            @Override
            void time_tick() {
                createGame();
            }
        });
        createGame();
        Gdx.input.setCatchBackKey(true);

        tr_fon = new TextureRegion(textureAtlas.findRegion("fon"));
        tr_X = new TextureRegion(textureAtlas.findRegion("x"));
        tr_propusk = new TextureRegion(textureAtlas.findRegion("znak pytanya"));
        tr_screen_replay = new TextureRegion(textureAtlas.findRegion("screen replay"));
        font_btn = new BitmapFont();
        //  myGameClass.bannerAdShow();
        text_best_score_x = 124;
        money = MyPreference.getMoney();


        replay = new ReplayDialog();
        replay.setListener(new ReplayDialog.ReplayListener() {
            @Override
            void onReplay() {
                myScore = 0;
                canClick = true;
                createGame();
                Gdx.input.setCatchBackKey(true);
            }

            @Override
            void onBack() {
                myGameClass.setScreen(new MenuScreen(myGameClass));
            }
        });
    }

    //генеруємо нову гру
    void createGame() {
        Gdx.app.log("tag", "create game");

        trueAnswer = 0;
        stage.clear();
        gameWorld4.generateValue(num_of_btn);
        valuesForCheck = new ArrayList<Integer>();
        //копіюємо масив
        for (int i = 0; i < num_of_btn; i++) {
            valuesForCheck.add(gameWorld4.values.get(i));
        }
        //сортуємо від меншого до більшого
        for (int i = 0; i < valuesForCheck.size(); i++) {
            for (int j = 0; j < valuesForCheck.size(); j++) {
                if (valuesForCheck.get(i) < valuesForCheck.get(j)) {
                    int temp = valuesForCheck.get(i);
                    valuesForCheck.set(i, valuesForCheck.get(j));
                    valuesForCheck.set(j, temp);
                }
            }
        }
        createTextButtons();
        updateButtonText();

        bool_timer_game = true;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (bool_timer_game) {
            gameWorld4.timer_game(delta);
        }

        if (gameWorld4.bool_timer_wait_start) {
            gameWorld4.timer_wait(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {    //  спрацьовує коли нажато Back
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        text_time.draw(spriteBatch, gameWorld4.getTimer_game(), (screen_width / 2) - Utill.getTextWidth(text_time, gameWorld4.getTimer_game()) / 2, text_time_y);
        score_text_font.draw(spriteBatch, "Score: ", score_text_x, score_text_y);
        score_value_font.draw(spriteBatch, myScore + "", score_value_x, score_value_y);
        best_score_text_font.draw(spriteBatch, "BS: ", best_score_text_x, best_score_text_y);
        best_score_value_font.draw(spriteBatch, bestScore + "", best_score_value_x, best_score_value_y);

        spriteBatch.end();

        Gdx.input.setInputProcessor(stage);
        stage.act(delta);
        stage.draw();

        if (replay.isShow()) {
            replay.render(myScore, bestScore);
        }

        MyPreference.setActiveGameAtTheMoment("game 4");
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
        text_score.dispose();
        text_best_score.dispose();
        text_time.dispose();
        spriteBatch.dispose();

    }

    void updateButtonText() {
        Gdx.app.log("tag", "update");
        for (int i = 0; i < num_of_btn; i++) {
            textButtons[i].setText(gameWorld4.getValue(i));
        }
    }


    public void variables() {   // налаштування значень Х і У для прорисовки
        score_text_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        score_text_font.getData().setScale(0.5f, 0.5f);

        score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        score_value_font.getData().setScale(0.6f, 0.6f);

        best_score_text_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        best_score_text_font.getData().setScale(0.5f, 0.5f);

        best_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
        best_score_value_font.getData().setScale(0.6f, 0.6f);

        text_time = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_time.getData().setScale(0.7f, 0.7f);

        text_text_ne_prav_vidp_x = 20;
        text_time_x = screen_width / 2 - 6;
        best_score_text_x = 18;
        best_score_value_x = 90;
        score_text_x = screen_width - 180;
        score_value_x = score_text_x + 126;

        text_text_ne_prav_vidp_y = screen_height - 50;
        text_ne_prav_vidp_y = text_text_ne_prav_vidp_y + 4;
        score_text_y = screen_height - 40;
        score_value_y = score_text_y + 4;
        best_score_text_y = screen_height - 40;
        best_score_value_y = best_score_text_y + 4;
        text_time_y = screen_height - 30;

        bestScore = MyPreference.getBSGame4();


    }


    public void createTextButtons() {   // налаштування кнопок
        Gdx.app.log("GameScreen4", "create text button");
        for (int i = 0; i < num_of_btn; i++) {
            textButtons[i] = drawButton(i + 1);
        }
    }

    private float getButtonX(int position) {
        //Придумав такий алггоритм, 530 це точка в якій малюється четверта кнопка
        float previousX = BTN_POINT_X;
        for (int i = 1; i != position; i++) {
            previousX = previousX + btn_diametr + vidstan_width;
            if (previousX > (720 - btn_diametr)) {
                previousX = BTN_POINT_X;
            }
        }
        return previousX;
    }

    private float getButtonY(int position) {
        int floor;
        //floor - ряд , рахую з низу
        int i = level + 1;
        if (position % i == 0) {
            floor = position / i;
        } else {
            floor = (position + i) / i;
        }
        if (floor == 1) {
            return 1200 - BTN_DOWN_POINT_Y - btn_diametr;
        } else {
            return 1200 - (BTN_DOWN_POINT_Y + btn_diametr * (floor) + 30 * (floor - 1));
        }
    }

    //Цей метод створює кожну кнопку по черзі
    TextButton drawButton(int position) {

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("btn krug");
        style.font = text_time;
        final TextButton textButton = new TextButton("", style);
        textButton.setSize(btn_diametr, btn_diametr);
        stage.addActor(textButton);
        final TextButton finalTextButton = textButton;
        textButton.setPosition(getButtonX(position), getButtonY(position));
        textButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("tag", "");
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("tag", "touchUp");
                if (!textButton.isDisabled()) {
                    Gdx.app.log("tag", "isDisabled=true");
                    if (canClick) {
                        Gdx.app.log("tag", "canClick=true");
                        Gdx.app.log("tag", finalTextButton.getText().toString());
                        checkIsTrueAnswer(finalTextButton.getText().toString(), finalTextButton);
                        textButton.setDisabled(true);
                    }
                }
            }

        });
        return textButton;
    }

    private void checkIsTrueAnswer(String s, TextButton textButton) {
        if (valuesForCheck.get(0) == Integer.parseInt(s)) {
            textButton.getStyle().checked = skin.getDrawable("btn krug press");
            valuesForCheck.remove(0);
            addTrueAnswer();
        } else {
            //Неправильна відповідь
            canClick = false;
            textButton.getStyle().up = skin.getDrawable("btn krug red");
            textButton.getStyle().down = skin.getDrawable("btn krug red");
            findTrueButton();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    replay.show();
                }
            }).start();
            bool_timer_game = false;
        }
    }

    //якщо відповідь правильна,до змінноЇ додаємо 1
    public void addTrueAnswer() {
        trueAnswer += 1;
        if (trueAnswer == num_of_btn) {
            //kінець гри, всі кнопки вибрано правильно
            drawAllButtonInOneColor("btn krug green");

            //TODO якщо вибрана правильна відповідь, збільшуємо score  перевіряємо рахунок для нарахування монет
            ++myScore;
            gameWorld4.i_boundaryOfGenerationNumbers += 5; // При кожному проходжені рівня межа генерації чисел збільшується на 5
            calculateMoney();
            if (myScore > bestScore) {
                bestScore = myScore;
                MyPreference.setBSGame4(myScore);
            }
        }
    }

    void drawAllButtonInOneColor(String skinUp) {
        for (int i = 0; i < num_of_btn; i++) {
            textButtons[i].getStyle().up = skin.getDrawable(skinUp);
            textButtons[i].getStyle().down = skin.getDrawable(skinUp);
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                createGame();
                canClick = true;
            }
        });
        t.start();
    }

    private void findTrueButton() {
        for (int i = 0; i < textButtons.length; i++) {
            if (Integer.parseInt(textButtons[i].getText().toString()) == valuesForCheck.get(0)) {
                textButtons[i].getStyle().up = skin.getDrawable("btn krug green");
                textButtons[i].getStyle().down = skin.getDrawable("btn krug green");
                break;
            }
        }
    }

    //нараховуємо кошти
    private void calculateMoney() {
        switch (myScore) {
            case 5:
                money += 5;
                //зберігаємо к-ть монет в преференс
                MyPreference.setMoney(money);
                break;
            case 10:
                money += 15;
                MyPreference.setMoney(money);
                break;
            case 20:
                money += 30;
                MyPreference.setMoney(money);
                break;
        }
    }
}