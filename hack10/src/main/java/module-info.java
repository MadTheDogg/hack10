module org.hack10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens org.hack10 to javafx.fxml;
    exports org.hack10;
}
