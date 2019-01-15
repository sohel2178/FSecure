package com.forbitbd.fsecure.ui.expenses.head;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.model.Account;
import com.forbitbd.fsecure.model.Head;
import com.forbitbd.fsecure.ui.expenses.account.AccountContract;

import java.util.ArrayList;
import java.util.List;

public class HeadAdapter extends RecyclerView.Adapter<HeadAdapter.AccountHolder> {

    private List<Head> headList;
    private LayoutInflater inflater;
    private AccountContract.View mView;

    public HeadAdapter(Fragment fragment) {
        this.headList = new ArrayList<>();
        this.inflater = LayoutInflater.from(fragment.getContext());
        this.mView = (AccountContract.View) fragment;
    }

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_head,parent,false);
        return new AccountHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
        Head head = headList.get(position);
        holder.bindAccount(head);
    }

    @Override
    public int getItemCount() {
        return headList.size();
    }

    public void addHead(Head account){
        this.headList.add(account);
        int position = headList.indexOf(account);
        notifyItemInserted(position);
    }

    class AccountHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView ivImage;

        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            ivImage = itemView.findViewById(R.id.image);
        }

        public void bindAccount(Head head){
            tvName.setText(head.getName());
        }
    }
}
