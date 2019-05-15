package com.example.talentmicro.smsgold;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    Context context;
    String uname;
    private ArrayList<ListDto> dataList;

    public MessageListAdapter(Context context, ArrayList<ListDto> dataList,String s) {
        this.context = context;
        this.dataList = dataList;
        this.uname = s;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item_tmpl, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ListDto listDto = dataList.get(position);

        //holder.tvMessage.setText(listDto.getMessage());
        holder.tvUserName.setText(listDto.getUsrName());

        holder.lnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("uname",uname);
                bundle.putString("cname",listDto.getUsrName());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    /**
     * View holder class
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_user_name)
        TextView tvUserName;

       // @Bind(R.id.tv_message)
        //TextView tvMessage;

        @Bind(R.id.lnMain)
        LinearLayout lnMain;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
