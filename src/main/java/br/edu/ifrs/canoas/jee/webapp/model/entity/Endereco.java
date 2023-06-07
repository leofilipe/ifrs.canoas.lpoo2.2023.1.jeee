package br.edu.ifrs.canoas.jee.webapp.model.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Data
public class Endereco extends BaseEntity<Long> implements Serializable {

	
	private static final long serialVersionUID = -7029493995060686692L;
	
	private String logradouro;
	private String cep;
	private String cidade;
	private String estado;
	
	private int numero;
	

   
}
