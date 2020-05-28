package com.test.stress;

import com.test.common.HttpClient;
import com.test.utils.JSONParser;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterVariables;

public class JHttpRequestSample extends AbstractJavaSamplerClient {
    /**
     * Get default parameter from the java request sampler.
     * @return arguments
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("hostname","default_host");
        return params;
    }

    /**
     * Pre-test method. (Optional)
     *
     * @param context
     */
    public void setupTest(JavaSamplerContext context) {
        System.out.println("==== Restful API test started! ====");
    }

    /**
     * Post-test method. (Optional)
     *
     * @param context
     */
    public void teardownTest(JavaSamplerContext context) {
        System.out.println("==== Restful API test stopped. ====");
    }

    /**
     * Test running method. (Required)
     *
     * @param arg0
     * @return
     */
    public SampleResult runTest(JavaSamplerContext arg0) {
        //从JMeter的全局变量中获取参数值
        JMeterVariables jMeterVariables = arg0.getJMeterVariables();
        String port = jMeterVariables.get("port");
        String username = jMeterVariables.get("username");
        String password = jMeterVariables.get("password");

        //从Java Request变量列表中获取参数值
        String hostname = arg0.getParameter("hostname");

        System.out.println("== JMeter Variables:");
        System.out.println("hostname = " + hostname);
        System.out.println("port = " + port);
        System.out.println("username = " + username);
        System.out.println("password = " + password);

        System.out.println("== Test Start with user " + username);
        SampleResult sampleResult = new SampleResult();
        sampleResult.sampleStart();
        //运行接口测试组合代码，接口1 -> 接口4
        boolean testResult = menuRestfulAPITest(hostname, port, username, password);
        if (testResult) {
            sampleResult.setSuccessful(true); //设定成功条件下的Java Request 结果为成功
            String succMsg = "Menu restfulAPI test success.";
            sampleResult.setResponseData(succMsg.getBytes());
            System.out.println(succMsg);

        } else {
            sampleResult.setSuccessful(false); //设定成功条件下的Java Request 结果为失败
            String failMsg = "Menu restfulAPI test failed.";
            sampleResult.setResponseData(failMsg.getBytes());
            System.out.println(failMsg);
        }
        sampleResult.sampleEnd();
        return sampleResult;
    }

    public static void main(String[] args) {
        //实验代码
        String hostname = "localhost";
        String port = "9091";
        String username = "user01";
        String password = "pwd";
        boolean result = JHttpRequestSample.menuRestfulAPITest(hostname, port, username, password);
        System.out.println("最终结果：" + result);
    }

    /**
     * 测试请求实际运行
     * @param hostname: Host or IP
     * @param port: 端口
     * @param username: 用户名
     * @param password: 密码
     * @return
     */
    public static boolean menuRestfulAPITest(String hostname, String port, String username, String password) {
        String protocal = "http";
        String access_token = "";

        //接口1 登录操作
        String path1 = "/api/v1/user/login";
        String url1 = protocal + "://" + hostname + ":" + port + path1;
        String reqData1 = "{\n" +
                "\t\"authRequest\": {\n" +
                "\t    \"userName\": \"" + username + "\",\n" +
                "\t    \"password\": \"" + password + "\"\n" +
                "\t}\n" +
                "}";
        String respData1 = HttpClient.sendPost(url1, reqData1, access_token);
        access_token = JSONParser.getJsonValue(respData1, "access_token");
        String retcode1 = JSONParser.getJsonValue(respData1, "code");
        if (!"200".equalsIgnoreCase(retcode1)) {  //校验接口1的返回code是否等于200
            System.out.println("1. 登录接口请求失败！" + " return code = " + retcode1);
            return false;
        }
        System.out.println("1. 登录接口请求成功");

        //接口2 浏览菜单
        String path2 = "/api/v1/menu/list";
        String url2 = protocal + "://" + hostname + ":" + port + path2;
        String respData2 = HttpClient.sendGet(url2, access_token);
        String retcode2 = JSONParser.getJsonValue(respData2, "code");
        if (!"200".equalsIgnoreCase(retcode2)) { //校验接口2的返回code是否等于200
            System.out.println("2. 浏览菜单接口请求失败" + " return code = " + retcode2);
            return false;
        }
        System.out.println("2. 浏览菜单接口请求成功");

        //接口3 下订单
        String path3 = "/api/v1/menu/confirm";
        String url3 = protocal + "://" + hostname + ":" + port + path3;
        String reqData3 = "{\n" +
                "    \"order_list\": [\n" +
                "        {\n" +
                "            \"menu_nunber\" : \"01\",\n" +
                "            \"number\" : 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"menu_nunber\" : \"03\",\n" +
                "            \"number\" : 2\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String respData3 = HttpClient.sendPost(url3, reqData3, access_token);
        String retcode3 = JSONParser.getJsonValue(respData3, "code");
        if (!"200".equalsIgnoreCase(retcode3)) { //校验接口3的返回code是否等于200
            System.out.println("3. 下订单接口请求失败" + " return code = " + retcode3);
            return false;
        }
        System.out.println("3. 下订单接口请求成功");

        //接口4 退出
        String path4 = "/api/v1/user/logout";
        String url4 = protocal + "://" + hostname + ":" + port + path4;
        String respData4 = HttpClient.sendDelete(url4, access_token);
        String retcode4 = JSONParser.getJsonValue(respData4, "code");

        if (!"200".equalsIgnoreCase(retcode4)) { //校验接口4的返回code是否等于200
            System.out.println("4. 退出接口请求成功" + " return code = " + retcode4);
            return false;
        }
        System.out.println("4. 退出接口请求成功");

        return true;
    }
}
