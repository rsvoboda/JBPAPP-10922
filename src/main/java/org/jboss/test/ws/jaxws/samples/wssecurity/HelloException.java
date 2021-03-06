package org.jboss.test.ws.jaxws.samples.wssecurity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for HelloException complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="HelloException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HelloException", propOrder = {"message", "code", "reason"})
public class HelloException {

  @XmlElement(required = true, nillable = true)
  protected String message;

  protected int code;

  @XmlElement(required = true, nillable = true)
  protected String reason;

  /**
   * Gets the value of the message property.
   *
   * @return
   *     possible object is
   *     {@link String }
   *
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the value of the message property.
   *
   * @param value
   *     allowed object is
   *     {@link String }
   *
   */
  public void setMessage(String value) {
    this.message = value;
  }

  /**
   * Gets the value of the code property.
   *
   */
  public int getCode() {
    return code;
  }

  /**
   * Sets the value of the code property.
   *
   */
  public void setCode(int value) {
    this.code = value;
  }

  /**
   * Gets the value of the reason property.
   *
   * @return
   *     possible object is
   *     {@link String }
   *
   */
  public String getReason() {
    return reason;
  }

  /**
   * Sets the value of the reason property.
   *
   * @param value
   *     allowed object is
   *     {@link String }
   *
   */
  public void setReason(String value) {
    this.reason = value;
  }

}
