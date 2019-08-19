package imdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import imdb.entity.AppUser;

@Repository("appUserRepository")
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	AppUser findUserByEmail(String email);

}
