module wardlaw.mainscreen {
    requires javafx.controls;
    requires javafx.fxml;
/* TODO: This is where something went wrong, I had to google why
   data wasn't being returned on a javafx error. Turns out I had to supply
    the opens and exports portions */
    opens wardlaw.mainscreen to javafx.fxml;
    opens model to javafx.fxml;

    exports wardlaw.mainscreen;
    exports model;
}
