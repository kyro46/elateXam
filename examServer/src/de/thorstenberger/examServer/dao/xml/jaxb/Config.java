//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.2-b15-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.05.23 at 01:04:11 CEST 
//


package de.thorstenberger.examServer.dao.xml.jaxb;


/**
 * Java content class for config element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/java_files/eclipse/workspace/examServer/jaxb/config.xsd line 4)
 * <p>
 * <pre>
 * &lt;element name="config">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;element name="RemoteUserManagerURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;element name="studentsLoginEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element name="loadJVMOnStartup" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface Config
    extends javax.xml.bind.Element, de.thorstenberger.examServer.dao.xml.jaxb.ConfigType
{


}
