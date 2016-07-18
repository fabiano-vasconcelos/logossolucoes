package br.com.mws4b.logossolucoes.code.obj.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.mws4b.logossolucoes.code.obj.Filme;
import br.com.mws4b.logossolucoes.code.util.SQLHelper;

/**
 * Created by Fabiano on 18/07/2016.
 * Classe que realiza o acesso aos dados locais do filme
 */
public class FilmeDAO {
    private static final String NOME_TABELA = "Filme";

    protected SQLiteDatabase db;

    public FilmeDAO(Context ctx) {
        SQLHelper dbHelper = new SQLHelper(ctx);
        dbHelper.close();
        db = dbHelper.getWritableDatabase();
    }

    // Resgata todos os filmes cadastrados
    public ArrayList<Filme> ResgataFilmes() {
        String query = String.format("SELECT * FROM %s", NOME_TABELA);
        Cursor c = db.rawQuery(query, null);
        ArrayList<Filme> result = null;
        if (c != null && c.moveToFirst()) {
            result = new ArrayList<>();
            do {
                result.add(new Filme(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8),
                        c.getString(9),
                        c.getString(10),
                        c.getString(11),
                        c.getString(12),
                        c.getString(13),
                        c.getString(14),
                        c.getString(15),
                        c.getString(16),
                        c.getString(17)
                ));
            } while (c.moveToNext());
            c.close();
        }
        return result;
    }

    // Resgata um determinado filme cadastrado.
    public Filme ResgataFilme(Filme filme) {
        String query = String.format("SELECT * FROM %s WHERE imdbID='%s'", NOME_TABELA, filme.getImdbID());
        Cursor c = db.rawQuery(query, null);
        Filme result = null;
        if (c != null && c.moveToFirst()) {
            result = new Filme(
                    c.getString(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8),
                    c.getString(9),
                    c.getString(10),
                    c.getString(11),
                    c.getString(12),
                    c.getString(13),
                    c.getString(14),
                    c.getString(15),
                    c.getString(16),
                    c.getString(17)
            );
            c.close();
        }
        return result;
    }

    // Salve ou altera um filme cadastrado.
    public void SaveUpdate(Filme filme) {
        ContentValues values = new ContentValues();
        values.put("imdbID", filme.getImdbID());
        values.put("titulo", filme.getTitulo());
        values.put("ano", filme.getAno());
        values.put("avaliacao", filme.getAvaliacao());
        values.put("lancamento", filme.getLancamento());
        values.put("duracao", filme.getDuracao());
        values.put("genero", filme.getGenero());
        values.put("diretor", filme.getDiretor());
        values.put("escritor", filme.getEscritor());
        values.put("atores", filme.getAtores());
        values.put("resenha", filme.getResenha());
        values.put("linguas", filme.getLinguas());
        values.put("paises", filme.getPaises());
        values.put("premios", filme.getPremios());
        values.put("poster", filme.getPoster());
        values.put("metascore", filme.getMetascore());
        values.put("imdbRating", filme.getImdbRating());
        values.put("imdbVotes", filme.getImdbVotes());

        if (this.ResgataFilme(filme) == null)
            db.insert(NOME_TABELA, "", values);
        else
            db.update(NOME_TABELA, values, "imdbID=" + filme.getImdbID(), null);
    }
}
