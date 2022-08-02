package com.colegio.colegioms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.colegioms.compartilhado.ColegioDto;
import com.colegio.colegioms.model.Colegio;
import com.colegio.colegioms.repository.ColegioRepository;

@Service
public class ColegioServiceImpl implements ColegioService {
    @Autowired
    private ColegioRepository repo;

    @Override
    public ColegioDto createColegio(ColegioDto colegio) {
        return saveColegio(colegio);
    }

    @Override
    public List<ColegioDto> getAll() {
        List<Colegio> colegios = repo.findAll();

        return colegios.stream()
                .map(colegio -> new ModelMapper().map(colegio, ColegioDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ColegioDto> getId(String id) {
        Optional<Colegio> colegio = repo.findById(id);

        if (colegio.isPresent()) {

            ColegioDto dto = new ModelMapper().map(colegio.get(), ColegioDto.class);

            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @Override
    public void removeColegio(String id) {
        repo.deleteById(id);
    }

    @Override
    public ColegioDto updateColegio(String id, ColegioDto colegio) {
        colegio.setId(id);
        return saveColegio(colegio);
    }

    private ColegioDto saveColegio(ColegioDto colegio) {
        ModelMapper mapper = new ModelMapper();
        Colegio colegioEntidade = mapper.map(colegio, Colegio.class);
        colegioEntidade = repo.save(colegioEntidade);

        return mapper.map(colegioEntidade, ColegioDto.class);
    }
}
