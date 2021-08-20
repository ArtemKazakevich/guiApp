package sample.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
     private TranslateTransition tt;
     
     public Shake(Node node) {
          tt = new TranslateTransition(Duration.millis(70), node); // указываем как долго будет длиться анимация + передаем объект, который будем анимировать
          tt.setFromX(0f); // отступ по Х = 0
          tt.setByX(10f); // по оси Х смещаем на 10
          tt.setCycleCount(3); // указываем количество срабатываний анимации
          tt.setAutoReverse(true); // смещает в начальное положение
     }
     
     public void playAnim() {
          tt.playFromStart(); // запуск анимации
     }
}
