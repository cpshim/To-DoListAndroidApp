package com.example.chris.to_dolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Author: Christopher Shim
 * Desc: Adapter for the taskListView
 *
 * Date: June 6, 2017
 */

public class TaskAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> taskList = new ArrayList<>();
    private Context context;
    private CheckBox checkBox;
    private ImageButton deleteButton;

    // Constructor for the TaskAdapter class
    public TaskAdapter(ArrayList<String> list, Context context) {
        this.taskList = list;
        this.context = context;
    }

    // Returns the size of the taskList
    @Override
    public int getCount(){
        return taskList.size();
    }

    // Returns the String at the index passed in
    @Override
    public String getItem(int pos) {
        return taskList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    // Method that takes the items in the ArrayList and displays them in the ListView
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.task_layout, null);
        }

        // Handle TextView and display string from taskList
        TextView taskText = (TextView)view.findViewById(R.id.taskTextView);
        taskText.setText(taskList.get(position));

        // Assign variables to widgets
        deleteButton = (ImageButton) view.findViewById(R.id.deleteImageButton);
        checkBox = (CheckBox) view.findViewById(R.id.doneCheckBox);

        //Assign onClickListeners
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                taskList.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    Toast.makeText(context, "Good Job!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    // Adds the specified String to the taskList
    public void addTask(String task) {
        taskList.add(task);
        notifyDataSetChanged();
    }

    // Clears the taskList
    public void clear() {
        taskList.clear();
    }
}

