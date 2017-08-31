package kopach.fast.math;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Руслан on 17.08.2017.
 */

public class GameWorld5 {
    int maxValue = 50;

    int myScore = 0;
    int bestScore;

    Random r;

    public GameWorld5() {
        r = new Random();
        data = new ArrayList<Integer>();
        bestScore = MyPreference.getBSGame5();
    }

    ArrayList<Integer> data;

    String example1;
    String example2;
    String example3;
    String example4;

    int solution1;
    int solution2;
    int solution3;
    int solution4;

    int num1;
    int num2;
    String znak;

    public void createExamples() {
        data.clear();
        nextExample();
        example1 = num1 + znak + num2 + "=";
        if (znak.equals("+")) {
            solution1 = num1 + num2;
        } else {
            solution1 = num1 - num2;
        }
        nextExample();
        example2 = num1 + znak + num2 + "=";
        if (znak.equals("+")) {
            solution2 = num1 + num2;
        } else {
            solution2 = num1 - num2;
        }
        nextExample();
        example3 = num1 + znak + num2 + "=";
        if (znak.equals("+")) {
            solution3 = num1 + num2;
        } else {
            solution3 = num1 - num2;
        }

        nextExample();
        example4 = num1 + znak + num2 + "=";
        if (znak.equals("+")) {
            solution4 = num1 + num2;
        } else {
            solution4 = num1 - num2;
        }
        Gdx.app.log("tag", "sol1" + solution1);
        Gdx.app.log("tag", "sol2" + solution2);

        data.add(0, solution1);
        data.add(1, solution2);
        data.add(2, solution3);
        data.add(3, solution4);
        data.add(4, r.nextInt(100));

        Gdx.app.log("tag", "data.get0" + data.get(0));
    }

    private void nextExample() {
        int zn = r.nextInt(2);
        if (zn == 0) {
            znak = "+";
        } else {
            znak = "-";
        }
        num1 = r.nextInt(maxValue);
        num2 = r.nextInt(maxValue);
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    //збільшуємо свій рахунок на 1 і перевіряємо чи він не більший ніж теперішній рекорд
    public void increaseScore() {
        myScore++;
        if (myScore > bestScore) {
            bestScore = myScore;
            MyPreference.setBSGame5(bestScore);
        }
    }
}
