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
		ApplicationContext context = new AnnotationConfigApplicationContext(AlertaConfig.class);
		Request request = new Request();
		
		Gson gson = new Gson();
		String url = (String) context.getBean("url_pesquisas");
		Pesquisa[] ps = gson.fromJson(request.get(url), Pesquisa[].class);

		for (Pesquisa p : ps) {
			for (Resposta r : p.getRespostas()) {
				Alerta alerta = new Alerta();
				alerta.setPontoDeVenda(p.getPonto_de_venda());
			    alerta.setProduto(p.getProduto());
				switch (r.getPergunta()) {
					case "Qual a situação do produto?":
						if(r.getResposta().equals("Produto ausente na gondola")){
						    alerta.setDescricao("Ruptura detectada!");
						    alerta.setFlTipo(1);
						    gateway.salvar(alerta);
						}
						break;
					case "Qual o preço do produto?":
						int precoColetado = Integer.parseInt(r.getResposta());
						int precoEstipulado = Integer.parseInt(p.getPreco_estipulado());
						int margemPreco = precoEstipulado - Integer.parseInt(r.getResposta());
						alerta.setMargem(margemPreco);
						if(precoColetado > precoEstipulado){
						    alerta.setDescricao("Preço acima do estipulado!");
						    alerta.setFlTipo(2);
						} else {
						    alerta.setDescricao("Preço abaixo do estipulado!");
						    alerta.setFlTipo(3);
						}
						gateway.salvar(alerta);
						break;
					case "%Share":
						int participacaoEstipulada = Integer.parseInt(p.getParticipacao_estipulada());
						int margemParticipacao = participacaoEstipulada - Integer.parseInt(r.getResposta());
						if (margemParticipacao > 0) {
							alerta.setDescricao("Participação superior ao estipulado");
						} else {
							alerta.setDescricao("Participação inferior ao estipulado");
						}
						gateway.salvar(alerta);
						break;
					default:
						System.out.println("Alerta ainda não implementado!");
				}
			}
		}
	}
	
	public void lista() {
		List<Alerta> lista = gateway.buscarTodos();
		System.out.println(lista);
	}
}

