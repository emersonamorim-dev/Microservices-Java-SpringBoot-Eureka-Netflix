package com.alunos.alunosms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alunos.alunosms.compartilhado.AlunosDto;
import com.alunos.alunosms.model.Alunos;
import com.alunos.alunosms.repository.AlunosRepositorio;

@Service
public class AlunosServiceImpl implements AlunosService {
    @Autowired
    private AlunosRepositorio repo;

    @Override
    public AlunosDto createAluno(AlunosDto aluno) {
        return saveAluno(aluno);
    }

    @Override
    public List<AlunosDto> getAll() {
        List<Alunos> alunos = repo.findAll();

        return alunos.stream()
                .map(aluno -> new ModelMapper().map(aluno, AlunosDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AlunosDto> getId(String id) {
        Optional<Alunos> aluno = repo.findById(id);

        if (aluno.isPresent()) {
            return Optional.of(new ModelMapper().map(aluno.get(), AlunosDto.class));
        }

        return Optional.empty();
    }

    @Override
    public List<AlunosDto> getAluno(String colegio) {
        List<Alunos> alunos = repo.findByAluno(colegio);

        return alunos.stream()
                .map(aluno -> new ModelMapper().map(alunos, AlunosDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAluno(String id) {
        repo.deleteById(id);
    }

    @Override
    public boolean definirComoAprovado(String id) {
        Optional<Alunos> aluno = repo.findById(id);
        if (aluno.isPresent()) {
            aluno.get().setAprovado(false);
            repo.save(aluno.get());

            return true;
        }

        return false;
    }

    @Override
    public AlunosDto updateAluno(String id, AlunosDto aluno) {
        aluno.setId(id);
        return saveAluno(aluno);
    }

    private AlunosDto saveAluno(AlunosDto aluno) {
        ModelMapper mapper = new ModelMapper();
        Alunos alunoEntidade = mapper.map(aluno, Alunos.class);
        alunoEntidade = repo.save(alunoEntidade);

        return mapper.map(alunoEntidade, AlunosDto.class);
    }

}
