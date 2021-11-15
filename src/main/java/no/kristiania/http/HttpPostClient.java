package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.net.URLEncoder;

public class HttpPostClient {
    private final HttpMessage httpMessage;
    private final int statusCode;

    public HttpPostClient(String host, int port, String requestTarget, String contentBody) throws IOException {


        Socket socket = new Socket(host, port);
        String encodedContentBody = URLEncoder.encode(contentBody, "UTF-8");

        String request = "POST " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n" +
                "Content-Length: " + encodedContentBody.length() + "\r\n" +
                "\r\n" +
                encodedContentBody;

        socket.getOutputStream().write(request.getBytes());

        httpMessage = new HttpMessage(socket);
        String[] statusLine = httpMessage.startLine.split(" ");
        this.statusCode = Integer.parseInt(statusLine[1]);


    }



    public int getStatusCode() {
        return statusCode;
    }



}
