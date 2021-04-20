package integracao.rest.agenda;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import integracao.rest.model.ContatoModel;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Teste {

	@Autowired
	private TestRestTemplate testRestTemplate;

	private ContatoModel contato;

	@Before
	public void popularObj() {
		contato = new ContatoModel("Lucas", "21", "44451198");
	}

	@Test
	public void deveMostrarTodosContatos() {
		ResponseEntity<String> resposta = testRestTemplate.exchange("/contatos/", HttpMethod.GET, null, String.class);
		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	public void deveRealizarPostContatos() {

		
		/*
		 * Criando um objeto do tipo HttpEntity para mandar como terceiro
		 * paramentro do metodo exchange. (Mandando um obj contato via body)
		 * 
		 * */
		HttpEntity<ContatoModel> request = new HttpEntity<ContatoModel>(contato);

		ResponseEntity<ContatoModel> resposta = testRestTemplate.exchange("/contatos/inserir", HttpMethod.POST, request, ContatoModel.class);
		Assert.assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
	}
}