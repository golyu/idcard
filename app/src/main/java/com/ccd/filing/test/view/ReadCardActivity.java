package com.ccd.filing.test.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccd.filing.test.R;
import com.ccd.filing.test.presenter.ReadCardPresenter;
import com.ccd.filing.test.utils.CustomToast;
import com.ccd.filing.test.view.custom.CardNegativeView;
import com.ccd.filing.test.view.custom.CardView;
import com.ccd.filing.test.view.interfaces.BaseActivity;
import com.ccd.filing.test.view.interfaces.ReadCardIF;
import com.example.lv.progressbutton.CircularProgressButton;
import com.lv.tools.IDCard;


public class ReadCardActivity extends BaseActivity<ReadCardIF, ReadCardPresenter> implements ReadCardIF, View.OnClickListener {

    private com.ccd.filing.test.view.custom.CardView card_positive;
    private android.widget.ImageView read_card_hint;
    private com.ccd.filing.test.view.custom.CardNegativeView card_negative;
    private android.widget.TextView card_id;
    private CircularProgressButton read_id_view;
    private CircularProgressButton read_info_view;

    @Override
    protected ReadCardPresenter createPresenter(Bundle savedInstanceState) {
        setContentView(R.layout.activity_read_card);
        initViews();
        return new ReadCardPresenter();
    }

    private void initViews() {
        this.read_info_view = (CircularProgressButton) findViewById(R.id.read_card_view);
        this.read_id_view = (CircularProgressButton) findViewById(R.id.read_id_view);
        this.card_id = (TextView) findViewById(R.id.card_id);
        this.card_negative = (CardNegativeView) findViewById(R.id.card_negative);
        this.read_card_hint = (ImageView) findViewById(R.id.readCardHint);
        this.card_positive = (CardView) findViewById(R.id.card_positive);

        read_id_view.setOnClickListener(this);
        read_id_view.setIndeterminateProgressMode(true);

        read_info_view.setOnClickListener(this);
        read_info_view.setIndeterminateProgressMode(true);
    }

    @Override
    public void onClick(View v) {
        if (buttonIsImpregnable((CircularProgressButton) v)) {
            switch (v.getId()) {
                case R.id.read_id_view:
                    mPresenter.readId();
                    break;
                case R.id.read_card_view:
                    mPresenter.readCard();
                    break;
            }
        }
    }


    private boolean buttonIsImpregnable(CircularProgressButton view) {
        if (view.getProgress() == PROGRESS_BUTTON_DEFAULT) {
            view.setProgress(PROGRESS_BUTTON_RUN);
            return true;
        } else if (view.getProgress() == PROGRESS_BUTTON_STOP) {
            if (view == read_id_view) {
                setIdProgressButtonStatus(PROGRESS_BUTTON_DEFAULT);
            } else if (view == read_info_view) {
                setInfoProgressButtonStatus(PROGRESS_BUTTON_DEFAULT);
            }
        }
        return false;
    }


    @Override
    public void showCardId(final String cardId) {
        ReadCardActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                card_id.setText(cardId);
            }
        });
    }

    @Override
    public void showInfo(IDCard idCard) {
        if (idCard == null) {
            card_positive.setPersonfullName("");
            card_positive.setSex("");
            card_positive.setNation("");
            card_positive.setBrith("");
            card_positive.setHomeAddress("");
            card_positive.setIdCardNumber("");
            card_positive.setImage(null);

            card_negative.setBeginDate("");
            card_negative.setEndDate("");
            card_negative.setIssuingAuthority("");
        } else {
            card_positive.setPersonfullName(idCard.getName());
            card_positive.setSex(idCard.getSex());
            card_positive.setNation(idCard.getNation());
            card_positive.setBrith(idCard.getBirthday());
            card_positive.setHomeAddress(idCard.getAddress());
            card_positive.setIdCardNumber(idCard.getCardNo());
            card_positive.setImage(idCard.getPhoto());

            card_negative.setBeginDate(idCard.getBeginDate());
            card_negative.setEndDate(idCard.getEndDate());
            card_negative.setIssuingAuthority(idCard.getIssuingAuthority());
        }
        card_positive.postInvalidate();
        card_negative.postInvalidate();
    }

    @Override
    public void showMessage(final String message) {
        ReadCardActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CustomToast customToast = new CustomToast(ReadCardActivity.this, R.layout.toast_layout, 5000);
                customToast.setTitle("提示").setContent(message);
                customToast.show();
            }
        });
    }

    @Override
    public void setIdProgressButtonStatus(final int status) {
        ReadCardActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                read_id_view.setProgress(status);
            }
        });
    }
    @Override
    public void setInfoProgressButtonStatus(final int status) {
        ReadCardActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                read_info_view.setProgress(status);
            }
        });
    }

    @Override
    public void showImage(Bitmap bitmap) {
        card_positive.setImage(bitmap);


        card_positive.invalidate();
        card_negative.invalidate();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setHintColor(final int i) {
        ReadCardActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (i) {
                    case CARD_FIND:
                        read_card_hint.setImageResource(R.drawable.idcard_hint_find);
                        break;
                    case CARD_SELECT:
                        read_card_hint.setImageResource(R.drawable.idcard_hint_select);
                        break;
                    case CARD_READ:
                        read_card_hint.setImageResource(R.drawable.idcard_hint_read);
                        break;
                    default:
                        break;
                }
            }

        });
    }


}
