package no.kristiania.http;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpMessage {
    public String startLine;
    public final Map<String, String> headerFields = new HashMap<>();
    public String messageBody;
    private String contentType;

    public HttpMessage(Socket socket) throws IOException {
        startLine = HttpMessage.readLine(socket);
        readHeaders(socket);
        if (headerFields.containsKey("Content-Length")) {
            messageBody = HttpMessage.readBytes(socket, getContentLength());
        }
    }

    public HttpMessage(String startLine, String headerField, String headerValue) {
        this.startLine = startLine;
        this.headerFields.put(headerField, headerValue);
    }

    public HttpMessage(String startLine, String messageBody) {
        this.startLine = startLine;
        this.messageBody = messageBody;
    }


/*
    public HttpMessage(String startLine, String messageBody, String contentType) {
        this.startLine = startLine;
        this.messageBody = messageBody;
        this.contentType = contentType;
    }

 */


    static Map<String, String> parseRequestParameters(String query) {
        Map<String, String> queryMap = new HashMap<>();
        for (String queryParameter : query.split("&")) {
            int equalsPos = queryParameter.indexOf('=');
            String parameterName = queryParameter.substring(0, equalsPos);
            String parameterValue = queryParameter.substring(equalsPos + 1);
            queryMap.put(parameterName, parameterValue);
        }
        return queryMap;
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-Length"));
    }

    public String getHeader(String headerName) {
        return headerFields.get(headerName);
    }

    static String readBytes(Socket socket, int contentLength) throws IOException {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < contentLength; i++) {
            buffer.append((char) socket.getInputStream().read());
        }
        return java.net.URLDecoder.decode(buffer.toString(), StandardCharsets.UTF_8);
    }

    private void readHeaders(Socket socket) throws IOException {
        String headerLine;
        while (!(headerLine = HttpMessage.readLine(socket)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String headerField = headerLine.substring(0, colonPos);
            String headerValue = headerLine.substring(colonPos + 1).trim();
            headerFields.put(headerField, headerValue);
        }
    }

    static String readLine(Socket socket) throws IOException {
        StringBuilder buffer = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != '\r') {
            buffer.append((char) c);
        }
        int expectedNewline = socket.getInputStream().read();
        assert expectedNewline == '\n';
        return java.net.URLDecoder.decode(buffer.toString(), StandardCharsets.UTF_8);
    }

    public void write(Socket clientSocket) throws IOException {
        String response;
        if (headerFields.isEmpty()) {
            response = startLine + "\r\n" +
                    "Content-Length: " + messageBody.length() + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    messageBody;
        } else {
            response = "HTTP/1.1 303 See Other" + "\r\n" +
                    "Content-length: 0" + "\r\n" +
                    "Location: " + headerFields.get("Location") + "\r\n" +
                    "Connection: close\r\n" +
                    "\r\n" +
                    "\r\n";
        }

        clientSocket.getOutputStream().write(response.getBytes());
    }
}




