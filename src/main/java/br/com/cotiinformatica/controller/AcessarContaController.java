package br.com.cotiinformatica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class AcessarContaController {

	// método para mapear qual a página que o controlador irá abrir (gerenciar) no
	// projeto
	@RequestMapping(value = "/") // página raiz do projeto
	public ModelAndView acessarConta() {

		// definir qual é a pagina que será acessada pelo controlador
		ModelAndView modelAndView = new ModelAndView("acessarconta");
		return modelAndView;
	}

}
