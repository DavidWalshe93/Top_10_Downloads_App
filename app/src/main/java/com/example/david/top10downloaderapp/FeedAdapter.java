package com.example.david.top10downloaderapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


// David Walshe
// 15/01/2019

public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<FeedEntry> applications;

    public FeedAdapter(Context context, int resource, List<FeedEntry> applications) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.applications = applications;
    }

    // Required to get the amount of views required from the adapter
    @Override
    public int getCount() {
        return applications.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);      // Create view by inflating the view from the inflater from the context

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);            // Get the IDs of the text views in the view generated above
//        TextView artistTextView = (TextView) convertView.findViewById(R.id.artistTextView);
//        TextView summaryTextView = (TextView) convertView.findViewById(R.id.summaryTextView);

        FeedEntry currentApp = applications.get(position);      // Get the data for the views

        viewHolder.nameTextView.setText(currentApp.getName());             // Set the data in the view
        viewHolder.artistTextView.setText(currentApp.getArtist());
        viewHolder.summaryTextView.setText(currentApp.getSummary());

        return convertView;
    }

    private class ViewHolder {
        final TextView nameTextView;
        final TextView artistTextView;
        final TextView summaryTextView;

        ViewHolder(View v) {
            this.nameTextView = v.findViewById(R.id.nameTextView);
            this.artistTextView = v.findViewById(R.id.artistTextView);
            this.summaryTextView = v.findViewById(R.id.summaryTextView);
        }
    }
}
