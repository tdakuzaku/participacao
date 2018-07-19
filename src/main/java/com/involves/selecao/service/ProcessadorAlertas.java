package com.involves.selecao.service;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;
import com.involves.selecao.config.AlertaConfig;
import com.involves.selecao.gateway.AlertaGateway;
import com.involves.selecao.utils.Request;

@Service
public class ProcessadorAlertas {

	@Autowired
	private AlertaGateway gateway;
	
	public void processa() throws IOException {
		ApplicationContext contexto = new AnnotationConfigApplicationContext(AlertaConfig.class);
		Request request = new Request();
		
		Gson gson = new Gson();
		String urlPesquisas = (String) contexto.getBean("url_pesquisas");
		Pesquisa[] pesquisas = gson.fromJson(request.get(urlPesquisas), Pesquisa[].class);

		for (Pesquisa pesquisa : pesquisas) {
			for (Resposta resposta : pesquisa.getRespostas()) {
				switch (resposta.getPergunta()) {
					case "Qual a situação do produto?":
						this.alertaSitucaoProduto(pesquisa, resposta);
						break;
					case "Qual o preço do produto?":
						this.alertaPrecoProduto(pesquisa, resposta);
						break;
					case "%Share":
						this.alertaParticipacaoProduto(pesquisa, resposta);
						break;
					default:
						System.out.println("Alerta ainda não implementado!");
				}
			}
		}
	}
	
	private void alertaSitucaoProduto(Pesquisa p, Resposta r) {
		Alerta a = new Alerta();
		a.setPontoDeVenda(p.getPonto_de_venda());
	    a.setProduto(p.getProduto());
		if(r.getResposta().equals("Produto ausente na gondola")){
		    a.setDescricao("Ruptura detectada!");
		    a.setFlTipo(1);
		    gateway.salvar(a);
		}
	}
	
	private void alertaPrecoProduto(Pesquisa p, Resposta r) {
		Alerta a = new Alerta();
		a.setPontoDeVenda(p.getPonto_de_venda());
	    a.setProduto(p.getProduto());
	    
		int precoColetado = Integer.parseInt(r.getResposta());
		int precoEstipulado = Integer.parseInt(p.getPreco_estipulado());
		int margemPreco = precoEstipulado - Integer.parseInt(r.getResposta());
		
		a.setMargem(margemPreco);
		
		if(precoColetado > precoEstipulado){
		    a.setDescricao("Preço acima do estipulado!");
		    a.setFlTipo(2);
		} else {
		    a.setDescricao("Preço abaixo do estipulado!");
		    a.setFlTipo(3);
		}
		gateway.salvar(a);
	}
	
	private void alertaParticipacaoProduto(Pesquisa p, Resposta r) {
		Alerta a = new Alerta();
		a.setPontoDeVenda(p.getPonto_de_venda());
	    a.setProduto(p.getProduto());
	    
		int participacaoEstipulada = Integer.parseInt(p.getParticipacao_estipulada());
		int margemParticipacao = participacaoEstipulada - Integer.parseInt(r.getResposta());
		
		a.setCategoria(p.getCategoria());
		a.setMargem(margemParticipacao);
		
		if (margemParticipacao > 0) {
			a.setFlTipo(4);
			a.setDescricao("Participação superior ao estipulado");
		} else {
			a.setFlTipo(5);
			a.setDescricao("Participação inferior ao estipulado");
		}
		gateway.salvar(a);
	}
}

