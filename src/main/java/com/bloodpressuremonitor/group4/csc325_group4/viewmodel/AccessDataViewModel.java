package com.bloodpressuremonitor.group4.csc325_group4.viewmodel;

public class AccessDataViewModel {

    //store data for first time login popups
    private static boolean first = true;
    public static boolean firstLaunch(){
        return first;
    }
    public static void setFirstLaunch(boolean f){
        first = f;
    }
}
