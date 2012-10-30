package com.spiru.dev.rtypeTask_addon;

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

public class SubTaskView_RtypeTask extends SubTaskView{

	private SubTasklet_RtypeTask rtypeSubTasklet;	

	public SubTaskView_RtypeTask( SubTasklet_RtypeTask rtypeSubTasklet ) {
		this.rtypeSubTasklet = rtypeSubTasklet;
		
	}

	/**
	 * @see de.thorstenberger.uebman.services.student.task.complex.SubTaskView#getRenderedHTML(int)
	 */
	public String getRenderedHTML( ViewContext context, int relativeTaskNumber) {		
		return getRenderedHTML( relativeTaskNumber, false );
	}

	public String getRenderedHTML(int relativeTaskNumber, boolean corrected) {				
		StringBuffer ret = new StringBuffer();		
		ret.append("\n");
		ret.append("<table>");
		ret.append("\n");
		// selection->text
		ret.append("<tr>");		
		ret.append("<td nowrap valign=top><textarea name=\"task["+relativeTaskNumber+"]\" id=\"task_"+relativeTaskNumber+".result\" style=\"display:none;\">clear</textarea></td>");
		ret.append("\n");
		ret.append("</tr>");
		// selection->hint
		ret.append("<tr>");
		//ret.append("\n  <td nowrap valign=top><i>"+rtypeSubTasklet.getSelectionHint()+"</i></td>");
		ret.append("\n");
		ret.append("</tr>\n");		
		ret.append("</table>");				
		String solution = rtypeSubTasklet.getHandlingSolution();		
		String[] text = null;
		String[][] list = null;		
		if (solution != null){			
			text = solution.split("//");			
			list = new String[text.length][2]; 
			for(int i=0; i<text.length;i++){				
				String[] a = text[i].split("--");
				list[i][0] = a[0];
				list[i][1] = a[1];
			}
		}		
		// Setze Teilfragen in div-Bereich zum scrollen
		ret.append("\n<div style=\" height:220px; width:520px; font-size:12px; overflow-x:hidden; overflow-y:visible;\">");		
		// mcList->questions		
		for(int i=0; i<rtypeSubTasklet.getCountQuestions(); i++){			
			if (list!=null){								
				ret.append(setQuestions(i, relativeTaskNumber, list[i][0], corrected));				
			}
			else{				
				ret.append(setQuestions(i, relativeTaskNumber, null, corrected));
			}
		}	
		// ende div		
		ret.append("\n</div>");
		
		if (!corrected) {
			ret.append("<script type=\"text/javascript\">\n");
			ret.append(" var preSave_task_"+relativeTaskNumber+" = function(){\n");
			ret.append(
		    "var sol = \"\";\n"+
		    "var relativeTaskNumber = \""+relativeTaskNumber+"\";\n"+	
			"for(var i = 0; i<"+rtypeSubTasklet.getCountQuestions()+"; i++){\n"+			
			"var clear = \"\";\n"+
	        "  for(var k = 0; k<document.getElementsByName(\"[\"+i+\"][\"+relativeTaskNumber+\"]\").length; k++){\n"+	        
	        "    if(document.getElementsByName(\"[\"+i+\"][\"+relativeTaskNumber+\"]\")[k].checked && document.getElementsByName(\"[\"+i+\"][\"+relativeTaskNumber+\"]\")[k].id == relativeTaskNumber ){\n"+	        
	        "        clear += document.getElementsByName(\"[\"+i+\"][\"+relativeTaskNumber+\"]\")[k].value+\"--\"+i+\"//\" ;\n"+
	        "        document.getElementsByName(\"[\"+i+\"][\"+relativeTaskNumber+\"]\")[k].checked = false;\n"+
	        "    }\n"+                        
	        "  }\n"+
	        "if (clear == \"\")\n"+
	        "  clear = \"null--\"+i+\"//\"\n"+
	        "sol += clear;\n"+
	        "}\n"+	
		    "alert(\"Text: \"+sol)\n"
		    );
			ret.append("document.getElementById(\"task_"+relativeTaskNumber+".result\").value = sol;\n");		
			ret.append("};\n");
			ret.append("preSaveManager.registerCallback( preSave_task_"+relativeTaskNumber+" );\n");
			ret.append("leavePageManager.registerCallback( leavePage_task_"+relativeTaskNumber+" );\n");
			ret.append("</script>\n");
		}
		return ret.toString();
	}
	
	private String setQuestions(int num, int relativ, String param, boolean disabled){		
		StringBuffer ret = new StringBuffer();
		// problem			
		ret.append("<hr>\n");
		ret.append("<table width=\"100%\" border=\"0\">");
		ret.append("\n<tr>\n  <td valign=top><b>"+rtypeSubTasklet.getQuestionProblem(num)+"</b></td>");
		ret.append("\n"); ret.append("</tr>");			
		// hint
		ret.append("<tr>\n  <td nowrap valign=top><i>"+rtypeSubTasklet.getQuestionHint(num)+"</i></td>");
		ret.append("\n"); ret.append("</tr>");
		// answers						
		for(String n:rtypeSubTasklet.getQuestionsAnswers(num)){
			ret.append("<tr>");				
			String sel = "";
			if (n.equals(param)){
				sel = "checked";
			}
			if (disabled){
				sel += " disabled";
			}
			ret.append("\n  <td nowrap valign=top> <input type=\"radio\" name=\"["+num+"]["+relativ+"]\"" +					
					   " id=\""+relativ+"\" value=\""+n+"\" "+sel+">"+n+"</td>");
			ret.append("\n");
			ret.append("</tr>");			
		}
		
		ret.append("\n");
		ret.append("</table>");

		return ret.toString();
	}

	public String getCorrectedHTML( ViewContext context, int relativeTaskNumber ){
		// for pdf...
		StringBuffer ret = new StringBuffer();		
		ret.append(getRenderedHTML( -1, true ));
		return ret.toString();
		
	}

	public String getCorrectionHTML(String actualCorrector, ViewContext context ){	
	    StringBuffer ret = new StringBuffer();
	    ret.append( getRenderedHTML( -1, true ) );	    	   

	    ret.append(getCorrectorPointsInputString(actualCorrector, "Rtype", rtypeSubTasklet));

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
			return new RtypeTaskSubmitData( value );
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
            return new RtypeTaskCorrectionSubmitData( points );
	    }else
	        throw new ParsingException();
	}

}
