package co.eeikee.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.eeikee.model.Funcionario;
import co.eeikee.model.Historico;
import co.eeikee.model.Vaga;
import co.eeikee.repository.FuncionarioRepository;
import co.eeikee.repository.GFTRepository;
import co.eeikee.repository.HistoricoRepository;
import co.eeikee.repository.TecnologiaRepository;
import co.eeikee.repository.VagaRepository;
import co.eeikee.repository.filter.FuncionarioFilter;
import co.eeikee.repository.filter.HistoricoFilter;
import co.eeikee.repository.filter.VagaFilter;

@Service
public class FuncionarioService {

	@Autowired
	FuncionarioRepository funcionarios;
	
	@Autowired
	VagaRepository vagas;
	
	@Autowired
	GFTRepository gfts;
	
	@Autowired
	TecnologiaRepository tecnologias;
	
	@Autowired
	HistoricoRepository historicos;
	
	
	public void salvarHistorico(Historico historico) {
		Long vagaId = historico.getVaga_id();
		Vaga vaga = vagas.getOne(vagaId);
		historico.setNomeCliente(vaga.getProjeto());
		historico.setCodigoVaga(vaga.getCodigo());
		historico.setInicioAlocacao(new Date());
		historicos.save(historico);
	}
	
	public void alocarFuncionario(Historico historico) {
		Long vagaId = historico.getVaga_id();
		Vaga vaga = vagas.getOne(vagaId);
		Funcionario funcionario = funcionarios.getOne(historico.getFuncionario_id());
		if(vaga.getQtd_vaga()>0) {
			vaga.setQtd_vaga(vaga.getQtd_vaga()-1);
			vagas.save(vaga);
		}
		funcionario.setVaga(vaga);
		funcionarios.save(funcionario);
	}
	
	public void atualizarQtdVaga(Historico historico) {
		Long vagaId = historico.getVaga_id();
		Vaga vaga = vagas.getOne(vagaId);
		if(vaga.getQtd_vaga()>0) {
			vaga.setQtd_vaga(vaga.getQtd_vaga()-1);
			if (vaga.getQtd_vaga()==0) {
				vagas.deleteById(vagaId);
			}
			else {
				vagas.save(vaga);
			}
		}
	}
	
	public List<Historico> listarHistoricos(HistoricoFilter filtro){
		String nomeFuncionario = filtro.getNomeFuncionario() == null ? "%" : filtro.getNomeFuncionario();
		return historicos.findByNomeFuncionarioContaining(nomeFuncionario);
	}
	
	public List<Vaga> listarVagas(VagaFilter filtro){
		String codigo = filtro.getCodigo() == null ? "%" : filtro.getCodigo();
		return vagas.findByCodigoContaining(codigo);
	}
	
	public List<Funcionario> listarFuncionarios(FuncionarioFilter filtro){
		String matricula = filtro.getMatricula() == null ? "%" : filtro.getMatricula();
		return funcionarios.findByMatriculaContaining(matricula);
	}
	
	public void mensagemAlocar(FuncionarioFilter filtro, RedirectAttributes redirectAttributes) {
		List<Funcionario> todosFuncionarios = funcionarios.findByMatriculaContaining("%");
		redirectAttributes.addFlashAttribute("mensagem", "Para realizar a alocação de um funcionario selecione o icone de alocação no lado direito da pagina.");
		redirectAttributes.addFlashAttribute("todosFuncionarios", todosFuncionarios);
	}
}
