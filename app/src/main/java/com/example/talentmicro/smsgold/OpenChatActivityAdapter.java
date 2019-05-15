package com.example.talentmicro.smsgold;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OpenChatActivityAdapter extends ArrayAdapter<ListDto> {

    Context context;
    ViewHolder holder = null;
    private ArrayList<ListDto> dataList;

    public OpenChatActivityAdapter(@NonNull Context context, int resource, ArrayList<ListDto> dataList) {
        super(context, resource);
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.their_message, null);
            holder = new ViewHolder();

            holder.tvMessageBody = convertView.findViewById(R.id.message_body);
            holder.tvName = convertView.findViewById(R.id.name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

            final ListDto listDto = dataList.get(position);
            holder.tvMessageBody.setText(listDto.getMessage());
            holder.tvName.setText(listDto.getUsrName());

        return convertView;
    }


    private class ViewHolder {
        TextView tvMessageBody;
        TextView tvName;
    }
}
