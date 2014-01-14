package org.jboss.test.ws.jaxws.samples.wssecurity;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

// import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 *
 */
@WebService(name = "Hello", targetNamespace = "http://org.jboss.ws/samples/wssecurity")
@SOAPBinding(style = SOAPBinding.Style.RPC)
// @XmlSeeAlso({
//     ObjectFactory.class
// })
public interface Hello {

  /**
   *
   * @param user
   * @return
   *     returns org.jboss.test.ws.jaxws.samples.wssecurity.UserType
   */
  @WebMethod
  @WebResult(partName = "return")
  public UserType echoUserType(@WebParam(name = "user", partName = "user") UserType user);

  /**
   *
   * @param string1
   * @param int2
   * @throws HelloException_Exception
   */
  @WebMethod
  public void triggerException(@WebParam(name = "String_1", partName = "String_1") String string1,
      @WebParam(name = "int_2", partName = "int_2") int int2) throws HelloException_Exception;

}
