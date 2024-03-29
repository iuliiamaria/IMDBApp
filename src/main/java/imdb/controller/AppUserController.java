package imdb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import imdb.entity.AppUser;
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

	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView model = new ModelAndView();
		AppUser appUser = new AppUser();
		model.addObject("appUser", appUser);
		model.setViewName("user/signup");

		return model;
	}

	@RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
	public ModelAndView createUser(@Valid AppUser appUser, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		AppUser appUserExists = appUserService.findByEmail(appUser.getEmail());

		if (appUserExists != null) {
			bindingResult.rejectValue("email", "error.appuser", "This email already exists!");
		}

		if (bindingResult.hasErrors()) {
			model.setViewName("user/signup");
		} else {
			appUserService.saveUser(appUser);
			model.addObject("msg", "User has been registered succesfully!");
			model.addObject("appUser", new AppUser());
			model.setViewName("user/login");
		}

		return model;
	}

	@RequestMapping(value = { "/acces_denied" }, method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		ModelAndView model = new ModelAndView();
		model.setViewName("error/access_denied");

		return model;
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUser appUser = appUserService.findByEmail(authentication.getName());

		model.addObject("userName", appUser.getFirstName() + appUser.getLastName());
		model.setViewName("home/home");

		return model;

	}

}