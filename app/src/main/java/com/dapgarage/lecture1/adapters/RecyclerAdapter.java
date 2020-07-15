package com.dapgarage.lecture1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dapgarage.lecture1.R;
import com.dapgarage.lecture1.models.FirebaseDatabaseUser;
import com.dapgarage.lecture1.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<Holder>{

    List<FirebaseDatabaseUser> list;
    Context context;

    public RecyclerAdapter(List<FirebaseDatabaseUser> list) {
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

        FirebaseDatabaseUser user = list.get(position);

        holder.profileFirstName.setText(user.getFirstName());
        holder.profileLastName.setText(user.getLastName());
        holder.profileEmail.setText(user.getEmail());
        holder.profilePhoneNumber.setText(user.getPhoneNumber());
        holder.profileAddress.setText(user.getAddress());
        holder.profileCNIC.setText(user.getCnic());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class Holder extends RecyclerView.ViewHolder {


    TextView profileFirstName, profileLastName, profileEmail, profilePhoneNumber, profileAddress, profileCNIC;

    public Holder(@NonNull View itemView) {
        super(itemView);

        profileFirstName = itemView.findViewById(R.id.profileFirstName);
        profileLastName = itemView.findViewById(R.id.profileLastName);
        profileEmail = itemView.findViewById(R.id.profileEmail);
        profilePhoneNumber = itemView.findViewById(R.id.profilePhoneNumber);
        profileAddress = itemView.findViewById(R.id.profileAddress);
        profileCNIC = itemView.findViewById(R.id.profileCNIC);
    }

}
