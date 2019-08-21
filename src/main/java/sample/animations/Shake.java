package sample.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {

    private TranslateTransition transition;

    public Shake(Node node) {
        transition = new TranslateTransition(Duration.millis(70), node);
        transition.setFromX(0f);
        transition.setByX(10f);
        transition.setCycleCount(4);
        transition.setAutoReverse(true);
    }

    public void playAnim() {
        transition.playFromStart();
    }


}
