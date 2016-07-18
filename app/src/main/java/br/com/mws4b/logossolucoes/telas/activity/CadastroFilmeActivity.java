package br.com.mws4b.logossolucoes.telas.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import br.com.mws4b.logossolucoes.R;
import br.com.mws4b.logossolucoes.code.backend.WebService;
import br.com.mws4b.logossolucoes.code.obj.Filme;
import br.com.mws4b.logossolucoes.code.obj.dao.FilmeDAO;
import br.com.mws4b.logossolucoes.code.util.BitmapHelper;
import br.com.mws4b.logossolucoes.code.util.DialogHelper;
import br.com.mws4b.logossolucoes.code.util.FotoHelper;

public class CadastroFilmeActivity extends AppCompatActivity {

    private EditText edtNomeFilme;
    private ListView lstFilmes;
    private ProgressBar loading;
    private String strUltimoTermoBuscado;
    private int pagina = 1;
    private int mLastFirstVisibleItem;
    private ArrayList<Filme> filmesResultado;
    private Filme filmeSelecionado;
    private ArrayAdapter<Filme> adapter;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_filme);

        // Adiciona função de voltar na action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // Resgata elementos de tela
        edtNomeFilme = (EditText) findViewById(R.id.edtNomeFilme);
        lstFilmes = (ListView) findViewById(R.id.lstFilmes);
        loading = (ProgressBar) findViewById(R.id.loading);

        // Lida com a seleção do filme pelo usuario
        lstFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                filmeSelecionado = filmesResultado.get(position);
                edtNomeFilme.setText(filmeSelecionado.getTitulo());
                strUltimoTermoBuscado = filmeSelecionado.getTitulo();
            }
        });

        // Lida com o scroll da lista de filmes
        lstFilmes.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (view.getId() == R.id.lstFilmes) {
                    final int currentFirstVisibleItem = lstFilmes.getFirstVisiblePosition();
                    // Caso o scroll seja pra baixo, resgata nova página de filmes no servidor.
                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                        pagina++;
                        loading.setVisibility(View.VISIBLE);
                        // Definindo o callback da chamada
                        WebService.WebServiceResult webServiceResult = new WebService.WebServiceResult() {
                            @Override
                            public void onResult(JSONObject jsonObject) {
                                try {
                                    final String erroJson = (jsonObject.has("erro")) ? jsonObject.getString("erro") : "";
                                    if (!erroJson.isEmpty()) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), erroJson);
                                                loading.setVisibility(View.GONE);
                                            }
                                        });
                                    } else {
                                        if (jsonObject.getString("Response").equals("True")) {
                                            JSONArray filmes = jsonObject.getJSONArray("Search");
                                            if (filmes instanceof JSONArray && filmes.length() > 0) {
                                                for (int i = 0; i < filmes.length(); i++) {
                                                    JSONObject filme = (JSONObject) filmes.get(i);
                                                    filmesResultado.add(new Filme(filme.getString("imdbID"), filme.getString("Title")));
                                                }
                                            }
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    adapter.notifyDataSetChanged();
                                                    loading.setVisibility(View.GONE);
                                                }
                                            });
                                        } else
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), getResources().getString(R.string.erro_sem_resultados));
                                                    loading.setVisibility(View.GONE);
                                                }
                                            });
                                    }
                                } catch (JSONException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), getResources().getString(R.string.erro_mensagem_generica));
                                            loading.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            }
                        };

                        // Chamada ao servidor
                        new WebService(CadastroFilmeActivity.this, webServiceResult).resgataFilmePesquisa(strUltimoTermoBuscado, pagina);
                    }
                    mLastFirstVisibleItem = currentFirstVisibleItem;
                }
            }
        });

        edtNomeFilme.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    loading.setVisibility(View.VISIBLE);
                    lstFilmes.setVisibility(View.GONE);

                    if (!edtNomeFilme.getText().toString().equals(strUltimoTermoBuscado)) {
                        // Configura variável locais
                        pagina = 1;
                        strUltimoTermoBuscado = edtNomeFilme.getText().toString();
                        // Configura callback da chamada à API
                        WebService.WebServiceResult webServiceResult = new WebService.WebServiceResult() {
                            @Override
                            public void onResult(JSONObject jsonObject) {
                                try {
                                    final String erroJson = (jsonObject.has("erro")) ? jsonObject.getString("erro") : "";
                                    if (!erroJson.isEmpty()) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), erroJson);
                                                loading.setVisibility(View.GONE);
                                            }
                                        });
                                    } else {
                                        if (jsonObject.getString("Response").equals("True")) {
                                            JSONArray filmes = jsonObject.getJSONArray("Search");
                                            if (filmes instanceof JSONArray && filmes.length() > 0) {
                                                filmesResultado = new ArrayList<>();
                                                for (int i = 0; i < filmes.length(); i++) {
                                                    JSONObject filme = (JSONObject) filmes.get(i);
                                                    filmesResultado.add(new Filme(filme.getString("imdbID"), filme.getString("Title")));
                                                }
                                            }

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    adapter = new ArrayAdapter<>(CadastroFilmeActivity.this, android.R.layout.simple_dropdown_item_1line, filmesResultado);
                                                    lstFilmes.setAdapter(adapter);
                                                    loading.setVisibility(View.GONE);
                                                    lstFilmes.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        } else
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), getResources().getString(R.string.erro_sem_resultados));
                                                    loading.setVisibility(View.GONE);
                                                }
                                            });
                                    }
                                } catch (JSONException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), getResources().getString(R.string.erro_mensagem_generica));
                                            loading.setVisibility(View.GONE);
                                        }
                                    });
                                }
                            }
                        };
                        // Chama à API
                        new WebService(CadastroFilmeActivity.this, webServiceResult).resgataFilmePesquisa(strUltimoTermoBuscado, pagina);
                    }
                }
                return false;
            }
        });

        findViewById(R.id.btnCadastrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verifica se o usuario selecionou algum filme
                if (filmeSelecionado == null) {
                    DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), getResources().getString(R.string.erro_filme_nao_selecionado));
                } else {
                    // Configura callback da api
                    pd = ProgressDialog.show(CadastroFilmeActivity.this, getResources().getString(R.string.aguarde_titulo), getResources().getString(R.string.aguarde_msg));
                    WebService.WebServiceResult webServiceResult = new WebService.WebServiceResult() {
                        @Override
                        public void onResult(JSONObject jsonObject) {
                            try {
                                final String erroJson = (jsonObject.has("erro")) ? jsonObject.getString("erro") : "";
                                if (!erroJson.isEmpty()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), erroJson);
                                            pd.dismiss();
                                        }
                                    });
                                } else {
                                    if (jsonObject.getString("Response").equals("True")) {
                                        final String foto = String.format("%s.jpg", UUID.randomUUID().toString());
                                        //Salva dados no celular
                                        new FilmeDAO(CadastroFilmeActivity.this).SaveUpdate(new Filme(
                                                jsonObject.getString("imdbID"),
                                                jsonObject.getString("Title"),
                                                jsonObject.getString("Year"),
                                                jsonObject.getString("Rated"),
                                                jsonObject.getString("Released"),
                                                jsonObject.getString("Runtime"),
                                                jsonObject.getString("Genre"),
                                                jsonObject.getString("Director"),
                                                jsonObject.getString("Writer"),
                                                jsonObject.getString("Actors"),
                                                jsonObject.getString("Plot"),
                                                jsonObject.getString("Language"),
                                                jsonObject.getString("Country"),
                                                jsonObject.getString("Awards"),
                                                foto,
                                                jsonObject.getString("Metascore"),
                                                jsonObject.getString("imdbRating"),
                                                jsonObject.getString("imdbVotes")
                                        ));
                                        // Callback do download da foto
                                        FotoHelper.FotoHelperResult fotoHelperResult = new FotoHelper.FotoHelperResult() {
                                            @Override
                                            public void onResult(String result) {
                                                if (result.equals(getResources().getString(R.string.erro_mensagem_internet)))
                                                    DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), getResources().getString(R.string.erro_mensagem_internet));
                                                else {
                                                    // Grava foto no sd card e vai pra lista de filmes
                                                    final byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            BitmapHelper.Salva(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length),
                                                                    foto,
                                                                    getContentResolver());
                                                            pd.dismiss();
                                                            startActivity(new Intent(CadastroFilmeActivity.this, MainActivity.class));
                                                            finish();
                                                        }
                                                    });
                                                }
                                            }
                                        };

                                        // Download da foto
                                        new FotoHelper(CadastroFilmeActivity.this, fotoHelperResult).resgataFoto(jsonObject.getString("Poster"));
                                    } else
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), getResources().getString(R.string.erro_sem_resultados));
                                                pd.dismiss();
                                            }
                                        });
                                }
                            } catch (JSONException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        DialogHelper.showMessage(CadastroFilmeActivity.this, getResources().getString(R.string.erro_titulo), getResources().getString(R.string.erro_mensagem_generica));
                                        pd.dismiss();
                                    }
                                });
                            }
                        }
                    };

                    // Resgata todos os dados do filme
                    new WebService(CadastroFilmeActivity.this, webServiceResult).resgataFilme(filmeSelecionado);
                }
            }
        });
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
