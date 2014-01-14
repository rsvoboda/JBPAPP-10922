package org.jboss.test.ws.jaxws.samples.wssecurity;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class MaliciousAddressingHandler implements SOAPHandler<SOAPMessageContext> {
  public static final String FAKE_ACTION = "fake.action";

  private static final QName soapAction = new QName("http://www.w3.org/2005/08/addressing", "Action");

  @Override
  public Set<QName> getHeaders() {
    return null;
  }

  @Override
  public boolean handleMessage(SOAPMessageContext ctx) {
    info("Invoking malicious soap action handler");
    boolean fakeAction = System.getProperty(FAKE_ACTION) != null && (!System.getProperty(FAKE_ACTION).isEmpty());
    if (fakeAction) {
      fakeAction(ctx);
    }
    return true;
  }

  private void fakeAction(SOAPMessageContext ctx) {
    info("Fake action");
    try {
      SOAPMessage msg = ctx.getMessage();
      SOAPHeader header = msg.getSOAPHeader();
      @SuppressWarnings("unchecked")
      Iterator<SOAPElement> iter = header.getChildElements(soapAction);
      while (iter.hasNext()) {
        SOAPElement childElement = iter.next();
        info("Modifying the wsa:Action element");
        childElement.setValue("INVALID_VALUE");
      }
    } catch (Exception e) {
      error("Error changing soap action", e);
    }
  }

  @Override
  public boolean handleFault(SOAPMessageContext ctx) {
    return true;
  }

  @Override
  public void close(MessageContext ctx) {
  }

  private void info(String message) {
    System.err.println(message);
  }

  private void error(String message, Exception e) {
    System.err.println(message);
    e.printStackTrace(System.err);
  }

}
