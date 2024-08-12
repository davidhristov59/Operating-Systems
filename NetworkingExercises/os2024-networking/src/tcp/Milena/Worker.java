package tcp.Milena;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Worker extends Thread {
    private Socket socket;
    public Worker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream())); //go iscituvame  na podatokot od vlezen stream
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())); //isprakame poraka do ...

            //TODO implement the HTTP protocol - client i server koristat HTTP protocol

            //GET baranje - koga clientot bara strana od browserot
            Request request = new Request(reader.readLine().split("\\s+"));
            System.out.println(request.method + " " + request.resource); //GET / HTTP/1.1

            String line = null;

            while (!(line = reader.readLine()).equals("")) {
                String[] parts = line.split(":\\s+", 2); //Headers - 
                request.headers.put(parts[0], parts[1]);
            }

            if (request.method.equals("POST") && request.headers.get("Content-Length") != null) {
                StringBuilder sb = new StringBuilder();
                int length = Integer.parseInt(request.headers.get("Content-Length").trim());
                while (length-- > 0)
                    sb.append((char) reader.read());

                request.body = sb.toString();
                System.out.println("BODY: " + request.body);
            }

            String clientName = Optional
                    .ofNullable(request.headers.get("User"))
                    .orElse(request.headers.get("User-Agent"));

            writer.write("HTTP/1.1 200 OK\n\n");
            writer.write("Hello, " + clientName + "!\n");
            writer.write("You requested to " + request.method + " the resource: " + request.resource + "\n");
            writer.write("\n");
            writer.flush();

            this.socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Request {
        public String method; //GET or POST
        public String resource;
        public String http_version;
        public Map<String, String> headers;
        public String body;

        public Request(String[] line) {
            this.method = line[0];
            this.resource = String.join(" ", Arrays.copyOfRange(line, 1, line.length - 1));
            this.http_version = line[line.length - 1];
            this.headers = new HashMap<>();
        }
    }
}

//Header
//GET / HTTP/1.1 - metod, resurs, version
//Host: ...
//Accept-Language: ..

//Hash mapa = Key-value vrednosti

//BufferedReader - request
//BufferedWriter - response