package com.forbitbd.fsecure.ui.anim.hourly;



import com.forbitbd.fsecure.api.model.RData;
import com.forbitbd.fsecure.ui.anim.base.BaseAnimActivity;
import com.forbitbd.fsecure.utility.Constant;

import java.util.List;

public class HourlyAnimActivy extends BaseAnimActivity {
    @Override
    public void initData() {
        rDataList = (List<RData>) getIntent().getSerializableExtra(Constant.DATA);
        vType = getIntent().getIntExtra(Constant.V_TYPE,1);
    }
}
