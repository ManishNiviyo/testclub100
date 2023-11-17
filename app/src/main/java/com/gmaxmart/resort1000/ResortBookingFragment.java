package com.gmaxmart.resort1000;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmaxmart.resort1000.adapters.Adapter;
import com.gmaxmart.resort1000.models.resortmodels.Constants;
import com.gmaxmart.resort1000.models.resortmodels.Employee;

import java.util.ArrayList;


public class ResortBookingFragment  extends Fragment {
    private String param1;
    private String param2;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            param1 = getArguments().getString("param1");
            param2 = getArguments().getString("param2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first,
                container, false);
    }

    public static ResortBookingFragment newInstance(String param1,
                                            String param2)
    {
        ResortBookingFragment fragment = new ResortBookingFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view,
                              Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // getting the employeelist
        ArrayList<Employee> employelist
                = Constants.getEmployeeData();

        // Assign employeelist to ItemAdapter
        Adapter itemAdapter = new Adapter(employelist);

        // Set the LayoutManager that
        // this RecyclerView will use.
        RecyclerView recyclerView
                = view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext()));

        // adapter instance is set to the
        // recyclerview to inflate the items.
        recyclerView.setAdapter(itemAdapter);
    }
}