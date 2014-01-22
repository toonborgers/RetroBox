package be.cegeka.retrobox.layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;

import java.util.List;


public abstract class GPlusListAdapter extends GenericBaseAdapter {

    public GPlusListAdapter(Context context, List<? extends Object> items) {
        super(context, new SpeedScrollListener(), items);
    }

    @Override
    protected void defineInterpolator() {
        interpolator = new DecelerateInterpolator();
    }

    @Override
    public View getAnimatedView(int position, View convertView, ViewGroup parent) {
        v = getRowView(position, convertView, parent);

        if (v != null && !positionsMapper.get(position) && position > previousPostition) {
            speed = scrollListener.getSpeed();

            animDuration = (((int) speed) == 0) ? ANIM_DEFAULT_SPEED : (long) (1 / speed * 15000);

            if (animDuration > ANIM_DEFAULT_SPEED)
                animDuration = ANIM_DEFAULT_SPEED;

            previousPostition = position;

            v.setTranslationX(0.0F);
            v.setTranslationY(height);
            v.setRotationX(45.0F);
            v.setScaleX(0.7F);
            v.setScaleY(0.55F);

            ViewPropertyAnimator localViewPropertyAnimator =
                    v.animate().rotationX(0.0F).rotationY(0.0F).translationX(0).translationY(0).setDuration(animDuration).scaleX(
                            1.0F).scaleY(1.0F).setInterpolator(interpolator);

            localViewPropertyAnimator.setStartDelay(0).start();
            positionsMapper.put(position, true);
        }
        return v;
    }

    protected abstract View getRowView(int position, View convertView, ViewGroup parent);

}
