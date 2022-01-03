package com.devrik.GaonBazaar.others;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devrik.GaonBazaar.Model.ArticleModel;
import com.devrik.GaonBazaar.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticleAdapter  extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    Context context;
    ArrayList<ArticleModel> articleModelArrayList;

    public ArticleAdapter(Context context, ArrayList<ArticleModel> myModelArrayList) {
        this.context = context;
        this.articleModelArrayList = myModelArrayList;

    }
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.article,parent,false);
        ArticleAdapter.ViewHolder viewHolder=new ArticleAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {

        ArticleModel myModel = articleModelArrayList.get(position);

        if (!myModel.equals("")) {
            holder.text_article.setText(myModel.getArtical());
            holder.text_date.setText(myModel.getDate());
            holder.text_username.setText(myModel.getName());

            try {
                Log.e("dkjh", myModel.getPath());
                Log.e("dkjh", myModel.getImage());
                Glide.with(context).load(myModel.getPath()+myModel.getImage())
                      //  .placeholder(R.drawable.spry).override(250, 250)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.profile_img);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dgfadf", e.getMessage());
            }

        }
    }

    @Override
    public int getItemCount() {
        return articleModelArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_username,text_date,text_article;
        CircleImageView profile_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_img=itemView.findViewById(R.id.profile_img);
            text_username=itemView.findViewById(R.id.text_username);
            text_date=itemView.findViewById(R.id.text_date);
            text_article=itemView.findViewById(R.id.text_article);
        }
    }

}
