package service;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager=true)
@ApplicationScoped
public class BoletoService {
	
	// A verificacao sera executada a cada 24 horas
	// 86400000ms = 24h
	private final long PERIODO = 86400000;
	
	// A verificacao sera executada as 12:00h
	private final int HORARIO_DE_VERIFICACAO = 12;
	
	private Timer timer = new Timer();
	
	public BoletoService() {
		VerificadorValidade vv = new VerificadorValidade();
		
		//TODO buscar a data da ultima verificacao no banco
		Calendar ultimaVerificacao = Calendar.getInstance();
		ultimaVerificacao.set(Calendar.DAY_OF_MONTH, 3);
		ultimaVerificacao.set(Calendar.HOUR_OF_DAY, 17);
		ultimaVerificacao.set(Calendar.MINUTE, 0);
		
		// Diferenca de tempo entre a data atual e a data da ultima verificacao
		long diferencaTempo = Calendar.getInstance().getTimeInMillis() - ultimaVerificacao.getTimeInMillis();
		
		// Executa a verificacao uma vez caso tenha passado mais de 24h desde a ultima verificacao
		if (diferencaTempo > PERIODO) {
			timer.schedule(vv, 0);
		}
		
		// Configura a data da proxima verificacao para o dia seguinte no horario programado
		Calendar proximaVerificacao = Calendar.getInstance();
		proximaVerificacao.add(Calendar.DAY_OF_MONTH, 1);
		proximaVerificacao.set(Calendar.HOUR_OF_DAY, HORARIO_DE_VERIFICACAO);
		
		// Agenda as verificacoes com um periodo de 24h entre elas
		timer.scheduleAtFixedRate(new VerificadorValidade(), proximaVerificacao.getTime(), PERIODO);
	}
	
	private class VerificadorValidade extends TimerTask {
		// Metodo que sera executado a cada 24h
		@Override
		public void run() {
			// TODO Implementar metodo que verifica a validade
			System.out.println("Verificando boletos proximos da data de validade...");
		} 
	}
}
