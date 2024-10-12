package com.timix.todo.back.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.timix.todo.back.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static UUID retrieveUserIdFromRequest(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("idUser");
        if (userId == null) {
            throw new RuntimeException("User ID not found in request");
        }
        return userId;
    }
}