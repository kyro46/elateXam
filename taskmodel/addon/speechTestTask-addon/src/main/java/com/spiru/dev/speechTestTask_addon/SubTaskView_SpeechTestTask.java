package com.spiru.dev.speechTestTask_addon;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import de.thorstenberger.taskmodel.MethodNotSupportedException;
import de.thorstenberger.taskmodel.complex.ParsingException;
import de.thorstenberger.taskmodel.complex.complextaskhandling.CorrectionSubmitData;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubmitData;
import de.thorstenberger.taskmodel.view.SubTaskView;
import de.thorstenberger.taskmodel.view.ViewContext;

public class SubTaskView_SpeechTestTask extends SubTaskView{

	private SubTasklet_SpeechTestTask speechTestSubTasklet;
	private HttpServletRequest request;

	public SubTaskView_SpeechTestTask( SubTasklet_SpeechTestTask speechTestSubTasklet ) {
		this.speechTestSubTasklet = speechTestSubTasklet;
		
	}

	/**
	 * @see de.thorstenberger.uebman.services.student.task.complex.SubTaskView#getRenderedHTML(int)
	 */
	public String getRenderedHTML( ViewContext context, int relativeTaskNumber) {
	    request=(HttpServletRequest) context.getViewContextObject();
		return getRenderedHTML( relativeTaskNumber, false );			
	}

