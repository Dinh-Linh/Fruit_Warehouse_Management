module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires org.kordamp.bootstrapfx.core;
    requires java.rmi;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires lombok;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.entity;
    opens com.example.demo.entity to javafx.base;
}