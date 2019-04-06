package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import DAO.UploadDAO;
import entidade.Retorno;
import entidade.Upload;
import util.ArquivoUtil;

@ManagedBean
public class UploadService extends UploadDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	private StreamedContent streamedContent;

	public boolean upload(UploadedFile uploadedFile, String nomeUsuario, Upload u) {
		
		System.out.println("********************************************");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Método : upload [UploadService]");
		if (uploadedFile != null) {
			try {
				Calendar c = Calendar.getInstance();
				
				// Pega informação do retorno de tudo
				String nomeArquivo = uploadedFile.getFileName();
				String ext = nomeArquivo.substring(nomeArquivo.lastIndexOf("."));
				// nome do Arquivo / Bytes a serem copiados / nome da pasta
				Retorno escreverResultado = ArquivoUtil.escrever(c.getTimeInMillis() + ext, uploadedFile.getContents(),nomeUsuario + "-" + c.getTimeInMillis());
				System.out.println("Milles de agora! : " + c.getTimeInMillis());
				System.out.println("NOME DO ARQUIVO : " + nomeUsuario + ext);
				
				// Pega caminho do arquivo
				u.setCaminho(escreverResultado.getCaminho());
				
				// Verifica se a inserção deu certo
				if (uploadArquivo(u, u.getCaminho()).getRetorno() && u.getCaminho() != "") {
					System.out.println( u + "Adiciou no DB as tabela upload_boleto ");
					System.out.println("TRY : " + u.getCaminho());
					// Pega as informações do arquivo
					File arquivo = escreverResultado.getFile();
					OutputStream saida = new FileOutputStream(arquivo);

					// Salva o PDF no servidor
					saida.write(uploadedFile.getContents());
					saida.close();

					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("Boleto armazenado com sucesso!"));

					System.out.println(
							"[ " + u.getId_usuario() + "] " + nomeUsuario + " <= Boleto inserido com sucesso!");
					System.out.println(" ");
					System.out.println(" ");
					System.out.println("********************************************");

					return true;
				}else {
					System.out.println( u + "Não ADD no DB");
					return false;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("********************************************");
				System.out.println(" ");
				System.out.println(" ");
				System.out.println("Problema ao inserir");
				System.out.println(" ");
				System.out.println(" ");
				System.out.println("********************************************");
				return false;
			}
		}else System.out.println("uploadedFile esta NULL");
		return false;
	}

	public void download(File arquivo) {
		try {
			InputStream is = new FileInputStream(arquivo);
			streamedContent = new DefaultStreamedContent(is, Files.probeContentType(arquivo.toPath()),
					arquivo.getName());
		} catch (FileNotFoundException e) {
			// FileInputStream(arquivo)
			e.printStackTrace();
		} catch (IOException e) {
			// Files.probeContentType(arquivo.toPath())
			e.printStackTrace();
		}
	}

	public StreamedContent getStreamedContent() {
		return streamedContent;
	}
}
