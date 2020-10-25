package com.example.vividseats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.image.setImageBitmap(item.getImage());
//        Bitmap image = item.getImage();
//        if (image.isEmpty()) {
//            holder.image.setImageResource(R.drawable.blackhole);
//        } else {
//            Glide.with(context).load(item.getImage()).into(holder.image);
//        }
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.description.setText(item.getDescription());
        holder.count.setText(item.getCount());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView title;
        private TextView subtitle;
        private TextView description;
        private TextView count;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            description = (TextView) itemView.findViewById(R.id.description);
            count = (TextView) itemView.findViewById(R.id.count);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
