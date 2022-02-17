package edu.uncc.itcs4180.inclass05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Assignment #: InClass05
 * FileName: AppAdapter.java
 * CJ D'Autorio, Mason Pipkin
 */
public class AppAdapter extends ArrayAdapter<DataServices.App> {

    public AppAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.app_row_item, parent, false);
        }

        DataServices.App app = getItem(position);
        TextView appRow_nameText = convertView.findViewById(R.id.appRow_nameText);
        TextView appRow_artistText = convertView.findViewById(R.id.appRow_artistText);
        TextView appRow_releaseDateText = convertView.findViewById(R.id.appRow_releaseDateText);

        appRow_nameText.setText(app.name);
        appRow_artistText.setText(app.artistName);
        appRow_releaseDateText.setText(app.releaseDate);

        return convertView;
    }
}
