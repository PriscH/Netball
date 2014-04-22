package com.prisch.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.prisch.R;

public class ActionButton extends FrameLayout {

    private FrameLayout rootLayout;
    private ImageView actionImage;
    private TextView actionText;

    // ===== Constructors =====

    public ActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.control_actionbutton, this, true);

        rootLayout = (FrameLayout)findViewById(R.id.layout_action);
        actionImage = (ImageView)findViewById(R.id.image_action);
        actionText = (TextView)findViewById(R.id.text_action);

        TypedArray styleAttributes = context.obtainStyledAttributes(attrs, R.styleable.ActionButton);
        if (styleAttributes.hasValue(R.styleable.ActionButton_imageSrc)) {
            setImageSrc(styleAttributes.getDrawable(R.styleable.ActionButton_imageSrc));
        }
        if (styleAttributes.hasValue(R.styleable.ActionButton_text)) {
            setText(styleAttributes.getString(R.styleable.ActionButton_text));
        }
        styleAttributes.recycle();
    }

    // ===== Properties =====

    public void setImageSrc(Drawable imageSrc) {
        actionImage.setImageDrawable(imageSrc);
    }

    public void setText(String text) {
        actionText.setText(text);
    }

    // TODO: Figure out a way to avoid having to overwrite this for all properties
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        rootLayout.setEnabled(enabled);
    }
}
