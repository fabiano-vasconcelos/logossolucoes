package br.com.mws4b.logossolucoes.code.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import br.com.mws4b.logossolucoes.R;

/**
 * Created by Fabiano on 18/07/2016.
 * Classe que lida com o download de uma imagem pela url
 */
public class FotoHelper extends AsyncTask<String, String, String> {

    private FotoHelperResult fotoHelperResult;
    private Context context;

    public FotoHelper(Context context, FotoHelperResult fotoHelperResult) {
        this.fotoHelperResult = fotoHelperResult;
        this.context = context;
    }

    // Resgata uma imagem pela URL
    public void resgataFoto(final String foto) {
        execute(foto);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            if (ConnectionHelper.verificaConexao(context)) {
                URL imageURL = new URL(params[0]);
                Log.d("URL", imageURL.toString());
                Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                this.fotoHelperResult.onResult(Base64.encodeToString(byteArray, Base64.DEFAULT));
                return "";
            } else {
                this.fotoHelperResult.onResult(context.getResources().getString(R.string.erro_mensagem_internet));
                return "";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return e.getMessage();
        }
    }

    // Callback da chamada externa
    public static abstract class FotoHelperResult {
        public abstract void onResult(String result);
    }
}
