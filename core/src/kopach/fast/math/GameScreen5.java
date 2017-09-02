package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Руслан on 16.08.2017.
 */

public class GameScreen5 implements Screen {
    private final OrthographicCamera orthographicCamera;
    private final Viewport viewport;
    private final Stage stage;
    private final TextureRegion tr_fon;
    Texture pazlPrykladTexture;
    Texture pazlAnswerTexture, pazlOverlaps;
    SpriteBatch spriteBatch;
    TextButton answerBtn1, answerBtn2, answerBtn3, answerBtn4, answerBtn5;


    TextButton pryklad1, pryklad2, pryklad3, pryklad4;
    float screen_width = 720, screen_height = 1280;
    float pazlPrykladWidth;
    float pazlHeight;
    float pazlAnswerWidth = 200;
    float pazlVidstupY = 20;
    public TextureAtlas textureAtlas_vg;
    Rectangle touchRect;
    MyRect answer1, answer2, answer3, answer4, answer5;
    MyRect pryklad1Rect, pryklad2Rect, pryklad3Rect, pryklad4Rect;
    MyRect selectedRect;

    GameWorld5 gameWorld5;

    MyRect[] myAnswer;
    MyListener inputListener;

    float connectedPazlX;

    int answerCount = 0;
    final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕ" +
            "ЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
    private float score_text_x, best_score_text_x, best_score_text_y, score_value_x, score_value_y;
    private float score_text_y, best_score_value_x, best_score_value_y;
    private BitmapFont score_text_font, score_value_font, best_score_value_font, best_score_text_font;
    BitmapFont btnFont;
    private ReplayDialog replay;

    public GameScreen5(final MyGameClass gameClass) {
        long start = System.currentTimeMillis();
        spriteBatch = new SpriteBatch();
        replay = new ReplayDialog();
        replay.setListener(new ReplayDialog.ReplayListener() {
            @Override
            void onReplay() {
                gameWorld5.myScore = 0;
                createButtons();
            }

            @Override
            void onBack() {
                gameClass.setScreen(new MenuScreen(gameClass));

            }
        });
        pazlAnswerTexture = new Texture("puzl2.png");
        pazlOverlaps = new Texture("pazl2_overlaps.png");
        pazlPrykladTexture = new Texture("puzl.png");

        orthographicCamera = new OrthographicCamera();
        viewport = new StretchViewport(screen_width, screen_height, orthographicCamera);
        stage = new Stage(viewport);

        textureAtlas_vg = new TextureAtlas("texture/TextureAtlas.atlas");

        tr_fon = new TextureRegion(textureAtlas_vg.findRegion("fon"));

        pazlHeight = (screen_height - pazlVidstupY * 4 - 200) / 5;
        pazlPrykladWidth = screen_width / 2 - 20;
        btnFont = createFont(Color.WHITE);
        loadFonts();
        variables();

        touchRect = new Rectangle();
        gameWorld5 = new GameWorld5();
        inputListener = new MyListener();
        myAnswer = new MyRect[4];
        createButtons();
        touchRect.setSize(20, 20);
        long end = System.currentTimeMillis();
        Gdx.app.log("tag", "loadTime:" + (end - start));

        MyPreference.setActiveGameAtTheMoment("game 5");
    }

    private void variables() {
        best_score_text_x = 18;
        best_score_value_x = 90;
        score_text_x = screen_width - 180;
        score_value_x = score_text_x + 126;
        best_score_text_y = screen_height - 40;
        best_score_value_y = best_score_text_y + 4;
        score_value_y = best_score_value_y + 4;
        score_text_y = best_score_text_y;

        connectedPazlX = 10 + pazlPrykladWidth - 50;

    }

    private void loadFonts() {
        score_text_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        score_text_font.getData().setScale(0.5f, 0.5f);

        score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/green bold 70.fnt"), Gdx.files.internal("bitmapfont/green bold 70.png"), false);
        score_value_font.getData().setScale(0.6f, 0.6f);

        best_score_text_font = new BitmapFont(Gdx.files.internal("bitmapfont/black bold 70.fnt"), Gdx.files.internal("bitmapfont/black bold 70.png"), false);
        best_score_text_font.getData().setScale(0.5f, 0.5f);

        best_score_value_font = new BitmapFont(Gdx.files.internal("bitmapfont/blue bold 70.fnt"), Gdx.files.internal("bitmapfont/blue bold 70.png"), false);
        best_score_value_font.getData().setScale(0.6f, 0.6f);
    }

    private void updateButtonPosition() {
        answerBtn1.setPosition(answer1.x, answer1.y);
        answerBtn2.setPosition(answer2.x, answer2.y);
        answerBtn3.setPosition(answer3.x, answer3.y);
        answerBtn4.setPosition(answer4.x, answer4.y);
        answerBtn5.setPosition(answer5.x, answer5.y);
    }

    @Override
    public void show() {

    }

