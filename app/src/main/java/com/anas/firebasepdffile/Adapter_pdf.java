package com.anas.firebasepdffile;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Adapter_pdf extends FirebaseRecyclerAdapter<Model_Pdf,Adapter_pdf.Pdf_ViewHolder> {

    public Adapter_pdf(@NonNull FirebaseRecyclerOptions<Model_Pdf> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Pdf_ViewHolder holder, int position, @NonNull Model_Pdf model) {

        holder.txtPdf_name.setText(model.getFilename());

        holder.txtPdf_likes.setText(String.valueOf(model.getLC()));
        holder.txtPdf_dislikes.setText(String.valueOf(model.getDC()));
        holder.txtPdf_views.setText(String.valueOf(model.getVC()));

        holder.imgPdf_logo.setOnClickListener(v -> {

            Intent i = new Intent(holder.imgPdf_logo.getContext(),PdfViewerActivity.class);
            i.putExtra("Filename",model.getFilename());
            i.putExtra("Fileurl",model.getFileurl());

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            holder.imgPdf_logo.getContext().startActivity(i);
        });
    }

    @NonNull
    @Override
    public Pdf_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_single,parent,false);
        return new Pdf_ViewHolder(view);
    }

    public class Pdf_ViewHolder extends RecyclerView.ViewHolder {


        ImageView imgPdf_logo,imgPdf_likes,imgPdf_dislikes,imgPdf_views;
        TextView txtPdf_name,txtPdf_likes,txtPdf_dislikes,txtPdf_views;

        public Pdf_ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPdf_logo = itemView.findViewById(R.id.imgPdf_logo);
            imgPdf_likes = itemView.findViewById(R.id.imgPdf_likes);
            imgPdf_dislikes = itemView.findViewById(R.id.imgPdf_dislikes);
            imgPdf_views = itemView.findViewById(R.id.imgPdf_views);

            txtPdf_name = itemView.findViewById(R.id.txtPdf_name);
            txtPdf_likes = itemView.findViewById(R.id.txtPdf_likes);
            txtPdf_dislikes = itemView.findViewById(R.id.txtPdf_dislikes);
            txtPdf_views = itemView.findViewById(R.id.txtPdf_views);

        }
    }
}
