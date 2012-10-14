/*

Copyright (C) 2007 Steffen Dienst

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
package de.thorstenberger.examServer.webapp.action;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import de.christophjobst.main.ElateXMLMain;

import de.thorstenberger.examServer.tasks.TaskFactoryImpl;
import de.thorstenberger.examServer.webapp.form.TaskDefUploadForm;
import de.thorstenberger.taskmodel.TaskApiException;
import de.thorstenberger.taskmodel.TaskFactory;

public class TaskUploadAction extends BaseAction {
	private final static Log log = LogFactory.getLog(TaskUploadAction.class.getName());
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TaskDefUploadForm tduf = (TaskDefUploadForm) form;
		TaskFactory tf = (TaskFactory) getBean("TaskFactory");
		byte[] file_data = tduf.getTaskDefFile().getFileData();
		// check if file is to be imported from Moodle XML
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setCoalescing(true); // Convert CDATA to Text nodes
		DocumentBuilder parser = factory.newDocumentBuilder();
		Document document = parser.parse(new InputSource(new ByteArrayInputStream(file_data)));
		Node root_element = document.getFirstChild();
		if(root_element.getNodeName().equals("quiz")) {
			log.warn("Moodle XML hochgeladen");
			file_data = ElateXMLMain.startTransformToElateFormat(file_data);
			log.warn("Moodle XML bearbeitet");

			//System.out.println(new String(file_data));
		}

		// load Task Definition from XML
		if (tf instanceof TaskFactoryImpl) {
			try {
				long taskId = ((TaskFactoryImpl) tf).storeNewTaskDef(
						tduf.getTaskDefFile().getFileName(),
						file_data );
				ActionForward af = mapping.findForward("success");
				return new ActionForward(af.getPath() + "?taskId=" + taskId, true);

			} catch (TaskApiException e) {
				saveMessages(request, new ActionMessage("upload.error.format", e.getCause().getMessage()));
				log.warn("Invalid taskdef xml format", e);
			}
		}
		return mapping.findForward("error");
	}
}
