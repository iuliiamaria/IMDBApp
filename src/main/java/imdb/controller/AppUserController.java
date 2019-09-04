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

import com.fasterxml.jackson.annotation.JacksonInject.Value;

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

	@RequestMapping(value = { "/user/signup" }, method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView model = new ModelAndView();
		AppUser appUser = new AppUser();
		model.addObject("appuser", appUser);
		model.setViewName("user/signup");

		return model;
	}

	@RequestMapping(value = { "/user/signup" }, method = RequestMethod.POST)
	public ModelAndView createUser(@Valid AppUser appUser, BindingResult bindingResult) {
		ModelAndView model = new ModelAndView();
		AppUser appUserExists = appUserService.findByEmail(appUser.getEmail());

		if (appUserExists != null) {
			bindingResult.rejectValue("email", "error.user", "This email already exists!");
		}

		if (bindingResult.hasErrors()) {
			model.setViewName("user/signup");
		} else {
			appUserService.saveUser(appUser);
			model.addObject("msg", "User has been registered succesfully!");
			model.addObject("appuser", new AppUser());
			model.setViewName("user/login");
		}

		return model;
	}

	@RequestMapping(value = { "/home/home" }, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView model = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AppUser appUser = appUserService.findByEmail(authentication.getName());

		model.addObject("userName", appUser.getFirstName() + " " + appUser.getLastName());
		model.setViewName("home/home");

		return model;

	}

	@RequestMapping(value = { "/errors/acces_denied" }, method = RequestMethod.GET)
	public ModelAndView accesDenied() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errors/access_denied");

		return model;
	}
}