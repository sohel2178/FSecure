package com.forbitbd.fsecure.ui.anim.rawAnim;


import com.forbitbd.fsecure.singleton.RawFData;
import com.forbitbd.fsecure.ui.anim.base.BaseAnimActivity;

public class RawAnim extends BaseAnimActivity {
    @Override
    public void initData() {
        rDataList = RawFData.getInstance().getData();
    }
}
