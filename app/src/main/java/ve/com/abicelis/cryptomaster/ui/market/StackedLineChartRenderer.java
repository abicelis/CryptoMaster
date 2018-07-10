package ve.com.abicelis.cryptomaster.ui.market;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

/**
 * Custom StackedLineChartRenderer to draw and fill the enclosed path.
 * Taken from https://stackoverflow.com/questions/43342597/android-fill-the-color-between-two-lines-using-mpandroidchart/43452404#43452404 on 7/7/2018.
 */
public class StackedLineChartRenderer extends LineChartRenderer {

    public StackedLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    // This method defines the perimeter of the area to be filled for horizontal bezier data sets.
    @Override
    protected void drawCubicFill(Canvas c, ILineDataSet dataSet, Path spline, Transformer trans, XBounds bounds) {

        if(dataSet.getFillFormatter() instanceof StackedLineFillFormatter) {
            final float phaseY = mAnimator.getPhaseY();

            //Call the custom method to retrieve the dataset for other line
            final List<Entry> boundaryEntries = ((StackedLineFillFormatter) dataSet.getFillFormatter()).getFillLineBoundary();

            // We are currently at top-last point, so draw down to the last boundary point
            Entry boundaryEntry = boundaryEntries.get(bounds.min + bounds.range);
            spline.lineTo(boundaryEntry.getX(), boundaryEntry.getY() * phaseY);

            // Draw a cubic line going back through all the previous boundary points
            Entry prev = dataSet.getEntryForIndex(bounds.min + bounds.range);
            Entry cur = prev;
            for (int x = bounds.min + bounds.range; x >= bounds.min; x--) {

                prev = cur;
                cur = boundaryEntries.get(x);

                final float cpx = (prev.getX()) + (cur.getX() - prev.getX()) / 2.0f;

                spline.cubicTo(
                        cpx, prev.getY() * phaseY,
                        cpx, cur.getY() * phaseY,
                        cur.getX(), cur.getY() * phaseY);
            }

            // Join up the perimeter
            spline.close();

            trans.pathValueToPixel(spline);

            final Drawable drawable = dataSet.getFillDrawable();
            if (drawable != null) {
                drawFilledPath(c, spline, drawable);
            } else {
                drawFilledPath(c, spline, dataSet.getFillColor(), dataSet.getFillAlpha());
            }
        } else {
            float fillMin = dataSet.getFillFormatter()
                    .getFillLinePosition(dataSet, mChart);

            spline.lineTo(dataSet.getEntryForIndex(bounds.min + bounds.range).getX(), fillMin);
            spline.lineTo(dataSet.getEntryForIndex(bounds.min).getX(), fillMin);
            spline.close();

            trans.pathValueToPixel(spline);

            final Drawable drawable = dataSet.getFillDrawable();
            if (drawable != null) {

                drawFilledPath(c, spline, drawable);
            } else {

                drawFilledPath(c, spline, dataSet.getFillColor(), dataSet.getFillAlpha());
            }
        }
    }

    //This method is same as it's parent implementation
    @Override
    protected void drawLinearFill(Canvas c, ILineDataSet dataSet, Transformer trans, XBounds bounds) {
        final Path filled = mGenerateFilledPathBuffer;

        final int startingIndex = bounds.min;
        final int endingIndex = bounds.range + bounds.min;
        final int indexInterval = 128;

        int currentStartIndex = 0;
        int currentEndIndex = indexInterval;
        int iterations = 0;

        // Doing this iteratively in order to avoid OutOfMemory errors that can happen on large bounds sets.
        do {
            currentStartIndex = startingIndex + (iterations * indexInterval);
            currentEndIndex = currentStartIndex + indexInterval;
            currentEndIndex = currentEndIndex > endingIndex ? endingIndex : currentEndIndex;

            if (currentStartIndex <= currentEndIndex) {
                generateFilledPath(dataSet, currentStartIndex, currentEndIndex, filled);

                trans.pathValueToPixel(filled);

                final Drawable drawable = dataSet.getFillDrawable();
                if (drawable != null) {

                    drawFilledPath(c, filled, drawable);
                } else {

                    drawFilledPath(c, filled, dataSet.getFillColor(), dataSet.getFillAlpha());
                }
            }

            iterations++;

        } while (currentStartIndex <= currentEndIndex);
    }

    //This is where we define the area to be filled.
    private void generateFilledPath(final ILineDataSet dataSet, final int startIndex, final int endIndex, final Path outputPath) {

        if(dataSet.getFillFormatter() instanceof StackedLineFillFormatter) {

            //Call the custom method to retrieve the dataset for other line
            final List<Entry> boundaryEntry = ((StackedLineFillFormatter)dataSet.getFillFormatter()).getFillLineBoundary();

            final float phaseY = mAnimator.getPhaseY();
            final Path filled = outputPath;
            filled.reset();

            final Entry entry = dataSet.getEntryForIndex(startIndex);

            filled.moveTo(entry.getX(), boundaryEntry.get(0).getY());
            filled.lineTo(entry.getX(), entry.getY() * phaseY);

            // create a new path
            Entry currentEntry = null;
            Entry previousEntry = null;
            for (int x = startIndex + 1; x <= endIndex; x++) {

                currentEntry = dataSet.getEntryForIndex(x);
                filled.lineTo(currentEntry.getX(), currentEntry.getY() * phaseY);

            }

            // close up
            if (currentEntry != null && previousEntry!= null) {
                filled.lineTo(currentEntry.getX(), previousEntry.getY());
            }

            //Draw the path towards the other line
            for (int x = endIndex ; x > startIndex; x--) {
                previousEntry = boundaryEntry.get(x);
                filled.lineTo(previousEntry.getX(), previousEntry.getY() * phaseY);
            }

            filled.close();

        } else {
            final float fillMin = dataSet.getFillFormatter().getFillLinePosition(dataSet, mChart);
            final float phaseY = mAnimator.getPhaseY();
            final boolean isDrawSteppedEnabled = dataSet.getMode() == LineDataSet.Mode.STEPPED;

            final Path filled = outputPath;
            filled.reset();

            final Entry entry = dataSet.getEntryForIndex(startIndex);

            filled.moveTo(entry.getX(), fillMin);
            filled.lineTo(entry.getX(), entry.getY() * phaseY);

            // create a new path
            Entry currentEntry = null;
            Entry previousEntry = null;
            for (int x = startIndex + 1; x <= endIndex; x++) {

                currentEntry = dataSet.getEntryForIndex(x);

                if (isDrawSteppedEnabled && previousEntry != null) {
                    filled.lineTo(currentEntry.getX(), previousEntry.getY() * phaseY);
                }

                filled.lineTo(currentEntry.getX(), currentEntry.getY() * phaseY);

                previousEntry = currentEntry;
            }

            // close up
            if (currentEntry != null) {
                filled.lineTo(currentEntry.getX(), fillMin);
            }

            filled.close();
        }
    }
}
