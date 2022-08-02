package com.alunos.alunosms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alunos.alunosms.model.Alunos;

@Repository
public interface AlunosRepositorio extends JpaRepository<Alunos, String> {

	List<Alunos> findByAluno(String aluno);

}
