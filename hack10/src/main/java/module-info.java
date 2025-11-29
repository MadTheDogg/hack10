module org.hack10 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.hack10 to javafx.fxml;
    exports org.hack10;
}
