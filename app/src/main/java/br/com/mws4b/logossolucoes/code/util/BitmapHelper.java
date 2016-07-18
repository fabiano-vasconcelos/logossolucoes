package br.com.mws4b.logossolucoes.code.util;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Fabiano on 18/07/2016.
 * Classe auxiliadora para lidar com o salvamento e consulta de imagens no sd do celular.
 */
public class BitmapHelper {

    public static void Salva(Bitmap bitmap, String filename, ContentResolver contentResolver) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/logossolucoes");
        if (!myDir.exists()) {
            if (!myDir.mkdirs()) {
                Log.d("Erro Diretorio", myDir.getAbsolutePath());
                return;
            }
        }
        File file = new File(myDir, filename);
        try {
            FileOutputStream out = new FileOutputStream(file);
            String filenameArray[] = filename.split("\\.");
            String extension = filenameArray[filenameArray.length - 1];
            if (extension.equals("png"))
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            else
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            bitmap.recycle();
            MediaStore.Images.Media.insertImage(contentResolver, file.getAbsolutePath(), file.getName(), file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File Resgata(String imagename) {

        File mediaImage = null;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/logossolucoes");
            if (!myDir.exists())
                return null;

            mediaImage = new File(myDir, imagename);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mediaImage;
    }
}
