package com.example.personalhealthmonitor.utils;

public class UtilsValue {

    public static final String TEMPERATURA = "Temperatura";
    public static final String PRESSIONE_SISTOLICA = "Pressione Sistolica";
    public static final String PRESSIONE_DIASTOLICA = "Pressione Diastolica";
    public static final String GLICEMIA = "Glicemia";
    public static final String PESO = "Peso";

    public static final String DB_TEMPERATURA = "infoTemperatura";
    public static final String DB_PRESSIONE_SISTOLICA = "infoPressioneSistolica";
    public static final String DB_PRESSIONE_DIASTOLICA = "infoPressioneDiastolica";
    public static final String DB_GLICEMIA = "infoGlicemia";
    public static final String DB_PESO = "infoPeso";

    //shared prefs
    public static final String SHARED_PREF = "sharedPref";
    public static final String FIRST_RUN_MAIN = "DBcreated";
    public static final String NOTIFICATION_ON = "DailyNotifications";
    public static final String NEW_REPORT = "AddedReport";
    public static final String DAILY_TIME = "OraNotificaGiornaliera";

    //costante
    public static final String DAILY_TIME_DEFAULT = "false";

    //MONITORAGGIO DEI PARAMETRI
    public static final String MONITORA_TEMP = "MonitoraggioTemperatura";
    public static final String MONITORA_PRESS_SIS = "MonitoraggioPressioneSistolica";
    public static final String MONITORA_PRESS_DIA = "MonitoraggioPressioneDiastolica";
    public static final String MONITORA_GLIC = "MonitoraggioGlicemia";
    public static final String MONITORA_PESO = "MonitoraggioPeso";

    //MONITORAGGIO DEI PARAMETRI
    public static final String EXCEEDED_TEMP = "SuperamentoTemperatura";
    public static final String EXCEEDED_PRESS_SIS = "SuperamentoPressioneSistolica";
    public static final String EXCEEDED_PRESS_DIA = "SuperamentoPressioneDiastolica";
    public static final String EXCEEDED_GLIC = "SuperamentoGlicemia";
    public static final String EXCEEDED_PESO = "SuperamentoPeso";

    //intent code
    public static final String NOTIFICATION_TYPE = "notification_type";
    public static final String TIMED_NOTIFICATION = "timed_notification";
    public static final String REMIND_NOTIFICATION = "reminded_notification";
    public static final String PARAMETERS_OUT_BOUND_NOTIFICATION = "parameters_out_bound_notification";
    public static final String MONITOR_NOTIFICATION = "monitor_notification";

    //id builder notifiche
    public static final int ID_NOTIFICATION_DAILY = 200;
    public static final int ID_NOTIFICATION_REMINDER = 300;
    public static final int ID_NOTIFICATION_MONITOR = 400;

}
