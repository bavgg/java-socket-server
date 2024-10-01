package com.jonasgestopa.serversocket;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloController {
    static private Socket socket;
    static private ServerSocket serverSocket;
    static private InputStreamReader inputStreamReader;
    static private BufferedReader bufferedReader;
    static private String message;

    @FXML
    Label messageOutput;

    @FXML
    public void initialize() {
        messageOutput.setText("Waiting for connection...");
        startSocketServer();
    }

    private void startSocketServer() {
        System.out.println("Socket server started");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    serverSocket = new ServerSocket(7800);
                    while (true) {
                        System.out.println("Waiting for connection...");
                        socket = serverSocket.accept();
                        inputStreamReader = new InputStreamReader(socket.getInputStream());
                        bufferedReader = new BufferedReader(inputStreamReader);
                        message = bufferedReader.readLine();

                        System.out.println("Message received: " + message);
                        test(message);
//                        updateMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        task.messageProperty().addListener((obs, oldMessage, newMessage) -> {
            System.out.println("task liner triggered");
            System.out.println(obs);
            System.out.println(oldMessage);
            System.out.println(newMessage);
            Platform.runLater(() -> {
                if (newMessage == null){
                    messageOutput.setText(oldMessage);
                }else {
                    messageOutput.setText(newMessage);
                }

            });
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void test(String message) {
        System.out.println("test triggered message: " + message);
    }
}