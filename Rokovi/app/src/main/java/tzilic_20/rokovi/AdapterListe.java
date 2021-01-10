package tzilic_20.rokovi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tzilic_20.rokovi.model.Rok;


public class AdapterListe extends RecyclerView.Adapter<AdapterListe.Red> {

    private List<Rok> podaci;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public AdapterListe(Context context) {
        this.mInflater = LayoutInflater.from(context);
        podaci = new ArrayList<>();
    }

    @Override
    public Red onCreateViewHolder(ViewGroup roditelj, int viewType) {
        View view = mInflater.inflate(R.layout.red_liste, roditelj, false);
        return new Red(view);
    }

    @Override
    public void onBindViewHolder(Red red, int position) {
        Rok o = podaci.get(position);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(o.getDatum());
        red.datum.setText(strDate);
        red.kolegij.setText(o.getKolegij());
        red.brojPrijavljenih.setText(String.valueOf(o.getBrojPrijavljenih()));

    }

    @Override
    public int getItemCount() {
        return podaci==null ? 0 : podaci.size();
    }


    public class Red extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView datum;
        private TextView kolegij;
        private TextView brojPrijavljenih;

        Red(View itemView) {
            super(itemView);
            datum = itemView.findViewById(R.id.datum);
            kolegij = itemView.findViewById(R.id.kolegij);
            brojPrijavljenih = itemView.findViewById(R.id.brojPrijavljenih);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Rok getItem(int id) {
        return podaci.get(id);
    }

    public void setPodaci(List<Rok> itemList) {
        this.podaci = itemList;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
