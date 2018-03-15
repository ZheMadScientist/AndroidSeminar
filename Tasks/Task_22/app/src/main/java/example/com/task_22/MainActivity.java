package example.com.task_22;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import example.com.task_22.recycler.*;


public class MainActivity extends AppCompatActivity implements ItemsRecyclerTouchHelperListener {

    static final String CONTACTS = "contacts";

    RecyclerView recyclerView;
    ItemsAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton fab;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });

        recyclerView = findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new ItemsAdapter(mLayoutManager, new ArrayList<Item>());

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback touchHelperCallbackLeft = new ItemsRecyclerTouchHelper(0, ItemTouchHelper.LEFT, this);
        ItemTouchHelper.SimpleCallback touchHelperCallbackRight = new ItemsRecyclerTouchHelper(0, ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(touchHelperCallbackLeft).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(touchHelperCallbackRight).attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0){
                    fab.hide();
                } else{
                    fab.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String backup = sharedPreferences.getString(CONTACTS, "");
        if(!backup.equals("")){
            Gson gson = new GsonBuilder().create();
            Contacts contacts = gson.fromJson(backup, Contacts.class);
            adapter = new ItemsAdapter(mLayoutManager, contacts.contacts);
            updateAt(adapter, 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(adapter.getStorage().size() > 0) {
            Gson gson = new GsonBuilder().create();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(CONTACTS, gson.toJson(new Contacts(adapter.getStorage())));
            editor.apply();
        }
    }

    public void showAddItemDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View content = inflater.inflate(R.layout.add_item_dialog, null);

        builder.setView(content);

        builder.setTitle("Add new contact");
        builder.setCancelable(true);

        final EditText name, description, price;
        name = content.findViewById(R.id.add_item_name);
        description = content.findViewById(R.id.add_item_description);
        price = content.findViewById(R.id.add_item_number);

        builder.setPositiveButton("add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameS = String.valueOf(name.getText());
                String descriptionS = String.valueOf(description.getText());
                String priseS = String.valueOf(price.getText());

                boolean isOk = true;
                String message = "";
                if (nameS.trim().equals("")) {
                    isOk = false;
                    message += "Wrong name";
                }

                if (priseS.trim().equals("")) {
                    isOk = false;
                    if (!message.equals(""))
                        message += " and number";
                    else
                        message += "Wrong number";
                }

                if (!isOk) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    return;
                }

                Item newItem = new Item(nameS, descriptionS, priseS);

                Log.d("TEXTS", newItem.name + " " + newItem.description + " " + newItem.number);

                adapter.addItem(newItem);
            }
        }).setNegativeButton("cancel", null);

        AlertDialog d = builder.create();
        d.show();

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        final ItemsAdapter adapter = (ItemsAdapter) this.adapter;

        final Item item = adapter.getItemAt(position);
        final int pos = position;

        if(direction == ItemTouchHelper.LEFT){
            Intent smsIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.number));

            startActivity(smsIntent);

            updateAt(adapter, pos);


        } else if(direction == ItemTouchHelper.RIGHT){
            Log.d("onSwiped", "RIGHT");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @SuppressLint("MissingPermission")
                @Override
                public void run() {
                    Intent intent = new Intent(Intent.ACTION_CALL);

                    intent.setData(Uri.parse("tel:" + item.number));
                    startActivity(intent);

                    updateAt(adapter, pos);
                }
            },200);
        }
    }

    void updateAt (ItemsAdapter adapter, int position){
        adapter = new ItemsAdapter(mLayoutManager, adapter.getStorage());
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(position);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.scrollToPosition(position);
    }
}
