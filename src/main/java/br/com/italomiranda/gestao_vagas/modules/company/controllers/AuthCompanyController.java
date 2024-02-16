package br.com.italomiranda.gestao_vagas.modules.company.controllers;

import br.com.italomiranda.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.italomiranda.gestao_vagas.modules.company.useCases.AuthCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase; //chama o objeto de autenticacao

    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO){
       try{
           var result =  this.authCompanyUseCase.execute(authCompanyDTO); //verifica e gera o token da empresa
            return ResponseEntity.ok().body(result);
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
       }
    }
}
