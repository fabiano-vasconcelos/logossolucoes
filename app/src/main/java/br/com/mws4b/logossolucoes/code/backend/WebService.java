package br.com.mws4b.logossolucoes.code.backend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import br.com.mws4b.logossolucoes.R;
import br.com.mws4b.logossolucoes.code.obj.Filme;
import br.com.mws4b.logossolucoes.code.util.ConnectionHelper;

/**
 * Created by Fabiano on 18/07/2016.
 * Realiza todas as consultas necesárias à API do OMDB
 */
public class WebService extends AsyncTask<List<NameValuePair>, Void, Void> {

    private Context context;
    private static final int HTTP_TIMEOUT = 30 * 1000;
    private static HttpClient httpClient;
    public WebServiceResult webServiceResult;

    public WebService(Context context, WebServiceResult webServiceResult) {
        this.context = context;
        this.webServiceResult = webServiceResult;
    }

    // Resgata o filme pelo termo pesquisa bem como realiza a paginação dos dados.
    public void resgataFilmePesquisa(String termo, int pagina) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("url", String.format("%s?s=%s&type=movie&r=json&page=%s",
                context.getResources().getString(R.string.url_webservice),
                termo,
                String.valueOf(pagina))));
        this.execute(nameValuePairs);
    }

    // Resgata os dados de um filme em específico
    public void resgataFilme(Filme filme) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("url", String.format("%s?i=%s&type=movie&plot=full&r=json",
                context.getResources().getString(R.string.url_webservice),
                filme.getImdbID())));
        this.execute(nameValuePairs);
    }

    // Cria um client http padrão
    private static HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();

            final HttpParams httpParams = httpClient.getParams();

            HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(httpParams, HTTP_TIMEOUT);
        }
        return httpClient;
    }

    // Executa um get na API para retorno dos dados
    @Override
    protected Void doInBackground(List<NameValuePair>... params) {
        if (ConnectionHelper.verificaConexao(context)) {
            BufferedReader bufferedReader = null;
            try {
                HttpClient client = getHttpClient();
                HttpGet httpGet = new HttpGet(params[0].get(0).getValue());
                httpGet.addHeader("Referer", params[0].get(0).getValue());

                HttpResponse httpResponse = client.execute(httpGet);
                bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                StringBuffer stringBuffer = new StringBuffer("");
                String line = "";
                String LS = System.getProperty("line.separator");

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + LS);
                }
                bufferedReader.close();

                String resultado = stringBuffer.toString();
                Log.d("WSERVICE", resultado);
                webServiceResult.onResult(new JSONObject(resultado));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException erro) {
                        erro.printStackTrace();
                    }
                }
            }
        } else {
            try {
                webServiceResult.onResult(new JSONObject().put("erro", context.getResources().getString(R.string.erro_mensagem_internet)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Callback da chamada a API
    public static abstract class WebServiceResult {
        public abstract void onResult(JSONObject jsonObject);
    }
}
