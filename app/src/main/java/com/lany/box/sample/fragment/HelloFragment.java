package com.lany.box.sample.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lany.box.adapter.MultiAdapter;
import com.lany.box.delegate.ItemDelegate;
import com.lany.box.fragment.BaseFragment;
import com.lany.box.sample.R;
import com.lany.box.sample.delegate.HelloDelegate;
import com.lany.box.utils.DensityUtils;
import com.lany.itemdecoration.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HelloFragment extends BaseFragment {
    @BindView(R.id.showView)
    RecyclerView mShowView;

    @Override
    protected int getLayoutId() {
        return R.layout.hello;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mShowView.setLayoutManager(manager);
        mShowView.addItemDecoration(new LinearItemDecoration(manager.getOrientation())
                .setPadding(DensityUtils.dp2px(8))
                .setColor(Color.GRAY)
                .setWidth(1));
        String pics[] = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550479961661&di=58f0ec0ba23d0bf8cff25499d8a93623&imgtype=0&src=http%3A%2F%2Fbannerdesign.cn%2Fwp-content%2Fuploads%2F2015%2F02%2F20150204014336322.jpg", "http://e.hiphotos.baidu.com/image/pic/item/eac4b74543a982265bd540e38782b9014b90ebda.jpg", "http://d.hiphotos.baidu.com/image/pic/item/4b90f603738da977625f2cf7bd51f8198718e3fe.jpg", "https://wx3.sinaimg.cn/bmiddle/0060lm7Tgy1g22fgqr55oj30u00k00um.jpg", "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22ff9ge8zj30u00s777u.jpg", "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fhvz2z5j30k00f0mya.jpg", "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22ff31rzuj30u00s077j.jpg", "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fhjkxdoj30hv0ih3zf.jpg", "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fh7l9qhj30u01hc7an.jpg", "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fi7pdy6j30u0140n35.jpg", "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fhwgxx2j30u0140djp.jpg", "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22fhw7p7aj30u014041u.jpg", "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22ff9oa5pj30u0140gpd.jpg", "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22ffkxeptj30rs0rst9k.jpg", "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22ff37jsaj30u00l6acv.jpg", "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22ff3ay1hj30k00plq57.jpg", "https://wx4.sinaimg.cn/bmiddle/0060lm7Tgy1g22fgkxa75j30u00mijwr.jpg", "https://wx2.sinaimg.cn/bmiddle/0060lm7Tgy1g22fidcdp4j30tr1sg7a5.jpg", "https://wx3.sinaimg.cn/bmiddle/0060lm7Tgy1g22fhpsiy1j30u0140dhi.jpg", "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22fflat9oj30u0140tkl.jpg", "https://wx2.sinaimg.cn/bmiddle/0060lm7Tgy1g22fft14xtj30u0140wqo.jpg", "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22ffsig5wj30u0140wqn.jpg", "https://wx1.sinaimg.cn/bmiddle/0060lm7Tgy1g22ffs63lhj30u0140gv0.jpg"};
        List<ItemDelegate> items = new ArrayList<>();
        for (String pic : pics) {
            items.add(new HelloDelegate(pic));
        }
        mShowView.setAdapter(new MultiAdapter(items));
    }
}