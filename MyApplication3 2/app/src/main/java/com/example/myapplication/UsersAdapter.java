package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import org.w3c.dom.Text;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {
    private ArrayList<User> users;
    private Context context;
    private OnUserClickListener onUserClickListener;

    public UsersAdapter(ArrayList<User> users, Context context, OnUserClickListener onUserClickListener) {
        this.users = users;
        this.context = context;
        this.onUserClickListener = onUserClickListener;
    }
    interface OnUserClickListener{
        void onUserCLicked(int position);
    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_holder,parent,false);
        return new UserHolder(view);
    }


    public void onBindViewHolder(@NonNull UsersAdapter.UserHolder holder, int position) {
        holder.txtUsername.setText(users.get(position).getUsername());
        Glide.with(context).load(users.get(position).getProfilePicture()).error(R.drawable.userfoto).placeholder(R.drawable.userfoto);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    class UserHolder extends RecyclerView.ViewHolder {
        TextView txtUsername;
        ImageView imageView;

        public UserHolder(@NonNull View itemView)

        {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUserClickListener.onUserCLicked(getAdapterPosition());
                }
            });
            txtUsername=itemView.findViewById(R.id.txtUsername);
            imageView=itemView.findViewById(R.id.imag_pro);
        }


    }





}