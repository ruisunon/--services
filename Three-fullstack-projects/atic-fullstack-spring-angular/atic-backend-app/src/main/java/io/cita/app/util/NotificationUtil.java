package io.cita.app.util;

import io.cita.app.model.dto.notif.MailNotification;

import java.util.Map;

public interface NotificationUtil {
	
	void sendMail(final MailNotification mailNotification);
	
	/**
	 * TODO: To be refactored for better impl
	 * Pick bodyProps as a body instead of mailNotification.body()
	 * @param mailNotification
	 * @param bodyProps
	 */
	void sendHtmlMail(final MailNotification mailNotification, final Map<String, Object> bodyProps);
	
}



