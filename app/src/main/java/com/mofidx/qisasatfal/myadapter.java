package com.mofidx.qisasatfal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class myadapter extends FirebaseRecyclerAdapter<dataholder,myadapter.myviewholder>
{
    public myadapter(@NonNull FirebaseRecyclerOptions<dataholder> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull dataholder model) {
        holder.name.setText(model.getName());
        Glide.with(holder.img.getContext()).load(model.getImglink()).into(holder.img);
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
       return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView name;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.roundedImageView);
            name=(TextView)itemView.findViewById(R.id.txtitem1);
        }
    }
}
