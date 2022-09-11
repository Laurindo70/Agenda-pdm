package com.unigran.agenda.bancoDados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unigran.agenda.Contato;

import java.util.LinkedList;
import java.util.List;

public class ContatoDB {

    private DBHelper db;
    private SQLiteDatabase conexao;
    public ContatoDB(DBHelper db){
        this.db=db;
    }
    public void inserir(Contato contato){
        conexao = db.getWritableDatabase();//abri o bd
        ContentValues valores = new ContentValues();
        valores.put("nome",contato.getNome());
        valores.put("telefone",contato.getTelefone());
        conexao.insertOrThrow("Agenda",null,valores);
        conexao.close();
    }
    public void atualizar(Contato contato){
        conexao = db.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("nome", contato.getNome());
        valores.put("telefone",contato.getTelefone());

        conexao.update("Agenda", valores, "id=?", new String[]{Integer.toString(contato.getId())});
    }
    public void remover(int id){
        conexao=db.getWritableDatabase();
        conexao.delete("Agenda","id=?",
                new String[]{id+""});
    }
    public void lista(List dados){
        dados.clear();
        conexao=db.getReadableDatabase();
        String names[]={"id","nome","telefone"};
        Cursor query = conexao.query("Agenda", names,
                null, null, null,
                null, "nome");
        while (query.moveToNext()){
            Contato contato = new Contato();
            contato.setId(Integer.parseInt(
                    query.getString(0)));
            contato.setNome(
                    query.getString(1));
            contato.setTelefone(
                    query.getString(2));
            dados.add(contato);
        }
        conexao.close();
    }

}
