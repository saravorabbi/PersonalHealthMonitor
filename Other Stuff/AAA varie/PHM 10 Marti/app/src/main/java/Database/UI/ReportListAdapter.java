package Database.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phm.MainActivity;
import com.example.phm.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import Database.Report;

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.ReportViewHolder> {

    //VARIABILI
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Report> mReports;

    //COSTRUTTORE
    public ReportListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        ReportViewHolder viewHolder = new ReportViewHolder(itemView);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        if(mReports != null){
            Report report = mReports.get(position);
            holder.setData(report, position);
            holder.setListeners();
        }
        else {
            holder.TXVNote.setText(R.string.no_report);
        }
    }

    @Override
    public int getItemCount() {
        if(mReports != null)
            return mReports.size();
        else return 0;
    }

    public void setReports(List<Report> reports) {
        mReports = reports;
        notifyDataSetChanged();
    }

    //VIEW HOLDER
    public class ReportViewHolder extends RecyclerView.ViewHolder{

        private TextView TXVDate, TXVBattito, TXVPressione, TXVTemperatura, TXVGlicemia, TXVNote;
        private int mPosition;
        private ImageView IMGDelete, IMGEdit;

        //COSTRUTTORE
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            TXVDate = itemView.findViewById(R.id.TXVdate_val);
            TXVBattito = itemView.findViewById(R.id.TXVbattito);
            TXVPressione = itemView.findViewById(R.id.TXVpressione);
            TXVTemperatura = itemView.findViewById(R.id.TXVtemperatura);
            TXVGlicemia = itemView.findViewById(R.id.TXVglicemia);
            TXVNote = itemView.findViewById(R.id.TXVnote_val);
            IMGDelete = itemView.findViewById(R.id.IMGDelete);
            IMGEdit = itemView.findViewById(R.id.IMGEdit);
        }

        //ASSEGNA I VALORI DEL REPORT ALLE ETICHETTE
        public void setData(Report report, int position) {
            TXVDate.setText(report.getData());
            TXVBattito.setText(nullValue(report.getBattito()));
            TXVPressione.setText(nullValue(report.getPressione()));
            TXVTemperatura.setText(nullValue(report.getTemperatura()));
            TXVGlicemia.setText(nullValue(report.getGlicemia()));
            TXVNote.setText(nullString(report.getNota()));
            mPosition = position;
        }

        //ICONE DI DESTRA ELIMINA E MODIFICA
        public void setListeners() {
            //SE CLICCHI SU MODIFICA
            IMGEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditReportActivity.class);
                    int id = mReports.get(mPosition).getId();
                    intent.putExtra("report_id", id);
                    ((Activity)mContext).startActivity(intent);
                }

            });

            //SE CLICCHI SU ELIMINA
            IMGDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Report report = mReports.get(mPosition);
                    MainActivity.reportViewModel.deleteReport(report);

                    SnackbarUndo SU = new SnackbarUndo();
                    SU.reportRimosso(report);
                    Snackbar snackbar = Snackbar.make(v, "Report rimosso", BaseTransientBottomBar.LENGTH_LONG);
                    snackbar.setAction("Cancella operazione", SU);
                    snackbar.show();
                }
            });

        }

        //SE NON HO INSERITO DEI VALORI == 0 ALLORA STAMPO NULL A SCHERMO
        private String nullValue(double val){
            if(val == 0){
                return "null";
            }
            return String.valueOf(val);
        }

        //SE LE INFORMAZIONI AGGIUNTIVE SONO VUOTE SCRIVO NULL
        private String nullString(String nota){
            if(nota == null) return "Nessuna info aggiuntiva";
            else return nota;
        }
    }
}
