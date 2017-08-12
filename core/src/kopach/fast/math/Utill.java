package kopach.fast.math;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * Created by Руслан on 12.08.2017.
 */

public class Utill {
    public static float getTextWidth(BitmapFont font,String text){
        return new GlyphLayout(font, text).width;
    }
}
