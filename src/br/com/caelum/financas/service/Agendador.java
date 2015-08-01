package br.com.caelum.financas.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.AccessTimeout;
import javax.ejb.Stateless;

@Stateless // cria e em seguida já destroy
// Singleton - Cria apenas uma
@AccessTimeout(unit = TimeUnit.SECONDS, value = 5)
public class Agendador {

	private static int totalCriado;

	@PostConstruct
	void posConstrucao() {
		System.out.println("Criando agendador");
		totalCriado++;
	}
	
	@PreDestroy
	void preDestruicao(){
		System.out.println("Destruindo agendador");
	}

	public void executa() {
		System.out.printf("%d instancias criadas %n", totalCriado);

		// simulando demora de 4s na execucao
		try {
			System.out.printf("Executando %s %n", this);
			Thread.sleep(4000);
		} catch (InterruptedException e) {
		}
	}

}
