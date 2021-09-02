package controllers;

import java.util.Date;
import java.util.List;

import models.Consulta;
import models.Prontuario;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import play.mvc.With;


@With(Seguranca.class)
public class Prontuarios extends Controller { 
	
	public static void form() {
		Prontuario prontuario = (Prontuario)Cache.get("prontuario");
		Cache.clear();
		render(prontuario);
	}
	
	public static void salvar(Prontuario prontuario) {
		

		validation.valid(prontuario);
		
		//verificar se tem erro, se tiver guardar com o keep
		if (validation.hasErrors()) {
			validation.keep();
			
			flash.error("Oops, erro ao salvar Prontuario!");
			
			Cache.set("prontuario", prontuario);
			
			form();
		}
		
		prontuario.save();
		
		flash.success("Prontuario salvo com sucesso!");
		form();
		
	}
	
	public static void listar() {
		
		String busca = params.get("busca");
		
		List<Prontuario> lista;
		
		if (busca == null) {
			lista = Prontuario.findAll();
		}else {
			lista = Prontuario.find("cpfPaciente", busca).fetch();
		}
		
		ValuePaginator listaPaginada = new ValuePaginator(lista);
		listaPaginada.setPageSize(5);
		
		render(listaPaginada);
	}
	
	public static void editar(long id) {
		Prontuario prontuario = Prontuario.findById(id);
		renderTemplate("Prontuarios/form.html", prontuario);
	}
	
	public static void deletar (long id) {
		Consulta consulta = Consulta.findById(id);
		consulta.delete();
		flash.success("Consulta excluida com sucesso!");
		listar();
	}
}

