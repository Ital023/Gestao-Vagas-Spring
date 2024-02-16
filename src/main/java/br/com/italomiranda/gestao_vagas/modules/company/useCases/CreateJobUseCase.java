package br.com.italomiranda.gestao_vagas.modules.company.useCases;

import br.com.italomiranda.gestao_vagas.modules.company.Repositories.JobRepository;
import br.com.italomiranda.gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {
    @Autowired
    private JobRepository jobRepository;

    public JobEntity create(JobEntity jobEntity){
        return this.jobRepository.save(jobEntity);
    }
}
