package com.volcengine.plugin1;

import androidx.fragment.app.Fragment;
import com.volcengine.mira.IPlugin1Service;

public class Plugin1ServiceImpl implements IPlugin1Service {
    @Override
    public Fragment getFragment() {
        return new Plugin1Fragment();
    }
}
