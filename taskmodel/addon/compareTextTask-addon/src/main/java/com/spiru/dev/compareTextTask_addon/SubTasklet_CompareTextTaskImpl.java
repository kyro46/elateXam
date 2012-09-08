package com.spiru.dev.compareTextTask_addon;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import de.thorstenberger.taskmodel.TaskApiException;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDefRoot.CorrectionModeType;
import de.thorstenberger.taskmodel.complex.complextaskhandling.CorrectionSubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.subtasklets.impl.AbstractAddonSubTasklet;
import de.thorstenberger.taskmodel.complex.jaxb.AddonSubTaskDef;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskHandling.Try.Page.AddonSubTask;
import de.thorstenberger.taskmodel.complex.jaxb.SubTaskDefType;

public class SubTasklet_CompareTextTaskImpl extends AbstractAddonSubTasklet implements SubTasklet_CompareTextTask {
	private Element mementoTaskDef;
	private Element mementoTaskHandling;

	public SubTasklet_CompareTextTaskImpl( SubTaskDefType aoSubTaskDef, AddonSubTask atSubTask, CorrectionModeType correctionMode, float reachablePoints ) {
		super(aoSubTaskDef, atSubTask, correctionMode, reachablePoints);
		System.out.println("\n\nConstructor Called");
		mementoTaskDef = ((AddonSubTaskDef)aoSubTaskDef).getMemento();
		mementoTaskHandling = atSubTask.getMemento();
		if(mementoTaskHandling==null) { // null at the first instantiation
			try {
				mementoTaskHandling=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument().createElementNS("http://complex.taskmodel.thorstenberger.de/complexTaskHandling","Memento");
			} catch (DOMException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			atSubTask.setMemento(mementoTaskHandling); // won't be null next time
		}
		atSubTask.setTaskType(getAddOnType());
	}

	@Override
	public String getAddOnType() {
		return ((AddonSubTaskDef)this.jaxbSubTaskDef).getTaskType();
	}
	@Override
	public void doSave( SubmitData submitData ) throws IllegalStateException{
		System.out.println("\n\ndoSave() Called");
		//CompareTextTaskSubmitData tsd = (CompareTextTaskSubmitData) submitData;
		//anordnungSubTask.setAnswer( tsd.getAnswer() );
	}
	@Override
	public void doAutoCorrection(){
		System.out.println("\n\ndoAutoCorrection() Called");
		try {
			// TODO!
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void doManualCorrection( CorrectionSubmitData csd ){
		System.out.println("\n\ndoManualCorrection() Called");
		//CompareTextTaskCorrectionSubmitData acsd = (CompareTextTaskCorrectionSubmitData) csd;
		//super.setAutoCorrection(acsd.getPoints());
		// TODO!
	}
	@Override
	public boolean isProcessed(){
		System.out.println("\n\nisProcessed() Called");
		//return getAnswer()!=null
		//		&& getAnswer().length() > 0
		//		&& !getAnswer().equals(getText(memento,"defaultAnswer",null));
		return false;
	}

	/*
	 *  (non-Javadoc)
	 * @see de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet#build()
	 */
	@Override
	public void build(long randomSeed) throws TaskApiException {
		System.out.println("\n\nbuild() Called");
	}

	@Override
	public int getHash(){
		System.out.println("\n\ngetHash() Called");
		StringBuffer ret = new StringBuffer();
		ret.append( subTaskType.getRefId() );
		//ret.append( getAnswer() );
		ret.append( getVirtualSubtaskNumber() );
		return ret.toString().hashCode();
	}

	@Override
	public String getTagsString() {
		// XPath: Memento/addonConfig/avaiableTags -> write into Base64 String
		Element avaiableTags = (Element)mementoTaskDef.getElementsByTagName("avaiableTags").item(0);
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(avaiableTags);
			ByteArrayOutputStream bytewriter = new ByteArrayOutputStream();
			transformer.transform(source, new StreamResult(bytewriter));
			// having it as base64 string so browsers won't complain
			String ret = DatatypeConverter.printBase64Binary(bytewriter.toByteArray());
			return ret;
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return "ERROR";
	}

	@Override
	public String getInitialText() {
		// XPath: Memento/textComparisonSubTaskDef/initialText
		Element initialText = (Element)mementoTaskDef.getElementsByTagName("initialText").item(0);
		return initialText.getTextContent();
	}
}
