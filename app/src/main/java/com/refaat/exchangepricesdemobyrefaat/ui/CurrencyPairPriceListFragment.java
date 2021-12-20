package com.refaat.exchangepricesdemobyrefaat.ui;

import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.refaat.exchangepricesdemobyrefaat.R;
import com.refaat.exchangepricesdemobyrefaat.data.CurrencyPairItem;
import com.refaat.exchangepricesdemobyrefaat.databinding.FragmentCurrencyPairPriceListBinding;
import com.refaat.exchangepricesdemobyrefaat.utils.Helper;
import com.refaat.exchangepricesdemobyrefaat.view.ItemAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrencyPairPriceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrencyPairPriceListFragment extends Fragment {

    private FragmentCurrencyPairPriceListBinding binding;
    MainActivityViewModel mainActivityViewModel;
    ItemAdapter itemAdapter;
    private boolean shouldPlayTheBeepSound;
    Observer observer;

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

        initViews();

        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);


        binding.textClock.setText(Helper.getDateString());




        mainActivityViewModel.getTheSelectedInterval().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.textRefreshRate.setText("The data is refreshed every "+integer+" seconds");
            }
        });

        observer = new Observer<List<CurrencyPairItem>>() {
            @Override
            public void onChanged(List<CurrencyPairItem> currencyPairItems) {
                playTheBeepSound();
                itemAdapter.updateItems(currencyPairItems);
            }
        };

        mainActivityViewModel.getCurrencyPairListMutableLiveData().observe(this, observer);

    }

    private void initViews() {

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.recyclerView.setHasFixedSize(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(new ColorDrawable(R.color.white));
        itemDecoration.setDrawable(getContext().getResources().getDrawable(R.drawable.line_divider));
        binding.recyclerView.addItemDecoration(itemDecoration);
        itemAdapter = new ItemAdapter();
        binding.recyclerView.setAdapter(itemAdapter);

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
    public void onStop() {
        super.onStop();
        shouldPlayTheBeepSound=false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.recyclerView.setAdapter(null);
        binding = null;
        mainActivityViewModel.getCurrencyPairListMutableLiveData().removeObserver(observer);
    }


    void showSettingDialog() {
        new SettingDialog().show(getParentFragmentManager(), "SettingDialog");
    }

    /**
     * Play the sound from first update not the initial values
     */
    void playTheBeepSound() {

        if (!shouldPlayTheBeepSound) {
            shouldPlayTheBeepSound = true;
            return;
        }

        MediaPlayer mediaPlayer = MediaPlayer.create(requireActivity(), R.raw.beep_beep);
        mediaPlayer.start();
    }

}