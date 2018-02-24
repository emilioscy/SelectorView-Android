package com.emcy.selector;

import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This class is used with ObjectSelector.
 * It is responsible for getting the adapter data of Object<T> asynchronously
 * It's main method is getData() of List<T> which has to be implemented be the user
 * and return the data to be shown
 *
 * @param <T> : The object class
 */
class SelectorData<T extends SelectorDataGetter> extends AsyncTask<Void, Void, Void> {

    private Class<T> tClass;
    private OnSelectorDataResult resultListener;

    SelectorData(Class<T> tClass, OnSelectorDataResult resultListener) {
        this.tClass = tClass;
        this.resultListener = resultListener;
        executeOnExecutor(new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()));
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            SelectorDataGetter instance = tClass.newInstance();
            if (instance != null) {
                getData();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void getData() {
        try {
            SelectorDataGetter instance = tClass.newInstance();
            resultListener.setData(instance.getData());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    interface OnSelectorDataRequester<T> {
        List<T> getData();
    }

    public interface OnSelectorDataResult<T> {
        void setData(List<T> list);
    }

}