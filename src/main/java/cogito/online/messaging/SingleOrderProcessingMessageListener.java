package cogito.online.messaging;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cogito.online.model.Order;
import cogito.online.processing.BatchServices;

/**
 * Listens for Magic Supply Order (Single)
 * @author jeremydeane
 */
public class SingleOrderProcessingMessageListener implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger
			(SingleOrderProcessingMessageListener.class);
	
	private final JAXBContext jaxbContext;
	
	/**
	 * Default Constructor
	 * @throws Exception
	 */
	public SingleOrderProcessingMessageListener () throws Exception {
		jaxbContext = JAXBContext.newInstance(Order.class);
	}
	
	@Autowired
	BatchServices batchServices;

	@Override
	public void onMessage (Message message) {
		
		Order order = null;

		try {			
			
			TextMessage textMessage = (TextMessage) message;
				
			order = xmlToPOJO(textMessage.getText());
			
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(order);
		
			batchServices.javaFireAndForget(orderList);
			
			logger.debug("Processed Magic Order:" + order);
			
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
	private Order xmlToPOJO (String xml) throws Exception {
		
		logger.debug("Order : " + xml);
	
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		InputStream stream = IOUtils.toInputStream (xml);	

		return (Order) unmarshaller.unmarshal(stream);		
	}
}