package com.br.nicco.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.br.nicco.model.Contato;

public class BD {
	private static SQLiteDatabase bd;

	public BD(Context context) {
		BDCore auxBd = new BDCore(context);
		bd = auxBd.getWritableDatabase();
	}

	public void inserir(Contato contato, String banco) {
		ContentValues valores = new ContentValues();
		valores.put("nome", contato.getNome());
		valores.put("telefone", contato.getTelefone());
		valores.put("data", contato.getDataSalvo());

		bd.insert(banco, null, valores);
	}

	public void atualizar(Contato contato) {
		ContentValues valores = new ContentValues();
		valores.put("nome", contato.getNome());
		valores.put("telefone", contato.getTelefone());
		valores.put("data", contato.getTelefone());

		bd.update("contatos", valores, "_id = ?", new String[] { "" + contato.getId() });
	}

	public void deletar(Contato contato, String banco) {
		bd.delete(banco, "_id = " + contato.getId(), null);
	}

	public static List<Contato> buscar(String banco) {
		List<Contato> list = new ArrayList<Contato>();
		String[] colunas = new String[] { "_id", "nome", "telefone", "data" };

		Cursor cursor = bd.query(banco, colunas, null, null, null, null, "data DESC");
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();

			do {

				Contato u = new Contato();
				u.setId(cursor.getLong(0));
				u.setNome(cursor.getString(1));
				u.setTelefone(cursor.getString(2));
				u.setDataSalvo(cursor.getString(3));
				list.add(u);

			} while (cursor.moveToNext());
		}

		return (list);
	}
	
	
	public void deletaAllContacts(String banco) {
		List<Contato> list = new ArrayList<Contato>();
		String[] colunas = new String[] { "_id", "nome", "telefone", "data" };

		Cursor cursor = bd.query(banco, colunas, null, null, null, null, "data DESC");
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();

			do {

				Contato u = new Contato();
				u.setId(cursor.getLong(0));
				u.setNome(cursor.getString(1));
				u.setTelefone(cursor.getString(2));
				u.setDataSalvo(cursor.getString(3));
				deletar(u, banco);
			} while (cursor.moveToNext());
		}

		
	}
	
	
	public boolean CheckIsDataAlreadyInDBorNot(String TableName, String dbfield, String fieldValue) {
		String Query = "Select * from " + TableName + " where nome =" + "'"+dbfield+"'"+" AND telefone = " +"'"+ fieldValue+"'";
		Cursor cursor = bd.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			return false;
		}
		return true;
	}
	
}
