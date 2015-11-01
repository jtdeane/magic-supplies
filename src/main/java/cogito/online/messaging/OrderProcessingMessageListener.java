package cogito.online.messaging;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens for Magic Supply Orders
 * @author jeremydeane
 */
public class OrderProcessingMessageListener implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger
			(OrderProcessingMessageListener.class);	

	@Override
	public void onMessage (Message message) {

		try {			
			
			TextMessage textMessage = (TextMessage) message;
			
			logger.debug("Processed Magic Order: \n" + textMessage.getText());
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}
	}
}