package com.forbitbd.fsecure.ui.expenses.account;

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

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountHolder> {

    private List<Account> accountList;
    private LayoutInflater inflater;
    private AccountContract.View mView;

    public AccountAdapter(Fragment fragment) {
        this.accountList = new ArrayList<>();
        this.inflater = LayoutInflater.from(fragment.getContext());
        this.mView = (AccountContract.View) fragment;
    }

    @NonNull
    @Override
    public AccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_account,parent,false);
        return new AccountHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountHolder holder, int position) {
        Account account = accountList.get(position);
        holder.bindAccount(account);
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public void addAccount(Account account){
        this.accountList.add(account);
        int position = accountList.indexOf(account);
        notifyItemInserted(position);
    }

    class AccountHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvType;
        ImageView ivImage;

        public AccountHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            tvType = itemView.findViewById(R.id.type);
            ivImage = itemView.findViewById(R.id.image);
        }

        public void bindAccount(Account account){
            tvName.setText(account.getName());
            tvType.setText(itemView.getContext().getResources().getStringArray(R.array.account_type)[account.getType()]);

            switch (account.getType()){
                case 1:
                    ivImage.setColorFilter(ContextCompat.getColor(ivImage.getContext(), R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                    break;

                case 2:
                    ivImage.setColorFilter(ContextCompat.getColor(ivImage.getContext(), R.color.spring_green), android.graphics.PorterDuff.Mode.MULTIPLY);
                    break;
            }
        }
    }
}
