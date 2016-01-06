package com.ccd.filing.test.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.ccd.filing.test.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: document your custom view class.
 */
public class CardView extends View {
    private String mCardString; // TODO: use a default from R.string...
    private int mCardColor = Color.RED; // TODO: use a default from R.color...
    private float mCardDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mCardDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private String personfullName = "";
    private String sex = "";
    private String nation = "";
    private String year = "";
    private String month = "";
    private String day = "";
    private String homeAddress = "";
    private String idCardNumber = "";
    private Bitmap image = null;

    public CardView(Context context) {
        super(context);
        init(null, 0);
    }

    public CardView(Context context, AttributeSet attrs) {
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

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CardView, defStyle, 0);

        mCardString = a.getString(
                R.styleable.CardView_cardString);
        mCardColor = a.getColor(
                R.styleable.CardView_cardColor,
                mCardColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mCardDimension = a.getDimension(
                R.styleable.CardView_cardDimension,
                mCardDimension);

//        if (a.hasValue(R.styleable.CardView_cardDrawable)) {
//            mCardDrawable = a.getDrawable(
//                    R.styleable.CardView_cardDrawable);
//            mCardDrawable.setCallback(this);
//        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mCardDimension);
        mTextPaint.setColor(mCardColor);
        mTextWidth = mTextPaint.measureText(mCardString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawHint(canvas);
        drawContent(canvas);
        drawImage(canvas);

        // Draw the example drawable on top of the text.
        setBackgroundResource(R.mipmap.card_positive);
        //设置透明度
        getBackground().setAlpha(200);
    }

    /**
     * 绘制头像
     *
     * @param canvas 画布
     */
    private void drawImage(Canvas canvas) {
        if (image != null) {
            mTextPaint.setAlpha(200);
            canvas.drawBitmap(image, 310, 25, mTextPaint);
        } else {
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.person);
//            canvas.drawBitmap(bitmap, 310, 25, mTextPaint);
//            L.v("lvzhongyi","null");
        }
    }

    /**
     * 绘制内容
     *
     * @param canvas 画布
     */
    private void drawContent(Canvas canvas) {
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(24);
        canvas.drawText(personfullName, 100, 60, mTextPaint);
        mTextPaint.setTextSize(18);
        canvas.drawText(sex, 100, 105, mTextPaint);
        canvas.drawText(nation, 220, 105, mTextPaint);
        canvas.drawText(year, 100, 150, mTextPaint);
        canvas.drawText(month, 185, 150, mTextPaint);
        canvas.drawText(day, 235, 150, mTextPaint);

        int addressY = 195;
        List<String> strList = string2List(null, homeAddress);
        if (strList != null) {
            for (String str : strList) {
                canvas.drawText(str, 100, addressY, mTextPaint);
                addressY += 25;
            }
        }

        mTextPaint.setTextSize(23);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        fontMetrics.leading = 20;
        canvas.drawText(idCardNumber, 160, 250, mTextPaint);
    }

    private String addSpace(String str) {
        str = str.replaceAll("\\d{1}(?!$)", "$0 ");
        return str;
    }

    /**
     * 字体换行
     *
     * @param stringList 存储的每一行数据的容器
     * @param str        原始数据
     * @return 转换后的
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
     * 绘制提示栏
     *
     * @param canvas 画布
     */
    private void drawHint(Canvas canvas) {
        mTextPaint.setColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
        mTextPaint.setTextSize(16);
        canvas.drawText("姓　名", 30, 60, mTextPaint);
        canvas.drawText("性　别", 30, 105, mTextPaint);
        canvas.drawText("民　族", 160, 105, mTextPaint);
        canvas.drawText("出　生", 30, 150, mTextPaint);
        canvas.drawText("年", 160, 150, mTextPaint);
        canvas.drawText("月", 210, 150, mTextPaint);
        canvas.drawText("日", 260, 150, mTextPaint);
        canvas.drawText("住　址", 30, 195, mTextPaint);
        canvas.drawText("公民身份证号码", 30, 250, mTextPaint);
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mCardString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mCardString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mCardColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mCardColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mCardDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mCardDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mCardDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mCardDrawable = exampleDrawable;
    }

    /**
     * set the person name;
     *
     * @param personfullName
     */
    public void setPersonfullName(String personfullName) {
        this.personfullName = personfullName;

    }

    /**
     * set the person sex
     *
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex;

    }

    /**
     * set the person nation
     *
     * @param nation
     */
    public void setNation(String nation) {
        this.nation = nation;

    }

    /**
     * set the brith year
     *
     * @param year
     */
    private void setYear(String year) {
        this.year = year;
    }

    /**
     * set the brith month
     *
     * @param month
     */
    private void setMonth(String month) {
        this.month = month;
    }

    /**
     * set the brith day
     *
     * @param day
     */
    private void setDay(String day) {
        this.day = day;
    }

    /**
     * 设置出生日期
     */
    public void setBrith(Date brith_date) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM/dd");
        String brith = sdf.format(brith_date);
        setBrith(brith);
    }

    /**
     * 设置出生日期
     */
    public void setBrith(String brith) {
        String rule = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])" +
                "(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])" +
                "(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3})" +
                ")([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)" +
                "([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\" +
                "._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^" +
                "([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])" +
                "([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)" +
                "([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^" +
                "([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(brith);
        if (matcher.matches() && brith.length() > 9) {
            setYear(brith.substring(0, 4));
            setMonth(brith.substring(5, 7));
            setDay(brith.substring(8));

        } else {
            setYear("");
            setMonth("");
            setDay("");

        }
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;

    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;

    }

    public void clear() {
        personfullName = "";
        sex = "男";
        nation = "";
        year = "";
        month = "";
        day = "";
        homeAddress = "";
        idCardNumber = "";
        image = null;
    }

    /**
     * 设置头像
     *
     * @param image 头像
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }
}
