package br.com.caelum.financas.service;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;


@Stateless
// cria e em seguida já destroy
// Singleton - Cria apenas uma
@AccessTimeout(unit = TimeUnit.SECONDS, value = 5)
@Startup
public class Agendador {

	private static int totalCriado;

	@Resource
	private TimerService timerService;
	
	public void agenda(String expressaoMinutos, String expressaoSegundos) {
		ScheduleExpression expression = new ScheduleExpression();
		expression.hour("*");
		expression.minute(expressaoMinutos);
		expression.second(expressaoSegundos);

		TimerConfig config = new TimerConfig();
		config.setInfo(expression.toString());
		config.setPersistent(false);

		this.timerService.createCalendarTimer(expression, config);

		System.out.println("Agendamento: " + expression);
	}
	
	@Timeout
	public void verificacaoPeriodicaSeHaNovasContas(Timer timer){
		System.out.println(timer.getInfo());
		//aqui poderiamos acessar o banco de dados
		//com JPA para verificar as contas periodicamente
	}
	
	@Schedule(hour="9", minute="0", second="0", dayOfWeek="Mon", persistent= false)
	public void enviaEmailCadaMinutoComInformacoesDasUltimas(){
		System.out.println("Enviando email a cada minuto");
	}

	@PostConstruct
	void posConstrucao() {
		System.out.println("Criando agendador");
		totalCriado++;
	}

	@PreDestroy
	void preDestruicao() {
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
