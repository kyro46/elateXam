package com.spiru.dev.compareTextTask_addon;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.thorstenberger.taskmodel.TaskApiException;
import de.thorstenberger.taskmodel.complex.complextaskdef.ComplexTaskDefRoot.CorrectionModeType;
import de.thorstenberger.taskmodel.complex.complextaskhandling.CorrectionSubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.subtasklets.impl.AbstractAddonSubTasklet;
import de.thorstenberger.taskmodel.complex.jaxb.AddonSubTaskDef;
import de.thorstenberger.taskmodel.complex.jaxb.ComplexTaskHandling.Try.Page.AddonSubTask;
import de.thorstenberger.taskmodel.complex.jaxb.SubTaskDefType;

public class SubTasklet_CompareTextTaskImpl  extends AbstractAddonSubTasklet implements SubTasklet_CompareTextTask {
	private Element memento;

	public SubTasklet_CompareTextTaskImpl( SubTaskDefType aoSubTaskDef, AddonSubTask atSubTask, CorrectionModeType correctionMode, float reachablePoints ) {
		super(aoSubTaskDef, atSubTask, correctionMode, reachablePoints);
		parseSubTask(atSubTask);
		atSubTask.setTaskType(getAddOnType());
	}

	@Override
	public String getProblem(){
		//ruft die Methode von AddonSubtasklet auf, dort wird auch das Problem in der XML Version gespeichert
		//muss nicht überschrieben werden!!!!
		return super.getProblem();

	}

	@Override
	public String getAnswer(){
		String answer= getText(memento,"answer",null);
		if(answer==null) {
			answer=getText(memento,"defaultAnswer",null);
		}
		return answer;
	}
	@Override
	public String getLastCorrectedAnswer(){
		String answer= getText(memento,"lastCorrectedAnswer",null);
		if(answer==null) {
			answer=getAnswer();
		}
		return answer;
	}

	@Override
	public void doSave( SubmitData submitData ) throws IllegalStateException{
		CompareTextTaskSubmitData tsd = (CompareTextTaskSubmitData) submitData;
		//anordnungSubTask.setAnswer( tsd.getAnswer() );
	}

	@Override
	public void doAutoCorrection(){
		try {
			//anordnungSubTask.setAnordnungScore(334);
			//anordnungSubTask.setLastCorrectedAnswer(getAnswer());
		} catch (Exception e) {//TODO bloed
			e.printStackTrace();
		}
	}


	@Override
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
	}


	@Override
	public boolean isProcessed(){
		return getAnswer()!=null
				&& getAnswer().length() > 0
				&& !getAnswer().equals(getText(memento,"defaultAnswer",null));
	}

	/*
	 *  (non-Javadoc)
	 * @see de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet#build()
	 */
	@Override
	public void build(long randomSeed) throws TaskApiException {
		// nothing to build :)
		//System.out.println("SJFLKHKL\n\n");
		// except:
		//anordnungSubTask.setAnswer( "Hier wurde die Methode build aufgerufen!!!!!!! Was macht sie es und wann?" );

	}

	@Override
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

	private void setText(Element memento, String nodeName, String text) {
		Element element=getElement(memento,nodeName);
		if(element==null) {
			element=memento.getOwnerDocument().createElement(nodeName);
			memento.appendChild(element);
		}
		element.setTextContent(text);

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

	@Override
	public String getAddOnType() {
		return ((AddonSubTaskDef)this.jaxbSubTaskDef).getTaskType();
	}

	@Override
	public String getTagsString() {
		Element e = null;
		String ret = "";
		NodeList nl=memento.getElementsByTagName("XML");
		if(nl.getLength()>0) {
			e = (Element) nl.item(0);
			System.out.println(e);
			ret += e.toString();
		}
		return ret;
	}

	@Override
	public String getInitialText() {
		//return "";
		return "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
				+ "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat."
				+ "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi."
				+ "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat."
				+ "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis."
				+ "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat."
				+ "Consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus."; 
	}

	@Override
	public String getSampleSolution() {
		return "";
	}

}
