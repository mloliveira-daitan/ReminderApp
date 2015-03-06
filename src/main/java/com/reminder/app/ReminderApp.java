package com.reminder.app;

import com.bandwidth.sdk.*;
import com.bandwidth.sdk.model.Call;

public class ReminderApp
{

    private static String toNumber = "+1530300777";// your phone number here
    private static String fromNumber = "+1";// this is a number that is allocated on the AppPlatform. You can do this
    // via the dev console or with the SDK (see AllocateNumberExample)


    public static void main( String[] args ) throws Exception {




        Call call = Call.create(toNumber, fromNumber);



        Thread.sleep(7000);

        call.hangUp();


    }
}
