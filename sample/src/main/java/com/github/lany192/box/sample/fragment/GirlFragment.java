package com.github.lany192.box.sample.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.lany192.box.adapter.MultiAdapter;
import com.github.lany192.box.fragment.BaseFragment;
import com.github.lany192.box.fragment.FragmentConfig;
import com.github.lany192.box.sample.R;
import com.github.lany192.box.sample.delegate.ImageDelegate;
import com.github.lany192.box.view.CollectionView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

public class GirlFragment extends BaseFragment {
    @BindView(R.id.collection_view)
    CollectionView mCollectionView;

    private StaggeredGridLayoutManager layoutManager;

    @NonNull
    @Override
    public FragmentConfig getConfig() {
        return FragmentConfig.builder()
                .layoutId(R.layout.fragment_girl)
                .build();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layoutManager != null) {
            mCollectionView.setLayoutManager(layoutManager);
            mCollectionView.restoreSaveState();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mCollectionView.setLayoutManager(layoutManager);

        List<String> images = new ArrayList<>();
        images.add("https://img.tupianzj.com/uploads/allimg/202011/9999/fda6ef1bcc.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202011/9999/3d7174cee3.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202011/9999/46ff3ad1f2.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/151002/9-151002114P3.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/200224/37-200224113113-52.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/200224/37-200224113114-53.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/200224/37-200224113527.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/200224/37-200224113528-52.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/200224/37-200224113528-53.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202011/9999/f07d8a8801.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202011/9999/ab484a269c.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202011/9999/42929d3f98.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/151002/9-151002114P7.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/151002/9-151002114Q0.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/151002/9-151002114Q1.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/160209/9-160209105409.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/160209/9-160209105415.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/160209/9-160209105418.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/160209/9-160209105421.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/160209/9-160209105431.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202002/9999/13944db09d.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202002/9999/b3dacc5656.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202002/9999/0a299d5bdc.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202002/9999/71883dd7fe.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202002/9999/1e2832a665.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202002/9999/010e4c81a0.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202002/9999/4c46903d26.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202002/9999/3f3a4f3ba3.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/161210/9-161210204631.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/190301/9-1Z301155925.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/202011/9999/1a32fce732.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/170108/9-1F10PR424.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/151014/9-1510141Q214.jpg");
        images.add("https://img.tupianzj.com/uploads/allimg/151014/9-1510141S623.jpg");
        mCollectionView.setAdapter(new MultiAdapter(images.stream().map(ImageDelegate::new).collect(Collectors.toList())));
    }
}
