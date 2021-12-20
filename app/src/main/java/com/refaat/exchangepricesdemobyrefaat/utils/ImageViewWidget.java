package com.refaat.exchangepricesdemobyrefaat.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.refaat.exchangepricesdemobyrefaat.R;


public class ImageViewWidget extends WidgetBase {

    private ImageViewWidget(int id, View.OnClickListener onClickListener) {
        super(id, onClickListener);
    }

    @Override
    public View createView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.example_toolbar_item, null, false);
        layout.setOnClickListener(onClickListener);

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setAdjustViewBounds(true);
        imageView.setImageResource(id);

        layout.addView(imageView);

        return layout;
    }

    public static class Builder {
        private int id;
        private View.OnClickListener onClickListener;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public ImageViewWidget build() {
            return new ImageViewWidget(id, onClickListener);
        }
    }
}
