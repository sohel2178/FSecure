package com.forbitbd.fsecure.ui.expenses.transaction;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.model.Transaction;
import com.forbitbd.fsecure.utility.MyUtil;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {

    private List<Transaction> transactionList;
    private LayoutInflater inflater;
    private TransactionContract.View mView;

    public TransactionAdapter(Fragment fragment) {
        this.transactionList = new ArrayList<>();
        this.inflater = LayoutInflater.from(fragment.getContext());
        this.mView = (TransactionContract.View) fragment;
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_transaction,parent,false);
        return new TransactionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void addTransaction(Transaction transaction){
        transactionList.add(0,transaction);
        notifyItemInserted(0);
    }


    class TransactionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView purpose,transaction_date,from,to,amount;

        public TransactionHolder(@NonNull View itemView) {
            super(itemView);
            purpose =itemView.findViewById(R.id.name);
            transaction_date =itemView.findViewById(R.id.tranaction_date);
            from =itemView.findViewById(R.id.from);
            to =itemView.findViewById(R.id.to);
            amount =itemView.findViewById(R.id.amount);

        }

        @Override
        public void onClick(View view) {

        }

        public void bind(Transaction transaction){
            purpose.setText(transaction.getPurpose());
            transaction_date.setText(MyUtil.getStringDate(transaction.getDate()));
            from.setText(transaction.getFrom().getName());
            to.setText(transaction.getTo().getName());
            amount.setText(String.valueOf(transaction.getAmount()));
        }
    }
}
