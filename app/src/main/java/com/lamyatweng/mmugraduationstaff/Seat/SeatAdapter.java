package com.lamyatweng.mmugraduationstaff.Seat;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lamyatweng.mmugraduationstaff.Constants;
import com.lamyatweng.mmugraduationstaff.R;

public class SeatAdapter extends ArrayAdapter<Seat> {
    LayoutInflater mInflater;

    public SeatAdapter(Context context) {
        super(context, 0);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Create a new view if no available view to reuse
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_seat, parent, false);
            holder = new ViewHolder();
            holder.textViewRow = (TextView) convertView.findViewById(R.id.text_view_row);
            holder.textViewColumn = (TextView) convertView.findViewById(R.id.text_view_column);
            holder.itemSeatContainer = (LinearLayout) convertView.findViewById(R.id.item_seat_container);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Seat seat = getItem(position);
        holder.textViewRow.setText(seat.getRow());
        holder.textViewColumn.setText(seat.getColumn());

        switch (seat.getStatus()) {
            case Constants.SEAT_STATUS_AVAILABLE:
                holder.itemSeatContainer.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.seatAvailable));
                break;
            case Constants.SEAT_STATUS_OCCUPIED:
                holder.itemSeatContainer.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.seatOccupied));
                break;
            case Constants.SEAT_STATUS_DISABLED:
                holder.itemSeatContainer.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.seatDisabled));
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textViewRow;
        TextView textViewColumn;
        LinearLayout itemSeatContainer;
    }
}


