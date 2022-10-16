package vn.tapbi.youtubeplayer3.ui.main.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import vn.tapbi.youtubeplayer3.R;

public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        setTextColor(0xffffff);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        // draw the gradient filled text
        getPaint().clearShadowLayer();
        setTextColor(0xffffffff); // set the paint's alpha by ff
        getPaint().setShader(new LinearGradient(0, 0, getWidth(), getHeight(), 0xffFF0000, 0xffFF7100, Shader.TileMode.CLAMP)); // or whatever gradient/shader you use
        super.onDraw(canvas);
    }
}
