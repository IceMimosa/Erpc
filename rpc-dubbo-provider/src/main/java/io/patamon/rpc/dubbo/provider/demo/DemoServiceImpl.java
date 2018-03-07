package io.patamon.rpc.dubbo.provider.demo;

/**
 * Desc:
 * <p>
 * Mail: chk@terminus.io
 * Created by IceMimosa
 * Date: 2018/3/2
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
