package com.qiyi.lens.ui.viewinfo.json;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import com.qiyi.lens.ui.viewinfo.uicheck.IRect;
import com.qiyi.lens.utils.iface.IUIVerifyFactory;

public class ViewJson implements IJson, IRect {

    View mView;
    private Rect mRect;
    String mType;
    private IUIVerifyFactory mFactory;

    public ViewJson(IUIVerifyFactory factory, View view, Rect rect) {
        mRect = rect;
        mView = view;
        mType = "view";
        mFactory = factory;

    }

    @Override
    public String toJson() {
        JsonCompiler compiler = new JsonCompiler();
        compileJson(compiler);
        return compiler.value();
    }

    @CallSuper
    protected void compileJson(JsonCompiler compiler) {
//        IUIVerifyFactory factory = LensConfig.getInstance().getUIVeryfyFactory();
        compiler.addPair("type", mType);
        compiler.addPair("frame", new Frame(mRect));
        if (mView.getBackground() != null) {
            compiler.addPair("backgroundColor", new ViewBackGround(mFactory, mView.getBackground()));
        }
        compiler.addPair("opacity", mView.getAlpha());

        //self defined other info
        mFactory.onJsonBuildView(mView, compiler);
    }

    @Override
    public void toJson(StringBuilder builder) {
        builder.append(toJson());
    }

    @Override
    public boolean isInside(float x, float y) {
        return x > mRect.left && x < mRect.right && y > mRect.top && y < mRect.bottom;
    }

    public View getView() {
        return mView;
    }

    @Override
    public @NonNull
    String toString() {
        return mView + " " + mRect;
    }
}
