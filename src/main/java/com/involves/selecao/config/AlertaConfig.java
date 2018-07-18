package com.involves.selecao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlertaConfig {
	
	@Bean(name="url_pesquisas")
	public String getUrlPesquisas(){
		return "https://selecao-involves.agilepromoter.com/pesquisas";
	}
}
