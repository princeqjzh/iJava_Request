package com.test.stress;

import com.dubbo.service.HelloService;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboRequestSample extends AbstractJavaSamplerClient {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
    private static HelloService helloService;
    private static long start = 0;
    private static long end = 0;

    public void setupTest(JavaSamplerContext arg0){
        helloService=(HelloService)context.getBean("helloService");
        start = System.currentTimeMillis();
    }

    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult sr = new SampleResult();
        sr.setSamplerData("dubbo测试Java Request");
        sr.sampleStart();// jmeter 开始统计响应时间标记
        String result = helloService.sayHello("World");
        System.out.println(result);
        if(result.contains("World")){
            sr.setResponseData("结果是：" + result, null);
            sr.setDataType(SampleResult.TEXT);
            sr.setSuccessful(true);
        }else {
            sr.setSuccessful(false);
        }
        sr.sampleEnd();// jmeter 结束统计响应时间标记
        return sr;
    }

    //测试结束时调用；
    public void teardownTest(JavaSamplerContext arg0) {
        end = System.currentTimeMillis();
        // 总体耗时
        System.out.println("cost time:" + (end - start) + "毫秒");
    }

    public static void main(String[] args) {
        JavaSamplerContext arg0 = new JavaSamplerContext(new Arguments());

        DubboRequestSample test=new DubboRequestSample();
        test.setupTest(arg0);
        test.runTest(arg0);
    }

}
