package com.forbitbd.fsecure.ui.searchUser;

import com.forbitbd.fsecure.model.ShareVehicle;
import com.forbitbd.fsecure.model.SharedUser;
import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SearchUserPresenter implements SearchUserContract.Presenter {

    private SearchUserContract.View mView;
    private MyDatabaseRef myDatabaseRef;
    private FirebaseUser mCurrentUser;

    private List<User> userList;


    public SearchUserPresenter(SearchUserContract.View mView) {
        this.mView = mView;
        this.myDatabaseRef = MyDatabaseRef.getInstance();
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        userList = new ArrayList<>();
    }


    public boolean isAlreadyLoded(String query){
        boolean retbool = false;
        if(userList.size()==0){
            return false;
        }

        for (User x: userList){
            if(x.getEmail().toLowerCase().startsWith(query.toLowerCase())){
                retbool = true;
                break;
            }
        }
        return retbool;
    }


    public void filter(String text){
        List<User> tempList = new ArrayList<>();

        for (User x: userList){
            if(x.getEmail().toLowerCase().startsWith(text.toLowerCase())){
                tempList.add(x);
            }
        }

        mView.renderAdapter(tempList);
    }

    @Override
    public void loadData(final String text) {
        if(isAlreadyLoded(text)){
            filter(text);
        }else{
            myDatabaseRef.getUserRef().orderByChild("email")
                    .startAt(text.toLowerCase()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for (DataSnapshot x: dataSnapshot.getChildren()){
                            User user = x.getValue(User.class);

                            if(!userExist(user)){
                                userList.add(x.getValue(User.class));
                            }

                        }

                        filter(text);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void shareClick(ShareVehicle shareVehicle, final String uid, final String vehicleId) {
        mView.showDialog();

        myDatabaseRef.getSharedVehicleRef(uid)
                .child(vehicleId)
                .setValue(shareVehicle, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        SharedUser sharedUser = new SharedUser(uid,1);

                        myDatabaseRef.getSharedUserRef(vehicleId).child(uid).setValue(sharedUser, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                mView.shareDone(uid);
                            }
                        });
                    }
                });

    }


    private boolean userExist(User user){
        for (User x: userList){
            if (x.getEmail().equals(user.getEmail())){
                return true;
            }
        }

        return false;
    }
}
