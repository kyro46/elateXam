//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.08.03 at 01:32:53 PM MESZ 
//


package de.thorstenberger.taskmodel.complex.complextaskdef;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaskBlockType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaskBlockType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://complex.taskmodel.thorstenberger.de/complexTaskDef}config"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaskBlockType", propOrder = {
    "config"
})
@XmlSeeAlso({
    de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.McTaskBlock.class,
    de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.ClozeTaskBlock.class,
    de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.TextTaskBlock.class,
    de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.MappingTaskBlock.class,
    de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.PaintTaskBlock.class,
    de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDef.Category.AddonTaskBlock.class
})
public abstract class TaskBlockType implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Config config;

    /**
     * Gets the value of the config property.
     * 
     * @return
     *     possible object is
     *     {@link Config }
     *     
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Sets the value of the config property.
     * 
     * @param value
     *     allowed object is
     *     {@link Config }
     *     
     */
    public void setConfig(Config value) {
        this.config = value;
    }

    public boolean isSetConfig() {
        return (this.config!= null);
    }

}
