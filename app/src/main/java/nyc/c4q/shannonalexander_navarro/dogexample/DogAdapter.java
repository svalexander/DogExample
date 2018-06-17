package nyc.c4q.shannonalexander_navarro.dogexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;



public class DogAdapter extends RecyclerView.Adapter<DogViewHolder> {

    List<String> dogUrls = new ArrayList<>();

    public DogAdapter(List<String> list) {
        this.dogUrls = list;
    }

    @Override
    public DogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new DogViewHolder(root);
    }

    @Override
    public void onBindViewHolder(DogViewHolder holder, int position) {

        String url = dogUrls.get(position);
        holder.bind(url);
    }

    @Override
    public int getItemCount() {
        return dogUrls.size();
    }
}
