package mbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import DAO.BoletoDAO;
import DAO.UploadDAO;
import DAO.UsuarioDAO;
import entidade.Boleto;
import entidade.Upload;
import entidade.Usuario;
import service.UploadService;

@ManagedBean(name = "home")
@ViewScoped
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
	LoginMBean teste;
	List<Boleto> boletos;
	
	Upload resultadoBoletoSolicitado;

	UploadedFile arquivoFileUpload = null;
	Upload arquivo;
	
	@ManagedProperty(value = "#{loginMBean.usuarioLogado.codigo}")
	private Integer codigoUsuarioFRONT;

	// String idUsuario = informa.getUsuarioLogado().getCodigo().toString();
	public HomeAdmMB() {
		bDao = new BoletoDAO();
		boleto_inserir = new Boleto();
		context = FacesContext.getCurrentInstance();
		// System.out.println("Construtor : " + informa.getUsuarioLogado().getCodigo());
		arquivo = new Upload();
		uDAO = new UsuarioDAO();
		uPDAO = new UploadDAO();
		temporario = new Usuario();
		resultadoBoletoSolicitado = new Upload();
		boletos = new ArrayList<Boleto>();
	}

	@PostConstruct
	public void init() {
		boletos = bDao.listaBoletosUsuarioLogado(codigoUsuarioFRONT);
	}
	
	public void limpa() {
		boleto_inserir = null;
	}

	public void atualizarListaBoleto() {
		boletos = bDao.listaBoletosUsuarioLogado(codigoUsuarioFRONT);
	}

	public void novoBoleto() {
		if (boleto_inserir != null) {
			if (bDao.inserirBoleto(boleto_inserir, codigoUsuarioFRONT)) {
				System.out.println("Boleto inserido com sucesso!");
				boleto_inserir.setId_usuario(codigoUsuarioFRONT);
				resultadoBoletoSolicitado = bDao.busca_Id_Boleto(boleto_inserir);

				// Verifica se existe um PDF
				// Encapsulou ID_BOLETO + ID_USUARIO
				// byte x = 0;
				if (resultadoBoletoSolicitado != null && arquivoFileUpload.getSize() > 0) {
					// Pega informação do retorno de tudo
					String nomeArquivo = arquivoFileUpload.getFileName();
					String ext = nomeArquivo.substring(nomeArquivo.lastIndexOf("."));
					// Verifica se o Arquivo é o que pode ser inserido
					if (ext == ".pdf" || ext == ".png" || ext == ".jpg") {
						if (upload(arquivoFileUpload, String.valueOf(codigoUsuarioFRONT), resultadoBoletoSolicitado)) {
							System.out.println("PDF Inserido com sucesso!");
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
									FacesMessage.SEVERITY_INFO, "PDF + Boleto inserido com sucesso!", ""));
							boleto_inserir = null;
							boletos = bDao.listaBoletosUsuarioLogado(codigoUsuarioFRONT);
						} else {
							System.out.println("PDF Não inserido");
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_ERROR, "PDF Não inserido", ""));
						}
					} else {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Arquivo Inválido!", ""));
					}
				}
			} else {
				System.out.println("Boleto não encontrado + PDF Não inserido");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Boleto não encontrado + PDF Não inserido", ""));
			}
		} else {
			System.out.println("Boleto inserido não encontrado!");
		}
		boletos = bDao.listaBoletosUsuarioLogado(resultadoBoletoSolicitado.getId_usuario());
		boleto_inserir = null;
	}
	
	/*
	 * else{
		boletos = bDao.listaBoletosUsuarioLogado(codigoUsuarioFRONT.toString());
		System.out.println("Preencha as informações");
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Preencha as informações", ""));
	}

	 * */
	
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

	public List<Boleto> getBoletos() {
		return boletos;
	}

	public void setBoletos(List<Boleto> boletos) {
		this.boletos = boletos;
	}

}
