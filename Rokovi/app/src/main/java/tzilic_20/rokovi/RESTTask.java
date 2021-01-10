package tzilic_20.rokovi;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import tzilic_20.rokovi.model.Odgovor;

public class RESTTask extends AsyncTask<String, Void, Odgovor> {

    private RestSucelje pozivatelj;

    public RESTTask(RestSucelje pozivatelj) {
        this.pozivatelj = pozivatelj;
    }

    protected Odgovor doInBackground(String... parametri) {
        String stringUrl = parametri[0];
        String metoda=parametri[1];
        String json=parametri[2];
        Log.d("parametri" , Arrays.toString(parametri));
        Odgovor odgovor = null;
        try {
            URL myUrl = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection)
                    myUrl.openConnection();
            connection.setRequestMethod(metoda);
            if(metoda.equals("GET")){
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.connect();
            }else {
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("charset", "utf-8");
                connection.setUseCaches(false);
                if(json!=null){
                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
                    writer.write(json);
                    writer.close();
                    wr.close();
                }
            }
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream(),"UTF-8");
            BufferedReader reader = new BufferedReader(streamReader);

            StringBuilder jsonServer = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                Log.d("Odgovor REST API --->",inputLine);
                jsonServer.append(inputLine);
            }
            reader.close();
            streamReader.close();

            odgovor = new Gson().fromJson(jsonServer.toString(), Odgovor.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return odgovor;
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    protected void onPostExecute(Odgovor odgovor) {
       pozivatelj.zavrseno(odgovor);
    }

}