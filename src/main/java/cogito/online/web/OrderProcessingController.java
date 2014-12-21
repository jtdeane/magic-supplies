package cogito.online.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cogito.online.model.Orders;
import cogito.online.processing.BatchServices;

/**
 * Handles submissions of batch orders
 * @author jeremydeane
 *
 */
@Controller
@RequestMapping("/")
public class OrderProcessingController {
	
	private static final Logger logger = LoggerFactory.getLogger
			(OrderProcessingController.class);
	
	@Autowired
	private BatchServices batchServices;
	
	
	/**
	 * Accepts in a batch of orders and processes them
	 * @param orders
	 * @throws Exception
	 */
	@RequestMapping(value = "order/java/fireandforget", method=RequestMethod.PUT)
	public void putOrdersJavaFireAndForget (@RequestBody Orders orders, 
			HttpServletResponse response) throws Exception {
		
		logger.debug("Processing Batch " + orders.getBatchId());
				
		//single threaded Java code
		batchServices.javaFireAndForget(orders.getOrders());
		
		response.setStatus(HttpStatus.ACCEPTED.value());
	}
	
	/**
	 * Simple Health Check
	 * @throws Exception
	 */
	@RequestMapping(value = "health", method=RequestMethod.GET)
	@ResponseBody
	public String getHealth (HttpServletResponse response) throws Exception {
		
		logger.debug("Processing Health Check ");
		
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
		
		return "All Systems Go";
	}	
	
	/**
	 * Accepts in a batch of orders and processes them
	 * @param orders
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "order/java/forkjoin", method=RequestMethod.PUT)
	public @ResponseBody String putOrdersJavaForkJoin (@RequestBody Orders orders, 
			HttpServletResponse response) throws Exception {
		
		logger.debug("Processing Batch " + orders.getBatchId());
				
		//single threaded Java code
		double batchTotal = batchServices.javaForkJoin(orders.getOrders());
		
		response.setStatus(HttpStatus.OK.value());
		
		return "$" + Double.toString(Math.round(batchTotal));
	}	
}