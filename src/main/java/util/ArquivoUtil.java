package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import entidade.Retorno;

public class ArquivoUtil {
	
	public static Retorno escrever(String nome, byte[] contents, String nomeUsuario) throws IOException {
		
		String nomePDF = nomeUsuario.substring(nomeUsuario.lastIndexOf("-"));
		String nomePasta = nomeUsuario.substring(0, nomeUsuario.lastIndexOf("-")) ;
		
		File file = new File(diretorioArquivo(nomePasta), nome);
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
		File dir = new File(System.getProperty("java.io.tmpdir"), "ArquivoBoletoUsuarios");
		
		// Se o diretorio não existir
		if(!dir.exists()){
			// Cria o diretório do arquivo
			dir.mkdirs();
		}
		System.out.println("diretorioRaiz " +  dir);
		return dir;
	}
	
}
