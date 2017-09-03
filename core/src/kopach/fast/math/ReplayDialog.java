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
    PointsInGame pointsInGame;

    private TextureRegion tr_screen_replay;
    private TextureRegion tr_progressBarLine, tr_progressBarBorder;
    private TextureRegion tr_coin;
    private float tr_progressBarLine_width, tr_progressBarLine_height;
    private float tr_progressBarBorder_width, tr_progressBarBorder_height;
    private float tr_coin_width, tr_coin_height;
    private float tr_progressBarLine_x, tr_progressBarLine_y;
    private float tr_progressBarBorder_x, tr_progressBarBorder_y;
    private float tr_coin_x, tr_coin_y;
    private float btn_replay_size;
    private float btn_replay_y;
    private float btn_function_x;
    private float btn_back_x;
    private float f_scorePoint;
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
        pointsInGame = new PointsInGame();

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
                tr_coin = new TextureRegion(textureAtlas.findRegion("coin"));
                tr_progressBarLine = new TextureRegion(textureAtlas.findRegion("progress bar line"));
                tr_progressBarBorder = new TextureRegion(textureAtlas.findRegion("progress bar border"));

                tr_progressBarLine_width = 394;
                tr_progressBarBorder_width = 400;
                tr_coin_width = 45;

                tr_progressBarLine_height = 78;
                tr_progressBarBorder_height = 80;
                tr_coin_height = 45;

                btn_replay_size = 170;
                btn_replay_y = 300;
                btn_replay_x = screen_width / 2 - btn_replay_size / 2;
                btn_function_size = 100;
                btn_function_y = 290;
                btn_replay_x = screen_width / 2 - btn_replay_size / 2;
                btn_function_x = btn_replay_x + btn_replay_size + 70;
                btn_back_x = btn_replay_x - 70 - 100;
                tr_progressBarLine_x = screen_width/2 - tr_progressBarLine_width/2;
                tr_progressBarBorder_x = screen_width/2 - tr_progressBarBorder_width/2;
                tr_coin_x = screen_width/2 - tr_coin_width/2;

                replay_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
                replay_score_value_font.getData().setScale(0.8f, 0.8f);

                replay_best_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
                replay_best_score_value_font.getData().setScale(0.9f, 0.9f);

                tr_screen_replay_x = screen_width / 2 - screen_width / 2;
                tr_screen_replay_y = 10;

                replay_score_value_y = 600;
                replay_best_score_value_y = 760;

                score_value_x = screen_width / 2 - 30;

                tr_progressBarLine_y = 940;
                tr_progressBarBorder_y = 940;
                tr_coin_y = 950;
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
        getInfoActiveGameAtTheMoment(myScore, bestScore);
        updateInRender(bestScore);

        if (FLAG_SHOW_DIALOG) {
            stageReplay.getBatch().begin();
            stageReplay.getBatch().draw(tr_screen_replay, tr_screen_replay_x, tr_screen_replay_y, screen_width, screen_height);
            stageReplay.getBatch().draw(tr_progressBarLine, tr_progressBarLine_x, tr_progressBarLine_y, tr_progressBarLine_width, tr_progressBarLine_height);
            stageReplay.getBatch().draw(tr_progressBarBorder, tr_progressBarBorder_x, tr_progressBarBorder_y, tr_progressBarBorder_width, tr_progressBarBorder_height);
            stageReplay.getBatch().draw(tr_coin, tr_coin_x, tr_coin_y, tr_coin_width, tr_coin_height);
            replay_score_value_font.draw(stageReplay.getBatch(), myScore + "", score_value_x, replay_score_value_y);
            replay_best_score_value_font.draw(stageReplay.getBatch(), bestScore + "", score_value_x, replay_best_score_value_y);

            stageReplay.getBatch().end();

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

    public void getInfoActiveGameAtTheMoment(int myScore, int bestScore){
        if (MyPreference.getActiveGameAtTheMoment().equals("game 1")){
            if (bestScore > pointsInGame.INT_game1FourthScorePoint){
                f_scorePoint = pointsInGame.INT_game1FifthScorePoint;

            }if (bestScore > pointsInGame.INT_game1ThirdScorePoint){
                f_scorePoint = pointsInGame.INT_game1FourthScorePoint;

            }if (bestScore > pointsInGame.INT_game1SecondScorePoint){
                f_scorePoint = pointsInGame.INT_game1ThirdScorePoint;

            }if (bestScore > pointsInGame.INT_game1FirstScorePoint){
                f_scorePoint = pointsInGame.INT_game1SecondScorePoint;

            }else f_scorePoint = pointsInGame.INT_game1FifthScorePoint;


        } if (MyPreference.getActiveGameAtTheMoment().equals("game 2")) {
            if (bestScore > pointsInGame.INT_game2FourthScorePoint) {
                f_scorePoint = pointsInGame.INT_game2FifthScorePoint;

            }
            if (bestScore > pointsInGame.INT_game2ThirdScorePoint) {
                f_scorePoint = pointsInGame.INT_game2FourthScorePoint;

            }
            if (bestScore > pointsInGame.INT_game2SecondScorePoint) {
                f_scorePoint = pointsInGame.INT_game2ThirdScorePoint;

            }
            if (bestScore > pointsInGame.INT_game2FirstScorePoint) {
                f_scorePoint = pointsInGame.INT_game2SecondScorePoint;

            } else f_scorePoint = pointsInGame.INT_game2FifthScorePoint;


        }if (MyPreference.getActiveGameAtTheMoment().equals("game 3")) {
            if (bestScore > pointsInGame.INT_game3FourthScorePoint) {
                f_scorePoint = pointsInGame.INT_game3FifthScorePoint;

            }
            if (bestScore > pointsInGame.INT_game3ThirdScorePoint) {
                f_scorePoint = pointsInGame.INT_game3FourthScorePoint;

            }
            if (bestScore > pointsInGame.INT_game3SecondScorePoint) {
                f_scorePoint = pointsInGame.INT_game3ThirdScorePoint;

            }
            if (bestScore > pointsInGame.INT_game3FirstScorePoint) {
                f_scorePoint = pointsInGame.INT_game3SecondScorePoint;

            } else f_scorePoint = pointsInGame.INT_game3FifthScorePoint;


        }if (MyPreference.getActiveGameAtTheMoment().equals("game 4")) {
            if (bestScore > pointsInGame.INT_game4FourthScorePoint) {
                f_scorePoint = pointsInGame.INT_game4FifthScorePoint;

            }
            if (bestScore > pointsInGame.INT_game4ThirdScorePoint) {
                f_scorePoint = pointsInGame.INT_game4FourthScorePoint;

            }
            if (bestScore > pointsInGame.INT_game4SecondScorePoint) {
                f_scorePoint = pointsInGame.INT_game4ThirdScorePoint;

            }
            if (bestScore > pointsInGame.INT_game4FirstScorePoint) {
                f_scorePoint = pointsInGame.INT_game4SecondScorePoint;

            } else f_scorePoint = pointsInGame.INT_game4FifthScorePoint;


        }if (MyPreference.getActiveGameAtTheMoment().equals("game 5")) {
            if (bestScore > pointsInGame.INT_game5FourthScorePoint) {
                f_scorePoint = pointsInGame.INT_game5FifthScorePoint;

            }
            if (bestScore > pointsInGame.INT_game5ThirdScorePoint) {
                f_scorePoint = pointsInGame.INT_game5FourthScorePoint;

            }
            if (bestScore > pointsInGame.INT_game5SecondScorePoint) {
                f_scorePoint = pointsInGame.INT_game5ThirdScorePoint;

            }
            if (bestScore > pointsInGame.INT_game5FirstScorePoint) {
                f_scorePoint = pointsInGame.INT_game5SecondScorePoint;

            } else f_scorePoint = pointsInGame.INT_game5FifthScorePoint;


        }if (MyPreference.getActiveGameAtTheMoment().equals("game 6")) {
            if (bestScore > pointsInGame.INT_game6FourthScorePoint) {
                f_scorePoint = pointsInGame.INT_game6FifthScorePoint;

            }
            if (bestScore > pointsInGame.INT_game6ThirdScorePoint) {
                f_scorePoint = pointsInGame.INT_game6FourthScorePoint;

            }
            if (bestScore > pointsInGame.INT_game6SecondScorePoint) {
                f_scorePoint = pointsInGame.INT_game6ThirdScorePoint;

            }
            if (bestScore > pointsInGame.INT_game6FirstScorePoint) {
                f_scorePoint = pointsInGame.INT_game6SecondScorePoint;

            } else f_scorePoint = pointsInGame.INT_game6FifthScorePoint;
        }
    }

    public void updateInRender(int bestScore){

        tr_progressBarLine_width = 394;
        f_scorePoint = 25;
        float f_coefficient = (tr_progressBarLine_width / f_scorePoint);
        tr_progressBarLine_width = f_coefficient * bestScore;

    }
}
