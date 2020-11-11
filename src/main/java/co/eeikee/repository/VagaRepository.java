package co.eeikee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import co.eeikee.model.Funcionario;
import co.eeikee.model.Vaga;

public interface VagaRepository extends JpaRepository<Vaga, Long>{
	
	public List<Vaga> findByCodigoContaining(String codigo);
}
