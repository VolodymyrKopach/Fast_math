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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by vova on 25.05.17.
 */

public class MenuScreen implements Screen, GestureDetector.GestureListener {
    MyGameClass myGameClass;
    Sound clickSound;

    public TextureAtlas textureAtlas;
    public TextureRegion tr_menu_fon, tr_menu_blocked_game, tr_menu_game_1, tr_menu_game_2, tr_menu_game_3, tr_menu_game_4, tr_coin;
    public TextButton btn_play_1, btn_play_2, btn_play_3, btn_play_4, btn_setting, btn_exit, btn_coin;
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
    public float btn_coin_width, btn_coin_height, width_tr_game_1, height_tr_game_1, width_tr_game_2, height_tr_game_2, width_tr_game_3, height_tr_game_3, width_tr_game_4, height_tr_game_4, width_btn_play, height_btn_play, width_btn_setting, height_btn_setting, width_btn_exit, height_btn_exit, tr_coin_width, tr_coin_height, width_tr_level_text, height_tr_text;
    public float btn_coin_x, btn_coin_y, btn_play_1_x, btn_play_1_y, btn_play_2_x, btn_play_2_y, btn_play_3_x, btn_play_3_y, btn_play_4_x, btn_play_4_y, btn_setting_x, btn_setting_y, btn_exit_x, btn_exit_y, tr_menu_game_1_x, tr_menu_game_1_y, tr_menu_game_2_x, tr_menu_game_2_y, tr_menu_game_3_x, tr_menu_game_3_y, tr_menu_game_4_x, tr_menu_game_4_y, tr_coin_x, tr_coin_y;
    float f = 0;

    boolean bool_block_game_1 = true; //переміна, яка буде не давати tr_game_1_пересуватись в право
    boolean bool_action_swipe; // переміна, яка буде вмикатись при свайпі, і цим самим вмикати переміщення

    String string_to_swipe_game;

    final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕ" +
            "ЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

    BitmapFont coinFont;

    public MenuScreen(MyGameClass myGameClass) {
        this.myGameClass = myGameClass;

        variables();

        Gdx.input.setCatchBackKey(true);

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);

        spriteBatch = new SpriteBatch();

        textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_menu_fon = new TextureRegion(textureAtlas.findRegion("menu fon"));
        tr_menu_blocked_game = new TextureRegion(textureAtlas.findRegion("blocked game"));
        tr_menu_game_1 = new TextureRegion(textureAtlas.findRegion("menu game 1"));
        tr_menu_game_2 = new TextureRegion(textureAtlas.findRegion("menu game 2"));
        tr_menu_game_3 = new TextureRegion(textureAtlas.findRegion("menu game 3"));
        tr_menu_game_4 = new TextureRegion(textureAtlas.findRegion("menu game 4"));
        tr_coin = new TextureRegion(textureAtlas.findRegion("coin"));
        //  tr_level_text = new TextureRegion(textureAtlas_ss.findRegion("level text"));

        texture_menu_fon = new Texture("texture/menu fon hd.png");

        stage = new Stage(viewport);

        inputMultiplexer = new InputMultiplexer();
        gestureDetector = new GestureDetector(this);
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(inputMultiplexer);

        skin = new Skin();
        skin.addRegions(textureAtlas);
        text_to_button = new BitmapFont();

        textButton();
        clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/clickSound.mp3"));
        //  myGameClass.bannerAdShow();

