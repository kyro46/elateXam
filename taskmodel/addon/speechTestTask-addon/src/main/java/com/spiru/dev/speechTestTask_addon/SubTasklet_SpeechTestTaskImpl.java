package com.spiru.dev.speechTestTask_addon;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import de.thorstenberger.taskmodel.TaskApiException;
import de.thorstenberger.taskmodel.complex.complextaskdef.Block;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDefRoot;
import de.thorstenberger.taskmodel.complex.complextaskhandling.CorrectionSubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.subtasklets.impl.AbstractAddonSubTasklet;
import de.thorstenberger.taskmodel.complex.jaxb.AddonSubTaskDef;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskHandling.Try.Page.AddonSubTask;
import de.thorstenberger.taskmodel.complex.jaxb.SubTaskDefType;

public class SubTasklet_SpeechTestTaskImpl extends AbstractAddonSubTasklet implements SubTasklet_SpeechTestTask {
	private Element mementoTaskDef;
	private Element mementoTaskHandling;
	//private AddonSubTask subTaskObject;

	public SubTasklet_SpeechTestTaskImpl( ComplexTaskDefRoot root, Block block, SubTaskDefType aoSubTaskDef, AddonSubTask atSubTask ) {
		super(root, block,aoSubTaskDef,atSubTask);	
		mementoTaskDef = ((AddonSubTaskDef)aoSubTaskDef).getMemento();
		mementoTaskHandling = atSubTask.getMemento();
		//subTaskObject = atSubTask;
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
		SpeechTestTaskSubmitData ctSD = (SpeechTestTaskSubmitData) submitData;
		setAnswer(ctSD.getAnswer());
	}
	
	@Override
    public boolean isSetNeedsManualCorrectionFlag() {		
		return false;
	}
	
	public List<Boolean> getAnswerList(){
		NodeList mcList = mementoTaskDef.getElementsByTagName("mcList");
		if (mcList == null)
			return null;
		NodeList questions = ((Element)mcList.item(0)).getElementsByTagName("question");
		List<Boolean> boolList = new ArrayList<Boolean>();
		// alle radioButtons auflisten und vermerken, ob sie wahr oder falsch sein sollten
		for(int i=0; i<questions.getLength(); i++){				
			NodeList list = ((Element)questions.item(i)).getElementsByTagName("answer");				
			for(int k=0; k<list.getLength(); k++){
				Element el = (Element)list.item(k);
				if (el.getAttribute("correct")!=null && el.getAttribute("correct").equals("true")){
					boolList.add(true);
				}
				else boolList.add(false);
			}		
		}
		return boolList;
	}

	@Override
	public void doAutoCorrection(){						
		float points = block.getPointsPerSubTask();
		// Elemente aus der Aufgabe holen...
		NodeList mcList = mementoTaskDef.getElementsByTagName("mcList");		
		if (mcList != null){									
			// antworten vom studenten holen
			String solution = getHandlingSolution();
			if (solution == null){				
				setAutoCorrection( 0 );
				return;
			}			
			
			List<Boolean> boolList = getAnswerList();
			// {true, false}-FragenNummer//{true, false}-FragenNummer
			Boolean[] b = null;			
			String[] sp = solution.split("//");        
			b = new Boolean[sp.length];
			for(int i=0; i<b.length; i++){
				if ( sp[i].split("-")[0].equals("true") )
					b[i] = true;            
				else b[i] = false;
			}	
						
			int count = 0;
			for(int i=0; i<getCountQuestions(); i++){				
				for(int k=0; k<getQuestionsAnswers(i).size(); k++){						
					if (b[count+k] != boolList.get(count+k)){
						points-=1;
						break;
					}					
				}
				count += getQuestionsAnswers(i).size();
			}
			
			if (points < 0)
				points = 0;
			setAutoCorrection( points );
		}
		/*
		 * <question><answer correct="true">Text</answer><answer>...</answer></question
		 * <question>.......</question>
		 * 
		 * private boolean isCorrectAnser(questionNum, answerNum){
		 * 		suche question raus, suche answer Element raus, hat das das attribut correct="true" ?
		 * 		wenn ja -> return true else false;
		 * }
		 */
	}

	@Override
	public void doManualCorrection( CorrectionSubmitData csd ){	
		/*
		System.out.println("*****************************");
		// max. Punkte fuer diese aufgabe
		System.out.println(block.getPointsPerSubTask());		
		System.out.println("*****************************");
		SpeechTestTaskCorrectionSubmitData pcsd = (SpeechTestTaskCorrectionSubmitData) csd;
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
		*/
	}

	@Override
	public boolean isProcessed(){
		String solution = getHandlingSolution();
		if (solution == null)
			return false;	
		// Wenn auch nur eine einzige Antwort gegeben, dann markiere als bearbeitet
		String[] sp = solution.split("//");        		
		for(int i=0; i<sp.length; i++){
			if ( sp[i].split("-")[0].equals("true") ){
				return true;
			}		            		
		}
		// Wenn keine einzige Antwort selektiert, dann nicht bearbeitet
		return false;
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
