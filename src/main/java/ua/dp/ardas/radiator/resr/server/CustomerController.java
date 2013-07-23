package ua.dp.ardas.radiator.resr.server;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("customer")
public class CustomerController {
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getAllCustomers(ModelMap model)
	{
		List<String> customers = Arrays.asList("Test1", "Test2");
//		CustomerList customerList = new CustomerList(customers);
		return new ModelAndView("customerView", "customers", customers);
	}
	
//	@RequestMapping(value="/{id}", method = RequestMethod.GET)
//	public ModelAndView getCustomer(@PathVariable Long id){
//		Customer customer = customerService.getCustomer(id);
//		return new ModelAndView("customerView", "customer", customer);
//
//	}
//
//	@RequestMapping(method = RequestMethod.POST)
//	public View addCustomer(@RequestBody Customer customer)
//	{
//		customerService.saveCustomer(customer);
//		return new RedirectView("/customerView/"+ customer.getId());
//	}
	
}

