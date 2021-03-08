package tr.com.paydaytrade.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tr.com.paydaytrade.authenticationservice.config.JwtTokenUtil;
import tr.com.paydaytrade.authenticationservice.domain.model.AppUser;
import tr.com.paydaytrade.authenticationservice.domain.model.ConfirmationToken;
import tr.com.paydaytrade.authenticationservice.domain.service.ConfirmationTokenService;
import tr.com.paydaytrade.authenticationservice.domain.service.EmailSenderService;
import tr.com.paydaytrade.authenticationservice.domain.service.UserService;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Autowired
    private EmailSenderService emailSenderServiceImpl;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @PostMapping(value = "/auth/signUp")
    public ResponseEntity<?> signUp(@RequestBody AppUser appUser) {
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        userService.createUser(appUser);
        return new ResponseEntity<>("User was created succesfully!", HttpStatus.OK);
    }

    @GetMapping(value = "/auth/signIn")
    public ResponseEntity<?> signIn(@RequestBody AppUser appUser) throws Exception {
        authenticate(appUser.getUserName(), appUser.getPassword());
        AppUser user = userService.findByUserName(appUser.getUserName());

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.createConfirmationToken(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("xxxxx@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/auth/confirmAccount?token=" + confirmationToken.getConfirmationToken());

        emailSenderServiceImpl.sendEmail(mailMessage);

        return new ResponseEntity<>("A verification email has been sent to your email address!", HttpStatus.OK);
    }


    @GetMapping(value = "/auth/confirmAccount")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken);
        if (token != null) {
            AppUser user = userService.findByUserName(token.getUser().getUserName());
            if (user != null) {
                return new ResponseEntity<>(jwtTokenUtil.generateToken(userDetailsServiceImpl.loadUserByUsername(user.getUserName())), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The link is invalid or broken!", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("The link is invalid or broken!", HttpStatus.BAD_REQUEST);
        }
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