	public String getRenderedHTML(int relativeTaskNumber, boolean corrected) {				
		StringBuffer ret = new StringBuffer();		
		ret.append("\n");
		
		//Hide save and save-and-backward Buttons and Mainpagelink to prevent jumping through test
		ret.append("</body><body onload=\"hideBackButton();\"/>\n");
		ret.append("<script type=\"text/javascript\">\n");
			ret.append("function hideBackButton() {\n");
			ret.append("var saveforward = document.getElementById('save-and-forward');\n");	
					ret.append("document.getElementById('save').onclick = function(){\n");
					ret.append("setCookies();\n");
					ret.append("}\n");
					ret.append("if (saveforward != null) {\n");
						ret.append("document.getElementById('save').style.visibility = 'hidden';\n");
						ret.append("}\n");
			ret.append("document.getElementById('save-and-backward').style.visibility = 'hidden';}\n");
			//ret.append("hideBackButton();\n");
		ret.append("</script>\n");		
			
		ret.append("<table>");
		ret.append("\n");
		// selection->text
		ret.append("<tr>");		
		ret.append("<td nowrap valign=top><textarea name=\"task["+relativeTaskNumber+"]\" id=\"task_"+relativeTaskNumber+".result\" style=\"display:none;\">clear</textarea></td>");
		ret.append("\n");
		ret.append("</tr>");
		// selection->hint
		ret.append("<tr>");
		//ret.append("\n  <td nowrap valign=top><i>"+speechTestSubTasklet.getSelectionHint()+"</i></td>");
		ret.append("\n");
		ret.append("</tr>\n");		
		ret.append("</table>\n");				

		//Set JavaScriptCookie to get the right timing in 1. remaining time for task till gray-out and if/when to play flash
		ret.append("<script>\n");
		ret.append("var startTime = new Date();\n");
		ret.append("startTime = startTime.getTime();\n");
		ret.append("startTime = parseInt(startTime/1000); //convert to seconds\n");
		ret.append("function getCookie(c_name)\n");
		ret.append("{\n");
		ret.append("var c_value = document.cookie;\n");
		ret.append("var c_start = c_value.indexOf(\" \" + c_name + \"=\");\n");
		ret.append("if (c_start == -1)\n");
		ret.append("{\n");
		ret.append("c_start = c_value.indexOf(c_name + \"=\");\n");
		ret.append("}\n");
		ret.append("if (c_start == -1)\n");
		ret.append("{\n");
		ret.append("c_value = null;\n");
		ret.append("}\n");
		ret.append("else\n");
		ret.append("{\n");
		ret.append("c_start = c_value.indexOf(\"=\", c_start) + 1;\n");
		ret.append("var c_end = c_value.indexOf(\";\", c_start);\n");
		ret.append("if (c_end == -1)\n");
		ret.append("{\n");
		ret.append("c_end = c_value.length;\n");
		ret.append("}\n");
		ret.append("c_value = unescape(c_value.substring(c_start,c_end));\n");
		ret.append("}\n");
		ret.append("return c_value;\n");
		ret.append("}\n");
		ret.append("function setCookie(name, value, duration) {\n");
		ret.append("var date = new Date();\n");
		ret.append("   //duration = max time for a single task in seconds\n");
		ret.append("date.setTime(date.getTime()+(duration*1000));\n");
		ret.append("var expires = \"; expires=\"+date.toGMTString();\n");
		ret.append("document.cookie = name+\"=\"+value+expires+\"; path=/\";\n");
		ret.append("}\n");
		ret.append("function setCookies()\n");
		ret.append("{\n");
		ret.append("var sendTime=getCookie(\"sendTime\");\n");
		ret.append("if (sendTime!=null && sendTime!=\"\")\n");
		ret.append("{\n");
		ret.append("  //alert(\"SendTime in seconds = \" + sendTime);\n");
		ret.append("	sendTime = new Date().getTime(); //currentTimeMillis\n");
		ret.append("sendTime = parseInt(sendTime/1000); //convert to seconds\n");
		ret.append("  if (sendTime!=null && sendTime!=\"\")\n");
		ret.append("{\n");
		ret.append("setCookie(\"sendTime\",sendTime,5);\n");
		ret.append("}\n");
		ret.append("}\n");
		ret.append("else\n"); 
		ret.append("{\n");
		ret.append("sendTime = new Date().getTime(); //currentTimeMillis\n");
		ret.append("sendTime = parseInt(sendTime/1000); //convert to seconds\n");
		ret.append("if (sendTime!=null && sendTime!=\"\")\n");
		ret.append("{\n");
		ret.append("setCookie(\"sendTime\",sendTime,5);\n");
		ret.append("}\n");
		ret.append("}\n");
		ret.append("var c_startTime=getCookie(\"startTime\");\n");
		ret.append("if (c_startTime!=null && c_startTime!=\"\")\n");
		ret.append("{\n");
		ret.append("return;\n");
		ret.append("}\n");
		ret.append("else\n"); 
		ret.append("{\n");
		ret.append("if (startTime!=null && startTime!=\"\")\n");
		ret.append("{\n");
		ret.append("	//set duration to length of task in seconds\n");
		ret.append("setCookie(\"startTime\",startTime,25);\n");
		ret.append("}\n");
		ret.append("}\n"); 
		ret.append("}\n");
		ret.append("</script>\n");		
		
		//Animated remaining time for task
		ret.append("<div id=balken><div id=ladung></div></div>\n");
		ret.append("<script type=\"text/javascript\">\n");
		ret.append("function ladebalken(){\n\n");
			ret.append(" var s = new Date();\n");
			ret.append(" s = s.getTime();\n\n");
			//Get elapsed time from cookie
			ret.append(" var difference = 0;\n");
			ret.append("var sendTime = getCookie(\"sendTime\");\n");
			ret.append("var startTime = getCookie(\"startTime\");\n");
			ret.append("if (sendTime !=null && sendTime!=\"\")\n");
			ret.append("{\n");
			ret.append("difference = sendTime - startTime;\n");
			ret.append("}\n");
			ret.append("else\n");
			ret.append("{\n");
			ret.append("difference = 0;\n");
			ret.append("}\n");
			
		//TODO Set var sekunden (maximum time for single task) to value in memento
			ret.append("var sekunden = 25;\n");
		//TODO get expiredTime from cookie
			ret.append("var expiredTime = 0;\n");
			ret.append("var breite =300;\n");
			ret.append("var hoehe = 20;\n");
			ret.append(" var balken = document.getElementById(\"balken\");\n");
			ret.append("var ladung = document.getElementById(\"ladung\");\n");
			ret.append("expiredTime = Math.ceil(breite/sekunden*difference);\n");
			ret.append("balken.style.width = breite+\"px\";\n");
			ret.append("balken.style.border = \"1px solid gray\";\n");
			ret.append(" ladung.style.backgroundColor = \"green\";\n");
			ret.append("ladung.style.height = hoehe +\"px\";\n\n");
			ret.append("ladung.innerHTML = \"<b>Gesamtbearbeitungszeit</b>\";\n");
		  	ret.append("ladung.innerHTML = \"<b>Gesamtbearbeitungszeit</b>\";\n");
			ret.append("prozent = function(){\n");
			ret.append("  e = new Date();\n");
			ret.append("   w = Math.ceil(breite/sekunden*(e.getTime()-s)/1000)+expiredTime;\n\n");
			ret.append("   if (w >= breite){\n");
			ret.append("   clearInterval(sto);\n");
		  	ret.append("  ladung.style.width = breite+\"px\";\n");
		  	ret.append("ladung.style.backgroundColor = \"red\";\n");
		  	ret.append("ladung.innerHTML = \"<b>Gesamtbearbeitungszeit abgelaufen</b>\";\n");
		  	ret.append("document.getElementById('save-and-forward').click(); \n");
		  	ret.append(" }\n");
		  	ret.append("  else {\n");
		  	ret.append("    ladung.style.width = w+\"px\";\n");
		  	ret.append("  }\n");
		  	ret.append("}\n");
		  	ret.append(" sto = setInterval(\"prozent()\",50);\n");
		ret.append("}\n");
		ret.append("ladebalken();\n");
		ret.append("</script>\n");
		ret.append("<br>");
		
		//Play MP3 and provide replays according to Memento counts	
		//Buttons for Play, Pause, Stop, currently only Play is used:	
		ret.append(" <div style=\"text-align:left; visibility:hidden;\" id=\"playercontroller\">\n");
		ret.append("    <button id=\"playerplay\" name=\"playerplay\" type=\"button\"\n");
		ret.append("      value=\"Play\" onclick=\"javascript:count()\">\n");
		ret.append("        <img src=\"/taskmodel-core-view/player_mp3_js/play.png\" width=\"50\" height=\"50\" ><br>\n");
		ret.append("		<b> Starten</b>\n");
		ret.append("    </button>\n");
		ret.append("<!--	   \n");
		ret.append("	<button id=\"playerpause\" name=\"playerpause\" type=\"button\"\n");
		ret.append("      value=\"Play\" onclick=\"javascript:pause()\">\n");
		ret.append("      <p>\n");
		ret.append("        <img src=\"/taskmodel-core-view/player_mp3_js/pause.png\" width=\"50\" height=\"50\" ><br>\n");
		ret.append("      </p>\n");
		ret.append("    </button>\n");
		ret.append("	    <button id=\"playerstop\" name=\"playerstop\" type=\"button\"\n");
		ret.append("      value=\"Play\" onclick=\"javascript:stop()\">\n");
		ret.append("      <p>\n");
		ret.append("        <img src=\"/taskmodel-core-view/player_mp3_js/stop.png\" width=\"50\" height=\"50\" ><br>\n");
		ret.append("      </p>\n");
		ret.append("    </button>\n");
		ret.append("-->\n");
		ret.append("  </div>\n");

		//Script for Flash-Player (by neolao, see https://github.com/neolao/mp3-player)
		ret.append("<script type=\"text/javascript\">\n");
		ret.append("//<![CDATA[\n");
		ret.append("   var myListener = new Object();\n");
		ret.append("myListener.onInit = function()\n");
		ret.append("{\n");
		ret.append("this.position = 0;\n");
		ret.append("};\n");
		ret.append("myListener.onUpdate = function()\n");
		ret.append("{\n");
		ret.append("document.getElementById(\"info_playing\").innerHTML = this.isPlaying;\n");
		ret.append("document.getElementById(\"info_url\").innerHTML = this.url;\n");
		ret.append("document.getElementById(\"info_volume\").innerHTML = this.volume;\n");
		ret.append("document.getElementById(\"info_position\").innerHTML = this.position;\n");
		ret.append("document.getElementById(\"info_duration\").innerHTML = this.duration;\n");
		ret.append("document.getElementById(\"info_bytes\").innerHTML = this.bytesLoaded + \"/\" + this.bytesTotal + \" (\" + this.bytesPercent + \"%)\";\n");
              
		ret.append("                var isPlaying = (this.isPlaying == \"true\");\n");
		ret.append("//document.getElementById(\"playerplay\").style.display = (isPlaying)?\"none\":\"block\";\n");
		ret.append("//document.getElementById(\"playerpause\").style.display = (isPlaying)?\"block\":\"none\";\n");
             
		ret.append("var timelineWidth = 160;\n");
		ret.append("var sliderWidth = 40;\n");
		ret.append("var sliderPositionMin = 40;\n");
		ret.append("var sliderPositionMax = sliderPositionMin + timelineWidth - sliderWidth;\n");
		ret.append("var sliderPosition = sliderPositionMin + Math.round((timelineWidth - sliderWidth) * this.position / this.duration);\n");
              
		ret.append("                if (sliderPosition < sliderPositionMin) {\n");
		ret.append("    sliderPosition = sliderPositionMin;\n");
		ret.append("}\n");
		ret.append("if (sliderPosition > sliderPositionMax) {\n");
		ret.append("sliderPosition = sliderPositionMax;\n");
		ret.append("}\n");
              
		ret.append("                document.getElementById(\"playerslider\").style.left = sliderPosition+\"px\";\n");
		ret.append("};\n");
          
		ret.append("function getFlashObject()\n");
		ret.append("{\n");
		ret.append("return document.getElementById(\"myFlash\");\n");
		ret.append("}\n");
		ret.append("function play()\n");
		ret.append("{\n");
		ret.append("if (myListener.position == 0) {\n");
		//TODO take MP3-name from MEMENTO
		ret.append("getFlashObject().SetVariable(\"method:setUrl\", \"/taskmodel-core-view/player_mp3_js/sample.mp3\");\n");
		ret.append("}\n");
		ret.append("getFlashObject().SetVariable(\"method:play\", \"\");\n");
		ret.append("getFlashObject().SetVariable(\"enabled\", \"true\");\n");
		ret.append("}\n");
		ret.append("function pause()\n");
		ret.append("{\n");
		ret.append("getFlashObject().SetVariable(\"method:pause\", \"\");\n");
		ret.append("}\n");
		ret.append("function stop()\n");
		ret.append("{\n");
		ret.append("getFlashObject().SetVariable(\"method:stop\", \"\");\n");
		ret.append("}\n");
		ret.append("function setPosition()\n");
		ret.append("{\n");
		ret.append("var position = document.getElementById(\"inputPosition\").value;\n");
		ret.append("getFlashObject().SetVariable(\"method:setPosition\", position);\n");
		ret.append("}\n");
		ret.append("function setVolume()\n");
		ret.append("{\n");
		ret.append("var volume = document.getElementById(\"inputVolume\").value;\n");
		ret.append("getFlashObject().SetVariable(\"method:setVolume\", volume);\n");
		ret.append("}\n");
		ret.append("//]]>\n");
		ret.append("</script>\n");
		ret.append("<object class=\"speechTestTaskPlayer\" id=\"myFlash\" type=\"application/x-shockwave-flash\" data=\"/taskmodel-core-view/player_mp3_js/player_mp3_js.swf\" height=\"1\" width=\"1\">\n");
		ret.append("<param name=\"movie\" value=\"/taskmodel-core-view/player_mp3_js/player_mp3_js.swf\">\n");
		ret.append("<param name=\"AllowScriptAccess\" value=\"always\">\n");
		ret.append("<param name=\"FlashVars\" value=\"listener=myListener&amp;interval=500\">\n");
		ret.append("</object>       \n");
		ret.append("<!--	\n");
		ret.append("<h2>Information</h2>\n");
		ret.append("<ul>\n");
		ret.append("<li>url : <span id=\"info_url\">undefined</span></li>\n");
		ret.append("<li>chargement : <span id=\"info_bytes\">undefined</span></li>\n");
		ret.append("<li>isPlaying : <span id=\"info_playing\">undefined</span></li>  \n");  
		ret.append("<li>position : <span id=\"info_position\">undefined</span></li>\n");
		ret.append("<li>duration : <span id=\"info_duration\">undefined</span></li>\n");
		ret.append("<li>volume : <span id=\"info_volume\">undefined</span></li>\n");
		ret.append("</ul>\n");
		ret.append("-->\n");
		
		//counter to controll plays and replays
		ret.append("<script>\n");
		//TODO Set time, when mp3 is to be played according to MEMENTO
		ret.append("// get from cookie\n");
		ret.append("var expiredTimeToFlashplay = 0;\n");
		ret.append("var sendTime_cookie = getCookie(\"sendTime\");\n");
		ret.append("var startTime_cookie = getCookie(\"startTime\");\n");
		ret.append("//get from memento, default is 15s\n");
		ret.append("var timeToPlayFlash = 5000; //in ms\n");
		ret.append("if (sendTime_cookie !=null && sendTime_cookie!=\"\")\n");
		ret.append("{\n");
		ret.append("expiredTimeToFlashplay = (sendTime_cookie - startTime_cookie)*1000;\n");
		ret.append("timeToPlayFlash -= expiredTimeToFlashplay;\n");
		ret.append("if (timeToPlayFlash > 0) { //time to play flash not yet reached\n");
		ret.append("					window.setTimeout(\"count()\", timeToPlayFlash); // Aus MEMENTO\n");
		ret.append("}\n");
		ret.append("if (timeToPlayFlash < 0 && plays < counter) { //time to play flash not yet reached\n");
		ret.append("					document.getElementById('playerplay').style.visibility='visible';\n");
		ret.append("					document.getElementById('playerplay').innerHTML='<img src=\"/taskmodel-core-view/player_mp3_js/play.png\" width=\"50\" height=\"50\" ><br><b> ' + (counter-plays) + ' x</b>';\n");
		ret.append("				}\n");
		ret.append("			}\n");
		ret.append("else\n");
		ret.append("{\n");
		ret.append("window.setTimeout(\"count()\", timeToPlayFlash); // Aus MEMENTO\n");
		ret.append("}\n");
		//TODO Set counter according to Memento (number of replays)
		ret.append("var counter = 3; //Aus MEMENTO, default:1\n");
		ret.append("var plays = 0;\n");
		ret.append("function count() {\n");
		ret.append("if (plays < counter) {\n");
		ret.append("plays +=1;\n");
		ret.append("document.getElementById('playerplay').innerHTML='<img src=\"/taskmodel-core-view/player_mp3_js/play.png\" width=\"50\" height=\"50\" ><br><b> ' + (counter-plays) + ' x</b>'\n");
		ret.append("play();\n");
		ret.append("}\n");
		ret.append("if (counter > 1) {\n");
		ret.append("document.getElementById('playerplay').style.visibility='visible';\n");
		ret.append("}\n");	
		ret.append("}\n");
		ret.append("</script>\n");	
	
		//Room for notes - won't be saved
		ret.append("<div style='	width: 120px; 	bottom:400px; 	left:5px; 	height: 350px; 	margin: 0px 0px 0px 0px; 	position: fixed; 	border: solid 0px #ffffff;'>               <textarea style=\"	width: 200px; 	height: 120px; 	border: 3px solid #cccccc; 	padding: 15px; 	font-family: Tahoma, sans-serif; 	background-repeat: no-repeat;\" name=\"textbox\" id=\"textbox\" onfocus=\"document.getElementById('textbox').style.background='#fbe3c6';\"  	onblur=\"document.getElementById('textbox').style.background='white'\">Notizen...</textarea> </div>\n");
		
		//Disable Navigation for the pages
		ret.append("<script>\n");
		ret.append("var navigation = document.getElementById('navigation');\n");
		ret.append("a = navigation.getElementsByTagName('a');\n");
		ret.append("for( var x=0; x < a.length; x++ ) {\n");
		ret.append("a[x].onclick = function(){\n");
		ret.append("alert('Freie Navigation ist nicht erlaubt.');\n");
		ret.append("return false;\n");
		ret.append("};\n");
		ret.append("}\n");
		ret.append("</script>\n");
		
		//Disable Navigation to the mainpage
		ret.append("<script>\n");
		ret.append("backToMainpage = document.getElementById('backToMainpage');\n");
		ret.append("a = backToMainpage.getElementsByTagName('a');\n");
		ret.append("for( var x=0; x < a.length; x++ ) {\n");
		ret.append("a[x].onclick = function(){\n");
		ret.append("alert('Bitte die Klausur abgeben, um wieder zur Hauptseite zu gelangen.');\n");
		ret.append("return false;\n");
		ret.append("};\n");
		ret.append("}\n");
		ret.append("</script>\n");	
		
		String solution = speechTestSubTasklet.getHandlingSolution();
		Boolean[] b = null;
		if (solution != null){
			String[] sp = solution.split("//");        
			b = new Boolean[sp.length];
			for(int i=0; i<b.length; i++){
				if ( sp[i].split("-")[0].equals("true") )
					b[i] = true;            
				else b[i] = false;
			}		
		}
		//if (!corrected){
			// 	Setze Teilfragen in div-Bereich zum scrollen
			//ret.append("\n<div style=\" height:220px; width:520px; font-size:12px; overflow-x:hidden; overflow-y:visible;\">");
		//}
		// mcList->questions		
		for(int i=0; i<speechTestSubTasklet.getCountQuestions(); i++){						
			ret.append(setQuestions(i, relativeTaskNumber, b, corrected));							
		}	
		// ende div		
		//if (!corrected)
			//ret.append("\n</div>");
		
		if (!corrected) {
			ret.append("<script type=\"text/javascript\">\n");
			ret.append(" var preSave_task_"+relativeTaskNumber+" = function(){\n");
			ret.append(
		    "var sol = \"\";\n"+
		    "var relativeTaskNumber = \""+relativeTaskNumber+"\";\n"+	
			"for(var i = 0; i<"+speechTestSubTasklet.getCountQuestions()+"; i++){\n"+			
			"var clear = \"\";\n"+
	        "  for(var k = 0; k<document.getElementsByName(\"[\"+i+\"][\"+relativeTaskNumber+\"]\").length; k++){\n"+	        
	        "    if(document.getElementsByName(\"[\"+i+\"][\"+relativeTaskNumber+\"]\")[k].checked && document.getElementsByName(\"[\"+i+\"][\"+relativeTaskNumber+\"]\")[k].id == relativeTaskNumber ){\n"+	        
	        //"        clear += document.getElementsByName(\"[\"+i+\"][\"+relativeTaskNumber+\"]\")[k].value+\"--\"+i+\"//\" ;\n"+
	        "          clear += \"true-\"+i+\"//\";\n"+
	        "        document.getElementsByName(\"[\"+i+\"][\"+relativeTaskNumber+\"]\")[k].checked = false;\n"+
	        "    }\n"+   
	        "    else{\n"+
	        "          clear += \"false-\"+i+\"//\";\n"+
	        "    }"+
	        "  }\n"+
	        "if (clear == \"\")\n"+
	        "  clear = \"null--\"+i+\"//\"\n"+
	        "sol += clear;\n"+
	        "}\n"	
		    //"alert(\"Text: \"+sol)\n"
		    );
			ret.append("document.getElementById(\"task_"+relativeTaskNumber+".result\").value = sol;\n");		
			ret.append("};\n");
			ret.append("preSaveManager.registerCallback( preSave_task_"+relativeTaskNumber+" );\n");
			ret.append("leavePageManager.registerCallback( leavePage_task_"+relativeTaskNumber+" );\n");
			ret.append("</script>\n");
		}
		return ret.toString();
	}
	
