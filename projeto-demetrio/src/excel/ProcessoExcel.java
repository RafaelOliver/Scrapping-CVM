package excel;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ProcessoExcel {
	
	// Cria o arquivo excel
	private WritableWorkbook pastaTrabalho = criarArquivoExcel();
	private WritableSheet planilha;
	private int linha;
	private int coluna;
	private String dado;
	
	public ProcessoExcel()  throws BiffException, IOException, WriteException {
		
	}
	
	public void rotinaExcel() {
		
		try {
			preencherPlanilha();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		
	}
	
	public void fecharPasta() {
		try {
			pastaTrabalho.write();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			pastaTrabalho.close();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void preencherPlanilha() throws RowsExceededException, WriteException {
		
		// Cria a celula
		Label celula = new Label(this.getColuna(),this.getLinha(), this.getDado());
		
		planilha.addCell(celula);

	}

	private WritableWorkbook criarArquivoExcel() throws BiffException, IOException {

		// define o arquivo
		File arquivoExcel = new File("ArquivoGerado.xls");

		// Cria a instância de configuração
		WorkbookSettings configuracaoPastaTrabalho = new WorkbookSettings();
		configuracaoPastaTrabalho.setLocale(new Locale("pt", "BR"));

		// Cria a pasta de trabalho
		WritableWorkbook pastaTrabalho = Workbook.createWorkbook(arquivoExcel, configuracaoPastaTrabalho);

		// Nomeia o arquivo excel
		pastaTrabalho.createSheet("Dados", 0);
		
		// Seta a planilha
		planilha = pastaTrabalho.getSheet(0);

		return pastaTrabalho;

	}

	private int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	private int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	private String getDado() {
		return dado;
	}

	public void setDado(String dado) {
		this.dado = dado;
	}
	
}
