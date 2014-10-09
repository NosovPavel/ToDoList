package example.todolist;

/**
 * Created by nosovpavel on 08/10/14.
 */

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class newItemFragment extends Fragment {

    private OnNewItemAddedListener onNewItemAddedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.new_item_fragment,container,false);

        final EditText myEditText = (EditText)view.findViewById(R.id.myEditText);

        //Создадим обработчик нажатий EditText
        myEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)||(keyCode == KeyEvent.KEYCODE_ENTER)){

                        String newItem = myEditText.getText().toString();
                        onNewItemAddedListener.onNewItemAdded(newItem);
                        myEditText.setText("");
                        return true;
                    }
                    return false;
                }
                return false;
            }
        });

        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            onNewItemAddedListener = (OnNewItemAddedListener)activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnNewItemAddedListener");
        }
    }
}