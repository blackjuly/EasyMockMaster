package cn.whdreamblog.mockhelper.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * ViewPager 嵌套 Fragment 组合的 Adapter
 *
 * @author Frank <a href="xiaoyu.zhu@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2017/2/20 9:10
 */
public class CommonFragmentViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<? extends Fragment> mFragments;

    public CommonFragmentViewPagerAdapter(FragmentManager fragmentManager, List<? extends Fragment> fragments) {
        super(fragmentManager);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
