package example.com.task_22.recycler;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import example.com.task_22.R;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    RecyclerView.LayoutManager layoutManager;

    private List<Item> storage;

    public void setStorage(List<Item> storage) {
        this.storage = storage;
    }

    public List<Item> getStorage() {
        return storage;
    }

    public ItemsAdapter(RecyclerView.LayoutManager layoutManager, List<Item> items) {
        this.layoutManager = layoutManager;
        storage = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final Item item = storage.get(position);

        holder.name.setText(item.name);
        holder.description.setText(item.description);
        holder.number.setText(item.number);

    }

    @Override
    public int getItemCount() {
        return storage.size();
    }

    public void removeAt(int position) {
        storage.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(Item item) {
        int pos = storage.indexOf(item);
        storage.remove(item);
        notifyItemRemoved(pos);
    }

    public void restoreItem(Item item, int position) {
        storage.add(position, item);
        notifyItemInserted(position);
    }

    public void addItem(Item item) {
        storage.add(item);
        notifyItemInserted(getItemCount());
    }


    public Item getItemAt(int position) {
        return storage.get(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name, description, number;
        public TextView toMessageTV, toCallTV;
        public ImageView toMessageImg, toCallImg, deleteButton;
        public RelativeLayout background;
        public CardView foreground;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            description = itemView.findViewById(R.id.item_description);
            number = itemView.findViewById(R.id.item_number);

            toCallTV = itemView.findViewById(R.id.item_card_call_textview);
            toMessageTV = itemView.findViewById(R.id.item_card_mess_textview);
            toCallImg = itemView.findViewById(R.id.item_card_call_image);
            toMessageImg = itemView.findViewById(R.id.item_card_mess_image);
            deleteButton = itemView.findViewById(R.id.delete_button);

            background = itemView.findViewById(R.id.item_card_background);
            foreground = itemView.findViewById(R.id.item_card_foreground);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    final Item item = storage.get(pos);

                    removeItem(item);

                    Snackbar snackbar = Snackbar
                            .make(v, item.name + " removed from storage!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            restoreItem(item, pos);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            });
        }
    }
}