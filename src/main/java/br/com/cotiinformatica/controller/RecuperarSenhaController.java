package br.com.cotiinformatica.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.github.javafaker.Faker;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.messages.EmailMessage;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Controller
public class RecuperarSenhaController {

	@RequestMapping(value = "/recuperarsenha")
	public ModelAndView recuperarSenha() {

		ModelAndView modelAndView = new ModelAndView("recuperarsenha");
		return modelAndView;
	}

	@RequestMapping(value = "recuperar-senha", method = RequestMethod.POST)
	public ModelAndView recuperarSenha(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("recuperarsenha");

		try {

			// capturar o conteudo do campo email enviado para o controlador
			String email = request.getParameter("email");

			// consultar o usuário no banco de dados através do email
			UsuarioRepository usuarioRepository = new UsuarioRepository();
			Usuario usuario = usuarioRepository.findByEmail(email);

			if (usuario != null) {
				
				//gerar uma nova senha para o usuário
				Faker faker = new Faker();
				String novaSenha = faker.internet().password(8, 10);
				
				//escrevendo o email
				String assunto = "Recuperação de senha de acesso - Agenda de Contatos";
				String mensagem = "Olá, " + usuario.getNome()
									+ "\n\nUma nova senha de acesso foi gerada com sucesso."
									+ "\nUtilize a senha: " + novaSenha
									+ "\nAcesse o sistema com essa senha e altere no menu 'Minha Conta' se preferir."
									+ "\n\nAtt"
									+ "\nEquipe Agenda de Contatos";
				
				//enviando o email
				EmailMessage emailMessage = new EmailMessage();
				emailMessage.send(usuario.getEmail(), assunto, mensagem);
				
				//atualizando a senha do usuario no banco de dados
				usuarioRepository.update(usuario.getIdUsuario(), novaSenha);
				
				modelAndView.addObject("mensagem", "Nova senha gerada com sucesso. Enviamos a nova senha para o seu email!");

			} 
			else {
				modelAndView.addObject("mensagem", "Usuário não encontrado. Verifique o email informado.");
			}

		} 
		catch (Exception e) {
			modelAndView.addObject("mensagem", "Falha ao recuperar senha. Erro: " + e.getMessage());
		}

		return modelAndView;
	}

}
