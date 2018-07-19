package com.involves.selecao;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.gateway.AlertaGateway;
import com.involves.selecao.service.BuscaAlertasService;
import com.involves.selecao.service.ProcessadorAlertas;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

	@Autowired
	private BuscaAlertasService buscaAlertasService;
	
	@Autowired
	private ProcessadorAlertas processador;
	
	@GetMapping("/alertas")
    public List<Alerta> alertas() {
		return buscaAlertasService.buscarTodos();
    }    

	@GetMapping("/tipos")
    public List<Integer> getTipos() {
		return buscaAlertasService.listaTipos();
    }
	
	@GetMapping("/pontos_de_vendas")
    public List<String> getPontosDeVendas() {
		return buscaAlertasService.listaPontosDeVendas();
    }
	
	@GetMapping("/buscar")
    public List<Alerta> buscar(@RequestParam("tipo") String tipo, @RequestParam("pontoDeVenda") String pontoDeVenda) {
		return buscaAlertasService.buscar(tipo, pontoDeVenda);
    }
	
	@GetMapping("/processar")
    public void processar() {
		try {
			processador.processa();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
