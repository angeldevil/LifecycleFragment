package me.angeldevil.lifecyclefragment.app;

import android.content.Context;
import android.support.annotation.CallSuper;

import me.angeldevil.lifecyclefragment.LifecycleFragment;

/**
 * <p>Created by AngelDevil on 2018/5/28.</p>
 */
public abstract class DebugFragment extends LifecycleFragment {

    private OnVisibleListener onVisibleListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof OnVisibleListener) {
            onVisibleListener = (OnVisibleListener) getActivity();
        }
    }

    @CallSuper
    @Override
    protected void onInvisible() {
        if (onVisibleListener != null) {
            onVisibleListener.onInvisible(this);
        }
    }

    @CallSuper
    @Override
    protected void onVisible(boolean firstVisible) {
        if (onVisibleListener != null) {
            onVisibleListener.onVisible(this, firstVisible);
        }
    }

    public abstract String getName();

    public interface OnVisibleListener {
        void onVisible(DebugFragment fragment, boolean firstVisible);

        void onInvisible(DebugFragment fragment);
    }
}
