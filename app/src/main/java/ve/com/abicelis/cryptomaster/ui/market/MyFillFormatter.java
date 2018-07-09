package ve.com.abicelis.cryptomaster.ui.market;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.List;

/**
 * Custom FillFormator to return the dataset of another line.
 * Taken from https://stackoverflow.com/questions/43342597/android-fill-the-color-between-two-lines-using-mpandroidchart/43452404#43452404 on 7/7/2018.
 */
public class MyFillFormatter implements IFillFormatter {
    private ILineDataSet boundaryDataSet;

    public MyFillFormatter() {
        this(null);
    }
    //Pass the dataset of other line in the Constructor
    public MyFillFormatter(ILineDataSet boundaryDataSet) {
        this.boundaryDataSet = boundaryDataSet;
    }

    @Override
    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
        return 0;
    }

    //Define a new method which is used in the LineChartRenderer
    public List<Entry> getFillLineBoundary() {
        if(boundaryDataSet != null) {
            return ((LineDataSet) boundaryDataSet).getValues();
        }
        return null;
    }}
