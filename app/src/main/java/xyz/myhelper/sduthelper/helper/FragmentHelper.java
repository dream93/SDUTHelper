package xyz.myhelper.sduthelper.helper;

import android.app.Fragment;
import android.os.Bundle;

/**
 * @author dream
 * @version 1.0
 * Created by dream on 15-11-18.
 */
public class FragmentHelper {

    private Fragment targetFragment;
    private String fragmentTag;
    private boolean isClearBackTask;
    private Bundle bundle;

    public Fragment getTargetFragment() {
        return targetFragment;
    }

    public void setTargetFragment(Fragment targetFragment) {
        this.targetFragment = targetFragment;
    }

    public String getFragmentTag() {
        return fragmentTag;
    }

    public void setFragmentTag(String fragmentTag) {
        this.fragmentTag = fragmentTag;
    }

    public boolean isClearBackTask() {
        return isClearBackTask;
    }

    public void setIsClearBackTask(boolean isClearBackTask) {
        this.isClearBackTask = isClearBackTask;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
