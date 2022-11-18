package br.com.cotiinformatica.controller.admin;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.entities.Contato;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.enums.TipoContato;
import br.com.cotiinformatica.repositories.ContatoRepository;

@Controller
public class AtualizarContatosController {

	@RequestMapping(value = "/admin/atualizarcontatos")
	public ModelAndView atualizarContato(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("admin/atualizarcontatos");

		try {

			// capturando o id enviado na URL
			Integer idContato = Integer.parseInt(request.getParameter("id"));

			// obter os dados do usuário autenticado no sistema
			Usuario usuario = (Usuario) request.getSession().getAttribute("auth_usuario");

			// consultar o contato no banco de dados
			ContatoRepository contatoRepository = new ContatoRepository();
			Contato contato = contatoRepository.findById(idContato, usuario.getIdUsuario());

			if (contato != null) {
				// enviando dados do contato para a página
				modelAndView.addObject("contato", contato);
			} else {
				// redirecionar para a página de consulta
				modelAndView.setViewName("redirect:consultarcontatos");

			}

		} catch (Exception e) {
			modelAndView.addObject("mensagem", "Falha ao obter contato: " + e.getMessage());
		}

		return modelAndView;
	}
	
	@RequestMapping(value = "admin/atualizar-contato", method = RequestMethod.POST)
	public ModelAndView updateContato(HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("admin/atualizarcontatos");
		
		try {
			
			//capturar os campos do formulário
			Contato contato = new Contato();
			
			contato.setIdContato(Integer.parseInt(request.getParameter("idContato")));
			contato.setNome(request.getParameter("nome"));
			contato.setEmail(request.getParameter("email"));
			contato.setTelefone(request.getParameter("telefone"));
			contato.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("datanascimento")));

			Integer tipo = Integer.parseInt(request.getParameter("tipo"));
			switch (tipo) {
			case 1:
				contato.setTipo(TipoContato.FAMILIA); break;
			case 2:
				contato.setTipo(TipoContato.AMIGOS); break;
			case 3:
				contato.setTipo(TipoContato.TRABALHO); break;
			case 4:
				contato.setTipo(TipoContato.OUTROS); break;
			}
			
			//atualizando no banco de dados
			ContatoRepository contatoRepository = new ContatoRepository();
			contatoRepository.update(contato);
			
			modelAndView.addObject("mensagem", "Contato atualizado com sucesso.");
			modelAndView.addObject(contato);
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem", "Falha ao atualizar o contato. Erro: " + e.getMessage());
		}
		
		return modelAndView;
	}
	
}
