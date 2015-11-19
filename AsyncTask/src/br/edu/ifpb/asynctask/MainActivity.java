package br.edu.ifpb.asynctask;

import br.edu.ifpb.asynctask.R;
import android.app.Activity;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.edu.ifpb.asynctask.JSONParser;;

public class MainActivity extends Activity {
	TextView nome;
    TextView senha;
    Button Button;
 
    //URL to get JSON Array
    private static String url = "http://10.0.2.2/JSON/";
 
    //JSON Node Names
    private static final String TAG_USER = "user";
    static final String TAG_NAME = "nome";
 
    JSONArray user = null;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.main_activity);
        Button = (Button)findViewById(R.id.btEnviar);
        Button.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                 new JSONParse().execute();
 
            }
        });
 
    }
 
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
         private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             nome = (TextView)findViewById(R.id.campoUsuario);
             senha = (TextView)findViewById(R.id.campoSenha);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Entrando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
 
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
 
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
         @Override
         protected void onPostExecute(JSONObject json) {
             pDialog.dismiss();
             try {
                    // Getting JSON Array
                    user = json.getJSONArray(TAG_USER);
                    JSONObject c = user.getJSONObject(0);
 
                    // Storing  JSON item in a Variable
                    String name = c.getString(TAG_NAME);
                    
                    //Set JSON Data in TextView
                    nome.setText(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
         }
    }
}
