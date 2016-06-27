package com.pearl.subwayguider.Tools;

import java.util.HashMap;

/**
 * Created by Administrator on 30/05/2016.
 */
public class Tools {
    public static String decTohex(String decimal){

        int number = Integer.parseInt(decimal);
        String a = Integer.toHexString(number);
        return a;

    }

    public static double calculateDistance(int txPower, int rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine distance, return -1.
        }

        double ratio = rssi*1.0/txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio,10);
        }
        else {
            double accuracy =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
            return accuracy;
        }
    }

    /**
     * Created by Administrator on 30/05/2016.
     */
    public static class SampleBleNames {
        private static HashMap<String, String> devicenames = new HashMap();

        static{
            devicenames.put("01010101", "IPS_Exit1");
            devicenames.put("01010102", "IPS_Exit2");
            devicenames.put("01010103", "IPS_Exit3");
            devicenames.put("01010104", "IPS_Exit4");

            devicenames.put("01010201", "IPS_GateL1");
            devicenames.put("01010202", "IPS_GateR1");
            devicenames.put("01010203", "IPS_GateL2");
            devicenames.put("01010204", "IPS_GateR2");

            devicenames.put("01010301", "IPS_Nor1");
            devicenames.put("01010302", "IPS_Nor2");
            devicenames.put("01010303", "IPS_Nor3");
            devicenames.put("01010304", "IPS_Nor4");
            devicenames.put("01010305", "IPS_Nor5");
            devicenames.put("01010306", "IPS_Nor6");
        }

        public static String lookup(String major2minor){
            String name = devicenames.get(major2minor);
            return name == null ? "unknown device":name;
        }
    }
}
