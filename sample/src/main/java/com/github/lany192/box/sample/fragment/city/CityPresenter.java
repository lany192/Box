package com.github.lany192.box.sample.fragment.city;

import com.github.lany192.box.delegate.ItemDelegate;
import com.github.lany192.box.mvp.presenter.BasePresenter;
import com.github.lany192.box.sample.bean.Area;
import com.github.lany192.box.sample.bean.Result;
import com.github.lany192.box.sample.delegate.AreaDelegate;
import com.github.lany192.box.sample.http.ApiService;
import com.github.lany192.box.utils.NetUtils;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CityPresenter extends BasePresenter<CityContract.View, ApiService>
        implements CityContract.Presenter {

    @Inject
    public CityPresenter(CityFragment view, ApiService model) {
        super(view, model);
    }

    @Override
    public void init() {
        if (NetUtils.isNetWorkAvailable()) {
            request();
        } else {
            getView().showNoWifi();
        }
    }

    private void request() {
        getModel().getCityInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result<List<Area>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        getView().showLoading();
                    }

                    @Override
                    public void onNext(Result<List<Area>> result) {
                        getView().showContent();
                        List<ItemDelegate> items = new ArrayList<>();
                        for (Area area : result.getData()) {
                            items.add(new AreaDelegate(area));
                        }
                        getView().showCities(items);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(e.getMessage());
                        e.printStackTrace();
                        ToastUtils.show("请求异常" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        getView().showContent();
                    }
                });
    }
}