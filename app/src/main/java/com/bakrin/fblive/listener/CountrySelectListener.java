package com.bakrin.fblive.listener;

import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.model.Pojo.Country;

public interface CountrySelectListener {

    public void onCountrySelect(Country country, int pos, Actions actions);
}
