package com.ljmu.restility.configuration;

import org.modelmapper.Condition;
import org.modelmapper.spi.MappingContext;

public class SkipNullAndDefaultCondition implements Condition {

    @Override
    public boolean applies(MappingContext mappingContext) {
        Object sourceValue = mappingContext.getSource();
        if (sourceValue == null) {
            return false;
        }
        if (sourceValue instanceof Number) {
            if (sourceValue instanceof Integer && ((Integer) sourceValue).intValue() == 0) {
                return false; // ignore int with default value 0
            }
            if (sourceValue.getClass() == int.class && (int) sourceValue == 0) {
                return false; // ignore int with default value 0
            }
            if (sourceValue instanceof Byte && ((Byte) sourceValue).byteValue() == 0) {
                return false; // ignore byte with default value 0
            }
            if (sourceValue.getClass() == byte.class && (byte) sourceValue == 0) {
                return false; // ignore byte with default value 0
            }
            if (sourceValue instanceof Short && ((Short) sourceValue).shortValue() == 0) {
                return false; // ignore short with default value 0
            }
            if (sourceValue.getClass() == short.class && (short) sourceValue == 0) {
                return false; // ignore short with default value 0
            }
            if (sourceValue instanceof Long && ((Long) sourceValue).longValue() == 0L) {
                return false; // ignore long with default value 0L
            }
            if (sourceValue.getClass() == long.class && (long) sourceValue == 0L) {
                return false; // ignore long with default value 0L
            }
            if (sourceValue instanceof Float && ((Float) sourceValue).floatValue() == 0.0f) {
                return false; // ignore float with default value 0.0f
            }
            if (sourceValue.getClass() == float.class && (float) sourceValue == 0.0f) {
                return false; // ignore float with default value 0.0f
            }
            if (sourceValue instanceof Double && ((Double) sourceValue).doubleValue() == 0.0d) {
                return false; // ignore double with default value 0.0d
            }
            if (sourceValue.getClass() == double.class && (double) sourceValue == 0.0d) {
                return false; // ignore double with default value 0.0d
            }
        }
        if (sourceValue instanceof Boolean && !((Boolean) sourceValue)) {
            return false; // ignore boolean with default value false
        }
        if (sourceValue.getClass() == boolean.class && !(boolean) sourceValue) {
            return false; // ignore boolean with default value false
        }
        if (sourceValue instanceof Character && ((Character) sourceValue).charValue() == '\u0000') {
            return false; // ignore char with default value '\u0000'
        }
        if (sourceValue.getClass() == char.class && (char) sourceValue == '\u0000') {
            return false; // ignore char with default value '\u0000'
        }
        return true;
    }
}
