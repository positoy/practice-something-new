package io.github.positoy.protobuf;

import io.github.positoy.protobuf.proto.PersonProto.*;
import lombok.SneakyThrows;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class PersonServer extends Thread{

    int PORT;
    ServerSocket serverSocket;
    Map<String, Socket> clientSockets;

    @SneakyThrows
    public PersonServer(int PORT) {
        this.PORT = PORT;
        serverSocket = new ServerSocket(PORT);
        clientSockets = new HashMap<String, Socket>();

        InetSocketAddress inetSocketAddress = (InetSocketAddress) serverSocket.getLocalSocketAddress();
        System.out.println("Server started listening " + inetSocketAddress.getAddress() + " / " + inetSocketAddress.getPort());
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            Socket welcomeSocket = serverSocket.accept();
            InetSocketAddress inetSocketAddress = (InetSocketAddress) welcomeSocket.getRemoteSocketAddress();
            System.out.println("client connected " + inetSocketAddress.getAddress() + " / " + inetSocketAddress.getPort());
            processAndClearSocket(welcomeSocket);
        }
    }

    @SneakyThrows
    public void processAndClearSocket(Socket socket) {
        OutputStream outputStream = socket.getOutputStream();

        Person person = Person.newBuilder()
                .setId(0)
                .setName("Andy")
                .setEmail("positoy@gmail.com")
                .setJob(Person.JobType.Developer)
                .setPhones(Person.PhoneNumber.newBuilder()
                        .setType(Person.PhoneType.MOBILE)
                        .setNumber("01099272703")
                        .build())
                .build();
        person.writeTo(outputStream);
        outputStream.close();
        socket.close();
    }
}
