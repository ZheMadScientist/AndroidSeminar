package example.com.task_22.recycler;

import android.support.v7.widget.RecyclerView;

/**
 * Created by makar on 3/15/18.
 */

public interface ItemsRecyclerTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
