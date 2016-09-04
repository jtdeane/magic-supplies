package cogito.online.messaging;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlertProcessingTopicListener implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger
			(AlertProcessingTopicListener.class);
	
	@Override
	public void onMessage (Message message) {

		try {			
			
			TextMessage textMessage = (TextMessage) message;
			
			logger.info("Magic Alert:" + textMessage.getText());
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}
	}	
}