package br.com.mws4b.logossolucoes.code.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fabiano on 18/07/2016.
 * Classe responsável pela construção do SQLite
 */
public class SQLHelper extends SQLiteOpenHelper {
        private static final String SCRIPT_CREATE_FILME = "create table IF NOT EXISTS Filme ( "
                + "imdbID text not null, "
                + "titulo text null, "
                + "ano text null, "
                + "avaliacao text null, "
                + "lancamento text null, "
                + "duracao text null, "
                + "genero text null, "
                + "diretor text null, "
                + "escritor text null, "
                + "atores text null, "
                + "resenha text null, "
                + "linguas text null, "
                + "paises text null, "
                + "premios text null, "
                + "poster text null, "
                + "metascore text null, "
                + "imdbRating text null, "
                + "imdbVotes text null); ";

        private static final String NOME_BANCO = "LOGOSSOLUCOES";
        private static final int DATABASE_VERSION = 1;

        public SQLHelper(Context ctx) {
            super(ctx, NOME_BANCO, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SCRIPT_CREATE_FILME);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            switch (oldVersion) {
                case 1:
                    db.execSQL(SCRIPT_CREATE_FILME);
            }
        }
}