	private String setQuestions(int num, int relativ, Boolean[] param, boolean disabled){				
		StringBuffer ret = new StringBuffer();
		// problem			
		ret.append("<hr>\n");
		ret.append("<table width=\"100%\" border=\"0\">");
		ret.append("\n<tr>\n  <td valign=top><b>"+speechTestSubTasklet.getQuestionProblem(num)+"</b></td>");
		ret.append("\n"); 
		ret.append("</tr>");			
		// hint
		ret.append("<tr>\n  <td nowrap valign=top><i>"+speechTestSubTasklet.getQuestionHint(num)+"</i></td>");
		ret.append("\n"); ret.append("</tr>");
		// answers						
		int pos = 0;
		for(int k=0; k<num; k++){
			pos+=speechTestSubTasklet.getQuestionsAnswers(k).size();
		}
		for(String n:speechTestSubTasklet.getQuestionsAnswers(num)){
			ret.append("<tr>");				
			String sel = "";
			String symbolPic = "";
			if (param != null && param[pos]){
				sel = "checked";				
			}					
			if (disabled){
				sel += " disabled";
				if (param!= null){
					if (param[pos]){
						symbolPic = getSymbolForCorrectedAnswer(pos,true);
					}
					else 
						symbolPic = getSymbolForCorrectedAnswer(pos,false);
				}
				else symbolPic = getSymbolForCorrectedAnswer(pos,false);
			}			
			
			ret.append("\n  <td nowrap valign=top>"+
					   " <input type=\"radio\" name=\"["+num+"]["+relativ+"]\"" +					
					   " id=\""+relativ+"\" value=\""+n+"\" "+sel+" onChange=\"setModified()\" >"+n+
					   symbolPic+
					   "</td>");
			ret.append("\n");
			ret.append("</tr>");	
			pos++;
		}
		
		ret.append("\n");
		ret.append("</table>");

		return ret.toString();
	}
	
