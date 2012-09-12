package com.spiru.dev.compareTextTask_addon;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

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
		ret += "<param name=\"initialText\" value=\"" + subTasklet.getInitialText() + "\">\n";
		//ret += "<param name=\"initialResult\" value=\"" + subTasklet.getInitialText() + "\">\n";
		ret += "<param name=\"xmlDef\" value=\"" + subTasklet.getTagsString() + "\">\n";
		ret += "</applet>\n";
		// SavePageAction.getSubmitData() erwartet eine bestimmte Benennung von Element-Name und -ID!
		ret += "<textarea name=\"task[%s].result\" id=\"task_%s.result\" style=\"display:none;\"></textarea>";
		if (!corrected) {
			ret += "<script type=\"text/javascript\">\n";
			ret += " var preSave_task_%s = function(){\n";
			ret += " 	document.getElementById(\"task_%s.result\").value = document.applet_%s.getResult();\n";
			ret += " 	alert(document.getElementById(\"task_%s.result\").value);\n";
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
		return getRenderedHTML( -1, true );
	}

	@Override
	public String getCorrectionHTML(String actualCorrector, ViewContext context ){
		StringBuffer ret = new StringBuffer();
		ret.append( getRenderedHTML( -1, true ) );
		ret.append(getCorrectorPointsInputString(actualCorrector, "Anordnung", subTasklet));
		return ret.toString();
	}

	/**
	 * @see de.thorstenberger.uebman.services.student.task.complex.SubTaskView#getSubmitData(java.util.Map, int)
	 */
	@Override
	public SubmitData getSubmitData(Map postedVarsForTask) throws ParsingException {
		// Hier die daten rausholen
		String debugstr = "DESCR:" + postedVarsForTask.toString();
		Iterator it = postedVarsForTask.keySet().iterator();
		while( it.hasNext() ) {
			String key = (String) it.next();
			String value = (String) postedVarsForTask.get(key);
			debugstr = key + "_" + value + "\n";
			return new CompareTextTaskSubmitData(value);
		}
		//return new CompareTextTaskSubmitData("LLER");
		throw new ParsingException(debugstr);
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
