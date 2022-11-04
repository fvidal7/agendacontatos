package br.com.cotiinformatica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RecuperarSenhaController {

	@RequestMapping(value = "/recuperarsenha")
	public ModelAndView recuperarSenha() {

		ModelAndView modelAndView = new ModelAndView("recuperarsenha");
		return modelAndView;
	}

}
