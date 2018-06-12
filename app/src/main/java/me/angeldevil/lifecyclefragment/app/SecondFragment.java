package me.angeldevil.lifecyclefragment.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * <p>Created by AngelDevil on 2018/5/28.</p>
 */
public class SecondFragment extends DebugFragment {

    private static final String EXTRA_LABEL = "label";

    private String label;

    public static SecondFragment newInstance(String label) {
        Bundle args = new Bundle();
        args.putString(EXTRA_LABEL, label);
        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(args);
        fragment.label = label;
        return fragment;
    }

    private TabLayout tab;
    private ViewPager pager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        label = getArguments() != null ? getArguments().getString(EXTRA_LABEL) : null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        TextView tvLabel = view.findViewById(R.id.frag_label);
        tvLabel.setText(label);

        pager = view.findViewById(R.id.frag_pager);
        pager.setAdapter(new ThirdPagerAdapter(getChildFragmentManager(), label));

        tab = view.findViewById(R.id.frag_tab);
        tab.setupWithViewPager(pager);

        return view;
    }

    @Override
    public String getName() {
        return label;
    }
}
