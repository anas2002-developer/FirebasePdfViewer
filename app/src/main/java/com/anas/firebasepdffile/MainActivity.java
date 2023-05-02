package com.anas.firebasepdffile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView vRV;

    Adapter_pdf adpater_pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fbtnAdd).setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(),UploadActivity.class));
        });

        vRV=findViewById(R.id.vRV);
        vRV.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Model_Pdf> options =
                new FirebaseRecyclerOptions.Builder<Model_Pdf>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Pdfs"), Model_Pdf.class)
                        .build();

        adpater_pdf = new Adapter_pdf(options);
        vRV.setAdapter(adpater_pdf);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adpater_pdf.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adpater_pdf.stopListening();
    }
}