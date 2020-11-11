package co.eeikee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.eeikee.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

	public List<Funcionario> findByMatriculaContaining(String matricula);
}
