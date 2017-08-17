package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Руслан on 16.08.2017.
 */

public class GameScreen5 implements Screen {
    private final OrthographicCamera orthographicCamera;
    private final Viewport viewport;
    private final Stage stage;
    private final TextureRegion tr_fon;
    Texture pazlPryklad;
    Drawable pazlAnswer;
    SpriteBatch spriteBatch;
    BitmapFont pryklad;
    TextButton pryklad1, pryklad2, pryklad3, pryklad4, pryklad5;
    float screen_width = 720, screen_height = 1280;
    public TextureAtlas textureAtlas_vg;

    public GameScreen5() {
        spriteBatch = new SpriteBatch();
//        pazlAnswer = new Texture();
        pazlPryklad = new Texture("puzl.png");

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
        stage = new Stage(viewport);

        textureAtlas_vg = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_fon = new TextureRegion(textureAtlas_vg.findRegion("fon"));

        createButtons();
    }

    @Override
    public void show() {

    }

    private void createButtons() {
        pryklad1 = makeBtn("25+10");
    }

    private TextButton makeBtn(String text) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(pazlPryklad));
        style.font = new BitmapFont();
        TextButton button = new TextButton(text, style);
        stage.addActor(button);
        button.setPosition(10, 10);
        button.setSize(300, 200);
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);
        spriteBatch.end();
        Gdx.input.setInputProcessor(stage);
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
        pazlPryklad.dispose();
        spriteBatch.dispose();
    }
}
