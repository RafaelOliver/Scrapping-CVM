package scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import excel.ProcessoExcel;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class RotinaRequests {

	private String cnpj;
	private String data;
	private String resposta;
	private String partic;
	private int contador = 1;
	private int contColuna = 1;
	private int lin = 0;
	private ProcessoExcel excel = new ProcessoExcel();

	// Contrutor
	public RotinaRequests() throws BiffException, IOException, WriteException {

	}

	public void Processo() {

		getDados();
		excel.fecharPasta();
		System.out.println("Planilha gerada");

	}

	private void getDados() {

		// Faz a requisição para a pagina dos dados
		this.navegarPegarDados();

		// Faz o scrapper
		Document documentoHTML = Jsoup.parse(this.getResposta());

		// Pega todas as linhas de dados
		Elements linhas = documentoHTML.select("#dlAplics > tbody > tr");

		for (Element linha : linhas) {

			if (contador >= 5) {

				// Cria a lista para os dados
				ArrayList<String> dados = new ArrayList<String>();

				// Pega todas as colunas de cada linha
				Elements colunas = linha.getElementsByTag("td");

				// Percorre cada coluna
				for (Element coluna : colunas) {

					// Pega o nome
					if (contColuna == 1) {
						dados.add(coluna.text());
					}

					// Pega o nome
					if (contColuna == 3) {
						dados.add(coluna.text());
					}

					// Pega o nome
					if (contColuna == 8) {
						dados.add(coluna.text());
					}

					// Pega o nome
					if (contColuna == 9) {
						dados.add(coluna.text());
					}

					// Pega o nome
					if (contColuna == 10) {
						dados.add(coluna.text());
					}

					// Pega o nome
					if (contColuna == 11) {
						dados.add(coluna.text());
					}

					contColuna++;
				}

				// Grava os dados na planilha
				for (int i = 0; i <= 5; i++) {

					excel.setLinha(lin);
					excel.setColuna(i);
					excel.setDado(dados.get(i));
					excel.rotinaExcel();
				}

				lin++;

			}

			contColuna = 1;
			contador++;
		}

	}

	// Monta a Url inicial
	private String urlMontada(int id) {
		
		if (id == 1) {

			return "https://cvmweb.cvm.gov.br/SWB/Sistemas/SCW/CPublica/CConsolFdo/ResultBuscaParticFdo.aspx?CNPJNome="
					+ this.getCnpj() + "&TpPartic=0&Adm=false&SemFrame=";

		}

		if (id == 2) {

			return "https://cvmweb.cvm.gov.br/SWB/Sistemas/SCW/CPublica/CDA/CPublicaCDA.aspx?PK_PARTIC="
					+ this.getPartic() + "&SemFrame=";

		}
		return "";
	}
// Pega os metodos para passar por parâmetros
	private List<NameValuePair> getMetodos() {
		
		// Cria a lista de metodos
		List<NameValuePair> metodos = new ArrayList<NameValuePair>();

		// Faz a navegação get
		this.navegarUrlGet();

		// Começa a fazer o scrapping
		Document documentoHTML;
		documentoHTML = Jsoup.parse(this.getResposta());

		// Pega os dados dos metodos
		Element viewstate = documentoHTML.selectFirst("#__VIEWSTATE");
		Element viewstategenerator = documentoHTML.selectFirst("#__VIEWSTATEGENERATOR");
		Element eventvalidation = documentoHTML.selectFirst("#__EVENTVALIDATION");
		Element eventargument = documentoHTML.selectFirst("#__EVENTARGUMENT");

		// Cria os cabeçalhos da solicitação
		metodos.add(new BasicNameValuePair("__VIEWSTATE", viewstate.attr("value")));
		metodos.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", viewstategenerator.attr("value")));
		metodos.add(new BasicNameValuePair("__EVENTVALIDATION", eventvalidation.attr("value")));
		metodos.add(new BasicNameValuePair("__EVENTTARGET", "ddlFundos$_ctl0$Linkbutton2"));

		try {
			metodos.add(new BasicNameValuePair("__EVENTARGUMENT", eventargument.attr("value")));
		} catch (Exception e) {
			metodos.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		}

		// Retorna os metodos
		return metodos;
	}

	private List<NameValuePair> getMetodosPegarDados() {

		// Cria a lista de metodos
		List<NameValuePair> metodos = new ArrayList<NameValuePair>();

		// Faz a navegação get
		this.navegarUrlPost(2);
		
		// Começa a fazer o scrapping
		Document documentoHTML;
		documentoHTML = Jsoup.parse(this.getResposta());

		// Pega os dados dos metodos
		Element viewstate = documentoHTML.selectFirst("#__VIEWSTATE");
		Element viewstategenerator = documentoHTML.selectFirst("#__VIEWSTATEGENERATOR");
		Element eventvalidation = documentoHTML.selectFirst("#__EVENTVALIDATION");
		Elements caixaSelecao = documentoHTML.getElementsByTag("option");
		
		String elementSelected = null;
		
		// Percorre todas as datas
		for(Element selecao: caixaSelecao) {
			
			// Compara se a data é igual a data fornecida
			if (selecao.text().equals(this.getData())) {
				
				elementSelected = selecao.attr("value");
				
			}
			
		}
		
		// Cria os cabeçalhos da solicitação
		metodos.add(new BasicNameValuePair("__EVENTTARGET", "ddCOMPTC"));
		metodos.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
		metodos.add(new BasicNameValuePair("__LASTFOCUS", ""));
		metodos.add(new BasicNameValuePair("__VIEWSTATE", viewstate.attr("value")));
		metodos.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", viewstategenerator.attr("value")));
		metodos.add(new BasicNameValuePair("__EVENTVALIDATION", eventvalidation.attr("value")));
		metodos.add(new BasicNameValuePair("ddCOMPTC", elementSelected));

		// Retorna os metodos
		return metodos;
	}

// Faz uma requisição get
	private void navegarUrlGet() {

		// Cria o metodo client e post
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpPost = new HttpGet(this.urlMontada(1));

		// Cria a variavel de retorno
		CloseableHttpResponse response = null;

		// Executa o metodo post
		try {
			response = httpclient.execute(httpPost);

		} catch (Exception e) {

		}

		// Pega o documetno
		HttpEntity responseEntity = response.getEntity();

		// Verifica se tem algo
		if (responseEntity != null) {
			try {
				this.setResposta(EntityUtils.toString(responseEntity));
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	// Faz uma requisição Post
	private void navegarUrlPost(int id) {

		// Cria o metodo client e post
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(this.urlMontada(id));
		
		// Instância o cabeçalho
		httpPost.setEntity(new UrlEncodedFormEntity(this.getMetodos()));

		// Cria a variavel de retorno
		CloseableHttpResponse response = null;

		// Executa o metodo post
		try {
			response = httpclient.execute(httpPost);

		} catch (Exception e) {

		}

		// Pega o documetno
		HttpEntity responseEntity = response.getEntity();

		// Verifica se tem algo
		if (responseEntity != null) {
			try {
				this.setResposta(EntityUtils.toString(responseEntity));
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

// Faz a requisição para pegar os dados
	private void navegarPegarDados() {

		// Cria o metodo client e post
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(this.urlMontada(2));

		// Instância o cabeçalho
		httpPost.setEntity(new UrlEncodedFormEntity(this.getMetodosPegarDados()));

		// Cria a variavel de retorno
		CloseableHttpResponse response = null;

		// Executa o metodo post
		try {
			response = httpclient.execute(httpPost);

		} catch (Exception e) {

		}

		// Pega o documetno
		HttpEntity responseEntity = response.getEntity();

		// Verifica se tem algo
		if (responseEntity != null) {
			try {
				this.setResposta(EntityUtils.toString(responseEntity));
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

// Pega o Partic

	private String getPartic() {

		// Faz a navegação Post
		navegarUrlPost(1);

		// Começa a fazer o scrapping
		Document doc;

		// Faz o scrapp
		doc = Jsoup.parse(this.getResposta());

		// Cria o elemento
		Element elemento = doc.selectFirst("#Hyperlink1");

		return elemento.attr("href").replaceAll("[^0-9]", "");

	}

	private void setPartic(String partic) {
		this.partic = partic;
	}

	private String getResposta() {
		return resposta;
	}

	private void setResposta(String resposta) {
		this.resposta = resposta;
	}

	private String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	private String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
