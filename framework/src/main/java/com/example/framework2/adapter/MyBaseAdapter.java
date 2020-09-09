package com.example.framework2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class MyBaseAdapter<T> extends RecyclerView.Adapter<MyBaseAdapter.BaseViewHolder> {
    private BaseRVAdapter.IRecyclerViewItemClickListener iRecyclerViewItemClickListener;
    private ArrayList<T> dataList = new ArrayList<>();

    public void upData(List<T> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        dataList.clear();
        dataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void removeOneData(T dataBean) {
        dataList.remove(dataBean);
        notifyDataSetChanged();
    }

    public void addOneData(T dataBean) {
        dataList.add(dataBean);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent,false));
    }

    protected abstract int getLayoutId(int id);

    //需要子类来，渲染UI
    protected abstract void convert(T itemData, MyBaseAdapter.BaseViewHolder baseViewHolder, int position);

    public T getItemData(int position) {
        return dataList.get(position);
    }

    //让子类来实现
    protected abstract int getViewType(int position);

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, final int position) {
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iRecyclerViewItemClickListener != null) {
                    iRecyclerViewItemClickListener.onItemClick(position);//设置Item的点击事件
                }
            }
        });

        T itemData = getItemData(position);
        convert(itemData, baseViewHolder, position);//通过position，将itemData转换成需要的类型，并且将baseViewHoder也转换成需要的viewHolder
    }

    //通过这个position来返回一种对应的布局类型，让子类来指定
    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public BaseRVAdapter.IRecyclerViewItemClickListener getiRecyclerViewItemClickListener() {
        return iRecyclerViewItemClickListener;
    }

    public void setiRecyclerViewItemClickListener(BaseRVAdapter.IRecyclerViewItemClickListener iRecyclerViewItemClickListener) {
        this.iRecyclerViewItemClickListener = iRecyclerViewItemClickListener;
    }

    public static class BaseViewHolder extends RecyclerView.ViewHolder {

        HashMap<Integer, View> viewHashMap = new HashMap<>();

        public BaseViewHolder(View rootView) {
            super(rootView);
        }

        //泛型方法，可以通过它获取view，并且强制转换成需要的view类型,可以参考系统的findViewById
        public <V extends View> V getView(@IdRes int id) {
            View view = viewHashMap.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                viewHashMap.put(id, view);
            }

            return (V) view;
        }
    }
}
