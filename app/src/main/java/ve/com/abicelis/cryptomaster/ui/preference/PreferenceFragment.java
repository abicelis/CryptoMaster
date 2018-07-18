package ve.com.abicelis.cryptomaster.ui.preference;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.transition.Fade;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompatDividers;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.data.local.SharedPreferenceHelper;
import ve.com.abicelis.cryptomaster.data.model.Currency;
import ve.com.abicelis.cryptomaster.ui.about.AboutActivity;

/**
 * Created by abicelis on 15/7/2018.
 */
public class PreferenceFragment extends PreferenceFragmentCompatDividers {

    //UI
    private ListPreference mDefaultCurrency;
    private Preference mBackup;
    private Preference mAbout;
    private Preference mRate;
    private Preference mContact;

    private AppCompatActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }


    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_preference, rootKey);

        mDefaultCurrency = (ListPreference) findPreference(getResources().getString(R.string.fragment_preference_default_currency_key));
        mDefaultCurrency.setSummary(new SharedPreferenceHelper().getDefaultCurrency().getFriendlyName());
        mDefaultCurrency.setEntries(Currency.getEntries());
        mDefaultCurrency.setEntryValues(Currency.getEntryValues());
        mDefaultCurrency.setOnPreferenceChangeListener((Preference preference, Object newValue) -> {
            Currency currency = Currency.valueOf((String) newValue);
            mDefaultCurrency.setSummary(currency.getFriendlyName());
            new SharedPreferenceHelper().setDefaultCurrency(currency);
            return true;
        });

//        mBackup = findPreference(getString(R.string.fragment_preference_backup_key));
//        mBackup.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                Intent goToBackupActivity = new Intent(mActivity, BackupActivity.class);
//                startActivity(goToBackupActivity);
//                return true;
//            }
//        });

        mAbout = findPreference(getResources().getString(R.string.fragment_preference_about_key));
        mAbout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent goToAboutActivity = new Intent(mActivity, AboutActivity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //mActivity.getWindow().setEnterTransition(new Explode());
                    mActivity.getWindow().setExitTransition(new Fade(Visibility.MODE_OUT));
                    startActivity(goToAboutActivity, ActivityOptions.makeSceneTransitionAnimation(mActivity).toBundle());
                } else {
                    startActivity(goToAboutActivity);
                }
                return true;
            }
        });


        mRate = findPreference(getResources().getString(R.string.fragment_preference_rate_key));
        mRate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
                playStoreIntent.setData(Uri.parse(getResources().getString(R.string.url_market)));

                ResolveInfo resolveInfo = mActivity.getPackageManager().resolveActivity(playStoreIntent, PackageManager.MATCH_DEFAULT_ONLY);
                if (null == resolveInfo)
                    Toast.makeText(mActivity, "Play Store not installed on device", Toast.LENGTH_SHORT).show();
                else
                    startActivity(playStoreIntent);

                return true;
            }
        });


        mContact = findPreference(getResources().getString(R.string.fragment_preference_contact_key));
        mContact.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",getResources().getString(R.string.address_email), null));

                ResolveInfo resolveInfo = mActivity.getPackageManager().resolveActivity(emailIntent, PackageManager.MATCH_DEFAULT_ONLY);
                if (null == resolveInfo)
                    Toast.makeText(mActivity, "Mail app not installed on device", Toast.LENGTH_SHORT).show();
                else
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                return true;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            return super.onCreateView(inflater, container, savedInstanceState);
        } finally {
            setDividerPreferences(DIVIDER_PADDING_CHILD | DIVIDER_CATEGORY_AFTER_LAST | DIVIDER_CATEGORY_BETWEEN);
        }
    }
}