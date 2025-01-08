package gdg.week2;

import gdg.week2.config.Appconfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootTest
class Week2ApplicationTests {

	@Test
	void contextLoads() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(Appconfig.class);
		String[] beanDefinitionNames = ac.getBeanDefinitionNames(); // 빈 이름 가져오기

		for(String beanDefinitionName : beanDefinitionNames){
			Object bean = ac.getBean(beanDefinitionName);
			System.out.println("beanDefinitionName = " + beanDefinitionName + ", bean = " + bean);
		}

	}

}
