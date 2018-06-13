package com.example.pc.intrn;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pc.intrn.Model.items;
import com.example.pc.intrn.Adapter.Myadapter;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Myadapter myadapter;

    private static final int RC_SIGN_IN = 123;
    private RecyclerView recyclerView;
    private ArrayList<items> itemslist;
    private Boolean isConnected;
    private ProgressDialog progress2;
    private ProgressDialog progressl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = MainActivity.this;

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            progressl = new ProgressDialog(MainActivity.this);
            progressl.setTitle("Logging Out");
            progressl.setMessage("...please wait");
            progressl.setCancelable(false); // disable dismiss by tapping outside of the dialog

            progress2 = new ProgressDialog(MainActivity.this);
            progress2.setTitle("Loading ");
            progress2.setMessage("...please wait");
            progress2.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress2.show();


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Demo App");
            setSupportActionBar(toolbar);


            itemslist = new ArrayList<items>();
            items one = new items(getDrawable(R.drawable.image1),
                    "Intel G4560 7th Genenration Pentium Dual Core Processor",
                    "Rs.4,633.00", "Intel");
            items two = new items(getDrawable(R.drawable.image2),
                    "Intel 8Th Generation I3 8100 3.6GHZ Quad Core/ 4 Core/ 4 Threads / Coffee Lake / Lga-1151 Socket / Ddr4",
                    "Rs.9,633.00", "Intel");
            items three = new items(getDrawable(R.drawable.image3),
                    "Intel Core i5-8400 BX80684I58400 Processor",
                    "Rs.11,633.00", "Intel");
            items four = new items(getDrawable(R.drawable.image4),
                    "Intel BX80684I78700K 8th Gen Core i7-8700K 3.7 GHz Processor",
                    "Rs.13,633.00", "Intel");
            items five = new items(getDrawable(R.drawable.image3),
                    "Intel Core i5-8400 BX80684I58400 Processor",
                    "Rs.11,633.00", "Intel");


            itemslist.add(one);
            itemslist.add(two);
            itemslist.add(three);
            itemslist.add(four);
            itemslist.add(five);

            myadapter = new Myadapter(itemslist, MainActivity.this);


            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() != null) {
                // already signed in
                setview();
            } else {

                sign_in();
                // not signed in
            }


        } else {
            startActivity(new Intent(MainActivity.this, NoConnectionActivity.class));
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                // todo: goto back activity from here
                progressl.show();

                signout();

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.app_bar, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }


    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                myadapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void setview() {

progress2.dismiss();
        recyclerView.setAdapter(myadapter);

    }


    private void sign_in() {

// ...


// Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                // new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()

                //  ,new AuthUI.IdpConfig.FacebookBuilder().build(),
                //  new AuthUI.IdpConfig.TwitterBuilder().build()
        );


        startActivityForResult(


                AuthUI.getInstance()

                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false, true)
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_a)      // Set logo drawable
                        .setTheme(R.style.GreenTheme)      // Set theme
                        .build(),
                RC_SIGN_IN);


// Create and launch sign-in intent
       /* startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
*/

    }

    private void signout() {


        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                        progressl.dismiss();
                        sign_in();
                        // ...
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in

                progress2.dismiss();
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                setview();
                // ...
            } else {
                progress2.dismiss();
                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
