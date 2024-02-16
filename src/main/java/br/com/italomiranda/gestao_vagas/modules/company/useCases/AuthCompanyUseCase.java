package br.com.italomiranda.gestao_vagas.modules.company.useCases;

import br.com.italomiranda.gestao_vagas.modules.company.Repositories.CompanyRepository;
import br.com.italomiranda.gestao_vagas.modules.company.dto.AuthCompanyDTO;
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

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; //Invoca o objeto de gerador de Token JWT

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException { //Passa o objeto da auth de company DTO
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Username/Password Not Found"); //Verifica se o usuario está dentro do repo, se nao lança exception
                }
        );
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword()); // Verifica se a senha passa está igual a que está salva no repo do bd

        if(!passwordMatches){
            throw new AuthenticationException();
        }

        //Se for igual -> Gerando o token
        Algorithm algorithm = Algorithm.HMAC256(secretKey); //Eh minha senha para criptografar o token, pois cada letra vai ser diferente
        var token = JWT.create().withIssuer("javagas") //withIssuer = ao emissor que emitiu o token, ex: nome da empresa
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString()) // Define o payload, geralmente usa o dono do token (id do usuario), uma info unica da company
                .sign(algorithm);
        return token;
    }



}
