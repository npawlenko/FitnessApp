package com.np.fitnessapp.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.np.fitnessapp.FitnessApp;
import com.np.fitnessapp.R;
import com.np.fitnessapp.activity.UserCreateActivity;
import com.np.fitnessapp.database.AppDatabase;
import com.np.fitnessapp.database.entity.Sex;
import com.np.fitnessapp.database.entity.User;

import java.util.List;

public class UserSelectFragment extends Fragment {

    private static final String LOG_TAG = "tag";
    public static final String SELECTED_USERID_EXTRA = "selectedUserId";

    private RecyclerView recyclerView;
    private Button addUserButton;
    private UserAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_user, container, false);
        recyclerView = view.findViewById(R.id.user_select_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        addUserButton = view.findViewById(R.id.button);
        addUserButton.setOnClickListener(v -> {
            Intent addUserIntent = new Intent(getContext(), UserCreateActivity.class);
            startActivity(addUserIntent);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }


    private void updateView() {
        AppDatabase db = AppDatabase.getDatabase(getContext());
        List<User> users = db.userDao().getAllUsers();

        if(adapter == null) {
            adapter = new UserAdapter(users);
            recyclerView.setAdapter(adapter);
        }
        else {
            adapter.setUsers(users);
            adapter.notifyDataSetChanged();
        }
    }




    private class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private User user;

        private final ImageView userSexImageView;
        private final TextView userTitleTextView;
        private final TextView userSubtitleTextView;
        private ImageView userDeleteImageView;

        public UserHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.user, parent, false));
            itemView.setOnClickListener(this);

            userTitleTextView = itemView.findViewById(R.id.user_title);
            userSubtitleTextView = itemView.findViewById(R.id.user_subtitle);
            userSexImageView = itemView.findViewById(R.id.user_sex_icon);
            userDeleteImageView = itemView.findViewById(R.id.user_delete_imageview);
        }

        public void bind(User user, int position) {
            this.user = user;

            if (user.sex == Sex.MALE) {
                userSexImageView.setImageResource(R.drawable.ic_baseline_male_24);
            } else {
                userSexImageView.setImageResource(R.drawable.ic_baseline_female_46);
            }
            userTitleTextView.setText(user.name);
            userSubtitleTextView.setText(user.weight + "kg, " + user.height + "cm, " + user.age + " lat");

            userDeleteImageView.setOnClickListener(v -> {
                AppDatabase.getDatabase(getContext()).userDao().deleteUser(user);
                adapter.users.remove(position);
                adapter.notifyDataSetChanged();

                if(user.userId == FitnessApp.getInstance().getUser().userId) {
                    SharedPreferences appSharedPreferences = getActivity().getSharedPreferences(FitnessApp.APP_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
                    appSharedPreferences.edit().remove(FitnessApp.SAVED_USERID_KEY).commit();
                }
            });
        }

        @Override
        public void onClick(View view) {
            FitnessApp.getInstance().setUser(user);

            SharedPreferences appSharedPreferences = getActivity().getSharedPreferences(FitnessApp.APP_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
            appSharedPreferences.edit().putLong(FitnessApp.SAVED_USERID_KEY, user.userId).commit();
        }
    }

    private class UserAdapter extends RecyclerView.Adapter<UserHolder> {
        private List<User> users;

        public UserAdapter(List<User> users) {
            this.users = users;
        }

        @NonNull
        @Override
        public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new UserHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull UserHolder holder, int position) {
            if(users != null) {
                User user = users.get(position);
                holder.bind(user, position);
            }
        }

        public void setUsers(List<User> users) {
            this.users = users;
        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }
}
