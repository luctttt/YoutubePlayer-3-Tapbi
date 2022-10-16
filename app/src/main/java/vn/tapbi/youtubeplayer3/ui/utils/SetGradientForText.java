package vn.tapbi.youtubeplayer3.ui.utils;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import vn.tapbi.youtubeplayer3.App;
import vn.tapbi.youtubeplayer3.R;


public class SetGradientForText {
    public static final int colorStart = ContextCompat.getColor(App.getContext(), R.color.text_red_gran_2);
    public static final int colorEnd = ContextCompat.getColor(App.getContext(), R.color.text_red_gran_1);

    public static void setGradientTitleForText(TextView textView, String title, int icon) {

        textView.getPaint().setShader(null);
        textView.getPaint().setShadowLayer(4.0f, -4.0f, 4.0f,
                App.getContext().getResources().getColor(R.color.color_shadow));

        textView.setText(title);
        Shader textShader = new LinearGradient(0, 0, textView.getWidth(), textView.getHeight(), colorStart, colorEnd,
                Shader.TileMode.REPEAT);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, 0, 0, 0);
        textView.getPaint().setShader(textShader);
    }
}
