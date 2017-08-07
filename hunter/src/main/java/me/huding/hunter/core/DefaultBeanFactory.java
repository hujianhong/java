package me.huding.hunter.core;

public class DefaultBeanFactory implements BeanFactory {
	
	private ApplicationContext context;
	
	private ClassPathResource resource;

	public DefaultBeanFactory(ClassPathResource resource) {
		this.resource = resource;
	}

	public Object getBean(String beanName) {
		if(context == null){
			this.context = this.resource.getApplicationContext();
		}
		return context.getBean(beanName);
	}

}
