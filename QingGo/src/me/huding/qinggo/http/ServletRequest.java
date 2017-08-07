package me.huding.qinggo.http;

import java.io.IOException;
import java.io.InputStream;

public class ServletRequest {
	private String request;
	
	private InputStream socketIn;
	
	private String URI;
	
	private String contentType;
	
	private String param;
	
	
	public ServletRequest(InputStream socketIn) throws IOException {
        this.socketIn = socketIn;
        this.request = _getRequest();
        this.URI = _getURI();
        this.contentType = _getContentType();
        this.param = _getParam();
    }

    public String getRequest() {
        return request;
    }

    public String getURI() {
        return URI;
    }

    public String getContentType() {
        return contentType;
    }

    public String getParam() {
        return param;
    }

    private String _getRequest() throws IOException {
        byte[] buf = new byte[10 * 1024];
        int len = - 1;
        int p = 0;
        while((len = socketIn.read(buf,p,1024)) != -1){
        	p += len;
        }
        return new String(buf, 0, p - 1);
    }
    
    
    public String getServletName(){
        return URI.substring(URI.indexOf("/") + 1, URI.indexOf("?"));
    }

    private String _getURI() {
    	System.out.println(request);
        String firstLine = request.substring(0, request.indexOf("\r\n"));
        String[] parts = firstLine.split(" ");

        return parts[1];
    }

    private String _getContentType() {
        /* 决定HTTP响应正文的类型 */
        return "html";
    }

//获得请求参数
    private String _getParam() {
        String paramString = URI.substring(URI.indexOf("?") + 1);
        String[] paramPairs = paramString.split("=");
        return paramPairs[1];
    }
}
