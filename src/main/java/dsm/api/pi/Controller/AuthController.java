package dsm.api.pi.Controller;


import dsm.api.pi.DTO.Servico.AuthResponseDTO;
import dsm.api.pi.DTO.User.AuthDTO;
import dsm.api.pi.DTO.User.UserRegisterDTO;
import dsm.api.pi.Entities.User;
import dsm.api.pi.Repository.UserRepository;
import dsm.api.pi.Service.BearerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private BearerService bearerService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO data){
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.userName(), data.senha());
        var auth = this.authenticationManager.authenticate(userNamePassword);

        var bearer = bearerService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new AuthResponseDTO(bearer));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRegisterDTO data){
        if(this.repository.findByUserName(data.userName()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        User newUser = new User(data.userName(), encryptedPassword, data.role());

        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
