package edu.escuelaing.parcial;

import java.net.*;
import java.io.*;
public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while(running) {
            Socket clientSocket = null;

            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine = "";
            boolean primeraLinea = true;
            String file = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (primeraLinea){
                    file = inputLine.split(" ")[1];
                    System.out.println(file);
                    primeraLinea = false;
                }
                if (!in.ready()) {
                    break;
                }
            }
            getClima climita = new getClima();
            String json= climita.getClimaCiudadAPI("Bogota");
            if (file.startsWith("/clima/")){

                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        //+ "<script src=\"index.js\"</script>\n"
                        + "<body>"
                        + "Prueba1"
                        + "<h4>Ingrese la ciudad:</h4>\n"
                        + "<input type=\"text\" id=\"city\" >\n"
                        + "<br>\n"
                        +"<button type=\"button\" onclick=\"res.connexion($('#lugar').val())\">\n"
                        + "</body>"
                        + "<script>\n"
                        + "document.addEventListener(\"DOMContentLoaded\", () => {\n"
                        + "document.getElementById(\"submit\").addEventListener(\"click\", () => {\n"
                        + "var request = \"consulta?lugar=\" + document.getElementById(\"city\").value\n"
                        + "window.location.replace(request);\n"
                        + "})\n"
                        + "});\n"
                        + "</script>\n"
                        + "</html>";
            }else if (file.startsWith("/consulta/")){
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "prueba2"
                        + "<label> El resultado es: </label>"
                        + "<span id=\"Resultado\"</span>\n"
                        + "<p>" +json+ "</p>"
                        + "</body>"
                        + "</html>";}
            else{
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type \"application/json\"\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "prueba3"
                        + "</body>"
                        + "</html>";}
            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        }
            serverSocket.close();

    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}