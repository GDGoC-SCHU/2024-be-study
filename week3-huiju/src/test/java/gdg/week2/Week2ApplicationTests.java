package gdg.week2;

import gdg.week2.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


class Week2ApplicationTests {

	@Test
	void contextLoads() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
			String[] beanDefinitionNames = ac.getBeanDefinitionNames();	// 빈 이름 가져오기

		for(String beanDefinitionName : beanDefinitionNames){
			Object bean = ac.getBean(beanDefinitionName);	// 빈 가져오기
			System.out.println("beanDefinitionName =" + beanDefinitionName + ",bean = " + bean);
		}
	}

}
