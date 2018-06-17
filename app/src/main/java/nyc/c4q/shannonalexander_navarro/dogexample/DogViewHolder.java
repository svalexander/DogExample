package nyc.c4q.shannonalexander_navarro.dogexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;



public class DogViewHolder extends RecyclerView.ViewHolder {

    ImageView iv;
    final Context context;

    public DogViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        iv = itemView.findViewById(R.id.dogIV);
    }

    public void bind(String url) {
        Picasso.with(context).load(url).into(iv);
    }
}
