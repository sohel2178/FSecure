package com.forbitbd.fsecure.ui.expenses.account;


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
import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.ui.accountAdd.AddAccountActivity;
import com.forbitbd.fsecure.utility.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements AccountContract.View ,View.OnClickListener{

    private static final int REQUEST_CODE=5000;

    private AccountPresenter mPresenter;

    //View
    private RecyclerView mRecyclerView;
    private FloatingActionButton mAdd;

    private AccountAdapter adapter;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AccountPresenter(this);
        adapter = new AccountAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
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

        mPresenter.requestForGetAllAccount();
    }

    @Override
    public void onClick(View view) {
        mPresenter.addButtonClick();
    }

    @Override
    public void startAddAccountActivity() {
        Intent intent = new Intent(getContext(), AddAccountActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void updateAdapter(Account account) {
        adapter.addAccount(account);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("JJJJJJ","onActivityResult Called");

        if(requestCode==REQUEST_CODE && resultCode==getActivity().RESULT_OK){
            Account account = (Account) data.getSerializableExtra(Constant.ACCOUNT);
            adapter.addAccount(account);
        }
    }
}
