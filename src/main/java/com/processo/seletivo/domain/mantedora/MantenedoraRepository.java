package com.processo.seletivo.domain.mantedora;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MantenedoraRepository extends JpaRepository<Mantenedora, Long> {

    public List<Mantenedora> findByNomeOrCodigo(String nome, String codigo);

    public List<Mantenedora> findByIdAndNomeOrCodigo(long id, String nome, String codigo);
}
