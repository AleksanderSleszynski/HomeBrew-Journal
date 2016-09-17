package com.example.julian.homebrewjournal.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageView;

import com.example.julian.homebrewjournal.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomizeLogoActivity extends AppCompatActivity {

    @BindView(R.id.customize_beer_icon) ImageView mBeerIcon;
    @BindView(R.id.customize_background) View mBackground;

    public static final String TAG = CustomizeLogoActivity.class.getSimpleName();
    // ToDo: Change name of variable
    int i = 0;
    int j = 0;
    int k = 0;

    int backgroundNumber = 0;
    int glassNumber = 0;
    int styleNumber = 0;

    int[] colors, glasses, styles;

    @OnClick(R.id.change_background_color_left) void onChangeBackgroundRight(){
        if(i > 0)
            i--;
        else
            i = colors.length - 1;

        mBackground.setBackgroundColor(colors[i]);
        backgroundNumber = i;
    }

    @OnClick(R.id.change_background_color_right) void onChangeBackgroundLeft(){
        if(i < colors.length - 1)
            i++;
        else
            i = 0;

        mBackground.setBackgroundColor(colors[i]);
        backgroundNumber = i;
    }

    @OnClick(R.id.change_beer_glass_button_left) void onChangeGlassLeft(){
        if(j > 0)
            j--;
        else
            j = glasses.length - 1;

        mBeerIcon.setImageResource(glasses[j]);
        mBeerIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                glasses[j], new ContextThemeWrapper(this, styles[styleNumber]).getTheme()));
        glassNumber = j;
    }

    @OnClick(R.id.change_beer_glass_button_right) void onChangeGlassRight(){
        if(j < glasses.length - 1)
            j++;
        else
            j = 0;

        mBeerIcon.setImageResource(glasses[j]);
        mBeerIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                glasses[j], new ContextThemeWrapper(this, styles[styleNumber]).getTheme()));
        glassNumber = j;
    }

    @OnClick(R.id.change_beer_color_button_left) void onChangeStyleLeft(){
        if(k > 0)
            k--;
        else
            k = styles.length - 1;

        mBeerIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                glasses[glassNumber], new ContextThemeWrapper(this, styles[k]).getTheme()));
        styleNumber = k;
    }

    @OnClick(R.id.change_beer_color_button_right) void onChangeStyleRight(){
        if(k < styles.length - 1)
            k++;
        else
            k = 0;

        mBeerIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                glasses[glassNumber], new ContextThemeWrapper(this, styles[k]).getTheme()));
        styleNumber = k;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_logo);
        ButterKnife.bind(this);

        colors = this.getResources().getIntArray(R.array.background_colors);
        glasses = new int[]{R.drawable.glass_nonic_pint, R.drawable.glass_mug,
                R.drawable.glass_imperial_pint, R.drawable.glass_snifter, R.drawable.glass_shaker_pint,
                R.drawable.glass_tulip, R.drawable.glass_weizen};
        styles = new int[]{R.style.ipa, R.style.ale, R.style.porter, R.style.red, R.style.stout,
                R.style.weizen};


        final ContextThemeWrapper wrapper = new ContextThemeWrapper(this, styles[0]);
        final Drawable drawable = ResourcesCompat.getDrawable(getResources(),
                glasses[0], wrapper.getTheme());
        mBeerIcon.setImageDrawable(drawable);
        mBackground.setBackgroundColor(colors[0]);
    }
}

