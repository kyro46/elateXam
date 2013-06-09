package de.thorstenberger.examServer.webapp.action;

import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import de.thorstenberger.examServer.webapp.form.FileUploadForm;

public class FileUploadAction extends BaseAction {
	private final static Log log = LogFactory.getLog(FileUploadAction.class.getName());

	@Override
	public ActionForward execute(final ActionMapping mapping,
			final ActionForm form, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		FileUploadForm fuf = (FileUploadForm) form;

		FormFile myFile = fuf.getFile();
		String fileName = myFile.getFileName();

		List<String> filetypes = new ArrayList<String>();
		filetypes.add("mp3");
		filetypes.add("ogg");
		filetypes.add("wav");
		filetypes.add("avi");
		filetypes.add("mp4");
		filetypes.add("webm");
		filetypes.add("flv");
		filetypes.add("sfw");
		
		String fspath = System.getProperty("user.dir").replaceAll("bin", "webapps/files");
		new File(fspath).mkdir();

		/* Save file on the server */
		if (!fileName.equals("")) {
			Boolean filetypeOK = false;
			for (String filetype:filetypes) {
				System.out.println("Momentane Endung: " + filetype);
				if (fileName.substring(fileName.length()-3, fileName.length()).equalsIgnoreCase(filetype)) {
					filetypeOK = true;
					break;
				} else {
					filetypeOK = false;
				}
			}

			if (!filetypeOK) {
				final ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"file.wrongfiletype"));
				saveMessages(request, messages);
				return mapping.findForward("success");		
			}

			// Create file
			File fileToCreate = new File(fspath, fileName);
			// If file does not exists create file
			if (!fileToCreate.exists()) {
				FileOutputStream fileOutStream = new FileOutputStream(
						fileToCreate);
				fileOutStream.write(myFile.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
				final ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"file.saved"));
				saveMessages(request, messages);
			} else {
				final ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"file.allreadyexists"));
				saveMessages(request, messages);
			}
		} else {
			final ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"file.nofileselected"));
			saveMessages(request, messages);
		}

		// Set file name to the request object
		request.setAttribute("fileName", fileName);

		return mapping.findForward("success");
	}
}
