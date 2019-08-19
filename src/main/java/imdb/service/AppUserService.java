package imdb.service;

import imdb.entity.AppUser;

public interface AppUserService {

	public AppUser findByEmail(String email);

	public void saveUser(AppUser appUser);

}
