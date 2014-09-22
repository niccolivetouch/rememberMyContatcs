package com.br.nicco.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.br.nicco.adapter.AdapterListaDeContatos;
import com.br.nicco.dao.BD;
import com.br.nicco.model.Contato;
import com.br.nicco.utils.PrefUtils;
import com.example.exemplo.R;

public class listaContatosActivity extends Activity {

	private ListView list;
	private TextView textView;
	private TextView numeroDeContatos;
	private ArrayAdapter<String> listAdapter;
	private List<Contato> listaDeContatos;
	private String phoneNo = null;
	private int tamanho;
	Contato contato;
	private String nome;
	private String dataAtual;
	private BD bd;

	private List<Contato> listaContato;

	@Override
	protected void onCreate(Bundle iclice) {
		super.onCreate(iclice);
		setContentView(R.layout.lista_contatos_existentes);
		textView = (TextView) findViewById(R.id.texto);
		numeroDeContatos = (TextView) findViewById(R.id.numeroDeContatos);
		numeroDeContatos.setVisibility(TextView.VISIBLE);
		list = (ListView) findViewById(R.id.lista);

		bd = new BD(this);
		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		GetContacts();
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();

	}
	private void GetContacts() {
		
		contato = new Contato(null, null);
		listaDeContatos = new ArrayList<Contato>();
		listaContato = new ArrayList<Contato>();
		try {
			ContentResolver cr = getContentResolver();
			Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

			if (cur.getCount() > 0) {
				while (cur.moveToNext()) {
					String contactId = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
					if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
							Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { contactId }, null);
							while (pCur.moveToNext()) {
								nome = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
								phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

								listAdapter.add("Nome do contato: " + nome + " Numero: " + phoneNo);

							}

							dataAtual = getActualDateFormatted();
							Contato contato = new Contato(nome, phoneNo);
							contato.setNome(nome);
							contato.setTelefone(phoneNo);
							contato.setDataSalvo(dataAtual);
							
							listaDeContatos.add(contato);
							phoneNo = null;
							pCur.close();
						}
					}
				}
			}
			tamanho = listaDeContatos.size();
			String valor = String.valueOf(tamanho);
			numeroDeContatos.setText(valor);
			

			int i = 0;
			try {
				for (Contato c : listaDeContatos) {
					if (bd.CheckIsDataAlreadyInDBorNot("contatos", c.getNome(), c.getTelefone())) {
						bd.inserir(c, "contatos");
						bd.inserir(c, "contatos2");
						i++;
						PrefUtils.setString(listaContatosActivity.this, "CHAVE_CONTATO", String.valueOf(i));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
		}
		List<Contato> bancoContato = bd.buscar("contatos");
		list.setAdapter(new AdapterListaDeContatos(getBaseContext(), bancoContato));

	}
	
    private String getActualDateFormatted() {
        Date newDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(newDate);
    }

    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	finish();
    }
    
    

    

}


