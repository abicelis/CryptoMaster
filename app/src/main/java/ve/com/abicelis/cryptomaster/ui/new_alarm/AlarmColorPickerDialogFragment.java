package ve.com.abicelis.cryptomaster.ui.new_alarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.model.AlarmColor;
import ve.com.abicelis.cryptomaster.ui.alarm.AlarmAdapter;
import ve.com.abicelis.cryptomaster.ui.base.BaseDialogFragment;


/**
 * Created by abice on 16/3/2017.
 */

public class AlarmColorPickerDialogFragment extends BaseDialogFragment {

    //DATA
    static private ColorPickedListener mListener;

    //UI
    @BindView(R.id.dialog_color_picker_recycler)
    RecyclerView mRecycler;
    AlarmColorAdapter mAdapter;
    GridLayoutManager mLayoutManager;

    @BindView(R.id.dialog_color_picker_cancel)
    Button mCancel;


    public AlarmColorPickerDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AlarmColorPickerDialogFragment newInstance(ColorPickedListener listener) {
        AlarmColorPickerDialogFragment frag = new AlarmColorPickerDialogFragment();

        mListener = listener;
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dialogView =  inflater.inflate(R.layout.dialog_alarm_color_picker, container);
        ButterKnife.bind(this, dialogView);


        mLayoutManager = new GridLayoutManager(getContext(), 3);
        mAdapter = new AlarmColorAdapter(getContext(), mListener);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);
        mAdapter.getItems().addAll(Arrays.asList(AlarmColor.values()));
        mAdapter.notifyDataSetChanged();

        mCancel.setOnClickListener(v -> dismiss());
        return dialogView;
    }




    public void setListener(ColorPickedListener listener) {
        mListener = listener;
    }


    public interface ColorPickedListener {
        void onColorPicked(AlarmColor color);
    }
}
