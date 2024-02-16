package br.com.italomiranda.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;
    
    public String name;

    @NotBlank()
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
    public String username;

    @Email(message = "O campo deve conter um email valido")
    public String email;

    @Length(min = 10,max = 100, message = "A senha deve conter entre 10 a 100 caracteres")
    public String password;

    public String description;

    public String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
