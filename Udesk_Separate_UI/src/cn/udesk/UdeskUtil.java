package cn.udesk;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import cn.udesk.activity.UdeskZoomImageActivty;
import cn.udesk.config.UdeskBaseInfo;
import udesk.com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import udesk.com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import udesk.com.nostra13.universalimageloader.core.DisplayImageOptions;
import udesk.com.nostra13.universalimageloader.core.ImageLoader;
import udesk.com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import udesk.com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import udesk.com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import udesk.com.nostra13.universalimageloader.utils.StorageUtils;
import udesk.core.UdeskCoreConst;
import udesk.core.model.MessageInfo;

public class UdeskUtil {
	public static final String  ImgFolderName = "UDeskIMg";
	public static final String  AudioFolderName = "UDeskAudio";
	public static final String  SaveImg = "saveImg";

	public static Uri getOutputMediaFileUri(Context context) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		try {
			  if(getOutputMediaFile(context,"IMG_" + timeStamp + ".jpg") != null){
                  return Uri.fromFile(getOutputMediaFile(context,"IMG_" + timeStamp + ".jpg"));
              }else{
                  return null;
              }
		} catch (Exception e) {
			return null;
		}

	}

	
	/**
	 * 提供的Uri 解析出文件绝对路径
	 * @param uri
	 * @return
	 */
	public static String parseOwnUri(Uri uri,Context context){
		if(uri==null)return "";
	
		return uri.getPath();
	}

	public static File getOutputMediaFile(Context context,String mediaName) {
		File mediaFile = null;
		try {
			File mediaStorageDir = null;
			try {
                mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), ImgFolderName);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

			if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }

			mediaFile = new File(mediaStorageDir.getPath() + File.separator + mediaName);
		} catch (Exception e) {
			return  null;
		}
		return mediaFile;
	}

	public static boolean isExitFile(String path) {
		File file = new File(path);
		return file.exists();
	}


	public static boolean audiofileIsDown(Context context ,String url) {
		if (TextUtils.isEmpty(url)) {
			return false;
		}
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		File mediaStorageDir = new File(
				context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES),
				AudioFolderName);
		if (!mediaStorageDir.exists()) {
			return false;
		}
		String filepath = mediaStorageDir.getPath() + File.separator + fileName;
		File file = new File(filepath);
		return file.exists();
	}


	public static String getDownAudioPath(Context context ,String url) {
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		File mediaStorageDir = new File(
				context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES),
				AudioFolderName);

		return mediaStorageDir.getPath() + File.separator + fileName;
	}


	public static String getOutputAudioPath(Context context) {
		return getOutputAudioPath(context,"audio_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
	}

	public static File getOutputAudioFile(Context context) {
		return getOutputAudioFile(context,"audio_"
				+ new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
	}

	public static File getOutputAudioFile(Context context ,String mediaName) {
		String path = getOutputAudioPath(context,mediaName);
		if (TextUtils.isEmpty(path)) {
			return null;
		} else {
			return new File(path);
		}
	}

	public static String getOutputAudioPath(Context context , String mediaName) {
		File mediaStorageDir = new File(
				context.getExternalFilesDir(Environment.DIRECTORY_RINGTONES),
				AudioFolderName);

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}

		File noMediaFile = new File(mediaStorageDir, ".nomedia");
		if (!noMediaFile.exists()) {
			try {
				noMediaFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return mediaStorageDir.getPath() + File.separator + mediaName;
	}


	public static File getAudioFile(Context context ,String url) {
		String fileName = url.substring(url.lastIndexOf("/") + 1);
		String path = getOutputAudioPath(context,fileName);
		if (TextUtils.isEmpty(path)) {
			return null;
		} else {
			return new File(path);
		}
	}


	public static String buildImageLoaderImgUrl(MessageInfo message){

		if(!TextUtils.isEmpty(message.getLocalPath()) && isExitFile(message.getLocalPath())){
			return "file:///" + message.getLocalPath();
		}else{
			return message.getMsgContent();
		}
	}

	public static String getFormUrlPara(Context context){
		StringBuilder builder = new StringBuilder();
		builder.append("?sdk_token=").append(UdeskSDKManager.getInstance().getSdkToken(context))
				.append("&sdk_version=").append(UdeskCoreConst.sdkversion).append("&app_id=").append(UdeskSDKManager.getInstance().getAppId(context));
		if (!isZh(context)){
			builder.append("&language=en-us");
		}
		Map<String, String> userinfo = UdeskBaseInfo.userinfo;
		Map<String,String> textField =UdeskBaseInfo.textField;
		if(userinfo != null && !userinfo.isEmpty()){
			Set<String> keySet = userinfo.keySet();
			for (String key : keySet) {
				if(!TextUtils.isEmpty(userinfo.get(key))){
					if(key.equals("sdk_token")){
						continue;
					}
					if(key.equals(UdeskConst.UdeskUserInfo.NICK_NAME)){
						builder.append("&c_name=").append(userinfo.get(key));
					}else if(key.equals(UdeskConst.UdeskUserInfo.CELLPHONE)){
						builder.append("&c_phone=").append(userinfo.get(key));
					}else if(key.equals(UdeskConst.UdeskUserInfo.EMAIL)){
						builder.append("&c_email=").append(userinfo.get(key));
					}else if(key.equals(UdeskConst.UdeskUserInfo.DESCRIPTION)){
						builder.append("&c_desc=").append(userinfo.get(key));
					}
				}
			}
		}
		if(textField != null && !textField.isEmpty()){
			Set<String> textFieldSet = textField.keySet();
			for (String key : textFieldSet) {
				if(!TextUtils.isEmpty(textField.get(key))){
					builder.append("&c_cf_").append(key).append("=").append(textField.get(key));
				}
			}
		}
		return builder.toString();
	}


	public static ImageLoaderConfiguration initImageLoaderConfig(Context context){
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				context, "udesksdk/img/cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new WeakMemoryCache())
				.memoryCacheSize(2 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100) // 缓存的文件数量
				.discCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(context,
								5 * 1000, 30 * 1000))
				.build();// 开始构建
		ImageLoader.getInstance().init(config);
		return config;
	}

	public static int getDisplayWidthPixels(Activity activity) {
		DisplayMetrics dMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		return dMetrics.widthPixels;
	}

	public static String formatLongTypeTimeToString(Context context ,long time){
		long OFFSET_DAY = 3600 * 24;
		String timeYes = context.getString(R.string.udesk_im_time_format_yday);
		String timeQt =  context.getString(R.string.udesk_im_time_format_dby);
		String timeDate = "yyyy/MM/dd";
		Calendar calendar = Calendar.getInstance();
		StringBuilder build = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		// 解析需要转化时间
		calendar.setTimeInMillis(time);
		int year = calendar.get(Calendar.YEAR);
		int day = calendar.get(Calendar.DAY_OF_YEAR);

		// 拼接 转化结果
		build.append(" ").append(sdf.format(calendar.getTime()));// 先添加

		// 先解析当前时间。取出当前年，日 等信息
		calendar.setTimeInMillis(System.currentTimeMillis());
		int nowYear = calendar.get(Calendar.YEAR);
		int nowDay = calendar.get(Calendar.DAY_OF_YEAR);

		if (year != nowYear) {// 不是一年内
			calendar.set(Calendar.HOUR_OF_DAY, 0); // 凌晨1点
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);

			if ((calendar.getTimeInMillis() - time) <= OFFSET_DAY) {// 昨天
				return timeYes;
			} else if ((calendar.getTimeInMillis() - time) <= (OFFSET_DAY << 2)) {// 前天
				// 。这里不用判断是否大于OFFSET_DAY
				return timeQt;
			} else {
				sdf.applyLocalizedPattern(timeDate);
				return sdf.format(time);
			}

		} else if (day == nowDay) {// 这里是一年内的当天
			// 当天的话 就不用管了
		} else {// 一年内
			int dayOffset = (nowDay - day);// nowDay要大一些
			if (dayOffset == 0) {
				// 同一天不用 添加日期判断
			} else if (dayOffset == 1) {// 1表示差一天，即昨天
				return timeYes;
			} else if (dayOffset == 2) {// 1表示差两天，即前天
				return timeQt;
			} else {
				timeDate = "MM月dd日";
				sdf.applyLocalizedPattern(timeDate);
				return sdf.format(time);
			}
		}

		return build.toString();
	}


	public static boolean isZh(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		return language.endsWith("zh");
	}


	//预览大图
	public static void previewPhoto(Context context ,String sourceImagePath) {
		try {
			if (TextUtils.isEmpty(sourceImagePath)){
				return;
			}
			File sourceFile = new File(sourceImagePath);
			if (sourceFile.exists()) {
				Intent intent = new Intent(context,
						UdeskZoomImageActivty.class);
				Bundle data = new Bundle();
				data.putParcelable("image_path", Uri.fromFile(sourceFile));
				intent.putExtras(data);
				context.startActivity(intent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static  String getAppName(Context context){
		String appName = "";
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = null;
			info = manager.getPackageInfo(context.getPackageName(), 0);
			appName = info.applicationInfo.loadLabel(manager).toString();
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return appName;
	}
	
	public static String getFilePath(Activity context, Uri uri) {
        String path = "";
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            Cursor cursor = null;
            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    path = getPath(context, uri);
                } else {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    cursor = context.getContentResolver().query(uri, projection, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    path = cursor.getString(column_index);
                }
            } catch (Exception e) {
                return uri.getPath();
            } finally {

                if (cursor != null) {
                    cursor.close();
                    cursor = null;
                }
            }
        }
        if (TextUtils.isEmpty(path.trim())) {
            path = uri.getPath();
        }

        return path;
    }

	 public static String getPath(final Context context, final Uri uri) {

	        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	        // DocumentProvider
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	                // ExternalStorageProvider
	                if (isExternalStorageDocument(uri)) {
	                    final String docId = DocumentsContract.getDocumentId(uri);
	                    final String[] split = docId.split(":");
	                    final String type = split[0];

	                    if ("primary".equalsIgnoreCase(type)) {
	                        return Environment.getExternalStorageDirectory() + "/" + split[1];
	                    }

	                }
	                // DownloadsProvider
	                else if (isDownloadsDocument(uri)) {

	                    final String id = DocumentsContract.getDocumentId(uri);
	                    final Uri contentUri = ContentUris.withAppendedId(
	                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	                    return getDataColumn(context, contentUri, null, null);
	                }
	                // MediaProvider
	                else if (isMediaDocument(uri)) {
	                    final String docId = DocumentsContract.getDocumentId(uri);
	                    final String[] split = docId.split(":");
	                    final String type = split[0];

	                    Uri contentUri = null;
	                    if ("image".equals(type)) {
	                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	                    } else if ("video".equals(type)) {
	                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	                    } else if ("audio".equals(type)) {
	                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	                    }

	                    final String selection = "_id=?";
	                    final String[] selectionArgs = new String[]{split[1]};

	                    return getDataColumn(context, contentUri, selection, selectionArgs);
	                }
	            }
	            // MediaStore (and general)
	            else if ("content".equalsIgnoreCase(uri.getScheme())) {
	                return getDataColumn(context, uri, null, null);
	            }
	            // File
	            else if ("file".equalsIgnoreCase(uri.getScheme())) {
	                return uri.getPath();
	            }
	        }

	        return null;
	    }

	    /**
	     * Get the value of the data column for this Uri. This is useful for
	     * MediaStore Uris, and other file-based ContentProviders.
	     *
	     * @param context       The context.
	     * @param uri           The Uri to query.
	     * @param selection     (Optional) Filter used in the query.
	     * @param selectionArgs (Optional) Selection arguments used in the query.
	     * @return The value of the _data column, which is typically a file path.
	     */
	    public static String getDataColumn(Context context, Uri uri, String selection,
	                                       String[] selectionArgs) {

	        Cursor cursor = null;
	        final String column = "_data";
	        final String[] projection = {column};

	        try {
	            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                    null);
	            if (cursor != null && cursor.moveToFirst()) {
	                final int column_index = cursor.getColumnIndexOrThrow(column);
	                return cursor.getString(column_index);
	            }
	        } finally {
	            if (cursor != null)
	                cursor.close();
	        }
	        return null;
	    }

	    /**
	     * @param uri The Uri to check.
	     * @return Whether the Uri authority is ExternalStorageProvider.
	     */
	    public static boolean isExternalStorageDocument(Uri uri) {
	        return "com.android.externalstorage.documents".equals(uri.getAuthority());
	    }

	    /**
	     * @param uri The Uri to check.
	     * @return Whether the Uri authority is DownloadsProvider.
	     */
	    public static boolean isDownloadsDocument(Uri uri) {
	        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	    }

	    /**
	     * @param uri The Uri to check.
	     * @return Whether the Uri authority is MediaProvider.
	     */
	    public static boolean isMediaDocument(Uri uri) {
	        return "com.android.providers.media.documents".equals(uri.getAuthority());
	    }


	    public static File getExternalCacheDir(final Context context) {
	        if (hasExternalCacheDir())
	            return context.getExternalCacheDir();

	        // Before Froyo we need to construct the external cache dir ourselves
	        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
	        return createFile(Environment.getExternalStorageDirectory().getPath() + cacheDir, "");
	    }

	    public static boolean hasExternalCacheDir() {
	        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	    }

	    public static File createFile(String folderPath, String fileName) {
	        File destDir = new File(folderPath);
	        if (!destDir.exists()) {
	            destDir.mkdirs();
	        }
	        return new File(folderPath, fileName);
	    }
}
