package org.hack10;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.hack10.gamestate.*;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        scene = new Scene(root, 640, 480);
        
        Image icon = new Image(getClass().getResource("/org/hack10/TopDown.png").toExternalForm());
        ImageView ship = new ImageView(icon);
        ship.setX(50);
        ship.setY(50);
        root.getChildren().add(ship);

        // Movement state (pixels per second)
        final double SPEED = 200.0; // change to taste
        final double[] velocity = new double[] { SPEED, 0 }; // vx, vy

        // Make scene focusable and handle keys
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case RIGHT:  { velocity[0] = SPEED;  velocity[1] = 0;  break; }
                case LEFT:   { velocity[0] = -SPEED; velocity[1] = 0;  break; }
                case DOWN:   { velocity[0] = 0;      velocity[1] = SPEED; break; }
                case UP:     { velocity[0] = 0;      velocity[1] = -SPEED; break; }
                case SPACE:  { velocity[0] = 0;      velocity[1] = 0;  break; } // stop
                // optional: diagonal with shift or additional keys
                case D:      { velocity[0] = SPEED;  velocity[1] = 0;  break; } // WASD support
                case A:      { velocity[0] = -SPEED; velocity[1] = 0;  break; }
                case S:      { velocity[0] = 0;      velocity[1] = SPEED; break; }
                case W:      { velocity[0] = 0;      velocity[1] = -SPEED; break; }
                default:     { break; }
            }
        });

        // Optional: keep moving while key held (more advanced handling uses setOnKeyReleased)
        scene.setOnKeyReleased(event -> {
            // If you want to stop when keys are released, implement logic here.
        });

        // Animation loop with delta time
        final long[] lastTime = { 0L };
        AnimationTimer anim = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime[0] == 0) {
                    lastTime[0] = now;
                    return;
                }
                double deltaSeconds = (now - lastTime[0]) / 1_000_000_000.0;
                lastTime[0] = now;

                // update position
                double nx = ship.getX() + velocity[0] * deltaSeconds;
                double ny = ship.getY() + velocity[1] * deltaSeconds;

                // optional: clamp to scene bounds (keep fully visible)
                double maxX = scene.getWidth() - ship.getBoundsInLocal().getWidth();
                double maxY = scene.getHeight() - ship.getBoundsInLocal().getHeight();
                if (nx < 0) nx = 0;
                if (ny < 0) ny = 0;
                if (nx > maxX) nx = maxX;
                if (ny > maxY) ny = maxY;

                ship.setX(nx);
                ship.setY(ny);

                // optional: rotate ship to face movement direction
                if (velocity[0] != 0 || velocity[1] != 0) {
                    double angle = Math.toDegrees(Math.atan2(velocity[1], velocity[0]));
                    ship.setRotate(angle);
                }
            }
        };
        anim.start();

        stage.setScene(scene);
        stage.show();

        // ensure focus after the window is visible
        Platform.runLater(() -> root.requestFocus());
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
        //Engine engine = new Engine();
    }

}