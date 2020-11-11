package co.eeikee.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GenerationType;

@Entity
@Table(name = "vaga")
public class Vaga {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date abertura_vaga;
	
	@NotBlank
	@Size(max=255)
	private String codigo;
	
	@NotBlank
	@Size(max=255)
	private String descricao;
	
	@NotBlank
	@Size(max=255)
	private String projeto;
	
	@NotNull
	private int qtd_vaga;
	
	@ManyToMany
	@JoinTable(
			name= "vaga_tecnologia",
			joinColumns = @JoinColumn(name="vaga_id"),
			inverseJoinColumns = @JoinColumn(name="tecnologias_id"))
	private List<Tecnologia> tecnologias;
	
	@OneToMany(mappedBy = "vaga")
	 private List<Funcionario> funcionarios;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	public List<Tecnologia> getTecnologias() {
		return tecnologias;
	}
	public void setTecnologias(List<Tecnologia> tecnologias) {
		this.tecnologias = tecnologias;
	}
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vaga other = (Vaga) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getAbertura_vaga() {
		return abertura_vaga;
	}
	public void setAbertura_vaga(Date abertura_vaga) {
		this.abertura_vaga = abertura_vaga;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getProjeto() {
		return projeto;
	}
	public void setProjeto(String projeto) {
		this.projeto = projeto;
	}
	public int getQtd_vaga() {
		return qtd_vaga;
	}
	public void setQtd_vaga(int qtd_vaga) {
		this.qtd_vaga = qtd_vaga;
	}
	
}
