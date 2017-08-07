package me.huding.qinggo.http;
import java.io.IOException;
import java.io.OutputStream;

public class ServletResponse {
    private OutputStream outputStream;

    public ServletResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String assembleResponseHeader(String contentType) {
        /* 创建HTTP响应结果 */
        // HTTP响应的第一行
        String responseFirstLine = "HTTP/1.1 200 OK\r\n";
        // HTTP响应头
        String responseHeader = "Content-Type:" + contentType + "\r\n\r\n";

        return responseFirstLine + responseHeader;
    }

    public String assembleResponseBody(String param) {
        String content = "<body><h1>Hello:" + param + "</h1></body>";
        String title = "<head><title>HelloWorld</title></head>";
        String body = "<html>" + title + content + "</html>";
        return body;
    }

    public void write(String res) throws IOException {
        outputStream.write(res.getBytes());
    }

}