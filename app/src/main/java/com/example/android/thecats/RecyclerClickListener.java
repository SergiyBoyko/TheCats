package com.example.android.thecats;

import android.view.View;

/**
 * Created by fbrsw on 13.10.2017.
 */

public class RecyclerClickListener implements View.OnClickListener {
    private int pos;

    public void show(View v, int pos) {
        this.pos = pos;
        onClick(v);
    }

    public int getPos() {
        return pos;
    }

    @Override
    public void onClick(View view) {
    }
}
