package util;

public class Paralela {
	public void upload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		
		contentType = uploadedFile.getContentType();
		nomeArq = uploadedFile.getFileName();
		
		System.out.println(contentType);

		 File file = new File(diretorioRaiz(),uploadedFile.getFileName());
		 
		 try {
			OutputStream out = new FileOutputStream(file);
			out.write(uploadedFile.getContents());
			out.close();
			
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Arquivo enviado com sucesso!."));
			
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage("Erro ao fazer o upload.",e.toString()));
			
		}
	}
}
