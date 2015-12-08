package es.hol.ecotiffins.ecotiffins.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.ecotiffins.model.History;
import es.hol.ecotiffins.ecotiffins.model.Order;

public class HistoryAdapter extends ArrayAdapter<History> {

	private Context context;
	private int layoutId;
    private ArrayList<History> histories;

    public HistoryAdapter(Context context, int layoutId, ArrayList<History> histories){
        super(context, layoutId, histories);
        this.context = context;
        this.layoutId = layoutId;
        this.histories = histories;
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
            convertView.setTag(viewHolder);
    	} else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtTitle.setText(histories.get(position).getTitle());
        viewHolder.txtSubTitle.setText(histories.get(position).getSubtitle());

    	return convertView;
    }

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtSubTitle;
        TextView txtQuantity;
        TextView txtPrice;
        ImageView imgIcon;
        TextView txtDate;
    }
}