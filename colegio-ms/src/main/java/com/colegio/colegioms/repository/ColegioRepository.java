package com.colegio.colegioms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colegio.colegioms.model.Colegio;

@Repository
public interface ColegioRepository extends JpaRepository<Colegio, String> {

}
