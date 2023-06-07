package br.edu.ifrs.canoas.jee.webapp.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class Mensagens {

	private static ResourceBundle resBundle = ResourceBundle.getBundle("ValidationMessages");

	public static void define(Severity severity, String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(severity, getBundleMessage(msg),getBundleMessage(msg)));
	}
	

	public static void define(Severity severity, String msg, Object...args) {
		if (FacesContext.getCurrentInstance() != null)
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(severity, getBundleMessage(msg, args),getBundleMessage(msg)));
	}

	private static String getBundleMessage(final String key, final Object... args) {
		return MessageFormat.format(resBundle.getString(key), args);
	}
	
	private static String getBundleMessage(final String key) {
		return resBundle.getString(key);
	}

}
