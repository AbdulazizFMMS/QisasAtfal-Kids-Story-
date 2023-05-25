package com.mofidx.qisasatfal;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    protected void onBindViewHolder(@NonNull final myviewholder holder, int position, @NonNull final dataholder model) {
//        holder.pdflink.setText(model.getPdflink());
        holder.name.setText(model.getName());
        Glide.with(holder.img.getContext()).load(model.getImglink()).into(holder.img);

        holder.CardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.CardViewItem.getContext(), PDFActivity3.class);

                intent.putExtra("FileName",model.getName());
                intent.putExtra("FileUrl",model.getPdflink());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                holder.CardViewItem.getContext().startActivity(intent);


            }
        });
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
       return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {
        CardView CardViewItem;
        ImageView img;
        TextView name;
        TextView pdflink;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.roundedImageView);
            name=(TextView)itemView.findViewById(R.id.txtitem1);
            pdflink=(TextView)itemView.findViewById(R.id.txtitem2);
            CardViewItem=(CardView)itemView.findViewById(R.id.CardViewItem);
        }
    }
}
