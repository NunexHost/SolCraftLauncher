package soldev.solcraftlauncher.fragments;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ExpandableListAdapter;

import soldev.solcraftlauncher.JavaGUILauncherActivity;
import soldev.solcraftlauncher.R;
import soldev.solcraftlauncher.Tools;
import soldev.solcraftlauncher.modloaders.ModloaderListenerProxy;
import soldev.solcraftlauncher.modloaders.OptiFineDownloadTask;
import soldev.solcraftlauncher.modloaders.OptiFineUtils;
import soldev.solcraftlauncher.modloaders.OptiFineVersionListAdapter;

import java.io.File;
import java.io.IOException;

public class OptiFineInstallFragment extends ModVersionListFragment<OptiFineUtils.OptiFineVersions> {
    public static final String TAG = "OptiFineInstallFragment";
    private static ModloaderListenerProxy sTaskProxy;
    @Override
    public int getTitleText() {
        return R.string.of_dl_select_version;
    }

    @Override
    public int getNoDataMsg() {
        return R.string.of_dl_failed_to_scrape;
    }

    @Override
    public ModloaderListenerProxy getTaskProxy() {
        return sTaskProxy;
    }

    @Override
    public OptiFineUtils.OptiFineVersions loadVersionList() throws IOException {
        return OptiFineUtils.downloadOptiFineVersions();
    }

    @Override
    public void setTaskProxy(ModloaderListenerProxy proxy) {
        sTaskProxy = proxy;
    }

    @Override
    public ExpandableListAdapter createAdapter(OptiFineUtils.OptiFineVersions versionList, LayoutInflater layoutInflater) {
        return new OptiFineVersionListAdapter(versionList, layoutInflater);
    }

    @Override
    public Runnable createDownloadTask(Object selectedVersion, ModloaderListenerProxy listenerProxy) {
        return new OptiFineDownloadTask((OptiFineUtils.OptiFineVersion) selectedVersion, listenerProxy);
    }

    @Override
    public void onDownloadFinished(Context context, File downloadedFile) {
        Intent modInstallerStartIntent = new Intent(context, JavaGUILauncherActivity.class);
        OptiFineUtils.addAutoInstallArgs(modInstallerStartIntent, downloadedFile);
        context.startActivity(modInstallerStartIntent);
    }
}
