package com.auth0.android.jwt;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A Claim is a value contained inside the JWT Payload.
 */
@SuppressWarnings("WeakerAccess")
public class Claim {

    private final JsonElement value;

    Claim(@NonNull JsonElement value) {
        this.value = value;
    }


    /**
     * Get this Claim as a Boolean.
     * If the value isn't of type Boolean or it can't be converted to a Boolean, null will be returned.
     *
     * @return the value as a Boolean or null.
     * @throws DecodeException if the value can't be converted to a Boolean.
     */
    @Nullable
    public Boolean asBoolean() throws DecodeException {
        try {
            return value.getAsBoolean();
        } catch (Exception e) {
            throw new DecodeException("Failed to decode claim as Boolean", e);
        }
    }

    /**
     * Get this Claim as an Integer.
     * If the value isn't of type Integer or it can't be converted to an Integer, null will be returned.
     *
     * @return the value as an Integer or null.
     * @throws DecodeException if the value can't be converted to an Int.
     */
    @Nullable
    public Integer asInt() throws DecodeException {
        try {
            return value.getAsInt();
        } catch (Exception e) {
            throw new DecodeException("Failed to decode claim as Int", e);
        }
    }

    /**
     * Get this Claim as a Double.
     * If the value isn't of type Double or it can't be converted to a Double, null will be returned.
     *
     * @return the value as a Double or null.
     * @throws DecodeException if the value can't be converted to a Double.
     */
    @Nullable
    public Double asDouble() throws DecodeException {
        try {
            return value.getAsDouble();
        } catch (Exception e) {
            throw new DecodeException("Failed to decode claim as Double", e);
        }
    }

    /**
     * Get this Claim as a String.
     * If the value isn't of type String or it can't be converted to a String, null will be returned.
     *
     * @return the value as a String or null.
     * @throws DecodeException if the value can't be converted to a String.
     */
    @Nullable
    public String asString() throws DecodeException {
        try {
            return value.getAsString();
        } catch (Exception e) {
            throw new DecodeException("Failed to decode claim as String", e);
        }
    }

    /**
     * Get this Claim as a Date.
     * If the value can't be converted to a Date, null will be returned.
     *
     * @return the value as a Date or null.
     * @throws DecodeException if the value can't be converted to a Date.
     */
    @Nullable
    public Date asDate() throws DecodeException {
        try {
            long ms = Long.parseLong(value.getAsString()) * 1000;
            return new Date(ms);
        } catch (Exception e) {
            throw new DecodeException("Failed to decode claim as Date", e);
        }
    }

    /**
     * Get this Claim as an Array of type T.
     * If the value isn't an Array, an empty Array will be returned.
     *
     * @return the value as an Array or an empty Array.
     * @throws DecodeException if the values inside the Array can't be converted to a class T.
     */
    @SuppressWarnings("unchecked")
    public <T> T[] asArray(Class<T> tClazz) throws DecodeException {
        try {
            if (!value.isJsonArray() || value.isJsonNull()) {
                return (T[]) Array.newInstance(tClazz, 0);
            }
            Gson gson = new Gson();
            JsonArray jsonArr = value.getAsJsonArray();
            T[] arr = (T[]) Array.newInstance(tClazz, jsonArr.size());
            for (int i = 0; i < jsonArr.size(); i++) {
                arr[i] = gson.fromJson(jsonArr.get(i), tClazz);
            }
            return arr;
        } catch (JsonSyntaxException e) {
            throw new DecodeException("Failed to decode claim as Array", e);
        }
    }

    /**
     * Get this Claim as a List of type T.
     * If the value isn't an Array, an empty List will be returned.
     *
     * @return the value as a List or an empty List.
     * @throws DecodeException if the values inside the List can't be converted to a class T.
     */
    public <T> List<T> asList(Class<T> tClazz) throws DecodeException {
        try {
            if (!value.isJsonArray() || value.isJsonNull()) {
                return new ArrayList<>();
            }
            Gson gson = new Gson();
            JsonArray jsonArr = value.getAsJsonArray();
            List<T> list = new ArrayList<>();
            for (int i = 0; i < jsonArr.size(); i++) {
                list.add(gson.fromJson(jsonArr.get(i), tClazz));
            }
            return list;
        } catch (JsonSyntaxException e) {
            throw new DecodeException("Failed to decode claim as List", e);
        }
    }
}
