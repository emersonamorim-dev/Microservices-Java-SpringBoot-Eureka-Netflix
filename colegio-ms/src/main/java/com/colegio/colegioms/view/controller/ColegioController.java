package com.colegio.colegioms.view.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colegio.colegioms.compartilhado.ColegioDto;
import com.colegio.colegioms.model.Colegio;
import com.colegio.colegioms.service.ColegioService;
import com.colegio.colegioms.view.model.ColegioModeloRequest;
import com.colegio.colegioms.view.model.ColegioModeloResponse;

@RestController
@RequestMapping("/api/colegio")
public class ColegioController {
    @Autowired
    private ColegioService service;

    @GetMapping(value = "/status")
    public String statusServico(@Value("${local.server.port}") String porta) {
        return String.format("Serviço ativo e executando na porta %s", porta);
    }

    @PostMapping
    public ResponseEntity<ColegioModeloResponse> createColegio(@RequestBody @Valid ColegioModeloRequest colegio) {
        ModelMapper mapper = new ModelMapper();
        ColegioDto dto = mapper.map(colegio, ColegioDto.class);
        dto = service.createColegio(dto);
        return new ResponseEntity<>(mapper.map(dto, ColegioModeloResponse.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ColegioModeloResponse>> getAll() {
        List<ColegioDto> dtos = service.getAll();

        if (dtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ModelMapper mapper = new ModelMapper();
        List<ColegioModeloResponse> resp = dtos.stream()
                .map(dto -> mapper.map(dto, ColegioModeloResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ColegioModeloResponse> getId(@PathVariable String id) {
        Optional<ColegioDto> colegio = service.getId(id);

        if (colegio.isPresent()) {
            return new ResponseEntity<>(
                    new ModelMapper().map(colegio.get(), ColegioModeloResponse.class),
                    HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ColegioModeloResponse> updateColegio(@PathVariable String id,
            @Valid @RequestBody Colegio colegio) {
        ModelMapper mapper = new ModelMapper();
        ColegioDto dto = mapper.map(colegio, ColegioDto.class);
        dto = service.updateColegio(id, dto);

        return new ResponseEntity<>(mapper.map(dto, ColegioModeloResponse.class), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removeColegio(@PathVariable String id) {
        service.removeColegio(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
