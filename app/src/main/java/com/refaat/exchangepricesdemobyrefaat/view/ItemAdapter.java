package com.refaat.exchangepricesdemobyrefaat.view;

import static com.refaat.exchangepricesdemobyrefaat.utils.ChangeStatus.DECREASE;
import static com.refaat.exchangepricesdemobyrefaat.utils.ChangeStatus.INCREASE;
import static com.refaat.exchangepricesdemobyrefaat.utils.Constants.FLASH_ANIMATION_PERIOD;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.refaat.exchangepricesdemobyrefaat.R;
import com.refaat.exchangepricesdemobyrefaat.model.CurrencyPairItem;
import com.refaat.exchangepricesdemobyrefaat.databinding.ItemViewBinding;
import com.refaat.exchangepricesdemobyrefaat.view.ui.CurrencyPairPriceListFragmentDirections;

import java.util.ArrayList;
import java.util.List;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    List<CurrencyPairItem> currencyPairItems = new ArrayList<>();


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemViewBinding binding = ItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindViews(currencyPairItems.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections navDirections = CurrencyPairPriceListFragmentDirections.actionCurrencyPairPriceListFragmentToCurrencyPairPriceGraphFragment(position);
                Navigation.findNavController(view).navigate(navDirections);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyPairItems.size();
    }

    public void updateItems(List<CurrencyPairItem> currencyPairItems) {
        this.currencyPairItems.clear();
        this.currencyPairItems.addAll(currencyPairItems);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemViewBinding binding;

        public ItemViewHolder(ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindViews(CurrencyPairItem currencyPairItem) {
            binding.txtName.setText(currencyPairItem.getName());
            binding.txtBidPrice.setText(String.valueOf(currencyPairItem.getBidPrice()));
            binding.txtAskPrice.setText(String.valueOf(currencyPairItem.getBidPrice()));


            final AnimationDrawable drawable = new AnimationDrawable();


            if (currencyPairItem.getChangeStatus() == DECREASE) {

                binding.txtBidPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_24, 0);
                binding.txtAskPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_24, 0);

                drawable.addFrame(new ColorDrawable(itemView.getContext().getResources().getColor(android.R.color.holo_red_light)), FLASH_ANIMATION_PERIOD);
                drawable.addFrame(new ColorDrawable(Color.TRANSPARENT), FLASH_ANIMATION_PERIOD);



            } else if (currencyPairItem.getChangeStatus() == INCREASE) {
                binding.txtBidPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_24, 0);
                binding.txtAskPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_24, 0);

                drawable.addFrame(new ColorDrawable(itemView.getContext().getResources().getColor(android.R.color.holo_green_light)), FLASH_ANIMATION_PERIOD);
                drawable.addFrame(new ColorDrawable(Color.TRANSPARENT), FLASH_ANIMATION_PERIOD);



            } else {
                binding.txtBidPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_nutral_24, 0);
                binding.txtAskPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_nutral_24, 0);

                drawable.addFrame(new ColorDrawable(Color.TRANSPARENT), FLASH_ANIMATION_PERIOD);

            }
            drawable.setOneShot(true);
            binding.lytRootView.setBackground(drawable);
            drawable.start();


        }


    }
}
