package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by vova on 25.05.17.
 */

public class MenuScreen implements Screen, GestureDetector.GestureListener {
    MyGameClass myGameClass;
    Sound clickSound;

    public TextureAtlas textureAtlas;
    public TextureRegion tr_menu_fon, tr_coin;
    Texture tr_menu_item_fon;
    public TextButton btn_play_1, btn_play_2, btn_play_3, btn_play_4,btn_play_5,btn_play_6, btn_setting, btn_exit, btn_coin;
    public TextButton.TextButtonStyle btn_play_style, btn_setting_style, btn_exit_style, btn_coin_style;
    Skin skin;
    BitmapFont text_to_button;

    Texture texture_menu_fon;

    SpriteBatch spriteBatch;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage;
    InputMultiplexer inputMultiplexer;
    GestureDetector gestureDetector;

    float screen_width = 720, screen_height = 1280;
    public float width_vidtstan, height_vidstan;
    public float btn_coin_size, width_menu_item, height_menu_item, width_btn_play, height_btn_play, width_btn_setting, height_btn_setting, width_btn_exit, height_btn_exit, tr_coin_width, tr_coin_height;
    public float btn_coin_x, btn_coin_y, btn_play_1_x, btn_play_y, btn_setting_x, btn_setting_y, btn_exit_x, btn_exit_y, tr_menu_game_1_x, tr_menu_game_y, tr_menu_game_2_x, tr_menu_game_3_x, tr_menu_game_4_x, tr_menu_game_5_x, tr_menu_game_6_x, tr_coin_x, tr_coin_y;
    float f = 0;

    boolean bool_block_game_1 = true; //переміна, яка буде не давати tr_game_1_пересуватись в право
    boolean bool_action_swipe; // переміна, яка буде вмикатись при свайпі, і цим самим вмикати переміщення

    String string_to_swipe_game;

    BitmapFont coinFont;
    BitmapFont bitmapFont;

    public MenuScreen(MyGameClass myGameClass) {
        this.myGameClass = myGameClass;

        variables();

        Gdx.input.setCatchBackKey(true);

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);

        spriteBatch = new SpriteBatch();

        textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_menu_fon = new TextureRegion(textureAtlas.findRegion("menu fon"));
        tr_menu_item_fon = new Texture("fon.png");
        tr_coin = new TextureRegion(textureAtlas.findRegion("coin"));

        texture_menu_fon = new Texture("texture/menu fon hd.png");

        stage = new Stage(viewport);
        gestureDetector = new GestureDetector(this);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(inputMultiplexer);

        skin = new Skin();
        skin.addRegions(textureAtlas);
        text_to_button = new BitmapFont();
        textButton();
        clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/clickSound.mp3"));
        //  myGameClass.bannerAdShow();

        coinFont = Utill.createFont(Color.ORANGE);
        bitmapFont = Utill.createFont(Color.BLACK);

