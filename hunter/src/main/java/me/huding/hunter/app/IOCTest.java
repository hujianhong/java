package me.huding.hunter.app;

import me.huding.hunter.core.ClassPathResource;
import me.huding.hunter.core.DefaultBeanFactory;

public class IOCTest {

	public static void main(String[] args) {
		DefaultBeanFactory factory = new DefaultBeanFactory(new ClassPathResource());
		
		Company company = (Company) factory.getBean("company");
		System.out.println(company);
		
		Department department = (Department) factory.getBean("department");
		
		System.out.println(department);

	}

}
