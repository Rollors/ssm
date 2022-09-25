import com.ldm.spring.pojo.Person;
import com.ldm.spring.pojo.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOCByXMLTest {

    /**
     * 获取bean的三种方式
     * 1、通过applicationContext.xml中配置的id来获取，即bean的id，返回值类型为Object
     * 2、根据bean的类型来获取，返回值类型为根据的类型，当一个类有多个bean时，会抛出多个bean的异常;若没有匹配的bean,则会抛出异常没有定义bean
     * 3、以上两个结合
     * 结论：
     * 根据类型来获取bean时，在满足bean唯一性的前提下，其实只是看：『对象 instanceof 指定的类
     * 型』的返回结果，只要返回的是true就可以认定为和类型匹配，能够获取到。
     * 即，bean的类型、继承的类型、实现的接口的类型都可以获取bean
     */
    @Test
    public void testIOC(){
        // 获取IOC
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-ioc.xml");
        // 获取bean
        // Student studentOne = (Student) ioc.getBean("studentOne");
        // Student studentOne = ioc.getBean(Student.class);
        // Student studentOne = ioc.getBean("studentOne",Student.class);
        Person studentOne = ioc.getBean(Student.class); // 向上转型，可以
        System.out.println(Student.class.getName());
        Person person = ioc.getBean(Person.class); // 找到Person接口的实现类，因为唯一，若是有多个实现类，则不能获取bean了
        System.out.println(studentOne);
        System.out.println(person);
    }

    @Test
    public void testDI(){
        // 获取IOC
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-ioc.xml");
        // 获取bean
        Person studentTwo = ioc.getBean("studentTwo",Student.class); // 向上转型，可以
        System.out.println(studentTwo);

        Person studentThree = ioc.getBean("studentThree",Student.class); // 向上转型，可以
        System.out.println(studentThree);
    }
}
