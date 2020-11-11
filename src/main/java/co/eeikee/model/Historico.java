package co.eeikee.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "historico")
public class Historico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nomeFuncionario;
	private String nomeCliente;
	private String codigoVaga;
	private Long vaga_id;
	private Long funcionario_id;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date inicioWA;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date InicioAlocacao;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getFuncionario_id() {
		return funcionario_id;
	}
	public void setFuncionario_id(Long funcionario_id) {
		this.funcionario_id = funcionario_id;
	}
	public Long getVaga_id() {
		return vaga_id;
	}
	public void setVaga_id(Long vaga_id) {
		this.vaga_id = vaga_id;
	}
	
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getCodigoVaga() {
		return codigoVaga;
	}
	public void setCodigoVaga(String codigoVaga) {
		this.codigoVaga = codigoVaga;
	}
	public Date getInicioWA() {
		return inicioWA;
	}
	public void setInicioWA(Date inicioWA) {
		this.inicioWA = inicioWA;
	}
	public Date getInicioAlocacao() {
		return InicioAlocacao;
	}
	public void setInicioAlocacao(Date inicioAlocacao) {
		InicioAlocacao = inicioAlocacao;
	}
	
	
}
