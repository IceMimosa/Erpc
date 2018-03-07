package io.patamon.rpc.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Desc:
 * <p>
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/2
 */
public class DemoProviderMain {

    public static void main(String[] args) throws IOException {
        // System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/spring/dubbo-demo-provider.xml"});
        context.start();
        // press any key to exit
        System.in.read();
    }
}
