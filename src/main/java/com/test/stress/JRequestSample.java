package com.test.stress;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class JRequestSample extends AbstractJavaSamplerClient {

    /**
     * Pre-test method. (Optional)
     * @param context
     */
    public void setupTest(JavaSamplerContext context) {
        System.out.println("I'm in the setupTest !!!!! +++++");
    }

    /**
     * Post-test method. (Optional)
     * @param context
     */
    public void teardownTest(JavaSamplerContext context) {
        System.out.println("I'm in the teardownTest!!!!! ++++++");
    }

    /**
     * Test running method. (Required)
     * @param arg0
     * @return
     */
    public SampleResult runTest(JavaSamplerContext arg0) {
        System.out.println("I'm running!");
        SampleResult result = new SampleResult();
        result.sampleStart();
        result.setSuccessful(true);
        result.setResponseCode("200");
        result.setRequestHeaders("Content-Type: application/json");
        result.setResponseMessage("\"Message\": \"success\"");
        result.setResponseData("hello".getBytes());
        System.out.println("return success!!!!");
        System.out.println("the result response code = " + result.getResponseCode());
        result.sampleEnd();
        return result;
    }
}
