package com.bofsoft.laio.laiovehiclegps.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.laio.laiovehiclegps.R;


public class Widget_Image_Text_Btn extends RelativeLayout {
  public final float[] BT_SELECTED = new float[] {1, 0, 0, 0, 50, 0, 1, 0, 0, 50, 0, 0, 1, 0, 50,
      0, 0, 0, 1, 0};
  public final float[] BT_NOT_SELECTED = new float[] {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0,
      0, 0, 0, 1, 0};
  private ImageView imageView;
  private TextView textView;
  LayoutParams countTextviewlp;
  LinearLayout outterLayout;

  public Widget_Image_Text_Btn(Context context) {
    super(context);
  }

  public Widget_Image_Text_Btn(Context context, AttributeSet attrs) {
    super(context, attrs);

    imageView = new ImageView(context);
    textView = new TextView(context);
    imageView.setId((int) (Math.random() * 0x200000));

    // 点击事件
    this.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });
    // 获取元件属性
    TypedArray typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.Widget_Image_Text_Btn);
    CharSequence text = typedArray.getText(R.styleable.Widget_Image_Text_Btn_text);
    int textColor = typedArray.getColor(R.styleable.Widget_Image_Text_Btn_textColor, Color.BLACK);
    int textSize = typedArray.getInt(R.styleable.Widget_Image_Text_Btn_textSize, 14);
    Drawable src = typedArray.getDrawable(R.styleable.Widget_Image_Text_Btn_src);
    String position = typedArray.getString(R.styleable.Widget_Image_Text_Btn_position);
    int backgroundColor =
        typedArray.getColor(R.styleable.Widget_Image_Text_Btn_backgroundColor, Color.TRANSPARENT);
    Drawable background = typedArray.getDrawable(R.styleable.Widget_Image_Text_Btn_background);
    int margin = typedArray.getInt(R.styleable.Widget_Image_Text_Btn_margin, 0);
    int width =
        typedArray.getInt(R.styleable.Widget_Image_Text_Btn_width, LayoutParams.WRAP_CONTENT);
    CharSequence countText = typedArray.getText(R.styleable.Widget_Image_Text_Btn_countText);
    int countLeft = typedArray.getInt(R.styleable.Widget_Image_Text_Btn_countLeft, 0);
    int countTop = typedArray.getInt(R.styleable.Widget_Image_Text_Btn_countTop, 0);

    typedArray.recycle();



    // margin = Fun.px2dip(this.getContext(),
    // (margin / ConfigallStu.designHeight) * ConfigallStu.screenHeight);
    // countLeft = Fun.px2dip(this.getContext(),
    // (countLeft / ConfigallStu.designWidth) * ConfigallStu.screenWidth);
    // countTop = Fun.px2dip(this.getContext(),
    // (countTop / ConfigallStu.designHeight) * ConfigallStu.screenHeight);

    // 设置属性
    this.setBackgroundColor(backgroundColor);

    this.setLayoutParams(new LayoutParams(width, LayoutParams.WRAP_CONTENT));

    textView.setEllipsize(TruncateAt.END);
    textView.setSingleLine();
    // 文字
    if (text != null) {
      textView.setText(text);
    }

    textView.setTextColor(textColor);
    textView.setTextSize(textSize);

    // 背景或图片
    if (src != null)
      imageView.setBackgroundDrawable(src);
    if (background != null) {
      this.setBackgroundDrawable(background);
    } else if (backgroundColor != Color.TRANSPARENT) {
      Bitmap b = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
      Canvas c = new Canvas(b);
      c.drawColor(backgroundColor);
      BitmapDrawable bd = new BitmapDrawable(getResources(), b);
      this.setBackgroundDrawable(bd);
    }

    if (position == null)
      position = "right";

    // 共两层Layout保证水平和垂直都在中间
    outterLayout = new LinearLayout(context);
    outterLayout.setId((int) (Math.random() * 0x100000));
    LayoutParams outterLayoutlp =
        new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    outterLayoutlp.addRule(CENTER_IN_PARENT);
    this.addView(outterLayout, outterLayoutlp);
    LinearLayout innerlayout = new LinearLayout(context);
    LinearLayout.LayoutParams layoutlp;
    if (position.equalsIgnoreCase("left") || position.equalsIgnoreCase("right")) {
      outterLayout.setOrientation(LinearLayout.VERTICAL);
      layoutlp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
      innerlayout.setOrientation(LinearLayout.HORIZONTAL);
    } else {
      outterLayout.setOrientation(LinearLayout.HORIZONTAL);
      layoutlp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
      innerlayout.setOrientation(LinearLayout.VERTICAL);
    }
    layoutlp.gravity = Gravity.CENTER;
    outterLayout.addView(innerlayout, layoutlp);

    // 动态布局图片和文字
    LinearLayout.LayoutParams lpImageView =
        new LinearLayout.LayoutParams(width, LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams lpTextView =
        new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    lpImageView.gravity = Gravity.CENTER;
    lpTextView.gravity = Gravity.CENTER;

    int imageLeft = 0;
    int imageTop = 0;
    int imageRight = 0;
    int imageBottom = 0;
    int textLeft = 0;
    int textTop = 0;
    int textRight = 0;
    int textBottom = 0;

    if (position.equalsIgnoreCase("left")) {
      if (!isInEditMode()) {
        margin = margin * (720 / 1080);
      }
      if (text != null) {
        innerlayout.addView(textView, lpTextView);
        imageLeft = margin;
      }
      innerlayout.addView(imageView, lpImageView);
    } else if (position.equalsIgnoreCase("right")) {
      if (!isInEditMode()) {
        margin = margin * (720 / 1080);
      }
      innerlayout.addView(imageView, lpImageView);
      if (text != null) {
        textLeft = margin;
        innerlayout.addView(textView, lpTextView);
      }
    } else if (position.equalsIgnoreCase("top")) {
      if (!isInEditMode()) {
        margin = margin * (720 / 1080);
      }
      if (text != null) {
        innerlayout.addView(textView, lpTextView);
        imageTop = margin;
      }
      innerlayout.addView(imageView, lpImageView);
    } else if (position.equalsIgnoreCase("bottom")) {
      if (!isInEditMode()) {
        margin = margin * (720 / 1080);
      }
      innerlayout.addView(imageView, lpImageView);
      if (text != null) {
        textTop = margin;
        innerlayout.addView(textView, lpTextView);
      }
    }

    lpImageView.setMargins(imageLeft, imageTop, imageRight, imageBottom);
    lpTextView.setMargins(textLeft, textTop, textRight, textBottom);

    // 按钮变色
    this.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
          Widget_Image_Text_Btn.this.getBackground().setColorFilter(
              new ColorMatrixColorFilter(BT_SELECTED));
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
          Widget_Image_Text_Btn.this.getBackground().setColorFilter(
              new ColorMatrixColorFilter(BT_NOT_SELECTED));
        }
        return false;
      }
    });

  }

  /**
   * 设置图片资源
   */
  public void setImageResource(int resId) {
    imageView.setImageResource(resId);
  }

  public void setImage(Drawable drawable) {
    imageView.setBackgroundDrawable(drawable);
  }

  public void setImageBitmap(Bitmap bm) {
    imageView.setImageBitmap(bm);
  }

  public void setTextColor(int color) {
    textView.setTextColor(color);
  }

  public void setTextSize(float size) {
    textView.setTextSize(size);
  }

  public void setBackgrounds(Drawable background) {
    // this.setBackgrounds(background);
    this.setBackgroundDrawable(background);
  }

  /**
   * 设置显示的文字
   */
  public void setTextViewText(String text) {
    textView.setVisibility(View.VISIBLE);
    textView.setText(text);
  }
}
