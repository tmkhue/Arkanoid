module org.example.game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires javafx.media;
    requires java.prefs;
    requires javafx.base;

    opens org.example.game to javafx.fxml;
    //exports org.example.game;
    exports Ball;
    opens Ball to javafx.fxml;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Main;
    opens Main to javafx.fxml;
    exports Brick;
    opens Brick to javafx.fxml;
    exports Paddle;
    opens Paddle to javafx.fxml;
    exports PowerUp;
    opens PowerUp to javafx.fxml;
}