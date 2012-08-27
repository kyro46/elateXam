package com.spiru.dev.compareTextTask_addon;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.thorstenberger.taskmodel.TaskApiException;
import de.thorstenberger.taskmodel.complex.RandomUtil;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDefRoot.CorrectionModeType;
import de.thorstenberger.taskmodel.complex.complextaskhandling.CorrectionSubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.subtasklets.impl.AbstractAddonSubTasklet;
import de.thorstenberger.taskmodel.complex.jaxb.AddonSubTaskDef;
import de.thorstenberger.taskmodel.complex.jaxb.SubTaskDefType;
import de.thorstenberger.taskmodel.complex.jaxb.SubTaskType;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskHandling.Try.Page.AddonSubTask;

public class SubTasklet_CompareTextTaskImpl  extends AbstractAddonSubTasklet implements SubTasklet_CompareTextTask {

	private class AnordnungSubTaskDummy{
		private Element memento;


		public AnordnungSubTaskDummy(AddonSubTask atSubTask) {
			parseSubTask(atSubTask);
			atSubTask.setTaskType(getAddOnType());
		}

		private void parseSubTask(AddonSubTask atSubTask) {
			this.memento=atSubTask.getMemento();
			if(memento==null) {
				try {
					memento=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument().createElementNS("http://complex.taskmodel.thorstenberger.de/complexTaskHandling","Memento");
				} catch (Exception e) {
					// TODO make sure there is always a memento element!
					e.printStackTrace();
				}
				atSubTask.setMemento(memento);
			}
		}

		public String getAnswer() {
			return getText(memento,"answer",null);
		}
		public void setAnswer(String value) {
			setText(memento,"answer",value);
		}
		public String getLastCorrectedAnswer() {
			return getText(memento,"lastCorrectedAnswer",null);
		}
		public void setLastCorrectedAnswer(String value) {
			setText(memento,"lastCorrectedAnswer",value);
		}

		public String getDefaultAnswer() {
			return getText(memento,"defaultAnswer",null);
		}

		public void setDefaultAnswer(String defaultAnswer) {
			setText(memento,"defaultAnswer",defaultAnswer);
		}

		public void setAnordnungScore(double score) {
			setText(memento,"score",Double.toString(score));
		}
		public double getAnordnungScore() {
			return Double.parseDouble(getText(memento,"score","-1"));
		}


	}


	private AnordnungSubTaskDummy anordnungSubTask;


	public SubTasklet_CompareTextTaskImpl( SubTaskDefType aoSubTaskDef, AddonSubTask atSubTask, CorrectionModeType correctionMode, float reachablePoints ) {

		super(aoSubTaskDef, atSubTask, correctionMode, reachablePoints);

		this.anordnungSubTask = new AnordnungSubTaskDummy(atSubTask);
	}

	@Override
	public String getProblem(){

		//ruft die Methode von AddonSubtasklet auf, dort wird auch das Problem in der XML Version gespeichert
		//muss nicht überschrieben werden!!!!
		return super.getProblem();

	}

	public String getAnswer(){
		String answer=anordnungSubTask.getAnswer();
		if(answer==null) {
			answer=anordnungSubTask.getDefaultAnswer();
		}
		return answer;
	}
	public String getLastCorrectedAnswer(){
		String answer=anordnungSubTask.getLastCorrectedAnswer();
		if(answer==null) {
			answer=getAnswer();
		}
		return answer;
	}

	public void doSave( SubmitData submitData ) throws IllegalStateException{
		CompareTextTaskSubmitData tsd = (CompareTextTaskSubmitData) submitData;
		anordnungSubTask.setAnswer( tsd.getAnswer() );
	}

	public void doAutoCorrection(){
		try {

			//Dummywert FRAGE WOHER NEHMEN WENN NICHT STEHLEN????
			anordnungSubTask.setAnordnungScore(334);
			anordnungSubTask.setLastCorrectedAnswer(getAnswer());
		} catch (Exception e) {//TODO bloed
			e.printStackTrace();
		}
	}


