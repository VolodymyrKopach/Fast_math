package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    public TextureAtlas textureAtlas_ms;
    public TextureRegion tr_menu_fon, tr_menu_game_1, tr_menu_game_2, tr_menu_game_3, tr_menu_game_4;
    public TextButton btn_play, btn_setting, btn_exit;
    public TextButton.TextButtonStyle btn_play_style, btn_setting_style, btn_exit_style;
    Skin skin;
    BitmapFont text_to_button;

    Viewport viewport;
    public OrthographicCamera orthographicCamera;
    public Stage stage_ms;

   // float x,y;

    float screen_width = 720, screen_height = 1280;
    public float width_vidtstan, height_vidstan;
    public float width_tr_level_game_1, height_tr_level_game_1, width_tr_level_game_2, height_tr_level_game_2, width_tr_level_game_3, height_tr_level_game_3, width_tr_level_game_4, height_tr_level_game_4, width_btn_play, height_btn_play, width_btn_setting, height_btn_setting, width_btn_exit, height_btn_exit, width_tr_level_text, height_tr_level_text;
    public float btn_play_x,btn_play_y, btn_setting_x,btn_setting_y, btn_exit_x,btn_exit_y, tr_menu_game_1_x, tr_menu_game_1_y, tr_menu_game_2_x, tr_menu_game_2_y, tr_menu_game_3_x, tr_menu_game_3_y, tr_menu_game_4_x, tr_menu_game_4_y;//tr_level_text_x, tr_level_text_y;

    public MenuScreen(MyGameClass myGameClass){
        this.myGameClass = myGameClass;
        menuWorld = new MenuWorld();

        variables();

        Gdx.input.setCatchBackKey(true);

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);

        textureAtlas_ms = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_menu_fon = new TextureRegion(textureAtlas_ms.findRegion("menu fon"));
        tr_menu_game_1 = new TextureRegion(textureAtlas_ms.findRegion("menu game 1"));
        tr_menu_game_2 = new TextureRegion(textureAtlas_ms.findRegion("menu game 2"));
        tr_menu_game_3 = new TextureRegion(textureAtlas_ms.findRegion("menu game 3"));
        tr_menu_game_4 = new TextureRegion(textureAtlas_ms.findRegion("menu game 4"));
      //  tr_level_text = new TextureRegion(textureAtlas_ss.findRegion("level text"));

        stage_ms = new Stage(viewport);
        stage_ms.clear();
        Gdx.input.setInputProcessor(stage_ms);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        GestureDetector gestureDetector = new GestureDetector(this);
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage_ms);

        Gdx.input.setInputProcessor(inputMultiplexer);

        skin = new Skin();
        skin.addRegions(textureAtlas_ms);
        text_to_button = new BitmapFont();

        textButton();
      //  myGameClass.bannerAdShow();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(9, 9, 9, 5);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      //  variables_game();

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            Gdx.app.exit();
        }

        orthographicCamera.update();
        myGameClass.spriteBatch.setProjectionMatrix(orthographicCamera.combined);

        myGameClass.spriteBatch.begin();
        myGameClass.spriteBatch.draw(tr_menu_fon, 0, 0, screen_width, screen_height);
        myGameClass.spriteBatch.draw(tr_menu_game_1, tr_menu_game_1_x, tr_menu_game_1_y, width_tr_level_game_1, height_tr_level_game_1);
        myGameClass.spriteBatch.draw(tr_menu_game_2, tr_menu_game_2_x, tr_menu_game_2_y, width_tr_level_game_2, height_tr_level_game_2);
        myGameClass.spriteBatch.draw(tr_menu_game_3, tr_menu_game_3_x, tr_menu_game_3_y, width_tr_level_game_3, height_tr_level_game_3);
        myGameClass.spriteBatch.draw(tr_menu_game_4, tr_menu_game_4_x, tr_menu_game_4_y, width_tr_level_game_4, height_tr_level_game_4);
      //  myGameClass.spriteBatch.draw(tr_level_text, tr_level_text_x, tr_level_text_y, width_tr_level_text, height_tr_level_text);
        myGameClass.spriteBatch.end();

        stage_ms.act(delta);
        stage_ms.draw();
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

    void variables_game(float x){
        width_tr_level_game_1 = screen_width-125*2;
        width_tr_level_game_2 = screen_width-125*2;
        width_tr_level_game_3 = screen_width-125*2;
        width_tr_level_game_4 = screen_width-125*2;
        width_btn_play = 200;

        height_tr_level_game_1 = 700;
        height_tr_level_game_2 = 700;
        height_tr_level_game_3 = 700;
        height_tr_level_game_4 = 700;
        height_btn_play = 200;

        tr_menu_game_1_x = x; //screen_width/2-width_tr_level_game_1/2;
        tr_menu_game_2_x = tr_menu_game_1_x+width_tr_level_game_1+80;
        tr_menu_game_3_x = tr_menu_game_2_x+width_tr_level_game_2+80;
        tr_menu_game_4_x = tr_menu_game_3_x+width_tr_level_game_3+80;
        btn_play_x = tr_menu_game_1_x+(width_tr_level_game_1/2)-(width_btn_play/2);

        tr_menu_game_1_y = 370;
        tr_menu_game_2_y = 370;
        tr_menu_game_3_y = 370;
        tr_menu_game_4_y = 370;

        btn_play_y = tr_menu_game_1_y+(height_tr_level_game_1/2)-(width_btn_play/2) -25;
    }

    public void variables(){
        width_vidtstan = 60;  height_vidstan = 70;

        width_btn_setting = 70; height_btn_setting = 70;
        width_btn_exit = 70; height_btn_exit = 70;
        width_tr_level_text = 480; height_tr_level_text = 60;

        btn_setting_x = 615;
        btn_exit_x = 35;
        // tr_level_text_x = Gdx.graphics.getWidth()/2-(width_tr_level_text/2); tr_level_text_y = 650;

        btn_setting_y = 1180;
        btn_exit_y = 1180;
    }


    public void textButton() {
        Gdx.app.log("log", "text button level");

        btn_play_style = new TextButton.TextButtonStyle();
        btn_play_style.up = skin.getDrawable("btn play");
        btn_play_style.down = skin.getDrawable("btn play press");
        btn_play_style.font = text_to_button;
        btn_play = new TextButton(" ", btn_play_style);
        btn_play.setSize(width_btn_play, height_btn_play);
        btn_play.setPosition(btn_play_x, btn_play_y);
        btn_play.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                menuWorld.btn_game();
                myGameClass.setScreen(new GameScreen(myGameClass));

                // dispose();

            }
        });
        stage_ms.addActor(btn_play);


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
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                // dispose();

            }
        });
        stage_ms.addActor(btn_setting);


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
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                menuWorld.btn_exit();
                Gdx.app.exit();
                // dispose();

            }
        });
        stage_ms.addActor(btn_exit);
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
       // Gdx.app.log("","tap");
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
       // Gdx.app.log("","longPress");
        return true;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
      //  Gdx.app.log("","fling" + " velocityX:" + velocityX + " velocityY:"+ velocityY+" button:"+button);
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Gdx.app.log("","pan"+" x:"+x+" y:"+ y+" deltaX:"+ deltaX +" deltaY:"+deltaY);
        variables_game(x);
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Gdx.app.log("","panStop");
        return true;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
       // Gdx.app.log("","zoom");
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
       // Gdx.app.log("","pinch");
        return false;
    }
}
