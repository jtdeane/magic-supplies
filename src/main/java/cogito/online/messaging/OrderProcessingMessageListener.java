package cogito.online.messaging;

import java.io.InputStream;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cogito.online.model.Orders;

/**
 * Listens for Magic Supply Orders
 * @author jeremydeane
 */
public class OrderProcessingMessageListener implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger
			(OrderProcessingMessageListener.class);
	
	private final JAXBContext jaxbContext;
	
	/**
	 * Default Constructor
	 * @throws Exception
	 */
	public OrderProcessingMessageListener () throws Exception {
		jaxbContext = JAXBContext.newInstance(Orders.class);
	}

	@Override
	public void onMessage (Message message) {
		
		Orders orders = null;

		try {			
			
			TextMessage textMessage = (TextMessage) message;
				
			orders = xmlToPOJO(textMessage.getText());
			
			logger.debug("Processed Magic Order:" + orders);
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Transrept to POJO from xml
	 * @param xml
	 * @return Orders
	 * @throws Exception
	 */
	private Orders xmlToPOJO (String xml) throws Exception {
		
		logger.debug("Orders Payload: " + xml);
	
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		InputStream stream = IOUtils.toInputStream (xml);	

		return (Orders) unmarshaller.unmarshal(stream);		
	}
}