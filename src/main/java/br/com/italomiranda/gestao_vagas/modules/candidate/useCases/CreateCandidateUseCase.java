package br.com.italomiranda.gestao_vagas.modules.candidate.useCases;

import br.com.italomiranda.gestao_vagas.exceptions.UserFoundException;
import br.com.italomiranda.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.italomiranda.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity){
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(),candidateEntity.getEmail())
                .ifPresent((user)-> {
                    throw new UserFoundException();
                });

        var password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }


}
