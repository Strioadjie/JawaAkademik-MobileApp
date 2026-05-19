package id.kelompok5.jawaakademik;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public final class AppToast {

    private AppToast() {}

    public static void show(Context context, String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        TextView tvMessage = view.findViewById(R.id.tvToastMessage);
        tvMessage.setText(message);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 120);
        toast.setView(view);
        toast.show();
    }
}
