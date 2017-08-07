package me.huding.hunter.core;

public class ClassPathResource {
	
	private static final String DEFAULT_CONTEXT_FILE_NAME = "/hunter-context.xml";
	
	private String contextPath; 
	
	public ClassPathResource() {
		this(DEFAULT_CONTEXT_FILE_NAME);
	}
	
	public ClassPathResource(String name){
		this.contextPath = name;
	}
	
	
	private ApplicationContext context;
	
	
	public ApplicationContext getApplicationContext(){
		try {
			if(context == null){
				context = new ApplicationContext(contextPath);
			}
			return context;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
