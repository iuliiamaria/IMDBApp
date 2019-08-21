package imdb.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import imdb.entity.AppUser;
import imdb.entity.Role;
import imdb.repository.AppUserRepository;
import imdb.repository.RoleRepository;

@Service("userService")
public class AppUserImplementation implements AppUserService {

	@Autowired
	private AppUserRepository appUserRep;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder password;

	@Override
	public AppUser findByEmail(String email) {

		return appUserRep.findUserByEmail(email);
	}

	@Override
	public void saveUser(AppUser appUser) {
		
		appUser.setPassword(password.encode(appUser.getPassword()));
		Role userRole = roleRepository.findByRole("Admin");
		appUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		
		appUserRep.save(appUser);

	}

}