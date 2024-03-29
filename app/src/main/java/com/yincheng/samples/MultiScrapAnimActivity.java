package com.yincheng.samples;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yincheng.samples.common.Direction;
import com.yincheng.samples.common.MagicActivity;
import com.yincheng.samples.updater.MultiScrapUpdater;
import com.yincheng.surfaceanimation.MagicMultiSurface;
import com.yincheng.surfaceanimation.MagicMultiSurfaceUpdater;
import com.yincheng.surfaceanimation.MagicSurfaceView;
import com.yincheng.surfaceanimation.MagicUpdater;
import com.yincheng.surfaceanimation.MagicUpdaterListener;

public class MultiScrapAnimActivity extends MagicActivity {

    private MagicSurfaceView mSurfaceView;
    private TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_scrap_anim);
        setTitle("ScrapAnim");
        mSurfaceView = (MagicSurfaceView) findViewById(R.id.magic_surface_view);
        mTvTest = (TextView) findViewById(R.id.tv_test);

        findViewById(R.id.view_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTvTest.getVisibility() == View.VISIBLE) {
                    hide();
                } else {
                    show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSurfaceView.onDestroy();
    }

    private void show() {
        MultiScrapUpdater mMultiUpdater = new MultiScrapUpdater(false, Direction.RIGHT);
        mMultiUpdater.addListener(new MagicUpdaterListener() {
            @Override
            public void onStart() {
                mTvTest.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onStop() {
                mTvTest.setVisibility(View.VISIBLE);
                mSurfaceView.setVisibility(View.GONE);
                mSurfaceView.release();
            }
        });
        mSurfaceView.render(new MagicMultiSurface(mTvTest, 20, 10).setUpdater(mMultiUpdater));
    }

    private void hide() {
        MultiScrapUpdater mMultiUpdater = new MultiScrapUpdater(true, Direction.RIGHT);
        mMultiUpdater.addListener(new MagicUpdaterListener() {
            @Override
            public void onStart() {
                mTvTest.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onStop() {
                mTvTest.setVisibility(View.INVISIBLE);
                mSurfaceView.setVisibility(View.GONE);
                mSurfaceView.release();
            }
        });
        mSurfaceView.render(new MagicMultiSurface(mTvTest, 20, 10).setUpdater(mMultiUpdater));
    }

    // 返回null禁用 单Surface转场动画
    @Override
    protected MagicUpdater getPageUpdater(boolean isHide) {
        return null;
    }

    // 启用多Surface转场动画
    @Override
    protected MagicMultiSurfaceUpdater getPageMultiUpdater(boolean isHide) {
        return new MultiScrapUpdater(isHide, Direction.BOTTOM);
    }

    // 纵向surface数量
    @Override
    protected int pageAnimRowCount() {
        return 20;
    }

    // 横向surface数量
    @Override
    protected int pageAnimColCount() {
        return 10;
    }
}
