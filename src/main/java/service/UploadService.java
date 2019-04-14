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

	public Retorno upload(UploadedFile uploadedFile, Integer nomePastaUsuarioDonoPDF) {
		// [1] = CAMINHO DO ARQUIVO!
		// [2] = BOOLEAN
		//rET
		System.out.println("********************************************");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Método : upload [UploadService]");
		Retorno escreverResultado = null;
		if (uploadedFile != null) {
			
			try {
				Calendar c = Calendar.getInstance();

				// Pega informação do retorno de tudo
				String nomeArquivo = uploadedFile.getFileName();
				String ext = nomeArquivo.substring(nomeArquivo.lastIndexOf("."));

				// nome do Arquivo / Bytes a serem copiados / nome da pasta
				escreverResultado = ArquivoUtil.escrever(c.getTimeInMillis() + ext, uploadedFile.getContents(),
						nomePastaUsuarioDonoPDF + "-" + c.getTimeInMillis());
				System.out.println("upload :: NOME DO ARQUIVO : " + nomePastaUsuarioDonoPDF + ext);

				// Pega caminho do arquivo
				System.out.println("upload :: caminho do arquivo " + escreverResultado.getCaminho());
				
				File arquivo = escreverResultado.getFile();
				OutputStream saida = new FileOutputStream(arquivo);

				// Salva o PDF no servidor
				saida.write(uploadedFile.getContents());
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Boleto armazenado com sucesso!"));
				saida.close();
				return escreverResultado;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("********************************************");
				System.out.println(" ");
				System.out.println(" ");
				System.out.println("Problema ao inserir PDF/BOLETO");
				System.out.println(" ");
				System.out.println(" ");
				System.out.println("********************************************");
				escreverResultado = null;
				return null;
			}
		} else
			System.out.println("upload :: Arquivo do Form esta NULL");
		escreverResultado = null;
		return null;
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
