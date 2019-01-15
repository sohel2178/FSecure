package com.forbitbd.fsecure.ui.expenses.head;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.ui.expenses.account.AccountAdapter;
import com.forbitbd.fsecure.ui.expenses.account.AccountPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadFragment extends Fragment implements HeadContract.View,View.OnClickListener {

    private static final int REQUEST_CODE=5000;

    private HeadPresenter mPresenter;

    //View
    private RecyclerView mRecyclerView;
    private FloatingActionButton mAdd;

    private HeadAdapter adapter;


    public HeadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new HeadPresenter(this);
        adapter = new HeadAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_head, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_accounts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);
        mAdd = view.findViewById(R.id.add);
        mAdd.setOnClickListener(this);

        mPresenter.requestForGetAllHeads();
    }

    @Override
    public void startAddHeadActivity() {

    }

    @Override
    public void updateAdapter(Head head) {
        adapter.addHead(head);
    }

    @Override
    public void onClick(View view) {

    }
}
