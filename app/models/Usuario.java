package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Transient;

import play.data.validation.Equals;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.libs.Crypto;


@Entity
public class Usuario extends Model {
	
	@Required
	public String cargo;
	
	@Required
	public String nome;
	
	@Required
	@MinSize(11)
	@MaxSize(11)
	public String cpf;
	
	@Required
	public String registro;
	
	public String bairro;
	
	@Required
	@Equals(value="confirmacaoSenha", message="senhaDiferente")
	public String senha;
	
	//Não criar novo item na tabela
	@Transient
	public String confirmacaoSenha;
	
	
	//Criptografar a senha
	public void setSenha(String s) {
		senha = Crypto.passwordHash(s);
	}
	
	//Criptografar a confirmacaoSenha
	public void setconfirmacaoSenha(String s) {
		confirmacaoSenha = Crypto.passwordHash(s);
	}
	
}
