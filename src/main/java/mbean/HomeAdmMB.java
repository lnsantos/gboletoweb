package mbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import DAO.BoletoDAO;
import DAO.UploadDAO;
import DAO.UsuarioDAO;
import entidade.Boleto;
import entidade.Upload;
import entidade.Usuario;
import service.UploadService;

@ManagedBean(name = "home")
@RequestScoped
public class HomeAdmMB extends UploadService {
	private static final long serialVersionUID = 1L;
	BoletoDAO bDao;
	UsuarioDAO uDAO;
	UploadDAO uPDAO;

	Boleto boleto_inserir;
	Usuario temporario;
	Usuario usuarioLogado;
	FacesContext context;
	LoginMBean informa;

	UploadedFile arquivoFileUpload;

	Upload arquivo;
	Integer codigoUsuarioFRONT;

	public HomeAdmMB() {
		bDao = new BoletoDAO();
		boleto_inserir = new Boleto();
		context = FacesContext.getCurrentInstance();
		System.out.println("Construtor : " + boleto_inserir.getId_usuario());
		codigoUsuarioFRONT = 0;
		arquivo = new Upload();
		uDAO = new UsuarioDAO();
		uPDAO = new UploadDAO();
		temporario = new Usuario();
	}

	public void novoBoleto() {
		if (bDao.inserirBoleto(boleto_inserir, codigoUsuarioFRONT)) {
			System.out.println("Boleto inserido com sucesso!");
			if (upload(arquivoFileUpload, String.valueOf(codigoUsuarioFRONT), bDao.busca_Id_Boleto(boleto_inserir))) {
				System.out.println("PDF Inserido com sucesso!");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "PDF + Boleto inserido com sucesso!", ""));

			} else {
				System.out.println("PDF Não inserido");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "PDF Não inserido", ""));
			}
		} else {
			System.out.println("Boleto + PDF Não inserido");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Boleto + PDF Não inserido", ""));
		}
		/*
		 * if (boleto_inserir != null) { System.out.println("LOGADO NO SISTEMA : " +
		 * codigoUsuarioFRONT); if (bDao.inserirBoleto(boleto_inserir,
		 * codigoUsuarioFRONT)) { context.addMessage(null, new
		 * FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", boleto_inserir.getItem()
		 * + " Inserido!")); } else { context.addMessage(null, new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", boleto_inserir.getItem() +
		 * " Não foi inserido!")); } } else { context.addMessage(null, new
		 * FacesMessage(FacesMessage.SEVERITY_ERROR,
		 * "Informações do boleto estão nulas", boleto_inserir.getItem() +
		 * " Não foi inserido!")); }
		 */
	}

	/*
	 * public void upload(UploadedFile arquivoFileUpload) {
	 * 
	 * // Pega as informações do arquivo File arquivo =
	 * escreverByte(arquivoFileUpload.getFileName(),
	 * arquivoFileUpload.getContents());
	 * 
	 * System.out.println("PDF Boleto inseido com sucesso!");
	 * 
	 * }
	 */
	public File escreverByte(String nome, byte[] contents) throws IOException {
		File file = new File(diretorioUsuario(), nome);
		OutputStream saida = new FileOutputStream(file);
		// Grava od bytes
		saida.write(contents);
		saida.close();

		return file;
	}

	public File diretorioUsuario() {
		temporario = uDAO.buscaUsuarioID(codigoUsuarioFRONT);
		if (temporario != null) {
			File dir = new File(diretorioPrincipal(), temporario.getUsuario());

			// Se o diretorio não existir
			if (!dir.exists()) {
				// Cria o diretório do arquivo
				dir.mkdirs();
			}
			System.out.println("diretorioArquivo " + dir);
			return dir;
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, codigoUsuarioFRONT + "Não encontrado", ""));
		return null;
	}

	public File diretorioPrincipal() {
		File dir = new File(System.getProperty("java.io.tmpdir"), "ArquivosBoleto");
		// Se o diretorio não existir
		if (!dir.exists()) {
			// Cria o diretório do arquivo
			dir.mkdirs();
		}
		System.out.println("diretorioRaiz " + dir);
		return dir;
	}

	public Boleto getBoleto_inserir() {
		return boleto_inserir;
	}

	public void setBoleto_inserir(Boleto boleto_inserir) {
		this.boleto_inserir = boleto_inserir;
	}

	public Integer getCodigoUsuarioFRONT() {
		return codigoUsuarioFRONT;
	}

	public void setCodigoUsuarioFRONT(Integer codigoUsuarioFRONT) {
		this.codigoUsuarioFRONT = codigoUsuarioFRONT;
	}

	public Upload getArquivo() {
		return arquivo;
	}

	public void setArquivo(Upload arquivo) {
		this.arquivo = arquivo;
	}

	public UploadedFile getArquivoFileUpload() {
		return arquivoFileUpload;
	}

	public void setArquivoFileUpload(UploadedFile arquivoFileUpload) {
		this.arquivoFileUpload = arquivoFileUpload;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
}
