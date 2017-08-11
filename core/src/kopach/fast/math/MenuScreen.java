package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by vova on 25.05.17.
 */

public class MenuScreen implements Screen, GestureDetector.GestureListener {
    MyGameClass myGameClass;
    MenuWorld menuWorld;
    Sound clickSound;

    public TextureAtlas textureAtlas_ms;
    public TextureRegion tr_menu_fon, tr_menu_game_1, tr_menu_game_2, tr_menu_game_3, tr_menu_game_4;
    public TextButton btn_play_1, btn_play_2, btn_play_3, btn_play_4, btn_setting, btn_exit;
    public TextButton.TextButtonStyle btn_play_style, btn_setting_style, btn_exit_style;
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
    public float width_tr_game_1, height_tr_game_1, width_tr_game_2, height_tr_game_2, width_tr_game_3, height_tr_game_3, width_tr_game_4, height_tr_game_4, width_btn_play, height_btn_play, width_btn_setting, height_btn_setting, width_btn_exit, height_btn_exit, width_tr_level_text, height_tr_text;
    public float btn_play_1_x, btn_play_1_y, btn_play_2_x, btn_play_2_y, btn_play_3_x, btn_play_3_y, btn_play_4_x, btn_play_4_y, btn_setting_x, btn_setting_y, btn_exit_x, btn_exit_y, tr_menu_game_1_x, tr_menu_game_1_y, tr_menu_game_2_x, tr_menu_game_2_y, tr_menu_game_3_x, tr_menu_game_3_y, tr_menu_game_4_x, tr_menu_game_4_y;//tr_level_text_x, tr_level_text_y;
    float f = 0;

    boolean bool_block_game_1 = true; //переміна, яка буде не давати tr_game_1_пересуватись в право
    boolean bool_action_swipe; // переміна, яка буде вмикатись при свайпі, і цим самим вмикати переміщення

    String string_to_swipe_game;

    public MenuScreen(MyGameClass myGameClass) {
        this.myGameClass = myGameClass;
        menuWorld = new MenuWorld();

        variables();

        Gdx.input.setCatchBackKey(true);

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);

        spriteBatch = new SpriteBatch();

        textureAtlas_ms = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_menu_fon = new TextureRegion(textureAtlas_ms.findRegion("menu fon"));
        tr_menu_game_1 = new TextureRegion(textureAtlas_ms.findRegion("menu game 1"));
        tr_menu_game_2 = new TextureRegion(textureAtlas_ms.findRegion("menu game 2"));
        tr_menu_game_3 = new TextureRegion(textureAtlas_ms.findRegion("menu game 3"));
        tr_menu_game_4 = new TextureRegion(textureAtlas_ms.findRegion("menu game 4"));
        //  tr_level_text = new TextureRegion(textureAtlas_ss.findRegion("level text"));

        texture_menu_fon = new Texture("texture/menu fon hd.png");

        stage = new Stage(viewport);

        inputMultiplexer = new InputMultiplexer();
        gestureDetector = new GestureDetector(this);
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(inputMultiplexer);

        skin = new Skin();
        skin.addRegions(textureAtlas_ms);
        text_to_button = new BitmapFont();

        textButton();
        clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/clickSound.mp3"));
        //  myGameClass.bannerAdShow();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(9, 9, 9, 5);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }


        if (bool_action_swipe){
            action_swipe(string_to_swipe_game, 40, 10, 10);
            stabilization_of_variables();
           // Gdx.app.log("","render");
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
        spriteBatch.draw(tr_menu_game_1, tr_menu_game_1_x, tr_menu_game_1_y, width_tr_game_1, height_tr_game_1);
        spriteBatch.draw(tr_menu_game_2, tr_menu_game_2_x, tr_menu_game_2_y, width_tr_game_2, height_tr_game_2);
        spriteBatch.draw(tr_menu_game_3, tr_menu_game_3_x, tr_menu_game_3_y, width_tr_game_3, height_tr_game_3);
        spriteBatch.draw(tr_menu_game_4, tr_menu_game_4_x, tr_menu_game_4_y, width_tr_game_4, height_tr_game_4);
        //  myGameClass.spriteBatch.draw(tr_level_text, tr_level_text_x, tr_level_text_y, width_tr_level_text, height_tr_text);
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
        textureAtlas_ms.dispose();
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

        btn_setting_x = screen_width - 20 - width_btn_setting;
        btn_exit_x = 20;

        btn_setting_y = 20;
        btn_exit_y = 20;


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

        tr_menu_game_1_y = 300;
        tr_menu_game_2_y = 300;
        tr_menu_game_3_y = 300;
        tr_menu_game_4_y = 300;
        btn_play_1_y = tr_menu_game_1_y + (height_tr_game_1 / 2) - (width_btn_play / 2) - 25;
        btn_play_2_y = tr_menu_game_2_y + (height_tr_game_2 / 2) - (width_btn_play / 2) - 25;
        btn_play_3_y = tr_menu_game_3_y + (height_tr_game_3 / 2) - (width_btn_play / 2) - 25;
        btn_play_4_y = tr_menu_game_4_y + (height_tr_game_4 / 2) - (width_btn_play / 2) - 25;
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
            if (tr_menu_game_1_x > 1000){
                tr_menu_game_1_x = 1000;
                bool_action_swipe = false;
            }else stop_action(velocity);

            if (tr_menu_game_1_x > 580 && tr_menu_game_1_x < 700){
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
            }
        }else if (string_to_swipe_game.equals("right")){
            tr_menu_game_1_x += velocity;
            if (tr_menu_game_1_x > 200){
                tr_menu_game_1_x = 200;
            bool_action_swipe = false;
            }else stop_action(velocity);

            if (tr_menu_game_1_x > 580 && tr_menu_game_1_x < 700){
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
            }
        }



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

                myGameClass.setScreen(new GameScreen(myGameClass));

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

                myGameClass.setScreen(new GameScreen1(myGameClass));

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

                myGameClass.setScreen(new GameScreen2(myGameClass));

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

                myGameClass.setScreen(new GameScreen3(myGameClass));

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

                Gdx.input.setInputProcessor(inputMultiplexer);
            }
        });
        stage.addActor(btn_exit);
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
