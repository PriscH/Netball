package com.prisch.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.prisch.R;

import java.util.LinkedList;
import java.util.List;

public class MisplaysFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_misplays, container, false);

        List<ImageButton> buttons = new LinkedList<ImageButton>();
        buttons.add((ImageButton)view.findViewById(R.id.button_badPass));
        buttons.add((ImageButton)view.findViewById(R.id.button_badCatch));

        for (ImageButton button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });
        }

        return view;
    }
}
