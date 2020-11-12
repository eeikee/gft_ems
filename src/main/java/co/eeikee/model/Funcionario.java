package co.eeikee.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "funcionario")
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@NotBlank(message = "O cargo é obrigatório.")
	@Size(max=255)
	protected String cargo;
	
	@NotNull(message = "A data de início do W.A. é obrigatório.")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	protected Date inicio_wa;
	
	@NotBlank(message = "A matrícula é obrigatória.")
	@Size(max=255)
	protected String matricula;
	
	@NotBlank(message = "O nome do funcionário é obrigatório.")
	@Size(max=255)
	protected String nome;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	protected Date termino_wa;

	@NotNull(message = "Selecione o local de trabalho do funcionário.")
	@ManyToOne
	@JoinColumn(name = "gft_id")
	private GFT gft;
	
	@ManyToOne
	@JoinColumn(name = "vaga_id")
	private Vaga vaga;
	
	@NotNull(message = "Selecione pelo menos 1 tecnologia")
	@ManyToMany
	@JoinTable(
			name= "funcionario_tecnologia",
			joinColumns = @JoinColumn(name="funcionarios_id"),
			inverseJoinColumns = @JoinColumn(name="tecnologias_id"))
	private List<Tecnologia> tecnologias;
	
	public GFT getGft() {
		return gft;
	}

	public void setGft(GFT gft) {
		this.gft = gft;
	}

	public Vaga getVaga() {
		return vaga;
	}

	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}
	
	public List<Tecnologia> getTecnologias() {
		return tecnologias;
	}

	public void setTecnologias(List<Tecnologia> tecnologias) {
		this.tecnologias = tecnologias;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Date getInicio_wa() {
		return inicio_wa;
	}

	public void setInicio_wa(Date inicio_wa) {
		this.inicio_wa = inicio_wa;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getTermino_wa() {
		return termino_wa;
	}

	public void setTermino_wa(Date termino_wa) {
		this.termino_wa = termino_wa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
