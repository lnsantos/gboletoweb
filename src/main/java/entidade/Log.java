package entidade;

import util.DataUtils;

public class Log {
	private String data;
	private String log;
	
	public Log(String log) {
		this.setLog(" " + log + "\n");
		this.data = DataUtils.dataAtualFormatada();
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
}
