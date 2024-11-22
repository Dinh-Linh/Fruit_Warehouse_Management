module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires org.kordamp.bootstrapfx.core;
    requires java.rmi;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires static lombok;


    opens entity to org.hibernate.orm.core, javafx.base;
    opens generator to org.hibernate.orm.core;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo.entity;
    opens com.example.demo.entity to javafx.base;
    exports dao;
    exports com.example.demo.controller;
    opens com.example.demo.controller to javafx.fxml;
    exports com.example.demo;
}