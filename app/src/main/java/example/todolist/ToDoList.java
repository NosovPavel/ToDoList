package example.todolist;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class ToDoList extends ActionBarActivity implements OnNewItemAddedListener {

    private ToDoItemAdapter aa;
    private ArrayList<ToDoItem> toDoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Загружаем представление
        setContentView(R.layout.activity_to_do_list);

        //Получаем ссылки на фрагмент
        FragmentManager fm = getFragmentManager();
        ToDoListFragment toDoListFragment= (ToDoListFragment)fm.findFragmentById(R.id.toDoListFragment);



        //Получаем ссылки на элементы пользовательского интерфейса
//        ListView myListView = (ListView)findViewById(R.id.myListView);
//        final EditText myEditText = (EditText)findViewById(R.id.myEditText);

        //Создаем массив для хранения списка задач
//        final ArrayList<String> toDoItems = new ArrayList<String>();

        //Создаем Адаптер чтобы привязать массив к listView
//        final ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,toDoItems);

        toDoItems = new ArrayList<ToDoItem>();
        int resId = R.layout.todo_list_item;
        aa = new ToDoItemAdapter(this,resId,toDoItems);

        //Привязываем массив к listView
//        myListView.setAdapter(aa);

        toDoListFragment.setListAdapter(aa);

//        //Создадим обработчик нажатий EditText
//        myEditText.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
//                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)||
//                    (keyCode == KeyEvent.KEYCODE_ENTER)){
//                        toDoItems.add(0,myEditText.getText().toString());
//                        aa.notifyDataSetChanged();
//                        myEditText.setText("");
//                        return true;
//                    }
//                    return false;
//                }
//                return false;
//            }
//        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNewItemAdded(String newItem) {
        ToDoItem newTodoItem = new ToDoItem(newItem);
        toDoItems.add(newTodoItem);
        aa.notifyDataSetChanged();
    }
}
