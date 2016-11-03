package polischukovik.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import polischukovik.ui.UserInterfaceSet;
import polischukovik.web.service.PropertyService;
import polischukovik.web.service.TestService;

@Controller
@SessionAttributes(names={"categories"})
public class TestController {
	@Autowired
	PropertyService propertyService;
	
	@Autowired
	TestService testService;
	
	@Autowired
	UserInterfaceSet currentInterfaceSet;
	
	@RequestMapping("/")
	public String rootMapping(){
		return "redirect:/test-main";
	}
	
	@RequestMapping("/test-main")
	public String testMain(ModelMap model) throws InstantiationException, IllegalAccessException{
		model.addAttribute("categories", propertyService.getPropertyList(currentInterfaceSet));
		return "test-main";
	}
	
	@RequestMapping("/create")
	public String createTest(ModelMap model){
		String s = testService.createTest();
		System.err.println(s);
		model.addAttribute("result", s);
		return "test-main";
	}

}
