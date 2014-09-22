package com.br.nicco.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.br.nicco.utils.PrefUtils;
import com.example.exemplo.R;

public class homeActivity extends Activity {

	private Button atualizarContato;
	private Button visualizarContato;
	private Button relembrarNovosContatos;
	private TextView qtContatosNovo;

	@Override
	protected void onCreate(Bundle iclice) {
		super.onCreate(iclice);
		setContentView(R.layout.home_activity);
		atualizarContato = (Button) findViewById(R.id.atualizarContato);
		visualizarContato = (Button) findViewById(R.id.visualizarContato);
		relembrarNovosContatos = (Button) findViewById(R.id.relembrarNovosContatos);
		qtContatosNovo = (TextView) findViewById(R.id.QuantidadeDeNovosContatos);

		if (PrefUtils.getString(this, "CHAVE_CONTATO") != null) {
			qtContatosNovo.setText(PrefUtils.getString(homeActivity.this, "CHAVE_CONTATO"));
		}
		
		defineCor();
		
		atualizarContato.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), listaContatosActivity.class);
				startActivity(intent);
			}
		});

		visualizarContato.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), visualizarContato.class);
				startActivity(intent);
			}
		});

		relembrarNovosContatos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), relembrarContatos.class);
				startActivity(intent);
			}
		});

	}

	private void defineCor() {
		if(!qtContatosNovo.getText().toString().equals("0")){
			qtContatosNovo.setTextColor(getResources().getColor(R.color.OrangeRed));
		}else{
			qtContatosNovo.setTextColor(getResources().getColor(R.color.SummerSky));
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (PrefUtils.getString(this, "CHAVE_CONTATO") != null) {
			qtContatosNovo.setText(PrefUtils.getString(homeActivity.this, "CHAVE_CONTATO"));
		}
		
		defineCor();
	}
}
