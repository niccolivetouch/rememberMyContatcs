package com.br.nicco.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.br.nicco.adapter.AdapterListaDeContatos;
import com.br.nicco.dao.BD;
import com.br.nicco.model.Contato;
import com.br.nicco.utils.PrefUtils;
import com.example.exemplo.R;


public class relembrarContatos extends Activity{
	
	private List<Contato> Lista;
	private ListView listaDeContatos;
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.lista_contatos_existentes);
		
		listaDeContatos = (ListView) findViewById(R.id.lista);
		BD bd = new BD(this);
		Lista = bd.buscar("contatos2");
		PrefUtils.setString(relembrarContatos.this, "CHAVE_CONTATO", "0");
		listaDeContatos.setAdapter(new AdapterListaDeContatos(getBaseContext(), Lista));
		bd.deletaAllContacts("contatos2");
	}
}
