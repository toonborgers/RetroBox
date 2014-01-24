package be.cegeka.retrobox.presentation.overview;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import be.cegeka.retrobox.R;
import be.cegeka.retrobox.domain.Retro;
import be.cegeka.retrobox.infrastructure.BeanProvider;

public class PlannedRetroFragment extends ListFragment implements OverviewActivity.PlannedRetrosChangeListener {
    private static final String TAG = PlannedRetroFragment.class.getSimpleName();
    private ActionMode actionMode = null;
    private PlannedRetroAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new PlannedRetroAdapter(getActivity(), BeanProvider.retroRepository().getRetros());
        setListAdapter(adapter);
        setUpLongClickListener();
    }

    private void setUpLongClickListener() {
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                itemClicked(position);
                return true;
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (actionMode != null) {
            itemClicked(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((OverviewActivity) activity).setPlannedRetrosChangeListener(this);
    }

    @Override
    public void changedRetros() {
        adapter.clear();
        adapter.clearSelection();
        adapter.addAll(BeanProvider.retroRepository().getRetros());
    }

    private void itemClicked(int position) {
        adapter.toggleSelection(position);
        boolean hasSelectedItems = adapter.getSelectedCount() > 0;

        if (hasSelectedItems && actionMode == null) {
            actionMode = getActivity().startActionMode(new ActionBarcallBack());
        } else if (!hasSelectedItems && actionMode != null) {
            actionMode.finish();
            actionMode = null;
        }
    }

    private void remove(List<Retro> retros) {
        for (Retro retro : retros) {
            adapter.remove(retro);
            BeanProvider.retroRepository().remove(retro);
        }
    }

    private class ActionBarcallBack implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            actionMode.getMenuInflater().inflate(R.menu.overview_planned_actionbar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.overview_planned_cab_delete) {
                remove(adapter.getSelectedItems());
                actionMode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            adapter.clearSelection();
        }
    }
}
