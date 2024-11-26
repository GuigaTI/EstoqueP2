package fatec.br.edu.EstoqueApp.controllers;

import fatec.br.edu.EstoqueApp.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cadastro")
public class CadastroController {
    @Autowired
    private EmailService emailService;
    @Operation(summary = "Envia um email para alguem")
    @PostMapping("/enviarEmail")
    public String enviarEmailCadastro(@RequestParam String email) {
        // Exemplo de e-mail após cadastro
        emailService.enviarEmail(email, "Bem-vindo!", "Obrigado por se cadastrar na nossa plataforma.");
        return "E-mail enviado com sucesso!";
    }
}
