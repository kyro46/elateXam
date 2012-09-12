package com.spiru.dev.groupingTask_addon;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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
import org.w3c.dom.NodeList;

import de.thorstenberger.taskmodel.TaskApiException;
import de.thorstenberger.taskmodel.complex.complextaskdef.Block;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDefRoot;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDefRoot.CorrectionModeType;
import de.thorstenberger.taskmodel.complex.complextaskhandling.CorrectionSubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.subtasklets.impl.AbstractAddonSubTasklet;
import de.thorstenberger.taskmodel.complex.jaxb.AddonSubTaskDef;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskHandling.Try.Page.AddonSubTask;
import de.thorstenberger.taskmodel.complex.jaxb.SubTaskDefType;

public class SubTasklet_GroupingTaskImpl extends AbstractAddonSubTasklet implements SubTasklet_GroupingTask {
	private Element mementoTaskDef;
	private Element mementoTaskHandling;

	public SubTasklet_GroupingTaskImpl( ComplexTaskDefRoot root, Block block, SubTaskDefType aoSubTaskDef, AddonSubTask atSubTask ) {
		super(root, block,aoSubTaskDef,atSubTask);
		mementoTaskDef = ((AddonSubTaskDef)aoSubTaskDef).getMemento();
		mementoTaskHandling = atSubTask.getMemento();
		if (mementoTaskHandling == null) { // null at the first instantiation
			try {
				mementoTaskHandling = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
						.createElementNS("http://complex.taskmodel.thorstenberger.de/complexTaskHandling", "Memento");
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
		GroupingTaskSubmitData ctSD = (GroupingTaskSubmitData) submitData;
		setAnswer(ctSD.getAnswer());
	}

	@Override
	public void doAutoCorrection(){
		try {
			// TODO!
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doManualCorrection( CorrectionSubmitData csd ){
		//CompareTextTaskCorrectionSubmitData acsd = (CompareTextTaskCorrectionSubmitData) csd;
		//super.setAutoCorrection(acsd.getPoints());
		// TODO!
	}

	@Override
	public boolean isProcessed(){
		return getAnswer() != null && getAnswer().length() > 0;// && !getResult().equals(getText(memento,"defaultAnswer",null));
	}

	/*
	 *  (non-Javadoc)
	 * @see de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet#build()
	 */
	@Override
	public void build(long randomSeed) throws TaskApiException {
		//System.out.println("\n\nbuild() Called");
	}

	@Override
	public int getHash(){
		String ret = subTaskType.getRefId() + getAnswer() + getVirtualSubtaskNumber();
		return ret.hashCode();
	}

	// METHODS WHICH ARE SPECIFIC FOR THIS TASK FOLLOW FROM HERE:	

	@Override
	public String getAnswer() {
		NodeList resultText = mementoTaskHandling.getElementsByTagName("answer");
		if(resultText.getLength() == 1)
			return resultText.item(0).getTextContent();
		return "";
	}

	private void setAnswer(String resultString) {
		Element resultText = (Element) mementoTaskHandling.getElementsByTagName("answer").item(0);
		if(resultText == null) {
			resultText = mementoTaskHandling.getOwnerDocument().createElement("answer");
			mementoTaskHandling.appendChild(resultText);
		}
		resultText.setTextContent(resultString);
	}
		
	@Override
	public List<String> getBoxContainerAttributes() {		
		// dragSubTaskDef Element herausfiltern
		Element dragSubTaskDef = (Element)mementoTaskDef.getElementsByTagName("dragSubTaskDef").item(0);				
		// alle BoxContainer aus dragSubTaskDef filtern		
		NodeList boxContainer = dragSubTaskDef.getElementsByTagName("BoxContainer");		
		// Attribute der BoxContainer auslesen
		List<String> stringList = new ArrayList<String>();
		for(int i=0; i<boxContainer.getLength(); i++){
			Element container = (Element)boxContainer.item(i);
			stringList.add(container.getAttribute("BoxName"));
			stringList.add(container.getAttribute("count"));
		}		
		return stringList;
	}

	@Override
	public boolean loadFromHandling() {
		if (mementoTaskHandling.getElementsByTagName("answer").item(0) != null){
			return true;
		}
		return false;
	}

	@Override
	public String getImage() {		
		return mementoTaskHandling.getElementsByTagName("answer").item(0).getTextContent();
	}

}
