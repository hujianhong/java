package me.huding.hunter.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import me.huding.hunter.bean.Bean;
import me.huding.hunter.bean.Property;

public class ApplicationContext {
	// 定义一个IOC容器
	private Map<String, Object> ioc;

	private Map<String, Bean> config;

	private Map<String, Map<String, Method>> map = new HashMap<String, Map<String, Method>>();

	public ApplicationContext(String path) throws Exception {
		// 初始化IOC容器
		ioc = new HashMap<String, Object>();
		// 读取配置文件
		config = getConfig(path);
		if (config == null) {
			return;
		}
		for (Entry<String, Bean> entry : config.entrySet()) {
			String beanId = entry.getKey();
			Bean bean = entry.getValue();
			// 根据bean生成相应的对象
			Object object = createBean(bean);
			ioc.put(beanId, object);
		}
	}

	private Method getSetterMethod(Class<?> clazz, String name) throws Exception {
		return map.get(clazz.getName()).get("set" + name);
	}

	private Object createBean(Bean bean) throws Exception {
		String className = bean.getClassName();
		Class<?> c = null;
		Object object = null;
		try {
			c = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("您配置的class属性不合法：" + className);
		}
		//
		Map<String, Method> methodsMap = mapMethods(c);
		map.put(className, methodsMap);
		try {
			object = c.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("该类缺少一个无参构造方法：" + className);
		}
		if (bean.getProperties() == null || bean.getProperties().isEmpty()) {
			return object;
		}
		for (Property p : bean.getProperties()) {
			if (notBlank(p.getValue())) {
				Method getMethod = getSetterMethod(c, p.getName());
				try {
					getMethod.invoke(object, p.getValue());
				} catch (Exception e) {
					throw new RuntimeException("属性名称不合法或者没有相应的getter方法：" + p.getName());
				}
			}
			if (notBlank(p.getRef())) {
				Method getMethod = getSetterMethod(c, p.getName());
				Object obj = ioc.get(p.getRef());
				if (obj == null) {
					throw new RuntimeException("没有找到依赖的对象：" + p.getRef());
				} else {
					try {
						getMethod.invoke(object, obj);
					} catch (Exception e) {
						throw new RuntimeException("属性名称不合法或者没有相应的getter方法：" + p.getName());
					}
				}
			}
		}
		return object;
	}

	public Object getBean(String beanName) {
		return ioc.get(beanName);
	}

	private static Map<String, Bean> getConfig(String path) throws Exception {
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse(ApplicationContext.class.getResourceAsStream(path));
		Element root = document.getDocumentElement();
		NodeList childNodes = root.getElementsByTagName(IConsts.BEAN);
		Map<String, Bean> configMap = new HashMap<String, Bean>();
		for (int i = 0, len = childNodes.getLength(); i < len; i++) {
			Node beanNode = childNodes.item(i);
			if (beanNode instanceof Element) {
				Bean bean = parseBean((Element) beanNode);
				configMap.put(bean.getId(), bean);
			}
		}
		return configMap;
	}

	private static Bean parseBean(Element beanNode) {
		Bean bean = new Bean();
		bean.setId(beanNode.getAttribute(IConsts.ID));
		bean.setClassName(beanNode.getAttribute(IConsts.CLASS));
		NodeList properties = beanNode.getChildNodes();
		for (int j = 0, plen = properties.getLength(); j < plen; j++) {
			Node proNode = properties.item(j);
			if (proNode instanceof Element) {
				bean.getProperties().add(parseProperty((Element) proNode));
			}
		}
		return bean;
	}

	private static Property parseProperty(Element proNode) {
		Property prop = new Property();
		prop.setName(proNode.getAttribute(IConsts.NAME));
		prop.setValue(proNode.getAttribute(IConsts.VALUE));
		prop.setRef(proNode.getAttribute(IConsts.REF));
		return prop;
	}

	private Map<String, Method> mapMethods(Class<?> c) {
		Method[] methods = c.getMethods();
		Map<String, Method> map = new HashMap<String, Method>();
		for (Method method : methods) {
			if (isGetter(method) || isSetter(method)) {
				map.put(method.getName().toLowerCase(), method);
			}
		}
		return map;
	}

	public static boolean isGetter(Method method) {
		if (!method.getName().startsWith("get"))
			return false;
		if (method.getParameterTypes().length != 0)
			return false;
		if (void.class.equals(method.getReturnType()))
			return false;
		return true;
	}

	public static boolean isSetter(Method method) {
		if (!method.getName().startsWith("set"))
			return false;
		if (method.getParameterTypes().length != 1)
			return false;
		return true;
	}

	private static boolean notBlank(String string) {
		return !isBlank(string);
	}

	private static boolean isBlank(String string) {
		return string == null || string.trim().length() == 0;
	}
}
