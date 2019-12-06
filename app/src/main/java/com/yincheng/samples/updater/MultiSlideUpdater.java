package com.yincheng.samples.updater;

import com.yincheng.samples.common.AnimHelper;
import com.yincheng.samples.common.Direction;
import com.yincheng.samples.common.FloatValueAnimator;
import com.yincheng.surfaceanimation.MagicMultiSurface;
import com.yincheng.surfaceanimation.MagicMultiSurfaceUpdater;
import com.yincheng.surfaceanimation.Vec;

public class MultiSlideUpdater extends MagicMultiSurfaceUpdater {

    private final float MOVING_TOTAL_TIME = 0.4f;

    private FloatValueAnimator mAnimator = new FloatValueAnimator(600);
    private boolean mIsHide;
    private boolean mIsVertical;
    private int mDirection;
    private AnimHelper mAnimHelper;
    private Vec mAxis;

    public MultiSlideUpdater(boolean isHide, int direction) {
        mIsHide = isHide;
        mDirection = direction;
        mAnimator.addListener(new FloatValueAnimator.FloatValueAnimatorListener() {
            @Override
            public void onAnimationUpdate(float value) {
                mAnimHelper.update(value);
                notifyChanged();
            }

            @Override
            public void onStop() {
                notifyChanged();
            }
        });
    }

    @Override
    protected void willStart(MagicMultiSurface surface) {
        mIsVertical = Direction.isVertical(mDirection);
        mAnimHelper = new AnimHelper(surface, mDirection, mIsHide);
        mAxis = mIsVertical ? new Vec(0, 1, 0) : new Vec(1, 0, 0);
    }

    @Override
    protected void didStart(MagicMultiSurface surface) {
        mAnimator.start(!mIsHide);
    }

    @Override
    protected void didStop(MagicMultiSurface surface) {

    }

    @Override
    protected void updateBegin(MagicMultiSurface surface) {

    }

    @Override
    protected void update(MagicMultiSurface surface, int r, int c, float[] matrix, Vec offset, Vec color) {
        float startTime = mAnimHelper.getStartAnimTime(
                mIsVertical ? r : c,
                mIsVertical ? surface.getRows() : surface.getCols(),
                false, 1 - MOVING_TOTAL_TIME);
        float ratio = mAnimHelper.getAnimProgress(startTime, MOVING_TOTAL_TIME);
        float len = mAnimHelper.getMoveDistance(AnimHelper.MOVE_TYPE_SCENE, startTime, MOVING_TOTAL_TIME);
        reset(matrix);
        if (mIsVertical) {
            offset.y(offset.y() + len);
        } else {
            offset.x(offset.x() + len);
        }
        translate(matrix, offset);
        rotate(matrix, mAxis, 110 * ratio);
        color.a(1 - ratio);
    }

    @Override
    protected void updateEnd(MagicMultiSurface surface) {
        if (mAnimator.isStopped()) {
            stop();
        }
    }
}
