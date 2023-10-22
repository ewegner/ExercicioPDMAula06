package com.example.exerciciopdmaula06;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import java.util.List;
import java.util.Map;

public class MeuAdaptador extends SimpleAdapter {
    public MeuAdaptador(Context ctx, List<Map<String, Object>> lista, int umaLinha, String[] de, int[] para) {
        super(ctx, lista, umaLinha, de, para);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        TextView temp  = (TextView) v.findViewById(R.id.txtTemp);
        TextView hum   = (TextView) v.findViewById(R.id.txtHum);
        TextView dew   = (TextView) v.findViewById(R.id.txtDew);
        TextView pres  = (TextView) v.findViewById(R.id.txtPres);
        TextView speed = (TextView) v.findViewById(R.id.txtSpe);
        TextView dir   = (TextView) v.findViewById(R.id.txtDir);
        TextView date  = (TextView) v.findViewById(R.id.txtDate);
        if (position % 2 == 0) {
            v.setBackgroundColor(Color.parseColor("#CCCCCC"));

            temp.setTextColor(Color.parseColor("#000000"));
            hum.setTextColor(Color.parseColor("#000000"));
            dew.setTextColor(Color.parseColor("#000000"));
            pres.setTextColor(Color.parseColor("#000000"));
            speed.setTextColor(Color.parseColor("#000000"));
            dir.setTextColor(Color.parseColor("#000000"));
            date.setTextColor(Color.parseColor("#000000"));
        } else {
            v.setBackgroundColor(Color.parseColor("#FFFFFF"));

            temp.setTextColor(Color.parseColor("#000000"));
            hum.setTextColor(Color.parseColor("#000000"));
            dew.setTextColor(Color.parseColor("#000000"));
            pres.setTextColor(Color.parseColor("#000000"));
            speed.setTextColor(Color.parseColor("#000000"));
            dir.setTextColor(Color.parseColor("#000000"));
            date.setTextColor(Color.parseColor("#000000"));
        }

        return v;
    }
}
