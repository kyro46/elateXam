package com.spiru.dev.rtypeTask_addon;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import de.thorstenberger.taskmodel.TaskApiException;
import de.thorstenberger.taskmodel.complex.complextaskdef.Block;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDefRoot;
import de.thorstenberger.taskmodel.complex.complextaskhandling.CorrectionSubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.subtasklets.impl.AbstractAddonSubTasklet;
import de.thorstenberger.taskmodel.complex.jaxb.AddonSubTaskDef;
import de.thorstenberger.taskmodel.complex.jaxb.ManualCorrectionType;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskHandling.Try.Page.AddonSubTask;
import de.thorstenberger.taskmodel.complex.jaxb.SubTaskDefType;

public class SubTasklet_RtypeTaskImpl extends AbstractAddonSubTasklet implements SubTasklet_RtypeTask {
	private Element mementoTaskDef;
	private Element mementoTaskHandling;
	private AddonSubTask subTaskObject;

	public SubTasklet_RtypeTaskImpl( ComplexTaskDefRoot root, Block block, SubTaskDefType aoSubTaskDef, AddonSubTask atSubTask ) {
		super(root, block,aoSubTaskDef,atSubTask);
		mementoTaskDef = ((AddonSubTaskDef)aoSubTaskDef).getMemento();
		mementoTaskHandling = atSubTask.getMemento();
		subTaskObject = atSubTask;
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
		RtypeTaskSubmitData ctSD = (RtypeTaskSubmitData) submitData;
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
		RtypeTaskCorrectionSubmitData pcsd = (RtypeTaskCorrectionSubmitData) csd;
		//super.setAutoCorrection(acsd.getPoints());	
		List<ManualCorrectionType> manualCorrections = subTaskObject.getManualCorrection();
		if( complexTaskDefRoot.getCorrectionMode().getType() == ComplexTaskDefRoot.CorrectionModeType.MULTIPLECORRECTORS ) {
			for( ManualCorrectionType mc : manualCorrections ){
				if( mc.getCorrector().equals( pcsd.getCorrector() ) ){
					mc.setPoints( pcsd.getPoints() );
					return;
				}
			}
			// corrector not found, so create a new ManualCorrection for him
			ManualCorrectionType mc;
			mc = objectFactory.createManualCorrectionType();
			mc.setCorrector(pcsd.getCorrector());
			mc.setPoints(pcsd.getPoints());
			manualCorrections.add(mc);
		} else {
			ManualCorrectionType mc;
			if( manualCorrections.size() > 0 ) {
				mc = manualCorrections.get(0);
			} else {
				mc = objectFactory.createManualCorrectionType();
				manualCorrections.add(mc);
			}
			mc.setCorrector(pcsd.getCorrector());
			mc.setPoints(pcsd.getPoints());
		}
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
		/*return mementoTaskHandling.getTextContent();*/
		
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
	public String getSelectionText(){
		Element child = (Element)(mementoTaskDef.getElementsByTagName("selection").item(0));		
		return child.getElementsByTagName("text").item(0).getTextContent();
	}
	
	@Override
	public String getSelectionHint(){
		Element child = (Element)(mementoTaskDef.getElementsByTagName("selection").item(0));		
		return child.getElementsByTagName("hint").item(0).getTextContent();
	}

	@Override
	public List<String> getSelectionAnswers(){
		List<String> list = new ArrayList<String>();
		Element child = (Element)(mementoTaskDef.getElementsByTagName("selection").item(0));
		NodeList nodes = child.getElementsByTagName("answer");
		for(int i=0; i<nodes.getLength(); i++){
			Element node = (Element)nodes.item(i);			
			String a = node.getAttribute("symbol")+node.getTextContent();
			list.add(a);
		}
		return list;
	}

	@Override
	public int getCountQuestions() {
		Element child = (Element)(mementoTaskDef.getElementsByTagName("mcList").item(0));
		return child.getElementsByTagName("question").getLength();
	}

	@Override
	public String getQuestionProblem(int number) {
		Element child = (Element)(mementoTaskDef.getElementsByTagName("mcList").item(0));
		NodeList list = child.getElementsByTagName("question");
		Element el = (Element)list.item(number);
		return el.getElementsByTagName("problem").item(0).getTextContent();
	}

	@Override
	public String getQuestionHint(int number) {
		Element child = (Element)(mementoTaskDef.getElementsByTagName("mcList").item(0));
		NodeList list = child.getElementsByTagName("question");
		Element el = (Element)list.item(number);
		return el.getElementsByTagName("hint").item(0).getTextContent();
	}

	@Override
	public List<String> getQuestionsAnswers(int question) {
		List<String> answerList = new ArrayList<String>();
		Element child = (Element)(mementoTaskDef.getElementsByTagName("mcList").item(0));
		NodeList list = child.getElementsByTagName("question");
		Element el = (Element)list.item(question);
		NodeList answers = el.getElementsByTagName("answer");
		for(int i=0; i<answers.getLength(); i++){
			Element a = (Element)answers.item(i);
			answerList.add(a.getTextContent());
		}
		return answerList;
	}

	@Override
	public String getHandlingSolution() {
		NodeList nl = mementoTaskHandling.getElementsByTagName("answer");
		if (nl != null){
			Element el = (Element)nl.item(0);
			if (el != null){
				return el.getTextContent();
			}
		}
		return null;
	}

}
