package com.forbitbd.fsecure.ui.admin.customers;

import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class CustomerPresenter implements CustomerContract.Presenter {

    private CustomerContract.View mView;
    private MyDatabaseRef myDatabaseRef;

    public CustomerPresenter(CustomerContract.View mView) {
        this.mView = mView;
        myDatabaseRef = MyDatabaseRef.getInstance();
    }

    @Override
    public void requestForAllUser() {
        myDatabaseRef.getUserRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> userList = new ArrayList<>();

                        for (DataSnapshot x: dataSnapshot.getChildren()){
                            userList.add(x.getValue(User.class));
                        }

                        mView.updateUserList(userList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void filterUser(List<User> userList, final String filterText) {
        Observable.from(userList)
                .filter(new Func1<User, Boolean>() {
                    @Override
                    public Boolean call(User user) {
                        return user.getEmail().startsWith(filterText.toLowerCase());
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        mView.addUser(user);
                    }
                });
    }
}