        coinFont = createFont(Color.ORANGE);
        Gdx.app.log("tag",MyPreference.getMoney()+"money");

    }
    public BitmapFont createFont(Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = font_chars;
        parameter.size = 48;
        parameter.color = color;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(9, 9, 9, 5);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {Gdx.app.exit();}


        if (bool_action_swipe){
            action_swipe(string_to_swipe_game, 40, 10, 10);
            stabilization_of_variables();
        }


        if (bool_block_game_1 == true){
            if (tr_menu_game_1_x > screen_width/2 - width_tr_game_1/2){
                tr_menu_game_1_x -= 20;
                stabilization_of_variables();
                Gdx.app.log("",""+tr_menu_game_2_x +" width " + width_tr_game_2);
            }else bool_block_game_1 = false;
        }


       // orthographicCamera.update();
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        spriteBatch.begin();
        spriteBatch.draw(texture_menu_fon, 0, 0, screen_width, screen_height);
        coinFont.draw(spriteBatch,String.valueOf(MyPreference.getMoney()),100,screen_height-35);
        spriteBatch.draw(tr_coin, tr_coin_x, tr_coin_y, tr_coin_width, tr_coin_height);
        spriteBatch.draw(tr_menu_game_1, tr_menu_game_1_x, tr_menu_game_1_y, width_tr_game_1, height_tr_game_1);
        spriteBatch.draw(tr_menu_game_2, tr_menu_game_2_x, tr_menu_game_2_y, width_tr_game_2, height_tr_game_2);
        spriteBatch.draw(tr_menu_game_3, tr_menu_game_3_x, tr_menu_game_3_y, width_tr_game_3, height_tr_game_3);
        spriteBatch.draw(tr_menu_game_4, tr_menu_game_4_x, tr_menu_game_4_y, width_tr_game_4, height_tr_game_4);
        spriteBatch.draw(tr_menu_blocked_game, tr_menu_game_3_x, tr_menu_game_3_y, width_tr_game_3, height_tr_game_3);
        spriteBatch.draw(tr_menu_blocked_game, tr_menu_game_4_x, tr_menu_game_4_y, width_tr_game_4, height_tr_game_4);
        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(inputMultiplexer);
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
        btn_coin_width = 110;
        btn_coin_height = 110;
        tr_coin_width = 50;
        tr_coin_height = 50;

        btn_setting_x = screen_width - 20 - width_btn_setting;
        btn_exit_x = 20;
        btn_coin_x = 450;

        btn_setting_y = 20;
        btn_exit_y = 20;
        btn_coin_y = 20;


        width_tr_game_1 = 400;
        width_tr_game_2 = 400;
        width_tr_game_3 = 400;
        width_tr_game_4 = 400;
        width_btn_play = 200;

        height_tr_game_1 = 700;
        height_tr_game_2 = 700;
        height_tr_game_3 = 700;
        height_tr_game_4 = 700;
        height_btn_play = 200;

        tr_menu_game_1_x = 1000;
        tr_menu_game_2_x = tr_menu_game_1_x + width_tr_game_1 + width_vidtstan;
        tr_menu_game_3_x = tr_menu_game_2_x + width_tr_game_2 + width_vidtstan;
        tr_menu_game_4_x = tr_menu_game_3_x + width_tr_game_3 + width_vidtstan;
        btn_play_1_x = tr_menu_game_1_x + (width_tr_game_1 / 2) - (width_btn_play / 2);
        btn_play_2_x = tr_menu_game_2_x + (width_tr_game_2 / 2) - (width_btn_play / 2);
        btn_play_3_x = tr_menu_game_3_x + (width_tr_game_3 / 2) - (width_btn_play / 2);
        btn_play_4_x = tr_menu_game_4_x + (width_tr_game_4 / 2) - (width_btn_play / 2);
        tr_coin_x = 30;

        tr_menu_game_1_y = 300;
        tr_menu_game_2_y = 300;
        tr_menu_game_3_y = 300;
        tr_menu_game_4_y = 300;
        btn_play_1_y = tr_menu_game_1_y + (height_tr_game_1 / 2) - (width_btn_play / 2) - 25;
        btn_play_2_y = tr_menu_game_2_y + (height_tr_game_2 / 2) - (width_btn_play / 2) - 25;
        btn_play_3_y = tr_menu_game_3_y + (height_tr_game_3 / 2) - (width_btn_play / 2) - 25;
        btn_play_4_y = tr_menu_game_4_y + (height_tr_game_4 / 2) - (width_btn_play / 2) - 25;
        tr_coin_y = 1200;
    }

    void variables_game(float velocityX) {

        if (velocityX < 0){
            string_to_swipe_game = "left";
            bool_action_swipe = true;
        }else if (velocityX > 0){
            string_to_swipe_game = "right";
            bool_action_swipe = true;
        }

    }

    void stabilization_of_variables(){
        tr_menu_game_2_x = tr_menu_game_1_x + width_tr_game_1 + width_vidtstan;
        tr_menu_game_3_x = tr_menu_game_2_x + width_tr_game_2 + width_vidtstan;
        tr_menu_game_4_x = tr_menu_game_3_x + width_tr_game_3 + width_vidtstan;

        btn_play_1_x = tr_menu_game_1_x + (width_tr_game_1 / 2) - (width_btn_play / 2);
        btn_play_2_x = tr_menu_game_2_x + (width_tr_game_2 / 2) - (width_btn_play / 2);
        btn_play_3_x = tr_menu_game_3_x + (width_tr_game_3 / 2) - (width_btn_play / 2);
        btn_play_4_x = tr_menu_game_4_x + (width_tr_game_4 / 2) - (width_btn_play / 2);


        btn_play_1_y = tr_menu_game_1_y + (height_tr_game_1 / 2) - (width_btn_play / 2) - 25;
        btn_play_2_y = tr_menu_game_2_y + (height_tr_game_2 / 2) - (width_btn_play / 2) - 25;
        btn_play_3_y = tr_menu_game_3_y + (height_tr_game_3 / 2) - (width_btn_play / 2) - 25;
        btn_play_4_y = tr_menu_game_4_y + (height_tr_game_4 / 2) - (width_btn_play / 2) - 25;

        stage.clear();
        textButton();
    }

    void action_swipe(String string_to_swipe_game , float velocity, float width_plus, float height_plus) {
        Gdx.app.log("action swipe", "");

        if (string_to_swipe_game.equals("left")){
            tr_menu_game_1_x -= velocity;
            if (tr_menu_game_1_x < -1280){
                tr_menu_game_1_x = -1280;
                bool_action_swipe = false;
            }else stop_action(velocity);

           /* if (tr_menu_game_1_x > 580 && tr_menu_game_1_x < 700){
                width_tr_game_1 += width_plus;
                height_tr_game_1 += height_plus;
            }else if (tr_menu_game_2_x > 580 && tr_menu_game_2_x < 700){
                width_tr_game_2 += width_plus;
                height_tr_game_2 += height_plus;
            }else if (tr_menu_game_3_x > 580 && tr_menu_game_3_x < 700){
                width_tr_game_3 += width_plus;
                height_tr_game_3 += height_plus;
            }else if (tr_menu_game_4_x > 580 && tr_menu_game_4_x < 700){
                width_tr_game_4 += width_plus;
                height_tr_game_4 += height_plus;
            }

            if (tr_menu_game_1_x+width_tr_game_1 > 20 && tr_menu_game_1_x+width_tr_game_1 < 150){
                width_tr_game_1 -= width_plus;
                height_tr_game_1 -= height_plus;
            }else if (tr_menu_game_2_x+width_tr_game_2 > 20 && tr_menu_game_2_x+width_tr_game_2 < 150){
                width_tr_game_2 -= width_plus;
                height_tr_game_2 -= height_plus;
            }else if (tr_menu_game_3_x+width_tr_game_3 > 20 && tr_menu_game_3_x+width_tr_game_3 < 150){
                width_tr_game_3 -= width_plus;
                height_tr_game_3 -= height_plus;
            }else if (tr_menu_game_4_x+width_tr_game_4 > 20 && tr_menu_game_4_x+width_tr_game_4 < 150){
                width_tr_game_4 -= width_plus;
                height_tr_game_4 -= height_plus;
            }  */
        }else if (string_to_swipe_game.equals("right")) {
            tr_menu_game_1_x += velocity;
            if (tr_menu_game_1_x > 160) {
                tr_menu_game_1_x = 160;
                bool_action_swipe = false;
            } else stop_action(velocity);

          /*  if (tr_menu_game_1_x > 580 && tr_menu_game_1_x < 700){
                width_tr_game_1 -= width_plus;
                height_tr_game_1 -= height_plus;
            }else if (tr_menu_game_2_x > 580 && tr_menu_game_2_x < 700){
                width_tr_game_2 -= width_plus;
                height_tr_game_2 -= height_plus;
            }else if (tr_menu_game_3_x > 580 && tr_menu_game_3_x < 700){
                width_tr_game_3 -= width_plus;
                height_tr_game_3 -= height_plus;
            }else if (tr_menu_game_4_x > 580 && tr_menu_game_4_x < 700){
                width_tr_game_4 -= width_plus;
                height_tr_game_4 -= height_plus;
            }

            if (tr_menu_game_1_x+width_tr_game_1 > 20 && tr_menu_game_1_x+width_tr_game_1 < 150){
                width_tr_game_1 += width_plus;
                height_tr_game_1 += height_plus;
            }else if (tr_menu_game_2_x+width_tr_game_2 > 20 && tr_menu_game_2_x+width_tr_game_2 < 150){
                width_tr_game_2 += width_plus;
                height_tr_game_2 += height_plus;
            }else if (tr_menu_game_3_x+width_tr_game_3 > 20 && tr_menu_game_3_x+width_tr_game_3 < 150){
                width_tr_game_3 += width_plus;
                height_tr_game_3 += height_plus;
            }else if (tr_menu_game_4_x+width_tr_game_4 > 20 && tr_menu_game_4_x+width_tr_game_4 < 150){
                width_tr_game_4 += width_plus;
                height_tr_game_4 += height_plus;
            } */
        }

        Gdx.app.log("", tr_menu_game_1_x + " ");



    }

    void stop_action(float delta){
        f += delta;
        if (f > 470){
            f = 0;
            bool_action_swipe = false;
        }

        Gdx.app.log("2_x-1_x", tr_menu_game_2_x - tr_menu_game_1_x+" ");
        Gdx.app.log("tr_menu_1_width", width_tr_game_1 +" ");
        Gdx.app.log("tr_menu_3_width", width_tr_game_3 + " ");

        Gdx.app.log("f", " "+f);
    }

    public void textButton() {
      //  Gdx.app.log("log", "text button level");
        btn_play_style = new TextButton.TextButtonStyle();
        btn_play_style.up = skin.getDrawable("btn play");
        btn_play_style.down = skin.getDrawable("btn play press");
        btn_play_style.font = text_to_button;

        btn_play_1 = new TextButton(" ", btn_play_style);
        btn_play_1.setSize(width_btn_play, height_btn_play);
        btn_play_1.setPosition(btn_play_1_x, btn_play_1_y);
        btn_play_1.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                clickSound.play();
                Gdx.input.setInputProcessor(stage);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                myGameClass.setScreen(new GameScreen1(myGameClass));

                Gdx.input.setInputProcessor(inputMultiplexer);

            }
        });
        stage.addActor(btn_play_1);

        btn_play_2 = new TextButton(" ", btn_play_style);
        btn_play_2.setSize(width_btn_play, height_btn_play);
        btn_play_2.setPosition(btn_play_2_x, btn_play_2_y);
        btn_play_2.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                clickSound.play();
                Gdx.input.setInputProcessor(stage);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                myGameClass.setScreen(new GameScreen2(myGameClass));

                Gdx.input.setInputProcessor(inputMultiplexer);
            }
        });
        stage.addActor(btn_play_2);

        btn_play_3 = new TextButton(" ", btn_play_style);
        btn_play_3.setSize(width_btn_play, height_btn_play);
        btn_play_3.setPosition(btn_play_3_x, btn_play_3_y);
        btn_play_3.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                clickSound.play();
                Gdx.input.setInputProcessor(stage);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                myGameClass.setScreen(new GameScreen3(myGameClass));

                Gdx.input.setInputProcessor(inputMultiplexer);
            }
        });
        stage.addActor(btn_play_3);

        btn_play_4 = new TextButton(" ", btn_play_style);
        btn_play_4.setSize(width_btn_play, height_btn_play);
        btn_play_4.setPosition(btn_play_4_x, btn_play_4_y);
        btn_play_4.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                clickSound.play();
                Gdx.input.setInputProcessor(stage);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                myGameClass.setScreen(new GameScreen4(myGameClass));

                Gdx.input.setInputProcessor(inputMultiplexer);
            }
        });
        stage.addActor(btn_play_4);


        btn_setting_style = new TextButton.TextButtonStyle();
        btn_setting_style.up = skin.getDrawable("btn setting");
        btn_setting_style.down = skin.getDrawable("btn setting press");
        btn_setting_style.font = text_to_button;
        btn_setting = new TextButton(" ", btn_setting_style);
        btn_setting.setSize(width_btn_setting, height_btn_setting);
        btn_setting.setPosition(btn_setting_x, btn_setting_y);
        btn_setting.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.input.setInputProcessor(stage);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.input.setInputProcessor(inputMultiplexer);
            }
        });
        stage.addActor(btn_setting);


        btn_exit_style = new TextButton.TextButtonStyle();
        btn_exit_style.up = skin.getDrawable("btn exit");
        btn_exit_style.down = skin.getDrawable("btn exit press");
        btn_exit_style.font = text_to_button;
        btn_exit = new TextButton(" ", btn_exit_style);
        btn_exit.setSize(width_btn_exit, height_btn_exit);
        btn_exit.setPosition(btn_exit_x, btn_exit_y);
        btn_exit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.input.setInputProcessor(stage);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.exit();
            }
        });
        stage.addActor(btn_exit);

        btn_coin_style = new TextButton.TextButtonStyle();
        btn_coin_style.up = skin.getDrawable("btn coin");
        btn_coin_style.down = skin.getDrawable("btn coin press");
        btn_coin_style.font = text_to_button;
        btn_coin = new TextButton(" ", btn_coin_style);
        btn_coin.setSize(btn_coin_width, btn_coin_height);
        btn_coin.setPosition(btn_coin_x, btn_coin_y);
        btn_coin.addListener(new InputListener() {
            @Override
             public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.input.setInputProcessor(stage);
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
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
       // Gdx.app.log("","fling" + " velocityX:" + velocityX + " velocityY:"+ velocityY+" button:"+button);
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
