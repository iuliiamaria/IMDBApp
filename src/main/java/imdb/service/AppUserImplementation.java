package imdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import imdb.entity.AppUser;
import imdb.repository.AppUserRepository;

public class AppUserImplementation implements AppUserService {

	@Autowired
	private AppUserRepository appUserRep;
	
	@Autowired
	private BCryptPasswordEncoder password;

	@Override
	public AppUser findByEmail(String email) {

		return appUserRep.findUserByEmail(email);
	}

	@Override
	public void saveUser(AppUser appUser) {
		
		appUser.setPass(password.encode(appUser.getPass()));
		
		appUserRep.save(appUser);

	}

}
