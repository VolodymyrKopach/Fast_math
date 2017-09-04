package kopach.fast.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by Руслан on 03.09.2017.
 */

public class MenuItem extends Actor {
    private final String text;
    private TextureRegion tex;

    private BitmapFont font;
    Texture fon;
    TextButton button;

    public MenuItem(Texture icon, String text) {
        this.text = text;
        tex = new TextureRegion(icon, 0, 0, icon.getWidth(), icon.getHeight());
        font = Utill.createFont(Color.BLACK);
        fon = new Texture("white.png");
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(new TextureRegionDrawable(new TextureRegion(icon)), new TextureRegionDrawable(new TextureRegion(icon)), new TextureRegionDrawable(new TextureRegion(icon)), new BitmapFont());
        button = new TextButton("", style);
        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("tag", "touchDown");
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Gdx.app.log("tag", "touchUp");
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(fon, getX(), getY(), getWidth(), getHeight());
//        batch.draw(tex, getX() + 50, getY() + 50, getWidth() - 100, getHeight() - 250);
        button.setPosition(getX() + 50, getY() + 50);
        button.setSize(getWidth() - 100, getHeight() - 250);

        button.draw(batch, parentAlpha);
        font.draw(batch, text, getX() + getWidth() / 2 - Utill.getTextWidth(font, text) / 2, getY() + getHeight() - 50);
    }
}
