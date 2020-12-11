package integracao.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import integracao.rest.model.ContatoModel;

public interface ContatoRepository extends JpaRepository<ContatoModel, Long> {

}
