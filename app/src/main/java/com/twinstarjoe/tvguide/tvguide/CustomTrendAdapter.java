package com.twinstarjoe.tvguide.tvguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomTrendAdapter extends SimpleAdapter {
    private static Context mContext;
    ImageView imgShow;
    protected TvGuideApplication app;
    ArrayList<HashMap<String, String>> mapItems;

    public CustomTrendAdapter(Context context, ArrayList<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
        mContext = context;
        app = TvGuideApplication.getInstance();
        mapItems = items;
  }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tvshow_item, parent, false);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.txtTitle);
        TextView txtAirDate = (TextView) rowView.findViewById(R.id.txtAirDate);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.show_image);

        HashMap map = mapItems.get(position);
        String strHttp = (String) map.get("IMAGEVIEWID");
        String strName = (String) map.get("NAME");
        String strDate = (String) map.get("AIRDATE");
        tvTitle.setText(strName.toString());
        txtAirDate.setText(strDate.toString());

//        // Set the image
        Picasso.with(mContext).load(strHttp).into(imageView);

        tvTitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                return;
            }
        });
        txtAirDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                return;
            }
        });
        ImageView chevron = (ImageView) rowView.findViewById(R.id.greaterthan);
        chevron.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                return;
            }
        });


        return rowView;
    }

}