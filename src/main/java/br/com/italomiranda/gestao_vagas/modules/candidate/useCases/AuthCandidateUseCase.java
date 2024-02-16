package br.com.italomiranda.gestao_vagas.modules.candidate.useCases;

import br.com.italomiranda.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.italomiranda.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.italomiranda.gestao_vagas.modules.candidate.dto.AuthCandidateResquestDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthCandidateResponseDTO execute(AuthCandidateResquestDTO authCandidateResquestDTO) throws AuthenticationException {

        var candidate = this.candidateRepository.findByUsername(authCandidateResquestDTO.username())
                .orElseThrow(()->{
                   throw new UsernameNotFoundException("Username/Password incorrect");
                });

        var passwordMatches = this.passwordEncoder.matches(authCandidateResquestDTO.password(), candidate.getPassword());

        if(!passwordMatches){
            throw new AuthenticationException("Username/Password incorrect");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expires_in = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
                .withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("candidate"))
                .withExpiresAt(expires_in)
                .sign(algorithm);

        var AuthCandidateResponse = AuthCandidateResponseDTO.builder()
                .acess_token(token)
                .expires_in(expires_in.toEpochMilli())
                .build();

        return AuthCandidateResponse;
    }
}
