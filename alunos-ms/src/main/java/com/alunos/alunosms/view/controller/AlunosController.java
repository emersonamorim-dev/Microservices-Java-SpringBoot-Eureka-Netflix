package com.alunos.alunosms.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alunos.alunosms.compartilhado.AlunosDto;
import com.alunos.alunosms.service.AlunosService;
import com.alunos.alunosms.view.model.AlunosModeloAlteracao;
import com.alunos.alunosms.view.model.AlunosModeloInclusao;
import com.alunos.alunosms.view.model.AlunosModeloResponse;

@RestController
@RequestMapping("/api/alunos")
public class AlunosController {
    @Autowired
    private AlunosService service;

    @GetMapping(value = "/status")
    public String statusServico(@Value("${local.server.port}") String porta) {
        return String.format("Serviço ativo e executando na porta %s", porta);
    }

    @PostMapping
    public ResponseEntity<AlunosModeloResponse> createAluno(@RequestBody @Valid AlunosModeloInclusao Aluno) {
        ModelMapper mapper = new ModelMapper();
        AlunosDto dto = mapper.map(Aluno, AlunosDto.class);
        dto = service.createAluno(dto);
        return new ResponseEntity<>(mapper.map(dto, AlunosModeloResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AlunosModeloResponse>> getAll() {
        List<AlunosDto> dtos = service.getAll();

        if (dtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ModelMapper mapper = new ModelMapper();
        List<AlunosModeloResponse> resp = dtos.stream()
                .map(dto -> mapper.map(dto, AlunosModeloResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(value = "/{aluno}/lista")
    public ResponseEntity<List<AlunosModeloResponse>> getAluno(@PathVariable String aluno) {
        List<AlunosDto> dtos = service.getAluno(aluno);

        if (dtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ModelMapper mapper = new ModelMapper();
        List<AlunosModeloResponse> resp = dtos.stream()
                .map(dto -> mapper.map(dto, AlunosModeloResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AlunosModeloResponse> getId(@PathVariable String id) {
        Optional<AlunosDto> Aluno = service.getId(id);

        if (Aluno.isPresent()) {
            return new ResponseEntity<>(
                    new ModelMapper().map(Aluno.get(), AlunosModeloResponse.class),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AlunosModeloResponse> updateAluno(@PathVariable String id,
            @Valid @RequestBody AlunosModeloAlteracao Aluno) {
        ModelMapper mapper = new ModelMapper();
        AlunosDto dto = mapper.map(Aluno, AlunosDto.class);
        dto = service.updateAluno(id, dto);

        return new ResponseEntity<>(mapper.map(dto, AlunosModeloResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeAluno(@PathVariable String id) {
        service.removeAluno(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> definirAprovado(@PathVariable String id) {
        if (service.definirComoAprovado(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
