/**
 * Programm zur Konvertierung von aus Moodle exportierten Übungsfragen (Moodle-XML)
 * in Elate ComplexTaskDef-XML.
 *
 * @author Christoph Jobst
 * @version 1.0
 *
 */
package de.christophjobst.main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import generated.*;

public class Base64Relocator {

	public static String relocateBase64(String inputText, List<generated.File> fileList) {

		String text = "";
		String problem_string =  inputText;

//		List<File> fileList = new ArrayList<File>();
//		fileList = input.getFile();
		// fileList.get(0).getValue(); //Base64-String
		// fileList.get(0).getEncoding(); //base64
		// fileList.get(0).getName(); //Dateiname, der im Text zu ersetzen ist
		
		if (!problem_string.equals("")) {
			try {
				for (int i = 0; i < fileList.toArray().length; i++) {
					problem_string = problem_string.replaceAll("@@PLUGINFILE@@/" + fileList.get(i).getName().replaceAll(" ", "%20"), "data:image/gif;base64," + fileList.get(i).getValue());					
				}
				text = problem_string;

			} catch (Exception e) {
				e.printStackTrace();

				text = problem_string;
			}
		} else {
			// Keine Aufgabenstellung angegeben
			text = " ";
		}
		
		
		// Konvertierung des String in separaten Block,
		// falls bei häufiger Nutzung Auslagerung nötig
		try {
				String orig = text;
				// Workaround: Go through all Images containing Base64 Strings and write them
				// somewhere to the disk, putting links to those files afterwards.
				// TODO: handle deletion of image files (e.g. when the corresponding exam is deleted)
				// TODO name files corresponding to examname
				// TODO save files in the taskfile-dir in user.home-dir
				// TODO do this for the memento too
				// TODO use relative path without webapp-name, same at the code for special images generated in applettasks (time, comparetext, grouping)

				String ret = "";
				int lastPos = 0;
				//String fspath = "/opt/apache-tomcat-7.0.29/webapps/taskmodel-core-view"; // FIXME: Get rid of this
				String fspath = System.getProperty("user.dir").replaceAll("bin", "webapps/taskmodel-core-view/ExamImages");
				new File(fspath).mkdir();
				SecureRandom random = new SecureRandom();
				Pattern pattern = Pattern.compile("data:image/([a-z]+);base64,([^\"]+)");
				Matcher match = pattern.matcher(orig);
				while(match.find()) try {
					String type = match.group(1), base64 = match.group(2);
					byte[] img = DatatypeConverter.parseBase64Binary(base64);
					final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(img));
					String randomname = "/frombase64_" + new BigInteger(130, random).toString(32) + "." + type;
					ImageIO.write(bufferedImage, type, new File(fspath + randomname)); // write to file on filesystem
					ret += orig.substring(lastPos, match.start()) + "/taskmodel-core-view/ExamImages" + randomname; // have a link to that file accessible via web
					lastPos = match.end();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ret += orig.substring(lastPos);
				text = ret;
			
		} catch (Exception e) {
		}
	
		return text;
		
		// System.out.println(root.getChildNodes().item(0).getNodeName());//img
		// System.out.println(root.getChildNodes().item(0).getAttributes().item(0).getNodeValue());//alt
		// System.out.println(root.getChildNodes().item(0).getAttributes().item(1).getNodeValue());//height
		// System.out.println(root.getChildNodes().item(0).getAttributes().item(2).getNodeValue());//src
		// System.out.println(root.getChildNodes().item(0).getAttributes().item(3).getNodeValue());//width
		// System.out.println(root.getChildNodes().item(0).getAttributes().item(2).getNodeValue().substring(15));

		// root.getChildNodes().item(0).getAttributes().item(2).setNodeValue("BASE64STRING");
		// root.getChildNodes().item(0).getAttributes().item(2).setNodeValue("data:image/gif;base64,"
		// + questiontext.getFile().get(0).getValue());

		// System.out.println(text);

		// Start: <img src="@@PLUGINFILE@@/name.jpg" alt="12345678" width="776"
		// height="436" />
		// Ziel: <img src="data:image/gif;base64,_x_x_x_x_x_" alt="12345678"
		// width="776" height="436" />
	}
}
