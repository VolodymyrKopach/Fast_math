package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
 * Created by Руслан on 30.08.2017.
 */

public class ReplayDialog {
    private TextureRegion tr_screen_replay;
    private float btn_replay_size;
    private float btn_replay_y;
    private float btn_function_x;
    private float btn_back_x;
    private BitmapFont replay_best_score_value_font;
    private BitmapFont replay_score_value_font;
    private float tr_screen_replay_x;
    private float tr_screen_replay_y;
    private int replay_best_score_value_y;
    private int replay_score_value_y;
    private int score_value_x;
    private OrthographicCamera orthographicCamera;
    private Viewport viewport;
    private float btn_replay_x;
    int screen_height = 1280;
    private final int screen_width = 720;
    private int btn_function_size;
    private int btn_function_y;
    ReplayListener listener;
    private boolean FLAG_SHOW_DIALOG = false;
    private TextButton btn_replay;
    private TextButton btn_back;
    private TextButton btn_function;
    private Stage stageReplay;
    TextureAtlas textureAtlas;
    Skin replaySkin;

    public ReplayDialog() {
        createDialogAsynk();
    }

    private void createDialogAsynk() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                orthographicCamera = new OrthographicCamera();
                viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
                stageReplay = new Stage(viewport);
                replaySkin = new Skin();
                textureAtlas = new TextureAtlas("texture/TextureAtlas.atlas");
                replaySkin.addRegions(textureAtlas);
                tr_screen_replay = new TextureRegion(textureAtlas.findRegion("screen replay"));

                btn_replay_size = 170;
                btn_replay_y = 300;
                btn_replay_x = screen_width / 2 - btn_replay_size / 2;
                btn_function_size = 100;
                btn_function_y = 290;
                btn_replay_x = screen_width / 2 - btn_replay_size / 2;
                btn_function_x = btn_replay_x + btn_replay_size + 70;
                btn_back_x = btn_replay_x - 70 - 100;

                replay_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
                replay_score_value_font.getData().setScale(0.8f, 0.8f);

                replay_best_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
                replay_best_score_value_font.getData().setScale(0.9f, 0.9f);

                tr_screen_replay_x = screen_width / 2 - screen_width / 2;
                tr_screen_replay_y = 10;

                replay_score_value_y = 600;
                replay_best_score_value_y = 760;

                score_value_x = screen_width / 2 - 30;
            }
        };
        Gdx.app.postRunnable(r);
    }

    public void show() {
        FLAG_SHOW_DIALOG = true;
    }

    private void btnInReplay() {
        BitmapFont bitmapFont = new BitmapFont();
        TextButton.TextButtonStyle btn_replay_style = new TextButton.TextButtonStyle();
        btn_replay_style.up = replaySkin.getDrawable("btn replay");
        btn_replay_style.down = replaySkin.getDrawable("btn replay press");
        btn_replay_style.font = bitmapFont;

        btn_replay = new TextButton(" ", btn_replay_style);
        btn_replay.setSize(btn_replay_size, btn_replay_size);
        btn_replay.setPosition(btn_replay_x, btn_replay_y);
        Gdx.app.log("", btn_replay_x + "");
        btn_replay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    listener.onReplay();
                } catch (NullPointerException e) {
                    Gdx.app.log("tag", "Listener Not Found");
                }
                hide();
            }
        });

        TextButton.TextButtonStyle btn_back_style = new TextButton.TextButtonStyle();
        btn_back_style.up = replaySkin.getDrawable("btn back");
        btn_back_style.down = replaySkin.getDrawable("btn back press");
        btn_back_style.font = bitmapFont;

        btn_back = new TextButton(" ", btn_back_style);
        btn_back.setSize(btn_function_size, btn_function_size);
        btn_back.setPosition(btn_back_x, btn_function_y);
        btn_back.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    listener.onBack();
                } catch (NullPointerException e) {
                    hide();
                }
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        TextButton.TextButtonStyle btn_function_style = new TextButton.TextButtonStyle();
        btn_function_style.up = replaySkin.getDrawable("btn function");
        btn_function_style.down = replaySkin.getDrawable("btn function press");
        btn_function_style.font = bitmapFont;

        btn_function = new TextButton(" ", btn_function_style);
        btn_function.setSize(btn_function_size, btn_function_size);
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

    public void render(SpriteBatch spriteBatch, int myScore, int bestScore) {
        if (FLAG_SHOW_DIALOG) {
            spriteBatch.draw(tr_screen_replay, tr_screen_replay_x, tr_screen_replay_y, screen_width, screen_height);
            replay_score_value_font.draw(spriteBatch, myScore + "", score_value_x, replay_score_value_y);
            replay_best_score_value_font.draw(spriteBatch, bestScore + "", score_value_x, replay_best_score_value_y);
            btnInReplay();
            stageReplay.addActor(btn_replay);
            stageReplay.addActor(btn_back);
            stageReplay.addActor(btn_function);
            Gdx.input.setInputProcessor(stageReplay);
            stageReplay.act();
            stageReplay.draw();
        }
    }

    public void hide() {
        FLAG_SHOW_DIALOG = false;
    }

    public void setListener(ReplayListener listener) {
        this.listener = listener;
    }

    public boolean isShow() {
        return FLAG_SHOW_DIALOG;
    }

    static abstract class ReplayListener {
        abstract void onReplay();

        abstract void onBack();
    }
}
