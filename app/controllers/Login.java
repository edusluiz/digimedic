package controllers;

import models.Usuario;
import play.libs.Crypto;
import play.mvc.Controller;

public class Login extends Controller {
	
	//metodo para criar o primeiro admin
	public static void admin() {
		
		Usuario usu = new Usuario();
		
		usu.nome = "Admin";
		usu.cpf = "admin";
		usu.senha = "admin";
		usu.cargo = "A";
		usu.save();
		
		form();
	}
	
	public static void form() {
		render();
	}
	
	
	public static void logar (String cpf, String senha, String nome) {
		
		Usuario usuario = Usuario.find("cpf = ?1 and senha = ?2", cpf, Crypto.passwordHash(senha)).first();
		
		
		if (usuario == null) {
			
			flash.error("Login ou senha inválido(a)");
			form();
		} else {
			session.put("usuario.cpf", usuario.cpf);
			session.put("usuario.cargo", usuario.cargo);
			session.put("usuario.nome", usuario.nome);
			
			Application.index();
			
		}
		
	}
	
	public static void sair() {
		session.clear();
		Login.form();
	}
	
}
