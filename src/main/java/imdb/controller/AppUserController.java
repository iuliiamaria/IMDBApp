package imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import imdb.service.AppUserService;

@Controller
public class AppUserController {

	@Autowired
	private AppUserService appUserService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView model = new ModelAndView();
		model.setViewName("user/login");

		return model;
	}
/*
	@RequestMapping(value = { "/home/home" }, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUser appUser = appUserService.findByEmail(authentication.getName());

		model.addObject("userName", appUser.getName());
		model.setViewName("home/home");

		return model;
	
	}
	*/

}