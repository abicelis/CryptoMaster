package ve.com.abicelis.cryptomaster.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ve.com.abicelis.cryptomaster.R;

/**
 * Created by abicelis on 30/5/2018.
 */
public class ColorFragment extends Fragment {

    public static final String COLOR_OF_FRAGMENT = "COLOR_OF_FRAGMENT";
    private int color;

    @BindView(R.id.fragment_color_container)
    FrameLayout mContainer;


    public ColorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            color = getArguments().getInt(COLOR_OF_FRAGMENT);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_color, container, false);
        ButterKnife.bind(this, rootView);

        mContainer.setBackgroundColor(color);
        return rootView;
    }
}
