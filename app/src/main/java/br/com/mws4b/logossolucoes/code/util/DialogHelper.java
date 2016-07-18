package br.com.mws4b.logossolucoes.code.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import br.com.mws4b.logossolucoes.R;

/**
 * Created by Fabiano on 18/07/2016.
 * Classe auliadora para popups de mensagem
 */
public class DialogHelper {
    // Mostra uma mensagem ao usuario com titulo e mensagem customizados
    public static void showMessage(final Context context, String titulo, String mensagem) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setTitle(titulo)
                .setMessage(
                        mensagem)
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.botao_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
