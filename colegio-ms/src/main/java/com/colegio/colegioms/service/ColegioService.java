package com.colegio.colegioms.service;

import java.util.List;
import java.util.Optional;

import com.colegio.colegioms.compartilhado.ColegioDto;

public interface ColegioService {
    ColegioDto createColegio(ColegioDto colegio);

    List<ColegioDto> getAll();

    Optional<ColegioDto> getId(String id);

    void removeColegio(String id);

    ColegioDto updateColegio(String id, ColegioDto colegio);
}
