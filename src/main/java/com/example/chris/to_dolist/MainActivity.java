package com.example.chris.to_dolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;

/**
 * Author: Christopher Shim
 * Desc: A to-do list application that helps the user plan his/her day
 *
 * Date: June 6, 2017
 */

public class MainActivity extends Activity {
    // Initialize all variables
    private ArrayList<String> list = new ArrayList<>();
    // Instantiate custom adapter
    private TaskAdapter adapter = new TaskAdapter(list, this);
    private ListView taskList;
    private Button addTaskButton;
    private EditText editText;
    private String taskString;
    private SharedPreferences savedTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Assigns each variable to the widgets on the screen
        addTaskButton = (Button) findViewById(R.id.addTaskButton);
        editText = (EditText) findViewById(R.id.taskEditText);
        taskList = (ListView) findViewById(R.id.taskListView);

        // Assigns the listener for edit texts
        TextView.OnEditorActionListener textEventListener = new TaskListener();
        editText.setOnEditorActionListener( new TaskListener() );

        // Assigns the listener for buttons
        View.OnClickListener buttonEventListener = new ButtonListener();
        addTaskButton.setOnClickListener( buttonEventListener );

        // Assigns the adapter
        taskList.setAdapter(adapter);

        // Creates a SharedPreferences object to store tasks
        savedTasks = getSharedPreferences( "ToDoListTasks", MODE_PRIVATE );

    }

    @Override
    public void onPause() {
        // Saves all the tasks the user had
        Editor prefsEditor = savedTasks.edit();
        for (int counter = 0; counter < adapter.getCount(); counter++) {
            prefsEditor.putString( "task" + counter, adapter.getItem(counter));
        }
        // Saves how many tasks the user had
        prefsEditor.putInt("numOfTasks", adapter.getCount());
        prefsEditor.commit();

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Resets the task list
        adapter.clear();
        // Finds out how many tasks there were before app closed
        int numOfTasks = savedTasks.getInt("numOfTasks", 0);
        // Loads all the tasks back into the array
        for (int counter = 0; counter < numOfTasks; counter++) {
            adapter.addTask(savedTasks.getString("task" + counter, ""));
        }
    }

    // Inner class that allows the user to enter a task
    private class TaskListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if ( actionId == EditorInfo.IME_ACTION_DONE ) {
                taskString = editText.getText().toString();
            }
            return false;
        }
    }

    // Inner class that passes the task the user entered into the taskList
    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // When the user clicks this button, add a task to taskList
            if (v.getId() == R.id.addTaskButton) {
                adapter.addTask(taskString);
            }
        }
    }
}
