package in.sanket.ExpenseTrackerApi.controller;
import in.sanket.ExpenseTrackerApi.entity.AuthModel;
import in.sanket.ExpenseTrackerApi.entity.JwtResponse;
import in.sanket.ExpenseTrackerApi.entity.User;
import in.sanket.ExpenseTrackerApi.entity.UserModel;
import in.sanket.ExpenseTrackerApi.security.CustomUserDetailsService;
import in.sanket.ExpenseTrackerApi.service.UserService;
import in.sanket.ExpenseTrackerApi.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private CustomUserDetailsService userDetailsService;
@Autowired
    private AuthenticationManager authenticationManager;
@Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthModel authModel) throws Exception{

       authenticate(authModel.getEmail(), authModel.getPassword());
       final UserDetails userDetails = userDetailsService.loadUserByUsername(authModel.getEmail());
       final String token = jwtTokenUtil.generateToken(userDetails);
       return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
    }

    private void authenticate(String email, String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }
        catch (DisabledException e){
            throw new Exception("User disabled");
        }
        catch (BadCredentialsException e){
            throw new Exception("Bad Credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> save(@Valid @RequestBody UserModel user){
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }

}
