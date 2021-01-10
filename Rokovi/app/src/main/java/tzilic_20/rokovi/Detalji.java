package tzilic_20.rokovi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import tzilic_20.rokovi.model.Odgovor;
import tzilic_20.rokovi.model.Rok;

public class Detalji extends AppCompatActivity implements RestSucelje {

    private Rok rok;
    private EditText datum;
    private EditText kolegij;
    private EditText brojPrijavljenih;
    private Button nazad, dodaj, promjeni, obrisi;
    private RESTTask restTask;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji);
        datum = findViewById(R.id.datum);
        kolegij = findViewById(R.id.kolegij);
        brojPrijavljenih = findViewById(R.id.brojPrijavljenih);
        nazad = findViewById(R.id.nazad);
        dodaj = findViewById(R.id.dodaj);
        promjeni = findViewById(R.id.promjeni);
        obrisi = findViewById(R.id.obrisi);

        Intent i = getIntent();
        boolean noviRok = i.getBooleanExtra("noviRok",false);
        if(!noviRok){
            rok = (Rok)i.getSerializableExtra("rok");
            kolegij.setText(rok.getKolegij());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(rok.getDatum());
            datum.setText(date);
            brojPrijavljenih.setText(String.valueOf(rok.getBrojPrijavljenih()));
            dodaj.setVisibility(View.INVISIBLE);
        }else {
            promjeni.setVisibility(View.INVISIBLE);
            obrisi.setVisibility(View.INVISIBLE);
        }


        nazad.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                nazad(true);
            }
        });

        dodaj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    dodaj();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        promjeni.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    promjeni();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        obrisi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                obrisi();
            }
        });

        restTask = new RESTTask(this);

         gson = new GsonBuilder().create();


    }

    private void obrisi() {
        restTask.execute(getString(R.string.RESTAPI) + "rok/" + rok.getSifra(),"DELETE",null);
    }

    // nakon dodavanja ParseException, otići mišem na crveno gore kod promjeni/dodaj i odabrati Surround with try/catch.
    private void promjeni() throws ParseException {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(datum.getText().toString().trim());
        rok.setDatum(date);
        rok.setKolegij(kolegij.getText().toString());
        rok.setBrojPrijavljenih(Integer.parseInt(String.valueOf(brojPrijavljenih.getText())));
        restTask.execute(getString(R.string.RESTAPI) + "rok/" + rok.getSifra(),"PUT",gson.toJson(rok));
    }

    private void dodaj() throws ParseException {
        rok = new Rok();
        rok.setKolegij(kolegij.getText().toString());
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(datum.getText().toString().trim());
        rok.setDatum(date);
        rok.setBrojPrijavljenih(Integer.parseInt(String.valueOf(brojPrijavljenih.getText())));
        restTask.execute(getString(R.string.RESTAPI) + "rok" ,"POST",gson.toJson(rok));
    }

    void nazad(boolean ok){
        setResult(ok ? MainActivity.OK : MainActivity.GRESKA, null);
        finish();
    }


    @Override
    public void zavrseno(Odgovor odgovor) {
            nazad(!odgovor.getPoruka().isGreska());
    }
}
