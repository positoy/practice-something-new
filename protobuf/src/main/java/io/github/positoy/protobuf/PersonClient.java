package io.github.positoy.protobuf;

import io.github.positoy.protobuf.proto.PersonProto.*;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.Socket;

public class PersonClient implements Runnable {
    @SneakyThrows
    @Override
    public void run() {
        Socket socket = new Socket("localhost", 5050);
        System.out.println(socket.isConnected());
        InputStream inputStream = socket.getInputStream();

        Person person = Person.parseFrom(inputStream);
        System.out.println(person.toString());

        inputStream.close();
        socket.close();
    }
}
