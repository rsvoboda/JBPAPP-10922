/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.test.ws.jaxws.samples.wssecurity;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.soap.Addressing;

import org.jboss.logging.Logger;
import org.jboss.ws.annotation.EndpointConfig;

@WebService(name = "Hello", serviceName = "HelloService", targetNamespace = "http://org.jboss.ws/samples/wssecurity")
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@Addressing(enabled = true, required = true)
public class HelloJavaBean {
  private final Logger log = Logger.getLogger(HelloJavaBean.class);

  @WebMethod(action = "echoUserType")
  public UserType echoUserType(@WebParam(name = "user") UserType in0) {
    log.info(in0);
    return in0;
  }

  @WebMethod(action = "triggerException")
  public void triggerException(@WebParam(name = "String_1") String reason, @WebParam(name = "int_2") int code)
      throws HelloException_Exception {
    String message = "Error: " + reason + ":" + code;
    HelloException he = new HelloException();
    he.setMessage(message);
    he.setCode(code);
    he.setReason(reason);
    throw new HelloException_Exception(message, he);
  }
}
