package ute.fcii.dsii.permisos;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pidePermiso(View v) {
        int permissionCheck = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            trabajaConArchivo();
        } else if(permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    private void trabajaConArchivo() {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "mitexto.txt");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write("Este es mi mensaje".getBytes());
            fos.close();
            TextView tv = (TextView) findViewById(R.id.tv);
            tv.setText("Funcionó");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso concedido!!!", Toast.LENGTH_LONG).show();
                trabajaConArchivo();
            } else {
                Toast.makeText(this, "Permiso negado XXX", Toast.LENGTH_LONG).show();
                trabajaConArchivo();//Esto debe fallar y presentar errores !!!
            }
        } else {
            Toast.makeText(this, "Pedido desconocido", Toast.LENGTH_LONG).show();
        }
    }
}
