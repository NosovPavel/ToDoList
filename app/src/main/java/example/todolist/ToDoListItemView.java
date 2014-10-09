package example.todolist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nosovpavel on 09/10/14.
 */
public class ToDoListItemView extends TextView {

    private Paint marginPaint;
    private Paint linePaint;
    private int paperColor;
    private float margin;

    public ToDoListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ToDoListItemView(Context context){
        super(context);
        init();
    }

    public ToDoListItemView(Context context,AttributeSet attrs){
        super(context,attrs);
        init();
    }

    private void init() {

        //Получаем ссылку на таблицу ресурсов
        Resources myRes = getResources();

        //Создаем кисти для рисования
        marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(myRes.getColor(R.color.notepad_margin));

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(myRes.getColor(R.color.notepad_lines));

        //Получаем цвет фона для листа и ширину кромки
        paperColor = myRes.getColor(R.color.notepad_paper);
        margin = myRes.getDimension(R.dimen.notepad_margin);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Фоновый цвет для листа
        canvas.drawColor(paperColor);

        //Направляющие линии
        canvas.drawLine(0,0,getMeasuredWidth(),0,linePaint);
        canvas.drawLine(0,0,0,getMeasuredHeight(),linePaint);

        //Рисуем кромку
        canvas.drawLine(margin,0,margin,getMeasuredHeight(),marginPaint);

        //Переместим текст в сторону от кромки
        canvas.save();
        canvas.translate(margin,0);

        super.onDraw(canvas);
        canvas.restore();
    }
}
