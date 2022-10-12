package antifraud.constant;

import antifraud.exception.InvalidRegionCodeException;

import java.util.Arrays;

public enum RegionCode {
    EAP, ECA, HIC, LAC, MENA, SA, SSA;

    public static RegionCode getRegionCode(String region) {
        try{
            return RegionCode.valueOf(region);
        } catch (IllegalArgumentException e) {
            throw new InvalidRegionCodeException();
        }
    }

    public static boolean exists(String region) {
        return Arrays.stream(RegionCode.values()).anyMatch(r -> r.name().equals(region));
    }
}
