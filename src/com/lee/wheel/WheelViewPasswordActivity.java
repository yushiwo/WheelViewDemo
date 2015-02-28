package com.lee.wheel;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.lee.wheel.widget.TosAdapterView;
import com.lee.wheel.widget.TosGallery;
import com.lee.wheel.widget.WheelView;

public class WheelViewPasswordActivity extends Activity {
    int[] mData = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, };

    WheelView mWheel1 = null;
    WheelView mWheel2 = null;
    WheelView mWheel3 = null;
    WheelView mWheel4 = null;
    /** 密码显示textview */
    TextView mTextView = null;

    View mDecorView = null;

    boolean mStart = false;

    private TosAdapterView.OnItemSelectedListener mListener = new TosAdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
            formatData();
        }

        @Override
        public void onNothingSelected(TosAdapterView<?> parent) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wheel_password);

        mTextView = (TextView) findViewById(R.id.sel_password);

        mWheel1 = (WheelView) findViewById(R.id.wheel1);
        mWheel2 = (WheelView) findViewById(R.id.wheel2);
        mWheel3 = (WheelView) findViewById(R.id.wheel3);
        mWheel4 = (WheelView) findViewById(R.id.wheel4);
        //设置可以循环滑动
        mWheel1.setScrollCycle(true);
        mWheel2.setScrollCycle(true);
        mWheel3.setScrollCycle(true);
        mWheel4.setScrollCycle(true);

        mWheel1.setAdapter(new NumberAdapter());
        mWheel2.setAdapter(new NumberAdapter());
        mWheel3.setAdapter(new NumberAdapter());
        mWheel4.setAdapter(new NumberAdapter());

        mWheel1.setOnItemSelectedListener(mListener);
        mWheel2.setOnItemSelectedListener(mListener);
        mWheel3.setOnItemSelectedListener(mListener);
        mWheel4.setOnItemSelectedListener(mListener);

        formatData();

        mDecorView = getWindow().getDecorView();

        final Button btn = (Button) findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStart) {
                    // Stop
                    btn.setText("Start");
                } else {
                    // Start
                    btn.setText("Stop");

                    startScrolling();
                }

                mStart = !mStart;
            }
        });
    }

    private void startScrolling() {
        mWheel1.post(new ScrollRunnable(mWheel1));
        mWheel2.postDelayed(new ScrollRunnable(mWheel2), 200);
        mWheel3.postDelayed(new ScrollRunnable(mWheel3), 400);
        mWheel4.postDelayed(new ScrollRunnable(mWheel4), 600);
    }

    private void formatData() {
        int pos1 = mWheel1.getSelectedItemPosition();
        int pos2 = mWheel2.getSelectedItemPosition();
        int pos3 = mWheel3.getSelectedItemPosition();
        int pos4 = mWheel4.getSelectedItemPosition();

        String text = String.format("%d%d%d%d", pos1, pos2, pos3, pos4);
        mTextView.setText(text);
    }

    private class ScrollRunnable implements Runnable {
        WheelView mWheelView;

        public ScrollRunnable(WheelView wheelView) {
            mWheelView = wheelView;
        }

        @Override
        public void run() {
            int position = mWheelView.getSelectedItemPosition();
            int count = mWheelView.getCount();
            position = (position + 1) % count;

            mWheelView.setSelection(position);

            if (mStart) {
                mWheelView.postDelayed(this, 0);
            }
        }
    }

    private class NumberAdapter extends BaseAdapter {
        int mHeight = 50;

        public NumberAdapter() {
            mHeight = (int) Utils.pixelToDp(WheelViewPasswordActivity.this, mHeight);
        }

        @Override
        public int getCount() {
            return (null != mData) ? mData.length : 0;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView txtView = null;

            if (null == convertView) {
                convertView = new TextView(WheelViewPasswordActivity.this);
                convertView.setLayoutParams(new TosGallery.LayoutParams(-1, mHeight));

                txtView = (TextView) convertView;
                txtView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                txtView.setTextColor(Color.BLACK);
                txtView.setGravity(Gravity.CENTER);
            }

            String text = String.valueOf(mData[position]);
            if (null == txtView) {
                txtView = (TextView) convertView;
            }

            txtView.setText(text);

            return convertView;
        }
    }
}
