package com.involves.selecao.gateway;

import java.util.List;

import com.involves.selecao.alerta.Alerta;

public interface AlertaGateway {
	
	void salvar(Alerta alerta);

	List<Alerta> buscarTodos();
	
	List<Integer> listaTipos();
	
	List<String> listaPontosDeVendas();

	List<Alerta> buscar(String tipo, String pontoDeVenda);
}
