package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller {
	
	@Before
	public static void verificar() {
		
		if (session.contains("usuario.cpf") == false) {
			Login.form();
		}
		
	}
	
	@Before(unless={"Usuarios.listar","Application.index","Prontuarios.listar","Prontuarios.form","Consultas.listar"})
	public static void permissoes() {
		
		if (session.get("usuario.cargo").equals("A") == false) {
			if (session.get("usuario.cargo").equals("R") == false) {
			Application.index();
			}
		}

	}
}
