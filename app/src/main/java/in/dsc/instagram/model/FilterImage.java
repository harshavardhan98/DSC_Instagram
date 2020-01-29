package in.dsc.instagram.model;

import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.Filter;

public class FilterImage {
    Bitmap image;
    Filter filter;

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    String filterName;

    public FilterImage(Bitmap image, Filter filter,String filterName) {
        this.image = image;
        this.filter = filter;
        this.filterName=filterName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}

