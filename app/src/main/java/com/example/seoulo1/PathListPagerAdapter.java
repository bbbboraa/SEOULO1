package com.example.seoulo1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PathListPagerAdapter extends FragmentStateAdapter {
    private long numDates; //날짜 수에 따라 프래그먼트 생성


    public PathListPagerAdapter(PathListPagerActivity fragment, long daysDiff) {
        super(fragment);
        this.numDates = daysDiff; //'daysDiff' 값을 사용하여 'numDates' 설정
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 사용자가 선택한 날짜에 따라 프래그먼트를 생성하여 반환
        // position을 기반으로 해당 날짜에 대한 프래그먼트를 생성하는 로직을 구현
        // 예를 들어, 선택한 날짜를 기반으로 해당 날짜에 대한 데이터를 가져와 프래그먼트에 전달
        return PathListFragment.newInstance(position, numDates);
    }

    @Override
    public int getItemCount() {
        return (int) numDates;
    }

}