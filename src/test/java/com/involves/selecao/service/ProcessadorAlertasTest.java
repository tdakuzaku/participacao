package com.involves.selecao.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.alerta.Pesquisa;
import com.involves.selecao.alerta.Resposta;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessadorAlertasTest extends TestCase {

    @Autowired
    private ProcessadorAlertas pc;

    @Test
    public void verificaAlertaSitucaoProduto() throws Exception {
    		Pesquisa p = new Pesquisa();
    		Resposta r = new Resposta();
    		Alerta a = new Alerta();
    		r.setResposta("Produto ausente na gondola");
    		a = pc.alertaSitucaoProduto(p, r);
    		
    		Assert.assertTrue(a.getDescricao().equals("Ruptura detectada!") 
    				&& a.getFlTipo().equals(1));
    }
    
    @Test
    public void verificaAlertaPrecoProdutoAcima() throws Exception {
    		Pesquisa p = new Pesquisa();
    		Resposta r = new Resposta();
    		Alerta a = new Alerta();
    		r.setResposta("20");
    		p.setPreco_estipulado("10");
    		a = pc.alertaPrecoProduto(p, r);
    		
    		Assert.assertTrue(a.getDescricao().equals("Preço acima do estipulado!") 
    				&& a.getFlTipo().equals(2));
    }
    
    @Test
    public void verificaAlertaPrecoProdutoAbaixo() throws Exception {
    		Pesquisa p = new Pesquisa();
    		Resposta r = new Resposta();
    		Alerta a = new Alerta();
    		r.setResposta("10");
    		p.setPreco_estipulado("20");
    		a = pc.alertaPrecoProduto(p, r);
    		
    		Assert.assertTrue(a.getDescricao().equals("Preço abaixo do estipulado!") 
    				&& a.getFlTipo().equals(3));
    }
    
    @Test
    public void verificaAlertaParticipacaoProdutoAcima() throws Exception {
    		Pesquisa p = new Pesquisa();
    		Resposta r = new Resposta();
    		Alerta a = new Alerta();
    		r.setResposta("60");
    		p.setParticipacao_estipulada("50");
    		a = pc.alertaParticipacaoProduto(p, r);
    		
    		Assert.assertTrue(a.getDescricao().equals("Participação superior ao estipulado") 
    				&& a.getFlTipo().equals(4));
    }
    
    @Test
    public void verificaAlertaParticipacaoProdutoAbaixo() throws Exception {
    		Pesquisa p = new Pesquisa();
    		Resposta r = new Resposta();
    		Alerta a = new Alerta();
    		r.setResposta("10");
    		p.setParticipacao_estipulada("50");;
    		a = pc.alertaParticipacaoProduto(p, r);
    		
    		Assert.assertTrue(a.getDescricao().equals("Participação inferior ao estipulado") 
    				&& a.getFlTipo().equals(5));
    }
}