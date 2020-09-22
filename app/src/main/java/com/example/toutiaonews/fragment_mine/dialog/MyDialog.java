package com.example.toutiaonews.fragment_mine.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.toutiaonews.R;

public class MyDialog extends Dialog {
    public MyDialog(@NonNull final Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_layout);

        findViewById(R.id.button_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        findViewById(R.id.button_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}
