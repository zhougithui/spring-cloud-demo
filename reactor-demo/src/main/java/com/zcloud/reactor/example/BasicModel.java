package com.zcloud.reactor.example;

import com.zcloud.reactor.SystemConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class BasicModel implements Runnable {
    public void run() {
        try {
            ServerSocket ss =
                    new ServerSocket(SystemConfig.SOCKET_SERVER_PORT);
            while (!Thread.interrupted())
                new Thread(new Handler(ss.accept())).start();
            //创建新线程来handle
            // or, single-threaded, or a thread pool
        } catch (IOException ex) { /* ... */ }
    }

    static class Handler implements Runnable {
        final Socket socket;
        Handler(Socket s) { socket = s; }
        public void run() {
            try {
                byte[] input = new byte[SystemConfig.INPUT_SIZE];
                socket.getInputStream().read(input);
                byte[] output = process(input);
                socket.getOutputStream().write(output);
            } catch (IOException ex) { /* ... */ }
        }
        private byte[] process(byte[] input) {
            byte[] output=null;
            /* ... */
            return output;
        }
    }
}