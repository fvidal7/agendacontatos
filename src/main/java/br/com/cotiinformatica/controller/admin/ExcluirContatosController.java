package br.com.cotiinformatica.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.cotiinformatica.entities.Contato;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.ContatoRepository;

@Controller
public class ExcluirContatosController {
	
	@RequestMapping(value ="/admin/excluircontatos")
	public ModelAndView excluirContatos(HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("admin/consultarcontatos");
		
		try {
			
			//capturar o id do contato enviado na URL
			Integer idContato = Integer.parseInt(request.getParameter("id"));
			
			Usuario usuario = (Usuario) request.getSession().getAttribute("auth_usuario");
			
			//capturando o contato no banco de dados
			ContatoRepository contatoRepository = new ContatoRepository();
			Contato contato = contatoRepository.findById(idContato, usuario.getIdUsuario());
			
			if(contato != null) {
				contatoRepository.delete(contato);
				modelAndView.addObject("mensagem", "Contato " + contato.getNome() + " excluído com sucesso.");
			}
			
			//enviando uma consulta d contatos para a página
			List<Contato> contatos = contatoRepository.findByUsuario(usuario.getIdUsuario());
			modelAndView.addObject("contatos", contatos);
		 	
		}
		catch(Exception e) {
			modelAndView.addObject("mensagem", "Falha ao excluir o contato. Erro: " + e.getMessage());
		}
	
		return modelAndView;
	}
	
}