	private String getSymbolForCorrectedAnswer(int answersNumber, boolean answer){		
		if (request == null) return "";
		
		List<Boolean> boolList = speechTestSubTasklet.getAnswerList();	
		if(answer && boolList.get(answersNumber)){			
			return "<img src=\"" + request.getContextPath() + "/pics/true.gif\">";			
		}
		else if (!answer && boolList.get(answersNumber)){			
			return "<img src=\"" + request.getContextPath() + "/pics/false.gif\">";
		}
		
		return "";
	}

	public String getCorrectedHTML( ViewContext context, int relativeTaskNumber ){
		// for pdf...
		request=(HttpServletRequest) context.getViewContextObject();
		StringBuffer ret = new StringBuffer();		
		ret.append(getRenderedHTML( -1, true ));
		return ret.toString();
		
	}

	public String getCorrectionHTML(String actualCorrector, ViewContext context ){	
		request=(HttpServletRequest) context.getViewContextObject();
	    StringBuffer ret = new StringBuffer();
	    ret.append( getRenderedHTML( -1, true ) );	    	   

	    ret.append(getCorrectorPointsInputString(actualCorrector, "SpeechTest", speechTestSubTasklet));

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
			return new SpeechTestTaskSubmitData( value );
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
            return new SpeechTestTaskCorrectionSubmitData( points );
	    }else
	        throw new ParsingException();
	}	

}
