package com.forbitbd.fsecure.ui.main.nav;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.forbitbd.fsecure.R;
import com.forbitbd.fsecure.model.User;
import com.forbitbd.fsecure.singleton.MyDatabaseRef;
import com.forbitbd.fsecure.ui.admin.AdminActivity;
import com.forbitbd.fsecure.ui.expenses.ExpenseActivity;
import com.forbitbd.fsecure.ui.login.LoginActivity;
import com.forbitbd.fsecure.ui.main.MainActivity;
import com.forbitbd.fsecure.ui.main.alert.AlertFragment;
import com.forbitbd.fsecure.ui.main.home.HomeFragment;
import com.forbitbd.fsecure.ui.main.payment.PaymentFragment;
import com.forbitbd.fsecure.ui.main.profile.ProfileFragment;
import com.forbitbd.fsecure.ui.newExpenses.NewExpensesActivity;
import com.forbitbd.fsecure.utility.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawer extends Fragment implements View.OnClickListener,NavContract.View {


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;


    private CircleImageView ivProfile;
    private TextView tvName,tvEmail;
    private LinearLayout rvHome,rvAdmin,rvProfile, rvContactUs, rvLogOut,rvNotifications,rvAlart,rvPayment,rvExpenses;


    // View Initialize Here




    private NavPresenter mPresenter;


    public NavigationDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new NavPresenter(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


        //Initialize View

        initView(view);

        return view;
    }

    private void initView(View view) {
        ivProfile = view.findViewById(R.id.iv_profile);
        tvName = view.findViewById(R.id.name);
        tvEmail = view.findViewById(R.id.email);
        rvHome = view.findViewById(R.id.home);
        rvAdmin = view.findViewById(R.id.admin);
        rvProfile = view.findViewById(R.id.profile);
        rvContactUs = view.findViewById(R.id.contact_us);
        rvLogOut = view.findViewById(R.id.logout);
        rvNotifications = view.findViewById(R.id.notification);
        rvAlart = view.findViewById(R.id.alert);
        rvPayment = view.findViewById(R.id.payment);
        rvExpenses = view.findViewById(R.id.expenses);

        rvHome.setOnClickListener(this);
        rvAdmin.setOnClickListener(this);
        rvContactUs.setOnClickListener(this);
        rvLogOut.setOnClickListener(this);
        rvNotifications.setOnClickListener(this);
        rvAlart.setOnClickListener(this);
        rvProfile.setOnClickListener(this);
        rvPayment.setOnClickListener(this);
        rvExpenses.setOnClickListener(this);


        mPresenter.updateNav();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }



    public void setUp(int fragmentId, DrawerLayout layout, final Toolbar toolbar) {

        mDrawerLayout = layout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                //if user gonna not seen the drawer before thats mean the drawer is open for the first time

                /*if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    // save it in sharedpreferences
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWERR,mUserLearnedDrawer+"");

                    getActivity().invalidateOptionsMenu();
                }*/

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.home:
                mPresenter.itemClick(1);
                break;

            case R.id.admin:
                mPresenter.itemClick(2);
                break;

            case R.id.profile:
                mPresenter.itemClick(3);
                break;

            case R.id.notification:
                mPresenter.itemClick(4);
                break;

            case R.id.alert:
                mPresenter.itemClick(5);
                break;

            case R.id.payment:
                mPresenter.itemClick(6);
                break;

            case R.id.expenses:
                mPresenter.itemClick(7);
                break;

            case R.id.contact_us:
                mPresenter.itemClick(8);
                break;

            case R.id.logout:
                mPresenter.itemClick(9);
                break;

        }
    }

    @Override
    public void updateNav(User user) {

        if(!user.getPhotoUri().equals("")){
            Picasso.with(getContext())
                    .load(user.getPhotoUri())
                    .into(ivProfile);
        }

        if(user.getName()!=null){
            tvName.setText(user.getName());
        }

        if(user.getEmail()!=null){
            tvEmail.setText(user.getEmail());
        }

        if(user.getIsAdmin()==1){
            rvAdmin.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void loadHomeFragment() {
        mDrawerLayout.closeDrawer(Gravity.START);
        if(!(getFragmentManager().findFragmentById(R.id.main_container) instanceof HomeFragment)){
            getFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment()).commit();
        }
    }

    @Override
    public void startAdminActivity() {
        Intent intent = new Intent(getContext(), AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void loadProfile() {
        mDrawerLayout.closeDrawer(Gravity.START);
        if(!(getFragmentManager().findFragmentById(R.id.main_container) instanceof ProfileFragment)){
            getFragmentManager().beginTransaction().replace(R.id.main_container,new ProfileFragment()).commit();
        }
    }

    @Override
    public void loadNotification() {
        mDrawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void loadAlert() {
        mDrawerLayout.closeDrawer(Gravity.START);
        if(!(getFragmentManager().findFragmentById(R.id.main_container) instanceof AlertFragment)){
            getFragmentManager().beginTransaction().replace(R.id.main_container,new AlertFragment()).commit();
        }
    }

    @Override
    public void loadPayment() {
        mDrawerLayout.closeDrawer(Gravity.START);
        if(!(getFragmentManager().findFragmentById(R.id.main_container) instanceof PaymentFragment)){
            getFragmentManager().beginTransaction().replace(R.id.main_container,new PaymentFragment()).commit();
        }
    }

    @Override
    public void startExpenseActivity() {
        mDrawerLayout.closeDrawer(Gravity.START);
        Intent exIntent = new Intent(getContext(), NewExpensesActivity.class);
        startActivity(exIntent);
    }

    @Override
    public void contactUs() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", getString(R.string.mobi_phone), null));
        startActivity(intent);
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    public void logout() {
        mDrawerLayout.closeDrawer(Gravity.START);

        if(getActivity() instanceof MainActivity){
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.logout();
            startActivity(new Intent(getContext(),LoginActivity.class));
        }


    }
}
