package example.todolist;

import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by nosovpavel on 10/10/14.
 */
public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {
    int resource;

    public ToDoItemAdapter(Context context,int resource,List<ToDoItem> items){
        super(context,resource,items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout toDoView;

        ToDoItem item = getItem(position);

        String taskString = item.getTask();
        Date createdDate = item.getCreated();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(createdDate);

        if(convertView == null){
            toDoView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)getContext().getSystemService(inflater);

            li.inflate(resource,toDoView,true);
        } else {
            toDoView = (LinearLayout)convertView;
        }

        TextView dateView = (TextView)toDoView.findViewById(R.id.rowDate);
        TextView taskView = (TextView)toDoView.findViewById(R.id.row);

        dateView.setText(dateString);
        taskView.setText(taskString);

        return toDoView;
    }
}
