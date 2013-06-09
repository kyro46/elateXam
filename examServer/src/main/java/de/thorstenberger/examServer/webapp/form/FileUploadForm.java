package de.thorstenberger.examServer.webapp.form;

import org.apache.struts.upload.FormFile;


public class FileUploadForm extends BaseForm {
	FormFile file;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}


	}
