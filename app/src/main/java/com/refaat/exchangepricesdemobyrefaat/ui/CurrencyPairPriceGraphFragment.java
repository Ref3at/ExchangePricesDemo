package com.refaat.exchangepricesdemobyrefaat.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.refaat.exchangepricesdemobyrefaat.R;
import com.refaat.exchangepricesdemobyrefaat.databinding.FragmentCurrencyPairPriceGraphBinding;
import com.refaat.exchangepricesdemobyrefaat.databinding.FragmentCurrencyPairPriceListBinding;


public class CurrencyPairPriceGraphFragment extends Fragment {

    FragmentCurrencyPairPriceGraphBinding binding;

    public CurrencyPairPriceGraphFragment() {
        super(R.layout.fragment_currency_pair_price_graph);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCurrencyPairPriceGraphBinding.bind(view);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                // navigate to settings screen
                showSettingDialog();
                return true;
            }
            case R.id.action_about: {
                // save about screen
                NavDirections navDirections = CurrencyPairPriceGraphFragmentDirections.actionCurrencyPairPriceGraphFragmentToAboutFragment();
                Navigation.findNavController(getView()).navigate(navDirections);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    void showSettingDialog() {
        new SettingDialog().show(getParentFragmentManager(),"SettingDialog");
    }


}