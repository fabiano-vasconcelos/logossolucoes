package br.com.mws4b.logossolucoes.telas.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import br.com.mws4b.logossolucoes.R;
import br.com.mws4b.logossolucoes.code.obj.Filme;
import br.com.mws4b.logossolucoes.code.util.BitmapHelper;
import br.com.mws4b.logossolucoes.code.util.DialogHelper;
import br.com.mws4b.logossolucoes.code.util.FotoHelper;

public class DetalheFilmeActivity extends AppCompatActivity {

    private Filme filme;
    private ImageView imgFilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Filme.class.toString())) {
            filme = (Filme) getIntent().getExtras().get(Filme.class.toString());

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(filme.getTitulo());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }

            imgFilme = (ImageView) findViewById(R.id.imgFilme);

            imgFilme.setImageBitmap(BitmapFactory.
                    decodeFile(BitmapHelper.Resgata(filme.getPoster()).getAbsolutePath()));

            TextView txtTituloFilme = (TextView) findViewById(R.id.txtTituloFilme);
            txtTituloFilme.setText(filme.getTitulo());
            TextView txtAnoFilme = (TextView) findViewById(R.id.txtAnoFilme);
            txtAnoFilme.setText(filme.getAno());
            TextView txtAvaliacaoFilme = (TextView) findViewById(R.id.txtAvaliacaoFilme);
            txtAvaliacaoFilme.setText(filme.getAvaliacao());
            TextView txtLancamentoFilme = (TextView) findViewById(R.id.txtLancamentoFilme);
            txtLancamentoFilme.setText(filme.getTitulo());
            TextView txtDuracaoFilme = (TextView) findViewById(R.id.txtDuracaoFilme);
            txtDuracaoFilme.setText(filme.getDuracao());
            TextView txtGeneroFilme = (TextView) findViewById(R.id.txtGeneroFilme);
            txtGeneroFilme.setText(filme.getGenero());
            TextView txtDiretorFilme = (TextView) findViewById(R.id.txtDiretorFilme);
            txtDiretorFilme.setText(filme.getDiretor());
            TextView txtEscritorFilme = (TextView) findViewById(R.id.txtEscritorFilme);
            txtEscritorFilme.setText(filme.getEscritor());
            TextView txtAtorFilme = (TextView) findViewById(R.id.txtAtorFilme);
            txtAtorFilme.setText(filme.getAtores());
            TextView txtResenhaFilme = (TextView) findViewById(R.id.txtResenhaFilme);
            txtResenhaFilme.setText(filme.getResenha());
            TextView txtLinguaFilme = (TextView) findViewById(R.id.txtLinguaFilme);
            txtLinguaFilme.setText(filme.getLinguas());
            TextView txtPaisFilme = (TextView) findViewById(R.id.txtPaisFilme);
            txtPaisFilme.setText(filme.getPaises());
            TextView txtPremioFilme = (TextView) findViewById(R.id.txtPremioFilme);
            txtPremioFilme.setText(filme.getPremios());
            TextView txtMetascoreFilme = (TextView) findViewById(R.id.txtMetascoreFilme);
            txtMetascoreFilme.setText(filme.getMetascore());
            TextView txtIMDBRatingFilme = (TextView) findViewById(R.id.txtIMDBRatingFilme);
            txtIMDBRatingFilme.setText(filme.getImdbRating());
            TextView txtIMDBVoteFilme = (TextView) findViewById(R.id.txtIMDBVoteFilme);
            txtIMDBVoteFilme.setText(filme.getImdbVotes());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
