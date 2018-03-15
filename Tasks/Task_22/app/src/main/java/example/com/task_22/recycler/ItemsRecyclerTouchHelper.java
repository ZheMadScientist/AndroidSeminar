package example.com.task_22.recycler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ItemsRecyclerTouchHelper extends ItemTouchHelper.SimpleCallback {
    private ItemsRecyclerTouchHelperListener listener;

    public ItemsRecyclerTouchHelper(int dragDirs, int swipeDirs, ItemsRecyclerTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null){
            final View foregroundView = ((ItemsAdapter.ItemViewHolder) viewHolder).foreground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((ItemsAdapter.ItemViewHolder) viewHolder).foreground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((ItemsAdapter.ItemViewHolder) viewHolder).foreground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        ItemsAdapter.ItemViewHolder vh = (ItemsAdapter.ItemViewHolder) viewHolder;

        final View foregroundView = vh.foreground;

        View background = vh.background;
        TextView toCartTV = vh.toCallTV;
        TextView toDeleteTV = vh.toMessageTV;
        ImageView toCartImg = vh.toCallImg;
        ImageView toDeleteImg = vh.toMessageImg;

        toDeleteTV.setVisibility(View.VISIBLE);
        toCartTV.setVisibility(View.VISIBLE);
        toDeleteImg.setVisibility(View.VISIBLE);
        toCartImg.setVisibility(View.VISIBLE);

        if(dX > 0) {
            background.setBackgroundColor(Color.parseColor("#00B8D4"));
            toDeleteTV.setVisibility(View.INVISIBLE);
            toDeleteImg.setVisibility(View.INVISIBLE);
        }
        else{
            background.setBackgroundColor(Color.parseColor("#43A047"));
            toCartTV.setVisibility(View.INVISIBLE);
            toCartImg.setVisibility(View.INVISIBLE);
        }


        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

}
