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
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;

/**
 * JavaFX App
 */
public class App extends Application {

    // moved here so lambdas can mutate it
    private double direction = 0.0;
    private double speed = 200.0;  // add this
    private ImageView background;   // add this

    private static Scene scene;
    private Group root;

    @Override
    public void start(Stage stage) throws IOException {
        root = new Group();
        scene = new Scene(root, 640, 480);
        setTitle(stage);
 
        Context context = new Context();

        // ensure Boat exists before Map.calcNextTile() uses it
        context.setBoat(new Boat(new Position(50, 415)));
        context.getBoat().setSailDirection(Math.PI / 2);
        context.getBoat().setWindDirection(Math.PI / 2);
        context.setMap(new Map(context));

        //create 
        Arrow arrow = new Arrow(10);
        arrow.setPosition(new Position(100,100));
        java.net.URL imgUrl = getClass().getResource("/org/hack10/resources/arrow.png");
        if (imgUrl == null) {
            System.err.println("ERROR: /org/hack10/resources/arrow.png not found on classpath; using placeholder.");
        }
        ImageView arrow = new ImageView(imgUrl == null ? new Image("https://via.placeholder.com/50") : new Image(imgUrl.toExternalForm()));
        arrow.setFitWidth(50);
        arrow.setFitHeight(50);
        arrow.setX(context.getArrow().getPosition().x);
        arrow.setY(context.getArrow().getPosition().y);
        root.getChildren().add(arrow);

        // Getting current tile
        background = new ImageView(context.getMap().getCurrentTile().getImage());
        background.fitWidthProperty().bind(scene.widthProperty());
        background.fitHeightProperty().bind(scene.heightProperty());
        context.getMap().getCurrentTile().setBackground(background);
        root.getChildren().add(background);

        List<ImageView> entities = new ArrayList<>();
        for (Entity e : context.getMap().getCurrentTile().getInteractables()) {
            // add entity images to root
            ImageView entityView = new ImageView(e.getViewImage());
            entityView.setFitWidth(100);
            entityView.setFitHeight(100);
            entityView.setX(e.getPosition().x);
            entityView.setY(e.getPosition().y);
            root.getChildren().add(entityView);
            entityView.toFront();
            entities.add(entityView);
        }

        // safe resource load
        java.net.URL imgUrl2 = getClass().getResource("/org/hack10/TopDown.png");
        if (imgUrl2 == null) {
            System.err.println("ERROR: /org/hack10/TopDown.png not found on classpath; using placeholder.");
        }
        Image icon = imgUrl2 == null ? new Image("https://via.placeholder.com/64") : new Image(imgUrl2.toExternalForm());
        ImageView ship = new ImageView(icon);
        ship.setFitWidth(150);
        ship.setFitHeight(150);
        ship.setX(50);
        ship.setY(415);

        //wind stuff
        java.io.InputStream sailStream = getClass().getResourceAsStream("/org/hack10/sail.png");
        if (sailStream == null) {
            System.err.println("ERROR: /org/hack10/sail.png not found on classpath");
        }
        Image sailIcon = sailStream == null ? new Image("https://via.placeholder.com/50") : new Image(sailStream);
        ImageView sail = new ImageView(sailIcon);
        sail.setFitWidth(50);
        sail.setFitHeight(50);
        sail.setX(100);
        sail.setY(100);
        sail.toFront();

        //wind image specifically
        java.io.InputStream windStream = getClass().getResourceAsStream("/org/hack10/windparticle.png");
        if (windStream == null) {
            System.err.println("ERROR: /org/hack10/windparticle.png not found on classpath");
        }
        Image windIcon = windStream == null ? new Image("https://via.placeholder.com/20") : new Image(windStream);
        ImageView windParticle = new ImageView(windIcon);
        windParticle.setFitWidth(100);
        windParticle.setFitHeight(100);
        windParticle.setX(150);
        windParticle.setY(75);

        context.setBoat(new Boat(new Position(50, 415)));
        context.getBoat().setSailDirection(Math.PI / 2);
        context.getBoat().setWindDirection(Math.PI / 2);

        root.getChildren().add(ship);
        root.getChildren().add(sail);
        root.getChildren().add(windParticle);

        // Make scene focusable and handle keys
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case D:
                    direction += Math.PI / 16;
                    break;
                case A:
                    direction -= Math.PI / 16;
                    break;
                case LEFT:
                    if (context.getBoat().getSailDirection() <= 0) {
                        context.getBoat().setSailDirection(2 * Math.PI);
                    } else {
                        context.getBoat().setSailDirection(context.getBoat().getSailDirection() - 0.5);
                    }
                    break;
                case RIGHT:
                    if (context.getBoat().getSailDirection() >= 2 * Math.PI) {
                        context.getBoat().setSailDirection(0);
                    } else {
                        context.getBoat().setSailDirection(context.getBoat().getSailDirection() + 0.5);
                    }
                    break;
                default:
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            // optionally handle release
        });

        Label health = new Label("Health: " + context.getBoat().getHealth());
        health.setLayoutX(10);
        health.setLayoutY(10);
        root.getChildren().add(health);

        final long[] lastTime = { 0L };
        //Essentially the game loop
        AnimationTimer anim = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastTime[0] == 0) {
                    lastTime[0] = now;
                    return;
                }
                Position pos = context.getBoat().getPos();

                if (context.getBoat().getHealth() <= 0) {
                    Label gameOver = new Label("GOON!");
                    gameOver.setLayoutX(scene.getWidth() / 2 - 50);
                    gameOver.setLayoutY(scene.getHeight() / 2 - 25);
                    gameOver.setPrefSize(scene.getWidth(), scene.getHeight());
                    root.getChildren().add(gameOver);
                    this.stop();
                    Platform.exit();
                }

                if (context.getMap().isEnd(context)) {
                    System.out.println("App.Java : Reached end of tile, calculating next tile.");
                    pos = context.getBoat().getPos();
                    ship.setX(pos.x);
                    ship.setY(pos.y);
                }
                else if (context.getMap().isValidMove(context)) {
                    //ship.setX(nx);
                    //ship.setY(ny);

                    double deltaSeconds = (now - lastTime[0]) / 1_000_000_000.0;
                    lastTime[0] = now;
                    speed = 50*((2*Math.PI) - Math.abs(context.getBoat().getWindDirection() - context.getBoat().getSailDirection()));
                    double nx = pos.x + Math.cos(direction) * speed * deltaSeconds;
                    double ny = pos.y + Math.sin(direction) * speed * deltaSeconds;

                    double maxX = scene.getWidth() - ship.getBoundsInLocal().getWidth();
                    double maxY = scene.getHeight() - ship.getBoundsInLocal().getHeight();

                    if (nx < 0) nx = 0;
                    if (ny < 0) ny = 0;
                    if (nx > maxX) nx = maxX;
                    if (ny > maxY) ny = maxY;

                    context.getBoat().move(new Position(nx, ny));
                    ship.setX(pos.x);
                    ship.setX(pos.y);
                }
                else {

                    double bx = pos.x;
                    double by = pos.y;
                    double bounceDistance = 10;
                    double reversedDir = direction + Math.PI;
                    double pushX = bx + Math.cos(reversedDir) * bounceDistance;
                    double pushY = by + Math.sin(reversedDir) * bounceDistance;
                    if (context.getMap().isBorder(context, new Position(pushX, pushY))) {
                        // try reducing bounce until safe
                        for (int i = 0; i < 10; i++) {
                            bounceDistance -= 1;
                            pushX = bx + Math.cos(reversedDir) * bounceDistance;
                            pushY = by + Math.sin(reversedDir) * bounceDistance;

                            if (!context.getMap().isBorder(context, new Position(pushX, pushY))) {
                                break;
                            }
                        }
                    }
                    context.getBoat().move(new Position(pushX, pushY));
                    ship.setX(pushX);
                    ship.setY(pushY);
                    direction = reversedDir % (2 * Math.PI);        
                }
                ship.setX(pos.x);
                ship.setY(pos.y);
                sail.setX(pos.x + 40);
                sail.setY(pos.y + 50);

                double angle = Math.toDegrees(direction);
                ship.setRotate(angle);
                sail.setRotate(Math.toDegrees(context.getBoat().getSailDirection()));
                windParticle.setRotate(Math.toDegrees(context.getBoat().getWindDirection()));
                context.getBoat().angleMove(angle);

                health.setText("Health: " + context.getBoat().getHealth());

                if (context.getMap().getCurrentTile().moved == false) {
                    root.getChildren().remove(0);
                    for (ImageView e : entities) {                        
                        root.getChildren().remove(e);
                    }
                    entities.clear();

                    ImageView background = new ImageView(context.getMap().getCurrentTile().getImage());
                    background.fitWidthProperty().bind(scene.widthProperty());
                    background.fitHeightProperty().bind(scene.heightProperty());
                    root.getChildren().add(background);
                    context.getMap().getCurrentTile().setBackground(background);

                    background.toBack();
                    sail.toFront();

                    for (Entity e : context.getMap().getCurrentTile().getInteractables()) {
                        // add entity images to root
                        ImageView entityView = new ImageView(e.getViewImage());
                        entityView.setFitWidth(100);
                        entityView.setFitHeight(100);
                        entityView.setX(e.getPosition().x);
                        entityView.setY(e.getPosition().y);
                        root.getChildren().add(entityView);
                        entityView.toFront();
                        entities.add(entityView);
                    }
                }
            }
        };
        //Starts the timer
        anim.start();

        //Shows the scene
        stage.setScene(scene);
        stage.show();

        Platform.runLater(() -> root.requestFocus());
    }

    public void setTitle(Stage stage) {
        // set stage title and icon
        stage.setTitle("Odyssey");
        java.io.InputStream iconStream = getClass().getResourceAsStream("/org/hack10/shipicon.png");
        if (iconStream != null) {
            try { stage.getIcons().add(new Image(iconStream)); }
            catch (Exception e) { System.err.println("WARN: failed to load icon: " + e.getMessage()); }
        } else {
            System.err.println("WARN: /org/hack10/shipicon.png not found on classpath; skipping app icon.");
        }
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