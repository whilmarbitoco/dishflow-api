package org.whilmarbitoco.Service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.whilmarbitoco.Core.Model.User;
import org.whilmarbitoco.Repository.UserRepository;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void verifyUser(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new BadRequestException("User Doesn't Exist.");
        }

        if (user.IsVerified()) {
            throw new BadRequestException("User Already Verified");
        }
        user.verify();

    }

}
