package com.spiru.dev.groupingTask_addon;

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

public class SubTaskView_GroupingTask extends SubTaskView{

	private SubTasklet_GroupingTask AnordnungSubTasklet;

	/**
	 *
	 */
	public SubTaskView_GroupingTask( SubTasklet_GroupingTask anordnungSubTasklet ) {
		this.AnordnungSubTasklet = anordnungSubTasklet;
	}

	/**
	 * @see de.thorstenberger.uebman.services.student.task.complex.SubTaskView#getRenderedHTML(int)
	 */
	public String getRenderedHTML( ViewContext context, int relativeTaskNumber) {
		return getRenderedHTML( relativeTaskNumber, false );
	}

	public String getRenderedHTML(int relativeTaskNumber, boolean corrected) {
		final StringBuffer ret = new StringBuffer();
		String path = "com/spiru/dev/groupingTask_addon/GroupingTaskAddOnApplet.class";
		ret.append("<applet archive=\"applet/groupingTask.jar,applet/lib/jdom-1.0.jar\" code=\"" + path + "\" width=\"710\" height=\"540\" title=\"Java\">\n");
		/*
		 * ret.append( <param name="url" value="http://java-tutor.com/index.html">);
		 */
		ret.append("</applet>\n");
		return ret.toString();

	}

	public String getCorrectedHTML( ViewContext context, int relativeTaskNumber ){
		return getRenderedHTML( -1, true );
	}

	public String getCorrectionHTML(String actualCorrector, ViewContext context ){
	    StringBuffer ret = new StringBuffer();
	    ret.append( getRenderedHTML( -1, true ) );

	    ret.append(getCorrectorPointsInputString(actualCorrector, "Anordnung", AnordnungSubTasklet));

	    return ret.toString();
	}

	/**
	 * @see de.thorstenberger.uebman.services.student.task.complex.SubTaskView#getSubmitData(java.util.Map, int)
	 */
	public SubmitData getSubmitData(Map postedVarsForTask)
	throws ParsingException {

		Iterator it = postedVarsForTask.keySet().iterator();
		while( it.hasNext() ) {
			String key=(String) it.next();
			if( getMyPart( key ).equals( "Anordnung" ) )
				return new GroupingTaskSubmitData( (String) postedVarsForTask.get(key) );
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
            return new GroupingTaskCorrectionSubmitData( points );
	    }else
	        throw new ParsingException();
	}

}
