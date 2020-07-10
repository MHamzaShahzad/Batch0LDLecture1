package com.dapgarage.lecture1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dapgarage.lecture1.R;
import com.dapgarage.lecture1.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<Holder>{

    List<User> list;
    Context context;

    public RecyclerAdapter(List<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        User user = list.get(position);

        holder.recyclerItemText.setText(user.getName() + "\n" + user.getEmail() + "\n" + user.getAge()  + "\n" + "-------\n");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class Holder extends RecyclerView.ViewHolder {


    TextView recyclerItemText;

    public Holder(@NonNull View itemView) {
        super(itemView);
        recyclerItemText = itemView.findViewById(R.id.recyclerItemText);
    }

}
