package org.jboss.test.ws.jaxws.samples.wssecurity;

import java.io.File;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.addressing.MapRequiredException;
import javax.xml.ws.soap.AddressingFeature;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.ws.core.StubExt;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@RunAsClient
public class WSSConfigIT {

  private static final String name = "jaxws-samples-wssecurity-encrypt";
  private static final boolean NO_SIGN = true;

  @Deployment
  static WebArchive createDeployment() throws Exception {

    String contextPath = "src/main/webapp";
    WebArchive archive = ShrinkWrap
        .create(WebArchive.class, name + ".war")

        .addAsWebInfResource(new File(contextPath, "WEB-INF/web.xml"))
        .addAsWebInfResource(new File(contextPath, "WEB-INF/jboss-wsse-server.xml"))
        .addAsWebInfResource(new File(contextPath, "WEB-INF/wsse.keystore"))
        .addAsWebInfResource(new File(contextPath, "WEB-INF/wsse.truststore"))

        .addAsManifestResource(new File("src/main/resources", "META-INF/hello.wsdl"))
        .addAsManifestResource(new File("src/main/resources", "META-INF/jaxws-client-config.xml"))
        .addAsManifestResource(new File("src/main/resources", "META-INF/jboss-wsse-client-NO-SIGN.xml"))
        .addAsManifestResource(new File("src/main/resources", "META-INF/jboss-wsse-client.xml"))

        .addClass(Hello.class)
        .addClass(HelloException_Exception.class)
        .addClass(HelloException.class)
        .addClass(HelloJavaBean.class)
        .addClass(MaliciousAddressingHandler.class)
        .addClass(UserType.class)
        .addAsResource("log4j.xml")
        ;
    return TestUtils.backupArchiveForDebug(archive);
  }

  @Test
  public void testNormal() throws Exception {
    System.setProperty(MaliciousAddressingHandler.FAKE_ACTION, "");
    testEndpoint(false);
  }

  @Test(expected = MapRequiredException.class)
  public void testNormalNosign() throws Exception {
    System.setProperty(MaliciousAddressingHandler.FAKE_ACTION, "");
    testEndpoint(NO_SIGN);
  }

  @Test(expected = MapRequiredException.class)
  public void testFakeAction() throws Exception {
    System.setProperty(MaliciousAddressingHandler.FAKE_ACTION, "true");
    testEndpoint(false);
  }

  @Test(expected = MapRequiredException.class)
  public void testFakeActionNosign() throws Exception {
    System.setProperty(MaliciousAddressingHandler.FAKE_ACTION, "true");
    testEndpoint(NO_SIGN);
  }


  private void testEndpoint(boolean nosign) throws Exception {
    org.apache.log4j.xml.DOMConfigurator.configure(getClass().getResource("/log4j.xml"));
    System.setProperty("org.jboss.ws.wsse.keyStore", "src/main/webapp/WEB-INF/wsse.keystore");
    System.setProperty("org.jboss.ws.wsse.trustStore", "src/main/webapp/WEB-INF/wsse.truststore");
    System.setProperty("org.jboss.ws.wsse.keyStorePassword", "jbossws");
    System.setProperty("org.jboss.ws.wsse.trustStorePassword", "jbossws");
    System.setProperty("org.jboss.ws.wsse.keyStoreType", "jks");
    System.setProperty("org.jboss.ws.wsse.trustStoreType", "jks");

    Hello hello = getPort(nosign);

    UserType in0 = new UserType();
    in0.setMsg("Kermit");
    UserType retObj = hello.echoUserType(in0);
    System.out.println(retObj.getMsg());
  }

  private Hello getPort(boolean nosign) throws Exception {
    URL wsdlURL = getClass().getResource("/META-INF/hello.wsdl");
    String wsseXml = "/META-INF/" + (nosign ? "jboss-wsse-client-NO-SIGN.xml" : "jboss-wsse-client.xml");
    URL securityURL = getClass().getResource(wsseXml);
    QName serviceName = new QName("http://org.jboss.ws/samples/wssecurity", "HelloService");

    Service service = Service.create(wsdlURL, serviceName);

    Hello port = service.getPort(Hello.class, new AddressingFeature(true, true));
    ((StubExt) port).setSecurityConfig(securityURL.toExternalForm());
    ((StubExt) port).setConfigName("WSSecurity + Addressing", "META-INF/jaxws-client-config.xml");

    return port;
  }
}
