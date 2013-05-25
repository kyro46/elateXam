/*

Copyright (C) 2006 Thorsten Berger

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
/**
 *
 */
package de.thorstenberger.taskmodel.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.thorstenberger.taskmodel.ManualCorrection;
import de.thorstenberger.taskmodel.MethodNotSupportedException;
import de.thorstenberger.taskmodel.ReportBuilder;
import de.thorstenberger.taskmodel.TaskApiException;
import de.thorstenberger.taskmodel.TaskDef;
import de.thorstenberger.taskmodel.TaskManager;
import de.thorstenberger.taskmodel.TaskManager.UserAttribute;
import de.thorstenberger.taskmodel.Tasklet;
import de.thorstenberger.taskmodel.UserInfo;
import de.thorstenberger.taskmodel.complex.ComplexTasklet;
import de.thorstenberger.taskmodel.complex.TaskDef_Complex;
import de.thorstenberger.taskmodel.complex.complextaskdef.Block;
import de.thorstenberger.taskmodel.complex.complextaskdef.Category;
import de.thorstenberger.taskmodel.complex.complextaskdef.Choice;
import de.thorstenberger.taskmodel.complex.complextaskdef.SubTaskDef;
import de.thorstenberger.taskmodel.complex.complextaskdef.SubTaskDefOrChoice;
import de.thorstenberger.taskmodel.complex.complextaskhandling.Page;
import de.thorstenberger.taskmodel.complex.complextaskhandling.SubTasklet;
import de.thorstenberger.taskmodel.complex.complextaskhandling.Try;
import de.thorstenberger.taskmodel.complex.complextaskhandling.subtasklets.SubTasklet_MC;

/**
 * @author Thorsten Berger
 *
 */
public class ReportBuilderImpl implements ReportBuilder {

	class Match{
		public String task;		
		public boolean auto = false;
		public Set<String> kor = new TreeSet<String>();
		public String cat = "";
	}
	
	private TaskManager taskManager;

	Log log = LogFactory.getLog( ReportBuilderImpl.class );

	private static DateFormat df = DateFormat.getDateTimeInstance( );
		
	//private Set<TreeSet<String>> tasks = new TreeSet<TreeSet<String>>();
	private Set<Match> taskKorrektorMatches = new HashSet<Match>();			
	
	private void patchTasksToCats(String cat, long taskId){
		// gehe alle ordner durch
		// exsistiert complextask_ID ?
		// finde in jeder die cat
		// von jeder cat die namen der aufgaben in liste speichern				
		String home = System.getProperty("user.home") + 
				"/ExamServerRepository_examServer-leipzig" +
				"/home";
		// TODO change path!!!
		File file = new File(home);
		File[] fileArray = file.listFiles();
		if (fileArray == null){
			return;
		}
		TreeSet<String> tasks = new TreeSet<String>(); 
		for(int i=0; i<fileArray.length; i++){
			if(fileArray[i].isDirectory()){
				leseDaten(fileArray[i].getAbsolutePath(), taskId, cat);
			}
		}	
		return;
	}
	
	private void leseDaten(String pfad, long taskId, String cat){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;	    
		try {
			builder = factory.newDocumentBuilder();			
			File file = new File(pfad+"/complextask_"+taskId+".xml");
			if (!file.exists()){
				return;
			}
			Document document = builder.parse( file );
			Element handling = (Element)document.getElementsByTagName("complexTaskHandling").item(0);									
			Element tryE = (Element)handling.getElementsByTagName("try").item(0);
			NodeList pages = tryE.getElementsByTagName("page");			
			for(int i=0; i<pages.getLength(); i++){
				Element el = (Element)pages.item(i);				
				if(!el.getAttribute("categoryRefID").equals(cat)){
					continue;
				}								
				NodeList list = el.getChildNodes();
				for(int k=0; k<list.getLength(); k++){					
					Node taskName = list.item(k);
					NamedNodeMap map = taskName.getAttributes();
					Node node = null;
					if (map != null)
						node = map.getNamedItem("refId");
					if (node != null){												
						// Korrekteure auslesen und zur jeweiligen aufgabe speichern
						NodeList korList = ((Element)taskName).getElementsByTagName("manualCorrection");
						Match match = new Match();
						match.task = node.getNodeValue();
						for(Match ma:taskKorrektorMatches){
							if (ma.task.equals(match.task) && ma.cat.equals(cat)){
								// wenn kategorie schon gesetzt und nicht übereinstimmt,
								// so ist es ein anderer task!
								match = ma;
								break;
							}
						}							
						if (korList == null || korList.getLength()==0){
							match.auto = true;
						}
						for(int name=0; name < korList.getLength(); name++){
							Node kor = korList.item(name);							
							if (kor == null)
								continue;							
							match.kor.add(kor.getAttributes().getNamedItem("corrector").getNodeValue());							
						}			
						/*
						 * speichere Kategorie, um spaeter richtig zu ordnen zu koennen 
						 */
						match.cat=cat;
						taskKorrektorMatches.add(match);						
					}
				}								
			}			
		} catch (Exception e) {			
			e.printStackTrace();
		} 
	}

