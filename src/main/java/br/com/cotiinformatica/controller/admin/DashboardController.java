package br.com.cotiinformatica.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.dtos.TipoQuantidadeDto;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.ContatoRepository;

@Controller
public class DashboardController {

	// método para mapear a rota de navegação para a página /admin/dashboard
	@RequestMapping(value = "/admin/dashboard")
	public ModelAndView dashboard(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("admin/dashboard");

		try {

			Usuario usuario = (Usuario) request.getSession().getAttribute("auth_usuario");

			ContatoRepository contatoRepository = new ContatoRepository();
			List<TipoQuantidadeDto> dados = contatoRepository.countByTipo(usuario.getIdUsuario());

			modelAndView.addObject("dados", dados);

		} 
		catch (Exception e) {
			modelAndView.addObject("mensagem", "Falha ao obter dados. Erro: " + e.getMessage());
		}

		return modelAndView;
	}

}
