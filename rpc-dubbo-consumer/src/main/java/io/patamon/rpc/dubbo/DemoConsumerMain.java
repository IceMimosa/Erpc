package io.patamon.rpc.dubbo;

import io.patamon.rpc.dubbo.provider.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Desc:
 * <p>
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/2
 */
public class DemoConsumerMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
        context.start();
        // obtain proxy object for remote invocation
        DemoService demoService = (DemoService) context.getBean("demoService");
        // execute remote invocation
        String hello = demoService.sayHello("world");
        // show the result
        System.out.println(hello);
    }
}
