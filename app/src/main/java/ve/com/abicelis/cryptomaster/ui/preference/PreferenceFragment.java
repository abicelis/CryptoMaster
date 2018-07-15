package ve.com.abicelis.cryptomaster.ui.preference;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompatDividers;

import ve.com.abicelis.cryptomaster.R;
import ve.com.abicelis.cryptomaster.ui.about.AboutActivity;

/**
 * Created by abicelis on 15/7/2018.
 */
public class PreferenceFragment extends PreferenceFragmentCompatDividers {


    //UI
    private Preference mBackup;
    private Preference mAbout;
    private Preference mRate;
    private Preference mContact;
    
    
    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_preference, rootKey);

//        mBackup = findPreference(getString(R.string.fragment_preference_backup_key));
//        mBackup.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                Intent goToBackupActivity = new Intent(getActivity(), BackupActivity.class);
//                startActivity(goToBackupActivity);
//                return true;
//            }
//        });

        mAbout = findPreference(getResources().getString(R.string.fragment_preference_about_key));
        mAbout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent goToAboutActivity = new Intent(getActivity(), AboutActivity.class);
                startActivity(goToAboutActivity);
                return true;
            }
        });


        mRate = findPreference(getResources().getString(R.string.fragment_preference_rate_key));
        mRate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
                playStoreIntent.setData(Uri.parse(getResources().getString(R.string.url_market)));

                ResolveInfo resolveInfo = PreferenceFragment.this.getActivity().getPackageManager().resolveActivity(playStoreIntent, PackageManager.MATCH_DEFAULT_ONLY);
                if (null == resolveInfo)
                    Toast.makeText(PreferenceFragment.this.getActivity(), "Play Store not installed on device", Toast.LENGTH_SHORT).show();
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

                ResolveInfo resolveInfo = PreferenceFragment.this.getActivity().getPackageManager().resolveActivity(emailIntent, PackageManager.MATCH_DEFAULT_ONLY);
                if (null == resolveInfo)
                    Toast.makeText(PreferenceFragment.this.getActivity(), "Mail app not installed on device", Toast.LENGTH_SHORT).show();
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