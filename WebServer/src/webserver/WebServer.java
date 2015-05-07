package webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.nio.file.Files;

public class WebServer {

    public static void main(String[] args) throws Exception {
        
        System.out.println("Starting Server");
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server Started");
        
    }

    static class MyHandler implements HttpHandler {
        
        @Override
        public void handle(HttpExchange t) throws IOException {
            
            String path = t.getRequestURI().getPath();
            
            path = path.replace("/", "");
            
            System.out.println(path);
            
            File file = new File(path);
            
            t.sendResponseHeaders(200, file.length());
            
            try (OutputStream os = t.getResponseBody()) {
                Files.copy(file.toPath(), os);
            }
            
        }
        
    }

}