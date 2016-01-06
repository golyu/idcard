package com.ccd.filing.test.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import com.ccd.filing.test.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class CardNegativeView extends View {

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private String organ = "";
    private String startDate = "";
    private String endDate = "";

    public CardNegativeView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardNegativeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            float width = getWidth();
            float height = width * 54f / 85.6f;
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.width = (int) width;
            layoutParams.height = (int) height;
            setLayoutParams(layoutParams);
        }
    }

    public CardNegativeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {


        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        drawHint(canvas);
        drawContent(canvas);

        setBackgroundResource(R.mipmap.card_negative);
        //设置透明度
        getBackground().setAlpha(200);
    }


    private void drawContent(Canvas canvas) {
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(18);
        mTextPaint.setFakeBoldText(false);
        canvas.drawText(organ, 190, 205, mTextPaint);
        canvas.drawText(startDate + "-" + endDate, 190, 250, mTextPaint);

    }

    private String addSpace(String str) {
        str = str.replaceAll("\\d{1}(?!$)", "$0 ");
        return str;
    }

    /**
     * 超过11个字换行
     *
     * @param stringList 用来保存每行的字
     * @param str        所有的字
     * @return 转好好的list
     */
    private List<String> string2List(List<String> stringList, String str) {
        if (stringList == null) {
            stringList = new ArrayList<>();
        }
        if (str.length() > 11) {
            stringList.add(str.substring(0, 11));
            string2List(stringList, str.substring(11));
        } else if (str.length() > 0) {
            stringList.add(str);
        }
        return stringList;
    }

    /**
     * 画提示栏
     *
     * @param canvas 画布
     */
    private void drawHint(Canvas canvas) {

        mTextPaint.setTextSize(18);
        mTextPaint.setFakeBoldText(true);
        canvas.drawText("签证机关", 100, 205, mTextPaint);
        canvas.drawText("有效期至", 100, 250, mTextPaint);
    }

    public void setIssuingAuthority(String organ) {
        this.organ = organ;
    }

    /**
     * 设置有效期起点
     *
     * @param startDate 起点时间
     */
    public void setBeginDate(Date startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/DD");
        setBeginDate(this.startDate = sdf.format(startDate));
    }

    public void setBeginDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 设置有效期终点
     *
     * @param endDate 终点时间
     */
    public void setEndDate(Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/DD");
        setEndDate(sdf.format(endDate));
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
