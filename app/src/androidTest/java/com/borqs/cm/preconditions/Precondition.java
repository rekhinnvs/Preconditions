package com.borqs.cm.preconditions;

import android.content.Context;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.provider.Contacts;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 26)
public class Precondition {

    private static final String TAG = "STABILIIY";
    private static final String gmailPackage = "com.google.android.gm";
    private static final int LAUNCH_TIMEOUT = 3000;
    private static final int OPEN_TIMEOUT = 2000;
    private static UiDevice mDevice;
    private static String calculatorPackage = "com.google.android.calculator";
    private static String chromePackage = "com.android.chrome";
    private Context context;

    @BeforeClass
    public static void before() throws Exception {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }

    @Before
    public void initialise() throws Exception {
        context = InstrumentationRegistry.getContext();
        mDevice.pressHome();
    }

    @Ignore
    public void launchCalculator() throws Exception {

        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(calculatorPackage);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        mDevice.wait(Until.hasObject(By.pkg(calculatorPackage).depth(0)), LAUNCH_TIMEOUT);

        UiObject calculatorMode = mDevice.findObject(new UiSelector().resourceId("com.google.android.calculator:id/mode"));
        String mode = calculatorMode.getText();
        assertEquals("DEG", mode);


    }

    @Ignore
    public void disablegoogleSync() throws Exception {

        UiObject syncSettings = mDevice.findObject(new UiSelector().className("android.support.v7.widget.RecyclerView")
                .childSelector(new UiSelector().className("android.widget.LinearLayout").instance(3)));
        launchIntent("android.settings.SYNC_SETTINGS");
        syncSettings.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        UiObject syncItems = mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/list")); //Recycler view
        int numberOfItems = syncItems.getChildCount();
        boolean flag = true;
        Log.i("TAG", "Number of children : " + numberOfItems);
        //numberOfItems*=3;
        for (int i = 0; i < numberOfItems; i++) {
            Log.i(TAG, "value of i : " + i);
            Log.i(TAG, "number of children : " + numberOfItems);
            if ((i == (numberOfItems - 1)) && flag) {
                Log.i(TAG, "Number of children before scroll : " + numberOfItems);
                mDevice.drag(682, 2057, 677, 932, 300);
                numberOfItems = syncItems.getChildCount();
                Log.i(TAG, "Number of children after scroll : " + numberOfItems);
                //numberOfItems*=3;
                flag = false;
            }
            UiObject syncingApps = syncItems.getChild(new UiSelector().className("android.widget.LinearLayout").index(i));
            UiObject checkStatus = syncingApps.getChild(new UiSelector().className("android.widget.RelativeLayout"))
                    .getChild(new UiSelector().resourceId("android:id/summary")); //get the current sync status
            UiObject gmailSync = syncingApps.getChild(new UiSelector().className("android.widget.RelativeLayout"))
                    .getChild(new UiSelector().resourceId("android:id/title")); //get the app name
            Log.i(TAG, "App name : " + gmailSync.getText());
            if ((gmailSync.getText().contains("Gmail"))) {
                if (checkStatus.getText().contains("OFF")) {
                    syncingApps.click();
                    continue;
                } else
                    continue;
            }
            syncingApps.click();
        }
    }

    @Ignore
    public void modifyDisplaySettings() throws Exception { // Issues - Toggle button on auto rotate disables and enables by itself.
        launchIntent("android.settings.DISPLAY_SETTINGS");
        UiObject entireView = mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/list"));
        UiObject moreDisplaySettings = entireView.getChild(new UiSelector().className("android.widget.LinearLayout").index(4));
        moreDisplaySettings.clickAndWaitForNewWindow(OPEN_TIMEOUT);
        //UiObject sleepTime = entireView.getChild(new UiSelector().className("android.widget.LinearLayout").index(4));
        //sleepTime.click();
        //UiObject thirtyMinute = mDevice.findObject(new UiSelector().textStartsWith("30"));
        //thirtyMinute.clickAndWaitForNewWindow();
        /*UiObject autoRotateScreen = entireView.getChild(new UiSelector().className("android.widget.LinearLayout").index(5))
                .getChild(new UiSelector().resourceId("android:id/widget_frame"))
                .getChild(new UiSelector().resourceId("android:id/switch_widget"));
        *///autoRotateScreen.click();
        //autoRotateScreen.swipeLeft(3);
        //autoRotateScreen.longClick();
        //UiObject atr = mDevice.findObject(new UiSelector().textStartsWith("Auto-rotate"));

    }

