package co.eeikee.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.eeikee.model.Funcionario;
import co.eeikee.model.GFT;
import co.eeikee.model.Historico;
import co.eeikee.model.Tecnologia;
import co.eeikee.model.Vaga;
import co.eeikee.repository.FuncionarioRepository;
import co.eeikee.repository.GFTRepository;
import co.eeikee.repository.HistoricoRepository;
import co.eeikee.repository.TecnologiaRepository;
import co.eeikee.repository.VagaRepository;
import co.eeikee.repository.filter.FuncionarioFilter;
import co.eeikee.repository.filter.HistoricoFilter;
import co.eeikee.repository.filter.VagaFilter;

@Controller
public class FuncionarioController {
	
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
	
	boolean popular = false;
	
	@RequestMapping("/home")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@RequestMapping("/funcionario/cadastro")
	public ModelAndView funcionario() {
		ModelAndView mv = new ModelAndView("cadastroFuncionario");
		return mv;
	}
	
	@PostMapping(value = "/funcionario/cadastro/sucesso")
	public String cadastroFuncionario(@Validated Funcionario funcionario, Errors error, RedirectAttributes redirectAttributes) {
		if (error.hasErrors()) {
			return "cadastroFuncionario";
		}
		try {
			funcionarios.save(funcionario);
			redirectAttributes.addFlashAttribute("mensagem", "Funcionário adicionado com sucesso!");
			return "redirect:/funcionario/cadastro";
		} catch (Exception e) {
			error.rejectValue("inicio_wa", null, e.getMessage());
		}
		return "cadastroFuncionario";
	}
	
	@RequestMapping("/vaga/cadastro")
	public ModelAndView vaga() {
		ModelAndView mv = new ModelAndView("cadastroVaga");
		return mv;
	}
	
	@RequestMapping(value = "/vaga/cadastro/sucesso", method= RequestMethod.POST)
	public String cadastroVaga(@Validated Vaga vaga, Errors error, RedirectAttributes redirectAttributes) {
		if (error.hasErrors()) {
			return "cadastroVaga";
		}
		try {
			vagas.save(vaga);
			redirectAttributes.addFlashAttribute("mensagem", "Vaga adicionada com sucesso!");
			return "redirect:/vaga/cadastro";
		} catch (Exception e) {
			error.rejectValue("abertura_vaga", null, e.getMessage());
		}
		return "cadastroVaga";
	}
	
	@RequestMapping("/funcionario/pesquisa")
	public ModelAndView pesquisaFuncionario(@ModelAttribute("filtro")FuncionarioFilter filtro) {
		String matricula = filtro.getMatricula() == null ? "%" : filtro.getMatricula();
		List<Funcionario> todosFuncionarios = funcionarios.findByMatriculaContaining(matricula);
		ModelAndView mv = new ModelAndView("ListaFuncionario");
		mv.addObject("todosFuncionarios", todosFuncionarios);
		return mv;
	}
	
	@RequestMapping("/vaga/pesquisa")
	public ModelAndView pesquisaVaga(@ModelAttribute("filtro")VagaFilter filtro){
		String codigo = filtro.getCodigo() == null ? "%" : filtro.getCodigo();
		List<Vaga> todasVagas = vagas.findByCodigoContaining(codigo);
		ModelAndView mv = new ModelAndView("ListaVaga");
		mv.addObject("todasVagas", todasVagas);
		return mv;
	}
	
	
	@ModelAttribute("funcioariosWA")
	public List<Funcionario> todosFuncionarios(){
		return funcionarios.findAll();
	}
	
	@ModelAttribute("GFTs")
	public List<GFT> todasGFTs(){
		return gfts.findAll();
	}
	
	@ModelAttribute("todasTecnologias")
	public List<Tecnologia> todasTecnologias(){
		return tecnologias.findAll();
	}
	
	@ModelAttribute("todasAsVagas")
	public List<Vaga> todasVagas(){
		return vagas.findAll();
	}
	
	@RequestMapping(value = "/alocar", method={ RequestMethod.GET, RequestMethod.POST })
	public String alocacao(@Validated Historico historico,Errors error, RedirectAttributes redirectAttributes) {
		if (error.hasErrors()) {
			redirectAttributes.addFlashAttribute("filtro", "%");
			return "listaFuncionario";
		}
		try {
			Long vagaId = historico.getVaga_id();
			Vaga vaga = vagas.getOne(vagaId);
			historico.setNomeCliente(vaga.getProjeto());
			historico.setCodigoVaga(vaga.getCodigo());
			historico.setInicioAlocacao(new Date());
			historicos.save(historico);
			funcionarios.deleteById(historico.getFuncionario_id());
			if(vaga.getQtd_vaga()>0) {
				vaga.setQtd_vaga(vaga.getQtd_vaga()-1);
				if (vaga.getQtd_vaga()==0) {
					vagas.deleteById(vagaId);
				}
				else {
					vagas.save(vaga);
				}
			}
			redirectAttributes.addFlashAttribute("mensagem", "Funcionario alocado com sucesso!");
			return "redirect:/funcionario/pesquisa?matricula=";
		} catch (Exception e) {
			error.rejectValue("abertura_vaga", null, e.getMessage());
		}
		redirectAttributes.addFlashAttribute("filtro", "%");
		return "listaFuncionario";
	}
	
	@RequestMapping("/historico")
	public ModelAndView historicoWA(@ModelAttribute("filtro")HistoricoFilter filtro) {
		String nomeFuncionario = filtro.getNomeFuncionario() == null ? "%" : filtro.getNomeFuncionario();
		List<Historico> todosHistoricos = historicos.findByNomeFuncionarioContaining(nomeFuncionario);
		ModelAndView mv = new ModelAndView("historico");
		mv.addObject("todosHistoricos", todosHistoricos);
		return mv;
	}
		
	@RequestMapping("/funcionario/alocar")
	public String acessoAlocacao(@ModelAttribute("filtro")FuncionarioFilter filtro, RedirectAttributes redirectAttributes) {
		List<Funcionario> todosFuncionarios = funcionarios.findByMatriculaContaining("%");
		redirectAttributes.addFlashAttribute("mensagem", "Para realizar a alocação de um funcionario selecione o icone de alocação no lado direito da pagina.");
		redirectAttributes.addFlashAttribute("todosFuncionarios", todosFuncionarios);
		return "redirect:/funcionario/pesquisa?matricula=";
	}	
	
	@GetMapping("/error")
    public ModelAndView error(){
        final ModelAndView modelAndView = new ModelAndView("error");
        return modelAndView;
    }

}
