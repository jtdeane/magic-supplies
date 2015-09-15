package cogito.online.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cogito.online.model.Orders;

/**
 * Handles submissions of magic orders
 * @author jeremydeane
 *
 */
@RestController
@RequestMapping("/")
public class OrderProcessingController {
	
	private static final Logger logger = LoggerFactory.getLogger
			(OrderProcessingController.class);
	
	
	/**
	 * Accepts in a batch of orders and processes them
	 * @param orders
	 * @throws Exception
	 */
	@RequestMapping(value = "orders", method=RequestMethod.PUT)
	public void putOrdersJavaFireAndForget (@RequestBody Orders orders, 
			HttpServletResponse response) throws Exception {
		
		logger.debug("Processing Batch " + orders.getBatchId());
		
		response.setStatus(HttpStatus.ACCEPTED.value());
	}
	
	/**
	 * Simple Health Check
	 * @throws Exception
	 */
	@RequestMapping(value = "health", method=RequestMethod.GET)
	public String getHealth (HttpServletResponse response) throws Exception {
		
		logger.debug("Processing Health Check ");
		
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
		
		return "All Systems Go";
	}	
}