package es.hol.ecotiffins.ecotiffins.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import es.hol.ecotiffins.ecotiffins.R;
import es.hol.ecotiffins.ecotiffins.model.Order;

public class ListViewAdapter extends ArrayAdapter<Order> {

	private Context context;	
	private int layoutId;
    private ArrayList<Order> orders;

    public ListViewAdapter (Context context, int layoutId, ArrayList<Order> orders){
        super(context, layoutId, orders);
        this.context = context;
        this.layoutId = layoutId;
        this.orders = orders;
    }
    
/*
	@Override
    public int getCount() {
        return orders.size();
    }
 
    @Override
    public Order getItem(int position) {
        return orders.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
*/

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

        viewHolder.txtTitle.setText(orders.get(position).getTitle());
        viewHolder.txtSubTitle.setText(
                orders.get(position).getPrice().equals("Price Not Available")
                        ? orders.get(position).getPrice()
                        : orders.get(position).getPrice() + " Rs. / Tiffin");
        //viewHolder.txtQuantity.setText(orders.get(position).getQuantity());
        //viewHolder.txtPrice.setText(orders.get(position).getPrice());
        viewHolder.imgIcon.setImageResource(orders.get(position).getImgIcon());

    	return convertView;
    }

    private static class ViewHolder {
        TextView txtTitle;
        TextView txtSubTitle;
        TextView txtQuantity;
        TextView txtPrice;
        ImageView imgIcon;
    }
}