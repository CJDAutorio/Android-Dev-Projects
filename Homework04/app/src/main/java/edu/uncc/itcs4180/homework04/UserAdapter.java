package edu.uncc.itcs4180.homework04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class UserAdapter extends ArrayAdapter<DataServices.User> {

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.users_row_item, parent, false);
        }

        DataServices.User user = getItem(position);
        ImageView userRow_imageView = convertView.findViewById(R.id.userRow_imageView);
        TextView userRow_nameText = convertView.findViewById(R.id.userRow_nameText);
        TextView userRow_stateText = convertView.findViewById(R.id.userRow_stateText);
        TextView userRow_ageText = convertView.findViewById(R.id.userRow_ageText);
        TextView userRow_groupText = convertView.findViewById(R.id.userRow_groupText);

        if (user.gender.equals("Male")) {
            userRow_imageView.setImageDrawable(ContextCompat.getDrawable(
                    convertView.getContext(), R.drawable.avatar_male));
        } else {
            userRow_imageView.setImageDrawable(ContextCompat.getDrawable(
                    convertView.getContext(), R.drawable.avatar_female));
        }

        userRow_nameText.setText(user.name);
        userRow_stateText.setText(user.state);
        userRow_ageText.setText(user.age + "");
        userRow_groupText.setText(user.group);

        return convertView;
    }
}
