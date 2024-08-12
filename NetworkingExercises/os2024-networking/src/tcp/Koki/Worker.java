package tcp.Koki;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//nie tuka bi trebalo sve da implementirame
public class Worker extends Thread{

    private Socket socket;

    public Worker(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader bufferedReader = null; //iscituvanje na tekstualna datoteka sto sme dobile
        BufferedWriter bufferedWriter = null; //zapisuvanje vo tekstualna datoteka - prakame podatok do klientot

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //TODO implement the HTTP protocol - client i server use HTTP protocol
            //ova na kraj go pravime , prvo dole vo klasata WebRequest pravime, posle ova - instancirame
            WebRequest webRequest = WebRequest.webRequestBuild(bufferedReader); //go koristime builder metodot
            System.out.println(webRequest.method + " " +  webRequest.url);

            bufferedWriter.write("HTTP/1.1 200 OK\n\n"); //vrakame deka baranjeto e uspesno obraboteno
            bufferedWriter.write("Content-Type: text/html\n\n");

            bufferedWriter.write("Hello " + webRequest.headers.get("User-Agent") + "!\n");
            bufferedWriter.write("You requested: " + webRequest.method + " " + webRequest.url + " by using HTTP version " + webRequest.http_version + "\n");
            bufferedWriter.write("\n");
            bufferedWriter.flush();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        finally { //ako nastane Exception so sigurnost socketot ke se zatvori. Socketot ni trosi porta
            //sve zatvarame

            try {
                this.socket.close();
                if(bufferedReader != null) bufferedReader.close();
                if(bufferedWriter != null) bufferedWriter.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    public static class WebRequest{

        //GET / HTTP/1.1
        //Host: developer.mozilla.org
        //Accept-Language: fr

        private String method;
        private String url;
        private String http_version;

        private Map<String, String> headers;

        private WebRequest(String method, String url, String http_version, Map<String, String> headers) {
            this.method = method;
            this.url = url;
            this.http_version = http_version;
            this.headers = headers;
        }

        //BufferedReader - request
        //BufferedWriter - response

        //nova instanca od ovaa klasa so pomos na staticki metod
         public static WebRequest webRequestBuild(BufferedReader reader) throws IOException { //tuka go iscituvame request-ot

            List<String> inputs = new ArrayList<>();
            String line;

            while(!(line = reader.readLine()).equals("")){ //gi iscituvam site linii
                inputs.add(line);
            }

            //GET / HTTP/1.1
             String[] parts = inputs.get(0).split("\\s+"); //ke ja zemam prvata linija od inputot

             String method = parts[0];
             String url = parts[1];
             String http_version = parts[2];

             HashMap<String, String> headers = new HashMap<>();

             //klucot ni e sve sto e pred : , vrednosta e posle :

             for (int i = 1; i < inputs.size(); i++) {
                 //headers - posle 1vata linija gi dodavam vo samata HashMapa

                 //HOST: developermozilla
                 String[] pair = inputs.get(0).split(": ", 2);
                 if(pair.length == 2) {
                     headers.put(pair[0], pair[1]);
                 }
             }

             return new WebRequest(method, url, http_version, headers);
         }
    }

    public static void main(String[] args) {

        Server server = new Server(7002);
        server.start();
    }
}

//localhost:7002 on browser