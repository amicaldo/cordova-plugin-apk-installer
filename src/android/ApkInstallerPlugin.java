package de.amicaldo.cordova.plugin;

import android.content.Intent;
import android.content.Context;
import android.widget.*;
import org.apache.cordova.*;
import org.json.JSONArray;
import android.support.v4.content.FileProvider;
import android.net.Uri;
import java.io.File;


public class ApkInstallerPlugin extends CordovaPlugin {
    private static final String MYME_TYPE_APK = "application/vnd.android.package-archive";

    @Override
    public boolean execute(
        String action,
        JSONArray args,
        CallbackContext callbackContext
    ) {
        switch (action) {
            case "install":
                String file = args.getString(0);

                if (this.install(file)) {
                    callbackContext.success();

                    return true;
                } else {
                    callbackContext.error("Could not install " + file);

                    return false;
                }
        }

        callbackContext.error("Action not found");

        return false;
    }

    private boolean install(String filename) {
        try {
            Context context = this.cordova.getActivity().getApplicationContext();
            String filepath = context.getFilesDir() + "/" + filename;
            File file = new File(context.getFilesDir() + "/" + filename);

            // Get file uri
            Uri uri = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".fileprovider",
                file
            );
            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setDataAndType(uri, this.MYME_TYPE_APK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            );
            context.startActivity(intent);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
