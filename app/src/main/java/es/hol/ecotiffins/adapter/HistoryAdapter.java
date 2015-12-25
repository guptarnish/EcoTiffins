package es.hol.ecotiffins.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.model.History;

public class HistoryAdapter extends ArrayAdapter<History> {

	private Context context;
	private int layoutId;
    private ArrayList<History> histories;
    SimpleDateFormat simpleDateFormat;

    public HistoryAdapter(Context context, int layoutId, ArrayList<History> histories){
        super(context, layoutId, histories);
        this.context = context;
        this.layoutId = layoutId;
        this.histories = histories;
        simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:SS", Locale.ENGLISH);
    }

 	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
    	if(convertView == null) {
            viewHolder = new ViewHolder();
    		convertView = (((Activity)context).getLayoutInflater()).inflate(layoutId, parent,false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            viewHolder.txtSubTitle = (TextView) convertView.findViewById(R.id.txtSubTitle);
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            viewHolder.txtTime = (TextView) convertView.findViewById(R.id.txtTime);
            convertView.setTag(viewHolder);
    	} else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        try {
            Date date = simpleDateFormat.parse(histories.get(position).getDate());
            viewHolder.txtTitle.setText(histories.get(position).getTitle());
            viewHolder.txtSubTitle.setText(histories.get(position).getSubtitle());
            viewHolder.txtDate.setText(new SimpleDateFormat("dd MMM", Locale.ENGLISH).format(date));
            viewHolder.txtTime.setText(new SimpleDateFormat("HH:MM a", Locale.ENGLISH).format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtSubTitle;
        TextView txtQuantity;
        TextView txtTime;
        ImageView imgIcon;
        TextView txtDate;
    }
}