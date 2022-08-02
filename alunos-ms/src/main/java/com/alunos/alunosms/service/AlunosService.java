package com.alunos.alunosms.service;

import java.util.List;
import java.util.Optional;

import com.alunos.alunosms.compartilhado.AlunosDto;

public interface AlunosService {
    AlunosDto createAluno(AlunosDto aluno);

    List<AlunosDto> getAll();

    Optional<AlunosDto> getId(String id);

    List<AlunosDto> getAluno(String aluno);

    void removeAluno(String id);

    boolean definirComoAprovado(String id);

    AlunosDto updateAluno(String id, AlunosDto aluno);
}
