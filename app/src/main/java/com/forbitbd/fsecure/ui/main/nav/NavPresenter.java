package com.forbitbd.fsecure.ui.main.nav;

import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class NavPresenter implements NavContract.Presenter {

    private NavContract.View mView;

    private FirebaseUser mCurrentUser;
    private MyDatabaseRef myDatabaseRef;

    public NavPresenter(NavContract.View mView) {
        this.mView = mView;
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        myDatabaseRef = MyDatabaseRef.getInstance();
    }

    @Override
    public void updateNav() {
        if(mCurrentUser!=null){
            myDatabaseRef.getUserRef()
                    .child(mCurrentUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue()!=null){
                                User user = dataSnapshot.getValue(User.class);
                                mView.updateNav(user);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    @Override
    public void itemClick(int action) {
        switch (action){
            case 1:
                mView.loadHomeFragment();
                break;

            case 2:
                mView.startAdminActivity();
                break;

            case 3:
                mView.loadProfile();
                break;

            case 4:
                mView.loadNotification();
                break;

            case 5:
                mView.loadAlert();
                break;

            case 6:
                mView.loadPayment();
                break;

            case 7:
                mView.startExpenseActivity();
                break;

            case 8:
                mView.contactUs();
                break;

            case 9:
                mView.logout();
                break;
        }
    }
}
