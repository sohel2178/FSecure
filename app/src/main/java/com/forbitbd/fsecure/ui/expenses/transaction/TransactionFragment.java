package com.forbitbd.fsecure.ui.expenses.transaction;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.model.Transaction;
import com.forbitbd.fsecure.ui.expenses.account.AccountAdapter;
import com.forbitbd.fsecure.ui.transactionAdd.AddTransactionActivity;
import com.forbitbd.fsecure.utility.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment implements TransactionContract.View,View.OnClickListener {
    private static final int REQUEST_CODE=5000;

    private TransactionPresenter mPresenter;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mAdd;

    private TransactionAdapter adapter;


    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new TransactionPresenter(this);
        adapter = new TransactionAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_transaction, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_transaction);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(adapter);

        mAdd = view.findViewById(R.id.add);
        mAdd.setOnClickListener(this);

        mPresenter.getAllTransaction();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), AddTransactionActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE && resultCode==getActivity().RESULT_OK){
            //Todo Update Adapter Here

            Log.d("HHHHHHH","Called");

            Transaction transaction = (Transaction) data.getSerializableExtra(Constant.TRANSACTION);
            adapter.addTransaction(transaction);
        }
    }

    @Override
    public void updateAdapter(Transaction transaction) {
        adapter.addTransaction(transaction);
    }
}
