package me.huding.qinggo.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
	private int port;

	private ServerSocket serverSocket = null;

	public HttpServer(int port) throws IOException {
		this.serverSocket = new ServerSocket(port);
		System.out.println("Http Server startup OK...");
	}

	private Map<String, Servlet> servlets = new HashMap<String, Servlet>();

	private Map<String, String> mapping = new HashMap<String, String>();
	
	
	public void addMapping(String url,String className){
		mapping.put(url, className);
	}

	public void work() throws IOException {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ServletRequest request = new ServletRequest(socket.getInputStream());
				ServletResponse response = new ServletResponse(socket.getOutputStream());

				System.out.println("Receive request:\n" + request.getRequest());
				String servletName = request.getServletName();
				String className = mapping.get(servletName);
				Servlet servlet = servlets.get(className);
				if(servlet == null){
					servlet = (Servlet) Class.forName(servletName).newInstance();
					servlets.put(className, servlet);
				}
				servlet.init();
				servlet.service(request, response);
				servlet.destory();

				socket.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int getPort() {
		return port;
	}

	public static void main(String[] args) {
		try {
			HttpServer server = new HttpServer(8080);
			server.addMapping("HelloServlet", HttpServlet.class.getName());
			server.work();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
