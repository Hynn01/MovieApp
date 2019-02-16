package cuc.edu.cn.hynnsapp02.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseRecyclerAdapter_GridView<T> extends RecyclerView.Adapter<SmartViewHolder_Improved> implements ListAdapter {


    //<editor-fold desc="BaseRecyclerAdapter">

    private final int mLayoutId;
    private final List<T> mList;
    private int mLastPosition = -1;
    private boolean mOpenAnimationEnable = true;
    protected AdapterView.OnItemClickListener mListener;

    public BaseRecyclerAdapter_GridView(@LayoutRes int layoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>();
        this.mLayoutId = layoutId;
    }

    public BaseRecyclerAdapter_GridView(Collection<T> collection, @LayoutRes int layoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>(collection);
        this.mLayoutId = layoutId;
    }

    public BaseRecyclerAdapter_GridView(Collection<T> collection, @LayoutRes int layoutId, AdapterView.OnItemClickListener listener) {
        setHasStableIds(false);
        setOnItemClickListener(listener);
        this.mList = new ArrayList<>(collection);
        this.mLayoutId = layoutId;
    }
    //</editor-fold>


    private void addAnimate(SmartViewHolder_Improved holder, int postion) {
        if (mOpenAnimationEnable && mLastPosition < postion) {
            holder.itemView.setAlpha(0);
            holder.itemView.animate().alpha(1).start();
            mLastPosition = postion;
        }
    }


    //<editor-fold desc="RecyclerAdapter">
    @Override
    public SmartViewHolder_Improved onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SmartViewHolder_Improved(LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false),mListener);
    }

    @Override
    public void onBindViewHolder(SmartViewHolder_Improved holder, int position) {
        onBindViewHolder(holder, position < mList.size() ? mList.get(position) : null, position);
    }

    protected abstract void onBindViewHolder(SmartViewHolder_Improved holder, T model, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onViewAttachedToWindow(SmartViewHolder_Improved holder) {
        super.onViewAttachedToWindow(holder);
        addAnimate(holder, holder.getLayoutPosition());
    }

    public void setOpenAnimationEnable(boolean enabled) {
        this.mOpenAnimationEnable = enabled;
    }

    //</editor-fold>

    //<editor-fold desc="API">

    public T get(int index) {
        return mList.get(index);
    }

    public BaseRecyclerAdapter_GridView<T> setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
        return this;
    }

    public BaseRecyclerAdapter_GridView<T> refresh(Collection<T> collection) {
        mList.clear();
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        mLastPosition = -1;
        return this;
    }

    public BaseRecyclerAdapter_GridView<T> loadMore(Collection<T> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }

    public BaseRecyclerAdapter_GridView<T> insert(Collection<T> collection) {
        mList.addAll(0, collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }


    //</editor-fold>

    //<editor-fold desc="ListAdapter">
    private final DataSetObservable mDataSetObservable = new DataSetObservable();

//    public boolean hasStableIds() {
//        return false;
//    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    /**
     * Notifies the attached observers that the underlying data has been changed
     * and any View reflecting the data set should refresh itself.
     */
    public void notifyListDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    /**
     * Notifies the attached observers that the underlying data is no longer valid
     * or available. Once invoked this adapter is no longer valid and should
     * not report further data set changes.
     */
    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SmartViewHolder_Improved holder;
        if (convertView != null) {
            holder = (SmartViewHolder_Improved) convertView.getTag();
        } else {
            holder = onCreateViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        }
        holder.setPosition(position);
        onBindViewHolder(holder, position);
        addAnimate(holder, position);
        return convertView;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    //</editor-fold>

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView (recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager ();
        if(manager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager)manager;
            gridLayoutManager.setSpanSizeLookup (new GridLayoutManager.SpanSizeLookup () {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
        }
    }


    public void clean() {
        mList.clear();
    }
}