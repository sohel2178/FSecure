package com.forbitbd.fsecure.ui.admin.customers;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.SearchUserHolder> {

    private List<User> userList;
    private LayoutInflater inflater;

    private CustomerContract.View mView;

    public SearchUserAdapter(Fragment fm) {
        userList = new ArrayList<>();
        inflater = LayoutInflater.from(fm.getContext());
        mView = (CustomerContract.View) fm;
    }

    @NonNull
    @Override
    public SearchUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_search_user,parent,false);
        return new SearchUserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUser(User user){
        userList.add(user);

        int pos = userList.indexOf(user);
        notifyItemInserted(pos);
    }


    public void clear(){
        userList.clear();
        notifyDataSetChanged();
    }

    class SearchUserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView imageView;
        TextView tvName,tvEmail;

        public SearchUserHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.name);
            tvEmail = itemView.findViewById(R.id.email);
        }


        public void bind(User user){
            if(!user.getPhotoUri().equals("")){
                Picasso.with(itemView.getContext())
                        .load(user.getPhotoUri())
                        .into(imageView);
            }

            tvName.setText(user.getName());
            tvEmail.setText(user.getEmail());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mView.startCustomerActivity(userList.get(getAdapterPosition()));
        }
    }
}
