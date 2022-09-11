package com.unigran.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.unigran.agenda.bancoDados.ContatoDB;
import com.unigran.agenda.bancoDados.DBHelper;

public class TelaEditar extends AppCompatActivity {

    EditText nome_contato_editar;
    EditText numero_contato_editar;
    DBHelper db;
    ContatoDB contatoDB;
    int idContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_editar);

        Intent it = getIntent();
        idContato = it.getIntExtra("id_contato", 0);

        db = new DBHelper(this);

        nome_contato_editar = findViewById(R.id.nome_contato_editar);
        numero_contato_editar = findViewById(R.id.numero_contato_editar);
    }

    public void voltar(View view){

        Intent home = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(home);

    }

    public void editar(View view){

        new AlertDialog.Builder(view.getContext())
                .setMessage("Deseja realmente editar esse contato ?")
                .setPositiveButton("Confirmar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,
                                                int i) {

                                Contato contato = new Contato();
                                contato.setTelefone(numero_contato_editar.getText().toString());
                                contato.setNome(nome_contato_editar.getText().toString());
                                contato.setId(idContato);

                                contatoDB.atualizar(contato);

                            }
                        })
                .setNegativeButton("cancelar",null)
                .create().show();

    }
}