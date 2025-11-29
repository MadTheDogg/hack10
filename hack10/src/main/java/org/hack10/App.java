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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Group;
import org.hack10.entities.*;

/**
 * JavaFX App
 */
public class App extends Application {

    // moved here so lambdas can mutate it
    private double direction = 0.0;

    private static Scene scene;
    private Group root;

    @Override
    public void start(Stage stage) throws IOException {
        root = new Group();
        scene = new Scene(root, 640, 480);
        setTitle(stage);

        Context context = new Context();
        context.setMap(new Map(context));

        //Getting current tile
        ImageView background = new ImageView(context.getMap().getCurrentTile().getImage());
        background.fitWidthProperty().bind(scene.widthProperty());
        background.fitHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(background);

        // safe resource load
        java.net.URL imgUrl = getClass().getResource("/org/hack10/TopDown.png");
        if (imgUrl == null) {
            System.err.println("ERROR: /org/hack10/TopDown.png not found on classpath; using placeholder.");
        }
        Image icon = imgUrl == null ? new Image("https://via.placeholder.com/64") : new Image(imgUrl.toExternalForm());
        ImageView ship = new ImageView(icon);
        ship.setFitWidth(150);
        ship.setFitHeight(150);
        ship.setX(50);
        ship.setY(415);

        root.getChildren().add(ship);

        final double SPEED = 200.0; // pixels per second

        // Make scene focusable and handle keys
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case D: { direction += Math.PI / 16; break; }
                case A: { direction -= Math.PI / 16; break; }
                default: { break; }
            }
        });

        scene.setOnKeyReleased(event -> {
            // optionally handle release
        });

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

                double nx = ship.getX() + Math.cos(direction) * SPEED * deltaSeconds;
                double ny = ship.getY() + Math.sin(direction) * SPEED * deltaSeconds;

                double maxX = scene.getWidth() - ship.getBoundsInLocal().getWidth();
                double maxY = scene.getHeight() - ship.getBoundsInLocal().getHeight();
                if (nx < 0) nx = 0;
                if (ny < 0) ny = 0;
                if (nx > maxX) nx = maxX;
                if (ny > maxY) ny = maxY;

                ship.setX(nx);
                ship.setY(ny);

                double angle = Math.toDegrees(direction);
                ship.setRotate(angle);
            }
        };
        anim.start();

        stage.setScene(scene);
        stage.show();

        Platform.runLater(() -> root.requestFocus());
    }

    public void setTitle(Stage stage) {
        // set stage title and icon
        stage.setTitle("Odyssey");
        Image iconApp = new Image(getClass().getResourceAsStream("/org/hack10/shipicon.png"));
        stage.getIcons().add(iconApp);
        stage.setFullScreen(true);
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
    }

}