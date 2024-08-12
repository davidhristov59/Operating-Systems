package tcp;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Worker extends Thread {

    private Socket socket;

    public Worker(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //TODO HTTP PROTOCOL - LAST
            WebRequest webRequest = WebRequest.createWebRequest(bufferedReader);
            System.out.println(webRequest.method + " " + webRequest.url);

            //ODGOVOR VRAKAME SO BufferedWriter - RESPONSE

            //STAVAME /n/n za flush obavezno
            bufferedWriter.write("HTTP/1.1 200 OK\n\n");
            bufferedWriter.write("Content-Type: text/html\n\n");

            bufferedWriter.write("Hello " + webRequest.headers.get("User-Agent") + "!\n");
            bufferedWriter.write("You requested " + webRequest.method + " " + webRequest.url + " by using the version " + webRequest.http_version + "\n");
            bufferedWriter.write("\n");
            bufferedWriter.flush();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        //gi zatvorame bf i br
        finally { //koristime finally za ako nastane exception sigurn oda se zatvori socketot
            try {
                this.socket.close();
                bufferedReader.close();
                bufferedWriter.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                return;
            }

        }
    }

    public static class WebRequest{ //Builder method - Singleton

        //GET / HTTP/1.1
        //Host: developer.mozilla.org
        //Accept-Language: fr

        //koristime Map - Key, Value
        Map<String, String> headers;

        //GET / HTTP/1.1
        private String method;
        private String url;
        private String http_version;

        public WebRequest(Map<String, String> headers, String method, String url, String http_version) {
            this.headers = headers;
            this.method = method;
            this.url = url;
            this.http_version = http_version;
        }

        //BufferedReader - obrabotuva request
        //BufferedWriter - obrabotuva response

        public static WebRequest createWebRequest(BufferedReader bufferedReader) throws IOException {
            List<String> inputs = new ArrayList<>();

            String line ;

            //vo line gi cuvam vrednostite na tekovnata linija i ja dodavam vo lista
            while(!(line = bufferedReader.readLine()).equals("")){ //is not empty
                //linija po linija iscituvam
                //prvo GET .., pa Host..., pa Accept-Language

                inputs.add(line);
            }

            //GET / HTTP/1.1

            String[] parts = inputs.get(0).split("\\s+");

            String method = parts[0];
            String url = parts[1];
            String http_version = parts[2];

            Map<String, String > headers = new HashMap<>();

            for (int i = 1; i < inputs.size(); i++) {
                String[] pair = inputs.get(i).split(": ");

                if (pair.length == 2) {  //Host: developer.mozilla.org
                    headers.put(pair[0], pair[1]);
                }
            }

            return new WebRequest(headers,method, url, http_version);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(7005);
        server.start();
    }
}
