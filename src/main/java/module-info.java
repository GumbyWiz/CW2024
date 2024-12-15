module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    exports com.example.demo.controller;
    exports com.example.demo.Display;
    exports com.example.demo.Level;
    exports com.example.demo.Actor.Planes;
    exports com.example.demo.Actor.Projectiles;
    exports com.example.demo.Actor;
}