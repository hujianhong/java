package me.huding.qinggo.http;

public class HttpServlet implements Servlet {

	@Override
	public void init() {
		System.out.println("servlet init ");
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws Exception {
		String contentType = req.getContentType();
		String param = req.getParam();
		String header = res.assembleResponseHeader(contentType);
		String body = res.assembleResponseBody(param);
		res.write(header + body);
	}

	@Override
	public void destory() {
		System.out.println("servlet destroy ");
	}

}
