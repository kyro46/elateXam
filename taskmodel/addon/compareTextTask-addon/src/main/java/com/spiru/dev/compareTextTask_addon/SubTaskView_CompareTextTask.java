package com.spiru.dev.compareTextTask_addon;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

import javax.management.modelmbean.XMLParseException;
import de.thorstenberger.taskmodel.MethodNotSupportedException;
import de.thorstenberger.taskmodel.complex.ParsingException;
import de.thorstenberger.taskmodel.complex.complextaskhandling.CorrectionSubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;
import de.thorstenberger.taskmodel.view.SubTaskView;
import de.thorstenberger.taskmodel.view.ViewContext;

public class SubTaskView_CompareTextTask extends SubTaskView{

	private SubTasklet_CompareTextTask subTasklet;

	public SubTaskView_CompareTextTask( SubTasklet_CompareTextTask subTaskletObject ) {
		this.subTasklet = subTaskletObject;
	}

	@Override
	public String getRenderedHTML( ViewContext context, int relativeTaskNumber) {
		return getRenderedHTML( relativeTaskNumber, false );
	}

	public String getRenderedHTML(int relativeTaskNumber, boolean corrected) {
		String path = "com/spiru/dev/compareTextTask_addon/CompareTextApplet.class";
		String ret = "<applet archive=\"applet/compareTextTask.jar\" code=\"" + path + "\""
				+ " id=\"applet_%s\""
				+ " width=\"710\" height=\"540\" title=\"Java\">\n";
		ret += "<param name=\"memento\" value=\"" + subTasklet.getMementoAsBase64() + "\">\n";
		try {
			ret += "<param name=\"soFarSolution\" value=\"" + subTasklet.getResultAsBase64() + "\">\n";
		} catch (XMLParseException e) {
			e.printStackTrace();
		}
		ret += "<param name=\"viewOnly\" value=\"" + corrected + "\">\n";
		ret += "</applet>\n";
		// SavePageAction.getSubmitData() erwartet eine bestimmte Benennung von Element-Name und -ID!
		ret += "<textarea name=\"task[%s].result\" id=\"task_%s.result\" style=\"display:none;\"></textarea>";
		if (!corrected) {
			ret += "<script type=\"text/javascript\">\n";
			ret += " var preSave_task_%s = function(){\n";
			ret += " 	document.getElementById(\"task_%s.result\").value = document.applet_%s.getResult();\n";			
			ret += "};\n";			
			ret += " var leavePage_task_%s = function(){\n";			
			ret += " 	if( document.applet_%s.hasChanged() ){\n";
			ret += " 		setModified();\n";			
			ret += " 	};\n";
			ret += " };\n";
			
			ret += "preSaveManager.registerCallback( preSave_task_%s );\n";
			ret += "leavePageManager.registerCallback( leavePage_task_%s );\n";
			ret += "</script>\n";
		}
		return ret.replaceAll("%s",""+relativeTaskNumber);
	}

	@Override
	public String getCorrectedHTML( ViewContext context, int relativeTaskNumber ){
		// @see http://stackoverflow.com/questions/4874626/java-escape-html
		//ret = StringUtils.replaceEach(ret, new String[]{"<", ">"}, new String[]{"&lt;", "&gt;"});
		//System.out.println("RESULT" + subTasklet.getResult());
		String ret = "";
		try {
			ret = subTasklet.getResult();
			ret = ret.replaceAll("<br/>", "\n");
			ret = ret.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			ret = ret.replaceAll("\n", "<br/>");
		} catch (XMLParseException e) {
			e.printStackTrace();
		}
		ret = "<b>Eingereichte Lösung des Studenten: </b><br/>" + ret;
		return ret;
	}

	@Override
	public String getCorrectionHTML(String actualCorrector, ViewContext context ){
		String ret = getRenderedHTML( -1, true );
		//System.out.println(actualCorrector);
		//System.out.println(context.toString());
		ret += getCorrectorPointsInputString(actualCorrector, "Anordnung", subTasklet);
		return ret;
	}

	/**
	 * @see de.thorstenberger.uebman.services.student.task.complex.SubTaskView#getSubmitData(java.util.Map, int)
	 */
	@Override
	public SubmitData getSubmitData(Map postedVarsForTask) throws ParsingException {
		Iterator it = postedVarsForTask.keySet().iterator();
		while( it.hasNext() ) {
			String key=(String) it.next();
			String value = (String) postedVarsForTask.get(key);	
			return new CompareTextTaskSubmitData( value );
		}
		throw new ParsingException();
	}

	@Override
	public CorrectionSubmitData getCorrectionSubmitData( Map postedVars ) throws ParsingException, MethodNotSupportedException{
		Iterator it = postedVars.values().iterator();
		if( it.hasNext() ){
			float points;
			try {
				points = NumberFormat.getInstance().parse( (String) it.next() ).floatValue();
			} catch (ParseException e) {
				throw new ParsingException( e );
			}
			return new CompareTextTaskCorrectionSubmitData( points );
		} else
			throw new ParsingException();
	}

}
