package com.example.yfsl.smartrefreshlayout_demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {
    public final static String BASE_PATH = "AirPort";
    private final static String APK_PATH = "apk";
    private final static String HEADER_PATH = "header";
    private final static String KNOWLEDGE_PATH = "knowledge";
    private final static String KNOWLEDGE_DOWNLOAD_PATH = "knowledge_download";
    private final static String INSPECTION_IMG = "inspection_img";

    private FileUtil() {
    }

    private static File takeImageFile;

    public static boolean checkStorageSpace() {
        //不存在外部存储器
        if (!externalExist()) {
            return false;
        }
        return getExternalStorageAvailableSize() < ((long) 50) * 1024 * 1024;
    }

    private static boolean externalExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static long getExternalStorageAvailableSize() {
        if (!externalExist()) {
            return 0;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        return ((long) statFs.getAvailableBlocks()) * statFs.getBlockSize();
    }

    public static boolean isZTE() {
        return !TextUtils.isEmpty(Build.FINGERPRINT) && Build.FINGERPRINT.startsWith("ZTE");
    }

    public static void take(Activity activity, int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (takePictureIntent.resolveActivity(activity.getApplicationContext().getPackageManager()) != null) {
            takeImageFile = new File(createInspectionPath());
            takeImageFile = createFile(takeImageFile, "IMG_", ".jpg");
            Uri uri;
            if (FileUtil.isBelowAndroidVersion(Build.VERSION_CODES.N)) {
                uri = Uri.fromFile(takeImageFile);
            } else {
                String authorities = FileUtil.getFileProviderName(activity.getApplicationContext());
                uri = FileProvider.getUriForFile(activity.getApplicationContext(), authorities, takeImageFile);
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        activity.startActivityForResult(takePictureIntent, requestCode);
    }

    private static String createInspectionPath() {
        return getRealFilePath(getFilePathName(externalFilesDir(), INSPECTION_IMG));
    }

    private static String getRealFilePath(String path) {
        File p = new File(path);
        if (!p.exists()) {
            p.mkdirs();
        }
        if (!p.exists()) {
            return null;
        }
        return p.getPath();
    }

    private static String getFilePathName(String... args) {
        String[] param = args;
        String Str = args[0];
        int i = 1;

        for (int len = args.length; i < len; ++i) {
            if (Str == null) {
                return null;
            }

            if (Str.endsWith(File.separator) && param[i].startsWith(File.separator)) {
                Str = Str.substring(0, Str.length() - 1) + param[i];
            } else if (!Str.endsWith(File.separator) && !param[i].startsWith(File.separator)) {
                Str = Str + File.separator + param[i];
            } else {
                Str = Str + param[i];
            }
        }

        return Str;
    }

    private static String externalFilesDir() {
        if (externalExist()) {
            return getFilePathName(Environment.getExternalStorageDirectory().getAbsolutePath(), BASE_PATH);
        }
        return null;
    }

    private static File createFile(File folder, String prefix, String suffix) {
        if (!folder.exists() || !folder.isDirectory()) folder.mkdirs();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String filename = prefix + dateFormat.format(new Date(System.currentTimeMillis())) + suffix;
        return new File(folder, filename);
    }

    public static boolean isBelowAndroidVersion(int version) {
        return Build.VERSION.SDK_INT < version;
    }

    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".fileprovider";
    }
}
