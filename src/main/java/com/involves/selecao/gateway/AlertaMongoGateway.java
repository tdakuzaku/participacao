package com.involves.selecao.gateway;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.involves.selecao.alerta.Alerta;
import com.involves.selecao.gateway.mongo.MongoDbFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Component
public class AlertaMongoGateway implements AlertaGateway{
	
	@Autowired
	private MongoDbFactory mongoFactory;

	@Override
	public void salvar(Alerta alerta) {
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date today = Calendar.getInstance().getTime();        
		String data = df.format(today);	
		
		MongoDatabase database = mongoFactory.getDb();
		MongoCollection<Document> collection = database.getCollection("Alertas");
		Document doc = new Document("ponto_de_venda", alerta.getPontoDeVenda())
                .append("descricao", alerta.getDescricao())
                .append("tipo", alerta.getFlTipo())
                .append("margem", alerta.getMargem())
                .append("categoria", alerta.getCategoria())
                .append("produto", alerta.getProduto())
				.append("data", data);
		collection.insertOne(doc);
	}

	@Override
	public List<Alerta> buscarTodos() {
		MongoDatabase database = mongoFactory.getDb();
		MongoCollection<Document> collection = database.getCollection("Alertas");
		FindIterable<Document> db = collection.find();
		List<Alerta> alertas = new ArrayList<>();
		for (Document document : db) {
			Alerta alerta = new Alerta();
			alerta.setDescricao(document.getString("descricao"));
			alerta.setFlTipo(document.getInteger("tipo"));
			alerta.setMargem(document.getInteger("margem"));
			alerta.setPontoDeVenda(document.getString("ponto_de_venda"));
			alerta.setCategoria(document.getString("categoria"));
			alerta.setProduto(document.getString("produto"));
			alerta.setData(document.getString("data"));
			alertas.add(alerta);
		}
		return alertas;
	}
	
	@Override
	public List<Alerta> buscar(String tipo, String pontoDeVenda) {
		
		BasicDBObject query = new BasicDBObject();
		if (tipo != "") {
			query.put("tipo", Integer.parseInt(tipo));	
		}
		if(pontoDeVenda != "") {
			query.put("ponto_de_venda", pontoDeVenda);			
		}
		
		MongoDatabase database = mongoFactory.getDb();
		MongoCollection<Document> collection = database.getCollection("Alertas");
		FindIterable<Document> db = collection.find(query);
		List<Alerta> alertas = new ArrayList<>();
		for (Document document : db) {
			Alerta alerta = new Alerta();
			alerta.setDescricao(document.getString("descricao"));
			alerta.setFlTipo(document.getInteger("tipo"));
			alerta.setMargem(document.getInteger("margem"));
			alerta.setPontoDeVenda(document.getString("ponto_de_venda"));
			alerta.setCategoria(document.getString("categoria"));
			alerta.setProduto(document.getString("produto"));
			alerta.setData(document.getString("data"));
			alertas.add(alerta);
		}
		return alertas;
	}
	
	@Override
	public List<Integer> listaTipos() {
		MongoDatabase database = mongoFactory.getDb();
		MongoCollection<Document> collection = database.getCollection("Alertas");
		MongoCursor<Integer> db = collection.distinct("tipo", Integer.class).iterator();
		
		List<Integer> tipos = new ArrayList<>();
	    while(db.hasNext()) {
			tipos.add(db.next());
		}
		return tipos;
	}
	
	@Override
	public List<String> listaPontosDeVendas() {
		MongoDatabase database = mongoFactory.getDb();
		MongoCollection<Document> collection = database.getCollection("Alertas");
		MongoCursor<String> db = collection.distinct("ponto_de_venda", String.class).iterator();
		List<String> pdvs = new ArrayList<>();
	    while(db.hasNext()) {
	    		pdvs.add(db.next());
		}
		return pdvs;
	}
}
