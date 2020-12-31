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

/**
 * This class is used for custom Spinner layout in {@link MainActivity},
 * providing row layout defined in custom_spinner_row.xml
 * @author Ryszard Jezierski
 */
public class ImageArrayAdapter extends ArrayAdapter {
	private static class ViewHolder {
		ImageView image;
		TextView text;
	}
	String[] titles;
	int[] images;
	Context mContext;

	/**
	 *
	 * @param context Activity where new ImageArrayAdapter will be used
	 * @param titles Titles of the displayed items
	 * @param images Images displayed next to items
	 */
	public ImageArrayAdapter(@NonNull Context context, String[] titles, int[] images) {
		super(context, R.layout.custom_spinner_row);
		this.titles = titles;
		this.images = images;
		this.mContext = context;
	}

	/**
	 *
	 * @return Number of items in this adapter
	 */
	@Override
	public int getCount() {
		return titles.length;
	}

	/**
	 * Get a View at the specified position
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return a View corresponding to the data at the specified position.
	 */
	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.custom_spinner_row, parent, false);
			viewHolder.image = convertView.findViewById(R.id.custom_spinner_image);
			viewHolder.text = convertView.findViewById(R.id.custom_spinner_text);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.image.setImageResource(images[position]);
		viewHolder.text.setText(titles[position]);

		return convertView;
	}

	/**
	 * Return View displayed in the drop-down popup
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return a View corresponding to the data at the specified position.
	 */
	@Override
	public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		return getView(position, convertView, parent);
	}
}
