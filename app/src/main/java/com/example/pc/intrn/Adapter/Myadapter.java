package com.example.pc.intrn.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.intrn.DetailsActivity;
import com.example.pc.intrn.Model.items;
import com.example.pc.intrn.R;

import java.util.ArrayList;

import android.widget.Filter;
import android.widget.Filterable;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder> implements Filterable {

    ArrayList<items> mitems;
    ArrayList<items> mFilteredList;
    Context context;
    int size = 0;

    public Myadapter(ArrayList<com.example.pc.intrn.Model.items> mitems, Context context) {
        this.mitems = mitems;
        this.context = context;
        mFilteredList = mitems;

    }

    @NonNull
    @Override
    public Myadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recyclerview, parent, false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Myadapter.MyViewHolder holder, final int position) {

        if (mFilteredList.get(position) != null)

        {
            // holder.imageView.setImageDrawable(mFilteredList.get(position).getImage());
            Glide.with(context).load(mFilteredList.get(position).getImage()).into(holder.imageView);
            holder.title.setText(mFilteredList.get(position).getTitle());
            holder.price.setText(mFilteredList.get(position).getPrice());
            holder.provider.setText(mFilteredList.get(position).getProvider());

            holder.cardView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (position == 0) {
                                context.startActivity(new Intent(context, DetailsActivity.class));

                            }
                            else {

                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(context);
                                }
                                builder.setTitle("Only Valid for 1st item")
                                        .setMessage("")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // continue with delete
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }

                        }
                    }
            );
        }

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mitems;
                } else {

                    ArrayList<items> filteredList = new ArrayList<>();

                    for (items androidVersion : mitems) {

                        if (androidVersion.getTitle().toLowerCase().contains(charString) || androidVersion.getProvider().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<items>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView title;
        TextView price;
        TextView provider;

        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view_1);

            imageView = itemView.findViewById(R.id.imageview);

            title = itemView.findViewById(R.id.title);

            price = itemView.findViewById(R.id.price);

            provider = itemView.findViewById(R.id.provider);
        }
    }
}
