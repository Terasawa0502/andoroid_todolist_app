package com.example.todolistapp.ui.top;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolistapp.R;

/**
 * TODOシート
 */
public class ToDoSheetFragement extends Fragment {

    private ToDoSheetFragement() {
    }

    public static ToDoSheetFragement newInstance() {
        ToDoSheetFragement fragment = new ToDoSheetFragement();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_do_sheet_fragement, container, false);
    }
}