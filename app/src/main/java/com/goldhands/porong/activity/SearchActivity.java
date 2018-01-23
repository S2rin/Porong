package com.goldhands.porong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.goldhands.porong.R;
import com.goldhands.porong.UI.BottomNavigation;
import com.goldhands.porong.UI.Divider;
import com.goldhands.porong.UI.SimpleTouchCallback;
import com.goldhands.porong.activity.upload.UploadActivity_1;
import com.goldhands.porong.realm.SearchItem;
import com.goldhands.porong.realm.SearchItemAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class SearchActivity extends AppCompatActivity {
    //Header Navigation
    private DrawerLayout mDrawerLayout;

    //RecyclerView
    private RecyclerView recycler;
    private SearchItemAdapter adapter;

    //Spinner
    String selectStr;

    //SearchEditText
    private EditText editText;
    private String editStr;
    private InputMethodManager imm;

    //SearchDeleteAll
    private Button deleteAll;

    //Realm
    private static final String TAG = "Porong";
    private Realm realm;
    private RealmResults<SearchItem> results;
    private RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            adapter.update(results);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /**
         * Search RecyclerView
         */
        realm = Realm.getDefaultInstance();
        results = realm.where(SearchItem.class).findAllAsync();
        recycler = (RecyclerView) findViewById(R.id.search_recycler);
        recycler.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));

        adapter = new SearchItemAdapter(this, realm, results);
        recycler.setAdapter(adapter);

        SimpleTouchCallback callback = new SimpleTouchCallback(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recycler);

        /**
         * recent_search_word Delete All
         */

        deleteAll = (Button) findViewById(R.id.delete_all);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAlert();
            }
        });


        /**
         *  header Navigation (Toolbar)
         */
        Toolbar hToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(hToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = item.getItemId();
                switch (id) {
                    default:
                        showDialog();
                        break;
                }
                return true;
            }
        });

        /**
         *  Search Spinner
         */
        Spinner spinner = (Spinner) findViewById(R.id.spineer);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectStr = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**
         * Search Box (EditText)
         */
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editText = (EditText) findViewById(R.id.searchEditText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_SEARCH:
                        //KeyBoard Search Action
                        if(checkString(editText.getText().toString())){
                            //TODO: 검색 기능 & RecyclerView add
                            hideKeyBoard();
                            addRecyclerView();
                            return true;
                        }
                    default:
                        return false;
                }
            }
        });

        /**
         * Search Navigation (toolbar)
         */
        Toolbar sToolbar = (Toolbar) findViewById(R.id.toolbar_search);
        sToolbar.inflateMenu(R.menu.search_menu);
        sToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.search_btn:
                        if(checkString(editText.getText().toString())){
                            //TODO: 검색 기능 & RecyclerView add
                            hideKeyBoard();
                            addRecyclerView();
                        }

                        return true;
                }
                return false;
            }
        });

        /**
         * Bottom Navigation
         */
        BottomNavigation bn = new BottomNavigation();
        bn.bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bn.navigationInit(bn.bnve);
        bn.bnve.setCurrentItem(1);
        bn.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.i_home:
                        intent = new Intent(SearchActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.i_search:
                        break;
                    case R.id.i_upload:
                        intent = new Intent(SearchActivity.this, UploadActivity_1.class);
                        startActivity(intent);
                        break;
                    case R.id.i_recommend:
                        break;
                    case R.id.i_mypage:
                        intent = new Intent(SearchActivity.this, MyPageActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(SearchActivity.this,SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        results.addChangeListener(mChangeListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        results.removeChangeListener(mChangeListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void deleteAlert(){

        MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(this)
                .setTitle(R.string.information)
                .setIcon(R.drawable.ic_info_black_24dp)
                .setDescription(R.string.delete_dialog_content)
                .setCancelable(true)
                .setPositiveText(R.string.delete)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //TODO: deleteAll
                    }
                })
                .setNegativeText(R.string.cancel)
                .withIconAnimation(false)
                .show();

    }

    public void showDialog() {
        MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(this)
                .setTitle(R.string.information)
                .setIcon(R.drawable.ic_info_black_24dp)
                .setDescription(R.string.search_description)
                .setCancelable(true)
                .setPositiveText(R.string.ok)
                .withIconAnimation(false)
                .show();
    }

    public boolean checkString(String str){
        str = str.trim();
        if(str.equals("")){
            Toast.makeText(getApplicationContext(), "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            editStr = str;
            return true;
        }
    }

    public void hideKeyBoard(){
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void addRecyclerView(){
        String searchItem = editStr;
        SearchItem search = new SearchItem(searchItem);
        realm.beginTransaction();
        realm.copyToRealm(search);
        realm.commitTransaction();
    }
}
