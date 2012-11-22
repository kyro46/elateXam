package com.spiru.dev.compareTextTask_addon;

import java.util.List;

import javax.management.modelmbean.XMLParseException;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.spiru.dev.compareTextTask_addon.Utils.XMLBase64;

import de.thorstenberger.taskmodel.TaskApiException;
import de.thorstenberger.taskmodel.complex.complextaskdef.Block;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDefRoot;
import de.thorstenberger.taskmodel.complex.complextaskhandling.CorrectionSubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.subtasklets.impl.AbstractAddonSubTasklet;
import de.thorstenberger.taskmodel.complex.jaxb.AddonSubTaskDef;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskHandling.Try.Page.AddonSubTask;
import de.thorstenberger.taskmodel.complex.jaxb.ManualCorrectionType;
import de.thorstenberger.taskmodel.complex.jaxb.SubTaskDefType;

public class SubTasklet_CompareTextTaskImpl extends AbstractAddonSubTasklet implements SubTasklet_CompareTextTask {
	private AddonSubTask subTaskObject;
	private Element mementoTaskDef;
	private Element mementoTaskHandling;

	public SubTasklet_CompareTextTaskImpl( ComplexTaskDefRoot root, Block block, SubTaskDefType aoSubTaskDef, AddonSubTask atSubTask ) {
		super(root, block,aoSubTaskDef,atSubTask);
		subTaskObject = atSubTask;
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
		//System.out.println("\n\ndoSave() Called");
		CompareTextTaskSubmitData ctSD = (CompareTextTaskSubmitData) submitData;
		setResult(ctSD.getResultString());
	}

	@Override
	public void doAutoCorrection(){
		//System.out.println("\n\ndoAutoCorrection() Called");
		try {
			// TODO!
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private float checkPoints(float maxPointsPerTask, float points){
		float pointsCorrector = points;
		if (pointsCorrector < 0) 
			pointsCorrector = 0;
		if (pointsCorrector > maxPointsPerTask)
			pointsCorrector = maxPointsPerTask;
		return pointsCorrector;		
	}
	
	@Override
	public void doManualCorrection( CorrectionSubmitData csd ){
		//System.out.println("\n\ndoManualCorrection() Called");
		CompareTextTaskCorrectionSubmitData pcsd = (CompareTextTaskCorrectionSubmitData) csd;
		final float maxPointsPerTask = block.getPointsPerSubTask();
		//super.setAutoCorrection(acsd.getPoints());

		//if( isAutoCorrected() )
		//	throw new IllegalStateException( TaskHandlingConstants.SUBTASK_AUTO_CORRECTED );
		//if( pcsd.getPoints() < 0 || pcsd.getPoints() > reachablePoints )
		//	return;

		List<ManualCorrectionType> manualCorrections = subTaskObject.getManualCorrection();
		if( complexTaskDefRoot.getCorrectionMode().getType() == ComplexTaskDefRoot.CorrectionModeType.MULTIPLECORRECTORS ) {
			for( ManualCorrectionType mc : manualCorrections ){
				if( mc.getCorrector().equals( pcsd.getCorrector() ) ){
					mc.setPoints( checkPoints(maxPointsPerTask, pcsd.getPoints()) );
					return;
				}
			}
			// corrector not found, so create a new ManualCorrection for him
			ManualCorrectionType mc;
			mc = objectFactory.createManualCorrectionType();
			mc.setCorrector(pcsd.getCorrector());
			mc.setPoints( checkPoints(maxPointsPerTask, pcsd.getPoints()) );
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
			mc.setPoints( checkPoints(maxPointsPerTask, pcsd.getPoints()) );
		}
	}

	@Override
    public boolean isSetNeedsManualCorrectionFlag() {		
		return true;
	}
	
	@Override
	public boolean isProcessed(){
		try {
			if (getResult().equals("EMPTY"))
				return false;
		} catch (XMLParseException e) {
			e.printStackTrace();
		}
		return true;
		//return getResult() != null && getResult().length() > 0 && !getResult().equals(getInitialText());
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
		String ret = "";
		try {
			ret = subTaskType.getRefId() + getResult() + getVirtualSubtaskNumber();
		} catch (XMLParseException e) {
			e.printStackTrace();
		}
		return ret.hashCode();
	}

	// METHODS WHICH ARE SPECIFIC FOR THIS TASK FOLLOW FROM HERE:

	@Override
	public String getMementoAsBase64() {
		return XMLBase64.elementToBase64String(mementoTaskDef, null);
	}

	@Override
	public String getResult() throws XMLParseException {
		// sometimes we need the result not encoded via Base64, e.g. when generating PDF
		NodeList resultElement = mementoTaskHandling.getElementsByTagName("answer");
		if(resultElement.getLength() == 1)
			return resultElement.item(0).getFirstChild().getNodeValue();
		else if(resultElement.getLength() > 1) // see constructor -> never happens unless somewone changes things somewhere
			throw new XMLParseException("Unhandled Exception: mementoTaskHandling shall never contain more than one Answer");
		return "EMPTY";
	}

	@Override
	public String getResultAsBase64() throws XMLParseException {
		// send to browser as Base64 String (safer, as it may contain line-breaks etc)
		String result = getResult();
		if (result != "EMPTY")
			return DatatypeConverter.printBase64Binary(result.getBytes());
		return result;
	}

	private void setResult(String resultString) {
		Element resultElement = (Element) mementoTaskHandling.getElementsByTagName("answer").item(0);
		if(resultElement == null) {
			resultElement = mementoTaskHandling.getOwnerDocument().createElement("answer");
			mementoTaskHandling.appendChild(resultElement);
		}
		// will be sent as Base64 String
		String s = new String(DatatypeConverter.parseBase64Binary(resultString));
		resultElement.setTextContent(s);
	}
}
