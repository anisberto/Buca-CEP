package com.br.nascimento.endereco.resource;

import com.br.nascimento.endereco.model.Endereco;
import java.lang.String;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/cep")
public class EnderecoResource {

  private final RestTemplate restTemplate = new RestTemplate();

  public Endereco getEndereco(String cep) {
    Endereco quote = restTemplate.getForObject(
      "https://viacep.com.br/ws/" + cep + "/json/",
      Endereco.class
    );
    return quote;
  }

  @GetMapping(value = "/{cep}")
  public ResponseEntity<Endereco> enderecoCompleto(@PathVariable String cep) {
    if (validate(cep)) {
      return ResponseEntity.ok(getEndereco(cep));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping
  public ResponseEntity<String> enderecoCompleto() {
    return ResponseEntity.ok(
      new String(
        "Para buscar um endereco inclua o seu cep neste padrão:" +
        "\nhttp://localhost:8080/cep/SEU_CEP_AQUI\nAtenção!" +
        "Informe um CEP sem caracteres especiais."
      )
    );
  }

  private boolean validate(String cep) {
    return cep.matches("\\d{5}\\d{3}");
  }
}
