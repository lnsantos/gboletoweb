package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.mail.EmailException;

import DAO.BoletoDAO;
import DAO.UsuarioDAO;
import email.EmailCommons;
import entidade.Boleto;
import entidade.Log;
import entidade.Usuario;
import entidade.UsuarioCodigo;
import util.ArquivoUtil;
import util.GeradorUtil;

@ManagedBean(eager=true)
@ApplicationScoped
public class BoletoService {
	
	BoletoDAO bDao = new BoletoDAO();
	UsuarioDAO uDao = new UsuarioDAO();
	List<Boleto> boletos = new ArrayList<Boleto>();
	// A verificacao sera executada a cada 24 horas
	// 86400000ms = 24h
	// private final long PERIODO = 86400000;
	private final long PERIODO = 1000;
	
	// A verificacao sera executada as 12:00h
	private final int HORARIO_DE_VERIFICACAO = 12;

	private Timer timer = new Timer();
	
	public BoletoService() throws IOException, ParseException {
		VerificadorValidade vv = new VerificadorValidade();
		Calendar ultimaVerificacao = getDataUltimaVerificacao();
		if (ultimaVerificacao != null) {	
			// Diferenca de tempo entre a data atual e a data da ultima verificacao
			long diferencaTempo = Calendar.getInstance().getTimeInMillis() - ultimaVerificacao.getTimeInMillis();
			
			// Executa a verificacao uma vez caso tenha passado mais de 24h desde a ultima verificacao
			if (diferencaTempo > PERIODO) {
				timer.schedule(vv, 0);
			}
		} else {
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
			Log log = new Log("Verificacao de boletos");
			Set<Usuario> usuarios = null;
			
			if(usuarios == null) {
				System.out.println("Recuperando boletos, mais seus usuários!");
				usuarios = bDao.todoBoletosPendenteVerificandoStatu();
			}
			// Set<Mail> emails = bV.listSendEmail();
			for (Usuario u : usuarios) {
				EmailCommons emailCommons = new EmailCommons();
				try {
					emailCommons.enviaEmail(u);
				} catch (EmailException e) {
					e.printStackTrace();
				}
			}
			ArquivoUtil.gravarLog(log);
			System.out.println("Verificação concluída...");
		}
	}

	public static String verificaVencimento(Date dataVencimento) {
		Calendar dataAtual = Calendar.getInstance();

		long diferencaEntreData = dataVencimento.getTime() - dataAtual.getTime().getTime();
		System.out.println("DIA HOJE : " + dataAtual.getTime().getTime() + " ||| VENCIMENTO : "
				+ dataVencimento.getTime() + " ||| DIFERENCA : " + diferencaEntreData);

		if (diferencaEntreData < 0) {
			System.out.println("Atrasado");
		} else if (diferencaEntreData > 86400000) {
			System.out.println("Boleto Pendente no sistema");
		} else if ((diferencaEntreData) < 86400000) {
			System.out.println("Falta 1 Dia");
		} else {
			System.out.println("Atrasado");
		}
		return "";
	}

	public Calendar getDataUltimaVerificacao() throws IOException, ParseException {
		File diretorio = ArquivoUtil.diretorioRaiz("logs");
		Path arquivoLog = ArquivoUtil.abrirArquivo(diretorio.getPath() + "/logs.txt");
		BufferedReader reader = Files.newBufferedReader(arquivoLog);
		String linha;
		String ultimaVerificacaoLog = null;
		while ((linha = reader.readLine()) != null) {
			if (linha.contains("Verificacao de boletos"))
				ultimaVerificacaoLog = linha;
		}

		if (ultimaVerificacaoLog != null) {
			Calendar ultimaVerificacao = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			ultimaVerificacao.setTime(sdf.parse(ultimaVerificacaoLog.substring(0, 19)));
			return ultimaVerificacao;
		}
		return null;
	}
}
