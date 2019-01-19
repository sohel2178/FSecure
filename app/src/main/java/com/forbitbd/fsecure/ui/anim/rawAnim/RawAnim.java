package com.forbitbd.fsecure.ui.anim.rawAnim;


import com.forbitbd.fsecure.singleton.RawFData;
import com.forbitbd.fsecure.ui.anim.base.BaseAnimActivity;
import com.forbitbd.fsecure.utility.Constant;

public class RawAnim extends BaseAnimActivity {
    @Override
    public void initData() {

        rDataList = RawFData.getInstance().getData();
        vType = getIntent().getIntExtra(Constant.V_TYPE,1);
    }
}
