package com.bakrin.fblive.listener;

import com.bakrin.fblive.action.Actions;
import com.bakrin.fblive.model.response.FixtureItem;
import com.bakrin.fblive.model.response.Team;

public interface HomeTeamSelectListener {
    public void onTeamSelect(int pos, Team team, Actions actions);
}
