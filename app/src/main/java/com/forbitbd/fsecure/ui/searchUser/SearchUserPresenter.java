package com.forbitbd.fsecure.ui.searchUser;

import android.util.Log;

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

public class SearchUserPresenter implements SearchUserContract.Presenter {

    private SearchUserContract.View mView;
    private MyDatabaseRef myDatabaseRef;
    private FirebaseUser mCurrentUser;

    public SearchUserPresenter(SearchUserContract.View mView) {
        this.mView = mView;
        this.myDatabaseRef = MyDatabaseRef.getInstance();
        this.mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void requestForData(final String value, int before, int after) {

        Log.d("HHHHH","Calll");

        if(after>before){
            if(after==1){
                //Request

                myDatabaseRef.getUserRef().orderByChild("email")
                        .startAt(value).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot x: dataSnapshot.getChildren()){
                            User user = x.getValue(User.class);
                            if(!user.getUid().equals(mCurrentUser.getUid())){
                                //userList.add(user);
                                Log.d("UUUUU","Calll");
                                mView.updateData(user,value);

                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }else{
               mView.updateAdapter(value);
            }
        }else {
            mView.updateAdapter(value);
        }

    }

    @Override
    public void shareClick(ShareVehicle shareVehicle, final String uid, final String projectId) {
        mView.showDialog();

        myDatabaseRef.getSharedVehicleRef(uid)
                .child(projectId)
                .setValue(shareVehicle, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        SharedUser sharedUser = new SharedUser(uid,1);

                        myDatabaseRef.getSharedUserRef(projectId).child(uid).setValue(sharedUser, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                mView.shareDone(uid);

                            }
                        });
                    }
                });
    }
}
