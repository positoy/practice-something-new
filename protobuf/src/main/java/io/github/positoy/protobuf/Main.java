package io.github.positoy.protobuf;

import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        Thread serverThread = new PersonServer(5050);
        serverThread.start();

        Thread.sleep(3000);

        Thread clientThread = new Thread(new PersonClient());
        clientThread.start();

        serverThread.join();
        clientThread.join();
    }

}
