package com.example.user.moo_ptt4;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


public class DelayedMessageService extends IntentService {

    public static final String EXTRA_MESSAGE = "message";
    public static final int NOTIFICATION_ID = 5453;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    public DelayedMessageService() {
        super("DelayedMessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            try {
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);
    }

    private void showText(final String text) {
        //按下訊息時畫面會跳出的目的地
        Intent actionIntent = new Intent(this, MainActivity.class);
        Intent actionIntent2 = new Intent(this, NotificationActivity.class);
       // Intent actionIntent3 = new Intent(this, Main2Activity.class);

        //PendingIntent 可以傳給其他app的 intent
        PendingIntent pendingIntent = PendingIntent.getActivity(DelayedMessageService.this, 0, actionIntent, 0);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(DelayedMessageService.this, 0, actionIntent2, 0);
      //  PendingIntent btPendingIntent = PendingIntent.getBroadcast(DelayedMessageService.this, 0, actionIntent3,0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //sdk版本大于26，26以上多加了一個類別 NotificationChannel
        if (Build.VERSION.SDK_INT >= 26) {
            String id = "channel_1";
            String description = "143";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, description, importance);
            channel.enableLights(true);
            channel.enableVibration(true); // 不一定有用，手機設定也要一起做設定
            channel.setLightColor(Color.RED); // 不一定有用的功能，硬體也要支援
            manager.createNotificationChannel(channel);

            Notification notification = new Notification.Builder(DelayedMessageService.this, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    //small icon 必加，compile 會過，但閃退
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setContentIntent(pendingIntent) // 若沒加點擊後的默認目的是app main，應該也是要必加
                    .setContentTitle(getString(R.string.question))
                    .addAction(R.mipmap.ic_launcher, "回復", pendingIntent2) // 按下關閉會跳到main2
                    .setContentIntent(pendingIntent)
                    .setContentText(text).
                            setVibrate(new long[]{0, 1000}).
                            setAutoCancel(true).build();
            manager.notify(1, notification);

//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(DelayedMessageService.this, id);
//            builder.setSmallIcon(android.R.drawable.ic_dialog_info);
//            builder.setContentIntent(pendingIntent);
//            builder.setContentTitle("Notifications Title");
//            builder.setContentText(text);
//            builder.setSubText("Tap to view the website.");
//            android.support.v4.app.NotificationCompat.BigTextStyle style = new android.support.v4.app.NotificationCompat.BigTextStyle();
////bigText 给样式设置大文本内容
//            style.bigText("这里是点击通知后要显示的正文，正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字正文很多字");
////setBigContentTitle 给样式设置大文本的标题
//            style.setBigContentTitle("点击后的标题");
////SummaryText没什么用 可以不设置
//            style.setSummaryText("末尾只一行的文字内容");
////setStyle 将样式添加到通知
//            builder.setStyle(style); // 畫面沒顯示????
//           builder.addAction(R.mipmap.ic_launcher, "左按钮", pendingIntent); //图标，文字，点击事件
//
//            notificationManager.notify(1, builder.build());

        } else {
            Notification notification = new NotificationCompat.Builder(DelayedMessageService.this)
                    .setContentTitle(getString(R.string.question))
                    .setContentIntent(pendingIntent)
                    .setContentText(text)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon).
                            setAutoCancel(true)
                    .build();

            manager.notify(1, notification);
        }

    }


}
