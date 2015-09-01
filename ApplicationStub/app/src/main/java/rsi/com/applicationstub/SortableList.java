package rsi.com.applicationstub;

import java.util.ArrayList;
import java.util.Comparator;

public class SortableList<T> {

    ArrayList<T> mData;

    Comparator<T> mComparator;

    Callback<T> mCallbackk;

    public SortableList() {
        mData = new ArrayList<>();
    }

    public interface Callback<T> {

        int compare(T object1, T object2);

        void onInserted(int position, int count);

        void onRemoved(int position, int count);

        void onMoved(int fromPosition, int toPosition);

        void onChanged(int position, int count);

        boolean areContentsTheSame(T oldT, T newT);

        boolean areItemsTheSame(T object1, T object2);
    }
}
