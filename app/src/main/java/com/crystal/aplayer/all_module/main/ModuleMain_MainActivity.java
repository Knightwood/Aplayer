package com.crystal.aplayer.all_module.main;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.crystal.aplayer.R;
import com.crystal.aplayer.all_module.home.repo.HomeDataProvider;
import com.crystal.aplayer.databinding.ModuleMainActivityMainBinding;
import com.crystal.module_base.base.ui.activity.BaseActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ModuleMain_MainActivity extends BaseActivity {
    private ModuleMainActivityMainBinding mainBinding;
    private NavController navController;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding=ModuleMainActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        //底部导航与Navigation
        navigationView=mainBinding.moduleMainBottomNav;

        navController= Navigation.findNavController(ModuleMain_MainActivity.this,R.id.module_main_fragment_nav);
        NavigationUI.setupWithNavController(navigationView,navController);
    }

}
