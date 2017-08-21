package kopach.fast.math;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Руслан on 20.08.2017.
 */

public class MyRect extends Rectangle {
    float defaultX;

    public float getDefaultX() {
        return defaultX;
    }

    public void setDefaultX(float defaultX) {
        this.defaultX = defaultX;
    }

    public float getDefaultY() {
        return defaultY;
    }


    float defaultY;
    int answer;

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public MyRect() {
        super();
    }

    public MyRect(float x, float y, float width, float height) {
        super(x, y, width, height);
        defaultX = x;
        defaultY = y;
    }

    public MyRect(Rectangle rect) {
        super(rect);
    }
}
