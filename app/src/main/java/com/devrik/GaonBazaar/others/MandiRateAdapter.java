package com.devrik.GaonBazaar.others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devrik.GaonBazaar.Model.MandiRateModel;
import com.devrik.GaonBazaar.R;

import java.util.ArrayList;

public class MandiRateAdapter extends RecyclerView.Adapter<MandiRateAdapter.ViewHolder> {
    Context context;
    ArrayList<MandiRateModel> myModelArrayList;

    public MandiRateAdapter(Context context, ArrayList<MandiRateModel> myModelArrayList) {
        this.context = context;
        this.myModelArrayList = myModelArrayList;

    }
    @Override
    public MandiRateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mandirate,parent,false);
        MandiRateAdapter.ViewHolder viewHolder=new MandiRateAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MandiRateAdapter.ViewHolder holder, int position) {

        MandiRateModel  myModel = myModelArrayList.get(position);

        if (!myModel.equals("")) {
            holder.marqueeText.setText(myModel.getCrops_name());
            holder.marqueeText1.setText(myModel.getCrops_price());
           /* holder.marqueeText.setSelected(true);
            holder.marqueeText1.setSelected(true);*/
        }
    }

    @Override
    public int getItemCount() {
        return myModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView marqueeText,marqueeText1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            marqueeText=itemView.findViewById(R.id.marqueeText);
            marqueeText1=itemView.findViewById(R.id.marqueeText1);

        }
    }

}
