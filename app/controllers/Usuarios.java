package controllers;

import java.util.Date;
import java.util.List;

import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;


@With(Seguranca.class)
public class Usuarios extends Controller { 
	
	public static void form() {
		Usuario usuario = (Usuario)Cache.get("usuario");
		Cache.clear();
		render(usuario);
	}
	
	public static void salvar(Usuario usuario, String senha) {
		

		//para mudar outra opção que n seja a senha
		if (senha.equals("") == false) {
			usuario.senha = senha;
		}
		
		validation.valid(usuario);
		
		//verificar se tem erro, se tiver guardar com o keep
		if (validation.hasErrors()) {
			validation.keep();
			
			flash.error("Oops, erro ao salvar usuário!");
			
			Cache.set("usuario", usuario);
			
			form();
		}
		
		usuario.save();
		
		flash.success("Usuário salvo com sucesso!");
		listar();
		
	}
	
	public static void listar() {
		
		String busca = params.get("busca");
		
		List<Usuario> lista;
		
		if (busca == null) {
			lista = Usuario.findAll();
		}else {
			lista = Usuario.find("byCargo", busca).fetch();
		}
		
		render(lista);
	}
	
	public static void editar(long id) {
		Usuario usuario = Usuario.findById(id);
		renderTemplate("Usuarios/form.html", usuario);
	}
	
	public static void deletar (long id) {
		Usuario usuario = Usuario.findById(id);
		usuario.delete();
		flash.success("Usuário removido com sucesso!");
		listar();
	}
}

