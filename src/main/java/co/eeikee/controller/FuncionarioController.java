package co.eeikee.controller;

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
import co.eeikee.service.FuncionarioService;

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

	@Autowired
	FuncionarioService fs;

	boolean popular = false;

	@RequestMapping("/wa")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}

	@RequestMapping("/wa/funcionarios/cadastrar")
	public ModelAndView funcionario() {
		ModelAndView mv = new ModelAndView("cadastroFuncionario");
		mv.addObject(new Funcionario());
		return mv;
	}

	@PostMapping(value = "/wa/funcionarios/cadastrar/sucesso")
	public String cadastroFuncionario(@Validated Funcionario funcionario, Errors error,
			RedirectAttributes redirectAttributes) {
		if (error.hasErrors()) {
			return "cadastroFuncionario";
		}
		try {
			funcionarios.save(funcionario);
			redirectAttributes.addFlashAttribute("mensagem", "Funcionário adicionado com sucesso!");
			return "redirect:/wa/funcionarios?matricula=";
		} catch (Exception e) {
			error.rejectValue("inicio_wa", null, e.getMessage());
		}
		return "cadastroFuncionario";
	}

	@RequestMapping("/wa/vagas/cadastrar")
	public ModelAndView vaga() {
		ModelAndView mv = new ModelAndView("cadastroVaga");
		mv.addObject(new Vaga());
		return mv;
	}

	@RequestMapping(value = "/wa/vagas/cadastrar/sucesso", method = RequestMethod.POST)
	public String cadastroVaga(@Validated Vaga vaga, Errors error, RedirectAttributes redirectAttributes) {
		if (error.hasErrors()) {
			return "cadastroVaga";
		}
		try {
			vagas.save(vaga);
			redirectAttributes.addFlashAttribute("mensagem", "Vaga adicionada com sucesso!");
			return "redirect:/wa/vagas?codigo=";
		} catch (Exception e) {
			error.rejectValue("abertura_vaga", null, e.getMessage());
		}
		return "cadastroVaga";
	}

	@RequestMapping("/wa/funcionarios")
	public ModelAndView pesquisaFuncionario(@ModelAttribute("filtro") FuncionarioFilter filtro) {
		ModelAndView mv = new ModelAndView("ListaFuncionario");
		mv.addObject("todosFuncionarios", fs.listarFuncionarios(filtro));
		return mv;
	}

	@RequestMapping("/wa/vagas")
	public ModelAndView pesquisaVaga(@ModelAttribute("filtro") VagaFilter filtro) {
		ModelAndView mv = new ModelAndView("ListaVaga");
		mv.addObject("todasVagas", fs.listarVagas(filtro));
		return mv;
	}

	@ModelAttribute("funcioariosWA")
	public List<Funcionario> todosFuncionarios() {
		return funcionarios.findAll();
	}

	@ModelAttribute("GFTs")
	public List<GFT> todasGFTs() {
		return gfts.findAll();
	}

	@ModelAttribute("todasTecnologias")
	public List<Tecnologia> todasTecnologias() {
		return tecnologias.findAll();
	}

	@ModelAttribute("todasAsVagas")
	public List<Vaga> todasVagas() {
		return vagas.findAll();
	}

	@ModelAttribute("todosAlocados")
	public boolean todosAlocados() {
		return fs.verificacaoWA();
	}
	
	@ModelAttribute("semVagas")
	public boolean semVagas(){
		return fs.verificacaoVaga();
	}
	
	@ModelAttribute("indisponivel")
	public boolean vagaIndisponivel(){
		return fs.verificacaoVagaIndisponivel();
	}

	@RequestMapping(value = "/wa/alocar", method = { RequestMethod.GET, RequestMethod.POST })
	public String alocacao(@ModelAttribute("filtro") FuncionarioFilter filtro, @Validated Historico historico,
			Errors error, RedirectAttributes redirectAttributes) {
		if (error.hasErrors()) {
			redirectAttributes.addFlashAttribute("mensagem", "Não foi possível alocar este funcionário. Selecione uma vaga válida para realizar a alocação.");
			return "redirect:/wa/funcionarios?matricula=";
		}
		try {
			fs.salvarHistorico(historico);
			fs.alocarFuncionario(historico);
			fs.atualizarQtdVaga(historico);
			redirectAttributes.addFlashAttribute("mensagem", "Funcionario alocado com sucesso!");
			return "redirect:/wa/historico?nomeFuncionario=";
		} catch (Exception e) {
			error.rejectValue("abertura_vaga", null, e.getMessage());
		}
		redirectAttributes.addFlashAttribute("filtro", "%");
		return "redirect:/wa/funcionarios?matricula=";
	}

	@RequestMapping("/wa/historico")
	public ModelAndView historicoWA(@ModelAttribute("filtro") HistoricoFilter filtro) {
		ModelAndView mv = new ModelAndView("historico");
		mv.addObject("todosHistoricos", fs.listarHistoricos(filtro));
		return mv;
	}

	@RequestMapping("/wa/funcionarios/alocar")
	public String acessoAlocacao(@ModelAttribute("filtro") FuncionarioFilter filtro,
			RedirectAttributes redirectAttributes) {
		fs.mensagemAlocar(filtro, redirectAttributes);
		return "redirect:/wa/funcionarios?matricula=";
	}

	@RequestMapping("/wa/vagas/{id}")
	public ModelAndView editarVaga(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("cadastroVaga");
		mv.addObject(fs.editarVaga(id));
		return mv;
	}

	@RequestMapping("/wa/funcionarios/{id}")
	public ModelAndView editarFuncionario(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("cadastroFuncionario");
		mv.addObject(fs.editarFuncionario(id));
		return mv;
	}

	@RequestMapping(value = "/wa/vagas/hide/{id}" ,method = { RequestMethod.GET, RequestMethod.POST })
	public String esconderVaga(@PathVariable Long id) {
		fs.esconderVaga(id);
		return "redirect:/wa/vagas?codigo=";
	}
	
	@RequestMapping(value = "/wa/funcionarios/hide/{id}",  method = { RequestMethod.GET, RequestMethod.POST })
	public String esconderFuncionario(@PathVariable Long id) {
		fs.esconderFuncionario(id);
		return "redirect:/wa/funcionarios?matricula=";
	}
	
	
	/*
	 * @RequestMapping(value = "/wa/vagas/{id}",method = RequestMethod.DELETE)
	 * public String excluirVaga(@PathVariable Long id, RedirectAttributes ra) {
	 * try { vagas.deleteById(id); ra.addFlashAttribute("mensagem",
	 * "Vaga removida com sucesso."); return "/wa/vagas?codigo="; } catch (Exception
	 * e) { ra.addFlashAttribute("mensagem",
	 * "Houve um erro ao tentar remover esta vaga."); return "/wa/vagas?codigo="; }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/wa/funcionarios/{id}",method =
	 * RequestMethod.DELETE) public String excluirFuncionario(@PathVariable Long id,
	 * RedirectAttributes ra) { 
	 * try { funcionarios.deleteById(id); ra.addFlashAttribute("mensagem",
	 * "Funcionário removido com sucesso."); return "/wa/funcionarios?codigo="; }
	 * catch (Exception e) { ra.addFlashAttribute("mensagem",
	 * "Houve um erro ao tentar remover esta vaga."); return
	 * "/wa/funcionarios?codigo="; }
	 * 
	 * }
	 */

	@GetMapping("/error")
	public ModelAndView error() {
		final ModelAndView modelAndView = new ModelAndView("error");
		return modelAndView;
	}

}
