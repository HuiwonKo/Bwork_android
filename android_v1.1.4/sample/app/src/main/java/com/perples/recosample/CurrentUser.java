package com.perples.recosample;

import android.content.Context;

/**
 * Created by haein on 2017-09-10.
 */
public class CurrentUser{

    private Context mContext = null;

    public CurrentUser(Context context) {
        this.mContext = context;
    }

    public String getCurrentUserId() {
        return ((NavigationActivity) mContext).getUserId();
    }

}
