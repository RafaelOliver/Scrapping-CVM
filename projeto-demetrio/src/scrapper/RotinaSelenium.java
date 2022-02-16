package scrapper;

import java.io.IOException;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import excel.ProcessoExcel;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class RotinaSelenium {

	// Cria a instância do navegador
	private WebDriver navegador = new ChromeDriver();
	private ProcessoExcel excel = new ProcessoExcel();
	private String cnpj;
	private String data;
	private int contador = 1;
	private int lin = 0;

	// Contrutor
	public RotinaSelenium() throws BiffException, IOException, WriteException {

	}

	// Faz a rotina do selenium
	public void processo()  {
		
		try {

		this.acessarUrl();
		this.dormir(2000);
		this.consultarCnpj();
		excel.fecharPasta();
		this.fecharNavegador();
		
		}catch (Exception e) {
			this.fecharNavegador();
		}
	}

	// Acessa a url
	private void acessarUrl() {

		navegador.get("https://cvmweb.cvm.gov.br/SWB/default.asp?sg_sistema=fundosreg");

	}
	
	// Fecha o navegador
	private void fecharNavegador() {
		try {
			navegador.close();
		} catch (Exception e) {
			
		}
		
		try {
			navegador.quit();
		} catch (Exception e) {

		}
	}
	
	// Faz o processo de consulta no Cnpj
	private void consultarCnpj() {

		// Entra no Iframe do cnpj
		navegador.switchTo().frame("Main");

		// imputa o cnpj no campo
		navegador.findElement(By.id("txtCNPJNome")).sendKeys(this.cnpj);

		// aguarda 1 segundos
		this.dormir(1000);

		// Clica para acessar os dados do cnpj
		navegador.findElement(By.id("btnContinuar")).click();

		// aguardo 1 segunfo
		this.dormir(1000);

		// Acessa o cnpj
		navegador.findElement(By.id("ddlFundos__ctl0_Linkbutton2")).click();

		// aguardo 1 segundo
		this.dormir(1000);

		// Pega o link do botão
		String link = navegador.findElement(By.xpath("//*[@id='Hyperlink1']")).getAttribute("href");

		// Acessa a url
		navegador.get(link);

		// aguardo 1 segundo
		this.dormir(1000);

		// Saio do Iframe
		navegador.switchTo().defaultContent();

		// Faz a seleção da data
		WebElement caixaSelecao = navegador.findElement(By.id("ddCOMPTC"));
		Select itemSelecao = new Select(caixaSelecao);
		itemSelecao.selectByVisibleText(this.data);

		// Faz a captura dos dados
		WebElement tabelaDados = navegador.findElement(By.id("dlAplics"));

		// Cria uma lista
		java.util.List<WebElement> tr = tabelaDados.findElements(By.cssSelector("tr"));

		// Percorre cada linha da lista
		for (WebElement linha : tr) {

			// Verifica se o contador é maior que 5
			if (contador >= 5) {

				// Cria a lista para os dados
				ArrayList<String> dados = new ArrayList<String>();

				// Adiciona os dados capturados dentro da lista
				dados.add(linha.findElement(By.xpath("td[1]")).getText());
				dados.add(linha.findElement(By.xpath("td[3]")).getText());
				dados.add(linha.findElement(By.xpath("td[8]")).getText());
				dados.add(linha.findElement(By.xpath("td[9]")).getText());
				dados.add(linha.findElement(By.xpath("td[10]")).getText());
				dados.add(linha.findElement(By.xpath("td[11]")).getText());
				
				// Grava os dados na planilha
				for (int i=0; i<= 5; i++) {
					
					excel.setLinha(lin);
					excel.setColuna(i);
					excel.setDado(dados.get(i));
					excel.rotinaExcel();
				}

				lin++;

			}

			// Faz o incremento do contador
			contador++;

		}

	}

	// Cria os setters
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setData(String data) {
		this.data = data;
	}

	// Faz uma espera
	private void dormir(int segundos) {
		try {
			Thread.sleep(segundos);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
