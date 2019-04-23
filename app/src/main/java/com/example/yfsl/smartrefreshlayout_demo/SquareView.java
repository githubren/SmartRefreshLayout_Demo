package com.example.yfsl.smartrefreshlayout_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SquareView extends RelativeLayout {
    private EditText editText;
    private TextView wordCalc;
    private Context mContext;
    private CharSequence tempChar;
    private int editStart;
    private int editEnd;
    private long toastInterval;
    private final int INTERVAL = 3 * 1000;//间隔3秒，提醒一次

    public SquareView(Context context) {
        this(context, null);
    }

    public SquareView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initSquare(context);
    }

    private void initSquare(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.square_view_layout, null, false);
        editText = view.findViewById(R.id.square_edit);
        wordCalc = view.findViewById(R.id.square_word_calc);
        initListener();
        addView(view);
    }

    public boolean hasEditFocus() {
        return editText != null && editText.hasFocus();
    }

    public void setEditText(String text) {
        editText.setText(text);
        if (!TextUtils.isEmpty(text)) {
            editText.setSelection(text.length());
        }
    }

    public String getEditText() {
        return editText == null ? "" : Utils.getEditTextString(editText);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        editText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //字数小于95个字的时候，在et上滑动，不拦截scrollview的滑动
                //95字数，是因为在定制机上输入后查看的。
                if (editText != null && editText.getText().length() < 95)
                    return false;
                // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tempChar = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordCalc.setText(mContext.getString(R.string.str_word_calc, s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                editStart = editText.getSelectionStart();
                editEnd = editText.getSelectionEnd();
                if (s.length() > 150) {
                    long l = System.currentTimeMillis();
                    if (l - toastInterval >= INTERVAL) {//间隔指定时间后，再次提示用户
                        Utils.toastShort(mContext, R.string.str_word_beyond_limit);
                        toastInterval = l;
                    }
                    s.delete(editStart - 1, editEnd);
                    editText.setText(s);
                    editText.setSelection(150);
                }
            }
        });
    }
}
