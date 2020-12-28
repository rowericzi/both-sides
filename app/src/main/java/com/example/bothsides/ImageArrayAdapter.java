package com.example.bothsides;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImageArrayAdapter extends ArrayAdapter {
	private class ViewHolder {
		ImageView image;
		TextView text;
	}
	String[] titles;
	int[] images;
	Context mContext;

	public ImageArrayAdapter(@NonNull Context context, String[] titles, int[] images) {
		super(context, R.layout.custom_spinner_row);
		this.titles = titles;
		this.images = images;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return titles.length;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		ViewHolder mViewHolder = new ViewHolder();
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.custom_spinner_row, parent, false);
			mViewHolder.image = (ImageView) convertView.findViewById(R.id.custom_spinner_image);
			mViewHolder.text = (TextView) convertView.findViewById(R.id.custom_spinner_text);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		mViewHolder.image.setImageResource(images[position]);
		mViewHolder.text.setText(titles[position]);

		return convertView;
	}

	@Override
	public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		return getView(position, convertView, parent);
	}
}
