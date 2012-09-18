package com.spiru.dev.timeTask_addon;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import de.thorstenberger.taskmodel.MethodNotSupportedException;
import de.thorstenberger.taskmodel.complex.ParsingException;
import de.thorstenberger.taskmodel.complex.complextaskhandling.CorrectionSubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;
import de.thorstenberger.taskmodel.view.SubTaskView;
import de.thorstenberger.taskmodel.view.ViewContext;

public class SubTaskView_TimeTask extends SubTaskView{

	private SubTasklet_TimeTask timeSubTasklet;

	/**
	 *
	 */
	public SubTaskView_TimeTask( SubTasklet_TimeTask timeSubTasklet ) {
		this.timeSubTasklet = timeSubTasklet;
	}

	/**
	 * @see de.thorstenberger.uebman.services.student.task.complex.SubTaskView#getRenderedHTML(int)
	 */
	public String getRenderedHTML( ViewContext context, int relativeTaskNumber) {
		return getRenderedHTML( relativeTaskNumber, false );
	}

	public String getRenderedHTML(int relativeTaskNumber, boolean corrected) {		
		String path = "com/spiru/dev/timeTask_addon/TimeTaskAddOnApplet.class";
		String ret = "<applet archive=\"applet/timeTask.jar\" code=\"" + path + "\""
				+ " id=\"applet_%s\""
				+ " width=\"710\" height=\"540\" title=\"Java\">\n";
		ret += "<param name=\"param\" value=\""+timeSubTasklet.getMemento()+"\">";
		if (timeSubTasklet.fromHandling()){
			ret += "<param name=\"handling\" value=\"true\">";			
		}
		else{
			ret += "<param name=\"handling\" value=\"false\">";
		}
		
		/*
		List<String> dragElements = timeSubTasklet.getDragElements();
		for(int i=0; i<dragElements.size(); i++){
			ret += "<param name=\"e"+i+"\" value=\""+dragElements.get(i)+"\">";
		}
		List<String> datePoints = timeSubTasklet.getDatePoints();
		System.out.println("********** add DatePoints to HTML");
		for(int i=0; i<datePoints.size(); i++){
			ret += "<param name=\"dp"+i+"\" value=\""+datePoints.get(i)+"\">";
		}
		*/
		
		ret+= "</applet>\n";
		ret += "<textarea name=\"task[%s].result\" id=\"task_%s.result\" style=\"display:none;\"></textarea>";
		if (!corrected) {
			ret += "<script type=\"text/javascript\">\n";
			ret += " var preSave_task_%s = function(){\n";
			ret += " 	document.getElementById(\"task_%s.result\").value = document.applet_%s.getResult();\n";
			//ret += " 	alert(document.getElementById(\"task_%s.result\").value);\n";
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

	public String getCorrectedHTML( ViewContext context, int relativeTaskNumber ){
		return getRenderedHTML( -1, true );
	}

	public String getCorrectionHTML(String actualCorrector, ViewContext context ){
	    StringBuffer ret = new StringBuffer();
	    ret.append( getRenderedHTML( -1, true ) );

	    ret.append(getCorrectorPointsInputString(actualCorrector, "Time", timeSubTasklet));

	    return ret.toString();
	}

	/**
	 * @see de.thorstenberger.uebman.services.student.task.complex.SubTaskView#getSubmitData(java.util.Map, int)
	 */
	public SubmitData getSubmitData(Map postedVarsForTask) throws ParsingException {
		Iterator it = postedVarsForTask.keySet().iterator();
		while( it.hasNext() ) {
			String key=(String) it.next();
			String value = (String) postedVarsForTask.get(key);	
			return new TimeTaskSubmitData( value );
		}
		throw new ParsingException();
	}

	public CorrectionSubmitData getCorrectionSubmitData( Map postedVars ) throws ParsingException, MethodNotSupportedException{
	    Iterator it = postedVars.values().iterator();
	    if( it.hasNext() ){
	        float points;
            try {
                points = NumberFormat.getInstance().parse( (String) it.next() ).floatValue();
            } catch (ParseException e) {
                throw new ParsingException( e );
            }
            return new TimeTaskCorrectionSubmitData( points );
	    }else
	        throw new ParsingException();
	}

}
