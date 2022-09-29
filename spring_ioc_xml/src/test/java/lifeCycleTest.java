import com.ldm.spring.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class lifeCycleTest {
    /**
     * 1、实例化
     * 2、依赖注入
     * 3、初始化，需要通过bean的init-method属性指定初始化方法
     * 4、IOC容器关闭时销毁，需要通过bean的destroy-method属性指定销毁方法
     *
     * bean的后置处理器会在生命周期的初始化前后添加额外的操作，需要实现BeanPostProcessor接口，
     * 且配置到IOC容器中，需要注意的是，bean后置处理器不是单独针对某一个bean生效，而是针对IOC容
     * 器中所有bean都会执行
     *
     * 注意：
     * 若bean的作用域为单例，生命周期的前三个步骤在获取IOC容器时执行
     * 若bean的作用域为多例，生命周期的前三个步骤在获取bean时执行
     */
    @Test
    public void test(){
        // ConfigurableApplicationContext是ApplicationContext的子接口，其中扩展了刷新和关闭容器的方法
        ConfigurableApplicationContext ioc = new ClassPathXmlApplicationContext("spring-lifecycle.xml");
        User user = ioc.getBean(User.class);
        System.out.println(user);
//        user.destroyMethod();多例时销毁的手段
        ioc.close();
    }

}
