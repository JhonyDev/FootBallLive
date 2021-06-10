package com.bakrin.fblive.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bakrin.fblive.R;
import com.bakrin.fblive.action.DialogAction;
import com.bakrin.fblive.action.DialogType;
import com.bakrin.fblive.listener.DialogActionListener;

public class CustomDialog {


    private static Dialog dialogProgressbar;

    public static void showGeneralDialog(Context context, String title, String msg, DialogType type,
                                         final DialogActionListener actionListener) {


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg)
                .setPositiveButton("OK", (dialog, id) -> {
                    dialog.dismiss();
                    if (actionListener != null) {
                        actionListener.onDialogAction(DialogAction.OK);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        if (type == DialogType.ERROR) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.red_text));
        } else {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.text_blue));
        }


    }


    /**
     * show progress bar
     */
    public static Dialog showProgressDialog(Context context, String text) {
        try {
            if (dialogProgressbar != null) {
                try {
                    dialogProgressbar.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialogProgressbar = null;
            }
            dialogProgressbar = new Dialog(context);
            dialogProgressbar.setCancelable(false);
            dialogProgressbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogProgressbar.setContentView(R.layout.progress_bar);
            TextView progressbarTextView = (TextView) dialogProgressbar.findViewById(R.id.progressbarTextView);
            if (text.trim().isEmpty()) {
                progressbarTextView.setVisibility(View.GONE);
            } else {
                progressbarTextView.setVisibility(View.VISIBLE);
                progressbarTextView.setText(text);
            }
            dialogProgressbar.show();
            return dialogProgressbar;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * hide progress bar
     */
    public static void hideProgressDialog() {
        try {
            if (dialogProgressbar != null) {
                dialogProgressbar.dismiss();
                dialogProgressbar = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Confirm alert box
     */
    public static void showConfirmAlert(Context context, String title, String msg,
                                        final DialogActionListener listener) {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(msg)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            if (listener != null) {
                                listener.onDialogAction(DialogAction.OK);
                            }
                        }
                    });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (listener != null) {
                        listener.onDialogAction(DialogAction.CANCEL);
                    }
                }
            });
            // Create the AlertDialog object and return it
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            throw e;
        }


    }
}
