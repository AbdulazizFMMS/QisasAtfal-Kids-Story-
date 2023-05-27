package com.mofidx.qisasatfal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    int pos_keyARorEN;
    RecyclerView recview;
    myadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        recview= findViewById(R.id.recview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
//        linearLayoutManager.setReverseLayout(true);
//        linearLayoutManager.setStackFromEnd(true);
        recview.setLayoutManager(gridLayoutManager);

//        recview.setLayoutManager(new LinearLayoutManager(this));
        pos_keyARorEN = getIntent().getIntExtra("pos_key",0);
        if (pos_keyARorEN==0){

            FirebaseRecyclerOptions<dataholder> options =
                    new FirebaseRecyclerOptions.Builder<dataholder>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("QisassAr"), dataholder.class)
                            .build();

            adapter=new myadapter(options);
            recview.setAdapter(adapter);
        }else {


            FirebaseRecyclerOptions<dataholder> options =
                    new FirebaseRecyclerOptions.Builder<dataholder>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("QisassEn"), dataholder.class)
                            .build();

            adapter=new myadapter(options);
            recview.setAdapter(adapter);
        }













    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }

}