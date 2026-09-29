package com.ccsw.tutorial.user;

import com.ccsw.tutorial.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public User get(String name) {

        return this.userRepository.findById(name).orElse(null);
    }
}
