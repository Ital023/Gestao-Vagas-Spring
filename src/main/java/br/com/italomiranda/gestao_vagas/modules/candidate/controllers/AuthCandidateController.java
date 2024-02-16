package br.com.italomiranda.gestao_vagas.modules.candidate.controllers;

import br.com.italomiranda.gestao_vagas.modules.candidate.dto.AuthCandidateResquestDTO;
import br.com.italomiranda.gestao_vagas.modules.candidate.useCases.AuthCandidateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateResquestDTO authCandidateResquestDTO){

        try{
            var token = this.authCandidateUseCase.execute(authCandidateResquestDTO);
            return ResponseEntity.ok().body(token);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }


    }

}