    /* Write only app related test case below */

    @Ignore
    public void skipChrome() throws Exception {

        UiObject chromeMenu = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/menu_button"));
        UiObject test = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/toolbar_buttons").childSelector(new UiSelector().resourceId("com.android.chrome:id/tab_switcher_button")));
        UiObject closeAllTabs = mDevice.findObject(new UiSelector().descriptionContains("Close all tabs"));
        UiObject reportToGoogle = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/send_report_checkbox"));
        UiObject acceptAndContinue = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/terms_accept"));
        UiObject noThanks = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/negative_button"));
        UiObject homeButton = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/home_button"));
        UiObject bookMarks = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/menu_item_text").text("Bookmarks"));
        UiObject closeSignInprompt = mDevice.findObject(new UiSelector().resourceId("com.android.chrome:id/signin_promo_close_button"));
        launchPackage(chromePackage);
        reportToGoogle.click();
        acceptAndContinue.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        noThanks.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        assertTrue(homeButton.exists());
        chromeMenu.click();
        bookMarks.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        closeSignInprompt.click();
        mDevice.pressHome();
    }

    @Ignore
    public void skipGmail() throws Exception {

        UiObject closeSenderImage = mDevice.findObject(new UiSelector().resourceId("com.google.android.gm:id/dismiss_icon"));
        launchPackage(gmailPackage);
        closeSenderImage.click();
        mDevice.pressHome();

    }

    @Ignore
    public void skipCalendar() throws Exception {
        launchPackage("com.google.android.calendar");
        UiObject welcomeScreen = findByResourceID("android.widget.TextView", "Calendar");
        UiObject rightSwipe = findByResourceID("com.google.android.calendar:id/right_arrow_touch");
        UiObject gotItButton = findByResourceID("com.google.android.calendar:id/done_button");
        UiObject dismissSync = findByResourceID("com.google.android.calendar:id/negative_button");
        sleep(2000);
        for (int i = 0; i < 3; i++)
            rightSwipe.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        gotItButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        if (dismissSync.exists()) {
            dismissSync.click();
        }
    }

    @Test
    public void cameraPermission() throws Exception {
        UiObject launcherButton = findByResourceID("com.cloudminds.launcher3:id/all_apps_handle");
        UiObject camera = findByResourceID("com.cloudminds.launcher3:id/icon", "Camera");
        UiObject appInfo = findByText("info");
        UiObject permissions = findByText("Permissions");
        launcherButton.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        camera.longClick();
        appInfo.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        permissions.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
        enablePermissions();
    }

    private void launchPackage(String packageName) {

        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), LAUNCH_TIMEOUT);
    }

    private void launchIntent(String intent) throws Exception {
        String command = "am start -a " + intent;
        mDevice.executeShellCommand(command);
        sleep(2000);
    }

    private void enablePermissions() throws Exception {
        UiObject container = findByResourceID("android:id/list");
        int noOfPermission = container.getChildCount();
        Log.i(TAG, "Number of permissions " + noOfPermission);
        for (int i = 0; i < noOfPermission; i++) {
            UiObject permission = container.getChild(new UiSelector().className("android.widget.LinearLayout").index(i));
            UiObject toggleSwitch = permission.getChild(new UiSelector().resourceId("android:id/switch_widget"));
            //Log.i(TAG,"Toggle switch status : "+toggleSwitch.getText());
            if(toggleSwitch.getText().equals("OFF"))
                toggleSwitch.click();
            sleep(1000);
        }


    }

    private UiObject findByResourceID(String resourceID) {
        return mDevice.findObject(new UiSelector().resourceId(resourceID));
    }

    private UiObject findByResourceID(String resourceID, String text) {
        return mDevice.findObject(new UiSelector().resourceId(resourceID).textContains(text));
    }

    private UiObject findByText(String text) {
        return mDevice.findObject(new UiSelector().textContains(text));
    }

    private UiObject findByClass(String className) {
        return mDevice.findObject(new UiSelector().className(className));
    }

}
