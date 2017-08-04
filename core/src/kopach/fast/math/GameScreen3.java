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

public class GameScreen3 implements Screen {
    private static final float BTN_POINT_X = 50;
    private static final float BTN_DOWN_POINT_Y = 140;
    private static final float vidstan_width = 20;
    private static final float vidstan_height = 30;
    private static float btn_diametr;
    MyGameClass myGameClass;
    int level;
    int num_of_btn;
    public GameWorld3 gameWorld3;
    ArrayList<Integer> valuesForCheck;//використовуємо цей аррей для перевірки чи вибрана кнопка є правильною

    int trueAnswer;

    public TextureAtlas textureAtlas_vg;
    public TextureRegion tr_fon, tr_X, tr_propusk;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage;


    public TextButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12, btn_13, btn_14, btn_15, btn_16, btn_17, btn_18, btn_19, btn_20, btn_21, btn_22, btn_23, btn_24, btn_25, btn_26, btn_27, btn_28, btn_29, btn_30;
    TextButton[] textButtons = new TextButton[]{btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_10, btn_11, btn_12, btn_13, btn_14, btn_15, btn_16, btn_17, btn_18, btn_19, btn_20, btn_21, btn_22, btn_23, btn_24, btn_25, btn_26, btn_27, btn_28, btn_29, btn_30};
    Skin skin;
    BitmapFont text_to_button;
    BitmapFont text_text_score, text_score, text_text_best_score, text_best_score, text_time;
    SpriteBatch spriteBatch;


    float screen_width = 720, screen_height = 1280;
    float text_text_score_x, text_score_x, text_text_score_y, text_score_y, text_time_x, text_text_ne_prav_vidp_x, text_text_ne_prav_vidp_y, text_ne_prav_vidp_y;

    public GameScreen3(final MyGameClass myGameClass) {   // метод що запускається відразу
        this.myGameClass = myGameClass;

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
        stage = new Stage(viewport);
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        //TODO постав тут цифри 1-3 і запусти
        level = 3;

        spriteBatch = new SpriteBatch();

        skin = new Skin();
        textureAtlas_vg = new TextureAtlas("texture/TextureAtlas.atlas");
        skin.addRegions(textureAtlas_vg);

        btn_diametr = (720 - (BTN_POINT_X * 2 + vidstan_width * (level))) / (level + 1);
        if (level == 1) {
            num_of_btn = 6;
        } else if (level == 2) {
            num_of_btn = 12;
        } else if (level == 3) {
            num_of_btn = 24;
        }
        variables();
        gameWorld3 = new GameWorld3(this);
        createGame();
        Gdx.input.setCatchBackKey(true);

        tr_fon = new TextureRegion(textureAtlas_vg.findRegion("fon"));
        tr_X = new TextureRegion(textureAtlas_vg.findRegion("x"));
        tr_propusk = new TextureRegion(textureAtlas_vg.findRegion("znak pytanya"));
        text_to_button = new BitmapFont();
        //  myGameClass.bannerAdShow();
    }

    //генеруємо нову гру
    void createGame() {
        trueAnswer = 0;
        stage.clear();
        gameWorld3.generateValue(num_of_btn);
        valuesForCheck = new ArrayList<Integer>();
        //копіюємо масив
        for (int i = 0; i < num_of_btn; i++) {
            valuesForCheck.add(gameWorld3.values.get(i));
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
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(stage);

        gameWorld3.timer_game(delta);

        if (gameWorld3.bool_timer_wait_start) {
            gameWorld3.timer_wait(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {    //  спрацьовує коли нажато Back
            myGameClass.setScreen(new MenuScreen(myGameClass));
        }

        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);

        text_text_score.draw(spriteBatch, "Score: ", text_text_score_x, text_text_score_y);
        text_score.draw(spriteBatch, gameWorld3.getString_score(), text_score_x, text_score_y);
        text_time.draw(spriteBatch, gameWorld3.getTimer_game(), text_text_ne_prav_vidp_x, text_text_ne_prav_vidp_y);
        text_text_best_score.draw(spriteBatch, "BS:", text_text_score_x, text_text_score_y);              //Дороблю
        text_best_score.draw(spriteBatch, gameWorld3.getString_score(), text_score_x, text_score_y);      //Дороблю


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
        textureAtlas_vg.dispose();
        stage.dispose();
        skin.dispose();
        text_to_button.dispose();
        text_text_score.dispose();
        text_score.dispose();
        text_text_best_score.dispose();
        text_best_score.dispose();
        text_time.dispose();
        spriteBatch.dispose();

    }

    void updateButtonText() {
        Gdx.app.log("tag", "update");
        for (int i = 0; i < num_of_btn; i++) {
            textButtons[i].setText(gameWorld3.getValue(i));
        }
    }


    public void variables() {   // налаштування значень Х і У для прорисовки
        text_text_score = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_score = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        text_time = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_text_best_score = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        text_best_score = new BitmapFont(Gdx.files.internal("bitmapfont/red bold 70.fnt"), Gdx.files.internal("bitmapfont/red bold 70.png"), false);

        text_text_score.getData().setScale(0.5f, 0.5f);
        text_score.getData().setScale(0.6f, 0.6f);
        text_time.getData().setScale(0.7f, 0.7f);
        text_text_best_score.getData().setScale(0.5f, 0.5f);
        text_best_score.getData().setScale(0.6f, 0.6f);
        text_text_ne_prav_vidp_x = 20;
        text_time_x = screen_width / 2 - 6;
        text_text_score_x = screen_width - 200;
        text_score_x = text_text_score_x + 130;

        text_text_ne_prav_vidp_y = screen_height - 50;
        text_ne_prav_vidp_y = text_text_ne_prav_vidp_y + 4;
        text_text_score_y = text_ne_prav_vidp_y;
        text_score_y = text_text_score_y + 4;


    }


    public void createTextButtons() {   // налаштування кнопок
        Gdx.app.log("GameScreen3", "create text button");
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
            return 1200 - (BTN_DOWN_POINT_Y + btn_diametr * (floor) + vidstan_height * (floor - 1));
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
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!textButton.isDisabled()) {
                    Gdx.app.log("tag", finalTextButton.getText().toString());
                    checkIsTrueAnswer(finalTextButton.getText().toString(), finalTextButton);
                    textButton.setDisabled(true);
                }
            }

        });
        return textButton;
    }

    private void checkIsTrueAnswer(String s, TextButton textButton) {
        if (valuesForCheck.get(0) == Integer.parseInt(s)) {
            textButton.getStyle().up = skin.getDrawable("btn krug press");
            textButton.getStyle().down = skin.getDrawable("btn krug press");
            valuesForCheck.remove(0);
            addTrueAnswer();
        } else {
            //Неправильна відповідь
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
                    drawAllButtonInOneColor("btn krug red");
                }
            }).start();
        }
    }

    //якщо відповідь правильна,до змінноЇ додаємо 1
    public void addTrueAnswer() {
        trueAnswer += 1;
        if (trueAnswer == num_of_btn) {
            //інець гри, всі кнопки вибрано правильно
            drawAllButtonInOneColor("btn krug green");
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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                createGame();
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
}