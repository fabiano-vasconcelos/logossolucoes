package br.com.mws4b.logossolucoes.telas.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.mws4b.logossolucoes.R;
import br.com.mws4b.logossolucoes.code.adapter.ListaFilmesAdapter;
import br.com.mws4b.logossolucoes.code.obj.Filme;
import br.com.mws4b.logossolucoes.code.obj.dao.FilmeDAO;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Filme> filmes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lstFilmes = (ListView) findViewById(R.id.lstFilmes);
        TextView txtSemFilmes = (TextView) findViewById(R.id.txtSemFilmes);

        filmes = new FilmeDAO(MainActivity.this).ResgataFilmes();

        if (filmes == null) {
            lstFilmes.setVisibility(View.GONE);
            txtSemFilmes.setVisibility(View.VISIBLE);
        } else {
            lstFilmes.setVisibility(View.VISIBLE);
            txtSemFilmes.setVisibility(View.GONE);
            lstFilmes.setAdapter(new ListaFilmesAdapter(MainActivity.this, filmes));
            lstFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, DetalheFilmeActivity.class);
                    intent.putExtra(Filme.class.toString(), filmes.get(position));
                    startActivity(intent);
                }
            });
        }

        findViewById(R.id.btnNovoFilme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroFilmeActivity.class);
                startActivity(intent);
            }
        });
    }
}
