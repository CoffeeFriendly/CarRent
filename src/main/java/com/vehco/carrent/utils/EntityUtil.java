package com.vehco.carrent.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;



@UtilityClass
public class EntityUtil {
    /**
     * Update метод. Записывает значения notnull полей из source объекта в target
     * @param target Объект-цель обновления значений
     * @param source Объект-источник обновленных значений
     */
    public static void updateEntity(Object target, Object source) {
        try {
            if (!target.getClass().equals(source.getClass())) {
                throw new RuntimeException("Source и Target объекты не могут быть разных классов");
            }
            for (Field field : source.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
                field.setAccessible(false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
