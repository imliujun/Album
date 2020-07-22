package com.yanzhenjie.album.sample;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 项目名称：DSAlbum
 * 类描述：
 * 创建人：cuichao
 * 创建时间：2020/7/21 16:03
 * 修改人：cuichao
 * 修改时间：2020/7/21 16:03
 * 修改备注：
 */
public class FrescoUtils {
    // 紧挨着ImageView添加SimpleDraweeView到原来的ImageView的位置
    private static SimpleDraweeView exchangeChilde(
        ViewGroup parent,
        View oldImageView,
        ViewGroup.LayoutParams layoutParams
    ) {
        SimpleDraweeView draweeview = null;
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (oldImageView == parent.getChildAt(i)) {
                if (oldImageView instanceof ImageView) {
                    ImageView img = (ImageView) oldImageView;
                    img.setBackgroundDrawable(null);
                    img.setImageDrawable(null);
                    img.setVisibility(View.INVISIBLE);
                }
                if (i + 1 < parent.getChildCount()) {
                    View child = parent.getChildAt(i + 1);
                    // 此处理应做更加仔细的判断
                    if (child instanceof SimpleDraweeView) {
                        return (SimpleDraweeView) child;
                    }
                }
                draweeview = new SimpleDraweeView(oldImageView.getContext());
                draweeview.setLayoutParams(layoutParams);
                parent.addView(draweeview, i + 1);
                return draweeview;
            }
        }
        return draweeview;
    }
    
    // 传入加载图片的ImageView,返回一个相同位置,相同大小的SimpleDraweeView
    public static SimpleDraweeView getDraweeView(
        View viewContainer,
        Class<?> classType
    ) {
        if (viewContainer instanceof SimpleDraweeView) {
            return (SimpleDraweeView) viewContainer;
        }
        SimpleDraweeView mDraweeView = null;
        if (classType.isInstance(viewContainer)) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) viewContainer.getLayoutParams();
            mDraweeView = exchangeChilde((ViewGroup) viewContainer.getParent(), viewContainer, params);
        }
        return mDraweeView;
    }
    
    // 该方将ImageView从原来的Parent种移除，并添加到一个FrameLayout中去
    private void addToViewGroup(
        ViewGroup parent,
        View viewOld,
        View viewNew
    ) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (parent.getChildAt(i) == viewOld) {
                parent.removeView(viewOld);
                parent.addView(viewNew, i);
                return;
            }
        }
    }
    
}