        //  myGameClass.loadRewardedVideoAd();
    }

    @Override
    public void show() {
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(9, 9, 9, 5);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(inputMultiplexer);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }


        if (bool_action_swipe) {
            action_swipe(string_to_swipe_game, 40);
            stabilization_of_variables();
        }


        if (bool_block_game_1 == true) {
            if (tr_menu_game_1_x > screen_width / 2 - width_menu_item / 2) {
                tr_menu_game_1_x -= 20;
                stabilization_of_variables();
            } else bool_block_game_1 = false;
        }

        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(texture_menu_fon, 0, 0, screen_width, screen_height);
        coinFont.draw(spriteBatch, String.valueOf(MyPreference.getMoney()), 100, screen_height - 35);
        spriteBatch.draw(tr_coin, tr_coin_x, tr_coin_y, tr_coin_width, tr_coin_height);
        spriteBatch.draw(tr_menu_item_fon, tr_menu_game_1_x, tr_menu_game_y, width_menu_item, height_menu_item);
        spriteBatch.draw(tr_menu_item_fon, tr_menu_game_2_x, tr_menu_game_y, width_menu_item, height_menu_item);
        spriteBatch.draw(tr_menu_item_fon, tr_menu_game_3_x, tr_menu_game_y, width_menu_item, height_menu_item);
        spriteBatch.draw(tr_menu_item_fon, tr_menu_game_4_x, tr_menu_game_y, width_menu_item, height_menu_item);
        spriteBatch.draw(tr_menu_item_fon, tr_menu_game_5_x, tr_menu_game_y, width_menu_item, height_menu_item);
        spriteBatch.draw(tr_menu_item_fon, tr_menu_game_6_x, tr_menu_game_y, width_menu_item, height_menu_item);

        bitmapFont.draw(spriteBatch, "Game1", tr_menu_game_1_x + width_menu_item / 2 - Utill.getTextWidth(bitmapFont, "Game1") / 2, tr_menu_game_y + height_menu_item - 50);
        bitmapFont.draw(spriteBatch, "Game2", tr_menu_game_2_x + width_menu_item / 2 - Utill.getTextWidth(bitmapFont, "Game1") / 2, tr_menu_game_y + height_menu_item - 50);
        bitmapFont.draw(spriteBatch, "Game3", tr_menu_game_3_x + width_menu_item / 2 - Utill.getTextWidth(bitmapFont, "Game1") / 2, tr_menu_game_y + height_menu_item - 50);
        bitmapFont.draw(spriteBatch, "Game4", tr_menu_game_4_x + width_menu_item / 2 - Utill.getTextWidth(bitmapFont, "Game1") / 2, tr_menu_game_y + height_menu_item - 50);
        bitmapFont.draw(spriteBatch, "Game5", tr_menu_game_5_x + width_menu_item / 2 - Utill.getTextWidth(bitmapFont, "Game1") / 2, tr_menu_game_y + height_menu_item - 50);
        bitmapFont.draw(spriteBatch, "Game6", tr_menu_game_6_x + width_menu_item / 2 - Utill.getTextWidth(bitmapFont, "Game1") / 2, tr_menu_game_y + height_menu_item - 50);
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
        dispose();
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
        skin.dispose();
        text_to_button.dispose();
        stage.dispose();
        spriteBatch.dispose();

    }

    public void variables() {
        width_vidtstan = 80;
        height_vidstan = 70;

        width_btn_setting = 110;
        height_btn_setting = 110;
        width_btn_exit = 110;
        height_btn_exit = 110;
        btn_coin_size = 110;
        tr_coin_width = 50;
        tr_coin_height = 50;

        btn_setting_x = screen_width - 20 - width_btn_setting;
        btn_exit_x = 20;
        btn_coin_x = 450;

        btn_setting_y = 20;
        btn_exit_y = 20;
        btn_coin_y = 20;

        width_menu_item = 400;
        width_btn_play = 300;

        height_menu_item = 700;
        height_btn_play = 500;

        tr_menu_game_1_x = 1000;
        updateMenuPosition();
        tr_coin_x = 30;

        tr_menu_game_y = 300;
        btn_play_y = 350;
        tr_coin_y = 1200;
    }

    float getBtnPlayX(int position) {
        switch (position) {
            case 1:
                return tr_menu_game_1_x + (width_menu_item / 2) - (width_btn_play / 2);
            case 2:
                return tr_menu_game_2_x + (width_menu_item / 2) - (width_btn_play / 2);
            case 3:
                return tr_menu_game_3_x + (width_menu_item / 2) - (width_btn_play / 2);
            case 4:
                return tr_menu_game_4_x + (width_menu_item / 2) - (width_btn_play / 2);
            case 5:
                return tr_menu_game_5_x + (width_menu_item / 2) - (width_btn_play / 2);
            case 6:
                return tr_menu_game_6_x + (width_menu_item / 2) - (width_btn_play / 2);
        }
        return 0;
    }

    void variables_game(float velocityX) {
        if (velocityX < 0) {
            string_to_swipe_game = "left";
            bool_action_swipe = true;
        } else if (velocityX > 0) {
            string_to_swipe_game = "right";
            bool_action_swipe = true;
        }

    }

    void updateMenuPosition() {
        tr_menu_game_2_x = tr_menu_game_1_x + width_menu_item + width_vidtstan;
        tr_menu_game_3_x = tr_menu_game_2_x + width_menu_item + width_vidtstan;
        tr_menu_game_4_x = tr_menu_game_3_x + width_menu_item + width_vidtstan;
        tr_menu_game_5_x = tr_menu_game_4_x + width_menu_item + width_vidtstan;
        tr_menu_game_6_x = tr_menu_game_5_x + width_menu_item + width_vidtstan;
        Gdx.app.log("tag", "updateMenuPosition");

    }

    void updateBtnPosition() {
        btn_play_1.setPosition(getBtnPlayX(1), btn_play_y);
        btn_play_2.setPosition(getBtnPlayX(2), btn_play_y);
        btn_play_3.setPosition(getBtnPlayX(3), btn_play_y);
        btn_play_4.setPosition(getBtnPlayX(4), btn_play_y);
        btn_play_5.setPosition(getBtnPlayX(5), btn_play_y);
        btn_play_6.setPosition(getBtnPlayX(6), btn_play_y);

        Gdx.app.log("tag", "updateBtnPosition");

        stage.addActor(btn_play_1);
        stage.addActor(btn_play_2);
        stage.addActor(btn_play_3);
        stage.addActor(btn_play_4);
        stage.addActor(btn_play_5);
        stage.addActor(btn_play_6);
    }

    void stabilization_of_variables() {
        updateMenuPosition();

        Gdx.app.log("tag", "stabilization_of_variables");

        btn_play_1_x = tr_menu_game_1_x + (width_menu_item / 2) - (width_btn_play / 2);
        stage.clear();
        updateBtnPosition();
    }

    void action_swipe(String string_to_swipe_game, float velocity) {
        Gdx.app.log("tag", "actionSwipe , swipe:" + string_to_swipe_game);
        if (string_to_swipe_game.equals("left")) {
            tr_menu_game_1_x -= velocity;
            if (tr_menu_game_1_x < -2220) {
                Gdx.app.log("tag", "menu1x: " + tr_menu_game_1_x);
                tr_menu_game_1_x = -2220;
                bool_action_swipe = false;
                f = 0;
            } else stop_action(velocity);
        } else if (string_to_swipe_game.equals("right")) {
            tr_menu_game_1_x += velocity;
            if (tr_menu_game_1_x > 160) {
                tr_menu_game_1_x = 160;
                bool_action_swipe = false;
                f = 0;
            } else stop_action(velocity);

        }
    }

    void stop_action(float delta) {
        Gdx.app.log("tag", "stop action " + f);
        f += delta;
        if (f > 470) {
            f = 0;
            bool_action_swipe = false;
        }
    }

    TextButton createutton(final int position) {
        Texture texture = new Texture("texture" + position + ".png");
        btn_play_style = new TextButton.TextButtonStyle();
        btn_play_style.up = new TextureRegionDrawable(new TextureRegion(texture));
        btn_play_style.font = text_to_button;

        TextButton button = new TextButton(" ", btn_play_style);
        button.setSize(width_btn_play, height_btn_play);
        button.setPosition(getBtnPlayX(position), btn_play_y);
        stage.addActor(button);
        return button;
    }

    public void textButton() {
        btn_play_1 = createutton(1);

        btn_play_2 = createutton(2);
        btn_play_1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                myGameClass.setScreen(new GameScreen1(myGameClass));
            }
        });
        btn_play_2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                myGameClass.setScreen(new GameScreen2(myGameClass));
            }
        });

        btn_play_3 = createutton(3);
        btn_play_3.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                myGameClass.setScreen(new GameScreen3(myGameClass));
            }
        });

        btn_play_4 = createutton(4);
        btn_play_4.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                myGameClass.setScreen(new GameScreen4(myGameClass));
            }
        });
        btn_play_5 = createutton(5);
        btn_play_5.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                myGameClass.setScreen(new GameScreen5(myGameClass));
            }
        });
        btn_play_6 = createutton(6);
        btn_play_6.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                clickSound.play();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                myGameClass.setScreen(new GameScreen6(myGameClass));
            }
        });

        btn_setting_style = new TextButton.TextButtonStyle();
        btn_setting_style.up = skin.getDrawable("btn setting");
        btn_setting_style.down = skin.getDrawable("btn setting press");
        btn_setting_style.font = text_to_button;
        btn_setting = new TextButton(" ", btn_setting_style);
        btn_setting.setSize(width_btn_setting, height_btn_setting);
        btn_setting.setPosition(btn_setting_x, btn_setting_y);
        stage.addActor(btn_setting);


        btn_exit_style = new TextButton.TextButtonStyle();
        btn_exit_style.up = skin.getDrawable("btn exit");
        btn_exit_style.down = skin.getDrawable("btn exit press");
        btn_exit_style.font = text_to_button;
        btn_exit = new TextButton(" ", btn_exit_style);
        btn_exit.setSize(width_btn_exit, height_btn_exit);
        btn_exit.setPosition(btn_exit_x, btn_exit_y);
        stage.addActor(btn_exit);

        btn_coin_style = new TextButton.TextButtonStyle();
        btn_coin_style.up = skin.getDrawable("btn coin");
        btn_coin_style.down = skin.getDrawable("btn coin press");
        btn_coin_style.font = text_to_button;
        btn_coin = new TextButton(" ", btn_coin_style);
        btn_coin.setSize(btn_coin_size, btn_coin_size);
        btn_coin.setPosition(btn_coin_x, btn_coin_y);
        stage.addActor(btn_coin);
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        variables_game(velocityX);
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}