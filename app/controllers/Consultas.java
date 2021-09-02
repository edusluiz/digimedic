package controllers;

import java.util.Date;
import java.util.List;

import models.Consulta;
import models.Usuario;
import play.cache.Cache;
import play.data.validation.Valid;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import play.mvc.With;


@With(Seguranca.class)
public class Consultas extends Controller { 
	
	public static void form() {
		Consulta consulta = (Consulta)Cache.get("consulta");
		Cache.clear();
		render(consulta);
	}
	
	public static void salvar(Consulta consulta) {
		

		validation.valid(consulta);
		
		//verificar se tem erro, se tiver guardar com o keep
		if (validation.hasErrors()) {
			validation.keep();
			
			flash.error("Oops, erro ao salvar consulta!");
			
			Cache.set("consulta", consulta);
			
			form();
		}
		
		consulta.save();
		
		flash.success("Consulta salvo com sucesso!");
		form();
		
	}
	
	public static void listar() {
		
		String busca = params.get("busca");
		
		List<Consulta> lista;
		
		if (busca == null) {
			lista = Consulta.findAll();
		}else {
			lista = Consulta.find("cpfPaciente", busca).fetch();
		}
		
		ValuePaginator listaPaginada = new ValuePaginator(lista);
		listaPaginada.setPageSize(5);
		
		render(listaPaginada);
	}
	
	public static void editar(long id) {
		Consulta consulta = Consulta.findById(id);
		renderTemplate("Consultas/form.html", consulta);
	}
	
	public static void deletar (long id) {
		Consulta consulta = Consulta.findById(id);
		consulta.delete();
		flash.success("Consulta excluida com sucesso!");
		listar();
	}
}

