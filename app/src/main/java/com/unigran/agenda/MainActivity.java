package com.unigran.agenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.unigran.agenda.bancoDados.ContatoDB;
import com.unigran.agenda.bancoDados.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nome_contato;
    EditText numero_contato;
    List<Contato> dados;
    ListView listagem;
    DBHelper db;
    ContatoDB contatoDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        ArrayList<Integer> arrayIds;

        nome_contato = findViewById(R.id.nome_contato);
        numero_contato = findViewById(R.id.numero_contato);
        dados = new ArrayList<>();
        listagem = findViewById(R.id.lista_contatos);
        ArrayAdapter adapter =
                new ArrayAdapter(this,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,dados);
        listagem.setAdapter(adapter);
        contatoDB=new ContatoDB(db);
        contatoDB.lista(dados);
        acoes();
    }

    public void SalvarContato(View view){

        Contato contato = new Contato();
        contato.setNome(nome_contato.getText().toString());
        contato.setTelefone(numero_contato.getText().toString());

        contatoDB.inserir(contato);
        contatoDB.lista(dados);

        Toast.makeText(this,"Salvo com sucesso",Toast.LENGTH_SHORT).show();

    }

    public void telaEditar(int id){

        Intent edit = new Intent(getApplicationContext(), TelaEditar.class);
        edit.putExtra("id_contato", id);
        startActivity(edit);

    }

    private void acoes() {
        listagem.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView,
                                                   View view, int i, long l) {
                        new AlertDialog.Builder(view.getContext())
                                .setMessage("Deseja realmente remover")
                                .setPositiveButton("Confirmar",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface,
                                                                int i) {
                                                contatoDB.remover(dados.get(i).getId());
                                                contatoDB.lista(dados);
                                            }
                                        })
                                .setNegativeButton("cancelar",null)
                                .create().show();
                        return false;
                    }
                });

        listagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id_contato = dados.get(i).getId();
                telaEditar(id_contato);
            }
        });

        listagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}