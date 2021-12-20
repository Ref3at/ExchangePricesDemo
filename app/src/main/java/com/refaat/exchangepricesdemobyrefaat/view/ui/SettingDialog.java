package com.refaat.exchangepricesdemobyrefaat.view.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.refaat.exchangepricesdemobyrefaat.R;
import com.refaat.exchangepricesdemobyrefaat.databinding.DialogSettingLayoutBinding;
import com.refaat.exchangepricesdemobyrefaat.utils.PreferencesManager;
import com.refaat.exchangepricesdemobyrefaat.viewModel.MainActivityViewModel;

public class SettingDialog extends androidx.fragment.app.DialogFragment {
    DialogSettingLayoutBinding binding;

    SettingDialog() {
        super(R.layout.dialog_setting_layout);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_NoActionBar_MinWidth);
        setCancelable(true);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DialogSettingLayoutBinding.bind(view);


        //slider
        LabelFormatter labelFormatter = new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return ((int) value) + " Sec";
            }
        };

        binding.slider.setLabelFormatter(labelFormatter);

        binding.slider.setValue(PreferencesManager.getInstance().getTheSelectedInterval());

        binding.txtSelectedTime.setText(((int) binding.slider.getValue()) + " Sec");
        binding.slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                binding.txtSelectedTime.setText(((int) slider.getValue()) + " Sec");
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                binding.txtSelectedTime.setText(((int) slider.getValue()) + " Sec");
            }
        });


        binding.btnAdjust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesManager.getInstance().saveTheSelectedInterval(((int) binding.slider.getValue()));
                MainActivityViewModel mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
                mainActivityViewModel.setTheSelectedInterval((int) binding.slider.getValue());

                dismiss();
            }
        });
    }
}