    TextButton drawPrykladPazl(String text, float posX, float posY) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(pazlPrykladTexture));
        style.font = btnFont;
        TextButton button = new TextButton(text, style);
        stage.addActor(button);
        button.setSize(pazlPrykladWidth, pazlHeight);
        button.setPosition(posX, posY);
        return button;
    }

    private void createButtons() {
        stage.clear();
        gameWorld5.createExamples();
        pryklad1 = drawPrykladPazl(gameWorld5.example1, 10, screen_height - 100 - pazlVidstupY - pazlHeight);
        pryklad2 = drawPrykladPazl(gameWorld5.example2, 10, screen_height - 100 - pazlVidstupY * 2 - pazlHeight * 2);
        pryklad3 = drawPrykladPazl(gameWorld5.example3, 10, screen_height - 100 - pazlVidstupY * 3 - pazlHeight * 3);
        pryklad4 = drawPrykladPazl(gameWorld5.example4, 10, screen_height - 100 - pazlVidstupY * 4 - pazlHeight * 4);

        ArrayList<Integer> answers = new ArrayList<Integer>(gameWorld5.getData());
        Collections.shuffle(answers);
        answerBtn1 = makeBtn(String.valueOf(answers.get(0)));
        answerBtn2 = makeBtn(String.valueOf(answers.get(1)));
        answerBtn3 = makeBtn(String.valueOf(answers.get(2)));
        answerBtn4 = makeBtn(String.valueOf(answers.get(3)));
        answerBtn5 = makeBtn(String.valueOf(answers.get(4)));

        float answerBtnX = screen_width - pazlAnswerWidth - 10;

        answer1 = new MyRect(answerBtnX, screen_height - 100 - pazlVidstupY - pazlHeight, pazlAnswerWidth, pazlHeight);
        answer1.setAnswer(Integer.parseInt(answerBtn1.getText().toString()));
        answer2 = new MyRect(answerBtnX, screen_height - 100 - pazlVidstupY * 2 - pazlHeight * 2, pazlAnswerWidth, pazlHeight);
        answer2.setAnswer(Integer.parseInt(answerBtn2.getText().toString()));
        answer3 = new MyRect(answerBtnX, screen_height - 100 - pazlVidstupY * 3 - pazlHeight * 3, pazlAnswerWidth, pazlHeight);
        answer3.setAnswer(Integer.parseInt(answerBtn3.getText().toString()));
        answer4 = new MyRect(answerBtnX, screen_height - 100 - pazlVidstupY * 4 - pazlHeight * 4, pazlAnswerWidth, pazlHeight);
        answer4.setAnswer(Integer.parseInt(answerBtn4.getText().toString()));
        answer5 = new MyRect(answerBtnX, screen_height - 100 - pazlVidstupY * 5 - pazlHeight * 5, pazlAnswerWidth, pazlHeight);
        answer5.setAnswer(Integer.parseInt(answerBtn5.getText().toString()));

        updateButtonPosition();

        float prykladX = pryklad1.getX();
        pryklad1Rect = new MyRect(prykladX, pryklad1.getY(), pryklad1.getWidth(), pryklad1.getHeight());
        pryklad2Rect = new MyRect(prykladX, pryklad2.getY(), pryklad2.getWidth(), pryklad2.getHeight());
        pryklad3Rect = new MyRect(prykladX, pryklad3.getY(), pryklad3.getWidth(), pryklad3.getHeight());
        pryklad4Rect = new MyRect(prykladX, pryklad4.getY(), pryklad4.getWidth(), pryklad4.getHeight());
        for (int i = 0; i < myAnswer.length; i++) {
            myAnswer[i] = null;
        }
        stage.addListener(inputListener);
    }

    private TextButton makeBtn(String text) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(pazlAnswerTexture));
        style.font = btnFont;
        final TextButton button = new TextButton(text, style);
        stage.addActor(button);
        button.setSize(pazlAnswerWidth, pazlHeight);
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        spriteBatch.begin();

        spriteBatch.draw(tr_fon, 0, 0, screen_width, screen_height);
        score_text_font.draw(spriteBatch, "Score: ", score_text_x, score_text_y);
        score_value_font.draw(spriteBatch, String.valueOf(gameWorld5.myScore), score_value_x, score_value_y);
        best_score_text_font.draw(spriteBatch, "BS: ", best_score_text_x, best_score_text_y);
        best_score_value_font.draw(spriteBatch, String.valueOf(gameWorld5.bestScore), best_score_value_x, best_score_value_y);
        if (replay.isShow()) {
            replay.render(spriteBatch, 1, 1);
        } else {
            Gdx.input.setInputProcessor(stage);
            stage.act(delta);
            stage.draw();
        }
        spriteBatch.end();
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
        pazlPrykladTexture.dispose();
        spriteBatch.dispose();
    }

    public BitmapFont createFont(Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("bitmapfont/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = font_chars;
        parameter.size = (int) pazlHeight / 4;
        parameter.color = color;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    class MyListener extends DragListener {
        float startDrugX;
        float startDrugY;
        float deltaY;
        float deltaX;

        TextButton selectedBtn;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            startDrugX = x;
            startDrugY = y;
            touchRect.setPosition(x, y);
            if (touchRect.overlaps(answer1)) {
                selectedRect = answer1;
                selectedBtn = answerBtn1;
            } else if (touchRect.overlaps(answer2)) {
                selectedRect = answer2;
                selectedBtn = answerBtn2;
            } else if (touchRect.overlaps(answer3)) {
                selectedRect = answer3;
                selectedBtn = answerBtn3;
            } else if (touchRect.overlaps(answer4)) {
                selectedRect = answer4;
                selectedBtn = answerBtn4;
            } else if (touchRect.overlaps(answer5)) {
                selectedRect = answer5;
                selectedBtn = answerBtn5;
            } else {
                selectedRect = null;
            }
            if (selectedRect != null) {
                if (selectedRect.getX() == connectedPazlX) {
                    if (selectedRect.overlaps(pryklad1Rect)) {
                        myAnswer[0] = null;
                    } else if (selectedRect.overlaps(pryklad2Rect)) {
                        myAnswer[1] = null;
                    } else if (selectedRect.overlaps(pryklad3Rect)) {
                        myAnswer[2] = null;
                    } else if (selectedRect.overlaps(pryklad4Rect)) {
                        myAnswer[3] = null;
                    }
                }
            }

            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            deltaX = x - startDrugX;
            deltaY = y - startDrugY;
            try {
                selectedRect.setPosition(selectedRect.x + deltaX, selectedRect.y + deltaY);
                isOverlaps(selectedRect);
            } catch (NullPointerException e) {
            }
            startDrugY = y;
            startDrugX = x;
            updateButtonPosition();
            super.drag(event, x, y, pointer);
        }

        private void isOverlaps(MyRect selectedRect) {
            TextButton.TextButtonStyle style = selectedBtn.getStyle();
            if (selectedRect.overlaps(pryklad1Rect) | (selectedRect.overlaps(pryklad2Rect)) | (selectedRect.overlaps(pryklad3Rect)) | (selectedRect.overlaps(pryklad4Rect))) {
                style.up = new TextureRegionDrawable(new TextureRegion(pazlOverlaps));
            } else {
                style.up = new TextureRegionDrawable(new TextureRegion(pazlAnswerTexture));
            }
            selectedBtn.setStyle(style);
        }


        private void checkIsAnswerTrue() {
            int trueAnswers = 0;
            for (int i = 0; i < 4; i++) {
                Gdx.app.log("tag", "myAnswer" + i + " .getAnswer()" + "=" + myAnswer[i].getAnswer());
                Gdx.app.log("tag", "data.get" + i + "=" + gameWorld5.getData().get(i));
                if (myAnswer[i].getAnswer() == gameWorld5.getData().get(i)) {
                    trueAnswers++;
                }
            }
            //TODO тут потрібно викликати ре-плей, якщо trueAnswers==4, то всі приклади вирішено правильно
            Gdx.app.log("tag", "checkIsAnswerTrue" + trueAnswers);
            if (trueAnswers >= 4) {
                gameWorld5.increaseScore();
                createButtons();
            } else {
                replay.show();
            }
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (selectedRect != null) {
                if (selectedRect.overlaps(pryklad1Rect)) {
                    selectedRect.setPosition(connectedPazlX, pryklad1.getY());
                    checkIsUnique(selectedRect);
                    myAnswer[0] = selectedRect;
                } else if (selectedRect.overlaps(pryklad2Rect)) {
                    selectedRect.setPosition(connectedPazlX, pryklad2.getY());
                    checkIsUnique(selectedRect);
                    myAnswer[1] = selectedRect;
                } else if (selectedRect.overlaps(pryklad3Rect)) {
                    selectedRect.setPosition(connectedPazlX, pryklad3.getY());
                    checkIsUnique(selectedRect);
                    myAnswer[2] = selectedRect;
                } else if (selectedRect.overlaps(pryklad4Rect)) {
                    selectedRect.setPosition(connectedPazlX, pryklad4.getY());
                    checkIsUnique(selectedRect);
                    myAnswer[3] = selectedRect;
                } else {
                    selectedRect.setPosition(selectedRect.getDefaultX(), selectedRect.getDefaultY());
                }
                for (int i = 0; i < 4; i++) {
                    if (myAnswer[i] != null) {
                        answerCount++;
                    } else {
                        answerCount = 0;
                    }
                }
                // юзер з'єднав всі 4 відповіді
                if (answerCount >= 4) {
                    checkIsAnswerTrue();
                }
            }
            TextButton.TextButtonStyle style = selectedBtn.getStyle();
            style.up = new TextureRegionDrawable(new TextureRegion(pazlAnswerTexture));
            selectedBtn.setStyle(style);
            updateButtonPosition();
            super.touchUp(event, x, y, pointer, button);
        }

        private void checkIsUnique(MyRect selectedRect) {
            for (int i = 0; i < 4; i++) {
                if (myAnswer[i] != null) {
                    MyRect rect = myAnswer[i];
                    if (rect.defaultY != selectedRect.defaultY) {
                        if ((rect.getY() == selectedRect.getY()) & (rect.getX() == selectedRect.getX())) {
                            rect.setPosition(rect.getDefaultX(), rect.getDefaultY());
                            myAnswer[i] = null;
                        }
                    }
                }
            }
        }
    }
}
