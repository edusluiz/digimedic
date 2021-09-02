package models;

import java.util.Date;

import javax.persistence.Entity;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;


@Entity
public class Prontuario extends Model {
	
	@Required
	public String nomePaciente;

	@Required
	@MinSize(11)
	@MaxSize(11)
	public String cpfPaciente;
	
	@Required
	public String data;
	
	@Required
	public String descricao;
	
	
}
