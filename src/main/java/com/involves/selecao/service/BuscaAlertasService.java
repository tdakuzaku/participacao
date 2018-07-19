package com.involves.selecao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.gateway.AlertaGateway;

@Service
public class BuscaAlertasService {
	
	@Autowired
	private AlertaGateway gateway;
	
	public List<Alerta> buscarTodos() {
		return gateway.buscarTodos();
	}
	
	public List<Alerta> buscar(String tipo, String pontoDeVenda) {
		return gateway.buscar(tipo, pontoDeVenda);
	}

	public List<Integer> listaTipos() {
		return gateway.listaTipos();
	}
	
	public List<String> listaPontosDeVendas() {
		return gateway.listaPontosDeVendas();
	}
}