	/**
	 * @param taskletContainer
	 */
	public ReportBuilderImpl( TaskManager taskManager ) {
		this.taskManager = taskManager;
	}

	/* (non-Javadoc)
	 * @see de.thorstenberger.taskmodel.ReportBuilder#createExcelBinary(long, java.io.OutputStream)
	 */	
	public void createExcelBinary(long taskId, OutputStream out) throws TaskApiException, MethodNotSupportedException{
		taskKorrektorMatches.clear();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		TaskDef taskDef = taskManager.getTaskDef( taskId );

		short r = 0;
		short c = 0;

		List<Tasklet> tasklets = taskManager.getTaskletContainer().getTasklets( taskId );

		///////////////////
		// create header
		HSSFRow row = sheet.createRow( r++ );
		row.createCell( c++ ).setCellValue( "Login" );
		row.createCell( c++ ).setCellValue( "Vorname" );
		row.createCell( c++ ).setCellValue( "Name" );
		row.createCell( c++ ).setCellValue( "EMail" );

		List<UserAttribute> uas = taskManager.availableUserAttributes();
		for( UserAttribute ua : uas ) {
			row.createCell( c++ ).setCellValue( ua.getName( null ) );
		}
		row.createCell( c++ ).setCellValue( "Status" );

		// add header cols for automatic and manual corrections
		row.createCell( c++ ).setCellValue( "autom. Korrektur" );
		// determine number of correctors
		int maxManualCorrectors = 0;
		for( Tasklet tasklet : tasklets ){
			List<ManualCorrection> mcs = tasklet.getTaskletCorrection().getManualCorrections();
			if( mcs != null && mcs.size() > maxManualCorrectors ) {
				maxManualCorrectors = mcs.size();
			}
		}

		// ok, so add header cols for every corrector, as determined above
		for( int i = 1; i<=maxManualCorrectors; i++ ) {
			row.createCell( c++ ).setCellValue( "Punkte " + i + ". Korrektor" );
		}
		for( int i = 1; i<=maxManualCorrectors; i++ ) {
			row.createCell( c++ ).setCellValue( "Name " + i + ". Korrektor" );
		}

		row.createCell( c++ ).setCellValue( "zugeordneter Korrektor" );
		row.createCell( c++ ).setCellValue( "Zuordnungs-History" );

		// add some more columns, if complex task; e.g. points per category
		if( taskDef instanceof TaskDef_Complex ){
			
			row.createCell( c++ ).setCellValue( "Startzeit" );

			TaskDef_Complex ctd = (TaskDef_Complex) taskDef;
			List<Category> categories = ctd.getComplexTaskDefRoot().getCategoriesList();
						
			for( Category category : categories ) {				
				row.createCell( c++ ).setCellValue( category.getTitle() + " (" + category.getId() + ")" );
				// hier Task zu den Kat´s zuordnen
				patchTasksToCats(category.getId(), taskId);
				// erstelle alle spalten zur aufgabe mit allen ihrer korrektoren
				//  hier Kopfzeilen schreiben
				for(Match match:taskKorrektorMatches){	
					if (!match.cat.equals(category.getId())){
						// wenn kategorie nicht passt, naechsten match testen
						continue;
						
					}
					int posChar = match.task.lastIndexOf("_");					
					String taskCell = match.task.substring(0, posChar);					
					if (match.kor == null || match.kor.isEmpty()){
						row.createCell( c++ ).setCellValue( "auto : "+ taskCell );
					} else
					for(String kor:match.kor){
						row.createCell( c++ ).setCellValue( kor +" : "+ taskCell );
					}
					// nur, wenn auto und maual auftreten
					if (match.auto && match.kor!=null && !match.kor.isEmpty()){
						row.createCell( c++ ).setCellValue( "auto" +" : "+ taskCell );
					}
				}
			}

		}		

		// show tasklet flags
		row.createCell( c++ ).setCellValue( "Flags" );
		
		// end create header
		//////////////////////


		for( Tasklet tasklet : tasklets ){

			if( tasklet.getStatus() == Tasklet.Status.INITIALIZED ) {
				continue;
			}

			row = sheet.createRow( r++ );
			c = 0;

			c = createUserInfoColumns( tasklet, c, wb, row );
			row.createCell( c++ ).setCellValue( tasklet.getStatus().toString() );

			// auto correction points
			row.createCell( c++ ).setCellValue( tasklet.getTaskletCorrection().getAutoCorrectionPoints() != null ? "" + tasklet.getTaskletCorrection().getAutoCorrectionPoints() : "-" );
			List<ManualCorrection> mcs = tasklet.getTaskletCorrection().getManualCorrections();
			for( int i = 0; i < maxManualCorrectors; i++ ){
				if( mcs != null && mcs.size() > i ) {
					row.createCell( c++ ).setCellValue( mcs.get( i ).getPoints() );
				} else {
					row.createCell( c++ ).setCellValue( "-" );
				}
			}
			for( int i = 0; i < maxManualCorrectors; i++ ){
				if( mcs != null && mcs.size() > i ) {
					row.createCell( c++ ).setCellValue( mcs.get( i ).getCorrector() );
				} else {
					row.createCell( c++ ).setCellValue( "-" );
				}
			}

			row.createCell( c++ ).setCellValue( tasklet.getTaskletCorrection().getCorrector() != null ? tasklet.getTaskletCorrection().getCorrector() : "-" );
			row.createCell( c++ ).setCellValue( tasklet.getTaskletCorrection().getCorrectorHistory().toString() );

			// add the additional cols for complex tasks
			if( taskDef instanceof TaskDef_Complex ){

				TaskDef_Complex ctd = (TaskDef_Complex) taskDef;
				//				if( ctd.getComplexTaskDefRoot().getCorrectionMode().getType() == ComplexTaskDefRoot.CorrectionModeType.MULTIPLECORRECTORS )
				//					throw new IllegalStateException( "MultiCorrectorMode not supported yet. Stay tuned!" );

				ComplexTasklet ct = (ComplexTasklet)tasklet;
				row.createCell( c++ ).setCellValue( getStringFromMillis( ct.getSolutionOfLatestTry().getStartTime() ) );

				List<Category> categories = ctd.getComplexTaskDefRoot().getCategoriesList();

				// points per category
				Map<String, Float> pointsInCatMap = new Hashtable<String, Float>();

				// init every category with 0 points
				for( Category category : categories ) {
					pointsInCatMap.put( category.getId(), 0f );
				}

				Try studentsLastTry;

				try {
					studentsLastTry = ct.getSolutionOfLatestTry();
				} catch (IllegalStateException e) {
					log.error( "Error generating excel stream: ", e );
					throw new TaskApiException( e );
				}

				List<Page> pages = studentsLastTry.getPages();

				for( Page page : pages ){
					Float points = pointsInCatMap.get( page.getCategoryRefId() );

					// continue if marked as uncorrected earlier
					if( points == null ) {
						continue;
					}

					boolean markCategoryAsUncorrected = false;

					List<SubTasklet> subTasklets = page.getSubTasklets();
					for( SubTasklet subTasklet : subTasklets ){
						if( subTasklet.isCorrected() ){
							points += subTasklet.isAutoCorrected() ? subTasklet.getAutoCorrection().getPoints() : subTasklet.getManualCorrections().get( 0 ).getPoints();
						}else{
							markCategoryAsUncorrected = true;
							break;
						}
					}

					if( markCategoryAsUncorrected ) {
						pointsInCatMap.remove( page.getCategoryRefId() );
					} else {
						pointsInCatMap.put( page.getCategoryRefId(), points );
					}

				}
				int anzInTask = 0;
				for( Category category : categories ){
					Float points = pointsInCatMap.get( category.getId() );
					row.createCell( c++ ).setCellValue( points != null ? "" + points : "-" );
					/* cat lesen
					 * liste mit aufgaben in cat lesen
					 * login vom stud holen // nur ein student, da obersten for-schleifezu beachten
					 * genau diese aufgaben in dieser cat durchgehen
					 * punkte für diese aufgabe lesen
					 * */
					UserInfo userInfo = taskManager.getUserInfo( tasklet.getUserId() );
					String login = null;
					if( userInfo != null ){
						login = userInfo.getLogin();
					}else{
						login = tasklet.getUserId();
					}	
					// lies punkte aus xml
					ArrayList<String> punkte = getPointsFromXml(category.getId(), login, taskId, anzInTask);
					if (punkte !=null)
					for(String text:punkte){
						row.createCell( c++ ).setCellValue( text );
					}										
				}

			}

			row.createCell( c++ ).setCellValue( tasklet.getFlags().toString() );

		}

		try {
			wb.write( out );
			out.flush();
		} catch (IOException e) {
			log.error( "Error writing excel stream!", e );
			throw new TaskApiException( e );
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				throw new TaskApiException( e );
			}
		}

	}
	
	private ArrayList<String> getPointsFromXml(String cat, String name, long taskId, int pos){
		String home = System.getProperty("user.home") + 
				"/ExamServerRepository_examServer-leipzig" +
				"/home/"+name+"/complextask_"+taskId+".xml";
		// TODO change path!!!
		File file = new File(home);
		if (!file.exists()){
			return null;
		}		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;	    
		try {
			builder = factory.newDocumentBuilder();						
			Document document = builder.parse( file );
			Element handling = (Element)document.getElementsByTagName("complexTaskHandling").item(0);									
			Element tryE = (Element)handling.getElementsByTagName("try").item(0);
			NodeList pages = tryE.getElementsByTagName("page");			
			
			// gehe alle aufgaben aus taskKorrektorMatches durch
			// suche task in pages
			// -> task nicht gefunden, setze alles null			
			// sonst gehe alle kor durch
			// -> gibt es kor nicht, dann setze string = " "
			// -> sonst speichere seine punkte ab	
			ArrayList<String> points = new ArrayList<String>();
			for(Match match:taskKorrektorMatches){
				if (!match.cat.equals(cat)){
					continue;
				}
				Element task = null;
				boolean br = false;
				for(int pa=0; pa<pages.getLength() && !br; pa++){					
					Element page = (Element)pages.item(pa);
					NodeList tasks = page.getElementsByTagName("*");										
					for(int tas=0; tas<tasks.getLength() && !br; tas++){
						Element ta = (Element)tasks.item(tas);						
						if(ta.getAttribute("refId").equals(match.task)){
							task = ta;
							br = true;							
						}
					}
				}				
				if (task == null || br==false){				
					// fülle alles mit leerstring aus
					for(String kor:match.kor){
						points.add(" ");
					}
					if (match.auto){
						points.add(" ");
					}					
				}else{
					if (match.kor == null || match.kor.isEmpty()){
						Element point = (Element)task.getElementsByTagName("autoCorrection").item(0);
						points.add(point.getAttribute("points"));
					}else{
						int fail = 0;
						for(String kor:match.kor){
							NodeList kors = task.getElementsByTagName("manualCorrection");
							if (kors == null){
								// automatische
								Element point = (Element)task.getElementsByTagName("autoCorrection").item(0);
								points.add(point.getAttribute("points"));
							}else{
								Element per = null;
								for(int k=0; k<kors.getLength(); k++){
									Element person = (Element)kors.item(k);
									if(person.getAttribute("corrector").equals(kor)){
										// name gefunden
										per = person;									
										break;
									}
								}
								if (per == null){
									points.add(" ");
									fail++;
								}else{
									points.add(per.getAttribute("points"));
								}
							}						
						}
						if (fail == match.kor.size()){
							Element point = (Element)task.getElementsByTagName("autoCorrection").item(0);
							points.add(point.getAttribute("points"));
						}
						else if (match.auto){
							points.add(" ");
						}
					}					
				}
			}
								
			return points;
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return null;
	}

	private short createUserInfoColumns( Tasklet tasklet, short c, HSSFWorkbook wb, HSSFRow row ){
		UserInfo userInfo = taskManager.getUserInfo( tasklet.getUserId() );
		List<UserAttribute> uas = taskManager.availableUserAttributes();

		String login;
		String firstName;
		String name;
		String email;
		List<String> userAttributeValues = new LinkedList<String>();;
		boolean notfound;

		if( userInfo != null ){
			login = userInfo.getLogin();
			firstName = userInfo.getFirstName();
			name = userInfo.getName();
			email = userInfo.getEMail();
			for( UserAttribute ua : uas ){
				String s = userInfo.getUserAttributeValue( ua.getKey() );
				if( s == null || s.trim().length() == 0 ) {
					s = "-";
				}
				userAttributeValues.add( s );

			}
			notfound = false;
		}else{
			login = tasklet.getUserId();
			firstName = "?";
			name = "?";
			email = "?";
			for( UserAttribute ua : uas ) {
				userAttributeValues.add( "?" );
			}
			notfound = true;
		}


		if( notfound ){
			HSSFCellStyle cs2 = wb.createCellStyle();
			HSSFFont font2 = wb.createFont(); font2.setColor( HSSFColor.RED.index );
			cs2.setFont( font2 );
			HSSFCell cell2 = row.createCell( c++ );
			cell2.setCellStyle( cs2 );
			cell2.setCellValue( login );
		}else{
			row.createCell( c++ ).setCellValue( login );
		}

		row.createCell( c++ ).setCellValue( firstName );
		row.createCell( c++ ).setCellValue( name );
		row.createCell( c++ ).setCellValue( email );
		for( String uav : userAttributeValues ) {
			row.createCell( c++ ).setCellValue( uav );
		}		
		return c;
	}

	/* (non-Javadoc)
	 * @see de.thorstenberger.taskmodel.ReportBuilder#createExcelAnalysisForBlock(de.thorstenberger.taskmodel.complex.complextaskdef.Block, java.io.OutputStream)
	 */
	public void createExcelAnalysisForBlock(long taskId, String categoryId, int blockIndex, OutputStream out) throws TaskApiException, MethodNotSupportedException {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		TaskDef_Complex taskDef;
		try {
			taskDef = (TaskDef_Complex)taskManager.getTaskDef( taskId );
		} catch (ClassCastException e) {
			throw new MethodNotSupportedException( "Only analysis of complexTasks supported (for now)!" );
		}
		Category category = taskDef.getComplexTaskDefRoot().getCategories().get( categoryId );
		Block block = category.getBlock( blockIndex );

		short r = 0;
		short c = 0;

		HSSFRow row = sheet.createRow( r++ );
		row.createCell( c++ ).setCellValue( "Login" );
		row.createCell( c++ ).setCellValue( "Vorname" );
		row.createCell( c++ ).setCellValue( "Name" );

		if( !block.getType().equals( "mc" ) )
			throw new MethodNotSupportedException( "Only MC analysis supported (for now)!" );

		List<SubTaskDefOrChoice> stdocs = block.getSubTaskDefOrChoiceList();
		List<SubTaskDef> stds = new ArrayList<SubTaskDef>();
		// put all SubTaskDefs into stds
		for( SubTaskDefOrChoice stdoc : stdocs ){
			if( stdoc instanceof SubTaskDef ){
				stds.add( (SubTaskDef)stdoc );
				row.createCell( c++ ).setCellValue( ((SubTaskDef)stdoc).getId() );
			}else{
				Choice choice = (Choice)stdoc;
				for( SubTaskDef std : choice.getSubTaskDefs() ){
					stds.add( std );
					row.createCell( c++ ).setCellValue( std.getId() );
				}
			}
		}


		List<Tasklet> tasklets = taskManager.getTaskletContainer().getTasklets( taskId );

		for( Tasklet tasklet : tasklets ){

			if( tasklet.getStatus() == Tasklet.Status.INITIALIZED ) {
				continue;
			}

			if( !(tasklet instanceof ComplexTasklet ) )
				throw new MethodNotSupportedException( "Only analysis of complexTasklets supported (for now)!" );

			row = sheet.createRow( r++ );
			c = 0;

			ComplexTasklet ct = (ComplexTasklet)tasklet;
			Try actualTry = ct.getSolutionOfLatestTry();

			c = createUserInfoColumns( tasklet, c, wb, row );

			for( SubTaskDef std : stds ){

				SubTasklet_MC mcst = (SubTasklet_MC)actualTry.lookupSubTasklet( std );
				if( mcst == null ){
					row.createCell( c++ ).setCellValue( "[n/a]" );
					continue;
				}

				StringBuilder sb = new StringBuilder();
				List<SubTasklet_MC.Answer> answers = mcst.getAnswers();
				boolean first = true;
				for( SubTasklet_MC.Answer answer : answers ){
					if( answer.isSelected() ){
						if( !first ) {
							sb.append( ";" );
						}
						sb.append( answer.getId() );
						first = false;
					}
				}
				row.createCell( c++ ).setCellValue( sb.toString() );

			}

		}

		try {
			wb.write( out );
			out.flush();
		} catch (IOException e) {
			log.error( "Error writing excel stream!", e );
			throw new TaskApiException( e );
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				throw new TaskApiException( e );
			}
		}

	}

	private static String getStringFromMillis( long timestamp ){
		return df.format( new Date( timestamp ) );
	}

}

