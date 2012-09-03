package com.spiru.dev.timeTask_addon;

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

public class SubTaskView_TimeTask extends SubTaskView{

	private SubTasklet_TimeTask AnordnungSubTasklet;

	/**
	 *
	 */
	public SubTaskView_TimeTask( SubTasklet_TimeTask anordnungSubTasklet ) {
		this.AnordnungSubTasklet = anordnungSubTasklet;
	}

	/**
	 * @see de.thorstenberger.uebman.services.student.task.complex.SubTaskView#getRenderedHTML(int)
	 */
	public String getRenderedHTML( ViewContext context, int relativeTaskNumber) {
		return getRenderedHTML( relativeTaskNumber, false );
	}

	public String getRenderedHTML(int relativeTaskNumber, boolean corrected) {
		StringBuffer ret = new StringBuffer();

		// workaround: textarea nicht disabled
		//corrected = false;

		ret.append("<div align=\"left\">\n");
		ret.append("<textarea name=\"task[" + relativeTaskNumber + "].Anordnung\" cols=\"" +
						AnordnungSubTasklet.getTextFieldWidth() + "\" rows=\"" + AnordnungSubTasklet.getTextFieldHeight() + "\" onChange=\"setModified()\"" +
						( corrected ? "disabled=\"disabled\"" : "" ) + ">\n");
		ret.append( corrected?AnordnungSubTasklet.getLastCorrectedAnswer():AnordnungSubTasklet.getAnswer() );
		ret.append("</textarea></div>\n");

		if(corrected) {
			ret.append("<div class=\"problem\">\n");
			ret.append("Anordnungantwort:<br><br>");
			ret.append(AnordnungSubTasklet.getAnordnungGradeDoc());
			ret.append("</div><br>");
		}

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
				return new TimeTaskSubmitData( (String) postedVarsForTask.get(key) );
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