	public void doManualCorrection( CorrectionSubmitData csd ){
		CompareTextTaskCorrectionSubmitData acsd=(CompareTextTaskCorrectionSubmitData) csd;
		setCorrection(acsd.getPoints(), "manually corrected", false);
	}

	protected void setCorrection( float points, String doc, boolean auto ){
		if(auto) {
			super.setAutoCorrection(points);
		}
		else {
			;//TODO
		}
		//Für was ist AutotoolDoc bzw. AnordnungsDoc??????????
		//anordnungSubTask.setAutotoolDoc(doc);
	}


	public boolean isProcessed(){
		return anordnungSubTask.getAnswer()!=null
				&& anordnungSubTask.getAnswer().length() > 0
				&& !anordnungSubTask.getAnswer().equals(anordnungSubTask.getDefaultAnswer());//mmh, was wenn die aber die richtige ist? wohl unwahrscheinlich
	}

	/*
	 *  (non-Javadoc)
	 * @see de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet#build()
	 */
	public void build(long randomSeed) throws TaskApiException {
		// nothing to build :)
		//System.out.println("SJFLKHKL\n\n");
		// except:
		anordnungSubTask.setAnswer( "Hier wurde die Methode build aufgerufen!!!!!!! Was macht sie es und wann?" );

	}

	private byte[] serialize(Object obj) {
		try {
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.close();
			byte[] arr=bos.toByteArray();
			bos.close();
			return arr;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	private Object deserialize(byte[] arr) {
		try {
			ByteArrayInputStream bos=new ByteArrayInputStream(arr);
			ObjectInputStream oos=new ObjectInputStream(bos);
			Object o=oos.readObject();
			oos.close();
			bos.close();
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}





	public int getHash(){
		StringBuffer ret = new StringBuffer();
		ret.append( subTaskType.getRefId() );
		ret.append( getAnswer() );
		ret.append( getVirtualSubtaskNumber() );
		return ret.toString().hashCode();
	}

	//Helper methods........................................................

	private Element getElement(Element memento, String string) {
		Element e=null;
		NodeList nl=memento.getElementsByTagName(string);
		if(nl.getLength()>0) {
			e=(Element) nl.item(0);
		}
		return e;
	}

	private String getText(Element memento, String nodeName, String dflt) {
		Element element=getElement(memento,nodeName);
		if(element!=null) {
			String text = element.getFirstChild().getTextContent();
			return text;
		}
		return dflt;
	}

	private String deEscapeXML(String text) {
		return text.replaceAll("&amp;", "&")
				.replaceAll("&lt;", "<")
				.replaceAll("&gt;", ">")
				.replaceAll("&apos;", "\\'")
				.replaceAll("&quot;", "\\\"");
	}

	private String escapeXML(String text) {
		return text.replaceAll("&", "&amp;")
				.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;")
				.replaceAll("\\'", "&apos;")
				.replaceAll("\\\"", "&quot;");
	}

	private void setText(Element memento, String nodeName, String text) {
		Element element=getElement(memento,nodeName);
		if(element==null) {
			element=memento.getOwnerDocument().createElement(nodeName);
			memento.appendChild(element);
		}
		element.setTextContent(text);//setTextContent() laesst sich mit maven nicht kompilieren??? dom3 nicht im classpath?

	}


	public String getAddOnType() {
		return ((AddonSubTaskDef)this.jaxbSubTaskDef).getTaskType();
	}


	public double getAnordnungScore() {
		return anordnungSubTask.getAnordnungScore();
	}

	public String getAnordnungGradeDoc() {
		// TODO Auto-generated method stub
		//Für was wird das benötigt????
		return "SubTasklet_AnordnungImpl.getAnordnungGradDoc() wurde aufgerufen!!!!";
	}


}
