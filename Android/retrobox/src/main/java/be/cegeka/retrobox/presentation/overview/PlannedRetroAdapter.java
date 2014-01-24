package be.cegeka.retrobox.presentation.overview;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.domain.Retro;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlannedRetroAdapter extends ArrayAdapter<Retro> {
    private static final String TAG = PlannedRetroAdapter.class.getSimpleName();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm");

    private List<Retro> plannedRetros;
    private LayoutInflater layoutInflater;
    private SparseBooleanArray selection;

    public PlannedRetroAdapter(Context ctx, List<Retro> plannedRetros) {
        super(ctx, R.layout.overview_list_item, plannedRetros);
        this.plannedRetros = plannedRetros;
        layoutInflater = LayoutInflater.from(ctx);
        selection = new SparseBooleanArray();
    }

    public void clearSelection() {
        selection.clear();
        notifyDataSetChanged();
    }

    public void toggleSelection(int position) {
        selectView(position, !selection.get(position));
    }

    public void selectView(int position, boolean select) {
        if (select) {
            selection.put(position, select);
        } else {
            selection.delete(position);
        }

        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return selection.size();
    }

    public List<Retro> getSelectedItems() {
        List<Retro> result = new ArrayList<Retro>();
        for (int i = 0; i < plannedRetros.size(); i++) {
            if (selection.get(i)) {
                result.add(plannedRetros.get(i));
            }
        }
        return result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            rowView = layoutInflater.inflate(R.layout.overview_list_item, null);
            rowView.setTag(new ViewHolder(rowView));
        }
        Retro retro = plannedRetros.get(position);
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();

        viewHolder.tvName.setText(retro.getName());
        viewHolder.tvDate.setText(DATE_FORMAT.print(retro.getTime()));
        viewHolder.tvTime.setText(TIME_FORMAT.print(retro.getTime()));
        viewHolder.tvLocation.setText(retro.getLocation());
        viewHolder.tvLocationSpacer.setVisibility(retro.getLocation() == null || retro.getLocation().isEmpty() ? View.INVISIBLE : View.VISIBLE);

        int color = Color.TRANSPARENT;
        if (selection.get(position)) {
            color = getContext().getResources().getColor(android.R.color.holo_blue_light);
        }
        rowView.setBackgroundColor(color);
        return rowView;
    }

    static class ViewHolder {
        @InjectView(R.id.overview_item_name)
        TextView tvName;
        @InjectView(R.id.overview_item_date)
        TextView tvDate;
        @InjectView(R.id.overview_item_time)
        TextView tvTime;
        @InjectView(R.id.overview_item_location)
        TextView tvLocation;
        @InjectView(R.id.overview_item_spacer_location)
        TextView tvLocationSpacer;

        public ViewHolder(View v) {
            ButterKnife.inject(this, v);
        }
    }
}
