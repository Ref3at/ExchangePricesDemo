package com.refaat.exchangepricesdemobyrefaat.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.MenuItem;
import android.view.View;

import com.refaat.exchangepricesdemobyrefaat.R;
import com.refaat.exchangepricesdemobyrefaat.databinding.FragmentCurrencyPairPriceListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrencyPairPriceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrencyPairPriceListFragment extends Fragment {

    private FragmentCurrencyPairPriceListBinding binding;

    public CurrencyPairPriceListFragment() {
        super(R.layout.fragment_currency_pair_price_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCurrencyPairPriceListBinding.bind(view);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections navDirections = CurrencyPairPriceListFragmentDirections.actionCurrencyPairPriceListFragmentToCurrencyPairPriceGraphFragment();
                Navigation.findNavController(view).navigate(navDirections);
            }
        });

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
                // save profile changes
                NavDirections navDirections = CurrencyPairPriceListFragmentDirections.actionCurrencyPairPriceListFragmentToAboutFragment();
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
        new SettingDialog().show(getParentFragmentManager(), "SettingDialog");
    }

}