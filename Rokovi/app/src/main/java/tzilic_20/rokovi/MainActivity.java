package tzilic_20.rokovi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import tzilic_20.rokovi.model.Odgovor;
import tzilic_20.rokovi.model.Rok;

public class MainActivity extends AppCompatActivity implements AdapterListe.ItemClickListener, RestSucelje{

    private AdapterListe adapter;
    private RESTTask ayncTask;
    public static final int DETALJI=1;
    public static final int OK=2;
    public static final int GRESKA=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterListe(this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        ayncTask = new RESTTask(this);
        ayncTask.execute( getString(R.string.RESTAPI) + "rokovi","GET",null);

        Button novo = findViewById(R.id.novo);
        novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noviRok();
            }
        });
    }

    private void noviRok() {

        Intent i = new Intent(this, Detalji.class);
        i.putExtra("noviRok", true);
        startActivityForResult(i,DETALJI);
    }

    @Override
    public void onItemClick(View view, int position) {
        Rok o = adapter.getItem(position);
        Intent i = new Intent(this, Detalji.class);
        i.putExtra("rok", o);
        startActivityForResult(i,DETALJI);
        //Toast.makeText(this, "Odabrali ste Osobu s šifrom " + o.getSifra(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case OK:
                ayncTask = new RESTTask(this);
                ayncTask.execute( getString(R.string.RESTAPI) + "rokovi","GET",null);
                break;
            case GRESKA:
                Toast.makeText(this, "Dogodila se pogreška, akcija nije izvedena", Toast.LENGTH_SHORT).show();
                break;
        }
        if(resultCode==OK){

        }



    }


    @Override
    public void zavrseno(Odgovor odgovor) {
        adapter.setPodaci(odgovor.getPodaci());
        adapter.notifyDataSetChanged();
    }
}
