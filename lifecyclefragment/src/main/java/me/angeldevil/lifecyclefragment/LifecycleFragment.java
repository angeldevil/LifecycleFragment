package me.angeldevil.lifecyclefragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.List;

/**
 * Simplify fragment's lifecycle to onVisible & onInvisible
 * <p>Created by AngelDevil on 2018/5/28.</p>
 */
public class LifecycleFragment extends Fragment {

    /**
     * Current visible state, combines self state and parental state.
     * <p>
     * Also used to avoid notify sate changes multiple times for the same state.
     */
    private boolean currentVisible = false;

    /**
     * Flag to indicate onStart and onStop
     */
    private boolean started = false;

    /**
     * First visible or not
     */
    private boolean firstVisible = true;

    private Handler handler = new Handler(Looper.getMainLooper());

    private Runnable adjustStateRunnable = new Runnable() {
        @Override
        public void run() {
            adjustState();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        started = true;
        postAdjustState();
    }

    @Override
    public void onStop() {
        super.onStop();
        started = false;
        postAdjustState();
    }

    @CallSuper
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        postAdjustState();
    }

    @CallSuper
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        postAdjustState();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postAdjustState();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        firstVisible = true;
    }

    /**
     * Post adjust state to next message loop, otherwise sometimes the state flicks like this:<br>
     * onVisible -> onInvisible -> onVisible
     * <p>
     * For example, when used in nested ViewPager, each with a FragmentPagerAdapter,
     * when switches from Outer1-Inner1 to Outer4-Inner1, then switches back.
     * <pre>
     *     <ol>
     *         <li>Outer1-Inner1 will recreate, causes onVisible be invoked in onStart<br></li>
     *         <li>The new created FragmentPagerAdapter.mCurrentPrimaryItem is null, so setUserVisibleHint(false)
     *         will be called, causes onInvisible be invoked.</li>
     *         <li>Then ViewPager restores current item, call FragmentPagerAdapter.setPrimaryItem, so
     *         setUserVisibleHint(true) will be called, causes onVisible be invoked again</li>
     *     </ol>
     * </pre>
     */
    private void postAdjustState() {
        handler.removeCallbacks(adjustStateRunnable);
        handler.post(adjustStateRunnable);
    }

    /**
     * Adjust visible state and dispatch state change to children
     */
    private void adjustState() {
        boolean visible = started && getUserVisibleHint() && isVisible() && isParentVisible();
        if (visible != currentVisible) {
            currentVisible = visible;
            if (visible) {
                onVisible(firstVisible);
                firstVisible = false;
                dispatchVisibleChanged();
            } else {
                dispatchVisibleChanged();
                onInvisible();
            }
        }
    }

    private void dispatchVisibleChanged() {
        if (getHost() == null) {
            return;
        }
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof LifecycleFragment) {
                    ((LifecycleFragment) fragment).adjustState();
                }
            }
        }
    }

    public boolean isCurrentVisible() {
        return currentVisible;
    }

    private boolean isParentVisible() {
        boolean visible = true;
        Fragment parent = getParentFragment();
        while (parent instanceof LifecycleFragment && visible) {
            visible = ((LifecycleFragment) parent).isCurrentVisible();
            parent = parent.getParentFragment();
        }
        return visible;
    }

    /**
     * Fragment is visible to user
     *
     * @param firstVisible Whether is the first time being visible to user
     */
    protected void onVisible(boolean firstVisible) {

    }

    /**
     * Fragment is invisible to user
     */
    protected void onInvisible() {

    }
}
