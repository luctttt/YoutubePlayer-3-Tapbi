package vn.tapbi.youtubeplayer3.ui.main.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.core.app.ActivityCompat;

import java.util.Locale;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;
import vn.tapbi.youtubeplayer3.data.local.db.SharedPreferenceHelper;
import vn.tapbi.youtubeplayer3.databinding.FragmentLanguageBinding;
import vn.tapbi.youtubeplayer3.ui.base.BaseBindingFragmentNoViewModel;
import vn.tapbi.youtubeplayer3.ui.main.HomeActivity;


public class LanguageFragment extends BaseBindingFragmentNoViewModel<FragmentLanguageBinding> {

    Locale myLocale;
    String currentLanguage = "en", currentLang = "language";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_language;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        String language = SharedPreferenceHelper.getLanguage(requireContext());
        if (language.equals("vi")) {
            binding.radLanguageVietNam.setChecked(true);
        } else if (language.equals("en")) {
            binding.radLanguageEnglish.setChecked(true);
        }


        binding.radGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rad_languageVietNam) {
                setLocal(requireActivity(),"vi");
                SharedPreferenceHelper.saveLanguage(requireContext(), "vi");
                Timber.d("language : vietnamese !!");
            } else if (checkedId == R.id.rad_languageEnglish) {
                setLocal(requireActivity(),"en");
                SharedPreferenceHelper.saveLanguage(requireContext(), "en");
                Timber.d("language : english !!");
            }
        });

    }

    public void setLocal(Activity activity , String localeName) {

//        requireContext().getResources().updateConfiguration(configuration, requireContext().getResources().getDisplayMetrics());
        Context context = SharedPreferenceHelper.setLocale(getContext(), localeName);
        //Resources resources = context.getResources();
        myLocale = new Locale(localeName);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(getActivity(), HomeActivity.class);
        refresh.putExtra(currentLang, localeName);
        startActivity(refresh);
        ActivityCompat.finishAffinity(activity);

    }


}