package com.planepockets.proton.service;

import com.planepockets.proton.exception.CustomException;
import com.planepockets.proton.mailservice.MailService;
import com.planepockets.proton.model.Role;
import com.planepockets.proton.model.User;
import com.planepockets.proton.model.UserAuthentication;
import com.planepockets.proton.repository.RoleRepository;
import com.planepockets.proton.repository.UserRepository;
import com.planepockets.proton.wrapperclasses.ForgotPasswordPojo;
import com.planepockets.proton.wrapperclasses.SimpleResponse;
import com.planepockets.util.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Qualifier("UserService")
@Service
public class UserService {

    static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    public User registerNewUser(User user) throws CustomException {
        if (userRepository.existsById(user.getLoginId())) {
            log.warn("User with loginId {} already exists", user.getLoginId());
            throw new CustomException("User with loginId: " + user.getLoginId() + " already exists");
        }
        Role role = roleRepository.findById("User").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setPassword(getEncodedPassword(user.getPassword()));
        log.info("User added successfully to the database {}", user);
        return userRepository.save(user);
    }

    public User registerAnonymousUser(User user) throws CustomException {
        if (userRepository.existsById(user.getLoginId())) {
            return user;
        }
        Role role = roleRepository.findById("User").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setCreatedAt(UtilityClass.createNewDate());
        user.setPassword(getEncodedPassword(user.getPassword()));
        log.info("User added successfully to the database {}", user);
        return userRepository.save(user);
    }

    public void initRoleAndUser() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role for the application");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("User role for the application");
        roleRepository.save(userRole);

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);

        User adminUser = new User("planepockets@gmail.com", "Admin", getEncodedPassword("planepockets@admin"), "9358342345", adminRoles, UtilityClass.createNewDate());
        userRepository.save(adminUser);
        log.info("User added successfully to the database {}", adminUser);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);

        User user = new User("manmohan@planepockets.com", "Manmohan Saraswat", getEncodedPassword("manmohan@123"), "9358342345",
                userRoles, UtilityClass.createNewDate());
        userRepository.save(user);
        log.info("User added successfully to the database {}", user);

    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public SimpleResponse forgotPassword(ForgotPasswordPojo obj) throws CustomException {
        Optional<User> user = userRepository.findById(obj.getLoginId());
        if (user.isEmpty()) throw new CustomException("User does not exists, can-not reset password");
        if (obj.getOtp() == null) {
            String otp = String.format("%04d", (int) (Math.random() * 10000));
            userAuthenticationService.saveOtp(obj.getLoginId(), otp);
            mailService.sendUserOtpPasswordReset(user.get().getFullName(), obj.getLoginId(), otp);
            return new SimpleResponse("Otp sent successful on mail");
        } else {
            UserAuthentication uA = userAuthenticationService.getOtp(obj.getLoginId());
            if (uA == null) throw new CustomException("Please resend the otp to continue");
            if (uA.getOtp().equals(obj.getOtp())) {
                User fUser = user.get();
                if(obj.getNewPassword() == null) throw new CustomException("Please provide the new password to continue");
                fUser.setPassword(passwordEncoder.encode(obj.getNewPassword()));
                userRepository.save(fUser);
                userAuthenticationService.deleteOtp(fUser.getLoginId());
                mailService.sendUserPasswordResetSuccessful(user.get().getFullName(), obj.getLoginId());
                return new SimpleResponse("Password reset successful");
            }
            throw new CustomException("Otp didn't matched, please try again");
        }
    }

    public SimpleResponse resetPassword(ForgotPasswordPojo obj) throws CustomException {
        Optional<User> user = userRepository.findById(obj.getLoginId());
        if (user.isEmpty()) throw new CustomException("User does not exists, can-not reset password");
        if (obj.getOldPassword() != null) {
            User fUser = user.get();
            if (passwordEncoder.matches(obj.getOldPassword(), fUser.getPassword())) {
                if(obj.getNewPassword() == null) throw new CustomException("Please provide the new password to continue");
                fUser.setPassword(passwordEncoder.encode(obj.getNewPassword()));
                userRepository.save(fUser);
                String subject = "Reset Password Successful- Arcreation";
                String body = "Hi " + obj.getLoginId() + ", Your password is reset successfully";
                mailService.sendMail(subject, body, obj.getLoginId());
                return new SimpleResponse("Password reset successful");
            }
            throw new CustomException("Old password didn't matched");
        }
        throw new CustomException("Please provide old password");
    }

    public User getUser(String userId) {
        Optional<User> opUser = userRepository.getUser(userId);
        return opUser.orElse(null);
    }

}
