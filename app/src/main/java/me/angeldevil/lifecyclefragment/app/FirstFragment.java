package me.angeldevil.lifecyclefragment.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;


/**
 * <p>Created by AngelDevil on 2018/5/28.</p>
 */
public class FirstFragment extends DebugFragment {

    private static final String EXTRA_LABEL = "label";

    private String label;

    public static FirstFragment newInstance(String label) {
        Bundle args = new Bundle();
        args.putString(EXTRA_LABEL, label);
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        fragment.label = label;
        return fragment;
    }

    private RadioButton btn0, btn1, btn2;
    private SecondFragment frag0;
    private SecondFragment frag1;
    private SecondFragment frag2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        label = getArguments() != null ? getArguments().getString(EXTRA_LABEL) : null;

        frag0 = SecondFragment.newInstance(label + "--Second-0");
        frag1 = SecondFragment.newInstance(label + "--Second-1");
        frag2 = SecondFragment.newInstance(label + "--Second-2");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        TextView tvLabel = view.findViewById(R.id.label);
        tvLabel.setText(label);

        btn0 = view.findViewById(R.id.second0);
        btn1 = view.findViewById(R.id.second1);
        btn2 = view.findViewById(R.id.second2);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_second_container, frag0)
                        .commit();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frag_second_container, frag1)
                        .commit();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment tmp = getChildFragmentManager().findFragmentByTag("frag2");
                if (tmp == null) {
                    getChildFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_second_container, FirstFragment.this.frag2, "frag2")
                            .commit();
                } else {
                    if (tmp.isHidden()) {
                        getChildFragmentManager().beginTransaction().show(tmp).commit();
//                        btn2.setChecked(true);
                    } else {
                        getChildFragmentManager().beginTransaction().hide(tmp).commit();
                        btn2.setChecked(false);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public String getName() {
        return label;
    }
}
