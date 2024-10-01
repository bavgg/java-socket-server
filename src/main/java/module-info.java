module com.jonasgestopa.serversocket {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jonasgestopa.serversocket to javafx.fxml;
    exports com.jonasgestopa.serversocket;
}