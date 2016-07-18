package br.com.mws4b.logossolucoes.code.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.mws4b.logossolucoes.R;
import br.com.mws4b.logossolucoes.code.obj.Filme;
import br.com.mws4b.logossolucoes.code.util.BitmapHelper;
import br.com.mws4b.logossolucoes.code.util.DialogHelper;
import br.com.mws4b.logossolucoes.code.util.FotoHelper;

/**
 * Created by Fabiano on 18/07/2016.
 * Classe responsável por criar layout customizado da lista dos meus filmes cadastrados.
 */
public class ListaFilmesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Filme> filmeListItems;

    public ListaFilmesAdapter(Context context,
                              ArrayList<Filme> filmeListItems) {
        this.context = context;
        this.filmeListItems = filmeListItems;
    }

    @Override
    public int getCount() {
        return this.filmeListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return this.filmeListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_filme, parent,
                    false);

            //Resgata elementos do layout que representa um filme da lista de filmes

            ImageView imgFilme = (ImageView) convertView.findViewById(R.id.imgFilme);
            TextView txtTituloFilme = (TextView) convertView.findViewById(R.id.txtTituloFilme);
            TextView txtAnoFilme = (TextView) convertView.findViewById(R.id.txtAnoFilme);
            TextView txtGeneroFilme = (TextView) convertView.findViewById(R.id.txtGeneroFilme);
            TextView txtDuracaoFilme = (TextView) convertView.findViewById(R.id.txtDuracaoFilme);
            TextView txtDataLancamentoFilme = (TextView) convertView.findViewById(R.id.txtDataLancamentoFilme);


            // Resgata o filme corrente
            Filme filme = (Filme) getItem(position);

            // Caso o filme não seja nulo, preenche os dados
            if (filme != null) {
                imgFilme.setImageBitmap(BitmapFactory.
                        decodeFile(BitmapHelper.Resgata(filme.getPoster()).getAbsolutePath()));
                txtTituloFilme.setText(filme.getTitulo());
                txtAnoFilme.setText(filme.getAno());
                txtGeneroFilme.setText(filme.getGenero());
                txtDuracaoFilme.setText(filme.getDuracao());
                txtDataLancamentoFilme.setText(filme.getLancamento());
            }
        }
        return convertView;
    }
}
