package me.huding.qinggo.http;

public interface Servlet {
	
	public void init();
	
	public void service(ServletRequest request,ServletResponse response) throws Exception;
	
	public void destory();
}
