package me.angeldevil.lifecyclefragment.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * <p>Created by AngelDevil on 2018/5/28.</p>
 */
public class ThirdFragment extends DebugFragment {

    private static final String EXTRA_LABEL = "label";

    private String label;

    public static ThirdFragment newInstance(String label) {
        Bundle args = new Bundle();
        args.putString(EXTRA_LABEL, label);
        ThirdFragment fragment = new ThirdFragment();
        fragment.setArguments(args);
        fragment.label = label;
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        label = getArguments() != null ? getArguments().getString(EXTRA_LABEL) : null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setPadding(20, 20, 20, 20);
        tv.setText(label);

        tv.setBackgroundColor(Color.parseColor("#B950F969"));
        return tv;
    }

    @Override
    public String getName() {
        return label;
    }
}
