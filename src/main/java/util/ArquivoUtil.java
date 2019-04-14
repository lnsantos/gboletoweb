package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import entidade.Log;
import entidade.Retorno;

public class ArquivoUtil {
	
	public static Retorno escrever(String nomeArquivoCompleto, byte[] contents, String millisAtual) throws IOException {
		
		String nomePDF = millisAtual.substring(millisAtual.lastIndexOf("-"));
		String nomePasta = millisAtual.substring(0, millisAtual.lastIndexOf("-")) ;
		
		File file = new File(diretorioArquivo(nomePasta), nomeArquivoCompleto);
		OutputStream saida = new FileOutputStream(file);
		
		Retorno result = new Retorno(file,file.getPath(),true);
		System.out.println("ESCREVER CAMINHO : " + file.getPath());
		
		// Grava od bytes
		saida.write(contents);
		saida.close();
		
		return result;
	}
	
	public static File diretorioArquivo(String nomePastaUsuario){
		File dir = new File(diretorioRaiz(), nomePastaUsuario);
		
		// Se o diretorio não existir
				if(!dir.exists()){
					// Cria o diretório do arquivo
					dir.mkdirs();
				}
			System.out.println("diretorioArquivo " + dir);
			return dir;
	}
	
	public static File diretorioRaiz() {
		File dir = new File(System.getProperty("java.io.tmpdir"), "/gboletosweb/ArquivoBoletoUsuarios");
		
		// Se o diretorio não existir
		if(!dir.exists()){
			// Cria o diretório do arquivo
			dir.mkdirs();
		}
		System.out.println("diretorioRaiz " +  dir);
		return dir;
	}
	
	public static File diretorioRaiz(String pasta) {
		File dir = new File(System.getProperty("java.io.tmpdir"), "/gboletosweb/"+pasta);
		
		// Se o diretorio não existir
		if(!dir.exists()){
			// Cria o diretório do arquivo
			dir.mkdirs();
		}
		System.out.println("diretorioRaiz " +  dir);
		return dir;
	}
	
	public static Path abrirArquivo(String diretorio) throws IOException {
		Path path = Paths.get(diretorio);
		if (Files.notExists(path)) {
			Files.createDirectories(path.getParent());
			Files.createFile(path);
		}
		
		return path;
	}
	
	public static boolean escreverNoArquivo(Path path, String s) throws IOException, AccessDeniedException {
		if (Files.isWritable(path)) {
			BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
			
			writer.write(s);
			writer.close();
			return true;
		}
		
		return false;
	}
	
	public static boolean gravarLog(Log log) {
		try {
			Path path = abrirArquivo(diretorioRaiz("logs") + "/logs.txt");
			StringBuilder logString = new StringBuilder(log.getData());
			logString.append(log.getLog());
			
			escreverNoArquivo(path, logString.toString());
			
			System.out.println("Log gravado em " + path.toAbsolutePath());
			
			return true;
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		} catch ( IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
