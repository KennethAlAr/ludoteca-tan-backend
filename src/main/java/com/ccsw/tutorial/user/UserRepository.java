package com.ccsw.tutorial.user;

import com.ccsw.tutorial.user.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 */
public interface UserRepository extends CrudRepository<User, String> {

}
