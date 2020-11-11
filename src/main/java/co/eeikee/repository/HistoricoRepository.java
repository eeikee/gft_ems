package co.eeikee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.eeikee.model.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long>{

	public List<Historico> findByNomeFuncionarioContaining(String nomeFuncionario);
}
